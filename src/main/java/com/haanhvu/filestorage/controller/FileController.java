package com.haanhvu.filestorage.controller;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.haanhvu.filestorage.model.File;
import com.haanhvu.filestorage.service.FileService;

@Controller
@RequestMapping("/files")
public class FileController {
	
	private FileService fileService;
	
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@PostMapping("/upload")
	public String uploadFile( MultipartFile fileUpload, Authentication authentication, Model model,
			RedirectAttributes redirectAttributes) {		
		if((fileUpload.getSize() > 5242880)) {
			model.addAttribute("error", "File size exceeds maximum");
			throw new MaxUploadSizeExceededException(fileUpload.getSize());
		}
		
		String uploadError = null;
		String fileName = fileUpload.getOriginalFilename();
		
		if(fileUpload.isEmpty()) {
			uploadError = "Empty file!";
			redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", uploadError);
		} else if(fileService.getFileByFileNameAndUsername(fileName, authentication.getName()) !=  null) {
			uploadError = "File already exists!";
			redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", uploadError);
		} else {
			try {
				fileService.addFile(fileUpload, authentication.getName());
			} catch(IOException e) {
				redirectAttributes.addFlashAttribute("ifError", true);
	            redirectAttributes.addFlashAttribute("errorMessage", "System error: " + e.getMessage());
			}
		}
		return "redirect:/home";
	}
	
	@GetMapping("/delete")
	public String deleteFile(@ModelAttribute File file, Authentication authentication, RedirectAttributes redirectAttributes) {
		try {
			fileService.deleteFile(file);
			redirectAttributes.addFlashAttribute("success", true);
			redirectAttributes.addFlashAttribute("successMessage", "File Deleted!");
		} catch (Exception e) {
            redirectAttributes.addFlashAttribute("ifError", true);
            redirectAttributes.addFlashAttribute("errorMessage", "System error: " + e.getMessage());
        }	
		return "redirect:/home";
	}
	
	@GetMapping("/download")
    public ResponseEntity<Resource> download(@ModelAttribute File file) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(httpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getFileName());
        httpHeaders.add("Cache-control", "no-cache, no-store, must-revalidate");
        httpHeaders.add("Pragma", "no-cache");
        httpHeaders.add("Expires", "0");
        ByteArrayResource resource = new ByteArrayResource(file.getFileData());
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(resource);
    }
	
}
