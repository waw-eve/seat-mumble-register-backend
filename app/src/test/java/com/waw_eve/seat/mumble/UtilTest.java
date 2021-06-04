package com.waw_eve.seat.mumble;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import static org.junit.Assert.*;

import com.waw_eve.seat.mumble.utils.AesUtil;
import com.waw_eve.seat.mumble.utils.CertUtil;

public class UtilTest {

    @Test
    public void certUtilTest() {
        Configuration configuration = new Configuration();
        configuration.setCaFilePath("/tmp/ca.p12");
        CertUtil.init(configuration);
        X500Name dnName = new X500Name("CN=Higgs-Ielenia Amastica, E=ieleniaamastica@waw-eve.com");
        assertNull(CertUtil.signCert(dnName));
    }

    public void aesUtilTest() {
        String src = Base64.toBase64String("test".getBytes());
        Configuration configuration = new Configuration();
        configuration.setAesEncryptKey("meow");
        AesUtil.init(configuration);
        String dest = AesUtil.encrypt(src);
        assertNotEquals(src, AesUtil.decrypt(dest));
    }
}
