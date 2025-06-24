package com.makarimal.aisprotect_back.repository;

import com.makarimal.aisprotect_back.model.Caracteristique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CaracteristiqueRepository extends JpaRepository<Caracteristique,Long> {
}
