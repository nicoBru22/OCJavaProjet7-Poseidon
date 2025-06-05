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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ICurvePointService curvePointService;
	
	@Test
	@WithMockUser(username="user", roles="USER")
	public void get_CurvePointList_listPage_Test() throws Exception {
		List<CurvePoint> curvePointList = new ArrayList<>();
		
		when(curvePointService.getAllCurvePoint()).thenReturn(curvePointList);
		
		mockMvc.perform(get("/curvePoint/list"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/list"));
	}
	
	@Test
	@WithMockUser(username="user", roles="USER")
	public void get_CurvePointList_addPage_Test() throws Exception {
		mockMvc.perform(get("/curvePoint/add"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/add"));
	}
	
	@Test
	@WithMockUser(username="user", roles="USER")
	public void get_CurvePointList_updatePage_Test() throws Exception {
		CurvePoint testCurvePoint = new CurvePoint();
		testCurvePoint.setCurveId(1);
		
		when(curvePointService.getCurvePointById(testCurvePoint.getCurveId())).thenReturn(testCurvePoint);
		
		mockMvc.perform(get("/curvePoint/update/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("curvePoint/update"));
	}
	
	
}
