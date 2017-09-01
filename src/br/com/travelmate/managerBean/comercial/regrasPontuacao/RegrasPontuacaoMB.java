package br.com.travelmate.managerBean.comercial.regrasPontuacao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.RegraVendaFacade;
import br.com.travelmate.model.Regravenda;

@Named
@ViewScoped
public class RegrasPontuacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Regravenda> listaRegras;

	@PostConstruct()
	public void init() {
		gerarListaRegras();
	}

	public List<Regravenda> getListaRegras() {
		return listaRegras;
	}

	public void setListaRegras(List<Regravenda> listaRegras) {
		this.listaRegras = listaRegras;
	}

	public String novaRegra() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 560);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("cadRegras", options, null);
		return "";
	}

	public void gerarListaRegras() {
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "select r from Regravenda r order by r.produtos.descricao";
		listaRegras = regraVendaFacade.lista(sql);
	}

	public String editar(Regravenda regravenda) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 560);
		options.put("closable", false);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("regraVenda", regravenda);
		RequestContext.getCurrentInstance().openDialog("cadRegras", options, null);
		return "";
	}

}
