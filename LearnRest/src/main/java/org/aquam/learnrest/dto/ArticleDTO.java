package org.aquam.learnrest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ArticleDTO {

    private Long articleId;
    private String heading;
    private String content;
    private String link;
    private String literature;

    private Long sectionId;
    // private String sectionName;

    private Long userId;
    // private String username;

}
