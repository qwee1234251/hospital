package com.example.hospital.repository;

import com.example.hospital.model.NurseSite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NurseSiteRepository extends JpaRepository<NurseSite, Long> {
    List<NurseSite> findBySiteId(Long siteId);
    List<NurseSite> findByNurseEmployeeId(String nurseId);
}
