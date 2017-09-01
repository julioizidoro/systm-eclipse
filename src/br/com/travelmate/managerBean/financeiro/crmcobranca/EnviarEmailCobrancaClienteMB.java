package br.com.travelmate.managerBean.financeiro.crmcobranca;

import java.io.FileNotFoundException;
import java.io.Serializable;
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
public class EnviarEmailCobrancaClienteMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String emailDestinatario = "";
	private String emailConsultor = "";
	private String texto = "";
	private List<String> listaEmails;
	private String assunto;
	private Vendas venda;
	private String textoRodape;
	private List<Vendamotivopendencia> listaVendaMotivoPencencia;
	private Date dataProximoContato;
	private Vendapendenciahistorico vendapendenciahistorico;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		session.removeAttribute("venda");
		carregarDados();
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
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public String enviarEmail() throws FileNotFoundException {
		if (listaEmails != null && listaEmails.size() > 0) {
				if (emailConsultor != null) {
					listaEmails.add(emailConsultor);
				}
				EnviarEmail enviarEmail = null;
				enviarEmail = new EnviarEmail(null, null, null, usuarioLogadoMB.getUsuario(),
						emailConsultor, texto, usuarioLogadoMB.getUsuario().getNome(), assunto, "Anderson",
						listaEmails, textoRodape, null, null);
				enviarEmail.enviarEmail();
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("venda", venda);
				RequestContext.getCurrentInstance().closeDialog(true);
				return "";
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
		emailDestinatario = "";
		emailConsultor = usuarioLogadoMB.getUsuario().getEmail();
		texto = "";
		textoRodape = "Caso tenham alguma dúvida estamos a disposição.";

	}
	

}
