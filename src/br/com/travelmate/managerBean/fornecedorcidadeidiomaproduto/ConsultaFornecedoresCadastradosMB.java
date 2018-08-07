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
import org.primefaces.event.SelectEvent;

import br.com.travelmate.bean.DestinoParceirosBean;
import br.com.travelmate.bean.EstrelasBean;
import br.com.travelmate.bean.ListaTMStarBean;
import br.com.travelmate.bean.TMStarBean;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoIndiceFacade;
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Produtosorcamentoindice;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ConsultaFornecedoresCadastradosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Paisproduto> listaPais;
	private List<Paisproduto> listaTabelaPais;
	private List<Paisproduto> filtroTabelaPais;
	private List<Cidadepaisproduto> listaCidade;
	private List<Cidadepaisproduto> listaTabelaCidade;
	private List<Cidadepaisproduto> filtroTabelaCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private List<Fornecedorcidade> filtroFornecedorCidade;
	private List<Produtosorcamentoindice> listaProdutosOrcamentoIndice;
	private List<Fornecedor> listaFornecedor;
	private Paisproduto paisproduto;
	private Fornecedor fornecedor;
	private Produtosorcamentoindice produtosorcamentoindice;
	private Cidadepaisproduto cidadepaisproduto;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidadeidiomaproduto> listaTabelaProduto;
	private List<Fornecedorcidadeidiomaproduto> filtroTabelaProduto;
	private EstrelasBean estrela;
	private List<EstrelasBean> listaEstrela;
	private Ftpdados ftpdados;
	private List<Departamento> listaDepartamento;
	private Departamento departamento;
	private List<Produtosorcamento> listaprodutosorcamento;
	private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto;
	private String nomeProdutosOrcamento;
	private boolean filtrarProduto;
	private boolean informaçõesEscola;

	@PostConstruct
	public void init() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamento = departamentoFacade
				.listar("SELECT d FROM Departamento d WHERE d.venda=TRUE ORDER BY d.nome");
		corDepartamento();
		departamento = new Departamento();
		
		gerarListaFornecedor();
		gerarEstrelaBean();
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaTabelaPais = (List<Paisproduto>) session.getAttribute("listaTabelaPais");
		paisproduto = (Paisproduto) session.getAttribute("pais");
		listaTabelaCidade = (List<Cidadepaisproduto>) session.getAttribute("listaTabelaCidade");
		cidadepaisproduto = (Cidadepaisproduto) session.getAttribute("cidade");
		listaFornecedorCidade = (List<Fornecedorcidade>) session.getAttribute("listaFornecedorCidade");
		session.removeAttribute("listaTabelaPais");
		session.removeAttribute("pais");
		session.removeAttribute("listaTabelaCidade");
		session.removeAttribute("cidade");
		session.removeAttribute("listaFornecedorCidade");
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		ftpdados = new Ftpdados(); 
		gerarListaPais();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Fornecedorcidadeidiomaproduto> getFiltroTabelaProduto() {
		return filtroTabelaProduto;
	}

	public void setFiltroTabelaProduto(List<Fornecedorcidadeidiomaproduto> filtroTabelaProduto) {
		this.filtroTabelaProduto = filtroTabelaProduto;
	}

	public boolean isFiltrarProduto() {
		return filtrarProduto;
	}

	public void setFiltrarProduto(boolean filtrarProduto) {
		this.filtrarProduto = filtrarProduto;
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

	public List<Paisproduto> getFiltroTabelaPais() {
		return filtroTabelaPais;
	}

	public void setFiltroTabelaPais(List<Paisproduto> filtroTabelaPais) {
		this.filtroTabelaPais = filtroTabelaPais;
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

	public List<Cidadepaisproduto> getFiltroTabelaCidade() {
		return filtroTabelaCidade;
	}

	public void setFiltroTabelaCidade(List<Cidadepaisproduto> filtroTabelaCidade) {
		this.filtroTabelaCidade = filtroTabelaCidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public List<Fornecedorcidade> getFiltroFornecedorCidade() {
		return filtroFornecedorCidade;
	}

	public void setFiltroFornecedorCidade(List<Fornecedorcidade> filtroFornecedorCidade) {
		this.filtroFornecedorCidade = filtroFornecedorCidade;
	}

	public List<Produtosorcamento> getListaprodutosorcamento() {
		return listaprodutosorcamento;
	}

	public void setListaprodutosorcamento(List<Produtosorcamento> listaprodutosorcamento) {
		this.listaprodutosorcamento = listaprodutosorcamento;
	} 

	public List<Produtosorcamentoindice> getListaProdutosOrcamentoIndice() {
		return listaProdutosOrcamentoIndice;
	}

	public void setListaProdutosOrcamentoIndice(List<Produtosorcamentoindice> listaProdutosOrcamentoIndice) {
		this.listaProdutosOrcamentoIndice = listaProdutosOrcamentoIndice;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	} 

	public Paisproduto getPaisproduto() {
		return paisproduto;
	}

	public void setPaisproduto(Paisproduto paisproduto) {
		this.paisproduto = paisproduto;
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
	
	public Cidadepaisproduto getCidadepaisproduto() {
		return cidadepaisproduto;
	}

	public void setCidadepaisproduto(Cidadepaisproduto cidadepaisproduto) {
		this.cidadepaisproduto = cidadepaisproduto;
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

	public Fornecedorcidadeidiomaproduto getFornecedorcidadeidiomaproduto() {
		return fornecedorcidadeidiomaproduto;
	}

	public void setFornecedorcidadeidiomaproduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		this.fornecedorcidadeidiomaproduto = fornecedorcidadeidiomaproduto;
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

	public String getNomeProdutosOrcamento() {
		return nomeProdutosOrcamento;
	}

	public void setNomeProdutosOrcamento(String nomeProdutosOrcamento) {
		this.nomeProdutosOrcamento = nomeProdutosOrcamento;
	}

	public boolean isInformaçõesEscola() {
		return informaçõesEscola;
	}

	public void setInformaçõesEscola(boolean informaçõesEscola) {
		this.informaçõesEscola = informaçõesEscola;
	}

	public String arquivos(Fornecedorcidade fornecedorcidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorCidade", fornecedorcidade);
		session.setAttribute("listaTabelaPais", listaTabelaPais);
		session.setAttribute("pais", paisproduto);
		session.setAttribute("listaTabelaCidade", listaTabelaCidade);
		session.setAttribute("cidade", cidadepaisproduto);
		session.setAttribute("listaFornecedorCidade", listaFornecedorCidade);
		return "consArquivosFornecedor";
	}

	public String documentosFornecedor(Fornecedorcidade fornecedorcidade) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorCidade", fornecedorcidade);
		session.setAttribute("listaTabelaPais", listaTabelaPais);
		session.setAttribute("pais", paisproduto);
		session.setAttribute("listaTabelaCidade", listaTabelaCidade);
		session.setAttribute("cidade", cidadepaisproduto);
		session.setAttribute("listaFornecedorCidade", listaFornecedorCidade);
		return "consDocumentosFornecedor";
	}

	public void gerarListaProdutosOrcamentoIndice() {
		ProdutoOrcamentoIndiceFacade produtoOrcamentoIndiceFacade = new ProdutoOrcamentoIndiceFacade();
		listaProdutosOrcamentoIndice = produtoOrcamentoIndiceFacade
				.listar("select p from Produtosorcamentoindice p where p.departamentoproduto.departamento.iddepartamento=" + departamento.getIddepartamento() + " order by p.descricao");
		if (listaProdutosOrcamentoIndice == null) {
			listaProdutosOrcamentoIndice = new ArrayList<Produtosorcamentoindice>();
		}
	} 
	
	public void gerarListaFornecedor() {
		FornecedorFacade forncedorFacade = new FornecedorFacade();
		listaFornecedor = forncedorFacade
				.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}
	
	public boolean habilitarDocumentosVisto(Paisproduto pais) {
		if (pais.getPais().getDocumentovisto() != null && pais.getPais().getDocumentovisto().length() > 0) {
			return false;
		}
		return true;
	}

	public void gerarListaFornecedorCidade() {
		String sql = "";
		if (cidadepaisproduto != null) {
			produtosorcamentoindice = null;
			filtrarProduto = false;
			DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
			if (produtosorcamentoindice != null || listaprodutosorcamento != null
					|| fornecedorcidadeidiomaproduto != null) {
				sql = destinoParceirosBean.retornarSqlFornecedorCidadeIdiomaProduto
						(fornecedor, fornecedorcidadeidiomaproduto, listaprodutosorcamento, produtosorcamentoindice,
								departamento, estrela, paisproduto, cidadepaisproduto, fornecedorCidade);
				sql = sql + " GROUP BY f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor "
						+ " ORDER BY f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
				FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
				List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeFacade.listar(sql);
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
				filtroFornecedorCidade = null;
				if (lista != null) {
					for (int i = 0; i < lista.size(); i++) {
						listaFornecedorCidade.add(lista.get(i).getFornecedorcidadeidioma().getFornecedorcidade());
					}
				}
			} else {
				sql = destinoParceirosBean.retonarSqlFornecedorCidade(fornecedor, departamento, estrela, paisproduto, cidadepaisproduto);
				sql = sql + " GROUP BY f.fornecedor.idfornecedor ORDER BY f.fornecedor.nome";
				FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
				listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
				filtroFornecedorCidade = null;
			}
		} else {
			gerarListaPaisFornecedor();
		}
	}

	public void gerarListaFornecedorCidadeIdiomaProduto() {
		if(produtosorcamentoindice!=null && produtosorcamentoindice.getIdprodutosorcamentoindice()!=null) {
			filtrarProduto = true;
		}else filtrarProduto = false;
		DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
		String sql = destinoParceirosBean.retornarSqlFornecedorCidadeIdiomaProduto
				(fornecedor, fornecedorcidadeidiomaproduto, listaprodutosorcamento, produtosorcamentoindice, 
						departamento, estrela, paisproduto, cidadepaisproduto, fornecedorCidade);
		sql = sql + " GROUP BY f.produtosorcamento.idprodutosOrcamento "
				+ " ORDER BY f.produtosorcamento.descricao";
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
		listaTabelaProduto = fornecedorCidadeFacade.listar(sql);
		filtroTabelaProduto = null;
		if (listaTabelaProduto == null || listaTabelaProduto.size() == 0) {
			Mensagem.lancarMensagemInfo("Atenção", "Este parceiro não possui produtos cadastrados.");
		}
	}
 
	public void gerarListaPaisFornecedor() {
		if (produtosorcamentoindice != null || listaprodutosorcamento != null
				|| fornecedorcidadeidiomaproduto != null) {
			gerarListaPaisProduto();
		}
		DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
		String sql = destinoParceirosBean.retonarSqlFornecedorCidade(fornecedor, departamento, estrela, paisproduto, cidadepaisproduto);
		sql = sql + " Group by f.cidade.pais.idpais order by f.cidade.pais.nome";
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
		listaTabelaPais = new ArrayList<Paisproduto>();
		filtroTabelaPais = null;
		listaTabelaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
		filtroTabelaProduto = null;
		listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
		filtroFornecedorCidade = null;
		listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
		filtroTabelaCidade = null;
		paisproduto = null;
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
				String sqlCidade = destinoParceirosBean.retornarSqlCidadePaisProduto(
						lista.get(i).getCidade(), departamento);
				sqlCidade = sqlCidade + " GROUP BY c.cidade.pais.idpais";
				List<Cidadepaisproduto> listacidade = cidadePaisProdutosFacade.listar(sqlCidade); 
				if(listacidade!=null) {
					for (int j = 0; j < listacidade.size(); j++) {
						listaTabelaPais.add(listacidade.get(j).getPaisproduto());
					} 
				}
			}
		}
	}

	public void selecionarPaisComboBox() {
		if (paisproduto != null) {
			filtroTabelaPais = null;
			listaTabelaPais = new ArrayList<Paisproduto>();
			listaTabelaPais.add(paisproduto);
			gerarListaCidade();
		} else {
			filtroTabelaPais = null;
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
			listaTabelaPais = paisProdutoFacade.listar(destinoParceirosBean.retornarSqlPaisProduto(departamento)); 
		}
	}

	public void gerarListaCidade() {
		cidadepaisproduto = null;
		String sql = "";
		if (produtosorcamentoindice != null || listaprodutosorcamento != null
				|| fornecedorcidadeidiomaproduto != null) {
			DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
			sql = destinoParceirosBean.retornarSqlFornecedorCidadeIdiomaProduto
					(fornecedor, fornecedorcidadeidiomaproduto, listaprodutosorcamento, produtosorcamentoindice, 
							departamento, estrela, paisproduto, cidadepaisproduto, fornecedorCidade);
			sql = sql + " GROUP BY f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade "
					+ " ORDER BY f.fornecedorcidadeidioma.fornecedorcidade.cidade.nome";
			FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
			List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeFacade.listar(sql);
			listaCidade = new ArrayList<Cidadepaisproduto>();
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
			filtroTabelaCidade = null;
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			filtroFornecedorCidade = null;
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
					String sqlCidade = destinoParceirosBean.retornarSqlCidadePaisProduto(
							lista.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade(), departamento);
					sqlCidade = sqlCidade + " GROUP BY c.cidade.idcidade";
					List<Cidadepaisproduto> listacidade = cidadePaisProdutosFacade.listar(sqlCidade);
					if (listacidade != null) {
						for (int j = 0; j < listacidade.size(); j++) {
							listaCidade.add(listacidade.get(j));
							listaTabelaCidade.add(listacidade.get(j));
							filtroTabelaCidade = null;
						}
					} else {
						listaCidade = new ArrayList<>();
						listaTabelaCidade = new ArrayList<>();
						filtroTabelaCidade = null;
					}
				}
			}
		} else {
			DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
			sql = destinoParceirosBean.retonarSqlFornecedorCidade(fornecedor, departamento, estrela, paisproduto, cidadepaisproduto);
			sql = sql + " GROUP BY f.cidade.idcidade ORDER BY f.cidade.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			List<Fornecedorcidade> lista = fornecedorCidadeFacade.listar(sql);
			if (lista != null) {
				listaCidade = new ArrayList<Cidadepaisproduto>();
				listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
				filtroTabelaCidade = null;
				for (int i = 0; i < lista.size(); i++) {
					CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
					String sqlCidade = destinoParceirosBean.retornarSqlCidadePaisProduto(
							lista.get(i).getCidade(), departamento);
					sqlCidade = sqlCidade + " GROUP BY c.cidade.idcidade";
					List<Cidadepaisproduto> listacidade = cidadePaisProdutosFacade.listar(sqlCidade);
					if (listacidade != null) {
						for (int j = 0; j < listacidade.size(); j++) {
							listaCidade.add(listacidade.get(j));
							listaTabelaCidade.add(listacidade.get(j));
							filtroTabelaCidade = null;
						}
					}
				}
			}
		}
	}

	public void gerarListaPaisProduto() {
		gerarListaProdutosOrcamentoIndice();
		paisproduto = null;
		if (produtosorcamentoindice != null || listaprodutosorcamento != null
				|| fornecedorcidadeidiomaproduto != null) {
			DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
			String sql = destinoParceirosBean.retornarSqlFornecedorCidadeIdiomaProduto
					(fornecedor, fornecedorcidadeidiomaproduto, listaprodutosorcamento, produtosorcamentoindice, 
							departamento, estrela, paisproduto, cidadepaisproduto, fornecedorCidade);
			sql = sql + " GROUP BY f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais "
					+ " ORDER BY f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.nome";
			FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
			List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeFacade.listar(sql);
			filtroTabelaPais = null;
			listaTabelaPais = new ArrayList<Paisproduto>();
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
			filtroTabelaCidade = null;
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>(); 
			filtroFornecedorCidade = null;
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
					String sqlCidade = destinoParceirosBean.retornarSqlCidadePaisProduto(
							lista.get(i).getFornecedorcidadeidioma().getFornecedorcidade().getCidade(), departamento);
					sqlCidade = sqlCidade + " GROUP BY c.cidade.idcidade";
					List<Cidadepaisproduto> listacidade = cidadePaisProdutosFacade.listar(sqlCidade);
					if (listacidade != null) {
						for (int j = 0; j < listacidade.size(); j++) {
							listaTabelaPais.add(listacidade.get(j).getPaisproduto());
						}
					} else {
						listaTabelaPais = new ArrayList<>();
					}
				}
			}
		} else {
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
			filtroTabelaPais = null;
			listaTabelaPais = paisProdutoFacade.listar(destinoParceirosBean.retornarSqlPaisProduto(departamento));
			listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			filtroFornecedorCidade = null;
			listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
			filtroTabelaCidade = null;
		}
	}

	public String gerarOrcamento(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorCidadeIdiomaProduto", fornecedorcidadeidiomaproduto);
		return "filtrarorcamento";
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
		DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
		listaEstrela = destinoParceirosBean.gerarEstrelaBean();
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

	public void retornoDialogoProdutosOrcamento(SelectEvent event) {
		List<Produtosorcamento> po = (List<Produtosorcamento>) event.getObject();
		this.listaprodutosorcamento = po;
		fornecedorcidadeidiomaproduto = null;
		filtroTabelaProduto = null;
		produtosorcamentoindice = null;
		gerarListaFornecedorCidadeIdiomaProduto(); 
		filtrarProduto = true;
	}

	public String buscarProdutosOrcamento() {
		if (nomeProdutosOrcamento != null && nomeProdutosOrcamento.length() > 0) {
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			List<Produtosorcamento> listaProdutoOrcamento = produtoOrcamentoFacade
					.listarProdutosOrcamento(nomeProdutosOrcamento);
			if (listaProdutoOrcamento != null && listaProdutoOrcamento.size() > 0) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("listaProdutoOrcamento", listaProdutoOrcamento);
				Map<String, Object> options = new HashMap<String, Object>();
				options.put("contentWidth", 400);
				RequestContext.getCurrentInstance().openDialog("buscarProdutosOrcamento", options, null);
			} else {
				Mensagem.lancarMensagemInfo("Atenção!", "Nenhum produto encontrado.");
			}
		} else {
			Mensagem.lancarMensagemInfo("Atenção!", "Campos obrigatórios não preenchidos.");
		}
		return "";
	} 

	public void gerarListaPais() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		DestinoParceirosBean destinoParceirosBean = new DestinoParceirosBean();
		listaPais = paisProdutoFacade.listar(destinoParceirosBean.retornarSqlPaisProduto(departamento));
		listaCidade = new ArrayList<Cidadepaisproduto>();
	}

	public void limparFiltro() {
		paisproduto = null;
		estrela = null;
		cidadepaisproduto = null;
		fornecedorCidade = null;
		fornecedor = null;
		fornecedorcidadeidiomaproduto = null;
		produtosorcamentoindice = null;
		listaprodutosorcamento = null;
		listaTabelaCidade = new ArrayList<Cidadepaisproduto>();
		filtroTabelaCidade = null;
		listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
		filtroFornecedorCidade = null;
		nomeProdutosOrcamento = "";
		fornecedorcidadeidiomaproduto = null;
		listaTabelaProduto = new ArrayList<>();
		filtroTabelaProduto = null;
		filtroTabelaPais=null;
		listaTabelaPais = new ArrayList<>();
		departamento = new Departamento();
		gerarListaPais(); 
	}
	

	public void selecionarFornecedorCidadeIdiomaProduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		this.fornecedorcidadeidiomaproduto = fornecedorcidadeidiomaproduto;
		gerarListaPaisProduto();
	}  
	
	public void selecionarFornecedorCidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorCidade = fornecedorcidade;
	}

	public void selecionarPais(Paisproduto pais) {
		this.paisproduto = pais;
	}

	public void selecionarCidade(Cidadepaisproduto cidade) {
		this.cidadepaisproduto = cidade;
	}

	public boolean mostrarPais() {
		if (listaTabelaPais != null && listaTabelaPais.size() > 0) {
			return true;
		} else
			return false;
	}

	public boolean mostrarCidade() {
		if (listaTabelaCidade != null && listaTabelaCidade.size() > 0) {
			return true;
		} else
			return false;
	}

	public boolean mostrarParceiro() {
		if (listaFornecedorCidade != null && listaFornecedorCidade.size() > 0) {
			return true;
		} else
			return false;
	}

	public boolean mostrarProdutosFuncao() {
		if (listaTabelaProduto != null && listaTabelaProduto.size() > 0 && filtrarProduto) {
			return true;
		} else
			return false;
	}
	
	public boolean mostrarProdutosSemFuncao() {
		if (listaTabelaProduto != null && listaTabelaProduto.size() > 0 && !filtrarProduto) {
			return true;
		} else
			return false;
	}

	public String habilitarGuia(Fornecedorcidade fornecedorcidade) {
		if (fornecedorcidade.getFornecedorcidadeguiaList() != null) {
			return "false";
		} else
			return "true";
	} 
	
	public void corDepartamento() {
		if(listaDepartamento!=null) {
			for (int i = 0; i < listaDepartamento.size(); i++) {
				if(listaDepartamento.get(i).getNome().equalsIgnoreCase("Cursos")) {
					listaDepartamento.get(i).setCor("bolaVerde.png");
				}else if(listaDepartamento.get(i).getNome().equalsIgnoreCase("Programas de Trabalho")) {
					listaDepartamento.get(i).setCor("bolaVermelha.png");
				}else if(listaDepartamento.get(i).getNome().equalsIgnoreCase("Teens")) {
					listaDepartamento.get(i).setCor("bolaRoxa.png");
				}else if(listaDepartamento.get(i).getNome().equalsIgnoreCase("Higher Education")) {
					listaDepartamento.get(i).setCor("bolaAzul.png");
				}else if(listaDepartamento.get(i).getNome().equalsIgnoreCase("Turismo")) {
					listaDepartamento.get(i).setCor("bolaLaranja.png");
				}
			}
		}
	}
	
	public String corProduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		if(fornecedorcidadeidiomaproduto!=null) { 
			int idproduto = fornecedorcidadeidiomaproduto.getFornecedorcidadeidioma().getFornecedorcidade()
					.getProdutos().getIdprodutos();
			if(idproduto==aplicacaoMB.getParametrosprodutos().getCursos()) {
				return "../../resources/img/bolaVerde.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getWork()) {
				return "../../resources/img/bolaVermelha.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getAupair()) {
				return "../../resources/img/bolaVermelha.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getTrainee()) {
				return "../../resources/img/bolaVermelha.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
				return "../../resources/img/bolaVermelha.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getDemipair()) {
				return "../../resources/img/bolaVermelha.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
				return "../../resources/img/bolaRoxa.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getHighSchool()) {
				return "../../resources/img/bolaRoxa.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getHighereducation()) {
				return "../../resources/img/bolaAzul.png";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getPacotes()) {
				return "../../resources/img/bolaLaranja.png";
			} 
		}
		return "";
	}
	
	public String titleProduto(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto) {
		if(fornecedorcidadeidiomaproduto!=null) { 
			int idproduto = fornecedorcidadeidiomaproduto.getFornecedorcidadeidioma().getFornecedorcidade()
					.getProdutos().getIdprodutos();
			if(idproduto==aplicacaoMB.getParametrosprodutos().getCursos()) {
				return "Cursos";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getWork()) {
				return "Programas de Trabalho";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getAupair()) {
				return "Programas de Trabalho";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getTrainee()) {
				return "Programas de Trabalho";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
				return "Programas de Trabalho";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getDemipair()) {
				return "Programas de Trabalho";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
				return "Teens";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getHighSchool()) {
				return "Teens";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getHighereducation()) {
				return "Higher Education";
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getPacotes()) {
				return "Turismo";
			} 
		}
		return "";
	}
	
	public void visualizarInformacoesEscola() {
		if(informaçõesEscola) {
			informaçõesEscola=false;
		}else informaçõesEscola =true;
	}
	  
	public String imagemInformacaoEscola() {
		if(informaçõesEscola) {
			return "../../resources/img/esconderOpcoes.png";
		}else return "../../resources/img/expandirOpcoes.png";
	}
	
	public String titleInformacaoEscola() {
		if(!informaçõesEscola) {
			return "Expandir informações da escola.";
		}else return "Esconder informações da escola.";
	}
}
