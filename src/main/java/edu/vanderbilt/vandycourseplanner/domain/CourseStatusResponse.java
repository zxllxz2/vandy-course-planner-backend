package edu.vanderbilt.vandycourseplanner.domain;

import lombok.Data;

/**
 * @author Chenxi Dong
 *
 * Response of course info
 */
@Data
public class CourseStatusResponse {

    private String Subject;

    private Integer Course_no;

    private String status;
}
