package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Course;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/course")
public class CourseController extends BaseController<Course>{
}