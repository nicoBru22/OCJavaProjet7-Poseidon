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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;

@SpringBootTest
@AutoConfigureMockMvc
public class Controller_rating_test {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RatingService ratingService;

	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_ratingList_Page() throws Exception {
		List<Rating> ratingList = new ArrayList<>();
		
		when(ratingService.getAllRating()).thenReturn(ratingList);
		
		mockMvc.perform(get("/rating/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/list"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_addRating_Page() throws Exception {
		mockMvc.perform(get("/rating/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/add"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void get_updateRating_Page() throws Exception {
		Rating ratingTest = new Rating();
		ratingTest.setId(1);
		
		when(ratingService.getRatingById(ratingTest.getId())).thenReturn(ratingTest);
		
		mockMvc.perform(get("/rating/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("rating/update"));
	}
}
