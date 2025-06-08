package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Entité représentant une entrée de la table {@code rating}.
 * <p>
 * Cette classe mappe les colonnes de la table {@code RATING} en base de données
 * vers des champs Java. Elle est utilisée pour stocker les notations financières.
 * </p>
 */
@Entity
@Data
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@NotNull
	@NotBlank
	String moodysRating;
	
	@NotNull
	@NotBlank
	String sandRating;
	
	@NotNull
	@NotBlank
	String fitchRating;
	
	@NotNull
	Integer orderNumber;
}
