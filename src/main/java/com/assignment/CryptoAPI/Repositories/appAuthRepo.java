package com.assignment.CryptoAPI.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.CryptoAPI.Entities.appAuth;

public interface appAuthRepo extends JpaRepository<appAuth,Integer>{
    // @Query()
    // @Query("select u from Auth_Table u where u.APP_NAME = ?#{[0]} and u.API_KEY=?#{[1]}")
    // List<appAuth>findByApiKeyApp(String appName,String apiKey);
    List<appAuth>findByApiKey(String apiKey);
    List<appAuth>findByappName(String appName);
    @Query(value="SELECT u FROM appAuth u WHERE u.apiKey=:apiKey AND u.appName=:appName")
    List<appAuth> findKeysByAPIKeyAndName(@Param("apiKey")String apiKey,@Param("appName")String appName);
    @Query(value="SELECT u FROM appAuth u WHERE u.apiKey=:apiKey AND u.appName=:appName AND u.role=:role")
    List<appAuth> findKeysByAPIKeyAndNameAndRole(@Param("apiKey")String apiKey,@Param("appName")String appName,@Param("role")String role);

}
