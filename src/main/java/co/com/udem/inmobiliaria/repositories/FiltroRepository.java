package co.com.udem.inmobiliaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.udem.inmobiliaria.entities.Propiedad;

public interface FiltroRepository extends JpaRepository<Propiedad, Long> {

}
