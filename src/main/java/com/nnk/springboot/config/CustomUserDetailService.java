package com.nnk.springboot.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

public class CustomUserDetailService implements UserDetailsService{

	
	private static final Logger logger = LogManager.getLogger(CustomUserDetailService.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Tentative de chargement de l'utilisateur avec le username : {}", username);
		
		User user = userRepository.findByUsername(username);
		if(user == null) {
			logger.error("Cet utilisateur n'existe ps en base de donnée.");
			throw new UsernameNotFoundException("Utilisateur non trouvé avec le username : " + username);
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getGrantedAuthorities(user.getRole()));
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(String role) {
		logger.info("Assignation des rôles pour l'utilisateur avec le rôle : {}", role);
		List<GrantedAuthority> authorities = new ArrayList<>();
		try {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		} catch (Exception e) {
			logger.error("Erreur lors de l'ajout des rôles pour : {}", role, e);
			throw new RuntimeException("Erreur lors de l'attribution des rôles", e);
		}
		return authorities;
	}


}
