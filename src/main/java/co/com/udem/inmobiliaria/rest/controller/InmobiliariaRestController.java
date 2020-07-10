package co.com.udem.inmobiliaria.rest.controller;

import java.text.ParseException;
import java.util.HashMap;
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
	
	@GetMapping("/listarUsuarios")
	public Iterable<Registro> listarUsuarios() {  //Tarea: convertir a DTO convertToDTO
		return registroRepository.findAll();
	}
	
	@GetMapping("/listarUsuarios/{id}")
	public Registro buscarUsuario(@PathVariable Long id) {
        return registroRepository.findById(id).get();
		
	}
	
	@DeleteMapping("/eliminarUsuario/{id}")
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
			    response.put(Constantes.MENSAJE_EXITO, "El usuario a modificar no existe en la base de datos");
			    return response;	
			}
			
		} catch (Exception e) {	
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
	         return response;
		}
	
	}
	
	@PutMapping("/modificarUsuario/{id}")
    public Map<String, String> updateUser(@RequestBody Registro nuevoUsuario, @PathVariable Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			if(registroRepository.findById(id).isPresent()) {
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
			    response.put(Constantes.MENSAJE_EXITO, "El usuario a modificar no existe en la base de datos");
			    return response;
			}
		} catch (Exception e) {
			 response.put(Constantes.CODIGO_HTTP, "500");
	         response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al modificar el usuario");
	         return response;
    }
}

}
