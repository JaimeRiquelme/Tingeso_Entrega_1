package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.RepairsEntity;
import com.Tingeso.demo.services.RepairsServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; // Corregido aquí
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RepairsController.class)
public class RepairsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepairsServices repairsServices;

    /*Informacion entidad
    *
    *     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String type;

    private String description;

    private float cost_gasoline;

    private float cost_diesel;

    private float cost_hybrid;

    private float cost_electric;*/

    @Test
    public void listRepairs_ShouldReturnRepairs() throws Exception {
        RepairsEntity repair1 = new RepairsEntity(1L, "Reparacion1", "Descripcion1", 50000, 60000, 70000, 80000);
        RepairsEntity repair2 = new RepairsEntity(2L, "Reparacion2", "Descripcion2", 60000, 70000, 80000, 90000);
        List<RepairsEntity> repairList = new ArrayList<>(Arrays.asList(repair1, repair2));

        given(repairsServices.getRepairs()).willReturn((ArrayList<RepairsEntity>) repairList);

        mockMvc.perform(get("/api/v1/repairs/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }


    //Test para guardar una reparacion y obtenerla para ver si se guardo correctamente
    @Test
    public void saveRepair_ShouldReturnSavedRepair() throws Exception {
        RepairsEntity savedRepair = new RepairsEntity(1L, "Reparacion1", "Descripcion1", 50000, 60000, 70000, 80000);

        given(repairsServices.saveRepair(any(RepairsEntity.class))).willReturn(savedRepair);

        String repairJson = """
        {
            "type": "Reparacion1",
            "description": "Descripcion1",
            "cost_gasoline": 50000,
            "cost_diesel": 60000,
            "cost_hybrid": 70000,
            "cost_electric": 80000
        }
        """;

        mockMvc.perform(post("/api/v1/repairs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(repairJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type", is("Reparacion1")))
                .andExpect(jsonPath("$.description", is("Descripcion1")))
                .andExpect(jsonPath("$.cost_gasoline", closeTo(50000, 0.1)))
                .andExpect(jsonPath("$.cost_diesel", closeTo(60000, 0.1)))
                .andExpect(jsonPath("$.cost_hybrid", closeTo(70000, 0.1)))
                .andExpect(jsonPath("$.cost_electric", closeTo(80000, 0.1)));
    }


    @Test
    public void getTypes_ShouldReturnArrayOfTypes() throws Exception {
        // Preparar los datos de prueba
        String[] expectedTypes = {"Tipo1", "Tipo2", "Tipo3"};
        given(repairsServices.getTypes()).willReturn(expectedTypes);

        // Realizar la petición GET y verificar los resultados esperados
        mockMvc.perform(get("/api/v1/repairs/types"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(expectedTypes.length)))
                .andExpect(jsonPath("$[0]", is(expectedTypes[0])))
                .andExpect(jsonPath("$[1]", is(expectedTypes[1])))
                .andExpect(jsonPath("$[2]", is(expectedTypes[2])));
    }

    @Test
    public void getRepairById_ShouldReturnRepairWhenFound() throws Exception {
        // Preparar un objeto reparación que simule el resultado esperado
        long repairId = 1L;
        RepairsEntity foundRepair = new RepairsEntity(repairId, "Reparacion1", "Descripcion1", 50000, 60000, 70000, 80000);
        given(repairsServices.getRepairById(repairId)).willReturn(foundRepair);

        // Realizar la petición GET y verificar que se obtiene la reparación correcta
        mockMvc.perform(get("/api/v1/repairs/{id}", repairId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is((int) repairId)))
                .andExpect(jsonPath("$.type", is(foundRepair.getType())))
                .andExpect(jsonPath("$.description", is(foundRepair.getDescription())));
    }

    @Test
    public void getRepairById_ShouldReturnNotFoundWhenMissing() throws Exception {
        // Simular que el servicio no encuentra la reparación
        long repairId = 1L;
        given(repairsServices.getRepairById(repairId)).willReturn(null);

        // Realizar la petición GET y verificar que la respuesta es 404 Not Found
        mockMvc.perform(get("/api/v1/repairs/{id}", repairId))
                .andExpect(status().isNotFound());
    }





}
