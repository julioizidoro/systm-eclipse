package br.com.travelmate.managerBean.voluntariadoprojeto;
 
import br.com.travelmate.facade.VoluntariadoProjetoFacade;
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Fornecedorcidade; 
import br.com.travelmate.model.Voluntariadoprojeto;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;  
import javax.annotation.PostConstruct; 
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadVoluntariadoProjetoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Voluntariadoprojeto voluntariadoprojeto;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Fornecedorcidade fornecedorcidade = (Fornecedorcidade) session.getAttribute("fornecedorcidade");
		voluntariadoprojeto = (Voluntariadoprojeto) session.getAttribute("voluntariadoprojeto");
		session.removeAttribute("voluntariadoprojeto");
		getAplicacaoMB();
		if (voluntariadoprojeto == null && fornecedorcidade != null) {
			voluntariadoprojeto = new Voluntariadoprojeto();
			voluntariadoprojeto.setFornecedorcidade(fornecedorcidade);
		}
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Voluntariadoprojeto getVoluntariadoprojeto() {
		return voluntariadoprojeto;
	}

	public void setVoluntariadoprojeto(Voluntariadoprojeto voluntariadoprojeto) {
		this.voluntariadoprojeto = voluntariadoprojeto;
	}
 
	public String salvar() {
		if (voluntariadoprojeto.getDescricao() != null && voluntariadoprojeto.getDescricao().length()>0) { 
			VoluntariadoProjetoFacade voluntariadoProjetoFacade = new VoluntariadoProjetoFacade();
			voluntariadoprojeto = voluntariadoProjetoFacade.salvar(voluntariadoprojeto);
			RequestContext.getCurrentInstance().closeDialog(voluntariadoprojeto);
			return "";
		} else {
			Mensagem.lancarMensagemErro("Atencao! ", "Campos obrigatorios nao preenchidos.");
			return "";
		}
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null); 
		return "";
	}
 
}
