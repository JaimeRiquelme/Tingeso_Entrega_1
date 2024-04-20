package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import com.Tingeso.demo.services.GenerateRepairsServices;
import io.micrometer.common.KeyValue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.LocalTime;

import java.util.*;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenerateRepairsController.class)
public class GenerateRepairsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenerateRepairsServices generateRepairsServices;

    /*Atributos
    *
    * @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private LocalDateTime fecha_ingreso_taller;

    private LocalTime hora_ingreso_taller;

    //Como puede tener mas de un tipo de reparacion, podriamos hacer una lista de ids de reparaciones
    private String tipo_reparacion;

    private Float monto_total_reparacion;

    private LocalDateTime fecha_salida_reparacion;

    private LocalTime hora_salida_reparacion;

    private LocalDateTime fecha_entrega_cliente;

    private LocalTime hora_entrega_cliente;

    private String patente_vehiculo; //Aqui podriamos relacionar */

    @Test
    public void listRepairs_shouldReturnRepairs() throws Exception {
        // Arrange
        GenerateRepairsEntity repair1 = new GenerateRepairsEntity(1L,
                LocalDateTime.of(2021, 10, 10, 10, 10),
                LocalTime.of(10, 10),
                "Cambio de aceite",
                50000f,
                LocalDateTime.of(2021, 10, 10, 17, 10),
                LocalTime.of(17, 10),
                LocalDateTime.of(2021, 10, 11, 9, 10),
                LocalTime.of(9, 10),
                "AB1234");

        GenerateRepairsEntity repair2 = new GenerateRepairsEntity(2L,
                LocalDateTime.of(2021, 10, 12, 11, 10),
                LocalTime.of(11, 10),
                "Reparación de frenos",
                75000f,
                LocalDateTime.of(2021, 10, 12, 18, 10),
                LocalTime.of(18, 10),
                LocalDateTime.of(2021, 10, 13, 10, 10),
                LocalTime.of(10, 10),
                "BC2345");

        List<GenerateRepairsEntity> repairList = new ArrayList<>(Arrays.asList(repair1, repair2));

        given(generateRepairsServices.getGenerateRepairs()).willReturn((ArrayList<GenerateRepairsEntity>) repairList);
        // Act & Assert
        mockMvc.perform(get("/api/v1/generateRepairs/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].tipo_reparacion", is("Cambio de aceite")))
                .andExpect(jsonPath("$[0].monto_total_reparacion", is(50000.0)))
                .andExpect(jsonPath("$[0].patente_vehiculo", is("AB1234")));

    }

    @Test
    public void saveAndRetrieveGenerateRepair_ShouldWorkCorrectly() throws Exception {
        // Arrange for POST
        GenerateRepairsEntity repairToSave = new GenerateRepairsEntity(
                1L,
                LocalDateTime.of(2021, 10, 10, 10, 10),
                LocalTime.of(10, 10),
                "Cambio de aceite",
                50000f,
                LocalDateTime.of(2021, 10, 10, 17, 10),
                LocalTime.of(17, 10),
                LocalDateTime.of(2021, 10, 11, 9, 10),
                LocalTime.of(9, 10),
                "AB1234");


        given(generateRepairsServices.saveGenerateRepairs(Mockito.any(GenerateRepairsEntity.class), eq(true)))
                .willReturn(Map.of("generateRepair", repairToSave, "message", "Reparación guardada con éxito"));

        String repairJson = """
                {
                    "fechaIngresoTaller": "2021-10-10T10:10:00",
                    "horaIngresoTaller": "10:10",
                    "tipoReparacion": "Cambio de aceite",
                    "montoTotalReparacion": 50000,
                    "fechaSalidaReparacion": "2021-10-10T17:10:00",
                    "horaSalidaReparacion": "17:10",
                    "fechaEntregaCliente": "2021-10-11T09:10:00",
                    "horaEntregaCliente": "09:10",
                    "patenteVehiculo": "AB1234"
                }
                """;

        mockMvc.perform(post("/api/v1/generateRepairs/true")
                .contentType(MediaType.APPLICATION_JSON)
                .content(repairJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.generateRepair.id", is(1)))
                .andExpect(jsonPath("$.generateRepair.tipo_reparacion", is("Cambio de aceite")))
                .andExpect(jsonPath("$.generateRepair.monto_total_reparacion", is(50000.0)))
                .andExpect(jsonPath("$.generateRepair.patente_vehiculo", is("AB1234")))
                .andExpect(jsonPath("$.message", is("Reparación guardada con éxito")));

    }

    @Test
    public void getPromedioHorasPorMarca_ShouldReturnAverageHoursPerBrand() throws Exception {
        // Arrange
        List<Object[]> averageHoursPerBrand = new ArrayList<>();
        averageHoursPerBrand.add(new Object[]{"Toyota", 5.0});
        averageHoursPerBrand.add(new Object[]{"Ford", 6.0});

        given(generateRepairsServices.getPromedioHorasPorMarca()).willReturn(averageHoursPerBrand);

        // Act & Assert
        mockMvc.perform(get("/api/v1/generateRepairs/promedioHorasPorMarca"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0]", is("Toyota")))
                .andExpect(jsonPath("$[0][1]", is(5.0)))
                .andExpect(jsonPath("$[1][0]", is("Ford")))
                .andExpect(jsonPath("$[1][1]", is(6.0)));
    }

    @Test
    public void getGenerateRepairById_ShouldReturnRepair() throws Exception {
        // Arrange
        int repairId = 1;
        GenerateRepairsEntity foundRepair = new GenerateRepairsEntity(
                repairId,
                LocalDateTime.of(2021, 10, 10, 10, 10),
                LocalTime.of(10, 10),
                "Cambio de aceite",
                50000f,
                LocalDateTime.of(2021, 10, 10, 17, 10),
                LocalTime.of(17, 10),
                LocalDateTime.of(2021, 10, 11, 9, 10),
                LocalTime.of(9, 10),
                "AB1234"
        );

        given(generateRepairsServices.getGenerateRepairsById(repairId)).willReturn(foundRepair);

        // Act & Assert
        mockMvc.perform(get("/api/v1/generateRepairs/{idReparacion}", repairId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(repairId)))
                .andExpect(jsonPath("$.tipo_reparacion", is("Cambio de aceite")))
                .andExpect(jsonPath("$.monto_total_reparacion", is(50000.0)));
    }

    @Test
    public void generateGroupByTipoReparacion_ShouldReturnGroupedData() throws Exception {
        // Arrange
        List<Object[]> groupedData = new ArrayList<>();
        groupedData.add(new Object[]{"Cambio de aceite", 2, 50000.0}); // Tipo de reparación, Cantidad, Promedio de monto
        groupedData.add(new Object[]{"Revisión de frenos", 1, 75000.0});

        given(generateRepairsServices.GenerateGroupByTipoReparacion()).willReturn(groupedData);

        // Act & Assert
        mockMvc.perform(get("/api/v1/generateRepairs/GenerateGroupByTipoReparacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0]", is("Cambio de aceite")))
                .andExpect(jsonPath("$[0][1]", is(2)))
                .andExpect(jsonPath("$[0][2]", is(50000.0)))
                .andExpect(jsonPath("$[1][0]", is("Revisión de frenos")))
                .andExpect(jsonPath("$[1][1]", is(1)))
                .andExpect(jsonPath("$[1][2]", is(75000.0)));
    }

    @Test
    public void generateGroupByCombustible_ShouldReturnGroupedData() throws Exception {
        // Arrange
        List<Object[]> groupedDataByFuelType = new ArrayList<>();
        groupedDataByFuelType.add(new Object[]{"Gasolina", 3, 45000.0}); // Tipo de combustible, Cantidad, Promedio de monto
        groupedDataByFuelType.add(new Object[]{"Diesel", 5, 60000.0});

        given(generateRepairsServices.GenerateGroupByCombustible()).willReturn(groupedDataByFuelType);

        // Act & Assert
        mockMvc.perform(get("/api/v1/generateRepairs/GenerateGroupByCombustible"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0][0]", is("Gasolina")))
                .andExpect(jsonPath("$[0][1]", is(3)))
                .andExpect(jsonPath("$[0][2]", is(45000.0)))
                .andExpect(jsonPath("$[1][0]", is("Diesel")))
                .andExpect(jsonPath("$[1][1]", is(5)))
                .andExpect(jsonPath("$[1][2]", is(60000.0)));
    }






}
