package com.gestionSalleCefim.Group3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionSalleCefim.Group3.entities.Role;
import com.gestionSalleCefim.Group3.repositories.RoleRepository;
import com.gestionSalleCefim.Group3.services.RoleService;
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

import java.util.List;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RoleTests {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostRole() throws Exception {
        Role role = new Role();
        role.setName("role test");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Role newRole = objectMapper.readValue(contentAsString, Role.class);
        Assertions.assertNotNull(newRole.getId());
    }

    @Test
    void testPostRoleIdExists() throws Exception {

        Role role = new Role();
        role.setId(1);
        role.setName("role test");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Role newRole = objectMapper.readValue(contentAsString, Role.class);
        assert !Objects.equals(newRole.getName(), role.getName());
    }

    @Test
    void testPrintAllRolesLastName(){
        List<Role> roles = roleRepository.findAll();
        roles.forEach(role -> System.out.println(role.getName()));
    }

    @Test
    void testFindRoleById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/role/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Administrateur"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     *
     * @throws Exception
     *
     * erreur attendue : java.lang.AssertionError: JSON path "$.name" expected:<Utilisateur> but was:<Administrateur>
     */
    @Test
    void testFindRoleByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/role/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Utilisateur"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllRolesByAPI() {
        MockMvcRequestBuilders.get("/api/role/all");

        MockMvcResultMatchers.status().isOk();
    }

    @Test
    public void testUpdateRole() throws Exception
    {
        Role role = new Role();
        role.setId(1);
        role.setName("role test update");
        RequestBuilder request = MockMvcRequestBuilders.put("/api/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("role test update"))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteRole() throws Exception {
        Role role = roleRepository.findById(1).orElse(null);
        Assertions.assertNotNull(role);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/role/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Role role1 = roleRepository.findById(1).orElse(null);
        Assertions.assertNull(role1);
    }
}
