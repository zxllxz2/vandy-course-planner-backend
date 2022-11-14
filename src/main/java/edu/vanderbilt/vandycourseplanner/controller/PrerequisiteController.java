package edu.vanderbilt.vandycourseplanner.controller;


import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.domain.CourseStatusResponse;
import edu.vanderbilt.vandycourseplanner.service.IPrerequisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@RestController
@RequestMapping("/prereqs")
public class PrerequisiteController {

    @Autowired
    private IPrerequisiteService prerequisiteService;

    @GetMapping("/")
    public List<CourseStatusResponse> getPrereqs(@RequestBody List<CourseRequest> courses) {
        return prerequisiteService.getPrereqs(courses);
    }
}
