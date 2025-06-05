package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.services.IRatingService;

import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class RatingIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private IRatingService ratingService;
	
	@Autowired
	private RatingRepository ratingRepositorye;
	
	@Test
	@Transactional
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void addRating_test() throws Exception {
	    Rating ratingTest = new Rating();
	    ratingTest.setMoodysRating("moodysRatingTest");
	    ratingTest.setSandRating("sandRatingTest");
	    ratingTest.setFitchRating("fitchRatingTest");
	    ratingTest.setOrderNumber(15);

	    mockMvc.perform(post("/rating/validate")
	            .param("moodysRating", ratingTest.getMoodysRating())
	            .param("sandRating", ratingTest.getSandRating())
	            .param("fitchRating", ratingTest.getFitchRating())
	            .param("orderNumber", ratingTest.getOrderNumber().toString()))
        .andDo(print())
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/rating/list"));
	    
	    List<Rating> ratingList = ratingService.getAllRating();
	    Optional<Rating> savedRating = ratingList.stream()
	    		.filter(r -> 
	    		r.getFitchRating().equals(ratingTest.getFitchRating()) && 
	    		r.getMoodysRating().equals(ratingTest.getMoodysRating()) && 
	    		r.getSandRating().equals(ratingTest.getSandRating()) && 
	    		r.getOrderNumber().equals(ratingTest.getOrderNumber()))
	    		.findFirst();
	    
	    assertThat(savedRating).isPresent();
	    
	}
	
	@Test
	@Transactional
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void deleteRating_test() throws Exception {
	    Rating ratingTest = new Rating();
	    ratingTest.setMoodysRating("moodysRatingTest");
	    ratingTest.setSandRating("sandRatingTest");
	    ratingTest.setFitchRating("fitchRatingTest");
	    ratingTest.setOrderNumber(15);

	    mockMvc.perform(post("/rating/validate")
	            .param("moodysRating", ratingTest.getMoodysRating())
	            .param("sandRating", ratingTest.getSandRating())
	            .param("fitchRating", ratingTest.getFitchRating())
	            .param("orderNumber", ratingTest.getOrderNumber().toString()))
        .andDo(print())
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/rating/list"));
	    
	    List<Rating> ratingList = ratingService.getAllRating();
	    Optional<Rating> savedRating = ratingList.stream()
	    		.filter(r -> 
	    		r.getFitchRating().equals(ratingTest.getFitchRating()) && 
	    		r.getMoodysRating().equals(ratingTest.getMoodysRating()) && 
	    		r.getSandRating().equals(ratingTest.getSandRating()) && 
	    		r.getOrderNumber().equals(ratingTest.getOrderNumber()))
	    		.findFirst();
	    
	    assertThat(savedRating).isPresent();
	    
	    Integer ratingID = savedRating.get().getId();
	    
	    mockMvc.perform(get("/rating/delete/" + ratingID))
	    	.andExpect(status().is3xxRedirection())
	    	.andExpect(redirectedUrl("/rating/list"));
	    
	    Optional<Rating> existingRating = ratingRepositorye.findById(ratingID);
	    assertThat(existingRating).isEmpty();	    
	    
	}
	
	@Test
	@Transactional
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void updateRating_test() throws Exception {
	    Rating ratingTest = new Rating();
	    ratingTest.setMoodysRating("moodysRatingTest");
	    ratingTest.setSandRating("sandRatingTest");
	    ratingTest.setFitchRating("fitchRatingTest");
	    ratingTest.setOrderNumber(15);
	    
	    Rating ratingTest2 = new Rating();
	    ratingTest2.setMoodysRating("moodysRatingTest2");
	    ratingTest2.setSandRating("sandRatingTest2");
	    ratingTest2.setFitchRating("fitchRatingTest2");
	    ratingTest2.setOrderNumber(50);

	    mockMvc.perform(post("/rating/validate")
	            .param("moodysRating", ratingTest.getMoodysRating())
	            .param("sandRating", ratingTest.getSandRating())
	            .param("fitchRating", ratingTest.getFitchRating())
	            .param("orderNumber", ratingTest.getOrderNumber().toString()))
        .andDo(print())
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/rating/list"));
	    
	    List<Rating> ratingList = ratingService.getAllRating();
	    Optional<Rating> savedRating = ratingList.stream()
	    		.filter(r -> 
	    		r.getFitchRating().equals(ratingTest.getFitchRating()) && 
	    		r.getMoodysRating().equals(ratingTest.getMoodysRating()) && 
	    		r.getSandRating().equals(ratingTest.getSandRating()) && 
	    		r.getOrderNumber().equals(ratingTest.getOrderNumber()))
	    		.findFirst();
	    
	    assertThat(savedRating).isPresent();
	    
	    Integer ratingID = savedRating.get().getId();
	    
	    mockMvc.perform(post("/rating/update/" + ratingID)
	            .param("moodysRating", ratingTest2.getMoodysRating())
	            .param("sandRating", ratingTest2.getSandRating())
	            .param("fitchRating", ratingTest2.getFitchRating())
	            .param("orderNumber", ratingTest2.getOrderNumber().toString()))
	    	.andExpect(status().is3xxRedirection())
	    	.andExpect(redirectedUrl("/rating/list"));
	    
	    Optional<Rating> existingRating = ratingRepositorye.findById(ratingID);
	    assertThat(existingRating).isPresent();
	    
	    List<Rating> updatedRatingList = ratingService.getAllRating();
	    Optional<Rating> updatedRating = updatedRatingList.stream()
	    		.filter(u ->
	    		u.getFitchRating().equals(ratingTest2.getFitchRating()) && 
	    		u.getMoodysRating().equals(ratingTest2.getMoodysRating()) && 
	    		u.getSandRating().equals(ratingTest2.getSandRating()) && 
	    		u.getOrderNumber().equals(ratingTest2.getOrderNumber()))
	    		.findFirst();
	    
	    assertThat(updatedRating).isPresent();
	    
	}

}
