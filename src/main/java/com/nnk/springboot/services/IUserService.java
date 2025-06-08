package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;

/**
 * Interface de service pour la gestion des objets {@link User}.
 * Fournit les opérations CRUD pour les entités User.
 */
public interface IUserService {

    /**
     * Récupère la liste complète des utilisateurs.
     * 
     * @return une liste de tous les objets User
     */
    List<User> getAllUsers();

    /**
     * Récupère un utilisateur par son identifiant.
     * 
     * @param id l'identifiant de l'utilisateur recherché
     * @return le User correspondant à l'identifiant, ou {@code null} si non trouvé
     */
    User getUserById(Integer id);

    /**
     * Ajoute un nouvel utilisateur.
     * 
     * @param user l'objet User à ajouter
     * @return le User ajouté, généralement avec son identifiant généré
     */
    User addUser(User user);

    /**
     * Met à jour un utilisateur existant identifié par son id.
     * 
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param user l'objet User contenant les nouvelles données
     * @return le User mis à jour, ou {@code null} si l'id n'existe pas
     */
    User updateUser(Integer id, User user);

    /**
     * Supprime un utilisateur par son identifiant.
     * 
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    void deleteUser(Integer id);
}
