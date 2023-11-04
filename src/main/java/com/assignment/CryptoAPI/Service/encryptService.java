package com.assignment.CryptoAPI.Service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assignment.CryptoAPI.Entities.appEncrypt;

@Service
public class encryptService {
    @Autowired
    repoService repoSVC;

    @Autowired
    encryptionService enSVC;

    
    public boolean validkey(String token,String appName){
        return repoSVC.isFromApp(appName, token);
    }

    public ResponseEntity<String> getEncryptedText(String appName, String secretKeyName,String text) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        appEncrypt entity=repoSVC.getEncryptEntity(appName, secretKeyName);
        if(entity==null){
            return ResponseEntity.badRequest().body("Invalid Inputs for appName or SecretName");
        }else{
            String encryptedText=enSVC.encryptText(text, entity.getEncryptionKey(), entity.getSalt());
            return ResponseEntity.ok().body(encryptedText);
        }
    }
    public ResponseEntity<String> getDecryptedText(String appName, String secretKeyName,String text) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{
        appEncrypt entity=repoSVC.getEncryptEntity(appName, secretKeyName);
        if(entity==null){
            return ResponseEntity.badRequest().body("Invalid Inputs for appName or SecretName");
        }else{
            String decryptedText=enSVC.decryptText(text, entity.getEncryptionKey(), entity.getSalt());
            return ResponseEntity.ok().body(decryptedText);
        }
    }
    
}
