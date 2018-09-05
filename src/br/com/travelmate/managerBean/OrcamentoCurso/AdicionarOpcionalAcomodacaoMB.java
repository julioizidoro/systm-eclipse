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
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AdicionarOpcionalAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private List<ProdutosOrcamentoBean> listaAcOpcional;
	private List<ProdutosOrcamentoBean> listaAcOpcionalIndependente;
	private String nomeAcomodacaoFornecedor;
	private String moedaNacional;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		resultadoOrcamentoBean = (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
		session.removeAttribute("resultadoOrcamentoBean");
		gerarListaAcOpcional();
		if (resultadoOrcamentoBean.getListaAcomodacoes() != null
				&& resultadoOrcamentoBean.getListaAcomodacoes().size() > 0
				&& resultadoOrcamentoBean.getListaAcomodacoes().get(0).getValorcoprodutos().getCoprodutos()
						.getFornecedorcidadeidioma().isAcomodacaoindependente()) {
			gerarListaAcOpcionalIndependente();
		}
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	}

	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}

	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}

	public List<ProdutosOrcamentoBean> getListaAcOpcional() {
		return listaAcOpcional;
	}

	public void setListaAcOpcional(List<ProdutosOrcamentoBean> listaAcOpcional) {
		this.listaAcOpcional = listaAcOpcional;
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

	public List<ProdutosOrcamentoBean> getListaAcOpcionalIndependente() {
		return listaAcOpcionalIndependente;
	}

	public void setListaAcOpcionalIndependente(List<ProdutosOrcamentoBean> listaAcOpcionalIndependente) {
		this.listaAcOpcionalIndependente = listaAcOpcionalIndependente;
	}

	public String getNomeAcomodacaoFornecedor() {
		return nomeAcomodacaoFornecedor;
	}

	public void setNomeAcomodacaoFornecedor(String nomeAcomodacaoFornecedor) {
		this.nomeAcomodacaoFornecedor = nomeAcomodacaoFornecedor;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public void gerarListaAcOpcional() {
		listaAcOpcional = new ArrayList<>();
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		String sql =  "";
		List<Coprodutos> listaCoProdutos = null;
		if (resultadoOrcamentoBean != null) {
			sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ resultadoOrcamentoBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
					+ " and c.tipo='AcOpcional'" + " and c.produtosorcamento.idprodutosOrcamento<>"
					+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
					+ " and c.produtosorcamento.idprodutosOrcamento<>"
					+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
					+ " and c.produtosorcamento.idprodutosOrcamento<>"
					+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
			listaCoProdutos = coProdutosFacade.listar(sql);
		}
		if (listaCoProdutos != null) {
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						resultadoOrcamentoBean.getDataConsulta());
				if (po != null) {
					listaAcOpcional.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date());
					if (po != null) {
						listaAcOpcional.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(),
								resultadoOrcamentoBean.getDataConsulta());
						if (po != null) {
							listaAcOpcional.add(po);
						}
					}
				}
			}
		}
	}

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataconsulta) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.numerosemanainicial<="
				+ resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " and v.numerosemanafinal>="
				+ resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " and v.tipodata='" + tipoData
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
		List<Valorcoprodutos> listaValorCoprodutos = valorCoProdutosFacade.listar(sql);
		int ano;
		if (resultadoOrcamentoBean.getFornecedorcidadeidioma() != null) {
			ano = resultadoOrcamentoBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor()
					.getAnotarifario();
		} else {
			ano = Formatacao.getAnoData(new Date());
		}
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
			po.setValorOrigianl(valorcoprodutos.getValororiginal());
			po.setValorOriginalRS(po.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
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

	public void calcularValorAcOpcional(ProdutosOrcamentoBean produtosOrcamentoBean) {
		Double numeroSemanas = produtosOrcamentoBean.getNumeroSemanasAcOpcional();
		if (produtosOrcamentoBean.getNumeroSemanasAcOpcional() != resultadoOrcamentoBean.getOcurso()
				.getNumerosemanas()) {
			ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
			Valorcoprodutos valorcoprodutos = null;
			String sql = "Select v from  Valorcoprodutos v where v.datainicial<='"
					+ Formatacao.ConvercaoDataSql(resultadoOrcamentoBean.getDataConsulta()) + "' and v.datafinal>='"
					+ Formatacao.ConvercaoDataSql(resultadoOrcamentoBean.getDataConsulta())
					+ "' and v.numerosemanainicial<=" + numeroSemanas + " and v.numerosemanafinal>=" + numeroSemanas
					+ " and v.coprodutos.idcoprodutos="
					+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos();
			List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
			if (listaValorcoprodutoses != null) {
				for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
					if (valorcoprodutos == null) {
						valorcoprodutos = new Valorcoprodutos();
						valorcoprodutos = listaValorcoprodutoses.get(n);
					} else {
						valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
					}
				}
			}
			if (valorcoprodutos != null) {
				produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos);
			}
		}
		int multiplicador = 1;
		if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
			multiplicador = (int) numeroSemanas.intValue();
		} else if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
			multiplicador = (int) (numeroSemanas * 7);
		}
		produtosOrcamentoBean.setValorOriginalAcOpcional(
				multiplicador * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
		produtosOrcamentoBean.setValorRSacOpcional(
				multiplicador * (produtosOrcamentoBean.getValorcoprodutos().getValororiginal()
						* resultadoOrcamentoBean.getOcurso().getValorcambio()));
	}

	public String adicionar(ProdutosOrcamentoBean po) {
		po.setSelecionado(true);
		RequestContext.getCurrentInstance().closeDialog(po);
		return "";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public float calcularValorRealAcOpcional(ProdutosOrcamentoBean produtosOrcamentoBean) {
		Float valorReal = produtosOrcamentoBean.getValorcoprodutos().getValororiginal()
				* resultadoOrcamentoBean.getOcurso().getValorcambio();
		return valorReal;
	}
	
	public void gerarListaAcOpcionalIndependente() {
		listaAcOpcionalIndependente = new ArrayList<>();
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		String sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+  resultadoOrcamentoBean.getListaAcomodacoes().get(0).getValorcoprodutos().getCoprodutos()
				.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and c.tipo='AcOpcional'" + " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
		List<Coprodutos> listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) {
			Date dataconsulta = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
					resultadoOrcamentoBean.getListaAcomodacoes().get(0).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
							.getFornecedorcidade().getFornecedor()); 
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						dataconsulta);
				if (po != null) {
					listaAcOpcionalIndependente.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date());
					if (po != null) {
						listaAcOpcionalIndependente.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(),
								dataconsulta);
						if (po != null) {
							listaAcOpcionalIndependente.add(po);
						}
					}
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
		if (listaAcOpcionalIndependente == null || listaAcOpcionalIndependente.size() == 0) {
			return false;
		} else
			return true;
	}

	public String tabelaAcomodacaoEscola() {
		if (listaAcOpcionalIndependente == null || listaAcOpcionalIndependente.size() == 0) {
			return "480";
		} else
			return "200";
	}

	public boolean mostrarAcomodacaoEscola() {
		if (listaAcOpcional == null || listaAcOpcional.size() == 0) {
			return false;
		} else
			return true;
	}

	public String tabelaAcomodacaoIndependente() {
		if (listaAcOpcional == null || listaAcOpcional.size() == 0) {
			return "480";
		} else
			return "200";
	}
}
