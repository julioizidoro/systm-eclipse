package br.com.travelmate.managerBean.cancelamento;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import org.primefaces.model.UploadedFile;

import br.com.travelmate.bean.DashBoardBean;
import br.com.travelmate.facade.ArquivosFacade;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.CartaoCreditoLancamentoContasFacade;
import br.com.travelmate.facade.CartaoCreditoLancamentoFacade;
import br.com.travelmate.facade.CondicaoCancelamentoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TipoArquivoFacade;
import br.com.travelmate.facade.UsuarioPontosFacade; 
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.DashBoardMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.ProductRunnersMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cloud.midia.CadVideoMB;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cartaocreditolancamentocontas;
import br.com.travelmate.model.Condicaocancelamento;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Usuariopontos; 
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CancelamentoFichaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Cancelamento cancelamento;
	private boolean emissao;
	@Inject
	private DashBoardMB dashBoardMB;
	@Inject
	private MateRunnersMB metaRunnersMB;
	@Inject
	private ProductRunnersMB productRunnersMB;
	private Vendas venda;
	private Vendas venda1;
	private List<String> listaNomeArquivo;
	private UploadedFile file;
	private String nomeArquivoFTP;
	private List<Banco> listaBanco;
	private Banco banco;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			Vendas venda = (Vendas) session.getAttribute("venda");
			venda1 = (Vendas) session.getAttribute("venda1");
			session.removeAttribute("venda1");
			session.removeAttribute("venda");
			gerarListaBanco();
			cancelamento = (Cancelamento) session.getAttribute("cancelamento");
			if (cancelamento == null) {
				cancelamento = new Cancelamento();
				cancelamento.setVendas(venda);
				cancelamento.setDatasolicitacao(new Date());
				cancelamento.setEstornoloja(0.0f);
				cancelamento.setIdvendacredito(0);
				cancelamento.setMultacliente(0.0f);
				cancelamento.setMultafornecedor(0.0f);
				cancelamento.setSaldocancelamento(0.0f);
				cancelamento.setTotalrecebido(0.0f);
				cancelamento.setTotalrecebidoloja(0.0f);
				cancelamento.setTotalrecebidomatriz(0.0f);
				cancelamento.setTotalreembolso(0.0f);
				cancelamento.setHora(Formatacao.foramtarHoraString());
				cancelamento.setFormapagamento("Reembolso");
				emissao = true;
			} else {
				emissao = false;
			}
		}
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

	public boolean isEmissao() {
		return emissao;
	}

	public void setEmissao(boolean emissao) {
		this.emissao = emissao;
	}

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public MateRunnersMB getMetaRunnersMB() {
		return metaRunnersMB;
	}

	public void setMetaRunnersMB(MateRunnersMB metaRunnersMB) {
		this.metaRunnersMB = metaRunnersMB;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Vendas getVenda1() {
		return venda1;
	}

	public void setVenda1(Vendas venda1) {
		this.venda1 = venda1;
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "consCancelamento";
	}

	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}

	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}

	public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String salvar() {
			CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
			cancelamento.setUsuario(usuarioLogadoMB.getUsuario());
			cancelamento.setSituacao("FINALIZADA");
			cancelamento = cancelamentoFacade.salvar(cancelamento);
			venda = cancelamento.getVendas();
			venda.setSituacao("CANCELADA");
			salvarArquivo();
			VendasFacade vendasFacade = new VendasFacade();
			vendasFacade.salvar(venda);
			cancelarContasReceber();
			int mesVenda = Formatacao.getMesData(venda.getDataVenda())  + 1;
			int mesAtual = Formatacao.getMesData(new Date()) +1; 
			if (mesVenda == mesAtual) {
				if (venda.getProdutos().getIdprodutos() == 1 || venda.getProdutos().getIdprodutos() == 4
						|| venda.getProdutos().getIdprodutos() == 5 || venda.getProdutos().getIdprodutos() == 9
						|| venda.getProdutos().getIdprodutos() == 10 || venda.getProdutos().getIdprodutos() == 11
						|| venda.getProdutos().getIdprodutos() == 13 || venda.getProdutos().getIdprodutos() == 16
						|| venda.getProdutos().getIdprodutos() == 20) {
					dashBoardMB.getVendaproduto().setIntercambio(dashBoardMB.getVendaproduto().getIntercambio() - 1);
				} else if (venda.getProdutos().getIdprodutos() == 7) {
					dashBoardMB.getVendaproduto().setTurismo(dashBoardMB.getVendaproduto().getTurismo() - 1);
				} else if (venda.getProdutos().getIdprodutos() == 2 || venda.getProdutos().getIdprodutos() == 3
						|| venda.getProdutos().getIdprodutos() == 6) {
					dashBoardMB.getVendaproduto().setProduto(dashBoardMB.getVendaproduto().getProduto() - 1);
				}
				dashBoardMB.getMetamensal()
						.setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado() - venda.getValor());
				dashBoardMB.getMetamensal().setPercentualalcancado(
						(dashBoardMB.getMetamensal().getValoralcancado() / dashBoardMB.getMetamensal().getValormeta())
								* 100);
	
				dashBoardMB.getMetaAnual()
						.setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada() - venda.getValor());
				dashBoardMB.getMetaAnual().setPercentualalcancado(
						(dashBoardMB.getMetaAnual().getMetaalcancada() / dashBoardMB.getMetaAnual().getValormeta()) * 100);
	
				dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() - venda.getValor());
				dashBoardMB.setPercsemana(
						(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana()) * 100);
	
				float valor = dashBoardMB.getMetamensal().getValoralcancado();
				dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));
	
				DashBoardBean dashBoardBean = new DashBoardBean();
				dashBoardBean.calcularNumeroVendasProdutos(venda, true);
				dashBoardBean.calcularMetaMensal(venda, 0, true);
				dashBoardBean.calcularMetaAnual(venda, 0, true);
				int[] pontos = dashBoardBean.calcularPontuacao(venda, 0, "", true);
				venda.setPonto(pontos[0]);
				venda.setPontoescola(pontos[1]);
				venda = vendasFacade.salvar(venda);
				productRunnersMB.calcularPontuacao(venda, pontos[0], true);
				metaRunnersMB.carregarListaRunners();
				emitirNotificacao();
	
				if ((venda.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getCursos()) || 
						(venda.getProdutos().getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVoluntariado())) {
					SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
					Seguroviagem seguroViagem = seguroViagemController.consultarSeguroCurso(venda.getIdvendas());
					if (seguroViagem != null && seguroViagem.getIdseguroViagem() != null) {
						Vendas vendas = seguroViagem.getVendas();
						cancelamento = new Cancelamento();
						cancelamento.setVendas(vendas);
						cancelamento.setDatasolicitacao(new Date());
						cancelamento.setEstornoloja(0.0f);
						cancelamento.setIdvendacredito(0);
						cancelamento.setMultacliente(0.0f);
						cancelamento.setMultafornecedor(0.0f);
						cancelamento.setSaldocancelamento(0.0f);
						cancelamento.setTotalrecebido(0.0f);
						cancelamento.setTotalrecebidoloja(0.0f);
						cancelamento.setTotalrecebidomatriz(0.0f);
						cancelamento.setTotalreembolso(0.0f);
						cancelamento.setHora(Formatacao.foramtarHoraString());
						cancelamento.setUsuario(usuarioLogadoMB.getUsuario());
						cancelamento.setSituacao("PROCESSO");
						cancelamento = cancelamentoFacade.salvar(cancelamento);
						vendas = cancelamento.getVendas();
						vendas.setSituacao("CANCELADA");
						vendasFacade.salvar(vendas);
	
						dashBoardMB.getVendaproduto().setProduto(dashBoardMB.getVendaproduto().getProduto() - 1);
	
						dashBoardMB.getMetamensal()
								.setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado() - vendas.getValor());
						dashBoardMB.getMetamensal().setPercentualalcancado((dashBoardMB.getMetamensal().getValoralcancado()
								/ dashBoardMB.getMetamensal().getValormeta()) * 100);
	
						dashBoardMB.getMetaAnual()
								.setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada() - vendas.getValor());
						dashBoardMB.getMetaAnual().setPercentualalcancado(
								(dashBoardMB.getMetaAnual().getMetaalcancada() / dashBoardMB.getMetaAnual().getValormeta())
										* 100);
	
						dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() - vendas.getValor());
						dashBoardMB.setPercsemana(
								(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana())
										* 100);
	
						valor = dashBoardMB.getMetamensal().getValoralcancado();
						dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));
	
						dashBoardBean = new DashBoardBean();
						dashBoardBean.calcularNumeroVendasProdutos(vendas, true);
						dashBoardBean.calcularMetaMensal(vendas, 0, true);
						dashBoardBean.calcularMetaAnual(vendas, 0, true);
						pontos = dashBoardBean.calcularPontuacao(vendas, 0, "", true);
						vendas.setPonto(pontos[0]);
						vendas.setPontoescola(pontos[1]);
						vendas = vendasFacade.salvar(vendas);
						productRunnersMB.calcularPontuacao(vendas, pontos[0], true);
						metaRunnersMB.carregarListaRunners();
						emitirNotificacao();
					}
				}
			} 
			if(venda1!=null){
				cancelarVenda1();
			}  
			RequestContext.getCurrentInstance().closeDialog(null);
			Mensagem.lancarMensagemInfo("Cancelamento Solicitado!", "");
		return "";
	}
	
	public void cancelarVenda1(){
		VendasFacade vendasFacade = new VendasFacade();
		venda1 = cancelamento.getVendas();
		venda1.setSituacao("CANCELADA");
		vendasFacade.salvar(venda1);
		int mesVenda = Formatacao.getMesData(venda1.getDataVenda())  + 1;
		int mesAtual = Formatacao.getMesData(new Date()) +1; 
		if (mesVenda == mesAtual) {
			if (venda1.getProdutos().getIdprodutos() == 1 || venda1.getProdutos().getIdprodutos() == 4
					|| venda1.getProdutos().getIdprodutos() == 5 || venda1.getProdutos().getIdprodutos() == 9
					|| venda.getProdutos().getIdprodutos() == 10 || venda1.getProdutos().getIdprodutos() == 11
					|| venda.getProdutos().getIdprodutos() == 13 || venda.getProdutos().getIdprodutos() == 16
					|| venda.getProdutos().getIdprodutos() == 20) {
				dashBoardMB.getVendaproduto().setIntercambio(dashBoardMB.getVendaproduto().getIntercambio() - 1);
			} else if (venda1.getProdutos().getIdprodutos() == 7) {
				dashBoardMB.getVendaproduto().setTurismo(dashBoardMB.getVendaproduto().getTurismo() - 1);
			} else if (venda1.getProdutos().getIdprodutos() == 2 || venda1.getProdutos().getIdprodutos() == 3
					|| venda1.getProdutos().getIdprodutos() == 6) {
				dashBoardMB.getVendaproduto().setProduto(dashBoardMB.getVendaproduto().getProduto() - 1);
			}
			dashBoardMB.getMetamensal()
					.setValoralcancado(dashBoardMB.getMetamensal().getValoralcancado() - venda1.getValor());
			dashBoardMB.getMetamensal().setPercentualalcancado(
					(dashBoardMB.getMetamensal().getValoralcancado() / dashBoardMB.getMetamensal().getValormeta())
							* 100);

			dashBoardMB.getMetaAnual()
					.setMetaalcancada(dashBoardMB.getMetaAnual().getMetaalcancada() - venda1.getValor());
			dashBoardMB.getMetaAnual().setPercentualalcancado(
					(dashBoardMB.getMetaAnual().getMetaalcancada() / dashBoardMB.getMetaAnual().getValormeta()) * 100);

			dashBoardMB.setMetaparcialsemana(dashBoardMB.getMetaparcialsemana() - venda1.getValor());
			dashBoardMB.setPercsemana(
					(dashBoardMB.getMetaparcialsemana() / dashBoardMB.getMetamensal().getValormetasemana()) * 100);

			float valor = dashBoardMB.getMetamensal().getValoralcancado();
			dashBoardMB.setValorFaturamento(Formatacao.formatarFloatString(valor));

			DashBoardBean dashBoardBean = new DashBoardBean();
			dashBoardBean.calcularNumeroVendasProdutos(venda1, true);
			dashBoardBean.calcularMetaMensal(venda1, 0, true);
			dashBoardBean.calcularMetaAnual(venda1, 0, true);
			int[] pontos = dashBoardBean.calcularPontuacao(venda1, 0, "", true);
			venda1.setPonto(pontos[0]);
			venda1.setPontoescola(pontos[1]);
			venda1 = vendasFacade.salvar(venda1);
			productRunnersMB.calcularPontuacao(venda1, pontos[0], true);
			metaRunnersMB.carregarListaRunners(); 
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
	
	
	public void cancelarPontuacao() {
		int ano = Formatacao.getAnoData(venda.getDataVenda());
		int mes = Formatacao.getMesData(venda.getDataVenda()) + 1;
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT u FROM Usuariopontos u where u.usuario.idusuario=" + venda.getUsuario().getIdusuario()
				+ " and u.mes=" + mes + " and u.ano=" + ano;
		Usuariopontos usuariopontos = usuarioPontosFacade.consultar(sql);
		if (usuariopontos == null) {
			usuariopontos = new Usuariopontos();
			usuariopontos.setAno(ano);
			usuariopontos.setMes(mes);
			usuariopontos.setUsuario(venda.getUsuario());
			usuariopontos.setPontos(0);
		}
		usuariopontos.setPontos(usuariopontos.getPontos() - venda.getPonto());
		usuariopontos.setPontoescola(usuariopontos.getPontoescola() - venda.getPontoescola());
		if (usuariopontos.getPontos() < 0) {
			usuariopontos.setPontos(0);
		}
		if (usuariopontos.getPontoescola() < 0) {
			usuariopontos.setPontoescola(0);
		}
		usuariopontos = usuarioPontosFacade.salvar(usuariopontos);
	}
	
	public void cancelarContasReceber(){
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		List<Contasreceber> lista = contasReceberFacade.listar("SELECT c FROM Contasreceber c where c.vendas.idvendas=" + venda.getIdvendas());
		if (lista == null) {
			lista = new ArrayList<>();
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getValorpago() == 0 && lista.get(i).getDatapagamento()==null) {
				EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean(
						"Conta cancelada pelo SysTM", lista.get(i), usuarioLogadoMB.getUsuario());
				lista.get(i).setSituacao("cc");
				lista.get(i).setDatacancelamento(new Date());
				lista.get(i).setMotivocancelamento("Alteração SysTM");
				contasReceberFacade.salvar(lista.get(i));
				if (lista.get(i).getCrmcobrancaconta() != null) {
					if (lista.get(i).getCrmcobrancaconta().getPaga() == false) {
						CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
						crmCobrancaBean.baixar(lista.get(i), usuarioLogadoMB.getUsuario());
					}
				}
			}
		}
	}
	
	
	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		salvarArquivoFTP();
		if (listaNomeArquivo == null) {
			listaNomeArquivo = new ArrayList<String>();
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
			pasta = "/systm/arquivos/";
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
	
	
	public void lancarContasPagar() {
		Contaspagar contaspagar = new Contaspagar();
		PlanoContaFacade planoContaFacade = new PlanoContaFacade();
		contaspagar.setBanco(banco);
		String comp;
		int mes = Formatacao.getMesData(cancelamento.getDatasolicitacao());
		if (mes < 10) {
			comp = "0" + mes + "/" + Formatacao.getAnoData(cancelamento.getDatasolicitacao());
		} else
			comp = mes + "/" + Formatacao.getAnoData(cancelamento.getDatasolicitacao());
		contaspagar.setCompetencia(comp);
		contaspagar.setDataEmissao(new Date());
		contaspagar.setDescricao("Reembolso referente ao cancelamento da venda " + cancelamento.getVendas().getIdvendas() + ", Cliente "+ 
				cancelamento.getVendas().getCliente().getNome() + ", com o plano de conta Reembolso a Clientes");
		contaspagar.setPlanoconta(planoContaFacade.consultar(7));
		contaspagar.setUnidadenegocio(cancelamento.getVendas().getUnidadenegocio());
		contaspagar.setValorentrada(cancelamento.getValorreembolso());
		contaspagar.setValorsaida(0.0f);
		contaspagar.setDatacompensacao(cancelamento.getDatasolicitacao());
		contaspagar.setDatavencimento(cancelamento.getDatasolicitacao());
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		contaspagar = contasPagarFacade.salvar(contaspagar);
		Mensagem.lancarMensagemInfo("Contas a Pagar lançado com sucesso!", "");
	}
	
	
	public void gerarListaBanco() {
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco == null) {
            listaBanco = new ArrayList<Banco>();
        }
    }
	
	
	public void salvarArquivo() {
		TipoArquivoFacade tipoarquivo = new TipoArquivoFacade();
		ArquivosFacade arquivosFacade = new ArquivosFacade();
		Arquivos arquivos;
		if (listaNomeArquivo.size() > 0) {
			for (int i = 0; i < listaNomeArquivo.size(); i++) {
				arquivos = new Arquivos();
				arquivos.setDataInclusao(new Date());
				arquivos.setNomeArquivo(listaNomeArquivo.get(i));
				arquivos.setNomesalvos(nomeArquivo(listaNomeArquivo.get(i)));
				arquivos.setSe(false);
				arquivos.setUsuario(usuarioLogadoMB.getUsuario());
				arquivos.setVendas(cancelamento.getVendas());
				try {
					arquivos.setTipoarquivo(tipoarquivo.consultar(14));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				arquivosFacade.salvar(arquivos);
			}
		}
	}

}
