package edu.vanderbilt.vandycourseplanner.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.mapper.CourseMapper;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Course service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class CourseServiceImpl extends MppServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getCoursesByLevel(Integer level) {
        // Get all course details by level
        List<Course> coursesByLevel = courseMapper.getCoursesByLevel(level);
        // Return empty list if nothing found
        if (Objects.isNull(coursesByLevel)) {
            return new ArrayList<>();
        }

        // Make prerequisites be strings for each course
        for (Course course : coursesByLevel) {
            List<List<String>> prereqs = new ArrayList<>();
            List<String> levelZero = new ArrayList<>();
            // If the course has no prereq, continue to next course
            if (course.getPrerequisites().isEmpty()) {
                prereqs.add(levelZero);
                course.setPrereqs(prereqs);
                continue;
            }

            // Add one of the lowest-level prereq
            List<Prerequisite> prerequisiteList = course.getPrerequisites();
            // The first (index 0) list of prereq is for courses conflict with the current course
            if (prerequisiteList.get(0).getLevel() != 0) {
                prereqs.add(new ArrayList<>());
            }
            Prerequisite prerequisite0 = prerequisiteList.get(0);
            levelZero.add(prerequisite0.getPre_subject() + " " + prerequisite0.getPre_course_no().toString());

            // Add all remaining prereqs
            // Every list, starting from index 1, must have >= 1 course satisfied as prereq
            for (int i = 1; i < prerequisiteList.size(); ++i) {
                if (!Objects.equals(prerequisiteList.get(i).getLevel(),
                        prerequisiteList.get(i-1).getLevel())) {
                    prereqs.add(new ArrayList<>(levelZero));
                    levelZero.clear();
                }
                Prerequisite prerequisiteCur = prerequisiteList.get(i);
                levelZero.add(prerequisiteCur.getPre_subject() + " " + prerequisiteCur.getPre_course_no().toString());
            }

            prereqs.add(levelZero);
            course.setPrereqs(prereqs);
        }

        return coursesByLevel;
    }
}
