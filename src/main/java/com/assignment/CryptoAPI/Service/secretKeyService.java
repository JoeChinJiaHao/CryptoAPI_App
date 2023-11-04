package com.assignment.CryptoAPI.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assignment.CryptoAPI.Entities.appEncrypt;


@Service
public class secretKeyService {
    @Autowired
    repoService repoSVC;

    @Autowired
    encryptionService encryptionSVC;
    public boolean validKey(String key, String appName){
        return repoSVC.isAdmin(appName, key);
    }

    public ResponseEntity<String> addSecretKey(String appName, String SecretKeyName) throws NoSuchAlgorithmException, InvalidKeySpecException{
        //generate secretKey from encrypt class
        String keyString = encryptionSVC.getsecretKeyString(encryptionSVC.generateKey());
        String IVString = encryptionSVC.getIVString();
        appEncrypt newEntity=new appEncrypt(appName,SecretKeyName,keyString,IVString);
        if(repoSVC.addSecretKey(newEntity)){
            return ResponseEntity.ok().body("Added Secretkey: "+SecretKeyName);
        }else{
            return ResponseEntity.badRequest().body("Secret Name already exist: "+SecretKeyName);
        }
        
    }
}
