package br.com.travelmate.managerBean.crm;

import java.io.IOException;
import java.io.Serializable;
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

import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadEncaminhadoDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Controlehighschool;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadencaminhado;
import br.com.travelmate.model.Leadposvenda;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class FollowUpMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PaisDao paisDao;
	@Inject
	private LeadEncaminhadoDao leadEncaminhadoDao;
	@Inject
	private LeadDao leadDao;
	@Inject 
	private LeadPosVendaDao leadPosVendaDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private String imagemNovos = "novos";
	private String imagemHoje = "hojeClick";
	private String imagemAtrasados = "atrasados";
	private String imagemProx = "prox";
	private String imagemTodos = "todos";
	private List<Lead> listaLeadTotal;
	private List<Lead> listaLead;
	private boolean acessoResponsavelUnidade = false;
	private boolean acessoResponsavelGerencial = false;
	// pesquisa
	private String nomeCliente;
	private Date dataProxInicio;
	private Date dataProxFinal;
	private Date dataUltInicio;
	private Date dataUltFinal;
	private boolean habilitarComboUnidade = true;
	private boolean habilitarComboUsuario = true;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private Produtos produto;
	private List<Produtos> listaProdutos;
	private Usuario consultorEncaminhar;
	private String funcao;
	private Pais pais;
	private List<Pais> listaPais;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private String situacao;
	private Produtos programas;
	private List<Produtos> listaProgramas;
	private List<Tipocontato> listaTipoContato;
	private Tipocontato tipocontato;
	private int novos;
	private int atrasados;
	private int hoje;
	private int prox7;
	private int todos;
	private String sql;
	private String status;
	private Date dataInseridoInicial;
	private Date dataInseridoFinal;
	private boolean mostrarLeads;
	private boolean mostrarPosVenda;
	private int posvenda;
	private List<Leadposvenda> listaPosVenda;
	private String imagemPosVenda = "posvenda";
	private Lead lead;
	private boolean campoCurso = false;
	private boolean campoVoluntariado = false;
	private Pais paisConsulta;
	private List<Pais> listaPaisConsulta;

	@SuppressWarnings("unchecked")
	@PostConstruct()
	public void init() {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			funcao = (String) session.getAttribute("funcao");
			session.removeAttribute("funcao");
			if (funcao == null || funcao.length() == 0) {
				funcao = "todos";
			}
			this.listaPosVenda = (List<Leadposvenda>) session.getAttribute("listaposvenda");
			this.listaProdutos = (List<Produtos>) session.getAttribute("listaproduto");
			this.listaPais = (List<Pais>) session.getAttribute("listapais");
			this.listaTipoContato = (List<Tipocontato>) session.getAttribute("listatipocontato");
			this.listaUsuario = (List<Usuario>) session.getAttribute("listausuario");
			this.listaUnidade = (List<Unidadenegocio>) session.getAttribute("listaunidade");
			this.listaProgramas = listaProdutos;
			this.listaPaisConsulta = listaPais;
			this.sql = (String) session.getAttribute("sql");
			session.removeAttribute("sql");
			session.removeAttribute("listaposvenda");
			session.removeAttribute("listaproduto");
			session.removeAttribute("listapais");
			session.removeAttribute("listatipocontato");
			session.removeAttribute("listausuario");
			session.removeAttribute("unidade");
			session.removeAttribute("listaLead");
			
			if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialcrm()) {
				acessoResponsavelGerencial = true;
				acessoResponsavelUnidade = false;
			}else if (usuarioLogadoMB.getUsuario().getAcessounidade().isCrm()) {
				acessoResponsavelUnidade = true;
				acessoResponsavelGerencial = false;
			} else if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialcrmunidade()) {
				acessoResponsavelUnidade = true;
			}
			
			if (acessoResponsavelGerencial || acessoResponsavelUnidade) {
				habilitarComboUsuario = false;
				if (acessoResponsavelGerencial) {
					acessoResponsavelUnidade = false;
					habilitarComboUnidade = false;
					if (listaUnidade==null) {
						listaUnidade = GerarListas.listarUnidade();
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getAcessounidade() != null) {
					if (usuarioLogadoMB.getUsuario().getAcessounidade().isCrm()) {
						habilitarComboUsuario = false;
					}
				}
			}
			iniciarListas();
		mostrarPosVenda = false;
		mostrarLeads = true;
		gerarListaPosVenda();
	}
	
	public void iniciarListas() {
		
		usuario = usuarioLogadoMB.getUsuario();
		unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		if (listaProdutos==null) {
			listaProdutos = GerarListas.listarProdutos("");
			listaProgramas =listaProdutos;
		}
		if (listaUsuario==null) {
			listaUsuario = GerarListas.listarUsuarios("Select u FROM Usuario u where u.situacao='Ativo'"
					+ " and u.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() + " order by u.nome");
			
		}
		if (listaUnidade==null) {
			
		}
		if (listaTipoContato==null) {
			listaTipoContato = GerarListas.listarTipoContato("select t from Tipocontato t order by t.tipo");
		}
		if (listaPais==null) {
			
			listaPais = paisDao.listar("");
			listaPaisConsulta = listaPais;
		}
		if (sql!=null) {
			gerarListaLead();
		}else {
			pesquisarInicial();
		}
		
		if (listaPosVenda==null) {
			gerarListaPosVenda();
		}
		
	}

	public String getImagemNovos() {
		return imagemNovos;
	}

	public void setImagemNovos(String imagemNovos) {
		this.imagemNovos = imagemNovos;
	}

	public String getImagemHoje() {
		return imagemHoje;
	}

	public void setImagemHoje(String imagemHoje) {
		this.imagemHoje = imagemHoje;
	}

	public String getImagemAtrasados() {
		return imagemAtrasados;
	}

	public void setImagemAtrasados(String imagemAtrasados) {
		this.imagemAtrasados = imagemAtrasados;
	}

	public String getImagemProx() {
		return imagemProx;
	}

	public void setImagemProx(String imagemProx) {
		this.imagemProx = imagemProx;
	}

	public String getImagemTodos() {
		return imagemTodos;
	}

	public void setImagemTodos(String imagemTodos) {
		this.imagemTodos = imagemTodos;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Lead> getListaLead() {
		return listaLead;
	}

	public void setListaLead(List<Lead> listaLead) {
		this.listaLead = listaLead;
	}

	public boolean isAcessoResponsavelUnidade() {
		return acessoResponsavelUnidade;
	}

	public void setAcessoResponsavelUnidade(boolean acessoResponsavelUnidade) {
		this.acessoResponsavelUnidade = acessoResponsavelUnidade;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Date getDataProxInicio() {
		return dataProxInicio;
	}

	public void setDataProxInicio(Date dataProxInicio) {
		this.dataProxInicio = dataProxInicio;
	}

	public Date getDataProxFinal() {
		return dataProxFinal;
	}

	public void setDataProxFinal(Date dataProxFinal) {
		this.dataProxFinal = dataProxFinal;
	}

	public Date getDataUltInicio() {
		return dataUltInicio;
	}

	public void setDataUltInicio(Date dataUltInicio) {
		this.dataUltInicio = dataUltInicio;
	}

	public Date getDataUltFinal() {
		return dataUltFinal;
	}

	public void setDataUltFinal(Date dataUltFinal) {
		this.dataUltFinal = dataUltFinal;
	}

	public boolean isHabilitarComboUsuario() {
		return habilitarComboUsuario;
	}

	public void setHabilitarComboUsuario(boolean habilitarComboUsuario) {
		this.habilitarComboUsuario = habilitarComboUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Produtos getProduto() {
		return produto;
	}

	public void setProduto(Produtos produto) {
		this.produto = produto;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public Usuario getConsultorEncaminhar() {
		return consultorEncaminhar;
	}

	public void setConsultorEncaminhar(Usuario consultorEncaminhar) {
		this.consultorEncaminhar = consultorEncaminhar;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String getFuncao() {
		return funcao;
	}

	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Produtos getProgramas() {
		return programas;
	}

	public void setProgramas(Produtos programas) {
		this.programas = programas;
	}

	public List<Produtos> getListaProgramas() {
		return listaProgramas;
	}

	public void setListaProgramas(List<Produtos> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}

	public List<Tipocontato> getListaTipoContato() {
		return listaTipoContato;
	}

	public void setListaTipoContato(List<Tipocontato> listaTipoContato) {
		this.listaTipoContato = listaTipoContato;
	}

	public Tipocontato getTipocontato() {
		return tipocontato;
	}

	public void setTipocontato(Tipocontato tipocontato) {
		this.tipocontato = tipocontato;
	}

	public boolean isHabilitarComboUnidade() {
		return habilitarComboUnidade;
	}

	public boolean isAcessoResponsavelGerencial() {
		return acessoResponsavelGerencial;
	}

	public void setAcessoResponsavelGerencial(boolean acessoResponsavelGerencial) {
		this.acessoResponsavelGerencial = acessoResponsavelGerencial;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setHabilitarComboUnidade(boolean habilitarComboUnidade) {
		this.habilitarComboUnidade = habilitarComboUnidade;
	}

	public int getNovos() {
		return novos;
	}

	public void setNovos(int novos) {
		this.novos = novos;
	}

	public int getAtrasados() {
		return atrasados;
	}

	public void setAtrasados(int atrasados) {
		this.atrasados = atrasados;
	}

	public int getHoje() {
		return hoje;
	}

	public void setHoje(int hoje) {
		this.hoje = hoje;
	}

	public int getProx7() {
		return prox7;
	}

	public void setProx7(int prox7) {
		this.prox7 = prox7;
	}

	public int getTodos() {
		return todos;
	}

	public void setTodos(int todos) {
		this.todos = todos;
	}

	public Date getDataInseridoInicial() {
		return dataInseridoInicial;
	}

	public void setDataInseridoInicial(Date dataInseridoInicial) {
		this.dataInseridoInicial = dataInseridoInicial;
	}

	public Date getDataInseridoFinal() {
		return dataInseridoFinal;
	}

	public void setDataInseridoFinal(Date dataInseridoFinal) {
		this.dataInseridoFinal = dataInseridoFinal;
	}

	public List<Lead> getListaLeadTotal() {
		return listaLeadTotal;
	}

	public void setListaLeadTotal(List<Lead> listaLeadTotal) {
		this.listaLeadTotal = listaLeadTotal;
	}

	public boolean isMostrarPosVenda() {
		return mostrarPosVenda;
	}

	public void setMostrarPosVenda(boolean mostrarPosVenda) {
		this.mostrarPosVenda = mostrarPosVenda;
	}

	public int getPosvenda() {
		return posvenda;
	}

	public void setPosvenda(int posvenda) {
		this.posvenda = posvenda;
	}

	public List<Leadposvenda> getListaPosVenda() {
		return listaPosVenda;
	}

	public void setListaPosVenda(List<Leadposvenda> listaPosVenda) {
		this.listaPosVenda = listaPosVenda;
	}

	public String getImagemPosVenda() {
		return imagemPosVenda;
	}

	public void setImagemPosVenda(String imagemPosVenda) {
		this.imagemPosVenda = imagemPosVenda;
	}

	public boolean isMostrarLeads() {
		return mostrarLeads;
	}

	public void setMostrarLeads(boolean mostrarLeads) {
		this.mostrarLeads = mostrarLeads;
	}

	public Lead getLead() {
		return lead;
	}

	public void setLead(Lead lead) {
		this.lead = lead;
	}

	public boolean isCampoCurso() {
		return campoCurso;
	}

	public void setCampoCurso(boolean campoCurso) {
		this.campoCurso = campoCurso;
	}

	public boolean isCampoVoluntariado() {
		return campoVoluntariado;
	}

	public void setCampoVoluntariado(boolean campoVoluntariado) {
		this.campoVoluntariado = campoVoluntariado;
	}

	public List<Pais> getListaPaisConsulta() {
		return listaPaisConsulta;
	}

	public void setListaPaisConsulta(List<Pais> listaPaisConsulta) {
		this.listaPaisConsulta = listaPaisConsulta;
	}

	public Pais getPaisConsulta() {
		return paisConsulta;
	}

	public void setPaisConsulta(Pais paisConsulta) {
		this.paisConsulta = paisConsulta;
	}

	public String novoLead() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("telaRetorno", "followup");
		RequestContext.getCurrentInstance().openDialog("cadLead", options, null);
		return "";
	}

	public void mudarCoresBotões(String funcao) {
		this.funcao = funcao;
		listaLead = new ArrayList<Lead>();  
		String dataHoje = Formatacao.ConvercaoDataPadrao(new Date());
		Date dHoje = Formatacao.ConvercaoStringData(dataHoje);
		if (funcao.equalsIgnoreCase("novos")) {
			imagemNovos = "novosClick";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todos";
			imagemPosVenda = "posvenda"; 
			mostrarPosVenda = false;
			mostrarLeads = true;
			for (int i = 0; i < listaLeadTotal.size(); i++) {
				String dataRecebimento = Formatacao.ConvercaoDataPadrao(listaLeadTotal.get(i).getDataenvio());
				Date dRecebimento = Formatacao.ConvercaoStringData(dataRecebimento);
				Date data2 = null;
				try {
					data2 = Formatacao.SomarDiasDatas(dRecebimento, 2);
				} catch (Exception e) {
					  
				}
				if (listaLeadTotal.get(i).getSituacao() == 1 && !data2.before(dHoje) && listaLeadTotal.get(i).getDataproximocontato() == null) {
					listaLead.add(listaLeadTotal.get(i));
				}
			}
		} else if (funcao.equalsIgnoreCase("hoje")) {
			imagemNovos = "novos";
			imagemHoje = "hojeClick";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todos";
			imagemPosVenda = "posvenda"; 
			mostrarPosVenda = false;
			mostrarLeads = true;
			for (int i = 0; i < listaLeadTotal.size(); i++) {
				if(listaLeadTotal.get(i).getDataproximocontato()!=null && listaLeadTotal.get(i).getSituacao() <=5) {
					String dataProximo = Formatacao.ConvercaoDataPadrao(listaLeadTotal.get(i).getDataproximocontato());
					Date dProximo = Formatacao.ConvercaoStringData(dataProximo);
					if (dProximo.equals(dHoje)) {
						listaLead.add(listaLeadTotal.get(i));
					}
				}
			}
		} else if (funcao.equalsIgnoreCase("atrasados")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasadoClick";
			imagemProx = "prox";
			imagemTodos = "todos";
			imagemPosVenda = "posvenda"; 
			mostrarPosVenda = false;
			mostrarLeads = true;
			for (int i = 0; i < listaLeadTotal.size(); i++) {
				String dataRecebimento = Formatacao.ConvercaoDataPadrao(listaLeadTotal.get(i).getDataenvio());
				Date dRecebimento = Formatacao.ConvercaoStringData(dataRecebimento);
				Date data2 = null;
				try {
					data2 = Formatacao.SomarDiasDatas(dRecebimento, 2);
				} catch (Exception e) {
					  
				}
				if (listaLeadTotal.get(i).getDataproximocontato() != null && listaLeadTotal.get(i).getSituacao() <=5) {
					String dataProximo = Formatacao.ConvercaoDataPadrao(listaLeadTotal.get(i).getDataproximocontato());
					Date dProximo = Formatacao.ConvercaoStringData(dataProximo);
					if (dProximo.before(dHoje)) {
						listaLead.add(listaLeadTotal.get(i));
					}
				}else if(listaLeadTotal.get(i).getSituacao() == 1 && data2.before(dHoje)){
					listaLead.add(listaLeadTotal.get(i));
				}
			}
		} else if (funcao.equalsIgnoreCase("prox")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "proxClick";
			imagemTodos = "todos";
			imagemPosVenda = "posvenda"; 
			mostrarPosVenda = false;
			mostrarLeads = true;
			Date data;
			try {
				data = Formatacao.SomarDiasDatas(new Date(), 7);
			} catch (Exception e) {
				data = null;
			}
			for (int i = 0; i < listaLeadTotal.size(); i++) {
				if (listaLeadTotal.get(i).getDataultimocontato() != null
						&& listaLeadTotal.get(i).getDataproximocontato()!=null && listaLeadTotal.get(i).getSituacao() <=5) {
					String dataProximo = Formatacao.ConvercaoDataPadrao(listaLeadTotal.get(i).getDataproximocontato());
					Date dProximo = Formatacao.ConvercaoStringData(dataProximo);
					if (dProximo.after(dHoje)
							&& dProximo.before(data)) {
						listaLead.add(listaLeadTotal.get(i));
					}
				}
			}
		} else if (funcao.equalsIgnoreCase("todos")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todosClick";
			imagemPosVenda = "posvenda"; 
			listaLead = listaLeadTotal;
			mostrarPosVenda = false;
			mostrarLeads = true;
		} else if (funcao.equalsIgnoreCase("posvenda")) { 
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todos";
			imagemPosVenda = "posvendaClick";  
			mostrarPosVenda = true;
			mostrarLeads = false;
		}
	}

	public void gerarListaLead() {
		listaLeadTotal = leadDao.lista(sql);
		if(listaLeadTotal==null) {
			listaLeadTotal = new ArrayList<Lead>();
		}
		gerarBotoesLead();
	}

	public String retornarCoresSituacao(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#2E5495;";
		} else if (numeroSituacao == 3) {
			return "#9ACD32;";
		} else if (numeroSituacao == 4) {
			return "#FF8C00;";
		} else if (numeroSituacao == 5) {
			return "#B22222;";
		} else if (numeroSituacao == 6) {
			return "#228B22;";
		} else if (numeroSituacao == 7) {
			return "#8B8989;";
		} else if (numeroSituacao == 8) {
			return "#9400D3;";
		}
		return "";
	}

	public String historicoCliente(int posicao) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaLead", listaLead);
		session.setAttribute("posicao", posicao);
		session.setAttribute("funcao", funcao);
		session.setAttribute("sql", sql);
		session.setAttribute("listaposvenda", listaPosVenda);
		session.setAttribute("listaproduto", listaProdutos);
		session.setAttribute("listapais", listaPais);
		session.setAttribute("listatipocontato", listaTipoContato);
		session.setAttribute("listausuario", listaUsuario);
		session.setAttribute("voltar", "followUp");
		return "historicoCliente";
	}
	
	public String historicoCliente(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("lead", lead); 
		session.setAttribute("sql", sql);
		session.setAttribute("listaLead", listaLead);
		session.setAttribute("listaposvenda", listaPosVenda);
		session.setAttribute("listaproduto", listaProdutos);
		session.setAttribute("listapais", listaPais);
		session.setAttribute("listatipocontato", listaTipoContato);
		session.setAttribute("listausuario", listaUsuario);
		return "historicoCliente";
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaUsuario = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}else {
			listaUsuario = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo' order by u.nome");
		}
		usuario = null;
	}
	
	public void pesquisarInicial() {
		sql = "select l from Lead l where l.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		sql = sql + " and l.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
		sql = sql + " and l.situacao<=5  and l.dataenvio is not null";
		sql = sql + " order by l.dataproximocontato";
		gerarListaLead();
	}
	
	public void pesquisar() {
		imagemNovos = "novos";
		imagemHoje = "hoje";
		imagemAtrasados = "atrasados";
		imagemProx = "prox";
		imagemTodos = "todosClick";
		novos = 0;
		atrasados = 0;
		hoje = 0;
		prox7 = 0;
		todos = 0;
		//sql = "select l from Lead l where l.dataenvio<='" + Formatacao.ConvercaoDataSql(data) + "'";
		sql = "select l from Lead l where (l.cliente.nome like '%" + nomeCliente + "%' or l.cliente.email like '%"+nomeCliente+"%') and l.dataenvio is not null"; 
		boolean outroParametro = false;
		if (nomeCliente.length()>0) {
			outroParametro=true;
		}
		if (acessoResponsavelGerencial) {
			if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sql = sql + " and l.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and l.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		}
		if (acessoResponsavelUnidade || acessoResponsavelGerencial) {
			if (usuario != null && usuario.getIdusuario() != null) {
				sql = sql + " and l.usuario.idusuario=" + usuario.getIdusuario();
			}
		} else {
			sql = sql + " and l.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
		}
		if (situacao != null && situacao.length() > 0 && !situacao.equals("0")) {
			funcao = "todos";
			sql = sql + " and l.situacao='" + situacao + "'";
		}else if(nomeCliente.length() == 0){
			sql = sql + " and l.situacao<=5";
		}
		if (dataProxInicio != null && dataProxFinal != null) {
			sql = sql + " and ((l.dataproximocontato>='" + Formatacao.ConvercaoDataSql(dataProxInicio) + "' and "
					+ "l.dataproximocontato<='" + Formatacao.ConvercaoDataSql(dataProxFinal) + "') or l.situacao=1) ";
			outroParametro = true;
		}

		if (dataUltInicio != null && dataUltFinal != null) {
			sql = sql + " and ((l.dataultimocontato>='" + Formatacao.ConvercaoDataSql(dataUltInicio) + "' and "
					+ "l.dataultimocontato<='" + Formatacao.ConvercaoDataSql(dataUltFinal) + "') or l.situacao=1)";
			outroParametro = true;
		}   
		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and l.produtos.idprodutos=" + programas.getIdprodutos();
		}
		if (tipocontato != null && tipocontato.getIdtipocontato() != null) {
			sql = sql + " and l.tipocontato.idtipocontato=" + tipocontato.getIdtipocontato();
		}
		if (paisConsulta != null && paisConsulta.getIdpais() != null) {
			sql = sql + " and l.pais.idpais=" + paisConsulta.getIdpais();
		}
		if (status != null && status.length() > 0 && !status.equalsIgnoreCase("0")) {
			if (status.equalsIgnoreCase("Novos")) {
				sql = sql + " and l.tipocontato.tipo='Novo' and l.dataultimocontato is null";
			} else if (status.equalsIgnoreCase("Atrasados")) {
				sql = sql + " and l.dataproximocontato<'" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			} else if (status.equalsIgnoreCase("Hoje")) {
				sql = sql + " and l.dataproximocontato='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			} else if (status.equalsIgnoreCase("Prox. 7 Dias")) {
				Date data7;
				try {
					data7 = Formatacao.SomarDiasDatas(new Date(), 7);
				} catch (Exception e) {
					data7 = null;
				}
				sql = sql + " and l.dataproximocontato>'" + Formatacao.ConvercaoDataSql(new Date())
						+ "' and l.dataproximocontato<'" + Formatacao.ConvercaoDataSql(data7) + "'";
			}
		}
		if (dataInseridoInicial != null && dataInseridoFinal != null) {
			sql = sql + " and l.dataenvio>='" + Formatacao.ConvercaoDataSql(dataInseridoInicial) + "' and l.dataenvio<='"
					+ Formatacao.ConvercaoDataSql(dataInseridoFinal) + "'";
			outroParametro = true;
		}
		
		sql = sql + " order by l.dataproximocontato";
		if (outroParametro) {
			gerarListaLead();
			pesquisarPosVenda();
		}else {
			Mensagem.lancarMensagemInfo("PESQUISA", "Selecionar um campo de data");
			sql = null;
		}
	}
	
	public void gerarBotoesLead() {
		novos = 0;
		atrasados = 0;
		hoje = 0;
		prox7 = 0;
		todos = 0;
		todos = listaLeadTotal.size();
		String dataHoje = Formatacao.ConvercaoDataPadrao(new Date());
		Date dHoje = Formatacao.ConvercaoStringData(dataHoje);
		for (int i = 0; i < listaLeadTotal.size(); i++) {
			String dataRecebimento = Formatacao.ConvercaoDataPadrao(listaLeadTotal.get(i).getDataenvio());
			Date dRecebimento = Formatacao.ConvercaoStringData(dataRecebimento);
			String dataProximo = null;
			Date dProximo = null;
			if (listaLeadTotal.get(i).getDataproximocontato() != null) {
				dataProximo = Formatacao.ConvercaoDataPadrao(listaLeadTotal.get(i).getDataproximocontato());
				dProximo = Formatacao.ConvercaoStringData(dataProximo);
			}
			Date data2 = null;
			try {
				data2 = Formatacao.SomarDiasDatas(dRecebimento, 2);
			} catch (Exception e) {
				  
			}
			if (listaLeadTotal.get(i).getSituacao() == 1 && !data2.before(dHoje) && listaLeadTotal.get(i).getDataproximocontato() == null) {
				novos = novos + 1;
			} else if ((dProximo != null)
					&& (dProximo
							.equals(dHoje))
					&& (listaLeadTotal.get(i).getSituacao() <= 5)) {
				hoje = hoje + 1;
			} else if ((dProximo != null
					&& dProximo.before(dHoje) && (listaLeadTotal.get(i).getSituacao() <= 5)
					) || (listaLeadTotal.get(i).getSituacao() == 1 && data2.before(dHoje))) {
				atrasados = atrasados + 1;
			} else if (dProximo != null
					&& dProximo.after(dHoje)
					&& (listaLeadTotal.get(i).getSituacao() <=5)) {
				Date data7 = null;
				try {
					data7 = Formatacao.SomarDiasDatas(new Date(), 7);
				} catch (Exception e) {
					  
				}
				if (dProximo.before(data7)) {
					prox7 = prox7 + 1;
				}
			}
		}
		mudarCoresBotões(funcao);
	}

	
	public void limparPesquisa() {
		nomeCliente = null;
		dataProxFinal = null;
		dataProxInicio = null;
		dataUltFinal = null;
		dataUltInicio = null;
		usuario = usuarioLogadoMB.getUsuario();
		unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		programas = null;
		situacao = "0";
		tipocontato = null;
		dataInseridoInicial = null;
		dataInseridoFinal = null;
		paisConsulta = null;
		pesquisarInicial();
		gerarListaPosVenda();
	}

	public void atualizarFollowUp(Lead lead) {
		if (produto != null && produto.getIdprodutos() != null) {
			lead.setProdutos(produto);
		}
		if (pais != null && pais.getIdpais() != null) {
			lead.setPais(pais);
		}
		leadDao.salvar(lead);
		produto = new Produtos();
		pais = new Pais();
	}

	public void encaminharFollowUp(Lead lead) {
		if (consultorEncaminhar != null) {
			lead.setUsuario(consultorEncaminhar);
			leadDao.salvar(lead);
			Leadencaminhado leadencaminhado = new Leadencaminhado();
			leadencaminhado.setLead(lead);
			leadencaminhado.setData(new Date());
			leadencaminhado.setHora(Formatacao.foramtarHoraString());
			leadencaminhado.setUsuariode(usuarioLogadoMB.getUsuario());
			leadencaminhado.setUsuariopara(consultorEncaminhar);
			leadEncaminhadoDao.salvar(leadencaminhado);
			this.consultorEncaminhar = new Usuario();
			Mensagem.lancarMensagemInfo("Follow Up encaminhado para " + lead.getUsuario().getNome() + "!", "");
			mudarCoresBotões(funcao);
		}
	}

	public boolean painelGerencial() {
		if (acessoResponsavelGerencial || acessoResponsavelUnidade) {
			return true;
		} else
			return false;
	}

	public boolean painelConsultor() {
		if (acessoResponsavelGerencial || acessoResponsavelUnidade) {
			return false;
		} else
			return true;
	}

	public String retornoNovaLead(SelectEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Lead lead = (Lead) session.getAttribute("lead");
		session.removeAttribute("lead");
		if (lead == null) {
			lead = (Lead) event.getObject();
		}
		if (lead != null) {
			mudarCoresBotões("novos");
			try {
				session.setAttribute("posicao", listaLead.size());
				listaLead.add(lead);
				session.setAttribute("listaLead", listaLead);
				FacesContext.getCurrentInstance().getExternalContext().redirect("historicoCliente.jsf");
			} catch (IOException e) {
				  
			}
		}
		return "";
	}

	public String historico() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("historicoCliente.jsf");
			HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			session.setAttribute("listaposvenda", listaPosVenda);
			session.setAttribute("listaproduto", listaProdutos);
			session.setAttribute("listapais", listaPais);
			session.setAttribute("sql", sql);
			session.setAttribute("listatipocontato", listaTipoContato);
			session.setAttribute("listausuario", listaUsuario);
		} catch (IOException e) {
			  
		}
		
		return "historicoCliente";
	}

	public void mensagemPesquisa() {
		if ((situacao.equals("6")) || (situacao.equals("7"))) {
			if ((dataUltInicio == null) && (dataUltFinal == null)) {
				Mensagem.lancarMensagemInfo("Pesquisa", "Está situação requer data do Ultimo Contato");
			}
		}
	}

	public String encaminharLead(Lead lead) {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 700);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lead", lead);
		RequestContext.getCurrentInstance().openDialog("encaminharLead", options, null);
		return "";
	}

	public void dialogReturnEncaminharLead(SelectEvent event) {
		if (event.getObject() != null) {
			if (event.getObject() instanceof Lead) {
				Lead lead = (Lead) event.getObject();
				listaLead.remove(lead);
			}
		}
	}
	
	public void gerarListaPosVenda() {
		Date data = null;
		try {
			data = Formatacao.SomarDiasDatas(new Date(), 5);
		} catch (Exception e) { 
			  
		}
		String sql = "SELECT l FROM Leadposvenda l WHERE l.vendas.situacao<>'CANCELADA' AND l.lead.produtos.idprodutos<>3 AND l.lead.produtos.idprodutos<>6 AND l.lead.produtos.idprodutos<>7 ";
		sql = sql + " AND l.vendas.unidadenegocio.idunidadeNegocio="+usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
		sql = sql + " AND l.vendas.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
		sql = sql + " AND (l.datachegada>='"+Formatacao.ConvercaoDataSql(data)+"'"
				+ " OR l.datachegada is null) ";
		sql = sql + " order by l.datachegada";
		listaPosVenda = leadPosVendaDao.listar(sql);
		if(listaPosVenda==null) {
			listaPosVenda = new ArrayList<Leadposvenda>();
		}
		posvenda = listaPosVenda.size();
	}
	
	public void pesquisarPosVenda() { 
		String sql = "SELECT l FROM Leadposvenda l WHERE l.vendas.situacao<>'CANCELADA' AND l.lead.produtos.idprodutos<>3 AND l.lead.produtos.idprodutos<>6 AND l.lead.produtos.idprodutos<>7 ";
		if(!acessoResponsavelGerencial) {
			sql = sql + " AND l.vendas.unidadenegocio.idunidadeNegocio="+usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			if(!acessoResponsavelUnidade) {
				sql = sql + " AND l.vendas.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
			}else {
				if(usuario!=null && usuario.getIdusuario()!=null) {
					sql = sql + " AND l.vendas.usuario.idusuario=" + usuario.getIdusuario();
				}
			}
		}else {
			if(unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null) {
				sql = sql + " AND l.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
			if(usuario!=null && usuario.getIdusuario()!=null) {
				sql = sql + " AND l.vendas.usuario.idusuario=" + usuario.getIdusuario();
			}
		}
		if (dataProxInicio != null && dataProxFinal != null) {
			sql = sql + " AND l.lead.dataproximocontato>='" + Formatacao.ConvercaoDataSql(dataProxInicio) + "' AND "
					+ "l.lead.dataproximocontato<='" + Formatacao.ConvercaoDataSql(dataProxFinal) + "'";
		}
		if (situacao != null && situacao.length() > 0 && !situacao.equals("0")) {
			sql = sql + " AND l.lead.situacao='" + situacao + "'";
		}
		if (tipocontato != null && tipocontato.getIdtipocontato() != null) {
			sql = sql + " AND l.lead.tipocontato.idtipocontato=" + tipocontato.getIdtipocontato();
		}
		if (nomeCliente != null && nomeCliente.length() > 0) {
			sql = sql + " and (l.vendas.cliente.nome like '" + nomeCliente + "%' or l.vendas.cliente.email like '"
					  + nomeCliente + "%')";
		}
		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and l.vendas.produtos.idprodutos=" + programas.getIdprodutos();
		}
		sql = sql + " order by l.datachegada";
		listaPosVenda = leadPosVendaDao.listar(sql);
		if(listaPosVenda==null) {
			listaPosVenda = new ArrayList<Leadposvenda>();
		}
		posvenda = listaPosVenda.size();
	}
	
	public void salvarDataControle(Leadposvenda leadposvenda) { 
		leadposvenda = leadPosVendaDao.salvar(leadposvenda);
		int idproduto = leadposvenda.getVendas().getProdutos().getIdprodutos();
		if(idproduto == aplicacaoMB.getParametrosprodutos().getCursos()) {
			CursoFacade cursoFacade = new CursoFacade();
			Controlecurso controle = cursoFacade.consultarControleCursos(leadposvenda.getVendas().getIdvendas());
			if (controle != null) {
				controle.setDatachegadabrasil(leadposvenda.getDatachegada());
				controle.setDataEmbarque(leadposvenda.getDataembarque());
				cursoFacade.salvar(controle);
			}
		} else if (idproduto == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
			VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
			Controlevoluntariado controle = voluntariadoFacade.consultarControle(leadposvenda.getVendas().getIdvendas());
			if (controle != null) {
				controle.setDatachegadabrasil(leadposvenda.getDatachegada());
				controle.setDataembarque(leadposvenda.getDataembarque());
				voluntariadoFacade.salvar(controle);
			}
		} else if (idproduto == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
			HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
			Controlehighschool controle = highSchoolFacade.consultarControle(leadposvenda.getVendas().getIdvendas());
			if (controle != null) {
				controle.setDataRetorno(leadposvenda.getDatachegada());
				controle.setDataEmbarque(leadposvenda.getDataembarque());
				highSchoolFacade.salvar(controle);
			}
		} 
	}
	
	
	public String emitirVenda(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		int idprodutos = lead.getProdutos().getIdprodutos();
		session.setAttribute("lead", lead); 
		if (idprodutos == 13) {
			session.setAttribute("tipoVenda", "trainee");
			RequestContext.getCurrentInstance().openDialog("escolherPrograma");
		}else{
			session.setAttribute("cliente", lead.getCliente());
			session.setAttribute("voltar", "followUp");
			return "cadCliente";
		}
		return "";
	}  
	
	public String emitirVendaQuestionario(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lead", lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("voltar", "followUp");
		session.setAttribute("faseHe", "questionario");
		return "cadCliente";
	}
	
	public String emitirVendaFormulario(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lead", lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("voltar", "followUp");
		session.setAttribute("faseHe", "formulario");
		return "cadCliente";
	}
	
	public String emitirVendaFinal(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("lead", lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("voltar", "followUp");
		session.setAttribute("faseHe", "Final");
		return "cadCliente";
	} 
	
	public String retornoDialogEmissao(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String tipo = (String) session.getAttribute("tipo");
		try {
			if (tipo.equalsIgnoreCase("EUA")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../trainee/cadTrainee.jsf");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("../trainee/cadEstagioAustralia.jsf");
			}
		} catch (IOException e) {
			  
		}
		return "";
	}
	
	
	public String emitirVendaTrainee(String tipo) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		session.setAttribute("tipo", tipo);
		return "cadCliente";
	}
	
	public void pegarLead(Lead lead){
		this.lead = lead;
	}
	
	public void selecionarLead(Lead lead){
		this.lead = lead;
	}
	
	public String emitirVendaPos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		int idprodutos = lead.getProdutos().getIdprodutos();
		if (idprodutos != 13) {
			session.setAttribute("cliente", lead.getCliente());
			session.setAttribute("lead", lead); 
			if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getAupair()) {
				return "cadAuPair";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getCursos()) {
				return "cadFichaCurso";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getDemipair()) {
				return "cadDemiPair";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getProgramasTeens()) {
				return "cadCursosTeens";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighSchool()) {
				return "cadHighSchool";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getTrainee()) {
				session.setAttribute("tipo", "EUA"); 
				return "cadTrainee";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
				return "cadVoluntariado";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getWork()) {
				return "cadWorkandTravel";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPacotes()) {
				return "cadpacote";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getPassagem()) {
				return "cadPassagem";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getVisto()) {
				return "cadVistos";
			} else if (produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getSeguroPrivado()) {
				return "fichaSeguroViagem";
			}else if(produto.getIdprodutos() == aplicacaoMB.getParametrosprodutos().getHighereducation()){
				return "questionarioHe";
			}else if(produto.getIdprodutos() == 24) {
				return "cadAcomodacao";
			}
		}
		return "";
	} 
	
	
	public void filtrarPesquisa(){
		pesquisar();
	}
	
	
	public void alterarDadosCliente(Cliente cliente){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cliente", cliente);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 300);
		RequestContext.getCurrentInstance().openDialog("dadosCliente", options , null); 
	}
	
	public boolean habilitarCampoCurso(Lead lead){
		if (lead.getProdutos().getIdprodutos() == 1 || lead.getProdutos().getIdprodutos() == 20) {
			return true;
		}
		return false;
	}
	
	public boolean habilitarCampoCursoModelo(Lead lead){
		if (lead.getProdutos().getIdprodutos() == 1) {
			return true;
		}
		return false;
	}
	
	
	public boolean habilitarCampoVoluntariado(Lead lead){
		if (lead.getProdutos().getIdprodutos() == 16) {
			return true;
		}
		return false;
	}
	
	public boolean habiltiarCampos(Lead lead){
		boolean resultado = false;
		if (lead.getProdutos().getIdprodutos() == 1 || lead.getProdutos().getIdprodutos() == 20) {
			resultado = habilitarCampoCurso(lead);
		}else if(lead.getProdutos().getIdprodutos() == 16){
			resultado = habilitarCampoVoluntariado(lead);
		}else if(lead.getProdutos().getIdprodutos() == 22) {
			resultado = false;
		}
		return resultado;
	}
	
	public boolean habiltiarCamposHe(Lead lead){
		boolean resultado = false;
		if (lead.getProdutos().getIdprodutos() == 22) {
			resultado = true;
		}
		return resultado;
	}
	
	public boolean habiltiarCamposSeguro(Lead lead){
		boolean resultado = false;
		if (lead.getProdutos().getIdprodutos() == 2) {
			resultado = true;
		}
		return resultado;
	}
	
	public boolean habiltiarVenda(Lead lead){
		boolean resultado = true;
		if (lead.getProdutos().getIdprodutos() == 21) {
			resultado = false;
		}else if(lead.getProdutos().getIdprodutos() == 22) {
			resultado = false;
		}else if (lead.getProdutos().getIdprodutos() == 2) {
			resultado = false;
		}
		return resultado;
	}
	
	
	public String orcamentoManual(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead.getCliente().setLead(lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		session.setAttribute("tipoorcamento", lead.getProdutos().getDescricao());
		return "cadOrcamentoManual";
	}
	
	
	public String orcamentoTarifario(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead.getCliente().setLead(lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		return "filtrarorcamento";
	}
	
	public String orcamentoTarifarioModelo(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lead.getCliente().setLead(lead);
		session.setAttribute("cliente", lead.getCliente());
		session.setAttribute("lead", lead);
		return "inicialPacotes";
	}
	
	
	public String novoOrcamentoVoluntariado(Lead lead){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		lead.getCliente().setLead(lead);
		lead.getCliente().setClienteLead(true);
		session.setAttribute("cliente", lead.getCliente());
		return "filtrarVoluntariadoProjetoOrcamento";
	}
	
	
	public String orcamentoVitrine(){
		usuarioLogadoMB.getUsuario().setDashboard("I"); 
		return "paginainicial";
	}
	
	
	public void retornoDialogDados(SelectEvent event) {
		Cliente cliente = (Cliente) event.getObject();
		if (cliente != null) {
			Mensagem.lancarMensagemInfo("Alteração feita com sucesso", "");
		}
	}
	
	public boolean habilitarBotaoHe(Lead lead) {
		if (lead.getProdutos().getIdprodutos() == 22) {
			return true;
		}
		return false;
	}
	
	public String orcamentoHE(Lead lead) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("lead", lead);
		return "cadOrcamentoHe";
	}
	
	
	public String vendaNaoMatriz(Lead lead) {
		String vendaMatriz = "N";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		session.setAttribute("lead", lead);
		return "fichaSeguroViagem";
	}
    
	public String vendaMatriz(Lead lead) {
		String vendaMatriz = "S";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		session.setAttribute("lead", lead);
		return "fichaSeguroViagem";
	}
	
	public void questionarioOnline(Lead lead) {
		if (lead.getCliente().isOnline()) {
			lead.getCliente().setOnline(false);
			ClienteFacade clienteFacade = new ClienteFacade();
			clienteFacade.salvar(lead.getCliente());
			Mensagem.lancarMensagemInfo("Questionário Desabilitado para o cliente", "");
		}else {
			lead.getCliente().setOnline(true);
			ClienteFacade clienteFacade = new ClienteFacade();
			clienteFacade.salvar(lead.getCliente());
			Mensagem.lancarMensagemInfo("Questionário Liberado para o cliente", "");
		}
	}
	
	
	public String corQuestionario(Lead lead) {
		if (lead.getCliente().isOnline()) {
			return "color:green;";
		}else {
			return "color:red;";
		}
	}
	
	
	public boolean retornarQuestionario() {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1 || usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 7) {
			return true;
		}else {
			return false;
		}
	}
	
	

}
