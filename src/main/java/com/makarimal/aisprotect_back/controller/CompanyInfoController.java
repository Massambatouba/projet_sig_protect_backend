package com.makarimal.aisprotect_back.controller;

import com.makarimal.aisprotect_back.model.CompanyInfo;
import com.makarimal.aisprotect_back.repository.CompanyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*")
public class CompanyInfoController {

    @Autowired
    private final CompanyInfoRepository companyInfoRepository;

    public CompanyInfoController(CompanyInfoRepository companyInfoRepository) {
        this.companyInfoRepository = companyInfoRepository;
    }

    @GetMapping("/info")
    public ResponseEntity<CompanyInfo> getCompanyInfo() {
        return ResponseEntity.ok(companyInfoRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Company info not found")));
    }
}
