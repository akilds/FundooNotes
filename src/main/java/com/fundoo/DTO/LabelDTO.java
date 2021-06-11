package com.fundoo.DTO;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
public @ToString class LabelDTO {

	@NotEmpty(message = "Label Name cannot be Empty")
	public String labelName;
}
