package com.nnk.springboot.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameService {
	
	private Logger logger = LogManager.getLogger(RuleNameService.class);
	
	@Autowired
	private RuleNameRepository ruleNameRepository;

	public List<RuleName> getAllRuleName() {
		
		try {
			List<RuleName> ruleNameList = ruleNameRepository.findAll();
			return ruleNameList;
		} catch (Exception e){
			throw new RuntimeException("Erreur lors de la récupération de la liste de tous les RuleName."+ e);
		}
	}
	
	
	public RuleName getRuleNameById(Integer id) {
		
		try {
			RuleName ruleName = ruleNameRepository.findById(id)
					.orElseThrow(() -> {
						logger.warn("Aucun RUleName trouvé avec l'id {} ", id);
						return new Exception("RuleName introuvable avec l'Id" + id);
					});
			return ruleName;
		} catch (Exception e){
			throw new RuntimeException("Erreur lors de la récupération du RuleName par son ID."+ e);
		}
	}
	
	public RuleName addRuleName(RuleName ruleName) {
		
		try {
			RuleName savedRuleName = ruleNameRepository.save(ruleName);
			
			return savedRuleName;
			
		} catch (Exception e){
			throw new RuntimeException("Erreur lors de l'ajout du RuleName."+ e);
		}
	}
	
	public void deleteRuleName(Integer id) {
		
		try {
			RuleName ruleNameToDelete = getRuleNameById(id);
			ruleNameRepository.delete(ruleNameToDelete);
		} catch (Exception e){
			throw new RuntimeException("Erreur lors de la suppression du RuleName."+ e);
		}
	}
	
	public RuleName updateRuleName(Integer id, RuleName ruleName) {
		try {
			RuleName ruleNameToUpdate = getRuleNameById(id);
			ruleNameToUpdate.setDescription(ruleName.getDescription());
			ruleNameToUpdate.setJson(ruleName.getJson());
			ruleNameToUpdate.setName(ruleName.getName());
			ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());
			ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
			ruleNameToUpdate.setTemplate(ruleName.getTemplate());
			
			return ruleNameRepository.save(ruleNameToUpdate);
		} catch (Exception e){
			throw new RuntimeException("Erreur lors de la modification du RuleName."+ e);
		}
	}
	
	
	
}
