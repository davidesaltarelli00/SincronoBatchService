package it.sincrono.step;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import it.sincrono.entities.AnagraficaDto;
import it.sincrono.entities.Commessa;
import it.sincrono.map.ContrattoMapperScadenza;

import java.util.ArrayList;

public class ContrattoItemReaderScadenza implements ItemReader<AnagraficaDto> {

	private static final Logger log = LoggerFactory.getLogger(ContrattoItemReaderScadenza.class);

	private DataSource dataSource;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private int currentPage;
	private List<AnagraficaDto> listAnagraficaDto;
	private boolean check=true;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public AnagraficaDto read() {
		
		
		if (listAnagraficaDto == null || listAnagraficaDto.isEmpty() && check) {
			listAnagraficaDto = ordinaCommesse(fetchAnagraficaFromDatabase());
		}

		if (!listAnagraficaDto.isEmpty()) {
			check=false;
			AnagraficaDto anagraficaDto = listAnagraficaDto.remove(0);
			log.info("Reading next Contratto: " + anagraficaDto.toString());
			return anagraficaDto;
		}

		return null;
	}

	private List<AnagraficaDto> fetchAnagraficaFromDatabase() {

		String sql = "SELECT\r\n" + "    a.id,\r\n" + "    c.id,\r\n" + "    c.data_assunzione,\r\n"
				+ "    c.mesi_durata,\r\n" + "    e.id\r\n" + "FROM\r\n" + "    anagrafica a\r\n" + "INNER JOIN\r\n"
				+ "    storico_contratti b ON a.id = b.id_anagrafica\r\n" + "INNER JOIN\r\n"
				+ "    contratto c ON b.id_contratto = c.id\r\n" + "INNER JOIN\r\n"
				+ "    storico_commesse d ON d.id_anagrafica = a.id\r\n" + "INNER JOIN\r\n"
				+ "    commessa e ON d.id_commessa = e.id\r\n" + "WHERE\r\n" + "    c.id = (\r\n" + "        SELECT\r\n"
				+ "            MAX(c1.id)\r\n" + "        FROM\r\n" + "            contratto c1\r\n"
				+ "        INNER JOIN\r\n" + "            storico_contratti s ON s.id_contratto = c1.id\r\n"
				+ "        WHERE\r\n" + "            s.id_anagrafica = a.id\r\n" + "    )\r\n" + "";

		// int offset = (currentPage) * pageSize;

		return jdbcTemplate.query(sql, new ContrattoMapperScadenza());
	}

	private List<AnagraficaDto> ordinaCommesse(List<AnagraficaDto> list) {

		List<AnagraficaDto> listAnagraficadto = new ArrayList<>();
		
		AnagraficaDto anagraficaDtoNew = new AnagraficaDto();

		for (AnagraficaDto anagraficaDto : list) {


			if (anagraficaDtoNew.getAnagrafica()!= null) {

				if (anagraficaDto.getAnagrafica().getId() == anagraficaDtoNew.getAnagrafica().getId()) {

					continue;
				}

			}
			
			anagraficaDtoNew = new AnagraficaDto();

			anagraficaDtoNew.setAnagrafica(anagraficaDto.getAnagrafica());

			anagraficaDtoNew.setContratto(anagraficaDto.getContratto());

			anagraficaDtoNew.setCommesse(new ArrayList<Integer>());

			for (AnagraficaDto anagraficaDtoApp : list) {

				if (anagraficaDtoApp.getAnagrafica().getId() == anagraficaDtoNew.getAnagrafica().getId()) {

					anagraficaDtoNew.getCommesse().add(anagraficaDtoApp.getCommesse().get(0));

				}

			}

			listAnagraficadto.add(anagraficaDtoNew);

		}

		return listAnagraficadto;

	}

}
