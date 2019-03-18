package com.example.springSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springSecurity.model.User;
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
