package com.example.springSecurity.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthenticatedReactiveAuthorizationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springSecurity.model.User;

@Service
@Transactional
public class MyUserDetailService implements UserDetailsService{
	@Autowired
	UserServices userServices;
	
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException{
		User user = userServices.findUserByUsername(username);
		if(user == null) {
			throw new UsernameNotFoundException(
					"Canot find user with username: " + username);
		}
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), 
				enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, 
				getAuthorities(user));
	}
	private List<GrantedAuthority> getAuthorities(User user) {
		String roleName = userServices.getUserRole(user.getUsername());
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add (new SimpleGrantedAuthority(roleName));
		return authorities;
	}
}
