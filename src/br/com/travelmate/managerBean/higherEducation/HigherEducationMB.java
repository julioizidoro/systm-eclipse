package br.com.travelmate.managerBean.higherEducation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import br.com.travelmate.bean.ListaHeBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.QuestionarioHeFacade;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cliente.ValidarClienteBean;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.He; 
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class HigherEducationMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB; 
	private List<ListaHeBean> listaHe;
	private int idvenda;
	private String nomeCliente = "";
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private boolean habilitarUnidade = true;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;
	private List<ListaHeBean> listaProcesso;
	private List<ListaHeBean> listaFinanceiro;
	private List<ListaHeBean> listaAndamento;
	private List<ListaHeBean> listaFinalizar;
	private List<ListaHeBean> listaCancelada;
	private Integer nFichasFinalizada;
	private Integer nFichasProcesso;
	private Integer nFichasAndamento;
	private Integer nFichaCancelada;
	private Integer nFichaFinanceiro;
	private String pesquisar = "Nao";
	private String nomePrograma;
	private String chamadaTela = "";

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = (String) session.getAttribute("pesquisar");
		listaProcesso = (List<ListaHeBean>) session.getAttribute("listaProcesso");
		listaFinanceiro = (List<ListaHeBean>) session.getAttribute("listaFinanceiro");
		listaAndamento = (List<ListaHeBean>) session.getAttribute("listaAndamento");
		listaFinalizar = (List<ListaHeBean>) session.getAttribute("listaFinalizar");
		listaCancelada = (List<ListaHeBean>) session.getAttribute("listaCancelada");
		nomePrograma = (String) session.getAttribute("nomePrograma");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		listaHe = (List<ListaHeBean>) session.getAttribute("listaHe");
		session.removeAttribute("listaAndamento");
		session.removeAttribute("listaFinalizar");
		session.removeAttribute("listaProcesso");
		session.removeAttribute("listaFinanceiro");
		session.removeAttribute("listaCancelada");
		session.removeAttribute("pesquisar");
		session.removeAttribute("nomePrograma");
		session.removeAttribute("chamadaTela");
		session.removeAttribute("listaHe");
		if (pesquisar != null && pesquisar.equalsIgnoreCase("Sim")) {
			if (nomePrograma != null && nomePrograma.equalsIgnoreCase("He")) {
				pesquisar = "Sim";
			}else {
				pesquisar = "Não";
			}
		}
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			listaUnidade = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			if ((pesquisar == null || pesquisar.equalsIgnoreCase("Nao")) || (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu"))) {
				gerarListaHe();
			}else {
				gerarQuantidadesFichas();
			}
		}
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<ListaHeBean> getListaProcesso() {
		return listaProcesso;
	}

	public void setListaProcesso(List<ListaHeBean> listaProcesso) {
		this.listaProcesso = listaProcesso;
	}

	public List<ListaHeBean> getListaFinanceiro() {
		return listaFinanceiro;
	}

	public void setListaFinanceiro(List<ListaHeBean> listaFinanceiro) {
		this.listaFinanceiro = listaFinanceiro;
	}

	public List<ListaHeBean> getListaAndamento() {
		return listaAndamento;
	}

	public void setListaAndamento(List<ListaHeBean> listaAndamento) {
		this.listaAndamento = listaAndamento;
	}

	public List<ListaHeBean> getListaFinalizar() {
		return listaFinalizar;
	}

	public void setListaFinalizar(List<ListaHeBean> listaFinalizar) {
		this.listaFinalizar = listaFinalizar;
	}

	public List<ListaHeBean> getListaCancelada() {
		return listaCancelada;
	}

	public void setListaCancelada(List<ListaHeBean> listaCancelada) {
		this.listaCancelada = listaCancelada;
	}

	public Integer getnFichasFinalizada() {
		return nFichasFinalizada;
	}

	public void setnFichasFinalizada(Integer nFichasFinalizada) {
		this.nFichasFinalizada = nFichasFinalizada;
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

	public Integer getnFichaFinanceiro() {
		return nFichaFinanceiro;
	}

	public void setnFichaFinanceiro(Integer nFichaFinanceiro) {
		this.nFichaFinanceiro = nFichaFinanceiro;
	}

	public List<ListaHeBean> getListaHe() {
		return listaHe;
	}

	public void setListaHe(List<ListaHeBean> listaHe) {
		this.listaHe = listaHe;
	}

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
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

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public boolean isExpandirOpcoes() {
		return expandirOpcoes;
	}

	public void setExpandirOpcoes(boolean expandirOpcoes) {
		this.expandirOpcoes = expandirOpcoes;
	}

	public boolean isEsconderFicha() {
		return esconderFicha;
	}

	public void setEsconderFicha(boolean esconderFicha) {
		this.esconderFicha = esconderFicha;
	}

	public String novoQuestionario() {
		return "questionarioHe";
	}

	public String editar(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("he", listaHeBean.getHe());
			if (listaHeBean.getHe().isAprovado()) {
				return "cadFichaHe2";
			} else {
				session.setAttribute("questionariohe", listaHeBean.getHe().getVendas().getCliente());
				return "cadFichaHe1";
			}
		} else if (listaHeBean.getQuestionariohe() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("questionariohe", listaHeBean.getQuestionariohe());
		} else
			return "";
		return "";
	}

	public void gerarListaHe() {
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()); 
		Calendar c = new GregorianCalendar(ano, mes, 1); 
		Date data = c.getTime();
		String dataConsulta = Formatacao.ConvercaoDataSql(data);
		// questionario
		List<Questionariohe> listaQuestionario = null;
		if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAprovarquestionariohe()) {
		    String sqlQuestionario = "Select q From Questionariohe q where q.dataenvio>='" + dataConsulta + "'"
			 		+ " and q.cliente.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sqlQuestionario = sqlQuestionario + " and q.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			} 
			sqlQuestionario = sqlQuestionario + " order by q.dataenvio desc";
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaQuestionario = questionarioHeFacade.listar(sqlQuestionario);
		}  

		// ficha inscricao
		String sqlFicha1 = "Select h From He h where h.vendas.dataVenda>='" + dataConsulta + "' and h.fichafinal=FALSE";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sqlFicha1 = sqlFicha1 + " and h.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sqlFicha1 = sqlFicha1 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		} else {
			sqlFicha1 = sqlFicha1 + " and h.aprovado=true";
		}
		sqlFicha1 = sqlFicha1 + " order by h.vendas.dataVenda desc";
		HeFacade heFacade = new HeFacade();
		List<He> listaficha1 = heFacade.listar(sqlFicha1);

		// ficha inscricao
		String sqlFicha2 = "Select h From He h where h.vendas.dataVenda>='" + dataConsulta + "' and h.fichafinal=TRUE";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
			if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
				if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
					sqlFicha2 = sqlFicha2 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sqlFicha2 = sqlFicha2 + " order by h.vendas.dataVenda desc";
		List<He> listaficha2 = heFacade.listar(sqlFicha2);
		listaHe = new ArrayList<ListaHeBean>();
		ListaHeBean heBean; 
		if (listaficha1 != null && listaficha1.size() > 0) {
			for (int i = 0; i < listaficha1.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha1.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha1.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha1.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha1.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha1.get(i));
				if (listaficha1.get(i).isAprovado()) {
					heBean.setSituacao("Ficha de Inscrição Aprovada");
				} else
					heBean.setSituacao("Ficha de Inscrição");
				if(listaficha1.get(i).isDesistencia()) {
					heBean.setStatus("CANCELADA"); 
				}else {
					heBean.setStatus(listaficha1.get(i).getVendas().getSituacao());
				} 
				heBean.setStatus(listaficha1.get(i).getVendas().getSituacao());
				heBean.setData(listaficha1.get(i).getVendas().getDataVenda());
				heBean.setIdVenda(listaficha1.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha1.get(i).isAprovado());
				heBean.setDesistencia(listaficha1.get(i).isDesistencia());
				listaHe.add(heBean);
			}
		}
		if (listaficha2 != null && listaficha2.size() > 0) {
			for (int i = 0; i < listaficha2.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha2.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha2.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha2.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha2.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha2.get(i));
				heBean.setSituacao("Ficha Final");  
				heBean.setStatus(listaficha2.get(i).getVendas().getSituacao());
				heBean.setData(listaficha2.get(i).getVendas().getDataVenda());
				heBean.setIdVenda(listaficha2.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha2.get(i).isAprovado());
				heBean.setDesistencia(listaficha2.get(i).isDesistencia());
				listaHe.add(heBean); 
			}
		}
		if (listaQuestionario != null && listaQuestionario.size() > 0) {
			for (int i = 0; i < listaQuestionario.size(); i++) {
				boolean ok=true;
				if (listaficha2 != null && listaficha2.size() > 0) {
					for (int j = 0; j < listaficha2.size(); j++) {
					//	if(listaficha2.get(j).getVendas().getIdvendas()!=listaQuestionario.get(i).getVendas().getIdvendas()){
					//		ok=false;
				//		}
					}
				}
				if (listaficha1 != null && listaficha1.size() > 0) {
					for (int j = 0; j < listaficha1.size(); j++) {
					//	if(listaficha1.get(j).getVendas().getIdvendas()==listaQuestionario.get(i).getVendas().getIdvendas()){
					//		ok=false;
					//	}
					}  
				}
				if(ok){
					heBean = new ListaHeBean();
					heBean.setConsultor(listaQuestionario.get(i).getUsuario().getNome());
					heBean.setFornecedor("");
					heBean.setUnidade(listaQuestionario.get(i).getUsuario().getUnidadenegocio().getNomerelatorio());
					heBean.setNomecliente(listaQuestionario.get(i).getCliente().getNome());
					heBean.setQuestionariohe(listaQuestionario.get(i));
					heBean.setStatus(listaQuestionario.get(i).getSituacao());
					heBean.setData(listaQuestionario.get(i).getDataenvio());
				//	heBean.setIdVenda(listaQuestionario.get(i).getVendas().getIdvendas());
					listaHe.add(heBean);
				}
			}
		}
		gerarQuantidadesFichas();
	}

	public String corNome(ListaHeBean hebean) {
		if (hebean.getHe() != null) {
			if (hebean.getStatus().equals("CANCELADA")) {
				return "color:#808080;text-decoration: line-through;";
			}
		}else if (hebean.getStatus().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(ListaHeBean hebean) {
		if (hebean.getHe() != null) {
			if (hebean.getStatus().equals("CANCELADA")) {
				return "color:#808080;";
			}
		}else if (hebean.getStatus().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public boolean habilitarBtnEditar(ListaHeBean hebean) {
		if (hebean.getHe() != null) {
			if (!hebean.getStatus().equals("CANCELADA")) {
				return false;
			}
		}else if (hebean.getStatus().equals("CANCELADA")) {
			return false;
		}
		return true;
	}

	public void limpar() {
		dataInicio = null;
		dataTermino = null;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			unidadenegocio = null;
		} else {
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		}
		nomeCliente = "";
		idvenda = 0;
		situacao = "sn";
		pesquisar = "Nao";
		gerarListaHe();
	}

	public void pesquisar() {
		// questionario
		List<Questionariohe> listaQuestionario = null;
		if (situacao.equalsIgnoreCase("sn") || situacao.equalsIgnoreCase("Questionario")
				|| situacao.equalsIgnoreCase("Questionario Autorizado")) {
			String sqlQuestionario = "Select q From Questionariohe q where q.cliente.nome like '" + nomeCliente + "%'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sqlQuestionario = sqlQuestionario + " and q.cliente.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sqlQuestionario = sqlQuestionario + " and q.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}else if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sqlQuestionario = sqlQuestionario + " and q.cliente.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio();
			}
			if (dataInicio != null && dataTermino != null) {
				sqlQuestionario = sqlQuestionario + " and q.dataenvio>='" + Formatacao.ConvercaoDataSql(dataInicio)
						+ "' and q.dataenvio<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			}
			if (idvenda > 0) {
				sqlQuestionario = sqlQuestionario + " and q.vendas.idvendas=" + idvenda;
			}
			if (situacao.equalsIgnoreCase("Questionario Autorizado")) {
				sqlQuestionario = sqlQuestionario + " and q.autorizado=true";
			} else if (situacao.equalsIgnoreCase("Questionario")) {
				sqlQuestionario = sqlQuestionario + " and q.autorizado=false";
			}
			sqlQuestionario = sqlQuestionario + " order by q.dataenvio desc";
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaQuestionario = questionarioHeFacade.listar(sqlQuestionario);
		}

		// ficha inscricao
		List<He> listaficha1 = null;
		if (situacao.equalsIgnoreCase("sn") || situacao.equalsIgnoreCase("Ficha Inscrição")
				|| situacao.equalsIgnoreCase("Ficha Inscrição Aprovada")) {
			String sqlFicha1 = "Select h From He h where h.vendas.cliente.nome like '" + nomeCliente + "%'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sqlFicha1 = sqlFicha1 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}else if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio();
			}
			if (dataInicio != null && dataTermino != null) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
						+ "' and h.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			}
			if (situacao.equalsIgnoreCase("Ficha Inscrição Aprovada")) {
				sqlFicha1 = sqlFicha1 + " and h.aprovado=true";
			} else if (situacao.equalsIgnoreCase("Ficha Inscrição")) {
				sqlFicha1 = sqlFicha1 + " and h.aprovado=false";
			}
			if (idvenda > 0) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.idvendas=" + idvenda;
			}
			sqlFicha1 = sqlFicha1 + " and h.fichafinal=FALSE order by h.vendas.dataVenda desc";
			HeFacade heFacade = new HeFacade();
			listaficha1 = heFacade.listar(sqlFicha1);
		}
		// ficha final
		List<He> listaficha2 = null;
		if (situacao.equalsIgnoreCase("sn") || situacao.equalsIgnoreCase("Ficha Final")) {
			String sqlFicha2 = "Select h From He h where h.vendas.cliente.nome like '" + nomeCliente + "%'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
				if(usuarioLogadoMB.getUsuario().getAcessounidade()!=null) {
					if(!usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
						sqlFicha2 = sqlFicha2 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio();
			}
			if (dataInicio != null && dataTermino != null) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
						+ "' and h.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			} 
			if (idvenda > 0) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.idvendas=" + idvenda;
			}
			sqlFicha2 = sqlFicha2 + " and h.fichafinal=TRUE order by h.vendas.dataVenda desc";
			HeFacade heFacade = new HeFacade();
			listaficha2 = heFacade.listar(sqlFicha2);
		}
		listaHe = new ArrayList<ListaHeBean>();
		ListaHeBean heBean; 
		if (listaficha1 != null && listaficha1.size() > 0) {
			for (int i = 0; i < listaficha1.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha1.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha1.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha1.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha1.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha1.get(i));
				if (listaficha1.get(i).isAprovado()) {
					heBean.setSituacao("Ficha de Inscrição Aprovada");
				} else
					heBean.setSituacao("Ficha de Inscrição");
				if(listaficha1.get(i).isDesistencia()) {
					heBean.setStatus("CANCELADA"); 
				}else {
					heBean.setStatus(listaficha1.get(i).getVendas().getSituacao());
				} 
				heBean.setData(listaficha1.get(i).getVendas().getDataVenda());
				heBean.setIdVenda(listaficha1.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha1.get(i).isAprovado());
				heBean.setDesistencia(listaficha1.get(i).isDesistencia());
				listaHe.add(heBean);
			}
		} 
		if (listaficha2 != null && listaficha2.size() > 0) {
			for (int i = 0; i < listaficha2.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha2.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha2.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha2.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha2.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha2.get(i));
				heBean.setSituacao("Ficha Final");  
				heBean.setStatus(listaficha2.get(i).getVendas().getSituacao());
				heBean.setData(listaficha2.get(i).getVendas().getDataVenda());
				heBean.setIdVenda(listaficha2.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha2.get(i).isAprovado());
				heBean.setDesistencia(listaficha2.get(i).isDesistencia());
				listaHe.add(heBean);
			}
		}
		if (listaQuestionario != null && listaQuestionario.size() > 0) {
			for (int i = 0; i < listaQuestionario.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaQuestionario.get(i).getUsuario().getNome());
				heBean.setFornecedor("");
				heBean.setUnidade(listaQuestionario.get(i).getUsuario().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaQuestionario.get(i).getCliente().getNome());
				heBean.setQuestionariohe(listaQuestionario.get(i));
				heBean.setStatus(listaQuestionario.get(i).getSituacao());
				heBean.setData(listaQuestionario.get(i).getDataenvio());
				listaHe.add(heBean);
			}
		}
		pesquisar = "Sim";
		gerarQuantidadesFichas();
	}

	public void autorizarQuestionario(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			listaHeBean.getHe().setAprovado(true);
			listaHeBean.setAutorizado(true);
			listaHeBean.setSituacao("Ficha de Inscrição Aprovada");
			HeFacade heFacade = new HeFacade();
			listaHeBean.setHe(heFacade.salvar(listaHeBean.getHe()));
			Mensagem.lancarMensagemInfo("Ficha de Inscrição autorizada!", "");
		} else if (listaHeBean.getQuestionariohe() != null) {
			listaHeBean.setAutorizado(true);
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaHeBean.setSituacao("Questionario Autorizado");
			listaHeBean.setQuestionariohe(questionarioHeFacade.salvar(listaHeBean.getQuestionariohe()));
			Mensagem.lancarMensagemInfo("Questionario autorizado!", "");
		}
	}

	public String retornarimagem(String status) {
		if (status.equalsIgnoreCase("FINALIZADO")) {
			return "../../resources/img/finalizadoFicha.png";
		}else if (status.equalsIgnoreCase("FINALIZADA")) {
			return "../../resources/img/finalizadoFicha.png";
		} else if (status.equalsIgnoreCase("ANDAMENTO")) {
			return "../../resources/img/amarelaFicha.png";
		} else if (status.equalsIgnoreCase("CANCELADA")) {
			return "../../resources/img/fichaCancelada.png";
		} else {
			return "../../resources/img/processoFicha.png";   
		}
	}
	
	public String retornarTituloFicha(String status) {
		if (status.equalsIgnoreCase("FINALIZADO")) {
			return "FICHA FINALIZADA";
		}else if (status.equalsIgnoreCase("FINALIZADA")) {
			return "FICHA FINALIZADA";
		} else if (status.equalsIgnoreCase("ANDAMENTO")) {
			return "ANDAMENTO (FICHA AGUARDANDO UPLOAD DOS DOCUMENTOS)";
		} else if (status.equalsIgnoreCase("CANCELADA")) {
			return "FICHA CANCELADA";
		} else {
			return "PROCESSO (FICHA NÃO ENVIADA PARA GERÊNCIA)";   
		}
	}

	public boolean desabilitarBtnAutorizar(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			if (listaHeBean.isAutorizado()) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	public boolean desabilitarBtnFichaInscricao(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			return true;
		} else if (listaHeBean.getQuestionariohe() != null) {
			return false;
		} else
			return false;
	}
	
	public boolean desabilitarBtnContrato(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null && listaHeBean.getHe().isFichafinal()) {
			return true;
		} else return false;
	}

	public String gerarRelatorioFicha(ListaHeBean listaHeBean) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if(listaHeBean.getHe().isFichafinal()){
			caminhoRelatorio = "/reports/higherEducation/FichaFinalHe1.jasper";
		}else{
			caminhoRelatorio = "/reports/higherEducation/FichaInscricaoHe1.jasper";
		} 
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
		parameters.put("idvendas", listaHeBean.getHe().getVendas().getIdvendas()); 
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"Ficha de Inscrição-" + listaHeBean.getHe().getVendas().getCliente().getNome() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(ListaHeBean listaHeBean) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/higherEducation/contratoHePagina01.jasper";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
		parameters.put("idvendas", listaHeBean.getHe().getVendas().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"Contrato de Higher Education-" + listaHeBean.getHe().getVendas().getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	public String fichaHE(ListaHeBean listaHeBean){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("he", listaHeBean.getHe());
		session.setAttribute("listaAndamento", listaAndamento);
		session.setAttribute("listaCancelada", listaCancelada);
		session.setAttribute("listaFinalizar", listaFinalizar);
		session.setAttribute("listaFinanceiro", listaFinanceiro);
		session.setAttribute("listaProcesso", listaProcesso);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("chamadaTela", "He");
		session.setAttribute("listaHe", listaHe);
		return "fichaHE";
	}
	
	public String contrato(ListaHeBean listaHeBean){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("he", listaHeBean.getHe());
		session.setAttribute("listaAndamento", listaAndamento);
		session.setAttribute("listaCancelada", listaCancelada);
		session.setAttribute("listaFinalizar", listaFinalizar);
		session.setAttribute("listaFinanceiro", listaFinanceiro);
		session.setAttribute("listaProcesso", listaProcesso);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("chamadaTela", "He");
		session.setAttribute("listaHe", listaHe);
		return "contratoHE";
	}
	

	
	public String documentacao(ListaHeBean listaHeBean) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("vendas", listaHeBean.getHe().getVendas());
		session.setAttribute("pesquisar", "Sim");
		session.setAttribute("nomePrograma", "He");
		session.setAttribute("listaAndamento", listaAndamento);
		session.setAttribute("listaCancelada", listaCancelada);
		session.setAttribute("listaFinalizar", listaFinalizar);
		session.setAttribute("listaFinanceiro", listaFinanceiro);
		session.setAttribute("listaProcesso", listaProcesso);
		session.setAttribute("chamadaTela", "fichaHE");
		session.setAttribute("listaHe", listaHe);
		String voltar = "consultaquestionarioHe";
		session.setAttribute("voltar", voltar);
		return "consArquivos"; 
	}
	
	public String informacoes(ListaHeBean listaHeBean) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", listaHeBean.getHe().getVendas());
		String voltar = "consquestionarioHe";
		session.setAttribute("voltar", voltar);
		return "consLogVenda"; 
	}
	
