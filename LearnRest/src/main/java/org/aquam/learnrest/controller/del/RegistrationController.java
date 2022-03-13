package org.aquam.learnrest.controller.del;
/*
import org.aquamara.tolearn.model.AppUser;
import org.aquamara.tolearn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/learn/register")
public class RegistrationController {

    public final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ModelAndView getRegisterViewTeacher() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", new AppUser());
        mv.setViewName("forms/register.html");
        return mv;
    }

    @PostMapping("")
    public ModelAndView registerTeacher(@Valid AppUser user, BindingResult bindingResult, @ModelAttribute("passwordCheck")String passwordCheck) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("forms/register.html");
        if (!userService.userExists(user)) {
            if (bindingResult.hasErrors())
                mv.addObject("bindingResult", bindingResult);
            if (!user.getPassword().equals(passwordCheck))
                mv.addObject("passwordMessage", "Must be same!");
            if (!bindingResult.hasErrors() && user.getPassword().equals(passwordCheck)) {
                mv.addObject("message", "Registered!");
                // user.setUserRole(UserRole.TEACHER);
                userService.registerUser(user);
            }
        } else {
            mv.addObject("usernameMessage", "Username already taken!");
        }

        mv.addObject("user", new AppUser());
        return mv;
    }
}

/*
ModelAndView mv = new ModelAndView();
        mv.setViewName("base_forms/register.html");
        if (bindingResult.hasErrors()) {
            mv.addObject("bindingResult", bindingResult);
            if (!user.getPassword().equals(passwordCheck))
                mv.addObject("passwordMessage", "Must be same!");
        } else if (userService.userExists(user)) {
            mv.addObject("message", "Username already taken!");
            if (!user.getPassword().equals(passwordCheck))
                mv.addObject("passwordMessage", "Must be same!");
        }
        else {
            mv.addObject("message", "You've been successfully registered");
            // ***************************************************************************************
            user.setUserRole(UserRole.TEACHER);
            userService.registerUser(user);
        }
        mv.addObject("user", new AppUser());
        return mv;
 */
