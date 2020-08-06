package co.com.udem.inmobiliaria.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	public Propiedad(Long id, double area, int numerohabitaciones, int numerobaños, String tipopropiedad,
			double valor) {
		super();
		this.id = id;
		this.area = area;
		this.numerohabitaciones = numerohabitaciones;
		this.numerobaños = numerobaños;
		this.tipopropiedad = tipopropiedad;
		this.valor = valor;
	}
	public Propiedad() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
