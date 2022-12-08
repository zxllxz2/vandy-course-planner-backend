package edu.vanderbilt.vandycourseplanner.domain;

import lombok.Data;
import lombok.NonNull;

/**
 * @author Chenxi Dong
 *
 * Request of course info
 */
@Data
public class CourseRequest {

    @NonNull
    private String Subject;

    @NonNull
    private Integer Course_no;
}
