package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
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
public class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeService tradeService;

    private Trade trade;

    @BeforeEach
    public void setup() {
        tradeService.getTrades().forEach(tradeUnit -> tradeService.deleteTrade(tradeUnit.getId()));

        trade = new Trade("testAccount", "testType");
        tradeService.saveTrade(trade);
    }

    @AfterEach
    public void tearDown() {
        tradeService.getTrades().forEach(tradeUnit -> tradeService.deleteTrade(tradeUnit.getId()));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testHome() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("trades"));
    }

    @Test
    @WithMockUser
    public void testAddTrade() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        mockMvc.perform(post("/trade/validate").with(csrf())
                .param("account", trade.getAccount())
                .param("type", trade.getType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        mockMvc.perform(get("/trade/update/" + trade.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("trade"));
    }

    @Test
    @WithMockUser
    public void testUpdateTrade() throws Exception {
        mockMvc.perform(post("/trade/update/" + trade.getId()).with(csrf())
                .param("account", "UpdatedTestAccount")
                .param("type", trade.getType()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
        assertEquals("UpdatedTestAccount", tradeService.getTrade(trade.getId()).get().getAccount());
    }

    @Test
    @WithMockUser
    public void testDeleteTrade() throws Exception {
        Trade secondTrade = new Trade("secondTrade", "type");
        tradeService.saveTrade(secondTrade);
        assertEquals(2, tradeService.getTrades().size());

        mockMvc.perform(get("/trade/delete/" + trade.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
        assertEquals(1, tradeService.getTrades().size());
    }
}
