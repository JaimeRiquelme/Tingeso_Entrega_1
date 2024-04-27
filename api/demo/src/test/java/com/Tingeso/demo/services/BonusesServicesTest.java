package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.BonusesEntity;
import com.Tingeso.demo.repositories.BonusesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(BonusesServices.class)
public class BonusesServicesTest {

    @Autowired
    BonusesServices bonusesServices;

    @MockBean
    BonusesRepository bonusesRepository;

    //public ArrayList<BonusesEntity> getBonuses(){
    //        return (ArrayList<BonusesEntity>) bonusesRepository.findAll();
    //    }

    @Test
    void whenGetBonuses_thenCorrect() {
        //Given
        BonusesEntity bonus1 = new BonusesEntity();
        BonusesEntity bonus2 = new BonusesEntity();
        BonusesEntity bonus3 = new BonusesEntity();
        ArrayList<BonusesEntity> bonuses = new ArrayList<>();
        bonuses.add(bonus1);
        bonuses.add(bonus2);
        bonuses.add(bonus3);

        //When
        when(bonusesRepository.findAll()).thenReturn(bonuses);
        List<BonusesEntity> bonusesList = bonusesServices.getBonuses();

        //Then
        assertNotNull(bonusesList);
        assertThat(bonusesList.size()).isEqualTo(3);
    }

    //  public BonusesEntity saveBonus(BonusesEntity bonus){
    //        return bonusesRepository.save(bonus);
    //    }

    @Test
    void whenSaveBonus_thenCorrect() {
        //Given
        BonusesEntity bonus = new BonusesEntity();
        bonus.setId(1);
        bonus.setMarca("Marca1");
        bonus.setDisponibilidad("Disponible");
        bonus.setMonto(10000);

        //When
        when(bonusesRepository.save(bonus)).thenReturn(bonus);
        BonusesEntity bonusSaved = bonusesServices.saveBonus(bonus);

        //Then
        assertNotNull(bonusSaved);
        assertThat(bonusSaved).isEqualTo(bonus);
    }

    //public BonusesEntity updateBonus(BonusesEntity bonus, long id){
    //        BonusesEntity bonusUpdate = bonusesRepository.findById(id).get();
    //        bonusUpdate.setMarca(bonus.getMarca());
    //        bonusUpdate.setDisponibilidad(bonus.getDisponibilidad());
    //        bonusUpdate.setMonto(bonus.getMonto());
    //        return bonusesRepository.save(bonusUpdate);
    //    }

    @Test
    void whenUpdateBonus_thenCorrect() {
        //Given
        BonusesEntity bonus = new BonusesEntity();
        bonus.setId(1);
        bonus.setMarca("Marca1");
        bonus.setDisponibilidad("Disponible");
        bonus.setMonto(10000);

        //When
        when(bonusesRepository.findById(1L)).thenReturn(bonus);
        when(bonusesRepository.save(bonus)).thenReturn(bonus);
        BonusesEntity bonusUpdated = bonusesServices.updateBonus(bonus, 1L);

        //Then
        assertNotNull(bonusUpdated);
        assertThat(bonusUpdated).isEqualTo(bonus);
    }

    //public BonusesEntity getBonusById(long id){
    //        return bonusesRepository.findById(id).get();
    //    }

    @Test
    void whenGetBonusById_thenCorrect(){
        //Given
        BonusesEntity bonus = new BonusesEntity();
        bonus.setId(1);
        bonus.setMarca("Marca1");
        bonus.setDisponibilidad("Disponible");
        bonus.setMonto(10000);

        //When
        when(bonusesRepository.findById(1L)).thenReturn(bonus);
        BonusesEntity bonusFound = bonusesServices.getBonusById(1L);

        //Then
        assertNotNull(bonusFound);
        assertThat(bonusFound).isEqualTo(bonus);
    }


}
