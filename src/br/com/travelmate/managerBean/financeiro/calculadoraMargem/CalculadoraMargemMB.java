package br.com.travelmate.managerBean.financeiro.calculadoraMargem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
 
import br.com.travelmate.bean.DesagioBean;
import br.com.travelmate.bean.comissao.CalcularComissaoBean;
import br.com.travelmate.bean.comissao.ComissaoAuPairBean;
import br.com.travelmate.bean.comissao.ComissaoCursoBean;
import br.com.travelmate.bean.comissao.ComissaoDemiPairBean;
import br.com.travelmate.bean.comissao.ComissaoHEInscricaoBean;
import br.com.travelmate.bean.comissao.ComissaoHighSchoolBean;
import br.com.travelmate.bean.comissao.ComissaoPacotesBean;
import br.com.travelmate.bean.comissao.ComissaoProgramasTeensBean;
import br.com.travelmate.bean.comissao.ComissaoSeguroBean;
import br.com.travelmate.bean.comissao.ComissaoTraineeBean;
import br.com.travelmate.bean.comissao.ComissaoVistoBean;
import br.com.travelmate.bean.comissao.ComissaoVoluntariadoBean;
import br.com.travelmate.bean.comissao.ComissaoWorkBean;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
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
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
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
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Vistos;
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
	private float margemFinal;
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
    private float valorComissionavel;

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

	 

	public float getMargemFinal() {
		return margemFinal;
	}

	public void setMargemFinal(float margemFinal) {
		this.margemFinal = margemFinal;
	}

	public float getValorComissionavel() {
		return valorComissionavel;
	}

	public void setValorComissionavel(float valorComissionavel) {
		this.valorComissionavel = valorComissionavel;
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
			valorComissionavel = valoresAupair.getValorgross()*cambio.getValor();
		}else if(cursosTeens && valoresProgramasTeens!=null && valoresProgramasTeens.getIdvaloresprogramasteens()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valoresProgramasTeens.getMoedas());
			valorComissionavel = valoresProgramasTeens.getValorgross()*cambio.getValor();
		}else if(highschool && valoresHighSchool!=null && valoresHighSchool.getIdvaloresHighSchool()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valoresHighSchool.getMoedas());
			valorComissionavel = valoresHighSchool.getValorgross()*cambio.getValor();
		}else if(trainee && valorestrainee!=null && valorestrainee.getIdvalorestrainee()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valorestrainee.getMoedas());
			valorComissionavel = valorestrainee.getValorgross()*cambio.getValor();
		}else if(work && valoresWork!=null && valoresWork.getIdvaloresWork()!=null) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), valoresWork.getMoedas());
			valorComissionavel = valoresWork.getValorgross()*cambio.getValor();
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

	
	public String calcularCustoFranquia() {
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
		juros = comissaoBean.calcularJurosFranquia(listaParcelamento, dataInicio);
		custoTotal = desagio + juros;
		custoFranquia = juros + desagio;
		if (usuarioLogadoMB.getUsuario().getUnidadenegocio().getTipo().equalsIgnoreCase("Express")) {
			custoFranquia = custoTotal / 3;
		} else custoFranquia = custoTotal / 2;
		return "";
	
	}
	

	public String numeroColunas() {
		if(curso) {
			return "2";
		}else return "1";
	}
	
	public String classColunas() {
		if(curso) {
			return "tamanho7, tamanho7";
		}else return "tamanho7";
	} 
	
	public void habilitarMargem() {
		if (margem) {
			margem = false;
		} else margem = true;
	} 
	
	public void iniciarCalculoMargemCompleta() {
		Vendas venda = gerarVenda();
		venda.setFormapagamento(gerarFormaPagamento());
		venda.setOrcamento(gerarOrcamento(venda));
		
		int idProduto = produto.getIdprodutos();
		margemFinal= 0.0f;
		if (idProduto==aplicacaoMB.getParametrosprodutos().getCursos()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamento);
			venda.getOrcamento().getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
			Fornecedorcomissaocurso fornecedorcomissaocurso = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
					fornecedorCidade.getCidade().getPais().getIdpais());
			ComissaoCursoBean cc = new ComissaoCursoBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(), fornecedorcomissaocurso,
					venda.getFormapagamento().getParcelamentopagamentoList(), dataInicio, new Vendascomissao(), 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		} else if (idProduto==aplicacaoMB.getParametrosprodutos().getAupair()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(16));
			venda.getOrcamento().getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			
			ComissaoAuPairBean cc = new ComissaoAuPairBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(), venda.getCambio().getValor(), valoresAupair,
					venda.getFormapagamento().getParcelamentopagamentoList(), dataInicio, new Vendascomissao(), 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		}  else if (idProduto==aplicacaoMB.getParametrosprodutos().getHighSchool()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(12));
			venda.getOrcamento().getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			ComissaoHighSchoolBean cc = new ComissaoHighSchoolBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(),
					venda.getCambio(), valoresHighSchool, venda.getFormapagamento().getParcelamentopagamentoList(), new Vendascomissao(), dataInicio, 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		}  else if (idProduto==aplicacaoMB.getParametrosprodutos().getDemipair()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(465));
			venda.getOrcamento().getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
			Fornecedorcomissaocurso fornecedorcomissaocurso = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
					fornecedorCidade.getCidade().getPais().getIdpais());
			ComissaoDemiPairBean cc = new ComissaoDemiPairBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(),
					fornecedorcomissaocurso, venda.getFormapagamento().getParcelamentopagamentoList(), dataInicio, new Vendascomissao(), 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		} else if (idProduto==aplicacaoMB.getParametrosprodutos().getTrainee()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(31));
			venda.getOrcamento().getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			ComissaoTraineeBean cc = new ComissaoTraineeBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(), venda.getCambio().getValor(),
					valorestrainee, venda.getFormapagamento().getParcelamentopagamentoList(), dataInicio, new Vendascomissao(), 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		} else if (idProduto==aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(29));
			venda.getOrcamento().getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
			Fornecedorcomissaocurso fornecedorcomissaocurso = fornecedorComissaoCursoFacade.consultar(fornecedorCidade.getFornecedor().getIdfornecedor(),
					fornecedorCidade.getCidade().getPais().getIdpais());
			ComissaoVoluntariadoBean cc = new ComissaoVoluntariadoBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(),
					fornecedorcomissaocurso, venda.getFormapagamento().getParcelamentopagamentoList(), dataInicio, new Vendascomissao(), 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		} else if (idProduto==aplicacaoMB.getParametrosprodutos().getWork()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(15));
			venda.getOrcamento().getOrcamentoprodutosorcamentoList().add(orcamentoprodutosorcamento);
			ComissaoWorkBean cc = new ComissaoWorkBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(), venda.getCambio().getValor(),
					valoresWork, venda.getFormapagamento().getParcelamentopagamentoList(), new Vendascomissao(), 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		} else if (idProduto==aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
			Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
			orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
			orcamentoprodutosorcamento.setValorMoedaNacional(valorComissionavel);
			orcamentoprodutosorcamento.setImportado(false);
			orcamentoprodutosorcamento.setOrcamento(venda.getOrcamento());
			orcamentoprodutosorcamento.setObrigatorio(false);
			ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
			orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(2));
			ComissaoProgramasTeensBean cc = new ComissaoProgramasTeensBean(aplicacaoMB, venda, venda.getOrcamento().getOrcamentoprodutosorcamentoList(),
					venda.getCambio().getValor(), valoresProgramasTeens, venda.getFormapagamento().getParcelamentopagamentoList(), dataInicio, new Vendascomissao(), 0.0f, false);
			margemFinal = cc.getVendasComissao().getLiquidofranquia();
		} 
		
	}
	
	public Orcamento gerarOrcamento(Vendas venda) {
		Orcamento orcamento = new Orcamento();
		orcamento.setCambio(venda.getCambio());
		orcamento.setValorCambio(venda.getCambio().getValor());
		orcamento.setOrcamentoprodutosorcamentoList(popularOProdutoOcamento(orcamento));
 		return orcamento;
	}
	
	
	public List<Orcamentoprodutosorcamento> popularOProdutoOcamento(Orcamento orcamento) {
		List<Orcamentoprodutosorcamento> listaProdutos = new  ArrayList<Orcamentoprodutosorcamento>();
		Orcamentoprodutosorcamento orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
		orcamentoprodutosorcamento.setValorMoedaNacional(assessoriaTM);
		orcamentoprodutosorcamento.setImportado(false);
		orcamentoprodutosorcamento.setOrcamento(orcamento);
		orcamentoprodutosorcamento.setObrigatorio(false);
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(8));
		listaProdutos.add(orcamentoprodutosorcamento);
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
		orcamentoprodutosorcamento.setValorMoedaNacional(descontoMatriz);
		orcamentoprodutosorcamento.setImportado(false);
		orcamentoprodutosorcamento.setOrcamento(orcamento);
		orcamentoprodutosorcamento.setObrigatorio(false);
		produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(33));
		listaProdutos.add(orcamentoprodutosorcamento);
		orcamentoprodutosorcamento = new Orcamentoprodutosorcamento();
		orcamentoprodutosorcamento.setValorMoedaEstrangeira(0.0f);
		orcamentoprodutosorcamento.setValorMoedaNacional(descontoloja);
		orcamentoprodutosorcamento.setImportado(false);
		orcamentoprodutosorcamento.setOrcamento(orcamento);
		orcamentoprodutosorcamento.setObrigatorio(false);
		produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		orcamentoprodutosorcamento.setProdutosorcamento(produtoOrcamentoFacade.consultar(9));
		listaProdutos.add(orcamentoprodutosorcamento);
		return listaProdutos;
	}
	
	
	
	public Formapagamento gerarFormaPagamento() {
		Formapagamento forma = new Formapagamento();
		List<Parcelamentopagamento> listaParcelamento = new ArrayList<Parcelamentopagamento>();
		Parcelamentopagamento parcelamento = new Parcelamentopagamento();
		parcelamento.setFormaPagamento("Cartão de crédito");
		parcelamento.setNumeroParcelas(numeroParcelas);
		parcelamento.setTipoParcelmaneto("Matriz");
		parcelamento.setValorParcelamento(saldoParcelar);
		parcelamento.setDiaVencimento(dataVenda);
		parcelamento.setValorParcela(saldoParcelar / numeroParcelas);
		listaParcelamento.add(parcelamento);
		forma.setCondicao("A PRAZO");
		forma.setParcelamentopagamentoList(listaParcelamento);
		forma.setPossuiJuros("Não");
		forma.setValordesconto(0.0f);
		forma.setValorJuros(0.0f);
		forma.setValorOrcamento(this.valorTotal);
		forma.setValorTotal(this.valorTotal);
		return forma;
		
	}
	
	public Vendas gerarVenda() {
		Vendas venda = new Vendas();
		venda.setDataVenda(dataVenda);
		Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), pais.getMoedas().getIdmoedas());
		venda.setCambio(cambio);
		venda.setFornecedorcidade(fornecedorCidade);
		venda.setProdutos(produto);
		venda.setValorcambio(cambio.getValor());
		venda.setUsuario(usuarioLogadoMB.getUsuario());
		venda.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		venda.setValor(valorComissionavel);
		return venda;
	}
	
}
