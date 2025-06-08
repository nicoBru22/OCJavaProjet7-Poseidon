package com.nnk.springboot.unitaire.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
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

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private IBidListService bidListService;
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void test_get_bidList_page() throws Exception {
	    List<BidList> bidLists = new ArrayList<>();
	    bidLists.add(new BidList());

	    when(bidListService.getAllBid()).thenReturn(bidLists);

	    mockMvc.perform(get("/bidList/list"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("bidList/list"))
	        .andExpect(model().attributeExists("bidLists"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void test_get_bidList_add_page() throws Exception {
	    mockMvc.perform(get("/bidList/add"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("bidList/add"));
	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void test_update_bidList() throws Exception {
	    BidList mockBid = new BidList();
	    mockBid.setAccount("accountTest");
	    mockBid.setType("typeTest");
	    mockBid.setBidQuantity(new BigDecimal("15.20"));

	    when(bidListService.getBidById(1)).thenReturn(mockBid);

	    mockMvc.perform(get("/bidList/update/1"))
	        .andExpect(status().isOk())
	        .andExpect(model().attributeExists("bidList"))
	        .andExpect(view().name("bidList/update"));
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void test_update_bidList_not_found() throws Exception {
	    when(bidListService.getBidById(999)).thenReturn(null);

	    mockMvc.perform(get("/bidList/update/999"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidList/list"));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void test_update_bidList_valid() throws Exception {
	    mockMvc.perform(post("/bidList/update/1")
	            .param("account", "UpdatedAccount")
	            .param("type", "UpdatedType")
	            .param("bidQuantity", "100.50"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidList/list"));
	}
	
	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void test_delete_bidList() throws Exception {
	    // Optionnel : mock de la méthode delete, si tu veux vérifier son appel
	    doNothing().when(bidListService).deleteBidList(1);

	    mockMvc.perform(get("/bidList/delete/1"))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidList/list"));

	    verify(bidListService, times(1)).deleteBidList(1);
	}

	@Test
	@WithMockUser(username = "admin", roles = "ADMIN")
	public void test_updateBid_withValidationErrors_shouldReturnUpdateView() throws Exception {
	    mockMvc.perform(post("/bidList/update/1")
	            .param("account", "") // champ vide → invalide car @NotBlank
	            .param("type", "TestType")
	            .param("bidQuantity", "10.00"))
	        .andExpect(status().isOk())
	        .andExpect(view().name("bidList/update"))
	        .andExpect(model().attributeExists("bidList"));
	}	
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void test_addBidList_errorValidation() throws Exception {
		mockMvc.perform(post("/bidList/validate")
		        .param("account", "TestAccount")
		        .param("type", "TestType")
		        .param("bidQuantity", "123.4567")) //bidQuantity valide si 2 chiffres après la virgule
		    .andExpect(status().isOk())
	        .andExpect(view().name("bidList/add"));

	}
	
	@Test
	@WithMockUser(username="admin", roles="ADMIN")
	public void test_addBidList_fieldBlanck() throws Exception {
		mockMvc.perform(post("/bidList/validate")
		        .param("account", "")
		        .param("type", "")
		        .param("bidQuantity", ""))
		    .andExpect(status().isOk())
	        .andExpect(view().name("bidList/add"));

	}


}
