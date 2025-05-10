package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

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
