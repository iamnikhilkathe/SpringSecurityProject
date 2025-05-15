package com.project.spring.restControllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.spring.mdoel.MainUser;
import com.project.spring.service.JWTService;
import com.project.spring.service.MainUserDetailService;

@RestController
@RequestMapping(path = "mainUser")
public class MainUserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MainUserDetailService userDetailService;
	@Autowired
	private JWTService jwtservice;
	
	
	@GetMapping()
	public List<MainUser> getMainUser() {
		return userDetailService.getAllMainUser();
	}
	
   @PostMapping
    public MainUser createUser(@RequestBody MainUser user) {
	  // user.setActive(true);
        return userDetailService.saveUser(user);
    }
   
   @DeleteMapping("/{userName}")
   public String deleteMainUser(@PathVariable String userName) {
       return userDetailService.deleteMainUser(userName);
   }
	
   
   //  login for MainUser
   @PostMapping("/loginByUser")
   public ResponseEntity<?> mainUserLogin(@RequestBody Map<String, String> credentials) {
	   Map<String, Object> response = new LinkedHashMap<>();
	    
	   try {
           Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"))
           );
           String token = jwtservice.generateToken(credentials.get("username"));
           
        response.put("status", HttpStatus.OK);
   	    response.put("JWT_Token", token);
           return ResponseEntity.ok(response);
       } catch (AuthenticationException e) {
    	   
    	   response.put("status", HttpStatus.UNAUTHORIZED);
      	   response.put("JWT_Token", null);
      	 response.put("message", "Invalid username or password");
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                   .body(response);
       }    
   }
   

}
