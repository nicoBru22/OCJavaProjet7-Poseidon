package com.nnk.springboot.integration;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AuthentificationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll(); // Nettoyage pour éviter les doublons

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("password123"));
        admin.setRole("ADMIN");
        admin.setFullname("Admin Test");
        
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("password123"));
        user.setRole("USER");
        user.setFullname("User Test");
        
        User userUnknow = new User();
        userUnknow.setUsername("userUnknow");
        userUnknow.setPassword(passwordEncoder.encode("password123"));
        userUnknow.setRole("ROLETEST");
        userUnknow.setFullname("UserUnknow Test");
        
        userRepository.save(userUnknow);
        userRepository.save(admin);        
        userRepository.save(user);
    }

    @Test
    public void authentificationAdminTestOk() throws Exception {
        mockMvc.perform(post("/app/login")
                .param("username", "admin")
                .param("password", "password123"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/home"));
    }
    
    @Test
    public void authentificationUserTestOk() throws Exception {
        mockMvc.perform(post("/app/login")
                .param("username", "user")
                .param("password", "password123"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/user/home"));
    }
    
    @Test
    public void authentificationUserUnknowTest() throws Exception {
        mockMvc.perform(post("/app/login")
                .param("username", "userUnknow")
                .param("password", "password123"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/403"));
    }

    @Test
    public void testAuthenticationWithInvalidCredentials_shouldRedirectToLoginError() throws Exception {
        mockMvc.perform(post("/app/login")
                .param("username", "admin")
                .param("password", "wrongpassword"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/app/login?error"));
    }
}

