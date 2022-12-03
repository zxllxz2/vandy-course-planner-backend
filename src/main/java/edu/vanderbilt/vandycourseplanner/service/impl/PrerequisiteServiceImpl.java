package edu.vanderbilt.vandycourseplanner.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.mapper.CourseMapper;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.mapper.PrerequisiteMapper;
import edu.vanderbilt.vandycourseplanner.domain.CourseStatusResponse;
import edu.vanderbilt.vandycourseplanner.service.IPrerequisiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Prerequisite service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class PrerequisiteServiceImpl
        extends MppServiceImpl<PrerequisiteMapper, Prerequisite>
        implements IPrerequisiteService {

    @Autowired
    private CourseMapper courseMapper;

    @Value("${course-status.selected}")
    private String selected;

    @Value("${course-status.able}")
    private String able;

    @Value("${course-status.unable}")
    private String unable;

    @Override
    public Map<String, String> getPrereqs(List<CourseRequest> courses) {

        // Put all selected course into a set to check prereqs
        Set<String> selectedCourse = new HashSet<>();
        courses.forEach(cs -> selectedCourse.add(cs.getSubject() + cs.getCourse_no()));

        // Get all courses with prereqs
        List<Course> allCourseWithPrereqs =  courseMapper.getAllCourseWithPrereqs();
        Map<String, String> allStatus = new HashMap<>();

        for (Course crs : allCourseWithPrereqs) {
            // Initialize returning response for each course
            String index = crs.getSubject() + crs.getNumber();

            // If the course is already selected, mark the status as selected
            if (selectedCourse.contains(index)) {
                allStatus.put(index, selected);
                continue;
            }

            // Set the default returned course status "able"
            allStatus.put(index, able);
            List<Prerequisite> prereqList = crs.getPrerequisites();

            // If the course not selected has no prerequisite, it is enabled
            if (prereqList.isEmpty()) {
                continue;
            }

            // The boolean variable to check if any course in a single level is satisfied
            boolean theLevelSatisfied = prereqList.get(0).getLevel() == 0;

            // Check each prerequisite course with level > 0
            for (int i = 0; i < prereqList.size(); ++i) {

                Prerequisite thisPreq = prereqList.get(i);
                String preIndex = thisPreq.getPre_subject() + thisPreq.getPre_course_no();

                // Disable all 0 level courses
                if (thisPreq.getLevel() == 0 && selectedCourse.contains(preIndex)) {
                    allStatus.put(index, unable);
                    break;
                }

                // Check if the last level has any prereq course satisfied
                if (thisPreq.getLevel() > 0
                        && i > 0
                        && !Objects.equals(thisPreq.getLevel(), prereqList.get(i - 1).getLevel())) {
                    if (!theLevelSatisfied) {
                        allStatus.put(index, unable);
                        break;
                    } else {
                        if (!index.equals(preIndex)) {
                            theLevelSatisfied = selectedCourse.contains(preIndex);
                        }
                    }
                } else {
                    // Skip if it already has a prereq satisfied
                    if (theLevelSatisfied) continue;
                    // Continue checking the prereq for the current level
                    theLevelSatisfied = index.equals(preIndex) || selectedCourse.contains(preIndex);
                }
            }
            if (!theLevelSatisfied) {
                allStatus.put(index, unable);
            }
        }
        return allStatus;
    }
}
