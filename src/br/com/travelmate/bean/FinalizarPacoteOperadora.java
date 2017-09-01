/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

import br.com.travelmate.facade.PacoteCircuitoFacade;
import br.com.travelmate.facade.PacoteCruzeiroFacade;
import br.com.travelmate.facade.PacotePassagemPassageiroFacade;
import br.com.travelmate.facade.PacoteTransferFacade;
import br.com.travelmate.facade.PacoteTremFacade;
import br.com.travelmate.facade.PacotesCarroFacade;
import br.com.travelmate.facade.PacotesHotelFacade;
import br.com.travelmate.facade.PacotesPassagemFacade;
import br.com.travelmate.facade.PacotesServicoFacade;
import br.com.travelmate.model.Pacotecarro;
import br.com.travelmate.model.Pacotecircuito;
import br.com.travelmate.model.Pacotecruzeiro;
import br.com.travelmate.model.Pacotehotel;
import br.com.travelmate.model.Pacoteingresso;
import br.com.travelmate.model.Pacotepassagem;
import br.com.travelmate.model.Pacotepassagempassageiro;
import br.com.travelmate.model.Pacotepasseio;
import br.com.travelmate.model.Pacoteservico;
import br.com.travelmate.model.Pacotetransfer;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pacotetrem;

import java.util.List;

/**
 *
 * @author Wolverine
 */
public class FinalizarPacoteOperadora {
    
    private List<Pacotetrecho> listaTrecho;
    private float tarifas;
    private float taxas;
    private float comissaoFornecedor;

    public FinalizarPacoteOperadora(List<Pacotetrecho> listaTrecho) {
        this.listaTrecho = listaTrecho;
        tarifas=0.0f;
        taxas=0.0f;
        comissaoFornecedor=0.0f;
        iniciarCalculo();
    }
    
    private void iniciarCalculo(){
        for(int i=0;i<listaTrecho.size();i++){
        	PacotesCarroFacade pacotesCarroFacade = new PacotesCarroFacade();
        	Pacotecarro pacotecarro = new Pacotecarro();
        	pacotecarro = pacotesCarroFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
            calcularValoresCarro(pacotecarro);
            
            PacotesPassagemFacade pacotesPassagemFacade = new PacotesPassagemFacade();
        	Pacotepassagem pacotepassagem = new Pacotepassagem();
        	pacotepassagem = pacotesPassagemFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
        	calcularValoresPassagem(pacotepassagem);
        	
        	PacoteCruzeiroFacade pacotesCruzeiroFacade = new PacoteCruzeiroFacade();
        	Pacotecruzeiro pacotecruzeiro = new Pacotecruzeiro();
        	pacotecruzeiro = pacotesCruzeiroFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
        	calcularValoresCruzeiro(pacotecruzeiro);
            
        	PacotesHotelFacade pacoteHotelFacade = new PacotesHotelFacade();
        	Pacotehotel pacotehotel= new Pacotehotel();
        	pacotehotel = pacoteHotelFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
        	calcularValoresHotel(pacotehotel);
            
        	calcularValoresIngresso(listaTrecho.get(i).getPacoteingressoList());
           
        	calcularValoresPasseio(listaTrecho.get(i).getPacotepasseioList());
            
        	PacoteTransferFacade pacoteTransferFacade = new PacoteTransferFacade();
        	Pacotetransfer pacotetransfer= new Pacotetransfer();
        	pacotetransfer = pacoteTransferFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
        	calcularValoresTransfer(pacotetransfer);
           
        	PacoteTremFacade pacoteTremFacade = new PacoteTremFacade();
        	Pacotetrem pacotetrem= new Pacotetrem();
        	pacotetrem = pacoteTremFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
        	calcularValoresTrem(pacotetrem);
        	
        	
        	PacoteCircuitoFacade pacoteCircuitoFacade = new PacoteCircuitoFacade();
        	Pacotecircuito pacotecircuito=  pacoteCircuitoFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
        	calcularValoresCircuito(pacotecircuito);
        	
        	
        	PacotesServicoFacade pacotesServicoFacade = new PacotesServicoFacade();
        	Pacoteservico pacoteservico = pacotesServicoFacade.consultar(listaTrecho.get(i).getIdpacotetrecho());
        	calcularValoresServico(pacoteservico);
        }
    }

    public List<Pacotetrecho> getListaTrecho() {
        return listaTrecho;
    }

    public void setListaTrecho(List<Pacotetrecho> listaTrecho) {
        this.listaTrecho = listaTrecho;
    }

