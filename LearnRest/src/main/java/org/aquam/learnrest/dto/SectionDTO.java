package org.aquam.learnrest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SectionDTO {

    private Long sectionId;
    private String sectionName;

    private Long subjectId;
    // private String subjectName;

}
