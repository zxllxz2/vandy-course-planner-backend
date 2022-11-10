package edu.vanderbilt.vandycourseplanner.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;

@Data
public class CourseStatusResponse {
//    @MppMultiId
//    @TableField(value = "Subject")
    private String Subject;

//    @MppMultiId
//    @TableField(value = "Course_no")
    private Integer Course_no;

    private String status;
}
