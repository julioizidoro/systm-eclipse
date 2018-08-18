package br.com.travelmate.managerBean.controleVendas;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TraineeFacade;
import br.com.travelmate.facade.UsuarioFacade;

import br.com.travelmate.facade.VistosFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB; 
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ControleVendasMB implements Serializable {

	/**
	 * 
	 */   
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private ControleVendaBean controleVendasBean;
	private List<ControleVendaBean> listaControleVendasBean;
	private List<Vendas> listaVendas;
	private String numeroFichas;
	private Integer nFichasFinalizadas;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private boolean habilitarUnidade = true;
	private List<Fornecedor> listaFornecedor;
	private Fornecedor fornecedor;
	private int idVenda;
	private String nome;
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private Curso curso;
	private Worktravel worktravel;
	private Voluntariado voluntariado;
	private Aupair aupair;
	private Trainee trainee;
	private Programasteens programateens;
	private Highschool highschool;
	private Vendas vendas;
	private Demipair demipair;
	private String voltar;
	private String obsTM;
	private He he;
	private Seguroviagem seguroViagem;
	private Vistos vistos;
	private String voltarContreVendas = "";
	private String vendaMatriz = "";
	private Usuario consultor;
	private List<Usuario> listaConsultor;
	private Produtos programas;
	private List<Produtos> listaProgramas;
	private Questionariohe questionarioHe;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct   
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaVendas = (List<Vendas>) session.getAttribute("listaVendas");
		session.removeAttribute("listaVendas");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("Curso")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
					gerarListaVendas();
				}
			}
			listaUnidadeNegocio = GerarListas.listarUnidade();
			gerarListaFornecedor();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
				gerarListaConsultor();
			}
			idVenda = 0;
			listaProgramas = GerarListas.listarProdutos("");
		}
	}

	public ControleVendaBean getControleVendasBean() {
		return controleVendasBean;
	}

	public void setControleVendasBean(ControleVendaBean controleVendasBean) {
		this.controleVendasBean = controleVendasBean;
	}

	public List<ControleVendaBean> getListaControleVendasBean() {
		return listaControleVendasBean;
	}

	public void setListaControleVendasBean(List<ControleVendaBean> listaControleVendasBean) {
		this.listaControleVendasBean = listaControleVendasBean;
	}

	public List<Vendas> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}

	public String getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(String numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public Integer getnFichasFinalizadas() {
		return nFichasFinalizadas;
	}

	public void setnFichasFinalizadas(Integer nFichasFinalizadas) {
		this.nFichasFinalizadas = nFichasFinalizadas;
	}

	public Integer getnFichasProcesso() {
		return nFichasProcesso;
	}

	public void setnFichasProcesso(Integer nFichasProcesso) {
		this.nFichasProcesso = nFichasProcesso;
	}

	public Integer getnFichasAndamento() {
		return nFichasAndamento;
	}

	public void setnFichasAndamento(Integer nFichasAndamento) {
		this.nFichasAndamento = nFichasAndamento;
	}

	public Integer getnFichaCancelada() {
		return nFichaCancelada;
	}

	public void setnFichaCancelada(Integer nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Worktravel getWorktravel() {
		return worktravel;
	}

	public void setWorktravel(Worktravel worktravel) {
		this.worktravel = worktravel;
	}

	public Voluntariado getVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
	}

	public Aupair getAupair() {
		return aupair;
	}

	public void setAupair(Aupair aupair) {
		this.aupair = aupair;
	}

	public Trainee getTrainee() {
		return trainee;
	}

	public void setTrainee(Trainee trainee) {
		this.trainee = trainee;
	}

	public Programasteens getProgramateens() {
		return programateens;
	}

	public void setProgramateens(Programasteens programateens) {
		this.programateens = programateens;
	}

	public Highschool getHighschool() {
		return highschool;
	}

	public void setHighschool(Highschool highschool) {
		this.highschool = highschool;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public Demipair getDemipair() {
		return demipair;
	}

	public void setDemipair(Demipair demipair) {
		this.demipair = demipair;
	}

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public He getHe() {
		return he;
	}

	public void setHe(He he) {
		this.he = he;
	}

	public Seguroviagem getSeguroViagem() {
		return seguroViagem;
	}

	public void setSeguroViagem(Seguroviagem seguroViagem) {
		this.seguroViagem = seguroViagem;
	}

	public Vistos getVistos() {
		return vistos;
	}

	public void setVistos(Vistos vistos) {
		this.vistos = vistos;
	}

	public String getVoltarContreVendas() {
		return voltarContreVendas;
	}

	public void setVoltarContreVendas(String voltarContreVendas) {
		this.voltarContreVendas = voltarContreVendas;
	}

	public String getVendaMatriz() {
		return vendaMatriz;
	}

	public void setVendaMatriz(String vendaMatriz) {
		this.vendaMatriz = vendaMatriz;
	}

	public Usuario getConsultor() {
		return consultor;
	}

	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
	}

	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
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

	public Questionariohe getQuestionarioHe() {
		return questionarioHe;
	}

	public void setQuestionarioHe(Questionariohe questionarioHe) {
		this.questionarioHe = questionarioHe;
	}

	public void gerarListaVendas() {
		if (usuarioLogadoMB.getUsuario() != null || usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy-MM-dd");
			String sql = "Select v from Vendas v where (v.produtos.idprodutos<>6 and v.produtos.idprodutos<>7 and v.produtos.idprodutos<> 8"
					+ " and v.produtos.idprodutos<>15 and v.produtos.idprodutos<>17 and v.produtos.idprodutos<>18 and v.produtos.idprodutos<>21 and v.produtos.idprodutos<>22) ";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sql = sql + " and  v.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sql = sql + " and v.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}
			sql = sql + " and v.dataVenda>='" + dataConsulta + "' order by v.dataVenda desc, v.cliente.nome";
			
			listaVendas = vendasDao.lista(sql);
			if (listaVendas == null) {
				listaVendas = new ArrayList<Vendas>();  
			}
			numeroFichas = "" + String.valueOf(listaVendas.size());
		} else {
			if (listaVendas == null) {
				listaVendas = new ArrayList<Vendas>();
			}
			numeroFichas = "" + String.valueOf(listaVendas.size());
		}
		gerarQuantidadesFichas();
	}

	public void gerarQuantidadesFichas() {
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizadas = 0;
		nFichasProcesso = 0;
		for (int i = 0; i < listaVendas.size(); i++) {
			if (listaVendas.get(i).getSituacao().equalsIgnoreCase("FINALIZADA")) {
				nFichasFinalizadas = nFichasFinalizadas + 1;
			} else if (listaVendas.get(i).getSituacao().equalsIgnoreCase("PROCESSO")) {
				nFichasProcesso = nFichasProcesso + 1;
			} else if (listaVendas.get(i).getSituacao().equalsIgnoreCase("ANDAMENTO")) {
				nFichasAndamento = nFichasAndamento + 1;
			} else {
				nFichaCancelada = nFichaCancelada + 1;
			}
		}
	}

	public void gerarListaFornecedor() {
		FornecedorFacade forncedorFacade = new FornecedorFacade();
		listaFornecedor = forncedorFacade
				.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}

	public void retornoDialogoEditar() {
		gerarListaVendas();
	}

	public void pesquisarListaVendas() {
		String sql = null;
		sql = "Select v from Vendas v where v.cliente.nome like '%" + nome + "%' and (v.produtos.idprodutos<>6 and v.produtos.idprodutos<>7 and v.produtos.idprodutos<> 8"
					+ " and v.produtos.idprodutos<>15 and v.produtos.idprodutos<>17 and v.produtos.idprodutos<>18 and v.produtos.idprodutos<>21 and v.produtos.idprodutos<>22) ";
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			if (unidadenegocio != null) {
				sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
			}
		} else {
			sql = sql + " and v.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sql = sql + " and v.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		if (idVenda > 0) {
			sql = sql + " and v.idvendas=" + idVenda;
		}
		if (programas != null) {
			sql = sql + " and v.produtos.idprodutos=" + programas.getIdprodutos();
		}
		if (consultor != null && consultor.getIdusuario() != null) {
			sql = sql + " and v.usuario.idusuario=" + consultor.getIdusuario();
		}
		if ((dataInicio != null) && (dataTermino != null)) {
			sql = sql + " and v.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and v.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		} else {
			if (nome.length() == 0) {
				String dataConsulta = Formatacao.SubtarirDatas(new Date(), 365, "yyyy-MM-dd");
				sql = sql + " and v.dataVenda>='" + dataConsulta + "'";
			}
		}
		if (!situacao.equalsIgnoreCase("TODAS")) {
			sql = sql + " and v.situacao='" + situacao + "'";
		}
		if (fornecedor != null && fornecedor.getIdfornecedor() != null) {
			sql = sql + " and v.fornecedorcidade.fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
		}
		sql = sql + " order by v.dataVenda desc, v.cliente.nome";
		
		listaVendas = vendasDao.lista(sql);
		if (listaVendas == null) {
			listaVendas = new ArrayList<Vendas>();
		}
		numeroFichas = "" + String.valueOf(listaVendas.size());
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	public void limparPesquisa() {
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			habilitarUnidade = false;
			unidadenegocio = null;
			listaVendas = new ArrayList<>();
		} else {
			habilitarUnidade = true;
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			gerarListaVendas();
			gerarListaConsultor();
		}
		dataInicio = null;
		dataTermino = null;
		situacao = "TODAS";
		nome = "";
		idVenda = 0;
		fornecedor = null;
		consultor = null;
		programas = null;
		pesquisar = "Nao";
	}

	public String visualizarContasReceber(Vendas venda) {
		if ((venda.getOrcamento() != null)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 620);
			RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		} else {
			FacesMessage msg = new FacesMessage("Venda não Possui Contas a Receber! ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "";
	}

	public void imprimirFicha(Vendas vendas) {
		this.vendas = vendas;
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
		} else if (vendas.getProdutos().getIdprodutos() == 4) {
			buscarHighSchool();
		} else if (vendas.getProdutos().getIdprodutos() == 5) {
			buscarProgramasTeens();
		} else if (vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
		} else if (vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
		} else if (vendas.getProdutos().getIdprodutos() == 16) {
			buscarVoluntariado();
		} else if (vendas.getProdutos().getIdprodutos() == 20) {
			buscarDemiPair();
		} else if (vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
		}
		try {
			gerarRelatorioFicha();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Date retornarDataInicio(Vendas vendas) {
		this.vendas = vendas;
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
			if (curso == null) {
				listaVendas.remove(vendas);
				return null;
			} else {
				return curso.getDataInicio();
			}
		} else if (vendas.getProdutos().getIdprodutos() == 4) {
			buscarHighSchool();
			return null;
		} else if (vendas.getProdutos().getIdprodutos() == 5) {
			buscarProgramasTeens();
			if (programateens == null) {
				listaVendas.remove(vendas);
				return null;
			} else {
				return programateens.getDataInicioCurso();
			}
		} else if (vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
			if (aupair == null) {
				listaVendas.remove(vendas);
				return null;
			} else {
				return aupair.getDataInicioPretendida01();
			}
		} else if (vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
			return null;
		} else if (vendas.getProdutos().getIdprodutos() == 16) {
			buscarVoluntariado();
			if (voluntariado == null) {
				listaVendas.remove(vendas);
				return null;
			} else {
				return voluntariado.getDataInicio();
			}
		} else if (vendas.getProdutos().getIdprodutos() == 20) {
			buscarDemiPair();
			if (demipair == null) {
				listaVendas.remove(vendas);
				return null;
			} else {
				return demipair.getDatainicio();
			}
		} else if (vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
			if (worktravel == null) {
				listaVendas.remove(vendas);
				return null;
			} else {
				return worktravel.getDataInicioPretendida01();
			}
		} else if (vendas.getProdutos().getIdprodutos() == 22) {
			buscarHe();
			if (he == null) {
				listaVendas.remove(he);
				return null;
			} else {
				return he.getDatainicio();
			}
		} else if (vendas.getProdutos().getIdprodutos() == 2) {
			buscarSeguro();
			if (seguroViagem == null) {
				listaVendas.remove(seguroViagem);
				return null;
			} else {
				return seguroViagem.getDataInicio();
			}
		}else if(vendas.getProdutos().getIdprodutos() == 3){
			buscarVistos();
			if (vistos == null) {
				listaVendas.remove(vendas);
				return null;
			}else{
				return vistos.getDataembarque();
			}
		}
		return null;
	}

	public String gerarRelatorioFicha() throws IOException {
		Map parameters = new HashMap();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		if (vendas.getProdutos().getIdprodutos() == 1) {
			if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
				if (curso.isDadospais()) {
					caminhoRelatorio = "/reports/curso/FichaCursoDadosPaisPagina01.jasper";
				} else {
					caminhoRelatorio = "/reports/curso/FichaCursoPagina01.jasper";
				}
			} else {
				if (curso.isDadospais()) {
					caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
				} else {
					caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
				}
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));
			parameters.put("idvendas", curso.getVendas().getIdvendas());
			parameters.put("sqlpagina2", gerarSqlSeguroViagems());
		} else if (vendas.getProdutos().getIdprodutos() == 4) {
			caminhoRelatorio = "/reports/highSchool/FichaHighSchoolPagina01.jasper";
			parameters.put("idvendas", highschool.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));
		} else if (vendas.getProdutos().getIdprodutos() == 5) {
			caminhoRelatorio = "/reports/cursosTeens/FichaProgramasTeensPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));
			parameters.put("idvendas", programateens.getVendas().getIdvendas());
		} else if (vendas.getProdutos().getIdprodutos() == 9) {
			caminhoRelatorio = "/reports/aupair/FichaAupairPagina01.jasper";
			parameters.put("idvendas", aupair.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));
		} else if (vendas.getProdutos().getIdprodutos() == 13) {
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				caminhoRelatorio = "/reports/trainee/FichaEstagioAustralia01.jasper";
			} else {
				caminhoRelatorio = "/reports/trainee/FichaTraineePagina01.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
			parameters.put("idvendas", trainee.getVendas().getIdvendas());
		} else if (vendas.getProdutos().getIdprodutos() == 16) {
			caminhoRelatorio = "/reports/voluntariado/FichaVoluntariadoPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
			parameters.put("idvendas", voluntariado.getVendas().getIdvendas());
			parameters.put("sqlpagina2", gerarSqlPagina1(voluntariado));
		} else if (vendas.getProdutos().getIdprodutos() == 10) {
			caminhoRelatorio = "/reports/worktravel/FichaWorkPagina01.jasper";
			parameters.put("idvendas", worktravel.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));
		} else if (vendas.getProdutos().getIdprodutos() == 20) {
			caminhoRelatorio = "/reports/demipair/FichaDemipairPagina01.jasper";
			parameters.put("idvendas", demipair.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));
		} else if (vendas.getProdutos().getIdprodutos() == 22) {
			if (he.isFichafinal()) {
				caminhoRelatorio = "/reports/higherEducation/FichaFinalHe1.jasper";
			} else {
				caminhoRelatorio = "/reports/higherEducation/FichaInscricaoHe1.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
			parameters.put("idvendas", he.getVendas().getIdvendas());
		} else if (vendas.getProdutos().getIdprodutos() == 2) {
			if (seguroViagem.getIdvendacurso() > 0) {
				caminhoRelatorio = ("/reports/seguro/FichaSeguroCursoPagina01.jasper");
			} else {
				caminhoRelatorio = ("/reports/seguro/FichaSeguroPagina01.jasper");
			}
			if (seguroViagem.getIdvendacurso() > 0) {
				CursoFacade cursoFacade = new CursoFacade();
				Curso curso = cursoFacade.consultarCursos(seguroViagem.getIdvendacurso());
				if (curso != null) {
					seguroViagem.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
					seguroViagem.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
					seguroViagem.setPaisDestino(curso.getPais());
					SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
					seguroViagem = seguroViagemFacade.salvar(seguroViagem);
				}
			}
			parameters.put("idvendas", seguroViagem.getVendas().getIdvendas());
			if (seguroViagem.isSegurocancelamento()) {
				parameters.put("segurocancelamento", "Sim");
			}else{
				parameters.put("segurocancelamento", "Não");
			}
		}else if(vendas.getProdutos().getIdprodutos() == 3){
			caminhoRelatorio = "/reports/visto/FichaOrcamentoVistoPagina01.jasper";
			parameters.put("idvisto", vistos.getIdvistos());
		}
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"ficha-" + vendas.getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public Curso buscarCurso() {
		CursoFacade cursoFacade = new CursoFacade();
		curso = cursoFacade.consultarCursos(vendas.getIdvendas());
		return curso;
	}

	public Aupair buscarAuPair() {
		AupairFacade aupairFacade = new AupairFacade();
		aupair = aupairFacade.consultar(vendas.getIdvendas());
		return aupair;
	}

	public Highschool buscarHighSchool() {
		HighSchoolFacade hiSchoolFacade = new HighSchoolFacade();
		highschool = hiSchoolFacade.consultarHighschool(vendas.getIdvendas());
		return highschool;
	}

	public Programasteens buscarProgramasTeens() {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		programateens = programasTeensFacede.find(vendas.getIdvendas());
		return programateens;
	}

	public Trainee buscarTrainee() {
		TraineeFacade traineeFacade = new TraineeFacade();
		trainee = traineeFacade.consultar(vendas.getIdvendas());
		return trainee;
	}

	public Voluntariado buscarVoluntariado() {
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		voluntariado = voluntariadoFacade.consultar(vendas.getIdvendas());
		return voluntariado;
	}

	public Demipair buscarDemiPair() {
		DemipairFacade demipairFacade = new DemipairFacade();
		demipair = demipairFacade.consultar(vendas.getIdvendas());
		return demipair;
	}

	public Worktravel buscarWorkTravel() {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		worktravel = workTravelFacade.consultarWork(vendas.getIdvendas());
		return worktravel;
	}

	public He buscarHe() {
		HeFacade heFacade = new HeFacade();
			he = heFacade.consultarVenda(vendas.getIdvendas());
		return he;   
	}    
	
	public Questionariohe buscarQuestionarioHe() {
		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		questionarioHe = questionarioHeFacade.consultarVenda(vendas.getIdvendas());
		return questionarioHe;
	}

	public Seguroviagem buscarSeguro() {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		seguroViagem = seguroViagemFacade.consultar(vendas.getIdvendas());
		return seguroViagem;
	}
	
	public Vistos buscarVistos(){
		VistosFacade vistosFacade = new VistosFacade();
		vistos = vistosFacade.consultarVistos(vendas.getIdvendas());
		return vistos;
	}

	public String gerarSqlSeguroViagems() {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(curso.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(curso.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct vendas.dataVenda, vendas.valor as valorVenda, cursos.nomeCurso, cursos.escola,"
				+ "cursos.cidade, cursos.pais, cursos.aulassemana, cursos.numerosenamas, cursos.dataInicio, "
				+ "cursos.dataTermino, cursos.tipoAcomodacao, cursos.numeroSemanasAcamodacao, cursos.tipoquarto,"
				+ "cursos.refeicoes, cursos.adicionais, cursos.datachegada, cursos.dataSaida, cursos.familiacomcrianca,"
				+ "cursos.familiacomanimais, cursos.fumante, cursos.vegetariano, cursos.hobbies, cursos.possuiAlergia,"
				+ "cursos.quaisalergias, cursos.solicitacoesespeciais, cursos.caratovtm, cursos.numerocartaovtm,"
				+ "cursos.moedacartaovtm, cursos.transferin, cursos.transferouto, cursos.passagemaerea, cursos.observacaopassagemaerea,"
				+ "cursos.vistoconsular, cursos.dataEntregadocumentosvistos, cursos.observacaovisto, cursos.nomecontatoemergencia,"
				+ "cursos.fonecontatoemergencia, cursos.emalcontatoemergencia, cursos.relacaocontatoemergencia, cursos.datainscricao as dataInscricaCurso, cursos.idioma, cursos.nivelIdioma, cursos.possuiSeguroGovernamental, cursos.numeroMeses as numeromesesgovernamental, cursos.seguradoraGovernamental,"
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "usuario.nome as nomeUsuario,unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento,seguroviagem.idseguroViagem,seguroviagem.seguradora,seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor,orcamentoprodutosorcamento.idorcamentoprodutosorcamento"
				+ " from " + "vendas join cursos on vendas.idvendas = cursos.vendas_idvendas "
				+ "join usuario on vendas.usuario_idusuario = usuario.idusuario "
				+ "join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio "
				+ "join orcamento on vendas.idvendas = orcamento.vendas_idvendas "
				+ "join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento "
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento ";
		sql = sql + sqlseguro;
		sql = sql + " where " + " vendas.idvendas = " + curso.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento ";
		return sql;

	}

	public String gerarSqlPagina1(Voluntariado voluntariado) {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(voluntariado.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(voluntariado.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct " + "vendas.dataVenda, vendas.valor as valorVenda,vendas.idvendas,"
				+ "voluntariado.idvoluntariado,voluntariado.idiomaEstudar, voluntariado.nivelIdiomaEstudar,"
				+ "voluntariado.nomeContatoEmergencia,voluntariado.foneContatoEmergencia, voluntariado.emailContatoEmergencia,"
				+ "voluntariado.relacaoContatoEmergencia,voluntariado.instituicaoEstuda,voluntariado.cursoEstuda,"
				+ "voluntariado.duracaoCursoEstuda,voluntariado.periodoCursoEstuda,voluntariado.dataMatriculaCursoEstuda,voluntariado.dataEstimadaTerminoCursoEstuda,"
				+ "voluntariado.profissao,voluntariado.cargo,voluntariado.descricaoCargo,voluntariado.outrasHabilidade,"
				+ "voluntariado.curso,voluntariado.aulasporSemana,voluntariado.numeroSemanas,voluntariado.dataInicio,"
				+ "voluntariado.dataTermino,voluntariado.tipoAcomodacao,voluntariado.numeroSemanasAcomodacao,voluntariado.tipoQuarto,"
				+ "voluntariado.refeicoes,voluntariado.adicionais,voluntariado.dataChegadaAcomodacao,voluntariado.dataPartidaAcomodacao,"
				+ "voluntariado.familiaCrianca,voluntariado.familiaAnimais,voluntariado.fumante,voluntariado.vegetariano,"
				+ "voluntariado.hobbies,voluntariado.possuiAlergia,voluntariado.quaisAlergias,voluntariado.solicitacoesEspeciais,voluntariado.transferin,"
				+ "voluntariado.transferout,voluntariado.dataChegadaTransfer,"
				+ "voluntariado.voo,voluntariado.ciaerea,voluntariado.horario,voluntariado.projetoVoluntariado,"
				+ "voluntariado.dataInicioVoluntariado,voluntariado.dataTerminoVoluntariado,voluntariado.experienciaVoluntariado,voluntariado.motivoVoluntariado,"
				+ "voluntariado.deficienciaFisica,voluntariado.possuiProblemaSaude,voluntariado.descricaoProblemaSaude,voluntariado.tratamentoMedico,"
				+ "voluntariado.descricaoMedico,voluntariado.tratamentoDrogas,voluntariado.descricaoDrogas,"
				+ "voluntariado.fezCirurgia,voluntariado.descricaoCirurgia,voluntariado.dietaEspecifica,voluntariado.cartaoVTM,"
				+ "voluntariado.numerocartaoVTM,voluntariado.meodaCartaoVTM,voluntariado.seguroViagem,voluntariado.seguradora,voluntariado.planoSeguro,"
				+ "voluntariado.dataInicioSeguro,voluntariado.dataTerminoSeguro,voluntariado.numeroSemanasSeguro,voluntariado.passagemAerea,"
				+ "voluntariado.observacaoPassagem,voluntariado.vistoConsular,voluntariado.observacaoVistoConsultar,voluntariado.vendas_idvendas,"
				+ "voluntariado.dataEntregaDocumentoVisto,voluntariado.nivelFitness,voluntariado.controle, "
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "cliente.nome, cliente.dataNascimento, cliente.paisnascimento, cliente.cidadenascimento, cliente.rg,"
				+ "cliente.sexo, cliente.numeropassaporte, cliente.dataExpedicaoPassaporte,"
				+ "cliente.validadePassaporte, cliente.tipologradouro as clientetipologradouro, cliente.logradouro as clientelogradouro, cliente.numero as clientenumero,"
				+ "cliente.bairro as clientebairro, cliente.cidade as clientecidade, cliente.estado as clienteestado, cliente.cep as clientecep, cliente.cpf as clientecpf, cliente.pais as clientepais, cliente.foneresidencial,"
				+ "cliente.fonecelular, cliente.fonecomercial, cliente.profissao,"
				+ "cliente.email as emailcliente,cliente.escolaridade, cliente.nomePai, cliente.profissaopai, cliente.fonepai, cliente.nomemae,"
				+ "cliente.profissaomae, cliente.fonemae,"
				+ "usuario.nome as nomeUsuario, usuario.email as usuarioemail,"
				+ "unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, formapagamento.condicao, formapagamento.valortotal,"
				+ "formapagamento.valorjuros, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento, moedas.descricao as descricaoMoedas, cambio.idcambio, moedas.sigla,parcelamentopagamento.idparcemlamentoPagamento,"
				+ "parcelamentopagamento.formaPagamento," + "parcelamentopagamento.tipoParcelmaneto,"
				+ "parcelamentopagamento.valorParcelamento," + "parcelamentopagamento.diaVencimento,"
				+ "parcelamentopagamento.numeroParcelas," + "parcelamentopagamento.valorParcela,"
				+ "parcelamentopagamento.formaPagamento_idformaPagamento,orcamentoprodutosorcamento.idorcamentoprodutosorcamento,"
				+ "fornecedor.idfornecedor,seguroviagem.idseguroViagem,seguroviagem.seguradora,"
				+ "seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,"
				+ "seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,"
				+ "seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor, "
				+ "fornecedor.nome as nomeFornecedor, cidade.nome as nomeCidade, pais.nome nomePais" + " from "
				+ "  vendas join voluntariado on vendas.idvendas = voluntariado.vendas_idvendas"
				+ "  join usuario on vendas.usuario_idusuario = usuario.idusuario"
				+ "  join cliente on vendas.cliente_idcliente = cliente.idcliente"
				+ "  join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio"
				+ " join orcamento on vendas.idvendas = orcamento.vendas_idvendas"
				+ "  join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento"
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento"
				+ " join formaPagamento on vendas.idvendas = formapagamento.vendas_idvendas"
				+ " join cambio on orcamento.cambio_idcambio = cambio.idcambio"
				+ "  join moedas on cambio.moedas_idmoedas = moedas.idmoedas"
				+ " join parcelamentopagamento on formapagamento.idformapagamento = parcelamentopagamento.formapagamento_idformaPagamento"
				+ " join fornecedorCidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
				+ "   join fornecedor on fornecedorcidade.fornecedor_idfornecedor = fornecedor.idfornecedor"
				+ "  join cidade on fornecedorcidade.cidade_idcidade = cidade.idcidade"
				+ "  join pais on cidade.pais_idpais = pais.idpais";
		sql = sql + sqlseguro;
		sql = sql + " where " + "vendas.idvendas =" + voluntariado.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento";
		return sql;
	}

	public String gerarRelatorioTermoVisto(Vendas vendas) throws SQLException, IOException {
		this.vendas = vendas;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", vendas.getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioRecibo(Vendas vendas) throws SQLException, IOException {
		this.vendas = vendas;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(vendas.getIdvendas());
		ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
		List<Parcelamentopagamento> listaParcelamento = parcelamentoPagamentoFacade.listar(forma.getIdformaPagamento());
		if (listaParcelamento != null) {
			for (int i = 0; i < listaParcelamento.size(); i++) {
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Dinheiro")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("cheque")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("depósito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
			}
		}
		if (valorRecibo > 0.0f) {
			Map parameters = new HashMap();
			parameters.put("idvendas", vendas.getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "reciboPagamento.pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}
	
	public String documentacao(Vendas vendas) {
		this.vendas = vendas;
		boolean validar = true;
		if (vendas.getSituacao().equalsIgnoreCase("PROCESSO") && usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = vendas.getDatavalidade();
			if (dataValidade != null) {
				if (!dataAtual.after(dataValidade)) {
					validar = true;
				} else {
					validar = false;
				}
			}
		}
		if (!validar) {
			Mensagem.lancarMensagemInfo("Favor atualizar o câmbio desta ficha",
					"está ficha ultrapassou os 3 dias de validade");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vendas);
			voltar = "consControleVendas";
			session.setAttribute("voltar", voltar);
			return "consArquivos";
		}
	}

	

	public String informacoes(Vendas vendas) {
		this.vendas = vendas;
		if (vendas.getSituacao().equalsIgnoreCase("Processo")) {
			Mensagem.lancarMensagemInfo("Atenção", "Ficha ainda não enviada para gerência");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", vendas);
			voltar = "consControleVendas";
			session.setAttribute("voltar", voltar);
			return "consLogVenda";
		}
	}

	public String boletos(Vendas vendas) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(vendas.getCliente());
		if (validarCliente.getMsg().length() < 5) {
			this.vendas = vendas;
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + vendas.getIdvendas()
					+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(vendas.getIdvendas()));
				} else {
					FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
					FacesContext.getCurrentInstance().addMessage(null, msg);
					RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
					relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
				}
			} else {
				FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
				relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
			}
		} else {
			FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Dados do cliente não converefe " + validarCliente.getMsg());
		}

		return "";
	}

	public String obsTM(Vendas vendas) {
		this.vendas = vendas;
		obsTM = vendas.getObstm();
		return obsTM;
	}

	public String gerarRelatorioContrato(Vendas vendas) throws SQLException, IOException {
		this.vendas = vendas;
		Map parameters = new HashMap();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
			if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
				caminhoRelatorio = ("/reports/curso/contratoCursoPagina01.jasper");
			} else
				caminhoRelatorio = ("/reports/curso/contratoCurso2Pagina01.jasper");
			parameters.put("idvendas", curso.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));

		} else if (vendas.getProdutos().getIdprodutos() == 4) {
			caminhoRelatorio = "/reports/highSchool/contratoHighSchoolPagina01.jasper";
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));

		} else if (vendas.getProdutos().getIdprodutos() == 5) {
			caminhoRelatorio = ("/reports/cursosTeens/contratoTeensPagina01.jasper");
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));

		} else if (vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
			if (!aupair.getPaisinteresse().equalsIgnoreCase("Caregiver")) {
				caminhoRelatorio = "/reports/aupair/contratoAupairPagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/aupair/contratoCaregiverPagina01.jasper";
			}
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));

		} else if (vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				caminhoRelatorio = "/reports/trainee/contratoEstagioAustralia01.jasper";
			} else {
				caminhoRelatorio = "/reports/trainee/contratoTraineePagina01.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
			parameters.put("idvendas", vendas.getIdvendas());

		} else if (vendas.getProdutos().getIdprodutos() == 16) {
			caminhoRelatorio = "/reports/voluntariado/contratoVoluntariadoPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
			parameters.put("idvendas", vendas.getIdvendas());

		} else if (vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
			if (worktravel.getTipo().equalsIgnoreCase("Premium")) {
				caminhoRelatorio = "/reports/worktravel/contratoWorkPremiumPagina01.jasper";
			} else if (worktravel.getTipo().equalsIgnoreCase("France")) {
				caminhoRelatorio = "/reports/worktravel/contratoWorkFrancePagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/worktravel/contratoWorkIndependentPagina01.jasper";
			}
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));

		} else if (vendas.getProdutos().getIdprodutos() == 20) {
			caminhoRelatorio = "/reports/demipair/contratoDemipairPagina01.jasper";
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));

		} else if (vendas.getProdutos().getIdprodutos() == 22) {
			caminhoRelatorio = "/reports/higherEducation/contratoHePagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
			parameters.put("idvendas", he.getVendas().getIdvendas());
		}else if(vendas.getProdutos().getIdprodutos() == 3){
			caminhoRelatorio = ("/reports/visto/contratovisto.jasper");
			parameters.put("idvendas", vistos.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//visto//"));
		}
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		try {
			gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"contratoCurso-" + vendas.getIdvendas() + ".pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String imagemSituacaoUsuario(Vendas vendas) {
		if (vendas.getSituacao().equals("FINALIZADA")) {
			return "../../resources/img/finalizadoFicha.png";
		} else if (vendas.getSituacao().equals("ANDAMENTO")) {
			return "../../resources/img/amarelaFicha.png";
		} else if (vendas.getSituacao().equals("CANCELADA")) {
			return "../../resources/img/fichaCancelada.png";
		} else if ((vendas.getSituacao().equalsIgnoreCase("PROCESSO")) && (vendas.isRestricaoparcelamento())) {
			return "../../resources/img/ficharestricao.png";
		} else {
			return "../../resources/img/processoFicha.png";
		}
	}

	public boolean validarTermoVisto(Vendas vendas) {
		if (vendas.getProdutos().getIdprodutos() == 22 || vendas.getProdutos().getIdprodutos() == 3) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validarRecibo(Vendas vendas) {
		if (vendas.getProdutos().getIdprodutos() == 22 || vendas.getProdutos().getIdprodutos() == 3) {
			return false;
		} else {
			return true;
		}   
	}

	public boolean validarContrato(Vendas vendas) {
		if (vendas.getProdutos().getIdprodutos() == 2) {
			return false;
		} else {
			return true;
		}
	}
	
	  
	public String editar(Vendas vendas){
		this.vendas = vendas;
		voltarContreVendas = "consControleVendas";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
			session.setAttribute("curso", curso);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadFichaCurso";
		}else if (vendas.getProdutos().getIdprodutos() == 2) {
			buscarSeguro();
			session.setAttribute("seguro", seguroViagem);
			session.setAttribute("idlead", vendas.getIdlead());
			return "fichaSeguroViagem";
		}else if (vendas.getProdutos().getIdprodutos() == 3) {
			buscarVistos();
			session.setAttribute("vistos", vistos);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadVistos";
		}else if (vendas.getProdutos().getIdprodutos() == 4) {
			buscarHighSchool();
			session.setAttribute("highschool", highschool);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadHighSchool";
		}else if (vendas.getProdutos().getIdprodutos() == 5) {
			buscarProgramasTeens();
			session.setAttribute("programasTeens", programateens);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadCursosTeens";
		}else if (vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
			session.setAttribute("aupair", aupair);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadAuPair";
		}else if (vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
			session.setAttribute("work", worktravel);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadWorkandTravel";
		}else if (vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
			session.setAttribute("trainee", trainee);
			session.setAttribute("idlead", vendas.getIdlead());
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				return "cadEstagioAustralia";
			} else{
				return "cadTrainee";
			}
		}else if (vendas.getProdutos().getIdprodutos() == 16) {
			buscarVoluntariado();
			session.setAttribute("voluntariado", voluntariado);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadVoluntariado";
		}else if (vendas.getProdutos().getIdprodutos() == 20) {
			buscarDemiPair();
			session.setAttribute("demipair", demipair);
			session.setAttribute("idlead", vendas.getIdlead());
			return "cadDemiPair";
		}else if (vendas.getProdutos().getIdprodutos() == 22) {
			session.setAttribute("idlead", vendas.getIdlead());
			buscarHe();
			if (he == null) {
				buscarQuestionarioHe();
				if (questionarioHe != null) {
					session.setAttribute("questionariohe", questionarioHe);
					return "questionarioHE";
				}
			}else{
				if (he != null) {
					session.setAttribute("he", he);
					if (he.isAprovado()) {
						return "cadFichaHe2";
					} else {
						session.setAttribute("cliente", he.getVendas().getCliente());
						return "cadFichaHe1";
					}
				} 
			}
		}
		return "";
	}
	
	
	
	public String cadastrarFicha() {
		voltarContreVendas = "consControleVendas";
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("voltarControleVendas", voltarContreVendas);
			session.setAttribute("idlead", idlead);
			return "cadFichaCurso";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}
	
	
	public String vendaNaoMatriz() {
		voltarContreVendas = "consControleVendas";
		String vendaMatriz = "N";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "fichaSeguroViagem";
	}

	public String vendaMatriz() {
		voltarContreVendas = "consControleVendas";
		String vendaMatriz = "S";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "fichaSeguroViagem";
	}
	
	
	public String cadastrarFichaHighSchool() {
		voltarContreVendas = "consControleVendas";
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("voltarControleVendas", voltarContreVendas);
			session.setAttribute("idlead", idlead);
			return "cadHighSchool";
		} else {
			Mensagem.lancarMensagemWarn("Cambio do dia ainda não liberado", "");
		}
		return "";
	}
	
	
	public String vendaNaoMatrizVisto() throws SQLException {
		voltarContreVendas = "consControleVendas";
		vendaMatriz = "N";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0;
		session.setAttribute("idlead", idlead);
		return "cadVistos";
	}
	
	public String vendaMoemaVisto() throws SQLException {
		voltarContreVendas = "consControleVendas";
		vendaMatriz = "M";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0;
		session.setAttribute("idlead", idlead);
		return "cadVistos";
	}

	public String vendaMatrizVisto() throws SQLException {
		voltarContreVendas = "consControleVendas";
		vendaMatriz = "S";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("vendaMatriz", vendaMatriz);
		int idlead=0;
		session.setAttribute("idlead", idlead);
		return "cadVistos";
	}
	
	
	public String cadastrarFichaTeens() {
		voltarContreVendas = "consControleVendas";
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("voltarControleVendas", voltarContreVendas);
			session.setAttribute("idlead", idlead);
			return "cadCursosTeens";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}
	
	
	public String cadastrarFichaAuPair() {
		voltarContreVendas = "consControleVendas";
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead = 0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("voltarControleVendas", voltarContreVendas);
			session.setAttribute("idlead", idlead);
			return "cadAuPair";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}
	
	
	public String cadastrarFichaWork() {
		voltarContreVendas = "consControleVendas";
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("voltarControleVendas", voltarContreVendas);
			session.setAttribute("idlead", idlead);
			return "cadWorkandTravel";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}
	
	
	public String cadAustralia() throws SQLException {
		voltarContreVendas = "consControleVendas";
		String tipo = "Australia";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("tipo", tipo);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "cadEstagioAustralia";
	}

	public String cadUsa() throws SQLException {
		voltarContreVendas = "consControleVendas";
		String tipo = "EUA";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("tipo", tipo);
		int idlead=0; 
		session.setAttribute("idlead", idlead);
		return "cadTrainee";
	}
	
	
	public String novo() {
		voltarContreVendas = "consControleVendas";
		int idlead = 0;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		session.setAttribute("idlead", idlead);
		return "cadVoluntariado";
	}
	
	
	public String cadastrarFichaDemiPair() {
		voltarContreVendas = "consControleVendas";
		if (aplicacaoMB.getDatacambio() != null) {
			int idlead=0;
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("voltarControleVendas", voltarContreVendas);
			session.setAttribute("idlead", idlead);
			return "cadDemiPair";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Cambio do dia ainda não liberado", ""));
		}
		return "";
	}
	
	
	public String novoQuestionario() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		voltarContreVendas = "consControleVendas";
		session.setAttribute("voltarControleVendas", voltarContreVendas);
		return "questionarioHe";
	}
	
	
	public void gerarListaConsultor() {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		listaConsultor = usuarioFacade
				.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		if (listaConsultor == null) {
			listaConsultor = new ArrayList<Usuario>();
		}
	} 
	
	
	public boolean verificarSituacaoVenda(Vendas vendas){
		if (vendas.getSituacao().equalsIgnoreCase("CANCELADA")) {
			return true;
		}
		return false;
	}
	
	
	public String ficha(Vendas vendas){
		this.vendas = vendas;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaVendas", listaVendas);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "ControleVendas");
		session.setAttribute("chamadaTela", "ControleVendas");
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
			session.setAttribute("curso", curso);
			return "fichaCurso";
		}else if(vendas.getProdutos().getIdprodutos() == 2) {
			buscarSeguro();
			session.setAttribute("seguroviagem", seguroViagem);
			return "fichasSeguroViagem";
		}else if(vendas.getProdutos().getIdprodutos() == 3) {
			buscarVistos();
			session.setAttribute("vistos", vistos);
			return "fichasVistos";
		}else if(vendas.getProdutos().getIdprodutos() == 4) {
			buscarHighSchool();
			session.setAttribute("highschool", highschool);
			return "fichaHighSchool";
		}else if(vendas.getProdutos().getIdprodutos() == 5) {
			buscarProgramasTeens();
			session.setAttribute("programateens", programateens);
			return "fichaCursosTeens";
		}else if(vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
			session.setAttribute("aupair", aupair);
			return "fichaAuPair";
		}else if(vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
			session.setAttribute("worktravel", worktravel);
			return "fichaWorkTravel";
		}else if(vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
			session.setAttribute("trainee", trainee);
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				return "fichaTraineeAus";
			}else {
				return "fichaTraineeEUA";
			}
		}else if(vendas.getProdutos().getIdprodutos() == 16) {
			buscarVoluntariado();
			session.setAttribute("voluntariado", voluntariado);
			return "fichaVoluntariado";
		}else if(vendas.getProdutos().getIdprodutos() == 20) {
			buscarDemiPair();
			session.setAttribute("demipair", demipair);
			return "fichasDemiPair";
		}else if(vendas.getProdutos().getIdprodutos() == 22) {
			buscarHe();
			session.setAttribute("he", he);
			return "fichaHE";
		}
		return "";
	}
	
	
	public String contrato(Vendas vendas){
		this.vendas = vendas;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaVendas", listaVendas);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "ControleVendas");
		session.setAttribute("chamadaTela", "ControleVendas");
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
			session.setAttribute("curso", curso);
			return "contratoCurso";
		}else if(vendas.getProdutos().getIdprodutos() == 3) {
			buscarVistos();
			session.setAttribute("vistos", vistos);
			return "contratoVisto";
		}else if(vendas.getProdutos().getIdprodutos() == 4) {
			buscarHighSchool();
			session.setAttribute("highschool", highschool);
			return "contratoHighSchool";
		}else if(vendas.getProdutos().getIdprodutos() == 5) {
			buscarProgramasTeens();
			session.setAttribute("programateens", programateens);
			return "contratoTeens";
		}else if(vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
			session.setAttribute("aupair", aupair);
			return "contratoAuPair";
		}else if(vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
			session.setAttribute("worktravel", worktravel);
			return "contratoWorkTravelPremium";
		}else if(vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
			session.setAttribute("trainee", trainee);
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				return "contratoTrainee";
			}else {
				return "contratoTrainee";
			}
		}else if(vendas.getProdutos().getIdprodutos() == 16) {
			buscarVoluntariado();
			session.setAttribute("voluntariado", voluntariado);
			return "contratoVoluntariado";
		}else if(vendas.getProdutos().getIdprodutos() == 20) {
			buscarDemiPair();
			session.setAttribute("demipair", demipair);
			return "contratoDemiPair";
		}else if(vendas.getProdutos().getIdprodutos() == 22) {
			buscarHe();
			session.setAttribute("he", he);
			return "contratoHE";
		}
		return "";
	}
	  

}
