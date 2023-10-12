package it.sincrono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeleteRecord {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void deleteAllRecords() {

		String deleteQuery = "TRUNCATE TABLE rapportini";
		jdbcTemplate.execute(deleteQuery);
	}
}
