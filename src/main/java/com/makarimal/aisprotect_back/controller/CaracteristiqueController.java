package com.makarimal.aisprotect_back.controller;

import com.makarimal.aisprotect_back.model.Caracteristique;
import com.makarimal.aisprotect_back.repository.CaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/caracteristiques")
@CrossOrigin(origins = "*")
public class CaracteristiqueController {
    @Autowired
    private final CaracteristiqueRepository caracteristiqueRepository;

    public CaracteristiqueController(CaracteristiqueRepository caracteristiqueRepository) {
        this.caracteristiqueRepository = caracteristiqueRepository;
    }

    @GetMapping
    public ResponseEntity<List<Caracteristique>> getAllFeatures() {
        List<Caracteristique> caracteristiques = caracteristiqueRepository.findAll();
        return ResponseEntity.ok(caracteristiques);
    }
}
