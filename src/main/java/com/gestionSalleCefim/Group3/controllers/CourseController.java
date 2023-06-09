package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Course;
import com.gestionSalleCefim.Group3.repositories.CourseRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/course")
public class CourseController extends BaseController<Course, CourseRepository>{
}