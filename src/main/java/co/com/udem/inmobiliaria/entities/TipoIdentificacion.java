package co.com.udem.inmobiliaria.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TipoIdentificacion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipoDocumento;
	
	@OneToMany(mappedBy = "tipoIdentificacion")
	private Collection<Registro> registrarUsuario;

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
