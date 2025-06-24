package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.model.Testimonial;
import com.makarimal.aisprotect_back.repository.TestimonialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestimonialService {
    @Autowired
    private final TestimonialRepository testimonialRepository;

    public TestimonialService(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    public List<Testimonial> getAllTestimonials() {
        return testimonialRepository.findAll();
    }

    public List<Testimonial> getActiveTestimonials() {
        return testimonialRepository.findByActiveOrderById(true);
    }

    public Optional<Testimonial> getTestimonialById(Long id) {
        return testimonialRepository.findById(id);
    }

    public Testimonial saveTestimonial(Testimonial testimonial) {
        return testimonialRepository.save(testimonial);
    }

    public void deleteTestimonial(Long id) {
        testimonialRepository.deleteById(id);
    }
}
