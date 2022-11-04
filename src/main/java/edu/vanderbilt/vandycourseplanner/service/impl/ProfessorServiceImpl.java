package edu.vanderbilt.vandycourseplanner.service.impl;

import edu.vanderbilt.vandycourseplanner.pojo.Professor;
import edu.vanderbilt.vandycourseplanner.mapper.ProfessorMapper;
import edu.vanderbilt.vandycourseplanner.service.IProfessorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Professor Service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class ProfessorServiceImpl extends ServiceImpl<ProfessorMapper, Professor> implements IProfessorService {

}
