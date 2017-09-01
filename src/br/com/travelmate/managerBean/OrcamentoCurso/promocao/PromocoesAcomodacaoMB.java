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
import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.PromocaoAcomodacaoFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class PromocoesAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedor> listaFornecedor;
	private List<Pais> listaPais;
	private Fornecedor fornecedor;
	private List<Pais> listaPaisSelecionado;
	private boolean habilitarBtnNovo;
	private List<Promocaoacomodacao> listaPromocoes;

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

	public List<Promocaoacomodacao> getListaPromocoes() {
		return listaPromocoes;
	}

	public void setListaPromocoes(List<Promocaoacomodacao> listaPromocoes) {
		this.listaPromocoes = listaPromocoes;
	}

	public String cadPromocao() {
		gerarListaCidade();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedor", fornecedor);
		return "cadPromocaoAcomodacao";
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
		listaPromocoes = new ArrayList<Promocaoacomodacao>();
		PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		String sql = "select p from Promocaoacomodacaocidade p where p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
				+ fornecedor.getIdfornecedor() + " and (p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
				+ listaPaisSelecionado.get(0).getIdpais();
		for (int i = 1; i < listaPaisSelecionado.size(); i++) {
			sql = sql + " or p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ listaPaisSelecionado.get(i).getIdpais();
		}
		sql = sql
				+ ") group by p.promocaoacomodacao.idpromoacaoacomodacao order by p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais";
		List<Promocaoacomodacaocidade> listaAcomodacaoCidade = promocaoAcomodacaoCidadeFacade.listar(sql);
		if (listaAcomodacaoCidade != null) {
			listaPromocoes = new ArrayList<Promocaoacomodacao>();
			for (int i = 0; i < listaAcomodacaoCidade.size(); i++) {
				listaPromocoes.add(listaAcomodacaoCidade.get(i).getPromocaoacomodacao());
			}
		}
	}

	public void excluir(Promocaoacomodacao promocaoacomodacao) {
		// cidade
		String sqlCidade = "select p from Promocaoacomodacaocidade p where p.promocaoacomodacao.idpromoacaoacomodacao="
				+ promocaoacomodacao.getIdpromoacaoacomodacao();
		PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		List<Promocaoacomodacaocidade> listaAcomodacaoCidade = new ArrayList<Promocaoacomodacaocidade>();
		listaAcomodacaoCidade = promocaoAcomodacaoCidadeFacade.listar(sqlCidade);
		if (listaAcomodacaoCidade != null) {
			for (int i = 0; i < listaAcomodacaoCidade.size(); i++) {
				promocaoAcomodacaoCidadeFacade.excluir(listaAcomodacaoCidade.get(i));
			}
		}

		// Acomodacao
		PromocaoAcomodacaoFacade promocaoAcomodacaoFacade = new PromocaoAcomodacaoFacade();
		promocaoAcomodacaoFacade.excluir(promocaoacomodacao);
		gerarListaPromocoes();
	}

	public String descricao(Promocaoacomodacao promocaoacomodacao) {
		String descricao = ""; 
		if (promocaoacomodacao.getDatainicioacomodacao() != null && promocaoacomodacao.getDataterminioacodomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioacomodacao()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDataterminioacodomodacao()) + " | ";
		}
		if (promocaoacomodacao.getDatainicioiniciocurso() != null && promocaoacomodacao.getDatainicioterminiocurso() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioiniciocurso()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioterminiocurso()) + " | ";
		}
		if (promocaoacomodacao.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatamatricula()) + " | ";
		}
		if (promocaoacomodacao.getDatavalidadeinicial() != null && promocaoacomodacao.getDatavalidadefinal() != null) {
			descricao = descricao + "Data validade: " + Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatavalidadeinicial())+" até "+Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatavalidadefinal())
					+ " | ";
		}
		if (promocaoacomodacao.getNumerosemanafinal() != null && promocaoacomodacao.getNumerosemanainicio() != null && promocaoacomodacao.getNumerosemanainicio()>0) {
			descricao = descricao + "Nº de semanas: " + promocaoacomodacao.getNumerosemanainicio() + " até "
					+ promocaoacomodacao.getNumerosemanafinal() + " | ";
		}
		if (promocaoacomodacao.getTipoacomodacao() != null && promocaoacomodacao.getTipoacomodacao().length() > 2) {
			descricao = descricao + "Tipo de Acomodação: " + promocaoacomodacao.getTipoacomodacao() + " | ";
		}
		if (promocaoacomodacao.getTipoquarto() != null && promocaoacomodacao.getTipoquarto().length() > 2) {
			descricao = descricao + "Tipo de Quarto: " + promocaoacomodacao.getTipoquarto() + " | ";
		}
		if (promocaoacomodacao.getTiporefeicao() != null && promocaoacomodacao.getTiporefeicao().length() > 2) {
			descricao = descricao + "Tipo de Refeição: " + promocaoacomodacao.getTiporefeicao() + " | ";
		}
		if (promocaoacomodacao.getTipobanheiro() != null && promocaoacomodacao.getTipobanheiro().length() > 2) {
			descricao = descricao + "Tipo de Banheiro: " + promocaoacomodacao.getTipobanheiro() + " | ";
		}
		if (promocaoacomodacao.getValormaximodesconto() != null && promocaoacomodacao.getValormaximodesconto() > 0) {
			descricao = descricao + "Valor máximo de desconto: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValormaximodesconto()) + " | ";
		}
		if (promocaoacomodacao.getValorsemanaacima() != null && promocaoacomodacao.getValorsemanaacima() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemanaacima()) + " | ";
		}
		if (promocaoacomodacao.getValorsemanaigual() != null && promocaoacomodacao.getValorsemanaigual() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemanaigual()) + " | ";
		}
		if (promocaoacomodacao.getTipodesconto() != null && promocaoacomodacao.getValorsemana() != null
				&& promocaoacomodacao.getValorsemana() > 0) {
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + "% | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + " | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + " | ";
			}
		}
		if (promocaoacomodacao.getTipodesconto() != null && promocaoacomodacao.getValortotal() != null
				&& promocaoacomodacao.getValortotal() > 0) {
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + "% | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + " | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional da acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + " | ";
			}
		}
		return descricao;
	}

	public String cidades(Promocaoacomodacao promocaoacomodacao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaoacomodacao", promocaoacomodacao);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("consCidadesAcomodacao", options, null);
		return "";
	}
	
	public String editar(Promocaoacomodacao promocaoacomodacao) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaoAcomodacao", promocaoacomodacao);
		return "editarPromocaoAcomodacao";
	}


}
