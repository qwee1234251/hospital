package com.example.hospital.controller;

import com.example.hospital.model.Nurse;
import com.example.hospital.model.NurseSite;
import com.example.hospital.model.Site;
import com.example.hospital.service.NurseService;
import com.example.hospital.service.SiteService;
import com.example.hospital.repository.NurseSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/nurses")
public class NurseController {

    @Autowired
    private NurseService nurseService;

    @Autowired
    private SiteService siteService;

    @Autowired
    private NurseSiteRepository nurseSiteRepository;

    // 護士列表
    @GetMapping
    public String list(Model model) {
        model.addAttribute("nurses", nurseService.findAll());
        return "nurse/list";
    }

    // 新增表單
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("nurse", new Nurse());
        model.addAttribute("joinedSites", Collections.emptyList());
        model.addAttribute("availableSites", siteService.findAll());
        return "nurse/form";
    }

    // 編輯表單
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        Nurse nurse = nurseService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid nurse ID: " + id));
        Set<Site> joinedSites = nurse.getNurseSites()
                                     .stream()
                                     .map(NurseSite::getSite)
                                     .collect(Collectors.toSet());
        List<Site> availableSites = siteService.findAll()
                                               .stream()
                                               .filter(site -> !joinedSites.contains(site))
                                               .collect(Collectors.toList());

        model.addAttribute("nurse", nurse);
        model.addAttribute("joinedSites", joinedSites);
        model.addAttribute("availableSites", availableSites);
        return "nurse/form";
    }

    // 儲存（修正版本）
    @PostMapping
    public String save(@ModelAttribute Nurse nurse,
                       @RequestParam(value = "joinedSiteIds", required = false) List<Long> joinedSiteIds) {

        // 檢查是否為更新操作
        Nurse existing = nurseService.findById(nurse.getEmployeeId()).orElse(null);
        
        if (existing != null) {
            // 更新現有護士
            existing.setName(nurse.getName());
            
            // 清除現有的 NurseSite 關聯
            existing.getNurseSites().clear();
            
            // 添加新的關聯
            if (joinedSiteIds != null) {
                for (Long siteId : joinedSiteIds) {
                    Site site = siteService.findById(siteId).orElse(null);
                    if (site != null) {
                        NurseSite ns = new NurseSite();
                        ns.setNurse(existing);
                        ns.setSite(site);
                        ns.setJoinTime(LocalDateTime.now());
                        existing.getNurseSites().add(ns);
                    }
                }
            }
            
            nurseService.save(existing);
        } else {
            // 新增護士
            nurse.setNurseSites(new ArrayList<>());
            
            if (joinedSiteIds != null) {
                for (Long siteId : joinedSiteIds) {
                    Site site = siteService.findById(siteId).orElse(null);
                    if (site != null) {
                        NurseSite ns = new NurseSite();
                        ns.setNurse(nurse);
                        ns.setSite(site);
                        ns.setJoinTime(LocalDateTime.now());
                        nurse.getNurseSites().add(ns);
                    }
                }
            }
            
            nurseService.save(nurse);
        }

        return "redirect:/nurses";
    }

    // 刪除
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        nurseService.deleteById(id);
        return "redirect:/nurses";
    }

    // 首頁導向
    @GetMapping("/home")
    public String goHome() {
        return "redirect:/";
    }
}