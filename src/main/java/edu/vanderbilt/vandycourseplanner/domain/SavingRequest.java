package edu.vanderbilt.vandycourseplanner.domain;

import lombok.Data;
import lombok.NonNull;

@Data
public class SavingRequest {

    @NonNull
    private String email;

    private String subject = "CS";

    @NonNull
    private Integer number;
}
