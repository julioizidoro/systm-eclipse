/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

import java.util.List;

import br.com.travelmate.facade.PacoteCircuitoFacade;
import br.com.travelmate.facade.PacoteCruzeiroFacade;
import br.com.travelmate.facade.PacotePassagemPassageiroFacade;
import br.com.travelmate.facade.PacoteTransferFacade;
import br.com.travelmate.facade.PacoteTremFacade;
import br.com.travelmate.facade.PacotesCarroFacade;
import br.com.travelmate.facade.PacotesFornecedorFacade;
import br.com.travelmate.facade.PacotesHotelFacade;
import br.com.travelmate.facade.PacotesPassagemFacade; 
import br.com.travelmate.model.Pacotecarro;
import br.com.travelmate.model.Pacotecircuito;
import br.com.travelmate.model.Pacotecruzeiro;
import br.com.travelmate.model.Pacotehotel;
import br.com.travelmate.model.Pacoteingresso;
import br.com.travelmate.model.Pacotepassagem;
import br.com.travelmate.model.Pacotepassagempassageiro;
import br.com.travelmate.model.Pacotepasseio; 
import br.com.travelmate.model.Pacotesfornecedor;
import br.com.travelmate.model.Pacotetransfer;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pacotetrem;

/**
 *
 * @author Wolverine
 */
public class GerarPacotesFornecedorBean {

	private List<Pacotetrecho> listaTrecho;

	public GerarPacotesFornecedorBean(List<Pacotetrecho> listaTrecho) {
		this.listaTrecho = listaTrecho;
		salvarPacotesFornecedor();
	}

