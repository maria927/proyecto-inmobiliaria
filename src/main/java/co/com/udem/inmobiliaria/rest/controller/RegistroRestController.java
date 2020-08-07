package co.com.udem.inmobiliaria.rest.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import co.com.udem.inmobiliaria.entities.Registro;
import co.com.udem.inmobiliaria.repositories.RegistroRepository;
import co.com.udem.inmobiliaria.utils.Constantes;
import co.com.udem.inmobiliaria.utils.ConvertRegistro;
import co.com.udem.inmobiliaria.utils.ConvertTipoIdentificacion;

@RestController
public class RegistroRestController {
	
	@Autowired
	private RegistroRepository registroRepository;
	
	@Autowired
	private ConvertRegistro convertRegistro;
	
	@Autowired
	private ConvertTipoIdentificacion convertTipo;
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
//	@PostMapping("/registro/registrarUsuario")
//	public Map<String, String> adicionarUsuario(@RequestBody RegistroDTO registroDTO) {
//		Map<String, String> response = new HashMap<>();
//		
//		try {
//			
//			if(registroRepository.buscarDocumentoTipo(registroDTO.getNumeroIdentificacion(), registroDTO.getTipoIdentificacionDTO().getId()) == null) 
//			{
//				Registro usuario = convertRegistro.convertToEntity(registroDTO);
//				registroRepository.save(usuario);
//				response.put(Constantes.CODIGO_HTTP, "200");
//	            response.put(Constantes.MENSAJE_EXITO, "Usuario registrado exitosamente");
//	            return response;
//			} else 
//			{
//				response.put(Constantes.CODIGO_HTTP, "500");
//		        response.put(Constantes.MENSAJE_ERROR, "Ya existe un registro con la identificación "+registroDTO.getNumeroIdentificacion()+" y tipo de documento "+registroDTO.getTipoIdentificacionDTO().getId());
//		        return response;
//			}
//			
//		} catch (ParseException e) {
//			 response.put(Constantes.CODIGO_HTTP, "500");
//	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
//	         return response;
//	    }
//	}
//	
	@PostMapping("/registro/registrarUsuario")
	public Map<String, String> adicionarUsuario(@RequestBody RegistroDTO registroDTO) {
		Map<String, String> response = new HashMap<>();
		
		try {
			
			if(registroRepository.buscarDocumentoTipo(registroDTO.getNumeroIdentificacion(), registroDTO.getTipoIdentificacion().getId()) == null) 
			{
				Registro usuario = convertRegistro.convertToEntity(registroDTO);
				registroRepository.save(Registro.builder()
						.username(registroDTO.getUsername())
						.password(this.passwordEncoder.encode(registroDTO.getPassword()))
						.numeroIdentificacion(usuario.getNumeroIdentificacion())
						.nombres(usuario.getNombres())
						.apellidos(usuario.getApellidos())
						.direccion(usuario.getDireccion())
						.telefono(usuario.getTelefono())
						.email(usuario.getEmail())
						.tipoIdentificacion(usuario.getTipoIdentificacion())
						.roles(Arrays.asList("ROLE_USER"))
						.build());

				response.put(Constantes.CODIGO_HTTP, "200");
	            response.put(Constantes.MENSAJE_EXITO, "Usuario registrado exitosamente");
	            return response;
			} else 
			{
				response.put(Constantes.CODIGO_HTTP, "500");
		        response.put(Constantes.MENSAJE_ERROR, "Ya existe un registro con la identificación "+registroDTO.getNumeroIdentificacion()+" y tipo de documento "+registroDTO.getTipoIdentificacion().getId());
		        return response;
			}
			
		} catch (ParseException e) {
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
	         return response;
	    }
	}
	
