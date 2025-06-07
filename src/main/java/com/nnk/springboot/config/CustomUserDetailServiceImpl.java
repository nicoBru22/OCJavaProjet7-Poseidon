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
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/**
 * Service permettant de charger les informations d'un utilisateur pour l'authentification Spring Security.
 */
@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private Logger logger = LogManager.getLogger(CustomUserDetailServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Charge un utilisateur par son nom d'utilisateur.
     * 
     * @param username Le nom d'utilisateur fourni pour l'authentification.
     * @return Un objet UserDetails contenant les informations de l'utilisateur.
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Tentative de chargement de l'utilisateur : {}", username);

        User user = userRepository.findByUsername(username);
        if (user == null) {
            logger.warn("Utilisateur non trouvé : {}", username);
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }

        logger.info("Utilisateur trouvé : {} - Attribution des rôles", username);
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), getGrantedAuthorities(user.getRole()));
    }

    /**
     * Récupère les autorités (rôles) d'un utilisateur.
     * 
     * @param role Le rôle de l'utilisateur sous forme de chaîne de caractères.
     * @return Une liste d'objets GrantedAuthority représentant les rôles de l'utilisateur.
     */
    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        logger.debug("Attribution du rôle ROLE_{}", role);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return authorities;
    }
}
