package com.nnk.springboot.services.implService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.IUserService;

import Exception.UserExistingException;

@Service
public class UserServiceImpl implements IUserService {
	
	private Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * Récupère la liste de tous les utilisateurs.
	 * 
	 * @return une liste d'objets User
	 */
	public List<User> getAllUsers() {
		logger.info("Tentative de récupération de la liste des utilisateurs.");
		List<User> userList = userRepository.findAll();
		logger.info("Liste des utilisateurs récupérée avec succès.");
		return userList;
	}
	
	/**
	 * Récupère un utilisateur par son ID.
	 * 
	 * @param id l'ID de l'utilisateur à récupérer
	 * @return l'objet User correspondant à l'ID
	 */
	public User getUserById(Integer id) {
		logger.info("Tentative de récupération de l'utilisateur avec l'ID : {}", id);
		User user = userRepository.findById(id).orElseThrow();
		logger.info("Utilisateur avec l'ID {} récupéré avec succès.", id);
		return user;
	}
	
	/**
	 * Ajoute un nouvel utilisateur à la base de données.
	 * 
	 * @param user l'utilisateur à ajouter
	 * @return l'utilisateur sauvegardé avec le mot de passe encodé
	 */
	public User addUser(User user) {
		logger.info("Entrée dans la méthode pour ajouter un nouvel utilisateur.");
		
		User userExisting = userRepository.findByUsername(user.getUsername());
		if(userExisting != null) {
			logger.error("L'utilisateur {} existe déjà en base de donnée.", user.getUsername());
			throw new UserExistingException("L'utilisateur existe déjà en base de donnée.");
		}
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		User newUser = userRepository.save(user);
		logger.info("Utilisateur sauvegardé avec succès.");
		logger.info("L'utilisateur sauvegardé : {}", newUser);
		return newUser;
	}
	
	/**
	 * Met à jour les informations d'un utilisateur existant.
	 * 
	 * @param id l'ID de l'utilisateur à mettre à jour
	 * @param user les nouvelles informations de l'utilisateur
	 * @return l'utilisateur mis à jour
	 */
	public User updateUser(Integer id, User user) {
	    logger.info("Tentative de mise à jour de l'utilisateur avec l'ID : {}", id);
	    
	    User userExisting = userRepository.findByUsername(user.getUsername());
	    if (userExisting != null && !userExisting.getId().equals(id.longValue())) {
	        logger.info("Un utilisateur existe déjà pour ce Username : {} ", user.getUsername());
	        throw new UserExistingException("Un utilisateur existe déjà avec ce Username");
	    }

	    String newPassword = bCryptPasswordEncoder.encode(user.getPassword());

	    User userToUpdate = getUserById(id);

	    userToUpdate.setRole(user.getRole());
	    userToUpdate.setUsername(user.getUsername());
	    userToUpdate.setFullname(user.getFullname());
	    userToUpdate.setPassword(newPassword);

	    User userUpdated = userRepository.save(userToUpdate);

	    logger.info("Utilisateur avec l'ID {} mis à jour avec succès.", id);
	    return userUpdated;
	}

	
	/**
	 * Supprime un utilisateur de la base de données.
	 * 
	 * @param id l'ID de l'utilisateur à supprimer
	 */
	public void deleteUser(Integer id) {
		logger.info("Tentative de suppression de l'utilisateur avec l'ID : {}", id);
		User userToDelete = getUserById(id);
		userRepository.delete(userToDelete);
		logger.info("Utilisateur avec l'ID {} supprimé avec succès.", id);
	}
}