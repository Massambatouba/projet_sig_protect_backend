package com.makarimal.aisprotect_back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

@Entity
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "le nom est requis")
    private String name;

    @Column(nullable = false)
    private Boolean read = false; // Utilisation de Boolean pour permettre null

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        if (this.read == null) {
            this.read = false;  // Assure-toi que 'read' n'est jamais null
        }
        this.createDate = LocalDateTime.now();
    }

    public Devis(String name, Boolean read, LocalDateTime createDate, String email, String phone, String message) {
        this.name = name;
        this.read = (read != null) ? read : false;  // Assure-toi que 'read' n'est jamais null
        this.createDate = createDate;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public Devis(String name, Boolean read, String email, String phone, String message) {
        this.name = name;
        this.read = (read != null) ? read : false;  // Assure-toi que 'read' n'est jamais null
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public boolean isRead() {
        return read != null && read; // Retourne false si read est null
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    @NotBlank(message = "L'email est requis")
    @Email(message = "L'email n'est pas valide")
    private String email;

    @NotBlank(message = "Le téléphone est requis")
    @Pattern(regexp = "^[0-9]{10}$", message = "Le téléphone doit contenir 10 chiffres")
    private String phone;

    private String message;

    // Constructeur sans paramètres
    public Devis() {}

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
