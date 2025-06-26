package com.makarimal.aisprotect_back.config;

import com.makarimal.aisprotect_back.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        // Créer un admin par défaut s'il n'existe pas
        try {
            adminService.createAdmin(
                    "samndodia@gmail.com",
                    "06101992Toub@",
                    "Massamba",
                    "Thiam"
            );
            log.info("✅ Admin par défaut créé: samndodia@gmail.com / 06101992Toub@");
        } catch (RuntimeException e) {
            log.info("📧 Admin par défaut existe déjà: {}", e.getMessage());
        }

        // ✅ Vérification que l'admin est bien créé
        try {
            var admin = adminService.findByEmail("samndodia@gmail.com");
            log.info("🔍 Vérification admin: {} {} - Actif: {}",
                    admin.getFirstName(), admin.getLastName(), admin.getIsActive());
        } catch (Exception e) {
            log.error("❌ Erreur lors de la vérification de l'admin: {}", e.getMessage());
        }
    }
}