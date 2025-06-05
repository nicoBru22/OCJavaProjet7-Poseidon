package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

public interface IRuleNameService {
	List<RuleName> getAllRuleName();
	RuleName getRuleNameById(Integer id);
	RuleName addRuleName(RuleName ruleName);
	void deleteRuleName(Integer id);
	RuleName updateRuleName(Integer id, RuleName ruleName);	
}
