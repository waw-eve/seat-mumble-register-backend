package com.waw_eve.seat.mumble.utils;

import static org.junit.Assert.assertNotNull;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import com.waw_eve.seat.mumble.Configuration;

import org.junit.Test;

public class CertUtilTests {

    @Test
    public void certUtilTest() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        Configuration configuration = new Configuration();
        configuration.setCaFilePath("/tmp/" + String.valueOf(System.currentTimeMillis()) + ".p12");
        configuration.setCaPassword("");
        CertUtil.init(configuration);
        CertUtil.init(configuration);
        String cn = "Higgs-Ielenia Amastica";
        KeyStore ks = CertUtil.signCert(cn, "ieleniaamastica@waw-eve.com", "War and Wormhole", "");
        assertNotNull(ks);
        Certificate certificate = ks.getCertificate(cn);
        assertNotNull(certificate);
    }

}
