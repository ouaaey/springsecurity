package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public String getUserPage(Principal principal,
                              ModelMap model) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute(user);
        return "/user/user";
    }

    @GetMapping(value = "/")
    public String getLoginPage() {
        return "redirect:/login";
    }
}
