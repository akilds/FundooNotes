package com.fundoo.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Returns all note present
	@Override
	public List<NoteData> getAllNotes(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
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
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Get All Notes In Trash");
			List<NoteData> note = noteRepository.findAll();
			return note.stream().filter(not -> not.isTrash()==true).collect(Collectors.toList());
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}

	//Returns all notes in archieve
	@Override
	public List<NoteData> getAllNotesInArchieve(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Get All Notes In Archieve");
			List<NoteData> note = noteRepository.findAll();
			return note.stream().filter(not -> not.isArchieve()==true).collect(Collectors.toList());
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}
	
	//Returns all collaborator present
	@Override
	public List<ColabData> getAllCollaborators(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
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
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Add Note : " + noteDTO);
			NoteData note = modelmapper.map(noteDTO, NoteData.class);
			note.setUserId(userId);
			noteRepository.save(note);
			return new Response(200, "Note Added Successfully", note.getNoteId());	
		}else {
			log.error("User Token Is Invalid");
			throw new NoteException(400, "User Token Is Invalid");
		}
	}
	
	//Adds a new Collaborator
	@Override
	public Response addCollaboratorToNote(String token,int noteId, String emailId) {
		Optional<NoteData> note = noteRepository.findById(noteId);
		int userId = tokenUtil.decodeToken(token);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			List<ColabData> colabb = colabRepository.findAll();
			boolean isNoteIdPresent = colabb.stream()
									  .anyMatch(col -> (col.getEmailId()==emailId && col.getNoteId()==noteId));
			if(isNoteIdPresent) {
				log.error("Colab Is Already Present");
				throw new NoteException(400, "Colab Is Already Present");
			}else {
				log.info("Collaborator Added ");
				ColabData colab = new ColabData();
				colab.setEmailId(emailId);
				colab.setNoteId(noteId);
				colabRepository.save(colab);
				NoteData not = new NoteData();
				not.setTitle(note.get().getTitle());
				not.setDescription(note.get().getDescription());
				not.setEmailId(emailId);
				noteRepository.save(note.get());
				return new Response(200, "Collaborator Added Successfully", colab.getColabId());
			}
		}else {
			log.error("Colab Cannot Be Added");
			throw new NoteException(400, "Colab Cannot Be Added");
		}
	}
	
	//Updates an existing note
	@Override
	public Response updateNote(int noteId, NoteDTO noteDTO, String userToken) {		
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
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
	public Response updateNoteToArchieved(int noteId, String userToken) {
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Archieve Status Changed");
			isPresent.get().setArchieve(! isPresent.get().isArchieve());
			return new Response(200, "Archieve Status Changed Successfully", noteId);
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}

	//Updates an existing note to pin
	@Override
	public Response updateNoteToPinned(int noteId, String userToken) {
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Pinned Status Changed");
			isPresent.get().setPin(! isPresent.get().isPin());
			return new Response(200, "Pinned Status Changed Successfully", noteId);
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}
	
	@Override
	public Response updateNoteToTrash(int noteId, String userToken) {
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Trash Status Changed");
			isPresent.get().setTrash(! isPresent.get().isTrash());
			return new Response(200, "Trash Status Changed Successfully", noteId);
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}	
	}
	//Removes an existing Collaborator
	@Override
	public Response removeCollaboratorFromNote(int colabId, String userToken) {
		Optional<ColabData> isPresent = colabRepository.findById(colabId);
		Optional<NoteData> note = noteRepository.findById(isPresent.get().getNoteId());
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
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
	public Response deleteNote(int noteId, String userToken) {
		Optional<NoteData> isPresent = noteRepository.findById(noteId);
		int userId = tokenUtil.decodeToken(userToken);
		String uri = "http://fundooUser/userregistration/verifyuserid/" + userId;
		boolean isIdPresent = restTemplate.getForObject(uri, Boolean.class);
		if(isIdPresent) {
			log.info("Note Moved To Trash");
			noteRepository.delete(isPresent.get());
			return new Response(200, "Note Removed From Trash Successfully", noteId);
		}else {
			log.error("Note Is Not Present");
			throw new NoteException(400, "Note Is Not Present");
		}
	}
}
