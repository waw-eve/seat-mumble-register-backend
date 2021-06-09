package com.waw_eve.seat.mumble.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import com.waw_eve.seat.mumble.Config;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertUtil {
    private static final Logger logger = LoggerFactory.getLogger(CertUtil.class);

    private static final String SIGNATURE_ALGORITHM = "SHA256WithRSA";
    private static final String CA_FRIENDLY_NAME = "CA";

    private static KeyPairGenerator keyPairGen;

    private static Certificate caCertificate;
    private static PrivateKey caPrivateKey;

    private static String caPassword;

    static {
        // adds the Bouncy castle provider to java security
        Security.addProvider(new BouncyCastleProvider());
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Unsupport RSA", e);
        }
        keyPairGen.initialize(4096, new SecureRandom());
    }

    private CertUtil() {
    }

    public static void init() {
        var configuration = Config.getGlobalConfig();
        caPassword = configuration.getCaPassword();
        KeyStore caKeyStore;
        String caFilePath = configuration.getCaFilePath();
        var caDN = new X500Name(configuration.getCaSubject());
        var caFile = new File(caFilePath);
        logger.info("Initializing certificate tool...");
        try {
            if (!caFile.exists()) {
                caKeyStore = createCA(caDN);
                if (caFile.createNewFile()) {
                    try (var stream = new FileOutputStream(caFile)) {
                        saveToFile(caKeyStore, stream, caPassword);
                    }
                }
            } else {
                try (var stream = new FileInputStream(caFile)) {
                    caKeyStore = loadFromFile(stream, caPassword);
                }
            }
            if (caKeyStore == null) {
                logger.error("Fail to init CA");
                return;
            }
            caPrivateKey = (PrivateKey) caKeyStore.getKey(CA_FRIENDLY_NAME, caPassword.toCharArray());
            if (caPrivateKey == null) {
                logger.error("The certificate tool has not been initialized");
                return;
            } else {
                caCertificate = caKeyStore.getCertificate(CA_FRIENDLY_NAME);
            }
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | IOException e) {
            logger.error("Error in init CA", e);
        }
        logger.info("The certificate tool is initialized.");
    }

    public static KeyStore signCert(String name, String email, String org, String password) {
        if (caPrivateKey == null) {
            logger.error("The certificate tool has not been initialized");
            return null;
        }
        var dnStr = new StringBuilder();
        dnStr.append("CN=").append(name).append(", E=").append(email).append(", O=").append(org);
        var dnName = new X500Name(dnStr.toString());
        var subjectAltNames = GeneralNames
                .getInstance(new DERSequence(new GeneralName[] { new GeneralName(GeneralName.rfc822Name, email) }));
        var keyPair = keyPairGen.genKeyPair();

        long now = System.currentTimeMillis();
        var startDate = new Date(now);

        var certSerialNumber = new BigInteger(Long.toString(now));

        var calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 1);

        var endDate = calendar.getTime();

        try {

            var contentSigner = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).build(caPrivateKey);

            var certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber, startDate, endDate, dnName,
                    keyPair.getPublic()).addExtension(Extension.subjectAlternativeName, false, subjectAltNames);
            return packageCert(keyPair,
                    new JcaX509CertificateConverter().getCertificate(certBuilder.build(contentSigner)), name, password);
        } catch (OperatorCreationException | CertificateException | CertIOException e) {
            logger.error("Error in sign new cert.", e);
            return null;
        }
    }

    private static KeyStore packageCert(KeyPair keyPair, X509Certificate certificate, String friendlyName,
            String password) {
        try {
            var keyStore = KeyStore.getInstance("PKCS12");
            var certChain = new Certificate[] { certificate };
            keyStore.load(null, null);
            if (caCertificate != null) {
                keyStore.setCertificateEntry(CA_FRIENDLY_NAME, caCertificate);
            }
            keyStore.setKeyEntry(friendlyName, keyPair.getPrivate(), password.toCharArray(), certChain);
            return keyStore;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            logger.error("Package certificate to PKCS#12 failed.", e);
            return null;
        }
    }

    private static KeyStore loadFromFile(FileInputStream stream, String password) {
        try {
            var keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(stream, password.toCharArray());
            return keyStore;
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            logger.error("Cannot load KeyStore from file", e);
            return null;
        }
    }

    private static void saveToFile(KeyStore keyStore, FileOutputStream stream, String password) {
        try {
            keyStore.store(stream, password.toCharArray());
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            logger.error("Cannot save KeyStore to file", e);
        }
    }

    private static KeyStore createCA(X500Name dnName) {

        var keyPair = keyPairGen.genKeyPair();

        long now = System.currentTimeMillis();
        var startDate = new Date(now);

        var certSerialNumber = new BigInteger(Long.toString(now));

        var calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 99);

        var endDate = calendar.getTime();

        ContentSigner contentSigner;
        try {
            contentSigner = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).build(keyPair.getPrivate());
        } catch (OperatorCreationException e) {
            logger.error("Cannot init content signer", e);
            return null;
        }

        var certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber, startDate, endDate, dnName,
                keyPair.getPublic());

        // Extensions --------------------------

        // Basic Constraints
        var basicConstraints = new BasicConstraints(true); // <-- true for CA, false for EndEntity
        try {
            certBuilder.addExtension(new ASN1ObjectIdentifier("2.5.29.19"), true, basicConstraints);
        } catch (CertIOException e) {
            logger.error("Cannot add certificate extension", e);
            return null;
        }

        // -------------------------------------

        try {
            X509Certificate certificate = new JcaX509CertificateConverter()
                    .getCertificate(certBuilder.build(contentSigner));
            return packageCert(keyPair, certificate, CA_FRIENDLY_NAME, caPassword);
        } catch (CertificateException e) {
            logger.error("Cannot build certificate", e);
            return null;
        }
    }

}