//	public String cancelarVenda(ListaHeBean listaHeBean) {
//		if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
//				|| listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
//			Map<String, Object> options = new HashMap<String, Object>();
//			options.put("contentWidth", 400);
//			FacesContext fc = FacesContext.getCurrentInstance();
//			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//			session.setAttribute("venda", listaHeBean.getQuestionariohe().getVendas());
//			if(listaHeBean.getHe()!=null && listaHeBean.getHe().getVendas1()!=null){
//				session.setAttribute("venda1", listaHeBean.getHe().getVendas1());
//			}
//			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
//		} else if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
//			VendasFacade vendasFacade = new VendasFacade();
//			listaHeBean.getQuestionariohe().getVendas().setSituacao("CANCELADA");
//			vendasFacade.salvar(listaHeBean.getQuestionariohe().getVendas());
//			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
//			listaHeBean.getQuestionariohe().setSituacao("CANCELADA");
//			listaHeBean.setQuestionariohe(questionarioHeFacade.salvar(listaHeBean.getQuestionariohe()));
//			gerarListaHe();
//		}
//		return "";
//	}
	
	public String boletos(ListaHeBean listaHeBean) {
		ValidarClienteBean validarCliente = new ValidarClienteBean(listaHeBean.getHe().getVendas().getCliente());
		if (validarCliente.getMsg().length() < 5) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			String sql = "SELECT r FROM Contasreceber r where r.vendas.idvendas=";
			if (listaHeBean.getHe() != null && listaHeBean.getHe().isFichafinal()) {
				sql = sql + listaHeBean.getHe().getVendas().getIdvendas();
			} else {
		//		sql = sql + listaHeBean.getQuestionariohe().getVendas().getIdvendas();
			}
			sql = sql + " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
					+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
			List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
			if (listaContas != null) {
				if (listaContas.size() > 0) {
					GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
					if (listaHeBean.getHe() != null && listaHeBean.getHe().isFichafinal()) {
						gerarBoletoConsultorBean.gerarBoleto(listaContas,
								String.valueOf(listaHeBean.getHe().getVendas().getIdvendas()));
					} else {
			//			gerarBoletoConsultorBean.gerarBoleto(listaContas,
				//				String.valueOf(listaHeBean.getQuestionariohe().getVendas().getIdvendas()));
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
	 
	
	public void expandirOpcoes(){
		if(expandirOpcoes){
			expandirOpcoes=false;
			esconderFicha=true;
		}else {
			expandirOpcoes=true;
			esconderFicha=false;
		}
	}
	
	public String retornarImgOpcoes(){
		if(expandirOpcoes){ 
			return "../../resources/img/esconderOpcoes.png";
		}else return "../../resources/img/expandirOpcoes.png";
	} 
	
	public String retornarTitleOpcoes(){
		if(expandirOpcoes){ 
			return "Esconder Opções";     
		}else return "Expandir Opções";
	} 
	
	
	public String visualizarContasReceber(ListaHeBean listaHeBean) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if(listaHeBean.getHe()!=null && listaHeBean.getHe().isFichafinal()){
			session.setAttribute("venda", listaHeBean.getHe().getVendas());
		}else{
	//		session.setAttribute("venda", listaHeBean.getQuestionariohe().getVendas());
		} 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
	
	public String notificarEfetuarFichaCrm(){
		return "followUp";
	}
	
	public void gerarQuantidadesFichas(){
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizada = 0; 
		nFichaFinanceiro = 0;
		nFichasProcesso = 0;
		listaFinalizar = new ArrayList<ListaHeBean>();
		listaAndamento = new ArrayList<ListaHeBean>();
		listaProcesso = new ArrayList<ListaHeBean>(); 
		listaFinanceiro = new ArrayList<ListaHeBean>();
		listaCancelada = new ArrayList<ListaHeBean>();
		for (int i = 0; i < listaHe.size(); i++) {
			if(listaHe.get(i).getHe()!=null) {
				if (listaHe.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("PROCESSO")
						&& !listaHe.get(i).getHe().isDesistencia()) {
					nFichasProcesso = nFichasProcesso + 1;
					listaProcesso.add(listaHe.get(i));
				}else if (listaHe.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
						&& !listaHe.get(i).getHe().isDesistencia()) {
					nFichasFinalizada = nFichasFinalizada + 1;
					listaFinalizar.add(listaHe.get(i));
				} else if(listaHe.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO") 
						&& !listaHe.get(i).getHe().getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")
						&& !listaHe.get(i).getHe().isDesistencia()){
					nFichaFinanceiro = nFichaFinanceiro + 1;
					listaFinanceiro.add(listaHe.get(i));
				}else if(listaHe.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
						&& !listaHe.get(i).getHe().isDesistencia()){
					nFichasAndamento = nFichasAndamento + 1;
					listaAndamento.add(listaHe.get(i));
				}else{
					nFichaCancelada = nFichaCancelada + 1;
					listaCancelada.add(listaHe.get(i));
				}
			}else {
				if (listaHe.get(i).getStatus().equalsIgnoreCase("PROCESSO")) {
					nFichasProcesso = nFichasProcesso + 1;
					listaProcesso.add(listaHe.get(i));
				}else if (listaHe.get(i).getStatus().equalsIgnoreCase("FINALIZADO")) {
					nFichasFinalizada = nFichasFinalizada + 1;
					listaFinalizar.add(listaHe.get(i));
		//		} else if(listaHe.get(i).getStatus().equalsIgnoreCase("ANDAMENTO") 
			//			&& !listaHe.get(i).getQuestionariohe().getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")){
					nFichaFinanceiro = nFichaFinanceiro + 1;
					listaFinanceiro.add(listaHe.get(i));
				}else if(listaHe.get(i).getStatus().equalsIgnoreCase("ANDAMENTO")){
					nFichasAndamento = nFichasAndamento + 1;
					listaAndamento.add(listaHe.get(i));
				}else{
					nFichaCancelada = nFichaCancelada + 1;
					listaCancelada.add(listaHe.get(i));
				}
			}
		}
	}
	
	public void desistenciaHE(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			listaHeBean.getHe().setDesistencia(true);
			listaHeBean.setStatus("CANCELADA");
			HeFacade heFacade = new HeFacade();
			listaHeBean.setHe(heFacade.salvar(listaHeBean.getHe()));
			Mensagem.lancarMensagemInfo("Ficha de Inscrição Cancelada!", "");
		} 
	}
	
	public boolean desabilitarBtnDesistencia(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			if (listaHeBean.getHe().isDesistencia()) {
				return true;
			} else {
				return false;
			}
		} else return true;
	}
	
	
	public String cancelarVenda(ListaHeBean listaHeBean) {
	//	if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
	//			|| listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			if(listaHeBean.getHe()!=null && listaHeBean.getHe().getVendas()!=null && listaHeBean.getHe()!=null && !listaHeBean.getHe().getVendas().getSituacao().equalsIgnoreCase("CANCELADA")){
				session.setAttribute("vendas", listaHeBean.getHe().getVendas());
			}else {
	//			session.setAttribute("vendas", listaHeBean.getQuestionariohe().getVendas());
			}
			session.setAttribute("voltar", "consquestionarioHe");
			return "emissaocancelamento";
	//	}  else if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("PROCESSO") 
		//		|| listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("CANCELADA")) {
	//		VendasFacade vendasFacade = new VendasFacade();
	//		listaHeBean.getQuestionariohe().getVendas().setSituacao("CANCELADA");
		//	vendasFacade.salvar(listaHeBean.getQuestionariohe().getVendas());
	//		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		//	listaHeBean.getQuestionariohe().setSituacao("CANCELADA");
		//	listaHeBean.setQuestionariohe(questionarioHeFacade.salvar(listaHeBean.getQuestionariohe()));
		//	gerarListaHe();
	//	}
	//	return "";
	}   
	
	

}
