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

import co.com.udem.inmobiliaria.dto.FiltroDTO;
import co.com.udem.inmobiliaria.dto.PropiedadDTO;
import co.com.udem.inmobiliaria.entities.Propiedad;
import co.com.udem.inmobiliaria.repositories.PropiedadRepository;
import co.com.udem.inmobiliaria.utils.Constantes;
import co.com.udem.inmobiliaria.utils.ConvertPropiedad;
import co.com.udem.inmobiliaria.utils.FiltroPropiedades;

@RestController
public class PropiedadRestController {
	
	@Autowired
	private PropiedadRepository propiedadRepository;
	
	@Autowired
	private ConvertPropiedad convertPropiedad;
	
	@Autowired
	private FiltroPropiedades filtroPropiedades;
	
	@PostMapping("/propiedad/registrarPropiedad")
	public ResponseEntity<Object> adicionarPropiedad(@RequestBody PropiedadDTO propiedadDTO) {
		Map<String, String> response = new HashMap<>();
		
		try {
				Propiedad propiedad = convertPropiedad.convertToEntity(propiedadDTO);
				propiedadRepository.save(propiedad);
	            response.put(Constantes.MENSAJE_EXITO, "Propiedad registrada exitosamente");
	            return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (ParseException e) {
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al registrar la propiedad");
	         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@GetMapping("/propiedad/listarPropiedades")
	public ResponseEntity<Object> listarPropiedades() {
		Map<String, Object> response = new HashMap<>();
		List<PropiedadDTO> propiedad = new ArrayList<>();

			try {
				Iterable<Propiedad> listaPropiedad = propiedadRepository.findAll();
				propiedad = convertPropiedad.convertToDTOList(listaPropiedad);
				response.put(Constantes.RESULTADO, propiedad);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (ParseException e) {
		        response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al listar las propiedades");
		        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	
	
	@GetMapping("/propiedad/listarPropiedad/{id}")
	public ResponseEntity<Object> buscarPropiedad(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		PropiedadDTO propiedadDTO = new PropiedadDTO();

		try {

			if (propiedadRepository.findById(id).isPresent()) {
				Propiedad propiedad = propiedadRepository.findById(id).get();
				propiedadDTO = convertPropiedad.convertToDTO(propiedad);
				response.put(Constantes.RESULTADO, propiedadDTO);
				
			} else {
				response.put(Constantes.MENSAJE_ERROR, "La propiedad con id "+id+" no existe");
			}

		} catch (ParseException e) {
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al buscar la propiedada");
			
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	@DeleteMapping("/propiedad/eliminarPropiedad/{id}")
	public ResponseEntity<Object> eliminarPropiedad(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if (propiedadRepository.findById(id).isPresent()) {
				propiedadRepository.deleteById(id);
				response.put(Constantes.MENSAJE_EXITO, "Propiedad eliminada exitosamente");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			else
			{
			    response.put(Constantes.MENSAJE_ERROR, "La propiedad a modificar no existe en la base de datos");
			    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {	
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al eliminar la propiedad");
	         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PutMapping("/propiedad/modificarPropiedad/{id}")
    public ResponseEntity<Object> modificarUsuario(@RequestBody PropiedadDTO nuevaPropiedadDTO, @PathVariable Long id) {
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
			    propiedad.setRegistro(nuevaPropiedad.getRegistro());
			    propiedadRepository.save(propiedad);
			    response.put(Constantes.MENSAJE_EXITO, "Propiedad modificada exitosamente");
			    return new ResponseEntity<>(response, HttpStatus.OK);
			}else {
			    response.put(Constantes.MENSAJE_ERROR, "La propiedad a modificar no existe en la base de datos");
			    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al modificar la propiedad");
	         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
	
	
	 /*  Filtro con criteria query api: Genera automáticamente la consulta de acuerdo
     * a los valores enviados*/
		@PostMapping("/propiedad/filtrarPropiedad")
		public ResponseEntity<Object> filtarPropiedad(@RequestBody FiltroDTO filtroDTO) throws ParseException {
			
			List<Propiedad> filtro = new ArrayList<>();
			
			/*  Filtro con Criteria Query
			 * */
			filtro = filtroPropiedades.filtrarPropiedades(filtroDTO);
			
			return new ResponseEntity<>(filtro, HttpStatus.OK);
		}
		
		@PostMapping("/propiedad/filtrarPorArea")
		public ResponseEntity<Object> filtrarPorArea(@RequestBody FiltroDTO filtroDTO) throws ParseException {
			
			List<Propiedad> filtro = new ArrayList<>();
			filtro = propiedadRepository.findByArea(filtroDTO.getArea());
			
			return new ResponseEntity<>(filtro, HttpStatus.OK);
		}
		
		@PostMapping("/propiedad/filtrarPorValor")
		public ResponseEntity<Object> filtrarPorValor(@RequestBody FiltroDTO filtroDTO) throws ParseException {
			
			List<Propiedad> filtro = new ArrayList<>();
			filtro = propiedadRepository.findByRangoValor(filtroDTO.getPrecioInicial(), filtroDTO.getPrecioFinal());
			
			return new ResponseEntity<>(filtro, HttpStatus.OK);
		}
		
		@PostMapping("/propiedad/filtrarPorHabitaciones")
		public ResponseEntity<Object> filtrarPorHabitaciones(@RequestBody FiltroDTO filtroDTO) throws ParseException {
			
			List<Propiedad> filtro = new ArrayList<>();
			filtro = propiedadRepository.findByNumerohabitaciones(filtroDTO.getNumeroHabitaciones());
			
			return new ResponseEntity<>(filtro, HttpStatus.OK);
		}
	

	    @ExceptionHandler(value = {ConstraintViolationException.class})
	    public  Map<String, String> handleConstraint(ConstraintViolationException ex, 
	            WebRequest request ) {
		    Map<String, String> response = new HashMap<>();
		   	response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Fallo en constraint: El usuario ingresado es incorrecto o no existe");
	        return response;       
	    }

}
