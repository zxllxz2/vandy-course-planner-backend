package edu.vanderbilt.vandycourseplanner.controller;


import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private ICourseService courseService;

    @GetMapping("/")
    public List<Course> getCoursesByLevel(@RequestParam Integer level) {
        return courseService.getCoursesByLevel(level);
    }

}
