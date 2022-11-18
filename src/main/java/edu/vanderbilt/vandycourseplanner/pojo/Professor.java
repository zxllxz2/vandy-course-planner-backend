package edu.vanderbilt.vandycourseplanner.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Professor
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Professor")
public class Professor implements Serializable {

    private static final long serialVersionUID = 1L;

    @MppMultiId
    @TableField(value = "Tid")
    private Integer Tid;

    @TableField("Name")
    private String Name;

    @TableField("Over_rate")
    private BigDecimal Over_rate;

    @TableField("Diff_rate")
    private BigDecimal Diff_rate;


}
