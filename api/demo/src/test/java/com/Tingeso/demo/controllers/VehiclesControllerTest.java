package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.VehiclesEntity;
import com.Tingeso.demo.services.VehiclesServices;

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

    // Test para listar vehiculos y verificar que se retorne una lista de vehiculos
    @Test
    public void listVehicles_ShouldReturnVehicles() throws Exception{



        //Creamos un vehiculo con todos los atributos y kilometraje
        VehiclesEntity vehicle1 = new VehiclesEntity(1,
                "AB1234",
                "Toyota",
                "Yaris",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0,
                1000);

        //Creamos un vehiculo con todos los atributos y kilometraje
        VehiclesEntity vehicle2 = new VehiclesEntity(2,
                "CD5678",
                "Nissan",
                "Versa",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0,
                2000);

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
        //Creamos un nuevo vehiculo con todos los atributos y kilometraje
        VehiclesEntity vehicle = new VehiclesEntity(1,
                "AB1234",
                "Toyota",
                "Yaris",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0,
                1000);

        given(vehiclesServices.saveVehicle(vehicle)).willReturn(vehicle);

        mockMvc.perform(post("/api/v1/vehicles/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"patente\":\"AB1234\",\"marca\":\"Toyota\",\"modelo\":\"Yaris\",\"tipo\":\"Sedan\",\"anio_fabricacion\":\"2021\",\"tipo_motor\":\"Gasolina\",\"numero_asientos\":\"5\",\"numero_reparaciones\":0,\"kilometraje\":1000}"))
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
        //Creamos un vehiculo con todos los atributos y kilometraje
        VehiclesEntity vehicle = new VehiclesEntity(1,
                "AB1234",
                "Toyota",
                "Yaris",
                "Sedan",
                "2021",
                "Gasolina",
                "5",
                0,
                1000);

        given(vehiclesServices.getVehiclesByPatente("AB1234")).willReturn(vehicle);

        mockMvc.perform(get("/api/v1/vehicles/AB1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.patente", is("AB1234")));
    }

    //test para obtener todas las patentes
    @Test
    public void findAllPatente_ShouldReturnPatentes() throws Exception{
        String[] patentes = {"AB1234", "CD5678"};

        given(vehiclesServices.findAllPatente()).willReturn(patentes);

        mockMvc.perform(get("/api/v1/vehicles/patentes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("AB1234")))
                .andExpect(jsonPath("$[1]", is("CD5678")));
    }

}
