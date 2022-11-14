package edu.vanderbilt.vandycourseplanner.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
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
    List<Prerequisite> getPrereqsByCourseSubjectAndNumber(String subject, Integer number);
}
