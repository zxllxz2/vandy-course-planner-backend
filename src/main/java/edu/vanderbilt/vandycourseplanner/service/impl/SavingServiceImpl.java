package edu.vanderbilt.vandycourseplanner.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import edu.vanderbilt.vandycourseplanner.domain.CourseRequest;
import edu.vanderbilt.vandycourseplanner.domain.CourseStatusResponse;
import edu.vanderbilt.vandycourseplanner.pojo.RespBean;
import edu.vanderbilt.vandycourseplanner.pojo.Saving;
import edu.vanderbilt.vandycourseplanner.mapper.SavingMapper;
import edu.vanderbilt.vandycourseplanner.service.IPrerequisiteService;
import edu.vanderbilt.vandycourseplanner.service.ISavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private IPrerequisiteService prerequisiteService;

    @Override
    public List<CourseStatusResponse> getSavingByUser(String email) {
        List<Saving> savings = savingMapper.selectList(new QueryWrapper<Saving>().eq("Email", email));
        if (savings.isEmpty()) return new ArrayList<>();
        List<CourseRequest> input = new ArrayList<>();
        savings.forEach(s -> input.add(new CourseRequest(s.getSubject(), s.getCourse_no())));
        return prerequisiteService.getPrereqs(input);
    }

    @Override
    public RespBean addOneSaving(String email, String subject, Integer number) {

        QueryWrapper<Saving> eq = new QueryWrapper<Saving>()
                .eq("Email", email)
                .eq("Subject", subject)
                .eq("Course_no", number);

        if (savingMapper.selectOne(eq) != null) {
            return RespBean.error("Course already saved");
        }

        Saving saving = new Saving();
        saving.setEmail(email);
        saving.setSubject(subject);
        saving.setCourse_no(number);
        saving.setSaving_time(LocalDateTime.now());

        if (savingMapper.insert(saving) == 1) {
            return RespBean.success("Successfully saved");
        } else {
            return RespBean.error("Fail to save course");
        }

    }

    @Override
    public RespBean deleteOneSaving(String email, String subject, Integer number) {

        QueryWrapper<Saving> eq = new QueryWrapper<Saving>()
                .eq("Email", email)
                .eq("Subject", subject)
                .eq("Course_no", number);

        if (savingMapper.selectList(eq).size() != 1) {
            return RespBean.error("User not exist or course not saved");
        }

        if (savingMapper.delete(eq) == 1) {
            return RespBean.success("Successfully deleted");
        } else {
            return RespBean.error("Fail to delete course");
        }
    }
}
