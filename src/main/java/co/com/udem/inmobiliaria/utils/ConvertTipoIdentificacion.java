package co.com.udem.inmobiliaria.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.entities.TipoIdentificacion;

public class ConvertTipoIdentificacion {

	@Autowired
	private ModelMapper modelMapper;

	public TipoIdentificacion convertToEntity(TipoIdentificacionDTO tipoIdentificacion) throws ParseException {
		return modelMapper.map(tipoIdentificacion, TipoIdentificacion.class);
	}

	public TipoIdentificacionDTO convertToDTO(TipoIdentificacion tipoIdentificacion) throws ParseException {
		return modelMapper.map(tipoIdentificacion, TipoIdentificacionDTO.class);
	}

	public List<TipoIdentificacionDTO> convertToDTOIterable(Iterable<TipoIdentificacion> tipoDocumentos)
			throws ParseException {

		List<TipoIdentificacion> tipoDoc = new ArrayList<>();
		tipoDocumentos.forEach(tipoDoc::add);
		return modelMapper.map(tipoDoc, new TypeToken<List<TipoIdentificacionDTO>>() {
		}.getType());

	}

}
