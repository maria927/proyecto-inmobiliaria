package co.com.udem.inmobiliaria.rest.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import co.com.udem.inmobiliaria.ProyectoInmobiliariaApplication;
import co.com.udem.inmobiliaria.dto.RegistroDTO;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProyectoInmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistroRestControllerTest {
	
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
//    @Test
//    public void adicionarUsuarioTest()
//    {
//    	RegistroDTO registrolDTO= new RegistroDTO();
//    	registrolDTO.setTipoIdentificacion("CC");
//    	registrolDTO.setNumeroIdentificacion((long)1017298);
//    	registrolDTO.setNombres("Pepito");
//    	registrolDTO.setApellidos("Lopez");
//    	registrolDTO.setDireccion("Calle 45");
//    	registrolDTO.setTelefono((long) 2222222);
//    	registrolDTO.setEmail("pepito@hotmail.com");
//    	registrolDTO.setPassword("pepito123**");
//    	ResponseEntity<RegistroDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/registro/registrarUsuario", registrolDTO, RegistroDTO.class);
//        assertNotNull(postResponse);
//        assertNotNull(postResponse.getBody());
//    	
//    }
    
    @Test
    public void testObtenerUsuario() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/registro/listarUsuarios", HttpMethod.GET, entity,
                String.class);
        assertNotNull(response.getBody());
    }
    
    @Test
    public void buscarUsuarioTest() {
    	
    	RegistroDTO registro = restTemplate.getForObject(getRootUrl() + "/registro/listarUsuarios/1", RegistroDTO.class);
        assertNotNull(registro);
    }
    
//    @Test
//    public void modificarUsuarioTest() {
//    	Registro registrolDTO= new Registro();
//    	int id = 1;
//    	registrolDTO = restTemplate.getForObject(getRootUrl() + "/registro/modificarUsuario/" + id, Registro.class);
//    	registrolDTO.setTipoIdentificacion("CC");
//    	registrolDTO.setNumeroIdentificacion((long)1017298);
//    	registrolDTO.setNombres("Juanito");
//    	registrolDTO.setApellidos("Lopez Perez");
//    	registrolDTO.setDireccion("Calle 12");
//    	registrolDTO.setTelefono((long) 32392829);
//    	registrolDTO.setEmail("juanito@hotmail.com");
//    	registrolDTO.setPassword("juanito321**");
//        restTemplate.put(getRootUrl() + "/registro/modificarUsuario/" + id, registrolDTO);
//        Registro updatedUsuario = restTemplate.getForObject(getRootUrl() + "/registro/modificarUsuario/" + id, Registro.class);
//        assertNotNull(updatedUsuario);
//    }

 

    @Test
    public void eliminarUsuarioTest() {
         int id = 1;
         RegistroDTO registroDTO= new RegistroDTO();
         registroDTO = restTemplate.getForObject(getRootUrl() + "/registro/eliminarUsuario/" + id, RegistroDTO.class);
         assertNotNull(registroDTO);
         restTemplate.delete(getRootUrl() + "/registro/eliminarUsuario/" + id);
         try {
        	 registroDTO = restTemplate.getForObject(getRootUrl() + "/registro/eliminarUsuario/" + id, RegistroDTO.class);
         } catch (final HttpClientErrorException e) {
              assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
         }
    }
    


}
