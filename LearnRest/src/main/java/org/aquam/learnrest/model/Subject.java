package org.aquam.learnrest.model;

import lombok.*;
import org.aquam.learnrest.dto.SubjectDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subjectId;
    @Column(unique = true)
    private String subjectName;
    private String filePath;

    @OneToMany(mappedBy = "subject")
    List<Section> allSections = new ArrayList<>();

    public void addSection(Section section) {
        this.allSections.add(section);
        section.setSubject(this);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
