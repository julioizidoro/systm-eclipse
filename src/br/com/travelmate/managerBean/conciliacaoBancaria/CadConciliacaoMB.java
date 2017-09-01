package br.com.travelmate.managerBean.conciliacaoBancaria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.model.Unidadenegocio;

@Named
@ViewScoped
public class CadConciliacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Planoconta planoconta;
	private List<Planoconta> listaPlanoConta;
	private ConciliacaoBean conciliacaoBean;
	private TransacaoBean transacaoBean;
	private Contaspagar outroslancamentos;
	private Banco banco;
	private String campoNomeValor;
	private Float valor;
	private Unidadenegocio unidadenegocio;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conciliacaoBean = (ConciliacaoBean) session.getAttribute("conciliacaoBean");
        transacaoBean = (TransacaoBean) session.getAttribute("transacaoBean");
        outroslancamentos = (Contaspagar) session.getAttribute("outroslancamentos");
        banco = (Banco) session.getAttribute("banco");
        unidadenegocio = (Unidadenegocio) session.getAttribute("unidadenegocio");
        session.removeAttribute("unidadenegocio");
        session.removeAttribute("banco");
        session.removeAttribute("transacaoBean");
        session.removeAttribute("conciliacaoBean");
		carregarPlanoConta();
		if (outroslancamentos == null) {
			outroslancamentos = new Contaspagar();
		}
	}
	 
	
	
	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}



	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}



	public Float getValor() {
		return valor;
	}



	public void setValor(Float valor) {
		this.valor = valor;
	}



	public String getCampoNomeValor() {
		return campoNomeValor;
	}



	public void setCampoNomeValor(String campoNomeValor) {
		this.campoNomeValor = campoNomeValor;
	}



	public Banco getBanco() {
		return banco;
	}



	public void setBanco(Banco banco) {
		this.banco = banco;
	}



	public Contaspagar getOutroslancamentos() {
		return outroslancamentos;
	}



	public void setOutroslancamentos(Contaspagar outroslancamentos) {
		this.outroslancamentos = outroslancamentos;
	}



	public TransacaoBean getTransacaoBean() {
		return transacaoBean;
	}



	public void setTransacaoBean(TransacaoBean transacaoBean) {
		this.transacaoBean = transacaoBean;
	}



	public Planoconta getPlanoconta() {
		return planoconta;
	}




	public void setPlanoconta(Planoconta planoconta) {
		this.planoconta = planoconta;
	}




	public List<Planoconta> getListaPlanoConta() {
		return listaPlanoConta;
	}




	public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
		this.listaPlanoConta = listaPlanoConta;
	}




	public ConciliacaoBean getConciliacaoBean() {
		return conciliacaoBean;
	}




	public void setConciliacaoBean(ConciliacaoBean conciliacaoBean) {
		this.conciliacaoBean = conciliacaoBean;
	}




	public void carregarPlanoConta(){
        PlanoContaFacade planoContaFacade = new PlanoContaFacade();
        listaPlanoConta = planoContaFacade.listar("");
        if (listaPlanoConta==null){
            listaPlanoConta = new ArrayList<Planoconta>();
        }
    }
	
	
	public String salvarConciliacao(){
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		outroslancamentos.setBanco(banco);
		outroslancamentos.setPlanoconta(planoconta);
		outroslancamentos.setDatacompensacao(transacaoBean.getData());
		outroslancamentos.setDescricao(transacaoBean.getDescricao());
		outroslancamentos.setUnidadenegocio(unidadenegocio);
		outroslancamentos.setConciliacao("0");
		if (transacaoBean.getValorEntrada() > 0) {
			outroslancamentos.setValorentrada(valor);
			outroslancamentos.setValorsaida(0f);
		}else{
			outroslancamentos.setValorsaida(valor);
			outroslancamentos.setValorentrada(0f);
		}
		outroslancamentos = contasPagarFacade.salvar(outroslancamentos);
		RequestContext.getCurrentInstance().closeDialog(outroslancamentos);
		return "";
	}
	
	public String nomeCampoValor(){
		if (transacaoBean.getValorEntrada() > 0) {
			campoNomeValor = "Valor Entrada";
			valor = transacaoBean.getValorEntrada();
			return campoNomeValor;
		}else{
			valor = transacaoBean.getValorSaida();
			campoNomeValor = "Valor Saida";
			return campoNomeValor;
		}
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

}
