package com.makarimal.aisprotect_back.repository;


import com.makarimal.aisprotect_back.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByCreateDateBefore(LocalDateTime date);
    List<Contact> findAllByOrderByCreateDateDesc();
}
