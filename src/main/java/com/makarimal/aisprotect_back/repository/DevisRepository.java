package com.makarimal.aisprotect_back.repository;

import com.makarimal.aisprotect_back.model.Devis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevisRepository extends JpaRepository<Devis, Long> {
    Page<Devis> findAll(Pageable pageable);
}
