package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameService {

    /* VARIABLES */
    private final RuleNameRepository ruleNameRepository;

    /* CONSTRUCTOR */
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /* METHODS */
    public List<RuleName> getRuleNames() { return ruleNameRepository.findAll(); }

    public Optional<RuleName> getRuleName(Integer id) {
        return ruleNameRepository.findById(id);
    }

    public void saveRuleName(RuleName ruleName) {
        ruleNameRepository.save(ruleName);
    }

    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
