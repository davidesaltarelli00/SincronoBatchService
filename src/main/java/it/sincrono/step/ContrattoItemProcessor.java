package it.sincrono.step;

import org.slf4j.Logger;
import java.time.LocalDate;
import java.time.Period;

import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import it.sincrono.entities.Contratto;

public class ContrattoItemProcessor implements ItemProcessor<Contratto,Contratto> {

	private static final Logger log = LoggerFactory.getLogger(ContrattoItemProcessor.class);

	@Override
	public Contratto process(Contratto contratto) {
		
		if(calcolaLivelloContratto(contratto)) {
			
			return contratto;
			
		}else {
			
			return null;
		}
		
	    
	  
		
		

}
	
	
	private boolean calcolaLivelloContratto(Contratto contratto) {
		
		
		   boolean check=false;
		
		   Integer mesiDurata=contratto.getMesiDurata();
		   

	        int diffInMonths = Period.between(LocalDate.of(contratto.getDataAssunzione().getYear(), 
	        	contratto.getDataAssunzione().getMonth()+1,contratto.getDataAssunzione().getDay()), LocalDate.now()).getMonths();
	  
		   
		   
		   if(contratto.getContrattoNazionale().getId()==0) {
			   
			   mesiDurata=mesiDurata/3;
			   
			   if(diffInMonths==mesiDurata) {
				   
				   if(contratto.getLivelloContratto().getId()<9) {
					   
					   contratto.getLivelloContratto().setId(
							   contratto.getLivelloContratto().getId()+1);
					   
					   check=true;
					   
				   }
				  
				   
				   
		  
				   
			   }else {
				   
				   if(diffInMonths==(mesiDurata*2)) {
					   
					   if(contratto.getLivelloContratto().getId()<9) {
						   
						   contratto.getLivelloContratto().setId(
								   contratto.getLivelloContratto().getId()+1);
						   
						   check=true;
						   
					   }
					   
					   
					   
				   }else {
					   
					   if(diffInMonths==(mesiDurata*3)) {
						   
						   
						   if(contratto.getLivelloContratto().getId()<9) {
							   
							   contratto.getLivelloContratto().setId(
									   contratto.getLivelloContratto().getId()+1);
							   
							   check=true;
							   
						   }
						   
						   
						   
					   }
					   
					   
				   }
				   
				   
			   }
			   
		   }else {
			   
			   mesiDurata=mesiDurata/2;
			   
			   if(diffInMonths==mesiDurata) {
				   
				   if(contratto.getLivelloContratto().getId()<9) {
					   
					   contratto.getLivelloContratto().setId(
							   contratto.getLivelloContratto().getId()+1);
					   
					   check=true;
					   
				   }
				  
				   
				   
		  
				   
			   }else {
				   
				   if(diffInMonths==(mesiDurata*2)) {
					   
					   if(contratto.getLivelloContratto().getId()<9) {
						   
						   contratto.getLivelloContratto().setId(
								   contratto.getLivelloContratto().getId()+1);
						   
						   check=true;
						   
					   }
					   
				   }
				   
			   }
			  
			   
		   }
			   
		   
		   
		   return check;
			
	}
	
	
	
}