package com.example.hospital.controller;

import com.example.hospital.model.Site;
import com.example.hospital.repository.NurseSiteRepository;
import com.example.hospital.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/sites")
public class SiteController {

    @Autowired
    private SiteService siteService;

    @Autowired
    private NurseSiteRepository nurseSiteRepository; // ← 補上這行

    // 顯示所有站點
    @GetMapping
    public String list(Model model) {
        model.addAttribute("sites", siteService.findAll());
        return "site/list";
    }

    // 顯示新增站點表單
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("site", new Site());
        return "site/form";
    }

    // 儲存站點（新增或修改）
    @PostMapping
    public String save(@ModelAttribute Site site) {
        siteService.save(site);
        return "redirect:/sites";
    }

    // 顯示編輯站點表單
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Site site = siteService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid site Id: " + id));
        model.addAttribute("site", site);
        model.addAttribute("nurseSites", nurseSiteRepository.findBySiteId(id));
        return "site/form";
    }

    // 刪除站點
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        siteService.deleteById(id);
        return "redirect:/sites";
    }
}
