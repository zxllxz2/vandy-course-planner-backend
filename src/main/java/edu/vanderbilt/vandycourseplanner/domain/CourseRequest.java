package edu.vanderbilt.vandycourseplanner.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.NonNull;

@Data
public class CourseRequest {
//    @MppMultiId
//    @TableField(value = "Subject")
    @NonNull
    private String Subject;

//    @MppMultiId
//    @TableField(value = "Course_no")
    @NonNull
    private Integer Course_no;
}
