package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.model.Service;
import com.makarimal.aisprotect_back.model.ServiceImage;
import com.makarimal.aisprotect_back.repository.ServiceImageRepository;
import com.makarimal.aisprotect_back.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceImageRepository serviceImageRepository;

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    public ServiceService(ServiceRepository serviceRepository, ServiceImageRepository serviceImageRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceImageRepository = serviceImageRepository;
    }

    public Service addService(String name, String description, String icon, List<String> features, List<MultipartFile> imageFiles) throws IOException {
        if (imageFiles == null || imageFiles.isEmpty()) {
            throw new IllegalArgumentException("Un service doit avoir au moins une image !");
        }

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        Service service = new Service();
        service.setName(name);
        service.setDescription(description);
        service.setIcon(icon);
        service.setFeatures(features);
        Service savedService = serviceRepository.save(service);

        List<ServiceImage> images = saveImages(imageFiles, savedService);
        serviceImageRepository.saveAll(images);

        savedService.setImages(images);
        return savedService;
    }

    public Service addImagesToService(Long serviceId, List<MultipartFile> imageFiles) throws IOException {
        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new RuntimeException("Service introuvable !"));
        List<ServiceImage> images = saveImages(imageFiles, service);
        serviceImageRepository.saveAll(images);
        service.getImages().addAll(images);
        return serviceRepository.save(service);
    }

    public void deleteImage(Long imageId) {
        ServiceImage image = serviceImageRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image introuvable !"));
        Service service = image.getService();

        if (service.getImages().size() <= 1) {
            throw new RuntimeException("Un service doit avoir au moins une image !");
        }

        try {
            Path imagePath = Paths.get(uploadDir).resolve(image.getImageName()).normalize();
            Files.deleteIfExists(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        service.getImages().remove(image);
        serviceImageRepository.delete(image);
    }

    public List<ServiceImage> saveImages(List<MultipartFile> imageFiles, Service service) throws IOException {
        List<ServiceImage> images = new ArrayList<>();
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path targetLocation = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

                ServiceImage serviceImage = new ServiceImage();
                serviceImage.setImageUrl("/uploads/" + fileName);
                serviceImage.setImageName(fileName);
                serviceImage.setService(service);
                images.add(serviceImage);
            }
        }

        return images;
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<Service> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }

    public Service saveService(Service service) {
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.findById(id).ifPresent(service -> {
            for (ServiceImage image : service.getImages()) {
                try {
                    Path imagePath = Paths.get(uploadDir).resolve(image.getImageName()).normalize();
                    Files.deleteIfExists(imagePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serviceRepository.deleteById(id);
        });
    }
}
