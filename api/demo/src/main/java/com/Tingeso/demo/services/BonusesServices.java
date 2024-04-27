package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.BonusesEntity;
import com.Tingeso.demo.repositories.BonusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BonusesServices {

    @Autowired
    BonusesRepository bonusesRepository;

    //Metodo para obtener todos los bonos
    public ArrayList<BonusesEntity> getBonuses(){
        return bonusesRepository.findAll();
    }

    //metodo para guardar un bono en la BD
    public BonusesEntity saveBonus(BonusesEntity bonus){
        return bonusesRepository.save(bonus);
    }

    public BonusesEntity updateBonus(BonusesEntity bonus, long id){
        BonusesEntity bonusUpdate = bonusesRepository.findById(id);
        bonusUpdate.setMarca(bonus.getMarca());
        bonusUpdate.setDisponibilidad(bonus.getDisponibilidad());
        bonusUpdate.setMonto(bonus.getMonto());
        return bonusesRepository.save(bonusUpdate);
    }

    public BonusesEntity getBonusById(long id){
        return bonusesRepository.findById(id);
    }
}
