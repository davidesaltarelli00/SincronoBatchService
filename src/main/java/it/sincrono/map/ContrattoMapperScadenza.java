package it.sincrono.map;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import it.sincrono.entities.AnagraficaDto;
import it.sincrono.entities.Anagrafica;
import it.sincrono.entities.Commessa;
import it.sincrono.entities.Ccnl;
import it.sincrono.entities.Contratto;
import it.sincrono.entities.LivelloContratto;
import java.util.ArrayList;

public class ContrattoMapperScadenza implements RowMapper<AnagraficaDto> {

	@Override
	public AnagraficaDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		AnagraficaDto anagraficaDto = new AnagraficaDto();

		Anagrafica anagrafica = new Anagrafica();

		anagrafica.setId(rs.getInt("a.id"));

		anagraficaDto.setAnagrafica(anagrafica);

		Contratto contratto = new Contratto();

		contratto.setId(rs.getInt("c.id"));

		contratto.setDataAssunzione((Date) rs.getDate("data_assunzione"));

		contratto.setMesiDurata(rs.getInt("mesi_durata"));

		anagraficaDto.setContratto(contratto);

		//Commessa commessa = new Commessa();

		//commessa.setId(rs.getInt("e.id"));

		List<Integer> commesse = new ArrayList<>();

		commesse.add(rs.getInt("e.id"));

		anagraficaDto.setCommesse(commesse);

		return anagraficaDto;

	}

}