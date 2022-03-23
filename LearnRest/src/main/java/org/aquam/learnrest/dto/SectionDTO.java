package org.aquam.learnrest.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SectionDTO {

    private Long sectionId;
    @NotBlank(message = "Can not be empty")
    private String sectionName;

    private Long subjectId;

}
