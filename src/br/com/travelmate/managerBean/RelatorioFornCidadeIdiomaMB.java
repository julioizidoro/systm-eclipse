package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.facade.IdiomaFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Pais;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class RelatorioFornCidadeIdiomaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedorcidadeidioma fornecedorcidadeidioma;
	private List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma;
	private Pais pais;
	private Cidade cidade;
	private List<Pais> listaPais;
	private Idioma idioma;
	private List<Idioma> listaIdiomas;
	
	
	
	@PostConstruct
	public void init() {
		listarIdiomas();
		listarPaises();
	}



	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}



	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}



	public List<Fornecedorcidadeidioma> getListaFornecedorCidadeIdioma() {
		return listaFornecedorCidadeIdioma;
	}



	public void setListaFornecedorCidadeIdioma(List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma) {
		this.listaFornecedorCidadeIdioma = listaFornecedorCidadeIdioma;
	}



	public Pais getPais() {
		return pais;
	}



	public void setPais(Pais pais) {
		this.pais = pais;
	}



	public Cidade getCidade() {
		return cidade;
	}



	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}



	public List<Pais> getListaPais() {
		return listaPais;
	}



	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}



	public Idioma getIdioma() {
		return idioma;
	}



	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}
	
	
	
	public List<Idioma> getListaIdiomas() {
		return listaIdiomas;
	}



	public void setListaIdiomas(List<Idioma> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}



	public void pesquisar() {
		FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
		String sql = "SELECT f FROM Fornecedorcidadeidioma f WHERE f.maioridade>=0";
		if (pais != null && pais.getIdpais() != null) {
			sql = sql + " and f.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais();
		}
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = sql + " and f.fornecedorcidade.cidade.idcidade=" + cidade.getIdcidade();
		}
		if (idioma != null && idioma.getIdidioma() != null) {
			sql = sql + " and f.idioma.ididioma=" + idioma.getIdidioma();
		}
		listaFornecedorCidadeIdioma = fornecedorCidadeIdiomaFacade.listar(sql);
		if (listaFornecedorCidadeIdioma == null) {
			listaFornecedorCidadeIdioma = new ArrayList<Fornecedorcidadeidioma>();
		}
		for (int i = 0; i < listaFornecedorCidadeIdioma.size(); i++) {
			if (listaFornecedorCidadeIdioma.get(i).isHabilitada()) {
				listaFornecedorCidadeIdioma.get(i).setTipoOrcamento("Tarifario");
				listaFornecedorCidadeIdioma.get(i).setAnoTarifario("" + listaFornecedorCidadeIdioma.get(i).getAno());
			}else {
				listaFornecedorCidadeIdioma.get(i).setTipoOrcamento("Manual");
				listaFornecedorCidadeIdioma.get(i).setAnoTarifario("");
			}
			
			if (listaFornecedorCidadeIdioma.get(i).isAcomodacao() || listaFornecedorCidadeIdioma.get(i).isAcomodacaoindependente()) {
				listaFornecedorCidadeIdioma.get(i).setPossuiAcomodacao("../../resources/img/confirmar.png");
			}else {
				listaFornecedorCidadeIdioma.get(i).setPossuiAcomodacao("../../resources/img/cancelado.png");
			}
		}
		Mensagem.lancarMensagemInfo("Pesquisa com sucesso", "");
	}
	
	
	public void limparPesquisar() {
		pais = null;
		cidade = null;
		idioma = null;
		listaFornecedorCidadeIdioma = new ArrayList<Fornecedorcidadeidioma>();
	}
	
	
	public void listarIdiomas() {
		IdiomaFacade idiomaFacade = new IdiomaFacade();
		listaIdiomas = idiomaFacade.listar("Select i from Idioma i order by i.descricao");
		if (listaIdiomas == null) {
			listaIdiomas = new ArrayList<Idioma>();
		}
	}
	
	
	public void listarPaises() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
	}

}
