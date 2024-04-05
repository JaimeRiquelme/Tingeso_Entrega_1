package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.SurchargeMileageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurchargeMileageRepository extends JpaRepository<SurchargeMileageEntity, Long> {
}
