package com.fundoo.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "colab_data")
public @Data class ColabData {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int colabId;
	
	@Column(name = "emailId")
	private String emailId;
	
	@Column(name="noteId")
	private int noteId;
}
