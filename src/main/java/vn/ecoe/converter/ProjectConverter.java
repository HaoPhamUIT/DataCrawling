package vn.ecoe.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.ecoe.dto.ProjectDTO;
import vn.ecoe.model.Project;

@Component("ProjectConverter")
@Scope("prototype")
public class ProjectConverter extends SuperConverter<ProjectDTO, Project> {

	@Autowired
	private ModelMapper modelMapper;


	@Override
	public ProjectDTO convertToDTO(Project entity) {
		return modelMapper.map(entity, ProjectDTO.class);
	}

	@Override
	public Project convertToEntity(ProjectDTO dto) {
		return modelMapper.map(dto, Project.class);
	}
}
