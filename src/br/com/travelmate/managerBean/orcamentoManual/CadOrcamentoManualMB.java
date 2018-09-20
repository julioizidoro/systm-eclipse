package br.com.travelmate.managerBean.orcamentoManual;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.bean.LeadSituacaoBean;
import br.com.travelmate.bean.NumeroParcelasBean;
import br.com.travelmate.bean.ProdutoOrcamentoCursoBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadHistoricoDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.OcClienteFacade;
import br.com.travelmate.facade.OrcamentoCursoFacade; 
import br.com.travelmate.facade.OrcamentoManualSeguroFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadhistorico;
import br.com.travelmate.model.Modeloorcamentocurso;
import br.com.travelmate.model.Modeloorcamentocursoforma;
import br.com.travelmate.model.Modeloprodutoorcamentocurso;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Occliente;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Orcamentocursoformapagamento;
import br.com.travelmate.model.Orcamentomanualseguro;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtoorcamentocurso;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadOrcamentoManualMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadSituacaoDao leadSituacaoDao;
	@Inject
	private LeadHistoricoDao leadHistoricoDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Orcamentocurso orcamentocurso;
	private Cliente cliente;
	private List<Publicidade> listaPublicidades;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private Fornecedorcidade fornecedorCidade; 
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private List<Filtroorcamentoproduto> listaProdutosOrcamento;
	private Produtosorcamento produtosorcamento;
	private String camposAcomodacao = "true";
	private Moedas moeda;
	private List<Moedas> listaMoedas;
	private Cambio cambio;
	private float valorCambio = 0;
	private float valorMoedaEstrangeira = 0;
	private float valorMoedaReal = 0;
	private float totalMoedaEstrangeira = 0;
	private float totalMoedaReal = 0;
	private float valorTotal = 0;
	private Orcamentocursoformapagamento orcamentoCursoFormaPagamento;
	private String habilitarFormaPagamento2 = "true";
	private String habilitaFormaPagamento03 = "true";
	private String habilitaFormaPagamento04 = "true";
	private Date dataCambio;
	private List<ProdutoOrcamentoCursoBean> listaProdutoOrcamentoBean;
	private List<ProdutoOrcamentoCursoBean> listaProdutoOrcamentoApagarBean;
	private boolean digitosFoneResidencial;
	private boolean digitosFoneCelular;
	private Orcamentomanualseguro seguroViagem;
	private Fornecedorcidade fornecedorSeguro;
	private List<Fornecedorcidade> listaFornecedorCidadeSeguro;
	private List<Valoresseguro> listaValoresSeguro; 
	private float valorUtilitarioRS = 0.0f;
	private float valorTotalSeguroDola = 0.0f;
	private boolean painelcliente=true;
	private boolean painelOcCliente=false;
	private Occliente occliente;
	private String tipo;
	private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;
	private List<Cidadepaisproduto> listaCidade;
	private Lead lead;
	private String funcao;
	private boolean segurocancelamento=false;
	private List<NumeroParcelasBean> listaNumeroParcelas;
	private String moedaNacional;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		orcamentocurso = (Orcamentocurso) session.getAttribute("orcamentoManual");
		cliente = (Cliente) session.getAttribute("cliente");   
		tipo = (String) session.getAttribute("tipoorcamento");
		funcao = (String) session.getAttribute("funcao");
		lead = (Lead) session.getAttribute("lead");
		session.removeAttribute("tipoorcamento");
		session.removeAttribute("cliente");
		session.removeAttribute("orcamentoManual");
		gerarListaProdutos();
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		listaNumeroParcelas = new ArrayList<NumeroParcelasBean>();
		int idProduto = 0;
		if(tipo!=null && tipo.equalsIgnoreCase("Voluntariado")){
			idProduto = aplicacaoMB.getParametrosprodutos().getVoluntariado();
			listaPais = paisProdutoFacade.listar(idProduto);
		}else{
			idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
			listaPais = paisProdutoFacade.listar(idProduto);
		}
		gerarListaCursos();
		gerarListaPublicidade();
		carregarComboMoedas();
		iniciarListaFornecedorSeguro();   
		if(cliente==null){
			cliente = new Cliente(); 
		}else{
			cliente.setClienteLead(true);
		}
		if (orcamentocurso == null) {
			iniciarNovoOrcamento();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		} else {
			iniciarAlteracaoOrcamento();
		}
		digitosFoneCelular = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), cliente.getFoneCelular());
		digitosFoneResidencial = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), cliente.getFoneResidencial());
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	}

	public Orcamentocurso getOrcamentocurso() {
		return orcamentocurso;
	}

	public void setOrcamentocurso(Orcamentocurso orcamentocurso) {
		this.orcamentocurso = orcamentocurso;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Publicidade> getListaPublicidades() {
		return listaPublicidades;
	}

	public void setListaPublicidades(List<Publicidade> listaPublicidades) {
		this.listaPublicidades = listaPublicidades;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
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

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public String getCamposAcomodacao() {
		return camposAcomodacao;
	}

	public void setCamposAcomodacao(String camposAcomodacao) {
		this.camposAcomodacao = camposAcomodacao;
	}

	public Moedas getMoeda() {
		return moeda;
	}

	public void setMoeda(Moedas moeda) {
		this.moeda = moeda;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public float getValorMoedaEstrangeira() {
		return valorMoedaEstrangeira;
	}

	public void setValorMoedaEstrangeira(float valorMoedaEstrangeira) {
		this.valorMoedaEstrangeira = valorMoedaEstrangeira;
	}

	public float getValorMoedaReal() {
		return valorMoedaReal;
	}

	public void setValorMoedaReal(float valorMoedaReal) {
		this.valorMoedaReal = valorMoedaReal;
	}

	public float getTotalMoedaEstrangeira() {
		return totalMoedaEstrangeira;
	}

	public void setTotalMoedaEstrangeira(float totalMoedaEstrangeira) {
		this.totalMoedaEstrangeira = totalMoedaEstrangeira;
	}

	public float getTotalMoedaReal() {
		return totalMoedaReal;
	}

	public void setTotalMoedaReal(float totalMoedaReal) {
		this.totalMoedaReal = totalMoedaReal;
	}

	public float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Orcamentocursoformapagamento getOrcamentoCursoFormaPagamento() {
		return orcamentoCursoFormaPagamento;
	}

	public void setOrcamentoCursoFormaPagamento(Orcamentocursoformapagamento orcamentoCursoFormaPagamento) {
		this.orcamentoCursoFormaPagamento = orcamentoCursoFormaPagamento;
	}

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
	}

	public List<ProdutoOrcamentoCursoBean> getListaProdutoOrcamentoBean() {
		return listaProdutoOrcamentoBean;
	}

	public void setListaProdutoOrcamentoBean(List<ProdutoOrcamentoCursoBean> listaProdutoOrcamentoBean) {
		this.listaProdutoOrcamentoBean = listaProdutoOrcamentoBean;
	}

	public List<ProdutoOrcamentoCursoBean> getListaProdutoOrcamentoApagarBean() {
		return listaProdutoOrcamentoApagarBean;
	}

	public void setListaProdutoOrcamentoApagarBean(List<ProdutoOrcamentoCursoBean> listaProdutoOrcamentoApagarBean) {
		this.listaProdutoOrcamentoApagarBean = listaProdutoOrcamentoApagarBean;
	}

	public String getHabilitarFormaPagamento2() {
		return habilitarFormaPagamento2;
	}

	public void setHabilitarFormaPagamento2(String habilitarFormaPagamento2) {
		this.habilitarFormaPagamento2 = habilitarFormaPagamento2;
	}

	public String getHabilitaFormaPagamento03() {
		return habilitaFormaPagamento03;
	}

	public void setHabilitaFormaPagamento03(String habilitaFormaPagamento03) {
		this.habilitaFormaPagamento03 = habilitaFormaPagamento03;
	}

	public String getHabilitaFormaPagamento04() {
		return habilitaFormaPagamento04;
	}

	public void setHabilitaFormaPagamento04(String habilitaFormaPagamento04) {
		this.habilitaFormaPagamento04 = habilitaFormaPagamento04;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isDigitosFoneResidencial() {
		return digitosFoneResidencial;
	}

	public void setDigitosFoneResidencial(boolean digitosFoneResidencial) {
		this.digitosFoneResidencial = digitosFoneResidencial;
	}

	public boolean isDigitosFoneCelular() {
		return digitosFoneCelular;
	}

	public void setDigitosFoneCelular(boolean digitosFoneCelular) {
		this.digitosFoneCelular = digitosFoneCelular;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Orcamentomanualseguro getSeguroViagem() {
		return seguroViagem;
	}

	public void setSeguroViagem(Orcamentomanualseguro seguroViagem) {
		this.seguroViagem = seguroViagem;
	}

	public Fornecedorcidade getFornecedorSeguro() {
		return fornecedorSeguro;
	}

	public void setFornecedorSeguro(Fornecedorcidade fornecedorSeguro) {
		this.fornecedorSeguro = fornecedorSeguro;
	}

	public List<Fornecedorcidade> getListaFornecedorCidadeSeguro() {
		return listaFornecedorCidadeSeguro;
	}

	public void setListaFornecedorCidadeSeguro(List<Fornecedorcidade> listaFornecedorCidadeSeguro) {
		this.listaFornecedorCidadeSeguro = listaFornecedorCidadeSeguro;
	}

	public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	} 

	public float getValorUtilitarioRS() {
		return valorUtilitarioRS;
	}

	public void setValorUtilitarioRS(float valorUtilitarioRS) {
		this.valorUtilitarioRS = valorUtilitarioRS;
	}

	public float getValorTotalSeguroDola() {
		return valorTotalSeguroDola;
	}

	public void setValorTotalSeguroDola(float valorTotalSeguroDola) {
		this.valorTotalSeguroDola = valorTotalSeguroDola;
	}

	public boolean isPainelcliente() {
		return painelcliente;
	}

	public void setPainelcliente(boolean painelcliente) {
		this.painelcliente = painelcliente;
	}

	public boolean isPainelOcCliente() {
		return painelOcCliente;
	}

	public void setPainelOcCliente(boolean painelOcCliente) {
		this.painelOcCliente = painelOcCliente;
	}

	public Occliente getOccliente() {
		return occliente;
	}

	public void setOccliente(Occliente occliente) {
		this.occliente = occliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public boolean isSegurocancelamento() {
		return segurocancelamento;
	}

	public void setSegurocancelamento(boolean segurocancelamento) {
		this.segurocancelamento = segurocancelamento;
	}

	public List<NumeroParcelasBean> getListaNumeroParcelas() {
		return listaNumeroParcelas;
	}

	public void setListaNumeroParcelas(List<NumeroParcelasBean> listaNumeroParcelas) {
		this.listaNumeroParcelas = listaNumeroParcelas;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade();
		try {
			listaPublicidades = publicidadeFacade.listar();
			if (listaPublicidades == null) {
				listaPublicidades = new ArrayList<Publicidade>();
			}
		} catch (SQLException ex) {
			Logger.getLogger(CadOrcamentoManualMB.class.getName()).log(Level.SEVERE, null, ex);
			Mensagem.lancarMensagemErro("Erro Listar Publicidade", "ERRO");
		}
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
		digitosFoneCelular = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), cliente.getFoneCelular());
		digitosFoneResidencial = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), cliente.getFoneResidencial());
	}

	public void gerarListaCursos() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getCursos()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade() + " and f.ativo=1 order by f.fornecedor.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}

	public void calcularDataTerminoCurso() {
		if ((orcamentocurso.getDataInicio() != null) && (orcamentocurso.getNumeroSemanas() != null)) {
			if (orcamentocurso.getNumeroSemanas() > 0) {
				Date data = Formatacao.calcularDataFinal(orcamentocurso.getDataInicio(),
						orcamentocurso.getNumeroSemanas());
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(CadOrcamentoManualMB.class.getName()).log(Level.SEVERE, null, ex);
				}
				orcamentocurso.setDataTermino(data);
				gerarListaNuneroParcelas(orcamentocurso.getDataInicio());
			}
		}
	}

	public void carregarCamposAcomodacao() {
		if (orcamentocurso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
			orcamentocurso.setTipoQuarto("Sem quarto");
			orcamentocurso.setRefeicoes("Sem Refeição");
			orcamentocurso.setNumeroSemanas(null);
		} else {
			camposAcomodacao = "false";
		}
	}

	public void calcularValorTotalOrcamento() {
		if (aplicacaoMB.getParametrosprodutos().isRemessaativa()) {
			calcularImpostoRemessa();
		}
		boolean seguro = true;
		totalMoedaEstrangeira = 0.0f;
		totalMoedaReal = 0.0f;
		valorTotal = 0.0f;
		if (listaProdutoOrcamentoBean != null) {
			float valorMoedaReal = 0.0f;
			float valorMoedaEstrangeira = 0.0f;
			for (int i = 0; i < listaProdutoOrcamentoBean.size(); i++) {
				int idProdutoOrcamento = listaProdutoOrcamentoBean.get(i).getIdProdutoOrcamento();
				int seguroViagem = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
				if (listaProdutoOrcamentoBean.get(i).isSomarvalortotal()) {
					int descontoLoja = aplicacaoMB.getParametrosprodutos().getDescontoloja();
					int descontoMatriz = aplicacaoMB.getParametrosprodutos().getDescontomatriz();
					int promocaoEscola = aplicacaoMB.getParametrosprodutos().getPromocaoescola();
					int promocaoEscolaAcomodacao = aplicacaoMB.getParametrosprodutos().getPromocaoescolaacomodacao();
					if (idProdutoOrcamento == descontoLoja) {
						valorMoedaReal = listaProdutoOrcamentoBean.get(i).getValorMoedaReal() * -1;
						valorMoedaEstrangeira = listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira() * -1;
					} else if (idProdutoOrcamento == descontoMatriz) {
						valorMoedaReal = listaProdutoOrcamentoBean.get(i).getValorMoedaReal() * -1;
						valorMoedaEstrangeira = listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira() * -1;
					} else if (idProdutoOrcamento == promocaoEscola) {
						valorMoedaReal = listaProdutoOrcamentoBean.get(i).getValorMoedaReal() * -1;
						valorMoedaEstrangeira = listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira() * -1;
					} else if (idProdutoOrcamento == promocaoEscolaAcomodacao) {
						valorMoedaReal = listaProdutoOrcamentoBean.get(i).getValorMoedaReal() * -1;
						valorMoedaEstrangeira = listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira() * -1;
					}else if(seguroViagem == idProdutoOrcamento) {
						if(this.seguroViagem != null && this.seguroViagem.isSomarvalortotal() && this.seguroViagem.getValor() > 0) {
							seguro = false;
							listaProdutoOrcamentoBean.get(i).setSomarvalortotal(true);
						}
					} else {
						valorMoedaReal = listaProdutoOrcamentoBean.get(i).getValorMoedaReal();
						valorMoedaEstrangeira = listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira();
					}
					valorTotal = valorTotal + valorMoedaReal;
					totalMoedaEstrangeira = totalMoedaEstrangeira + valorMoedaEstrangeira;
					totalMoedaReal = totalMoedaReal + valorMoedaReal;
				}
			}
			if (seguro) {
				if (seguroViagem.isSomarvalortotal() && seguroViagem.getValor() > 0) {
					valorTotal = valorTotal + seguroViagem.getValor();
					CambioFacade cambioFacade = new CambioFacade();
					Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
							seguroViagem.getValoresseguro().getMoedas().getIdmoedas());
					totalMoedaEstrangeira = totalMoedaEstrangeira + (seguroViagem.getValor() / cambioSeguro.getValor());
					totalMoedaReal = totalMoedaReal + seguroViagem.getValor();
				}
			}
			orcamentocurso.setValor(valorTotal);
			orcamentocurso.setTotalmoedaestrangeira(valorTotal / valorCambio);
			orcamentoCursoFormaPagamento.setAVista(orcamentocurso.getValor());
			try {
				calcularParcelamento();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade.listar(aplicacaoMB.getParametrosprodutos().getVoluntariado());
		} catch (Exception e) {
			e.printStackTrace();
		}
		float valorremessa = 0.0f;
		if (listaProdutoRemessa != null) {
			for (int i = 0; i < listaProdutoOrcamentoBean.size(); i++) {
				int idProduto = listaProdutoOrcamentoBean.get(i).getIdProdutoOrcamento();
				for (int n = 0; n < listaProdutoRemessa.size(); n++) {
					int idRemessa = listaProdutoRemessa.get(n).getProdutosorcamento().getIdprodutosOrcamento();
					if (idProduto == idRemessa) {
						valorremessa = valorremessa + listaProdutoOrcamentoBean.get(i).getValorMoedaReal();
					}
				}
			}
		}
		if (valorremessa > 0) {
			boolean achou = false;
			valorremessa = valorremessa * (aplicacaoMB.getParametrosprodutos().getPercentualremessa() / 100);
			int idRemessa = aplicacaoMB.getParametrosprodutos().getProdutoremessa();
			for (int i = 0; i < listaProdutoOrcamentoBean.size(); i++) {
				int idProduto = listaProdutoOrcamentoBean.get(i).getIdProdutoOrcamento();
				if (idRemessa == idProduto) {
					listaProdutoOrcamentoBean.get(i).setValorMoedaReal(valorremessa);
					listaProdutoOrcamentoBean.get(i).setValorMoedaEstrangeira(valorremessa / cambio.getValor());
					achou = true;
					i = 10000;
				}
			}
			if (!achou) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtosorcamento = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getProdutoremessa());
				ProdutoOrcamentoCursoBean pob = new ProdutoOrcamentoCursoBean();
				pob.setIdProdutoOrcamentoCurso(0);
				pob.setDescricaoProdutoOrcamento(produtosorcamento.getDescricao());
				pob.setIdProdutoOrcamento(produtosorcamento.getIdprodutosOrcamento());
				pob.setValorMoedaEstrangeira(valorremessa / cambio.getValor());
				pob.setValorMoedaReal(valorremessa);
				pob.setApagar(false);
				pob.setNovo(true);
				listaProdutoOrcamentoBean.add(pob);
			}
		}
	}

	public String editarcambio(Float valorCambio) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("valorCambio", valorCambio);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 230);
		RequestContext.getCurrentInstance().openDialog("editarCambioOrcamento", options, null);
		return "";
	}

	public void retornoDialogEditarCambio(SelectEvent event) {
		valorCambio = (float) event.getObject();
		atualizarValoresProduto();
	}

	public String tituloMoedaEstrangeira() {
		if (cambio != null) {
			if (cambio.getMoedas() != null) {
				return "Valor " + cambio.getMoedas().getSigla();
			} else {
				return "Moeda Estrangeira ";
			}
		} else
			return "Moeda Estrangeira";
	}

	public void adicionarProdutos() {
		if (moeda != null && moeda.getIdmoedas() != null) {
			if (valorCambio > 0) {
				float valorEstrangeira = 0.0f;
				float valorReal = 0.0f;
				if (valorMoedaEstrangeira > 0) {
					valorEstrangeira = valorMoedaEstrangeira;
				} else {
					if (valorMoedaReal > 0) {
						valorReal = valorMoedaReal;
					}
				}
				if (produtosorcamento.getIdprodutosOrcamento() != null) {
					ProdutoOrcamentoCursoBean pob = new ProdutoOrcamentoCursoBean();
					pob.setIdProdutoOrcamentoCurso(0);
					pob.setDescricaoProdutoOrcamento(produtosorcamento.getDescricao());
					pob.setIdProdutoOrcamento(produtosorcamento.getIdprodutosOrcamento());
					if ((valorEstrangeira != 0) && (valorCambio > 0)) {
						valorReal = valorEstrangeira * valorCambio;
					} else if ((valorReal != 0) && (valorCambio > 0)) {
						valorEstrangeira = valorReal / valorCambio;
					}
					if (produtosorcamento.getValormaximo()==0) {
						pob . setValorMoedaEstrangeira (valorEstrangeira);
						pob . setValorMoedaReal (valorReal);

						pob.setApagar(false);
						pob.setNovo(true);
						pob.setSomarvalortotal(true);
						listaProdutoOrcamentoBean.add(pob);
						calcularValorTotalOrcamento();
						this.valorMoedaEstrangeira = 0.0f;
						this.valorMoedaReal = 0.0f;
					}else if (produtosorcamento.getValormaximo()>=valorReal){
						pob . setValorMoedaEstrangeira (valorEstrangeira);
						pob . setValorMoedaReal (valorReal);

						pob.setApagar(false);
						pob.setNovo(true);
						pob.setSomarvalortotal(true);
						listaProdutoOrcamentoBean.add(pob);
						calcularValorTotalOrcamento();
						this.valorMoedaEstrangeira = 0.0f;
						this.valorMoedaReal = 0.0f;
					}else {
						FacesContext fc = FacesContext.getCurrentInstance();
				        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				        Map<String, Object> options = new HashMap<String, Object>();
						options.put("contentWidth", 230);
				        session.setAttribute("valorOriginal", 0f);
				        session.setAttribute("novoValor", 0f);
						RequestContext.getCurrentInstance().openDialog("validarTrocaCambioPIN", options, null);
						
					}  
					calcularValorTotalOrcamento();
				}
			}
		} else {
			Mensagem.lancarMensagemErro("", "Cambio não selecionado");
		}
	}
	
	public void retornoDialogProdutoOrcamento() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String adicionar = (String) session.getAttribute("adicionar");
		session.removeAttribute("adicionar");
		if (adicionar != null) {
			if (adicionar.equalsIgnoreCase("sim")) {

				ProdutoOrcamentoCursoBean pob = new ProdutoOrcamentoCursoBean();
				pob.setIdProdutoOrcamentoCurso(0);
				pob.setDescricaoProdutoOrcamento(produtosorcamento.getDescricao());
				pob.setIdProdutoOrcamento(produtosorcamento.getIdprodutosOrcamento());
				float valorEstrangeira = 0.0f;
				float valorReal = 0.0f;
				if (valorMoedaEstrangeira > 0) {
					valorEstrangeira = valorMoedaEstrangeira;
				} else {
					if (valorMoedaReal > 0) {
						valorReal = valorMoedaReal;
					}
				}
				if ((valorEstrangeira != 0) && (valorCambio > 0)) {
					valorReal = valorEstrangeira * valorCambio;
				} else if ((valorReal != 0) && (valorCambio > 0)) {
					valorEstrangeira = valorReal / valorCambio;
				}

				pob.setValorMoedaEstrangeira(valorEstrangeira);
				pob.setValorMoedaReal(valorReal);

				pob.setApagar(false);
				pob.setNovo(true);
				pob.setSomarvalortotal(true);
				listaProdutoOrcamentoBean.add(pob);

				calcularValorTotalOrcamento();
				produtosorcamento = null;
				this.valorMoedaEstrangeira = 0.0f;
				this.valorMoedaReal = 0.0f;
			}
		}
	}

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (listaProdutoOrcamentoBean.get(ilinha).getIdProdutoOrcamento() == tx) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Taxa TM não pode ser Excluída.", ""));
		} else {
			if (ilinha >= 0) {
				listaProdutoOrcamentoBean.get(ilinha).setApagar(true);
				listaProdutoOrcamentoApagarBean.add(listaProdutoOrcamentoBean.get(ilinha));
				listaProdutoOrcamentoBean.remove(ilinha);
				calcularValorTotalOrcamento();
			}
		}
	}

	public void verificarFormaPgamento02() throws SQLException {
		if (orcamentoCursoFormaPagamento.isSelecionado2()) {
			habilitarFormaPagamento2 = "false";
			orcamentoCursoFormaPagamento.setNumeroParcelas02(1);
			orcamentoCursoFormaPagamento.setPercentualEntrada2(30.0);
			orcamentoCursoFormaPagamento.setPercentualSaldo2(70.0);
			orcamentoCursoFormaPagamento.setValorEntrada2(0.0f);
			orcamentoCursoFormaPagamento.setValorParcela02(0.0f);
			orcamentoCursoFormaPagamento.setValorSaldo2(0.0f);
			calcularParcelamento();
		} else {
			habilitarFormaPagamento2 = "tre";
			orcamentoCursoFormaPagamento.setPercentualEntrada2(0.0);
			orcamentoCursoFormaPagamento.setValorEntrada2(0.0f);
			orcamentoCursoFormaPagamento.setNumeroParcelas02(1);
			orcamentoCursoFormaPagamento.setPercentualSaldo2(0.0);
			orcamentoCursoFormaPagamento.setValorParcela02(0.0f);
			orcamentoCursoFormaPagamento.setValorSaldo2(0.0f);
		}
	}

	public void calcularParcelamento() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;

		if (this.orcamentoCursoFormaPagamento.isSelecionado2()) {
			if (orcamentoCursoFormaPagamento.getPercentualEntrada2() > 0) {
				valorEntrada = orcamentoCursoFormaPagamento.getAVista()
						* (orcamentoCursoFormaPagamento.getPercentualEntrada2() / 100);
				saldo = 100 - orcamentoCursoFormaPagamento.getPercentualEntrada2();
				valorSaldo = orcamentoCursoFormaPagamento.getAVista() - valorEntrada;
				orcamentoCursoFormaPagamento.setValorEntrada2(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo2(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo2(saldo);
			}else if (orcamentoCursoFormaPagamento.getValorEntrada2()==0){
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = orcamentoCursoFormaPagamento.getaVista().doubleValue();
				orcamentoCursoFormaPagamento.setValorEntrada2(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo2(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo2(saldo);
			}
			valorSaldo = 0.0;
			if (orcamentoCursoFormaPagamento.getNumeroParcelas02() > 0) {
				valorSaldo = orcamentoCursoFormaPagamento.getValorSaldo2().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / orcamentoCursoFormaPagamento.getNumeroParcelas02();
					orcamentoCursoFormaPagamento.setValorParcela02(valorSaldo.floatValue());
				}
			}
		}

		// Opção 03

		if (this.orcamentoCursoFormaPagamento.isSelecionado3()) {

			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (orcamentoCursoFormaPagamento.getPercentualEntrada3() > 0) {
				valorEntrada = orcamentocurso.getValor() * (orcamentoCursoFormaPagamento.getPercentualEntrada3() / 100);
				saldo = 100 - orcamentoCursoFormaPagamento.getPercentualEntrada3();
				valorSaldo = orcamentocurso.getValor() - valorEntrada;
				orcamentoCursoFormaPagamento.setValorEntrada3(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo3(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo3(saldo.doubleValue());
			}else if (orcamentoCursoFormaPagamento.getValorEntrada3()==0){
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = orcamentoCursoFormaPagamento.getaVista().doubleValue();
				orcamentoCursoFormaPagamento.setValorEntrada3(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo3(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo3(saldo);
			}
			valorSaldo = 0.0;
			if (orcamentoCursoFormaPagamento.getNumeroParcelas03() > 0) {
				if (orcamentoCursoFormaPagamento.getValorSaldo3() > 0) {
					valorSaldo = orcamentoCursoFormaPagamento.getValorSaldo3().doubleValue();
					if (valorSaldo > 0) {
						if (orcamentoCursoFormaPagamento.getNumeroParcelas03() == 1) {
							orcamentoCursoFormaPagamento.setValorParcela03(valorSaldo.floatValue());
						} else {
							CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
							Coeficientejuros cf = coneficienteJurosFacade
									.consultar(orcamentoCursoFormaPagamento.getNumeroParcelas03(), "Juros Cliente");
							valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
							orcamentoCursoFormaPagamento.setValorParcela03(valorSaldo.floatValue());
						}
					}
				}
			}
		}

		// opção 04
		if (this.orcamentoCursoFormaPagamento.isSelecionado4()) {
			if (orcamentoCursoFormaPagamento.getPercentualEntrada4() > 0) {
				valorEntrada = orcamentocurso.getValor() * (orcamentoCursoFormaPagamento.getPercentualEntrada4() / 100);
				saldo = 100 - orcamentoCursoFormaPagamento.getPercentualEntrada4();
				valorSaldo = orcamentocurso.getValor() - valorEntrada;
				orcamentoCursoFormaPagamento.setValorEntrada4(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo4(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo4(saldo.doubleValue());
			}else if (orcamentoCursoFormaPagamento.getValorEntrada4()==0){
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = orcamentoCursoFormaPagamento.getaVista().doubleValue();
				orcamentoCursoFormaPagamento.setValorEntrada4(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo4(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo4(saldo);
			}
			valorSaldo = 0.0;
			if (orcamentoCursoFormaPagamento.getNumeroParcelasFinanciamento() > 0) {
					if(orcamentoCursoFormaPagamento.getValorSaldo4()>0){
	     	            CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
	     	            Coeficientejuros  cf = coneficienteJurosFacade.consultar(orcamentoCursoFormaPagamento.getNumeroParcelasFinanciamento(), "Juros Cliente");
	     	            Double valor = orcamentoCursoFormaPagamento.getValorSaldo4().doubleValue() * cf.getCoeficiente();
	     	           orcamentoCursoFormaPagamento.setFinaciamento(valor.floatValue());
	             	}else{
	             		CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
	         	        Coeficientejuros  cf = coneficienteJurosFacade.consultar(orcamentoCursoFormaPagamento.getNumeroParcelasFinanciamento(), "Juros Cliente");
	         	        Double valor = orcamentocurso.getValor() * cf.getCoeficiente();
	         	       orcamentoCursoFormaPagamento.setFinaciamento(valor.floatValue());
	             	}
			}
		}
	}
	
	public void calcularParcelamentoEntradaValor() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;

		if (this.orcamentoCursoFormaPagamento.isSelecionado2()) {
			if (this.orcamentoCursoFormaPagamento.getValorEntrada2()>0){
				valorEntrada = orcamentoCursoFormaPagamento.getValorEntrada2().doubleValue();
				float percentualentrada = (orcamentoCursoFormaPagamento.getValorEntrada2() / orcamentoCursoFormaPagamento.getAVista()) * 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				orcamentoCursoFormaPagamento.setPercentualEntrada2(Formatacao.formatarStringDouble(valor));
				saldo = 100 - orcamentoCursoFormaPagamento.getPercentualEntrada2();
				valorSaldo = orcamentoCursoFormaPagamento.getAVista() - valorEntrada;
				orcamentoCursoFormaPagamento.setValorEntrada2(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo2(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo2(saldo);
			}else if (this.orcamentoCursoFormaPagamento.getPercentualEntrada2()==0){
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = orcamentoCursoFormaPagamento.getaVista().doubleValue();
				orcamentoCursoFormaPagamento.setValorEntrada2(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo2(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo2(saldo);
			}
			valorSaldo = 0.0;
			if (orcamentoCursoFormaPagamento.getNumeroParcelas02() > 0) {
				valorSaldo = orcamentoCursoFormaPagamento.getValorSaldo2().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / orcamentoCursoFormaPagamento.getNumeroParcelas02();
					orcamentoCursoFormaPagamento.setValorParcela02(valorSaldo.floatValue());
				}
			}
		}

		// Opção 03

		if (this.orcamentoCursoFormaPagamento.isSelecionado3()) {

			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (orcamentoCursoFormaPagamento.getValorEntrada3()>0){
				valorEntrada = orcamentoCursoFormaPagamento.getValorEntrada3().doubleValue();
				float percentualentrada = (orcamentoCursoFormaPagamento.getValorEntrada3() / orcamentoCursoFormaPagamento.getAVista()) * 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				orcamentoCursoFormaPagamento.setPercentualEntrada3(Formatacao.formatarStringDouble(valor));
				saldo = 100 - orcamentoCursoFormaPagamento.getPercentualEntrada3();
				valorSaldo = orcamentoCursoFormaPagamento.getAVista() - valorEntrada;
				orcamentoCursoFormaPagamento.setValorEntrada3(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo3(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo3(saldo);
			}else if (orcamentoCursoFormaPagamento.getPercentualEntrada3()==0){
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = orcamentoCursoFormaPagamento.getaVista().doubleValue();
				orcamentoCursoFormaPagamento.setValorEntrada3(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo3(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo3(saldo);
			}
			valorSaldo = 0.0;
			if (orcamentoCursoFormaPagamento.getNumeroParcelas03() > 0) {
				if (orcamentoCursoFormaPagamento.getValorSaldo3() > 0) {
					valorSaldo = orcamentoCursoFormaPagamento.getValorSaldo3().doubleValue();
					if (valorSaldo > 0) {
						if (orcamentoCursoFormaPagamento.getNumeroParcelas03() == 1) {
							orcamentoCursoFormaPagamento.setValorParcela03(valorSaldo.floatValue());
						} else {
							CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
							Coeficientejuros cf = coneficienteJurosFacade
									.consultar(orcamentoCursoFormaPagamento.getNumeroParcelas03(), "Juros Cliente");
							valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
							orcamentoCursoFormaPagamento.setValorParcela03(valorSaldo.floatValue());
						}
					}
				}
			}
		}

		// opção 04
		if (this.orcamentoCursoFormaPagamento.isSelecionado4()) {
			if (orcamentoCursoFormaPagamento.getValorEntrada4()>0){
				valorEntrada = orcamentoCursoFormaPagamento.getValorEntrada4().doubleValue();
				float percentualentrada = (orcamentoCursoFormaPagamento.getValorEntrada4() / orcamentoCursoFormaPagamento.getAVista()) * 100;
				String valor = Formatacao.formatarFloatString(percentualentrada);
				orcamentoCursoFormaPagamento.setPercentualEntrada4(Formatacao.formatarStringDouble(valor));
				saldo = 100 - orcamentoCursoFormaPagamento.getPercentualEntrada4();
				valorSaldo = orcamentoCursoFormaPagamento.getAVista() - valorEntrada;
				orcamentoCursoFormaPagamento.setValorEntrada4(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo4(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo4(saldo);
			}else if (orcamentoCursoFormaPagamento.getPercentualEntrada4()==0){
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = orcamentoCursoFormaPagamento.getaVista().doubleValue();
				orcamentoCursoFormaPagamento.setValorEntrada4(valorEntrada.floatValue());
				orcamentoCursoFormaPagamento.setValorSaldo4(valorSaldo.floatValue());
				orcamentoCursoFormaPagamento.setPercentualSaldo4(saldo);
			}
			valorSaldo = 0.0;
			if (orcamentoCursoFormaPagamento.getNumeroParcelasFinanciamento() > 0) {
					if(orcamentoCursoFormaPagamento.getValorSaldo4()>0){
	     	            CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
	     	            Coeficientejuros  cf = coneficienteJurosFacade.consultar(orcamentoCursoFormaPagamento.getNumeroParcelasFinanciamento(), "Juros Cliente");
	     	            Double valor = orcamentoCursoFormaPagamento.getValorSaldo4().doubleValue() * cf.getCoeficiente();
	     	           orcamentoCursoFormaPagamento.setFinaciamento(valor.floatValue());
	             	}else{
	             		CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
	         	        Coeficientejuros  cf = coneficienteJurosFacade.consultar(orcamentoCursoFormaPagamento.getNumeroParcelasFinanciamento(), "Juros Cliente");
	         	        Double valor = orcamentocurso.getValor() * cf.getCoeficiente();
	         	       orcamentoCursoFormaPagamento.setFinaciamento(valor.floatValue());
	             	}
			}
		}
	}

	public void verificarFormaPgamento03() throws SQLException {
		if (orcamentoCursoFormaPagamento.isSelecionado3()) {
			habilitaFormaPagamento03 = "false";
			orcamentoCursoFormaPagamento.setNumeroParcelas03(1);
			orcamentoCursoFormaPagamento.setPercentualEntrada3(30.0);
			orcamentoCursoFormaPagamento.setPercentualSaldo3(70.0);
			orcamentoCursoFormaPagamento.setValorEntrada3(0.0f);
			orcamentoCursoFormaPagamento.setValorSaldo3(0.0f);
			orcamentoCursoFormaPagamento.setValorEntrada3(0.0f);
			orcamentoCursoFormaPagamento.setValorParcela03(0.0f);
			calcularParcelamento();
		} else {
			habilitaFormaPagamento03 = "true";
			orcamentoCursoFormaPagamento.setNumeroParcelas03(1);
			orcamentoCursoFormaPagamento.setPercentualEntrada3(0.0);
			orcamentoCursoFormaPagamento.setPercentualSaldo3(00.0);
			orcamentoCursoFormaPagamento.setValorEntrada3(0.0f);
			orcamentoCursoFormaPagamento.setValorSaldo3(0.0f);
			orcamentoCursoFormaPagamento.setValorEntrada3(0.0f);
			orcamentoCursoFormaPagamento.setValorParcela03(0.0f);
		}
	}

	public void verificarFormaPgamento04() throws SQLException {
		if (orcamentoCursoFormaPagamento.isSelecionado4()) {
			habilitaFormaPagamento04 = "false";
			orcamentoCursoFormaPagamento.setNumeroParcelasFinanciamento(2);
			orcamentoCursoFormaPagamento.setPercentualEntrada4(30.0);
			orcamentoCursoFormaPagamento.setPercentualSaldo4(70.0);
			orcamentoCursoFormaPagamento.setValorEntrada4(0.0f);
			orcamentoCursoFormaPagamento.setValorSaldo4(0.0f);
			orcamentoCursoFormaPagamento.setValorEntrada4(0.0f);
			orcamentoCursoFormaPagamento.setFinaciamento(0.0f);
			calcularParcelamento();
		} else {
			habilitaFormaPagamento04 = "true";
			orcamentoCursoFormaPagamento.setNumeroParcelasFinanciamento(2);
			orcamentoCursoFormaPagamento.setPercentualEntrada4(00.0);
			orcamentoCursoFormaPagamento.setPercentualSaldo4(00.0);
			orcamentoCursoFormaPagamento.setValorEntrada4(0.0f);
			orcamentoCursoFormaPagamento.setValorSaldo4(0.0f);
			orcamentoCursoFormaPagamento.setValorEntrada4(0.0f);
			orcamentoCursoFormaPagamento.setFinaciamento(0.0f);
		}
	}

	public void gerarListaProdutos() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getCursos()
				+ " and f.listar='S' order by f.produtosorcamento.descricao";
		listaProdutosOrcamento = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaProdutosOrcamento == null) {
			listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void carregarComboMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}

	public void carregarCambio() {
		CambioFacade cambioFacade = new CambioFacade();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Cambio alterado para o dia atual", ""));
		cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
				cambio.getMoedas().getIdmoedas());
		if (cambio != null) {
			valorCambio = cambio.getValor();
			atualizarValoresProduto();
		}
	}

	public void iniciarNovoOrcamento() { 
		produtosorcamento = new Produtosorcamento();
		fornecedorCidade = new Fornecedorcidade();
		cambio = new Cambio();
		cambio.setMoedas(new Moedas());
		orcamentocurso = new Orcamentocurso();
		if (listaProdutoOrcamentoBean == null) {
			listaProdutoOrcamentoBean = new ArrayList<ProdutoOrcamentoCursoBean>();
			listaProdutoOrcamentoApagarBean = new ArrayList<ProdutoOrcamentoCursoBean>();
		}
		orcamentoCursoFormaPagamento = new Orcamentocursoformapagamento();
		cidade = new Cidade();
		orcamentoCursoFormaPagamento.setAVista(0.0f);
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		Produtosorcamento produtosorcamento = orcamentoCursoFacade
				.consultarProdutoOrcamentoCurso(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		ProdutoOrcamentoCursoBean pob = new ProdutoOrcamentoCursoBean();
		pob.setIdProdutoOrcamento(produtosorcamento.getIdprodutosOrcamento());
		pob.setDescricaoProdutoOrcamento(produtosorcamento.getDescricao());
		pob.setIdProdutoOrcamento(produtosorcamento.getIdprodutosOrcamento());
		pob.setValorMoedaEstrangeira(0.0f);
		pob.setValorMoedaReal(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getTaxatm());
		pob.setApagar(false);
		pob.setSomarvalortotal(true);
		pob.setNovo(true);
		listaProdutoOrcamentoBean.add(pob);
		seguroViagem = new Orcamentomanualseguro();
		seguroViagem.setValoresseguro(new Valoresseguro());
		seguroplanos = new Seguroplanos();
	}

	public void iniciarAlteracaoOrcamento() {
		this.cliente = orcamentocurso.getCliente();
		if(orcamentocurso.getCliente()!=null && orcamentocurso.getCliente().getIdcliente()>1){
			orcamentocurso.setNomecliente(orcamentocurso.getCliente().getNome());
		}else{
			OcClienteFacade ocClienteFacade = new OcClienteFacade();
			occliente = ocClienteFacade.consultar(orcamentocurso.getOccliente());
			painelOcCliente = true;
			painelcliente =false;
		}  
		if (orcamentocurso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
		} else {
			camposAcomodacao = "false";
		}
		fornecedorCidade = orcamentocurso.getFornecedorcidade(); 
		pais = fornecedorCidade.getCidade().getPais();
		listarCidades(); 
		cidade = fornecedorCidade.getCidade();
		listarFornecedorCidade();
		cambio = orcamentocurso.getCambio();
		valorCambio = orcamentocurso.getValorCambio();
		dataCambio = orcamentocurso.getCambio().getData();
		fornecedorCidade = orcamentocurso.getFornecedorcidade();
		OrcamentoManualSeguroFacade seguroViagemController = new OrcamentoManualSeguroFacade();
		this.seguroViagem = seguroViagemController.consultar(orcamentocurso.getIdorcamentoCurso());
		if (seguroViagem == null) {
			this.seguroViagem = seguroViagemController.consultar(orcamentocurso.getIdorcamentoCurso());
		}
		if (seguroViagem != null && seguroViagem.getValoresseguro() != null) {
			fornecedorSeguro = seguroViagem.getValoresseguro().getFornecedorcidade();
			listarPlanosSeguro();
			seguroplanos = seguroViagem.getValoresseguro().getSeguroplanos();
			listarValoresSeguro();  
			segurocancelamento = seguroViagem.isSegurocancelamento();
		} else {
			seguroViagem = new Orcamentomanualseguro();
			seguroViagem.setValoresseguro(null);
		} 
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		listaProdutoOrcamentoBean = new ArrayList<ProdutoOrcamentoCursoBean>();
		listaProdutoOrcamentoApagarBean = new ArrayList<ProdutoOrcamentoCursoBean>();
		orcamentoCursoFormaPagamento = orcamentoCursoFacade
				.consultarFormaPagamento(orcamentocurso.getIdorcamentoCurso());
		List<Produtoorcamentocurso> listaProdutos = orcamentoCursoFacade
				.listarProdutoOrcamentoCurso(orcamentocurso.getIdorcamentoCurso());
		for (int i = 0; i < listaProdutos.size(); i++) {
			ProdutoOrcamentoCursoBean pob = new ProdutoOrcamentoCursoBean();
			pob.setIdProdutoOrcamentoCurso(listaProdutos.get(i).getIdprodutoOrcamentoCurso());
			pob.setIdProdutoOrcamento(listaProdutos.get(i).getProdutosOrcamento().getIdprodutosOrcamento());
			pob.setDescricaoProdutoOrcamento(listaProdutos.get(i).getProdutosOrcamento().getDescricao());
			pob.setValorMoedaEstrangeira(listaProdutos.get(i).getValorMoedaEstrangeira());
			pob.setValorMoedaReal(listaProdutos.get(i).getValorMoedaNacional());
			pob.setApagar(false);
			if (listaProdutos.get(i).getProdutosOrcamento().getTipoorcamento() != null
					&& listaProdutos.get(i).getProdutosOrcamento().getTipoorcamento().equalsIgnoreCase("R")) {
				pob.setSomarvalortotal(listaProdutos.get(i).isSomarvalortotal());
			} else {
				pob.setSomarvalortotal(true);
			}
			listaProdutoOrcamentoBean.add(pob);
		}
		if (orcamentocurso.getDataInicio() != null) {
			gerarListaNuneroParcelas(orcamentocurso.getDataInicio());
		}
		if (orcamentoCursoFormaPagamento.getValorEntrada2() != null
				&& orcamentoCursoFormaPagamento.getValorEntrada2() > 0) {
			habilitarFormaPagamento2 = "false";
			orcamentoCursoFormaPagamento.setSelecionado2(true);
		}
		if (orcamentoCursoFormaPagamento.getValorEntrada3() != null
				&& orcamentoCursoFormaPagamento.getValorEntrada3() > 0) {
			habilitaFormaPagamento03 = "false";
			orcamentoCursoFormaPagamento.setSelecionado3(true);
		}
		if (orcamentoCursoFormaPagamento.getFinaciamento() != null
				&& orcamentoCursoFormaPagamento.getFinaciamento() > 0) {
			habilitaFormaPagamento04 = "false";
			orcamentoCursoFormaPagamento.setSelecionado4(true);
		}
		valorTotal = orcamentocurso.getValor();
		moeda = cambio.getMoedas();
	}

	public boolean salvarOrcamento() {
		String msg = validarCampos();
		if (msg.length() <= 5) {
			ClienteFacade clienteFacade = new ClienteFacade();
			cliente = clienteFacade.salvar(cliente);
			orcamentocurso.setData(new Date());
			orcamentocurso.setCambioAlterado("Não");
			orcamentocurso.setIdioma("Inglês");
			orcamentocurso.setNivelIdioma("Intermediário");
			orcamentocurso.setSituacao("Processo");
			if (orcamentocurso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
				orcamentocurso.setTipoQuarto("Sem quarto");
				orcamentocurso.setRefeicoes("Sem Refeição");
				orcamentocurso.setDuracaoAcomodacao(0);
			}
			orcamentocurso.setValorCambio(valorCambio);
			orcamentocurso.setCambio(cambio);
			orcamentocurso.setCliente(cliente);
			orcamentocurso.setFornecedor(fornecedorCidade.getFornecedor());
			orcamentocurso.setFornecedorcidade(fornecedorCidade);
			orcamentocurso.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
			orcamentocurso.setUsuario(usuarioLogadoMB.getUsuario());
			orcamentocurso.setValor(valorTotal);
			orcamentocurso.setIdCurso(0);
			OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();  
			orcamentocurso = orcamentoCursoFacade.salvar(orcamentocurso);
			salvarOrcamentoProdutoOrcamento(orcamentocurso);
			return true;
		} else {
			Mensagem.lancarMensagemInfo("Informação", msg);
			return false;
		}
	}
	
	public String validarCampos(){
		String msg="";
		if (this.cliente.getPublicidade()==null){
			msg = msg + "Selecione como conheceu a Travelmate\r\n";
		}
		if (orcamentocurso.getDataInicio() != null && orcamentocurso.getDataTermino() != null) {
			Date dataInicio = orcamentocurso.getDataInicio();
			Date dataFinal = orcamentocurso.getDataTermino();
			Date datahoje = new Date();
			if (dataInicio.before(datahoje)) {
				msg = msg + "Data Inicial do curso menor que a data atual\r\n";
			}
			if (dataFinal.before(datahoje)) {
				msg = msg + "Data Término do curso menor que a data atual\r\n";
			}
		}
		return msg;
	}

	public void salvarOrcamentoProdutoOrcamento(Orcamentocurso orcamento) {
		if (listaProdutoOrcamentoBean != null) {
			OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
			for (int i = 0; i < listaProdutoOrcamentoBean.size(); i++) {
				Produtoorcamentocurso produto;
				if (listaProdutoOrcamentoBean.get(i).isNovo()) {
					produto = new Produtoorcamentocurso();
					produto.setOrcamentocurso(orcamento.getIdorcamentoCurso());
					produto.setValorMoedaEstrangeira(listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira());
					produto.setValorMoedaNacional(listaProdutoOrcamentoBean.get(i).getValorMoedaReal());
					Produtosorcamento produtosorcamentos = orcamentoCursoFacade
							.consultarProdutoOrcamentoCurso(listaProdutoOrcamentoBean.get(i).getIdProdutoOrcamento());
					produto.setProdutosOrcamento(produtosorcamentos);
					produto.setSomarvalortotal(listaProdutoOrcamentoBean.get(i).isSomarvalortotal());
					orcamentoCursoFacade.salvar(produto);
					if (produtosorcamentos.getTipoorcamento().equalsIgnoreCase("C")) {
						if (produtosorcamentos.getDescricao().equalsIgnoreCase("Voluntariado")) {
							orcamento.setTipoorcamento("Voluntariado");
							orcamentoCursoFacade.salvar(orcamento);
						}
					}
				} else {
					produto = orcamentoCursoFacade.consultarProdutoOrcamentoCuros(
							listaProdutoOrcamentoBean.get(i).getIdProdutoOrcamentoCurso());
					if (produto != null) {
						produto.setValorMoedaEstrangeira(listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira());
						produto.setValorMoedaNacional(listaProdutoOrcamentoBean.get(i).getValorMoedaReal());
						produto.setSomarvalortotal(listaProdutoOrcamentoBean.get(i).isSomarvalortotal());
						orcamentoCursoFacade.salvar(produto);
						if (produto.getProdutosOrcamento().getTipoorcamento().equalsIgnoreCase("C")) {
							if (produto.getProdutosOrcamento().getDescricao().equalsIgnoreCase("Voluntariado")) {
								orcamento.setTipoorcamento("Voluntariado");
								orcamentoCursoFacade.salvar(orcamento);
							}
						}
					}
				}
			}
		}
	}

	public void alterarValorCambio(String valor) {
		float novoValor = Formatacao.formatarStringfloat(valor);
		if (valorCambio != novoValor) {
			valorCambio = Formatacao.formatarStringfloat(valor);
			atualizarValoresProduto();
			orcamentocurso.getCambio().setData(new Date());
		} else
			Mensagem.lancarMensagemInfo("", "Valor não é diferente");
	}

	public void atualizarValoresProduto() {
		if (listaProdutoOrcamentoBean != null) {
			int codTxTM = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			for (int i = 0; i < listaProdutoOrcamentoBean.size(); i++) {
				int codProdutoTxTM = getListaProdutoOrcamentoBean().get(i).getIdProdutoOrcamento();
				if (codProdutoTxTM == codTxTM) {
					listaProdutoOrcamentoBean.get(i).setValorMoedaEstrangeira(
							listaProdutoOrcamentoBean.get(i).getValorMoedaReal() / valorCambio);
				} else if (listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira() > 0) {
					listaProdutoOrcamentoBean.get(i).setValorMoedaReal(
							listaProdutoOrcamentoBean.get(i).getValorMoedaEstrangeira() * valorCambio);
				}
				calcularValorTotalOrcamento();
			}
		}
	}

	public String confirmarOrcamento() {
		if (Formatacao.validarEmail(cliente.getEmail())) {
			if (fornecedorCidade != null && fornecedorCidade.getIdfornecedorcidade() != null) {
				if (moeda != null && moeda.getIdmoedas() != null) {
					if (validarFormaPagamento()) {
						if(painelOcCliente){
							salvarOcCliente();
						}else if (cliente.getIdcliente() == null) {
							salvarCliente();
						}
						boolean novo =false;
						if(orcamentocurso.getIdorcamentoCurso()==null){
							novo=true;
						}
						boolean emitirOrcamento = salvarOrcamento();
						if(emitirOrcamento) {
							apagarOrcamentoProdutosOrcamento();
							if (seguroViagem != null && seguroViagem.getValor() != null && seguroViagem.getValor() > 0) {
								salvarSeguro();
							}
							salvarOrcamentoFormaPagamento();
							Mensagem.lancarMensagemInfo("", "Orçamento salvo com sucesso"); 
							if(novo){
								if(cliente.isClienteLead()){
									Leadhistorico leadhistorico = new Leadhistorico();
									leadhistorico.setCliente(cliente);
									leadhistorico.setDatahistorico(new Date());
									leadhistorico.setDataproximocontato(new Date());
									TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
									String sql="Select t From Tipocontato t where t.tipo='Orçamento'";
									Tipocontato tipocontato = tipoContatoFacade.consultar(sql);
									leadhistorico.setTipocontato(tipocontato);
									leadhistorico.setHistorico("ORÇAMENTO MANUAL "+orcamentocurso.getIdorcamentoCurso()+": "+orcamentocurso.getCurso()+" - "
									    +fornecedorCidade.getCidade().getNome()+", "
										+fornecedorCidade.getFornecedor().getNome()+".");
									leadhistorico.setTipoorcamento("m");
			            			leadhistorico.setIdorcamento(orcamentocurso.getIdorcamentoCurso());
									leadhistorico = leadHistoricoDao.salvar(leadhistorico);
									if(cliente.getLead()!=null){
										Lead lead = cliente.getLead(); 
			            				lead.setDataultimocontato(new Date());
			            				if (lead.getSituacao() < 3) {
				            				new LeadSituacaoBean(lead, lead.getSituacao(), 3, leadSituacaoDao);
					            			lead.setSituacao(3);
										}
			            				lead = leadDao.salvar(lead);
			            			} 
								}
							}
							FacesContext fc = FacesContext.getCurrentInstance();
							HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
							session.setAttribute("tipoocamento", orcamentocurso.getTipoorcamento());
							return "consOrcamentoManual";
						}
					}
				} else
					Mensagem.lancarMensagemFatal("Moeda não selecionada", "");
			} else
				Mensagem.lancarMensagemFatal("Escola não selecionada", "");
		}
		return "";
	}

	public boolean validarFormaPagamento() {
		if (orcamentoCursoFormaPagamento.isSelecionado2()) {
			if (orcamentoCursoFormaPagamento.getValorParcela02() == null
					|| orcamentoCursoFormaPagamento.getValorParcela02() == 0) {
				Mensagem.lancarMensagemFatal("Informe nº parcelas", "Forma de pagamento - opção 2");
				return false;
			}
		}
		if (orcamentoCursoFormaPagamento.isSelecionado3()) {
			if (orcamentoCursoFormaPagamento.getValorParcela03() == null
					|| orcamentoCursoFormaPagamento.getValorParcela03() == 0) {
				Mensagem.lancarMensagemFatal("Informe nº parcelas", "Forma de pagamento - opção 3");
				return false;
			}
		}
		if (orcamentoCursoFormaPagamento.isSelecionado4()) {
			if (orcamentoCursoFormaPagamento.getFinaciamento() == null
					|| orcamentoCursoFormaPagamento.getFinaciamento() == 0) {
				Mensagem.lancarMensagemFatal("Informe nº parcelas", "Forma de pagamento - opção 4");
				return false;
			}
		}
		return true;
	}

	public String cancelarOrcamento() {
		return "consOrcamentoManual";
	}

	public void salvarCliente() {
		cliente.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		ClienteFacade clienteFacade = new ClienteFacade();
		cliente = clienteFacade.salvar(cliente);
	}
	
	public void salvarOcCliente() {
		occliente.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		OcClienteFacade occlienteFacade = new OcClienteFacade();
		occliente = occlienteFacade.salvar(occliente);
	}

	public void apagarOrcamentoProdutosOrcamento() {
		if (listaProdutoOrcamentoApagarBean != null) {
			for (int i = 0; i < listaProdutoOrcamentoApagarBean.size(); i++) {
				if (listaProdutoOrcamentoApagarBean.get(i).getIdProdutoOrcamentoCurso() > 0) {
					OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
					orcamentoCursoFacade.excluirProdutoOrcamentoCurso(
							listaProdutoOrcamentoApagarBean.get(i).getIdProdutoOrcamentoCurso());
				}
			}
		}
	}

	public void salvarOrcamentoFormaPagamento() {
		if (orcamentoCursoFormaPagamento == null) {
			orcamentoCursoFormaPagamento = new Orcamentocursoformapagamento();
		}
		orcamentoCursoFormaPagamento.setAVista(valorTotal);
		orcamentoCursoFormaPagamento.setOrcamentocurso(orcamentocurso);
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		orcamentoCursoFormaPagamento = orcamentoCursoFacade.salvarFormaPagamento(orcamentoCursoFormaPagamento);

	}

	public void carregarValorCambio() {
		cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		cambio.setData(new Date());
		orcamentocurso.setCambio(cambio);
		valorCambio = cambio.getValor();
		atualizarValoresProduto();
	}

	public void validarEmail() {
		if(Formatacao.validarEmail(cliente.getEmail())){
			if(cliente==null || cliente.getIdcliente()==null){
				ClienteFacade clienteFacade = new ClienteFacade();
				Cliente c = clienteFacade.consultarEmail(cliente.getEmail());
				if(c!=null && c.getIdcliente()!=null){
					Mensagem.lancarMensagemInfo("Cliente já existente.", "");
					cliente = c;
				}
			}
		}
	}

	public void importarModelo(Modeloorcamentocurso modeloOrcamento) {
		if (modeloOrcamento != null) {
			produtosorcamento = new Produtosorcamento();
			fornecedorCidade = new Fornecedorcidade();
			cambio = new Cambio();
			cambio.setMoedas(new Moedas());
			listaProdutoOrcamentoBean = new ArrayList<ProdutoOrcamentoCursoBean>();
			listaProdutoOrcamentoApagarBean = new ArrayList<ProdutoOrcamentoCursoBean>();
			orcamentoCursoFormaPagamento = new Orcamentocursoformapagamento();
			cidade = new Cidade();
			orcamentoCursoFormaPagamento.setAVista(0.0f);
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(),
					modeloOrcamento.getCambio().getMoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			valorCambio = cambio.getValor();
			moeda = modeloOrcamento.getCambio().getMoedas();
			this.fornecedorCidade = modeloOrcamento.getFornecedorcidade();
			orcamentocurso.setCurso(modeloOrcamento.getCurso());
			orcamentocurso.setFornecedor(fornecedorCidade.getFornecedor());
			pais = fornecedorCidade.getCidade().getPais();
			if (pais != null) {
				listarCidades();
			}
			cidade = fornecedorCidade.getCidade();
			listarFornecedorCidade();
			this.fornecedorCidade = modeloOrcamento.getFornecedorcidade();
			orcamentocurso.setAulasSemana(modeloOrcamento.getAulasSemana());
			orcamentocurso.setTipoDuracao(modeloOrcamento.getTipoDuracao());
			orcamentocurso.setTipoAcomodacao(modeloOrcamento.getTipoAcomodacao());
			orcamentocurso.setTipoQuarto(modeloOrcamento.getTipoQuarto());
			orcamentocurso.setRefeicoes(modeloOrcamento.getRefeicoes());
			orcamentocurso.setDuracaoAcomodacao(modeloOrcamento.getDuracaoAcomodacao());
			orcamentocurso.setObservacao(modeloOrcamento.getObservacao());
			orcamentocurso.setMaterialDidatico(modeloOrcamento.getMaterialDidatico());
			orcamentocurso.setSeguroSaude(modeloOrcamento.getSeguroSaude());
			orcamentocurso.setTransfer(modeloOrcamento.getTransfer());
			orcamentocurso.setPassagemAerea(modeloOrcamento.getPassagemAerea());
			orcamentocurso.setSedexInternacional(modeloOrcamento.getSedexInternacional());
			orcamentocurso.setVistoConsular(modeloOrcamento.getVistoConsular());
			orcamentocurso.setOutrasTaxas(modeloOrcamento.getOutrasTaxas());
			orcamentocurso.setNumeroSemanas(Integer.parseInt(modeloOrcamento.getDuracao()));
			carregarCamposAcomodacao();
			apagarOrcamentoProdutosOrcamento();

			List<Modeloprodutoorcamentocurso> listaMoleloProduto = modeloOrcamento.getModeloprodutoorcamentocursoList();
			if (listaMoleloProduto != null) {
				for (int i = 0; i < listaMoleloProduto.size(); i++) {
					Modeloprodutoorcamentocurso modeloProdto = listaMoleloProduto.get(i);
					ProdutoOrcamentoCursoBean produtoBean = new ProdutoOrcamentoCursoBean();
					Produtosorcamento produtoOrcamento = listaMoleloProduto.get(i).getProdutosorcamento();
					produtoBean.setIdProdutoOrcamento(modeloProdto.getProdutosorcamento().getIdprodutosOrcamento());
					int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
					if (produtoOrcamento.getIdprodutosOrcamento() == tx) {
						produtoBean.setValorMoedaReal(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getTaxatm());
						produtoBean.setValorMoedaEstrangeira(modeloProdto.getValorMoedaNacional() / valorCambio);
					} else {
						produtoBean.setValorMoedaEstrangeira(modeloProdto.getValorMoedaEstrangeira());
						produtoBean.setValorMoedaReal(valorCambio * modeloProdto.getValorMoedaEstrangeira());
					}
					produtoBean.setApagar(false);
					produtoBean.setNovo(true);
					produtoBean.setSomarvalortotal(true);
					produtoBean.setDescricaoProdutoOrcamento(produtoOrcamento.getDescricao());
					listaProdutoOrcamentoBean.add(produtoBean);
				}
				Modeloorcamentocursoforma modeloForma = null;
				calcularValorTotalOrcamento();
				if (modeloOrcamento.getModeloorcamentocursoformaList() != null) {
					if (modeloOrcamento.getModeloorcamentocursoformaList().size() > 0) {
						modeloForma = modeloOrcamento.getModeloorcamentocursoformaList().get(0);
					}

					if (modeloForma != null) {
						orcamentoCursoFormaPagamento.setAVista(modeloForma.getAVista());
						orcamentoCursoFormaPagamento.setPercentualEntrada2(modeloForma.getPercentualEntrada2());
						orcamentoCursoFormaPagamento.setValorEntrada2(modeloForma.getValorEntrada2());
						orcamentoCursoFormaPagamento.setPercentualSaldo2(modeloForma.getPercentualSaldo2());
						orcamentoCursoFormaPagamento.setValorSaldo2(modeloForma.getValorSaldo2());
						orcamentoCursoFormaPagamento.setPercentualEntrada3(modeloForma.getPercentualEntrada3());
						orcamentoCursoFormaPagamento.setValorEntrada3(modeloForma.getValorEntrada3());
						orcamentoCursoFormaPagamento.setPercentualSaldo3(modeloForma.getPercentualSaldo3());
						orcamentoCursoFormaPagamento.setValorSaldo3(modeloForma.getValorSaldo3());
						orcamentoCursoFormaPagamento.setNumeroParcelas02(modeloForma.getNumeroParcelas02());
						orcamentoCursoFormaPagamento.setNumeroParcelas03(modeloForma.getNumeroParcelas03());
						orcamentoCursoFormaPagamento
								.setNumeroParcelasFinanciamento(modeloForma.getNumeroParcelasFinanciamento());
						orcamentoCursoFormaPagamento.setValorParcela02(modeloForma.getValorParcela02());
						orcamentoCursoFormaPagamento.setValorParcela03(modeloForma.getValorParcela03());
						orcamentoCursoFormaPagamento.setFinaciamento(modeloForma.getFinaciamento());

						if (orcamentoCursoFormaPagamento.getValorEntrada2() != null
								&& orcamentoCursoFormaPagamento.getValorEntrada2() > 0) {
							habilitarFormaPagamento2 = "false";
							orcamentoCursoFormaPagamento.setSelecionado2(true);
						}
						if (orcamentoCursoFormaPagamento.getValorEntrada3() != null
								&& orcamentoCursoFormaPagamento.getValorEntrada3() > 0) {
							habilitaFormaPagamento03 = "false";
							orcamentoCursoFormaPagamento.setSelecionado3(true);
						}
						if (orcamentoCursoFormaPagamento.getFinaciamento() != null
								&& orcamentoCursoFormaPagamento.getFinaciamento() > 0) {
							habilitaFormaPagamento04 = "false";
							orcamentoCursoFormaPagamento.setSelecionado4(true);
						}
					}

				}
			}
		}

	}

	public String importarModeloOrcamento() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 700);
		RequestContext.getCurrentInstance().openDialog("importarModeloOrcamentoManual", options, null);
		return "";
	}

	public void retornoDialogModelos(SelectEvent event) {
		Modeloorcamentocurso modeloOrcamento = (Modeloorcamentocurso) event.getObject();
		importarModelo(modeloOrcamento);
	}

	public String validarMascaraFoneResidencial() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneResidencial);
	}

	public String validarMascaraFoneCelular() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneCelular);
	}
	
	public void listarPlanosSeguro() {
		if (fornecedorSeguro != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorSeguro.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}

	public void listarValoresSeguro() {
		if (fornecedorSeguro != null && seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			String sql;
			sql = "SELECT v FROM Valoresseguro v WHERE v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorSeguro.getIdfornecedorcidade() + " AND v.situacao='Ativo'"
					+ " AND v.seguroplanos.idseguroplanos=" + seguroplanos.getIdseguroplanos();
			listaValoresSeguro = valorSeguroFacade.listar(sql);
		}
		if (listaValoresSeguro == null) {
			listaValoresSeguro = new ArrayList<Valoresseguro>();
		}
	}

	public void calcularNumeroDiasSeguro() {
		if ((seguroViagem.getDatainicio() != null) && (seguroViagem.getDatatermino() != null)) {
			int numeroDias = Formatacao.subtrairDatas(seguroViagem.getDatainicio(), seguroViagem.getDatatermino());
			if (numeroDias < 0) {
				numeroDias = numeroDias * -1;
			}
			seguroViagem.setNumerodias(numeroDias + 1);
			dataTerminoSeguro();
			convertendoValoresSeguro();
		}
	}

	public void dataTerminoSeguro() {
		if ((seguroViagem.getDatainicio() != null) && (seguroViagem.getNumerodias() > 0)) {
			CambioFacade cambioFacade = new CambioFacade();
			if (seguroViagem.getValoresseguro()!=null){
			Cambio cambioSeguro = cambioFacade.consultarCambioMoedaPais(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas(), usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			if (cambioSeguro != null) {
				seguroViagem.setDatatermino(Formatacao.calcularDataFinalPorDias(seguroViagem.getDatainicio(),
						seguroViagem.getNumerodias()));
				float valornacional = seguroViagem.getValoresseguro().getValorgross() * cambioSeguro.getValor();
				if (seguroViagem.getValoresseguro().getCobranca().equalsIgnoreCase("Fixo")) {
					seguroViagem.setValor(valornacional);
				} else
					seguroViagem.setValor(valornacional * seguroViagem.getNumerodias());
				if (seguroViagem.isSomarvalortotal()) {
					calcularValorTotalOrcamento();
				}
				calcularValorSeguroPrivadoListaProdutos();
			}
			}
		}

	}

	public void iniciarListaFornecedorSeguro() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = (int) aplicacaoMB.getParametrosprodutos().getCartao01();
		List<Paisproduto> listaPais = paisProdutoFacade.listar(idProduto);
		if (listaPais != null) {
			listaFornecedorCidadeSeguro = listaPais.get(0).getProdutos().getFornecedorcidadeList();
		}
	}

	public void carregarCobrancaSeguro() {
		seguroViagem.getValoresseguro().setCobranca(seguroViagem.getValoresseguro().getCobranca());
		verificarSeguroCancelamento();
	}

	public void salvarSeguro() {
		OrcamentoManualSeguroFacade orcamentoManualSeguroFacade = new OrcamentoManualSeguroFacade();
		seguroViagem.setOrcamentocurso(orcamentocurso);
		seguroViagem = orcamentoManualSeguroFacade.salvar(seguroViagem);
	}

	public boolean habilitarCheckBoxSomar(ProdutoOrcamentoCursoBean produtoOrcamentoCursoBean) {
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		Produtosorcamento produto = orcamentoCursoFacade
				.consultarProdutoOrcamentoCurso(produtoOrcamentoCursoBean.getIdProdutoOrcamento());
		int seguro = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
		if (seguro == produtoOrcamentoCursoBean.getIdProdutoOrcamento()) {
			return true;
		}
		if (produto.getTipoorcamento() != null && produto.getTipoorcamento().equalsIgnoreCase("R")) {
			return false;
		} else
			return true;
	}
	 
	public void selecionarCliente(Cliente cliente){
		this.cliente = cliente;
	}

	  
	public void convertendoValoresSeguro(){  
		if(cambio==null || cambio.getValor()==null){
			Mensagem.lancarMensagemErro("Atenção!", "Cambio não informado.");
		}else{
			valorTotalSeguroDola = seguroViagem.getNumerodias() * seguroViagem.getValoresseguro().getValorgross();
			valorUtilitarioRS = seguroViagem.getValoresseguro().getValorgross() * cambio.getValor();
		}
	}
	
	
	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			valorCambio = cambio.getValor();
			if (valorCambio > 0) {
				if (listaProdutoOrcamentoBean != null && listaProdutoOrcamentoBean.size() > 0) {
					listaProdutoOrcamentoBean.get(0).setValorMoedaEstrangeira(listaProdutoOrcamentoBean.get(0).getValorMoedaReal() / valorCambio);
				}
			}
		}
	}
	
	public void listarCidades() {
		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
		String sql = "SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.pais.idpais="+pais.getIdpais(); 
		if(tipo!=null && tipo.equalsIgnoreCase("voluntariado")){
			sql=sql+ " and c.paisproduto.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getVoluntariado();
		}else {
			sql=sql+ " and c.paisproduto.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getCursos();
		}
		sql=sql+ " ORDER BY c.cidade.nome";
		listaCidade = cidadePaisProdutosFacade.listar(sql);  
	}
	

	public String retornaHistoricoLead() {
		if (lead != null) {
			if (lead.getIdlead() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
				session.setAttribute("lead", lead);
				session.setAttribute("funcao", funcao);
				session.setAttribute("posicao", 0);
				return "historicoCliente";
			}
		}
		return "";
	}
	
	public void verificarSeguroCancelamento() {
		if(seguroViagem.getValoresseguro().isSegurocancelamento()) {
			segurocancelamento = true; 
		} else {
			segurocancelamento = false; 
		}
	} 
	
	public void adicionarSeguroCancelamento() { 
		if (segurocancelamento && seguroViagem.getValoresseguro().isSegurocancelamento()) { 
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			Produtosorcamento produto = produtoOrcamentoFacade
					.consultar(aplicacaoMB.getParametrosprodutos().getSegurocancelamentoid());
			ProdutoOrcamentoCursoBean pob = new ProdutoOrcamentoCursoBean();
			pob.setIdProdutoOrcamentoCurso(0);
			pob.setDescricaoProdutoOrcamento(produto.getDescricao());
			pob.setIdProdutoOrcamento(produto.getIdprodutosOrcamento());
			CambioFacade cambioFacade = new CambioFacade();
			Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio),
					seguroViagem.getValoresseguro().getMoedas().getIdmoedas()); 
			float valorSeguro = seguroViagem.getValoresseguro().getValorsegurocancelamento()*cambioSeguro.getValor();
			pob.setValorMoedaEstrangeira(valorSeguro/cambio.getValor());
			pob.setValorMoedaReal(
					valorSeguro); 
			pob.setApagar(false);
			pob.setNovo(true);
			pob.setSomarvalortotal(seguroViagem.isSomarvalortotal());
			listaProdutoOrcamentoBean.add(pob);
			calcularValorTotalOrcamento();
			this.valorMoedaEstrangeira = 0.0f;
			this.valorMoedaReal = 0.0f;
		} else { 
			if (listaProdutoOrcamentoBean != null) {
				int idseguroCancelamento = aplicacaoMB.getParametrosprodutos().getSegurocancelamentoid();
				for (int i = 0; i < listaProdutoOrcamentoBean.size(); i++) {
					int idProdutoOrcamento = listaProdutoOrcamentoBean.get(i).getIdProdutoOrcamento();
					if (idseguroCancelamento == idProdutoOrcamento) { 
						listaProdutoOrcamentoBean.remove(i);
						calcularValorTotalOrcamento();
						this.valorMoedaEstrangeira = 0.0f;
						this.valorMoedaReal = 0.0f;
					}
				}
			}
		}
	}
	
	
	public void gerarListaNuneroParcelas(Date dataInicio) {
		listaNumeroParcelas = new ArrayList<>();
		NumeroParcelasBean np = new NumeroParcelasBean();
		np.setNumero(0);
		np.setTitulo("0");
		listaNumeroParcelas.add(np);
		int dias = Formatacao.subtrairDatas(new Date(), dataInicio);
		if (dias > 30) {
			int diaInicio = Formatacao.getDiaData(dataInicio);
			int diaVenciamento = Formatacao.getDiaData(new Date());
			if (diaVenciamento < diaInicio) {
				dias = dias - 30;
			}
			dias = dias / 30;
			if (dias > 0) {
				for (int i = 0; i < dias; i++) {
					np = new NumeroParcelasBean();
					np.setNumero(i + 1);
					np.setTitulo(String.valueOf(i + 1));
					listaNumeroParcelas.add(np);
				}
			}
		}
	}
	
	
	public void calcularValorSeguroPrivadoListaProdutos() {
		List<ProdutoOrcamentoCursoBean> listaProdutosBean = new ArrayList<>();
		int codSeguroPrivado = aplicacaoMB.getParametrosprodutos().getSeguroOrcamento();
		for (int i = 0; i < listaProdutoOrcamentoBean.size(); i++) {
			int codigoLista = listaProdutoOrcamentoBean.get(i).getIdProdutoOrcamento();
			if (codSeguroPrivado != codigoLista) {
				listaProdutosBean.add(listaProdutoOrcamentoBean.get(i));
			}
		}
		listaProdutoOrcamentoBean = listaProdutosBean;
		float valorEstrangeira = 0.0f;
		float valorReal = 0.0f;
		if (seguroViagem != null) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produto = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getSeguroOrcamento());
				ProdutoOrcamentoCursoBean pob = new ProdutoOrcamentoCursoBean();
				pob.setIdProdutoOrcamentoCurso(0);
				pob.setDescricaoProdutoOrcamento(produto.getDescricao());
				pob.setIdProdutoOrcamento(produto.getIdprodutosOrcamento());
				valorReal = seguroViagem.getValor();
				valorEstrangeira = seguroViagem.getValor() / cambio.getValor();
				pob.setValorMoedaEstrangeira(valorEstrangeira);
				pob.setValorMoedaReal(valorReal); 
				pob.setApagar(false);
				pob.setNovo(true);
				pob.setSomarvalortotal(seguroViagem.isSomarvalortotal());
				listaProdutoOrcamentoBean.add(pob);
		}
	}
}
