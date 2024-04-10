package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.VehiclesEntity;
import com.Tingeso.demo.services.VehiclesServices;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(VehiclesController.class)
public class VehiclesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiclesServices vehiclesServices;
/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    @Column(unique = true, nullable = false)
    private String patente;

    private String marca;

    private String modelo;

    private String tipo;

    private String anio_fabricacion;

    private String tipo_motor;

    private String numero_asientos;

    private int numero_reparaciones;

 */
    // Test para listar vehiculos y verificar que se retorne una lista de vehiculos
    @Test
    public void listVehicles_ShouldReturnVehicles() throws Exception{
        VehiclesEntity vehicle1 = new VehiclesEntity(1,
                "AB1234",
                "Toyota",
                "Yaris",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0);

        VehiclesEntity vehicle2 = new VehiclesEntity(2,
                "CD5678",
                "Nissan",
                "Sentra",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0);

        List<VehiclesEntity> vehicleList = new ArrayList<>(Arrays.asList(vehicle1, vehicle2));

        given(vehiclesServices.getVehicles()).willReturn((ArrayList<VehiclesEntity>) vehicleList);

        mockMvc.perform(get("/api/v1/vehicles/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].patente", is("AB1234")))
                .andExpect(jsonPath("$[1].patente", is("CD5678")));



    }

    //test para guardar un vehiculo y verificar que se retorne un vehiculo

    @Test
    public void saveVehicle_ShouldReturnVehicle() throws Exception{
        VehiclesEntity vehicle = new VehiclesEntity(1,
                "AB1234",
                "Toyota",
                "Yaris",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0);

        given(vehiclesServices.saveVehicle(vehicle)).willReturn(vehicle);

        mockMvc.perform(post("/api/v1/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"patente\":\"AB1234\",\"marca\":\"Toyota\",\"modelo\":\"Yaris\",\"tipo\":\"Sedan\",\"anio_fabricacion\":\"2021\",\"tipo_motor\":\"Gasolina\",\"numero_asientos\":\"5\",\"numero_reparaciones\":0}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patente", is("AB1234")));
    }


    //test para eliminar un vehiculo
    @Test
    public void deleteVehicleById_ShouldReturnMessage() throws Exception{

        mockMvc.perform(delete("/api/v1/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Vehiculo eliminado"));
    }

    //test para obtener un vehiculo por patente
    @Test
    public void getVehicleByPatente_ShouldReturnVehicle() throws Exception{
        VehiclesEntity vehicle = new VehiclesEntity(1,
                "AB1234",
                "Toyota",
                "Yaris",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0);

        given(vehiclesServices.getVehiclesByPatente("AB1234")).willReturn(vehicle);

        mockMvc.perform(get("/api/v1/vehicles/AB1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patente", is("AB1234")));
    }


}
