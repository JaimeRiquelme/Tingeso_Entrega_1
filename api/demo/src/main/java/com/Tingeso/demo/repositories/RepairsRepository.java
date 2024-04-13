package com.Tingeso.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Tingeso.demo.entities.RepairsEntity;

@Repository
public interface RepairsRepository extends JpaRepository<RepairsEntity, Long> {


}
