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
import br.com.travelmate.facade.PromocaoBrindeCursoCidadeFacade;
import br.com.travelmate.facade.PromocaoBrindeCursoFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Promocaobrindecurso;
import br.com.travelmate.model.Promocaobrindecursocidade;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class PromocoesBrindesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedor> listaFornecedor;
	private List<Pais> listaPais;
	private Fornecedor fornecedor;
	private List<Pais> listaPaisSelecionado;
	private boolean habilitarBtnNovo;
	private List<Promocaobrindecurso> listaPromocoes;

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

	public List<Promocaobrindecurso> getListaPromocoes() {
		return listaPromocoes;
	}

	public void setListaPromocoes(List<Promocaobrindecurso> listaPromocoes) {
		this.listaPromocoes = listaPromocoes;
	}

	public String cadPromocaoTaxa() {
		gerarListaCidade();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedor", fornecedor);
		return "cadPromocaoBrindes";
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
		listaPromocoes = new ArrayList<Promocaobrindecurso>();
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		String sql = "select p from Promocaobrindecursocidade p where p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
				+ fornecedor.getIdfornecedor()
				+ " and (p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
				+ listaPaisSelecionado.get(0).getIdpais();
		for (int i = 1; i < listaPaisSelecionado.size(); i++) {
			sql = sql
					+ " or p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ listaPaisSelecionado.get(i).getIdpais();
		}
		sql = sql
				+ ") group by p.promocaobrindecurso.idpromocaobrindecurso order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais";
		List<Promocaobrindecursocidade> listacidade = promocaoBrindeCursoCidadeFacade.listar(sql);
		if (listacidade != null) {
			listaPromocoes = new ArrayList<Promocaobrindecurso>();
			for (int i = 0; i < listacidade.size(); i++) {
				listaPromocoes.add(listacidade.get(i).getPromocaobrindecurso());
			}
		}
	}

	public void excluir(Promocaobrindecurso promocaobrindecurso) {
		// cidade
		String sqlCidade = "select p from Promocaobrindecursocidade p where p.promocaobrindecurso.idpromocaobrindecurso="
				+ promocaobrindecurso.getIdpromocaobrindecurso();
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		List<Promocaobrindecursocidade> listacidade = new ArrayList<Promocaobrindecursocidade>();
		listacidade = promocaoBrindeCursoCidadeFacade.listar(sqlCidade);
		if (listacidade != null) {
			for (int i = 0; i < listacidade.size(); i++) {
				promocaoBrindeCursoCidadeFacade.excluir(listacidade.get(i));
			}
		}

		// brinde
		PromocaoBrindeCursoFacade promocaoBrindeCursoFacade = new PromocaoBrindeCursoFacade();
		promocaoBrindeCursoFacade.excluir(promocaobrindecurso);
		gerarListaPromocoes();
	}

	public String descricao(Promocaobrindecurso promocaobrindecurso) {
		String descricao = "";
		if (promocaobrindecurso.getDatavalidadeinicial() != null && promocaobrindecurso.getDatavalidadeinicial() != null) {
			descricao = descricao + "Período de validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadeinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaobrindecurso.getNumerosemanainicial() != null && promocaobrindecurso.getNumerosemanafinal() != null
				&& promocaobrindecurso.getNumerosemanainicial()>0 && promocaobrindecurso.getNumerosemanafinal()>0) {
			descricao = descricao + "Nº de semanas: "
					+ promocaobrindecurso.getNumerosemanainicial() + " até "
					+ promocaobrindecurso.getNumerosemanafinal() + " | ";
		}
		if (promocaobrindecurso.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatamatricula()) + " | ";
		}
		if ((promocaobrindecurso.getDataacomodacaoinicial() != null) && (promocaobrindecurso.getDataacomodacaofinal() != null)) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaofinal()) + " | ";
		}
		if ((promocaobrindecurso.getDatainicio() != null) && (promocaobrindecurso.getDatafinal() != null)) {
			descricao = descricao + "Data início do curso entre: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatainicio()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatafinal()) + " | ";
		}
		if (promocaobrindecurso.getValorporsemana() != null && promocaobrindecurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaobrindecurso.getValorporsemana()) + " | ";
		}
		if (promocaobrindecurso.getTurno() != null && promocaobrindecurso.getTurno().length()>2) {
			descricao = descricao + "Turno: " + promocaobrindecurso.getTurno() + " | ";
		}
		if (promocaobrindecurso.getNumerosemanacurso() != null && promocaobrindecurso.getNumerosemanacurso() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanacurso() + " semana(s) curso | ";
		}
		if (promocaobrindecurso.getNumerosemanaacomodacao() != null && promocaobrindecurso.getNumerosemanaacomodacao() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanaacomodacao() + " semana(s) acomodação  | ";
		}
		if (promocaobrindecurso.getGanhasemana()!=null && promocaobrindecurso.getGanhasemana()>0) {
			descricao = descricao + "Ganha: " + promocaobrindecurso.getGanhasemana() +" semana(s) de curso " ;
		}
		if (promocaobrindecurso.getGanhadescontosemana()!=null && promocaobrindecurso.getGanhadescontosemana()>0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemana() +" semana(s) de curso " ;
		}
		if (promocaobrindecurso.getGanhadescontosemanaacomodacao()!=null && promocaobrindecurso.getGanhadescontosemanaacomodacao()>0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemanaacomodacao() +" semana(s) de acomodação " ;
		}
		if (promocaobrindecurso.getGanhadescricao()!=null && promocaobrindecurso.getGanhadescricao().length()>2) {
			descricao = descricao + "Brinde: " + promocaobrindecurso.getGanhadescricao();
		}
		return descricao;
	}

	public String cidades(Promocaobrindecurso promocaobrindecurso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaobrindecurso", promocaobrindecurso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("consCidadesBrindes", options, null);
		return "";
	}
	
	public String editar(Promocaobrindecurso promocaobrindecurso) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaobrindecurso", promocaobrindecurso);
		return "editarPromocaoBrindes";
	}

}
