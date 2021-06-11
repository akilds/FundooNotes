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

	Response updateNote(int noteId, @Valid NoteDTO noteDTO);

	Response deleteNote(int noteId);

	List<NoteData> getAllNotesInTrash(String userToken);

	List<NoteData> getAllNotesInArchieve(String userToken);

	Response updateNoteToArchieved(int noteId);

	Response updateNoteToPinned(int noteId);

	List<ColabData> getAllCollaborators(int colabId);

	Response addCollaboratorToNote(int noteId, String emailId);

	Response removeCollaboratorFromNote(int colabId);

}
