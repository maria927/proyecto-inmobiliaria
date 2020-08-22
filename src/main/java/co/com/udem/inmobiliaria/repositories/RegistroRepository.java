package co.com.udem.inmobiliaria.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import co.com.udem.inmobiliaria.entities.Registro;


public interface RegistroRepository extends CrudRepository<Registro, Long> {

	@Query("SELECT u FROM Registro u WHERE u.numeroIdentificacion = ?1  and tipo_iden=?2")
	Registro buscarDocumentoTipo(String numeroIdentificacion, Long tipoIdentificacion);
	
	//@Query("SELECT u FROM Registro u WHERE u.numeroIdentificacion = ?1")
	Optional<Registro> findByNumeroIdentificacion(String numeroIdentificacion);

	
}
