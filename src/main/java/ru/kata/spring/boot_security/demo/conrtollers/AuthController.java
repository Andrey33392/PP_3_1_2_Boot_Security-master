package ru.kata.spring.boot_security.demo.conrtollers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserRegisterService;
import ru.kata.spring.boot_security.demo.util.UserValidator;
//import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImpl;

import javax.validation.Valid;

@Controller
public class AuthController {

    private final UserRegisterService userRegisterService;
    private final UserValidator userValidator;

    public AuthController(UserRegisterService userRegisterService, UserValidator userValidator) {
        this.userRegisterService = userRegisterService;
        this.userValidator = userValidator;
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userFrom", new User());
        return "registration";
    }

//    @PostMapping("/registration")
//    public String addUser(@ModelAttribute("userFrom") @Valid User userFrom, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//        if (!userFrom.getPassword().equals(userFrom.getPasswordConfirm())) {
//            model.addAttribute("passwordError", "Password do not math");
//            return "registration";
//        }
//        if (!userRegisterService.saveUser(userFrom)) {
//            model.addAttribute("usernameError", "A user whit the same name already exists");
//            return "registration";
//        }
//        return "redirect:/";
//    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userFrom") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userRegisterService.saveUser(user);
        return "redirect:/login";
    }
}
