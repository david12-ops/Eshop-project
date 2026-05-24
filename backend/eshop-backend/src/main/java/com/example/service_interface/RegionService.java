package com.example.service_interface;

import java.util.List;

import com.example.model.Region;

public interface RegionService {

    Region getRegionById(Integer id);

    List<Region> getAllRegions();

    void saveRegion(Region region);

    void deleteRegionById(Integer id);
}
