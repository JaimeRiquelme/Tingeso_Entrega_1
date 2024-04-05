package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.DiscountRepairsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepairsRepository extends JpaRepository<DiscountRepairsEntity, Long> {
}
