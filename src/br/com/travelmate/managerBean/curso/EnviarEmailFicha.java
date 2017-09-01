package br.com.travelmate.managerBean.curso;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Curso;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class EnviarEmailFicha implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Curso curso;
	private String emailDe;
	private String emailPara;
	private String emailCC;
	private String assunto;
	private String senhaEmail;
	private String textoEmail;
	private String nomeArquivoFicha;
    private String nomeArquivoContrato;
    private String nomeArquivoRecibo;
	
	@PostConstruct()
    public void init(){
		 FacesContext fc = FacesContext.getCurrentInstance();
	     HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	     curso = (Curso) session.getAttribute("curso");
	     emailPara = curso.getVendas().getCliente().getEmail();
	     getUsuarioLogadoMB();
	     emailDe = usuarioLogadoMB.getUsuario().getEmail();
    }

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getEmailDe() {
		return emailDe;
	}

	public void setEmailDe(String emailDe) {
		this.emailDe = emailDe;
	}

	public String getEmailPara() {
		return emailPara;
	}

	public void setEmailPara(String emailPara) {
		this.emailPara = emailPara;
	}

	public String getEmailCC() {
		return emailCC;
	}

	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	
	public String getSenhaEmail() {
		return senhaEmail;
	}

	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}

	public String getTextoEmail() {
		return textoEmail;
	}

	public void setTextoEmail(String textoEmail) {
		this.textoEmail = textoEmail;
	}
	

	public String getNomeArquivoFicha() {
		return nomeArquivoFicha;
	}

	public void setNomeArquivoFicha(String nomeArquivoFicha) {
		this.nomeArquivoFicha = nomeArquivoFicha;
	}

	public String getNomeArquivoContrato() {
		return nomeArquivoContrato;
	}

	public void setNomeArquivoContrato(String nomeArquivoContrato) {
		this.nomeArquivoContrato = nomeArquivoContrato;
	}

	public String getNomeArquivoRecibo() {
		return nomeArquivoRecibo;
	}

	public void setNomeArquivoRecibo(String nomeArquivoRecibo) {
		this.nomeArquivoRecibo = nomeArquivoRecibo;
	}

	public String enviarEmail(){
		String msg = " ";
        if (msg.length()<5){
        	String s = senhaEmail;
            Formatacao f = new Formatacao();
            f.enviarEmailProdutos(emailPara, emailCC, s, emailDe, assunto, textoEmail, nomeArquivoFicha, nomeArquivoContrato, nomeArquivoRecibo);
        }
        RequestContext.getCurrentInstance().closeDialog("consCurso");
        return "";
	}
	
	
}
