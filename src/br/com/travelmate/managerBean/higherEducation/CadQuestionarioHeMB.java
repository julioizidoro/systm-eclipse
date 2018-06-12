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
		} else {
			cliente = questionarioHe.getCliente();
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
		if (cliente != null && cliente.getIdcliente() != null) {
			questionarioHe.setCliente(cliente);
			if (questionarioHe.getIdquestionariohe() == null) {
				questionarioHe.setUsuario(UsuarioLogadoMB.getUsuario()); 
				questionarioHe.setDataenvio(new Date());
				questionarioHe.setAutorizado(false);
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
		} else
			Mensagem.lancarMensagemErro("Atenção!", "Cliente não informado!");
		return "";
	}
	
	}
