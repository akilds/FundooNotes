package com.fundoo.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
public @ToString class NoteDTO {
	
	@NotEmpty(message = "Title cannot be Empty")
	public String title;
	
	@NotEmpty(message = "Description cannot be Empty")
	public String description;
	
	public String emailId;
	
	public String color;
	
	public LocalDateTime remindertime;
}
