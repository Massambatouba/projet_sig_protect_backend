package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.model.Contact;
import com.makarimal.aisprotect_back.model.Devis;
import com.makarimal.aisprotect_back.repository.DevisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DevisService {
    @Autowired
    private DevisRepository devisRepository;

    private final JavaMailSender mailSender;

    public DevisService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public Devis enregistrmentDevis(Devis devis) {
        Devis savedDevis = devisRepository.save(devis);
        sendEmailToAdmin(savedDevis);
        sendConfirmationEmailToClient(savedDevis);
        return savedDevis;
    }

    public Page<Devis> getAllDevisPaginated(Pageable pageable) {
        return devisRepository.findAll(pageable);
    }

    private void sendEmailToAdmin(Devis devis) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("samndodia@gmail.com"); // L'email de l'administrateur
        message.setSubject("Nouveau message demande de Devis");
        message.setText("Nom: " + devis.getName() + "\n" +
                "Email: " + devis.getEmail() + "\n" +
                "Téléphone: " + devis.getPhone() + "\n" +
                "Message: " + devis.getMessage());
        mailSender.send(message);  // Envoi de l'email
    }

    private void sendConfirmationEmailToClient(Devis devis) {
        SimpleMailMessage confirmation = new SimpleMailMessage();
        confirmation.setTo(devis.getEmail());
        confirmation.setSubject("Confirmation de votre demande de devis");
        confirmation.setText("Bonjour " + devis.getName() + ",\n\n" +
                "Nous avons bien reçu votre demande de devis.\n" +
                "Notre équipe vous contactera dans les plus brefs délais.\n\n" +
                "Merci de votre confiance.\n\n" +
                "Cordialement,\nL’équipe SiG Protect.");
        mailSender.send(confirmation);
    }
}
