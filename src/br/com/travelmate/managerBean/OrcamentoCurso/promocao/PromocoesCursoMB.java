package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.travelmate.facade.PromocaoCursoFacade;
import br.com.travelmate.facade.PromocaoCursoFornecedorCidadeFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Promocaocurso;
import br.com.travelmate.model.Promocaocursocidade;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class PromocoesCursoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedor> listaFornecedor;
	private List<Pais> listaPais;
	private Fornecedor fornecedor;
	private List<Pais> listaPaisSelecionado;
	private boolean habilitarBtnNovo;
	private List<Promocaocurso> listaPromocoes;
	private boolean vencidas = false;

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

	public List<Promocaocurso> getListaPromocoes() {
		return listaPromocoes;
	}

	public void setListaPromocoes(List<Promocaocurso> listaPromocoes) {
		this.listaPromocoes = listaPromocoes;
	}

	public boolean isVencidas() {
		return vencidas;
	}

	public void setVencidas(boolean vencidas) {
		this.vencidas = vencidas;
	}
	
	

	public String cadPromocaoCurso() {
		gerarListaCidade();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedor", fornecedor);
		return "cadPromocaoCurso";
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
		listaPromocoes = new  ArrayList<Promocaocurso>();
		PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
		String sql = "select p from Promocaocursocidade p where p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
				+ fornecedor.getIdfornecedor() + " and (p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
				+ listaPaisSelecionado.get(0).getIdpais();
		for (int i = 1; i < listaPaisSelecionado.size(); i++) {
			sql = sql + " or p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ listaPaisSelecionado.get(i).getIdpais();
		}
		sql = sql
				+ ") group by p.promoacaocurso.idpromoacaocurso order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais";
		List<Promocaocursocidade> listacidade = promocaoCursoFornecedorCidadeFacade.listar(sql);
		if (listacidade != null) {
			listaPromocoes = new ArrayList<Promocaocurso>();
			for (int i = 0; i < listacidade.size(); i++) {
				listaPromocoes.add(listacidade.get(i).getPromoacaocurso());
			}
		}
	}

	public void excluir(Promocaocurso promocaocurso) {
		// cidade
		String sqlCidade = "select p from Promocaocursocidade p where p.promoacaocurso.idpromoacaocurso="
				+ promocaocurso.getIdpromoacaocurso();
		PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
		List<Promocaocursocidade> listacidade = new ArrayList<Promocaocursocidade>();
		listacidade = promocaoCursoFornecedorCidadeFacade.listar(sqlCidade);
		if (listacidade != null) {
			for (int i = 0; i < listacidade.size(); i++) {
				promocaoCursoFornecedorCidadeFacade.excluir(listacidade.get(i));
			}
		}

		// curso
		PromocaoCursoFacade cursoFacade = new PromocaoCursoFacade();
		cursoFacade.excluir(promocaocurso);
		gerarListaPromocoes();
	}

	public String descricao(Promocaocurso promocaocurso) {
		String descricao = "";
		if (promocaocurso.getAcomodacaoescola()!=null && promocaocurso.getAcomodacaoescola()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaocurso.getCargahoraria() != null && promocaocurso.getCargahoraria() > 0
				&& promocaocurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaocurso.getCargahoraria() + " "
					+ promocaocurso.getTipocargahoraria() + " | ";
		}
		if (promocaocurso.getDatainicioacomodacao() != null && promocaocurso.getDataterminioacodomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatainicioacomodacao()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDataterminioacodomodacao()) + " | ";
		}
		if (promocaocurso.getDatainicioiniciocurso() != null && promocaocurso.getDatainicioterminiocurso() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatainicioiniciocurso()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatainicioterminiocurso()) + " | ";
		}
		if (promocaocurso.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatamatricula()) + " | ";
		}
		if (promocaocurso.getDataterminocurso() != null) {
			descricao = descricao + "Data termino de curso até: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDataterminocurso()) + " | ";
		}
		if ((promocaocurso.getDatavalidadefinal() != null) && (promocaocurso.getDatavalidadeinicial()!=null)) {
			descricao = descricao + "Data validade: " + Formatacao.ConvercaoDataPadrao(promocaocurso.getDatavalidadeinicial()) +
					" a " +  Formatacao.ConvercaoDataPadrao(promocaocurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaocurso.getNumerosemanafinal() != null && promocaocurso.getNumerosemanainicio() != null && promocaocurso.getNumerosemanainicio()>0) {
			descricao = descricao + "Nº de semanas: " + promocaocurso.getNumerosemanainicio() + " até "
					+ promocaocurso.getNumerosemanafinal() + " | ";
		}
		if (promocaocurso.getTurno() != null && promocaocurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaocurso.getTurno() + " | ";
		}
		if (promocaocurso.getValormaximodesconto() != null && promocaocurso.getValormaximodesconto() > 0) {
			descricao = descricao + "Valor máximo de desconto: "
					+ Formatacao.formatarFloatString(promocaocurso.getValormaximodesconto()) + " | ";
		}
		if (promocaocurso.getValorsemanaacima() != null && promocaocurso.getValorsemanaacima() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaocurso.getValorsemanaacima()) + " | ";
		}
		if (promocaocurso.getValorsemanaigual() != null && promocaocurso.getValorsemanaigual() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaocurso.getValorsemanaigual()) + " | ";
		}
		if (promocaocurso.getTipodesconto() != null && promocaocurso.getValorsemana() != null
				&& promocaocurso.getValorsemana() > 0) {
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + "% | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + " | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + " | ";
			}
		}
		if (promocaocurso.getTipodesconto() != null && promocaocurso.getValortotal() != null
				&& promocaocurso.getValortotal() > 0) {
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre o curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal()) + "% | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre o curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal()) + " | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional do curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal()) + " | ";
			}
		}
		return descricao;
	}

	public String cidades(Promocaocurso promocaocurso) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaocurso", promocaocurso);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("consCidades", options, null);
		return "";
	}

	public String editar(Promocaocurso promocaocurso) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("promocaocurso", promocaocurso);
		return "editarPromocaoCurso";
	}
	
	
	
	public void filtrarPromocoesVencidas(){
		List<Promocaocurso> listaPromocaocurso = new ArrayList<>();
		if (vencidas) {
			for (int i = 0; i < listaPromocoes.size(); i++) {
				if (listaPromocoes.get(i).getDatavalidadefinal().before(new Date())) {
					listaPromocaocurso.add(listaPromocoes.get(i));
				}
			}
			for (int i = 0; i < listaPromocaocurso.size(); i++) {
				listaPromocoes.remove(listaPromocaocurso.get(i));
			}
		}else{
			gerarListaPais();
		}
	}   

}
  