package co.com.udem.inmobiliaria.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.udem.inmobiliaria.entities.Propiedad;
import co.com.udem.inmobiliaria.entities.Registro;


public interface PropiedadRepository extends CrudRepository<Propiedad, Long> {

//	@Query("SELECT u FROM Registro u WHERE u.numeroIdentificacion = ?1  and tipo_iden=?2")
//	Registro buscarDocumentoTipo(Long numeroIdentificacion, Long tipoIdentificacion);
}
