package com.nnk.springboot.unitaire.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
public class BidListServiceTest {
	
	@MockBean
	private BidListRepository bidListRepository;
	
	@Autowired
	private IBidListService bidListService;

    @Test
    void getAllBid_shouldReturnEmptyList_whenNoBidExists() {
        when(bidListRepository.findAll()).thenReturn(Collections.emptyList());

        List<BidList> result = bidListService.getAllBid();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        
        verify(bidListRepository, times(1)).findAll();
    }
    
    @Test
    void getBidById_shouldThrowException_whenIdIsNull() {
        Integer id = null;

        assertThrows(IllegalArgumentException.class, () -> {
            bidListService.getBidById(id);
        });

        verifyNoInteractions(bidListRepository);
    }
    
    @Test
    void addBid_shouldThrowException_whenBidIsNull() {
        // Arrange
        BidList bid = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            bidListService.addBid(bid);
        });

        // Vérifie qu'on n'a pas appelé la méthode save() du repository
        verifyNoInteractions(bidListRepository);
    }
    
    
}
