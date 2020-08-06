package co.com.udem.inmobiliaria.rest.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import co.com.udem.inmobiliaria.dto.PropiedadDTO;
import co.com.udem.inmobiliaria.entities.Propiedad;
import co.com.udem.inmobiliaria.repositories.PropiedadRepository;
import co.com.udem.inmobiliaria.utils.Constantes;
import co.com.udem.inmobiliaria.utils.ConvertPropiedad;

@RestController
public class PropiedadRestController {
	
	@Autowired
	private PropiedadRepository propiedadRepository;
	
	@Autowired
	private ConvertPropiedad convertPropiedad;
	
	@PostMapping("/propiedad/registrarPropiedad")
	public Map<String, String> adicionarPropiedad(@RequestBody PropiedadDTO propiedadDTO) {
		Map<String, String> response = new HashMap<>();
		
		try {
			
//			if(registroRepository.buscarDocumentoTipo(registroDTO.getNumeroIdentificacion(), registroDTO.getTipoIdentificacionDTO().getId()) == null) 
//			{
				Propiedad propiedad = convertPropiedad.convertToEntity(propiedadDTO);
				propiedadRepository.save(propiedad);
				response.put(Constantes.CODIGO_HTTP, "200");
	            response.put(Constantes.MENSAJE_EXITO, "Propiedad registrada exitosamente");
	            return response;
//			} else 
//			{
//				response.put(Constantes.CODIGO_HTTP, "500");
//		        response.put(Constantes.MENSAJE_ERROR, "Ya existe un registro con la identificación "+registroDTO.getNumeroIdentificacion()+" y tipo de documento "+registroDTO.getTipoIdentificacionDTO().getId());
//		        return response;
//			}
			
		} catch (ParseException e) {
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al registrar la propiedad");
	         return response;
	    }
	}
	
	@GetMapping("/propiedad/listarPropiedades")
	public Map<String, Object> listarPropiedades() {
		Map<String, Object> response = new HashMap<>();
		List<PropiedadDTO> propiedad = new ArrayList<>();

			try {
				Iterable<Propiedad> listaPropiedad = propiedadRepository.findAll();
				propiedad = convertPropiedad.convertToDTOList(listaPropiedad);
				response.put(Constantes.CODIGO_HTTP, "200");
				response.put(Constantes.RESULTADO, propiedad);
				return response;
			} catch (ParseException e) {
				response.put(Constantes.CODIGO_HTTP, "500");
		        response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al listar las propiedades");
		        return response;
			}
	}
	
	
	@GetMapping("/propiedad/listarPropiedad/{id}")
	public Map<String, Object> buscarPropiedad(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		PropiedadDTO propiedadDTO = new PropiedadDTO();

		try {

			if (propiedadRepository.findById(id).isPresent()) {
				Propiedad propiedad = propiedadRepository.findById(id).get();
				propiedadDTO = convertPropiedad.convertToDTO(propiedad);
				response.put(Constantes.CODIGO_HTTP, "200");
				response.put(Constantes.RESULTADO, propiedadDTO);
				
			} else {
				response.put(Constantes.CODIGO_HTTP, "404");
				response.put(Constantes.MENSAJE_ERROR, "La propiedad con id "+id+" no existe");
			}

		} catch (ParseException e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al buscar la propiedada");
			
		}
		return response;
	}
	
	
	
	@DeleteMapping("/propiedad/eliminarPropiedad/{id}")
	public Map<String, String> eliminarPropiedad(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if (propiedadRepository.findById(id).isPresent()) {
				propiedadRepository.deleteById(id);
				response.put(Constantes.CODIGO_HTTP, "200");
				response.put(Constantes.MENSAJE_EXITO, "Propiedad eliminada exitosamente");
				return response;
			}
			else
			{
				response.put(Constantes.CODIGO_HTTP, "404");
			    response.put(Constantes.MENSAJE_ERROR, "La propiedad a modificar no existe en la base de datos");
			    return response;	
			}
			
		} catch (Exception e) {	
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al eliminar la propiedad");
	         return response;
		}
	
	}
	
	@PutMapping("/propiedad/modificarPropiedad/{id}")
    public Map<String, String> modificarUsuario(@RequestBody PropiedadDTO nuevaPropiedadDTO, @PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if(propiedadRepository.findById(id).isPresent()) {
				Propiedad nuevaPropiedad = convertPropiedad.convertToEntity(nuevaPropiedadDTO);
				Propiedad propiedad = propiedadRepository.findById(id).get();
			    propiedad.setArea(nuevaPropiedad.getArea());
			    propiedad.setNumerobaños(nuevaPropiedad.getNumerobaños());
			    propiedad.setNumerohabitaciones(nuevaPropiedad.getNumerohabitaciones());
			    propiedad.setTipopropiedad(nuevaPropiedad.getTipopropiedad());
			    propiedad.setValor(nuevaPropiedad.getValor());
			    propiedadRepository.save(propiedad);
				response.put(Constantes.CODIGO_HTTP, "200");
			    response.put(Constantes.MENSAJE_EXITO, "Propiedad modificada exitosamente");
			    return response;
			}else {
				response.put(Constantes.CODIGO_HTTP, "404");
			    response.put(Constantes.MENSAJE_ERROR, "La propiedad a modificar no existe en la base de datos");
			    return response;
			}
		} catch (Exception e) {
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al modificar la propiedad");
	         return response;
    }
}
	
	    @ExceptionHandler(value = {ConstraintViolationException.class})
	    public  Map<String, String> handleConstraint(ConstraintViolationException ex, 
	            WebRequest request ) {
		    Map<String, String> response = new HashMap<>();
		   	response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Fallo en constraint: El tipo de documento ingresado es incorrecto o no existe");
	        return response;       
	    }

}
