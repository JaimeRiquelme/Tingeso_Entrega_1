package com.Tingeso.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Tingeso.demo.entities.BonusesEntity;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BonusesRepository extends JpaRepository<BonusesEntity, Long>{

    public BonusesEntity findByMarca(String marca);

    public BonusesEntity findById(long id);

    public ArrayList<BonusesEntity> findAll();


}
