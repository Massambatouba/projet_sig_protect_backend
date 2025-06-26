package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.dto.AdminLoginRequest;
import com.makarimal.aisprotect_back.dto.AdminLoginResponse;
import com.makarimal.aisprotect_back.dto.ChangePasswordRequest;
import com.makarimal.aisprotect_back.model.Admin;
import com.makarimal.aisprotect_back.repository.AdminRepository;
import com.makarimal.aisprotect_back.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AdminLoginResponse login(AdminLoginRequest request) {
        log.info("🔍 Recherche de l'admin avec email: '{}'", request.getEmail());

        // ✅ Vérification de l'email avec trim pour éviter les espaces
        String email = request.getEmail().trim();

        Admin admin = adminRepository.findByEmailAndIsActive(email, true)
                .orElseThrow(() -> {
                    log.error("❌ Admin non trouvé pour l'email: '{}'", email);

                    // ✅ Debug: lister tous les admins pour vérifier
                    adminRepository.findAll().forEach(a ->
                            log.info("📋 Admin en base: email='{}', active={}", a.getEmail(), a.getIsActive())
                    );

                    return new RuntimeException("Email ou mot de passe incorrect");
                });

        log.info("✅ Admin trouvé: {} {}", admin.getFirstName(), admin.getLastName());

        // ✅ Vérification du mot de passe avec logs détaillés
        String rawPassword = request.getPassword();
        String encodedPassword = admin.getPassword();

        log.info("🔐 Vérification du mot de passe...");
        log.info("📝 Mot de passe fourni (longueur): {}", rawPassword.length());
        log.info("🔒 Mot de passe encodé en base: {}", encodedPassword.substring(0, 10) + "...");

        boolean passwordMatches = passwordEncoder.matches(rawPassword, encodedPassword);
        log.info("🎯 Résultat de la vérification: {}", passwordMatches);

        if (!passwordMatches) {
            log.error("❌ Mot de passe incorrect pour: {}", email);
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        // Mise à jour de la dernière connexion
        admin.setLastLogin(LocalDateTime.now());
        adminRepository.save(admin);

        // Génération du token JWT
        String token = jwtUtil.generateToken(admin.getEmail());

        log.info("✅ Token généré avec succès pour: {}", email);

        return new AdminLoginResponse(token, admin.getEmail(), admin.getFirstName(), admin.getLastName());
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));

        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) {
            throw new RuntimeException("Ancien mot de passe incorrect");
        }

        // Vérifier que les nouveaux mots de passe correspondent
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Les nouveaux mots de passe ne correspondent pas");
        }

        // Encoder et sauvegarder le nouveau mot de passe
        admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        adminRepository.save(admin);
    }

    public Admin createAdmin(String email, String password, String firstName, String lastName) {
        // ✅ Vérification avec trim pour éviter les doublons dus aux espaces
        String cleanEmail = email.trim();

        if (adminRepository.existsByEmail(cleanEmail)) {
            log.info("📧 Admin existe déjà pour l'email: {}", cleanEmail);
            throw new RuntimeException("Un administrateur avec cet email existe déjà");
        }

        Admin admin = new Admin();
        admin.setEmail(cleanEmail);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setFirstName(firstName);
        admin.setLastName(lastName);
        admin.setIsActive(true);

        Admin savedAdmin = adminRepository.save(admin);
        log.info("✅ Admin créé avec succès: {} {} ({})", firstName, lastName, cleanEmail);

        return savedAdmin;
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email.trim())
                .orElseThrow(() -> new RuntimeException("Administrateur non trouvé"));
    }
}
