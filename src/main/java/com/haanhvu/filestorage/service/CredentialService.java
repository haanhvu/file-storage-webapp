package com.haanhvu.filestorage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haanhvu.filestorage.mapper.CredentialMapper;
import com.haanhvu.filestorage.model.Credential;

@Service
public class CredentialService {
	
	private CredentialMapper credentialMapper;
	private UserService userService;
	private EncryptionService encryptionService;
	
	public CredentialService(CredentialMapper credentialMapper, UserService userService, EncryptionService encryptionService) {
		this.credentialMapper = credentialMapper;
		this.userService = userService;
		this.encryptionService = encryptionService;
	}
	
	public void addCredential(Credential credential, String username) {
		String key = encryptionService.generateKey();
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), key);
		int userId = userService.getUserByName(username).getuserId();
		credential.setUserId(userId);
        credential.setPassword(encryptedPassword);
        credential.setKey(key);
        credentialMapper.insertCredential(credential);
	}
	
	public void updateCredential(Credential credential, String username) {
		credential.setUserId(userService.getUserByName(username).getuserId());
		String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        credentialMapper.updateCredential(credential);
	}
	
	public void deleteCredential(Credential credential) {
		credentialMapper.deleteCredentialById(credential.getCredentialId());
	}

	public List<Credential> getCredentialListByUsername(String username) {
		return credentialMapper.getCredentialListByUserId(userService.getUserByName(username).getuserId());
	}
	
}
