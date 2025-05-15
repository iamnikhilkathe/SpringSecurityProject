package com.project.spring.controllers;

import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller

public class DefaultController {
	 @GetMapping("/")
	    public String home(Model model,HttpServletRequest request,Authentication authentication) {
		 
		 if (authentication != null
		            && authentication.isAuthenticated()
		            && !(authentication instanceof AnonymousAuthenticationToken)) {
		            return "redirect:/home";
		        }
	        model.addAttribute("message","Home Page" );
	        return "index"; 
	    }
	 
	  @GetMapping("/home")
	    public String home(Model model) {
	        model.addAttribute("message", "Welcome to the Application");
	        return "home";
	    }
	
}
