package com.makarimal.aisprotect_back.model;


import jakarta.persistence.*;


import java.util.List;

@Entity
public class About {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String subtitle;
    @Column(length = 1000)
    private String histoire;
    private String mission;
    private String imageName;

    @ElementCollection
    private List<String> values;

    @Lob // Annotation pour stocker des fichiers volumineux (Large Object)
    private String urlImage;

    // Constructeurs, getters et setters


    public About(String title, String subtitle, String histoire, String mission, String imageName, List<String> values, String urlImage) {
        this.title = title;
        this.subtitle = subtitle;
        this.histoire = histoire;
        this.mission = mission;
        this.imageName = imageName;
        this.values = values;
        this.urlImage = urlImage;
    }

    public About() {
    }

    public Long getId() {
        return id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getHistoire() {
        return histoire;
    }

    public void setHistoire(String histoire) {
        this.histoire = histoire;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String image) {
        this.urlImage = urlImage;
    }
}

