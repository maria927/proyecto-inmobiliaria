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
import co.com.udem.inmobiliaria.dto.FiltroDTO;
import co.com.udem.inmobiliaria.dto.PropiedadDTO;
import co.com.udem.inmobiliaria.dto.RegistroDTO;
import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.entities.Registro;
import co.com.udem.inmobiliaria.entities.TipoIdentificacion;
import co.com.udem.inmobiliaria.repositories.PropiedadRepository;
import co.com.udem.inmobiliaria.repositories.TipoIdentificacionRepository;
import co.com.udem.inmobiliaria.utils.ConvertPropiedad;
import co.com.udem.inmobiliaria.utils.ConvertTipoIdentificacion;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoInmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropiedadRestControllerTest {
	
    @Autowired
    private TestRestTemplate restTemplate;
        
    @Autowired
    private PropiedadRepository propiedadRepo;
    
    @Autowired
  	private ConvertTipoIdentificacion convertTipoId;
    
    @Autowired
    private TipoIdentificacionRepository tipoIdenRepo;

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
		autenticationRequestDTO.setUsername("1234567");
		autenticationRequestDTO.setPassword("juan123*");
		ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/auth/signin",
				autenticationRequestDTO, String.class);
		Gson g = new Gson();
		AutenticationResponseDTO autenticationResponseDTO = g.fromJson(postResponse.getBody(),
				AutenticationResponseDTO.class);
		token = autenticationResponseDTO.getToken();
	}
      
    @Test
    public void agregarPropiedadTest()
    {

    	PropiedadDTO propiedadDTO = new PropiedadDTO();
    	propiedadDTO.setArea(150.0);
    	propiedadDTO.setNumerobaños(2);
    	propiedadDTO.setNumerohabitaciones(3);
    	propiedadDTO.setTipopropiedad("Arriendo");
    	propiedadDTO.setValor(1200000.0);
    	RegistroDTO registroDTO = new RegistroDTO();
    	registroDTO.setId(1L);
    	propiedadDTO.setRegistro(registroDTO);
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + this.token);

		HttpEntity<PropiedadDTO> entity = new HttpEntity<PropiedadDTO>(propiedadDTO, headers);
		ResponseEntity<String> postResponse = restTemplate.exchange(
				getRootUrl() + "/propiedad/registrarPropiedad", HttpMethod.POST, entity, String.class);
		System.err.println("adicionar propiedad: " + postResponse);
		assertEquals(200, postResponse.getStatusCode().value());
    	
    }

    
    @Test
    public void buscarPropiedadTest() {
    	HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
    	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/propiedad/listarPropiedad/1", HttpMethod.GET,
				entity, String.class);
		System.err.println("buscar propiedad: " + postResponse);
		assertEquals(200, postResponse.getStatusCode().value());
		
    }
    
    
    @Test
    public void obtenerPropiedades() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedad/listarPropiedades", HttpMethod.GET, entity,
                String.class);
        assertNotNull(response.getBody());
        System.err.println("Obtener todo "+response);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void modificarPropiedadTest() {
    	int id = 1;
    	PropiedadDTO propiedadDTO = new PropiedadDTO();
    	propiedadDTO.setArea(150.0);
    	propiedadDTO.setNumerobaños(2);
    	propiedadDTO.setNumerohabitaciones(3);
    	propiedadDTO.setTipopropiedad("venta");
    	propiedadDTO.setValor(150000000.0);
    	RegistroDTO registroDTO = new RegistroDTO();
    	registroDTO.setId(1L);
    	propiedadDTO.setRegistro(registroDTO);
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + this.token);
		HttpEntity<PropiedadDTO> entity = new HttpEntity<PropiedadDTO>(propiedadDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedad/modificarPropiedad/" + id, HttpMethod.PUT,
				entity, String.class);	
		System.err.println("modificar propiedad: " + response);
		assertEquals(400, response.getStatusCode().value()); 
    }

    @Test
    public void eliminarPropiedadTest() {
         int id = 1;
	     HttpHeaders headers = new HttpHeaders();
	 	 headers.setContentType(MediaType.APPLICATION_JSON);
	 	 headers.set("Authorization", "Bearer " + this.token);
	 	 HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		 ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/propiedad/eliminarPropiedad/" + id, HttpMethod.DELETE,
				entity, String.class);
		 System.err.println("eliminar tipo id: " + postResponse);
    }
    
    @Test
    public void filtrarPropiedades() {
        HttpHeaders headers = new HttpHeaders();
        FiltroDTO filtroDTO = new FiltroDTO();
        filtroDTO.setArea(150.0);
        filtroDTO.setNumeroHabitaciones(3);
        filtroDTO.setPrecioInicial(0.0);
        filtroDTO.setPrecioFinal(160000000.0);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity<FiltroDTO> entity = new HttpEntity<>(filtroDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedad/filtrarPropiedad", HttpMethod.POST, entity,
                String.class); 

        assertNotNull(response.getBody());
        System.err.println("Obtener filtro"+response);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void filtrarArea() {
        HttpHeaders headers = new HttpHeaders();
        FiltroDTO filtroDTO = new FiltroDTO();
        filtroDTO.setArea(150.0);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity<FiltroDTO> entity = new HttpEntity<>(filtroDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedad/filtrarPorArea", HttpMethod.POST, entity,
                String.class); 

        assertNotNull(response.getBody());
        System.err.println("Obtener filtro"+response);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void filtrarPorValor() {
        HttpHeaders headers = new HttpHeaders();
        FiltroDTO filtroDTO = new FiltroDTO();
        filtroDTO.setPrecioInicial(0.0);
        filtroDTO.setPrecioFinal(160000000.0);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity<FiltroDTO> entity = new HttpEntity<>(filtroDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedad/filtrarPorValor", HttpMethod.POST, entity,
                String.class); 

        assertNotNull(response.getBody());
        System.err.println("Obtener filtro"+response);
        assertEquals(200, response.getStatusCode().value());
    }
    
    @Test
    public void filtrarPorHabitaciones() {
        HttpHeaders headers = new HttpHeaders();
        FiltroDTO filtroDTO = new FiltroDTO();
        filtroDTO.setNumeroHabitaciones(3);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);
        HttpEntity<FiltroDTO> entity = new HttpEntity<>(filtroDTO, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedad/filtrarPorValor", HttpMethod.POST, entity,
                String.class); 

        assertNotNull(response.getBody());
        System.err.println("Obtener filtro"+response);
        assertEquals(200, response.getStatusCode().value());
    }
    

    public void adicionarUsuarioTest()
    {
    	RegistroDTO registrolDTO= new RegistroDTO();
    	adicionarTipoIdInicial();
    	registrolDTO.setNumeroIdentificacion("1234567");
    	registrolDTO.setNombres("Juan");
    	registrolDTO.setApellidos("Perez");
    	registrolDTO.setDireccion("Calle 24");
    	registrolDTO.setTelefono((long) 2222222);
    	registrolDTO.setEmail("john@hotmail.com");
    	registrolDTO.setPassword("juan123*");
    	TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
    	tipoIdentificacionDTO.setId(1L);
    	registrolDTO.setTipoIdentificacion(tipoIdentificacionDTO);
    	ResponseEntity<RegistroDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/registro/registrarUsuario", registrolDTO, RegistroDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    	
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
