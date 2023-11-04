package com.assignment.CryptoAPI.Service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.assignment.CryptoAPI.Entities.apiKeys;

@Service
public class tokenService {
    public final static Integer maxFailCount=5;
    public final static Long failureTimeInterval=(long) (1000*60*10);
    public final static Long tokenValidity=(long)1000*60;

    @Autowired
    repoService repoSVC;

    @Autowired
    encryptionService encryptSVC;
    
    public String getUser(String basic){
        basic=basic.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(basic);
        String res=new String(decodedBytes);
        String[] resArr=res.split(":");
        return resArr[0];
    }
    public ResponseEntity<String> getToken(String basic){
        basic=basic.replace("Basic ", "");
        byte[] decodedBytes = Base64.getDecoder().decode(basic);
        String res=new String(decodedBytes);
        String[] resArr=res.split(":");
        if(resArr.length!=2){
            return ResponseEntity.badRequest().body("Invalid header");
        }
        
        apiKeys obj=repoSVC.getApiKey(resArr[0]);
        if(obj==null){
            return ResponseEntity.badRequest().body("invalid header");
        }
        if(!validAttempt(obj)){
            return ResponseEntity.status(429).body("Too many request");
        }
        if(BCrypt.checkpw(resArr[1], obj.getHash())){
            //successful login
            obj.setAttemptCount(0);
            obj.setLastSucc(new Date().getTime());
            repoSVC.saveApiKey(obj);
            return ResponseEntity.ok().body(resArr[0]);
        }else{
            //failure login
            if(obj.getLastAttempt()+failureTimeInterval<new Date().getTime()){
                //reset count
                obj.setAttemptCount(0);
                obj.setLastAttempt(new Date().getTime());
            }
            obj.setAttemptCount(obj.getAttemptCount()+1);
            repoSVC.saveApiKey(obj);
            return ResponseEntity.status(401).body("invalid credentials");
        }
        
    }

    public boolean validCred(String user, String pass){
        apiKeys obj=repoSVC.getApiKey(user);
        if(obj==null){
            return false;
        }else{
            if(BCrypt.checkpw(pass, obj.getHash())){
                // successful login
                obj.setAttemptCount(0);
                obj.setLastSucc(new Date().getTime());
                repoSVC.saveApiKey(obj);
                return true;
            }else{
                //failed login
                if(obj.getLastAttempt()+failureTimeInterval<new Date().getTime()){
                //reset count
                    obj.setAttemptCount(0);
                    obj.setLastAttempt(new Date().getTime());
                }
                obj.setAttemptCount(obj.getAttemptCount()+1);
                repoSVC.saveApiKey(obj);
                return false;
            }
        }
    }

    public boolean validObj(String apikey){
        apiKeys obj=repoSVC.getApiKey(apikey);
        if(obj==null){
            return false;
        }else{
            return true;
        }
    }
    public boolean validToken(String apiKey){
        return validToken(repoSVC.getApiKey(apiKey));
    }
    public boolean validToken(apiKeys apiObj){
        if(apiObj==null){
            return false;
        }else{
            
            if(apiObj.getLastSucc()+tokenValidity>new Date().getTime()){
                return true;
            }else{
                return false;
            }
            
        }
    }
    public boolean validAttempt(apiKeys apiObj){
        if(apiObj==null){
            return false;
        }else{
            if(apiObj.getLastAttempt()+failureTimeInterval<new Date().getTime()){
                //reset count
                apiObj.setAttemptCount(0);
                apiObj.setLastAttempt(apiObj.getLastAttempt());
                repoSVC.saveApiKey(apiObj);
            }
            if(apiObj.getAttemptCount()<=maxFailCount){
                return true;
            }else{
                return false;
            }
        }
    }
    public boolean validAttempt(String apikey){
        return validAttempt(repoSVC.getApiKey(apikey));
    }
    
    public String createToken(String apikey) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        return encryptSVC.tokenEncrypt(apikey);
    }

    public String readToken(String token) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        return encryptSVC.tokenDecrypt(token);
    }
}
