package com.example.service_impl;

import java.util.List;

import com.example.model.Region;
import com.example.repository.RegionRepository;
import com.example.service_interface.RegionService;

public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Region getRegionById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRegionById'");
    }

    @Override
    public List<Region> getAllRegions() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRegions'");
    }

    @Override
    public void saveRegion(Region region) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveRegion'");
    }

    @Override
    public void deleteRegionById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRegionById'");
    }

}
