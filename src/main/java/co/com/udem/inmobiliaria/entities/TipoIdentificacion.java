package co.com.udem.inmobiliaria.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class TipoIdentificacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipoDocumento;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "tipoIdentificacion", targetEntity = Registro.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Registro> registrarUsuario = new HashSet<Registro>();

	public TipoIdentificacion() {
		super();

	}

	public TipoIdentificacion(Long id, String tipoDocumento) {
		super();
		this.id = id;
		this.tipoDocumento = tipoDocumento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}




}
