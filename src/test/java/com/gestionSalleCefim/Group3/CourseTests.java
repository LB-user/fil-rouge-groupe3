package com.gestionSalleCefim.Group3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestionSalleCefim.Group3.entities.Course;
import com.gestionSalleCefim.Group3.repositories.CourseRepository;
import com.gestionSalleCefim.Group3.services.CourseService;
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
public class CourseTests {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseService courseService;

    // Classe pour simuler des appels REST
    @Autowired
    private MockMvc mockMvc;

    // Classe de sérialisation / désérialisation
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPrintAllCoursesLastName(){
        List<Course> books = courseRepository.findAll();
        books.forEach(Course -> System.out.println(Course.getName()));
    }

    @Test
    void testGetAllCoursesByAPI() throws Exception {
        // Création de notre requête au moyen de la classe MockMvcRequestBuilders
        // Utilisation de la méthode correspondant au verbe HTTP voulu, qui prend en paramètre l'URL du point d'API
        RequestBuilder request = MockMvcRequestBuilders.get("/api/course/all");
        // Test du status de la réponse, ici 200 (isOk())
        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        // Désérialisation du contenu de la réponse en List<Book>
        List<Course> courses = Arrays.asList(objectMapper.readValue(contentAsString, Course[].class));

        Course courseWithId1 = courseService.getById(1);
        boolean courseWithId9AndSameNameExists = courses.stream()
                .filter(course -> course.getId() == 1)
                .anyMatch(course -> course.getName().equals(courseWithId1.getName()));

        Assertions.assertTrue(courseWithId9AndSameNameExists);    }
}
