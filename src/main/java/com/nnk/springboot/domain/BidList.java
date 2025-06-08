package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité représentant une entrée de la table {@code bidlist}.
 * <p>
 * Cette classe mappe les colonnes de la table {@code BIDLIST} en base de données
 * vers des champs Java. Elle est utilisée pour stocker et manipuler les données
 * relatives aux offres (bids) dans l'application.
 * </p>
 */
@Entity
@Data
@Table(name = "bidlist")
public class BidList {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer bidListId;
	
	@NotNull
	@NotBlank
	private String account;
	
	@NotNull
	@NotBlank
	private String type;
	
	@NotNull
	@Digits(integer = 10, fraction = 2)
	private BigDecimal bidQuantity;
	
	private Double askQuantity;
	private Double bid;
	private Double ask;
	private String benchmark;
	private LocalDateTime bidListDate;
	private String commentary;
	private String security;
	private String status;
	private String trader;
	private String book;
	private String creationName;
	private LocalDateTime creationDate;
	private String revisionName;
	private LocalDateTime revisionDate;
	private String dealName;
	private String dealType;
	private String sourceListId;
	private String side;
}
