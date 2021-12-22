package com.haanhvu.filestorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haanhvu.filestorage.model.Credential;
import com.haanhvu.filestorage.service.CredentialService;
import com.haanhvu.filestorage.service.EncryptionService;
import com.haanhvu.filestorage.service.UserService;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
	
	private UserService userService;
	private CredentialService credentialService;
	private EncryptionService encryptionService;
	
	public CredentialController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
		this.userService = userService;
		this.credentialService = credentialService;
		this.encryptionService = encryptionService;
	}
	
	@PostMapping()
	public String addCredential(
			@ModelAttribute Credential credential,
			Authentication authentication,
			RedirectAttributes redirectAttributes,
			Model model
			) {	
		try {
			this.credentialService.addCredential(credential, authentication.getName());
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", "Credential added");
		}catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }
		return "redirect:/home";
	}
	
	@PostMapping("/update")
	public String updateCredential(
			@ModelAttribute Credential credential,
			RedirectAttributes redirectAttributes,
			Authentication authentication) {
		try {
			this.credentialService.updateCredential(credential, authentication.getName());
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", "Credential updated");
		}catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }
		return "redirect:/home";
	}
	
	@GetMapping("/delete")
	public String deleteCredential(
			@ModelAttribute Credential credential,
			Authentication authentication,
			RedirectAttributes redirectAttributes
			) {
		try {
			credentialService.deleteCredential(credential);		
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", "Credential deleted");
		}catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }
		return "redirect:/home";
	}
	
}