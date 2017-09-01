package br.com.travelmate.managerBean.grupoAcesso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.GrupoAcessoFacade;
import br.com.travelmate.model.Grupoacesso;

@Named
@ViewScoped
public class GrupoAcessoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nome = "";
	private List<Grupoacesso> listaAcesso;

	@PostConstruct
	public void init() {
		gerarlistaAcesso();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Grupoacesso> getListaAcesso() {
		return listaAcesso;
	}

	public void setListaAcesso(List<Grupoacesso> listaAcesso) {
		this.listaAcesso = listaAcesso;
	}

	public String novo() {
		return "cadAcesso";
	}

	public void gerarlistaAcesso() {
		GrupoAcessoFacade grupoAcessoFacade = new GrupoAcessoFacade();
		String sql = "select g from Grupoacesso g order by g.descricao";
		listaAcesso = grupoAcessoFacade.listar(sql);
		if (listaAcesso == null) {
			listaAcesso = new ArrayList<Grupoacesso>();
		}
	}

	public String editar(Grupoacesso grupoacesso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("grupoacesso", grupoacesso);
		return "cadAcesso";
	}

	public void pesquisar() {
		GrupoAcessoFacade grupoAcessoFacade = new GrupoAcessoFacade();
		String sql = "select g from Grupoacesso g where g.descricao like '%" + nome + "%' order by g.descricao";
		listaAcesso = grupoAcessoFacade.listar(sql);
	}

	public void limpar() {
		nome = "";
		gerarlistaAcesso();
	}
}
