package com.makarimal.aisprotect_back.controller;

import com.makarimal.aisprotect_back.model.Devis;
import com.makarimal.aisprotect_back.repository.DevisRepository;
import com.makarimal.aisprotect_back.service.DevisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/devis")
public class DevisController {
    @Autowired
    private DevisService devisService;

    @Autowired
    private DevisRepository devisRepository;

@PostMapping
    public ResponseEntity<?> creerDemandeDevis(@RequestBody @Valid Devis devis, BindingResult result) {
        if (result.hasErrors()) {
            // Retourner les erreurs de validation sous forme de réponse
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        Devis savedDevis = devisService.enregistrmentDevis(devis);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevis);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Map<String, Object>> getAllDevisPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        Page<Devis> devisPage = devisService.getAllDevisPaginated(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("content", devisPage.getContent());
        response.put("currentPage", devisPage.getNumber());
        response.put("totalItems", devisPage.getTotalElements());
        response.put("totalPages", devisPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/mark-as-read/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id, @RequestBody Map<String, Boolean> payload) {
        boolean read = payload.get("read");
        Optional<Devis> devisOpt = devisRepository.findById(id);

        if (devisOpt.isPresent()) {
            Devis devis = devisOpt.get();
            devis.setRead(read);
            devisRepository.save(devis);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
