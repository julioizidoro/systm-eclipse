package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadQuestionarioHeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB UsuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Cliente cliente;
	private Questionariohe questionarioHe;
	private List<Pais> listaPais;
	private Lead lead;
	private String voltarControleVendas = "";
	private boolean habilitarNivel12 = true;
	private boolean habilitarNivel3 = false;
	private boolean habilitarNotas = true;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		questionarioHe = (Questionariohe) session.getAttribute("questionariohe");
		lead = (Lead) session.getAttribute("lead");
		voltarControleVendas = (String) session.getAttribute("voltarControleVendas");
		session.removeAttribute("voltarControleVendas");
		session.removeAttribute("lead");
		session.removeAttribute("questionariohe");
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		if (questionarioHe == null) {
			questionarioHe = new Questionariohe();
			cliente = new Cliente();
			if (lead != null) {
				cliente = lead.getCliente();
			}
			questionarioHe.setUsuario(UsuarioLogadoMB.getUsuario());
		} else {
			cliente = questionarioHe.getCliente();
			if (questionarioHe.getPais1() != null) {
				verificarNivel();
			}
			if (questionarioHe.getResultadotesteonline() != null && questionarioHe.getResultadotesteonline().equalsIgnoreCase("Sim")) {
				habilitarNotas = false;
			}
		}

		if (cliente == null && lead != null) {
			cliente = lead.getCliente();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return UsuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		UsuarioLogadoMB = usuarioLogadoMB;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Questionariohe getQuestionarioHe() {
		return questionarioHe;
	}

	public void setQuestionarioHe(Questionariohe questionarioHe) {
		this.questionarioHe = questionarioHe;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public boolean isHabilitarNivel12() {
		return habilitarNivel12;
	}

	public void setHabilitarNivel12(boolean habilitarNivel12) {
		this.habilitarNivel12 = habilitarNivel12;
	}

	public boolean isHabilitarNivel3() {
		return habilitarNivel3;
	}

	public void setHabilitarNivel3(boolean habilitarNivel3) {
		this.habilitarNivel3 = habilitarNivel3;
	}

	public boolean isHabilitarNotas() {
		return habilitarNotas;
	}

	public void setHabilitarNotas(boolean habilitarNotas) {
		this.habilitarNotas = habilitarNotas;
	}

	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCliente", options, null);
		return "";
	}

	public void retornoDialogCliente(SelectEvent event) {
		cliente = (Cliente) event.getObject();
	}

	public String cancelar() {
		if (voltarControleVendas != null) {
			if (voltarControleVendas.length() > 1) {
				return voltarControleVendas;
			}
		}
		return "consquestionarioHe";
	}

	public String salvar(String situacao) {
		if (validarDados()) {
			ClienteFacade clienteFacade = new ClienteFacade();
			cliente = clienteFacade.salvar(cliente);
			questionarioHe.setCliente(cliente);
			if (questionarioHe.getIdquestionariohe() == null) {
				questionarioHe.setUsuario(UsuarioLogadoMB.getUsuario());
				questionarioHe.setDataenvio(new Date());
			}
			questionarioHe.setSituacao(situacao);
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			questionarioHe = questionarioHeFacade.salvar(questionarioHe);
			Mensagem.lancarMensagemInfo("Questionario salvo com sucesso!", "");
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			return "consquestionarioHe";
		} 
		return "";
	}
	
	
	public void verificarNivel() {
		if (questionarioHe.getPais1().equalsIgnoreCase("Portugal")) {
			habilitarNivel3  = true;
			habilitarNivel12 = false;
		}else {
			habilitarNivel12 = true;
			habilitarNivel3 = false;
		}
	}
	
	
	public boolean validarDados() {
		if (cliente == null || cliente.getIdcliente() == null) {
			Mensagem.lancarMensagemInfo("Cliente não informado", "");
			return false;
		}
		
		if (questionarioHe.getDiplomas() == null || questionarioHe.getDiplomas().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe o nome do curso", "");
			return false;
		}
		
		if (questionarioHe.getNivelcetificado() == null || questionarioHe.getNivelcetificado().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe o Nivel mais alto de escolaridade no Brasil", "");
			return false;
		}
		
		if (questionarioHe.getOntuacaotoefl() == null || questionarioHe.getOntuacaotoefl().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe a Pontuação no teste de proficiência ou teste online", "");
			return false;
		}
		
		if (questionarioHe.getOcupacao1() == null || questionarioHe.getOcupacao1().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe Descreva suas duas última principais ocupações profissionais", "");
			return false;
		}
		
		
		if (questionarioHe.getPrograma() == null || questionarioHe.getPrograma().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe Programa / Área de interesse", "");
			return false;
		}
		
		if (questionarioHe.getNivelcertificadointeresse() == null || questionarioHe.getNivelcertificadointeresse().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe Nível de Certificação de interesse", "");
			return false;
		}
		
		
		if (questionarioHe.getPais1() == null || questionarioHe.getPais1().length() <=0) {
			Mensagem.lancarMensagemInfo("Informe o Destino em que prefere estudar", "");
			return false;
		}
		
		
		if (questionarioHe.getDataprograma() == null) {
			Mensagem.lancarMensagemInfo("Informe Data aproximada do Programa", "");
			return false;
		}
		
		if (questionarioHe.getPrecisatrabalahar() == null || questionarioHe.getPrecisatrabalahar().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe Preciso trabalhar durante meu curso", "");
			return false;
		}
		
		
		if (questionarioHe.getInteresseemimigrar() == null || questionarioHe.getInteresseemimigrar().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe Tenho interesse em imigrar", "");
			return false;
		}
		
		
		if (questionarioHe.getObservacao() == null || questionarioHe.getObservacao().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe a Observações e parecer do consultor", "");
			return false;
		}
		
		if (questionarioHe.getPrecisatrabalahar() == null || questionarioHe.getPrecisatrabalahar().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe 'Preciso trabalhar durante meu curso?'", "");
			return false;
		}
		
		if (questionarioHe.getInteresseemimigrar() == null || questionarioHe.getInteresseemimigrar().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe 'Tenho interesse m Imigrar?'", "");
			return false;
		}
		
		if (questionarioHe.getVistotrabalho() == null || questionarioHe.getVistotrabalho().length() <= 0) {
			Mensagem.lancarMensagemInfo("Informe 'Tenho interesse em visto de trabalho após o curso?'", "");
			return false;
		}
		return true;
	}
	
	
	
	public void verificarEnem() {
		if (questionarioHe.getResultadotesteonline() != null && questionarioHe.getResultadotesteonline().equalsIgnoreCase("Sim")) {
			habilitarNotas = false;
		} else {
			habilitarNotas = true;
		}
	}
	
	
	

}
