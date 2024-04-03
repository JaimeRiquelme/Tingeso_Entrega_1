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
        return (ArrayList<BonusesEntity>) bonusesRepository.findAll();
    }

    //metodo para guardar un bono en la BD
    public BonusesEntity saveBonus(BonusesEntity bonus){
        return bonusesRepository.save(bonus);
    }
}
