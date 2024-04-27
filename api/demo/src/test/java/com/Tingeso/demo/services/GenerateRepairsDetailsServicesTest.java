package com.Tingeso.demo.services;

import com.Tingeso.demo.entities.GenerateRepairsDetailsEntity;
import com.Tingeso.demo.repositories.GenerateRepairsDetailsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@WebMvcTest(GenerateRepairsDetailsService.class)
public class GenerateRepairsDetailsServicesTest {

    @Autowired
    private GenerateRepairsDetailsService generateRepairsDetailsService;

    @MockBean
    private GenerateRepairsDetailsRepository generateRepairsDetailsRepository;

    @Test
    public void findByReparacionId_ShouldReturnDetails() {
        // Preparar
        int reparacionId = 1;
        GenerateRepairsDetailsEntity expectedDetails = new GenerateRepairsDetailsEntity();
        // Configura las propiedades de expectedDetails según sea necesario para el test

        // Mockear
        when(generateRepairsDetailsRepository.findByReparacion_id(reparacionId)).thenReturn(expectedDetails);

        // Ejecutar
        GenerateRepairsDetailsEntity actualDetails = generateRepairsDetailsService.findByReparacionId(reparacionId);

        // Verificar
        assertEquals(expectedDetails, actualDetails);
    }

}
