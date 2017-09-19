package br.com.travelmate.managerBean.financeiro.calculadoraMargem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.DesagioBean;
import br.com.travelmate.bean.comissao.CalcularComissaoBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ValorAupairFacade;
import br.com.travelmate.facade.ValoresHighSchoolFacade;
import br.com.travelmate.facade.ValoresProgramasTeensFacade;
import br.com.travelmate.facade.ValoresTraineeFacade;
import br.com.travelmate.facade.ValoresWorkFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valoresaupair;
import br.com.travelmate.model.Valoreshighschool;
import br.com.travelmate.model.Valoresprogramasteens;
import br.com.travelmate.model.Valorestrainee;
import br.com.travelmate.model.Valoreswork;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;

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
	private Date dataVencimento;
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
	private Produtos produto;
	private List<Produtos> listaProdutos;
	private boolean margem=false;
	private boolean curso=true;
	private boolean aupair=false;
	private boolean demipair=false;
	private boolean cursosTeens=false;
	private boolean highschool=false;
	private boolean trainee=false;
	private boolean voluntariado=false;
	private boolean work=false;
	private Valoresaupair valoresAupair;
	private List<Valoresaupair> listaValoresAupair;
	private Valoresprogramasteens valoresProgramasTeens;
	private List<Valoresprogramasteens> listaValoresProgramasTeens;
	private Valorestrainee valorestrainee;
	private List<Valorestrainee> listaValoresTrainee;
	private Valoreswork valoresWork;
	private List<Valoreswork> listaValoresWork;
	private Valoreshighschool valoresHighSchool;
	private List<Valoreshighschool> listaValoresHighSchool;
	private Produtosorcamento produtoOrcamento;
    private List<Filtroorcamentoproduto> listaProdutosOrcamento;

	@PostConstruct()
	public void init() {
		listaProdutos = GerarListas.listarProdutos(""); 
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

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
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

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public boolean isMargem() {
		return margem;
	}

	public void setMargem(boolean margem) {
		this.margem = margem;
	}

	public boolean isCurso() {
		return curso;
	}

	public void setCurso(boolean curso) {
		this.curso = curso;
	}

	public boolean isAupair() {
		return aupair;
	}

	public void setAupair(boolean aupair) {
		this.aupair = aupair;
	}

	public boolean isDemipair() {
		return demipair;
	}

	public void setDemipair(boolean demipair) {
		this.demipair = demipair;
	}

	public boolean isCursosTeens() {
		return cursosTeens;
	}

	public void setCursosTeens(boolean cursosTeens) {
		this.cursosTeens = cursosTeens;
	}

	public boolean isHighschool() {
		return highschool;
	}

	public void setHighschool(boolean highschool) {
		this.highschool = highschool;
	}

	public boolean isTrainee() {
		return trainee;
	}

	public void setTrainee(boolean trainee) {
		this.trainee = trainee;
	}

	public boolean isVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(boolean voluntariado) {
		this.voluntariado = voluntariado;
	}

	public boolean isWork() {
		return work;
	}

	public void setWork(boolean work) {
		this.work = work;
	}

	public Valoresaupair getValoresAupair() {
		return valoresAupair;
	}

	public void setValoresAupair(Valoresaupair valoresAupair) {
		this.valoresAupair = valoresAupair;
	}

	public List<Valoresaupair> getListaValoresAupair() {
		return listaValoresAupair;
	}

	public void setListaValoresAupair(List<Valoresaupair> listaValoresAupair) {
		this.listaValoresAupair = listaValoresAupair;
	}

	public Valoresprogramasteens getValoresProgramasTeens() {
		return valoresProgramasTeens;
	}

	public void setValoresProgramasTeens(Valoresprogramasteens valoresProgramasTeens) {
		this.valoresProgramasTeens = valoresProgramasTeens;
	}

	public List<Valoresprogramasteens> getListaValoresProgramasTeens() {
		return listaValoresProgramasTeens;
	}

	public void setListaValoresProgramasTeens(List<Valoresprogramasteens> listaValoresProgramasTeens) {
		this.listaValoresProgramasTeens = listaValoresProgramasTeens;
	}

	public Valorestrainee getValorestrainee() {
		return valorestrainee;
	}

	public void setValorestrainee(Valorestrainee valorestrainee) {
		this.valorestrainee = valorestrainee;
	}

	public List<Valorestrainee> getListaValoresTrainee() {
		return listaValoresTrainee;
	}

	public void setListaValoresTrainee(List<Valorestrainee> listaValoresTrainee) {
		this.listaValoresTrainee = listaValoresTrainee;
	}

	public Valoreswork getValoresWork() {
		return valoresWork;
	}

	public void setValoresWork(Valoreswork valoresWork) {
		this.valoresWork = valoresWork;
	}

	public List<Valoreswork> getListaValoresWork() {
		return listaValoresWork;
	}

	public void setListaValoresWork(List<Valoreswork> listaValoresWork) {
		this.listaValoresWork = listaValoresWork;
	}

	public Valoreshighschool getValoresHighSchool() {
		return valoresHighSchool;
	}

	public void setValoresHighSchool(Valoreshighschool valoresHighSchool) {
		this.valoresHighSchool = valoresHighSchool;
	}

	public List<Valoreshighschool> getListaValoresHighSchool() {
		return listaValoresHighSchool;
	}

	public void setListaValoresHighSchool(List<Valoreshighschool> listaValoresHighSchool) {
		this.listaValoresHighSchool = listaValoresHighSchool;
	}

	public Produtosorcamento getProdutoOrcamento() {
		return produtoOrcamento;
	}

	public void setProdutoOrcamento(Produtosorcamento produtoOrcamento) {
		this.produtoOrcamento = produtoOrcamento;
	}

	public List<Filtroorcamentoproduto> getListaProdutosOrcamento() {
		return listaProdutosOrcamento;
	}

	public void setListaProdutosOrcamento(List<Filtroorcamentoproduto> listaProdutosOrcamento) {
		this.listaProdutosOrcamento = listaProdutosOrcamento;
	}

	public void buscarPais() {
		aupair = false;
		curso=false;
		cursosTeens=false;
		highschool=false;
		trainee=false;
		voluntariado=false;
		demipair=false;
		work=false;
		if(produto!=null && produto.getIdprodutos()!=null) {
			int idproduto = produto.getIdprodutos();
			if(idproduto==aplicacaoMB.getParametrosprodutos().getAupair()) {
				aupair=true; 
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getCursos()) {
				curso=true;
				gerarListaProdutos();
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
				cursosTeens=true;
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getHighSchool()) {
				highschool=true;
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getTrainee()) {
				trainee=true;
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
				voluntariado=true;
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getDemipair()) {
				demipair=true;
			}else if(idproduto==aplicacaoMB.getParametrosprodutos().getWork()) {
				work=true;
			}
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade(); 
			listaPais = paisProdutoFacade.listar(idproduto);
		}
	}

	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade()
					+ " and f.ativo=1 and f.produtos.idprodutos="+produto.getIdprodutos();
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
		parcelamento.setValorParcela(saldoParcelar / numeroParcelas);
		listaParcelamento.add(parcelamento);
		DesagioBean desagioBean = new DesagioBean(listaParcelamento, aplicacaoMB);
		desagio = desagioBean.getBoleto() + desagioBean.getCartao() + desagioBean.getJuros()
				+ desagioBean.getCartaoDebito();

		CalcularComissaoBean comissaoBean = new CalcularComissaoBean();
		float jurosFranquia = comissaoBean.calcularJurosFranquia(listaParcelamento, dataInicio);
		custoTotal = desagio + jurosFranquia;
		custoFranquia = jurosFranquia + desagio;
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getTipo().equalsIgnoreCase("Express")) {
			custoFranquia = custoFranquia / 2;
		} else
			custoFranquia = custoFranquia / 3;
	}

	/*
	 * public void calcularComissaoFranquiaCurso() { FornecedorComissaoCursoFacade
	 * fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
	 * Fornecedorcomissaocurso fornecedorComissao =
	 * fornecedorComissaoCursoFacade.consultar(
	 * fornecedorCidade.getFornecedor().getIdfornecedor(),
	 * fornecedorCidade.getCidade().getPais().getIdpais()); Vendascomissao
	 * vendasComissao = null; vendasComissao = new Vendascomissao();
	 * vendasComissao.setVendas(venda); vendasComissao.setPaga("Não");
	 * 
	 * Vendas venda = new Vendas(); if (venda.getIdvendas() == null) {
	 * venda.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
	 * venda.setValor(valorTotal); Produtos produto =
	 * ConsultaBean.getProdtuo(aplicacaoMB.getParametrosprodutos().getCursos());
	 * venda.setProdutos(produto); } ForneceorComissaoFA ComissaoCursoBean
	 * comissaoCursoBean = new ComissaoCursoBean(aplicacaoMB, venda,
	 * orcamento.getOrcamentoprodutosorcamentoList(), fornecedorComissao,
	 * formaPagamento.getParcelamentopagamentoList(), curso.getDataInicio(),
	 * vendascomissao, formaPagamento.getValorJuros(), false); FacesContext fc =
	 * FacesContext.getCurrentInstance(); HttpSession session = (HttpSession)
	 * fc.getExternalContext().getSession(false);
	 * session.setAttribute("vendascomissao", vendascomissao);
	 * session.setAttribute("percentualcomissao",
	 * comissaoCursoBean.getPercentualComissao()); Map<String, Object> options = new
	 * HashMap<String, Object>(); options.put("contentWidth", 380);
	 * RequestContext.getCurrentInstance().openDialog("calcularcomissao", options,
	 * null); return ""; }
	 */

	public void habilitarMargem() {
		if (margem) {
			margem = false;
		} else margem = true;
	}
	
	public void fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	public void gerarListasValoresAupair() {
		String sql = "select v from Valoresaupair v where v.fornecedorcidade.idfornecedorcidade="
				+ fornecedorCidade.getIdfornecedorcidade() + " and v.situacao='Ativo'";
		ValorAupairFacade valorAupairFacade = new ValorAupairFacade();
		listaValoresAupair = valorAupairFacade.listar(sql);
		if (listaValoresAupair == null) {
			listaValoresAupair = new ArrayList<Valoresaupair>(); 
		}
	}
	
	public void gerarListasValoresProgramasTeens() {
		String sql = "select v from Valoresprogramasteens v where v.fornecedorcidade.idfornecedorcidade="
				+ fornecedorCidade.getIdfornecedorcidade() + " and v.situacao='Ativo'";
		ValoresProgramasTeensFacade valoresProgramasTeensFacade = new ValoresProgramasTeensFacade();
		listaValoresProgramasTeens = valoresProgramasTeensFacade.listar(sql);
		if (listaValoresProgramasTeens == null) {
			listaValoresProgramasTeens = new ArrayList<Valoresprogramasteens>(); 
		}
	} 
	
	public void gerarListasValoresTrainee() {
		String sql = "select v from Valorestrainee v where v.fornecedorcidade.idfornecedorcidade="
				+ fornecedorCidade.getIdfornecedorcidade() + " and v.situacao='Ativo' and v.tipotrainee='EUA'";
		ValoresTraineeFacade valoresTraineeFacade = new ValoresTraineeFacade();
		listaValoresTrainee = valoresTraineeFacade.listar(sql);
		if (listaValoresTrainee == null) {
			listaValoresTrainee = new ArrayList<Valorestrainee>(); 
		}
	} 
	
	public void gerarListasValoresWork() {
		String sql = "select v from Valoreswork v where v.situacao='Ativo' and v.fornecedorcidade.idfornecedorcidade='"
			+ fornecedorCidade.getIdfornecedorcidade() 
			+ "' order by v.idvaloresWork DESC";
		ValoresWorkFacade valoresWorkFacade = new ValoresWorkFacade();
		listaValoresWork = valoresWorkFacade.listar(sql); 
		if (listaValoresWork == null) {
			listaValoresWork = new ArrayList<Valoreswork>(); 
		}
	} 
	
	public void gerarListasValoresHighSchool() {
		String sql = "select v from Valoreshighschool v where v.fornecedorcidade.idfornecedorcidade="
				+ fornecedorCidade.getIdfornecedorcidade() + " and (v.situacao='Ativo' or v.situacao='Temporário')"
				+ " and v.datavalidade>='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
		ValoresHighSchoolFacade valoresHighSchoolFacade = new ValoresHighSchoolFacade();
		listaValoresHighSchool = valoresHighSchoolFacade.listar(sql);
		if (listaValoresHighSchool == null) {
			listaValoresHighSchool = new ArrayList<Valoreshighschool>(); 
		}
	} 
	

	public String juncaoInicio(Valoreshighschool valores){
		return valores.getInicio() + " " + valores.getAnoinicio() + " - " + valores.getDuracao();
	}
	
	public void veficicarPrograma() {
		if(aupair) { 
			gerarListasValoresAupair();
		}else if(cursosTeens) {
			gerarListasValoresProgramasTeens();
		}else if(highschool) {
			gerarListasValoresHighSchool();
		}else if(trainee) {
			gerarListasValoresTrainee();
		}else if(work) {
			gerarListasValoresWork();
		}
	}
	
	public void buscarValorPrograma() {
		if(aupair && valoresAupair!=null && valoresAupair.getIdvaloresAupair()!=null) {  
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valoresAupair.getMoedas());
			valorTotal = valoresAupair.getValorgross()*cambio.getValor();
		}else if(cursosTeens && valoresProgramasTeens!=null && valoresProgramasTeens.getIdvaloresprogramasteens()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valoresProgramasTeens.getMoedas());
			valorTotal = valoresProgramasTeens.getValorgross()*cambio.getValor();
		}else if(highschool && valoresHighSchool!=null && valoresHighSchool.getIdvaloresHighSchool()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valoresHighSchool.getMoedas());
			valorTotal = valoresHighSchool.getValorgross()*cambio.getValor();
		}else if(trainee && valorestrainee!=null && valorestrainee.getIdvalorestrainee()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valorestrainee.getMoedas());
			valorTotal = valorestrainee.getValorgross()*cambio.getValor();
		}else if(work && valoresWork!=null && valoresWork.getIdvaloresWork()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valoresWork.getMoedas());
			valorTotal = valoresWork.getValorgross()*cambio.getValor();
		}
	}
	
	public void gerarListaProdutos(){
	 	produtoOrcamento = new Produtosorcamento();
        FiltroOrcamentoProdutoFacade filtroOrcamentoFacade = new FiltroOrcamentoProdutoFacade();
        listaProdutosOrcamento = filtroOrcamentoFacade.pesquisar("select f from Filtroorcamentoproduto f "
        		+ "where f.produtos.idprodutos="+produto.getIdprodutos()+" order by f.produtosorcamento.descricao");
        if(listaProdutosOrcamento==null){
        		listaProdutosOrcamento = new ArrayList<Filtroorcamentoproduto>();
        }
    }
	
	public void valorParcelamento() {
		if(valorTotal>0 && valorEntrada>0) {
			saldoParcelar = valorTotal - valorEntrada;
			//calcularCustoFranquia();
		}
	}

	public String numeroColunas() {
		if(curso) {
			return "3";
		}else return "2";
	}
	
	public String classColunas() {
		if(curso) {
			return "tamanho14, cadCliente2, cadCliente2";
		}else return "tamanho14, tamanho14";
	}
	
	public String tamanhoPainel() {
		if(curso) {
			return "97%";
		}else return "35%";
	}
}
