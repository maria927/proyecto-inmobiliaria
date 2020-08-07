package co.com.udem.inmobiliaria.rest.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.inmobiliaria.entities.User;
import co.com.udem.inmobiliaria.repositories.UserRepository;
import co.com.udem.inmobiliaria.utils.Constantes;

@RestController
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/users/addUser")
	public Map<String, String> adicionarUsuario(@RequestBody User user) {
		Map<String, String> response = new HashMap<>();

		userRepository.save(User.builder()
				.username(user.getUsername())
				.password(this.passwordEncoder.encode(user.getPassword()))
				.roles(Arrays.asList("ROLE_USER"))
				.build());
		response.put(Constantes.CODIGO_HTTP, "200");
		response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
		return response;

	}

	@GetMapping("/users")
	public Iterable<User> listarUsuarios() {

		return userRepository.findAll();

	}
}