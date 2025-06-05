package com.nnk.springboot.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import jakarta.transaction.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BidListIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BidListRepository bidListRepository;

	@Test
	@Transactional //permet d'annuler l'enregistrement en base de donnée après le test.
	@WithMockUser(username="user", roles="USER")
	public void add_bid_test() throws Exception {
	    BidList testBidList = new BidList();
	    testBidList.setAccount("testAccount");
	    testBidList.setType("testType");
	    testBidList.setBidQuantity(new BigDecimal("15.20"));
	    
	    //vérifie que ça ne contient pas l objet
	    mockMvc.perform(get("/bidList/list"))
		    .andExpect(status().isOk())
		    .andExpect(content().string(not(containsString(testBidList.getAccount()))))
		    .andExpect(content().string(not(containsString(testBidList.getType()))))
		    .andExpect(content().string(not(containsString(testBidList.getBidQuantity().toString()))));

	    // ajoute l objet
	    mockMvc.perform(post("/bidList/validate")
	            .param("account", testBidList.getAccount())
	            .param("type", testBidList.getType())
	            .param("bidQuantity", testBidList.getBidQuantity().toString()))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidList/list"));

	    //verifie que la liste contient l objet
	    mockMvc.perform(get("/bidList/list"))
	        .andExpect(status().isOk())
	        .andExpect(content().string(org.hamcrest.Matchers.containsString(testBidList.getAccount())))
	        .andExpect(content().string(org.hamcrest.Matchers.containsString(testBidList.getType())))
	        .andExpect(content().string(org.hamcrest.Matchers.containsString(testBidList.getBidQuantity().toString())));
	    
	}
	
	@Test
	@Transactional
	@WithMockUser(username = "user", roles = "USER")
	public void addAndDeleteBidListTest() throws Exception {
	    // Créer un nouvel objet BidList pour le test
	    BidList testBidList = new BidList();
	    testBidList.setAccount("testAccount");
	    testBidList.setType("testType");
	    testBidList.setBidQuantity(new BigDecimal("15.20"));

	    // Effectuer le POST pour ajouter le BidList
	    mockMvc.perform(post("/bidList/validate")
	            .param("account", testBidList.getAccount())
	            .param("type", testBidList.getType())
	            .param("bidQuantity", testBidList.getBidQuantity().toString()))
	        .andExpect(status().is3xxRedirection())  // Vérifie la redirection
	        .andExpect(redirectedUrl("/bidList/list"))
	        .andExpect(flash().attribute("info", containsString("Ce Bid a bien été ajouté avec succès")));

	    // Vérifier que le BidList a bien été ajouté dans la base de données
	    List<BidList> bidListFromDb = bidListRepository.findAll();  // Récupérer la liste des BidLists en base
	    Optional<BidList> savedBidList = bidListFromDb.stream()
	            .filter(b -> b.getAccount().equals(testBidList.getAccount()) &&
	                        b.getType().equals(testBidList.getType()) &&
	                        b.getBidQuantity().equals(testBidList.getBidQuantity()))
	            .findFirst();

	    // Vérifier que le BidList a bien été sauvegardé et récupérer l'ID
	    assertThat(savedBidList).isPresent();  // Vérifie que l'objet existe
	    Integer bidListIdFromDb = savedBidList.get().getBidListId();  // Récupérer l'ID du BidList

	    // Supprimer le BidList créé en utilisant l'ID récupéré
	    mockMvc.perform(get("/bidList/delete/" + bidListIdFromDb))  // Utiliser l'ID récupéré dans la redirection
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidList/list"));

	    // Vérifier que le BidList a été supprimé de la base de données
	    bidListFromDb = bidListRepository.findAll();  // Recharger la liste après suppression
	    savedBidList = bidListFromDb.stream()
	            .filter(b -> b.getAccount().equals(testBidList.getAccount()) &&
	                        b.getType().equals(testBidList.getType()) &&
	                        b.getBidQuantity().equals(testBidList.getBidQuantity()))
	            .findFirst();

	    // Vérifier que le BidList n'existe plus
	    assertThat(savedBidList).isEmpty();  // Vérifie que le BidList a été supprimé
	}

	@Test
	@Transactional
	@WithMockUser(username="user", roles="USER")
	public void addAndUpdate_bid_test() throws Exception {
	    BidList testBidList = new BidList();
	    testBidList.setAccount("testAccount");
	    testBidList.setType("testType");
	    testBidList.setBidQuantity(new BigDecimal("15.20"));
	    
	    BidList testBidList2 = new BidList();
	    testBidList2.setAccount("testAccount2");
	    testBidList2.setType("testType2");
	    testBidList2.setBidQuantity(new BigDecimal("30.50"));

	    mockMvc.perform(post("/bidList/validate")
	            .param("account", testBidList.getAccount())
	            .param("type", testBidList.getType())
	            .param("bidQuantity", testBidList.getBidQuantity().toString()))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidList/list"))
	        .andExpect(flash().attribute("info", containsString("Ce Bid a bien été ajouté avec succès")));

	    List<BidList> bidListFromDb = bidListRepository.findAll();
	    Optional<BidList> savedBidList = bidListFromDb.stream()
	            .filter(b -> b.getAccount().equals(testBidList.getAccount()) &&
	                        b.getType().equals(testBidList.getType()) &&
	                        b.getBidQuantity().equals(testBidList.getBidQuantity()))
	            .findFirst();
	    
	    assertThat(savedBidList).isPresent();
	    Integer bidListIdFromDb = savedBidList.get().getBidListId();
	    
	    mockMvc.perform(post("/bidList/update/" + bidListIdFromDb)
	            .param("account", testBidList2.getAccount())
	            .param("type", testBidList2.getType())
	            .param("bidQuantity", testBidList2.getBidQuantity().toString()))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/bidList/list"));
	    
	    List<BidList> bidListUpdated = bidListRepository.findAll();
	    Optional<BidList> updatedBidList = bidListUpdated.stream()
	    		.filter(b -> 
	    			b.getAccount().equals(testBidList2.getAccount()) && 
	    			b.getType().equals(testBidList2.getType()) && 
	    			b.getBidQuantity().equals(testBidList2.getBidQuantity()))
	    		.findFirst();
	    
	    List<BidList> bidListFromDb2 = bidListRepository.findAll();
	    Optional<BidList> savedBidList2 = bidListFromDb2.stream()
	            .filter(b -> b.getAccount().equals(testBidList.getAccount()) &&
	                        b.getType().equals(testBidList.getType()) &&
	                        b.getBidQuantity().equals(testBidList.getBidQuantity()))
	            .findFirst();
	    
	    assertThat(updatedBidList).isPresent();
	    assertThat(savedBidList2).isEmpty();
	
	}
	
	
}
