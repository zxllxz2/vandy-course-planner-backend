package edu.vanderbilt.vandycourseplanner.controller;


import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
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

    /**
     * @author Chenxi Dong
     * @param courses
     * @return response bean
     */
    @PostMapping("/")
    public RespBean getPrereqs(@RequestBody List<CourseRequest> courses) {
        return RespBean.success(null, prerequisiteService.getPrereqs(courses));
    }
}
