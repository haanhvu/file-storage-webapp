package com.haanhvu.filestorage.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haanhvu.filestorage.model.Note;
import com.haanhvu.filestorage.service.NoteService;
import com.haanhvu.filestorage.service.UserService;

@Controller
@RequestMapping("/notes")
public class NoteController {
	
	private NoteService noteService;
	private UserService userService;
	
	public NoteController(NoteService noteService, UserService userService) {
		this.noteService = noteService;
		this.userService = userService;
	}
	
	@PostMapping("/note")
	public String addNote(@ModelAttribute("note") Note note, Authentication authentication,
			RedirectAttributes redirectAttributes,
			Model model) {		
		try {
			noteService.addNote(note, authentication.getName());
		} catch(Exception e) {
			redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
            return "redirect:/home";
		}
		redirectAttributes.addFlashAttribute("success", true);
		redirectAttributes.addFlashAttribute("successMessage", "Note Added!");
		return "redirect:/home";
	}
	
	@PostMapping("/update")
    public String updateNote(
    		@ModelAttribute Note note, 
    		Authentication authentication,
    		RedirectAttributes redirectAttributes) {   
        try {
        	noteService.updateNote(note, authentication.getName());
		} catch(Exception e) {
			redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
            return "redirect:/home";
		}
        redirectAttributes.addFlashAttribute("success", true);
		redirectAttributes.addFlashAttribute("successMessage", "Note Updated!");
        return "redirect:/home";
    }
	
	@GetMapping("/delete")
	public String deleteNote(
			@ModelAttribute Note note,
			Authentication authentication,
			RedirectAttributes redirectAttributes) {
		try {
			noteService.deleteNote(note);		
		}catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error!" + e.getMessage());
        }	
		redirectAttributes.addFlashAttribute("success", true);
		redirectAttributes.addFlashAttribute("successMessage", "Note Deleted!");
		return "redirect:/home";
	}

}
