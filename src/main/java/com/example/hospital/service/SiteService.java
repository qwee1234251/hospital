package com.example.hospital.service;

import com.example.hospital.model.Site;
import com.example.hospital.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SiteService {

    @Autowired
    private SiteRepository siteRepository;

    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    public Optional<Site> findById(Long id) {
        return siteRepository.findById(id);
    }

    public Site save(Site site) {
        return siteRepository.save(site);
    }

    public void deleteById(Long id) {
        siteRepository.deleteById(id);
    }
    
    public List<Site> findAllByIds(List<Long> ids) {
        return siteRepository.findAllById(ids);
    }
}

