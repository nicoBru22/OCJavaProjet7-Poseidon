package com.nnk.springboot.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

/**
 * Gestionnaire personnalisé déclenché lors d'une authentification réussie.
 * 
 * Cette classe implémente {@link AuthenticationSuccessHandler} pour rediriger
 * l'utilisateur vers une page spécifique selon son rôle :
 * <ul>
 *   <li>ROLE_ADMIN → /admin/home</li>
 *   <li>ROLE_USER → /user/home</li>
 *   <li>Sinon → /403 (page d'accès refusé)</li>
 * </ul>
 * 
 * L'objectif est de fournir une redirection dynamique basée sur les rôles de l'utilisateur.
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * Méthode appelée automatiquement après une authentification réussie.
     * 
     * @param request   requête HTTP
     * @param response  réponse HTTP
     * @param authentication objet contenant les informations d'authentification et les rôles
     * @throws IOException en cas d'erreur d'entrée/sortie lors de la redirection
     * @throws ServletException en cas d'erreur de servlet
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/home");
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/user/home");
        } else {
            response.sendRedirect("/403");
        }
    }
}
