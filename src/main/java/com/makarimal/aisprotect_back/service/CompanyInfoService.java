package com.makarimal.aisprotect_back.service;

import com.makarimal.aisprotect_back.model.CompanyInfo;
import com.makarimal.aisprotect_back.repository.CompanyInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyInfoService {

    @Autowired
    private final CompanyInfoRepository companyInfoRepository;

    public CompanyInfoService(CompanyInfoRepository companyInfoRepository) {
        this.companyInfoRepository = companyInfoRepository;
    }

    public CompanyInfo getCompanyInfo() {
        return companyInfoRepository.findById(1L).orElseThrow(() -> new RuntimeException("Company info not found"));
    }
}
