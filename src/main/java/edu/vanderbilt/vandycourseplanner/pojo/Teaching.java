package edu.vanderbilt.vandycourseplanner.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Teaching
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Teaching")
public class Teaching implements Serializable {

    private static final long serialVersionUID = 1L;

    @MppMultiId
    @TableField(value = "Subject")
    private String Subject;

    @MppMultiId
    @TableField(value = "Course_no")
    private Integer Course_no;

    @MppMultiId
    @TableField(value = "Tid")
    private Integer Tid;


}
