package com.Tingeso.demo.services;


import com.Tingeso.demo.entities.VehiclesEntity;
import com.Tingeso.demo.repositories.VehiclesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(VehiclesServices.class)
public class VehiclesServicesTest {

    @Autowired
    private VehiclesServices vehiclesServices;

    @MockBean
    private VehiclesRepository vehiclesRepository;

    //public ArrayList<VehiclesEntity> getVehicles(){
    //        return (ArrayList<VehiclesEntity>) vehiclesRepository.findAll();
    //    }

    @Test
    void whenGetVehicles_thenCorrect() {
        //Given
        VehiclesEntity vehicle1 = new VehiclesEntity();
        VehiclesEntity vehicle2 = new VehiclesEntity();
        VehiclesEntity vehicle3 = new VehiclesEntity();

        ArrayList<VehiclesEntity> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);


        //When
        when(vehiclesRepository.findAll()).thenReturn(vehicles);
        ArrayList<VehiclesEntity> vehiclesList = vehiclesServices.getVehicles();

        //Then
        assertNotNull(vehiclesList);
        assertThat(vehiclesList.size()).isEqualTo(3);

    }

    //public VehiclesEntity saveVehicle(VehiclesEntity vehicle){
    //        return vehiclesRepository.save(vehicle);
    //    }

    @Test
    void whenSaveVehicle_thenCorrect() {
        //Given
        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setId(1);
        vehicle.setPatente("AB1234");
        vehicle.setMarca("Toyota");
        vehicle.setModelo("Yaris");
        vehicle.setAnio_fabricacion("2020");
        vehicle.setTipo("Sedan");


        //When
        when(vehiclesRepository.save(vehicle)).thenReturn(vehicle);
        VehiclesEntity savedVehicle = vehiclesServices.saveVehicle(vehicle);

        //Then
        assertNotNull(savedVehicle);
        assertThat(savedVehicle).isEqualTo(vehicle);
    }

    // public VehiclesEntity getVehiclesByPatente(String patente){
    //        return vehiclesRepository.findByPatente(patente);
    //    }

    @Test
    void whenGetVehicleByPatente_thenCorrect() {
        //Given
        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setId(1);
        vehicle.setPatente("AB1234");
        vehicle.setMarca("Toyota");
        vehicle.setModelo("Yaris");
        vehicle.setAnio_fabricacion("2020");
        vehicle.setTipo("Sedan");

        //When
        when(vehiclesRepository.findByPatente("AB1234")).thenReturn(vehicle);
        VehiclesEntity vehicleByPatente = vehiclesServices.getVehiclesByPatente("AB1234");

        //Then
        assertNotNull(vehicleByPatente);
        assertThat(vehicleByPatente).isEqualTo(vehicle);
    }

    //public void deleteVehicle(long id){
    //        vehiclesRepository.deleteById(id);
    //    }

    @Test
    void whenDeleteVehicle_thenCorrect() {
        //Given
        VehiclesEntity vehicle = new VehiclesEntity();
        vehicle.setId(1);
        vehicle.setPatente("AB1234");
        vehicle.setMarca("Toyota");
        vehicle.setModelo("Yaris");
        vehicle.setAnio_fabricacion("2020");
        vehicle.setTipo("Sedan");

        //When
        vehiclesServices.deleteVehicle(1L);


        //Then
        assertThat(vehiclesRepository.findById(1L)).isEmpty();

    }

    //public String[] findAllPatente(){
    //        return vehiclesRepository.findAllPatente();
    //    }

    @Test
    void whenGetAllPatente_thenCorrect() {
        //Given
        String[] patentes = {"AB1234", "CD5678", "EF9101"};

        //When
        when(vehiclesRepository.findAllPatente()).thenReturn(patentes);
        String[] patentesList = vehiclesServices.findAllPatente();

        //Then
        assertNotNull(patentesList);
        assertThat(patentesList.length).isEqualTo(3);
    }
}
