package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.SurchargeSeniorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SurchargeSeniorityRepository extends JpaRepository<SurchargeSeniorityEntity, Long> {

    @Query(value = "SELECT t FROM SurchargeSeniorityEntity t WHERE :Antiguedad BETWEEN t.years_min AND t.years_max")
    SurchargeSeniorityEntity findByAntiguedad(int Antiguedad);
}
