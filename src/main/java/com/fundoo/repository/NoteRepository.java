package com.fundoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundoo.Model.NoteData;

public interface NoteRepository extends JpaRepository<NoteData, Integer>{

}
