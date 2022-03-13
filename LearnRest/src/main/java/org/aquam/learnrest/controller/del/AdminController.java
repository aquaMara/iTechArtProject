package org.aquam.learnrest.controller.del;

/*
import lombok.AllArgsConstructor;
import org.aquamara.tolearn.model.Section;
import org.aquamara.tolearn.model.Subject;
import org.aquamara.tolearn.service.SectionService;
import org.aquamara.tolearn.service.SubjectService;
import org.aquamara.tolearn.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/learn/admin")
@AllArgsConstructor
public class AdminController {

    private final SubjectService subjectService;
    private final SectionService sectionService;
    private final UserService userService;

    @GetMapping("")
    public ModelAndView showAdminMain() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_main.html");
        return mv;
    }

    @GetMapping("/subjects")
    public ModelAndView showSubjects() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_subjects.html");
        mv.addObject("subjects", subjectService.findSubjects());
        // STACK OVERFLOW
        mv.addObject("subject", new Subject());
        return mv;
    }

    @PostMapping("/add-subject")
    public ModelAndView addSubject(@ModelAttribute("subject") Subject subject, @ModelAttribute("file") MultipartFile file) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_subjects.html");
        String filePath = subjectService.uploadImage(file);
        subject.setFilePath(filePath);
        if (!subject.getSubjectName().isEmpty() && !subject.getSubjectName().isBlank())
            subjectService.addSubject(subject);
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("subject", new Subject());
        return mv;
    }

    @PostMapping("/delete-subject")
    public ModelAndView deleteSubject(@ModelAttribute("subject") Subject subject) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_subjects.html");
        subjectService.deleteSubject(subject);
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("subject", new Subject());
        return mv;
    }

    @GetMapping("/sections")
    public ModelAndView showSections() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_sections.html");
        mv.addObject("sections", sectionService.findSections());
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("section", new Section());
        return mv;
    }

    // required=false for @RequestBody
    @PostMapping("/add-section")
    public ModelAndView addSection(@ModelAttribute("section") Section section, @ModelAttribute("subject") Subject subject) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_sections.html");
        if (!section.getSectionName().isEmpty() && !section.getSectionName().isBlank()
                && subject.getSubjectName() != null) {
            section.setSubject(subject);
            sectionService.addSection(section);
        }
        mv.addObject("sections", sectionService.findSections());
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("section", new Section());
        return mv;
    }

    //@PostMapping("/delete-section")
    @DeleteMapping("/delete-section")
    public ModelAndView deleteSection(@ModelAttribute("section") Section section) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_sections.html");
        sectionService.deleteSection(section);
        mv.addObject("sections", sectionService.findSections());
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("section", new Section());
        return mv;
    }

    @GetMapping("/users")
    public ModelAndView showUsers(@ModelAttribute("section") Subject subject) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("admin/admin_sections.html");
        mv.addObject("sections", sectionService.findSections());
        return mv;
    }

}


 */