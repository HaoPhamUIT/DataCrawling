package vn.ecoe.service;

import vn.ecoe.dto.ProjectDTO;
import vn.ecoe.model.Project;

import java.util.List;

public interface ProjectService {
    List<ProjectDTO> getAll();
    void saveListProject();
}
