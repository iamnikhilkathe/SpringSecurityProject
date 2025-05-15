package com.project.spring.restControllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "main")
public class MainRestController {

	@GetMapping()
	public String mainConMethod() {
		return "got access to secured endpoint";
	}


	 @GetMapping("/user")
	    public OAuth2User user(@AuthenticationPrincipal OAuth2User principal) {
	        return principal;
	    }
	
	
}
