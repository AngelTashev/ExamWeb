package com.softuni.exam.web;

import com.softuni.exam.model.binding.UserLoginBindingModel;
import com.softuni.exam.model.binding.UserRegisterBindingModel;
import com.softuni.exam.model.service.UserServiceModel;
import com.softuni.exam.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    @Autowired
    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    // Login

    @GetMapping("/login")
    public String getLoginForm(Model model, HttpSession httpSession) {
        // Security
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("userLoginBindingModel")) {
            model.addAttribute("userLoginBindingModel", new UserLoginBindingModel());
            model.addAttribute("notFound", false);
        }

        return "login";
    }

    @PostMapping("/login")
    public String confirmLoginForm(@Valid @ModelAttribute("userLoginBindingModel")
                                   UserLoginBindingModel userLoginBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession httpSession) {
        // Security
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLoginBindingModel", bindingResult);
            return "redirect:login";
        }

        UserServiceModel user = this.userService.findByUsername(userLoginBindingModel.getUsername());
        if (user == null || !user.getPassword().equalsIgnoreCase(userLoginBindingModel.getPassword())) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            redirectAttributes.addFlashAttribute("notFound", true);
            return "redirect:login";
        }

        httpSession.setAttribute("user", user);
        return "redirect:/";
    }



    // Register

    @GetMapping("/register")
    public String getRegisterForm(Model model, HttpSession httpSession) {

        // Security
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "register";
    }

    @PostMapping("/register")
    public String confirmRegisterForm(@Valid @ModelAttribute("userRegisterBindingModel")
                                                  UserRegisterBindingModel userRegisterBindingModel,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes,
                                      HttpSession httpSession) {
        // Security
        if (httpSession.getAttribute("user") != null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors() ||
                !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword()) ||
                this.userService.findByUsername(userRegisterBindingModel.getUsername()) != null) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }


        this.userService.registerUser(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        return "redirect:login";
    }



    // Logout

    @GetMapping("/logout")
    public String logoutUser(HttpSession httpSession) {
        // Security (maybe not needed but it's in the description)
        if (httpSession.getAttribute("user") == null) {
            return "redirect:/";
        }
        httpSession.invalidate();
        return "redirect:/";
    }

}
