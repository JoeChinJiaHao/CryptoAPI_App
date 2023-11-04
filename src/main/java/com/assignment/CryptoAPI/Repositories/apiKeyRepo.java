package com.assignment.CryptoAPI.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.CryptoAPI.Entities.apiKeys;

public interface apiKeyRepo extends JpaRepository<apiKeys,Integer> {
    
    List<apiKeys> findByapiKey(String apiKey);


}
