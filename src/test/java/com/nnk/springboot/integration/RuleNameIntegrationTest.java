package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;

import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class RuleNameIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IRuleNameService ruleNameService;
	
	@Test
	@Transactional
	@WithMockUser(username="admin", roles="ADMIN")
	public void addRuleName_test() throws Exception {
		RuleName ruleNameTest = new RuleName();
		ruleNameTest.setName("name test");
		ruleNameTest.setDescription("desciption test");
		ruleNameTest.setJson("jsonTest");
		ruleNameTest.setSqlPart("sqlPartTest");
		ruleNameTest.setSqlStr("sqlStrTest");
		ruleNameTest.setTemplate("templateTest");
		
		mockMvc.perform(post("/ruleName/validate")
				.param("name", ruleNameTest.getName())
				.param("description", ruleNameTest.getDescription())
				.param("json", ruleNameTest.getJson())
				.param("sqlPart", ruleNameTest.getSqlPart())
				.param("sqlStr", ruleNameTest.getSqlStr())
				.param("template", ruleNameTest.getTemplate()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));
			
	}
	
	@Test
	@Transactional
	@WithMockUser(username="admin", roles="ADMIN")
	public void deleteRuleName_test() throws Exception {
		RuleName ruleNameTest = new RuleName();
		ruleNameTest.setName("name test");
		ruleNameTest.setDescription("desciption test");
		ruleNameTest.setJson("jsonTest");
		ruleNameTest.setSqlPart("sqlPartTest");
		ruleNameTest.setSqlStr("sqlStrTest");
		ruleNameTest.setTemplate("templateTest");
		
		mockMvc.perform(post("/ruleName/validate")
				.param("name", ruleNameTest.getName())
				.param("description", ruleNameTest.getDescription())
				.param("json", ruleNameTest.getJson())
				.param("sqlPart", ruleNameTest.getSqlPart())
				.param("sqlStr", ruleNameTest.getSqlStr())
				.param("template", ruleNameTest.getTemplate()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));
		
		List<RuleName> ruleNameList = ruleNameService.getAllRuleName();
		Optional<RuleName> savedRuleName = ruleNameList.stream()
				.filter(s ->
					s.getName().equals(ruleNameTest.getName()) &&
					s.getDescription().equals(ruleNameTest.getDescription()) &&
					s.getJson().equals(ruleNameTest.getJson()) &&
					s.getSqlPart().equals(ruleNameTest.getSqlPart()) &&
					s.getSqlStr().equals(ruleNameTest.getSqlStr()) &&
					s.getTemplate().equals(ruleNameTest.getTemplate()))
				.findFirst();
		
		assertThat(savedRuleName).isPresent();
		
		mockMvc.perform(get("/ruleName/delete/" + savedRuleName.get().getId()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));
		
		List<RuleName> ruleNameListDelete = ruleNameService.getAllRuleName();
		Optional<RuleName> deletedRuleName = ruleNameListDelete.stream()
				.filter(s ->
					s.getName().equals(ruleNameTest.getName()) &&
					s.getDescription().equals(ruleNameTest.getDescription()) &&
					s.getJson().equals(ruleNameTest.getJson()) &&
					s.getSqlPart().equals(ruleNameTest.getSqlPart()) &&
					s.getSqlStr().equals(ruleNameTest.getSqlStr()) &&
					s.getTemplate().equals(ruleNameTest.getTemplate()))
				.findFirst();
		
		assertThat(deletedRuleName).isEmpty();
		
	}
	
	@Test
	@Transactional
	@WithMockUser(username="admin", roles="ADMIN")
	public void updateRuleName_test() throws Exception {
		RuleName ruleNameTest = new RuleName();
		ruleNameTest.setName("name test");
		ruleNameTest.setDescription("desciption test");
		ruleNameTest.setJson("jsonTest");
		ruleNameTest.setSqlPart("sqlPartTest");
		ruleNameTest.setSqlStr("sqlStrTest");
		ruleNameTest.setTemplate("templateTest");
		
		RuleName ruleNameUpdateTest = new RuleName();
		ruleNameUpdateTest.setName("name 2 test");
		ruleNameUpdateTest.setDescription("desciption 2 test");
		ruleNameUpdateTest.setJson("jsonTest 2");
		ruleNameUpdateTest.setSqlPart("sqlPartTest 2");
		ruleNameUpdateTest.setSqlStr("sqlStrTest 2");
		ruleNameUpdateTest.setTemplate("templateTest 2");
		
		mockMvc.perform(post("/ruleName/validate")
				.param("name", ruleNameTest.getName())
				.param("description", ruleNameTest.getDescription())
				.param("json", ruleNameTest.getJson())
				.param("sqlPart", ruleNameTest.getSqlPart())
				.param("sqlStr", ruleNameTest.getSqlStr())
				.param("template", ruleNameTest.getTemplate()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));
		
		List<RuleName> ruleNameList = ruleNameService.getAllRuleName();
		Optional<RuleName> savedRuleName = ruleNameList.stream()
				.filter(s ->
					s.getName().equals(ruleNameTest.getName()) &&
					s.getDescription().equals(ruleNameTest.getDescription()) &&
					s.getJson().equals(ruleNameTest.getJson()) &&
					s.getSqlPart().equals(ruleNameTest.getSqlPart()) &&
					s.getSqlStr().equals(ruleNameTest.getSqlStr()) &&
					s.getTemplate().equals(ruleNameTest.getTemplate()))
				.findFirst();
		
		assertThat(savedRuleName).isPresent();
		
		mockMvc.perform(post("/ruleName/update/" + savedRuleName.get().getId())
				.param("name", ruleNameUpdateTest.getName())
				.param("description", ruleNameUpdateTest.getDescription())
				.param("json", ruleNameUpdateTest.getJson())
				.param("sqlPart", ruleNameUpdateTest.getSqlPart())
				.param("sqlStr", ruleNameUpdateTest.getSqlStr())
				.param("template", ruleNameUpdateTest.getTemplate()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/ruleName/list"));
		
		List<RuleName> ruleNameListUpdated = ruleNameService.getAllRuleName();
		Optional<RuleName> updatedRuleName = ruleNameListUpdated.stream()
				.filter(s ->
					s.getName().equals(ruleNameUpdateTest.getName()) &&
					s.getDescription().equals(ruleNameUpdateTest.getDescription()) &&
					s.getJson().equals(ruleNameUpdateTest.getJson()) &&
					s.getSqlPart().equals(ruleNameUpdateTest.getSqlPart()) &&
					s.getSqlStr().equals(ruleNameUpdateTest.getSqlStr()) &&
					s.getTemplate().equals(ruleNameUpdateTest.getTemplate()))
				.findFirst();
		
		assertThat(updatedRuleName).isPresent();
		
	}

}
