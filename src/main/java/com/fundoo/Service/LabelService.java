package com.fundoo.Service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fundoo.DTO.LabelDTO;
import com.fundoo.Model.LabelData;
import com.fundoo.Model.NoteData;
import com.fundoo.exception.NoteException;
import com.fundoo.repository.LabelRepository;
import com.fundoo.repository.NoteRepository;
import com.fundoo.util.Response;
import com.fundoo.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LabelService implements ILabelService{

	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private TokenUtil tokenUtil;
	
	//Returns all the label present
	@Override
	public List<LabelData> getAllLabels(String userToken) {
		int userId = tokenUtil.decodeToken(userToken);
		Optional<LabelData> isPresent = labelRepository.findByUserId(userId);
		if(isPresent.isPresent()) {
			log.info("Get All Labels");
			List<LabelData> getAllLabels = labelRepository.findAll();
			return getAllLabels;
		}else {
			log.error("Label Is Not Present");
			throw new NoteException(400, "Label Is Not Present");
		}	
	}

	//Adds a new label
	@Override
	public Response addLabel(String userToken,@Valid LabelDTO labelDTO) {
		int userId = tokenUtil.decodeToken(userToken);
		if(userId!=0) {
			log.info("Add Label : " + labelDTO);
			LabelData label = modelmapper.map(labelDTO, LabelData.class);
			label.setUserId(userId);
			labelRepository.save(label);
			return new Response(200, "Label Added Successfully", label.getLabelId());
		}else {
			log.error("User Is Not Present");
			throw new NoteException(400, "User Is Not Present");
		}
	}

	//Updates an existing label
	@Override
	public Response updateLabel(int labelId, @Valid LabelDTO labelDTO) {
		Optional<LabelData> isPresent = labelRepository.findById(labelId);
		if(isPresent.isPresent()) {
			log.info("Update Label : " + labelDTO);
			isPresent.get().updateLabel(labelDTO);
			labelRepository.save(isPresent.get());
			return new Response(200, "Label Updated Successfully", labelId);
		}else {
			log.error("Label Doesnt Exist");
			throw new NoteException(400, "Label Doesnt Exist");
		}
	}
	
	//Updates an existing note as label
	@Override
	public Response updateNoteAsLabel(int labelId, int noteId) {
		Optional<LabelData> isLabelPresent = labelRepository.findById(labelId);
		if(isLabelPresent.isPresent()) {
			Optional<NoteData> isNotePresent = noteRepository.findById(noteId);
			if(isNotePresent.isPresent()) {
				log.info("Update Note as Label ");
				isLabelPresent.get().getNotes().add(isNotePresent.get()); 
				isNotePresent.get().getLabelList().add(isLabelPresent.get());
				labelRepository.save(isLabelPresent.get());
				noteRepository.save(isNotePresent.get());
				return new Response(200, "Note Updated As Label Successfully", noteId);
			}else {
				log.error("Note Doesnt Exist");
				throw new NoteException(400, "Note Doesnt Exist");
			}
		}else {
			log.error("Label Doesnt Exist");
			throw new NoteException(400, "Label Doesnt Exist");
		}
	}
	
	//Removes an existing note as label
	@Override
	public Response removeNoteAsLabel(int labelId, int noteId) {
		Optional<LabelData> isLabelPresent = labelRepository.findById(labelId);
		if(isLabelPresent.isPresent()) {
			Optional<NoteData> isNotePresent = noteRepository.findById(noteId);
			if(isNotePresent.isPresent()) {
				List<NoteData> notes = isLabelPresent.get().getNotes();
				int noteCheck = 0;
				for(NoteData n: notes) {
					if(n==isNotePresent.get()) {
						noteCheck = 1;
						break;
					}
				}
				if(noteCheck==1) {
					log.info("Removed Note From Label ");
					isLabelPresent.get().getNotes().remove(isNotePresent.get());
					isNotePresent.get().getLabelList().remove(isLabelPresent.get());
					labelRepository.save(isLabelPresent.get());
					noteRepository.save(isNotePresent.get());
					return new Response(200, "Note Removed From Label Successfully", noteId);
				}else {
					log.error("Note Doesnt Exist In Label");
					throw new NoteException(400, "Note Doesnt Exist In Label");
				}
			}else {
				log.error("Note Doesnt Exist");
				throw new NoteException(400, "Note Doesnt Exist");
			}
		}else {
			log.error("Label Doesnt Exist");
			throw new NoteException(400, "Label Doesnt Exist");
		}
	}
	
	//Deletes an existing label
	@Override
	public Response deleteLabel(int labelId) {
		Optional<LabelData> isPresent = labelRepository.findById(labelId);
		if(isPresent.isPresent()) {
			log.info("User Data Deleted");
			labelRepository.delete(isPresent.get());
			return new Response(200, "Label Deleted Successfully", labelId);
		}else {
			log.error("Label Token Is Not Valid");
			throw new NoteException(400, "Label Token Is Not Valid");
		}
	}
}
