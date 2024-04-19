package com.Tingeso.demo.controllers;

import com.Tingeso.demo.entities.GenerateRepairsEntity;
import com.Tingeso.demo.services.GenerateRepairsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/generateRepairs")
@CrossOrigin("*")
public class GenerateRepairsController {

    @Autowired
    GenerateRepairsServices generateRepairsServices;

    @GetMapping("/")
    public ResponseEntity<List<GenerateRepairsEntity>> listGenerateRepairs() {
        List<GenerateRepairsEntity> generateRepairs = generateRepairsServices.getGenerateRepairs();
        return ResponseEntity.ok(generateRepairs);
    }

    @PostMapping("/{uso_bono}")
    public ResponseEntity<Map<String, Object>> saveGenerateRepair(@RequestBody GenerateRepairsEntity generateRepair, @PathVariable boolean uso_bono) {
        Map<String, Object> response = generateRepairsServices.saveGenerateRepairs(generateRepair, uso_bono);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/promedioHorasPorMarca")
    public ResponseEntity<List<Object[]>> getPromedioHorasPorMarca() {
        List<Object[]> promedioHorasPorMarca = generateRepairsServices.getPromedioHorasPorMarca();
        return ResponseEntity.ok(promedioHorasPorMarca);
    }

    @GetMapping("/{idReparacion}")
    public ResponseEntity<GenerateRepairsEntity> getGenerateRepair(@PathVariable int idReparacion) {
        GenerateRepairsEntity generateRepair = generateRepairsServices.getGenerateRepairsById(idReparacion);
        return ResponseEntity.ok(generateRepair);
    }

    @GetMapping("/GenerateGroupByTipoReparacion")
    public ResponseEntity<List<Object[]>> GenerateGroupByTipoReparacion() {
        List<Object[]> GenerateGroupByTipoReparacion = generateRepairsServices.GenerateGroupByTipoReparacion();
        return ResponseEntity.ok(GenerateGroupByTipoReparacion);
    }

    @GetMapping("/GenerateGroupByCombustible")
    public ResponseEntity<List<Object[]>> GenerateGroupByCombustible() {
        List<Object[]> GenerateGroupByCombustible = generateRepairsServices.GenerateGroupByCombustible();
        return ResponseEntity.ok(GenerateGroupByCombustible);
    }
}
