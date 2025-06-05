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
 * Service class for managing RuleName entities.
 * This class provides CRUD operations for RuleName.
 */
@Service
public class RuleNameServiceImpl implements IRuleNameService {

    private static final Logger logger = LogManager.getLogger(RuleNameServiceImpl.class);

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Retrieves all RuleName entities from the repository.
     * 
     * @return List of all RuleName entities.
     * @throws RuntimeException if an error occurs while retrieving the RuleNames.
     */
    public List<RuleName> getAllRuleName() {
        try {
            logger.info("Fetching all RuleNames from the repository.");
            List<RuleName> ruleNameList = ruleNameRepository.findAll();
            logger.info("Successfully fetched {} RuleNames.", ruleNameList.size());
            return ruleNameList;
        } catch (Exception e) {
            logger.error("Error retrieving all RuleNames.", e);
            throw new RuntimeException("Error retrieving the list of all RuleNames.", e);
        }
    }

    /**
     * Retrieves a RuleName entity by its ID.
     * 
     * @param id The ID of the RuleName to retrieve.
     * @return The RuleName entity.
     * @throws RuntimeException if an error occurs while retrieving the RuleName or if not found.
     */
    public RuleName getRuleNameById(Integer id) {
        try {
            logger.info("Fetching RuleName with ID: {}", id);
            RuleName ruleName = ruleNameRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("No RuleName found with ID: {}", id);
                        return new Exception("RuleName not found with ID: " + id);
                    });
            logger.info("Successfully fetched RuleName with ID: {}", id);
            return ruleName;
        } catch (Exception e) {
            logger.error("Error retrieving RuleName by ID: {}", id, e);
            throw new RuntimeException("Error retrieving RuleName by its ID.", e);
        }
    }

    /**
     * Adds a new RuleName entity to the repository.
     * 
     * @param ruleName The RuleName entity to add.
     * @return The saved RuleName entity.
     * @throws RuntimeException if an error occurs while adding the RuleName.
     */
    public RuleName addRuleName(RuleName ruleName) {
        try {
            logger.info("Adding new RuleName: {}", ruleName);
            RuleName savedRuleName = ruleNameRepository.save(ruleName);
            logger.info("Successfully added new RuleName: {}", savedRuleName);
            return savedRuleName;
        } catch (Exception e) {
            logger.error("Error adding new RuleName.", e);
            throw new RuntimeException("Error adding RuleName.", e);
        }
    }

    /**
     * Deletes a RuleName entity by its ID.
     * 
     * @param id The ID of the RuleName to delete.
     * @throws RuntimeException if an error occurs while deleting the RuleName.
     */
    public void deleteRuleName(Integer id) {
        try {
            logger.info("Deleting RuleName with ID: {}", id);
            RuleName ruleNameToDelete = getRuleNameById(id);
            ruleNameRepository.delete(ruleNameToDelete);
            logger.info("Successfully deleted RuleName with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting RuleName with ID: {}", id, e);
            throw new RuntimeException("Error deleting RuleName.", e);
        }
    }

    /**
     * Updates an existing RuleName entity.
     * 
     * @param id The ID of the RuleName to update.
     * @param ruleName The new RuleName data.
     * @return The updated RuleName entity.
     * @throws RuntimeException if an error occurs while updating the RuleName.
     */
    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        try {
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
        } catch (Exception e) {
            logger.error("Error updating RuleName with ID: {}", id, e);
            throw new RuntimeException("Error updating RuleName.", e);
        }
    }
}
