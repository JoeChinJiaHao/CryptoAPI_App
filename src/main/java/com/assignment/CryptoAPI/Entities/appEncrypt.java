package com.assignment.CryptoAPI.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Encryption_Table")
public class appEncrypt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer ID;
    @Column(name="APP_NAME")
    private String appName;
    @Column(name="SECRET_KEY")
    private String secretKey;
    @Column(name="ENCRYPT_KEY")
    private String encryptionKey;
    @Column(name="SALT")
    private String salt;

    public appEncrypt(){}
    public String getSalt(){
        return this.salt;
    }
    public String getEncryptionKey(){
        return this.encryptionKey;
    }
    public String getSecretKey(){
        return this.secretKey;
    }
    public String getAppName(){
        return this.appName;
    }
    public appEncrypt(String appName,String secretKey,String encryptionKey,String salt){
        this.appName=appName;
        this.secretKey=secretKey;
        this.encryptionKey=encryptionKey;
        this.salt=salt;
    }

}
