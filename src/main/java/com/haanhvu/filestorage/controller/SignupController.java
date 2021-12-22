package com.haanhvu.filestorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haanhvu.filestorage.model.User;
import com.haanhvu.filestorage.service.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	
	private UserService userService;
	
	public SignupController(UserService userService) {
        this.userService = userService;
	}
	
	@GetMapping()
	public String signupView() {
		return "signup";
	}
	
	@PostMapping()
	public String signup(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {
		String signupError = null;

		if (!userService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
            model.addAttribute("signupError", signupError);
            redirectAttributes.addFlashAttribute("errorMessage", signupError);
            return "signup";
        }

        if (signupError == null) {
            int something = userService.addUser(user);
            if (something < 0) {
                signupError = "There was an error signing you up. Please try again.";
                model.addAttribute("signupError", signupError);
                redirectAttributes.addFlashAttribute("errorMessage", signupError);
                return "signup";
            }
        }
        
        model.addAttribute("signupSuccess", true);
        redirectAttributes.addFlashAttribute("SuccessMessage", "Signup Successful!");
        return "redirect:login";
	}

}
