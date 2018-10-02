package br.com.travelmate.managerBean.voluntariadoprojeto.orcamento;
 
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosExtrasBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.util.Formatacao;

import java.io.Serializable;
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

@Named
@ViewScoped
public class ResultadoOrcamentoVoluntariadoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private OrcamentoVoluntariadoBean orcamento;
	private float totalmoedaestrangeira=0;
	private float totalmoedanacional=0;
	private float totalextrasmoedaestrangeira=0;
	private float totalextrasmoedanacional=0; 
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private List<Valoresseguro> listaValoresSeguro; 
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;
	private String moedaNacional;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		orcamento = (OrcamentoVoluntariadoBean) session.getAttribute("orcamento");   
		session.removeAttribute("orcamento");   
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = aplicacaoMB.getParametrosprodutos().getSeguroPrivado();
		List<Paisproduto> listaPais = paisProdutoFacade.listar(idProduto);
		if ((listaPais.size() > 0) && (listaPais != null)) {
			listaFornecedorCidade = listaPais.get(0).getProdutos().getFornecedorcidadeList();
		} else listaFornecedorCidade = new ArrayList<Fornecedorcidade>(); 
		if(orcamento.isPossuiSeguroViagem()){
			fornecedorcidade = orcamento.getValorSeguro().getFornecedorcidade();
			calcularDataTermino();
			listarPlanosSeguro(); 
			seguroplanos = orcamento.getValorSeguro().getSeguroplanos();
			listarValoresSeguro();
			convertendoValoresSeguro(); 
		}else {
			seguroplanos = new Seguroplanos();
		}
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		somarValorTotal();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public OrcamentoVoluntariadoBean getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(OrcamentoVoluntariadoBean orcamento) {
		this.orcamento = orcamento;
	}

	public float getTotalmoedaestrangeira() {
		return totalmoedaestrangeira;
	}

	public void setTotalmoedaestrangeira(float totalmoedaestrangeira) {
		this.totalmoedaestrangeira = totalmoedaestrangeira;
	}

	public float getTotalmoedanacional() {
		return totalmoedanacional;
	}

	public void setTotalmoedanacional(float totalmoedanacional) {
		this.totalmoedanacional = totalmoedanacional;
	}

	public float getTotalextrasmoedaestrangeira() {
		return totalextrasmoedaestrangeira;
	}

	public void setTotalextrasmoedaestrangeira(float totalextrasmoedaestrangeira) {
		this.totalextrasmoedaestrangeira = totalextrasmoedaestrangeira;
	}

	public float getTotalextrasmoedanacional() {
		return totalextrasmoedanacional;
	}

	public void setTotalextrasmoedanacional(float totalextrasmoedanacional) {
		this.totalextrasmoedanacional = totalextrasmoedanacional;
	} 

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	}
  
	public List<Seguroplanos> getListaSeguroPlanos() {
		return listaSeguroPlanos;
	}

	public void setListaSeguroPlanos(List<Seguroplanos> listaSeguroPlanos) {
		this.listaSeguroPlanos = listaSeguroPlanos;
	}

	public Seguroplanos getSeguroplanos() {
		return seguroplanos;
	}

	public void setSeguroplanos(Seguroplanos seguroplanos) {
		this.seguroplanos = seguroplanos;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public String retornarDescricaoAcomodacao(){
		String retorno = orcamento.getVoluntariadoprojetoacomodacao().getTipoacomodacao()+", "
				+orcamento.getVoluntariadoprojetoacomodacao().getTipoquarto()+", "
				+orcamento.getVoluntariadoprojetoacomodacao().getTiporefeicao()+", Banheiro "
				+orcamento.getVoluntariadoprojetoacomodacao().getTipobanheiro();
		return retorno;
	}
	
	public String retornarDescricaoSemanaAdicional(){
		String retorno = "";
		if(orcamento.getNumeroSemanasAdicionais()>0){
			if(orcamento.getNumeroSemanasAdicionais()>1){
				retorno = orcamento.getNumeroSemanasAdicionais() + " Semanas";
			}else{
				retorno = orcamento.getNumeroSemanasAdicionais() + " Semana";
			}
		} 
		return retorno;
	}
	
	public void somarValorTotal(){
		totalextrasmoedanacional = 0.0f;
		totalextrasmoedaestrangeira = 0.0f;
		totalmoedaestrangeira = orcamento.getValor() + orcamento.getValortaxatm();
		if(orcamento.getValorSemanaAdc()!=null && orcamento.getValorSemanaAdc()>0){
			totalmoedaestrangeira = totalmoedaestrangeira + orcamento.getValorSemanaAdc();
		}
		totalmoedanacional = totalmoedaestrangeira * orcamento.getValorcambio();
		if (orcamento.getSeguroviagem() != null) {
			if (orcamento.getSeguroviagem().getValorSeguro() != null && !orcamento.getSeguroviagem().isSomarvalortotal()) {
				totalextrasmoedanacional = totalextrasmoedanacional + orcamento.getSeguroviagem().getValorSeguro();
				totalextrasmoedaestrangeira = totalextrasmoedaestrangeira
						+ (orcamento.getSeguroviagem().getValorSeguro() / orcamento.getValorcambio());
			} else if (orcamento.getSeguroviagem().getValorSeguro() != null && orcamento.getSeguroviagem().isSomarvalortotal()) {
				totalmoedanacional = totalmoedanacional + orcamento.getSeguroviagem().getValorSeguro();
				totalmoedaestrangeira = totalmoedaestrangeira
						+ (orcamento.getSeguroviagem().getValorSeguro() / orcamento.getValorcambio());
			}
		}
		if(orcamento.getListaOutrosProdutos()!=null && orcamento.getListaOutrosProdutos().size()>0){
			for (int i = 0; i < orcamento.getListaOutrosProdutos().size(); i++) {
				if(orcamento.getListaOutrosProdutos().get(i).isSomarvalortotal()){
					totalmoedanacional = totalmoedanacional + orcamento.getListaOutrosProdutos().get(i).getValorOriginalRS();
					totalmoedaestrangeira = totalmoedaestrangeira + orcamento.getListaOutrosProdutos().get(i).getValorOrigianl();
				}else{
					totalextrasmoedanacional = totalextrasmoedanacional + orcamento.getListaOutrosProdutos().get(i).getValorOriginalRS();
					totalextrasmoedaestrangeira = totalextrasmoedaestrangeira + orcamento.getListaOutrosProdutos().get(i).getValorOrigianl();
				}
			}
		}
		if(orcamento.getListaDesconto()!=null && orcamento.getListaDesconto().size()>0){
			for (int i = 0; i < orcamento.getListaDesconto().size(); i++) {
				if(orcamento.getListaDesconto().get(i).isSelecionado()){
					totalmoedanacional = totalmoedanacional - orcamento.getListaDesconto().get(i).getValormoedanacional();
					totalmoedaestrangeira = totalmoedaestrangeira - orcamento.getListaDesconto().get(i).getValormoedaestrangeira();
				}  
			}
		}
	}
	  
	public String habilitarSeguro() {
		if (orcamento.isPossuiSeguroViagem()) {
			return "false";
		}
		return "true";
	}
	
	public void listarPlanosSeguro() {
		if (fornecedorcidade != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}

	public void listarValoresSeguro() {
		if (fornecedorcidade != null && seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			String sql;
			sql = "SELECT v FROM Valoresseguro v WHERE v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorcidade.getIdfornecedorcidade() + " AND v.situacao='Ativo'"
					+ " AND v.seguroplanos.idseguroplanos=" + seguroplanos.getIdseguroplanos();
			listaValoresSeguro = valorSeguroFacade.listar(sql);
		}
		if (listaValoresSeguro == null) {
			listaValoresSeguro = new ArrayList<Valoresseguro>();
		}
	}
	
	public void calcularDataTermino() {
		orcamento.getSeguroviagem().setValoresseguro(orcamento.getValorSeguro());
		if ((orcamento.getSeguroviagem().getDataInicio() != null) && (orcamento.getSeguroviagem().getNumeroSemanas() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoedaPais(
					Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getDatacambio()),
					orcamento.getValorSeguro().getMoedas().getIdmoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			if (cambioSeguro != null) {
				if (orcamento.getSeguroviagem().getValoresseguro().getCobranca().equalsIgnoreCase("semana")) {
					orcamento.getSeguroviagem().setDataTermino(Formatacao.calcularDataFinalPorDias(orcamento.getSeguroviagem().getDataInicio(),
							orcamento.getSeguroviagem().getNumeroSemanas()));
				} else if (orcamento.getSeguroviagem().getValoresseguro().getCobranca().equalsIgnoreCase("diaria")) {
					orcamento.getSeguroviagem().setDataTermino(Formatacao.calcularDataFinalPorDias(orcamento.getSeguroviagem().getDataInicio(),
							orcamento.getSeguroviagem().getNumeroSemanas()));
				}
				float valornacional = orcamento.getSeguroviagem().getValoresseguro().getValorgross() * cambioSeguro.getValor();
				orcamento.getSeguroviagem().setValorSeguro(valornacional * orcamento.getSeguroviagem().getNumeroSemanas());
				somarValorTotal();
				convertendoValoresSeguro();
				if (orcamento.getSeguroviagem().getValorSeguro() != null) {
					orcamento.getSeguroviagem().setValorMoedaEstrangeira(
							orcamento.getSeguroviagem().getValorSeguro() / cambioSeguro.getValor());
				}
			}
		}
	}
	public void calcularDataTerminoData() {
		orcamento.getSeguroviagem().setValoresseguro(orcamento.getValorSeguro());
		if ((orcamento.getSeguroviagem().getDataInicio() != null) && (orcamento.getSeguroviagem().getDataTermino() != null)) {
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoedaPais(
					Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getDatacambio()),
					orcamento.getValorSeguro().getMoedas().getIdmoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			if (cambioSeguro != null) {
				orcamento.getSeguroviagem().setNumeroSemanas(Formatacao.subtrairDatas(orcamento.getSeguroviagem().getDataInicio(), orcamento.getSeguroviagem().getDataTermino()) + 1);
				if (orcamento.getSeguroviagem().getValoresseguro().getCobranca().equalsIgnoreCase("semana")) {
					orcamento.getSeguroviagem().setDataTermino(Formatacao.calcularDataFinalPorDias(orcamento.getSeguroviagem().getDataInicio(),
							orcamento.getSeguroviagem().getNumeroSemanas()));
				} else if (orcamento.getSeguroviagem().getValoresseguro().getCobranca().equalsIgnoreCase("diaria")) {
					orcamento.getSeguroviagem().setDataTermino(Formatacao.calcularDataFinalPorDias(orcamento.getSeguroviagem().getDataInicio(),
							orcamento.getSeguroviagem().getNumeroSemanas()));
				}
				float valornacional = orcamento.getSeguroviagem().getValoresseguro().getValorgross() * cambioSeguro.getValor();
				orcamento.getSeguroviagem().setValorSeguro(valornacional * orcamento.getSeguroviagem().getNumeroSemanas());
				somarValorTotal();
				convertendoValoresSeguro();
				if (orcamento.getSeguroviagem().getValorSeguro() != null) {
					orcamento.getSeguroviagem().setValorMoedaEstrangeira(
							orcamento.getSeguroviagem().getValorSeguro() / cambioSeguro.getValor());
				}
			}
		}
	}
	  
	public void selecionarSeguro() {
		if (orcamento.isPossuiSeguroViagem()) {
			orcamento.setSeguroviagem(new Seguroviagem());
			try {
				orcamento.getSeguroviagem().setDataInicio(
						Formatacao.SomarDiasDatas(orcamento.getDatainicial(), -7));
				orcamento.getSeguroviagem().setDataTermino(
						Formatacao.SomarDiasDatas(orcamento.getDatatermino(), 7));
			} catch (Exception e) {
				  
			}
			orcamento.getSeguroviagem().setNumeroSemanas(
					Formatacao.subtrairDatas(orcamento.getSeguroviagem().getDataInicio(), orcamento.getSeguroviagem().getDataTermino()));
			orcamento.getSeguroviagem().setNumeroSemanas(orcamento.getSeguroviagem().getNumeroSemanas() + 1);
		} else {
			orcamento.setSeguroviagem(new Seguroviagem());
			somarValorTotal();
		}
		habilitarSeguro();
	}
	
	public void convertendoValoresSeguro() {
		CambioFacade cambioFacade = new CambioFacade();
		Cambio cambioSeguro = null;
		cambioSeguro = cambioFacade.consultarCambioMoedaPais(
				Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getDatacambio()),
				orcamento.getValorSeguro().getMoedas().getIdmoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		float valorCambio;
		if (cambioSeguro != null) {
			valorCambio = cambioSeguro.getValor();
		}else {
			valorCambio = 0.0f;
		}
		orcamento.setValorTotalSeguroDolar(orcamento.getSeguroviagem().getNumeroSemanas() * orcamento.getSeguroviagem().getValoresseguro().getValorgross());
		orcamento.setValorUtilitarioRS(orcamento.getSeguroviagem().getValoresseguro().getValorgross()
				* valorCambio);
	}
	
	public String adicionarProdutosExtra() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		session.setAttribute("valorCambio", orcamento.getValorcambio());
		RequestContext.getCurrentInstance().openDialog("produtosExtra", options, null);
		return "";
	}
	
	public void retornoDialogoNovo(SelectEvent event) {
		ProdutosExtrasBean feb = (ProdutosExtrasBean) event.getObject();
		feb.setValorOrigianlString(orcamento.getCambio().getMoedas().getSigla() + " "
				+ Formatacao.formatarFloatString(feb.getValorOrigianl()));
		feb.setValorOriginalRSString(moedaNacional + " " + Formatacao.formatarFloatString(feb.getValorOriginalRS()));
		orcamento.getListaOutrosProdutos().add(feb);
		somarValorTotal();
	}
	
	public void excluirProdutoExtra(ProdutosExtrasBean produtosExtrasBean) {  
		orcamento.getListaOutrosProdutos().remove(produtosExtrasBean);
		somarValorTotal();
	}
	
	public String retornarValorString(Float valor, String sigla) {
		String svalor = "";
		if (valor != null) {
			if (sigla == null || sigla.equalsIgnoreCase("")) {
				sigla = "R$";
			}
			svalor = sigla + " " + Formatacao.formatarFloatString(valor);
		}
		return svalor;
	}
	
	public void recalcularValores(){
		orcamento.setValorRS(orcamento.getVoluntariadoprojetovalor().getValor() * orcamento.getValorcambio()); 
		orcamento.setValorSemanaAdcRS(orcamento.getValorSemanaAdc() * orcamento.getValorcambio());
		if(orcamento.getListaOutrosProdutos()!=null && orcamento.getListaOutrosProdutos().size()>0){
			for (int i = 0; i < orcamento.getListaOutrosProdutos().size(); i++) { 
				orcamento.getListaOutrosProdutos().get(i).setValorOriginalRS(
						orcamento.getListaOutrosProdutos().get(i).getValorOrigianl() * orcamento.getValorcambio()); 
			}
		}
		if(orcamento.getListaDesconto()!=null && orcamento.getListaDesconto().size()>0){
			for (int i = 0; i < orcamento.getListaDesconto().size(); i++) { 
				orcamento.getListaDesconto().get(i).setValormoedanacional(
						orcamento.getListaDesconto().get(i).getValormoedaestrangeira()*orcamento.getValorcambio()); 
			}
		}
		if (orcamento.getValorcambio() >= 1) {
			orcamento.setValortaxatm(orcamento.getValortaxatmRS()/orcamento.getValorcambio());
		}else {
			orcamento.setValortaxatm(0.0f);
		}
		somarValorTotal();
	}
	
	public void valorDescontoEstrageiro() {
		for (int i = 0; i < orcamento.getListaDesconto().size(); i++) {
			if (orcamento.getListaDesconto().get(i).getValormoedanacional() > 0) {
				orcamento.getListaDesconto().get(i).setValormoedaestrangeira(
						orcamento.getListaDesconto().get(i).getValormoedanacional() / orcamento.getValorcambio());
			}
		}
	}

	public void valorDescontoReal() {
		for (int i = 0; i < orcamento.getListaDesconto().size(); i++) {
			if (orcamento.getListaDesconto().get(i).getValormoedaestrangeira() > 0) {
				orcamento.getListaDesconto().get(i).setValormoedanacional(
						orcamento.getListaDesconto().get(i).getValormoedaestrangeira() * orcamento.getValorcambio());
			}
		}
	}
	
	public String voltar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("cliente", orcamento.getCliente());
		return "filtrarVoluntariadoProjetoOrcamento";
	}
	
	public String formapagamento(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		orcamento.setTotal(totalmoedaestrangeira);
		orcamento.setTotalRS(totalmoedanacional);
		session.setAttribute("orcamento", orcamento);
		if(orcamento.getOrcamentovoluntariadoformapagamento()==null){
			orcamento.setOrcamentovoluntariadoformapagamento(new Orcamentovoluntariadoformapagamento());
		}
		return "formaPagamentoOrcamentoVoluntariado";
	}
	 
}
