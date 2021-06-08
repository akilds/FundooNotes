package com.fundoo.Service;

import java.util.List;

import javax.validation.Valid;

import com.fundoo.DTO.NoteDTO;
import com.fundoo.Model.NoteData;
import com.fundoo.util.Response;

public interface INoteService {

	List<NoteData> getAllNotes(String token);

	Response addNote(@Valid NoteDTO noteDTO);

	Response updateNote(String token, @Valid NoteDTO noteDTO);

	Response deleteNote(String token);

	List<NoteData> getAllNotesInTrash(String token);

	List<NoteData> getAllNotesInArchieve(String token);

	Response updateNoteToArchieved(String token);

	Response updateNoteToPinned(String token);

}
