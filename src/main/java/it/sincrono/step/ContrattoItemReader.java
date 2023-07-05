package it.sincrono.step;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import it.sincrono.entities.Contratto;
import it.sincrono.map.ContrattoMapper;

public class ContrattoItemReader implements ItemReader<Contratto> {

    private static final Logger log = LoggerFactory.getLogger(ContrattoItemReader.class);

    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private int currentPage;
    private List<Contratto> contratti;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

   

    @Override
    public Contratto read() {
        if (contratti == null || contratti.isEmpty()) {
            contratti = fetchContrattiFromDatabase(15);
            currentPage++;
        }

        if (!contratti.isEmpty()) {
            Contratto contratto = contratti.remove(0);
            log.info("Reading next Contratto: " + contratto.toString());
            return contratto;
        }

        return null;
    }

    private List<Contratto> fetchContrattiFromDatabase(int pageSize) {
    	
    	String sql = "SELECT * FROM contratto c WHERE c.attivo = true and c.id>0 LIMIT ? OFFSET ?";

        int offset = (currentPage) * pageSize;

        return jdbcTemplate.query(sql, new Object[]{pageSize, offset}, new ContrattoMapper());
    }
}
