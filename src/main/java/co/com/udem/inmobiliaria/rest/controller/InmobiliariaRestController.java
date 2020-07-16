package co.com.udem.inmobiliaria.rest.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import co.com.udem.inmobiliaria.dto.RegistroDTO;
import co.com.udem.inmobiliaria.entities.Registro;
import co.com.udem.inmobiliaria.repositories.RegistroRepository;
import co.com.udem.inmobiliaria.utils.Constantes;
import co.com.udem.inmobiliaria.utils.ConvertRegistro;

@RestController
public class InmobiliariaRestController {
	
	@Autowired
	private RegistroRepository registroRepository;
	
	@Autowired
	private ConvertRegistro convertRegistro;
	
	@PostMapping("/registro/registrarUsuario")
	public Map<String, String> adicionarUsuario(@RequestBody RegistroDTO registroDTO) {
		Map<String, String> response = new HashMap<>();
		
		try {
			Registro usuario = convertRegistro.convertToEntity(registroDTO);
			registroRepository.save(usuario);
			response.put(Constantes.CODIGO_HTTP, "200");
            response.put(Constantes.MENSAJE_EXITO, "Usuario registrado exitosamente");
            return response;
		} catch (ParseException e) {
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
	         return response;
		}
	}
	
	@GetMapping("/registro/listarUsuarios")
	public Map<String, Object> listarUsuarios() {
		Map<String, Object> response = new HashMap<>();
		List<RegistroDTO> registro = new ArrayList<>();

			try {
				Iterable<Registro> listaUsuarios = registroRepository.findAll();
				registro = convertRegistro.convertToDTOList(listaUsuarios);
				response.put(Constantes.CODIGO_HTTP, "200");
				response.put(Constantes.RESULTADO, registro);
				return response;
			} catch (ParseException e) {
				response.put(Constantes.CODIGO_HTTP, "500");
		        response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
		        return response;
			}
	}
	
	
	@GetMapping("/registro/listarUsuarios/{id}")
	public Map<String, Object> buscarUsuario(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		RegistroDTO registroDTO = new RegistroDTO();

		try {

			if (registroRepository.findById(id).isPresent()) {
				Registro usuario = registroRepository.findById(id).get();
				registroDTO = convertRegistro.convertToDTO(usuario);
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
			    user.setPassword(nuevoUsuario.getPassword());
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

}
