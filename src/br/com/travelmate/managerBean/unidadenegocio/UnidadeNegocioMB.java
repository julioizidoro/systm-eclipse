package br.com.travelmate.managerBean.unidadenegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Unidadenegocio;

@Named
@ViewScoped
public class UnidadeNegocioMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nome = "";
	private List<Unidadenegocio> listaUnidade;
	private String imagemEditar = "";
	private String tituloEditar = "";

	@PostConstruct
	public void init() {
		gerarlistaUnidade();
		if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isEditarunidade()) {
			tituloEditar = "Editar (Alterar informações da unidade)";
			imagemEditar = "../../resources/img/editaricon.png";
		}else{
			tituloEditar = "Visualizar (Informações sobre a unidade)";
			imagemEditar = "../../resources/img/verOrcamento.png";
		}
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

	public String getImagemEditar() {
		return imagemEditar;
	}

	public void setImagemEditar(String imagemEditar) {
		this.imagemEditar = imagemEditar;
	}

	public String getTituloEditar() {
		return tituloEditar;
	}

	public void setTituloEditar(String tituloEditar) {
		this.tituloEditar = tituloEditar;
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
	
	
	public void visualizarUsuarios(Unidadenegocio unidadenegocio){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("unidadenegocio", unidadenegocio);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("consUsuariosUnidade");
	}
}
