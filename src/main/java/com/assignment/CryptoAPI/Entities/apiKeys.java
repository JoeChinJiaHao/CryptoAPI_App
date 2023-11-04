package com.assignment.CryptoAPI.Entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="API_KEYS")
//Ignoring new fields on JSON objects
@JsonIgnoreProperties(ignoreUnknown = true)
public class apiKeys implements Serializable{
    @Id
    @Column(name="API_KEY")
    private String apiKey;
    @Column(name="API_HASH")
    private String hash;
    @Column(name="LAST_ATTEMPT")
    private Long LastAttempt;
    @Column(name="ATTEMPTS")
    private Integer Attempts;
    @Column(name="SUCCESSFUL_ATTEMPT")
    private Long lastSucc;

    public apiKeys(String apiKey,String hash){
        this.apiKey=apiKey;
        this.hash=hash;
        this.LastAttempt=(long) 0;
        this.Attempts=0;
    }
    public apiKeys(){
        
    }
    public Long getLastSucc(){
        return this.lastSucc;
    }
    public void setLastSucc(Long lastSucc){
        this.lastSucc=lastSucc;
    }
    public Long getLastAttempt(){
        return this.LastAttempt;
    }
    public void setLastAttempt(Long LastAttempt){
        this.LastAttempt=LastAttempt;
    }
    public Integer getAttemptCount(){
        return this.Attempts;
    }
    public void setAttemptCount(Integer Attempts){
        this.Attempts=Attempts;
    }
    public String getHash(){
        return this.hash;
    }

}
