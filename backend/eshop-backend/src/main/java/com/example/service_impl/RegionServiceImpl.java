package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.BadRequestException;
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
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + id));
    }

    @Override
    public List<Region> getRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Region saveRegion(Region region) {
        if (region == null) {
            throw new BadRequestException("Region must not be null");
        }

        return regionRepository.save(region);
    }

    // Soft deletion
    @Override
    public void deleteRegionById(Integer id) {
        Region existingRegion = getRegionById(id);

        if (existingRegion.getDeleted())
            return;

        regionRepository.softDeleteById(existingRegion.getRegionId());
    }

    @Override
    public Region editRegion(Integer id, Region region) {
        if (region == null) {
            throw new BadRequestException("Region to edit must not be null");
        }

        Region existingRegion = getRegionById(id);

        existingRegion.setRegionName(region.getRegionName());
        // existingRegion.setCurrency(new Currency());

        return regionRepository.save(existingRegion);
    }
}
