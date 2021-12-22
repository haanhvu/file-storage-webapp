package com.haanhvu.filestorage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.haanhvu.filestorage.service.CredentialService;
import com.haanhvu.filestorage.service.EncryptionService;
import com.haanhvu.filestorage.service.FileService;
import com.haanhvu.filestorage.service.NoteService;
import com.haanhvu.filestorage.service.UserService;

@Controller
//@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	private FileService fileService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private CredentialService credentialService;
	@Autowired
	private EncryptionService encryptionService;

	
	public HomeController(UserService userService, FileService fileService, NoteService noteService,
			CredentialService credentialService, EncryptionService encryptionService) {
		this.fileService = fileService;
		this.noteService = noteService;
		this.credentialService = credentialService;
		this.encryptionService = encryptionService;
	}
	
	@GetMapping("/home")
	public String getHomePage(
			Authentication authentication,
			Model model) {
		model.addAttribute("fileList", fileService.getFileListByUsername(authentication.getName()));
		model.addAttribute("noteList", noteService.getNoteListByUsername(authentication.getName()));
		model.addAttribute("credentialList", credentialService.getCredentialListByUsername(authentication.getName()));
		model.addAttribute("encryption", encryptionService);	
		return "home";
	}

    @GetMapping("/result")
    public String showResult() {
        return "result";
    }
    
}
