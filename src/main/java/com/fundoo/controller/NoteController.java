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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fundoo.DTO.NoteDTO;
import com.fundoo.Model.ColabData;
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
	@GetMapping("/getallnotes")
	public ResponseEntity<List<?>> getAllNotes(@RequestHeader String userToken) {
		log.info("Get All Notes");
		List<NoteData> response = noteService.getAllNotes(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	//Returns all the notes in trash
	@GetMapping("/getallnotesbytrash")
	public ResponseEntity<List<?>> getAllNotesByTrash(@RequestHeader String userToken) {
		log.info("Get All Notes In Trash");
		List<NoteData> response = noteService.getAllNotesInTrash(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	//Returns all the notes in archieve
	@GetMapping("/getallnotesbyarchieve")
	public ResponseEntity<List<?>> getAllNotesByArchieve(@RequestHeader String userToken) {
		log.info("Get All Notes In Archieve");
		List<NoteData> response = noteService.getAllNotesInArchieve(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);	
	}
	
	//Returns all the collaborators present
	@GetMapping("/getallcollaborators")
	public ResponseEntity<List<?>> getAllCollaborators(@RequestHeader String userToken) {
		log.info("Get All Notes");
		List<ColabData> response = noteService.getAllCollaborators(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
		
	//Creates a new note 
	@PostMapping("/addnewnote")
	public ResponseEntity<Response> createNote(@RequestHeader String userToken,
											   @Valid @RequestBody NoteDTO noteDTO) {
		log.info("Create Note : " + noteDTO);
		Response response = noteService.addNote(userToken,noteDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Adds a new Collaborator
	@PostMapping("/addcollaboratortonote")
	public ResponseEntity<Response> addCollaboratorToNote(@RequestHeader String userToken,
														  @RequestParam int noteId,
														  @RequestParam String emailId) {
		log.info("Collaborator Added To Note");
		Response response  = noteService.addCollaboratorToNote(userToken,noteId,emailId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
		
	//Updates an existing note
	@PutMapping("/updatenote/{noteId}")
	public ResponseEntity<Response> updateNote(@PathVariable int noteId,
											   @Valid @RequestBody NoteDTO noteDTO,
											   @RequestHeader String userToken) {
		log.info("Update Note : " + noteDTO);
		Response response  = noteService.updateNote(noteId, noteDTO,userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Updates an existing note to archieve
	@PutMapping("/archievenote/{noteId}")
	public ResponseEntity<Response> updateNoteToArchieved(@PathVariable int noteId,
														  @RequestHeader String userToken) {
		log.info("Note Archieved ");
		Response response  = noteService.updateNoteToArchieved(noteId,userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	//Updates an existing note to pin
	@PutMapping("/pinnote/{noteId}")
	public ResponseEntity<Response> updateNoteToPinned(@PathVariable int noteId,
													   @RequestHeader String userToken) {
		log.info("Note Pinned");
		Response response  = noteService.updateNoteToPinned(noteId,userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Updates an existing note to pin
	@PutMapping("/trashnote/{noteId}")
	public ResponseEntity<Response> updateNoteToTrash(@PathVariable int noteId,
													  @RequestHeader String userToken) {
		log.info("Note to Trash");
		Response response  = noteService.updateNoteToTrash(noteId,userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
		
	//Removes an existing collaborator
	@DeleteMapping("/removecollaboratorfromnote/{colabId}")
	public ResponseEntity<Response> removeCollaboratorFromNote(@PathVariable int colabId,
															   @RequestHeader String userToken) {
		log.info("Collaborator Removed From Note");
		Response response  = noteService.removeCollaboratorFromNote(colabId, userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
		
	//Deletes an existing note
	@DeleteMapping("/deletenote")
	public ResponseEntity<Response> deleteNote(@RequestParam int noteId,
											   @RequestHeader String userToken) {
		log.info("Note Moved To Trash");
		Response response  = noteService.deleteNote(noteId,userToken);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
