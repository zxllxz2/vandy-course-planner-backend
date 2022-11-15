package edu.vanderbilt.vandycourseplanner.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.mapper.CourseMapper;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private final HashSet<String> softwarePartial =
            new HashSet<>(Arrays.asList("cs2201", "cs3251", "cs3270"));
    private final HashSet<String> hardware = new HashSet<>(Arrays.asList("eece2123", "cs3281"));
    private final HashSet<String> foundation = new HashSet<>(Arrays.asList("cs2212", "cs3250"));
    private final HashSet<String> project =
            new HashSet<>(Arrays.asList("cs3259", "cs123892", "cs143892", "cs4249"
            , "cs4269", "cs4279", "cs4287"));
    private final String seminar = "cs4959";
    private final HashSet<String> depthEece = new HashSet<>(Arrays.asList("eece4353", "eece4354"
            , "eece4376"));
    private final HashSet<String> depthMath = new HashSet<>(Arrays.asList("math3320", "math3620",
            "math4600", "math4620"));

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

    @Override
    public RespBean classifyCourse(String subject, Integer number) {
        String requirement = null;
        String target = subject.toLowerCase() + number.toString();
        if (target.equals("cs1101") || target.equals("cs1104") || softwarePartial.contains(target)) {
            requirement = "software";
        } else if (hardware.contains(target)) {
            requirement = "hardware";
        } else if (foundation.contains(target)) {
            requirement = "foundation";
        } else if (project.contains(target)) {
            requirement = "project";
        } else if (target.equals(seminar)) {
            requirement = "seminar";
        } else {
            if (depthEece.contains(target) || depthMath.contains(target)
                    || (subject.equalsIgnoreCase("cs") && number >= 3000 && number != 3262)) {
                requirement = "depth";
            }
        }
        return RespBean.success(null, requirement);
    }

    @Override
    public RespBean isSatisfied(List<Course> courses, String requirement) {
        Boolean satisfied = false;
        HashSet<String> set = new HashSet<>();

        for (Course course : courses) {
            set.add(course.getSubject().toLowerCase() + course.getNumber().toString());
        }

        switch (requirement.toLowerCase()) {
            case "software":
                satisfied = (set.contains("cs1101") || set.contains("cs1104"))
                            && set.containsAll(softwarePartial);
                break;
            case "hardware":
                satisfied = set.containsAll(hardware);
                break;
            case "foundation":
                satisfied = set.containsAll(foundation);
                break;
            case "project":
                satisfied = set.containsAll(project);
                break;
            case "seminar":
                satisfied = set.contains(seminar);
                break;
            case "depth":
                int count = ((int) courses
                        .stream()
                        .filter(c -> c.getSubject().equalsIgnoreCase("cs")
                                && c.getNumber() >= 3000
                                && c.getNumber() != 3262)
                        .count());
                if (count < 4) {
                    HashSet<String> tempEece = new HashSet<>(depthEece);
                    HashSet<String> tempMath = new HashSet<>(depthMath);
                    tempEece.retainAll(set);
                    tempMath.retainAll(set);
                    count += tempMath.size() > 2 ? tempEece.size() + 2 :
                            tempEece.size() + tempMath.size();
                }
                satisfied = count >= 4;
        }
        return RespBean.success(null, satisfied);
    }
}
