package com.example.springSecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springSecurity.Exception.UserException;
import com.example.springSecurity.model.Role;
import com.example.springSecurity.model.User;
import com.example.springSecurity.repository.RoleRepository;
import com.example.springSecurity.repository.UserRepository;

@Service
public class UserServices {
	private RoleRepository roleRepo;
	private UserRepository userRepo;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserServices(RoleRepository roleRepo,
			UserRepository userRepo,
			BCryptPasswordEncoder passwordEncoder
			) {
		this.roleRepo = roleRepo;
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public User findUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
	
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoleId(roleRepo.findByRoleName("ROLE_USER").getRoleId());
		userRepo.save(user);
	}
	
	public boolean userExists(String username) {
		User user = userRepo.findByUsername(username);
		if(user == null)
			return false;
		else
			return true;
	}
	
	public String getUserRole(String username) {
		User user = userRepo.findByUsername(username);
		if(user==null)
			throw new UserException("No user found " + username);
		else {
			Role role = roleRepo.findById(user.getRoleId());
			return role.getRoleName();
		}
	}
}
