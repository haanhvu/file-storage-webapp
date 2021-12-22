package com.haanhvu.filestorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.haanhvu.filestorage.model.Credential;

@Mapper
public interface CredentialMapper {
	
   @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
   Credential getCredentialById(int credentialId);
   
   @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
           "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
   @Options(useGeneratedKeys = true, keyProperty = "credentialId")
   int insertCredential(Credential credential);
   
   @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
   void deleteCredentialById(int credentialId);
   
   @Update("UPDATE CREDENTIALS SET url = #{url}, key=#{key}, password = #{password}, username = #{username} WHERE credentialid = #{credentialId}")
   void updateCredential(Credential credential);
   
   @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
   List<Credential> getCredentialListByUserId(int userid);
   
}
