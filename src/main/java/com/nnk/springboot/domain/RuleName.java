package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "rulename")
public class RuleName {
    // TODO: Map columns in data table RULENAME with corresponding java fields
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@NotNull
	@NotBlank
	String name;
	
	@NotNull
	@NotBlank
	String description;
	
	@NotNull
	@NotBlank
	String json;
	
	@NotNull
	@NotBlank
	String template;
	
	@NotNull
	@NotBlank
	String sqlStr;
	
	@NotNull
	@NotBlank
	String sqlPart;
}
