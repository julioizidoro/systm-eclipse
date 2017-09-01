package br.com.travelmate.managerBean.comercial.metasFaturamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.facade.MetaFaturamentoMensalFacade;
import br.com.travelmate.model.Metasfaturamentomensal;

@Named
@ViewScoped
public class CadMetasFaturamentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Metasfaturamentomensal metasfaturamentomensal;
	private List<Unidadenegocio> listaUnidade;
	private Unidadenegocio unidade;
	private String notificacaoValidarDados;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		metasfaturamentomensal = (Metasfaturamentomensal) session.getAttribute("metasfaturamentomensal");
		session.removeAttribute("metasfaturamentomensal");
		listarUnidades(); 
		if (metasfaturamentomensal == null) {
			metasfaturamentomensal = new Metasfaturamentomensal();
		}else{
			unidade = metasfaturamentomensal.getUnidadenegocio();
		} 
	}



	public Metasfaturamentomensal getMetasfaturamentomensal() {
		return metasfaturamentomensal;
	}



	public void setMetasfaturamentomensal(Metasfaturamentomensal metasfaturamentomensal) {
		this.metasfaturamentomensal = metasfaturamentomensal;
	}



	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}



	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}



	public Unidadenegocio getUnidade() {
		return unidade;
	}



	public void setUnidade(Unidadenegocio unidade) {
		this.unidade = unidade;
	}
	
	
	public void listarUnidades(){
		listaUnidade = GerarListas.listarUnidade();
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Metasfaturamentomensal());
	}
	
	
	public void salvar(){
		if (validarDados()) {
			metasfaturamentomensal.setUnidadenegocio(unidade);
			metasfaturamentomensal.setValormetasemana(metasfaturamentomensal.getValormeta() / 4);
			metasfaturamentomensal.setValoralcancado(0f);
			metasfaturamentomensal.setPercentualalcancado(0f);
			MetaFaturamentoMensalFacade metaFaturamentoMensalFacade = new MetaFaturamentoMensalFacade();
			metasfaturamentomensal = metaFaturamentoMensalFacade.salvar(metasfaturamentomensal);
			RequestContext.getCurrentInstance().closeDialog(metasfaturamentomensal);
		}else{
			Mensagem.lancarMensagemInfo(notificacaoValidarDados, "");
		}
	}   
	
	
	public boolean validarDados(){
		notificacaoValidarDados = "";
		if (unidade == null) {
			notificacaoValidarDados = notificacaoValidarDados + " Selecione a unidade.";
			return false;
		}
		if (metasfaturamentomensal.getAno() == null) {
			notificacaoValidarDados = notificacaoValidarDados + " Escreva o ano. ";
			return false;
		}
		if (metasfaturamentomensal.getMes() == null) {
			notificacaoValidarDados = notificacaoValidarDados + " Escreva o Mes.";
			return false;
		}
		if (metasfaturamentomensal.getValormeta() == null) {
			notificacaoValidarDados = notificacaoValidarDados + " Escreva o valor da meta";
			return false;
		}
		return true;
	}

}
