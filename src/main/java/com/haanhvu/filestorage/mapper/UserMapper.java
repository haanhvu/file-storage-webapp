package com.haanhvu.filestorage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.haanhvu.filestorage.model.User;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM USERS WHERE userid = #{userId}")
	User getUserById(Integer userId);
	
	@Select("SELECT * FROM USERS WHERE username = #{username}")
	User getUserByName(String username);
	
	@Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	int insertUser(User user);
	
}
