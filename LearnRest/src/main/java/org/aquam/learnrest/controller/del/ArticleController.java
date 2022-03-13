package org.aquam.learnrest.controller.del;
/*
import lombok.AllArgsConstructor;
import org.aquamara.tolearn.model.AppUser;
import org.aquamara.tolearn.model.Article;
import org.aquamara.tolearn.model.Section;
import org.aquamara.tolearn.model.Subject;
import org.aquamara.tolearn.service.ArticleService;
import org.aquamara.tolearn.service.SectionService;
import org.aquamara.tolearn.service.SubjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RestController
@RequestMapping("/learn/article")
@AllArgsConstructor
public class ArticleController {

    // System.getProperty("user.dir") - get the home dir (D:/iTechArt/toLearn)
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
    private final ArticleService articleService;
    private final SubjectService subjectService;
    private final SectionService sectionService;

    @GetMapping("/{articleId}")
    public ModelAndView userAccount(@PathVariable("articleId") String articleId, @ModelAttribute("rating")String rating) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("article", articleService.find(Long.valueOf(articleId)));
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mv.addObject("user", principal);
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("articles", articleService.findArticles());
        mv.setViewName("article/exact.html");
        return mv;
    }

    @GetMapping("/add")
    public ModelAndView addArticle() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("article", new Article());
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("sections", sectionService.findSections());
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mv.addObject("user", principal);
        mv.setViewName("article/new.html");
        return mv;
    }

    @PostMapping("/add") // @RequestParam("article")Article article  // @RequestAttribute("article") Article article
    public ModelAndView uploadArticle(@ModelAttribute("article") Article article, @ModelAttribute("file") MultipartFile file, @ModelAttribute("subject")Subject subject, @ModelAttribute("section")Section section) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("article/new.html");
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (article.getArticleHeading().equals("") || (subject == null || section == null) || file == null) {
            mv.addObject("message", "Fill all the fields!");
        }
        else {
            article.setUser(principal);
            String filePath = articleService.uploadImage(uploadDirectory, file);
            article.setFilePath(filePath);
            articleService.save(article);
        }
        mv.addObject("article", article);
        mv.addObject("subjects", subjectService.findSubjects());
        mv.addObject("sections", sectionService.findSections());
        mv.addObject("articles", articleService.findArticles());
        mv.addObject("user", principal);
        return mv;
    }
}
/*
@PostMapping("/upload") // @RequestParam("article")Article article  // @RequestAttribute("article") Article article
    public ModelAndView uploadArticle(@ModelAttribute Article article, @ModelAttribute MultipartFile file) throws IOException {
        ModelAndView mv = new ModelAndView();
        String uploadPath = "/images/";

        if (file != null) {
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFilename = UUID.randomUUID().toString();
            String resultFilename = uuidFilename + "." + file.getOriginalFilename();
            file.transferTo(new File(resultFilename));
        }


        mv.setViewName("temp.html");
        articleService.saveArticle(article);
        return mv;
    }
 */

// @RequestParam("imageFile")MultipartFile imageFile
        /*
        try {
            articleService.saveImage(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
         */

