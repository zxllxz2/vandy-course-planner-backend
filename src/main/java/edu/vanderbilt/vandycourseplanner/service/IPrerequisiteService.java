package edu.vanderbilt.vandycourseplanner.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;

import java.util.List;
import java.util.Map;

/**
 * Prerequisite service
 *
 * @author Chenxi Dong
 * @since 2022-11-04
 */
public interface IPrerequisiteService extends IMppService<Prerequisite> {

    /**
     * Compute course status according to course selected
     * @param courses - selected courses
     * @return map (course name : status)
     */
    Map<String, String> getPrereqs(List<CourseRequest> courses);
}
