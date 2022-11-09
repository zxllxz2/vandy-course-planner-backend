package edu.vanderbilt.vandycourseplanner.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import edu.vanderbilt.vandycourseplanner.pojo.Course;

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
    public List<Course> getCoursesByLevel(Integer level);

}
