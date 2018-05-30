package br.com.travelmate.managerBean.turismo.passagemAerea;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
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

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.bean.ProgramasBean;
import br.com.travelmate.bean.comissao.ComissaoPassagemBean;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.OrcamentoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.PassagemFacade;
import br.com.travelmate.facade.PassagemPassageiroFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.TmRaceMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Passagemaerea;
import br.com.travelmate.model.Passagempassageiro;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadPassagemMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private MateRunnersMB mateRunnersMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	@Inject
	private TmRaceMB tmRaceMB;
	private Cliente cliente;
	private Fornecedorcidade fornecedorCidade;
	private Fornecedorcidade fornecedorCidadeAlterado;
	private Formapagamento formaPagamento;
	private float valorCambio;
	private float valorTotal = 0;
	private float valorJuros = 0;
	private float totalPagar = 0;
	private float valorEntrada = 0;
	private float valorParcelar = 0;
	private float valorParcela = 0;
	private float valorSaltoParcelar = 0;
	private String numeroParcelas;
	private Date dataPrimeiroPagamento;
	private String tipoParcelamento;
	private String formaPagamentoString;
	private Passagemaerea passagem;
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private Cambio cambio;
	private Vendas vendas;
	private String situacao = "PROCESSO";
	private boolean novaFicha = false;
	private Unidadenegocio unidadeNegocio;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Passagempassageiro passagempassageiro;
	private List<Passagempassageiro> listaPassageiros; 
	private float totalTarifa;
	private float totaltaxas;
	private float taxaemissao;
	private float totalcomissao;
	private float valorVendasAlterado;
	private Lead lead;
	private List<Usuario> listaUsuario;
	private Usuario usuarioFranquia;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Passagemaerea passagemaerea = (Passagemaerea) session.getAttribute("passagemaerea");
		cliente = (Cliente) session.getAttribute("cliente");
		lead = (Lead) session.getAttribute("lead");
		session.removeAttribute("cliente");
		session.removeAttribute("lead");  
		session.removeAttribute("passagemaerea");
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = 0;
		idProduto = aplicacaoMB.getParametrosprodutos().getPassagem();
		listaPais = paisProdutoFacade.listar(idProduto);
		PassagemFacade passagemFacade = new PassagemFacade();
		listaPais = paisProdutoFacade.listar(idProduto);
		listaUnidadeNegocio = GerarListas.listarUnidade();
		if (passagemaerea != null) {
			passagem = passagemFacade.consultarPassagem(passagemaerea.getIdpassagemAerea());
		}
		if (passagem == null) {
			passagem = new Passagemaerea();
			fornecedorCidade = new Fornecedorcidade();
			pais = new Pais();
			cidade = new Cidade();
			passagempassageiro = new Passagempassageiro();
			if(cliente==null){
				cliente = new Cliente();
			}
			vendas = new Vendas();
			formaPagamento = new Formapagamento();
			unidadeNegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			listaPassageiros = new ArrayList<Passagempassageiro>();
			valorVendasAlterado = 0.0f;
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade.listaUsuarioUnidade(unidadeNegocio.getIdunidadeNegocio());
		} else {
			vendas = passagem.getVendas();
			valorVendasAlterado = vendas.getValor();
			cliente = vendas.getCliente();
			unidadeNegocio = vendas.getUnidadenegocio();
			fornecedorCidade = vendas.getFornecedorcidade();
			cidade = fornecedorCidade.getCidade();
			pais = fornecedorCidade.getCidade().getPais();
			cambio = vendas.getCambio();
			listarFornecedorCidade(idProduto);
			passagempassageiro = new Passagempassageiro();
			FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
			this.formaPagamento = formaPagamentoFacade.consultar(vendas.getIdvendas());
			iniciarListaPassageiros();
			carregarCamposFormaPagamento();
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade.listaUsuarioUnidade(unidadeNegocio.getIdunidadeNegocio());
			if (passagem.getIdusuario() > 0) {
				usuarioFranquia = usuarioFacade.consultar(passagem.getIdusuario());
			}
		}
		if (cambio == null) {
			CambioFacade cambioFacade = new CambioFacade();
			cambio =cambioFacade.consultar(1);
		}
		valorCambio = cambio.getValor();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public Cambio getCambio() {
		return cambio;
	}

	public void setCambio(Cambio cambio) {
		this.cambio = cambio;
	}

	public Passagemaerea getPassagem() {
		return passagem;
	}

	public void setPassagem(Passagemaerea passagem) {
		this.passagem = passagem;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public float getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(float valorJuros) {
		this.valorJuros = valorJuros;
	}

	public float getTotalPagar() {
		return totalPagar;
	}

	public void setTotalPagar(float totalPagar) {
		this.totalPagar = totalPagar;
	}

	public float getValorEntrada() {
		return valorEntrada;
	}

	public void setValorEntrada(float valorEntrada) {
		this.valorEntrada = valorEntrada;
	}

	public float getValorParcelar() {
		return valorParcelar;
	}

	public void setValorParcelar(float valorParcelar) {
		this.valorParcelar = valorParcelar;
	}

	public float getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(float valorParcela) {
		this.valorParcela = valorParcela;
	}

	public float getValorSaltoParcelar() {
		return valorSaltoParcelar;
	}

	public void setValorSaltoParcelar(float valorSaltoParcelar) {
		this.valorSaltoParcelar = valorSaltoParcelar;
	}

	public String getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(String numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public Date getDataPrimeiroPagamento() {
		return dataPrimeiroPagamento;
	}

	public void setDataPrimeiroPagamento(Date dataPrimeiroPagamento) {
		this.dataPrimeiroPagamento = dataPrimeiroPagamento;
	}

	public String getTipoParcelamento() {
		return tipoParcelamento;
	}

	public void setTipoParcelamento(String tipoParcelamento) {
		this.tipoParcelamento = tipoParcelamento;
	}

	public String getFormaPagamentoString() {
		return formaPagamentoString;
	}

	public void setFormaPagamentoString(String formaPagamentoString) {
		this.formaPagamentoString = formaPagamentoString;
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public boolean isNovaFicha() {
		return novaFicha;
	}

	public void setNovaFicha(boolean novaFicha) {
		this.novaFicha = novaFicha;
	}

	public Fornecedorcidade getFornecedorCidadeAlterado() {
		return fornecedorCidadeAlterado;
	}

	public void setFornecedorCidadeAlterado(Fornecedorcidade fornecedorCidadeAlterado) {
		this.fornecedorCidadeAlterado = fornecedorCidadeAlterado;
	}

	public Unidadenegocio getUnidadeNegocio() {
		return unidadeNegocio;
	}

	public void setUnidadeNegocio(Unidadenegocio unidadeNegocio) {
		this.unidadeNegocio = unidadeNegocio;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Passagempassageiro getPassagempassageiro() {
		return passagempassageiro;
	}

	public void setPassagempassageiro(Passagempassageiro passagempassageiro) {
		this.passagempassageiro = passagempassageiro;
	}

	public List<Passagempassageiro> getListaPassageiros() {
		return listaPassageiros;
	}

	public void setListaPassageiros(List<Passagempassageiro> listaPassageiros) {
		this.listaPassageiros = listaPassageiros;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public float getTotalTarifa() {
		return totalTarifa;
	}

	public void setTotalTarifa(float totalTarifa) {
		this.totalTarifa = totalTarifa;
	}

	public float getTotaltaxas() {
		return totaltaxas;
	}

	public void setTotaltaxas(float totaltaxas) {
		this.totaltaxas = totaltaxas;
	}

	public float getTaxaemissao() {
		return taxaemissao;
	}

	public void setTaxaemissao(float taxaemissao) {
		this.taxaemissao = taxaemissao;
	}

	public float getTotalcomissao() {
		return totalcomissao;
	}

	public void setTotalcomissao(float totalcomissao) {
		this.totalcomissao = totalcomissao;
	}

	public float getValorVendasAlterado() {
		return valorVendasAlterado;
	}

	public void setValorVendasAlterado(float valorVendasAlterado) {
		this.valorVendasAlterado = valorVendasAlterado;
	}

	public MateRunnersMB getMateRunnersMB() {
		return mateRunnersMB;
	}

	public void setMateRunnersMB(MateRunnersMB mateRunnersMB) {
		this.mateRunnersMB = mateRunnersMB;
	}

	

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuarioFranquia() {
		return usuarioFranquia;
	}

	public void setUsuarioFranquia(Usuario usuarioFranquia) {
		this.usuarioFranquia = usuarioFranquia;
	}

	public String adicionarPassageiroBean() {
		if (passagempassageiro.getNome().length() > 0 && passagempassageiro.getCategoria().length() > 0) {
			if (passagempassageiro.getCategoria().equalsIgnoreCase("ADT")) {
				passagempassageiro
						.setValor(passagem.getAdttarifa() + passagem.getAdttaxas() + passagem.getAdttaxaemissao()); 
			} else if (passagempassageiro.getCategoria().equalsIgnoreCase("CHD")) {
				passagempassageiro
						.setValor(passagem.getChdtarifa() + passagem.getChdtaxas() + passagem.getChdtaxaemissao()); 
			} else {
				passagempassageiro
						.setValor(passagem.getInftarifa() + passagem.getInftaxas() + passagem.getInftaxaemissao()); 
			}
			listaPassageiros.add(passagempassageiro); 
			passagempassageiro = new Passagempassageiro();
			calcularValorTotal();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Atenção!", "Preencha todos os dados do cliente."));
		}
		return "";
	}

	public String removerPassageiroBean(String linha, Passagempassageiro pacotepassagempassageiro) {
		if (pacotepassagempassageiro.getIdpassagempassageiro() != null) {
			PassagemPassageiroFacade passagemPassageiroFacade = new PassagemPassageiroFacade();
			passagemPassageiroFacade.excluir(pacotepassagempassageiro.getIdpassagempassageiro()); 
			iniciarListaPassageiros();
		} else { 
			int nlinha = Integer.parseInt(linha);
			if (nlinha >= 0) {
				listaPassageiros.remove(nlinha);
			}
		}
		calcularValorTotal();
		return "";
	}

	public void iniciarListaPassageiros() {
		PassagemPassageiroFacade passagemPassageiroFacade = new PassagemPassageiroFacade();
		String sql = "SELECT p From Passagempassageiro p where p.passagemaerea.idpassagemAerea="
				+ passagem.getIdpassagemAerea() + " order by p.nome";
		listaPassageiros = passagemPassageiroFacade.lista(sql);
		if (listaPassageiros == null) {
			listaPassageiros = new ArrayList<Passagempassageiro>();
		}
		calcularValorTotal();
	}

	public void listarFornecedorCidade(int idProduto) {
		if (usuarioLogadoMB != null) {
			idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
		}
		if ((idProduto > 0) && (cidade != null)) {
			listaFornecedorCidade = GerarListas.listarFornecedorCidade(idProduto, cidade.getIdcidade());
		}
	}

	public String pesquisarCliente() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("turismo", "turismo");
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	// Forma de pagamento
	public void calcularValorTotalOrcamento() {
		if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Sim")) {
			if (vendas.getValor() != null) {
				valorTotal = 0.0f;
				valorTotal = vendas.getValor();
				if (formaPagamento == null) {
					formaPagamento = new Formapagamento();
				}
				formaPagamento.setValorOrcamento(valorTotal);
				totalPagar = valorTotal;
				if (formaPagamento.getPossuiJuros().equalsIgnoreCase("Sim")) {
					if (valorJuros > 0) {
						totalPagar = valorTotal + valorJuros;
						valorSaltoParcelar = totalPagar;
						valorParcelar = valorSaltoParcelar;
					}
				}
				calcularParcelamentoPagamento();
			}
		} else {
			valorJuros = 0.0f;
			valorTotal = vendas.getValor();
			totalPagar = valorTotal;
			valorSaltoParcelar = totalPagar;
		}
	}

	public void calcularParcelamentoPagamento() {
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			Float valorParcelado = 0.0f;
			for (int i = 0; i < formaPagamento.getParcelamentopagamentoList().size(); i++) {
				valorParcelado = valorParcelado
						+ formaPagamento.getParcelamentopagamentoList().get(i).getValorParcelamento();
			}
			valorSaltoParcelar = totalPagar - valorParcelado;
			valorParcelar = valorSaltoParcelar;
		}
	}

	public void calcularValorParcelas() {
		if (valorParcelar > 0) {
			int numero = Integer.parseInt(numeroParcelas);
			float vParcela = valorParcelar / numero;
			valorParcela = vParcela;
		}
	}

	public void adicionarFormaPagamento() {

		String msg = validarFormaPagamento();
		if (msg.length() < 5) {
			float saldoParcelar = this.valorParcelar;
			float valorParcela = this.valorParcela;
			if (valorParcela > saldoParcelar) {
			} else {
				Parcelamentopagamento parcelamento = new Parcelamentopagamento();
				parcelamento.setDiaVencimento(dataPrimeiroPagamento);
				parcelamento.setFormaPagamento(formaPagamentoString);
				int numeroParcelas = Integer.parseInt(this.numeroParcelas);
				parcelamento.setNumeroParcelas(numeroParcelas);
				parcelamento.setTipoParcelmaneto(tipoParcelamento);
				parcelamento.setValorParcela(valorParcela);
				parcelamento.setValorParcelamento(saldoParcelar);
				if (formaPagamento.getParcelamentopagamentoList() == null) {
					formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
				}
				if (formaPagamento != null) {
					parcelamento.setFormapagamento(formaPagamento);
				}
				if (vendas.getIdvendas()!=null){
					if (!vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
						ContasReceberBean contasReceberBean = new ContasReceberBean();
						parcelamento = contasReceberBean.gerarParcelasIndividuais(parcelamento, formaPagamento.getParcelamentopagamentoList().size(), vendas, usuarioLogadoMB, passagem.getDataviagem());
					}
				}
				if (parcelamento.getFormaPagamento().equalsIgnoreCase("Boleto")) {
					boolean horarioExcedido = false;
					int numeroAdicionar = 0;
					int diaSemana = Formatacao.diaSemana(parcelamento.getDiaVencimento());
					String horaAtual = Formatacao.foramtarHoraString();
					String horaMaxima = "16:00:00";
					Time horatime = null;
					Time horaMaxTime = null;
					try {
						horatime = Formatacao.converterStringHora(horaAtual);
						horaMaxTime = Formatacao.converterStringHora(horaMaxima);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (horatime.after(horaMaxTime)) {
						numeroAdicionar = 1;
						horarioExcedido = true;
					}
		
					if (diaSemana == 1) {
						numeroAdicionar = 2;
						horarioExcedido = true;
					}else if(diaSemana == 7) {
						numeroAdicionar = 3;
						horarioExcedido = true;
					}else if(diaSemana == 6) {
						numeroAdicionar = 4;
						horarioExcedido = true;
					}
					if (horarioExcedido) {
						try {
							parcelamento.setDiaVencimento(Formatacao.SomarDiasDatas(parcelamento.getDiaVencimento(), numeroAdicionar));
							Mensagem.lancarMensagemInfo("Primeira parcela efetuada para o próximo dia útil", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				formaPagamento.getParcelamentopagamentoList().add(parcelamento);
				calcularParcelamentoPagamento();
				valorParcela = 0;
				saldoParcelar = 0;
				this.valorParcela = 0;
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
		}
	}

	public String validarFormaPagamento() {
		formaPagamento.setValorJuros(valorJuros);
		String msg = "";
		if (formaPagamento.getCondicao() == null) {
			msg = msg + "Campo forma de pagamento obrigatório";
		}
		if (valorParcela <= 0) {
			msg = msg + "Valor da parcela não pode ser zero.";
		}
		if (dataPrimeiroPagamento == null) {
			msg = msg + "Data do 1º Vencimento Obrigatorio";
		}else {
			if (formaPagamentoString.equalsIgnoreCase("Boleto")){
				try {
					msg = msg + Formatacao.validarDataBoleto(dataPrimeiroPagamento);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(valorParcelar > (valorSaltoParcelar+0.01)){
			msg = msg + "Valor a parcelar mais alto que saldo em aberto.";
		}
		return msg;   
	}

	public void excluirformaPagamento(String ilinha) {
		int linha = Integer.parseInt(ilinha);
		if (linha >= 0) {
			if (formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento() != null) {
				ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
				parcelamentoPagamentoFacade.excluir(
						formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
				ContasReceberBean contasReceberBean = new ContasReceberBean();
				if (vendas.getIdvendas()!=null){
					if (!vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
						contasReceberBean.apagarContasReceber(formaPagamento.getParcelamentopagamentoList().get(linha), vendas.getIdvendas(), usuarioLogadoMB,
								formaPagamento.getParcelamentopagamentoList().get(linha).getIdparcemlamentoPagamento());
					}
				}
				if (contasReceberBean.getValorJaRecebido() > 0) {

					Parcelamentopagamento parcelamento = new Parcelamentopagamento();
					parcelamento.setDiaVencimento(new Date());
					parcelamento.setFormaPagamento("Saldo pago");

					parcelamento.setNumeroParcelas(01);
					parcelamento.setTipoParcelmaneto("Matriz");
					parcelamento.setValorParcela(contasReceberBean.getValorJaRecebido());
					parcelamento.setValorParcelamento(contasReceberBean.getValorJaRecebido());
					if (formaPagamento != null) {
						parcelamento.setFormapagamento(formaPagamento);
					}
					if (formaPagamento.getParcelamentopagamentoList() == null) {
						formaPagamento.setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
					}
					formaPagamento.getParcelamentopagamentoList().add(parcelamento);
				}
				formaPagamento.getParcelamentopagamentoList().remove(linha);
				calcularParcelamentoPagamento();
			} else {
				formaPagamento.getParcelamentopagamentoList().remove(linha);
				calcularParcelamentoPagamento();
			}
		}
	}

	public void carregarValorCambio() {
		valorCambio = cambio.getValor();
	}

	public void calcularValorTotal() {
		totalPagar = 0.0f;
		for (int i = 0; i < listaPassageiros.size(); i++) {
			totalPagar = totalPagar + listaPassageiros.get(i).getValor();
		}
		vendas.setValor(totalPagar);
		formaPagamento.setValorOrcamento(vendas.getValor());
		totalPagar = vendas.getValor();
		if (valorJuros > 0) {
			totalPagar = totalPagar + valorJuros;
		}
		valorSaltoParcelar = totalPagar;
		valorParcelar = valorSaltoParcelar;
	}

	public void carregarCamposFormaPagamento() {
		valorJuros = formaPagamento.getValorJuros();
		if (formaPagamento.getParcelamentopagamentoList() != null) {
			calcularParcelamentoPagamento();
		}
	}

	// salvar
	public String confirmarFichaPassagem() throws SQLException {
		String msg = validarDados();
		String nsituacao;
		if (msg.length() < 5) {
			if (situacao.equalsIgnoreCase("PROCESSO")) {
				String titulo = "";
				String operacao = "";
				String imagemNotificacao = "";
				nsituacao = "FINALIZADA";
				valorTotal = valorJuros + formaPagamento.getValorOrcamento();
				if (vendas.getIdvendas() == null) {
					titulo = "Nova Emissão de Passagem Aérea";
					operacao = "A";
					imagemNotificacao = "inserido";
				} else {
					titulo = "Alteração de Passagem Aérea";
					operacao = "I";
					imagemNotificacao = "alterado";
				}
				salvarVendas(nsituacao);
				if (novaFicha) {
					dashBoardMB.getVendaproduto().setProduto(dashBoardMB.getVendaproduto().getProduto() + 1);
					dashBoardMB.getMetamensal()
							.setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado() + vendas.getValor());
					dashBoardMB.getMetamensal().setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
							/ dashBoardMB.getMetamensal().getValormeta()) * 100);

					dashBoardMB.getMetaAnual()
							.setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada() + vendas.getValor());
					dashBoardMB.getMetaAnual().setPercentualalcancado(
							(dashBoardMB.getMetaAnual().getMetaalcancada() / dashBoardMB.getMetaAnual().getValormeta())
									* 100);

					dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() + vendas.getValor());
					dashBoardMB.setPercsemana(
							(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana())
									* 100);

					dashBoardMB.setValorFaturamento(dashBoardMB.getValorFaturamento() + vendas.getValor());
//					new Thread() {
//						@Override
//						public void run() {
							DashBoardBean dashBoardBean = new DashBoardBean();
							dashBoardBean.calcularNumeroVendasProdutos(vendas, false);
							dashBoardBean.calcularMetaMensal(vendas,valorVendasAlterado,false);
							dashBoardBean.calcularMetaAnual(vendas,valorVendasAlterado,false);
							int[] pontos = dashBoardBean.calcularPontuacaoPassagem(vendas, usuarioFranquia,  0, "",false);
							productRunnersMB.calcularPontuacaoPacote(usuarioFranquia, vendas.getProdutos(), false, pontos[0]);
							vendas.setPonto(pontos[0]);
							vendas.setPontoescola(pontos[1]);
							if (lead!=null){
								vendas.setIdlead(lead.getIdlead());
							}else vendas.setIdlead(0);
							VendasFacade vendasFacade = new VendasFacade();
							vendas=vendasFacade.salvar(vendas);
							mateRunnersMB.carregarListaRunners();
							tmRaceMB.gerarListaGold();
							tmRaceMB.gerarListaSinze();
							tmRaceMB.gerarListaBronze();
//						}
//					}.start();
				}  
				salvarPassagem();
				salvarPassageiros();
				salvarFormaPagamento();
				salvarCliente();
				OrcamentoFacade orcamentoFacade = new OrcamentoFacade();
				Orcamento orcamento = orcamentoFacade.consultar(vendas.getIdvendas());
				ProgramasBean programasBean = new ProgramasBean();
				programasBean.salvarOrcamento(orcamento, cambio, vendas.getValor(), 0.0f, valorCambio, vendas, "Não");
				calcularComissao();
				if (novaFicha) { 
					ContasReceberBean contasReceberBean = new ContasReceberBean(vendas,
							formaPagamento.getParcelamentopagamentoList(), usuarioLogadoMB, null, false, passagem.getDataviagem());
				} else {
					if (nsituacao.equalsIgnoreCase("FINALIZADA")) {
						float valorVendaatual = vendas.getValor();
					}
				}
				String vm = "Venda pela Matriz";
				if (vendas.getVendasMatriz().equalsIgnoreCase("N")) {
					vm = "Venda pela Loja";
				}
				DepartamentoFacade departamentoFacade = new DepartamentoFacade();
				List<Departamento> departamento = departamentoFacade.listar("select d From Departamento d where d.usuario.idusuario="+vendas.getProdutos().getIdgerente());
				if(departamento!=null && departamento.size()>0){
					if (novaFicha) {
						Formatacao.gravarNotificacaoVendas(titulo, vendas.getUnidadenegocio(),
								cliente.getNome(), vendas.getFornecedorcidade().getFornecedor().getNome(),
								Formatacao.ConvercaoDataPadrao(passagem.getDataviagem()), vendas.getUsuario().getNome(), vm,
								vendas.getValor(), valorCambio, vendas.getCambio().getMoedas().getSigla(), operacao,
								departamento.get(0), imagemNotificacao, "I");
					}else{
						Formatacao.gravarNotificacaoVendas(titulo, vendas.getUnidadenegocio(),
								cliente.getNome(), vendas.getFornecedorcidade().getFornecedor().getNome(),
								Formatacao.ConvercaoDataPadrao(passagem.getDataviagem()), vendas.getUsuario().getNome(), vm,
								vendas.getValor(), valorCambio, vendas.getCambio().getMoedas().getSigla(), operacao,
								departamento.get(0), imagemNotificacao, "A");
					}
				}
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Passagem Salva com Sucesso", ""));
				return "consPassagem";
			}
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
		}
		return "";
	}

	public String validarDados() {
		String msg = "";
		if (cliente == null) {
			msg = msg + "Campo cliente não selecionado\r\n";
		}
		if (passagem.getLocalizador() == null || passagem.getLocalizador().length() == 0) {
			msg = msg + "Localizador não informado\r\n";
		}
		if (passagem.getCiaAerea() == null || passagem.getCiaAerea().length() == 0) {
			msg = msg + "Cia aerea não informado\r\n";
		}
		if (passagem.getAtendente() == null || passagem.getAtendente().length() == 0) {
			msg = msg + "Atendente não informado\r\n";
		}
		if (fornecedorCidade == null) {
			msg = msg + "Fornecedor não informado\r\n";
		}
		if (pais == null) {
			msg = msg + "País não informado\r\n";
		}
		if (formaPagamento.getParcelamentopagamentoList() == null) {
			msg = msg + "Forma de Pagamento com erro\r\n";
		} else {
			if (formaPagamento.getParcelamentopagamentoList().size() == 0) {
				msg = msg + "Forma de Pagamento com erro\r\n";
			}
		}
		if (valorParcelar > 0) {
			msg = msg + "Forma de Pagamento possui saldo a parcelar em aberto\r\n";
		}
		return msg;
	}

	public void salvarPassageiros() {
		for (int i = 0; i < listaPassageiros.size(); i++) {
			listaPassageiros.get(i).setPassagemaerea(passagem);
			PassagemPassageiroFacade passagemPassageiroFacade = new PassagemPassageiroFacade();
			passagemPassageiroFacade.salvar(listaPassageiros.get(i));
		}
	}

	public void salvarVendas(String situacao) {
		if (vendas.getIdvendas() == null) {
			this.novaFicha = true;
			vendas.setDataVenda(new Date());
			vendas.setUsuariocancelamento(0);
			vendas.setObsCancelar("");
			vendas.setUsuario(usuarioLogadoMB.getUsuario());
			vendas.setValorcambio(0.0f);

		} else {
			if (this.vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
				this.vendas.setDataVenda(new Date());
			}
		}
		vendas.setSituacao(situacao);
		VendasFacade VendasFacade = new VendasFacade();
		vendas.setCliente(cliente);
		vendas.setVendasMatriz("S");
		ProdutoFacade produtoFacade = new ProdutoFacade();
		Produtos produto = new Produtos();
		produto = produtoFacade.consultar(aplicacaoMB.getParametrosprodutos().getPassagem());
		vendas.setProdutos(produto);
		vendas.setValor(this.valorTotal);
		vendas.setFornecedor(fornecedorCidade.getFornecedor());
		vendas.setFornecedorcidade(fornecedorCidade);
		vendas.setCambio(cambio);
		vendas.setUnidadenegocio(unidadeNegocio);
		if (!vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
			if (vendas.getSituacaogerencia().equalsIgnoreCase("P")){
				vendas.setSituacaogerencia("F");
			}
		}
		vendas = VendasFacade.salvar(vendas);
	}

	public void salvarFormaPagamento() {
		formaPagamento.setValorJuros(valorJuros);
		formaPagamento.setValorTotal(formaPagamento.getValorJuros() + formaPagamento.getValorOrcamento());
		formaPagamento.setVendas(this.vendas);
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		formaPagamento = formaPagamentoFacade.salvar(formaPagamento);
	}

	public void salvarCliente() {
		cliente.setTipoCliente("Fechado");
		cliente.setDataInicio(passagem.getDataviagem());
		cliente.setDataTermino(passagem.getDatavolta());
		ClienteFacade clienteFacade = new ClienteFacade();
		cliente = clienteFacade.salvar(cliente);
	}

	public void salvarPassagem() {
		if (usuarioFranquia !=null) {
			passagem.setIdusuario(usuarioFranquia.getIdusuario());
		}else {
			passagem.setIdusuario(0);
		}
		passagem.setVendas(vendas);
		PassagemFacade passagemFacade = new PassagemFacade();
		passagem = passagemFacade.salvar(passagem);
	}

	public String cancelar() {
		return "consPassagem";
	}

	public String calcularComissaoAdt() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tarifa", passagem.getAdttarifa());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 230);
		RequestContext.getCurrentInstance().openDialog("calcularComissao", options, null);
		return "";
	}

	public String calcularComissaoChd() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tarifa", passagem.getChdtarifa());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 230);
		RequestContext.getCurrentInstance().openDialog("calcularComissao", options, null);
		return "";
	}

	public String calcularComissaoInf() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tarifa", passagem.getInftarifa());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 230);
		RequestContext.getCurrentInstance().openDialog("calcularComissao", options, null);
		return "";
	}

	public void retornoDialogValorComissaoAdt(SelectEvent event) {
		Float valorComissao = (Float) event.getObject();
		passagem.setAdtcomissao(valorComissao);
	}

	public void retornoDialogValorComissaoChd(SelectEvent event) {
		Float valorComissao = (Float) event.getObject();
		passagem.setChdcomissao(valorComissao);
	}

	public void retornoDialogValorComissaoInf(SelectEvent event) {
		Float valorComissao = (Float) event.getObject();
		passagem.setInfcomissao(valorComissao);
	}

	public void editarValores() {
		for (int i = 0; i < listaPassageiros.size(); i++) {
			if (listaPassageiros.get(i).getCategoria() != null) {
				if (listaPassageiros.get(i).getCategoria().equalsIgnoreCase("ADT")) {
					listaPassageiros.get(i)
							.setValor(passagem.getAdttarifa() + passagem.getAdttaxas() + passagem.getAdttaxaemissao());
				} else if (listaPassageiros.get(i).getCategoria().equalsIgnoreCase("CHD")) {
					listaPassageiros.get(i)
							.setValor(passagem.getChdtarifa() + passagem.getChdtaxas() + passagem.getChdtaxaemissao());
				} else {
					listaPassageiros.get(i)
							.setValor(passagem.getInftarifa() + passagem.getInftaxas() + passagem.getInftaxaemissao());
				}
			}
		}
		calcularValorTotal();
	}

	public void calcularComissao() throws SQLException {
		Vendascomissao vendasComissao = vendas.getVendascomissao();
		if (vendasComissao == null) {
			vendasComissao = new Vendascomissao();
			vendasComissao.setVendas(vendas);
			vendasComissao.setPaga("Não");
		}
		if (vendasComissao.getPaga().equalsIgnoreCase("Não")) {
			float valorJuros = 0.0f;
			if (vendas.getFormapagamento() != null) {
				valorJuros = vendas.getFormapagamento().getValorJuros();
			}
			ComissaoPassagemBean cc = new ComissaoPassagemBean(aplicacaoMB, vendas,
					formaPagamento.getParcelamentopagamentoList(), passagem.getDataviagem(), vendasComissao, valorJuros,
					passagem, listaPassageiros);
		}
	}

	public String calcularJuros() {
		if (formaPagamento.getValorOrcamento() != null && formaPagamento.getValorOrcamento() > 0) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("total", formaPagamento.getValorOrcamento());
			RequestContext.getCurrentInstance().openDialog("calcularJuros");
		}
		return "";
	}

	public void retornoValorJuros() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		valorJuros = (float) session.getAttribute("valorJuros");
		session.removeAttribute("valorJuros");
		calcularValorTotalOrcamento();
	}
	
	
	public void gerarListaUsuario() {
		if (unidadeNegocio != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaUsuario = usuarioFacade.listaUsuarioUnidade(unidadeNegocio.getIdunidadeNegocio());
		}
	}
	
	
	
}
