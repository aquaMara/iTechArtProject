package org.aquam.learnrest.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubjectDTO {

    private Long subjectId;
    @NotBlank(message = "Can not be empty")
    private String subjectName;

}
