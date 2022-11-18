package edu.vanderbilt.vandycourseplanner.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper API
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Mapper
public interface PrerequisiteMapper extends MppBaseMapper<Prerequisite> {
    /**
     * Get prerequisites by subject and course number
     * @param subject
     * @param number
     * @return
     */
    List<Prerequisite> getPrereqsByCourseSubjectAndNumber(String subject, Integer number);

    /**
     * Get level 0 prerequisites by a list of subjects and course numbers
     * @param courses
     * @return
     */
    List<Prerequisite> getCourseLevelZero(List<CourseRequest> courses);
}
