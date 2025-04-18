package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RuleNameControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    RuleNameService ruleNameService;

    private RuleName ruleName;

    @BeforeEach
    public void setup() {
        ruleNameService.getRuleNames().forEach(rule -> ruleNameService.deleteRuleName(rule.getId()));

        ruleName = new RuleName("testRuleName", "ruleDescription", "ruleJson", "ruleDescription", "ruleSql", "ruleSqlPart");
        ruleNameService.saveRuleName(ruleName);
    }

    @AfterEach
    public void tearDown() {
        ruleNameService.getRuleNames().forEach(rule -> ruleNameService.deleteRuleName(rule.getId()));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testHome() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/list"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("ruleNames"));
    }

    @Test
    @WithMockUser
    public void testAddRuleForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        mockMvc.perform(post("/ruleName/validate").with(csrf())
                .param("name", ruleName.getName())
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("template", ruleName.getTemplate())
                .param("sql", ruleName.getSql())
                .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        mockMvc.perform(get("/ruleName/update/" + ruleName.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ruleName"))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser
    public void testUpdateRuleName() throws Exception {
        mockMvc.perform(post("/ruleName/update/" + ruleName.getId()).with(csrf())
                .param("name", "testRuleNameUpdated")
                .param("description", ruleName.getDescription())
                .param("json", ruleName.getJson())
                .param("template", ruleName.getTemplate())
                .param("sql", ruleName.getSql())
                .param("sqlPart", ruleName.getSqlPart()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
        assertEquals("testRuleNameUpdated", ruleNameService.getRuleName(ruleName.getId()).get().getName());
    }

    @Test
    @WithMockUser
    public void testDeleteRuleName() throws Exception {
        RuleName secondRuleName = new RuleName("secondRuleName",  "ruleDescription", "ruleJson", "ruleDescription", "ruleSql", "ruleSqlPart");
        ruleNameService.saveRuleName(secondRuleName);
        assertEquals(2, ruleNameService.getRuleNames().size());

        mockMvc.perform(get("/ruleName/delete/" + ruleName.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ruleName/list"));
        assertEquals(1, ruleNameService.getRuleNames().size());
    }
}
