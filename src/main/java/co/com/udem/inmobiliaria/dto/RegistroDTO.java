package co.com.udem.inmobiliaria.dto;

import org.springframework.beans.factory.annotation.Autowired;

public class RegistroDTO {
	
	private Long id;	
	private Long numeroIdentificacion;
	private String nombres;
	private String apellidos;
	private String direccion;
	private Long telefono;
	private String email;
	private String password;
	@Autowired
	private TipoIdentificacionDTO tipoIdentificacionDTO;

	
	public Long getNumeroIdentificacion() {
		return numeroIdentificacion;
	}
	public void setNumeroIdentificacion(Long numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Long getTelefono() {
		return telefono;
	}
	public void setTelefono(Long telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RegistroDTO() {
		super();
	}
	public TipoIdentificacionDTO getTipoIdentificacionDTO() {
		return tipoIdentificacionDTO;
	}
	public void setTipoIdentificacionDTO(TipoIdentificacionDTO tipoIdentificacionDTO) {
		this.tipoIdentificacionDTO = tipoIdentificacionDTO;
	}
	public RegistroDTO(Long id, Long numeroIdentificacion, String nombres, String apellidos, String direccion,
			Long telefono, String email, String password, TipoIdentificacionDTO tipoIdentificacionDTO) {
		super();
		this.id = id;
		this.numeroIdentificacion = numeroIdentificacion;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.tipoIdentificacionDTO = tipoIdentificacionDTO;
	}
	

	
	
	

}
