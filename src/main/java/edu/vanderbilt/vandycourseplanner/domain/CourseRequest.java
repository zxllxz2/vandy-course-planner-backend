package edu.vanderbilt.vandycourseplanner.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class CourseRequest {

    @NonNull
    private String Subject;

    @NonNull
    private Integer Course_no;
}
