package edu.vanderbilt.vandycourseplanner.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *  Mapper API
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Mapper
public interface CourseMapper extends MppBaseMapper<Course> {

    /**
     * Get the detailed course list by indicating the level
     * @param level
     * @return
     */
    List<Course> getCoursesByLevel(Integer level);

    /**
     * @author Chenxi Dong
     *
     * Get the course by subject and course number
     * @param subject
     * @param number
     * @return
     */
    Course getCourseBySubjectAndNumber(String subject, Integer number);

    /**
     * Get all courses with their prerequisites
     * @return
     */
    List<Course> getAllCourseWithPrereqs();
}
