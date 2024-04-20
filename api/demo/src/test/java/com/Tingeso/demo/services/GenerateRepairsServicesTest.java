package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import com.Tingeso.demo.entities.RepairsEntity;
import com.Tingeso.demo.entities.SurchargeMileageEntity;
import com.Tingeso.demo.entities.VehiclesEntity;
import com.Tingeso.demo.repositories.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(GenerateRepairsServices.class)
public class GenerateRepairsServicesTest {

    @Autowired
    GenerateRepairsServices generateRepairsServices;

    @MockBean
    GenerateRepairsRepository generateRepairsRepository;

    @MockBean
    RepairsRepository repairsRepository;

    @MockBean
    VehiclesRepository vehiclesRepository;

    @MockBean
    SurchargeMileageRepository surchargeMileageRepository;

    @MockBean
    SurchargeSeniorityRepository surchargeSeniorityRepository;

    @MockBean
    BonusesRepository bonusesRepository;





    //public ArrayList<GenerateRepairsEntity> getGenerateRepairs(){
    //        return (ArrayList<GenerateRepairsEntity>) generateRepairsRepository.findAll();
    //    }

    @Test
    void whenGetGenerateRepairs_thenCorrect() {
        //Given
        GenerateRepairsEntity generateRepair1 = new GenerateRepairsEntity();
        GenerateRepairsEntity generateRepair2 = new GenerateRepairsEntity();
        GenerateRepairsEntity generateRepair3 = new GenerateRepairsEntity();

        List<GenerateRepairsEntity> generateRepairs = new ArrayList<>();
        generateRepairs.add(generateRepair1);
        generateRepairs.add(generateRepair2);
        generateRepairs.add(generateRepair3);


        //When
        when(generateRepairsRepository.findAll()).thenReturn(generateRepairs);
        List<GenerateRepairsEntity> generateRepairsList = generateRepairsServices.getGenerateRepairs();


        //Then

        assertThat(generateRepairsList.size()).isEqualTo(3);
        assertNotNull(generateRepairsList);
    }

    //private double calcularMontoReparaciones(VehiclesEntity vehicle, List<Long> idsReparaciones) {
    //        double montoReparaciones = 0.0;
    //        for (Long id : idsReparaciones) {
    //            RepairsEntity repair = repairsRepository.findById(id).orElse(null);
    //            if (repair != null) {
    //                switch (vehicle.getTipo_motor()) {
    //                    case "Diesel":
    //                        montoReparaciones += repair.getCost_diesel();
    //                        break;
    //                    case "Gasolina":
    //                        montoReparaciones += repair.getCost_gasoline();
    //                        break;
    //                    case "Híbrido":
    //                        montoReparaciones += repair.getCost_hybrid();
    //                        break;
    //                    case "Electrico":
    //                        montoReparaciones += repair.getCost_electric();
    //                        break;
    //                }
    //            }
    //        }
    //        return montoReparaciones;
    //    }

