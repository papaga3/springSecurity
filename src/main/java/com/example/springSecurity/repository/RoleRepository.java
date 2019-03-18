package com.example.springSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springSecurity.model.Role;
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRoleName(String role);
	Role findById(int id);
}
