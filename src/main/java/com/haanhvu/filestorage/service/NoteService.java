package com.haanhvu.filestorage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.haanhvu.filestorage.mapper.NoteMapper;
import com.haanhvu.filestorage.model.Note;

@Service
public class NoteService {
	
	private NoteMapper noteMapper;
	private UserService userService;
	
	public NoteService(NoteMapper noteMapper, UserService userService) {
		this.noteMapper = noteMapper;
		this.userService = userService;
	}
	
	public void addNote(Note note, String userName) {
        note.setUserId(userService.getUserByName(userName).getuserId());
        noteMapper.insertNote(note);
	}
	
	public void updateNote(Note note, String username) {
		note.setUserId(userService.getUserByName(username).getuserId());
		noteMapper.updateNote(note);
	}
	
	public void deleteNote(Note note) {
		noteMapper.deleteNoteByNoteId(note.getNoteId());
	}
	
	public List<Note> getNoteListByUsername(String username) {
		return noteMapper.getNoteListByUserId(userService.getUserByName(username).getuserId());
	}

}
