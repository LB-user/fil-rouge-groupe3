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
    void testPrintAllUsersLastName(){
        List<User> users = userRepository.findAll();
        users.forEach(user -> System.out.println(user.getLastName()));
    }

    @Test
    void testGetAllUsersByAPI() throws Exception {
        // Création de notre requête au moyen de la classe MockMvcRequestBuilders
        // Utilisation de la méthode correspondant au verbe HTTP voulu, qui prend en paramètre l'URL du point d'API
        RequestBuilder request = MockMvcRequestBuilders.get("/api/user/all");
        // Test du status de la réponse, ici 200 (isOk())
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        // Désérialisation du contenu de la réponse en List<Book>
        List<User> users = Arrays.asList(objectMapper.readValue(contentAsString, User[].class));

        Assertions.assertTrue(users.contains(userService.getById(1)));
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

    @Test
    void testFindUserByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/user/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dupond"))
                //erreur attendue : java.lang.AssertionError: JSON path "$.lastName" expected:<Dupond> but was:<Dupont>
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Laurent"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("laurent.dupont@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("mypassword"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testPostUser() throws Exception {
        User user = new User(null,"Dupont", "Laurent", "laurent.dupond@mail.com", "mypassword", new Role());
        RequestBuilder request = MockMvcRequestBuilders.post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        User newUser = objectMapper.readValue(contentAsString, User.class);
        assert Objects.equals(newUser.getEmail(), user.getEmail());
    }

    @Test
    void testPostUserIdExists() throws Exception {

        User user = new User(1,"Dupond", "Laurent", "laurent.dupont@mail.com", "mypassword", new Role());
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
    void testDeleteUser() throws Exception {
        //User user = userRepository.findById(1).orElse(null);
        //Assertions.assertNotNull(user);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/user/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        //User user1 = userRepository.findById(1).orElse(null);
        //Assertions.assertNull(user1);
    }

    @Test
    public void testUpdateUser() throws Exception
    {
        List<Reservation> reservations = new ArrayList<>();
        User user = new User(1, "Valentin", "Amédée", "email@mail.com", "password", new Role(), reservations);
        RequestBuilder request = MockMvcRequestBuilders.put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Valentin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Amédée"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("password"))
                .andReturn().getResponse().getContentAsString();

    }
}
