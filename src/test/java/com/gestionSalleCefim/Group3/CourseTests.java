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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    void testPostCourse() throws Exception {
        LocalDate startDate = LocalDate.parse("2020-01-12");
        LocalDate endDate = LocalDate.parse("2020-08-20");

        Course course = new Course();
        course.setName("course test");
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setNbStudents(34);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isCreated();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Course newCourse = objectMapper.readValue(contentAsString, Course.class);
        Assertions.assertNotNull(newCourse.getId());
    }

    @Test
    void testPostCourseIdExists() throws Exception {
        LocalDate startDate = LocalDate.parse("2020-01-12");
        LocalDate endDate = LocalDate.parse("2020-08-20");

        Course course = new Course();
        course.setId(1);
        course.setName("course test");
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setNbStudents(34);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andReturn().getResponse().getContentAsString();

        Course newCourse = objectMapper.readValue(contentAsString, Course.class);
        assert !Objects.equals(newCourse.getName(), course.getName());
    }

    @Test
    void testPrintAllCoursesLastName(){
        List<Course> courses = courseRepository.findAll();
        courses.forEach(course -> System.out.println(course.getName()));
    }

    @Test
    void testFindCourseById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/course/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Marketing Digital"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-01-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-08-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbStudents").value(10))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     *
     * @throws Exception
     *
     * erreur attendue : java.lang.AssertionError: JSON path "$.name" expected:<Marketing Digital> but was:<Analyse de Données>
     */
    @Test
    void testFindCourseByIdError() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/api/course/{id}", 1);

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        String contentAsString = mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Analyse de Données"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-01-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-08-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbStudents").value(10))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    void testGetAllCoursesByAPI() {
        MockMvcRequestBuilders.get("/api/course/all");

        MockMvcResultMatchers.status().isOk();
    }

    @Test
    public void testUpdateCourse() throws Exception
    {
        LocalDate startDate = LocalDate.parse("2020-01-12");
        LocalDate endDate = LocalDate.parse("2020-08-20");

        Course course = new Course();
        course.setId(1);
        course.setName("course test update");
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setNbStudents(34);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course));

        ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
        mockMvc.perform(request)
                .andExpect(resultStatus)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("course test update"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value("2020-01-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value("2020-08-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbStudents").value(34))
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    void testDeleteCourse() throws Exception {
        Course course = courseRepository.findById(1).orElse(null);
        Assertions.assertNotNull(course);

        mockMvc.perform( MockMvcRequestBuilders.delete("/api/course/{id}", 1) )
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Course course1 = courseRepository.findById(1).orElse(null);
        Assertions.assertNull(course1);
    }
}
