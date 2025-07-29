package com.example.hospital.service;

import com.example.hospital.model.Nurse;
import com.example.hospital.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import jakarta.transaction.Transactional;

@Service
public class NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    public List<Nurse> findAll() {
        return nurseRepository.findAll();
    }

    public Optional<Nurse> findById(String id) {
        return nurseRepository.findById(id);
    }

    @Transactional
    public void save(Nurse nurse) {
        // 自動透過 CascadeType.ALL 儲存 NurseSite 關聯
        nurseRepository.save(nurse);
    }


    public void deleteById(String id) {
        nurseRepository.deleteById(id);
    }
}

