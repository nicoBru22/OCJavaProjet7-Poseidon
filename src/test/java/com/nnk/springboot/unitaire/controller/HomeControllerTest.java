package com.nnk.springboot.unitaire.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@WithMockUser(username="user", roles="USER")
	public void get_homePage_test() throws Exception {
		mockMvc.perform(get("/user/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("home/user-home"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_adminHomePage_test() throws Exception {
		mockMvc.perform(get("/admin/home"))
			.andExpect(status().isOk())
			.andExpect(view().name("home/admin-home"));
	}
	
	@Test
	@WithMockUser(username="user", roles="USER")
	public void get_errorPage_test() throws Exception {
		mockMvc.perform(get("/403"))
			.andExpect(status().isOk())
			.andExpect(view().name("403"));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	public void defaultHomeRedirect_withAdminRole_shouldRedirectToAdminHome() throws Exception {
	    mockMvc.perform(get("/"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/admin/home"));
	}

	@Test
	@WithMockUser(username = "user", roles = {"USER"})
	public void defaultHomeRedirect_withUserRole_shouldRedirectToUserHome() throws Exception {
	    mockMvc.perform(get("/"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(view().name("redirect:/user/home"));
	}

	@Test
	public void defaultHomeRedirect_withoutAuth_shouldRedirectToLogin() throws Exception {
	    mockMvc.perform(get("/"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrlPattern("**/login"));
	}

}
