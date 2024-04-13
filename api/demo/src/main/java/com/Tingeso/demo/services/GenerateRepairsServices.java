package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import com.Tingeso.demo.entities.RepairsEntity;
import com.Tingeso.demo.entities.VehiclesEntity;
import com.Tingeso.demo.repositories.GenerateRepairsRepository;
import com.Tingeso.demo.repositories.RepairsRepository;
import com.Tingeso.demo.repositories.VehiclesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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

    //Metodo para obtener todas las reparaciones generadas
    public ArrayList<GenerateRepairsEntity> getGenerateRepairs(){
        return (ArrayList<GenerateRepairsEntity>) generateRepairsRepository.findAll();
    }

    //Metodo para guardar una reparacion generada en la BD

    public GenerateRepairsEntity saveGenerateRepairs(GenerateRepairsEntity generateRepairs){
        GenerateRepairsEntity Repairs = new GenerateRepairsEntity();
        VehiclesEntity vehicle = vehiclesRepository.findByPatente(generateRepairs.getPatente_vehiculo()); //Busco el vehiculo por patente
        //obtenemos todas las reparaciones que se les realizó al vehiculo
        List<Long> ids_reparaciones = Arrays.stream(generateRepairs.getTipo_reparacion().split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        double monto = 0;
        double MontoReparaciones = 0;
        for (Long id : ids_reparaciones) {
            RepairsEntity repair = repairsRepository.findById(id).orElse(null);
            if (repair != null) {
                if(vehicle.getTipo_motor().equals("Diesel")){
                    MontoReparaciones += repair.getCost_diesel();
                }else if (vehicle.getTipo_motor().equals("Gasolina")) {
                    MontoReparaciones += repair.getCost_gasoline();
                } else if (vehicle.getTipo_motor().equals("Hibrido")) {
                    MontoReparaciones += repair.getCost_hybrid();
                } else if (vehicle.getTipo_motor().equals("Electrico")) {
                    MontoReparaciones += repair.getCost_electric();
                }
            }
        }
        // Continuación de tu código existente
        int Reparaciones = vehicle.getNumero_reparaciones();
        double descuento = 0;
        if(Reparaciones > 0) {
            if (Reparaciones >= 1 && Reparaciones <= 2) {
                if (vehicle.getTipo_motor().equals("Diesel")) {
                    descuento = 0.07;
                } else if (vehicle.getTipo_motor().equals("Gasolina")) {
                    descuento = 0.05;
                } else if (vehicle.getTipo_motor().equals("Hibrido")) {
                    descuento = 0.10;
                } else if (vehicle.getTipo_motor().equals("Electrico")) {
                    descuento = 0.08;
                }
            } else if (Reparaciones >= 3 && Reparaciones <= 5) {
                if (vehicle.getTipo_motor().equals("Diesel")) {
                    descuento = 0.12;
                } else if (vehicle.getTipo_motor().equals("Gasolina")) {
                    descuento = 0.10;
                } else if (vehicle.getTipo_motor().equals("Hibrido")) {
                    descuento = 0.15;
                } else if (vehicle.getTipo_motor().equals("Electrico")) {
                    descuento = 0.13;
                }
            } else if (Reparaciones >= 6 && Reparaciones <= 9) {
                if (vehicle.getTipo_motor().equals("Diesel")) {
                    descuento = 0.17;
                } else if (vehicle.getTipo_motor().equals("Gasolina")) {
                    descuento = 0.15;
                } else if (vehicle.getTipo_motor().equals("Hibrido")) {
                    descuento = 0.20;
                } else if (vehicle.getTipo_motor().equals("Electrico")) {
                    descuento = 0.18;
                }
            } else if (Reparaciones >= 10) {
                if (vehicle.getTipo_motor().equals("Diesel")) {
                    descuento = 0.22;
                } else if (vehicle.getTipo_motor().equals("Gasolina")) {
                    descuento = 0.20;
                } else if (vehicle.getTipo_motor().equals("Hibrido")) {
                    descuento = 0.25;
                } else if (vehicle.getTipo_motor().equals("Electrico")) {
                    descuento = 0.23;
                }
            }

            MontoReparaciones = MontoReparaciones - (MontoReparaciones * descuento); //Calculo de descuento
        }

        String fecha_Ingreso = generateRepairs.getFecha_ingreso_taller().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime ingreso_taller = LocalDateTime.parse(fecha_Ingreso, formatter);

        DayOfWeek dia = ingreso_taller.getDayOfWeek();
        if(dia == DayOfWeek.MONDAY || dia == DayOfWeek.THURSDAY){
            //Verificamos la hora
            LocalTime hora = generateRepairs.getHora_ingreso_taller();
            if(hora.isAfter(LocalTime.of(9,0)) && hora.isBefore(LocalTime.of(12,0))){
                MontoReparaciones = MontoReparaciones - (MontoReparaciones * 0.05); //Descuento por hora
            }
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
}
