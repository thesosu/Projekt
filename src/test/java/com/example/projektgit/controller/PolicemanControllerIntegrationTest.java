package com.example.projektgit.controller;

import com.example.projektgit.model.Paragraph;
import com.example.projektgit.model.Role;
import com.example.projektgit.model.User;
import com.example.projektgit.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PolicemanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FineRepository fineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ParagraphRepository paragraphRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StatusRepository statusRepository;

    private User testUser;
    private Paragraph testParagraph;

    @BeforeEach
    public void setup() {
        fineRepository.deleteAll();

        testUser = userRepository.findByUsername("user").orElse(null);
        if (testUser == null) {
            testUser = userRepository.save(new User(null, "user", "password", Role.USER));
        }

        testParagraph = paragraphRepository.findAll().stream().findFirst().orElse(null);
        if (testParagraph == null) {
            testParagraph = paragraphRepository.save(new Paragraph(null, "Art. 51", "Test paragraph"));
        }
    }

    @Test
    @WithMockUser(username = "policeman", roles = { "POLICEMAN" })
    public void testGetAllReportsAsPoliceman() throws Exception {
        mockMvc.perform(get("/api/policeman/reports"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "policeman", roles = { "POLICEMAN" })
    public void testCreateFineAsPoliceman() throws Exception {
        String fineJson = String.format(
                "{\"amount\": 500.00, \"recipient\": {\"id\": %d}, \"paragraph\": {\"id\": %d}}",
                testUser.getId(), testParagraph.getId());

        mockMvc.perform(post("/api/policeman/fines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fineJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "policeman", roles = { "POLICEMAN" })
    public void testGetAllFinesAsPoliceman() throws Exception {
        mockMvc.perform(get("/api/policeman/fines"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testGetReportsAsUser_Forbidden() throws Exception {
        mockMvc.perform(get("/api/policeman/reports"))
                .andExpect(status().isForbidden());
    }
}

