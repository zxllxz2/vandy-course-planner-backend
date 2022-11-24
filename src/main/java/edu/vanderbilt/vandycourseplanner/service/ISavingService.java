package edu.vanderbilt.vandycourseplanner.service;

import com.github.jeffreyning.mybatisplus.service.IMppService;
import edu.vanderbilt.vandycourseplanner.domain.CourseStatusResponse;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.pojo.Saving;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Saving service
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
public interface ISavingService extends IMppService<Saving> {

    /**
     * Get all saved courses of a user and the status of every course
     * @param email
     * @return
     */
    Map<String, String> getSavingByUser(String email);

    /**
     * Add one user saved course selection
     * @param email
     * @param subject
     * @param number
     * @return
     */
    RespBean addOneSaving(String email, String subject, Integer number);

    /**
     * Delete one user saved course selection
     * @param email
     * @param subject
     * @param number
     * @return
     */
    RespBean deleteOneSaving(String email, String subject, Integer number);
}
