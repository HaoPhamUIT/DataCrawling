package vn.ecoe.service;

import vn.ecoe.dto.LandDTO;

import java.util.List;

public interface LandService {
    List<LandDTO> getAll();
    void saveListLand();
}
