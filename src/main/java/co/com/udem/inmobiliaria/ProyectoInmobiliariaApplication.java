package co.com.udem.inmobiliaria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import co.com.udem.inmobiliaria.utils.ConvertPropiedad;
import co.com.udem.inmobiliaria.utils.ConvertRegistro;
import co.com.udem.inmobiliaria.utils.ConvertTipoIdentificacion;
import co.com.udem.inmobiliaria.utils.FiltroPropiedades;

@SpringBootApplication
@EnableDiscoveryClient
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
	
	@Bean
	public FiltroPropiedades filtroPropiedades() {		
		return new FiltroPropiedades();
	}


	

}
