package com.gestionSalleCefim.Group3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionSalleCefim.Group3.entities.Building;
import com.gestionSalleCefim.Group3.entities.Campus;
import com.gestionSalleCefim.Group3.repositories.BuildingRepository;
import com.gestionSalleCefim.Group3.services.BuildingService;
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
public class BuildingTests {
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingService buildingService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostBuilding() throws Exception {
        Campus campus = new Campus();
        campus.setId(1);

        Building building = new Building();
        building.setName("building test");
        building.setAddress("address test");
        building.setCity("city test");
        building.setPostalCode("pc test");
        building.setCampus(campus);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/building")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(building));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Building newBuilding = objectMapper.readValue(contentAsString, Building.class);
        Assertions.assertNotNull(newBuilding.getId());
    }

    @Test
    void testPostBuildingIdExists() throws Exception {
        Building building = new Building();
        building.setId(1);
        building.setName("building test");
        building.setAddress("address test");
        building.setCity("city test");
        building.setPostalCode("pc test");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/building")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(building));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Building newBuilding = objectMapper.readValue(contentAsString, Building.class);
        assert !Objects.equals(newBuilding.getName(), building.getName());
    }

    @Test
    void testPrintAllBuildingsLastName(){
        List<Building> buildings = buildingRepository.findAll();
        buildings.forEach(building -> System.out.println(building.getName()));
    }

    @Test
    void testFindBuildingById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/building/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bâtiment 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1 Rue des bâtiments"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("75000"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     *
     * @throws Exception
     *
     * erreur attendue : java.lang.AssertionError: JSON path "$.name" expected:<error> but was:<Bâtiment 1>
     */
    @Test
    void testFindBuildingByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/building/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("error"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("1 Rue des bâtiments"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Paris"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("75000"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllBuildingsByAPI() {
        MockMvcRequestBuilders.get("/api/building/all");

        MockMvcResultMatchers.status().isOk();
    }

    @Test
    public void testUpdateBuilding() throws Exception
    {
        Building building = new Building();
        building.setId(1);
        building.setName("building test update");
        building.setAddress("address test");
        building.setCity("city test");
        building.setPostalCode("pc test");

        RequestBuilder request = MockMvcRequestBuilders.put("/api/building")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(building));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("building test update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("address test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("city test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("pc test"))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteBuilding() throws Exception {
        Building building = buildingRepository.findById(1).orElse(null);
        Assertions.assertNotNull(building);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/building/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Building building1 = buildingRepository.findById(1).orElse(null);
        Assertions.assertNull(building1);
    }
}
