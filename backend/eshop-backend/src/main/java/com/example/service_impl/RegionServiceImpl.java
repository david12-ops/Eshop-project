package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Region;
import com.example.repository.RegionRepository;
import com.example.service_interface.RegionService;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Region getRegionById(Integer id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + id));
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public void saveRegion(Region region) {
        if (region == null)
            return;

        regionRepository.save(region);
    }

    @Override
    public void deleteRegionById(Integer id) {
        if (id == null)
            return;

        regionRepository.findById(id).ifPresent(region -> regionRepository.deleteById(id));
    }

    @Override
    public void editRegion(Integer id, Region region) {
        if (id == null || region == null)
            return;

        regionRepository.findById(id).ifPresent(existingRegion -> {

            existingRegion.setRegionName(region.getRegionName());
            // existingRegion.setCurrency(new Currency());

            regionRepository.save(existingRegion);
        });
    }
}
