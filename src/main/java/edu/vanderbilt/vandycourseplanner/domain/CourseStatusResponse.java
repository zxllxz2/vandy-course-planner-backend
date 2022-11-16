package edu.vanderbilt.vandycourseplanner.domain;

import lombok.Data;

@Data
public class CourseStatusResponse {

    private String Subject;

    private Integer Course_no;

    private String status;
}
