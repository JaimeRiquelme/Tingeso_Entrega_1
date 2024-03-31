package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateRepairsRepository extends JpaRepository<GenerateRepairsEntity, Long>{

}
