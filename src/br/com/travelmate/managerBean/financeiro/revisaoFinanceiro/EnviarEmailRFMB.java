package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.VendaMotivoPendenciaFacade;
import br.com.travelmate.facade.VendaPendenciaFacade;
import br.com.travelmate.facade.VendaPendenciaHistoricoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.email.EnviarEmail;
import br.com.travelmate.model.Vendamotivopendencia;
import br.com.travelmate.model.Vendapendencia;
import br.com.travelmate.model.Vendapendenciahistorico;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EnviarEmailRFMB implements Serializable{

	/**
	 * 
	 */
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private static final long serialVersionUID = 1L;
	private String emailDestinatario = "";
	private String emailConsultor = "";
	private String texto = "";
	private List<String> listaEmails;
	private String assunto;
	private Vendas venda;
	private String textoRodape;
	private List<Vendamotivopendencia> listaVendaMotivoPencencia;
	private Date dataProximoContato;
	private Vendamotivopendencia vendamotivopendencia;
	private Vendapendencia vendapendencia;
	private Vendapendenciahistorico vendapendenciahistorico;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		session.removeAttribute("venda");
		carregarDados();
		gerarListaMotivoPendencia();
	}


	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public String getEmailConsultor() {
		return emailConsultor;
	}

	public void setEmailConsultor(String emailConsultor) {
		this.emailConsultor = emailConsultor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<String> getListaEmails() {
		return listaEmails;
	}

	public void setListaEmails(List<String> listaEmails) {
		this.listaEmails = listaEmails;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTextoRodape() {
		return textoRodape;
	}

	public void setTextoRodape(String textoRodape) {
		this.textoRodape = textoRodape;
	}

	public Vendas getVenda() {
		return venda;
	}


	public void setVenda(Vendas venda) {
		this.venda = venda;
	}


	public List<Vendamotivopendencia> getListaVendaMotivoPencencia() {
		return listaVendaMotivoPencencia;
	}


	public void setListaVendaMotivoPencencia(List<Vendamotivopendencia> listaVendaMotivoPencencia) {
		this.listaVendaMotivoPencencia = listaVendaMotivoPencencia;
	}


	public Date getDataProximoContato() {
		return dataProximoContato;
	}


	public void setDataProximoContato(Date dataProximoContato) {
		this.dataProximoContato = dataProximoContato;
	}


	public Vendamotivopendencia getVendamotivopendencia() {
		return vendamotivopendencia;
	}


	public void setVendamotivopendencia(Vendamotivopendencia vendamotivopendencia) {
		this.vendamotivopendencia = vendamotivopendencia;
	}


	public Vendapendencia getVendapendencia() {
		return vendapendencia;
	}


	public void setVendapendencia(Vendapendencia vendapendencia) {
		this.vendapendencia = vendapendencia;
	}


	public Vendapendenciahistorico getVendapendenciahistorico() {
		return vendapendenciahistorico;
	}


	public void setVendapendenciahistorico(Vendapendenciahistorico vendapendenciahistorico) {
		this.vendapendenciahistorico = vendapendenciahistorico;
	}


	public void cancelar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		RequestContext.getCurrentInstance().closeDialog(new Vendapendencia());
	}

	public String enviarEmail() throws FileNotFoundException {
		VendaPendenciaFacade vendaPendenciaFacade = new VendaPendenciaFacade();
		VendaPendenciaHistoricoFacade vendaPendenciaHistoricoFacade = new VendaPendenciaHistoricoFacade();
		if (validarDados()) {
			if (listaEmails != null && listaEmails.size() > 0) {
				if (emailConsultor != null) {
					listaEmails.add(emailConsultor);
				}
				EnviarEmail enviarEmail = null;
				enviarEmail = new EnviarEmail(null, null, null, usuarioLogadoMB.getUsuario(),
						emailConsultor, texto, usuarioLogadoMB.getUsuario().getNome(), assunto, venda.getCliente().getNome(),
						listaEmails, textoRodape, null, null);
				enviarEmail.enviarEmail();
				vendapendencia = new Vendapendencia();
				vendapendencia.setDataproximocontato(dataProximoContato);
				vendapendencia.setVendamotivopendencia(vendamotivopendencia);
				vendapendencia.setVendas(venda);
				vendapendencia.setRelato("Enviado e-mail para o consultor");
				vendapendencia = vendaPendenciaFacade.salvar(vendapendencia);
				vendapendenciahistorico = new Vendapendenciahistorico();
				vendapendenciahistorico.setAssunto(assunto);
				vendapendenciahistorico.setContato(venda.getCliente().getNome());
				vendapendenciahistorico.setDatahistorico(new Date());
				vendapendenciahistorico.setUsuario(usuarioLogadoMB.getUsuario());
				vendapendenciahistorico.setVendapendencia(vendapendencia);
				vendaPendenciaHistoricoFacade.salvar(vendapendenciahistorico);
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("venda", venda);
				RequestContext.getCurrentInstance().closeDialog(vendapendencia);
				return "";
			}
		}
		return "";
	}

	public void removerEmail(String email) {
		listaEmails.remove(email);
	}

	public void adicionarEmail() {
		if (listaEmails == null) {
			listaEmails = new ArrayList<>();
		}
		if (emailDestinatario != null) {
			if (Formatacao.validarEmail(emailDestinatario)) {
				listaEmails.add(emailDestinatario);
				emailDestinatario = "";
			}
		} else
			Mensagem.lancarMensagemErro("", "Destinatario não informado.");
	}



	public void carregarDados() {
		assunto = "Pêndencia Financeira da Venda - ID " + venda.getIdvendas();
		emailDestinatario = venda.getCliente().getEmail();
		emailConsultor = usuarioLogadoMB.getUsuario().getEmail();
		texto = "";
		textoRodape = "Caso tenham alguma dpuvida estamos a disposição.";

	}
	
	
	public void gerarListaMotivoPendencia(){
		VendaMotivoPendenciaFacade vendaMotivoPendenciaFacade = new VendaMotivoPendenciaFacade();
		listaVendaMotivoPencencia = vendaMotivoPendenciaFacade.lista("Select v From Vendamotivopendencia v");
		if (listaVendaMotivoPencencia == null) {
			listaVendaMotivoPencencia = new ArrayList<>();
		}
		
	}
	
	public boolean validarDados(){
		if (vendamotivopendencia == null) {
			Mensagem.lancarMensagemInfo("Informe o motivo da Pendência", "");
			return false;
		}
		return true;
	}

}
