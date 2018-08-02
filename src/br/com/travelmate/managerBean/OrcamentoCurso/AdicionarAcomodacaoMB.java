package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AdicionarAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private List<ProdutosOrcamentoBean> listaAcomodacoes;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente;
	private ProdutosOrcamentoBean acomodacao;
	private ProdutosOrcamentoBean acomodacaoIndependente;
	private Coprodutos avisoSemAcomodacao;
	private boolean habilitarAcomodacao = true;
	private boolean habilitarAviso = false;
	private int fornecedor1;
	private int fornecedor2;
	private int fornecedor3;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente1;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente2;
	private List<ProdutosOrcamentoBean> listaAcomodacoesIndependente3;
	private String nomeFornecedor1 = "";
	private String nomeFornecedor2 = "";
	private String nomeFornecedor3 = "";
	private String nomeAcomodacaoFornecedor = "";
	private String advertencia = "";

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		resultadoOrcamentoBean = (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
		session.removeAttribute("resultadoOrcamentoBean");
		listaAcomodacoes = (List<ProdutosOrcamentoBean>) session.getAttribute("listaAcomodacoes");
		session.removeAttribute("listaAcomodacoes");
		gerarListaAcomodacao();
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() <= 2) {
			listaAcomodacoesIndependente1 = (List<ProdutosOrcamentoBean>) session.getAttribute("listaAcomodacoesIndependente1");
			session.removeAttribute("listaAcomodacoesIndependente1");
			listaAcomodacoesIndependente2 = (List<ProdutosOrcamentoBean>) session.getAttribute("listaAcomodacoesIndependente2");
			session.removeAttribute("listaAcomodacoesIndependente1");
			listaAcomodacoesIndependente3 = (List<ProdutosOrcamentoBean>) session.getAttribute("listaAcomodacoesIndependente3");
			session.removeAttribute("listaAcomodacoesIndependente3");
			nomeFornecedor1 = (String) session.getAttribute("nomeFornecedor1");
			session.removeAttribute("nomeFornecedor1");
			nomeFornecedor2 = (String) session.getAttribute("nomeFornecedor2");
			session.removeAttribute("nomeFornecedor2");
			nomeFornecedor3 = (String) session.getAttribute("nomeFornecedor3");
			session.removeAttribute("nomeFornecedor3");
		}
		nomeAcomodacaoFornecedor = resultadoOrcamentoBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome();
	}

	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}

	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoes() {
		return listaAcomodacoes;
	}

	public void setListaAcomodacoes(List<ProdutosOrcamentoBean> listaAcomodacoes) {
		this.listaAcomodacoes = listaAcomodacoes;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public ProdutosOrcamentoBean getAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(ProdutosOrcamentoBean acomodacao) {
		this.acomodacao = acomodacao;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoesIndependente() {
		return listaAcomodacoesIndependente;
	}

	public void setListaAcomodacoesIndependente(List<ProdutosOrcamentoBean> listaAcomodacoesIndependente) {
		this.listaAcomodacoesIndependente = listaAcomodacoesIndependente;
	}

	public ProdutosOrcamentoBean getAcomodacaoIndependente() {
		return acomodacaoIndependente;
	}

	public void setAcomodacaoIndependente(ProdutosOrcamentoBean acomodacaoIndependente) {
		this.acomodacaoIndependente = acomodacaoIndependente;
	}

	public Coprodutos getAvisoSemAcomodacao() {
		return avisoSemAcomodacao;
	}

	public void setAvisoSemAcomodacao(Coprodutos avisoSemAcomodacao) {
		this.avisoSemAcomodacao = avisoSemAcomodacao;
	}

	public boolean isHabilitarAcomodacao() {
		return habilitarAcomodacao;
	}

	public void setHabilitarAcomodacao(boolean habilitarAcomodacao) {
		this.habilitarAcomodacao = habilitarAcomodacao;
	}

	public boolean isHabilitarAviso() {
		return habilitarAviso;
	}

	public void setHabilitarAviso(boolean habilitarAviso) {
		this.habilitarAviso = habilitarAviso;
	}

	public int getFornecedor1() {
		return fornecedor1;
	}

	public void setFornecedor1(int fornecedor1) {
		this.fornecedor1 = fornecedor1;
	}

	public int getFornecedor2() {
		return fornecedor2;
	}

	public void setFornecedor2(int fornecedor2) {
		this.fornecedor2 = fornecedor2;
	}

	public int getFornecedor3() {
		return fornecedor3;
	}

	public void setFornecedor3(int fornecedor3) {
		this.fornecedor3 = fornecedor3;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoesIndependente1() {
		return listaAcomodacoesIndependente1;
	}

	public void setListaAcomodacoesIndependente1(List<ProdutosOrcamentoBean> listaAcomodacoesIndependente1) {
		this.listaAcomodacoesIndependente1 = listaAcomodacoesIndependente1;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoesIndependente2() {
		return listaAcomodacoesIndependente2;
	}

	public void setListaAcomodacoesIndependente2(List<ProdutosOrcamentoBean> listaAcomodacoesIndependente2) {
		this.listaAcomodacoesIndependente2 = listaAcomodacoesIndependente2;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoesIndependente3() {
		return listaAcomodacoesIndependente3;
	}

	public void setListaAcomodacoesIndependente3(List<ProdutosOrcamentoBean> listaAcomodacoesIndependente3) {
		this.listaAcomodacoesIndependente3 = listaAcomodacoesIndependente3;
	}

	public String getNomeFornecedor1() {
		return nomeFornecedor1;
	}

	public void setNomeFornecedor1(String nomeFornecedor1) {
		this.nomeFornecedor1 = nomeFornecedor1;
	}

	public String getNomeFornecedor2() {
		return nomeFornecedor2;
	}

	public void setNomeFornecedor2(String nomeFornecedor2) {
		this.nomeFornecedor2 = nomeFornecedor2;
	}

	public String getNomeFornecedor3() {
		return nomeFornecedor3;
	}

	public void setNomeFornecedor3(String nomeFornecedor3) {
		this.nomeFornecedor3 = nomeFornecedor3;
	}

	public String getNomeAcomodacaoFornecedor() {
		return nomeAcomodacaoFornecedor;
	}

	public void setNomeAcomodacaoFornecedor(String nomeAcomodacaoFornecedor) {
		this.nomeAcomodacaoFornecedor = nomeAcomodacaoFornecedor;
	}

	public String getAdvertencia() {
		return advertencia;
	}

	public void setAdvertencia(String advertencia) {
		this.advertencia = advertencia;
	}

	public void gerarListaAcomodacao() {
		if (listaAcomodacoes != null && listaAcomodacoes.size() > 0) {
			habilitarAcomodacao = true;
			habilitarAviso = false;
		}
	}
	
	
	public List<Integer> retornarNSemanas(Valorcoprodutos valorcoprodutos){
		int nSemana = 0;
		List<Integer> listaSemanas = new ArrayList<Integer>();
		for (int i = 0; i < 48; i++) {
			nSemana = 1 + i;
			listaSemanas.add(nSemana);
		}
		return listaSemanas;
	}

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataconsulta,
			double numerosemana) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		int nsemanas = Formatacao.formatarDouble(numerosemana);
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "'  and v.tipodata='" + tipoData
				+ "' and v.numerosemanainicial<=" + nsemanas + " and v.numerosemanafinal>=" + nsemanas
				+ " and v.coprodutos.idcoprodutos=" + idCoProdutos + " ORDER BY v.valororiginal";
		List<Valorcoprodutos> listaValorCoprodutos = valorCoProdutosFacade.listar(sql);
		if (listaValorCoprodutos != null) {
			for (int n = 0; n < listaValorCoprodutos.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorCoprodutos.get(n);
				} else {
					valorcoprodutos = compararValores(listaValorCoprodutos.get(n), valorcoprodutos);
				}
			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			po.setValorOrigianl(0.0f);
			po.setValorOriginalRS(0.0f);
			return po;
		}
		return null;
	}
	
	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataconsulta) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null; 
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "'  and v.tipodata='" + tipoData 
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos + " ORDER BY v.valororiginal";
		List<Valorcoprodutos> listaValorCoprodutos = valorCoProdutosFacade.listar(sql);
		if (listaValorCoprodutos != null && listaValorCoprodutos.size() > 0) {
			valorcoprodutos = listaValorCoprodutos.get(0);
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			po.setValorOrigianl(0.0f);
			po.setValorOriginalRS(0.0f);
			return po;
		}
		return null;
	}

	public Valorcoprodutos compararValores(Valorcoprodutos valorNovo, Valorcoprodutos valorAtual) {
		if (valorNovo.getPromocional() != null && valorNovo.getPromocional()) {
			return valorNovo;
		} else
			return valorAtual;
	}

	public String adicionar(ProdutosOrcamentoBean po) {
		po.setSelecionado(true);
		RequestContext.getCurrentInstance().closeDialog(po);
		return "";
	}
	
	public String adicionarIndependente(ProdutosOrcamentoBean po) {
		po.setSelecionado(true);
		po.setFornecedorcidadeidiomaAcomodacao(po.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma());
		RequestContext.getCurrentInstance().closeDialog(po);
		return "";
	}


	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public boolean renderedValorPromocionalAparecendo(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean renderedValorPromocionalEscondido(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return false;
		} else {
			return true;
		}
	}

	public void calcularValorAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		Valorcoprodutos valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos();
		Date dataconsulta = resultadoOrcamentoBean.getDataConsulta();
		if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
				.isAcomodacaoindependente()) {
			dataconsulta = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
					produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
							.getFornecedorcidade().getFornecedor());
		}
		ProdutosOrcamentoBean po = consultarValores("DI",
				produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos(), dataconsulta,
				produtosOrcamentoBean.getNumeroSemanas());
		if (po != null) {
			valorcoprodutos = po.getValorcoprodutos();
		} else {
			po = consultarValores("DM", produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos(),
					new Date(), produtosOrcamentoBean.getNumeroSemanas());
			if (po != null) {
				valorcoprodutos = po.getValorcoprodutos();
			} else {
				po = consultarValores("DS",
						produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos(), dataconsulta,
						produtosOrcamentoBean.getNumeroSemanas());
				if (po != null) {
					valorcoprodutos = po.getValorcoprodutos();
				}else {
					valorcoprodutos =null;
				}
			}
		}
		if (valorcoprodutos != null) { 
			valorcoprodutos = compararValores(valorcoprodutos, produtosOrcamentoBean.getValorcoprodutos());
		}
		if (valorcoprodutos != null) {
			produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos);
			int multiplicador = 1;
			if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
			} else if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
			}
			produtosOrcamentoBean
					.setValorOrigianl(multiplicador * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
			produtosOrcamentoBean.setValorOriginalRS(
					produtosOrcamentoBean.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
			gerarPromocaoAcomodacao(produtosOrcamentoBean);
		} else {
			produtosOrcamentoBean.setValorOrigianl(0.0f);
			produtosOrcamentoBean.setValorOriginalRS(0.0f);
			produtosOrcamentoBean.setNumeroSemanas(0);
		}
	}

	public void gerarPromocaoAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		String sql = "select p From Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'  and p.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
						.getIdfornecedorcidadeidioma()
				+ " group by p.promocaoacomodacao.idpromoacaoacomodacao";
		PromocaoAcomodacaoCidadeFacade cidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		List<Promocaoacomodacaocidade> promocaoacomodacaocidade = cidadeFacade.listar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getComplementoacomodacao() != null) {
			valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos();
		}
		if (promocaoacomodacaocidade != null && valorcoprodutos != null) {
			for (int i = 0; i < promocaoacomodacaocidade.size(); i++) {
				boolean tempromocao = verificarPromocaoAcomodacaoValida(
						promocaoacomodacaocidade.get(i).getPromocaoacomodacao(), valorcoprodutos, produtosOrcamentoBean);
				if (tempromocao) { 
					float valordesconto = 0.0f;
					if (resultadoOrcamentoBean.getOcurso().getDatavalidade() == null) {
						resultadoOrcamentoBean.getOcurso().setDatavalidade(
								promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getDatavalidadefinal());
					} else if (resultadoOrcamentoBean.getOcurso().getDatavalidade() != null
							&& resultadoOrcamentoBean.getOcurso().getDatavalidade()
									.after(promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getDatavalidadefinal())) {
						resultadoOrcamentoBean.getOcurso().setDatavalidade(
								promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getDatavalidadefinal());
					}
					if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto().equalsIgnoreCase("P")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = valorcoprodutos.getValororiginal()
									* (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() / 100);
							valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = (float) (valorcoprodutos.getValororiginal()
									* produtosOrcamentoBean.getNumeroSemanas());
							valordesconto = valordesconto
									* (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() / 100);
						}
					} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto()
							.equalsIgnoreCase("V")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = (float) (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana()
									* produtosOrcamentoBean.getNumeroSemanas());
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal();
						}
					} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getTipodesconto()
							.equalsIgnoreCase("T")) {
						if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana() > 0) {
							valordesconto = valorcoprodutos.getValororiginal()
									- promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValorsemana();
							if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
								valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
							}
						} else if (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() != null
								&& promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal() > 0) {
							valordesconto = (float) (valorcoprodutos.getValororiginal()
									* produtosOrcamentoBean.getNumeroSemanas());
							valordesconto = valordesconto
									- promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValortotal();
						}
					}
					if ((promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto() != null)
							&& (promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto() > 0)) {
						if (valordesconto > promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto()) {
							valordesconto = promocaoacomodacaocidade.get(i).getPromocaoacomodacao().getValormaximodesconto();
						}
					}
					if (valordesconto > 0) {
						float valorOriginal = (float) (valorcoprodutos.getValororiginal()
								* produtosOrcamentoBean.getNumeroSemanas());
						produtosOrcamentoBean.setValorOrigianl(valorOriginal);
						produtosOrcamentoBean.setValorOriginalRS(produtosOrcamentoBean.getValorOrigianl()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
						produtosOrcamentoBean
								.setValorPromocional(produtosOrcamentoBean.getValorOrigianl() - valordesconto);
						produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
								* resultadoOrcamentoBean.getOcurso().getValorcambio());
						produtosOrcamentoBean.setPromocao(true);
						i = 10000;
					}
				} else {
					produtosOrcamentoBean.setValorPromocional(0.0f);
					produtosOrcamentoBean.setValorPromocionalRS(0.0f);
					produtosOrcamentoBean.setPromocao(false); 
				}
			} 
		} else {
			produtosOrcamentoBean.setValorPromocional(0.0f);
			produtosOrcamentoBean.setValorPromocionalRS(0.0f);
			produtosOrcamentoBean.setPromocao(false);
		}
	}

	public boolean verificarPromocaoAcomodacaoValida(Promocaoacomodacao promocao, Valorcoprodutos valorcoprodutos,
			ProdutosOrcamentoBean produtosOrcamentoBean) {
		Boolean tempromocao = false;
		if (promocao.getDatainicioiniciocurso() != null && promocao.getDatainicioterminiocurso() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioiniciocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioiniciocurso()))
					&& (resultadoOrcamentoBean.getOcurso().getDatainicio().before(promocao.getDatainicioterminiocurso())
							|| resultadoOrcamentoBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioacomodacao())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioacomodacao()))
					&& (resultadoOrcamentoBean.getOcurso().getDatatermino()
							.before(promocao.getDataterminioacodomodacao())
							|| resultadoOrcamentoBean.getOcurso().getDatatermino()
									.equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDataterminocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatatermino().equals(promocao.getDataterminocurso())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}

		if (promocao.getNumerosemanainicio() != null && promocao.getNumerosemanainicio() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (produtosOrcamentoBean.getNumeroSemanas() >= promocao.getNumerosemanainicio()
					&& produtosOrcamentoBean.getNumeroSemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaacima() != null && promocao.getValorsemanaacima() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorsemanaacima()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaigual() != null && promocao.getValorsemanaigual() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorsemanaigual()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoacomodacao() != null && promocao.getTipoacomodacao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoacomodacao()
					.equalsIgnoreCase(promocao.getTipoacomodacao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoquarto() != null && promocao.getTipoquarto().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoquarto()
					.equalsIgnoreCase(promocao.getTipoquarto())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTiporefeicao() != null && promocao.getTiporefeicao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTiporefeicao()
					.equalsIgnoreCase(promocao.getTiporefeicao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipobanheiro() != null && promocao.getTipobanheiro().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipobanheiro()
					.equalsIgnoreCase(promocao.getTipobanheiro())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	}

	public boolean mostrarBtnConfirmar(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean != null
				&& produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia() != null
				&& produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia().length() > 4) {
			return false;
		}
		return true;
	}

	public boolean mostrarBtnMensagem(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean != null
				&& produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia() != null
				&& produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia().length() > 4) {
			return true;
		}
		return false;
	}


	public Date retornarDataConsultaOrcamento(Date dataInicio, Fornecedor fornecedor) {
		int anoFornecedor = fornecedor.getAnotarifario();
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicio);
		int ano = Formatacao.getAnoData(dataInicio);
		if (anoFornecedor >= ano) {
			return dataInicio;
		} else if (anoFornecedor < ano) {
			String sData = Formatacao.ConvercaoDataPadrao(dataInicio);
			String dia = sData.substring(0, 2);
			String mes = sData.substring(3, 5);
			sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
			return Formatacao.ConvercaoStringDataBrasil(sData);
		}
		return dataInicio;
	}

	public boolean mostrarAcomodacaoIndependente() {
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() <= 2) {
			if (listaAcomodacoesIndependente1 == null || listaAcomodacoesIndependente1.size() == 0) {
				return false;
			} else
				return true;
		}else {
			return false;
		}
	}
	
	public boolean mostrarAcomodacaoIndependente2() {
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() <= 2) {
			if (listaAcomodacoesIndependente2 == null || listaAcomodacoesIndependente2.size() == 0) {
				return false;
			} else
				return true;
		}else {
			return false;
		}
	}
	
	public boolean mostrarAcomodacaoIndependente3() {
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() <= 2) {
			if (listaAcomodacoesIndependente3 == null || listaAcomodacoesIndependente3.size() == 0) {
				return false;
			} else
				return true;
		}else {
			return false;
		}
	}

	public String tabelaAcomodacaoEscola() {
		if (listaAcomodacoesIndependente == null || listaAcomodacoesIndependente.size() == 0) {
			return "450";
		} else
			return "250";
	}
   
	public boolean mostrarAcomodacaoEscola() {
		if (listaAcomodacoes == null || listaAcomodacoes.size() == 0) {
			return false;
		} else
			return true;
	}  

	public String tabelaAcomodacaoIndependente() {
		if (listaAcomodacoes == null || listaAcomodacoes.size() == 0) {
			return "450";
		} else
			return "250";
	}
	
	public void selecionarAcomodacaoIndependente(ProdutosOrcamentoBean acomodacao){
		advertencia = acomodacao.getValorcoprodutos().getCoprodutos().getAdvertencia();
		this.acomodacaoIndependente = acomodacao;
	}
}
