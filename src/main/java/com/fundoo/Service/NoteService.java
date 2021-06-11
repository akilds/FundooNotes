package com.fundoo.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoo.DTO.NoteDTO;
import com.fundoo.Model.ColabData;
import com.fundoo.Model.NoteData;
import com.fundoo.exception.NoteException;
import com.fundoo.repository.ColabRepository;
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
	private ColabRepository colabRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private TokenUtil tokenUtil;

	List<NoteData> trash;
	
	List<NoteData> archieve;
	
	//Returns all note present
	@Override
	public List<NoteData> getAllNotes(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		Optional<NoteData> isPresent = noteRepository.findByUserId(userId);
		if(isPresent.isPresent()) {
			log.info("Get All Notes");
			List<NoteData> getAllNotes = noteRepository.findAll();
			return getAllNotes;
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}

	//Returns all notes in trash
	@Override
	public List<NoteData> getAllNotesInTrash(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		Optional<NoteData> isPresent = noteRepository.findByUserId(userId);
		if(isPresent.isPresent()) {
			log.info("Get All Notes In Trash");
			return trash;
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}

	//Returns all notes in archieve
	@Override
	public List<NoteData> getAllNotesInArchieve(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		Optional<NoteData> isPresent = noteRepository.findByUserId(userId);
		if(isPresent.isPresent()) {
			log.info("Get All Notes In Archieve");
			return archieve;
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}
	
	//Returns all collaborator present
	@Override
	public List<ColabData> getAllCollaborators(int colabId) {
		Optional<ColabData> isPresent = colabRepository.findById(colabId);
		if(isPresent.isPresent()) {
			log.info("Get All Collaborators");
			List<ColabData> getAllColabs = colabRepository.findAll();
			return getAllColabs;
		}else {
			log.error("Colab Is Not Present");
			throw new NoteException(400, "Colab Is Not Present");
		}
	}
	
	//Adds a new note
	@Override
	public Response addNote(String userToken,NoteDTO noteDTO) {
		int userId = tokenUtil.decodeToken(userToken);
		log.info("Add Note : " + noteDTO);
		NoteData note = modelmapper.map(noteDTO, NoteData.class);
		note.setUserId(userId);
		noteRepository.save(note);
		return new Response(200, "Note Added Successfully", note.getNoteId());				
	}
	
	//Adds a new Collaborator
	@Override
	public Response addCollaboratorToNote(int noteId, String emailId) {
		Optional<NoteData> note = noteRepository.findById(noteId);
		log.info("Collaborator Added ");
		ColabData colab = new ColabData();
		colab.setEmailId(emailId);
		colab.setNoteId(noteId);
		note.get().getCollaborator().add(colab);
		colabRepository.save(colab);
		noteRepository.save(note.get());
		return new Response(200, "Collaborator Added Successfully", colab.getColabId());
	}
	
	//Updates an existing note
	@Override
	public Response updateNote(int noteId, NoteDTO noteDTO) {		
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		if(isPresent.isPresent()) {
			log.info("Update Note : " + noteDTO);
			isPresent.get().updateNote(noteDTO);
			noteRepository.save(isPresent.get());
			return new Response(200, "Note Updated Successfully", noteId);
		}else {
			log.error("Note Doesnt Exist");
			throw new NoteException(400, "Note Doesnt Exist");
		}	
	}

	//Updates an existing note to archieve
	@Override
	public Response updateNoteToArchieved(int noteId) {
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		if(isPresent.isPresent()) {
			if(isPresent.get().isArchieve()==false) {
				log.info("Note Is Archieved");
				isPresent.get().setArchieve(true);
				archieve.add(isPresent.get());
				noteRepository.save(isPresent.get());
				return new Response(200, "Note Archieved Successfully", noteId);
			}else {
				log.info("Note Is UnArchieved");
				isPresent.get().setArchieve(false);
				archieve.remove(isPresent.get());
				noteRepository.save(isPresent.get());
				return new Response(200, "Note UnArchieved Successfully", noteId);
			}
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}

	//Updates an existing note to pin
	@Override
	public Response updateNoteToPinned(int noteId) {
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		if(isPresent.isPresent()) {
			if(isPresent.get().isPin()==false) {
				log.info("Note is Pinned");
				isPresent.get().setPin(true);
				noteRepository.save(isPresent.get());
				return new Response(200, "Note Pinned Successfully", noteId);
			}else {
				log.info("Note is UnPinned");
				isPresent.get().setPin(false);
				noteRepository.save(isPresent.get());
				return new Response(200, "Note UnPinned Successfully", noteId);
			}
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}
	
	//Removes an existing Collaborator
	@Override
	public Response removeCollaboratorFromNote(int colabId) {
		Optional<ColabData> isPresent = colabRepository.findById(colabId);
		Optional<NoteData> note = noteRepository.findById(isPresent.get().getNoteId());
		if(isPresent.isPresent()) {
			log.info("Colab Data Deleted");
			note.get().getCollaborator().remove(isPresent.get());
			colabRepository.delete(isPresent.get());
			noteRepository.save(note.get());
			return new Response(200, "Colab Deleted Successfully", colabId);
		}else {
			log.error("Colab Is Not Present");
			throw new NoteException(400, "Colab Is Not Present");
		}
	}
	
	//Deletes an existing note data
	@Override
	public Response deleteNote(int noteId) {
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		if(isPresent.isPresent()) {
			if(isPresent.get().isTrash()==false) {
				log.info("Note Moved To Trash");
				isPresent.get().setTrash(true);
				trash.add(isPresent.get());
				noteRepository.save(isPresent.get());
				return new Response(200, "Note Moved To Trash Successfully", noteId);
			}else {
				log.info("Note Moved To Trash");
				trash.remove(isPresent.get());
				noteRepository.delete(isPresent.get());
				return new Response(200, "Note Removed From Trash Successfully", noteId);
			}
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}
	}
}
