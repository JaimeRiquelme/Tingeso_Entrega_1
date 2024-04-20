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
import java.time.temporal.ChronoUnit;
import java.util.*;
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

    public Map<String,Object> saveGenerateRepairs(GenerateRepairsEntity generateRepairs, boolean uso_bono){
        // Obtener el vehículo por patente
        VehiclesEntity vehicle = vehiclesRepository.findByPatente(generateRepairs.getPatente_vehiculo());

        // Obtener los IDs de reparación y calcular el monto de reparaciones
        List<Long> ids_reparaciones = Arrays.stream(generateRepairs.getTipo_reparacion().split(","))
                .map(Long::parseLong)
                .sorted()
                .collect(Collectors.toList());

        System.out.println("IDs de reparaciones: " + ids_reparaciones);
        double MontoReparaciones = calcularMontoReparaciones(vehicle, ids_reparaciones);

        // Calcular descuentos y recargos
        double Descuento_Historial_Reparaciones = calcularDescuentoPorHistorial(vehicle.getTipo_motor(), vehicle.getNumero_reparaciones()) * MontoReparaciones;
        double Descuento_Fecha_Hora_Ingreso = calcularDescuentoPorHora(generateRepairs.getFecha_ingreso_taller(), generateRepairs.getHora_ingreso_taller()) * MontoReparaciones;
        double recargo_kilometraje = calculoRecargoKilometraje(vehicle) * MontoReparaciones;
        double recargo_antiguedad = calculoRecargoAntiguedad(vehicle) * MontoReparaciones;
        double RecargoDiasRetraso = obtenerRecargoPorRetraso(generateRepairs.getFecha_salida_reparacion(), generateRepairs.getFecha_entrega_cliente()) * MontoReparaciones;

        // Inicializar el monto de descuentos y recargos
        double totalDescuentos = Descuento_Historial_Reparaciones + Descuento_Fecha_Hora_Ingreso;
        double totalRecargos = recargo_kilometraje + recargo_antiguedad + RecargoDiasRetraso;

        // Verificar si se aplica descuento por bono
        double descuento_bono = 0;
        if (uso_bono) {
            descuento_bono = obtenerDescuentoPorBono(vehicle);
            // El descuento por bono se aplica directamente, ya que es un valor fijo en moneda
            System.out.println("Descuento por bono aplicado: " + descuento_bono);
        }

        // Calcular el IVA
        double subtotal = MontoReparaciones + totalRecargos - totalDescuentos - descuento_bono;
        double iva = calcularIVA(subtotal);

        // Calcular el monto total
        double monto = subtotal + iva;

        // Crear la entidad de reparación generada
        GenerateRepairsEntity Repairs = new GenerateRepairsEntity();

        // Asignar los valores a la entidad
        Repairs.setMonto_total_reparacion((float) monto);
        Repairs.setFecha_ingreso_taller(generateRepairs.getFecha_ingreso_taller());
        Repairs.setHora_ingreso_taller(generateRepairs.getHora_ingreso_taller());
        Repairs.setTipo_reparacion(ids_reparaciones.stream().map(String::valueOf).collect(Collectors.joining(",")));
        Repairs.setFecha_salida_reparacion(generateRepairs.getFecha_salida_reparacion());
        Repairs.setHora_salida_reparacion(generateRepairs.getHora_salida_reparacion());
        Repairs.setFecha_entrega_cliente(generateRepairs.getFecha_entrega_cliente());
        Repairs.setHora_entrega_cliente(generateRepairs.getHora_entrega_cliente());
        Repairs.setPatente_vehiculo(generateRepairs.getPatente_vehiculo());

        //Mostramos cada descuento y recargo
        System.out.println("Descuento por historial de reparaciones: " + Descuento_Historial_Reparaciones);
        System.out.println("Descuento por fecha y hora de ingreso: " + Descuento_Fecha_Hora_Ingreso);
        System.out.println("Recargo por kilometraje: " + recargo_kilometraje);
        System.out.println("Recargo por antiguedad: " + recargo_antiguedad);
        System.out.println("Recargo por dias de retraso: " + RecargoDiasRetraso);
        System.out.println("Descuento por bono: " + descuento_bono);
        System.out.println("IVA: " + iva);
        System.out.println("Monto total: " + monto);



        // Guardar la entidad en la BD
        generateRepairsRepository.save(Repairs);
        Map<String, Object> response = new HashMap<>();
        response.put("generateRepair", Repairs);
        response.put("totalDescuentos", totalDescuentos);
        response.put("totalRecargos", totalRecargos);
        response.put("descuentoBono", descuento_bono);
        response.put("iva", iva);
        response.put("montoReparaciones", MontoReparaciones);





        return response;



    }

    public double calcularMontoReparaciones(VehiclesEntity vehicle, List<Long> idsReparaciones) {
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
                    case "Híbrido":
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

    public double calcularDescuentoPorHistorial(String tipoMotor, int numeroReparaciones) {
        double descuento = 0.0;
        if (numeroReparaciones > 0) {
            if (numeroReparaciones < 3) {
                descuento = (tipoMotor.equals("Diesel")) ? 0.07 : (tipoMotor.equals("Gasolina")) ? 0.05 : (tipoMotor.equals("Híbrido")) ? 0.10 : 0.08;
            } else if (numeroReparaciones < 6) {
                descuento = (tipoMotor.equals("Diesel")) ? 0.12 : (tipoMotor.equals("Gasolina")) ? 0.10 : (tipoMotor.equals("Híbrido")) ? 0.15 : 0.13;
            } else if (numeroReparaciones < 10) {
                descuento = (tipoMotor.equals("Diesel")) ? 0.17 : (tipoMotor.equals("Gasolina")) ? 0.15 : (tipoMotor.equals("Híbrido")) ? 0.20 : 0.18;
            } else {
                descuento = (tipoMotor.equals("Diesel")) ? 0.22 : (tipoMotor.equals("Gasolina")) ? 0.20 : (tipoMotor.equals("Híbrido")) ? 0.25 : 0.23;
            }
        }
        return descuento;
    }

    public double calcularDescuentoPorHora(LocalDateTime fechaIngreso, LocalTime horaIngreso) {
        if ((fechaIngreso.getDayOfWeek() == DayOfWeek.MONDAY || fechaIngreso.getDayOfWeek() == DayOfWeek.THURSDAY) &&
                horaIngreso.isAfter(LocalTime.of(9, 0)) && horaIngreso.isBefore(LocalTime.of(12, 0))) {
            return 0.05;
        }
        return 0.0;
    }

    public double calculoRecargoKilometraje(VehiclesEntity vehicle){
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

    public double calculoRecargoAntiguedad(VehiclesEntity vehicle){
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

    public double obtenerDescuentoPorBono(VehiclesEntity vehicle){

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

    public double obtenerRecargoPorRetraso(LocalDateTime fechaSalidaReparacion, LocalDateTime fechaEntregaCliente){
        long DiasRetraso = ChronoUnit.DAYS.between(fechaSalidaReparacion, fechaEntregaCliente);
        return DiasRetraso * 0.05;
    }

    public double calcularIVA(double subtotal) {
        return subtotal * 0.19;
    }

    //Creamos la funcion para usar el reporte de promedio horas marca
    public List<Object[]> getPromedioHorasPorMarca(){
        return generateRepairsRepository.getPromedioHorasPorMarca();
    }

    //Creamos la funcion para obtener una reparacion por el id de la reparacion
    public GenerateRepairsEntity getGenerateRepairsById(int idReparacion){
        return generateRepairsRepository.findById(idReparacion);
    }

    //Creamos la funcion para obtener el reporte de reparaciones agrupadas por tipo de reparacion
    public List<Object[]> GenerateGroupByTipoReparacion(){
        return generateRepairsRepository.GenerateGroupByTipoReparacion();
    }

    //Creamos la funcion para obtener el reporte de reparaciones agrupadas por tipo de combustible
    public List<Object[]> GenerateGroupByCombustible(){
        return generateRepairsRepository.GenerateGroupByCombustible();
    }
}
