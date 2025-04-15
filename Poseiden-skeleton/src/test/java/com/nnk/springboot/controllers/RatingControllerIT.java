package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
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
public class RatingControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RatingService ratingService;

    private Rating rating;

    @BeforeEach
    public void setup() {
        ratingService.getRatings().forEach(rate -> ratingService.deleteRating(rate.getId()));

        rating = new Rating("moodys", "sand", "fitch", 1);
        ratingService.saveRating(rating);
    }

    @AfterEach
    public void tearDown() {
        ratingService.getRatings().forEach(rate -> ratingService.deleteRating(rate.getId()));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testHome() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    @WithMockUser
    public void testAddRatingForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser
    public void testValidate() throws Exception {
        mockMvc.perform(post("/rating/validate").with(csrf())
                .param("moodysRating", "moodys")
                .param("sandPRating", "sandP")
                .param("fitchRating", "fitch")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        mockMvc.perform(get("/rating/update/" + rating.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rating"));
    }

    @Test
    @WithMockUser
    public void testUpdateRating() throws Exception {
        mockMvc.perform(post("/rating/update/" + rating.getId()).with(csrf())
                .param("moodysRating", "moodysUpdated")
                .param("sandPRating", "sandP")
                .param("fitchRating", "fitch")
                .param("orderNumber", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
        assertEquals("moodysUpdated", ratingService.getRating(rating.getId()).get().getMoodysRating());
    }

    @Test
    @WithMockUser
    public void testDeleteRating() throws Exception {
        Rating secondRating = new Rating("secondMoodys", "secondSandP", "secondFitch", 2);
        ratingService.saveRating(secondRating);
        assertEquals(2, ratingService.getRatings().size());

        mockMvc.perform(get("/rating/delete/" + rating.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
        assertEquals(1, ratingService.getRatings().size());
    }
}
