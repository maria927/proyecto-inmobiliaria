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

import co.com.udem.inmobiliaria.dto.RegistroDTO;
import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.entities.TipoIdentificacion;
import co.com.udem.inmobiliaria.repositories.RegistroRepository;
import co.com.udem.inmobiliaria.repositories.TipoIdentificacionRepository;
import co.com.udem.inmobiliaria.utils.Constantes;
import co.com.udem.inmobiliaria.utils.ConvertRegistro;
import co.com.udem.inmobiliaria.utils.ConvertTipoIdentificacion;

@RestController
public class TipoIdRestController {
	
	@Autowired
	private TipoIdentificacionRepository tipoIdoRepository;
	
	@Autowired
	private ConvertTipoIdentificacion convertTipoId;
	
	@PostMapping("/tipoidentificacion/registrarTipoId")
	public ResponseEntity<Object> adicionarTipoId(@RequestBody TipoIdentificacionDTO tipoIdDTO) {
		Map<String, String> response = new HashMap<>();
		
		try {
			
			if(tipoIdoRepository.findByTipoDocumento(tipoIdDTO.getTipoDocumento()) == null)
			{
				TipoIdentificacion tipoid = convertTipoId.convertToEntity(tipoIdDTO);
				tipoIdoRepository.save(tipoid);
	            response.put(Constantes.MENSAJE_EXITO, "Tipo id registrado exitosamente");
	            return new ResponseEntity<>(response, HttpStatus.OK);
			} else 
			{
		        response.put(Constantes.MENSAJE_ERROR, "Ya existe el tipo de Id "+tipoIdDTO.getTipoDocumento());
		        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (ParseException e) {
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al registrar el tipo de id");
	         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/tipoidentificacion/obtenerTipoId")
	public ResponseEntity<Object> listarUsuarios() {
		Map<String, Object> response = new HashMap<>();
		List<TipoIdentificacionDTO> tipoId = new ArrayList<>();

			try {
				Iterable<TipoIdentificacion> listaId = tipoIdoRepository.findAll();
				tipoId = convertTipoId.convertToDTOIterable(listaId);
				response.put(Constantes.RESULTADO, tipoId);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (ParseException e) {
		        response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al listar los tipo de id");
		        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	
	@GetMapping("/tipoidentificacion/obtenerTipoId/{id}")
	public ResponseEntity<Object> buscarUsuario(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		TipoIdentificacionDTO tipoIdDTO = new TipoIdentificacionDTO();

		try {

			if (tipoIdoRepository.findById(id).isPresent()) {
				TipoIdentificacion tipoId = tipoIdoRepository.findById(id).get();
				tipoIdDTO = convertTipoId.convertToDTO(tipoId);
				response.put(Constantes.RESULTADO, tipoIdDTO);
				
			} else {
				response.put(Constantes.MENSAJE_ERROR, "El id no existe");
			}

		} catch (ParseException e) {
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al buscar el usuario");
			
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/tipoidentificacion/eliminarTipoId/{id}")
	public ResponseEntity<Object> eliminarUsuario(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if (tipoIdoRepository.findById(id).isPresent()) {
				tipoIdoRepository.deleteById(id);
				response.put(Constantes.MENSAJE_EXITO, "Tipo id eliminado exitosamente");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
			    response.put(Constantes.MENSAJE_ERROR, "El tipo de id no existe en la base de datos");
			    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);	
			}
			
		} catch (Exception e) {	
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al eliminar el tipo de id");
	         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PutMapping("/tipoidentificacion/modificarTipoId/{id}")
    public ResponseEntity<Object> modificarUsuario(@RequestBody TipoIdentificacionDTO nuevoTipoIdDTO, @PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if(tipoIdoRepository.findById(id).isPresent()) {
				TipoIdentificacion nuevoTipoId = convertTipoId.convertToEntity(nuevoTipoIdDTO);
				TipoIdentificacion tipoid = tipoIdoRepository.findById(id).get();
				tipoid.setTipoDocumento(nuevoTipoId.getTipoDocumento());
			    tipoIdoRepository.save(tipoid);
			    response.put(Constantes.MENSAJE_EXITO, "Tipo id modificado exitosamente");
			    return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
			    response.put(Constantes.MENSAJE_ERROR, "El tipo id a modificar no existe en la base de datos");
			    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al modificar el tipo id");
	         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
