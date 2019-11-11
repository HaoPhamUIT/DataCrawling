package vn.ecoe.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vn.ecoe.dto.LandDTO;
import vn.ecoe.model.Land;

@Component("LandConverter")
@Scope("prototype")
public class LandConverter extends SuperConverter<LandDTO, Land> {

	@Autowired
	private ModelMapper modelMapper;


	@Override
	public LandDTO convertToDTO(Land entity) {
		return modelMapper.map(entity, LandDTO.class);
	}

	@Override
	public Land convertToEntity(LandDTO dto) {
		return modelMapper.map(dto, Land.class);
	}
}
