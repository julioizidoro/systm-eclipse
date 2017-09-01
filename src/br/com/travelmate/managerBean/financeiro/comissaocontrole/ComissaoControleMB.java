package br.com.travelmate.managerBean.financeiro.comissaocontrole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.ComissaoControleFacade;
import br.com.travelmate.model.Comissaocontrole;
import br.com.travelmate.model.Metasfaturamentomensal;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class ComissaoControleMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mes;
	private int ano;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private List<Comissaocontrole> listaComissaoControle;
	
	
	@PostConstruct
	public void init(){
		listaUnidade = GerarListas.listarUnidade();
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
	}


	public int getMes() {
		return mes;
	}


	public void setMes(int mes) {
		this.mes = mes;
	}


	public int getAno() {
		return ano;
	}


	public void setAno(int ano) {
		this.ano = ano;
	}


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}


	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}
	
	
	
	public List<Comissaocontrole> getListaComissaoControle() {
		return listaComissaoControle;
	}


	public void setListaComissaoControle(List<Comissaocontrole> listaComissaoControle) {
		this.listaComissaoControle = listaComissaoControle;
	}


	public void limparPesquisa(){
		mes = 0;
		ano = 0;
		unidadenegocio = null;
		listaComissaoControle = new ArrayList<>();
	}
	
	
	public void pesquisar(){
		ComissaoControleFacade comissaoControleFacade = new ComissaoControleFacade();
		String sql = "Select c From Comissaocontrole c ";
		if (unidadenegocio != null || (mes > 0 && mes <=12) || ano > 0) {
			sql = sql + " where ";
		}
		
		if (unidadenegocio != null) {
			sql = sql + " c.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			if ((mes > 0 && mes<= 12) || ano > 0) {
				sql = sql + " and ";
			}
		}
		
		if (mes > 0 && mes <= 12) {
			sql = sql + " c.mes=" + mes;
			if (ano > 0) {
				sql = sql + " and";
			}
		}
		
		if (ano > 0) {
			sql = sql + " c.ano=" + ano;
		}
		
		listaComissaoControle = comissaoControleFacade.listar(sql);
		if (listaComissaoControle == null) {
			listaComissaoControle = new ArrayList<>();
		}
	}
	
	
	public void retornarDialogNovo(SelectEvent event){
		Comissaocontrole comissaocontrole = (Comissaocontrole) event.getObject();
		if (comissaocontrole.getIdcomissaocontrole() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			if (listaComissaoControle == null) {
				listaComissaoControle = new ArrayList<>();
			}
			listaComissaoControle.add(comissaocontrole);
		}
	}
	
	
	public void retornarDialogEditar(SelectEvent event){
		Comissaocontrole comissaocontrole = (Comissaocontrole) event.getObject();
		if (comissaocontrole.getIdcomissaocontrole() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
	}
	
	
	public String editarComissaoControle(Comissaocontrole comissaocontrole) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("comissaocontrole", comissaocontrole);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadComissaoControle", options, null);
		return "";
	}

	public String novaComissaoControle() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadComissaoControle", options, null);
		return "";
	}
	
	public void liberarComissao(Comissaocontrole comissaocontrole){
		ComissaoControleFacade comissaoControleFacade = new ComissaoControleFacade();
		comissaocontrole = comissaoControleFacade.salvar(comissaocontrole);
		Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
	}
	
	public void excluir(Comissaocontrole comissaocontrole){
		ComissaoControleFacade comissaoControleFacade = new ComissaoControleFacade();
		comissaoControleFacade.excluir(comissaocontrole.getIdcomissaocontrole());
		listaComissaoControle.remove(comissaocontrole);
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
	}
	
	

}
