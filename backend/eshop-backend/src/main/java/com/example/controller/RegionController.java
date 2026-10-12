package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Region;
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
    public List<Region> listRegions() {
        return regionService.getRegions();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Region regionDetail(@PathVariable Integer id) {
        return regionService.getRegionById(id);
    }

    // CREATE 
    @PostMapping("/create")
    public Region createRegion(@RequestBody  Region region) {
        return regionService.saveRegion(region);
    }

    // EDIT
    @PutMapping("/{id}")
    public Region editRegion(
            @PathVariable Integer id,
            @RequestBody Region region) {
        return  regionService.editRegion(id, region);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteRegion(@PathVariable Integer id) {
        regionService.deleteRegionById(id);
    }
}
