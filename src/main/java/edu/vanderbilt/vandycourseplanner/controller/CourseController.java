package edu.vanderbilt.vandycourseplanner.controller;


import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RespBean getCoursesByLevel(@RequestParam(required = false) Integer level) {
        return RespBean.success(null, courseService.getCoursesByLevel(level));
    }

    @GetMapping("/classify")
    public RespBean classifyCourse(@RequestParam String subject,
                                   @RequestParam Integer number) {
        return courseService.classifyCourse(subject, number);
    }

    @PostMapping("/satisfy")
    public RespBean isSatisfied(@RequestBody List<CourseRequest> courses) {
        return courseService.isSatisfied(courses);
    }

}
