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

import com.waw_eve.seat.mumble.Configuration;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
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

    private static final String signatureAlgorithm = "SHA256WithRSA";

    private static KeyPairGenerator keyPairGen;

    private static KeyStore caKeyStore;

    private static PrivateKey caPrivateKey;

    private static String caPassword;

    private static X500Name caDN;

    private static String caSubject;

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

    public static void init(Configuration configuration) {
        String caFilePath = configuration.getCaFilePath();
        caSubject = configuration.getCaSubject();
        caDN = new X500Name(caSubject);
        caPassword = configuration.getCaPassword();
        File caFile = new File(caFilePath);
        logger.info("Initializing certificate tool");
        try {
            if (!caFile.exists()) {
                caKeyStore = createCA(caDN);
                saveToFile(caKeyStore, caFile, caPassword);
            } else {
                caKeyStore = loadFromFile(caFile, caPassword);
            }
            caPrivateKey = (PrivateKey) caKeyStore.getKey(caSubject, caPassword.toCharArray());
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e) {
            logger.error("Error in init CA", e);
        }
        logger.info("The certificate tool is initialized");
    }

    public static KeyStore signCert(X500Name dnName) {
        if (caPrivateKey == null) {
            logger.error("The certificate tool has not been initialized");
            return null;
        }
        KeyPair keyPair = keyPairGen.genKeyPair();

        long now = System.currentTimeMillis();
        Date startDate = new Date(now);

        BigInteger certSerialNumber = new BigInteger(Long.toString(now));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 1);

        Date endDate = calendar.getTime();

        try {

            ContentSigner contentSigner = new JcaContentSignerBuilder(signatureAlgorithm).build(caPrivateKey);

            JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber,
                    startDate, endDate, dnName, keyPair.getPublic());
            return packageCert(keyPair,
                    new JcaX509CertificateConverter().getCertificate(certBuilder.build(contentSigner)), "");
        } catch (OperatorCreationException | CertificateException e) {
            logger.error("Error in sign new cert.", e);
            return null;
        }
    }

    private static KeyStore packageCert(KeyPair keyPair, X509Certificate certificate, String password) {
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setKeyEntry(certificate.getSubjectX500Principal().getName(), keyPair.getPrivate(),
                    password.toCharArray(), new Certificate[] { certificate });
            return keyStore;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            logger.error("Package certificate to PKCS#12 failed.", e);
            return null;
        }
    }

    private static KeyStore loadFromFile(File certFile, String password) {
        if (!certFile.exists()) {
            return null;
        }
        FileInputStream stream;
        try {
            stream = new FileInputStream(certFile);
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(stream, password.toCharArray());
            stream.close();
            return keyStore;
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            logger.error("Cannot load KeyStore from file", e);
            return null;
        }
    }

    private static void saveToFile(KeyStore keyStore, File certFile, String password) {
        try {
            certFile.createNewFile();
            FileOutputStream stream = new FileOutputStream(certFile);
            keyStore.store(stream, password.toCharArray());
            stream.close();
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            logger.error("Cannot save KeyStore to file", e);
        }
    }

    private static KeyStore createCA(X500Name dnName) {

        KeyPair keyPair = keyPairGen.genKeyPair();

        long now = System.currentTimeMillis();
        Date startDate = new Date(now);

        BigInteger certSerialNumber = new BigInteger(Long.toString(now));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.YEAR, 99);

        Date endDate = calendar.getTime();

        ContentSigner contentSigner;
        try {
            contentSigner = new JcaContentSignerBuilder(signatureAlgorithm).build(keyPair.getPrivate());
        } catch (OperatorCreationException e) {
            logger.error("Cannot init content signer", e);
            return null;
        }

        JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber, startDate,
                endDate, dnName, keyPair.getPublic());

        // Extensions --------------------------

        // Basic Constraints
        BasicConstraints basicConstraints = new BasicConstraints(true); // <-- true for CA, false for EndEntity
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
            return packageCert(keyPair, certificate, caPassword);
        } catch (CertificateException e) {
            logger.error("Cannot build certificate", e);
            return null;
        }
    }

}
