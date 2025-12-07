package com.example.projektgit.controller;

import com.example.projektgit.repository.StatusRepository;
import com.example.projektgit.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void testCreateStatusAsAdmin() throws Exception {
        String statusJson = "{\"name\": \"TEST_STATUS\"}";

        mockMvc.perform(post("/api/admin/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statusJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = { "USER" })
    public void testCreateStatusAsUser_Forbidden() throws Exception {
        String statusJson = "{\"name\": \"TEST_STATUS_2\"}";

        mockMvc.perform(post("/api/admin/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statusJson))
                .andExpect(status().isForbidden());
    }
}
