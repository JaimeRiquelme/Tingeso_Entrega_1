package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.DiscountRepairsEntity;
import com.Tingeso.demo.services.DiscountRepairsServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;



@WebMvcTest(DiscountRepairsController.class)
public class DiscountRepairsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiscountRepairsServices discountRepairsServices;


    @Test
    public void saveDiscountRepairs_ShouldReturnDiscountRepairs() throws Exception{
        DiscountRepairsEntity discountRepairsEntity = new DiscountRepairsEntity();
        discountRepairsEntity.setId(1);
        discountRepairsEntity.setReparaciones_minimas(1);
        discountRepairsEntity.setReparaciones_maximas(5);
        discountRepairsEntity.setGasolina(0.1f);
        discountRepairsEntity.setDiesel(0.2f);
        discountRepairsEntity.setHibrido(0.3f);
        discountRepairsEntity.setElectrico(0.4f);

        given(discountRepairsServices.saveDiscountRepairs(any(DiscountRepairsEntity.class))).willReturn(discountRepairsEntity);

        mockMvc.perform(post("/api/v1/discountrepairs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"reparaciones_minimas\":1,\"reparaciones_maximas\":5,\"gasolina\":0.1,\"diesel\":0.2,\"hibrido\":0.3,\"electrico\":0.4}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.reparaciones_minimas", is(1)))
                .andExpect(jsonPath("$.reparaciones_maximas", is(5)))
                .andExpect(jsonPath("$.gasolina", is(0.1)))
                .andExpect(jsonPath("$.diesel", is(0.2)))
                .andExpect(jsonPath("$.hibrido", is(0.3)))
                .andExpect(jsonPath("$.electrico", is(0.4)));
    }


    //Metodo para obtener dado un id un descuento
    @Test
    public void findByIdDiscount_ShouldReturnDiscountRepairs() throws Exception {
        DiscountRepairsEntity discountRepairsEntity = new DiscountRepairsEntity();
        discountRepairsEntity.setId(1);
        discountRepairsEntity.setReparaciones_minimas(1);
        discountRepairsEntity.setReparaciones_maximas(5);
        discountRepairsEntity.setGasolina(0.1f);
        discountRepairsEntity.setDiesel(0.2f);
        discountRepairsEntity.setHibrido(0.3f);
        discountRepairsEntity.setElectrico(0.4f);

        given(discountRepairsServices.findByIdDiscount(1)).willReturn(discountRepairsEntity);

        mockMvc.perform(get("/api/v1/discountrepairs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.reparaciones_minimas", is(1)))
                .andExpect(jsonPath("$.reparaciones_maximas", is(5)))
                .andExpect(jsonPath("$.gasolina", is(0.1)))
                .andExpect(jsonPath("$.diesel", is(0.2)))
                .andExpect(jsonPath("$.hibrido", is(0.3)))
                .andExpect(jsonPath("$.electrico", is(0.4)));
    }


}
