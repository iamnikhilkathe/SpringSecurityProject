package com.project.spring.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.spring.mdoel.MainUser;
import com.project.spring.mdoel.MainUserPrincipal;
import com.project.spring.repo.MainUserRepository;

@Service
public class MainUserDetailService implements UserDetailsService {
	
	@Autowired
	private MainUserRepository userRepository;
	
	
	
	

	private PasswordEncoder passwordEncoder = null;

	public MainUserDetailService() {
        // Using BCryptPasswordEncoder to encode passwords
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MainUser user=userRepository.findByUserName(username);
		
		if (user==null) {
			
			throw new UsernameNotFoundException(username+" User not found");
		}
		MainUserPrincipal userPrin = new MainUserPrincipal(user);
		return userPrin;
	}
	
	
	public List<MainUser> getAllMainUser() {
		return  userRepository.findAll();
	}


	public MainUser saveUser(MainUser user) {
		
		String codedPass=passwordEncoder.encode(user.getPassword());
		user.setPassword(codedPass);
		return userRepository.save(user);
	}


	public String deleteMainUser(String userName) {
		userRepository.deleteMainUser(userName);
		return "deleted" ;
	}


	
}
