package com.makarimal.aisprotect_back.repository;

import com.makarimal.aisprotect_back.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findByReadOrderByCreatedAtDesc(boolean read);
}