    public float getComissaoFornecedor() {
		return comissaoFornecedor;
	}

	public void setComissaoFornecedor(float comissaoFornecedor) {
		this.comissaoFornecedor = comissaoFornecedor;
	}

	public float getTarifas() {
		return tarifas;
	}

	public void setTarifas(float tarifas) {
		this.tarifas = tarifas;
	}

	public float getTaxas() {
		return taxas;
	}

	public void setTaxas(float taxas) {
		this.taxas = taxas;
	}

	private void calcularValoresCarro(Pacotecarro pacotecarro){
        if (pacotecarro!=null){
            tarifas = tarifas + pacotecarro.getTarifa();
            taxas =  taxas + pacotecarro.getTaxa();
            comissaoFornecedor = comissaoFornecedor + pacotecarro.getComissaofornecedor();
        }
    }
    
    private void calcularValoresPassagem(Pacotepassagem pacotepassagem){
        if (pacotepassagem!=null){
        	PacotePassagemPassageiroFacade pacotePassagemPassageiroFacade = new PacotePassagemPassageiroFacade();
        	List<Pacotepassagempassageiro> lista;
        	String sql;
        	sql = "SELECT p From Pacotepassagempassageiro p where p.pacotepassagem.idpacotepassagem="+pacotepassagem.getIdpacotepassagem();
        	lista = pacotePassagemPassageiroFacade.lista(sql);
        	comissaoFornecedor = comissaoFornecedor + pacotepassagem.getComissaofornecedor();
        	for (int i = 0; i < lista.size(); i++) {
        		if(lista.get(i).getCategoria().equalsIgnoreCase("ADT")){
        			tarifas = tarifas + pacotepassagem.getAdttarifa();
        			taxas =  taxas + pacotepassagem.getAdttaxas()+pacotepassagem.getAdttaxaemissao();
        		}else if(lista.get(i).getCategoria().equalsIgnoreCase("CHD")){
        			tarifas = tarifas + pacotepassagem.getChdtarifa();
        			taxas =  taxas + pacotepassagem.getChdtaxas()+ pacotepassagem.getChdtaxaemissao();
        		}else{
        			tarifas = tarifas + pacotepassagem.getInftarifa();
        			taxas =  taxas + pacotepassagem.getInftaxas()+ pacotepassagem.getInftaxaemissao();
        		}
            }
       	}
    }
    
    private void calcularValoresHotel(Pacotehotel pacotehotel){
        if (pacotehotel!=null){
        	tarifas = tarifas + pacotehotel.getTarifa();
            taxas =  taxas + pacotehotel.getTaxa();
            comissaoFornecedor = comissaoFornecedor + pacotehotel.getComissaofornecedor();
        }
    }
    
    private void calcularValoresCruzeiro(Pacotecruzeiro pacotecruzeiro){
        if (pacotecruzeiro!=null){
        	tarifas = tarifas + pacotecruzeiro.getTarifa();
            taxas =  taxas + pacotecruzeiro.getTaxa();
        }
    }
    
    private void calcularValoresIngresso(List<Pacoteingresso> lista){
        if (lista!=null){
            for(int i=0;i<lista.size();i++){
            	tarifas = tarifas + lista.get(i).getTarifa();
                taxas =  taxas + lista.get(i).getTaxa();
            }
        }
    }
    
    private void calcularValoresPasseio(List<Pacotepasseio> lista){
    	if (lista!=null){
            for(int i=0;i<lista.size();i++){
            	tarifas = tarifas + lista.get(i).getTarifa();
                taxas =  taxas + lista.get(i).getTaxa();
            }
        }
    }
    
    private void calcularValoresTransfer(Pacotetransfer pacotetransfer){
        if (pacotetransfer!=null){
        	tarifas = tarifas + pacotetransfer.getTarifa();
            taxas =  taxas + pacotetransfer.getTaxa();
        }
    }
    
    private void calcularValoresTrem(Pacotetrem pacotetrem){
        if (pacotetrem!=null){
        	tarifas = tarifas + pacotetrem.getTarifa();
            taxas =  taxas + pacotetrem.getTaxa();
        }
    }
    
    
    private void calcularValoresCircuito(Pacotecircuito pacote){
        if (pacote!=null){
        	tarifas = tarifas + pacote.getTarifa();
            taxas =  taxas + pacote.getTaxa();
        }
    }
    
    
    private void calcularValoresServico(Pacoteservico pacote){
        if (pacote!=null){
        	tarifas = tarifas + pacote.getTarifa();
        }
    }
}
