package com.nnk.springboot.unitaire.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class Controller_login_test {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	
	@Test
	public void get_loginPage_test() throws Exception {
		mockMvc.perform(get("/app/login"))
			.andExpect(status().isOk())
			.andExpect(view().name("login"));
	}
	
	@Test
	public void get_signupPage_test() throws Exception {
		mockMvc.perform(get("/app/signup"))
			.andExpect(status().isOk())
			.andExpect(view().name("signup"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_userListPage_test() throws Exception {
		List<User> userList = new ArrayList<>();
		
		when(userRepository.findAll()).thenReturn(userList);
		
		mockMvc.perform(get("/app/secure/article-details"))
			.andExpect(status().isOk())
			.andExpect(view().name("user/list"));
	}

}
