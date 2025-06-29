package com.makarimal.aisprotect_back.repository;


import com.makarimal.aisprotect_back.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
