package org.aquam.learnrest.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subjectId;
    @Column(unique = true)
    private String name;
    private String filePath;

    @OneToMany(mappedBy = "subject")
    List<Section> allSections = new ArrayList<>();

    public void addSection(Section section) {
        this.allSections.add(section);
        section.setSubject(this);
    }

    @OneToMany(mappedBy = "subject")
    List<Article> allArticles = new ArrayList<>();

    public void addArticle(Article article) {
        this.allArticles.add(article);
        article.setSubject(this);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + name + '\'' +
                '}';
    }
}
