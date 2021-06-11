package com.fundoo.Service;

import java.util.List;

import javax.validation.Valid;

import com.fundoo.DTO.LabelDTO;
import com.fundoo.Model.LabelData;
import com.fundoo.util.Response;

public interface ILabelService {

	List<LabelData> getAllLabels(String userToken);

	Response addLabel(String userToken, @Valid LabelDTO labelDTO);

	Response updateLabel(int labelId, @Valid LabelDTO labelDTO);

	Response deleteLabel(int labelId);

	Response updateNoteAsLabel(int labelId, int noteId);

	Response removeNoteAsLabel(int labelId, int noteId);

}
