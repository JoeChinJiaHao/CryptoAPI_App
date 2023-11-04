package com.assignment.CryptoAPI.Security;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;
import com.assignment.CryptoAPI.Service.tokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class CustomFilter extends GenericFilterBean {

  Logger logger = Logger.getLogger(CustomFilter.class.getName());

  @Autowired
  private tokenService tokenSVC;


  

  @Override
    public void doFilter(
      ServletRequest servletRequest, 
      ServletResponse servletResponse,
      FilterChain chain) throws IOException, ServletException {
        
        var response=(HttpServletResponse)servletResponse;
        var request=(HttpServletRequest)servletRequest;
        if(tokenSVC==null){
          ServletContext context = request.getSession().getServletContext();
          SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, context);
        }
        String path=request.getRequestURI();
        logger.info(path);
        if(path.compareTo("/api/token")==0){
          logger.info("token here");
          //response.sendError(HttpStatus.BAD_REQUEST.value(),String.format("Test %s",path));
          //authenticate password here
          String authHeader=request.getHeader("Authorization");
          if(authHeader==null){
            request.setAttribute("proceed", false);
            if(!response.isCommitted()){
              response.sendError(HttpStatus.UNAUTHORIZED.value(),"Missing Authorization header");
            }
            
          }else{
            if(!authHeader.contains("Basic")){
              request.setAttribute("proceed", false);
              if(!response.isCommitted()){
                response.sendError(HttpStatus.UNAUTHORIZED.value(),"Invalid Auth Type");
              }
              
            }else{
              authHeader=authHeader.replace("Basic ", "");
              byte[] decodedBytes = Base64.getDecoder().decode(authHeader);
              String res=new String(decodedBytes);
              String[] resArr=res.split(":");
              
              boolean validObj=tokenSVC.validObj(resArr[0]);
              if(!validObj){
                request.setAttribute("proceed", false);
                if(!response.isCommitted()){
                  response.sendError(HttpStatus.UNAUTHORIZED.value(),"Invalid Credentials");
                }
                
              }
              if(!tokenSVC.validAttempt(resArr[0])){
                request.setAttribute("proceed", false);
                if(!response.isCommitted()){
                  response.sendError(HttpStatus.TOO_MANY_REQUESTS.value(),"Too many attempts");
                }
                
              }else{
                if(tokenSVC.validCred(resArr[0], resArr[1])){
                  //successful login
                  request.setAttribute("proceed", true);
                }else{
                  //failure login
                  request.setAttribute("proceed", false);
                  if(!response.isCommitted()){
                    response.sendError(HttpStatus.UNAUTHORIZED.value(),"Invalid Credentials");
                  }
                }
              }
              
            }
          }
          
        }else if(path.length()>=11&&path.substring(0,11).compareTo("/h2-console")==0){
          //h2 console remove for prod
          logger.info("h2 here");
        }else if(path.compareTo("/error")==0){
          //error page insert valid page here
          logger.info("error occured");
        }else{
          //auth token here
          String token = request.getHeader("X-API-KEY");
          try {
            token=tokenSVC.readToken(token);
          } catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
              | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException
              | InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          // logger.info(token);
          if(token==null){
            
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing X-API-KEY header");
          }else{
            logger.info("here");
            if(tokenSVC.validToken(token)){
              request.setAttribute("proceed", true);
            }else{
              request.setAttribute("proceed", false);
              if(!response.isCommitted()){
                logger.info("second");
                
                response.sendError(HttpStatus.UNAUTHORIZED.value(),"Invalid Token submitted");
              }
              

            }
          }
          
        }
        chain.doFilter(request, response);
    }
    
  
}
