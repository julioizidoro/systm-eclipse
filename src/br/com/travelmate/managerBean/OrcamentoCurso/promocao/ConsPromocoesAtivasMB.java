package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.bean.PromocoesBean;
import br.com.travelmate.dao.PaisDao;

import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.PromocaoBrindeCursoCidadeFacade;
import br.com.travelmate.facade.PromocaoCursoFornecedorCidadeFacade;
import br.com.travelmate.facade.PromocaoTaxaCidadeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade; 
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.model.Promocaobrindecurso;
import br.com.travelmate.model.Promocaobrindecursocidade;
import br.com.travelmate.model.Promocaocurso;
import br.com.travelmate.model.Promocaocursocidade;
import br.com.travelmate.model.Promocaotaxacidade;
import br.com.travelmate.model.Promocaotaxacurso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ConsPromocoesAtivasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PaisDao paisDao;
	private List<Pais> listaTabelaPais; 
	private List<Cidade> listaTabelaCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private List<PromocoesBean> listaPromocao;
	private List<Idioma> listaIdioma;
	private List<Fornecedor> listaFornecedor;
	private Pais pais;
	private Fornecedor fornecedor;
	private Idioma idioma;
	private Cidade cidade;
	private Fornecedorcidade fornecedorCidade;
	@Inject
	private AplicacaoMB aplicacaoMB;

	@PostConstruct
	public void init() {
		gerarListaPaisPromocao();
		gerarListaFornecedor();
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public List<Idioma> getListaIdioma() {
		return listaIdioma;
	}

	public void setListaIdioma(List<Idioma> listaIdioma) {
		this.listaIdioma = listaIdioma;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Pais> getListaTabelaPais() {
		return listaTabelaPais;
	}

	public void setListaTabelaPais(List<Pais> listaTabelaPais) {
		this.listaTabelaPais = listaTabelaPais;
	}
  
	public List<Cidade> getListaTabelaCidade() {
		return listaTabelaCidade;
	}

	public void setListaTabelaCidade(List<Cidade> listaTabelaCidade) {
		this.listaTabelaCidade = listaTabelaCidade;
	} 
  
	public List<PromocoesBean> getListaPromocao() {
		return listaPromocao;
	}

	public void setListaPromocao(List<PromocoesBean> listaPromocao) {
		this.listaPromocao = listaPromocao;
	}

	public void gerarListaFornecedor() {
		listaFornecedor = new ArrayList<Fornecedor>();
		String sql = "select p from Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'"
				+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
		List<Promocaocursocidade> lista = promocaoCursoFornecedorCidadeFacade.listar(sql);
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				listaFornecedor.add(lista.get(i).getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
						.getFornecedorcidade().getFornecedor());
			}
		}

		// promocao taxa
		sql = "select p from Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'" 
				+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaTaxa = promocaoTaxaCidadeFacade.listar(sql);
		if (listaTaxa != null) {
			for (int i = 0; i < listaTaxa.size(); i++) {
				boolean add = true;
				for (int j = 0; j < listaFornecedor.size(); j++) {
					if (listaFornecedor.get(j).getIdfornecedor() == listaTaxa.get(i)
							.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma().getFornecedorcidade()
							.getFornecedor().getIdfornecedor()) {
						add = false;
						j = 1000;
					}
				}
				if (add) {
					listaFornecedor.add(listaTaxa.get(i).getFornecedorcidadeidiomaproduto()
							.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor());
				}
			}
		}

		// promoção acomodação
		sql = "select p from Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'" 
				+ " Group by p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		List<Promocaoacomodacaocidade> listaAcomodacao = promocaoAcomodacaoCidadeFacade.listar(sql);
		if (listaAcomodacao != null) {
			for (int i = 0; i < listaAcomodacao.size(); i++) {
				boolean add = true;
				for (int j = 0; j < listaFornecedor.size(); j++) {
					if (listaFornecedor.get(j).getIdfornecedor() == listaAcomodacao.get(i)
							.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getIdfornecedor()) {
						add = false;
						j = 1000;
					}
				}
				if (add) {
					listaFornecedor.add(listaAcomodacao.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor());
				}
			}
		}

		// promoção brinde
		sql = "select p from Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'" 
				+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		List<Promocaobrindecursocidade> listaBrinde = promocaoBrindeCursoCidadeFacade.listar(sql);
		if (listaBrinde != null) {
			for (int i = 0; i < listaBrinde.size(); i++) {
				boolean add = true;
				for (int j = 0; j < listaFornecedor.size(); j++) {
					if (listaFornecedor.get(j).getIdfornecedor() == listaBrinde.get(i)
							.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma().getFornecedorcidade()
							.getFornecedor().getIdfornecedor()) {
						add = false;
						j = 1000;
					}
				}
				if (add) {
					listaFornecedor.add(listaBrinde.get(i).getFornecedorcidadeidiomaproduto()
							.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor());
				}
			}
		}
	}
 

	// selecionar Fornecedor (gerar lista pais)
	public void gerarListaPaisPromocao() {
		if (fornecedor != null) {
			listaTabelaPais = new ArrayList<Pais>();
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			listaTabelaCidade = new ArrayList<Cidade>();
			pais = null;

			// promoção curso
			String sql = "select p from Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor();
			sql = sql
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
			List<Promocaocursocidade> lista = promocaoCursoFornecedorCidadeFacade.listar(sql);
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					listaTabelaPais.add(lista.get(i).getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
							.getFornecedorcidade().getCidade().getPais());
				}
			}

			// promocao taxa
			sql = "select p from Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor();
			sql = sql
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
			List<Promocaotaxacidade> listaTaxa = promocaoTaxaCidadeFacade.listar(sql);
			if (listaTaxa != null) {
				for (int i = 0; i < listaTaxa.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaPais.size(); j++) {
						if (listaTabelaPais.get(j).getIdpais() == listaTaxa.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaPais.add(listaTaxa.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais());
					}
				}
			}

			// promoção acomodação
			sql = "select p from Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor();
			sql = sql + " Group by p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
			List<Promocaoacomodacaocidade> listaAcomodacao = promocaoAcomodacaoCidadeFacade.listar(sql);
			if (listaAcomodacao != null) {
				for (int i = 0; i < listaAcomodacao.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaPais.size(); j++) {
						if (listaTabelaPais.get(j).getIdpais() == listaAcomodacao.get(i).getFornecedorcidadeidioma()
								.getFornecedorcidade().getCidade().getPais().getIdpais()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaPais.add(listaAcomodacao.get(i).getFornecedorcidadeidioma().getFornecedorcidade()
								.getCidade().getPais());
					}
				}
			}

			// promocao brinde
			sql = "select p from Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor();
			sql = sql
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
			List<Promocaobrindecursocidade> listaBrinde = promocaoBrindeCursoCidadeFacade.listar(sql);
			if (listaBrinde != null) {
				for (int i = 0; i < listaBrinde.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaPais.size(); j++) {
						if (listaTabelaPais.get(j).getIdpais() == listaBrinde.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaPais.add(listaBrinde.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais());
					}
				}
			}
		} else {
			listaTabelaPais = new ArrayList<Pais>();
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			listaTabelaCidade = new ArrayList<Cidade>();
			pais = null;

			String sql = "select p from Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'";
			sql = sql
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
			List<Promocaocursocidade> lista = promocaoCursoFornecedorCidadeFacade.listar(sql);
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					listaTabelaPais.add(lista.get(i).getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
							.getFornecedorcidade().getCidade().getPais());
				}
			}

			// promocao taxa
			sql = "select p from Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
			List<Promocaotaxacidade> listaTaxa = promocaoTaxaCidadeFacade.listar(sql);
			if (listaTaxa != null) {
				for (int i = 0; i < listaTaxa.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaPais.size(); j++) {
						if (listaTabelaPais.get(j).getIdpais() == listaTaxa.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaPais.add(listaTaxa.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais());
					}
				}
			}

			// promoção acomodação
			sql = "select p from Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " Group by p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
			List<Promocaoacomodacaocidade> listaAcomodacao = promocaoAcomodacaoCidadeFacade.listar(sql);
			if (listaAcomodacao != null) {
				for (int i = 0; i < listaAcomodacao.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaPais.size(); j++) {
						if (listaTabelaPais.get(j).getIdpais() == listaAcomodacao.get(i).getFornecedorcidadeidioma()
								.getFornecedorcidade().getCidade().getPais().getIdpais()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaPais.add(listaAcomodacao.get(i).getFornecedorcidadeidioma().getFornecedorcidade()
								.getCidade().getPais());
					}
				}
			}

			// promoção brinde
			sql = "select p from Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
			List<Promocaobrindecursocidade> listaBrinde = promocaoBrindeCursoCidadeFacade.listar(sql);
			if (listaBrinde != null) {
				for (int i = 0; i < listaBrinde.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaPais.size(); j++) {
						if (listaTabelaPais.get(j).getIdpais() == listaBrinde.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaPais.add(listaBrinde.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais());
					}
				}
			}
		}
	}

	public void selecionarPaisComboBox() {
		if (pais != null) { 
			gerarListaEscolas();
		} else {
			
			listaTabelaPais = paisDao.listar();
		}
	}

	public void gerarListaEscolas() {
		listaTabelaCidade = new ArrayList<Cidade>();
		listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
		listaPromocao = new ArrayList<PromocoesBean>(); 

		String sql = "select p from Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'"
				+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
				+ pais.getIdpais();
				if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
					sql=sql+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
							+ fornecedor.getIdfornecedor();
				}
				sql=sql+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
		List<Promocaocursocidade> lista = promocaoCursoFornecedorCidadeFacade.listar(sql);
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				listaFornecedorCidade.add(lista.get(i).getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
						.getFornecedorcidade());
			}  
		}

		// promocao taxa
		sql = "select p from Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'"
				+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
				+ pais.getIdpais();
				if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
					sql=sql+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
							+ fornecedor.getIdfornecedor();
				}
				sql=sql + " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaTaxa = promocaoTaxaCidadeFacade.listar(sql);
		if (listaTaxa != null) {
			for (int i = 0; i < listaTaxa.size(); i++) {
				boolean add = true;
				for (int j = 0; j < listaFornecedorCidade.size(); j++) {
					if (listaFornecedorCidade.get(j).getIdfornecedorcidade() == listaTaxa.get(i)
							.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma().getFornecedorcidade()
							.getIdfornecedorcidade()) {
						add = false;
						j = 1000;
					}
				}
				if (add) {
					listaFornecedorCidade.add(listaTaxa.get(i).getFornecedorcidadeidiomaproduto()
							.getFornecedorcidadeidioma().getFornecedorcidade());
				}
			}
		}

		// promoção acomodação
		sql = "select p from Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'"
				+ " and p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais();
				if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
					sql=sql+ " and p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
							+ fornecedor.getIdfornecedor();
				}
				sql=sql + " Group by p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		List<Promocaoacomodacaocidade> listaAcomodacao = promocaoAcomodacaoCidadeFacade.listar(sql);
		if (listaAcomodacao != null) {
			for (int i = 0; i < listaAcomodacao.size(); i++) {
				boolean add = true;
				for (int j = 0; j < listaFornecedorCidade.size(); j++) {
					if (listaFornecedorCidade.get(j).getIdfornecedorcidade() == listaAcomodacao.get(i)
							.getFornecedorcidadeidioma().getFornecedorcidade().getIdfornecedorcidade()) {
						add = false;
						j = 1000;
					}
				}
				if (add) {
					listaFornecedorCidade.add(listaAcomodacao.get(i).getFornecedorcidadeidioma().getFornecedorcidade());
				}
			}
		}

		// promoção brinde
		sql = "select p from Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'"
				+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
				+ pais.getIdpais();
				if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
					sql=sql+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
							+ fornecedor.getIdfornecedor();
				}
				sql=sql + " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor"
				+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		List<Promocaobrindecursocidade> listaBrinde = promocaoBrindeCursoCidadeFacade.listar(sql);
		if (listaBrinde != null) {
			for (int i = 0; i < listaBrinde.size(); i++) {
				boolean add = true;
				for (int j = 0; j < listaFornecedorCidade.size(); j++) {
					if (listaFornecedorCidade.get(j).getIdfornecedorcidade() == listaBrinde.get(i)
							.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma().getFornecedorcidade()
							.getIdfornecedorcidade()) {
						add = false;
						j = 1000;
					}
				}
				if (add) {
					listaFornecedorCidade.add(listaBrinde.get(i).getFornecedorcidadeidiomaproduto()
							.getFornecedorcidadeidioma().getFornecedorcidade());
				}
			}
		}
	}
	 
	
	public void gerarListaCidades() {
		if(fornecedorCidade!=null && fornecedorCidade.getIdfornecedorcidade()!=null){
			listaTabelaCidade = new ArrayList<Cidade>();  
			listaPromocao = new ArrayList<PromocoesBean>(); 
	
			String sql = "select p from Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ pais.getIdpais()
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedorCidade.getFornecedor().getIdfornecedor()
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
			PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
			List<Promocaocursocidade> lista = promocaoCursoFornecedorCidadeFacade.listar(sql);
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					listaTabelaCidade.add(lista.get(i).getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
							.getFornecedorcidade().getCidade());
				}  
			}
	
			// promocao taxa
			sql = "select p from Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ pais.getIdpais()
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedorCidade.getFornecedor().getIdfornecedor()
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
			PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
			List<Promocaotaxacidade> listaTaxa = promocaoTaxaCidadeFacade.listar(sql);
			if (listaTaxa != null) {
				for (int i = 0; i < listaTaxa.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaCidade.size(); j++) {
						if (listaTabelaCidade.get(j).getIdcidade() == listaTaxa.get(i)
								.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma().getFornecedorcidade()
								.getCidade().getIdcidade()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaCidade.add(listaTaxa.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade());
					}
				}
			}
	
			// promoção acomodação
			sql = "select p from Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais()
					+ " and p.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedorCidade.getFornecedor().getIdfornecedor()
					+ " Group by p.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade"
					+ " order by p.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
			PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
			List<Promocaoacomodacaocidade> listaAcomodacao = promocaoAcomodacaoCidadeFacade.listar(sql);
			if (listaAcomodacao != null) {
				for (int i = 0; i < listaAcomodacao.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaCidade.size(); j++) {
						if (listaTabelaCidade.get(j).getIdcidade() == listaAcomodacao.get(i)
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getIdcidade()) {
							add = false;
							j = 1000;
						}
					}
					if (add) {
						listaTabelaCidade.add(listaAcomodacao.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade());
					}
				}
			}
	
			// promoção brinde
			sql = "select p from Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'"
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="
					+ pais.getIdpais()
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedorCidade.getFornecedor().getIdfornecedor()
					+ " Group by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade"
					+ " order by p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
			PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
			List<Promocaobrindecursocidade> listaBrinde = promocaoBrindeCursoCidadeFacade.listar(sql);
			if (listaBrinde != null) {
				for (int i = 0; i < listaBrinde.size(); i++) {
					boolean add = true;
					for (int j = 0; j < listaTabelaCidade.size(); j++) {
						if (listaTabelaCidade.get(j).getIdcidade() == listaBrinde.get(i)
								.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma().getFornecedorcidade()
								.getCidade().getIdcidade()) {
							add = false;
							j = 1000;
						}
					}   
					if (add) {
						listaTabelaCidade.add(listaBrinde.get(i).getFornecedorcidadeidiomaproduto()
								.getFornecedorcidadeidioma().getFornecedorcidade().getCidade());
					}
				}
			}
		}
	}
	
	
	public void gerarListaPromocoes() {
		if(cidade!=null && cidade.getIdcidade()!=null){
			listaPromocao = new ArrayList<PromocoesBean>();  
	
			String sql = "select p from Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'" 
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade();
			PromocaoCursoFornecedorCidadeFacade promocaoCursoFornecedorCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
			List<Promocaocursocidade> lista = promocaoCursoFornecedorCidadeFacade.listar(sql);
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					PromocoesBean promocao = new PromocoesBean();
					promocao.setPromocao(descricao(lista.get(i), lista.get(i).getPromoacaocurso()));
					listaPromocao.add(promocao);
				}  
			}
	
			// promocao taxa
			sql = "select p from Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'" 
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade();
			PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
			List<Promocaotaxacidade> listaTaxa = promocaoTaxaCidadeFacade.listar(sql);
			if (listaTaxa != null) {
				for (int i = 0; i < listaTaxa.size(); i++) {
					PromocoesBean promocao = new PromocoesBean();
					promocao.setPromocao(descricaoTaxa(listaTaxa.get(i),listaTaxa.get(i).getPromocaotaxacurso()));
					listaPromocao.add(promocao);
				}  
			}
	
			// promoção acomodação
			sql = "select p from Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'" 
					+ " and p.fornecedorcidadeidioma.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade();
			PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
			List<Promocaoacomodacaocidade> listaAcomodacao = promocaoAcomodacaoCidadeFacade.listar(sql);
			if (listaAcomodacao != null) {
				for (int i = 0; i < listaAcomodacao.size(); i++) {
					PromocoesBean promocao = new PromocoesBean();
					promocao.setPromocao(descricaoAcomodacao(listaAcomodacao.get(i).getPromocaoacomodacao()));
					listaPromocao.add(promocao);
				}
			}
	
			// promoção brinde
			sql = "select p from Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
					+ Formatacao.ConvercaoDataSql(new Date()) + "'" 
					+ " and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade();
			PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
			List<Promocaobrindecursocidade> listaBrinde = promocaoBrindeCursoCidadeFacade.listar(sql);
			if (listaBrinde != null) {
				for (int i = 0; i < listaBrinde.size(); i++) {
					PromocoesBean promocao = new PromocoesBean();
					promocao.setPromocao(descricaoBrinde(listaBrinde.get(i), listaBrinde.get(i).getPromocaobrindecurso()));
					listaPromocao.add(promocao);
				}
			}
		}  
	}
	
	public String descricao(Promocaocursocidade promocaocursocidade, Promocaocurso promocaocurso) {
		String descricao ="PROMOÇÃO DE CURSO - Curso: "+promocaocursocidade.getFornecedorcidadeidiomaproduto().getProdutosorcamento().getDescricao()+" | ";
		if ((promocaocurso.getDatavalidadefinal() != null) && (promocaocurso.getDatavalidadeinicial()!=null)) {
			descricao = descricao + "Data de validade: " + Formatacao.ConvercaoDataPadrao(promocaocurso.getDatavalidadefinal()) + " | ";
		}  
		if (promocaocurso.getAcomodacaoescola()) {
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
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + "%";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana());
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana());
			}
		}
		if (promocaocurso.getTipodesconto() != null && promocaocurso.getValortotal() != null
				&& promocaocurso.getValortotal() > 0) {
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre o curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal()) + "%";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre o curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal());
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional do curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal());
			}
		} 
		return descricao;
	}
  
	
	public String descricaoTaxa(Promocaotaxacidade promocaotaxacidade,Promocaotaxacurso promocaotaxacurso) {
		String descricao = "PROMOÇÃO DE TAXAS - Curso: "+promocaotaxacidade.getFornecedorcidadeidiomaproduto().getProdutosorcamento().getDescricao()+" | ";
		if ((promocaotaxacurso.getDatavalidadefinal() != null)
				&& (promocaotaxacurso.getDatavalidadeinicial() != null)) {
			descricao = descricao + "Date de validade: " 
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaotaxacurso.getAcomodacaoselecionada()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaotaxacurso.getCargahoraria() != null && promocaotaxacurso.getCargahoraria() > 0
				&& promocaotaxacurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaotaxacurso.getCargahoraria() + " "
					+ promocaotaxacurso.getTipocargahoraria() + " | ";
		}
		if (promocaotaxacurso.getDataacomodacaoinicial() != null
				&& promocaotaxacurso.getDatafinalacomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatafinalacomodacao()) + " | ";
		}
		if (promocaotaxacurso.getDatainiciocursoinicial() != null
				&& promocaotaxacurso.getDatainiciocursofinal() != null) {
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
				descricao = descricao + "Percentual de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + "%";
			}
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto());
			}
		}  
		return descricao;
	}
	
	public String descricaoAcomodacao(Promocaoacomodacao promocaoacomodacao) { 
		String descricao="PROMOÇÃO DE ACOMODAÇÃO - ";
		if (promocaoacomodacao.getTipoacomodacao() != null && promocaoacomodacao.getTipoacomodacao().length() > 2) {
			descricao = descricao + "Tipo de Acomodação: " + promocaoacomodacao.getTipoacomodacao() + " | ";
		}
		if (promocaoacomodacao.getTipoquarto() != null && promocaoacomodacao.getTipoquarto().length() > 2) {
			descricao = descricao + "Tipo de Quarto: " + promocaoacomodacao.getTipoquarto() + " | ";
		}
		if (promocaoacomodacao.getTiporefeicao() != null && promocaoacomodacao.getTiporefeicao().length() > 2) {
			descricao = descricao + "Tipo de Refeição: " + promocaoacomodacao.getTiporefeicao() + " | ";
		}
		if (promocaoacomodacao.getDatavalidadeinicial() != null && promocaoacomodacao.getDatavalidadefinal() != null) {
			descricao = descricao+"Data de validade: " +Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatavalidadefinal())
					+ " | ";  
		}
		if (promocaoacomodacao.getAcomodacaoescola()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
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
		if (promocaoacomodacao.getNumerosemanafinal() != null && promocaoacomodacao.getNumerosemanainicio() != null && promocaoacomodacao.getNumerosemanainicio()>0) {
			descricao = descricao + "Nº de semanas: " + promocaoacomodacao.getNumerosemanainicio() + " até "
					+ promocaoacomodacao.getNumerosemanafinal() + " | ";
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
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + "%";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana());
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana());
			}
		}
		if (promocaoacomodacao.getTipodesconto() != null && promocaoacomodacao.getValortotal() != null
				&& promocaoacomodacao.getValortotal() > 0) {
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + "%";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal());
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional da acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal());
			}
		} 
		return descricao;
	}
	
	
	public String descricaoBrinde(Promocaobrindecursocidade promocaobrindecursocidade,Promocaobrindecurso promocaobrindecurso) {
		String descricao = "BRINDES - Curso: "+promocaobrindecursocidade.getFornecedorcidadeidiomaproduto().getProdutosorcamento().getDescricao()+" - ";
		if (promocaobrindecurso.getDatavalidadeinicial() != null && promocaobrindecurso.getDatavalidadeinicial() != null) {
			descricao = descricao + "Date de validade: " 
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
}
