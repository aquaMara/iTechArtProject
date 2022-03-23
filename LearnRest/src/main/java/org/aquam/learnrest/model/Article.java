package org.aquam.learnrest.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long articleId;
    private String heading;
    @Column(columnDefinition = "TEXT")
    private String content;

    private String link;
    private String literature;
    private Integer timesClicked;
    private Integer rating;

    private String filePath;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Section section;

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleHeading='" + heading + '\'' +
                ", articleContent='" + content + '\'' +
                ", link='" + link + '\'' +
                ", literature='" + literature + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
