package com.nnk.springboot.unitaire.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;

import Exception.UserExistingException;

@SpringBootTest
@AutoConfigureMockMvc
public class Controller_user_test {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_get_page_userList() throws Exception {
        mockMvc.perform(get("/user/list"))
               .andExpect(status().isOk())
               .andExpect(view().name("user/list"))
               .andExpect(model().attributeExists("users"));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_get_page_add() throws Exception {
        mockMvc.perform(get("/user/add"))
               .andExpect(status().isOk())
               .andExpect(view().name("user/add"));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_get_page_update() throws Exception {
        User mockUser = new User();
        mockUser.setId(((long)1)); //cast long car id est en long et non en int. Sinon 1L pour faire un long littéral.
        mockUser.setFullname("John Doe");
        mockUser.setUsername("John");
        mockUser.setRole("USER");
        mockUser.setPassword("JohnDoe@1!");

        when(userService.getUserById(1)).thenReturn(mockUser); //la méthode attend un int et pas un long

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", mockUser));
        
        verify(userService, times(1)).getUserById(1);
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_delete() throws Exception {
    	int userId = 1;

        mockMvc.perform(get("/user/delete/{id}", userId))
	        .andExpect(status().is3xxRedirection())
	        .andExpect(redirectedUrl("/user/list"));

        verify(userService, times(1)).deleteUser(userId);
        
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_addUser() throws Exception {
        mockMvc.perform(post("/user/validate")
                .param("fullname", "John Doe")
                .param("username", "John")
                .param("role", "USER")
                .param("password", "JohnDoe@1!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_updateUser() throws Exception {
        mockMvc.perform(post("/user/update/1")
                .param("fullname", "John Doe")
                .param("username", "John")
                .param("role", "USER")
                .param("password", "JohnDoe@1!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_updateUser_validationError() throws Exception {
        User mockUser = new User();
        mockUser.setFullname("");
        mockUser.setUsername("John");
        mockUser.setRole("USER");
        mockUser.setPassword("JohnDoe@1!");

        mockMvc.perform(post("/user/update/{id}", 1)
                .flashAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeHasErrors("user"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_updateUser_userExistingException() throws Exception {
        User mockUser = new User();
        mockUser.setId((long) 1);
        mockUser.setFullname("John Doe");
        mockUser.setUsername("John");
        mockUser.setRole("USER");
        mockUser.setPassword("JohnDoe@1!");

        when(userService.updateUser(1, mockUser))
                .thenThrow(new UserExistingException("User already exists"));

        mockMvc.perform(post("/user/update/{id}", 1)
                .flashAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attribute("errorMessage", "User already exists"))
                .andExpect(model().attribute("user", mockUser));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_addUser_validationError() throws Exception {
        User mockUser = new User();
        mockUser.setFullname("");
        mockUser.setUsername("John");
        mockUser.setRole("USER");
        mockUser.setPassword("JohnDoe@1!");

        mockMvc.perform(post("/user/validate")
                .flashAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attributeHasErrors("user"));
    }
    
    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void test_addUser_userExistingException() throws Exception {
        User mockUser = new User();
        mockUser.setFullname("John Doe");
        mockUser.setUsername("John");
        mockUser.setRole("USER");
        mockUser.setPassword("JohnDoe@1!");

        when(userService.addUser(mockUser))
                .thenThrow(new UserExistingException("User already exists"));

        mockMvc.perform(post("/user/validate")
                .flashAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"))
                .andExpect(model().attribute("errorMessage", "User already exists"))
                .andExpect(model().attribute("user", mockUser));
    }

    @Test
    @WithMockUser(username = "nicolas", roles = "USER")
    public void test_get_userList_forbidden() throws Exception {
        mockMvc.perform(get("/user/list"))
               .andExpect(status().isForbidden())
               .andExpect(forwardedUrl("/403"));
    }
    
}

