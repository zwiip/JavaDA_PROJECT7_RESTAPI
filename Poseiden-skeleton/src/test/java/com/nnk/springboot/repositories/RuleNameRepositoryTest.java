package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RuleNameRepositoryTest {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	private RuleName rule;

	@BeforeEach
	public void setup() {
		rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
	}

	@Test
	public void saveRuleNameTest() {
		rule = ruleNameRepository.save(rule);
		assertNotNull(rule.getId());
		assertEquals("Rule Name", rule.getName());
	}

	@Test
	public void updateRuleNameTest() {
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
		assertEquals("Rule Name Update", rule.getName());
	}

	@Test
	public void findAllRuleNameTest() {
		rule = ruleNameRepository.save(rule);
		List<RuleName> listResult = ruleNameRepository.findAll();
		assertEquals(1, listResult.size());
	}

	@Test
	public void deleteRuleNameTest() {
		rule = ruleNameRepository.save(rule);
		Integer id = rule.getId();
		ruleNameRepository.delete(rule);
		Optional<RuleName> ruleList = ruleNameRepository.findById(id);
		assertFalse(ruleList.isPresent());
	}
}
