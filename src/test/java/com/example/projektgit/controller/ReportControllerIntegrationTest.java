package com.example.projektgit.controller;

import com.example.projektgit.model.Role;
import com.example.projektgit.model.Status;
import com.example.projektgit.model.User;
import com.example.projektgit.repository.StatusRepository;
import com.example.projektgit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    private User testUser;
    private Status testStatus;

    @BeforeEach
    public void setup() {
        testUser = userRepository.findByUsername("user").orElse(null);
        if (testUser == null) {
            testUser = userRepository.save(new User(null, "user", "password", Role.USER));
        }

        testStatus = statusRepository.findAll().stream().findFirst().orElse(null);
        if (testStatus == null) {
            testStatus = statusRepository.save(new Status(null, "NEW"));
        }
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testCreateReportAsUser() throws Exception {
        String reportJson = String.format(
                "{\"description\": \"Test donos\", \"reporter\": {\"id\": %d}, \"status\": {\"id\": %d}}",
                testUser.getId(), testStatus.getId());

        mockMvc.perform(post("/api/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reportJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateReportWithoutAuth_Unauthorized() throws Exception {
        String reportJson = "{\"description\": \"Test donos\"}";

        mockMvc.perform(post("/api/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reportJson))
                .andExpect(status().isUnauthorized());
    }
}
