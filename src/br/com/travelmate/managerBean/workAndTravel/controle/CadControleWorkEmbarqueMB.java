package br.com.travelmate.managerBean.workAndTravel.controle;

import java.io.Serializable; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ControleWorkEmbarqueFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Controleworkembarque;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadControleWorkEmbarqueMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Controlework controlework;
	private Controleworkembarque controleworkembarque;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		controlework = (Controlework) session.getAttribute("controlework");
		controleworkembarque = (Controleworkembarque) session.getAttribute("controleworkembarque");
		session.removeAttribute("controleworkembarque");
		session.removeAttribute("controlework");
		if (controleworkembarque == null) {
			gerarDadosIniciais();
		}
	}

	public Controleworkembarque getControleworkembarque() {
		return controleworkembarque;
	}

	public void setControleworkembarque(Controleworkembarque controleworkembarque) {
		this.controleworkembarque = controleworkembarque;
	}

	public Controlework getControlework() {
		return controlework;
	}

	public void setControlework(Controlework controlework) {
		this.controlework = controlework;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String salvar() {
		if(validarDados()) {
			ControleWorkEmbarqueFacade controleWorkEmbarqueFacade = new ControleWorkEmbarqueFacade();
			controleworkembarque = controleWorkEmbarqueFacade.salvar(controleworkembarque);
			WorkTravelFacade workTravelFacade = new WorkTravelFacade();
			controlework.setStatusprocesso("Passagem/Visto");
			controlework = workTravelFacade.salvar(controlework);
			Mensagem.lancarMensagemInfo("Salvo com Sucesso!", "");
			RequestContext.getCurrentInstance().closeDialog(null);
		}
		return "";
	}
	
	public void gerarDadosIniciais() {
		controleworkembarque = new Controleworkembarque();
		controleworkembarque.setControlework(controlework);
		if(controlework.getDataIniciojoboffer()!=null) {
			try {
				controleworkembarque.setDataembarqueinicial(Formatacao.SomarDiasDatas
						(controlework.getDataIniciojoboffer(), -6));
				controleworkembarque.setDataembarquefinal(Formatacao.SomarDiasDatas
						(controlework.getDataIniciojoboffer(), -1));
			} catch (Exception e) { 
				  
			}
		}
		if(controlework.getDataTerminojoboffer()!=null) {
			try {
				controleworkembarque.setDataretornoinicial(Formatacao.SomarDiasDatas
						(controlework.getDataTerminojoboffer(), 1));
				controleworkembarque.setDataretornofinal(Formatacao.SomarDiasDatas
						(controlework.getDataTerminojoboffer(), 29));
			} catch (Exception e) { 
				  
			}
		}
	}

	public boolean validarDados() {
		if(controleworkembarque.getDataembarque()!=null) {
			if(controleworkembarque.getDataembarque().before(controleworkembarque.getDataembarqueinicial())) {
				Mensagem.lancarMensagemInfo("Atenção!", "Data de embarque invalída. Menor que a data de embarque inícial possível.");
				return false;
			}
		}
		if(controleworkembarque.getDataembarque()!=null) {
			if(controleworkembarque.getDataembarque().after(controleworkembarque.getDataembarquefinal())) {
				Mensagem.lancarMensagemInfo("Atenção!", "Data de embarque invalída. Maior que a data de embarque final possível.");
				return false;
			}
		}
		if(controleworkembarque.getDataretorno()!=null) {
			if(controleworkembarque.getDataretorno().before(controleworkembarque.getDataretornoinicial())) {
				Mensagem.lancarMensagemInfo("Atenção!", "Data de Retorno invalída. Menor que a data de Retorno inícial possível.");
				return false;
			}
		}
		if(controleworkembarque.getDataretorno()!=null) {
			if(controleworkembarque.getDataretorno().after(controleworkembarque.getDataretornofinal())) {
				Mensagem.lancarMensagemInfo("Atenção!", "Data de Retorno invalída. Maior que a data de Retorno final possível.");
				return false;
			}
		}
		return true;
	}
}
