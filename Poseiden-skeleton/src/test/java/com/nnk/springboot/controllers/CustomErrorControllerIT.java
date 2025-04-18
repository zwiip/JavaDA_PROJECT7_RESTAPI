package com.nnk.springboot.controllers;

import jakarta.servlet.RequestDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomErrorControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    public void testError404() throws Exception {
        mockMvc.perform(get("/not/real/page"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testError403() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testHandleError_404() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorName", "Error 404: Page not found"))
                .andExpect(model().attribute("errorMsg", "The page you tried to reach doesn't exist."))
                .andExpect(model().attribute("username", "testUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testHandleError_403() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 403))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attribute("errorName", "Error 403: Access Forbidden"))
                .andExpect(model().attribute("errorMsg", "You are not allowed."))
                .andExpect(model().attribute("username", "testUser"));
    }
}
