package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Region;
import com.example.service_interface.RegionService;

@Controller
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    // LIST
    @GetMapping
    public String listRegions(Model model) {

        model.addAttribute("regions", regionService.getAllRegions());

        return "regions/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String regionDetail(
            @PathVariable Integer id,
            Model model) {

        Region region = regionService.getRegionById(id);

        model.addAttribute(
                "region",
                region);

        return "regions/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "region",
                new Region());

        return "regions/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createRegion(
            @ModelAttribute Region region) {

        regionService.saveRegion(region);

        return "redirect:/regions";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        Region region = regionService.getRegionById(id);

        System.out.println("Editing region: " + region.toString());

        model.addAttribute(
                "region",
                region);

        return "regions/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editRegion(
            @PathVariable Integer id,
            @ModelAttribute Region region) {

        regionService.editRegion(id, region);

        return "redirect:/regions";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteRegion(
            @PathVariable Integer id) {

        regionService.deleteRegionById(id);

        return "redirect:/regions";
    }
}
