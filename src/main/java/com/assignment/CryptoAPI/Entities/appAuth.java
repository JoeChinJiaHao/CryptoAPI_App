package com.assignment.CryptoAPI.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Auth_Table")
public class appAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer ID;
    @Column(name="APP_NAME")
    private String appName;
    @Column(name="API_KEY")
    private String apiKey;
    @Column(name="KEY_ROLE") 
    private String role;

    public appAuth(){}
    public appAuth(String role, String appName, String apiKey){
        this.role=role;
        this.appName=appName;
        this.apiKey=apiKey;
        
    }
    public void setAppName(String appName){
        this.appName=appName;
    }
    public String getAppName(){
        return appName;
    }
    public void setApiKey(String apiKey){
        this.apiKey=apiKey;
    }
    public String getApiKey(){
        return this.apiKey;
    }
    public void setRole(String role){
        this.role=role;
    }
    public String getRole(){
        return this.role;
    }

}
