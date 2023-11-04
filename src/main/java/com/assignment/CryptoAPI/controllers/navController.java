package com.assignment.CryptoAPI.Controllers;



import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.assignment.CryptoAPI.Service.encryptService;
import com.assignment.CryptoAPI.Service.onboardingService;
import com.assignment.CryptoAPI.Service.secretKeyService;
import com.assignment.CryptoAPI.Service.tokenService;





@Controller
@RequestMapping("api/")
public class navController {
   

   
    
    @Autowired
    tokenService tokenSVC;

    @Autowired
    onboardingService onboardingSVC;

    @Autowired
    secretKeyService secretKeySVC;
    
    @Autowired
    encryptService enSVC;
    @GetMapping("token")
    public ResponseEntity<String> testApi(@RequestAttribute("proceed")boolean proceed,@RequestHeader("Authorization")String basic) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        if(proceed){
            return ResponseEntity.ok().body((tokenSVC.createToken(tokenSVC.getUser(basic))));
        }else{
            return null;
        }
        
    }

    
    @PostMapping("encrypt")
    public ResponseEntity<String> encryptTextIntoEncryptedString(@RequestAttribute("proceed")boolean proceed,@RequestBody Map<String,String> data,@RequestHeader("X-API-KEY")String token) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        if(proceed){
            String appName=data.getOrDefault("appName", "");
            String secretKeyName=data.getOrDefault("secretKeyName", "");
            String text=data.getOrDefault("text", "");
            if(appName.compareTo("")==0||secretKeyName.compareTo("")==0||text.compareTo("")==0){
                return ResponseEntity.badRequest().body("invalid input");
            }
            if(!enSVC.validkey(tokenSVC.readToken(token), appName)){
                return ResponseEntity.badRequest().body("Token has no permission/not from app");
            }

            return enSVC.getEncryptedText(appName, secretKeyName,text);
        }else{
            return null;
        }
        
    }
    @PostMapping("decrypt")
    public ResponseEntity<String> decryptTextIntoDecryptedString(@RequestAttribute("proceed")boolean proceed,@RequestBody Map<String,String> data,@RequestHeader("X-API-KEY")String token) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        if(proceed){
            String appName=data.getOrDefault("appName", "");
            String secretKeyName=data.getOrDefault("secretKeyName", "");
            String text=data.getOrDefault("text", "");
            if(appName.compareTo("")==0||secretKeyName.compareTo("")==0||text.compareTo("")==0){
                return ResponseEntity.badRequest().body("invalid input");
            }
            if(!enSVC.validkey(tokenSVC.readToken(token), appName)){
                return ResponseEntity.badRequest().body("Token has no permission/not from app");
            }

            return enSVC.getDecryptedText(appName, secretKeyName,text);
        }else{
            return null;
        }
        
    }
    @PostMapping("createSecretkey")
    public ResponseEntity<String> addKeyIntoApp(@RequestAttribute("proceed")boolean proceed,@RequestBody Map<String,String> data,@RequestHeader("X-API-KEY")String token) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        if(proceed){
            String appName=data.getOrDefault("appName", "");
            String secretKeyName=data.getOrDefault("secretKeyName", "");
            if(appName.compareTo("")==0||secretKeyName.compareTo("")==0){
                return ResponseEntity.badRequest().body("invalid input");
            }
            if(!secretKeySVC.validKey(tokenSVC.readToken(token), appName)){
                return ResponseEntity.badRequest().body("Token has no permission/not from app");
            }

            return secretKeySVC.addSecretKey(appName, secretKeyName);
        }else{
            return null;
        }
        
    }

    @PostMapping("onboard")
    public ResponseEntity<String> onboardApp(@RequestAttribute("proceed")boolean proceed,@RequestHeader("X-API-KEY")String token, @RequestBody Map<String,String> data) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException{
        if(proceed){
            String appName=data.getOrDefault("appName", "");
            if(appName.compareTo("")==0){
                return ResponseEntity.badRequest().body("invalid input");
            }else{
                return onboardingSVC.onboardApp(appName, tokenSVC.readToken(token));
            }
        }else{
            return null;
        }
        
        
    }

}
