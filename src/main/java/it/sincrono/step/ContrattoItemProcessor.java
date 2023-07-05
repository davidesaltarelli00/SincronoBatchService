package it.sincrono.step;

import org.slf4j.Logger;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

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
		   
		    

		   Calendar calendar = Calendar.getInstance();
		   
		   calendar.setTime(contratto.getDataAssunzione());

	       Period period=Period.between(LocalDate.of(calendar.get(Calendar.YEAR), 
	        		calendar.get(Calendar.MONTH) + 1,calendar.get(Calendar.DAY_OF_MONTH)), LocalDate.now());
	       
	        int years = period.getYears();
        	int months = period.getMonths();
	      

	        int diffInMonths = years * 12 + months;
	  
		   
		   
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