package com.example.hospital.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class NurseSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse nurse;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    private LocalDateTime joinTime;

    public NurseSite() {}

    public NurseSite(Nurse nurse, Site site, LocalDateTime joinTime) {
        this.nurse = nurse;
        this.site = site;
        this.joinTime = joinTime;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public Nurse getNurse() { return nurse; }
    public void setNurse(Nurse nurse) { this.nurse = nurse; }

    public Site getSite() { return site; }
    public void setSite(Site site) { this.site = site; }

    public LocalDateTime getJoinTime() { return joinTime; }
    public void setJoinTime(LocalDateTime joinTime) { this.joinTime = joinTime; }
}
