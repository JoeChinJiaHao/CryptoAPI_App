package com.assignment.CryptoAPI.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.assignment.CryptoAPI.Entities.appAuth;



@Service
public class onboardingService {
    @Autowired
    repoService repoSVC;

    @Autowired
    tokenService tokenSVC;
    public ResponseEntity<String> onboardApp(String AppName, String token){
        
            if(repoSVC.isSuperAdmin(token)){
                //valid admin
                appAuth obj=new appAuth("Admin",AppName,token);
                
                if(repoSVC.addNewApp(obj)){
                    return ResponseEntity.ok().body("Successfully added new App: "+AppName);
                }else{
                    return ResponseEntity.badRequest().body("invalid AppName");
                }
                
            }else{
                return ResponseEntity.status(403).body("forbidden");
            }
       
       
    }
}
