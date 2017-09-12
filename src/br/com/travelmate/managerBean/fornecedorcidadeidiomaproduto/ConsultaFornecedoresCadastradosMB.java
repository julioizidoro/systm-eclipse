package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;
import java.sql.SQLException;
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

import br.com.travelmate.bean.EstrelasBean;
import br.com.travelmate.bean.ListaTMStarBean;
import br.com.travelmate.bean.TMStarBean;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.DepartamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.IdiomaFacade; 
import br.com.travelmate.facade.PaisProdutoFacade; 
import br.com.travelmate.facade.ProdutoOrcamentoIndiceFacade;
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Departamentoproduto;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Idioma; 
import br.com.travelmate.model.Paisproduto; 
import br.com.travelmate.model.Produtosorcamentoindice; 
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ConsultaFornecedoresCadastradosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Paisproduto> listaPais;
	private List<Paisproduto> listaTabelaPais;
	private List<Cidadepaisproduto> listaCidade;
	private List<Cidadepaisproduto> listaTabelaCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private List<Idioma> listaIdioma;
	private List<Produtosorcamentoindice> listaProdutos;
	private List<Fornecedor> listaFornecedor;
	private Paisproduto pais;
	private Fornecedor fornecedor;
	private Produtosorcamentoindice produtosorcamentoindice;
	private Idioma idioma;
	private Cidadepaisproduto cidade;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidadeidiomaproduto> listaTabelaProduto;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private EstrelasBean estrela;
	private List<EstrelasBean> listaEstrela;
	private Ftpdados ftpdados;
	private Departamentoproduto produtos;
	private List<Departamentoproduto> listaProgramas;
	private boolean habilitarcampos;
	private List<Departamento> listaDepartamento;
	private Departamento departamento;

	@PostConstruct
	public void init() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade.listar("SELECT d FROM Departamento d WHERE d.lista=TRUE ORDER BY d.nome");
		gerarListaProdutosOrcamento();
		gerarListaFornecedor();
		gerarListaIdioma();
		gerarEstrelaBean();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaTabelaPais = (List<Paisproduto>) session.getAttribute("listaTabelaPais");
		pais = (Paisproduto) session.getAttribute("pais");
		listaTabelaCidade = (List<Cidadepaisproduto>) session.getAttribute("listaTabelaCidade");
		cidade = (Cidadepaisproduto) session.getAttribute("cidade");
		listaFornecedorCidade = (List<Fornecedorcidade>) session.getAttribute("listaFornecedorCidade");
		session.removeAttribute("listaTabelaPais");
		session.removeAttribute("pais");
		session.removeAttribute("listaTabelaCidade");
		session.removeAttribute("cidade");
		session.removeAttribute("listaFornecedorCidade");
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		ftpdados = new Ftpdados();
		habilitarCampos();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Paisproduto> getListaTabelaPais() {
		return listaTabelaPais;
	}

	public void setListaTabelaPais(List<Paisproduto> listaTabelaPais) {
		this.listaTabelaPais = listaTabelaPais;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Cidadepaisproduto> getListaTabelaCidade() {
		return listaTabelaCidade;
	}

	public void setListaTabelaCidade(List<Cidadepaisproduto> listaTabelaCidade) {
		this.listaTabelaCidade = listaTabelaCidade;
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

	

	public List<Produtosorcamentoindice> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtosorcamentoindice> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Paisproduto getPais() {
		return pais;
	}

	public void setPais(Paisproduto pais) {
		this.pais = pais;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Produtosorcamentoindice getProdutosorcamentoindice() {
		return produtosorcamentoindice;
	}

	public void setProdutosorcamentoindice(Produtosorcamentoindice produtosorcamentoindice) {
		this.produtosorcamentoindice = produtosorcamentoindice;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Cidadepaisproduto getCidade() {
		return cidade;
	}

	public void setCidade(Cidadepaisproduto cidade) {
		this.cidade = cidade;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public List<Fornecedorcidadeidiomaproduto> getListaTabelaProduto() {
		return listaTabelaProduto;
	}

	public void setListaTabelaProduto(List<Fornecedorcidadeidiomaproduto> listaTabelaProduto) {
		this.listaTabelaProduto = listaTabelaProduto;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public EstrelasBean getEstrela() {
		return estrela;
	}

	public void setEstrela(EstrelasBean estrela) {
		this.estrela = estrela;
	}

	public List<EstrelasBean> getListaEstrela() {
		return listaEstrela;
	}

	public void setListaEstrela(List<EstrelasBean> listaEstrela) {
		this.listaEstrela = listaEstrela;
	}

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}
 

	public Departamentoproduto getProdutos() {
		return produtos;
	}

	public void setProdutos(Departamentoproduto produtos) {
		this.produtos = produtos;
	}

	public List<Departamentoproduto> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<Departamentoproduto> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public boolean isHabilitarcampos() {
		return habilitarcampos;
	}

	public void setHabilitarcampos(boolean habilitarcampos) {
		this.habilitarcampos = habilitarcampos;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String arquivos(Fornecedorcidade fornecedorcidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorCidade", fornecedorcidade);
		session.setAttribute("listaTabelaPais", listaTabelaPais);
		session.setAttribute("pais", pais);
		session.setAttribute("listaTabelaCidade", listaTabelaCidade);
		session.setAttribute("cidade", cidade);
		session.setAttribute("listaFornecedorCidade", listaFornecedorCidade);
		return "consArquivosFornecedor";
	}

	public String documentosFornecedor(Fornecedorcidade fornecedorcidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorCidade", fornecedorcidade);
		session.setAttribute("listaTabelaPais", listaTabelaPais);
		session.setAttribute("pais", pais);
		session.setAttribute("listaTabelaCidade", listaTabelaCidade);
		session.setAttribute("cidade", cidade);
		session.setAttribute("listaFornecedorCidade", listaFornecedorCidade);
		return "consDocumentosFornecedor";
	}

	public void gerarListaProdutosOrcamento() {
		ProdutoOrcamentoIndiceFacade produtoOrcamentoIndiceFacade = new ProdutoOrcamentoIndiceFacade();
		listaProdutos = produtoOrcamentoIndiceFacade.listar(
				"select p from Produtosorcamentoindice p order by p.descricao");
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Produtosorcamentoindice>();
		}
	}

	public void selecionarFornecedorCidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorCidade = fornecedorcidade;
	}

	public void selecionarPais(Paisproduto pais) {
		this.pais = pais;
	}
	
	public void selecionarCidade(Cidadepaisproduto cidade) {
		this.cidade = cidade;
	}

	public void gerarListaFornecedor() {
		FornecedorFacade forncedorFacade = new FornecedorFacade();
		listaFornecedor = forncedorFacade
				.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}

	public void gerarListaIdioma() {
		IdiomaFacade idiomaFacade = new IdiomaFacade();
		String sql = "Select i from Idioma  i  order by i.descricao";
		listaIdioma = idiomaFacade.listar(sql);
		if (listaIdioma == null) {
			listaIdioma = new ArrayList<Idioma>();
		}
	}

	public void selecionarCidadeComboBox() {
		if (cidade != null) {
			gerarListaFornecedorCidade();
		}
	}

	public void gerarListaFornecedorCidade() {
		String sql = "";
		if (cidade != null) {
			if (produtosorcamentoindice != null) {
				if (fornecedor != null) {
					sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
							+ fornecedor.getIdfornecedor() + " and f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="
							+ produtosorcamentoindice.getIdprodutosorcamentoindice()
							+ " and f.fornecedorcidadeidioma.fornecedorcidade.ativo=1";
					if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
						if (estrela.isToptmstar()) {
							sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.toptmstar=true";
						}
						sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.numestrelas=" + estrela.getNumero();
					}
					sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade=" + cidade.getCidade().getIdcidade()
							+ " Group by f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor order by f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
				} else {
					sql = "select f from Fornecedorcidadeidiomaproduto f where f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="  
							+ produtosorcamentoindice.getIdprodutosorcamentoindice();
					if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
						if (estrela.isToptmstar()) {
							sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.toptmstar=true";
						}
						sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.numestrelas=" + estrela.getNumero();
					}
					sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade=" + cidade.getCidade().getIdcidade()
							+ " Group by f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor order by f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
				}
				FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
				List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeFacade.listar(sql);
				listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
				if (lista != null) {
					for (int i = 0; i < lista.size(); i++) {
						listaFornecedorCidade.add(lista.get(i).getFornecedorcidadeidioma().getFornecedorcidade());
					}
				}
			} else if (fornecedor != null) {
				sql = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor()
						+ " and f.cidade.idcidade=" + cidade.getCidade().getIdcidade() + " and f.ativo=1"
						+ " and f.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();
				if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
					if (estrela.isToptmstar()) {
						sql = sql + " and f.toptmstar=true";
					}
					sql = sql + " and f.numestrelas=" + estrela.getNumero();
				}
				sql = sql + " order by f.fornecedor.nome";
				FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
				listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
				listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			} else {
				sql = "SELECT f From Fornecedorcidade f where f.cidade.idcidade="
						+ cidade.getCidade().getIdcidade() + " and f.ativo=1"
						+ " and f.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();
				if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
					if (estrela.isToptmstar()) {
						sql = sql + " and f.toptmstar=true";
					}
					sql = sql + " and f.numestrelas=" + estrela.getNumero();
				}
				sql = sql + " order by f.fornecedor.nome";
				FornecedorCidadeFacade fornecedorCidadeFacede = new FornecedorCidadeFacade();
				listaFornecedorCidade = fornecedorCidadeFacede.listar(sql);
				listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			}
		} else {
			gerarListaPaisFornecedor();
		}
	}

	public void gerarListaFornecedorCidadeIdiomaProduto() {
		String sql = "";
		if (produtosorcamentoindice != null) {
			sql = "select f from Fornecedorcidadeidiomaproduto f where f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="
					+ produtosorcamentoindice.getIdprodutosorcamentoindice()
					+ " and f.fornecedorcidadeidioma.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade()
					+ " and f.produtosorcamento.tipoproduto='C' order by f.produtosorcamento.descricao";
		} else {
			sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.fornecedorcidade.idfornecedorcidade="
					+ fornecedorCidade.getIdfornecedorcidade()
					+ " and f.produtosorcamento.tipoproduto='C' order by f.produtosorcamento.descricao";
		}
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
		listaTabelaProduto = fornecedorCidadeFacade.listar(sql);
		int idproduto = aplicacaoMB.getParametrosprodutos().getCursos();
		if ((listaTabelaProduto == null || listaTabelaProduto.size() == 0) &&
				produtos.getProdutos().getIdprodutos()==idproduto) {
			Mensagem.lancarMensagemInfo("Atenção", "Este parceiro não possui produtos cadastrados.");
		}
	}

	// selecionar Fornecedor (gerar lista pais)
	public void gerarListaPaisFornecedor() {
		if (produtosorcamentoindice != null) {
			gerarListaPaisProduto();
		} else if (fornecedor != null) {
			String sql = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor() + " and f.ativo=1"
					+ " and f.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();
			if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
				if (estrela.isToptmstar()) {
					sql = sql + " and f.toptmstar=true";
				}
				sql = sql + " and f.numestrelas=" + estrela.getNumero();
			}
			sql = sql + " Group by f.cidade.pais.idpais order by f.cidade.pais.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
			listaTabelaPais = new ArrayList<Paisproduto>();
			listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
			pais = null;
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					List<Cidadepaisproduto> listacidade = lista.get(i).getCidade().getCidadepaisList();
					for (int j = 0; j < listacidade.size(); j++) {
						listaTabelaPais.add(listacidade.get(j).getPaisproduto());
					} 
				}
			}
		} else {
			String sql = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor>1000  and f.ativo=1"
					+ " and f.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();
			if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
				if (estrela.isToptmstar()) {
					sql = sql + " and f.toptmstar=true";
				}
				sql = sql + " and f.numestrelas=" + estrela.getNumero();
			}
			sql = sql + " Group by f.cidade.pais.idpais order by f.cidade.pais.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
			listaTabelaPais = new ArrayList<Paisproduto>();
			listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
			pais = null;
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					List<Cidadepaisproduto> listacidade = lista.get(i).getCidade().getCidadepaisList();
					for (int j = 0; j < listacidade.size(); j++) {
						listaTabelaPais.add(listacidade.get(j).getPaisproduto());
					} 
				}
			}
		}
	}

	public void selecionarPaisComboBox() {
		if (pais != null) {
			listaTabelaPais = new ArrayList<Paisproduto>();
			listaTabelaPais.add(pais);
			gerarListaCidade();
		} else {
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			listaTabelaPais = paisProdutoFacade.listar(produtos.getProdutos().getIdprodutos());
		}
	}

	public void gerarListaCidade() {
		String sql = "";
		if (produtosorcamentoindice != null) {
			if (fornecedor != null) {
				sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
						+ fornecedor.getIdfornecedor() + " and f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="
						+ produtosorcamentoindice.getIdprodutosorcamentoindice()+" and f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();
				if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
					if (estrela.isToptmstar()) {
						sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.toptmstar=true";
					}
					sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.numestrelas=" + estrela.getNumero();
				}
				sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais=" + pais.getPais().getIdpais()
						+ " and f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="
						+ produtos.getProdutos().getIdprodutos()
						+ " Group by f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade order by f.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
			} else {
				sql = "select f from Fornecedorcidadeidiomaproduto f where f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="
						+ produtosorcamentoindice.getIdprodutosorcamentoindice()+" and f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();;
				if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
					if (estrela.isToptmstar()) {
						sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.toptmstar=true";
					}
					sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.numestrelas=" + estrela.getNumero();
				}
				sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais=" + pais.getPais().getIdpais()
						+ " and f.fornecedorcidadeidioma.fornecedorcidade.produtos.idprodutos="
						+ produtos.getProdutos().getIdprodutos()
						+ " Group by f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade order by f.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
			}
			FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
			List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeFacade.listar(sql);
			listaCidade = new ArrayList<Cidadepaisproduto>();
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
			listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					List<Cidadepaisproduto> listacidade = lista.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getCidadepaisList();
					int idproduto = produtos.getProdutos().getIdprodutos();
					for (int j = 0; j < listacidade.size(); j++) {
						if(listacidade.get(j).getPaisproduto().getProdutos().getIdprodutos()==idproduto) {
							listaCidade.add(listacidade.get(j));
							listaTabelaCidade.add(listacidade.get(j));
						}
					}   
				}
			}
		} else if (fornecedor != null) {
			sql = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor()
					+ " and f.cidade.pais.idpais=" + pais.getPais().getIdpais() + " and f.ativo=1"
					+" and f.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();
			if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
				if (estrela.isToptmstar()) {
					sql = sql + " and f.toptmstar=true";
				}
				sql = sql + " and f.numestrelas=" + estrela.getNumero()
					+ " and f.produtos.idprodutos=" + produtos.getProdutos().getIdprodutos();
			}
			sql = sql + " Group by f.cidade.idcidade order by f.cidade.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
			if (lista != null) {
				listaCidade = new ArrayList<Cidadepaisproduto>();
				listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
				for (int i = 0; i < lista.size(); i++) {
					List<Cidadepaisproduto> listacidade = lista.get(i).getCidade().getCidadepaisList();
					int idproduto = produtos.getProdutos().getIdprodutos();
					for (int j = 0; j < listacidade.size(); j++) {
						if(listacidade.get(j).getPaisproduto().getProdutos().getIdprodutos()==idproduto) {
							listaCidade.add(listacidade.get(j));
							listaTabelaCidade.add(listacidade.get(j));
						}
					}   
				}
			}
		} else {
			sql = "select f from Fornecedorcidade f where f.cidade.pais.idpais=" + pais.getPais().getIdpais() + " and f.ativo=1"
					+" and f.produtos.idprodutos="+produtos.getProdutos().getIdprodutos();
			if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
				if (estrela.isToptmstar()) {
					sql = sql + " and f.toptmstar=true";
				}
				sql = sql + " and f.numestrelas=" + estrela.getNumero()
				+ " and f.cidade.produtos.idprodutos=" + produtos.getProdutos().getIdprodutos();;
			}
			sql = sql + " Group by f.cidade.idcidade order by f.cidade.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
			if (lista != null) {
				listaCidade = new ArrayList<Cidadepaisproduto>();
				listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
				for (int i = 0; i < lista.size(); i++) {
					List<Cidadepaisproduto> listacidade = lista.get(i).getCidade().getCidadepaisList();
					int idproduto = produtos.getProdutos().getIdprodutos();
					for (int j = 0; j < listacidade.size(); j++) {
						if(listacidade.get(j).getPaisproduto().getProdutos().getIdprodutos()==idproduto) {
							listaCidade.add(listacidade.get(j));
							listaTabelaCidade.add(listacidade.get(j));
						}   
					}  
				}
			}
		}
	}

	// selecionar Produto (gerar lista pais)
	public void gerarListaPaisProduto() {
		if (produtosorcamentoindice != null) {
			String sql = "";
			if (fornecedor != null) {
				sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
						+ fornecedor.getIdfornecedor() + " and f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="
						+ produtosorcamentoindice.getIdprodutosorcamentoindice();
				if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
					if (estrela.isToptmstar()) {
						sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.toptmstar=true";
					}
					sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.numestrelas=" + estrela.getNumero();
				}
				sql = sql
						+ " Group by f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais order by f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			} else {
				sql = "select f from Fornecedorcidadeidiomaproduto f where f.produtosorcamento.produtosorcamentogrupo.produtosorcamentoindice.idprodutosorcamentoindice="
						+ produtosorcamentoindice.getIdprodutosorcamentoindice();
				if (estrela != null && estrela.getCaminho() != null && estrela.getNumero() > 0) {
					if (estrela.isToptmstar()) {
						sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.toptmstar=true";
					}
					sql = sql + " and f.fornecedorcidadeidioma.fornecedorcidade.numestrelas=" + estrela.getNumero();
				}
				sql = sql
						+ " Group by f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais order by f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			}
			FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
			List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeFacade.listar(sql);
			listaTabelaPais = new ArrayList<Paisproduto>();
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
			listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			pais = null;
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
					List<Cidadepaisproduto> listacidade = cidadePaisProdutosFacade.listar("SELECT c FROM Cidadepaisproduto c "
							+ "WHERE c.paisproduto.produtos.idprodutos="+produtos.getProdutos().getIdprodutos()
							+ " AND c.cidade.idcidade="+lista.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getIdcidade()
							+" GROUP BY c.paisproduto.idpaisproduto");
					if(listacidade!=null) {
						for (int j = 0; j < listacidade.size(); j++) {
							listaTabelaPais.add(listacidade.get(j).getPaisproduto());
						} 
					}
				}
			}
		} else {
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade(); 
			listaTabelaPais = paisProdutoFacade.listar(produtos.getProdutos().getIdprodutos());
			listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
		}
	}

	public String gerarOrcamento(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorCidadeIdiomaProduto", fornecedorcidadeidiomaproduto);
		return "filtrarorcamento";
	}

	public String habilitarGuia(Fornecedorcidade fornecedorcidade) {
		if (fornecedorcidade.getFornecedorcidadeguiaList() != null) {
			return "false";
		} else
			return "true";
	}

	public String guia(Fornecedorcidade fornecedorcidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("guiaescola", fornecedorcidade.getFornecedorcidadeguiaList().getGuiaescola());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 580);
		RequestContext.getCurrentInstance().openDialog("guiaEscola");
		return "";
	}

	public List<TMStarBean> retornarListaImagem(Fornecedorcidade fornecedorcidade) {
		ListaTMStarBean lista = new ListaTMStarBean();
		lista.setLista(new ArrayList<TMStarBean>());
		if (fornecedorcidade != null) {
			int numeroCinza = 5 - fornecedorcidade.getNumestrelas();
			TMStarBean tmStarBean;
			if (fornecedorcidade.getNumestrelas() > 0) {
				for (int i = 0; i < fornecedorcidade.getNumestrelas(); i++) {
					tmStarBean = new TMStarBean();
					if (!fornecedorcidade.isToptmstar()) {
						tmStarBean.setImagem("../../resources/img/imgtmstar.png");
					} else {
						tmStarBean.setImagem("../../resources/img/imgtoptmstar.png");
					}
					lista.getLista().add(tmStarBean);
				}
			}
			if (numeroCinza > 0) {
				for (int i = 0; i < numeroCinza; i++) {
					tmStarBean = new TMStarBean();
					tmStarBean.setImagem("../../resources/img/imgtmstarcinza.png");
					lista.getLista().add(tmStarBean);
				}
			}
		}
		return lista.getLista();
	}

	public void gerarEstrelaBean() {
		listaEstrela = new ArrayList<EstrelasBean>();
		EstrelasBean estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(0);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("115");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelacinza.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(2);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("40");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela2.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(3);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("65");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela3.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(3);
		estrelasBean.setToptmstar(true);
		estrelasBean.setTamanho("65");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelatop3.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(4);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("90");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela4.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(4);
		estrelasBean.setToptmstar(true);
		estrelasBean.setTamanho("90");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelatop4.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(5);
		estrelasBean.setToptmstar(false);
		estrelasBean.setTamanho("115");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrela5.png");
		listaEstrela.add(estrelasBean);
		estrelasBean = new EstrelasBean();
		estrelasBean.setNumero(5);
		estrelasBean.setToptmstar(true);
		estrelasBean.setTamanho("115");
		estrelasBean.setCaminho("../../resources/img/estrelas/estrelatop5.png");
		listaEstrela.add(estrelasBean);
	}

	public String depoimentos(Fornecedorcidade fornecedorcidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorcidade", fornecedorcidade);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("consDepoimentos");
		return "";
	}

	public boolean habilitarDocumentosVisto(Paisproduto pais) {
		if (pais.getPais().getDocumentovisto() != null && pais.getPais().getDocumentovisto().length() > 0) {
			return false;
		}
		return true;
	}
	
	public void habilitarCampos() {
		if (produtos!=null && produtos.getIddepartamentoproduto()!=null) {
			gerarListaPaisProduto();
			habilitarcampos = false;
		}else {
			habilitarcampos = true;
		} 
	}
	
	public boolean habilitarProduto() {
		if (departamento!=null && departamento.getIddepartamento()!=null) { 
			return false;
		}else {
			return true;
		} 
	}
	
	public boolean mostrarProduto() {
		int idproduto = aplicacaoMB.getParametrosprodutos().getCursos();
		if (produtos!=null && produtos.getIddepartamentoproduto()!=null && produtos.getProdutos().getIdprodutos()==idproduto) {
			return true;
		}else {
			return false;
		} 
	}
	
	public String mostrarProdutos() {
		int idproduto = aplicacaoMB.getParametrosprodutos().getCursos();
		if (produtos!=null && produtos.getIddepartamentoproduto()!=null && produtos.getProdutos().getIdprodutos()==idproduto) {
			return "4";
		}else {
			return "3";
		} 
	} 
	
	public void gerarListasProdutos() {
		if(departamento!=null && departamento.getIddepartamento()!=null) {
			DepartamentoProdutoFacade departamentoProdutoFacade= new DepartamentoProdutoFacade();
			listaProgramas = departamentoProdutoFacade.listar(departamento.getIddepartamento());
		}
	}
	 
	 
}
