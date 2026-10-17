package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.RegionDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.RegionService;

@RestController
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    // LIST
    @GetMapping
    public List<RegionDTO> listRegions(@AuthenticationPrincipal MyUserDetail userDetail) {
        return regionService.getRegions(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public RegionDTO regionDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return regionService.getRegionById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public RegionDTO createRegion(
            @RequestBody RegionDTO regionDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return regionService.saveRegion(regionDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public RegionDTO editRegion(
            @PathVariable Integer id,
            @RequestBody RegionDTO regionDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return regionService.editRegion(id, regionDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteRegion(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        regionService.deleteRegionById(id, userDetail);
    }
}
