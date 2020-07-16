package co.com.udem.inmobiliaria.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.inmobiliaria.dto.RegistroDTO;
import co.com.udem.inmobiliaria.entities.Registro;

public class ConvertRegistro {
	
	@Autowired
	private ModelMapper modelMapper;
	   
    public Registro convertToEntity(RegistroDTO registroDTO) throws ParseException {
        return modelMapper.map(registroDTO, Registro.class);
    }
   
    public RegistroDTO convertToDTO(Registro registro) throws ParseException {
        return modelMapper.map(registro, RegistroDTO.class);
    }
    
    public List<RegistroDTO> convertToDTOList(Iterable<Registro> registroList)
			throws ParseException {

		List<Registro> registro = new ArrayList<>();
		registroList.forEach(registro::add);
		return modelMapper.map(registro, new TypeToken<List<RegistroDTO>>() {
		}.getType());

	}

}
