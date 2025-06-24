package com.makarimal.aisprotect_back.controller;
import com.makarimal.aisprotect_back.model.Testimonial;
import com.makarimal.aisprotect_back.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testimonials")
@CrossOrigin(origins = "*")
public class TestimonialController {
    @Autowired
    private final TestimonialService testimonialService;

    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }
    @PostMapping()
    public ResponseEntity<Testimonial> saveTestimonial(@RequestBody Testimonial testimonial) {
        Testimonial savedTestimonial = testimonialService.saveTestimonial(testimonial);
        return ResponseEntity.ok(savedTestimonial);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<Testimonial>> getActiveTestimonials() {
        List<Testimonial> testimonials = testimonialService.getActiveTestimonials();
        return ResponseEntity.ok(testimonials);
    }
}