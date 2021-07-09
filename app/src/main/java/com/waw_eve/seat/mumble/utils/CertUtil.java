package com.waw_eve.seat.mumble.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CertUtil {
    private static final Logger logger = LoggerFactory.getLogger(CertUtil.class);

    private static final String SIGNATURE_ALGORITHM = "SHA256WithRSA";

    private static KeyPairGenerator keyPairGen;

    static {
        // adds the Bouncy castle provider to java security
        Security.addProvider(new BouncyCastleProvider());
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Unsupported RSA", e);
        }
        keyPairGen.initialize(4096, new SecureRandom());
    }

    private CertUtil() {
    }

    public static KeyStore signCert(String name, String email, String password) {
        var dnStr = new StringBuilder();
        dnStr.append("CN=").append(name).append(", E=").append(email);
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
            List<ASN1ObjectIdentifier> asn1ObjectIdentifiersList = new ArrayList<>();
            // Client authentication
            asn1ObjectIdentifiersList.add(new ASN1ObjectIdentifier("1.3.6.1.5.5.7.3.2"));

            var kps = new KeyPurposeId[asn1ObjectIdentifiersList.size()];
            var i = 0;
            for (ASN1ObjectIdentifier oid : asn1ObjectIdentifiersList) {
                kps[i++] = KeyPurposeId.getInstance(oid);
            }
            var contentSigner = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM).build(keyPair.getPrivate());

            var certBuilder = new JcaX509v3CertificateBuilder(dnName, certSerialNumber, startDate, endDate, dnName,
                    keyPair.getPublic()).addExtension(Extension.subjectAlternativeName, false, subjectAltNames)
                            .addExtension(Extension.extendedKeyUsage, false, new ExtendedKeyUsage(kps));
            return packageCert(keyPair,
                    new JcaX509CertificateConverter().getCertificate(certBuilder.build(contentSigner)),
                    "Mumble Identity", password);
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
            keyStore.setKeyEntry(friendlyName, keyPair.getPrivate(), password.toCharArray(), certChain);
            return keyStore;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            logger.error("Package certificate to PKCS#12 failed.", e);
            return null;
        }
    }

}
