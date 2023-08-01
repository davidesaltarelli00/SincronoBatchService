package it.sincrono.map;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.sincrono.entities.Ccnl;
import it.sincrono.entities.Contratto;
import it.sincrono.entities.LivelloContratto;

public class ContrattoMapper implements RowMapper<Contratto> {

	@Override
	public Contratto mapRow(ResultSet rs, int rowNum) throws SQLException {

		Contratto contratto = new Contratto();

		contratto.setId(rs.getInt("id"));

		LivelloContratto livelloContratto = new LivelloContratto();
		livelloContratto.setId(rs.getInt("id_tipo_livello"));
		contratto.setLivelloContratto(livelloContratto);

		Ccnl ccnl = new Ccnl();
		ccnl.setId(rs.getInt("id_tipo_ccnl"));
		contratto.setCcnl(ccnl);

		contratto.setDataAssunzione(rs.getDate("data_assunzione"));

		contratto.setMesiDurata(rs.getInt("mesi_durata"));

		return contratto;
	}

}