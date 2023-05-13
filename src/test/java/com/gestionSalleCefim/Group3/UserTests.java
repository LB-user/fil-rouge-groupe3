package com.gestionSalleCefim.Group3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionSalleCefim.Group3.entities.Reservation;
import com.gestionSalleCefim.Group3.entities.Role;
import com.gestionSalleCefim.Group3.entities.User;
import com.gestionSalleCefim.Group3.repositories.RoleRepository;
import com.gestionSalleCefim.Group3.repositories.UserRepository;
import com.gestionSalleCefim.Group3.services.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserTests {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostUser() throws Exception {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        User newUser = objectMapper.readValue(contentAsString, User.class);
        Assertions.assertNotNull(newUser.getId());
    }

    @Test
    void testPostUserIdExists() throws Exception {

        User user = new User();
        user.setId(1);
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        User newUser = objectMapper.readValue(contentAsString, User.class);
        assert !Objects.equals(newUser.getLastName(), user.getLastName());
    }

    @Test
    void testPrintAllUsersLastName(){
        List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user.getLastName()));
    }

    @Test
    void testFindUserById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/user/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dupont"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Laurent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("laurent.dupont@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("mypassword"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     *
     * @throws Exception
     *
     * erreur attendue : java.lang.AssertionError: JSON path "$.lastName" expected:<Dupond> but was:<Dupont>
     */
    @Test
    void testFindUserByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/user/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dupond"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Laurent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("laurent.dupont@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("mypassword"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllUsersByAPI() {
        MockMvcRequestBuilders.get("/api/user/all");

        MockMvcResultMatchers.status().isOk();
    }

    @Test
    public void testUpdateUser() throws Exception
    {
        User user = new User();
        user.setId(1);
        user.setLastName("Valentin");
        user.setFirstName("Amédée");
        user.setEmail("email@mail.com");
        user.setPassword("password");
        RequestBuilder request = MockMvcRequestBuilders.put("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Valentin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Amédée"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password"))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteUser() throws Exception {
        User user = userRepository.findById(1).orElse(null);
        Assertions.assertNotNull(user);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/user/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        User user1 = userRepository.findById(1).orElse(null);
        Assertions.assertNull(user1);
    }
}
