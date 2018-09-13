package br.com.travelmate.managerBean.acomodacao;

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

import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean;
import br.com.travelmate.model.Acomodacao;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class SelecionarAcomodacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
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
	private Cidade cidade;
	private Acomodacao acomodacaoInd;
	private String moedaNacional;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cidade = (Cidade) session.getAttribute("cidade");
		session.removeAttribute("cidade");
		acomodacaoInd = (Acomodacao) session.getAttribute("acomodacaoInd");
		session.removeAttribute("acomodacaoInd");
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
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Acomodacao getAcomodacaoInd() {
		return acomodacaoInd;
	}

	public void setAcomodacaoInd(Acomodacao acomodacaoInd) {
		this.acomodacaoInd = acomodacaoInd;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
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
		if (po.getNumeroSemanas() > 0) {
			po.setSelecionado(true);
			RequestContext.getCurrentInstance().closeDialog(po);
		}else {
			Mensagem.lancarMensagemInfo("Informe o número de semanas", "");
		}
		return "";
	}
	
	public String adicionarIndependente(ProdutosOrcamentoBean po) {
		if (po.getNumeroSemanas() > 0) {
			po.setSelecionado(true);
			po.setFornecedorcidadeidiomaAcomodacao(po.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma());
			RequestContext.getCurrentInstance().closeDialog(po);
		}else {
			Mensagem.lancarMensagemInfo("Informe o número de semanas", "");
		}
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
		Date dataconsulta = null;
		if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
				.isAcomodacaoindependente()) {
			dataconsulta = retornarDataConsultaOrcamento(acomodacaoInd.getDatainicial(),
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
					produtosOrcamentoBean.getValorOrigianl() * acomodacaoInd.getCambio().getValor());
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
								* acomodacaoInd.getCambio().getValor());
						produtosOrcamentoBean
								.setValorPromocional(produtosOrcamentoBean.getValorOrigianl() - valordesconto);
						produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
								* acomodacaoInd.getCambio().getValor());
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
			if ((acomodacaoInd.getDatainicial().after(promocao.getDatainicioiniciocurso())
					|| acomodacaoInd.getDatainicial().equals(promocao.getDatainicioiniciocurso()))
					&& (acomodacaoInd.getDatainicial().before(promocao.getDatainicioterminiocurso())
							|| acomodacaoInd.getDatainicial()
									.equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((acomodacaoInd.getDatainicial().after(promocao.getDatainicioacomodacao())
					|| acomodacaoInd.getDatainicial().equals(promocao.getDatainicioacomodacao()))
					&& (acomodacaoInd.getDatatermino()
							.before(promocao.getDataterminioacodomodacao())
							|| acomodacaoInd.getDatatermino()
									.equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (acomodacaoInd.getDatatermino().before(promocao.getDataterminocurso())
					|| acomodacaoInd.getDatatermino().equals(promocao.getDataterminocurso())) {
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

	public void gerarListaAcomodacaoIndependente() {
		listaAcomodacoesIndependente = new ArrayList<>();
		ValorCoProdutosFacade coProdutosFacade = new ValorCoProdutosFacade();
		String sql = "Select c from Valorcoprodutos c where c.coprodutos.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
				+ cidade.getIdcidade()
				+ " and c.coprodutos.fornecedorcidadeidioma.acomodacaoindependente=TRUE"
				+ " and c.coprodutos.tipo='Acomodacao" + "' and c.coprodutos.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
				+ " and c.coprodutos.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
				+ " and c.coprodutos.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao()
				+ " and c.coprodutos.apenaspacote=FALSE" + " GROUP BY c.coprodutos.idcoprodutos"
				+ " ORDER BY c.valororiginal";
		List<Valorcoprodutos> listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) {
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				Date dataconsulta = retornarDataConsultaOrcamento(acomodacaoInd.getDatainicial(),
						listaCoProdutos.get(i).getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade()
								.getFornecedor());
				ProdutosOrcamentoBean po = consultarValores("DI",
						listaCoProdutos.get(i).getCoprodutos().getIdcoprodutos(), dataconsulta);
				if (po != null) {
					po.setListaSemanas(new ArrayList<Integer>());
					po.setListaSemanas(retornarNSemanas(listaCoProdutos.get(i)));
					listaAcomodacoesIndependente.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getCoprodutos().getIdcoprodutos(), new Date());
					if (po != null) {
						po.setListaSemanas(new ArrayList<Integer>());
						po.setListaSemanas(retornarNSemanas(listaCoProdutos.get(i)));
						listaAcomodacoesIndependente.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getCoprodutos().getIdcoprodutos(),
								dataconsulta);
						if (po != null) {
							po.setListaSemanas(new ArrayList<Integer>());
							po.setListaSemanas(retornarNSemanas(listaCoProdutos.get(i)));
							listaAcomodacoesIndependente.add(po);
						}
					}
				}
			}
			verificarAcomodacao();
		}
	}
	
	
	public void verificarAcomodacao() {
		listaAcomodacoesIndependente1 = new ArrayList<>();
		listaAcomodacoesIndependente2 = new ArrayList<>();
		listaAcomodacoesIndependente3 = new ArrayList<>();
		fornecedor1 = 0;
		fornecedor2 = 0;
		fornecedor3 = 0;
		ProdutosOrcamentoBean po;
		for (int i = 0; i < listaAcomodacoesIndependente.size(); i++) {
			if (fornecedor1 <=0) {
				fornecedor1 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor();
				nomeFornecedor1 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getNome();
			}else if(fornecedor2 <=0 && (fornecedor1 != fornecedor2)) {
				fornecedor2 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor();
				nomeFornecedor2 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getNome();
			}else if (fornecedor3 <=0 && (fornecedor2 != fornecedor3)) {
				fornecedor3 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor();
				nomeFornecedor3 = listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getNome();
			}
			if (fornecedor1 > 0) {
				if (fornecedor1 == listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor()) {
					po = new ProdutosOrcamentoBean();
					po = listaAcomodacoesIndependente.get(i);
					listaAcomodacoesIndependente1.add(po);
				}else if(fornecedor2 == listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor()) {
					po = new ProdutosOrcamentoBean();
					po = listaAcomodacoesIndependente.get(i);
					listaAcomodacoesIndependente2.add(po);
				}else if(fornecedor3 == listaAcomodacoesIndependente.get(i).getValorcoprodutos().getCoprodutos().getFornecedorcidade().getFornecedor().getIdfornecedor()) {
					po = new ProdutosOrcamentoBean();
					po = listaAcomodacoesIndependente.get(i);
					listaAcomodacoesIndependente3.add(po);
				}
			}
		}
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
		if (listaAcomodacoesIndependente1 == null || listaAcomodacoesIndependente1.size() == 0) {
			return false;
		} else
			return true;
	}
	
	public boolean mostrarAcomodacaoIndependente2() {
		if (listaAcomodacoesIndependente2 == null || listaAcomodacoesIndependente2.size() == 0) {
			return false;
		} else
			return true;
	}
	
	public boolean mostrarAcomodacaoIndependente3() {
		if (listaAcomodacoesIndependente3 == null || listaAcomodacoesIndependente3.size() == 0) {
			return false;
		} else
			return true;
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
