package co.com.udem.inmobiliaria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import co.com.udem.inmobiliaria.entities.Registro;
import co.com.udem.inmobiliaria.repositories.RegistroRepository;
import co.com.udem.inmobiliaria.utils.ConvertRegistro;

@SpringBootApplication
public class ProyectoInmobiliariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoInmobiliariaApplication.class, args);
		
	}
	
	@Bean
	public ConvertRegistro convertRegistro() {		
		return new ConvertRegistro();
	}
	
	@Bean
	public ModelMapper modelMapper() {		
		return new ModelMapper();
	}

	

}