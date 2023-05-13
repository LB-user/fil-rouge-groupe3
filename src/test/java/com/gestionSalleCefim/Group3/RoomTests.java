package com.gestionSalleCefim.Group3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionSalleCefim.Group3.entities.Building;
import com.gestionSalleCefim.Group3.entities.Room;
import com.gestionSalleCefim.Group3.repositories.RoomRepository;
import com.gestionSalleCefim.Group3.services.RoomService;
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
public class RoomTests {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostRoom() throws Exception {
        Room room = new Room();
        room.setName("Salle Test");
        room.setCapacity(11);
        room.setLocation("Lieu Test");
        room.setEquipment("Equipements fictifs");
        Building building = new Building();
        building.setId(1);
        room.setBuilding(building);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Room newRoom = objectMapper.readValue(contentAsString, Room.class);
        Assertions.assertNotNull(newRoom.getId());
    }

    @Test
    void testPostRoomIdExists() throws Exception {

        Room room = new Room();
        room.setId(1);
        room.setName("Salle Test");
        room.setCapacity(11);
        room.setLocation("Lieu Test");
        room.setEquipment("Equipements fictifs");

        RequestBuilder request = MockMvcRequestBuilders.post("/api/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Room newRoom = objectMapper.readValue(contentAsString, Room.class);
        assert !Objects.equals(newRoom.getName(), room.getName());
    }

    @Test
    void testPrintAllRoomsName(){
        List<Room> rooms = roomRepository.findAll();
        rooms.forEach(room -> System.out.println(room.getName()));
    }

    @Test
    void testFindRoomById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/room/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Salle 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(25))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Bâtiment A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment").value("Table et chaise"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     *
     * @throws Exception
     *
     * erreur attendue : java.lang.AssertionError: JSON path "$.capacity" expected:<24> but was:<25>
     */
    @Test
    void testFindRoomByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/room/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Salle 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(24))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Bâtiment A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment").value("Table et chaise"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllRoomsByAPI() {
        MockMvcRequestBuilders.get("/api/room/all");

        MockMvcResultMatchers.status().isOk();
    }

    @Test
    public void testUpdateRoom() throws Exception
    {
        Room room = new Room();
        room.setId(1);
        room.setName("Salle Test");
        room.setCapacity(11);
        room.setLocation("Lieu Test");
        room.setEquipment("Equipements fictifs");
        RequestBuilder request = MockMvcRequestBuilders.put("/api/room")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(room));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Salle Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Lieu Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment").value("Equipements fictifs"))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteRoom() throws Exception {
        Room room = roomRepository.findById(1).orElse(null);
        Assertions.assertNotNull(room);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/room/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Room room1 = roomRepository.findById(1).orElse(null);
        Assertions.assertNull(room1);
    }
}
