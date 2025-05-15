package com.project.spring.mdoel;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MainUserPrincipal  implements UserDetails{
	
	 private MainUser mainUser;
	
	

	public MainUserPrincipal(MainUser mainUser) {
		this.mainUser = mainUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return mainUser.getRole().stream().map(role-> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()) ).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return mainUser.getPassword();
	}

	@Override
	public String getUsername() {
		return mainUser.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return mainUser.getIsActive();
	}





}
