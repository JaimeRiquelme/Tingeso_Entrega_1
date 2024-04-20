package com.Tingeso.demo.services;


import com.Tingeso.demo.entities.DiscountRepairsEntity;
import com.Tingeso.demo.repositories.DiscountRepairsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(DiscountRepairsServices.class)
public class DiscountRepairsServicesTest {

    @Autowired
    DiscountRepairsServices discountRepairsServices;

    @MockBean
    DiscountRepairsRepository discountRepairsRepository;

    //public DiscountRepairsEntity findById(int idDiscount){
    //        return discountRepairsRepository.findById(idDiscount);
    //    }

    @Test
    void whenFindById_thenCorrect() {
        //Given
        DiscountRepairsEntity discountRepairs = new DiscountRepairsEntity();
        discountRepairs.setId(1);
        discountRepairs.setDiesel(10);
        discountRepairs.setGasolina(10);
        discountRepairs.setHibrido(10);
        discountRepairs.setElectrico(10);
        discountRepairs.setReparaciones_maximas(100000);
        discountRepairs.setReparaciones_minimas(10000);

        //When
        when(discountRepairsRepository.findById(1)).thenReturn(discountRepairs);
        DiscountRepairsEntity discountRepairsEntity = discountRepairsServices.findById(1);

        //Then
        assertNotNull(discountRepairsEntity);
        assertThat(discountRepairsEntity.getId()).isEqualTo(1);
        assertThat(discountRepairsEntity.getDiesel()).isEqualTo(10);
        assertThat(discountRepairsEntity.getGasolina()).isEqualTo(10);
        assertThat(discountRepairsEntity.getHibrido()).isEqualTo(10);
        assertThat(discountRepairsEntity.getElectrico()).isEqualTo(10);
        assertThat(discountRepairsEntity.getReparaciones_maximas()).isEqualTo(100000);
        assertThat(discountRepairsEntity.getReparaciones_minimas()).isEqualTo(10000);

    }

    //public DiscountRepairsEntity saveDiscountRepairs(DiscountRepairsEntity discountRepairsEntity){
    //        return discountRepairsRepository.save(discountRepairsEntity);
    //    }

    @Test
    void whenSaveDiscountRepairs_thenCorrect() {
        //Given
        DiscountRepairsEntity discountRepairs = new DiscountRepairsEntity();
        discountRepairs.setId(1);
        discountRepairs.setDiesel(10);
        discountRepairs.setGasolina(10);
        discountRepairs.setHibrido(10);
        discountRepairs.setElectrico(10);
        discountRepairs.setReparaciones_maximas(100000);
        discountRepairs.setReparaciones_minimas(10000);

        //When
        when(discountRepairsRepository.save(discountRepairs)).thenReturn(discountRepairs);
        DiscountRepairsEntity discountRepairsEntity = discountRepairsServices.saveDiscountRepairs(discountRepairs);

        //Then
        assertNotNull(discountRepairsEntity);
        assertThat(discountRepairsEntity.getId()).isEqualTo(1);
        assertThat(discountRepairsEntity.getDiesel()).isEqualTo(10);
        assertThat(discountRepairsEntity.getGasolina()).isEqualTo(10);
        assertThat(discountRepairsEntity.getHibrido()).isEqualTo(10);
        assertThat(discountRepairsEntity.getElectrico()).isEqualTo(10);
        assertThat(discountRepairsEntity.getReparaciones_maximas()).isEqualTo(100000);
        assertThat(discountRepairsEntity.getReparaciones_minimas()).isEqualTo(10000);
    }
}
