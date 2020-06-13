package com.test.encrypt;

/**
 * @author lixiaoyu
 * @since 2020/6/12
 */
public class CipherResult {

    private String id;

    private String iv;

    private String aad;

    private String secretKey;

    private String encryptText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getAad() {
        return aad;
    }

    public void setAad(String aad) {
        this.aad = aad;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEncryptText() {
        return encryptText;
    }

    public void setEncryptText(String encryptText) {
        this.encryptText = encryptText;
    }

    @Override
    public String toString() {
        return "CipherResult{" +
                "id='" + id + '\'' +
                ", iv='" + iv + '\'' +
                ", aad='" + aad + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", encryptText='" + encryptText + '\'' +
                '}';
    }
}
