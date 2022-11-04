package edu.vanderbilt.vandycourseplanner.service.impl;

import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.pojo.Saving;
import edu.vanderbilt.vandycourseplanner.mapper.SavingMapper;
import edu.vanderbilt.vandycourseplanner.service.ISavingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Saving service impl
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Service
public class SavingServiceImpl extends MppServiceImpl<SavingMapper, Saving> implements ISavingService {

    @Autowired
    private SavingMapper savingMapper;

    @Override
    public RespBean addOneSaving(String email, String subject, int number) {
        Saving saving = new Saving();
        saving.setEmail(email);
        saving.setSubject(subject);
        saving.setCourse_no(number);
        if (savingMapper.selectByMultiId(saving) != null) {
            return RespBean.error("Course already saved");
        } else {
            saving.setSaving_time(LocalDateTime.now());
        }

        if (savingMapper.insert(saving) == 1) {
            return RespBean.success("Successfully saved");
        } else {
            return RespBean.error("Fail to save course");
        }

    }
}
