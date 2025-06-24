package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.model.About;
import com.makarimal.aisprotect_back.repository.AboutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AboutService {
    @Autowired
    private AboutRepository aboutRepository;

    public About getAboutData() {
        return aboutRepository.findById(1L).orElse(null); // Suppose qu'il n'y a qu'un seul enregistrement
    }

}
