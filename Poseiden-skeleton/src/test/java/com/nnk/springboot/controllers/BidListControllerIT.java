package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
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
public class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListService bidListService;

    private BidList bidList;

    @BeforeEach
    public void setup() {
        bidListService.getBidLists().forEach(bid -> bidListService.deleteBidList(bid.getId()));

        bidList = new BidList("testAccount", "testType", 2.1);
        bidListService.saveBidList(bidList);
    }

    @AfterEach
    public void tearDown() {
        bidListService.getBidLists().forEach(bid -> bidListService.deleteBidList(bid.getId()));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testHome() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    @WithMockUser
    public void testAddBidForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        mockMvc.perform(post("/bidList/validate").with(csrf())
                        .param("account", "testAccount")
                        .param("type", "testType")
                        .param("bidQuantity", "10.2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        mockMvc.perform(get("/bidList/update/" + bidList.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bidList"));
    }

    @Test
    @WithMockUser
    public void testUpdateBid() throws Exception {
        mockMvc.perform(post("/bidList/update/" + bidList.getId()).with(csrf())
                        .param("account", "testAccountUpdated")
                        .param("type", "testTypeUpdated")
                        .param("bidQuantity", "10.2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
        assertEquals("testAccountUpdated", bidListService.getBidList(bidList.getId()).get().getAccount());
    }

    @Test
    @WithMockUser
    public void testDeleteBid() throws Exception {
        BidList secondBidList = new BidList("testAccount2", "testType2", 1.2);
        bidListService.saveBidList(secondBidList);
        assertEquals(2, bidListService.getBidLists().size());

        mockMvc.perform(get("/bidList/delete/" + bidList.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
        assertEquals(1, bidListService.getBidLists().size());
    }
}
