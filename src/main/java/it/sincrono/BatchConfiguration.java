package it.sincrono;



import javax.sql.DataSource;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.sincrono.entities.Contratto;
import it.sincrono.map.EmployeeRowMapper;
import it.sincrono.step.ContrattoItemProcessor;



@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job importEmployeesJob(JobExecutionListener listener, Step step1,Step step2) {
	  return jobBuilderFactory.get("importEmployeesJob")
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .start(step1)
	    .next(step2)
	    .build();
	}	
	
	@Bean
	public Step step1(JdbcBatchItemWriter<Contratto> writerStep1,JdbcPagingItemReader<Contratto> readerStep1,
			JdbcBatchItemWriter<Contratto> writerSecondStep1) {
		
	  return stepBuilderFactory.get("step1")
	    .<Contratto, Contratto> chunk(5)
	    .reader(readerStep1)
	    .processor(processorStep1())
	    .writer(writerStep1)
	    .build();
	}
	
	@Bean
	public Step step2(JdbcBatchItemWriter<Contratto> writerStep1,JdbcPagingItemReader<Contratto> readerStep1,
			JdbcBatchItemWriter<Contratto> writerStep2) {
		
	  return stepBuilderFactory.get("step2")
	    .<Contratto, Contratto> chunk(5)
	    .reader(readerStep1)
	    .processor(processorStep1())
	    .writer(writerStep2)
	    .build();
	}
	

	@Bean
	public JdbcPagingItemReader<Contratto> readerStep1(DataSource dataSource, PagingQueryProvider queryProvider) {
	    JdbcPagingItemReader<Contratto> reader = new JdbcPagingItemReader<>();
	    reader.setDataSource(dataSource);
	    reader.setQueryProvider(queryProvider);
	    reader.setPageSize(100);
	    reader.setRowMapper(new EmployeeRowMapper());
	    return reader;
	}


	

	
	@Bean
	public ContrattoItemProcessor processorStep1() {
		
		return new ContrattoItemProcessor();
	}
	
	

	
	@Bean
	public JdbcBatchItemWriter<Contratto> writerStep1(DataSource dataSource) {
	
	   String updateQuery = "UPDATE contratto SET id_tipo_livello = : contratto.livelloContratto.id WHERE id = :id";	
		
	  return new JdbcBatchItemWriterBuilder<Contratto>()
	    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	    .sql(updateQuery)
	    .dataSource(dataSource)
	    .build();
	}	
	
	
	
	@Bean
	public JdbcBatchItemWriter<Contratto> writerStep2(DataSource dataSource) {
	
	   String updateQuery = "INSERT INTO contratti_scatti_livello (id_contratto) VALUES (:contratto.id)";	
		
	  return new JdbcBatchItemWriterBuilder<Contratto>()
	    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	    .sql(updateQuery)
	    .dataSource(dataSource)
	    .build();
	}
}