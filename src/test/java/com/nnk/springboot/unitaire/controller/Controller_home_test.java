package com.nnk.springboot.unitaire.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class Controller_home_test {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username="user", roles="USER")
	public void get_homePage_test() throws Exception {
		mockMvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(view().name("home"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_adminHomePage_test() throws Exception {
		mockMvc.perform(get("/admin/home"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/bidList/list"));
	}
	
	@Test
	@WithMockUser(username="user", roles="USER")
	public void get_errorPage_test() throws Exception {
		mockMvc.perform(get("/403"))
			.andExpect(status().isOk())
			.andExpect(view().name("403"));
	}
	
	
	
}
