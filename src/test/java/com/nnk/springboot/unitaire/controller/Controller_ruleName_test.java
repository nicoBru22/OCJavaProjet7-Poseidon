package com.nnk.springboot.unitaire.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;

@SpringBootTest
@AutoConfigureMockMvc
public class Controller_ruleName_test {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RuleNameService ruleNameService;
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_ruleNameList_page() throws Exception {
		List<RuleName> ruleNameList = new ArrayList<>();
		
		when(ruleNameService.getAllRuleName()).thenReturn(ruleNameList);
		
		mockMvc.perform(get("/ruleName/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/list"));
		
		verify(ruleNameService, times(1)).getAllRuleName();
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_ruleNameAdd_page() throws Exception {		
		mockMvc.perform(get("/ruleName/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/add"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_ruleNameUpdate_page() throws Exception {	
		RuleName ruleNameTest = new RuleName();
		ruleNameTest.setId(1);
		
		when(ruleNameService.getRuleNameById(ruleNameTest.getId())).thenReturn(ruleNameTest);
		
		mockMvc.perform(get("/ruleName/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("ruleName/update"));
		
		verify(ruleNameService, times(1)).getRuleNameById(ruleNameTest.getId());
	}

}
