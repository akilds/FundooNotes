package com.fundoo.Model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fundoo.DTO.NoteDTO;

import lombok.Data;

@Entity
@Table(name = "fundoo_note")
public @Data class NoteData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="Title")
	private String title;
	@Column(name="NoteDescription")
	private String description;
	@Column(name="UserId")
	private int userId;
	@Column(name = "registeredDate")
	private LocalDateTime registerDate;
	@Column(name = "UpdatedDate")
	private LocalDateTime updateDate;
	@Column(name="trash")
	private boolean trash = false;
	@Column(name="archieve")
	private boolean isArchieve = false;;
	@Column(name="pin")
	private boolean pin = false;
	@Column(name="labelid")
	private int labelId;
	@Column(name="emailid")
	private String emailid;
	@Column(name="color")
	private String color;
	@Column(name="reminder")
	private LocalDateTime remindertime;
	
	public NoteData() {}
	
	public NoteData(int id, String title, String description, int userId, LocalDateTime registerDate,
			LocalDateTime updateDate, boolean trash, boolean isArchieve, boolean pin, int labelId, String emailid,
			String color, LocalDateTime remindertime) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.userId = userId;
		this.registerDate = registerDate;
		this.updateDate = updateDate;
		this.trash = trash;
		this.isArchieve = isArchieve;
		this.pin = pin;
		this.labelId = labelId;
		this.emailid = emailid;
		this.color = color;
		this.remindertime = remindertime;
	}
	
	public void updateNote(NoteDTO noteDTO) {
		this.title = noteDTO.title;
		this.description = noteDTO.description;
		this.userId = noteDTO.userId;
		this.updateDate = LocalDateTime.now();
		this.trash = noteDTO.trash;
		this.isArchieve = noteDTO.isArchieve;
		this.pin = noteDTO.pin;
		this.labelId = noteDTO.labelId;
		this.emailid = noteDTO.emailid;
		this.color = noteDTO.color;
		this.remindertime = noteDTO.remindertime;
	}
}
