package co.com.udem.inmobiliaria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import co.com.udem.inmobiliaria.utils.ConvertPropiedad;
import co.com.udem.inmobiliaria.utils.ConvertRegistro;
import co.com.udem.inmobiliaria.utils.ConvertTipoIdentificacion;

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
	public ConvertTipoIdentificacion convertTipoIdentificacion() {		
		return new ConvertTipoIdentificacion();
	}
	
	@Bean
	public ConvertPropiedad convertPropiedad() {		
		return new ConvertPropiedad();
	}
	
	@Bean
	public ModelMapper modelMapper() {		
		return new ModelMapper();
	}
	
	@Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }


	

}
