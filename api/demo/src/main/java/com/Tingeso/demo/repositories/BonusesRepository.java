package com.Tingeso.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Tingeso.demo.entities.BonusesEntity;

@Repository
public interface BonusesRepository extends JpaRepository<BonusesEntity, Long>{

    BonusesEntity findByMarca(String marca);
}
