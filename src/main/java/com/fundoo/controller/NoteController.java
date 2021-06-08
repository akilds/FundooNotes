package com.fundoo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundoo.DTO.NoteDTO;
import com.fundoo.Model.NoteData;
import com.fundoo.Service.INoteService;
import com.fundoo.util.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("fundoonote")
@Slf4j
public class NoteController {

	@Autowired
	private INoteService noteService;
	
	//Returns all the notes present
	@GetMapping("/getallnotes/{token}")
	public ResponseEntity<List<?>> getAllNotes(@PathVariable String token) {
		log.info("Get All Notes");
		List<NoteData> response = noteService.getAllNotes(token);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	//Returns all the notes in trash
	@GetMapping("/getallnotesbytrash/{token}")
	public ResponseEntity<List<?>> getAllNotesByTrash(@PathVariable String token) {
		log.info("Get All Notes In Trash");
		List<NoteData> response = noteService.getAllNotesInTrash(token);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	//Returns all the notes in archieve
	@GetMapping("/getallnotesbyarchieve/{token}")
	public ResponseEntity<List<?>> getAllNotesByArchieve(@PathVariable String token) {
		log.info("Get All Notes In Archieve");
		List<NoteData> response = noteService.getAllNotesInArchieve(token);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);	
	}
	
	//Creates a new note 
	@PostMapping("/addnewnote")
	public ResponseEntity<Response> createUser(@Valid @RequestBody NoteDTO noteDTO) {
		log.info("Create Note : " + noteDTO);
		Response response  = noteService.addNote(noteDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Updates an existing note
	@PutMapping("/updatenote/{token}")
	public ResponseEntity<Response> updateUser(@PathVariable String token,
											   @Valid @RequestBody NoteDTO noteDTO) {
		log.info("Update Note : " + noteDTO);
		Response response  = noteService.updateNote(token, noteDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Updates an existing note to archieve
	@PutMapping("/archievenote/{token}")
	public ResponseEntity<Response> updateUserToArchieved(@PathVariable String token) {
		log.info("Note Archieved ");
		Response response  = noteService.updateNoteToArchieved(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//Updates an existing note to pin
	@PutMapping("/pinnote/{token}")
	public ResponseEntity<Response> updateUserToPinned(@PathVariable String token) {
		log.info("Note Pinned");
		Response response  = noteService.updateNoteToPinned(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Deletes an existing note
	@DeleteMapping("/deletenote")
	public ResponseEntity<Response> deleteUser(@RequestParam String token) {
		log.info("Note Moved To Trash");
		Response response  = noteService.deleteNote(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
