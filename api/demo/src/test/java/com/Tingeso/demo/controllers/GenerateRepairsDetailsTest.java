package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.GenerateRepairsDetailsEntity;
import com.Tingeso.demo.services.GenerateRepairsDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(DiscountRepairsDetailsController.class)
public class GenerateRepairsDetailsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenerateRepairsDetailsService generateRepairsDetailsService;






    /*
    * @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private long reparacion_id;

    private Float total_descuento;

    private Float total_recargos;

    private Float descuento_bono;

    private Float iva;

    private Float monto_reparaciones;

    private Float total;*/

    @Test
    public void getDetailsByIdRepairs() throws Exception{
        GenerateRepairsDetailsEntity details = new GenerateRepairsDetailsEntity(
                1,
                1,
                1000F,
                100F,
                100F,
                100F,
                1000F,
                1000F
        );

        given(generateRepairsDetailsService.findByReparacionId(1)).willReturn(details);

        mockMvc.perform(get("/api/v1/discountrepairsdetails/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.reparacion_id", is(1)))
                .andExpect(jsonPath("$.total_descuento", is(1000.0)))
                .andExpect(jsonPath("$.total_recargos", is(100.0)))
                .andExpect(jsonPath("$.descuento_bono", is(100.0)))
                .andExpect(jsonPath("$.iva", is(100.0)))
                .andExpect(jsonPath("$.monto_reparaciones", is(1000.0)))
                .andExpect(jsonPath("$.total", is(1000.0)));
    }


}
