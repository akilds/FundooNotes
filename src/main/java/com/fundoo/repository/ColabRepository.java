package com.fundoo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundoo.Model.ColabData;

public interface ColabRepository extends JpaRepository<ColabData, Integer>{

	Optional<ColabData> findByEmailId(String emailId);

}
