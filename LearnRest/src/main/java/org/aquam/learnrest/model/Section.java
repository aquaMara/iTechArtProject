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
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sectionId;
    private String name;

    @ManyToOne
    private Subject subject;

    @OneToMany(mappedBy = "section")
    List<Article> allArticles = new ArrayList<>();

    public void addArticle(Article article) {
        this.allArticles.add(article);
        article.setSection(this);
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionId=" + sectionId +
                ", sectionName='" + name + '\'' +
                '}';
    }
}
