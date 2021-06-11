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

import com.fundoo.DTO.LabelDTO;
import com.fundoo.Model.LabelData;
import com.fundoo.Service.ILabelService;
import com.fundoo.util.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/label")
@Slf4j
public class LabelController {

	@Autowired
	private ILabelService labelService;
	
	//Returns all the labels present
	@GetMapping("/getalllabels/{userToken}")
	public ResponseEntity<List<?>> getAllLabels(@PathVariable String userToken) {
		log.info("Get All Labels");
		List<LabelData> response = labelService.getAllLabels(userToken);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	//Creates a new label 
	@PostMapping("/addnewlabel")
	public ResponseEntity<Response> createLabel(@RequestParam String userToken,
												@Valid @RequestBody LabelDTO labelDTO) {
		log.info("Create Label : " + labelDTO);
		Response response = labelService.addLabel(userToken,labelDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Updates an existing label
	@PutMapping("/updatelabel/{labelId}")
	public ResponseEntity<Response> updateLabel(@PathVariable int labelId,
											    @Valid @RequestBody LabelDTO labelDTO) {
		log.info("Update Label : " + labelDTO);
		Response response  = labelService.updateLabel(labelId, labelDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	//Updates an existing note as label
	@PutMapping("/updatenoteaslabel/{labelId}/{noteId}")
	public ResponseEntity<Response> updateNoteAsLabel(@PathVariable int labelId,
													  @PathVariable int noteId) {
		log.info("Updated Note As Label");
		Response response  = labelService.updateNoteAsLabel(labelId, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
		
	//Removes an existing note as label
	@PutMapping("/removenoteaslabel/{labelId}/{noteId}")
	public ResponseEntity<Response> removeNoteAsLabel(@PathVariable int labelId,
													  @PathVariable int noteId) {
		log.info("Removed Note As Label");
		Response response  = labelService.removeNoteAsLabel(labelId, noteId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
		
	//Deletes an existing label
	@DeleteMapping("/deletelabel")
	public ResponseEntity<Response> deleteLabel(@RequestParam int labelId) {
		log.info("Label Deleted");
		Response response  = labelService.deleteLabel(labelId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
