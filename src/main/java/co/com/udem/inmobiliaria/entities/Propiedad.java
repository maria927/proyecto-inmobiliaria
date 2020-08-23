package co.com.udem.inmobiliaria.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Propiedad {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;	
	private double area;
	private int numerohabitaciones;
	private int numerobaños;
	private String tipopropiedad;
	private double valor;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "regist")
	private Registro registro;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getArea() {
		return area;
	}
	public void setArea(double area) {
		this.area = area;
	}
	public int getNumerohabitaciones() {
		return numerohabitaciones;
	}
	public void setNumerohabitaciones(int numerohabitaciones) {
		this.numerohabitaciones = numerohabitaciones;
	}
	public int getNumerobaños() {
		return numerobaños;
	}
	public void setNumerobaños(int numerobaños) {
		this.numerobaños = numerobaños;
	}
	public String getTipopropiedad() {
		return tipopropiedad;
	}
	public void setTipopropiedad(String tipopropiedad) {
		this.tipopropiedad = tipopropiedad;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public Registro getRegistro() {
		return registro;
	}
	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	
	public Propiedad(Long id, double area, int numerohabitaciones, int numerobaños, String tipopropiedad, double valor,
			Registro registro) {
		super();
		this.id = id;
		this.area = area;
		this.numerohabitaciones = numerohabitaciones;
		this.numerobaños = numerobaños;
		this.tipopropiedad = tipopropiedad;
		this.valor = valor;
		this.registro = registro;
	}
	public Propiedad() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}