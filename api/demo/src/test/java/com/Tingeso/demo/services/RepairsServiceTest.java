package com.Tingeso.demo.services;


import com.Tingeso.demo.entities.RepairsEntity;
import com.Tingeso.demo.repositories.RepairsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;

@WebMvcTest(RepairsServices.class)
public class RepairsServiceTest {

    @Autowired
    RepairsServices repairsServices;

    @MockBean
    RepairsRepository repairsRepository;

    @Test
    void whenGetRepairs_thenCorrect() {
        //Given
        RepairsEntity repair1 = new RepairsEntity();
        RepairsEntity repair2 = new RepairsEntity();
        RepairsEntity repair3 = new RepairsEntity();
        List<RepairsEntity> repairs = new ArrayList<>();
        repairs.add(repair1);
        repairs.add(repair2);
        repairs.add(repair3);

        //When
        when(repairsRepository.findAll()).thenReturn(repairs);
        List<RepairsEntity> repairsList = repairsServices.getRepairs();

        //Then
        assertNotNull(repairsList);
        assertThat(repairsList.size()).isEqualTo(3);
    }

    @Test
    void whenSaveRepair_thenCorrect() {
        //Given
        RepairsEntity repair = new RepairsEntity();
        repair.setId(1);
        repair.setType("Mecanica");
        repair.setDescription("Cambio de aceite");
        repair.setCost_gasoline(10000);
        repair.setCost_diesel(15000);
        repair.setCost_hybrid(20000);
        repair.setCost_electric(25000);

        //When
        when(repairsRepository.save(repair)).thenReturn(repair);
        RepairsEntity savedRepair = repairsServices.saveRepair(repair);

        //Then
        assertNotNull(savedRepair);
        assertThat(savedRepair).isEqualTo(repair);
    }

    @Test
    void whenGetTypes_thenCorrect() {
        //Given
        String[] types = {"Mecanica", "Electrica", "Hibrida"};

        //When
        when(repairsRepository.getTypes()).thenReturn(types);
        String[] typesList = repairsServices.getTypes();

        //Then
        assertNotNull(typesList);
        assertThat(typesList.length).isEqualTo(3);
    }

    //test para public RepairsEntity getRepairById(Long id) {
    //        return repairsRepository.findById(id).orElse(null);
    //    }





}
