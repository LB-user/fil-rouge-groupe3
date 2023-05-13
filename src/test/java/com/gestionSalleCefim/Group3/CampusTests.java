package com.gestionSalleCefim.Group3;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionSalleCefim.Group3.entities.Campus;
import com.gestionSalleCefim.Group3.repositories.CampusRepository;
import com.gestionSalleCefim.Group3.services.CampusService;
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
public class CampusTests {
    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private CampusService campusService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostCampus() throws Exception {
        Campus campus = new Campus();
        campus.setName("campus test");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/campus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campus));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Campus newCampus = objectMapper.readValue(contentAsString, Campus.class);
        Assertions.assertNotNull(newCampus.getId());
    }

    @Test
    void testPostCampusIdExists() throws Exception {
        Campus campus = new Campus();
        campus.setId(1);
        campus.setName("campus test");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/campus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campus));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Campus newCampus = objectMapper.readValue(contentAsString, Campus.class);
        assert !Objects.equals(newCampus.getName(), campus.getName());
    }

    @Test
    void testPrintAllCampussLastName(){
        List<Campus> campuss = campusRepository.findAll();
        campuss.forEach(campus -> System.out.println(campus.getName()));
    }

    @Test
    void testFindCampusById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/campus/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Paris"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     *
     * @throws Exception
     *
     * erreur attendue : java.lang.AssertionError: JSON path "$.name" expected:<Campus test id error> but was:<Paris>
     */
    @Test
    void testFindCampusByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/campus/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Campus test id error"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllCampussByAPI() {
        MockMvcRequestBuilders.get("/api/campus/all");

        MockMvcResultMatchers.status().isOk();
    }

    @Test
    public void testUpdateCampus() throws Exception
    {
        Campus campus = new Campus();
        campus.setId(1);
        campus.setName("campus test update");

        RequestBuilder request = MockMvcRequestBuilders.put("/api/campus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(campus));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("campus test update"))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteCampus() throws Exception {
        Campus campus = campusRepository.findById(1).orElse(null);
        Assertions.assertNotNull(campus);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/campus/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Campus campus1 = campusRepository.findById(1).orElse(null);
        Assertions.assertNull(campus1);
    }
}


