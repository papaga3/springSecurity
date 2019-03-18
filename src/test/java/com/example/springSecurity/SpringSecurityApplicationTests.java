package com.example.springSecurity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.springSecurity.model.User;
import com.example.springSecurity.repository.RoleRepository;
import com.example.springSecurity.repository.UserRepository;
import com.example.springSecurity.services.UserServices;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringSecurityApplicationTests {
	@Mock
	private UserRepository mockUserRepo;
	@Mock
	private RoleRepository mockRoleRepo;
	@Mock
	private BCryptPasswordEncoder mockPasswordEncoder;
	
	private UserServices userServicesTest;
	private User user;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userServicesTest = new UserServices(mockRoleRepo, 
				mockUserRepo, mockPasswordEncoder);
		user = new User();
		user.setUserid(1);
		user.setUsername("testUser");
		user.setPassword("password");
		user.setEmail("testUser@test.com");
		
		Mockito.when(mockUserRepo.save(any()))
        		.thenReturn(user);
		Mockito.when(mockUserRepo.findByUsername(anyString()))
        		.thenReturn(user);
	}
	
	@Test
	public void testFindUserByUsername() {
		//Set up
		final String username = "testUser";
		
		//Run the test
		User result = userServicesTest.findUserByUsername(username);
		
		//Verify result
		assertEquals(username, result.getUsername());
	}
	
	@Test
	public void testSaveUser() {
		final String username = "testUser";
		User newUser = mockUserRepo.save(user);
		assertEquals(username, newUser.getUsername());
	}
	
	@Test
	public void testUserExist() {
		
	}
}
