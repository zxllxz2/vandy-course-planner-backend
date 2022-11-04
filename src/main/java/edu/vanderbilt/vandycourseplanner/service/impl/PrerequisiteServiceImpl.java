package edu.vanderbilt.vandycourseplanner.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.pojo.Prerequisite;
import edu.vanderbilt.vandycourseplanner.mapper.PrerequisiteMapper;
import edu.vanderbilt.vandycourseplanner.service.IPrerequisiteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Prerequisite service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class PrerequisiteServiceImpl extends MppServiceImpl<PrerequisiteMapper, Prerequisite> implements IPrerequisiteService {

}
