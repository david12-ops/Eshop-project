package com.example.service_interface;

import com.example.dto.RegionDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface RegionService {

    RegionDTO getRegionById(Integer id, MyUserDetail userDetail);

    List<RegionDTO> getRegions(MyUserDetail userDetail);

    RegionDTO saveRegion(RegionDTO regionDTO, MyUserDetail userDetail);

    RegionDTO editRegion(Integer id, RegionDTO regionDTO, MyUserDetail userDetail);

    void deleteRegionById(Integer id, MyUserDetail userDetail);
}
