package edu.vanderbilt.vandycourseplanner.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.mapper.CourseMapper;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${constants.software-highlevel}")
    private HashSet<String> softwarePartial;

    @Value("${constants.hardware}")
    private HashSet<String> hardware;

    @Value("${constants.foundation}")
    private HashSet<String> foundation;

    @Value("${constants.project}")
    private HashSet<String> project;

    @Value("${constants.seminar}")
    private String seminar;

    @Value("${constants.depth-eece}")
    private HashSet<String> depthEece;

    @Value("${constants.depth-math}")
    private HashSet<String> depthMath;

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
            // String list for prereqs
            List<List<String>> prereqs = new ArrayList<>();
            List<String> levelZero = new ArrayList<>();

            // String list for coreqs
            List<String> coreqs = new ArrayList<>();

            // If the course has no prereq, continue to next course
            if (course.getPrerequisites().isEmpty()) {
                prereqs.add(levelZero);
                course.setPrereqs(prereqs);
                course.setCoreqs(coreqs);
                continue;
            }

            // Add one of the lowest-level prereq
            List<Prerequisite> prerequisiteList = course.getPrerequisites();

            // The first (index 0) list of prereq is for courses conflict with the current course
            if (prerequisiteList.get(0).getLevel() != 0) {
                prereqs.add(new ArrayList<>());
            }

            Prerequisite prerequisite0 = prerequisiteList.get(0);
            if (prerequisiteList.get(0).getLevel() < 100) {
                levelZero.add(prerequisite0.getPre_subject() + " " + prerequisite0.getPre_course_no().toString());
            } else {
                StringBuilder coName = new StringBuilder(prerequisite0.getPre_subject());
                coName.append(' ');
                coName.append(prerequisite0.getPre_course_no());
                if (prerequisite0.getPre_subject().equals(course.getSubject())
                        && Objects.equals(prerequisite0.getPre_course_no(), course.getNumber())) {
                    coName.append('L');
                }
                coreqs.add(coName.toString());
            }


            // Add all remaining prereqs
            // Every list, starting from index 1, must have >= 1 course satisfied as prereq
            for (int i = 1; i < prerequisiteList.size(); ++i) {
                if (!Objects.equals(prerequisiteList.get(i).getLevel(),
                        prerequisiteList.get(i - 1).getLevel())) {
                    prereqs.add(new ArrayList<>(levelZero));
                    levelZero.clear();
                }
                Prerequisite prerequisiteCur = prerequisiteList.get(i);
                if (prerequisiteCur.getLevel() >= 100) {
                    StringBuilder coName2 = new StringBuilder(prerequisiteCur.getPre_subject());
                    coName2.append(' ');
                    coName2.append(prerequisiteCur.getPre_course_no());
                    if (prerequisiteCur.getPre_subject().equals(course.getSubject())
                            && Objects.equals(prerequisiteCur.getPre_course_no(), course.getNumber())) {
                        coName2.append('L');
                    }
                    coreqs.add(coName2.toString());
                } else {
                    levelZero.add(prerequisiteCur.getPre_subject() + " " + prerequisiteCur.getPre_course_no().toString());
                }
            }

            if (!levelZero.isEmpty()) prereqs.add(levelZero);
            course.setPrereqs(prereqs);
            course.setCoreqs(coreqs);
        }

        return coursesByLevel;
    }

    @Override
    public RespBean classifyCourse(String subject, Integer number) {

        if (courseMapper.getCourseBySubjectAndNumber(subject, number) == null) {
            return RespBean.error("No such course exists");
        }

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
    public RespBean isSatisfied(List<CourseRequest> courses) {
        Map<String, Boolean> satisfied = new HashMap<>();
        HashSet<String> set = new HashSet<>();

        for (CourseRequest course : courses) {
            set.add(course.getSubject().toLowerCase() + course.getCourse_no());
        }

        satisfied.put("software", (set.contains("cs1101") || set.contains("cs1104"))
                && set.containsAll(softwarePartial));
        satisfied.put("hardware", set.containsAll(hardware));
        satisfied.put("foundation", set.containsAll(foundation));
        satisfied.put("seminar", set.contains(seminar));

        set.retainAll(project);
        satisfied.put("project", !set.isEmpty());
        String oneProject = set.isEmpty() ? null : set.toArray()[0].toString();

        int count = ((int) courses
                .stream()
                .filter(c -> {
                    String t = c.getSubject().toLowerCase() + c.getCourse_no();
                    return c.getSubject().equalsIgnoreCase("cs")
                            && !softwarePartial.contains(t)
                            && !hardware.contains(t)
                            && !foundation.contains(t)
                            && !t.equals(oneProject)
                            && !t.equals(seminar)
                            && c.getCourse_no() >= 3000
                            && c.getCourse_no() != 3262;
                })
                .count());
        if (count < 4) {
            HashSet<String> tempEece = new HashSet<>(depthEece);
            HashSet<String> tempMath = new HashSet<>(depthMath);
            tempEece.retainAll(set);
            tempMath.retainAll(set);
            count += tempMath.size() > 2 ? tempEece.size() + 2 :
                    tempEece.size() + tempMath.size();
        }
        satisfied.put("depth", count >= 4);
        return RespBean.success(null, satisfied);
    }
}
