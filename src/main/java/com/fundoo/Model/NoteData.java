package com.fundoo.Model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundoo.DTO.NoteDTO;

import lombok.Data;

@Entity
@Table(name = "fundoo_note")
public @Data class NoteData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "note_id")
	private int noteId;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="NoteDescription")
	private String description;
	
	@Column(name="userId")
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
	
//	@ManyToOne
//	@JoinColumn(name = "label_nid", referencedColumnName = "label_id")
//	private LabelData labelNId;
	
	@Column(name="emailid")
	private String emailId;
	
	@Column(name="color")
	private String color;
	
	@Column(name="reminder")
	private LocalDateTime remindertime;
	
	@JsonIgnore()
	@ManyToMany(cascade = CascadeType.ALL)
	private List<LabelData> labelList;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<ColabData> collaborator;
	
	public NoteData() {}
	
	public NoteData(int id) {
		super();
		this.noteId = id;
	}
	
	public void updateNote(NoteDTO noteDTO) {
		this.title = noteDTO.title;
		this.description = noteDTO.description;
		this.updateDate = LocalDateTime.now();
		this.emailId = noteDTO.emailId;
		this.color = noteDTO.color;
		this.remindertime = noteDTO.remindertime;
	}
}
