package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.SurchargeSeniorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurchargeSeniorityRepository extends JpaRepository<SurchargeSeniorityEntity, Long> {
}
