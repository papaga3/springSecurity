package com.example.springSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.springSecurity.services.UserServices;
import com.example.springSecurity.Exception.UserException;
import com.example.springSecurity.model.User;
import com.example.springSecurity.payload.CurrentLoginUser;

@RestController
public class UserController {
	@Autowired
	UserServices userServices;
	
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String userRegister(@RequestBody User user) {
        if(userServices.userExists(user.getUsername())) {
        	throw new UserException("User already exist");
        }else {
        	userServices.saveUser(user);
        	return "{\"message\":\"Register successful!\"}";
        }
    }
    
    @GetMapping("/user/getLoginUser")
    public CurrentLoginUser getLoginUser() {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userServices.findUserByUsername(auth.getName());
    	return new CurrentLoginUser(user.getUsername(), user.getEmail());
    }
}
