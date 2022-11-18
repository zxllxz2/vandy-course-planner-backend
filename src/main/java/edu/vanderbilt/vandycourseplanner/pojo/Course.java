package edu.vanderbilt.vandycourseplanner.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Course
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @MppMultiId
    @TableField("Number")
    private Integer Number;

    @MppMultiId
    @TableField("Subject")
    private String Subject;

    @NonNull
    @TableField("Name")
    private String Name;

    @TableField("Frequency")
    private String Frequency;

    @TableField(exist = false)
    private List<Professor> professors;

    @TableField(exist = false)
    private List<Prerequisite> prerequisites;

    @TableField(exist = false)
    private List<List<String>> prereqs;

    @TableField(exist = false)
    private List<String> coreqs;

}
