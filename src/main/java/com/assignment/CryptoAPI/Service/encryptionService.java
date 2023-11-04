package com.assignment.CryptoAPI.Service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class encryptionService {

 
    private final static String tokenBaseStr="JgPjMK5R8ANOehTjr742OKJFR9z/YE+Yt6ktczKE1ak=";
    private final static String algo="AES/CBC/PKCS5Padding";
    private final static String localIV="/q7VbrVeV10trHuX36x5og==";
    public String tokenEncrypt(String unencrypted)throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        return encryptText(unencrypted, tokenBaseStr, localIV);
        
    }
    public String encryptText(String plainText,String key,String IV) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        Cipher cipher = Cipher.getInstance(algo);
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKeyFromString(key), getIVFromString(IV));
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }
    public String tokenDecrypt(String encrypted)throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        return decryptText(encrypted, tokenBaseStr, localIV);
    }

    public String decryptText(String encryptedText, String Key,String IV) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException{
        Cipher cipher = Cipher.getInstance(algo);
        cipher.init(Cipher.DECRYPT_MODE, getSecretKeyFromString(Key), getIVFromString(IV));
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
        .decode(encryptedText));
        return new String(plainText);
    }
    public static SecretKey generateLocalTokenKey()throws NoSuchAlgorithmException,InvalidKeySpecException{
        return getSecretKeyFromString(tokenBaseStr);
    }
    public String getsecretKeyString(SecretKey key) throws NoSuchAlgorithmException, InvalidKeySpecException{
        return  Base64.getEncoder().encodeToString(key.getEncoded());
    }
    public static SecretKey getSecretKeyFromString(String key){
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    public SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
    public String getIVString(){
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }
    public static IvParameterSpec getIVFromString(String iv){
        byte[] decodedIV = Base64.getDecoder().decode(iv);
        return new IvParameterSpec(decodedIV);
    }

}
