package com.haanhvu.filestorage.model;

public class NoteForm {
	
	private int noteId;
	private String title;
	private String description;
	
	public int getNoteId() {
		return noteId;
	}
	
	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

}