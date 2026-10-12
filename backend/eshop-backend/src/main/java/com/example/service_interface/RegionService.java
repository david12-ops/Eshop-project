package com.example.service_interface;

import java.util.List;

import com.example.model.Region;

public interface RegionService {

    Region getRegionById(Integer id);

    List<Region> getRegions();

    Region saveRegion(Region region);

    Region editRegion(Integer id, Region region);

    void deleteRegionById(Integer id);
}
