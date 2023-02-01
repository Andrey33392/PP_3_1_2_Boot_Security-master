package ru.kata.spring.boot_security.demo.conrtollers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;
import ru.kata.spring.boot_security.demo.service.UsersService;

import java.security.Principal;

@Controller
public class UserController {

    private final UsersService usersService;


    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    //    @RequestMapping(value = "/user", method = RequestMethod.GET)
//    @ResponseBody
//    public String currentUserName(Principal principal) {
//        return principal.getName();
//    }
    @GetMapping("/user")
    public String showUserInfo(@CurrentSecurityContext(expression = "authentication.principal") User principal,
                               Model model) {
        model.addAttribute("user", principal);

        return "user";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", usersService.getById(id));
        return "user";
    }

    @GetMapping("/update/{id}")
    public String updateUserFrom(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", usersService.getById(id));
        return "update";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "update";
        usersService.updateUser(user);
        return "redirect:/user";
    }

}
