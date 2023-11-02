package com.assignment.CryptoAPI.controllers;

import java.sql.PreparedStatement;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.assignment.CryptoAPI.preparedStatements.preparedStatement;


@Controller
@RequestMapping("api/")
public class navController {
    public final preparedStatement queries=new preparedStatement();
    @GetMapping("test")
    public ResponseEntity<String> testApi(){
        //get the value of api key
        
        return ResponseEntity.ok(queries.testString);
    }


}
