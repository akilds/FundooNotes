package com.fundoo.DTO;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
public @ToString class NoteDTO {
	
	@NotEmpty(message = "Title cannot be Empty")
	public String title;
	
	@NotEmpty(message = "Description cannot be Empty")
	public String description;
	
	@NotNull(message = "User Id cannot be Empty")
	public int userId;
	
	public boolean trash;
	
	public boolean isArchieve;
	
	public boolean pin;
	
	@NotNull(message = "label Id cannot be Empty")
	public int labelId;
	
	@NotEmpty(message = "Email Id cannot be Empty")
	public String emailid;
	
	@NotEmpty(message = "Color cannot be Empty")
	public String color;
	
	public LocalDateTime remindertime;
}
