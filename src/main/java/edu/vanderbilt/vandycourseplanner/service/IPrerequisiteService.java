package edu.vanderbilt.vandycourseplanner.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.domain.CourseStatusResponse;

import java.util.List;
import java.util.Map;

/**
 * Prerequisite service
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
public interface IPrerequisiteService extends IMppService<Prerequisite> {
    Map<String, String> getPrereqs(List<CourseRequest> courses);
}
