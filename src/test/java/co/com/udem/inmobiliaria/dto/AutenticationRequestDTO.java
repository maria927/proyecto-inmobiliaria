package co.com.udem.inmobiliaria.dto;

public class AutenticationRequestDTO {
	
	private String username;
	private String password;
	public AutenticationRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AutenticationRequestDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}