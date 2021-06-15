package com.fundoo.Service;

import java.util.List;

import javax.validation.Valid;

import com.fundoo.DTO.NoteDTO;
import com.fundoo.Model.ColabData;
import com.fundoo.Model.NoteData;
import com.fundoo.util.Response;

public interface INoteService {

	List<NoteData> getAllNotes(String userToken);

	Response addNote(String userToken,@Valid NoteDTO noteDTO);

	Response updateNote(int noteId, @Valid NoteDTO noteDTO, String userToken);

	Response deleteNote(int noteId, String userToken);

	List<NoteData> getAllNotesInTrash(String userToken);

	List<NoteData> getAllNotesInArchieve(String userToken);

	Response updateNoteToArchieved(int noteId, String userToken);

	Response updateNoteToPinned(int noteId, String userToken);

	List<ColabData> getAllCollaborators(String userToken);

	Response addCollaboratorToNote(String token, int noteId, String emailId);
	
	Response removeCollaboratorFromNote(int colabId, String userToken);

	Response updateNoteToTrash(int noteId, String userToken);

	

}
