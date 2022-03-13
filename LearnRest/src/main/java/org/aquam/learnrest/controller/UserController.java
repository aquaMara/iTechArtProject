package org.aquam.learnrest.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.model.AppUser;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learn")
@RequiredArgsConstructor
public class UserController {

    // тут вообще всё другое, в аккаунте выводятся и все статьи
    private final UserServiceImpl userService;

    @GetMapping("/{userId}")    // @AuthenticationPrincipal works or not
    public ResponseEntity<AppUser> getUserAccount(@AuthenticationPrincipal @PathVariable("userId") Long userId) {
        //
        return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
    }

    /*
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
     */
}
