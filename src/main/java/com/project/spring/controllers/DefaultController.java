package com.project.spring.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	
	    private String videoDirectory="C:\\Users\\Administrator\\Downloads\\videosSuits\\";

	    @GetMapping("/files")
	    public String listVideos() {
	        // future code is coming here
	        return "files";
	    }
}
