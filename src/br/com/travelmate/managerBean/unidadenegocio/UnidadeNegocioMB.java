package br.com.travelmate.managerBean.unidadenegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Unidadenegocio;

@Named
@ViewScoped
public class UnidadeNegocioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome = "";
	private List<Unidadenegocio> listaUnidade;

	@PostConstruct
	public void init() {
		gerarlistaUnidade();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public String novaUnidade() {
		return "cadUnidadeNegocio";
	}

	public void gerarlistaUnidade() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(true);
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<Unidadenegocio>();
		}
	}

	public String editarUnidade(Unidadenegocio unidadenegocio) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("unidadenegocio", unidadenegocio);
		return "cadUnidadeNegocio";
	}

	public void pesquisar() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidade = unidadeNegocioFacade.listar(nome);
	}

	public void limpar() {
		nome = "";
		gerarlistaUnidade();
	}
}
