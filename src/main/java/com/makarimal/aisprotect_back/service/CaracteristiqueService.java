package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.model.Caracteristique;
import com.makarimal.aisprotect_back.model.Service;
import com.makarimal.aisprotect_back.repository.CaracteristiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
@org.springframework.stereotype.Service
public class CaracteristiqueService {
    @Autowired
    private final CaracteristiqueRepository caracteristiqueRepository;

    public CaracteristiqueService(CaracteristiqueRepository caracteristiqueRepository) {
        this.caracteristiqueRepository = caracteristiqueRepository;
    }

    public List<Caracteristique> getAllCaracteristique() {
        return caracteristiqueRepository.findAll();
    }

    public Optional<Caracteristique> getCaracteristiqueById(Long id) {
        return caracteristiqueRepository.findById(id);
    }

    public Caracteristique saveCaracteristique(Caracteristique caracteristique) {
        return caracteristiqueRepository.save(caracteristique);
    }

    public void deleteService(Long id) {
        caracteristiqueRepository.deleteById(id);
    }
}
