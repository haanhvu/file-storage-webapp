package com.haanhvu.filestorage.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.haanhvu.filestorage.mapper.FileMapper;
import com.haanhvu.filestorage.model.File;
import com.haanhvu.filestorage.model.User;

@Service
public class FileService {
	
	private FileMapper fileMapper;
	private UserService userService;
	
	public FileService(FileMapper fileMapper, UserService userService) {
		this.fileMapper = fileMapper;
		this.userService = userService;
	}
	
	public int addFile(MultipartFile multipartFile, String username) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = userService.getUserByName(username).getuserId();
        File file = new File(null, fileName, contentType, fileSize, userId, multipartFile.getBytes());
		return fileMapper.insertFile(file);
	}

	public void deleteFile(File file) {
		fileMapper.deleteFileById(file.getFileId());
	}
	
	public File getFileByFileNameAndUsername(String fileName, String username) {
		User user = userService.getUserByName(username);
		return fileMapper.getFileByFileNameAndUserId(fileName, user.getuserId());
	}
		
	public List<File> getFileListByUsername(String username) {
		Integer userId = userService.getUserByName(username).getuserId();
		return fileMapper.getFileListByUserId(userId);
	}
	
}
