package org.aquam.learnrest.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleDTO {

    private Long articleId;
    @NotBlank(message = "Can not be empty")
    private String heading;
    @NotBlank(message = "Can not be empty")
    private String content;
    private String link;
    private String literature;
    private String filePath;

    private Long sectionId;
    private Long userId;

}