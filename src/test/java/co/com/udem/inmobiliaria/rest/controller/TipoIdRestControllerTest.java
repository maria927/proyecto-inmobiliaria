package co.com.udem.inmobiliaria.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.Before;
import org.junit.FixMethodOrder;
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
public class TipoIdRestControllerTest {
	
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
    	adicionarUsuarioTest();
		autenticationRequestDTO.setUsername("123456");
		autenticationRequestDTO.setPassword("john123*");
		ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/auth/signin",
				autenticationRequestDTO, String.class);
		Gson g = new Gson();
		AutenticationResponseDTO autenticationResponseDTO = g.fromJson(postResponse.getBody(),
				AutenticationResponseDTO.class);
		token = autenticationResponseDTO.getToken();
	}
      
    @Test
    public void AdicionarTipoIdTest()
    {

    	TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
    	tipoIdentificacionDTO.setId(2L);
    	tipoIdentificacionDTO.setTipoDocumento("CE");
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + this.token);

		HttpEntity<TipoIdentificacionDTO> entity = new HttpEntity<TipoIdentificacionDTO>(tipoIdentificacionDTO, headers);
		ResponseEntity<String> postResponse = restTemplate.exchange(
				getRootUrl() + "/tipoidentificacion/registrarTipoId", HttpMethod.POST, entity, String.class);
		System.err.println("adicionar tipo id: " + postResponse);
		assertEquals(200, postResponse.getStatusCode().value());
    	
    }

    
    @Test
    public void buscarTipoIdTest() {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
    	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/tipoidentificacion/obtenerTipoId/1", HttpMethod.GET,
				entity, String.class);
		System.err.println("buscar tipo id: " + postResponse);
		assertEquals(200, postResponse.getStatusCode().value());
    }
    
    
    @Test
    public void obtenerTipoId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/tipoidentificacion/obtenerTipoId", HttpMethod.GET, entity,
                String.class);
        assertNotNull(response.getBody());
        System.err.println("Obtener todo "+response);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void modificarTipoIdTest() {
    	int id = 1;
    	TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
    	tipoIdentificacionDTO.setId(1L);
    	tipoIdentificacionDTO.setTipoDocumento("PA");
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + this.token);
		HttpEntity<TipoIdentificacionDTO> entity = new HttpEntity<TipoIdentificacionDTO>(tipoIdentificacionDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/tipoidentificacion/modificarTipoId/" + id, HttpMethod.PUT,
				entity, String.class);	
		System.err.println("modificar tipo id: " + response);
		assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void eliminarUsuarioTest() {
         int id = 2;
	     HttpHeaders headers = new HttpHeaders();
	 	 headers.setContentType(MediaType.APPLICATION_JSON);
	 	 headers.set("Authorization", "Bearer " + this.token);
	 	 HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		 ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/tipoidentificacion/eliminarTipoId/" + id, HttpMethod.DELETE,
				entity, String.class);
		 System.err.println("eliminar tipo id: " + postResponse);
		 assertEquals(200, postResponse.getStatusCode().value());
    }
    

    public void adicionarUsuarioTest()
    {
    	RegistroDTO registrolDTO= new RegistroDTO();
    	adicionarTipoIdInicial();
    	registrolDTO.setNumeroIdentificacion("123456");
    	registrolDTO.setNombres("John");
    	registrolDTO.setApellidos("Doe");
    	registrolDTO.setDireccion("Calle 24");
    	registrolDTO.setTelefono((long) 2222222);
    	registrolDTO.setEmail("john@hotmail.com");
    	registrolDTO.setPassword("john123*");
    	TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
    	tipoIdentificacionDTO.setId(1L);
    	registrolDTO.setTipoIdentificacion(tipoIdentificacionDTO);
    	ResponseEntity<RegistroDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/registro/registrarUsuario", registrolDTO, RegistroDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(200, postResponse.getStatusCode().value());
    	
    }
    
    public void adicionarTipoIdInicial()
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
