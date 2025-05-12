package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class Test_integration_user {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
	
	@Test
	@Transactional
	public void addUser_test() throws Exception {
		User userTest = new User();
		userTest.setFullname("testeurNicolas");
		userTest.setUsername("testNico");
		userTest.setRole("user");
		userTest.setPassword("Password@123!");
		
		mockMvc.perform(post("/user/validate")
				.param("fullname", userTest.getFullname())
				.param("username", userTest.getUsername())
				.param("role", userTest.getRole())
				.param("password", userTest.getPassword()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		List<User> userList = userService.getAllUsers();
		Optional<User> savedUser = userList.stream()
				.filter(u ->
					u.getFullname().equals(userTest.getFullname()) &&
					u.getUsername().equals(userTest.getUsername()) &&
					u.getRole().equals(userTest.getRole()))
				.findFirst();
		
		assertThat(savedUser).isPresent();
	}
	
	@Test
	@Transactional
	@WithMockUser(username="admin" , roles="ADMIN")
	public void deleteUser_test() throws Exception {
		User userTest = new User();
		userTest.setFullname("testeurNicolas");
		userTest.setUsername("testNico");
		userTest.setRole("user");
		userTest.setPassword("Password@123!");
		
		mockMvc.perform(post("/user/validate")
				.param("fullname", userTest.getFullname())
				.param("username", userTest.getUsername())
				.param("role", userTest.getRole())
				.param("password", userTest.getPassword()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		List<User> userList = userService.getAllUsers();
		Optional<User> savedUser = userList.stream()
				.filter(u ->
					u.getFullname().equals(userTest.getFullname()) &&
					u.getUsername().equals(userTest.getUsername()) &&
					u.getRole().equals(userTest.getRole()))
				.findFirst();
		
		assertThat(savedUser).isPresent();
		Long userID = savedUser.get().getId();
		
		mockMvc.perform(get("/user/delete/" + userID))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		List<User> userListDelete = userService.getAllUsers();
		Optional<User> deletedUser = userListDelete.stream()
				.filter(u ->
					u.getFullname().equals(userTest.getFullname()) &&
					u.getUsername().equals(userTest.getUsername()) &&
					u.getRole().equals(userTest.getRole()))
				.findFirst();
		
		assertThat(deletedUser).isEmpty();

	}
	
	@Test
	@Transactional
	@WithMockUser(username="admin" , roles="ADMIN")
	public void updateUser_test() throws Exception {
		User userTest = new User();
		userTest.setFullname("testeurNicolas");
		userTest.setUsername("testNico");
		userTest.setRole("user");
		userTest.setPassword("Password@123!");
		
		User userTestUpdate = new User();
		userTestUpdate.setFullname("fullnameTest");
		userTestUpdate.setUsername("usernameTest");
		userTestUpdate.setRole("user");
		userTestUpdate.setPassword("Password@12345!");
		
		mockMvc.perform(post("/user/validate")
				.param("fullname", userTest.getFullname())
				.param("username", userTest.getUsername())
				.param("role", userTest.getRole())
				.param("password", userTest.getPassword()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		List<User> userList = userService.getAllUsers();
		Optional<User> savedUser = userList.stream()
				.filter(u ->
					u.getFullname().equals(userTest.getFullname()) &&
					u.getUsername().equals(userTest.getUsername()) &&
					u.getRole().equals(userTest.getRole()))
				.findFirst();
		
		assertThat(savedUser).isPresent();
		Long userID = savedUser.get().getId();
		
		mockMvc.perform(post("/user/update/" + userID)
				.param("fullname", userTestUpdate.getFullname())
				.param("username", userTestUpdate.getUsername())
				.param("role", userTestUpdate.getRole())
				.param("password", userTestUpdate.getPassword()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		List<User> userListUpdate = userService.getAllUsers();
		Optional<User> updatedUser = userListUpdate.stream()
				.filter(u ->
					u.getFullname().equals(userTestUpdate.getFullname()) &&
					u.getUsername().equals(userTestUpdate.getUsername()) &&
					u.getRole().equals(userTestUpdate.getRole()))
				.findFirst();
		
		assertThat(updatedUser).isPresent();

	}
	
	@Test
	@WithMockUser(username="admin" , roles="ADMIN")
	public void loginUser_test() throws Exception {
		
		User userTest = new User();
		userTest.setFullname("testeurNicolas");
		userTest.setUsername("testNico");
		userTest.setRole("admin");
		userTest.setPassword("Password@123!");
		
		mockMvc.perform(post("/user/validate")
				.param("fullname", userTest.getFullname())
				.param("username", userTest.getUsername())
				.param("role", userTest.getRole())
				.param("password", userTest.getPassword()))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/user/list"));
		
		List<User> userList = userService.getAllUsers();
		Optional<User> savedUser = userList.stream()
				.filter(u ->
					u.getFullname().equals(userTest.getFullname()) &&
					u.getUsername().equals(userTest.getUsername()) &&
					u.getRole().equals(userTest.getRole()))
				.findFirst();
		
		assertThat(savedUser).isPresent();
		
		mockMvc.perform(post("/app/login")
				.param("username", userTest.getUsername())
				.param("password", userTest.getPassword()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/"));
		
		Long userID = savedUser.get().getId();
		
		mockMvc.perform(get("/user/delete/" + userID))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/user/list"));
		
		
	}
	

}
