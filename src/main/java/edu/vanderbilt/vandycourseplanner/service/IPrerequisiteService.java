package edu.vanderbilt.vandycourseplanner.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.domain.CourseStatusResponse;

import java.util.List;

/**
 * Prerequisite service
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
public interface IPrerequisiteService extends IMppService<Prerequisite> {
    List<CourseStatusResponse> getPrereqs(List<CourseRequest> courses);
}
