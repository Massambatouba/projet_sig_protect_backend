/*package com.makarimal.aisprotect_back.controller;

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

 */
package com.makarimal.aisprotect_back.controller;

import com.makarimal.aisprotect_back.dto.AdminLoginRequest;
import com.makarimal.aisprotect_back.dto.AdminLoginResponse;
import com.makarimal.aisprotect_back.dto.ChangePasswordRequest;
import com.makarimal.aisprotect_back.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@Valid @RequestBody AdminLoginRequest request) {
        // ✅ Logs détaillés pour debug
        log.info("🔐 Tentative de connexion pour l'email: {}", request.getEmail());
        log.info("📧 Email reçu: '{}'", request.getEmail());
        log.info("🔍 Longueur du mot de passe: {}", request.getPassword() != null ? request.getPassword().length() : "null");
        try {
            AdminLoginResponse response = adminService.login(request);
            log.info("✅ Connexion réussie pour: {}", request.getEmail());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("❌ Erreur de connexion pour {}: {}", request.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName();
            adminService.changePassword(email, request);
            return ResponseEntity.ok("Mot de passe modifié avec succès");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        try {
            String email = authentication.getName();
            var admin = adminService.findByEmail(email);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Endpoint de test pour vérifier la connectivité
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.info("🏥 Health check appelé");
        return ResponseEntity.ok("Backend accessible");
    }

    // Endpoint pour vérifier l'accès admin (compatible avec votre code existant)
    @GetMapping("/access")
    public ResponseEntity<String> checkAccess(Authentication authentication) {
        return ResponseEntity.ok("Espace admin OK");
    }
}