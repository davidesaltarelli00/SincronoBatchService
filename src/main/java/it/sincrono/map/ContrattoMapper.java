package it.sincrono.map;
import java.sql.ResultSet;

import it.sincrono.entities.ContrattoNazionale;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

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
        
        ContrattoNazionale contrattoNazionale= new ContrattoNazionale();
        contrattoNazionale.setId(rs.getInt("id_contratto_nazionale"));
        contratto.setContrattoNazionale(contrattoNazionale);
        
        contratto.setDataAssunzione(rs.getDate("data_assunzione"));
        
        contratto.setMesiDurata(rs.getInt("mesi_durata"));
       
        
        return contratto;
    }
    
    
}