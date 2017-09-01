package br.com.travelmate.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.travelmate.util.Formatacao;

public class UtilBean {
	
	
	public Date calcularDataPagamentoComissaoAuPairEUA(Date dataEmbarque){
		Date dataPagamento = null;
		int mes = Formatacao.getMesData(dataEmbarque)+1;
		int ano = Formatacao.getAnoData(dataEmbarque);
		String dataFormatada="";
		if (mes>=1 && mes<=3){
			dataFormatada =  "15/04/" + String.valueOf(ano);  
		}else if (mes>=4 && mes<=6){
			dataFormatada =  "15/07/" + String.valueOf(ano); 
		}else if (mes>=7 && mes<=9){
			dataFormatada =  "15/10/" + String.valueOf(ano); 
		}else if (mes>=10 && mes<=12){
			dataFormatada =  "15/01/" + String.valueOf(ano+1); 
		}
		dataPagamento = Formatacao.ConvercaoStringData(dataFormatada);
		return dataPagamento;
	}
	
	public Date calcularDataRetornoMinimo(Date dataEmbarque, int numerosemanas){ 
		Calendar c = new GregorianCalendar();
		c.setTime(dataEmbarque);
		c.add(Calendar.WEEK_OF_MONTH, numerosemanas);
		return c.getTime(); 
	}
	
	public Integer calcularNumeroSemanas(Date dataInicial, Date dataFinal) { 
		int resultado = 0;
		resultado = (int) ((dataFinal.getTime() - dataInicial.getTime()) / 86400000L);
		int numeroSemanas = (resultado % 7);
		if (numeroSemanas > 0) {
			resultado = resultado - numeroSemanas;
		}
		numeroSemanas = resultado / 7;
		return numeroSemanas;
	}

}