	private void salvarPacotesFornecedor() { 
		for(int i=0;i<listaTrecho.size();i++){ 
        	PacotesCarroFacade pacotesCarroFacade = new PacotesCarroFacade();
        	Pacotecarro pacotecarro = pacotesCarroFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());  
        	if(pacotecarro!=null && pacotecarro.getIdpacoteCarro()!=null){
        		salvarPacoteFornecedor(pacotecarro);
        	} 
        	
            PacotesPassagemFacade pacotesPassagemFacade = new PacotesPassagemFacade();
        	Pacotepassagem pacotepassagem = pacotesPassagemFacade.consultar(listaTrecho.get(i).getIdpacotetrecho()); 
        	if(pacotepassagem!=null && pacotepassagem.getIdpacotepassagem()!=null){
        		salvarPacoteFornecedor(pacotepassagem);
        	} 
        	
        	PacoteCruzeiroFacade pacotesCruzeiroFacade = new PacoteCruzeiroFacade();
        	Pacotecruzeiro pacotecruzeiro = pacotesCruzeiroFacade.consultar(listaTrecho.get(i).getIdpacotetrecho()); 
        	if(pacotecruzeiro!=null && pacotecruzeiro.getIdpacotecruzeiro()!=null){
        		salvarPacoteFornecedor(pacotecruzeiro);
        	} 
            
        	PacotesHotelFacade pacoteHotelFacade = new PacotesHotelFacade();
        	Pacotehotel pacotehotel= pacoteHotelFacade.consultar(listaTrecho.get(i).getIdpacotetrecho()); 
        	if(pacotehotel!=null && pacotehotel.getIdpacoteHotel()!=null){
        		salvarPacoteFornecedor(pacotehotel);
        	}
            
        	PacoteTransferFacade pacoteTransferFacade = new PacoteTransferFacade();
        	Pacotetransfer pacotetransfer =  pacoteTransferFacade.consultar(listaTrecho.get(i).getIdpacotetrecho()); 
        	if(pacotetransfer!=null && pacotetransfer.getIdpacotetransfer()!=null){
        		salvarPacoteFornecedor(pacotetransfer);
        	}
           
        	PacoteTremFacade pacoteTremFacade = new PacoteTremFacade();
        	Pacotetrem pacotetrem = pacoteTremFacade.consultar(listaTrecho.get(i).getIdpacotetrecho()); 
        	if(pacotetrem!=null && pacotetrem.getIdpacotetrem()!=null){
        		salvarPacoteFornecedor(pacotetrem);
        	} 
        	
        	PacoteCircuitoFacade pacoteCircuitoFacade = new PacoteCircuitoFacade();
        	Pacotecircuito pacotecircuito  = pacoteCircuitoFacade.consultar(listaTrecho.get(i).getIdpacotetrecho()); 
        	if(pacotecircuito!=null && pacotecircuito.getIdpacotecircuito()!=null){
        		salvarPacoteFornecedor(pacotecircuito);
        	}   
        	
        	if(listaTrecho.get(i).getPacoteingressoList()!=null){
        		for (int j = 0; j < listaTrecho.get(i).getPacoteingressoList().size(); j++) {
        			salvarPacoteFornecedor(listaTrecho.get(i).getPacoteingressoList().get(j));
				}
        	}
        	if(listaTrecho.get(i).getPacotepasseioList()!=null){
        		for (int j = 0; j < listaTrecho.get(i).getPacotepasseioList().size(); j++) {
        			salvarPacoteFornecedor(listaTrecho.get(i).getPacotepasseioList().get(j));
				}
        	} 
        }
	}
	
	public void salvarPacoteFornecedor(Pacotecarro pacotecarro){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotecarro.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotecarro.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotecarro.getIdpacoteCarro());
		pacotesfornecedor.setTipoprodutotrecho("Carro");
		pacotesfornecedor.setPacotes(pacotecarro.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacotecarro.getTarifa()+pacotecarro.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	}
	
	public void salvarPacoteFornecedor(Pacotecruzeiro pacotecruzeiro){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotecruzeiro.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotecruzeiro.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotecruzeiro.getIdpacotecruzeiro());
		pacotesfornecedor.setTipoprodutotrecho("Cruzeiro");
		pacotesfornecedor.setPacotes(pacotecruzeiro.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacotecruzeiro.getTarifa()+pacotecruzeiro.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	}
	
	public void salvarPacoteFornecedor(Pacotehotel pacotehotel){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotehotel.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotehotel.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotehotel.getIdpacoteHotel());
		pacotesfornecedor.setTipoprodutotrecho("Hotel");
		pacotesfornecedor.setPacotes(pacotehotel.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacotehotel.getTarifa()+pacotehotel.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	}
	
	public void salvarPacoteFornecedor(Pacotetransfer pacotetransfer){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotetransfer.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotetransfer.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotetransfer.getIdpacotetransfer());
		pacotesfornecedor.setTipoprodutotrecho("Transfer");
		pacotesfornecedor.setPacotes(pacotetransfer.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacotetransfer.getTarifa()+pacotetransfer.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	}
	
	public void salvarPacoteFornecedor(Pacotetrem pacotetrem){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotetrem.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotetrem.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotetrem.getIdpacotetrem());
		pacotesfornecedor.setTipoprodutotrecho("Trem/Onibus");
		pacotesfornecedor.setPacotes(pacotetrem.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacotetrem.getTarifa()+pacotetrem.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	}
	
	public void salvarPacoteFornecedor(Pacotecircuito pacotecircuito){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotecircuito.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotecircuito.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotecircuito.getIdpacotecircuito());
		pacotesfornecedor.setTipoprodutotrecho("Circuito");
		pacotesfornecedor.setPacotes(pacotecircuito.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacotecircuito.getTarifa()+pacotecircuito.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	} 
	
	public void salvarPacoteFornecedor(Pacoteingresso pacoteingresso){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacoteingresso.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacoteingresso.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacoteingresso.getIdpacoteingresso());
		pacotesfornecedor.setTipoprodutotrecho("Ingresso");
		pacotesfornecedor.setPacotes(pacoteingresso.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacoteingresso.getTarifa()+pacoteingresso.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	} 
	
	public void salvarPacoteFornecedor(Pacotepasseio pacotepasseio){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotepasseio.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotepasseio.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotepasseio.getIdpacotepasseio());
		pacotesfornecedor.setTipoprodutotrecho("Passeio");
		pacotesfornecedor.setPacotes(pacotepasseio.getPacotetrecho().getPacotes());
		pacotesfornecedor.setValor(pacotepasseio.getTarifa()+pacotepasseio.getTaxa());
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	} 
	
	public void salvarPacoteFornecedor(Pacotepassagem pacotepassagem){
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		Pacotesfornecedor pacotesfornecedor = new Pacotesfornecedor();
		pacotesfornecedor.setComprovante(false);
		pacotesfornecedor.setDatapagamento(pacotepassagem.getDatapagamentoparceiro());
		pacotesfornecedor.setEmailenviado(false);
		pacotesfornecedor.setFornecedor(pacotepassagem.getFornecedorcidade().getFornecedor());
		pacotesfornecedor.setIdprodutotrecho(pacotepassagem.getIdpacotepassagem());
		pacotesfornecedor.setTipoprodutotrecho("Passagem");
		pacotesfornecedor.setPacotes(pacotepassagem.getPacotetrecho().getPacotes());
		 
	    	PacotePassagemPassageiroFacade pacotePassagemPassageiroFacade = new PacotePassagemPassageiroFacade();
	    	List<Pacotepassagempassageiro> lista;
	    	String sql;
	    	sql = "SELECT p From Pacotepassagempassageiro p where p.pacotepassagem.idpacotepassagem="+pacotepassagem.getIdpacotepassagem();
	    	lista = pacotePassagemPassageiroFacade.lista(sql); 
	    	float total = 0;
	    	for (int i = 0; i < lista.size(); i++) {
	    		if(lista.get(i).getCategoria().equalsIgnoreCase("ADT")){
	    			total = total + pacotepassagem.getAdttarifa()
	    				+ pacotepassagem.getAdttaxas()+pacotepassagem.getAdttaxaemissao();
	    		}else if(lista.get(i).getCategoria().equalsIgnoreCase("CHD")){
	    			total = total + pacotepassagem.getChdtarifa() + pacotepassagem.getChdtaxas()
	    				+ pacotepassagem.getChdtaxaemissao();
	    		}else{
	    			total = total + pacotepassagem.getInftarifa() + pacotepassagem.getInftaxas()
	    				+ pacotepassagem.getInftaxaemissao();
	    		}
        } 
		pacotesfornecedor.setValor(total);
		pacotesFornecedorFacade.salvar(pacotesfornecedor);
	}

	public List<Pacotetrecho> getListaTrecho() {
		return listaTrecho;
	}

	public void setListaTrecho(List<Pacotetrecho> listaTrecho) {
		this.listaTrecho = listaTrecho;
	}

}
