package edu.vanderbilt.vandycourseplanner.service.impl;

import edu.vanderbilt.vandycourseplanner.pojo.Course;
import edu.vanderbilt.vandycourseplanner.mapper.CourseMapper;
import edu.vanderbilt.vandycourseplanner.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Course service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

}
