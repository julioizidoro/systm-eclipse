package br.com.travelmate.managerBean.turismo.pacotefornecedor;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.FornecedorPacoteArquivoInvoiceFacade;
import br.com.travelmate.facade.FornecedorPacoteArquivoPagamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.PacotesFornecedorFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fornecedorpacotearquivoinvoice;
import br.com.travelmate.model.Fornecedorpacotearquivopagamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Pacotesfornecedor;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class PacoteFornecedorMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Pacotesfornecedor> listaPacotesFornecedor;
	private String fornecedor;
	private Date dataInicioVencimento;
	private Date dataFinalVencimento;
	private String produto;
	private String situacao;
	private int reserva;
	private float totalvencidas;
	private float totalvencer;
	private float totalprazo;
	private Ftpdados ftpDados;
	
	@PostConstruct
	public void init(){
		gerarListaPacotesFornecedor();
		ftpDados = new Ftpdados();
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpDados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Pacotesfornecedor> getListaPacotesFornecedor() {
		return listaPacotesFornecedor;
	}

	public void setListaPacotesFornecedor(List<Pacotesfornecedor> listaPacotesFornecedor) {
		this.listaPacotesFornecedor = listaPacotesFornecedor;
	}

	public String getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Date getDataInicioVencimento() {
		return dataInicioVencimento;
	}

	public void setDataInicioVencimento(Date dataInicioVencimento) {
		this.dataInicioVencimento = dataInicioVencimento;
	}

	public Date getDataFinalVencimento() {
		return dataFinalVencimento;
	}

	public void setDataFinalVencimento(Date dataFinalVencimento) {
		this.dataFinalVencimento = dataFinalVencimento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getReserva() {
		return reserva;
	}

	public void setReserva(int reserva) {
		this.reserva = reserva;
	}
	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	} 
	
	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public float getTotalvencidas() {
		return totalvencidas;
	}

	public void setTotalvencidas(float totalvencidas) {
		this.totalvencidas = totalvencidas;
	}

	public float getTotalvencer() {
		return totalvencer;
	}

	public void setTotalvencer(float totalvencer) {
		this.totalvencer = totalvencer;
	}

	public float getTotalprazo() {
		return totalprazo;
	}

	public void setTotalprazo(float totalprazo) {
		this.totalprazo = totalprazo;
	}

	public Ftpdados getFtpDados() {
		return ftpDados;
	}

	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}

	public void gerarListaPacotesFornecedor(){
		String sql = "SELECT p FROM Pacotesfornecedor p WHERE p.comprovante=FALSE";
		if(!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isTurismotodasvendas()){
			sql=sql+" AND p.pacotes.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
		}
		sql = sql +" ORDER BY p.datapagamento";
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		listaPacotesFornecedor = pacotesFornecedorFacade.listar(sql);
		if(listaPacotesFornecedor==null){
			listaPacotesFornecedor = new ArrayList<>();
		}
		gerarTotais();
	} 
	
	public void pesquisar(){
		String sql = "SELECT p FROM Pacotesfornecedor p WHERE p.fornecedor.nome like '%"+fornecedor+"%'";
		if(!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isTurismotodasvendas()){
			sql=sql+" AND p.pacotes.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
		}
		if(dataInicioVencimento!=null && dataFinalVencimento!=null){
			sql = sql + " AND p.datapagamento>='"+Formatacao.ConvercaoDataSql(dataInicioVencimento)+"'"
					  + " AND p.datapagamento<='"+Formatacao.ConvercaoDataSql(dataFinalVencimento)+"'";
		}
		if(situacao!=null && !situacao.equalsIgnoreCase("sn")){
			if(situacao.equalsIgnoreCase("Pagas")){
				sql = sql + " AND p.comprovante=TRUE";
			}else if(situacao.equalsIgnoreCase("Vencidas")){
				sql = sql + " AND p.comprovante=FALSE and p.datapagamento<'"+Formatacao.ConvercaoDataSql(new Date())+"'";
			}
		}
		if(reserva>0){
			sql = sql + " AND p.numeroreserva="+reserva;
		}
		if(produto!=null && produto.length()>0){
			sql = sql + " AND p.tipoprodutotrecho='"+produto+"'";
		}
		sql = sql +" ORDER BY p.datapagamento";
		PacotesFornecedorFacade pacotesFornecedorFacade = new PacotesFornecedorFacade();
		listaPacotesFornecedor = pacotesFornecedorFacade.listar(sql);
		if(listaPacotesFornecedor==null){
			listaPacotesFornecedor = new ArrayList<>();
		}
		gerarTotais();
	}
	
	public void limpar(){
		fornecedor = "";
		dataInicioVencimento = null;
		dataFinalVencimento = null;
		situacao = "sn";
		produto = "";
		reserva = 0;
		gerarListaPacotesFornecedor();
	}
	
	public String anexarComprovante(Pacotesfornecedor pacotesfornecedor){ 
		if(usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==1
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()==3){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("pacotesfornecedor", pacotesfornecedor); 
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			options.put("contentHeight", 480); 
			RequestContext.getCurrentInstance().openDialog("anexarComprovanteTurismo");
		}else Mensagem.lancarMensagemErro("Acesso negado!", "Você não possui autorização para realizar está ação.");
		return "";
	}
	
	public String emailEnviado(Pacotesfornecedor pacotesfornecedor){ 
		if(pacotesfornecedor.isComprovante()){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("pacotesfornecedor", pacotesfornecedor);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			RequestContext.getCurrentInstance().openDialog("enviarEmail", options, null);
		}else Mensagem.lancarMensagemErro("Atenção!", "Documento de comprovante de pagamento não encontra-se disponivel.");
		return "";
	}
	
	public String anexarInvoice(Pacotesfornecedor pacotesfornecedor){  
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("pacotesfornecedor", pacotesfornecedor); 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("anexarInvoiceTurismo"); 
		return "";
	}
	
	public boolean btnVermelhoComprovante(Pacotesfornecedor pacotesfornecedor){
		if(pacotesfornecedor.isComprovante()){
			return false;
		}else return true;
	}
	
	public boolean btnVerdeComprovante(Pacotesfornecedor pacotesfornecedor){
		if(!pacotesfornecedor.isComprovante()){
			return false;
		}else return true;
	}
	
	public String corBtnEmail(Pacotesfornecedor pacotesfornecedor){
		if(pacotesfornecedor.isEmailenviado()){
			return "../../resources/img/emailFicha.png";
		}else return "../../resources/img/emailNaoEnviado.png";
	} 
	
	public boolean btnVermelhoInvoice(Pacotesfornecedor pacotesfornecedor){
		FornecedorPacoteArquivoInvoiceFacade fornecedorPacoteArquivoInvoiceFacade = new FornecedorPacoteArquivoInvoiceFacade();
		Fornecedorpacotearquivoinvoice invoice = fornecedorPacoteArquivoInvoiceFacade.consultar
				("SELECT f FROM Fornecedorpacotearquivoinvoice f WHERE f.pacotesfornecedor.idpacotesfornecedor="
						+pacotesfornecedor.getIdpacotesfornecedor());
		if(invoice!=null && invoice.getIdfornecedorpacotearquivoinvoice()!=null){
			return false;
		}else return true;
	}
	
	public boolean btnVerdeInvoice(Pacotesfornecedor pacotesfornecedor){
		FornecedorPacoteArquivoInvoiceFacade fornecedorPacoteArquivoInvoiceFacade = new FornecedorPacoteArquivoInvoiceFacade();
		Fornecedorpacotearquivoinvoice invoice = fornecedorPacoteArquivoInvoiceFacade.consultar
				("SELECT f FROM Fornecedorpacotearquivoinvoice f WHERE f.pacotesfornecedor.idpacotesfornecedor="
						+pacotesfornecedor.getIdpacotesfornecedor());
		if(invoice!=null && invoice.getIdfornecedorpacotearquivoinvoice()!=null){
			return true;
		}else return false;
	}
	
	public void retornoDialogInvoice(SelectEvent event) {
		if (event.getObject() instanceof Pacotesfornecedor) {
			Pacotesfornecedor pacotesfornecedor = (Pacotesfornecedor) event.getObject();
			if (pacotesfornecedor!=null && pacotesfornecedor.getIdpacotesfornecedor() != null) {
				Mensagem.lancarMensagemInfo("Salvo com sucesso", "");  
			}
		}
	}
	
	public void retornoDialogComprovante(SelectEvent event) {
		if (event.getObject() instanceof Pacotesfornecedor) {
			Pacotesfornecedor pacotesfornecedor = (Pacotesfornecedor) event.getObject();
			if (pacotesfornecedor!=null && pacotesfornecedor.getIdpacotesfornecedor() != null) {
				Mensagem.lancarMensagemInfo("Salvo com sucesso", ""); 
				pacotesfornecedor.setComprovante(true);
			}
		}
	}
	
	public void retornoDialogEmail(SelectEvent event) {
		if (event.getObject() instanceof Pacotesfornecedor) {
			Pacotesfornecedor pacotesfornecedor = (Pacotesfornecedor) event.getObject();
			if (pacotesfornecedor!=null && pacotesfornecedor.getIdpacotesfornecedor() != null) {
				Mensagem.lancarMensagemInfo("Salvo com sucesso", ""); 
				pacotesfornecedor.setEmailenviado(true);
			}
		}
	}
	
	public void gerarTotais(){
		totalprazo=0.0f;
		totalvencer=0.0f;
		totalvencidas=0.0f;
		for (int i = 0; i < listaPacotesFornecedor.size(); i++) { 
			if(listaPacotesFornecedor.get(i).getDatapagamento()!=null){
				String hoje = Formatacao.ConvercaoDataPadrao(new Date()); 
				Date hojeData = Formatacao.ConvercaoStringData(hoje);
				if(listaPacotesFornecedor.get(i).getDatapagamento().after(hojeData)
						&& !listaPacotesFornecedor.get(i).isComprovante()){
					//a vencer
					totalprazo = totalprazo + listaPacotesFornecedor.get(i).getValor(); 
				}if(listaPacotesFornecedor.get(i).getDatapagamento().equals(hojeData)
						&& !listaPacotesFornecedor.get(i).isComprovante()){  
					//vencendo
					totalvencer = totalvencer + listaPacotesFornecedor.get(i).getValor();   
				}if(listaPacotesFornecedor.get(i).getDatapagamento().before(hojeData)
						&& !listaPacotesFornecedor.get(i).isComprovante()){
					//vencidas
					totalvencidas = totalvencidas + listaPacotesFornecedor.get(i).getValor();
				}
			}
		}
	}
	
	public String valorVencidas(){
		return Formatacao.formatarFloatString(totalvencidas);
	}
	
	public String valorVencer(){
		return Formatacao.formatarFloatString(totalvencer);
	}
	
	public String valorPrazo(){
		return Formatacao.formatarFloatString(totalprazo);
	}
	
	public String visualizarComprovante(Pacotesfornecedor pacotesfornecedor) {
		FornecedorPacoteArquivoPagamentoFacade fornecedorPacoteArquivoPagamentoFacade = new FornecedorPacoteArquivoPagamentoFacade();
		Fornecedorpacotearquivopagamento fornecedorpacotearquivopagamento = fornecedorPacoteArquivoPagamentoFacade.consultar
				("SELECT f FROM Fornecedorpacotearquivopagamento f WHERE f.pacotesfornecedor.idpacotesfornecedor="
						+pacotesfornecedor.getIdpacotesfornecedor());
		if(fornecedorpacotearquivopagamento!=null && fornecedorpacotearquivopagamento.getIdfornecedorpacotearquivopagamento()!=null) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			try {
				externalContext.redirect("http://"+ftpDados.getHost()+":82/ftproot/systm/turismo/comprovantes/"+fornecedorpacotearquivopagamento.getNomeftp());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public String visualizarInvoice(Pacotesfornecedor pacotesfornecedor) {
		FornecedorPacoteArquivoInvoiceFacade fornecedorPacoteArquivoInvoiceFacade = new FornecedorPacoteArquivoInvoiceFacade();
		Fornecedorpacotearquivoinvoice fornecedorpacotearquivoinvoice = fornecedorPacoteArquivoInvoiceFacade.consultar
				("SELECT f FROM Fornecedorpacotearquivoinvoice f WHERE f.pacotesfornecedor.idpacotesfornecedor="
						+pacotesfornecedor.getIdpacotesfornecedor());
		if(fornecedorpacotearquivoinvoice!=null && fornecedorpacotearquivoinvoice.getIdfornecedorpacotearquivoinvoice()!=null) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			try {
				externalContext.redirect("http://"+ftpDados.getHost()+":82/ftproot/systm/turismo/invoice/"+fornecedorpacotearquivoinvoice.getNomeftp());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	
}
