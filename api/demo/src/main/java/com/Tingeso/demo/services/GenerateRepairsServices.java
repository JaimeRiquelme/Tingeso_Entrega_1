package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.*;
import com.Tingeso.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenerateRepairsServices {

    @Autowired
    GenerateRepairsRepository generateRepairsRepository;

    @Autowired
    RepairsRepository repairsRepository;

    @Autowired
    VehiclesRepository vehiclesRepository;

    @Autowired
    SurchargeMileageRepository surchargeMileageRepository;

    @Autowired
    SurchargeSeniorityRepository surchargeSeniorityRepository;

    @Autowired
    BonusesRepository bonusesRepository;

    //Metodo para obtener todas las reparaciones generadas
    public ArrayList<GenerateRepairsEntity> getGenerateRepairs(){
        return (ArrayList<GenerateRepairsEntity>) generateRepairsRepository.findAll();
    }

    //Metodo para guardar una reparacion generada en la BD

    public GenerateRepairsEntity saveGenerateRepairs(GenerateRepairsEntity generateRepairs, boolean uso_bono){
        GenerateRepairsEntity Repairs = new GenerateRepairsEntity();

        System.out.println(generateRepairs.getPatente_vehiculo());

        VehiclesEntity vehicle = vehiclesRepository.findByPatente(generateRepairs.getPatente_vehiculo()); //Busco el vehiculo por patente
        //obtenemos todas las reparaciones que se les realizó al vehiculo
        List<Long> ids_reparaciones = Arrays.stream(generateRepairs.getTipo_reparacion().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        double monto = 0;
        double MontoReparaciones = calcularMontoReparaciones(vehicle, ids_reparaciones);
        int Reparaciones = vehicle.getNumero_reparaciones();
        double Descuento_Historial_Reparaciones = calcularDescuentoPorHistorial(vehicle.getTipo_motor(), Reparaciones);

        double Descuento_Fecha_Hora_Ingreso = calcularDescuentoPorHora(generateRepairs.getFecha_ingreso_taller(), generateRepairs.getHora_ingreso_taller());

        double recargo_kilometraje = calculoRecargoKilometraje(vehicle);
        System.out.println("Recargo Kilometraje: " + recargo_kilometraje);

        double recargo_antiguedad = calculoRecargoAntiguedad(vehicle);
        System.out.println("Recargo Antiguedad: " + recargo_antiguedad);

        if(uso_bono) {
            double descuento_bono = obtenerDescuentoPorBono(vehicle);
            if(descuento_bono == 0){
                System.out.println("No hay bono disponible");
            }else{
                System.out.println("Descuento por bono: " + descuento_bono);
            }
        }else {
            System.out.println("No uso bono");
        }








        //Calculo total = [Suma(Reparaciones) + Recargos - Descuentos] + Iva
        //Suma(Reparaciones) = Suma de todas las reparaciones
        //Recargos = Suma de todos los recargos
        //Descuentos = Suma de todos los descuentos
        //Iva = Suma de todos los ivas

        //Calculo de monto total
        monto = MontoReparaciones;
        Repairs.setMonto_total_reparacion((float) monto);
        Repairs.setFecha_ingreso_taller(generateRepairs.getFecha_ingreso_taller());
        Repairs.setHora_ingreso_taller(generateRepairs.getHora_ingreso_taller());
        Repairs.setTipo_reparacion(generateRepairs.getTipo_reparacion());
        Repairs.setFecha_salida_reparacion(generateRepairs.getFecha_salida_reparacion());
        Repairs.setHora_salida_reparacion(generateRepairs.getHora_salida_reparacion());
        Repairs.setFecha_entrega_cliente(generateRepairs.getFecha_entrega_cliente());
        Repairs.setHora_entrega_cliente(generateRepairs.getHora_entrega_cliente());
        Repairs.setPatente_vehiculo(generateRepairs.getPatente_vehiculo());


        return generateRepairsRepository.save(Repairs);


    }

    private double calcularMontoReparaciones(VehiclesEntity vehicle, List<Long> idsReparaciones) {
        double montoReparaciones = 0.0;
        for (Long id : idsReparaciones) {
            RepairsEntity repair = repairsRepository.findById(id).orElse(null);
            if (repair != null) {
                switch (vehicle.getTipo_motor()) {
                    case "Diesel":
                        montoReparaciones += repair.getCost_diesel();
                        break;
                    case "Gasolina":
                        montoReparaciones += repair.getCost_gasoline();
                        break;
                    case "Hibrido":
                        montoReparaciones += repair.getCost_hybrid();
                        break;
                    case "Electrico":
                        montoReparaciones += repair.getCost_electric();
                        break;
                }
            }
        }
        return montoReparaciones;
    }

    private double calcularDescuentoPorHistorial(String tipoMotor, int numeroReparaciones) {
        double descuento = 0.0;
        if (numeroReparaciones > 0) {
            if (numeroReparaciones < 3) {
                descuento = (tipoMotor.equals("Diesel")) ? 0.07 : (tipoMotor.equals("Gasolina")) ? 0.05 : (tipoMotor.equals("Hibrido")) ? 0.10 : 0.08;
            } else if (numeroReparaciones < 6) {
                descuento = (tipoMotor.equals("Diesel")) ? 0.12 : (tipoMotor.equals("Gasolina")) ? 0.10 : (tipoMotor.equals("Hibrido")) ? 0.15 : 0.13;
            } else if (numeroReparaciones < 10) {
                descuento = (tipoMotor.equals("Diesel")) ? 0.17 : (tipoMotor.equals("Gasolina")) ? 0.15 : (tipoMotor.equals("Hibrido")) ? 0.20 : 0.18;
            } else {
                descuento = (tipoMotor.equals("Diesel")) ? 0.22 : (tipoMotor.equals("Gasolina")) ? 0.20 : (tipoMotor.equals("Hibrido")) ? 0.25 : 0.23;
            }
        }
        return descuento;
    }

    private double calcularDescuentoPorHora(LocalDateTime fechaIngreso, LocalTime horaIngreso) {
        if ((fechaIngreso.getDayOfWeek() == DayOfWeek.MONDAY || fechaIngreso.getDayOfWeek() == DayOfWeek.THURSDAY) &&
                horaIngreso.isAfter(LocalTime.of(9, 0)) && horaIngreso.isBefore(LocalTime.of(12, 0))) {
            return 0.05;
        }
        return 0.0;
    }

    private double calculoRecargoKilometraje(VehiclesEntity vehicle){
        SurchargeMileageEntity surchargeMileage = surchargeMileageRepository.findByKilometraje(vehicle.getKilometraje());

    if(vehicle.getTipo().equals("Sedan")){
        return surchargeMileage.getSedan();
    }else if(vehicle.getTipo().equals("Hatchback")){
        return surchargeMileage.getHatchback();
    }else if(vehicle.getTipo().equals("SUV")){
        return surchargeMileage.getSuv();
    }else if(vehicle.getTipo().equals("Pickup")){
        return surchargeMileage.getPickup();
    }else if(vehicle.getTipo().equals("Furgoneta")){
        return surchargeMileage.getFurgoneta();
    }else{
        return 0;
    }

    }

    private double calculoRecargoAntiguedad(VehiclesEntity vehicle){
        LocalDate fechaActual = LocalDate.now();
        int añoActual = fechaActual.getYear();
        int añoFabricacion = Integer.parseInt(vehicle.getAnio_fabricacion()); // Convierte el año de fabricación de String a int.
        int antiguedad = añoActual - añoFabricacion;

        System.out.println("Antiguedad: " + antiguedad);
        SurchargeSeniorityEntity surchargeSeniority = surchargeSeniorityRepository.findByAntiguedad(antiguedad);

        if(vehicle.getTipo().equals("Sedan")){
            return surchargeSeniority.getSedan();
        }else if(vehicle.getTipo().equals("Hatchback")){
            return surchargeSeniority.getHatchback();
        }else if(vehicle.getTipo().equals("SUV")){
            return surchargeSeniority.getSuv();
        }else if(vehicle.getTipo().equals("Pickup")){
            return surchargeSeniority.getPickup();
        }else if(vehicle.getTipo().equals("Furgoneta")){
            return surchargeSeniority.getFurgoneta();
        }else{
            return 0;
        }
    }

    private double obtenerDescuentoPorBono(VehiclesEntity vehicle){

        String marca_vehiculo = vehicle.getMarca();
        BonusesEntity bono = bonusesRepository.findByMarca(marca_vehiculo);

        try{
            if (bono != null) {
                if (Integer.parseInt(bono.getDisponibilidad()) != 0) {
                    return bono.getMonto();
                }else {
                    return 0;
                }
            }else {
                return 0;
            }
        }catch (Exception e){
            return 0;
        }
    }
}
