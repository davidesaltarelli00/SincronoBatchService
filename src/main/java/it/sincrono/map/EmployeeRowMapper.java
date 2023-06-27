package it.sincrono.map;

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.sincrono.entities.Contratto;


public class EmployeeRowMapper implements RowMapper<Contratto> {

 

	@Override
	public Contratto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Contratto contratto = new Contratto();
       
        // Altri mapping delle proprietà dell'oggetto Employee se necessario
        return contratto;
	}
}