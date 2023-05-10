package com.gestionSalleCefim.Group3;


import com.gestionSalleCefim.Group3.entities.Campus;
import com.gestionSalleCefim.Group3.entities.Reservation;
import com.gestionSalleCefim.Group3.services.CampusService;
import com.gestionSalleCefim.Group3.services.ReservationService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CampusTests {

    @Autowired
    private CampusService campusService;

    @Test
    void testGetAllCampus() throws Exception {
        List<Campus> campus = campusService.getAll();
        campus.forEach(c -> System.out.println(c.getName()));
    }

    @Test
    void testGetCampusById() throws Exception {
        Campus campus = campusService.getById(1);
        assertEquals("Paris",campus.getName());
    }
}


