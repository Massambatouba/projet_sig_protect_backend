package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.model.Contact;
import com.makarimal.aisprotect_back.model.Devis;
import com.makarimal.aisprotect_back.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    private final JavaMailSender mailSender;

    public ContactService(ContactRepository contactRepository, JavaMailSender mailSender) {
        this.contactRepository = contactRepository;
        this.mailSender = mailSender;
    }

    // Méthode pour supprimer les messages de plus de 2 semaines
    @Scheduled(cron = "0 0 0 * * ?") // Exécute tous les jours à minuit
    public void deleteOldMessages() {
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(2);
        List<Contact> oldMessages = contactRepository.findByCreateDateBefore(twoWeeksAgo);

        if (!oldMessages.isEmpty()) {
            contactRepository.deleteAll(oldMessages);
            System.out.println(oldMessages.size() + " anciens messages supprimés.");
        }
    }

    public Contact markMessageAsRead(Long contactId, boolean read) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        contact.setRead(read);
        return contactRepository.save(contact); // Sauvegarder l'état mis à jour
    }

    public List<Contact> getAllContact() {
        return contactRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
    }

    // Méthode pour traiter le formulaire
    public void handleContactForm(Contact contact) {
        try {
            // Enregistrer le contact dans la base de données
            contactRepository.save(contact);

            // Envoi de l'email à l'administrateur
            sendEmailToAdmin(contact);
            sendConfirmationEmailToClient(contact);
        } catch (Exception e) {
            throw new RuntimeException("Une erreur est survenue lors du traitement du formulaire de contact.", e);
        }
    }

    // Méthode pour envoyer l'email


    private void sendEmailToAdmin(Contact contact) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("samndodia@gmail.com"); // L'email de l'administrateur
        message.setSubject("Nouveau message de contact");
        message.setText("Nom: " + contact.getName() + "\n" +
                "Email: " + contact.getEmail() + "\n" +
                "Téléphone: " + contact.getPhone() + "\n" +
                "Sujet: " + contact.getSubject() + "\n" +
                "Message: " + contact.getMessage());
        mailSender.send(message);  // Envoi de l'email
    }

    private void sendConfirmationEmailToClient(Contact contact) {
        SimpleMailMessage confirmation = new SimpleMailMessage();
        confirmation.setTo(contact.getEmail());
        confirmation.setSubject("Confirmation de votre demande de "+ contact.getSubject());
        confirmation.setText("Bonjour " + contact.getName() + ",\n\n" +
                "Nous avons bien reçu votre demande de " + contact.getSubject() + ".\n" +
                "Notre équipe vous contactera dans les plus brefs délais.\n\n" +
                "Merci de votre confiance.\n\n" +
                "Cordialement,\nL’équipe SiG Protect.");
        mailSender.send(confirmation);
    }
}
