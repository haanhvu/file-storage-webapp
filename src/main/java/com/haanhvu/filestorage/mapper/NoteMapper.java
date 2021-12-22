package com.haanhvu.filestorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.haanhvu.filestorage.model.Note;

@Mapper
public interface NoteMapper {
	
	@Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
	Note getNoteById(int noteId);
	
	@Select("SELECT * FROM NOTES WHERE userid = #{userId}")
	List<Note> getNoteListByUserId(int userId);
	
	@Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int insertNote(Note note);
	
	@Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
	void deleteNoteByNoteId(int noteId);
	
	@Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
	void updateNote(Note note);
	
}
