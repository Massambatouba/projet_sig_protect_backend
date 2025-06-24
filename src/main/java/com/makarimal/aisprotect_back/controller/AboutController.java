package com.makarimal.aisprotect_back.controller;

import com.makarimal.aisprotect_back.model.About;
import com.makarimal.aisprotect_back.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/about")
@CrossOrigin(origins = "http://localhost:4200")
public class AboutController {
    @Autowired
    private AboutService aboutService;

    @GetMapping
    public About getAboutData() {
        return aboutService.getAboutData();
    }

}
