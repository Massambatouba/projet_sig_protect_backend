package com.makarimal.aisprotect_back.repository;

import com.makarimal.aisprotect_back.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    Optional<Admin> findByEmailAndIsActive(String email, Boolean isActive);

    boolean existsByEmail(String email);
}
