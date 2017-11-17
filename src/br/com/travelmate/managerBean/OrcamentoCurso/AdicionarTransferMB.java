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
public class AdicionarTransferMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private List<ProdutosOrcamentoBean> listaTransfer;
	private ProdutosOrcamentoBean transfer;
	private String mensagem;
	private boolean transferIndependente;
	private List<ProdutosOrcamentoBean> listaTransferIndependente;

	@PostConstruct
	public void init() { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		resultadoOrcamentoBean = (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
		session.removeAttribute("resultadoOrcamentoBean");
		gerarListaTransfer();
		if(resultadoOrcamentoBean.getListaAcomodacoes()!=null && resultadoOrcamentoBean.getListaAcomodacoes().size()>0 &&
				resultadoOrcamentoBean.getListaAcomodacoes().get(0).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().isAcomodacaoindependente()) {
			gerarListaTransferIndependente();
		}
	}

	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}

	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}

	public List<ProdutosOrcamentoBean> getListaTransfer() {
		return listaTransfer;
	}

	public void setListaTransfer(List<ProdutosOrcamentoBean> listaTransfer) {
		this.listaTransfer = listaTransfer;
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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public ProdutosOrcamentoBean getTransfer() {
		return transfer;
	}

	public void setTransfer(ProdutosOrcamentoBean transfer) {
		this.transfer = transfer;
	}

	public boolean isTransferIndependente() {
		return transferIndependente;
	}

	public void setTransferIndependente(boolean transferIndependente) {
		this.transferIndependente = transferIndependente;
	}

	public List<ProdutosOrcamentoBean> getListaTransferIndependente() {
		return listaTransferIndependente;
	}

	public void setListaTransferIndependente(List<ProdutosOrcamentoBean> listaTransferIndependente) {
		this.listaTransferIndependente = listaTransferIndependente;
	}

	public void gerarListaTransfer() {
		listaTransfer = new ArrayList<>();
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		String sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
			+ resultadoOrcamentoBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma() + " and c.tipo='Transfer'"
			+ " and c.produtosorcamento.idprodutosOrcamento<>" + aplicacaoMB.getParametrosprodutos().getSuplementoidade()
			+ " and c.produtosorcamento.idprodutosOrcamento<>" + aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
			+ " and c.produtosorcamento.idprodutosOrcamento<>" + aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
		List<Coprodutos> listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) {
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						resultadoOrcamentoBean.getDataConsulta());
				if (po != null) {
					listaTransfer.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date());
					if (po != null) {
						listaTransfer.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), 
								resultadoOrcamentoBean.getDataConsulta());
						if (po != null) {
							listaTransfer.add(po);
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
			ano = resultadoOrcamentoBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getAnotarifario();
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
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = resultadoOrcamentoBean.getOcurso().getNumerosemanas() * 7;
			}
			po.setValorOrigianl(valorcoprodutos.getValororiginal() * multiplicador);
			po.setValorOriginalRS(po.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio()); 
			return po;
		}
		return null;
	}
	
	public Valorcoprodutos compararValores(Valorcoprodutos valorNovo, Valorcoprodutos valorAtual) {
		if (valorNovo.getPromocional() != null && valorNovo.getPromocional()) {
			return valorNovo;
		} else return valorAtual;
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
	
	public float calcularValorRealTransfer(ProdutosOrcamentoBean produtosOrcamentoBean) {
		Float valorReal = produtosOrcamentoBean.getValorcoprodutos().getValororiginal()
				* resultadoOrcamentoBean.getOcurso().getValorcambio();
		return valorReal;
	} 
	
	public boolean mostrarBtnConfirmar(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if(produtosOrcamentoBean!=null &&  
				produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia()!=null &&
				produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia().length()>1) { 
			return false;
		}return true;
	}
	
	public boolean mostrarBtnMensagem(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if(produtosOrcamentoBean!=null &&  
				produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia()!=null &&
				produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getAdvertencia().length()>1) { 
			return true;
		}return false;
	} 
	
	public void gerarListaTransferIndependente() {
		listaTransferIndependente = new ArrayList<>();
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		String sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
			+ resultadoOrcamentoBean.getListaAcomodacoes().get(0).getValorcoprodutos().getCoprodutos()
			.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()+ " and c.tipo='Transfer'"
			+ " and c.produtosorcamento.idprodutosOrcamento<>" + aplicacaoMB.getParametrosprodutos().getSuplementoidade()
			+ " and c.produtosorcamento.idprodutosOrcamento<>" + aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
			+ " and c.produtosorcamento.idprodutosOrcamento<>" + aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
		List<Coprodutos> listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) {
			Date dataconsulta = retornarDataConsultaOrcamento(resultadoOrcamentoBean.getOcurso().getDatainicio(),
						resultadoOrcamentoBean.getListaAcomodacoes().get(0).getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
								.getFornecedorcidade().getFornecedor()); 
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta);
				if (po != null) {
					listaTransferIndependente.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date());
					if (po != null) {
						listaTransferIndependente.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta);
						if (po != null) {
							listaTransferIndependente.add(po);
						}
					}
				}
			}
		}
	}
	
	public boolean mostrarTransferIndependente() {
		if (listaTransferIndependente == null || listaTransferIndependente.size() == 0) {
			return false;
		} else
			return true;
	}

	public String tabelaTransferEscola() {
		if (listaTransferIndependente == null || listaTransferIndependente.size() == 0) {
			return "480";
		} else
			return "200";
	}

	public boolean mostrarTransferEscola() {
		if (listaTransfer == null || listaTransfer.size() == 0) {
			return false;
		} else
			return true;
	}

	public String tabelaTransferIndependente() {
		if (listaTransfer == null || listaTransfer.size() == 0) {
			return "480";
		} else
			return "200";
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
}
