package br.com.travelmate.managerBean.banco;

import java.io.Serializable;  
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.BancoFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Banco;  
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadBancoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private AplicacaoMB aplicacaoMB;
	private Banco banco;      

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		banco = (Banco) session.getAttribute("banco");
		session.removeAttribute("banco"); 
		if (banco == null) { 
			banco = new Banco(); 
		} 
	}
  
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	} 

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}
 
	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public void validarDados(String msg) { 
		if (banco.getEmailgerente() != null && banco.getEmailgerente().length() > 2) {  
			if (Formatacao.validarEmail(banco.getEmailgerente())) {
				msg = msg + "\n" + "Email do gerente invalido.";
			}
		}
		if (banco.getNome() == null || banco.getNome().length() < 2) {
			msg = msg + "\n" + "Nome não informado.";
		}
		if (banco.getAgencia() == null || banco.getAgencia().length() < 2) {
			msg = msg + "\n" + "Agencia não informada.";
		}
		if (banco.getConta() == null || banco.getConta().length() < 2) {
			msg = msg + "\n" + "Conta não informada.";
		}
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			BancoFacade bancoFacade = new BancoFacade(); 
			banco = bancoFacade.salvar(banco);
			return "consBanco";
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}
 
	public String cancelar() {
		return "consBanco";
	}  
}
