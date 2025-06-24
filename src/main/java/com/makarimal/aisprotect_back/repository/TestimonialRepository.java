package com.makarimal.aisprotect_back.repository;

import com.makarimal.aisprotect_back.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    List<Testimonial> findByActiveOrderById(boolean active);
}

