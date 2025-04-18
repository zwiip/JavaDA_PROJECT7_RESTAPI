package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
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
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() throws Exception {
        userService.getUsers().forEach(testUser -> userService.deleteUser(testUser.getId()));

        user = new User("Anne", "1Avonlea!", "Anne Shirley", "USER");
        userService.saveNewUser(user);
    }

    @AfterEach
    public void tearDown() {
        userService.getUsers().forEach(testUser -> userService.deleteUser(testUser.getId()));
    }

    @Test
    @WithMockUser(username = "testUser",roles = "ADMIN")
    public void testHome() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("username"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddUser() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testValidate() throws Exception {
        mockMvc.perform(post("/user/validate").with(csrf())
                .param("username", "testUser")
                .param("password", user.getPassword())
                .param("fullname", user.getFullname())
                .param("role", user.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testShowUpdateForm() throws Exception {
        mockMvc.perform(get("/user/update/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateUser() throws Exception {
        mockMvc.perform(post("/user/update/" + user.getId()).with(csrf())
                .param("username", "UpdatedTestUser")
                .param("password", user.getPassword())
                .param("fullname", user.getFullname())
                .param("role", user.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
        assertEquals("UpdatedTestUser", userService.getUser(user.getId()).get().getUsername());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        User secondUser = new User("Diana", "1Avonlea!", "Diana Barry", "USER");
        userService.saveNewUser(secondUser);
        assertEquals(2, userService.getUsers().size());

        mockMvc.perform(get("/user/delete/" + user.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
        assertEquals(1, userService.getUsers().size());
    }
}