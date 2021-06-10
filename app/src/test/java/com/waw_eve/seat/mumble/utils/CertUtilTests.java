package com.waw_eve.seat.mumble.utils;

import static org.junit.Assert.assertNotNull;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import org.junit.Test;

public class CertUtilTests {

    @Test
    public void certUtilTest() throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        KeyStore ks = CertUtil.signCert("Higgs-Ielenia Amastica", "ieleniaamastica@waw-eve.com", "");
        assertNotNull(ks);
        Certificate certificate = ks.getCertificate("Mumble Identity");
        assertNotNull(certificate);
    }

}
