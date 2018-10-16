package br.com.travelmate.managerBean.higherEducation;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.NotificacaoFacade;

import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.facade.UsuarioDepartamentoUnidadeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Notificacao;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Usuariodepartamentounidade;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadQuestionarioHeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private PaisDao paisDao;
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
	private boolean novaficha;
	private boolean habilitarNivel3pais = true;
	private List<Cidade> listaCidade;
	private List<Cidade> listaCidade2;
	private Cidade cidade;
	private Cidade cidade2;
	private Cidadepaisproduto cidadepaisproduto;

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

		listaPais = paisDao.listar();
		if (questionarioHe == null) {
			questionarioHe = new Questionariohe();
			cliente = new Cliente();
			if (lead != null) {
				cliente = lead.getCliente();
			}
			questionarioHe.setUsuario(UsuarioLogadoMB.getUsuario());
			novaficha = true;
		} else {
			cliente = questionarioHe.getCliente();
			if (questionarioHe.getPais1() != null) {
				verificarNivel();
			}
			if (questionarioHe.getResultadotesteonline() != null
					&& questionarioHe.getResultadotesteonline().equalsIgnoreCase("Sim")) {
				habilitarNotas = false;
			}
			novaficha = false;
			verificarCidade();
		}

		if (cliente == null && lead != null) {
			cliente = lead.getCliente();
		}
		verificarNivel();
		verificarNivel2();
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

	public boolean isHabilitarNivel3pais() {
		return habilitarNivel3pais;
	}

	public void setHabilitarNivel3pais(boolean habilitarNivel3pais) {
		this.habilitarNivel3pais = habilitarNivel3pais;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Cidade> getListaCidade2() {
		return listaCidade2;
	}

	public void setListaCidade2(List<Cidade> listaCidade2) {
		this.listaCidade2 = listaCidade2;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Cidade getCidade2() {
		return cidade2;
	}

	public void setCidade2(Cidade cidade2) {
		this.cidade2 = cidade2;
	}

	public Cidadepaisproduto getCidadepaisproduto() {
		return cidadepaisproduto;
	}

	public void setCidadepaisproduto(Cidadepaisproduto cidadepaisproduto) {
		this.cidadepaisproduto = cidadepaisproduto;
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

	public String salvar() {
		if (validarDados()) {
			ClienteFacade clienteFacade = new ClienteFacade();
			cliente = clienteFacade.salvar(cliente);
			questionarioHe.setCliente(cliente);
			if (cidade != null) {
				questionarioHe.setCidade(cidade.getNome());
			}
			if (cidade2 != null) {
				questionarioHe.setCidade2(cidade2.getNome());
			}
				
			if (questionarioHe.getIdquestionariohe() == null) {
				questionarioHe.setUsuario(UsuarioLogadoMB.getUsuario());
				questionarioHe.setDataenvio(new Date());
				questionarioHe.setSituacao("Processo");
				questionarioHe.setTipoquestionario("SysTM");
			}
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			questionarioHe = questionarioHeFacade.salvar(questionarioHe);
			Mensagem.lancarMensagemInfo("Questionario salvo com sucesso!", "");
			if (voltarControleVendas != null) {
				if (voltarControleVendas.length() > 1) {
					return voltarControleVendas;
				}
			}
			if (!novaficha
					&& (UsuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 7
							|| UsuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1)
					&& !questionarioHe.getSituacao().equalsIgnoreCase("Processo")) {
				notificarDepartamento();
			}
			return "consquestionarioHe";
		}
		return "";
	}

	public void verificarNivel() {
		if (questionarioHe.getPais1() != null && !questionarioHe.getPais1().equalsIgnoreCase("")) {
			habilitarNivel3 = false;
			listarCidade();
		} else {
			habilitarNivel3 = true;
		}
	}
	
	public void verificarNivel2() {
		if (questionarioHe.getPais2() != null && !questionarioHe.getPais2().equalsIgnoreCase("")) {
			habilitarNivel3pais = false;
			listarCidade2();
		} else {
			habilitarNivel3pais = true;
		}
	}

	public boolean validarDados() {
		if (cliente == null || cliente.getIdcliente() == null) {
			Mensagem.lancarMensagemInfo("Cliente não informado", "");
			return false;
		}
		return true;
	}

	public void verificarEnem() {
		if (questionarioHe.getResultadotesteonline() != null
				&& questionarioHe.getResultadotesteonline().equalsIgnoreCase("Sim")) {
			habilitarNotas = false;
		} else {
			habilitarNotas = true;
		}
	}

	public void notificarDepartamento() {
		UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
		List<Usuariodepartamentounidade> listaResponsaveis = usuarioDepartamentoUnidadeFacade
				.listar("SELECT u FROM Usuariodepartamentounidade u WHERE "
						+ " u.departamento.iddepartamento=7 and u.unidadenegocio.idunidadeNegocio="
						+ UsuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		for (int i = 0; i < listaResponsaveis.size(); i++) {
			Notificacao notificacao = new Notificacao();
			NotificacaoFacade notificacaoFacade = new NotificacaoFacade();
			notificacao.setTitulo("Questionario Alterado - " + questionarioHe.getIdquestionariohe());
			notificacao.setUnidade(questionarioHe.getUsuario().getUnidadenegocio().getNomeFantasia());
			notificacao.setCliente(questionarioHe.getCliente().getNome());
			notificacao.setFornecedor("");
			notificacao.setDatainicio(null);
			notificacao.setConsultor(questionarioHe.getUsuario().getNome());
			notificacao.setTipovenda("");
			notificacao.setValorvenda(0.0f);
			notificacao.setCambio(0.0f);
			notificacao.setMoeda("");
			notificacao.setLimpar(false);
			notificacao.setData(new Date());
			notificacao.setImagem("alterado");
			notificacao.setUsuario(listaResponsaveis.get(i).getUsuario());
			String hora = Formatacao.foramtarHoraString();
			notificacao.setHora(hora);
			notificacaoFacade.salvar(notificacao);
		}
	}

	public void listarCidade() {
		if (questionarioHe.getPais1() != null) {
			CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
			List<Cidadepaisproduto> listaCidadeProduto = cidadePaisProdutosFacade.listar(
					"SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.produtos.idprodutos=22 and c.paisproduto.pais.nome like '%"
							+ questionarioHe.getPais1() + "%'" + " order by c.cidade.nome");
			listaCidade = new ArrayList<Cidade>();
			if (listaCidadeProduto == null) {
				listaCidadeProduto = new ArrayList<Cidadepaisproduto>();
			}
			for (int i = 0; i < listaCidadeProduto.size(); i++) {
				listaCidade.add(listaCidadeProduto.get(i).getCidade());
			}
		}
	}

	public void listarCidade2() {
		if (questionarioHe.getPais2() != null) {
			CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
			List<Cidadepaisproduto> listaCidadeProduto = cidadePaisProdutosFacade.listar(
					"SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.produtos.idprodutos=22 and c.paisproduto.pais.nome like '%"
							+ questionarioHe.getPais2() + "%'" + " order by c.cidade.nome");
			listaCidade2 = new ArrayList<Cidade>();
			if (listaCidadeProduto == null) {
				listaCidadeProduto = new ArrayList<Cidadepaisproduto>();
			}
			for (int i = 0; i < listaCidadeProduto.size(); i++) {
				listaCidade2.add(listaCidadeProduto.get(i).getCidade());
			}
		}
	}
	
	public void verificarCidade() {
		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
		if (questionarioHe.getCidade() != null && questionarioHe.getCidade().length() > 0) {
			cidadepaisproduto = cidadePaisProdutosFacade
					.consultar("Select c FROM Cidadepaisproduto c WHERE c.cidade.nome like '%"
							+ questionarioHe.getCidade() + "%' and c.paisproduto.produtos.idprodutos=22");
			if (cidadepaisproduto != null) {
				cidade = cidadepaisproduto.getCidade();
			}
		}

		if (questionarioHe.getCidade2() != null && questionarioHe.getCidade2().length() > 0) {
			cidadepaisproduto = cidadePaisProdutosFacade
					.consultar("Select c FROM Cidadepaisproduto c WHERE c.cidade.nome like '%"
							+ questionarioHe.getCidade2() + "%' and c.paisproduto.produtos.idprodutos=22");
			if (cidadepaisproduto != null) {
				cidade2 = cidadepaisproduto.getCidade();
			}
		}
	}

}
