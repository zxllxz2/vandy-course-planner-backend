package edu.vanderbilt.vandycourseplanner.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.pojo.Saving;
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
     * @author Chenxi Dong
     *
     * Get prerequisites by subject and course number
     * @param subject
     * @param number
     * @return
     */
    List<Prerequisite> getPrereqsByCourseSubjectAndNumber(String subject, Integer number);

    /**
     * Get course number and subject of prerequisites according to provided saved courses
     *
     * *** NOTE *** Please ALWAYS check savings is NOT EMPTY!!!
     *
     * @param savings
     * @return
     */
    List<String> getPrereqsFromSavings(List<Saving> savings);

    /**
     * Get level 0 prerequisites by a list of subjects and course numbers
     *
     * *** NOTE *** Please ALWAYS check courses is NOT EMPTY!!!
     *
     * @param courses
     * @return
     */
    List<Prerequisite> getCourseLevelZero(List<CourseRequest> courses);
}
