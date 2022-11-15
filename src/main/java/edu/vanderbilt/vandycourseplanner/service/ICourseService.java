package edu.vanderbilt.vandycourseplanner.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;

import java.util.List;

/**
 * Course service
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
public interface ICourseService extends IMppService<Course> {

    /**
     * Get all courses in a list by course level
     * @param level - 1, or 2, or 3, or 4, or null
     * @return
     */
    List<Course> getCoursesByLevel(Integer level);

    /**
     * Check the major requirement portion that a course satisfies
     * It assumes that the course exists
     * @param subject
     * @param number
     * @return
     */
    RespBean classifyCourse(String subject, Integer number);

    /**
     * Check if the selected courses satisfy the major requirement
     * @param courses
     * @return
     */
    RespBean isSatisfied(List<CourseRequest> courses);
}
