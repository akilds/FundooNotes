package com.fundoo.Model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fundoo.DTO.LabelDTO;

import lombok.Data;

@Entity
@Table(name = "label_data")
public @Data class LabelData {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private int labelId;

	@Column(name="labelName")
	private String labelName;

	@Column(name="userId")
	private int userId;

//	@ManyToMany
//	@JoinColumn(name = "note_lid", referencedColumnName = "note_id")
//	private NoteData noteLid;
	
	@Column(name = "registeredDate")
	private LocalDateTime registerDate = LocalDateTime.now(); 

	@Column(name = "UpdatedDate")
	private LocalDateTime updateDate;

	@ManyToMany(mappedBy = "labelList")
	private List<NoteData> notes;

	public LabelData() {}
	
	public LabelData(int id) {
		super();
		this.labelId = id;
	}
	public void updateLabel(LabelDTO labelDTO) {
		this.labelName = labelDTO.labelName;
		this.updateDate = LocalDateTime.now();
	}
}
