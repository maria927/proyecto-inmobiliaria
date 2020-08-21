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
	
	@Query("SELECT u FROM Propiedad u WHERE u.valor BETWEEN :valorInicial AND :valorFinal")
	List<Propiedad> findByRangoValor(double valorInicial, double valorFinal);
	
	@Query("SELECT u FROM Propiedad u WHERE u.valor >= :valorInicial")
	List<Propiedad> findByValorInicial(double valorInicial);
	
	@Query("SELECT u FROM Propiedad u WHERE u.valor <= :valorFinal")
	List<Propiedad> findByValorFinal(double valorFinal);
}
