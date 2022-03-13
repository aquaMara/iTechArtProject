package org.aquam.learnrest.controller.del;
/*
import lombok.AllArgsConstructor;
import org.aquamara.tolearn.model.AppUser;
import org.aquamara.tolearn.repository.ArticleRepository;
import org.aquamara.tolearn.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/learn/user")
@AllArgsConstructor
public class UserController {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @GetMapping("/{userId}")
    public ModelAndView userAccount(@PathVariable("userId") String userId) {
        ModelAndView mv = new ModelAndView();
        // ONLY FOR THAT TEACHER !!! (service)
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser user = (AppUser) userService.loadUserById(Long.valueOf(userId));    // текущий или хочу посмотреть
        mv.addObject("user", user);
        mv.addObject("role", String.valueOf(principal.getUserRole()));  // текущий
        mv.addObject("principal", principal);  // текущий
        // List<Article> a = user.getAllArticles();
        mv.addObject("articles", user.getAllArticles());
        mv.setViewName("user/user.html");
        return mv;
    }

    @GetMapping("/{userId}/teacher")
    public ModelAndView teacherAccount(@PathVariable("userId") String userId) {
        ModelAndView mv = new ModelAndView();
        // ONLY FOR THAT TEACHER !!! (service)
        AppUser principal = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mv.addObject("user", principal);
        mv.addObject("role", String.valueOf(principal.getUserRole()));
        mv.addObject("articles", articleRepository.findAll());
        mv.setViewName("user/teacher.html");
        return mv;
    }

}


 */