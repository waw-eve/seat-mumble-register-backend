package com.waw_eve.seat.mumble;

import org.bouncycastle.asn1.x500.X500Name;
import org.junit.Test;
import static org.junit.Assert.*;

import com.waw_eve.seat.mumble.utils.CertUtil;

public class CertTest {

    @Test
    public void certUtilSign() {
        Configuration configuration = new Configuration();
        configuration.setCaFilePath("/tmp/ca.p12");
        CertUtil.init(configuration);
        X500Name dnName = new X500Name("CN=Higgs-Ielenia Amastica, E=ieleniaamastica@waw-eve.com");
        assertNull(CertUtil.signCert(dnName));
    }
}
