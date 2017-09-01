package br.com.travelmate.managerBean.coeficiente;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped; 
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CoeficienteJurosFacade; 
import br.com.travelmate.model.Coeficientejuros; 
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadCoeficienteMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private Coeficientejuros coeficientejuros;      

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		coeficientejuros = (Coeficientejuros) session.getAttribute("coeficientejuros");
		session.removeAttribute("coeficientejuros"); 
		if (coeficientejuros == null) { 
			coeficientejuros = new Coeficientejuros(); 
		} 
	}
   

	public Coeficientejuros getCoeficientejuros() {
		return coeficientejuros;
	} 

	public void setCoeficientejuros(Coeficientejuros coeficientejuros) {
		this.coeficientejuros = coeficientejuros;
	} 

	public void validarDados(String msg) { 
		if (coeficientejuros.getNumeroParcelas() == null || coeficientejuros.getNumeroParcelas()==0) {
			msg = msg + "\n" + "Número de parcelas não informado.";
		}
		if (coeficientejuros.getCoeficiente() == null) {
			msg = msg + "\n" + "Coeficiente não informado.";
		} 
	}

	public String salvar() throws SQLException {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			CoeficienteJurosFacade coeficienteJurosFacade = new CoeficienteJurosFacade(); 
			coeficientejuros = coeficienteJurosFacade.salvar(coeficientejuros);
			 RequestContext.getCurrentInstance().closeDialog(coeficientejuros);
			return "consCoeficiente";
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}
 
	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(new Coeficientejuros());
		return "consCoeficiente";
	}  
}
