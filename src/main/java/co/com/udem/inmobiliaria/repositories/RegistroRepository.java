package co.com.udem.inmobiliaria.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import co.com.udem.inmobiliaria.entities.Registro;
import co.com.udem.inmobiliaria.entities.User;


public interface RegistroRepository extends CrudRepository<Registro, Long> {

	@Query("SELECT u FROM Registro u WHERE u.numeroIdentificacion = ?1  and tipo_iden=?2")
	Registro buscarDocumentoTipo(Long numeroIdentificacion, Long tipoIdentificacion);
	
	Optional<Registro> findByUsername(String username);

	
}
