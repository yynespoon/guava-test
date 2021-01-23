package com.test.encrypt;

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

/**
 * @author lixiaoyu
 * @since 2020/6/12
 */
public class GCMUtils {

    private static final int AES_KEY_SIZE = 256;

    private static final int GCM_IV_LENGTH = 12;

    private static final int GCM_TAG_LENGTH = 16;

    /**
     * data persistence, select one way you like
     */
    private static final CipherService cipherService = new CipherService();

    /**
     *
     * @param text the text to encrypt
     * @param aad
     * @return the id of record
     * @throws Exception
     */
    public static int encrypt(String text, String aad) throws Exception {
        CipherResult cipherResult = new CipherResult();
        // bean aes key generator
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        // init key
        keyGenerator.init(AES_KEY_SIZE);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] iv = new byte[GCM_IV_LENGTH];
        //bean random iv
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        // get aad bytes
        byte[] addBytes = null;
        if(!StringUtils.isEmpty(aad)){
            addBytes = aad.getBytes();
            cipherResult.setAad(Base64.getEncoder().encodeToString(addBytes));
        }
        String encryptText = Base64.getEncoder().encodeToString(doEncrypt(text.getBytes(), secretKey, iv, addBytes));
        //save aes key
        cipherResult.setSecretKey(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
        //save encryptText
        cipherResult.setEncryptText(encryptText);
        //save iv
        cipherResult.setIv(Base64.getEncoder().encodeToString(iv));
        //save
        return cipherService.setCipherResult(cipherResult);
    }

    public static String decrypt(int id, String aad) throws Exception{
        //get record
        CipherResult cipherResult = cipherService.getCipherResult(id);
        //get secretKey
        byte[] decodeKeyBytes = null;
        SecretKey secretKey = new SecretKeySpec(decodeKeyBytes = Base64.getDecoder().decode(cipherResult.getSecretKey()), 0, decodeKeyBytes.length, "AES");
        //get iv
        byte[] iv = Base64.getDecoder().decode(cipherResult.getIv());
        //get encryptText
        byte[] encryptText = Base64.getDecoder().decode(cipherResult.getEncryptText());
        return new String(doDecrypt(encryptText, secretKey, iv, aad.getBytes()));
    }

    private static byte[] doEncrypt(byte[] text, SecretKey secretKey, byte[] iv, byte[] aad) throws Exception {
        // get encrypt arithmetic
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //bean keySpec by secretKey
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        //bean GCMSpec by iv
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        //init
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        //set aad
        if (aad != null) {
            cipher.updateAAD(aad);
        }
        return cipher.doFinal(text);
    }

    private static byte[] doDecrypt(byte[] text, SecretKey secretKey, byte[] iv, byte[] aad) throws Exception {
        // get encrypt arithmetic
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //bean keySpec by secretKey
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
        //bean GCMSpec by iv
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
        //init
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        //set aad
        if (aad != null) {
            cipher.updateAAD(aad);
        }
        return cipher.doFinal(text);
    }

}
