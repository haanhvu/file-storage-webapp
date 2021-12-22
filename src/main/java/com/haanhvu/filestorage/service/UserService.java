package com.haanhvu.filestorage.service;

import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;
import com.haanhvu.filestorage.mapper.UserMapper;
import com.haanhvu.filestorage.model.User;

@Service
public class UserService {

	private UserMapper userMapper;
	private HashService hashService;
	
	public UserService(UserMapper userMapper, HashService hashService) {
		this.hashService = hashService;
		this.userMapper = userMapper;
	}
	
	public int addUser(User user) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		String encodedSalt = Base64.getEncoder().encodeToString(salt);
		String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
		return userMapper.insertUser(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
	}
	
	public boolean isUsernameAvailable(String username) {
		return userMapper.getUserByName(username) == null;
	}
	
	public User getUserByName(String username) {
		return userMapper.getUserByName(username);
	}

}