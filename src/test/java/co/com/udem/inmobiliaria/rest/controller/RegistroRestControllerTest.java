package co.com.udem.inmobiliaria.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.google.gson.Gson;

import co.com.udem.inmobiliaria.ProyectoInmobiliariaApplication;
import co.com.udem.inmobiliaria.dto.AutenticationRequestDTO;
import co.com.udem.inmobiliaria.dto.AutenticationResponseDTO;
import co.com.udem.inmobiliaria.dto.RegistroDTO;
import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.entities.Registro;
import co.com.udem.inmobiliaria.entities.TipoIdentificacion;
import co.com.udem.inmobiliaria.repositories.TipoIdentificacionRepository;
import co.com.udem.inmobiliaria.utils.ConvertTipoIdentificacion;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoInmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistroRestControllerTest {
	
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private TipoIdentificacionRepository tipoIdenRepo;
    
    @Autowired
	private ConvertTipoIdentificacion convertTipoId;

    @LocalServerPort
    private int port;
    
    private String token;

	private AutenticationRequestDTO autenticationRequestDTO = new AutenticationRequestDTO();

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    @Before
	public void authorization() {
		autenticationRequestDTO.setUsername("1017298");
		autenticationRequestDTO.setPassword("juanito123*");
		ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/auth/signin",
				autenticationRequestDTO, String.class);
		Gson g = new Gson();
		AutenticationResponseDTO autenticationResponseDTO = g.fromJson(postResponse.getBody(),
				AutenticationResponseDTO.class);
		token = autenticationResponseDTO.getToken();
	}
      
    
    @Test
    public void adicionarUsuarioTest()
    {
    	RegistroDTO registrolDTO= new RegistroDTO();
    	adicionarTipoIdTest();
    	registrolDTO.setNumeroIdentificacion("1017298");
    	registrolDTO.setNombres("Juanito");
    	registrolDTO.setApellidos("Velez");
    	registrolDTO.setDireccion("Calle 45");
    	registrolDTO.setTelefono((long) 2222222);
    	registrolDTO.setEmail("juanito@hotmail.com");
    	registrolDTO.setPassword("juanito123*");
    	TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
    	tipoIdentificacionDTO.setId(1L);
    	registrolDTO.setTipoIdentificacion(tipoIdentificacionDTO);
    	ResponseEntity<RegistroDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/registro/registrarUsuario", registrolDTO, RegistroDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        System.err.println("testAdicionarUsuarios: " + postResponse);
    	
    }
    
    @Test
    public void testObtenerUsuarios() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/registro/listarUsuarios", HttpMethod.GET, entity,
                String.class);
        assertNotNull(response.getBody());
        System.err.println("testObtenerUsuarios: " + response);
    }
    
    @Test
    public void buscarUsuarioTest() {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
    	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/registro/listarUsuario/1", HttpMethod.GET,
				entity, String.class);
		assertNotNull(postResponse.getBody());
		System.err.println("buscarUsuarioTest: " + postResponse);
    }
    
    @Test
    public void modificarUsuarioTest() {
    	RegistroDTO registrolDTO= new RegistroDTO();
    	int id = 1;
    	registrolDTO.setNumeroIdentificacion("1017298");
    	registrolDTO.setNombres("Juanito");
    	registrolDTO.setApellidos("Lopez Perez");
    	registrolDTO.setDireccion("Calle 12");
    	registrolDTO.setTelefono((long) 32392829);
    	registrolDTO.setEmail("juanito@hotmail.com");
    	registrolDTO.setPassword("juanito123*");     	
    	TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
    	tipoIdentificacionDTO.setId(1L);
    	registrolDTO.setTipoIdentificacion(tipoIdentificacionDTO);
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + this.token);
		HttpEntity<RegistroDTO> entity = new HttpEntity<RegistroDTO>(registrolDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/registro/modificarUsuario/" + id, HttpMethod.PUT,
				entity, String.class);	
		assertNotNull(response.getBody());
		System.err.println("testmodificarUsuarios: " + response);
    }

    @Test
    public void eliminarUsuarioTest() {
        int id = 1;
	     HttpHeaders headers = new HttpHeaders();
	 	 headers.setContentType(MediaType.APPLICATION_JSON);
	 	 headers.set("Authorization", "Bearer " + this.token);
	 	 HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		 ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/registro/eliminarUsuario/" + id, HttpMethod.DELETE,
				entity, String.class);
		 assertNotNull(postResponse.getBody());
		 System.err.println("testEliminarUsuarios: " + postResponse);
    }
    
    public void adicionarTipoIdTest()
    {
    	TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
    	tipoIdentificacionDTO.setId(1L);
    	tipoIdentificacionDTO.setTipoDocumento("CC");
    	TipoIdentificacion tipoid;
		try {
			tipoid = convertTipoId.convertToEntity(tipoIdentificacionDTO);
			tipoIdenRepo.save(tipoid);
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("No se pudo guardar registro en BD");
		}
    	
    }
    


}
