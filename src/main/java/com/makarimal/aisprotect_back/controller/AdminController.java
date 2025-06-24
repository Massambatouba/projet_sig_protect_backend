package com.makarimal.aisprotect_back.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    // Exemple d'endpoint sécurisé pour l'admin
    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin(@RequestHeader(value = "admin-code", required = false) String code) {
        // Vérifie si le code admin correspond à celui attendu
        System.out.println("Code admin reçu : " + code);
        if (code == null || !"monSuperCodeSecret2024".equals(code)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé");
        }
        return ResponseEntity.ok("Espace admin OK");
    }
}