	@DeleteMapping("/registro/eliminarUsuario/{id}")
	public Map<String, String> eliminarUsuario(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if (registroRepository.findById(id).isPresent()) {
				registroRepository.deleteById(id);
				response.put(Constantes.CODIGO_HTTP, "200");
				response.put(Constantes.MENSAJE_EXITO, "Usuario eliminado exitosamente");
				return response;
			}
			else
			{
				response.put(Constantes.CODIGO_HTTP, "404");
			    response.put(Constantes.MENSAJE_ERROR, "El usuario a modificar no existe en la base de datos");
			    return response;	
			}
			
		} catch (Exception e) {	
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al eliminar el usuario");
	         return response;
		}
	
	}
	
	@PutMapping("/registro/modificarUsuario/{id}")
    public Map<String, String> modificarUsuario(@RequestBody RegistroDTO nuevoUsuarioDTO, @PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if(registroRepository.findById(id).isPresent()) {
				Registro nuevoUsuario = convertRegistro.convertToEntity(nuevoUsuarioDTO);
			    Registro user = registroRepository.findById(id).get();
			    user.setTipoIdentificacion(nuevoUsuario.getTipoIdentificacion());
			    user.setNumeroIdentificacion(nuevoUsuario.getNumeroIdentificacion());
			    user.setNombres(nuevoUsuario.getNombres());
			    user.setApellidos(nuevoUsuario.getApellidos());
			    user.setDireccion(nuevoUsuario.getDireccion());
			    user.setTelefono(nuevoUsuario.getTelefono());
			    user.setEmail(nuevoUsuario.getEmail());
			    user.setPassword(this.passwordEncoder.encode(nuevoUsuario.getPassword()));
			    registroRepository.save(user);
				response.put(Constantes.CODIGO_HTTP, "200");
			    response.put(Constantes.MENSAJE_EXITO, "Usuario modificado exitosamente");
			    return response;
			}else {
				response.put(Constantes.CODIGO_HTTP, "404");
			    response.put(Constantes.MENSAJE_ERROR, "El usuario a modificar no existe en la base de datos");
			    return response;
			}
		} catch (Exception e) {
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al modificar el usuario");
	         return response;
    }
}
	
	@GetMapping("/registro/listarUsuarios")
	public Map<String, Object> listarUsuarios() {
		Map<String, Object> response = new HashMap<>();
		Iterable<Registro> iRegistro = registroRepository.findAll();
		List<Registro> listaRegistro = new ArrayList<Registro>();
		List<RegistroDTO> listaRegistroDTO = new ArrayList<RegistroDTO>();
		iRegistro.iterator().forEachRemaining(listaRegistro::add);
		for (int i = 0; i < listaRegistro.size(); i++) {
			try {
				TipoIdentificacionDTO tipoIdentificacion = null;
				if (listaRegistro.get(i).getTipoIdentificacion() != null) {
					tipoIdentificacion = convertTipo
							.convertToDTO(listaRegistro.get(i).getTipoIdentificacion());
				}
				RegistroDTO registroDTO = convertRegistro.convertToDTO(listaRegistro.get(i));
				registroDTO.setTipoIdentificacion(tipoIdentificacion);
				listaRegistroDTO.add(registroDTO);
				response.put(Constantes.CODIGO_HTTP, "200");
				response.put(Constantes.RESULTADO, listaRegistroDTO);
			} catch (ParseException e) {
				response.put(Constantes.CODIGO_HTTP, "500");
		        response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al listar los usuarios");
		        
			}
		}

		return response;
	}
	
	@GetMapping("/registro/listarUsuario/{id}")
	public Map<String, Object> buscarUsuario(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		RegistroDTO registroDTO = new RegistroDTO();
		try {

			if (registroRepository.findById(id).isPresent()) {
				Registro iRegistro = registroRepository.findById(id).get();
				TipoIdentificacionDTO tipoIdentificacion = convertTipo.convertToDTO(iRegistro.getTipoIdentificacion());
				registroDTO = convertRegistro.convertToDTO(iRegistro);
				registroDTO.setTipoIdentificacion(tipoIdentificacion);
				response.put(Constantes.CODIGO_HTTP, "200");
				response.put(Constantes.RESULTADO, registroDTO);
				
			} else {
				response.put(Constantes.CODIGO_HTTP, "404");
				response.put(Constantes.MENSAJE_ERROR, "El usuario con id "+id+" no existe");
			}

		} catch (ParseException e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al buscar el usuario");
			
		}
		return response;
	}
	
	
//	@GetMapping("/registro/listarUsuarios/eejj")
//	public Map<String, Object> listarUsuariosejj() {
//		Map<String, Object> response = new HashMap<>();
//		List<RegistroDTO> registro = new ArrayList<>();
//
//			try {
//				Iterable<Registro> listaUsuarios = registroRepository.findAll();
//				registro = convertRegistro.convertToDTOList(listaUsuarios);
//				response.put(Constantes.CODIGO_HTTP, "200");
//				response.put(Constantes.RESULTADO, registro);
//				return response;
//			} catch (ParseException e) {
//				response.put(Constantes.CODIGO_HTTP, "500");
//		        response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al listar los usuarios");
//		        return response;
//			}
//	}
	

	    @ExceptionHandler(value = {ConstraintViolationException.class})
	    public  Map<String, String> handleConstraint(ConstraintViolationException ex, 
	            WebRequest request ) {
		    Map<String, String> response = new HashMap<>();
		   	response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Fallo en constraint: El tipo de documento ingresado es incorrecto o no existe");
	        return response;       
	    }

}
