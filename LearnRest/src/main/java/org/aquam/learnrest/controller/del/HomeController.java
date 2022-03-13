package org.aquam.learnrest.controller.del;
/*
import lombok.AllArgsConstructor;
import org.aquamara.tolearn.model.AppUser;
import org.aquamara.tolearn.model.Article;
import org.aquamara.tolearn.service.ArticleService;
import org.aquamara.tolearn.service.SubjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/learn")
@AllArgsConstructor
public class HomeController {

    private final ArticleService articleService;
    private final SubjectService subjectService;

    @GetMapping("")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("main/index.html");
        mv.addObject("subjects", subjectService.findSubjects());
        return mv;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("main/home.html");
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mv.addObject("user", principal);
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("articles", articleService.findArticles());
        List<Article> a = articleService.findArticles();
        return mv;
    }

}

 */
