package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entité représentant un trade dans la table {@code trade}.
 * <p>
 * Cette classe mappe les colonnes de la table {@code TRADE} vers des champs Java.
 * Elle contient les informations relatives à une transaction financière,
 * telles que les quantités, prix, dates et autres détails.
 * </p>
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
