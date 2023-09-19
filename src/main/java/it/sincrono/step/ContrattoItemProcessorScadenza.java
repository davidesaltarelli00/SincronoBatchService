package it.sincrono.step;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import it.sincrono.entities.AnagraficaDto;
import it.sincrono.entities.Contratto;

public class ContrattoItemProcessorScadenza implements ItemProcessor<AnagraficaDto, AnagraficaDto> {

	private static final Logger log = LoggerFactory.getLogger(ContrattoItemProcessorScadenza.class);

	@Override
	public AnagraficaDto process(AnagraficaDto anagraficaDto) {

		if (checkScadenza(anagraficaDto)) {

			return anagraficaDto;

		} else {

			return null;
		}

	}

	private boolean checkScadenza(AnagraficaDto anagraficaDto) {
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(anagraficaDto.getContratto().getDataAssunzione());
		LocalDate localDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
				calendar.get(Calendar.DAY_OF_MONTH));
		
		LocalDate dataSommata = localDate.plusMonths(anagraficaDto.getContratto().getMesiDurata());
		
		
		if(dataSommata.isBefore(LocalDate.now())){
			
			
			return true;
			
		}else {
			
			return false;
		}
		

	}

}