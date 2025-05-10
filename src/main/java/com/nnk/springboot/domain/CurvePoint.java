package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	Integer curveId;
	
	Timestamp asOfDate;
	
	@NotNull
	@Digits(integer = 10, fraction = 2)
	BigDecimal term;

	@NotNull
	@Digits(integer = 10, fraction = 2)
	BigDecimal value;
	
	Timestamp creationDate;
}
