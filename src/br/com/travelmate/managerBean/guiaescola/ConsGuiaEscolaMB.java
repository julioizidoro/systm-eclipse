package br.com.travelmate.managerBean.guiaescola;

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

import br.com.travelmate.facade.FornecedorCidadeGuiaFacade;
import br.com.travelmate.model.Fornecedorcidadeguia;

@Named
@ViewScoped
public class ConsGuiaEscolaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedorcidadeguia> listaGuia;
	private String nome;

	@PostConstruct
	public void init() {
		gerarListaGuia();
	}

	public List<Fornecedorcidadeguia> getListaGuia() {
		return listaGuia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setListaGuia(List<Fornecedorcidadeguia> listaGuia) {
		this.listaGuia = listaGuia;
	}

	public void gerarListaGuia() {
		String sql = "select f from Fornecedorcidadeguia f group by f.fornecedorcidade.fornecedor.idfornecedor order by f.fornecedorcidade.fornecedor.nome";
		FornecedorCidadeGuiaFacade fornecedorCidadeGuiaFacade = new FornecedorCidadeGuiaFacade();
		listaGuia = fornecedorCidadeGuiaFacade.listar(sql);
	}

	public String guia(Fornecedorcidadeguia fornecedorcidadeguia) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("guiaescola", fornecedorcidadeguia.getGuiaescola());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 580);
		RequestContext.getCurrentInstance().openDialog("guiaEscola");
		return "";
	}

	public String cidades(Fornecedorcidadeguia fornecedorcidadeguia) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("guiaescola", fornecedorcidadeguia.getGuiaescola());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 580);
		RequestContext.getCurrentInstance().openDialog("fornecedorCidadeGuia");
		return "";
	}

	public void pesquisar() {
		String sql = "select f from Fornecedorcidadeguia f where f.fornecedorcidade.fornecedor.nome like '" + nome
				+ "%' group by f.fornecedorcidade.fornecedor.idfornecedor order by f.fornecedorcidade.fornecedor.nome";
		FornecedorCidadeGuiaFacade fornecedorCidadeGuiaFacade = new FornecedorCidadeGuiaFacade();
		listaGuia = fornecedorCidadeGuiaFacade.listar(sql);
	}

	public void limparPesquisa() {
		nome = "";
		gerarListaGuia();
	}
}
