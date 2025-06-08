package com.nnk.springboot.services.implService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.IRuleNameService;

/**
 * Service pour gérer les entités RuleName.
 * Cette classe fournit les opérations CRUD pour les objets RuleName.
 */
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    private static final Logger logger = LogManager.getLogger(RuleNameServiceImpl.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Récupère tous les RuleName en base de données.
     * 
     * @return Liste de tous les RuleName.
     * @throws RuntimeException en cas d'erreur lors de la récupération.
     */
    public List<RuleName> getAllRuleName() {
        logger.info("Fetching all RuleNames from the repository.");
        List<RuleName> ruleNameList = ruleNameRepository.findAll();
        logger.info("Successfully fetched {} RuleNames.", ruleNameList.size());
        return ruleNameList;
    }

    /**
     * Récupère un RuleName en fonction de son ID.
     * 
     * @param id Identifiant du RuleName à récupérer.
     * @return Le RuleName correspondant à l'ID.
     * @throws RuntimeException si le RuleName n'est pas trouvé ou en cas d'erreur.
     */
    public RuleName getRuleNameById(Integer id) {
        logger.info("Fetching RuleName with ID: {}", id);
        RuleName ruleName = ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("No RuleName found with ID: {}", id);
                    return new RuntimeException("RuleName not found with ID: " + id);
                });
        logger.info("Successfully fetched RuleName with ID: {}", id);
        return ruleName;
    }

    /**
     * Ajoute un nouveau RuleName en base de données.
     * 
     * @param ruleName Le RuleName à ajouter.
     * @return Le RuleName ajouté.
     * @throws RuntimeException en cas d'erreur lors de l'ajout.
     */
    public RuleName addRuleName(RuleName ruleName) {
        logger.info("Adding new RuleName: {}", ruleName);
        RuleName savedRuleName = ruleNameRepository.save(ruleName);
        logger.info("Successfully added new RuleName: {}", savedRuleName);
        return savedRuleName;
    }

    /**
     * Supprime un RuleName en fonction de son ID.
     * 
     * @param id Identifiant du RuleName à supprimer.
     * @throws RuntimeException en cas d'erreur lors de la suppression.
     */
    public void deleteRuleName(Integer id) {
        logger.info("Deleting RuleName with ID: {}", id);
        RuleName ruleNameToDelete = getRuleNameById(id);
        ruleNameRepository.delete(ruleNameToDelete);
        logger.info("Successfully deleted RuleName with ID: {}", id);
    }

    /**
     * Met à jour un RuleName existant.
     * 
     * @param id Identifiant du RuleName à mettre à jour.
     * @param ruleName Les nouvelles données du RuleName.
     * @return Le RuleName mis à jour.
     * @throws RuntimeException en cas d'erreur lors de la mise à jour.
     */
    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        logger.info("Updating RuleName with ID: {}", id);
        RuleName ruleNameToUpdate = getRuleNameById(id);
        ruleNameToUpdate.setDescription(ruleName.getDescription());
        ruleNameToUpdate.setJson(ruleName.getJson());
        ruleNameToUpdate.setName(ruleName.getName());
        ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
        ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
        ruleNameToUpdate.setTemplate(ruleName.getTemplate());

        RuleName updatedRuleName = ruleNameRepository.save(ruleNameToUpdate);
        logger.info("Successfully updated RuleName with ID: {}", id);
        return updatedRuleName;
    }
}
