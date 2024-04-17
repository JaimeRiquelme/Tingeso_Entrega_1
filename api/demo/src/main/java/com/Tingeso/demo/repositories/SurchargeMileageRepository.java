package com.Tingeso.demo.repositories;

import com.Tingeso.demo.entities.SurchargeMileageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SurchargeMileageRepository extends JpaRepository<SurchargeMileageEntity, Long> {

    @Query(value = "SELECT t FROM SurchargeMileageEntity t WHERE :Kilometraje BETWEEN t.kilometraje_minimo AND t.kilometraje_maximo")
    SurchargeMileageEntity findByKilometraje(int Kilometraje);

}
