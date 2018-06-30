package br.com.travelmate.managerBean.cancelamento;

import java.io.IOException;
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
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.facade.CancelamentoCreditoFacade;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.CondicaoCancelamentoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CreditoFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cloud.midia.CadVideoMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cancelamentocredito;
import br.com.travelmate.model.Condicaocancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Credito;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Pincambio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EmissaoCancelamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	private Cancelamento cancelamento;
	private List<Condicaocancelamento> listaCondicao;
	private boolean habilitarTotalRecebido;
	private float multaFornecedorReais;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private Vendas vendas;
	private float valorTotalMatriz;
	private float valorTotalLoja;
	private float valorTotalRecebido;
	private Condicaocancelamento condicaocancelamento;
	private boolean habilitarRegra = true;
	private boolean habilitarCampoAlinhamento = false;
	private boolean desabilitarCredito = true;
	private String voltar;
	private boolean novoCancelamento = false;
	private float valorOriginalMulta = 0f;
	private String pinCambio;
	private boolean habilitarPin = false;
	private List<Cancelamento> listaCancelamento;
	private boolean habilitarSalvar = true;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cancelamento = (Cancelamento) session.getAttribute("cancelamento");
		vendas = (Vendas) session.getAttribute("vendas");
		voltar = (String) session.getAttribute("voltar");
		listaCancelamento = (List<Cancelamento>) session.getAttribute("listaCancelamento");
		session.removeAttribute("vendas");
		session.removeAttribute("cancelamento");
		session.removeAttribute("voltar");
		session.removeAttribute("listaCancelamento");
		Date data = Formatacao.ConvercaoStringData("30/04/2016");
		if (cancelamento == null) {
			cancelamento = new Cancelamento();
			novoCancelamento = true;
			cancelamento.setVendas(vendas);
			cancelamento.setTotalrecebido(0.0f);
			cancelamento.setDatasolicitacao(new Date());
			cancelamento.setIdvendacredito(0);
			gerarListaCondicao();
			gerarValoresRecebidos();
		} else {
			vendas = cancelamento.getVendas();
			if (cancelamento.getVendas().getDataVenda().after(data)) {
				habilitarTotalRecebido = true;
				if (cancelamento.getTotalrecebido() <= 0) {
					valorTotalLoja = cancelamento.getTotalrecebidoloja();
					valorTotalMatriz = cancelamento.getTotalrecebidomatriz();
					// calcularTotalRecebidoLoja();
					calcularTotalRecebidoMatriz();
					habilitarTotalRecebido = true;
				} else {
					valorTotalLoja = cancelamento.getTotalrecebidoloja();
					valorTotalMatriz = cancelamento.getTotalrecebidomatriz();
					// cancelamento.setTotalrecebidoloja(0.0f);
					// cancelamento.setTotalrecebidomatriz(0.0f);
					// cancelamento.setTotalrecebido(0.0f);
					habilitarTotalRecebido = false;
				}
			} else {
				habilitarTotalRecebido = false;
			}
			gerarListaCondicao();
			calcularMultaFornecedorReais();
			escolherReembolsoCredito();
		}
		valorOriginalMulta = cancelamento.getMultacliente();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	} 

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public List<Condicaocancelamento> getListaCondicao() {
		return listaCondicao;
	}

	public void setListaCondicao(List<Condicaocancelamento> listaCondicao) {
		this.listaCondicao = listaCondicao;
	}

	public boolean isHabilitarTotalRecebido() {
		return habilitarTotalRecebido;
	}

	public void setHabilitarTotalRecebido(boolean habilitarTotalRecebido) {
		this.habilitarTotalRecebido = habilitarTotalRecebido;
	}

	public float getMultaFornecedorReais() {
		return multaFornecedorReais;
	}

	public void setMultaFornecedorReais(float multaFornecedorReais) {
		this.multaFornecedorReais = multaFornecedorReais;
	}

	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}

	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public float getValorTotalMatriz() {
		return valorTotalMatriz;
	}

	public void setValorTotalMatriz(float valorTotalMatriz) {
		this.valorTotalMatriz = valorTotalMatriz;
	}

	public float getValorTotalLoja() {
		return valorTotalLoja;
	}

	public void setValorTotalLoja(float valorTotalLoja) {
		this.valorTotalLoja = valorTotalLoja;
	}

	public float getValorTotalRecebido() {
		return valorTotalRecebido;
	}

	public void setValorTotalRecebido(float valorTotalRecebido) {
		this.valorTotalRecebido = valorTotalRecebido;
	}

	public Condicaocancelamento getCondicaocancelamento() {
		return condicaocancelamento;
	}

	public void setCondicaocancelamento(Condicaocancelamento condicaocancelamento) {
		this.condicaocancelamento = condicaocancelamento;
	}

	public boolean isHabilitarRegra() {
		return habilitarRegra;
	}

	public void setHabilitarRegra(boolean habilitarRegra) {
		this.habilitarRegra = habilitarRegra;
	}

	public boolean isHabilitarCampoAlinhamento() {
		return habilitarCampoAlinhamento;
	}

	public void setHabilitarCampoAlinhamento(boolean habilitarCampoAlinhamento) {
		this.habilitarCampoAlinhamento = habilitarCampoAlinhamento;
	}

	public boolean isDesabilitarCredito() {
		return desabilitarCredito;
	}

	public void setDesabilitarCredito(boolean desabilitarCredito) {
		this.desabilitarCredito = desabilitarCredito;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public float getValorOriginalMulta() {
		return valorOriginalMulta;
	}

	public void setValorOriginalMulta(float valorOriginalMulta) {
		this.valorOriginalMulta = valorOriginalMulta;
	}

	public String getPinCambio() {
		return pinCambio;
	}

	public void setPinCambio(String pinCambio) {
		this.pinCambio = pinCambio;
	}

	public boolean isHabilitarPin() {
		return habilitarPin;
	}

	public void setHabilitarPin(boolean habilitarPin) {
		this.habilitarPin = habilitarPin;
	}

	public void gerarListaCondicao() {
		String sql = "SELECT c FROM Condicaocancelamento c where c.produtos.idprodutos=";
		if (vendas != null) {
			sql = sql + vendas.getProdutos().getIdprodutos() + " order by c.descricao";
		} else {
			sql = sql + cancelamento.getVendas().getProdutos().getIdprodutos() + " order by c.descricao";
		}
		CondicaoCancelamentoFacade condicaoCancelamentoFacade = new CondicaoCancelamentoFacade();
		listaCondicao = condicaoCancelamentoFacade.listar(sql);
		if (listaCondicao == null) {
			listaCondicao = new ArrayList<Condicaocancelamento>();
		}
		if (listaCondicao.size() == 1) {
			cancelamento.setCondicaocancelamento(listaCondicao.get(0));
			habilitarRegra = false;
			habilitarCampoAlinhamento = true;
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			cancelamento.setMultacliente(calcularMultaCancelamentoBean.calcularMulta(cancelamento));
			if (vendas.getUnidadenegocio().getIdunidadeNegocio() == 1
					|| vendas.getUnidadenegocio().getIdunidadeNegocio() == 2) {
				cancelamento.setEstornoloja(0.0f);
			} else {
				cancelamento.setEstornoloja(calcularMultaCancelamentoBean.calcularEstornoFranquia(cancelamento));
			}
			cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
		} else if (listaCondicao.size() == 2) {
			if (listaCondicao.get(0).getDescricao().equalsIgnoreCase(" Sem condição")) {
				cancelamento.setCondicaocancelamento(listaCondicao.get(1));
				habilitarRegra = false;
				habilitarCampoAlinhamento = true;
				CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
				cancelamento.setMultacliente(calcularMultaCancelamentoBean.calcularMulta(cancelamento));
				if (vendas.getUnidadenegocio().getIdunidadeNegocio() == 1
						|| vendas.getUnidadenegocio().getIdunidadeNegocio() == 2) {
					cancelamento.setEstornoloja(0.0f);
				} else {
					cancelamento.setEstornoloja(calcularMultaCancelamentoBean.calcularEstornoFranquia(cancelamento));
				}
				if (cancelamento.getIdcancelamento() == null) {
					gerarValoresRecebidos();
				}
				cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
			} else {
				cancelamento.setCondicaocancelamento(listaCondicao.get(0));
				habilitarRegra = false;
				habilitarCampoAlinhamento = true;
				CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
				cancelamento.setMultacliente(calcularMultaCancelamentoBean.calcularMulta(cancelamento));
				if (vendas.getUnidadenegocio().getIdunidadeNegocio() == 1
						|| vendas.getUnidadenegocio().getIdunidadeNegocio() == 2) {
					cancelamento.setEstornoloja(0.0f);
				} else {
					cancelamento.setEstornoloja(calcularMultaCancelamentoBean.calcularEstornoFranquia(cancelamento));
				}
				if (cancelamento.getIdcancelamento() != null) {
					gerarValoresRecebidos();
				}
				cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
			}
		}
	}

	public void calcularTotalRecebidoLoja() {
		Formapagamento forma = cancelamento.getVendas().getFormapagamento();
		float valorRecebidoLoja = 0.0f;
		if (forma != null) {
			if (forma.getParcelamentopagamentoList() != null) {
				for (int i = 0; i < forma.getParcelamentopagamentoList().size(); i++) {
					if (forma.getParcelamentopagamentoList().get(i).getTipoParcelmaneto().equalsIgnoreCase("loja")) {
						valorRecebidoLoja = valorRecebidoLoja
								+ forma.getParcelamentopagamentoList().get(i).getValorParcelamento();
					}
				}
			}
		}
		cancelamento.setTotalrecebidoloja(valorRecebidoLoja);
		cancelamento.setTotalrecebido(cancelamento.getTotalrecebidoloja() + cancelamento.getTotalrecebidomatriz());
	}

	public void calcularTotalRecebidoMatriz() {
		float valorRecebidoMatriz = 0.0f;
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		String sql = "SELECT c FROM Contasreceber c where c.vendas.idvendas=" + cancelamento.getVendas().getIdvendas()
				+ " and c.valorpago>0";
		List<Contasreceber> listaConta = contasReceberFacade.listar(sql);
		if (listaConta != null) {
			for (int i = 0; i < listaConta.size(); i++) {
				valorRecebidoMatriz = valorRecebidoMatriz + listaConta.get(i).getValorparcela();
			}
		}
		cancelamento.setTotalrecebidomatriz(valorRecebidoMatriz);
		cancelamento.setTotalrecebido(cancelamento.getTotalrecebidoloja() + cancelamento.getTotalrecebidomatriz());
	}

	public void calcularMultaCliente() {
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		cancelamento.setMultacliente(calcularMultaCancelamentoBean.calcularMulta(cancelamento));
		if (vendas.getUnidadenegocio().getIdunidadeNegocio() == 1
				|| vendas.getUnidadenegocio().getIdunidadeNegocio() == 2) {
			cancelamento.setEstornoloja(0.0f);
		} else {
			cancelamento.setEstornoloja(calcularMultaCancelamentoBean.calcularEstornoFranquia(cancelamento));
		}
		calcularMultaCancelamentoBean.calcularTotais(cancelamento);
	}

	public String editarValoresCancelamento(String campo) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("campo", campo);
		if (campo.equalsIgnoreCase("recebidomatriz")) {
			session.setAttribute("valorcampo", cancelamento.getTotalrecebidomatriz());
		} else if (campo.equalsIgnoreCase("recebidoloja")) {
			session.setAttribute("valorcampo", cancelamento.getTotalrecebidoloja());
		} else if (campo.equalsIgnoreCase("multacliente")) {
			session.setAttribute("valorcampo", cancelamento.getMultacliente());
		} else if (campo.equalsIgnoreCase("estornoloja")) {
			session.setAttribute("valorcampo", cancelamento.getEstornoloja());
		}
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 250);
		RequestContext.getCurrentInstance().openDialog("editarValoresCancelamento", options, null);
		return "";
	}

	public void retornoEditarValoresCancelamento(SelectEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String campo = (String) session.getAttribute("campo");
		float valor = (float) session.getAttribute("valorcampo");
		String operacao = (String) session.getAttribute("operacao");
		session.removeAttribute("campo");
		session.removeAttribute("valorcampo");
		session.removeAttribute("operacao");
		if (operacao.equalsIgnoreCase("confirmada")) {
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			if (campo.equalsIgnoreCase("recebidomatriz")) {
				cancelamento.setTotalrecebidomatriz(valor);
			} else if (campo.equalsIgnoreCase("recebidoloja")) {
				cancelamento.setTotalrecebidoloja(valor);
			} else if (campo.equalsIgnoreCase("multacliente")) {
				cancelamento.setMultacliente(valor);
			} else if (campo.equalsIgnoreCase("estornoloja")) {
				cancelamento.setEstornoloja(valor);
			}
			cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
		}
	}

	public void calcularMultaFornecedorReais() {
		if (cancelamento.getMultafornecedor() > 0) {
			multaFornecedorReais = cancelamento.getMultafornecedor() * cancelamento.getVendas().getValorcambio();
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
		} else
			multaFornecedorReais = 0.0f;
	}

	public String confirmar() {
		if(habilitarSalvar) {
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			boolean situacao = calcularMultaCancelamentoBean.verifcarValorCreditoReembolso(cancelamento);
			if (situacao) {
				CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
				VendasFacade vendasFacade = new VendasFacade();
				cancelamento.setUploadtermo(false);
				cancelamento.setUsuario(usuarioLogadoMB.getUsuario());
				cancelamento = cancelamentoFacade.salvar(cancelamento);
				if (novoCancelamento) {
					salvarCredito();
				}
				DashBoardBean dashBoardBean = new DashBoardBean();
				dashBoardBean.calcularMetaMensal(vendas, vendas.getValor(), true);
				dashBoardBean.calcularMetaAnual(vendas, vendas.getValor(), true);
				dashBoardBean.calcularPontuacao(vendas, 0, "", true);
				productRunnersMB.calcularPontuacao(vendas, vendas.getPonto(), true);
				vendas.setSituacao("CANCELADA");
				vendasFacade.salvar(vendas);
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				if (listaCancelamento != null && listaCancelamento.size() > 0) {
					session.setAttribute("listaCancelamento", listaCancelamento);
				}
				emitirNotificacao();
				Mensagem.lancarMensagemInfo("Confirmação", "Cancelamento salvo com sucesso");
				return voltar;
			} else {
				Mensagem.lancarMensagemWarn("Cancelamento", "Valor Crédito + Valor Remebolso maior que Total Reembolso");
				return "";
			}
		}else {
			Mensagem.lancarMensagemInfo("Informe ou cancele o PIN", "");
		}
		return "";
	}

	public void salvarCredito() {
		CancelamentoCreditoFacade cancelamentoCreditoFacade = new CancelamentoCreditoFacade();
		CreditoFacade creditoFacade = new CreditoFacade();
		Credito credito = new Credito();
		Cancelamentocredito cancelamentocredito = new Cancelamentocredito();
		credito.setTipocredito(cancelamento.getFormapagamento());
		credito.setUsuario(usuarioLogadoMB.getUsuario());
		if (cancelamento.getFormapagamento().equalsIgnoreCase("Reembolso")) {
			credito.setValorcredito(cancelamento.getTotalreembolso());
		} else if (cancelamento.getFormapagamento().equalsIgnoreCase("Crédito")) {
			credito.setValorcredito(cancelamento.getTotalreembolso());
		} else if (cancelamento.getFormapagamento().equalsIgnoreCase("Ambos")) {
			credito.setValorcredito(cancelamento.getTotalreembolso() - cancelamento.getValorcredito());
		}
		credito.setCliente(cancelamento.getVendas().getCliente());
		credito = creditoFacade.salvar(credito);
		cancelamentocredito.setCancelamento(cancelamento);
		cancelamentocredito.setCredito(credito);
		cancelamentoCreditoFacade.salvar(cancelamentocredito);
	}

	public String cancelar() {
		Mensagem.lancarMensagemInfo("Confirmação", "Solicitação de cancelamento cancelada");
		return voltar;
	}

	public void calcularTotal() {
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		if (cancelamento.getTotalrecebidomatriz() == 0) {
			if (!habilitarTotalRecebido) {
				cancelamento.setTotalrecebidomatriz(cancelamento.getTotalrecebido());
			}
		}
		calcularMultaCancelamentoBean.calcularTotais(cancelamento);
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		salvarArquivoFTP();
		if (listaNomeArquivo == null) {
			listaNomeArquivo = new ArrayList<>();
		}
		listaNomeArquivo.add(file.getFileName());
		Mensagem.lancarMensagemInfo("Salvo com sucesso.", "");
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		String pasta = "";
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadVideoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(CadVideoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			nomeArquivoFTP = nomeArquivo(file.getFileName().trim());
			pasta = "/cloud/departamentos/";
			msg = ftp.enviarArquivo(file, nomeArquivoFTP, pasta);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CadVideoMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String nomeArquivo(String nome) {
		nomeArquivoFTP = cancelamento.getVendas().getIdvendas() + "_" + nome;
		return nomeArquivoFTP;
	}

	public void gerarValoresRecebidos() {
		valorTotalLoja = 0.0f;
		valorTotalMatriz = 0.0f;
		for (int i = 0; i < vendas.getContasreceberList().size(); i++) {
			if (vendas.getContasreceberList().get(i).getValorpago() > 0.0f
					&& !vendas.getContasreceberList().get(i).getSituacao().equalsIgnoreCase("cc")) {
				valorTotalMatriz = valorTotalMatriz + vendas.getContasreceberList().get(i).getValorpago();
			}
		}
		Formapagamento forma = vendas.getFormapagamento();
		if (forma != null) {
			if (forma.getParcelamentopagamentoList() != null) {
				for (int i = 0; i < forma.getParcelamentopagamentoList().size(); i++) {
					if (forma.getParcelamentopagamentoList().get(i).getTipoParcelmaneto().equalsIgnoreCase("loja")) {
						valorTotalLoja = valorTotalLoja
								+ forma.getParcelamentopagamentoList().get(i).getValorParcelamento();
					}
				}
			}
		}
		valorTotalRecebido = valorTotalMatriz + valorTotalLoja;
		cancelamento.setTotalrecebidoloja(valorTotalLoja);
		cancelamento.setTotalrecebidomatriz(valorTotalMatriz);
		cancelamento.setTotalrecebido(valorTotalRecebido);
		if (cancelamento.getMultacliente() == null) {
			cancelamento.setMultacliente(0f);
		}
		if (cancelamento.getEstornoloja() == null) {
			cancelamento.setEstornoloja(0f);
		}
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
	}

	public void calcularValoresRecebidos() {
		valorTotalRecebido = valorTotalMatriz + valorTotalLoja;
		cancelamento.setTotalrecebidoloja(valorTotalLoja);
		cancelamento.setTotalrecebidomatriz(valorTotalMatriz);
		cancelamento.setTotalrecebido(valorTotalRecebido);
		if (cancelamento.getMultacliente() == null) {
			cancelamento.setMultacliente(0f);
		}
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
	}
	
	public void calcularValoresRecebidosPin() {
		valorTotalRecebido = valorTotalMatriz + valorTotalLoja;
		cancelamento.setTotalrecebidoloja(valorTotalLoja);
		cancelamento.setTotalrecebidomatriz(valorTotalMatriz);
		cancelamento.setTotalrecebido(valorTotalRecebido);
	}

	public String relatorioTermoQuitacao() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cancelamento", cancelamento);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("reciboTermoQuitacao", options, null);
		return "";
	}

	public void calculcarMulta() {
		if (condicaocancelamento != null) {
			cancelamento.setCondicaocancelamento(condicaocancelamento);
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			cancelamento.setMultacliente(calcularMultaCancelamentoBean.calcularMulta(cancelamento));
			if (vendas.getUnidadenegocio().getIdunidadeNegocio() == 1
					|| vendas.getUnidadenegocio().getIdunidadeNegocio() == 2) {
				cancelamento.setEstornoloja(0.0f);
			} else {
				cancelamento.setEstornoloja(calcularMultaCancelamentoBean.calcularEstornoFranquia(cancelamento));
			}
			cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
		}

		if (cancelamento.getMultacliente() != null) {
			valorOriginalMulta = cancelamento.getMultacliente();
		}
	}

	public void escolherReembolsoCredito() {
		if (cancelamento.getFormapagamento().equalsIgnoreCase("Reembolso")) {
			cancelamento.setValorreembolso(cancelamento.getTotalreembolso());
			cancelamento.setValorcredito(0f);
			desabilitarCredito = true;
		} else if (cancelamento.getFormapagamento().equalsIgnoreCase("Crédito")) {
			cancelamento.setValorcredito(cancelamento.getTotalreembolso());
			cancelamento.setValorreembolso(0f);
			desabilitarCredito = true;
		} else if (cancelamento.getFormapagamento().equalsIgnoreCase("Ambos")) {
			cancelamento.setValorcredito(0f);
			cancelamento.setValorreembolso(cancelamento.getTotalreembolso() - cancelamento.getValorcredito());
			desabilitarCredito = false;
		}
	}

	public void calcularReembolsoCredito() {
		if (cancelamento.getValorcredito() == null) {
			cancelamento.setValorcredito(0f);
		}
		cancelamento.setValorreembolso(cancelamento.getTotalreembolso() - cancelamento.getValorcredito());
		desabilitarCredito = false;
	}

	public String visualizarContasReceber() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", vendas);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}

	public void verificarPin() {
		if (valorOriginalMulta != cancelamento.getMultacliente()) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 230);
			session.setAttribute("valorOriginal", 0f);
			session.setAttribute("novoValor", 0f);
			RequestContext.getCurrentInstance().openDialog("validarTrocaCambioPIN", options, null);
		} else {
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
		}
	}

	public void retornoDialogPin() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String adicionar = (String) session.getAttribute("adicionar");
		session.removeAttribute("adicionar");
		if (adicionar != null) {
			if (adicionar.equalsIgnoreCase("sim")) {
				CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
				cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
			}
		} else {
			float valorOriginal = (float) session.getAttribute("valorOriginal");
			session.removeAttribute("valorOriginal");
			cancelamento.setMultacliente(valorOriginal);
		}
		habilitarSalvar = true;
	}
	
	
	public void validarPin(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
        Pincambio pincambio = usuarioFacade.consultar(pinCambio, usuarioLogadoMB.getUsuario().getIdusuario());
        if (pincambio == null) {
        	FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("PIN inválido", ""));
        } else {
            salvarPinCambio(pincambio);
            valorOriginalMulta = cancelamento.getMultacliente();
            habilitarPin = false;
            habilitarSalvar = true;
            CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
            cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
            calcularValoresRecebidosPin();
            this.pinCambio = "";
        }
	}
	
	public void salvarPinCambio(Pincambio pinCambio){
        if (pinCambio!=null){
            pinCambio.setUtilizado("S");
            UsuarioFacade usuarioFacade = new UsuarioFacade();
            usuarioFacade.salvar(pinCambio);
        }
    }
	
	public void fecharPin() {
		cancelamento.setMultacliente(valorOriginalMulta);
		habilitarPin = false;
		habilitarSalvar = true;
	}
	
	public void verificarNovaMulta() {
		if (cancelamento.getMultacliente() != null) {
			float valorMulta = cancelamento.getMultacliente();
			if (valorMulta != valorOriginalMulta) {
				habilitarPin = true;
				habilitarSalvar = false;
			}
		}
	}
	
	public void emitirNotificacao() {
		String vm = "Venda pela Matriz";
		if (cancelamento.getVendas().getVendasMatriz().equalsIgnoreCase("N")) {
			vm = "Venda pela Loja";
		}
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		List<Departamento> departamento = departamentoFacade.listar("select d From Departamento d where d.usuario.idusuario="+cancelamento.getVendas().getProdutos().getIdgerente());
		if(departamento!=null && departamento.size()>0){
			Formatacao.gravarNotificacaoVendas(
					"Solicitação de Cancelamento Ficha No. " + String.valueOf(cancelamento.getVendas().getIdvendas()),
					usuarioLogadoMB.getUsuario().getUnidadenegocio(),
					cancelamento.getVendas().getCliente().getNome(),
					cancelamento.getVendas().getFornecedorcidade().getFornecedor().getNome(), "",
					cancelamento.getUsuario().getNome(), vm, cancelamento.getVendas().getValor(),
					cancelamento.getVendas().getCambio().getValor(),
					cancelamento.getVendas().getCambio().getMoedas().getSigla(), "A",
					departamento.get(0), "cancelado", "I");
		}
	}

}
