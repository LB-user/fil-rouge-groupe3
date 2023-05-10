package com.gestionSalleCefim.Group3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionSalleCefim.Group3.entities.Room;
import com.gestionSalleCefim.Group3.repositories.RoomRepository;
import com.gestionSalleCefim.Group3.services.RoomService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RoomTests {
    @Autowired
    private RoomRepository RoomRepository;

    @Autowired
    private RoomService RoomService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPrintAllRoomsName(){
        List<Room> books = RoomRepository.findAll();
        books.forEach(Room -> System.out.println(Room.getName()));
    }

    @Test
    void testGetAllRoomsByAPI() throws Exception {
        // Création de notre requête au moyen de la classe MockMvcRequestBuilders
        // Utilisation de la méthode correspondant au verbe HTTP voulu, qui prend en paramètre l'URL du point d'API
        RequestBuilder request = MockMvcRequestBuilders.get("/api/room/all");
        // Test du status de la réponse, ici 200 (isOk())
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        // Désérialisation du contenu de la réponse en List<Book>
        List<Room> rooms = Arrays.asList(objectMapper.readValue(contentAsString, Room[].class));

        Room roomWithId9 = RoomService.getById(9);
        boolean roomWithId9AndSameNameExists = rooms.stream()
                .filter(room -> room.getId() == 9)
                .anyMatch(room -> room.getName().equals(roomWithId9.getName()));

        Assertions.assertTrue(roomWithId9AndSameNameExists);
    }
}