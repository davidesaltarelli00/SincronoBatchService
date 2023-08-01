package it.sincrono.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_livelli_contrattuali")
public class LivelloContratto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "ccnl")
	private String ccnl;

	@Column(name = "livello")
	private String livello;

	@Column(name = "minimi_ret_23")
	private String minimiRet23;

	public LivelloContratto(Integer id, String ccnl, String livello, String minimiRet23) {
		super();
		this.id = id;
		this.ccnl = ccnl;
		this.livello = livello;
		this.minimiRet23 = minimiRet23;
	}

	public LivelloContratto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCcnl() {
		return ccnl;
	}

	public void setCcnl(String ccnl) {
		this.ccnl = ccnl;
	}

	public String getLivello() {
		return livello;
	}

	public void setLivello(String livello) {
		this.livello = livello;
	}

	public String getMinimiRet23() {
		return minimiRet23;
	}

	public void setMinimiRet23(String minimiRet23) {
		this.minimiRet23 = minimiRet23;
	}

}
