package co.com.udem.inmobiliaria.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.udem.inmobiliaria.entities.Propiedad;
import co.com.udem.inmobiliaria.entities.Registro;


public interface PropiedadRepository extends CrudRepository<Propiedad, Long> {


	List<Propiedad> findByNumerohabitaciones(int numerohabitaciones);
	List<Propiedad> findByArea(double area);
	List<Propiedad> findByValor(double valor);
	
	
//	@Query("SELECT u.id, u.area, u.numerohabitaciones, u.tipopropiedad, u.valor FROM Propiedad u WHERE u.area  = ?1 ")
//	List<Object> findByArea(double area);
}
