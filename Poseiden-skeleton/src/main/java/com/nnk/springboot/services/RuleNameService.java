package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service layer for the RuleName Entities.
 * Provides methods for the CRUD operations (create, read, update, delete) on the RuleName repository.
 */
@Service
public class RuleNameService {

    /* VARIABLES */
    private final RuleNameRepository ruleNameRepository;
    private final Logger logger = Logger.getLogger(RuleNameService.class.getName());

    /* CONSTRUCTOR */
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    /* METHODS */
    /**
     * Retrieves a list of all RuleName entities.
     * @return a list of RuleName objects
     */
    public List<RuleName> getRuleNames() {
        logger.fine("Fetching all RuleNames.");
        List<RuleName> ruleNames = ruleNameRepository.findAll();
        logger.info("Found " + ruleNames.size() + "RuleNames.");
        return ruleNames;
    }

    /**
     * Retrieve a RuleName entity by its ID.
     * @param id the ID of the RuleName to retrieve.
     * @return an Optional containing the matching RuleName or an empty Optional object.
     */
    public Optional<RuleName> getRuleName(Integer id) {
        logger.fine("Fetching the RuleName for the id: " + id);
        Optional<RuleName> ruleName = ruleNameRepository.findById(id);
        if (ruleName.isPresent()) {
            logger.info("Found a ruleName: " + ruleName.get().getName());
        } else {
            logger.warning("No ruleName found for the id: " + id);
        }
        return ruleName;
    }

    /**
     * Saves a RuleName entity to the repository.
     * @param ruleName the entity to save.
     */
    public void saveRuleName(RuleName ruleName) {
        logger.fine("Saving the ruleName: " + ruleName.getName());
        ruleNameRepository.save(ruleName);
        logger.info("RuleName saved: " + ruleName.getName());
    }

    /**
     * Deletes a RuleName entity matching the given ID.
     * @param id the ID of the RuleName to delete.
     */
    public void deleteRuleName(Integer id) {
        logger.fine("Deleting the RuleName for the id: " + id);
        ruleNameRepository.deleteById(id);
        logger.info("RuleName deleted.");
    }
}
