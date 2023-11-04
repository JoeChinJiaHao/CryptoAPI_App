package com.assignment.CryptoAPI.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.assignment.CryptoAPI.Entities.appEncrypt;

public interface appEncryptRepo extends JpaRepository<appEncrypt,Integer>{
    @Query("SELECT u from appEncrypt u WHERE u.secretKey=:secretKey AND u.appName=:appName")
    List<appEncrypt> findKeysBySecretKeyAndName(@Param("secretKey")String secretKey,@Param("appName")String appName);
}
