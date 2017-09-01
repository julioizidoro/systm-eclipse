package br.com.travelmate.managerBean.financeiro.calculadoraMargem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import br.com.travelmate.bean.ConsultaBean;
import br.com.travelmate.bean.DesagioBean;
import br.com.travelmate.bean.comissao.CalcularComissaoBean;
import br.com.travelmate.bean.comissao.ComissaoCursoBean;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class CalculadoraMargemMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private float valorTotal;
	private float valorEntrada;
	private float saldoParcelar;
	private int numeroParcelas;
	private Date dataVenda;
	private Date dataInicio;
	private float desagio;
	private float juros;
	private float custoTotal;
	private float custoFranquia;
	private float comissaoFranquia;
	private float assessoriaTM;
	private float descontoMatriz;
	private float descontoloja;
	private List<Paisproduto> listaPais;
	private Pais pais;
	private Cidade cidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Fornecedorcidade fornecedorCidade;
	
	@PostConstruct()
	public void init() {
		
	}
	
	
	public float getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}
	public float getValorEntrada() {
		return valorEntrada;
	}
	public void setValorEntrada(float valorEntrada) {
		this.valorEntrada = valorEntrada;
	}
	public float getSaldoParcelar() {
		return saldoParcelar;
	}
	public void setSaldoParcelar(float saldoParcelar) {
		this.saldoParcelar = saldoParcelar;
	}
	public int getNumeroParcelas() {
		return numeroParcelas;
	}
	public void setNumeroParcelas(int numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}
	public Date getDataVenda() {
		return dataVenda;
	}
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public float getDesagio() {
		return desagio;
	}
	public void setDesagio(float desagio) {
		this.desagio = desagio;
	}
	public float getJuros() {
		return juros;
	}
	public void setJuros(float juros) {
		this.juros = juros;
	}
	public float getCustoTotal() {
		return custoTotal;
	}
	public void setCustoTotal(float custoTotal) {
		this.custoTotal = custoTotal;
	}
	
	public float getCustoFranquia() {
		return custoFranquia;
	}


	public void setCustoFranquia(float custoFranquia) {
		this.custoFranquia = custoFranquia;
	}


	public float getComissaoFranquia() {
		return comissaoFranquia;
	}
	public void setComissaoFranquia(float comissaoFranquia) {
		this.comissaoFranquia = comissaoFranquia;
	}
	public float getAssessoriaTM() {
		return assessoriaTM;
	}
	public void setAssessoriaTM(float assessoriaTM) {
		this.assessoriaTM = assessoriaTM;
	}
	public float getDescontoMatriz() {
		return descontoMatriz;
	}
	public void setDescontoMatriz(float descontoMatriz) {
		this.descontoMatriz = descontoMatriz;
	}
	public float getDescontoloja() {
		return descontoloja;
	}
	public void setDescontoloja(float descontoloja) {
		this.descontoloja = descontoloja;
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


	public List<Paisproduto> getListaPais() {
		return listaPais;
	}


	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}


	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}
	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}
	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}
	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}
	
	public void iniciarCalculoComissao() {
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = 0;
		idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		listaPais = paisProdutoFacade.listar(idProduto);
	}
	
	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade() +
					" and f.ativo=1";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}
	
	public void calcularCustoFranquia() {
		List<Parcelamentopagamento> listaParcelamento = new ArrayList<Parcelamentopagamento>();
		Parcelamentopagamento parcelamento = new Parcelamentopagamento();
		parcelamento.setFormaPagamento("Cartão de crédito");
		parcelamento.setNumeroParcelas(numeroParcelas);
		parcelamento.setTipoParcelmaneto("Matriz");
		parcelamento.setValorParcelamento(saldoParcelar);
		parcelamento.setDiaVencimento(dataVenda);
		parcelamento.setValorParcela(saldoParcelar/numeroParcelas);
		listaParcelamento.add(parcelamento);
		DesagioBean desagioBean = new DesagioBean(listaParcelamento, aplicacaoMB);
	    desagio = desagioBean.getBoleto() + desagioBean.getCartao() + desagioBean.getJuros() + desagioBean.getCartaoDebito();
	
	    CalcularComissaoBean comissaoBean = new CalcularComissaoBean();   
	    float jurosFranquia = comissaoBean.calcularJurosFranquia(listaParcelamento, dataInicio);
	    custoTotal = desagio + jurosFranquia;
	    custoFranquia = jurosFranquia + desagio;
	    if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getTipo().equalsIgnoreCase("Express")) {
	    	custoFranquia = custoFranquia/2;
	    }else custoFranquia = custoFranquia/3;
	}
	
	/*public void calcularComissaoFranquiaCurso() {
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorComissao = fornecedorComissaoCursoFacade.consultar(
				fornecedorCidade.getFornecedor().getIdfornecedor(),
				fornecedorCidade.getCidade().getPais().getIdpais());
		Vendascomissao vendasComissao = null;
		vendasComissao = new Vendascomissao();
		vendasComissao.setVendas(venda);
		vendasComissao.setPaga("Não");
		
		Vendas venda = new Vendas();
		if (venda.getIdvendas() == null) {
			venda.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
			venda.setValor(valorTotal);
			Produtos produto = ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getCursos());
			venda.setProdutos(produto);
		}
		ForneceorComissaoFA
		ComissaoCursoBean comissaoCursoBean = new ComissaoCursoBean(aplicacaoMB, venda,
				orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao, formaPagamento.getParcelamentopagamentoList(),
				curso.getDataInicio(), vendascomissao, formaPagamento.getValorJuros(), false);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendascomissao", vendascomissao);
		session.setAttribute("percentualcomissao", comissaoCursoBean.getPercentualComissao());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 380);
		RequestContext.getCurrentInstance().openDialog("calcularcomissao", options, null);
		return "";
	}*/
	
	
	
	
	
	

}
