package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.RepairsCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairsCountRepository extends JpaRepository<RepairsCountEntity, Long> {
}
