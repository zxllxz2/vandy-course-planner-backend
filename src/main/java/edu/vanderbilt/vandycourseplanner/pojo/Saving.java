package edu.vanderbilt.vandycourseplanner.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Saving
 *
 * @author Toby Zhu
 * @since 2022-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("Saving")
public class Saving implements Serializable {

    private static final long serialVersionUID = 1L;

    @MppMultiId
    @TableField(value = "Email")
    private String Email;

    @MppMultiId
    @TableField(value = "Subject")
    private String Subject;

    @MppMultiId
    @TableField(value = "Course_no")
    private Integer Course_no;

    @TableField("Saving_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime Saving_time;


}
