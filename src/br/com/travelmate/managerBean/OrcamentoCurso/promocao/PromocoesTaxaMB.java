package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

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

import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.FornecedorFacade;  
import br.com.travelmate.facade.PromocaoTaxaCidadeFacade;
import br.com.travelmate.facade.PromocaoTaxaCursoFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Pais; 
import br.com.travelmate.model.Promocaotaxacidade;
import br.com.travelmate.model.Promocaotaxacurso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class PromocoesTaxaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedor> listaFornecedor;
	private List<Pais> listaPais;
	private Fornecedor fornecedor;
	private List<Pais> listaPaisSelecionado;
	private boolean habilitarBtnNovo;
	private List<Promocaotaxacurso> listaPromocoes;

	@PostConstruct
	public void init() {
		habilitarBtnNovo = true;
		gerarListaFornecedor();
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Pais> getListaPaisSelecionado() {
		return listaPaisSelecionado;
	}

	public void setListaPaisSelecionado(List<Pais> listaPaisSelecionado) {
		this.listaPaisSelecionado = listaPaisSelecionado;
	}

	public boolean isHabilitarBtnNovo() {
		return habilitarBtnNovo;
	}

	public void setHabilitarBtnNovo(boolean habilitarBtnNovo) {
		this.habilitarBtnNovo = habilitarBtnNovo;
	}

	public List<Promocaotaxacurso> getListaPromocoes() {
		return listaPromocoes;
	}

	public void setListaPromocoes(List<Promocaotaxacurso> listaPromocoes) {
		this.listaPromocoes = listaPromocoes;
	}

	public String cadPromocaoTaxa() {
		gerarListaCidade();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedor", fornecedor);
		return "cadPromocoesTaxas";
	}

	public void gerarListaFornecedor() {
		FornecedorFacade forncedorFacade = new FornecedorFacade();
		listaFornecedor = forncedorFacade
				.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}

	public void gerarListaPais() {
		if (fornecedor != null && fornecedor.getIdfornecedor() != null) {
			String sql = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor() + " and f.ativo=1 Group by f.cidade.pais.idpais order by f.cidade.pais.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
			listaPais = new ArrayList<Pais>();
			listaPaisSelecionado = new ArrayList<Pais>();
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					listaPais.add(lista.get(i).getCidade().getPais());
				}
			}
			listaPaisSelecionado = listaPais;
		} else {
			listaPaisSelecionado = new ArrayList<Pais>();
			listaPais = new ArrayList<Pais>();
		}
		habilitarBtnNovo();
		gerarListaPromocoes();
	}

	public void habilitarBtnNovo() {
		if (listaPaisSelecionado != null && listaPaisSelecionado.size() > 0) {
			habilitarBtnNovo = false;
		} else {
			habilitarBtnNovo = true;
		}
	}

	public void gerarListaCidade() {
		String sql = "";
		if (fornecedor != null) {
			if (listaPaisSelecionado != null && listaPaisSelecionado.size() > 0) {
				List<Cidade> listaCidade = new ArrayList<Cidade>();
				sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor()
						+ " and f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais=" + listaPaisSelecionado.get(0).getIdpais();
				for (int i = 0; i < listaPaisSelecionado.size(); i++) {
					sql = sql + " or f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais=" + listaPaisSelecionado.get(i).getIdpais();
				}
				sql = sql + " Group by f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade order by f.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
				FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
				List<Fornecedorcidadeidiomaproduto> listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
				for (int j = 0; j < listaFornecedorCidade.size(); j++) {
					listaCidade.add(listaFornecedorCidade.get(j).getFornecedorcidadeidioma().getFornecedorcidade().getCidade());
				}
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("listaCidade", listaCidade);
			}
		}
	}

	public void gerarListaPromocoes() {
		listaPromocoes = new ArrayList<Promocaotaxacurso>();
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		String sql = "select p from Promocaotaxacidade p where p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
				+ fornecedor.getIdfornecedor()
				+ " and (p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
				+ listaPaisSelecionado.get(0).getIdpais();
		for (int i = 1; i < listaPaisSelecionado.size(); i++) {
			sql = sql
					+ " or p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ listaPaisSelecionado.get(i).getIdpais();
		}
		sql = sql
				+ ") group by p.promocaotaxacurso.idpromocaotaxacurso order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais";
		List<Promocaotaxacidade> listacidade = promocaoTaxaCidadeFacade.listar(sql);
		if (listacidade != null) {
			listaPromocoes = new ArrayList<Promocaotaxacurso>();
			for (int i = 0; i < listacidade.size(); i++) {
				listaPromocoes.add(listacidade.get(i).getPromocaotaxacurso());
			}
		}
	}

	public void excluir(Promocaotaxacurso promocaotaxacurso) {
		// cidade
		String sqlCidade = "select p from Promocaotaxacidade p where p.promocaotaxacurso.idpromocaotaxacurso="
				+ promocaotaxacurso.getIdpromocaotaxacurso();
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listacidade = new ArrayList<Promocaotaxacidade>();
		listacidade = promocaoTaxaCidadeFacade.listar(sqlCidade);
		if (listacidade != null) {
			for (int i = 0; i < listacidade.size(); i++) {
				promocaoTaxaCidadeFacade.excluir(listacidade.get(i));
			}
		}

		// curso
		PromocaoTaxaCursoFacade promocaoTaxaCursoFacade = new PromocaoTaxaCursoFacade();
		promocaoTaxaCursoFacade.excluir(promocaotaxacurso);
		gerarListaPromocoes();
	}

	public String descricao(Promocaotaxacurso promocaotaxacurso) {
		String descricao = "";
		if (promocaotaxacurso.getAcomodacaoselecionada()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaotaxacurso.getCargahoraria() != null && promocaotaxacurso.getCargahoraria() > 0
				&& promocaotaxacurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaotaxacurso.getCargahoraria() + " "
					+ promocaotaxacurso.getTipocargahoraria() + " | ";
		}
		if (promocaotaxacurso.getDataacomodacaoinicial() != null && promocaotaxacurso.getDatafinalacomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatafinalacomodacao()) + " | ";
		}
		if (promocaotaxacurso.getDatainiciocursoinicial() != null && promocaotaxacurso.getDatainiciocursofinal() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursofinal()) + " | ";
		}
		if (promocaotaxacurso.getDatamatriculaenciadaate() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatamatriculaenciadaate()) + " | ";
		}
		if (promocaotaxacurso.getDataterminio() != null) {
			descricao = descricao + "Data termino de curso até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataterminio()) + " | ";
		}
		if ((promocaotaxacurso.getDatavalidadefinal() != null) && (promocaotaxacurso.getDatavalidadeinicial() != null)) {
			descricao = descricao + "Data validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadeinicial()) + " a "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaotaxacurso.getNumerosemanafinal() != null && promocaotaxacurso.getNumerosemanasinicial() != null
				&& promocaotaxacurso.getNumerosemanasinicial() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaotaxacurso.getNumerosemanasinicial() + " até "
					+ promocaotaxacurso.getNumerosemanafinal() + " | ";
		}
		if (promocaotaxacurso.getTurno() != null && promocaotaxacurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaotaxacurso.getTurno() + " | ";
		}
		if (promocaotaxacurso.getValorporsemana() != null && promocaotaxacurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaotaxacurso.getValorporsemana()) + " | ";
		}
		
		if (promocaotaxacurso.getTipodesconto() != null) {
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre "+
						promocaotaxacurso.getProdutosorcamento().getDescricao()+": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + "% | ";
			}
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre "+
						promocaotaxacurso.getProdutosorcamento().getDescricao()+": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + " | ";
			}
		}
		return descricao;
	}

	public String cidades(Promocaotaxacurso promocaotaxacurso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaotaxacurso", promocaotaxacurso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("consCidadesTaxas", options, null);
		return "";
	}

	public String editarPromocaoTaxa(Promocaotaxacurso promocaotaxacurso) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaotaxacurso", promocaotaxacurso);
		return "editarPromocoesTaxas";
	}
}
