//package co.com.udem.inmobiliaria.rest.controller;
//
//import java.text.ParseException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import co.com.udem.inmobiliaria.dto.RegistroDTO;
//import co.com.udem.inmobiliaria.entities.Registro;
//import co.com.udem.inmobiliaria.entities.User;
//import co.com.udem.inmobiliaria.repositories.RegistroRepository;
//import co.com.udem.inmobiliaria.repositories.UserRepository;
//import co.com.udem.inmobiliaria.utils.Constantes;
//import co.com.udem.inmobiliaria.utils.ConvertRegistro;
//
//@RestController
//public class UserController {
//	
//	@Autowired
//	private RegistroRepository registroRepository;
//	
//	@Autowired
//	private ConvertRegistro convertRegistro;
//
//	@Autowired
//	PasswordEncoder passwordEncoder;
//
//
//	@PostMapping("/users/addUser")
//	public Map<String, String> adicionarUsuario(@RequestBody RegistroDTO registroDTO) {
//		Map<String, String> response = new HashMap<>();
//		
//		try {
//			
//			if(registroRepository.buscarDocumentoTipo(registroDTO.getNumeroIdentificacion(), registroDTO.getTipoIdentificacionDTO().getId()) == null) 
//			{
//				Registro usuario = convertRegistro.convertToEntity(registroDTO);
//				registroRepository.save(Registro.builder()
//						.username(usuario.getUsername())
//						.password(this.passwordEncoder.encode(usuario.getPassword()))
//						.numeroIdentificacion(usuario.getNumeroIdentificacion())
//						.nombres(usuario.getNombres())
//						.apellidos(usuario.getApellidos())
//						.direccion(usuario.getDireccion())
//						.telefono(usuario.getTelefono())
//						.email(usuario.getEmail())
//						.tipoIdentificacion(usuario.getTipoIdentificacion())
//						.roles(Arrays.asList("ROLE_USER"))
//						.build());
//
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
//
//}