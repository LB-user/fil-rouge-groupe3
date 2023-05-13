package com.gestionSalleCefim.Group3;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.gestionSalleCefim.Group3.entities.Course;
import com.gestionSalleCefim.Group3.entities.Reservation;
import com.gestionSalleCefim.Group3.entities.Room;
import com.gestionSalleCefim.Group3.entities.User;
import com.gestionSalleCefim.Group3.repositories.ReservationRepository;
import com.gestionSalleCefim.Group3.services.ReservationService;
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


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReservationTests {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPostReservation() throws Exception {
        LocalDate startDate = LocalDate.parse("2024-01-08");
        LocalDate endDate = LocalDate.parse("2024-01-08");
        LocalTime startTime = LocalTime.parse("09:00:00");
        LocalTime endTime = LocalTime.parse("12:00:00");

        Course course = new Course();
        course.setId(1);
        User user = new User();
        user.setId(2);
        Room room = new Room();
        room.setId(3);

        Reservation reservation = new Reservation();
        reservation.setTitle("titre test create");
        reservation.setDescription("description test create");
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStartHour(startTime);
        reservation.setEndHour(endTime);
        reservation.setCourse(course);
        reservation.setUser(user);
        reservation.setRoom(room);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Reservation newReservation = objectMapper.readValue(contentAsString, Reservation.class);
        Assertions.assertNotNull(newReservation.getId());
    }

    @Test
    void testPostReservationIdExists() throws Exception {
        LocalDate startDate = LocalDate.parse("2024-01-08");
        LocalDate endDate = LocalDate.parse("2024-01-08");
        LocalTime startTime = LocalTime.parse("09:00:00");
        LocalTime endTime = LocalTime.parse("12:00:00");

        Course course = new Course();
        course.setId(1);
        User user = new User();
        user.setId(2);
        Room room = new Room();
        room.setId(3);

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setTitle("titre test update");
        reservation.setDescription("description test update");
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStartHour(startTime);
        reservation.setEndHour(endTime);
        reservation.setCourse(course);
        reservation.setUser(user);
        reservation.setRoom(room);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Reservation newReservation = objectMapper.readValue(contentAsString, Reservation.class);
        assert !Objects.equals(newReservation.getTitle(), reservation.getTitle());
    }

    @Test
    void testPrintAllReservationsLastName(){
        List<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservation -> System.out.println(reservation.getTitle()));
    }

    @Test
    void testFindReservationById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/reservation/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Réunion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Réunion hebdomadaire des employés"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-09-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-09-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startHour").value("09:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endHour").value("17:00:00"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     *
     * @throws Exception
     *
     * erreur attendue : java.lang.AssertionError: JSON path "$.endHour" expected:<18:00:00> but was:<17:00:00>
     */
    @Test
    void testFindReservationByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/reservation/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Réunion"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Réunion hebdomadaire des employés"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-09-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-09-30"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startHour").value("09:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endHour").value("18:00:00"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllReservationsByAPI() {
        MockMvcRequestBuilders.get("/api/reservation/all");

        MockMvcResultMatchers.status().isOk();
    }

    @Test
    public void testUpdateReservation() throws Exception {
        Course course = new Course();
        course.setId(1);
        User user = new User();
        user.setId(2);
        Room room = new Room();
        room.setId(3);

        LocalDate startDate = LocalDate.parse("2020-01-08");
        LocalDate endDate = LocalDate.parse("2020-01-08");
        LocalTime startTime = LocalTime.parse("09:00:00");
        LocalTime endTime = LocalTime.parse("12:00:00");

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setTitle("titre test update");
        reservation.setDescription("description test update");
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStartHour(startTime);
        reservation.setEndHour(endTime);
        reservation.setCourse(course);
        reservation.setUser(user);
        reservation.setRoom(room);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("titre test update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("description test update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-01-08"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-01-08"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startHour").value("09:00:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endHour").value("12:00:00"))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteReservation() throws Exception {
        Reservation reservation = reservationRepository.findById(1).orElse(null);
        Assertions.assertNotNull(reservation);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/reservation/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Reservation reservation1 = reservationRepository.findById(1).orElse(null);
        Assertions.assertNull(reservation1);
    }
}


