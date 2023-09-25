package it.sincrono.entities;

import java.util.List;


import it.sincrono.entities.Anagrafica;
import it.sincrono.entities.Commessa;
import it.sincrono.entities.Contratto;


public class AnagraficaDto {

	private Anagrafica anagrafica;

	private Contratto contratto;

	private List<Integer> commesse;



	public AnagraficaDto(Anagrafica anagrafica, Contratto contratto, List<Integer> commesse) {
		super();
		this.anagrafica = anagrafica;
		this.contratto = contratto;
		this.commesse = commesse;
	}

	public AnagraficaDto() {
		super();
	}

	public Anagrafica getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(Anagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}

	public Contratto getContratto() {
		return contratto;
	}

	public void setContratto(Contratto contratto) {
		this.contratto = contratto;
	}

	public List<Integer> getCommesse() {
		return commesse;
	}

	public void setCommesse(List<Integer> commesse) {
		this.commesse = commesse;
	}


}
