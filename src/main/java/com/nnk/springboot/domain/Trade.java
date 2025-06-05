package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Model de l'entité Trade
 */
@Entity
@Data
@Table(name = "trade")
public class Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer tradeId;
	
	@NotNull
	@NotBlank
	String account;
	
	@NotNull
	@NotBlank
	String type;
	
	@NotNull
	Double buyQuantity;
	
	Double sellQuantity;
	Double buyPrice;
	Double sellPrice;
	String benchmark;
	LocalDateTime tradeDate;
	String security;
	String status;
	String trader;
	String book;
	String creationName;
	LocalDateTime creationDate;
	String revisionName;
	LocalDateTime revisionDate;
	String dealName;
	String dealType;
	String sourceListId;
	String side;
}
