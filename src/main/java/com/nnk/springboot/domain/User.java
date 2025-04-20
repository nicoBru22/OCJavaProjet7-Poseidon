package com.nnk.springboot.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor 
@Table(name = "users")
public class User {
	
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username is mandatory")
    private String username;
    
    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).*$", 
    		message = "Le mot de passe doit contenir au moins une Majuscule, un chiffre, et un symbole parmis : !@#$%^&*.")
    private String password;
    
    @Column(nullable = false)
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    
    @Column(nullable = false)
    @NotBlank(message = "Role is mandatory")
    private String role;
}
