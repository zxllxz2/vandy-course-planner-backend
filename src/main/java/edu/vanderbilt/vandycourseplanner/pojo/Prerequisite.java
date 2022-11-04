package edu.vanderbilt.vandycourseplanner.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Prerequisite
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Prerequisite")
public class Prerequisite implements Serializable {

    private static final long serialVersionUID = 1L;

    @MppMultiId
    @TableField(value = "Pre_subject")
    private String Pre_subject;

    @MppMultiId
    @TableField(value = "Pre_course_no")
    private Integer Pre_course_no;

    @MppMultiId
    @TableField(value = "Subject")
    private String Subject;

    @MppMultiId
    @TableField(value = "Course_no")
    private Integer Course_no;

    @TableField("Level")
    private Integer Level;


}
