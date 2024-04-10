package com.Tingeso.demo.controllers;


import com.Tingeso.demo.entities.BonusesEntity;
import com.Tingeso.demo.services.BonusesServices;
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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(BonusesController.class)
public class BonusesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BonusesServices bonusesServices;

    /*    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;

    private String marca;

    private String disponibilidad;

    private float monto;

     */


    // Test para listar bonos y verificar que se retorne una lista de bonos
    @Test
    public void listBonuses_ShouldReturnBonuses() throws Exception{
        BonusesEntity bonus1 = new BonusesEntity(1L,
                "Bono1",
                "2",
                50000);

        BonusesEntity bonus2 = new BonusesEntity(2L,
                "Bono2",
                "3",
                60000);

        List<BonusesEntity> bonusList = new ArrayList<>(Arrays.asList(bonus1, bonus2));

        given(bonusesServices.getBonuses()).willReturn((ArrayList<BonusesEntity>) bonusList);

        mockMvc.perform(get("/api/v1/bonuses/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].marca", is("Bono1")))
                .andExpect(jsonPath("$[1].marca", is("Bono2")));
    }

    // Test para obtener un bono por id y verificar que se retorne un bono
    @Test
    public void getBonusById_ShouldReturnBonus() throws Exception{
        BonusesEntity bonus = new BonusesEntity(1L,
                "Bono1",
                "2",
                50000);

        given(bonusesServices.getBonusById(1L)).willReturn(bonus);

        mockMvc.perform(get("/api/v1/bonuses/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca", is("Bono1")));
    }

    // Test para guardar un bono y verificar que se retorne el bono guardado
    @Test
    public void saveBonus_ShouldReturnSavedBonus() throws Exception{
        BonusesEntity bonusToSave = new BonusesEntity(1L,
                "Bono1",
                "2",
                50000);
        BonusesEntity savedBonus = new BonusesEntity(1L,
                "Bono1",
                "2",
                50000);

        given(bonusesServices.saveBonus(bonusToSave)).willReturn(savedBonus);

        mockMvc.perform(post("/api/v1/bonuses/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"marca\":\"Bono1\",\"disponibilidad\":\"2\",\"monto\":50000}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca", is("Bono1")));
    }

    // Test para actualizar un bono y verificar que se retorne el bono actualizado
    @Test
    public void updateBonus_ShouldReturnUpdatedBonus() throws Exception{
        BonusesEntity bonus = new BonusesEntity(1L,
                "Bono1",
                "2",
                50000);
        BonusesEntity updatedBonus = new BonusesEntity(1L,
                "Bono1",
                "2",
                50000);

        given(bonusesServices.updateBonus(bonus, 1L)).willReturn(updatedBonus);

        mockMvc.perform(put("/api/v1/bonuses/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"marca\":\"Bono1\",\"disponibilidad\":\"2\",\"monto\":50000}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.marca", is("Bono1")));
    }


}
