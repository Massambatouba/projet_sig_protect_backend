package com.makarimal.aisprotect_back.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.makarimal.aisprotect_back.model.Service;
import com.makarimal.aisprotect_back.model.ServiceImage;
import com.makarimal.aisprotect_back.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {

    @Autowired
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        return ResponseEntity.ok(serviceService.getAllServices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public String home() {
        return "Bienvenue sur l'API AISProtect 🚀";
    }


    @PostMapping("/add")
    public ResponseEntity<?> createService(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam(required = false) String icon,
            @RequestParam("features") List<String> features,
            @RequestParam List<MultipartFile> images) {
        try {
            Service savedService = serviceService.addService(name, description, icon, features, images);
            return ResponseEntity.ok(savedService);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'enregistrement des images !");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServiceWithImages(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String icon,
            @RequestParam(value = "features", required = false) String featuresJson,
            @RequestParam(required = false) List<MultipartFile> imageFiles,
            @RequestParam(required = false) List<Long> deleteImageIds
    ) {
        try {
            Optional<Service> existingServiceOpt = serviceService.getServiceById(id);
            if (existingServiceOpt.isEmpty()) return ResponseEntity.notFound().build();

            Service service = existingServiceOpt.get();
            service.setName(name);
            service.setDescription(description);
            service.setIcon(icon);
            List<String> features = new ObjectMapper().readValue(featuresJson, new TypeReference<>() {});
            service.setFeatures(features);

            if (deleteImageIds != null) {
                for (Long imageId : deleteImageIds) {
                    serviceService.deleteImage(imageId);
                }
            }

            if (imageFiles != null && !imageFiles.isEmpty()) {
                List<ServiceImage> newImages = serviceService.saveImages(imageFiles, service);
                service.getImages().addAll(newImages);
            }

            Service updatedService = serviceService.saveService(service);
            return ResponseEntity.ok(updatedService);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur lors de la modification du service : " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        return serviceService.getServiceById(id)
                .map(service -> {
                    serviceService.deleteService(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
