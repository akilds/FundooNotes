package com.fundoo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundoo.Model.LabelData;

public interface LabelRepository extends JpaRepository<LabelData, Integer>{

	Optional<LabelData> findByUserId(int userId);

}
