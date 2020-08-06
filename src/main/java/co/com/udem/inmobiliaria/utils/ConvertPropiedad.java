package co.com.udem.inmobiliaria.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.inmobiliaria.dto.PropiedadDTO;
import co.com.udem.inmobiliaria.entities.Propiedad;

public class ConvertPropiedad {
	
	@Autowired
	private ModelMapper modelMapper;
	   
    public Propiedad convertToEntity(PropiedadDTO propiedadDTO) throws ParseException {
        return modelMapper.map(propiedadDTO, Propiedad.class);
    }
   
    public PropiedadDTO convertToDTO(Propiedad propiedad) throws ParseException {
        return modelMapper.map(propiedad, PropiedadDTO.class);
    }
    
    public List<PropiedadDTO> convertToDTOList(Iterable<Propiedad> propiedadList)
			throws ParseException {

		List<Propiedad> propiedad = new ArrayList<>();
		propiedadList.forEach(propiedad::add);
		return modelMapper.map(propiedad, new TypeToken<List<PropiedadDTO>>() {
		}.getType());

	}

}
