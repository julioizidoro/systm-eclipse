package br.com.travelmate.managerBean.orcamentoManual;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.ModeloOrcamentoCursoFacade;
import br.com.travelmate.facade.OrcamentoCursoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ProdutoRemessaFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Modeloorcamentocurso;
import br.com.travelmate.model.Modeloorcamentocursoforma;
import br.com.travelmate.model.Modeloprodutoorcamentocurso;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtoremessa;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadModeloOrcaManualMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Modeloorcamentocurso modeloOrcamentoCurso;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private Fornecedorcidade fornecedorCidade;
	private List<Cidade> listaCidade;
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
	private Modeloorcamentocursoforma formaPagamento;
	private String habilitarFormaPagamento2 = "true";
	private String habilitaFormaPagamento03 = "true";
	private String habilitaFormaPagamento04 = "true";
	private Date dataCambio;
	private List<Modeloprodutoorcamentocurso> listaProdutos;
	private Modeloprodutoorcamentocurso modeloProdutos;
	private String moedaNacional;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		modeloOrcamentoCurso = (Modeloorcamentocurso) session.getAttribute("modeloOrcamentoCurso");
		session.removeAttribute("modeloOrcamentoCurso");
		gerarListaProdutos();
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = 0;
		idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		listaPais = paisProdutoFacade.listar(idProduto);
		gerarListaCursos();
		carregarComboMoedas();
		if (modeloOrcamentoCurso == null) {
			iniciarNovoOrcamento();
			dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
		} else {
			iniciarAlteracaoOrcamento();
		}
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	}

	public Modeloorcamentocurso getModeloOrcamentoCurso() {
		return modeloOrcamentoCurso;
	}

	public void setModeloOrcamentoCurso(Modeloorcamentocurso modeloOrcamentoCurso) {
		this.modeloOrcamentoCurso = modeloOrcamentoCurso;
	}

	public Modeloorcamentocursoforma getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Modeloorcamentocursoforma formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<Modeloprodutoorcamentocurso> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Modeloprodutoorcamentocurso> listaProdutos) {
		this.listaProdutos = listaProdutos;
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

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
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

	public Date getDataCambio() {
		return dataCambio;
	}

	public void setDataCambio(Date dataCambio) {
		this.dataCambio = dataCambio;
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
	
	

	public Modeloprodutoorcamentocurso getModeloProdutos() {
		return modeloProdutos;
	}

	public void setModeloProdutos(Modeloprodutoorcamentocurso modeloProdutos) {
		this.modeloProdutos = modeloProdutos;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
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


	public void carregarCamposAcomodacao() {
		if (modeloOrcamentoCurso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
			modeloOrcamentoCurso.setTipoQuarto("Sem quarto");
			modeloOrcamentoCurso.setRefeicoes("Sem Refeição");
			modeloOrcamentoCurso.setDuracaoAcomodacao(null);
		} else {
			camposAcomodacao = "false";
		}
	}

	public void calcularValorTotalOrcamento() {
		if (aplicacaoMB.getParametrosprodutos().isRemessaativa()) {
			calcularImpostoRemessa();
		}
		if (listaProdutos != null) {
			totalMoedaEstrangeira = 0.0f;
			totalMoedaReal = 0.0f;
			valorTotal = 0.0f;
			float valorMoedaReal = 0.0f;
			float valorMoedaEstrangeira = 0.0f;
			for (int i = 0; i < listaProdutos.size(); i++) {
				int idProdutoOrcamento = listaProdutos.get(i).getProdutosorcamento().getIdprodutosOrcamento();
				int descontoLoja = aplicacaoMB.getParametrosprodutos().getDescontoloja();
				int descontoMatriz = aplicacaoMB.getParametrosprodutos().getDescontomatriz();
				int promocaoEscola = aplicacaoMB.getParametrosprodutos().getPromocaoescola();
				if (idProdutoOrcamento == descontoLoja) {
					valorMoedaReal = listaProdutos.get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = listaProdutos.get(i).getValorMoedaEstrangeira() * -1;
				} else if (idProdutoOrcamento == descontoMatriz) {
					valorMoedaReal = listaProdutos.get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = listaProdutos.get(i).getValorMoedaEstrangeira() * -1;
				} else if (idProdutoOrcamento == promocaoEscola) {
					valorMoedaReal = listaProdutos.get(i).getValorMoedaNacional() * -1;
					valorMoedaEstrangeira = listaProdutos.get(i).getValorMoedaEstrangeira() * -1;
				} else {
					valorMoedaReal = listaProdutos.get(i).getValorMoedaNacional();
					valorMoedaEstrangeira = listaProdutos.get(i).getValorMoedaEstrangeira();
				}
				valorTotal = valorTotal + valorMoedaReal;
				totalMoedaEstrangeira = totalMoedaEstrangeira + valorMoedaEstrangeira;
				totalMoedaReal = totalMoedaReal + valorMoedaReal;
			}
			modeloOrcamentoCurso.setValor(valorTotal);
			formaPagamento.setAVista(modeloOrcamentoCurso.getValor());

		}
	}

	private void calcularImpostoRemessa() {
		ProdutoRemessaFacade produtoRemessaFacade = new ProdutoRemessaFacade();
		List<Produtoremessa> listaProdutoRemessa = null;
		try {
			listaProdutoRemessa = produtoRemessaFacade
					.listar(aplicacaoMB.getParametrosprodutos().getVoluntariado());
		} catch (Exception e) {
			e.printStackTrace();
		}
		float valorremessa = 0.0f;
		if (listaProdutoRemessa != null) {
			for (int i = 0; i < listaProdutos.size(); i++) {
				int idProduto = listaProdutos.get(i).getProdutosorcamento().getIdprodutosOrcamento();
				for (int n = 0; n < listaProdutoRemessa.size(); n++) {
					int idRemessa = listaProdutoRemessa.get(n).getProdutosorcamento().getIdprodutosOrcamento();
					if (idProduto == idRemessa) {
						valorremessa = valorremessa + listaProdutos.get(i).getValorMoedaNacional();
					}
				}
			}
		}
		if (valorremessa > 0) {
			boolean achou = false;
			valorremessa = valorremessa * (aplicacaoMB.getParametrosprodutos().getPercentualremessa() / 100);
			int idRemessa = aplicacaoMB.getParametrosprodutos().getProdutoremessa();
			for (int i = 0; i < listaProdutos.size(); i++) {
				int idProduto = listaProdutos.get(i).getProdutosorcamento().getIdprodutosOrcamento();
				if (idRemessa == idProduto) {
					listaProdutos.get(i).setValorMoedaNacional(valorremessa);
					listaProdutos.get(i).setValorMoedaEstrangeira(valorremessa / cambio.getValor());
					achou = true;
					i = 10000;
				}
			}
			if (!achou) {
				ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
				Produtosorcamento produtosorcamento = produtoOrcamentoFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getProdutoremessa());
				Modeloprodutoorcamentocurso produtos = new Modeloprodutoorcamentocurso();
				produtos.setProdutosorcamento(produtosorcamento);
				produtos.setValorMoedaEstrangeira(valorremessa / cambio.getValor());
				produtos.setValorMoedaNacional(valorremessa);
				listaProdutos.add(produtos);
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
		modeloOrcamentoCurso.setValorCambio(valorCambio);
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
		if (modeloOrcamentoCurso.getValorCambio() > 0) {
			int idProdTx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
			if (produtosorcamento.getIdprodutosOrcamento() != idProdTx) {
				if (produtosorcamento != null) {
					modeloProdutos.setProdutosorcamento(produtosorcamento);
					if ((modeloProdutos.getValorMoedaEstrangeira() > 0) && (modeloOrcamentoCurso.getValorCambio() > 0)) {
						modeloProdutos.setValorMoedaNacional(
								modeloProdutos.getValorMoedaEstrangeira() * modeloOrcamentoCurso.getValorCambio());
					} else {
						if ((modeloProdutos.getValorMoedaNacional() > 0) && (modeloOrcamentoCurso.getValorCambio() > 0)) {
							modeloProdutos.setValorMoedaEstrangeira(
									modeloProdutos.getValorMoedaNacional() / modeloOrcamentoCurso.getValorCambio());
						}
					}
					listaProdutos.add(modeloProdutos);
					calcularValorTotalOrcamento();
					produtosorcamento = null;
					modeloProdutos = new Modeloprodutoorcamentocurso();
				}
			} else {
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Taxa TM já esta inclusa", ""));
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio não selecionado", ""));
		}
	}

	public void excluirProdutoOrcamento(String linha) {
		int ilinha = Integer.parseInt(linha);
		int tx = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		if (listaProdutos.get(ilinha).getProdutosorcamento().getIdprodutosOrcamento() == tx) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Taxa TM não pode ser Excluída.", ""));
		} else {
			if (ilinha >= 0) {
				if(listaProdutos.get(ilinha).getIdprodutoOrcamentoCurso()!=null){
					ModeloOrcamentoCursoFacade modeloOrcamentoCursoFacade = new ModeloOrcamentoCursoFacade();
					modeloOrcamentoCursoFacade.excluirModleoProdutoOrcamentoCurso(listaProdutos.get(ilinha).getIdprodutoOrcamentoCurso());
				}
				listaProdutos.remove(ilinha);
				calcularValorTotalOrcamento();
			}
		}
	}

	public void verificarFormaPgamento02() throws SQLException {
		if (formaPagamento.isSelecionado2()) {
			habilitarFormaPagamento2 = "false";
			formaPagamento.setNumeroParcelas02(1);
			formaPagamento.setPercentualEntrada2(30.0);
			formaPagamento.setPercentualSaldo2(70.0);
			formaPagamento.setValorEntrada2(0.0f);
			formaPagamento.setValorParcela02(0.0f);
			formaPagamento.setValorParcela02(0.0f);
			calcularParcelamento();
		} else {
			habilitarFormaPagamento2 = "tre";
			formaPagamento.setPercentualEntrada2(0.0);
			formaPagamento.setValorEntrada2(0.0f);
			formaPagamento.setNumeroParcelas02(1);
			formaPagamento.setPercentualSaldo2(0.0);
			formaPagamento.setValorParcela02(0.0f);
			formaPagamento.setValorSaldo2(0.0f);
		}
	}

	public void calcularParcelamento() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		formaPagamento.setAVista(modeloOrcamentoCurso.getValor());
		if (this.formaPagamento.isSelecionado2()) {
			if (formaPagamento.getPercentualEntrada2() > 0) {
				valorEntrada = formaPagamento.getAVista()
						* (formaPagamento.getPercentualEntrada2() / 100);
				saldo = 100 - formaPagamento.getPercentualEntrada2();
				valorSaldo = formaPagamento.getAVista() - valorEntrada;
				formaPagamento.setValorEntrada2(valorEntrada.floatValue());
				formaPagamento.setValorSaldo2(valorSaldo.floatValue());
				formaPagamento.setPercentualSaldo2(saldo);
			}
			valorSaldo = 0.0;
			if (formaPagamento.getNumeroParcelas02() > 1) {
				valorSaldo = formaPagamento.getValorSaldo2().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / formaPagamento.getNumeroParcelas02();
					formaPagamento.setValorParcela02(valorSaldo.floatValue());
				}
			}
		}

		// Opção 03

		if (this.formaPagamento.isSelecionado3()) {

			valorSaldo = 0.0;
			saldo = 0.0;
			valorEntrada = 0.0;
			if (formaPagamento.getPercentualEntrada3() > 0) {
				valorEntrada = modeloOrcamentoCurso.getValor() * (formaPagamento.getPercentualEntrada3() / 100);
				saldo = 100 - formaPagamento.getPercentualEntrada3();
				valorSaldo = modeloOrcamentoCurso.getValor() - valorEntrada;
				formaPagamento.setValorEntrada3(valorEntrada.floatValue());
				formaPagamento.setValorSaldo3(valorSaldo.floatValue());
				formaPagamento.setPercentualSaldo3(saldo.doubleValue());
			}
			valorSaldo = 0.0;
			if (formaPagamento.getNumeroParcelas03() > 0) {
				if (formaPagamento.getValorSaldo3() > 0) {
					valorSaldo = formaPagamento.getValorSaldo3().doubleValue();
					if (valorSaldo > 0) {
						CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
						Coeficientejuros cf = coneficienteJurosFacade
								.consultar(formaPagamento.getNumeroParcelas03(), "Juros Cliente");
						valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
						formaPagamento.setValorParcela03(valorSaldo.floatValue());
					}
				}
			}
		}

		// opção 04
		if (this.formaPagamento.isSelecionado4()) {
			if (formaPagamento.getNumeroParcelasFinanciamento() > 0) {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade
						.consultar(formaPagamento.getNumeroParcelasFinanciamento(), "Juros Banco");
				Double valor = formaPagamento.getAVista().doubleValue() * cf.getCoeficiente();
				formaPagamento.setFinaciamento(valor.floatValue());
			}
		}
	}

	public void verificarFormaPgamento03() throws SQLException {
		if (formaPagamento.isSelecionado3()) {
			habilitaFormaPagamento03 = "false";
			formaPagamento.setNumeroParcelas03(1);
			formaPagamento.setPercentualEntrada3(30.0);
			formaPagamento.setPercentualSaldo3(70.0);
			formaPagamento.setValorEntrada3(0.0f);
			formaPagamento.setValorSaldo3(0.0f);
			formaPagamento.setValorEntrada3(0.0f);
			calcularParcelamento();
		} else
			habilitaFormaPagamento03 = "true";
	}

	public void verificarFormaPgamento04() throws SQLException {
		if (formaPagamento.isSelecionado4()) {
			habilitaFormaPagamento04 = "false";
			formaPagamento.setNumeroParcelasFinanciamento(1);
			formaPagamento.setFinaciamento(0.0f);
			calcularParcelamento();
		} else
			habilitaFormaPagamento04 = "true";
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
		modeloOrcamentoCurso = new Modeloorcamentocurso();
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Modeloprodutoorcamentocurso>();
		}
		formaPagamento = new Modeloorcamentocursoforma();
		cidade = new Cidade();
		formaPagamento.setAVista(0.0f);
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		modeloProdutos = new Modeloprodutoorcamentocurso();
		Produtosorcamento produtosorcamento = orcamentoCursoFacade
				.consultarProdutoOrcamentoCurso(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		modeloProdutos.setProdutosorcamento(produtosorcamento);
		modeloProdutos.setValorMoedaEstrangeira(0.0f);
		modeloProdutos.setValorMoedaNacional(aplicacaoMB.getParametrosprodutos().getValorTaxaTM());
		listaProdutos.add(modeloProdutos);
		modeloProdutos = new Modeloprodutoorcamentocurso();
	}

	public void iniciarAlteracaoOrcamento() {
		if (modeloOrcamentoCurso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			camposAcomodacao = "true";
		} else {
			camposAcomodacao = "false";
		}
		fornecedorCidade = modeloOrcamentoCurso.getFornecedorcidade();
		cidade = fornecedorCidade.getCidade();
		pais = cidade.getPais();
		listarFornecedorCidade();
		cambio = modeloOrcamentoCurso.getCambio();
		valorCambio = modeloOrcamentoCurso.getValorCambio();
		fornecedorCidade = modeloOrcamentoCurso.getFornecedorcidade();
		ModeloOrcamentoCursoFacade modeloOrcamentoCursoFacade = new ModeloOrcamentoCursoFacade();
		listaProdutos = new ArrayList<Modeloprodutoorcamentocurso>();
		formaPagamento = modeloOrcamentoCursoFacade
				.consultarFormaPagamento(modeloOrcamentoCurso.getIdorcamentoCurso());
		modeloProdutos =new Modeloprodutoorcamentocurso();
		listaProdutos = modeloOrcamentoCursoFacade.listarProdutoOrcamentoCurso(modeloOrcamentoCurso.getIdorcamentoCurso());
		for(int i=0;i<listaProdutos.size();i++){
			if (listaProdutos.get(i).getValorMoedaNacional()<0){
				listaProdutos.get(i).setValorMoedaNacional(listaProdutos.get(i).getValorMoedaNacional()*-1);
			}
		}
		if (formaPagamento.getValorEntrada2() != null
				&& formaPagamento.getValorEntrada2() > 0) {
			habilitarFormaPagamento2 = "false";
			formaPagamento.setSelecionado2(true);
		}
		if (formaPagamento.getValorEntrada3() != null
				&& formaPagamento.getValorEntrada3() > 0) {
			habilitaFormaPagamento03 = "false";
			formaPagamento.setSelecionado3(true);
		}
		if (formaPagamento.getFinaciamento() != null
				&& formaPagamento.getFinaciamento() > 0) {
			habilitaFormaPagamento04 = "false";
			formaPagamento.setSelecionado4(true);
		}
		valorTotal = modeloOrcamentoCurso.getValor();
		moeda = cambio.getMoedas();
		
	}

	public void salvarOrcamento() {
		modeloOrcamentoCurso.setData(new Date());
		if (modeloOrcamentoCurso.getTipoAcomodacao().equalsIgnoreCase("Sem acomodação")) {
			modeloOrcamentoCurso.setTipoQuarto("Sem quarto");
			modeloOrcamentoCurso.setRefeicoes("Sem Refeição");
			modeloOrcamentoCurso.setDuracaoAcomodacao(0);
		}
		modeloOrcamentoCurso.setValorCambio(valorCambio);
		modeloOrcamentoCurso.setCambio(cambio);
		modeloOrcamentoCurso.setFornecedor(fornecedorCidade.getFornecedor());
		modeloOrcamentoCurso.setFornecedorcidade(fornecedorCidade);
		modeloOrcamentoCurso.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		modeloOrcamentoCurso.setUsuario(usuarioLogadoMB.getUsuario());
		modeloOrcamentoCurso.setValor(valorTotal);
		modeloOrcamentoCurso.setSituacao("Ativo");
		ModeloOrcamentoCursoFacade modeloOrcamentoCursoFacade = new ModeloOrcamentoCursoFacade();
		modeloOrcamentoCurso = modeloOrcamentoCursoFacade.salvar(modeloOrcamentoCurso);
		salvarOrcamentoProdutoOrcamento(modeloOrcamentoCurso);
	}

	public void salvarOrcamentoProdutoOrcamento(Modeloorcamentocurso modeloorcamentocurso) {
		if (listaProdutos != null) {
			ModeloOrcamentoCursoFacade modeloOrcamentoCursoFacade = new ModeloOrcamentoCursoFacade();
			for (int i = 0; i < listaProdutos.size(); i++) {
				listaProdutos.get(i).setModeloorcamentocurso(modeloorcamentocurso);
				modeloOrcamentoCursoFacade.salvar(listaProdutos.get(i));
			}
		} 
	}

	public void alterarValorCambio(String valor) {
		float novoValor = Formatacao.formatarStringfloat(valor);
		if (valorCambio != novoValor) {
			valorCambio = Formatacao.formatarStringfloat(valor);
			atualizarValoresProduto();
		} else
			Mensagem.lancarMensagemInfo("", "Valor não é diferente");
	}

	public void atualizarValoresProduto() {
		if (listaProdutos != null) {
			for (int i = 0; i < listaProdutos.size(); i++) {
				if (listaProdutos.get(i).getValorMoedaEstrangeira() > 0) {
					listaProdutos.get(i).setValorMoedaNacional(
							listaProdutos.get(i).getValorMoedaEstrangeira() * valorCambio);
				}
				calcularValorTotalOrcamento();
			}
		}
	}

	public String confirmarOrcamento() {
			if (fornecedorCidade != null && fornecedorCidade.getIdfornecedorcidade() != null) {
				if (moeda != null && moeda.getIdmoedas() != null) {
					if (validarFormaPagamento()) {
						salvarOrcamento();
						salvarOrcamentoFormaPagamento();
						Mensagem.lancarMensagemInfo("", "Modelo de Orçamento salvo com sucesso");
						return "consModeloOrcamentoManual";
					}
				} else
					Mensagem.lancarMensagemFatal("Moeda não selecionada", "");
			} else
				Mensagem.lancarMensagemFatal("Escola não selecionada", "");
		return "";
	}

	public boolean validarFormaPagamento() {
		if (formaPagamento.isSelecionado2()) {
			if (formaPagamento.getValorParcela02() == null
					|| formaPagamento.getValorParcela02() == 0) {
				Mensagem.lancarMensagemFatal("Informe nº parcelas", "Forma de pagamento - opção 2");
				return false;
			}
		}
		if (formaPagamento.isSelecionado3()) {
			if (formaPagamento.getValorParcela03() == null
					|| formaPagamento.getValorParcela03() == 0) {
				Mensagem.lancarMensagemFatal("Informe nº parcelas", "Forma de pagamento - opção 3");
				return false;
			}
		}
		if (formaPagamento.isSelecionado4()) {
			if (formaPagamento.getFinaciamento() == null
					|| formaPagamento.getFinaciamento() == 0) {
				Mensagem.lancarMensagemFatal("Informe nº parcelas", "Forma de pagamento - opção 4");
				return false;
			}
		}
		return true;
	}

	public String cancelarOrcamento() {
		return "consModeloOrcamentoManual";
	}

	public void salvarOrcamentoFormaPagamento() {
		if (formaPagamento == null) {
			formaPagamento = new Modeloorcamentocursoforma();
		}
		formaPagamento.setModeloorcamentocurso(modeloOrcamentoCurso);
		ModeloOrcamentoCursoFacade modeloOrcamentoCursoFacade = new ModeloOrcamentoCursoFacade();
		formaPagamento = modeloOrcamentoCursoFacade.salvar(formaPagamento);

	}

	public void carregarValorCambio() {
		cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		valorCambio = cambio.getValor();
		modeloOrcamentoCurso.setValorCambio(valorCambio);
		atualizarValoresProduto();
	}
	
	
	public void selecionarCambio() {
		if (pais != null && pais.getIdpais() != null) {
			moeda = pais.getMoedas();
			cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), moeda,
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			valorCambio = cambio.getValor();
			modeloOrcamentoCurso.setValorCambio(valorCambio);
		}
	}
}
