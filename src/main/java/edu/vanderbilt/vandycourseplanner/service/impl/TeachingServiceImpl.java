package edu.vanderbilt.vandycourseplanner.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.pojo.Teaching;
import edu.vanderbilt.vandycourseplanner.mapper.TeachingMapper;
import edu.vanderbilt.vandycourseplanner.service.ITeachingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Teaching service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class TeachingServiceImpl extends MppServiceImpl<TeachingMapper, Teaching> implements ITeachingService {

}
