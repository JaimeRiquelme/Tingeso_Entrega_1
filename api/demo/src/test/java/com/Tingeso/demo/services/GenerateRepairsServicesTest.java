package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.*;
import com.Tingeso.demo.repositories.*;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;

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


    @Test
    void whenCalculateDiscountByHour_thenCorrect() {
        LocalDateTime fechaIngreso = LocalDateTime.of(2021, 5, 10, 0, 0);
        LocalTime horaIngreso = LocalTime.of(10, 0);

        LocalDateTime fechaIngreso2 = LocalDateTime.of(2021, 5, 13, 0, 0);
        LocalTime horaIngreso2 = LocalTime.of(10, 0);

        LocalDateTime fechaIngreso3 = LocalDateTime.of(2021, 5, 10, 0, 0);
        LocalTime horaIngreso3 = LocalTime.of(8, 0);

        LocalDateTime fechaIngreso4 = LocalDateTime.of(2021, 5, 13, 0, 0);
        LocalTime horaIngreso4 = LocalTime.of(13, 0);

        double descuento = generateRepairsServices.calcularDescuentoPorHora(fechaIngreso, horaIngreso);
        double descuento2 = generateRepairsServices.calcularDescuentoPorHora(fechaIngreso2, horaIngreso2);
        double descuento3 = generateRepairsServices.calcularDescuentoPorHora(fechaIngreso3, horaIngreso3);
        double descuento4 = generateRepairsServices.calcularDescuentoPorHora(fechaIngreso4, horaIngreso4);

        assertThat(descuento).isEqualTo(0.05);
        assertThat(descuento2).isEqualTo(0.05);
        assertThat(descuento3).isEqualTo(0.0);
        assertThat(descuento4).isEqualTo(0.0);
    }


    @Test
    void whenCalculateSurchargeMileage_thenCorrect() {
        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setKilometraje(10000);
        vehicle.setTipo("Sedan");

        VehiclesEntity vehicle2 = new VehiclesEntity();
        vehicle2.setKilometraje(20000);
        vehicle2.setTipo("Hatchback");

        VehiclesEntity vehicle3 = new VehiclesEntity();
        vehicle3.setKilometraje(30000);
        vehicle3.setTipo("SUV");

        VehiclesEntity vehicle4 = new VehiclesEntity();
        vehicle4.setKilometraje(40000);
        vehicle4.setTipo("Pickup");

        VehiclesEntity vehicle5 = new VehiclesEntity();
        vehicle5.setKilometraje(50000);
        vehicle5.setTipo("Furgoneta");

        VehiclesEntity vehicle6 = new VehiclesEntity();
        vehicle6.setKilometraje(50000);
        vehicle6.setTipo("Camioneta");

        SurchargeMileageEntity surchargeMileage = new SurchargeMileageEntity();
        surchargeMileage.setSedan(0.05F);
        surchargeMileage.setHatchback(0.10F);
        surchargeMileage.setSuv(0.15F);
        surchargeMileage.setPickup(0.20F);
        surchargeMileage.setFurgoneta(0.25F);

        when(surchargeMileageRepository.findByKilometraje(10000)).thenReturn(surchargeMileage);
        when(surchargeMileageRepository.findByKilometraje(20000)).thenReturn(surchargeMileage);
        when(surchargeMileageRepository.findByKilometraje(30000)).thenReturn(surchargeMileage);
        when(surchargeMileageRepository.findByKilometraje(40000)).thenReturn(surchargeMileage);
        when(surchargeMileageRepository.findByKilometraje(50000)).thenReturn(surchargeMileage);

        double recargoKilometraje = generateRepairsServices.calculoRecargoKilometraje(vehicle);
        double recargoKilometraje2 = generateRepairsServices.calculoRecargoKilometraje(vehicle2);
        double recargoKilometraje3 = generateRepairsServices.calculoRecargoKilometraje(vehicle3);
        double recargoKilometraje4 = generateRepairsServices.calculoRecargoKilometraje(vehicle4);
        double recargoKilometraje5 = generateRepairsServices.calculoRecargoKilometraje(vehicle5);
        double recargoKilometraje6 = generateRepairsServices.calculoRecargoKilometraje(vehicle6);

        assertThat(recargoKilometraje).isEqualTo(0.05F);
        assertThat(recargoKilometraje2).isEqualTo(0.10F);
        assertThat(recargoKilometraje3).isEqualTo(0.15F);
        assertThat(recargoKilometraje4).isEqualTo(0.20F);
        assertThat(recargoKilometraje5).isEqualTo(0.25F);

    }

    @Test
    void whenCalculateSurchargeSeniority_thenCorrect() {
        int currentYear = LocalDate.now().getYear();

        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setAnio_fabricacion("2010");
        vehicle.setTipo("Sedan");

        VehiclesEntity vehicle2 = new VehiclesEntity();
        vehicle2.setAnio_fabricacion("2015");
        vehicle2.setTipo("Hatchback");

        VehiclesEntity vehicle3 = new VehiclesEntity();
        vehicle3.setAnio_fabricacion("2017");
        vehicle3.setTipo("SUV");

        VehiclesEntity vehicle4 = new VehiclesEntity();
        vehicle4.setAnio_fabricacion("2019");
        vehicle4.setTipo("Pickup");

        VehiclesEntity vehicle5 = new VehiclesEntity();
        vehicle5.setAnio_fabricacion("2020");
        vehicle5.setTipo("Furgoneta");

        VehiclesEntity vehicle6 = new VehiclesEntity();
        vehicle6.setAnio_fabricacion("2020");
        vehicle6.setTipo("Camioneta");

        SurchargeSeniorityEntity surchargeSeniority = new SurchargeSeniorityEntity();
        surchargeSeniority.setId(1);
        surchargeSeniority.setYears_min(5);  // Example value
        surchargeSeniority.setYears_max(10); // Example value
        surchargeSeniority.setSedan(0.05F);
        surchargeSeniority.setHatchback(0.10F);
        surchargeSeniority.setSuv(0.15F);
        surchargeSeniority.setPickup(0.20F);
        surchargeSeniority.setFurgoneta(0.25F);

        when(surchargeSeniorityRepository.findByAntiguedad(currentYear - Integer.parseInt(vehicle.getAnio_fabricacion())))
                .thenReturn(surchargeSeniority);
        when(surchargeSeniorityRepository.findByAntiguedad(currentYear - Integer.parseInt(vehicle2.getAnio_fabricacion())))
                .thenReturn(surchargeSeniority);
        when(surchargeSeniorityRepository.findByAntiguedad(currentYear - Integer.parseInt(vehicle3.getAnio_fabricacion())))
                .thenReturn(surchargeSeniority);
        when(surchargeSeniorityRepository.findByAntiguedad(currentYear - Integer.parseInt(vehicle4.getAnio_fabricacion())))
                .thenReturn(surchargeSeniority);
        when(surchargeSeniorityRepository.findByAntiguedad(currentYear - Integer.parseInt(vehicle5.getAnio_fabricacion())))
                .thenReturn(surchargeSeniority);
        when(surchargeSeniorityRepository.findByAntiguedad(currentYear - Integer.parseInt(vehicle6.getAnio_fabricacion())))
                .thenReturn(surchargeSeniority);

        double recargoAntiguedad = generateRepairsServices.calculoRecargoAntiguedad(vehicle);
        double recargoAntiguedad2 = generateRepairsServices.calculoRecargoAntiguedad(vehicle2);
        double recargoAntiguedad3 = generateRepairsServices.calculoRecargoAntiguedad(vehicle3);
        double recargoAntiguedad4 = generateRepairsServices.calculoRecargoAntiguedad(vehicle4);
        double recargoAntiguedad5 = generateRepairsServices.calculoRecargoAntiguedad(vehicle5);
        double recargoAntiguedad6 = generateRepairsServices.calculoRecargoAntiguedad(vehicle6);

        assertEquals(0.05, recargoAntiguedad, 0.01);
        assertEquals(0.10, recargoAntiguedad2, 0.01);
        assertEquals(0.15, recargoAntiguedad3, 0.01);
        assertEquals(0.20, recargoAntiguedad4, 0.01);
        assertEquals(0.25, recargoAntiguedad5, 0.01);
        assertEquals(0.0, recargoAntiguedad6, 0.01);
    }


    @Test
    void whenCalculateDiscountByBonus_thenCorrect() {
        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setMarca("Toyota");

        VehiclesEntity vehicle2 = new VehiclesEntity();
        vehicle2.setMarca("Chevrolet");

        VehiclesEntity vehicle3 = new VehiclesEntity();
        vehicle3.setMarca("Ford");

        BonusesEntity bonus = new BonusesEntity();
        bonus.setId(1);
        bonus.setMarca("Toyota");
        bonus.setDisponibilidad("1");
        bonus.setMonto(1000);

        BonusesEntity bonus2 = new BonusesEntity();
        bonus2.setId(2);
        bonus2.setMarca("Chevrolet");
        bonus2.setDisponibilidad("0");
        bonus2.setMonto(2000);

        BonusesEntity bonus3 = new BonusesEntity();
        bonus3.setId(3);
        bonus3.setMarca("Ford");
        bonus3.setDisponibilidad("1");
        bonus3.setMonto(3000);

        when(bonusesRepository.findByMarca("Toyota")).thenReturn(bonus);
        when(bonusesRepository.findByMarca("Chevrolet")).thenReturn(bonus2);
        when(bonusesRepository.findByMarca("Ford")).thenReturn(bonus3);

        double descuentoBono = generateRepairsServices.obtenerDescuentoPorBono(vehicle);
        double descuentoBono2 = generateRepairsServices.obtenerDescuentoPorBono(vehicle2);
        double descuentoBono3 = generateRepairsServices.obtenerDescuentoPorBono(vehicle3);

        assertEquals(1000, descuentoBono, 0.01);
        assertEquals(0, descuentoBono2, 0.01);
        assertEquals(3000, descuentoBono3, 0.01);
    }


    //private double obtenerRecargoPorRetraso(LocalDateTime fechaSalidaReparacion, LocalDateTime fechaEntregaCliente){
    //        long DiasRetraso = ChronoUnit.DAYS.between(fechaSalidaReparacion, fechaEntregaCliente);
    //        return DiasRetraso * 0.05;
    //    }

    @Test
    void whenCalculateSurchargeByDelay_thenCorrect() {
        LocalDateTime fechaSalidaReparacion = LocalDateTime.of(2021, 5, 10, 0, 0);
        LocalDateTime fechaEntregaCliente = LocalDateTime.of(2021, 5, 15, 0, 0);

        LocalDateTime fechaSalidaReparacion2 = LocalDateTime.of(2021, 5, 10, 0, 0);
        LocalDateTime fechaEntregaCliente2 = LocalDateTime.of(2021, 5, 20, 0, 0);

        LocalDateTime fechaSalidaReparacion3 = LocalDateTime.of(2021, 5, 10, 0, 0);
        LocalDateTime fechaEntregaCliente3 = LocalDateTime.of(2021, 5, 25, 0, 0);

        double recargoRetraso = generateRepairsServices.obtenerRecargoPorRetraso(fechaSalidaReparacion, fechaEntregaCliente);
        double recargoRetraso2 = generateRepairsServices.obtenerRecargoPorRetraso(fechaSalidaReparacion2, fechaEntregaCliente2);
        double recargoRetraso3 = generateRepairsServices.obtenerRecargoPorRetraso(fechaSalidaReparacion3, fechaEntregaCliente3);

        assertEquals(0.25, recargoRetraso, 0.01);
        assertEquals(0.50, recargoRetraso2, 0.01);
        assertEquals(0.75, recargoRetraso3, 0.01);
    }

