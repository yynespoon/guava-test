package com.test.encrypt;

import org.junit.Test;

import java.security.SecureRandom;

/**
 * @author lixiaoyu
 * @since 2020/6/12
 */
public class AESGCMArithmeticTest {

    @Test
    public void testEncrypt() throws Exception{
        int encrypt = GCMUtils.encrypt("abcdef", "aaa");
        System.out.println(GCMUtils.decrypt(encrypt, "aaa"));
    }
}