    @Test
    void whenCalculateRepairAmount_thenCorrect() {
        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setTipo_motor("Diesel");

        VehiclesEntity vehicle2 = new VehiclesEntity();
        vehicle2.setTipo_motor("Gasolina");

        VehiclesEntity vehicle3 = new VehiclesEntity();
        vehicle3.setTipo_motor("Híbrido");

        VehiclesEntity vehicle4 = new VehiclesEntity();
        vehicle4.setTipo_motor("Electrico");

        RepairsEntity repair = new RepairsEntity();
        repair.setId(1L);
        repair.setCost_diesel(1000);
        repair.setCost_gasoline(500);
        repair.setCost_hybrid(800);
        repair.setCost_electric(200);

        RepairsEntity repair2 = new RepairsEntity();
        repair2.setId(2L);
        repair2.setCost_diesel(2000);
        repair2.setCost_gasoline(1000);
        repair2.setCost_hybrid(1600);
        repair2.setCost_electric(400);

        RepairsEntity repair3 = new RepairsEntity();
        repair3.setId(3L);
        repair3.setCost_diesel(3000);
        repair3.setCost_gasoline(1500);
        repair3.setCost_hybrid(2400);
        repair3.setCost_electric(600);

        List<Long> idsReparaciones = new ArrayList<>();
        idsReparaciones.add(1L);
        idsReparaciones.add(2L);
        idsReparaciones.add(3L);

        when(repairsRepository.findById(1L)).thenReturn(Optional.of(repair));
        when(repairsRepository.findById(2L)).thenReturn(Optional.of(repair2));
        when(repairsRepository.findById(3L)).thenReturn(Optional.of(repair3));

        // Usar el bean autowired gestionado por Spring
        double montoReparaciones = generateRepairsServices.calcularMontoReparaciones(vehicle, idsReparaciones);
        double montoReparaciones2 = generateRepairsServices.calcularMontoReparaciones(vehicle2, idsReparaciones);
        double montoReparaciones3 = generateRepairsServices.calcularMontoReparaciones(vehicle3, idsReparaciones);
        double montoReparaciones4 = generateRepairsServices.calcularMontoReparaciones(vehicle4, idsReparaciones);

        assertThat(montoReparaciones).isEqualTo(6000);
        assertThat(montoReparaciones2).isEqualTo(3000);
        assertThat(montoReparaciones3).isEqualTo(4800);
        assertThat(montoReparaciones4).isEqualTo(1200);
    }

    //private double calcularDescuentoPorHistorial(String tipoMotor, int numeroReparaciones) {
    //        double descuento = 0.0;
    //        if (numeroReparaciones > 0) {
    //            if (numeroReparaciones < 3) {
    //                descuento = (tipoMotor.equals("Diesel")) ? 0.07 : (tipoMotor.equals("Gasolina")) ? 0.05 : (tipoMotor.equals("Híbrido")) ? 0.10 : 0.08;
    //            } else if (numeroReparaciones < 6) {
    //                descuento = (tipoMotor.equals("Diesel")) ? 0.12 : (tipoMotor.equals("Gasolina")) ? 0.10 : (tipoMotor.equals("Híbrido")) ? 0.15 : 0.13;
    //            } else if (numeroReparaciones < 10) {
    //                descuento = (tipoMotor.equals("Diesel")) ? 0.17 : (tipoMotor.equals("Gasolina")) ? 0.15 : (tipoMotor.equals("Híbrido")) ? 0.20 : 0.18;
    //            } else {
    //                descuento = (tipoMotor.equals("Diesel")) ? 0.22 : (tipoMotor.equals("Gasolina")) ? 0.20 : (tipoMotor.equals("Híbrido")) ? 0.25 : 0.23;
    //            }
    //        }
    //        return descuento;
    //    }

    @Test
    void whenCalculateDiscountByHistory_thenCorrect() {
        String tipoMotor = "Diesel";
        int numeroReparaciones = 2;

        String tipoMotor2 = "Gasolina";
        int numeroReparaciones2 = 5;

        String tipoMotor3 = "Híbrido";
        int numeroReparaciones3 = 8;

        int numeroReparaciones4 = 11;

        double descuento = generateRepairsServices.calcularDescuentoPorHistorial(tipoMotor, numeroReparaciones);
        double descuento2 = generateRepairsServices.calcularDescuentoPorHistorial(tipoMotor2, numeroReparaciones2);
        double descuento3 = generateRepairsServices.calcularDescuentoPorHistorial(tipoMotor3, numeroReparaciones3);
        double descuento4 = generateRepairsServices.calcularDescuentoPorHistorial(tipoMotor3, numeroReparaciones4);


        assertThat(descuento).isEqualTo(0.07);
        assertThat(descuento2).isEqualTo(0.10);
        assertThat(descuento3).isEqualTo(0.20);
        assertThat(descuento4).isEqualTo(0.25);



    }


}