//private double calcularIVA(double subtotal) {
//        return subtotal * 0.19;
//    }

    @Test
    void whenCalculateIVA_thenCorrect() {
        double subtotal = 10000;
        double subtotal2 = 20000;
        double subtotal3 = 30000;

        double iva = generateRepairsServices.calcularIVA(subtotal);
        double iva2 = generateRepairsServices.calcularIVA(subtotal2);
        double iva3 = generateRepairsServices.calcularIVA(subtotal3);

        assertEquals(1900, iva, 0.01);
        assertEquals(3800, iva2, 0.01);
        assertEquals(5700, iva3, 0.01);
    }

    //public List<Object[]> getPromedioHorasPorMarca(){
    //        return generateRepairsRepository.getPromedioHorasPorMarca();
    //    }

    @Test
    void whenGetAverageHoursByBrand_thenCorrect() {
        List<Object[]> promedioHorasPorMarca = new ArrayList<>();
        Object[] promedio1 = new Object[2];
        promedio1[0] = "Toyota";
        promedio1[1] = 10.0;

        Object[] promedio2 = new Object[2];
        promedio2[0] = "Chevrolet";
        promedio2[1] = 15.0;

        Object[] promedio3 = new Object[2];
        promedio3[0] = "Ford";
        promedio3[1] = 20.0;

        promedioHorasPorMarca.add(promedio1);
        promedioHorasPorMarca.add(promedio2);
        promedioHorasPorMarca.add(promedio3);

        when(generateRepairsRepository.getPromedioHorasPorMarca()).thenReturn(promedioHorasPorMarca);

        List<Object[]> promedioHorasPorMarcaList = generateRepairsServices.getPromedioHorasPorMarca();

        assertEquals(3, promedioHorasPorMarcaList.size());
        assertEquals("Toyota", promedioHorasPorMarcaList.get(0)[0]);
        assertEquals(10.0, promedioHorasPorMarcaList.get(0)[1]);
        assertEquals("Chevrolet", promedioHorasPorMarcaList.get(1)[0]);
        assertEquals(15.0, promedioHorasPorMarcaList.get(1)[1]);
        assertEquals("Ford", promedioHorasPorMarcaList.get(2)[0]);
        assertEquals(20.0, promedioHorasPorMarcaList.get(2)[1]);
    }


    //public GenerateRepairsEntity getGenerateRepairsById(int idReparacion){
    //        return generateRepairsRepository.findById(idReparacion);
    //    }

    @Test
    void whenGetGenerateRepairsById_thenCorrect() {
        GenerateRepairsEntity generateRepair = new GenerateRepairsEntity();
        generateRepair.setId(1);
        generateRepair.setFecha_salida_reparacion(LocalDateTime.of(2021, 5, 10, 0, 0));
        generateRepair.setFecha_entrega_cliente(LocalDateTime.of(2021, 5, 15, 0, 0));
        generateRepair.setFecha_ingreso_taller(LocalDateTime.of(2021, 5, 10, 0, 0));
        generateRepair.setHora_ingreso_taller(LocalTime.of(10, 0));
        generateRepair.setHora_salida_reparacion(LocalTime.of(15, 0));
        generateRepair.setHora_entrega_cliente(LocalTime.of(10, 0));
        generateRepair.setMonto_total_reparacion(10000F);
        generateRepair.setPatente_vehiculo("AB1234");
        generateRepair.setTipo_reparacion("Mecánica");

        when(generateRepairsRepository.findById(1)).thenReturn(generateRepair);

        GenerateRepairsEntity generateRepairById = generateRepairsServices.getGenerateRepairsById(1);

        assertEquals(1, generateRepairById.getId());
        assertEquals(LocalDateTime.of(2021, 5, 10, 0, 0), generateRepairById.getFecha_salida_reparacion());
        assertEquals(LocalDateTime.of(2021, 5, 15, 0, 0), generateRepairById.getFecha_entrega_cliente());
        assertEquals(LocalDateTime.of(2021, 5, 10, 0, 0), generateRepairById.getFecha_ingreso_taller());
        assertEquals(LocalTime.of(10, 0), generateRepairById.getHora_ingreso_taller());
        assertEquals(LocalTime.of(15, 0), generateRepairById.getHora_salida_reparacion());
        assertEquals(LocalTime.of(10, 0), generateRepairById.getHora_entrega_cliente());
        assertEquals(10000F, generateRepairById.getMonto_total_reparacion());
        assertEquals("AB1234", generateRepairById.getPatente_vehiculo());
        assertEquals("Mecánica", generateRepairById.getTipo_reparacion());

    }

    //public List<Object[]> GenerateGroupByTipoReparacion(){
    //        return generateRepairsRepository.GenerateGroupByTipoReparacion();
    //    }

    @Test
    void whenGenerateGroupByTipoReparacion_thenCorrect() {
        List<Object[]> groupByTipoReparacion = new ArrayList<>();
        Object[] reparacion1 = new Object[3];
        reparacion1[0] = "Mecánica";
        reparacion1[1] = 10;
        reparacion1[2] = 10000;

        Object[] reparacion2 = new Object[3];
        reparacion2[0] = "Eléctrica";
        reparacion2[1] = 15;
        reparacion2[2] = 20000;

        Object[] reparacion3 = new Object[3];
        reparacion3[0] = "Carrocería";
        reparacion3[1] = 20;
        reparacion3[2] = 30000;

        groupByTipoReparacion.add(reparacion1);
        groupByTipoReparacion.add(reparacion2);
        groupByTipoReparacion.add(reparacion3);

        when(generateRepairsRepository.GenerateGroupByTipoReparacion()).thenReturn(groupByTipoReparacion);

        List<Object[]> groupByTipoReparacionList = generateRepairsServices.GenerateGroupByTipoReparacion();

        assertEquals(3, groupByTipoReparacionList.size());
        assertEquals("Mecánica", groupByTipoReparacionList.get(0)[0]);
        assertEquals(10, groupByTipoReparacionList.get(0)[1]);
        assertEquals(10000, groupByTipoReparacionList.get(0)[2]);
        assertEquals("Eléctrica", groupByTipoReparacionList.get(1)[0]);
        assertEquals(15, groupByTipoReparacionList.get(1)[1]);
        assertEquals(20000, groupByTipoReparacionList.get(1)[2]);
        assertEquals("Carrocería", groupByTipoReparacionList.get(2)[0]);
        assertEquals(20, groupByTipoReparacionList.get(2)[1]);
        assertEquals(30000, groupByTipoReparacionList.get(2)[2]);
    }

    //public List<Object[]> GenerateGroupByCombustible(){
    //        return generateRepairsRepository.GenerateGroupByCombustible();
    //    }

    @Test
    void whenGenerateGroupByCombustible_thenCorrect() {
        List<Object[]> groupByCombustible = new ArrayList<>();
        Object[] reparacion1 = new Object[6];
        reparacion1[0] = "Mecánica";
        reparacion1[1] = 10;
        reparacion1[2] = 10000;
        reparacion1[3] = 20000;
        reparacion1[4] = 30000;
        reparacion1[5] = 40000;

        Object[] reparacion2 = new Object[6];
        reparacion2[0] = "Eléctrica";
        reparacion2[1] = 15;
        reparacion2[2] = 20000;
        reparacion2[3] = 40000;
        reparacion2[4] = 60000;
        reparacion2[5] = 80000;

        Object[] reparacion3 = new Object[6];
        reparacion3[0] = "Carrocería";
        reparacion3[1] = 20;
        reparacion3[2] = 30000;
        reparacion3[3] = 60000;
        reparacion3[4] = 90000;
        reparacion3[5] = 120000;

        groupByCombustible.add(reparacion1);
        groupByCombustible.add(reparacion2);
        groupByCombustible.add(reparacion3);

        when(generateRepairsRepository.GenerateGroupByCombustible()).thenReturn(groupByCombustible);

        List<Object[]> groupByCombustibleList = generateRepairsServices.GenerateGroupByCombustible();

        assertEquals(3, groupByCombustibleList.size());
        assertEquals("Mecánica", groupByCombustibleList.get(0)[0]);
        assertEquals(10, groupByCombustibleList.get(0)[1]);
        assertEquals(10000, groupByCombustibleList.get(0)[2]);
        assertEquals(20000, groupByCombustibleList.get(0)[3]);
        assertEquals(30000, groupByCombustibleList.get(0)[4]);
        assertEquals(40000, groupByCombustibleList.get(0)[5]);
        assertEquals("Eléctrica", groupByCombustibleList.get(1)[0]);
        assertEquals(15, groupByCombustibleList.get(1)[1]);
        assertEquals(20000, groupByCombustibleList.get(1)[2]);
        assertEquals(40000, groupByCombustibleList.get(1)[3]);
        assertEquals(60000, groupByCombustibleList.get(1)[4]);
        assertEquals(80000, groupByCombustibleList.get(1)[5]);
        assertEquals("Carrocería", groupByCombustibleList.get(2)[0]);
        assertEquals(20, groupByCombustibleList.get(2)[1]);
        assertEquals(30000, groupByCombustibleList.get(2)[2]);
        assertEquals(60000, groupByCombustibleList.get(2)[3]);
        assertEquals(90000, groupByCombustibleList.get(2)[4]);
        assertEquals(120000, groupByCombustibleList.get(2)[5]);

    }

    //public Map<String,Object> saveGenerateRepairs(GenerateRepairsEntity generateRepairs, boolean uso_bono){
    //        // Obtener el vehículo por patente
    //        VehiclesEntity vehicle = vehiclesRepository.findByPatente(generateRepairs.getPatente_vehiculo());
    //
    //        // Obtener los IDs de reparación y calcular el monto de reparaciones
    //        List<Long> ids_reparaciones = Arrays.stream(generateRepairs.getTipo_reparacion().split(","))
    //                .map(Long::parseLong)
    //                .sorted()
    //                .collect(Collectors.toList());
    //
    //        System.out.println("IDs de reparaciones: " + ids_reparaciones);
    //        double MontoReparaciones = calcularMontoReparaciones(vehicle, ids_reparaciones);
    //
    //        // Calcular descuentos y recargos
    //        double Descuento_Historial_Reparaciones = calcularDescuentoPorHistorial(vehicle.getTipo_motor(), vehicle.getNumero_reparaciones()) * MontoReparaciones;
    //        double Descuento_Fecha_Hora_Ingreso = calcularDescuentoPorHora(generateRepairs.getFecha_ingreso_taller(), generateRepairs.getHora_ingreso_taller()) * MontoReparaciones;
    //        double recargo_kilometraje = calculoRecargoKilometraje(vehicle) * MontoReparaciones;
    //        double recargo_antiguedad = calculoRecargoAntiguedad(vehicle) * MontoReparaciones;
    //        double RecargoDiasRetraso = obtenerRecargoPorRetraso(generateRepairs.getFecha_salida_reparacion(), generateRepairs.getFecha_entrega_cliente()) * MontoReparaciones;
    //
    //        // Inicializar el monto de descuentos y recargos
    //        double totalDescuentos = Descuento_Historial_Reparaciones + Descuento_Fecha_Hora_Ingreso;
    //        double totalRecargos = recargo_kilometraje + recargo_antiguedad + RecargoDiasRetraso;
    //
    //        // Verificar si se aplica descuento por bono
    //        double descuento_bono = 0;
    //        if (uso_bono) {
    //            descuento_bono = obtenerDescuentoPorBono(vehicle);
    //            // El descuento por bono se aplica directamente, ya que es un valor fijo en moneda
    //            System.out.println("Descuento por bono aplicado: " + descuento_bono);
    //        }
    //
    //        // Calcular el IVA
    //        double subtotal = MontoReparaciones + totalRecargos - totalDescuentos - descuento_bono;
    //        double iva = calcularIVA(subtotal);
    //
    //        // Calcular el monto total
    //        double monto = subtotal + iva;
    //
    //        // Crear la entidad de reparación generada
    //        GenerateRepairsEntity Repairs = new GenerateRepairsEntity();
    //
    //        // Asignar los valores a la entidad
    //        Repairs.setMonto_total_reparacion((float) monto);
    //        Repairs.setFecha_ingreso_taller(generateRepairs.getFecha_ingreso_taller());
    //        Repairs.setHora_ingreso_taller(generateRepairs.getHora_ingreso_taller());
    //        Repairs.setTipo_reparacion(ids_reparaciones.stream().map(String::valueOf).collect(Collectors.joining(",")));
    //        Repairs.setFecha_salida_reparacion(generateRepairs.getFecha_salida_reparacion());
    //        Repairs.setHora_salida_reparacion(generateRepairs.getHora_salida_reparacion());
    //        Repairs.setFecha_entrega_cliente(generateRepairs.getFecha_entrega_cliente());
    //        Repairs.setHora_entrega_cliente(generateRepairs.getHora_entrega_cliente());
    //        Repairs.setPatente_vehiculo(generateRepairs.getPatente_vehiculo());
    //
    //        //Mostramos cada descuento y recargo
    //        System.out.println("Descuento por historial de reparaciones: " + Descuento_Historial_Reparaciones);
    //        System.out.println("Descuento por fecha y hora de ingreso: " + Descuento_Fecha_Hora_Ingreso);
    //        System.out.println("Recargo por kilometraje: " + recargo_kilometraje);
    //        System.out.println("Recargo por antiguedad: " + recargo_antiguedad);
    //        System.out.println("Recargo por dias de retraso: " + RecargoDiasRetraso);
    //        System.out.println("Descuento por bono: " + descuento_bono);
    //        System.out.println("IVA: " + iva);
    //        System.out.println("Monto total: " + monto);
    //
    //
    //
    //        // Guardar la entidad en la BD
    //        generateRepairsRepository.save(Repairs);
    //        Map<String, Object> response = new HashMap<>();
    //        response.put("generateRepair", Repairs);
    //        response.put("totalDescuentos", totalDescuentos);
    //        response.put("totalRecargos", totalRecargos);
    //        response.put("descuentoBono", descuento_bono);
    //        response.put("iva", iva);
    //        response.put("montoReparaciones", MontoReparaciones);
    //
    //
    //
    //
    //
    //        return response;
    //
    //
    //
    //    }

    @Test
    void whenSaveGenerateRepairs_thenCorrect() {
        // Given
        GenerateRepairsEntity generateRepairs = new GenerateRepairsEntity();
        generateRepairs.setPatente_vehiculo("AB1234");
        generateRepairs.setTipo_reparacion("1,2,3");
        generateRepairs.setFecha_ingreso_taller(LocalDateTime.of(2021, 5, 10, 10, 0));
        generateRepairs.setHora_ingreso_taller(LocalTime.of(10, 0));
        generateRepairs.setFecha_salida_reparacion(LocalDateTime.of(2021, 5, 15, 15, 0));
        generateRepairs.setHora_salida_reparacion(LocalTime.of(15, 0));
        generateRepairs.setFecha_entrega_cliente(LocalDateTime.of(2021, 5, 15, 10, 0));
        generateRepairs.setHora_entrega_cliente(LocalTime.of(10, 0));

        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setPatente("AB1234");
        vehicle.setTipo_motor("Diesel");
        vehicle.setNumero_reparaciones(5);
        vehicle.setTipo("Sedan");
        vehicle.setKilometraje(50000);
        vehicle.setAnio_fabricacion("2015");
        vehicle.setMarca("Toyota");

        SurchargeMileageEntity surchargeMileage = new SurchargeMileageEntity();
        surchargeMileage.setSedan(0.05F);
        surchargeMileage.setHatchback(0.1F);
        surchargeMileage.setSuv(0.15F);
        surchargeMileage.setPickup(0.2F);
        surchargeMileage.setFurgoneta(0.25F);

        SurchargeSeniorityEntity surchargeSeniority = new SurchargeSeniorityEntity();
        surchargeSeniority.setYears_min(5);
        surchargeSeniority.setYears_max(10);
        surchargeSeniority.setSedan(0.05F);
        surchargeSeniority.setHatchback(0.1F);
        surchargeSeniority.setSuv(0.15F);
        surchargeSeniority.setPickup(0.2F);
        surchargeSeniority.setFurgoneta(0.25F);

        BonusesEntity bonus = new BonusesEntity();
        bonus.setMarca("Toyota");
        bonus.setDisponibilidad("1");
        bonus.setMonto(1000);

        RepairsEntity repair1 = new RepairsEntity();
        repair1.setId(1L);
        repair1.setCost_diesel(1000);
        repair1.setCost_gasoline(500);
        repair1.setCost_hybrid(800);
        repair1.setCost_electric(200);

        RepairsEntity repair2 = new RepairsEntity();
        repair2.setId(2L);
        repair2.setCost_diesel(2000);
        repair2.setCost_gasoline(1000);
        repair2.setCost_hybrid(1600);
        repair2.setCost_electric(400);

        int yearOfManufacture = Integer.parseInt(vehicle.getAnio_fabricacion());
        int currentYear = LocalDate.now().getYear();
        int vehicleAge = currentYear - yearOfManufacture;

        when(vehiclesRepository.findByPatente("AB1234")).thenReturn(vehicle);
        when(surchargeMileageRepository.findByKilometraje(50000)).thenReturn(surchargeMileage);
        when(surchargeSeniorityRepository.findByAntiguedad(vehicleAge)).thenReturn(surchargeSeniority);
        when(bonusesRepository.findByMarca("Toyota")).thenReturn(bonus);
        when(repairsRepository.findById(1L)).thenReturn(Optional.of(repair1));
        when(repairsRepository.findById(2L)).thenReturn(Optional.of(repair2));

        // When
        Map<String, Object> response = generateRepairsServices.saveGenerateRepairs(generateRepairs, true);

        // Then
        assertNotNull(response);
        assertEquals(6, response.size());
        assertEquals(GenerateRepairsEntity.class, response.get("generateRepair").getClass());
        assertEquals(510.0, response.get("totalDescuentos"));
        assertEquals(300.00000447034836, response.get("totalRecargos"));
        assertEquals(1000.0, response.get("descuentoBono"));
        assertEquals(340.1000008493662, response.get("iva"));
        assertEquals(3000.0, response.get("montoReparaciones"));

        GenerateRepairsEntity generateRepair = (GenerateRepairsEntity) response.get("generateRepair");
        assertEquals(2130.1F, generateRepair.getMonto_total_reparacion());
        assertEquals(LocalDateTime.of(2021, 5, 10, 10, 0), generateRepair.getFecha_ingreso_taller());
        assertEquals(LocalTime.of(10, 0), generateRepair.getHora_ingreso_taller());
        assertEquals("1,2,3", generateRepair.getTipo_reparacion());
        assertEquals(LocalDateTime.of(2021, 5, 15, 15, 0), generateRepair.getFecha_salida_reparacion());
        assertEquals(LocalTime.of(15, 0), generateRepair.getHora_salida_reparacion());
        assertEquals(LocalDateTime.of(2021, 5, 15, 10, 0), generateRepair.getFecha_entrega_cliente());
        assertEquals(LocalTime.of(10, 0), generateRepair.getHora_entrega_cliente());
        assertEquals("AB1234", generateRepair.getPatente_vehiculo());



    }







}
