package com.assignment.CryptoAPI.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

import com.assignment.CryptoAPI.Entities.apiKeys;
import com.assignment.CryptoAPI.Entities.appAuth;
import com.assignment.CryptoAPI.Entities.appEncrypt;
import com.assignment.CryptoAPI.Repositories.apiKeyRepo;
import com.assignment.CryptoAPI.Repositories.appAuthRepo;
import com.assignment.CryptoAPI.Repositories.appEncryptRepo;

@Service
public class repoService {
    @Autowired
    appEncryptRepo appEncryptRepository;

    @Autowired
    appAuthRepo appAuthRepository;

    @Autowired
    apiKeyRepo apiKeyRepository;
    
    
    public boolean addSecretKey(appEncrypt entity){
        //check if secretName already exist
        List<appEncrypt>eArr=appEncryptRepository.findKeysBySecretKeyAndName(entity.getSecretKey(), entity.getAppName());
        if(eArr.size()==0){
            appEncryptRepository.save(entity);
            return true;
        }else{
            return false;
        }
        
    }
    public appEncrypt getEncryptEntity(String appName, String secretKey){
        List<appEncrypt>enArr=appEncryptRepository.findKeysBySecretKeyAndName(secretKey, appName);
        if(enArr.size()==0){
            return null;
        }else{
            return enArr.get(0);
        }
    }
    public boolean isFromApp(String appName,String apiKey){
        List<appAuth> appAuthArr=appAuthRepository.findKeysByAPIKeyAndName(apiKey, appName);
        if(appAuthArr.size()==0){
            return false;
        }else{
            return true;
        }
    }

    public boolean addNewApp(appAuth authObj){
        //check if appName exist
        List<appAuth> appAuthArr=appAuthRepository.findByappName(authObj.getAppName());
        if(appAuthArr.size()>0){
            return false;
        }else{
            appAuthRepository.save(authObj);
            return true;
        }
        
    }
    public apiKeys getApiKey(String apiKey){
        List<apiKeys> apiKeyArr=apiKeyRepository.findByapiKey(apiKey);
        if(apiKeyArr.size()==0){
            return null;
        }else{
            return apiKeyArr.get(0);
        }
    }
    public void saveApiKey(apiKeys apiObj){
        apiKeyRepository.save(apiObj);
    }
    
    
    
    public boolean isAdmin(String appName, String apiKey){
        List<appAuth> authArr=appAuthRepository.findKeysByAPIKeyAndNameAndRole(apiKey, appName, "Admin");
        if(authArr.size()==0){
            return false;
        }else{
            return true;
        }

    }

    public boolean isSuperAdmin(String apiKey){
        List<appAuth> authArr=appAuthRepository.findKeysByAPIKeyAndNameAndRole(apiKey, "Admin", "Admin");
        if(authArr.size()==0){
            return false;
        }else{
            return true;
        }
    }
}
