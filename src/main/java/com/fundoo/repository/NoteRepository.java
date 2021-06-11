package com.fundoo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundoo.Model.NoteData;

public interface NoteRepository extends JpaRepository<NoteData, Integer>{

	Optional<NoteData> findByUserId(int userId);

}
