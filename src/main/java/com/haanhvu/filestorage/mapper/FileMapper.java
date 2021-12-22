package com.haanhvu.filestorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.haanhvu.filestorage.model.File;

@Mapper
public interface FileMapper {
	
	@Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
	File getFileById(Integer fileId);
	
	@Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    File getFileByFileNameAndUserId(String fileName, Integer userId);
	
	@Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFileListByUserId(Integer userId);
    
    @Insert("INSERT INTO FILES (filename, filesize, contenttype, filedata, userid) VALUES (#{fileName}, #{fileSize}, #{contentType}, #{fileData}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

	@Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
	void deleteFileById(int fileId);

}
