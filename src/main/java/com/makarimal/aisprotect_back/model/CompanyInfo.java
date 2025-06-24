package com.makarimal.aisprotect_back.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
public class CompanyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slogan;
    private int foundedYear;

    @Column(length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(name = "company_contact", joinColumns = @JoinColumn(name = "company_id"))
    @MapKeyColumn(name = "type")
    @Column(name = "value")
    private Map<String, String> contact;

    @ElementCollection
    @CollectionTable(name = "company_social_media", joinColumns = @JoinColumn(name = "company_id"))
    @MapKeyColumn(name = "platform")
    @Column(name = "url")
    private Map<String, String> socialMedia;

    public Long getId() {
        return id;
    }

    public CompanyInfo(Long id, String name, String slogan, int foundedYear, String description, Map<String, String> contact, Map<String, String> socialMedia) {
        this.id = id;
        this.name = name;
        this.slogan = slogan;
        this.foundedYear = foundedYear;
        this.description = description;
        this.contact = contact;
        this.socialMedia = socialMedia;
    }

    public CompanyInfo() {
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

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(int foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getContact() {
        return contact;
    }

    public void setContact(Map<String, String> contact) {
        this.contact = contact;
    }

    public Map<String, String> getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(Map<String, String> socialMedia) {
        this.socialMedia = socialMedia;
    }
}

