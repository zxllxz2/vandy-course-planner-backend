package edu.vanderbilt.vandycourseplanner.mapper;

import edu.vanderbilt.vandycourseplanner.pojo.Professor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper API
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Mapper
public interface ProfessorMapper extends BaseMapper<Professor> {

}
