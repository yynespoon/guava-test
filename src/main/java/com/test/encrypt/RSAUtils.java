package com.test.encrypt;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * java 中 RSA 加密公钥支持格式为 X509EncodedKeySpec
 *                私钥支持格式为 PKCS8EncodedKeySpec
 *
 *
 * 生成方式
 *  openssl genrsa -out rsa_private_key.pem 2048
 *  openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
 *  openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt
 *
 *
 * @author lixiaoyu
 * @since 2020/6/17
 */
public class RSAUtils {

    public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDC5PBULcKnd2T1" +
            "hnwLyZaKjGFo6OlucjPzQh/ai2WZ8JcCDTqtyVufaP0YD+UPXnekGXQHcH0Rry0o" +
            "UQ8scwoLH9TDDZis1RQrRKJqJwxbKa3VpEyqer4B7BHfqKzN1pQ93T+kZsPVuNFp" +
            "VW9Wu4ru8fwkN7eKxQZRHsEQk8gPyDLXDulY8c3NSNJuQEyMkTQR/bUFhJ7ghM/D" +
            "m5voW2fKN2on922c4GGv4KLfdlFadGNZ6FxXT2AJMNM6KDNutuwAOo0SGMa+M1u0" +
            "CLpzDj1VJ19vVUJbOZOF1pDrSUowDWR6Jc9Kp1pr+t4v8IPdYwkYqPJWbx/koIEq" +
            "gMoSy0wtAgMBAAECggEAMw6pC1SFpzu3Hj9iJfBk2N0X89T4um9+vp63G5jjQCXw" +
            "WaA/nCGhOCzYCUK4voMAEuR8KirFCSn8SZCayglFO07WfVcFhVm5E/DCH88lBIjx" +
            "uoN1dj/7oSNIN3DH/JDXezGIJ7ioW0g0zESOi4lMR3brmE1kbx1Yn03QRFiC0+co" +
            "qCvyJRNhfnNcLaWCEZsDN38v3BLzJ+uvX/eTf+NcYUd9QzmFQzdClt15AOAMMAFj" +
            "0pFPaOJdOZsx/8MK8fyV2akbGW+WXkYPtxKjl1zXZ8Fq7cbmanBx52pVDk0XgAXb" +
            "wj+IGtY0ykiWe3jwjdRl2OJujgbhKZBPIwHjko9RAQKBgQDhk1WIqKmbO0wMaZZ6" +
            "eDQCrLEBTGAVCg6hsrSewfpomwyKZ7b2gmm+9gwsM84g3JEKLSKkoXYlKHXuzDna" +
            "ndAVwQ7UUmyyGBZaYhCNnY2UBKgGW2mjDdr+44ZHlKxdUMIT3ko29rbxN6WiLELH" +
            "QslCBGNsgwFYhQJmYZclrnAUwQKBgQDdLj5vY99O1A70fpO6NLvVSQ2M5GLkH5IW" +
            "knYQYK5wPeLc2QGzBKBjYkJTp7/pRyhXnCM46GJJW0JQ8wmMWLq0N5T6qQ63mAvU" +
            "DLRWQW8V0ylg0EI4Wrs+rTHgcqC3LW9ufPGc2u9bnRMBS6kk1Rh+IeF7JKAdONuh" +
            "VVNKPGH2bQKBgQC4N4tltF/2nsFnbZ9vWtUWzhMrvUTGSdVTJk8tS7sFTII8UaDw" +
            "xeY4BlZAjvoqihaE2gFts1J53AYbw3sH11OFQImsybds5hDzNRzRmjZyCC90KZkm" +
            "iP0spYEW2gq9lmM4uJOhOXCDo+9Hdp/+fMNBf7X2ezJRKSahl9dI9aEgwQKBgQCQ" +
            "40EtENxwtANT8DLvkKnWzvMcb2PEklXNiYXjtqGujyu0hkdRsN8sG2rdiA70TpPD" +
            "yFleZwQzj5+CjaKcmGc9tQdXqDUm2WguMWBU/Ko7PsdlTyeB6RWtuiDpKc3UEORl" +
            "GV3g5Z5UgjG38ItNwHY58hHV7Cki22MsbUuRXrrBhQKBgE+VLPfOQjslz/UvK1zn" +
            "k5xJp9y7flLY0KVmdNTIkLyUFGBgBqypF0RiypBOZPN6nv1FIBCXugvKTRBhASUN" +
            "mGNMYs3YSEL+eDfXV7WZ7R3zv+SSU0QwwVCQJ2Dx6A5uB66xWUNVdk8cfDefsq+g" +
            "MiqS7E3a3YezRaK2bI8NY1GM";

    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwuTwVC3Cp3dk9YZ8C8mW" +
            "ioxhaOjpbnIz80If2otlmfCXAg06rclbn2j9GA/lD153pBl0B3B9Ea8tKFEPLHMK" +
            "Cx/Uww2YrNUUK0SiaicMWymt1aRMqnq+AewR36iszdaUPd0/pGbD1bjRaVVvVruK" +
            "7vH8JDe3isUGUR7BEJPID8gy1w7pWPHNzUjSbkBMjJE0Ef21BYSe4ITPw5ub6Ftn" +
            "yjdqJ/dtnOBhr+Ci33ZRWnRjWehcV09gCTDTOigzbrbsADqNEhjGvjNbtAi6cw49" +
            "VSdfb1VCWzmThdaQ60lKMA1keiXPSqdaa/reL/CD3WMJGKjyVm8f5KCBKoDKEstM" +
            "LQIDAQAB";

    public static RSAPrivateKey initPrivateKey() throws Exception {
        return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY.getBytes())));
    }

    public static RSAPublicKey initPublicKey() throws Exception{
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(PUBLIC_KEY.getBytes())));
    }

    @Test
    public void checkKey() throws Exception{
        String text = "abc";
        Cipher rsa = Cipher.getInstance("RSA");
        rsa.init(Cipher.ENCRYPT_MODE, initPrivateKey());
        byte[] bytes = rsa.doFinal(text.getBytes());

        Cipher decrypt = Cipher.getInstance("RSA");
        decrypt.init(Cipher.DECRYPT_MODE, initPublicKey());
        byte[] bytes1 = decrypt.doFinal(bytes);

        Assert.assertEquals(text, new String(bytes1));
    }
}
