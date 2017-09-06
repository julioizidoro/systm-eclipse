package br.com.travelmate.managerBean.crm;

import java.io.IOException;
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

import br.com.travelmate.dao.AcessoUnidadeDao;
import br.com.travelmate.facade.LeadEncaminhadoFacade;
import br.com.travelmate.facade.LeadFacade;
import br.com.travelmate.facade.PaisProdutoFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadencaminhado;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
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
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private AcessoUnidadeDao acessoUnidadeDao;
	private String imagemNovos = "novos";
	private String imagemHoje = "hojeClick";
	private String imagemAtrasados = "atrasados";
	private String imagemProx = "prox";
	private String imagemTodos = "todos";
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
	private List<Paisproduto> listaPais;
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

	@PostConstruct()
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			funcao = (String) session.getAttribute("funcao"); 
			PesquisaBean pesquisa = (PesquisaBean) session.getAttribute("pesquisa");
			session.removeAttribute("pesquisa");
			session.removeAttribute("funcao");
			if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialcrmunidade()) {
				acessoResponsavelUnidade = true;
			}
			if (usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerencialcrm()) {
				acessoResponsavelGerencial = true;
				acessoResponsavelUnidade = false;
			} 
			listaProdutos = GerarListas.listarProdutos("");
			listaProgramas = GerarListas.listarProdutos(""); 
			listaUsuario = GerarListas.listarUsuarios("Select u FROM Usuario u where u.situacao='Ativo'"
					+ " and u.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio() + " order by u.nome"); 
			if ((usuarioLogadoMB.getUsuario().getUnidadenegocio().getResponsavelcrm() == usuarioLogadoMB.getUsuario()
					.getIdusuario()) || (acessoResponsavelGerencial) || (acessoResponsavelUnidade)) {
				acessoResponsavelUnidade = true;
				habilitarComboUsuario = false;
				if(acessoResponsavelGerencial){ 
					acessoResponsavelUnidade = false;
					habilitarComboUnidade = false;
					listaUnidade = GerarListas.listarUnidade();
				}
				listaTipoContato = GerarListas.listarTipoContato("select t from Tipocontato t order by t.tipo");
			}else {
				Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
						+usuarioLogadoMB.getUsuario().getIdusuario());
				if(acessounidade!=null) {
					if(acessounidade.isCrm()) {
						habilitarComboUsuario = false;
					}
				}
			}
			if (funcao == null || funcao.length() == 0) {
				funcao = "hoje";
			}
			if(pesquisa!=null){
				dataProxFinal=pesquisa.getDataProxFinal();
				dataProxInicio= pesquisa.getDataProxInicio();
				dataUltFinal=pesquisa.getDataUltFinal();
				dataUltInicio=pesquisa.getDataUltInicio();
				nomeCliente=pesquisa.getNomeCliente();
				programas=pesquisa.getProgramas();
				situacao=pesquisa.getSituacao();
				tipocontato=pesquisa.getTipocontato();
				unidadenegocio=pesquisa.getUnidadenegocio();
				gerarListaConsultor();
				usuario=pesquisa.getUsuario();
				status=pesquisa.getStatus();
				dataInseridoInicial=pesquisa.getDataInseridoInicial();
				dataInseridoFinal=pesquisa.getDataInseridoFinal();
				pesquisar(); 
				mudarCoresBotões(funcao);
			}else if (!acessoResponsavelGerencial){
				pesquisar(); 
				mudarCoresBotões(funcao);
			}else{
				unidadenegocio=usuarioLogadoMB.getUsuario().getUnidadenegocio();
				gerarListaConsultor();
				usuario=usuarioLogadoMB.getUsuario();
				pesquisar();
			}
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			int idProduto = 0;
			idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
			listaPais = paisProdutoFacade.listar(idProduto);
			if(sql!=null && sql.length()>0){
				gerarListaLead(sql);
			}
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

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
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
		sql = "";
		this.funcao = funcao;
		if (funcao.equalsIgnoreCase("novos")) {
			imagemNovos = "novosClick";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todos";
			sql = "select l from Lead l where l.situacao<5 and l.tipocontato.tipo='Novo' and l.dataultimocontato is null ";
		} else if (funcao.equalsIgnoreCase("hoje")) {
			imagemNovos = "novos";
			imagemHoje = "hojeClick";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todos";
			sql = "select l from Lead l where l.situacao<5 and l.dataultimocontato>'2016-01-01' and"
					+ " l.dataproximocontato='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
		} else if (funcao.equalsIgnoreCase("atrasados")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasadoClick";
			imagemProx = "prox";
			imagemTodos = "todos";
			Date data = new Date();
			sql = "select l from Lead l where l.situacao<5 and l.dataultimocontato>'2016-01-01' and"
					+ " l.dataproximocontato < '" + Formatacao.ConvercaoDataSql(data) + "'";
		} else if (funcao.equalsIgnoreCase("prox")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "proxClick";
			imagemTodos = "todos";
			Date data;
			try {
				data = Formatacao.SomarDiasDatas(new Date(), 7);
			} catch (Exception e) {
				data = null;
			}
			sql = "select l from Lead l where l.situacao<5 and l.dataultimocontato>'2016-01-01' and"
					+ " l.dataproximocontato>'" + Formatacao.ConvercaoDataSql(new Date())
					+ "' and l.dataproximocontato<'" + Formatacao.ConvercaoDataSql(data) + "'";
		} else if (funcao.equalsIgnoreCase("todos")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todosClick";
			sql = "select l from Lead l where l.situacao<5";
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
		if (nomeCliente != null && nomeCliente.length() > 0) {
			sql = sql + " and l.cliente.nome like '" + nomeCliente + "%'";
		}

		if (dataProxInicio != null && dataProxFinal != null) {
			sql = sql + " and l.dataproximocontato>='" + Formatacao.ConvercaoDataSql(dataProxInicio) + "' and "
					+ "l.dataproximocontato<='" + Formatacao.ConvercaoDataSql(dataProxFinal) + "'";
		}

		if (dataUltInicio != null && dataUltFinal != null) {
			sql = sql + " and l.dataultimocontato>='" + Formatacao.ConvercaoDataSql(dataUltInicio) + "' and "
					+ "l.dataultimocontato<='" + Formatacao.ConvercaoDataSql(dataUltFinal) + "'";
		}
		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and l.produtos.idprodutos=" + programas.getIdprodutos();
		}
		if (tipocontato != null && tipocontato.getIdtipocontato() != null) {
			sql = sql + " and l.tipocontato.idtipocontato=" + tipocontato.getIdtipocontato();
		}
		sql = sql + " order by l.dataproximocontato";
		gerarListaLead(sql);
	}

	public void gerarListaLead(String sql) {
		LeadFacade leadFacade = new LeadFacade();
		listaLead = leadFacade.lista(sql);
	}

	public String retornarCoresSituacao(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#9ACD32;";
		} else if (numeroSituacao == 3) {
			return "#FF8C00;";
		} else if (numeroSituacao == 4) {
			return "#B22222;";
		} else if (numeroSituacao == 5) {
			return "#228B22;";
		} else if (numeroSituacao == 6) {
			return "#8B8989;";
		} else if (numeroSituacao == 7) {
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
		PesquisaBean pesquisa = new PesquisaBean();
		pesquisa.setDataProxFinal(dataProxFinal);
		pesquisa.setDataProxInicio(dataProxInicio);
		pesquisa.setDataUltFinal(dataUltFinal);
		pesquisa.setDataUltInicio(dataUltInicio);
		pesquisa.setNomeCliente(nomeCliente);
		pesquisa.setProgramas(programas);
		pesquisa.setSituacao(situacao);
		pesquisa.setTipocontato(tipocontato);
		pesquisa.setUnidadenegocio(unidadenegocio);
		pesquisa.setStatus(status);
		pesquisa.setUsuario(usuario);
		pesquisa.setDataInseridoInicial(dataInseridoInicial);
		pesquisa.setDataInseridoFinal(dataInseridoFinal);
		session.setAttribute("pesquisa", pesquisa);
		return "historicoCliente";
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaUsuario = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}
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
		Date data = new Date();
		sql = "select l from Lead l where l.dataenvio<='" + Formatacao.ConvercaoDataSql(data) + "'";
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
		} else {
			sql = sql + " and l.situacao<5 ";
		}
		if (nomeCliente != null && nomeCliente.length() > 0) {
			sql = sql + " and (l.cliente.nome like '" + nomeCliente + "%' or l.cliente.email like '" + nomeCliente + "%')";
		}
		if (dataProxInicio != null && dataProxFinal != null) {
			sql = sql + " and l.dataproximocontato>='" + Formatacao.ConvercaoDataSql(dataProxInicio) + "' and "
					+ "l.dataproximocontato<='" + Formatacao.ConvercaoDataSql(dataProxFinal) + "'";
		}

		if (dataUltInicio != null && dataUltFinal != null) {
			sql = sql + " and l.dataultimocontato>='" + Formatacao.ConvercaoDataSql(dataUltInicio) + "' and "
					+ "l.dataultimocontato<='" + Formatacao.ConvercaoDataSql(dataUltFinal) + "'";
		}
		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and l.produtos.idprodutos=" + programas.getIdprodutos();
		}
		if (tipocontato != null && tipocontato.getIdtipocontato() != null) {
			sql = sql + " and l.tipocontato.idtipocontato=" + tipocontato.getIdtipocontato();
		}
		if (status != null && status.length()>0 && !status.equalsIgnoreCase("0")) {
			if(status.equalsIgnoreCase("Novos")){
				sql = sql + " and l.tipocontato.tipo='Novo' and l.dataultimocontato is null";
			}else if(status.equalsIgnoreCase("Atrasados")){ 
				sql = sql + " and l.dataproximocontato<'" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			}else if(status.equalsIgnoreCase("Hoje")){ 
				sql = sql + " and l.dataproximocontato='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			}else if(status.equalsIgnoreCase("Prox. 7 Dias")){ 
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
		if(dataInseridoInicial!=null && dataInseridoFinal!=null){
			sql = sql + " and l.dataenvio>'" + Formatacao.ConvercaoDataSql(dataInseridoInicial)
				+ "' and l.dataenvio<'" + Formatacao.ConvercaoDataSql(dataInseridoFinal) + "'";
		}
		sql = sql + " order by l.dataproximocontato";
		gerarListaLead(sql);
		for (int i = 0; i < listaLead.size(); i++) {
			if (!listaLead.get(i).getSituacao().equals("0")) {
				todos = todos + 1;
			}
			if (listaLead.get(i).getDataultimocontato() == null && !listaLead.get(i).getSituacao().equals("0")
					&& listaLead.get(i).getTipocontato().getTipo().equalsIgnoreCase("Novo")) {
				novos = novos + 1;
			} else if ((listaLead.get(i).getDataultimocontato() != null)
					&& (listaLead.get(i).getDataproximocontato()) != null
					&& (Formatacao.ConvercaoDataSql(listaLead.get(i).getDataproximocontato())
							.equalsIgnoreCase(Formatacao.ConvercaoDataSql(new Date())))
					&& (!listaLead.get(i).getSituacao().equals("0"))) {
				hoje = hoje + 1;
			} else if (listaLead.get(i).getDataultimocontato() != null
					&& listaLead.get(i).getDataproximocontato() != null
					&& listaLead.get(i).getDataproximocontato().before(new Date())
					&& !listaLead.get(i).getSituacao().equals("0")) {
				atrasados = atrasados + 1;
			} else if (listaLead.get(i).getDataultimocontato() != null
					&& listaLead.get(i).getDataproximocontato() != null
					&& listaLead.get(i).getDataproximocontato().after(new Date())
					&& !listaLead.get(i).getSituacao().equals("0")) {
				Date data7 = null;
				try {
					data7 = Formatacao.SomarDiasDatas(new Date(), 7);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (listaLead.get(i).getDataproximocontato().before(data7)) {
					prox7 = prox7 + 1;
				}
			}
		}
	}

	public void limparPesquisa() {
		nomeCliente = null;
		dataProxFinal = null;
		dataProxInicio = null;
		dataUltFinal = null;
		dataUltInicio = null;
		usuario = null;
		unidadenegocio = null;
		programas = null;
		situacao = "0";
		tipocontato = null;
		situacao = "0";
		dataInseridoInicial=null;
		dataInseridoInicial=null;
		pesquisar();
	}

	public void atualizarFollowUp(Lead lead) {
		if (produto != null && produto.getIdprodutos() != null) {
			lead.setProdutos(produto);
		}
		if (pais != null && pais.getIdpais() != null) {
			lead.setPais(pais);
		}
		LeadFacade leadFacade = new LeadFacade();
		leadFacade.salvar(lead);
		produto = new Produtos();
		pais = new Pais();
	}

	public void encaminharFollowUp(Lead lead) {
		if (consultorEncaminhar != null) {
			LeadFacade leadFacade = new LeadFacade();
			lead.setUsuario(consultorEncaminhar);
			leadFacade.salvar(lead);
			Leadencaminhado leadencaminhado = new Leadencaminhado();
			leadencaminhado.setLead(lead);
			leadencaminhado.setData(new Date());
			leadencaminhado.setHora(Formatacao.foramtarHoraString());
			leadencaminhado.setUsuariode(usuarioLogadoMB.getUsuario());
			leadencaminhado.setUsuariopara(consultorEncaminhar);
			LeadEncaminhadoFacade leadEncaminhadoFacade = new LeadEncaminhadoFacade();
			leadEncaminhadoFacade.salvar(leadencaminhado);
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
		if (lead != null) {
			try {
				session.setAttribute("posicao", listaLead.size());
				listaLead.add(lead);
				session.setAttribute("listaLead", listaLead);
				FacesContext.getCurrentInstance().getExternalContext().redirect("historicoCliente.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	public String historico() { 
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("historicoCliente.jsf");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "historicoCliente";
	}

	public void mensagemPesquisa() {
		if ((situacao.equals("5")) || (situacao.equals("6"))) {
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
	
	public void dialogReturnEncaminharLead(SelectEvent event){
		if (event.getObject()!=null){
			if (event.getObject() instanceof Lead){
				Lead lead = (Lead) event.getObject();
				listaLead.remove(lead);
			}
		}
	}
}
