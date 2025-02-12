package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class RuleNameServiceTest {
    private RuleNameService ruleNameService;
    private RuleName ruleName;

    @Mock
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setup() {
        ruleNameRepository = mock(RuleNameRepository.class);
        ruleNameService = new RuleNameService(ruleNameRepository);

        ruleName = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
    }

    @Test
    public void givenTwoRuleNames_whenGetRuleNames_thenReturnTheListWithTwoRulenames() {
        RuleName secondRulaName = new RuleName("Second Rule Name", "Second Description", "Second Json", "Second Template", "Second SQL", "Second SQL Part");
        List<RuleName> listOfTwoRuleNames = new ArrayList<>();
        listOfTwoRuleNames.add(ruleName);
        listOfTwoRuleNames.add(secondRulaName);
        when(ruleNameRepository.findAll()).thenReturn(listOfTwoRuleNames);

        List<RuleName> actualRuleNames = ruleNameService.getRuleNames();

        assertEquals(2, actualRuleNames.size());
        assertEquals("Rule Name", actualRuleNames.getFirst().getName());
        assertEquals("Second Description", actualRuleNames.getLast().getDescription());
    }

    @Test
    public void givenCorrectId_whenGetRuleName_thenReturnMatchingRuleName() {
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        Optional<RuleName> actualRuleName = ruleNameService.getRuleName(1);

        assertTrue(actualRuleName.isPresent());
        assertEquals("Rule Name", actualRuleName.get().getName());
    }

    @Test
    public void givenCorrectRuleName_whenSaveRuleName_thenRuleNameIsSaved() {
        ruleNameService.saveRuleName(ruleName);

        verify(ruleNameRepository).save(ruleName);
    }

    @Test
    public void givenCorrectId_whenDeleteRuleName_thenRuleNameIsDeleted() {
        ruleNameService.deleteRuleName(1);

        verify(ruleNameRepository).deleteById(1);
    }
}
