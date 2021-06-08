package com.fundoo.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoo.DTO.NoteDTO;
import com.fundoo.Model.NoteData;
import com.fundoo.exception.NoteException;
import com.fundoo.repository.NoteRepository;
import com.fundoo.util.Response;
import com.fundoo.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoteService implements INoteService{

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private TokenUtil tokenUtil;

	List<NoteData> trash;
	
	List<NoteData> archieve;
	
	//Returns all note present
	@Override
	public List<NoteData> getAllNotes(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<NoteData> isPresent = noteRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Get All Notes");
			List<NoteData> getAllNotes = noteRepository.findAll();
			return getAllNotes;
		}else {
			log.error("Note Token Is Not valid");
			throw new NoteException(400, "Note Token Is Not Valid");
		}	
	}

	//Returns all notes in trash
	@Override
	public List<NoteData> getAllNotesInTrash(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<NoteData> isPresent = noteRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Get All Notes In Trash");
			return trash;
		}else {
			log.error("Note Token Is Not valid");
			throw new NoteException(400, "Note Token Is Not Valid");
		}	
	}

	//Returns all notes in archieve
	@Override
	public List<NoteData> getAllNotesInArchieve(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<NoteData> isPresent = noteRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Get All Notes In Archieve");
			return archieve;
		}else {
			log.error("Note Token Is Not valid");
			throw new NoteException(400, "Note Token Is Not Valid");
		}	
	}
	
	//Adds a new note
	@Override
	public Response addNote(NoteDTO noteDTO) {
		log.info("Add Note : " + noteDTO);
		NoteData note = modelmapper.map(noteDTO, NoteData.class);
		noteRepository.save(note);
		String token = tokenUtil.createToken(note.getUserId());
		return new Response(200, "Note Added Successfully", token);		
	}

	//Updates an existing note
	@Override
	public Response updateNote(String token, NoteDTO noteDTO) {		
		int id = tokenUtil.decodeToken(token);
		Optional<NoteData> isPresent = noteRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Update Note : " + noteDTO);
			isPresent.get().updateNote(noteDTO);
			noteRepository.save(isPresent.get());
			return new Response(200, "Note Updated Successfully", token);
		}else {
			log.error("Note Doesnt Exist");
			throw new NoteException(400, "Note Doesnt Exist");
		}	
	}

	//Updates an existing note to archieve
	@Override
	public Response updateNoteToArchieved(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<NoteData> isPresent = noteRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Note Is Archieved");
			isPresent.get().setArchieve(true);
			return new Response(200, "Note Archieved Successfully", token);
		}else {
			log.error("Note Token Is Not valid");
			throw new NoteException(400, "Note Token Is Not Valid");
		}	
	}

	@Override
	public Response updateNoteToPinned(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<NoteData> isPresent = noteRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Note is Pinned");
			isPresent.get().setPin(true);
			return new Response(200, "Note Pinned Successfully", token);
		}else {
			log.error("Note Token Is Not valid");
			throw new NoteException(400, "Note Token Is Not Valid");
		}	
	}

	//Deletes an existing user data
	@Override
	public Response deleteNote(String token) {
		int id = tokenUtil.decodeToken(token);
		Optional<NoteData> isPresent = noteRepository.findById(id);
		if(isPresent.isPresent()) {
			log.info("Note Moved To Trash");
			isPresent.get().setTrash(true);
			trash.add(isPresent.get());
			noteRepository.delete(isPresent.get());
			return new Response(200, "Note Moved To Trash Successfully", token);
		}else {
			log.error("Note Token Is Not Valid");
			throw new NoteException(400, "Note Token Is Not Valid");
		}
	}
}
