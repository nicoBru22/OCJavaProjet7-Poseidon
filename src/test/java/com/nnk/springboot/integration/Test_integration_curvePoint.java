package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.CurvePointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class Test_integration_curvePoint {
	
	private Logger logger = LogManager.getLogger();	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CurvePointRepository curvePointRepository;
	
	@Autowired
	private CurvePointService curvePointService;
	

	
	@Test
	@Transactional
	@WithMockUser(username="user", roles="USER")
	public void add_CurvePoint_Test() throws Exception {
		CurvePoint testCurvePoint = new CurvePoint();
		testCurvePoint.setTerm(new BigDecimal("10.50"));
		testCurvePoint.setValue(new BigDecimal("50.65"));
		
		mockMvc.perform(post("/curvePoint/validate")
				.param("term", testCurvePoint.getTerm().toString())
				.param("value", testCurvePoint.getValue().toString()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/curvePoint/list"));
		
	}
	
	@Test
	@Transactional
	@WithMockUser(username="user", roles="USER")
	public void delete_CurvePoint_Test() throws Exception {
	    // given
	    CurvePoint testCurvePoint = new CurvePoint();
	    testCurvePoint.setTerm(new BigDecimal("10.50"));
	    testCurvePoint.setValue(new BigDecimal("50"));
	    
	    // when: validation du curve point
	    mockMvc.perform(post("/curvePoint/validate")
	            .param("term", testCurvePoint.getTerm().toString())
	            .param("value", testCurvePoint.getValue().toString()))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/curvePoint/list"));
	    
	    // then: récupération de la liste des curvePoints pour vérifier l'enregistrement
	    List<CurvePoint> curvePointList = curvePointService.getAllCurvePoint();
	    logger.info("La liste de curvePoint : {}", curvePointList);
	    
	    Optional<CurvePoint> savedCurvePoint = curvePointList.stream()
	        .filter(c -> c.getTerm().equals(testCurvePoint.getTerm()) &&
	                     c.getValue().equals(testCurvePoint.getValue()))
	        .findFirst();
	    
	    assertThat(savedCurvePoint).isPresent();
	    
	    Integer curvePointID = savedCurvePoint.get().getId();
	    
	    // when: suppression du curve point via un appel GET
	    mockMvc.perform(get("/curvePoint/delete/" + curvePointID))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/curvePoint/list"));
	    
	    // then: vérification que le curvePoint est supprimé
	    Optional<CurvePoint> existingCurvePoint = curvePointRepository.findById(curvePointID);
	    assertThat(existingCurvePoint).isEmpty();
	}

	
	@Test
	@Transactional
	@WithMockUser(username="user", roles="USER")
	public void update_CurvePoint_Test() throws Exception {
	    CurvePoint testCurvePoint = new CurvePoint();
	    testCurvePoint.setTerm(new BigDecimal("10.50"));
	    testCurvePoint.setValue(new BigDecimal("50"));

	    CurvePoint testCurvePointUpdated = new CurvePoint();
	    testCurvePointUpdated.setTerm(new BigDecimal("200.55"));
	    testCurvePointUpdated.setValue(new BigDecimal("500.91"));

	    mockMvc.perform(post("/curvePoint/validate")
	            .param("term", testCurvePoint.getTerm().toString())
	            .param("value", testCurvePoint.getValue().toString()))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/curvePoint/list"));

	    List<CurvePoint> curvePointList = curvePointService.getAllCurvePoint();
	    Optional<CurvePoint> savedCurvePoint = curvePointList.stream()
	        .filter(c -> c.getTerm().equals(testCurvePoint.getTerm()) &&
	                     c.getValue().equals(testCurvePoint.getValue()))
	        .findFirst();

	    assertThat(savedCurvePoint).isPresent();

	    Integer curvePointID = savedCurvePoint.get().getId();

	    mockMvc.perform(post("/curvePoint/update/" + curvePointID)
	            .param("term", testCurvePointUpdated.getTerm().toString())
	            .param("value", testCurvePointUpdated.getValue().toString()))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/curvePoint/list"));

	    List<CurvePoint> curvePointListUpdated = curvePointService.getAllCurvePoint();

	    // L'ancien n'est plus censé exister
	    Optional<CurvePoint> oldCurvePoint = curvePointListUpdated.stream()
	        .filter(c -> c.getTerm().equals(testCurvePoint.getTerm()) &&
	                     c.getValue().equals(testCurvePoint.getValue()))
	        .findFirst();

	    // Le nouveau doit exister
	    Optional<CurvePoint> updatedCurvePoint = curvePointListUpdated.stream()
	        .filter(c -> c.getTerm().equals(testCurvePointUpdated.getTerm()) &&
	                     c.getValue().equals(testCurvePointUpdated.getValue()))
	        .findFirst();

	    assertThat(oldCurvePoint).isEmpty(); // ancienne valeur supprimée ou modifiée
	    assertThat(updatedCurvePoint).isPresent(); // nouvelle valeur présente
	}


}
