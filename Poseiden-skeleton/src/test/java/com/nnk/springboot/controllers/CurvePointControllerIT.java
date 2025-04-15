package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointService;
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
public class CurvePointControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CurvePointService curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    public void setup() {
        curvePointService.getCurvePoints().forEach(curve -> curvePointService.deleteCurvePoint(curve.getId()));

        curvePoint = new CurvePoint(1, 1.1, 2.2);
        curvePointService.saveCurvePoint(curvePoint);
    }

    @AfterEach
    public void tearDown() {
        curvePointService.getCurvePoints().forEach(curve -> curvePointService.deleteCurvePoint(curve.getId()));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testHome() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"))
                .andExpect(model().attributeExists("username"));
    }

    @Test
    @WithMockUser
    public void testAddCurvePointForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser
    public void testValide() throws Exception {
        mockMvc.perform(post("/curvePoint/validate").with(csrf())
                .param("curveId", "1")
                .param("term", "1.1")
                .param("curveValue", "1.1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser
    public void testShowUpdateForm() throws Exception {
        mockMvc.perform(get("/curvePoint/update/" + curvePoint.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("curvePoint"));
    }

    @Test
    @WithMockUser
    public void testUpdateCurvePoint() throws Exception {
        mockMvc.perform(post("/curvePoint/update/" + curvePoint.getId()).with(csrf())
                .param("curveId", "1")
                .param("term", "1.1")
                .param("curveValue", "1.1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
        assertEquals(1, curvePointService.getCurvePoint(curvePoint.getId()).get().getCurveId());
    }

    @Test
    @WithMockUser
    public void testDeleteCurvePoint() throws Exception {
        CurvePoint secondCurvePoint = new CurvePoint(2, 2.2, 2.2);
        curvePointService.saveCurvePoint(secondCurvePoint);
        assertEquals(2, curvePointService.getCurvePoints().size());

        mockMvc.perform(get("/curvePoint/delete/" + curvePoint.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
        assertEquals(1, curvePointService.getCurvePoints().size());
    }
}
