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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Prerequisite service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class PrerequisiteServiceImpl extends MppServiceImpl<PrerequisiteMapper, Prerequisite> implements IPrerequisiteService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private PrerequisiteMapper prerequisiteMapper;
    @Override
    public List<CourseStatusResponse> getPrereqs(List<CourseRequest> courses){

        List<Course>theCourses=new ArrayList<>();
        HashMap<String, Integer> courseMap=new HashMap<>();
        for (CourseRequest cs: courses){
            courseMap.put(cs.getSubject()+cs.getCourse_no(), 1);
        }

        List<Course> allCourse=courseMapper.selectList(null);
        List<CourseStatusResponse> allStatus=new ArrayList<>();

        for(Course crs: allCourse) {
            CourseStatusResponse thisStatus = new CourseStatusResponse();
            thisStatus.setSubject(crs.getSubject());
            thisStatus.setCourse_no(crs.getNumber());
            thisStatus.setStatus("notable");

            List<Prerequisite> prereqList = prerequisiteMapper.getPrereqsByCourseSubjectAndNumber(
                    crs.getSubject(),
                    crs.getNumber()
            );

            boolean theLevelSatisfied=false;
            //the boolean variable to check if all courses in the current level is satisfied

            if (courseMap.getOrDefault(crs.getSubject()+crs.getNumber(),0)==1) {
                //if the course is already selected, mark the status as selected
                thisStatus.setStatus("selected");
                allStatus.add(thisStatus);
                continue;
            }

            //if the course (checked not selected by above) has no prerequisite, it is enabled
            if (prereqList.isEmpty()){
                thisStatus.setStatus("able");
                allStatus.add(thisStatus);
                continue;
            }

            //check each prerequisite course
            for(int i=0; i<prereqList.size(); ++i){

                //find the current prerequisite course from the database
                Course thisPrereqCourse=courseMapper.getCourseBySubjectAndNumber(
                        prereqList.get(i).getPre_subject(),
                        prereqList.get(i).getPre_course_no()
                );

                //if there are level0 for this course prereqs
                if (prereqList.get(i).getLevel()==0){
                    boolean foundLevelZero=courseMap.getOrDefault(
                            thisPrereqCourse.getSubject()+thisPrereqCourse.getNumber(),
                            0
                    )==1;
                    //check if the selected courses contain this level 0 prerequisite course
                    if (foundLevelZero){
                        thisStatus.setStatus("notable");
                        break;
                        //jump out of the prereq checking since it is already not able
                    }
                }else
                //check level >0
                {
                    /**if the current level is already not satisfied due
                     * to some prereq courses in the level not selected,
                     * skip the current prereq course checking until the
                     * next level (when the variable may be reset to be true)
                    **/
                    if (theLevelSatisfied){
                        continue;
                    }

                    //when it is the next level
                    if (i>0 && !Objects.equals(prereqList.get(i).getLevel(), prereqList.get(i - 1).getLevel())){
                        //check if the variable is true for the previous level
                        if (!theLevelSatisfied){
                            //set the status to be notable and stop checking
                            // prereqs since one whole level is not satisfied
                            thisStatus.setStatus("notable");
                            break;
                        }else{
                            //if the previous level is satisfied, start checking the current prereq
                            // (the first prereq for the new level)
                            theLevelSatisfied=courseMap.getOrDefault(
                                    thisPrereqCourse.getSubject()+thisPrereqCourse.getNumber(),
                                    0
                            )==1;
                        }
                    }else{
                        //continue checking the prereq for the current level
                        theLevelSatisfied=courseMap.getOrDefault(
                                thisPrereqCourse.getSubject()+thisPrereqCourse.getNumber(),
                                0
                        )==1;
                    }
                }
            }
            if(theLevelSatisfied){
                thisStatus.setStatus("able");
            }
            allStatus.add(thisStatus);

        }
        return allStatus;
    }
}
