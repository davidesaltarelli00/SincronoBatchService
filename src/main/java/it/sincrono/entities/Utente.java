package it.sincrono.entities;

import java.util.Set;




import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "utenti")
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "token_password")
	private String tokenPassword;

	@Column(name = "attivo")
	private Boolean attivo;


	public Utente(Integer id, String username, String password, String tokenPassword, Boolean attivo) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.tokenPassword = tokenPassword;
		this.attivo = attivo;
	}

	public Utente(String username, Boolean attivo,String password) {
		super();
		this.username = username;
		this.attivo = attivo;
		this.password=password;
	}
	
	

	public Utente(Integer id) {
		super();
		this.id = id;
	}

	public Utente() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getTokenPassword() {
		return tokenPassword;
	}

	public void setTokenPassword(String tokenPassword) {
		this.tokenPassword = tokenPassword;
	}

	public Boolean getAttivo() {
		return attivo;
	}

	public void setAttivo(Boolean attivo) {
		this.attivo = attivo;
	}

}
