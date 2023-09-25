package it.sincrono;



import java.sql.Array;

import javax.sql.DataSource;

import it.sincrono.step.ContrattoItemProcessorScadenza;



import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import it.sincrono.entities.AnagraficaDto;
import it.sincrono.entities.Contratto;
import it.sincrono.map.ContrattoMapper;
import it.sincrono.step.ContrattoItemProcessor;
import it.sincrono.step.ContrattoItemReader;
import it.sincrono.step.ContrattoItemReaderScadenza;



@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Job importEmployeesJob(JobExecutionListener listener, Step step1,Step step2,Step step3) {
	  return jobBuilderFactory.get("importBatchJob")
	    .incrementer(new RunIdIncrementer())
	    .listener(listener)
	    .start(step1)
	    .next(step2)
	    .next(step3)
	    .build();
	}	
	


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    
	
	
	
	//step
	
	@Bean
	public Step step1(JdbcBatchItemWriter<Contratto> writerStep1) {
		
	  return stepBuilderFactory.get("step1")
	    .<Contratto, Contratto> chunk(5)
	    .reader(readerStep1())
	    .processor(processorStep1())
	    .writer(writerStep1)
	    .build();
	}
	
	@Bean
	public Step step2(JdbcBatchItemWriter<Contratto> writerStep2) {
		
	  return stepBuilderFactory.get("step2")
	    .<Contratto, Contratto> chunk(5)
	    .reader(readerStep1())
	    .processor(processorStep1())
	    .writer(writerStep2)
	    .build();
	}
	
	
	@Bean
	public Step step3(JdbcBatchItemWriter<AnagraficaDto> writerStep5) {
		
	  return stepBuilderFactory.get("step3")
	    .<AnagraficaDto, AnagraficaDto> chunk(5)
	    .reader(readerStep2())
	    .processor(processorStep2())
	    .writer(writerStep5)
	    .build();
	}
	


	
	//reader
	
	@Bean
	@StepScope
	public ContrattoItemReader readerStep1() {
		
		return new ContrattoItemReader();
	}
	
	@Bean
	@StepScope
	public ContrattoItemReaderScadenza readerStep2() {
		
		return new ContrattoItemReaderScadenza();
	}


	
	//processor
	

	
	@Bean
	public ContrattoItemProcessor processorStep1() {
		
		return new ContrattoItemProcessor();
	}
	
	
	@Bean
	public ContrattoItemProcessorScadenza processorStep2() {
		
		return new ContrattoItemProcessorScadenza();
	}
	
	

    
    
    //writer
    
	@Bean
	public JdbcBatchItemWriter<Contratto> writerStep1(DataSource dataSource) {
	   String updateQuery = "UPDATE contratto SET id_tipo_livello = :livelloContrattoId WHERE id = :id";

	  return new JdbcBatchItemWriterBuilder<Contratto>()
	    .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
	    .sql(updateQuery)
	    .dataSource(dataSource)
	    .itemSqlParameterSourceProvider(item -> {
	        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
	        sqlParameterSource.addValue("livelloContrattoId", item.getLivelloContratto().getId());
	        sqlParameterSource.addValue("id", item.getId());
	        return sqlParameterSource;
	    })
	    .build();
	}


	
	
	
    @Bean
    public JdbcBatchItemWriter<Contratto> writerStep2(DataSource dataSource) {
        String updateQuery = "INSERT INTO contratti_scatti_livello (id_contratto) VALUES (:contrattoId)";
        
        return new JdbcBatchItemWriterBuilder<Contratto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(updateQuery)
                .itemSqlParameterSourceProvider(item -> {
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("contrattoId", item.getId());
                    return parameterSource;
                })
                .dataSource(dataSource)
                .build();
    }
    
 
    
    @Bean
    public JdbcBatchItemWriter<AnagraficaDto> writerStep5(DataSource dataSource) {
        String updateQuery = "UPDATE contratto c,commessa d,anagrafica a SET c.attivo=0,d.attivo=0,a.attivo=0 where c.id=:idContratto and d.id in(:idCommesse) and a.id=:idAnagrafica";
        //String updateQuery1 = "UPDATE contratto SET attivo = FALSE WHERE id = :idContratto";
        //String updateQuery2 = "UPDATE Anagrafica SET attivo = FALSE WHERE id = :idAnagrafica;";
    	
    
        
        
        return new JdbcBatchItemWriterBuilder<AnagraficaDto>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql(updateQuery)
                
                //.sql(updateQuery1)
                //.sql(updateQuery2)
                .itemSqlParameterSourceProvider(item -> {
                    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
                    parameterSource.addValue("idCommesse",item.getCommesse());
                    parameterSource.addValue("idContratto",item.getContratto().getId());
                    parameterSource.addValue("idAnagrafica",item.getAnagrafica().getId());
                    return parameterSource;
                })
                .dataSource(dataSource)
                .build();
    }
    
 


}