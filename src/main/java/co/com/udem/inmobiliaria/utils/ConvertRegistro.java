package co.com.udem.inmobiliaria.utils;

import java.text.ParseException;

import org.modelmapper.ModelMapper;
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

}
