package br.com.travelmate.managerBean.seguroviagem.controle;

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

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.ControleSeguroFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ControleSeguroMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controleseguro controleSeguro;
	private List<Controleseguro> listaControleSeguro;
	private String nomeCliente;
	private Date dataInicio;
	private Date dataFim;
	private int idVenda = 0;
	private String tipoData; 
	private String numeroDiasString;
	private String motivoCancelamento;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String tipoEmissao;
	private String numeroSeguroString;
	private int nFichasFinalizadas;
	private int nFichasAndamento;
	private int nFichaCancelada;
	private int nFichasFinanceiro;
	private int nFichaSeguroCancelamento;
	private List<Controleseguro> listaVendasFinalizada;
	private List<Controleseguro> listaVendasAndamento;
	private List<Controleseguro> listaVendasCancelada;
	private List<Controleseguro> listaVendasFinanceiro;
	private List<Controleseguro> listaVendasSeguroCancelamento;
	private int numeroFichas;
	private boolean pesquisa=false;

	@PostConstruct
	public void init() {
		//salvarDataEmissão();
		listarControle();
		gerarListaUnidadeNegocio();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Controleseguro getControleCurso() {
		return controleSeguro;
	}

	public void setControleCurso(Controleseguro controleCurso) {
		this.controleSeguro = controleCurso;
	}

	public List<Controleseguro> getListaControleSeguro() {
		return listaControleSeguro;
	}

	public void setListaControleSeguro(List<Controleseguro> listaControleSeguro) {
		this.listaControleSeguro = listaControleSeguro;
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

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Controleseguro getControleSeguro() {
		return controleSeguro;
	}

	public void setControleSeguro(Controleseguro controleSeguro) {
		this.controleSeguro = controleSeguro;
	}

	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public String getNumeroDiasString() {
		return numeroDiasString;
	}

	public void setNumeroDiasString(String numeroDiasString) {
		this.numeroDiasString = numeroDiasString;
	} 

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public String getTipoData() {
		return tipoData;
	}

	public void setTipoData(String tipoData) {
		this.tipoData = tipoData;
	}

	public String getTipoEmissao() {
		return tipoEmissao;
	}

	public void setTipoEmissao(String tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
	}

	public String getNumeroSeguroString() {
		return numeroSeguroString;
	}

	public void setNumeroSeguroString(String numeroSeguroString) {
		this.numeroSeguroString = numeroSeguroString;
	}

	public int getnFichasFinalizadas() {
		return nFichasFinalizadas;
	}

	public void setnFichasFinalizadas(int nFichasFinalizadas) {
		this.nFichasFinalizadas = nFichasFinalizadas;
	}

	public int getnFichasAndamento() {
		return nFichasAndamento;
	}

	public void setnFichasAndamento(int nFichasAndamento) {
		this.nFichasAndamento = nFichasAndamento;
	}

	public int getnFichaCancelada() {
		return nFichaCancelada;
	}

	public void setnFichaCancelada(int nFichaCancelada) {
		this.nFichaCancelada = nFichaCancelada;
	}

	public int getnFichasFinanceiro() {
		return nFichasFinanceiro;
	}

	public void setnFichasFinanceiro(int nFichasFinanceiro) {
		this.nFichasFinanceiro = nFichasFinanceiro;
	}

	public List<Controleseguro> getListaVendasFinalizada() {
		return listaVendasFinalizada;
	}

	public void setListaVendasFinalizada(List<Controleseguro> listaVendasFinalizada) {
		this.listaVendasFinalizada = listaVendasFinalizada;
	}

	public List<Controleseguro> getListaVendasAndamento() {
		return listaVendasAndamento;
	}

	public void setListaVendasAndamento(List<Controleseguro> listaVendasAndamento) {
		this.listaVendasAndamento = listaVendasAndamento;
	}

	public List<Controleseguro> getListaVendasCancelada() {
		return listaVendasCancelada;
	}

	public void setListaVendasCancelada(List<Controleseguro> listaVendasCancelada) {
		this.listaVendasCancelada = listaVendasCancelada;
	}

	public List<Controleseguro> getListaVendasFinanceiro() {
		return listaVendasFinanceiro;
	}

	public void setListaVendasFinanceiro(List<Controleseguro> listaVendasFinanceiro) {
		this.listaVendasFinanceiro = listaVendasFinanceiro;
	}

	public int getNumeroFichas() {
		return numeroFichas;
	}

	public void setNumeroFichas(int numeroFichas) {
		this.numeroFichas = numeroFichas;
	}

	public int getnFichaSeguroCancelamento() {
		return nFichaSeguroCancelamento;
	}

	public void setnFichaSeguroCancelamento(int nFichaSeguroCancelamento) {
		this.nFichaSeguroCancelamento = nFichaSeguroCancelamento;
	}

	public List<Controleseguro> getListaVendasSeguroCancelamento() {
		return listaVendasSeguroCancelamento;
	}

	public void setListaVendasSeguroCancelamento(List<Controleseguro> listaVendasSeguroCancelamento) {
		this.listaVendasSeguroCancelamento = listaVendasSeguroCancelamento;
	}

	public void listarControle() {
		ControleSeguroFacade controleSeguroFacade = new ControleSeguroFacade();
		String sql = "SELECT c FROM Controleseguro c WHERE c.seguroviagem.possuiSeguro='Sim' " 
				+ " and c.seguroviagem.dataInicio>='" + Formatacao.ConvercaoDataSql(new Date())
				+ "' AND c.seguroviagem.vendas.situacao<>'CANCELADA' ORDER BY c.seguroviagem.dataInicio, c.idcontroleSeguro DESC";
		listaControleSeguro = controleSeguroFacade.listar(sql);
		if (listaControleSeguro == null) {
			listaControleSeguro = new ArrayList<Controleseguro>();
		}
		numeroFichas = listaControleSeguro.size();
		gerarQuantidadesFichas();
		numeroDias();
	}

	public void pesquisar() {
		ControleSeguroFacade controleSeguroFacade = new ControleSeguroFacade();
		String sql;
		sql = "select c from Controleseguro c where c.seguroviagem.vendas.cliente.nome like '%" + nomeCliente
				+ "%' and c.seguroviagem.vendas.situacao<>'CANCELADA' ";
		if (idVenda > 0) {
			sql = sql + " and (c.seguroviagem.vendas.idvendas=" + idVenda + " or c.seguroviagem.idvendacurso=" + idVenda
					+ ")";
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and c.seguroviagem.vendas.unidadenegocio.idunidadeNegocio="
					+ unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " and c.seguroviagem.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if ((dataInicio != null) && (dataFim != null)) {
			sql = sql + " and c.seguroviagem.dataInicio>='" + Formatacao.ConvercaoDataSql(dataInicio) + "'";
			sql = sql + " and c.seguroviagem.dataInicio<='" + Formatacao.ConvercaoDataSql(dataFim) + "'";
		}
		if (tipoEmissao != null && !tipoEmissao.equals("Todos")) {
			sql = sql + " and c.seguroviagem.controle='" + tipoEmissao + "' ";
		}
		sql = sql + " order by c.seguroviagem.dataInicio, c.idcontroleSeguro desc";
		listaControleSeguro = controleSeguroFacade.listar(sql);
		if (listaControleSeguro == null) {
			listaControleSeguro = new ArrayList<Controleseguro>();
		}
		pesquisa= true; 
		gerarQuantidadesFichas(); 
		numeroDias();
	}

	public void limpar() {
		pesquisa=false;
		nomeCliente = "";
		idVenda = 0;
		dataFim = null;
		dataInicio = null;
		unidadenegocio = null;
		usuario = null;
		tipoEmissao = "Todos";
		listarControle(); 
	} 
	
	public void numeroDias() {
		int numeroDias = 0;
		int numeroseguro = 0;
		for (int i = 0; i < listaVendasFinanceiro.size(); i++) {
			if (listaVendasFinanceiro.get(i).getSeguroviagem().getNumeroSemanas() != null) {
				numeroDias = numeroDias + listaVendasFinanceiro.get(i).getSeguroviagem().getNumeroSemanas();
				numeroseguro = numeroseguro + 1;
			}
		}
		for (int i = 0; i < listaVendasAndamento.size(); i++) {
			if (listaVendasAndamento.get(i).getSeguroviagem().getNumeroSemanas() != null) {
				numeroDias = numeroDias + listaVendasAndamento.get(i).getSeguroviagem().getNumeroSemanas();
				numeroseguro = numeroseguro + 1;
			}
		}
		for (int i = 0; i < listaVendasFinalizada.size(); i++) {
			if (listaVendasFinalizada.get(i).getSeguroviagem().getNumeroSemanas() != null) {
				numeroDias = numeroDias + listaVendasFinalizada.get(i).getSeguroviagem().getNumeroSemanas();
				numeroseguro = numeroseguro + 1;
			}
		}
		for (int i = 0; i < listaVendasCancelada.size(); i++) {
			if (listaVendasCancelada.get(i).getSeguroviagem().getNumeroSemanas() != null) {
				numeroDias = numeroDias + listaVendasCancelada.get(i).getSeguroviagem().getNumeroSemanas();
				numeroseguro = numeroseguro + 1;
			}
		}
		for (int i = 0; i < listaVendasSeguroCancelamento.size(); i++) {
			if (listaVendasSeguroCancelamento.get(i).getSeguroviagem().getNumeroSemanas() != null) {
				numeroDias = numeroDias + listaVendasSeguroCancelamento.get(i).getSeguroviagem().getNumeroSemanas();
				numeroseguro = numeroseguro + 1;
			}
		}
		numeroDiasString = "Número de dias vendido: " + numeroDias;
		numeroSeguroString = "Número de Seguros:" + numeroseguro;
	}

	public void cancelar() {
		ControleSeguroFacade controleSeguroFacade = new ControleSeguroFacade();
		for (int i = 0; i < listaControleSeguro.size(); i++) {
			if (listaControleSeguro.get(i).isSelecionado()) {
				listaControleSeguro.get(i).setSituacao("CANCELADA");
				controleSeguroFacade.salvarControle(listaControleSeguro.get(i));
				Vendas vendas = new Vendas();
				vendas = listaControleSeguro.get(i).getSeguroviagem().getVendas();
				vendas.setSituacao("CANCELADA");
				vendas.setObsCancelar(motivoCancelamento);
				vendas.setDatacancelamento(new Date());
				vendas.setUsuariocancelamento(usuarioLogadoMB.getUsuario().getIdusuario());
				
				vendas = vendasDao.salvar(vendas);
				Seguroviagem seguroviagem = new Seguroviagem();
				seguroviagem = listaControleSeguro.get(i).getSeguroviagem();
				seguroviagem.setControle("CANCELADA");
				SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
				seguroviagem = seguroViagemFacade.salvar(seguroviagem);
			}
		}
		listarControle();
	}  

	public String cancelarVenda(Controleseguro controleseguro) {
		if (controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", controleseguro.getSeguroviagem().getVendas());
			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
		} else if (controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			
			controleseguro.getSeguroviagem().getVendas().setSituacao("CANCELADA");
			vendasDao.salvar(controleseguro.getSeguroviagem().getVendas());
			listarControle();
		}
		return "";
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar();
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null) {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			listaConsultor = usuarioFacade
					.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
			if (listaConsultor == null) {
				listaConsultor = new ArrayList<Usuario>();
			}
		}
	}

	public void gerarQuantidadesFichas() {
		nFichaCancelada = 0;
		nFichasAndamento = 0;
		nFichasFinalizadas = 0;
		nFichasFinanceiro = 0;
		nFichaSeguroCancelamento = 0;
		listaVendasFinalizada = new ArrayList<Controleseguro>();
		listaVendasAndamento = new ArrayList<Controleseguro>();
		listaVendasCancelada = new ArrayList<Controleseguro>();
		listaVendasFinanceiro = new ArrayList<Controleseguro>();
		listaVendasSeguroCancelamento = new ArrayList<Controleseguro>();
		Date data = null; 
		Date dataemissao = null;
		try {
			data = Formatacao.SomarDiasDatas(new Date(), 10);
			dataemissao = Formatacao.SomarDiasDatas(new Date(), 3);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		for (int i = 0; i < listaControleSeguro.size(); i++) { 
			if(pesquisa) { 
				if (listaControleSeguro.get(i).getSeguroviagem().isSegurocancelamento() 
						&& listaControleSeguro.get(i).getDataemissao()==null) {
					nFichaSeguroCancelamento = nFichaSeguroCancelamento + 1;
					listaVendasSeguroCancelamento.add(listaControleSeguro.get(i));
				}else if (listaControleSeguro.get(i).getSeguroviagem().isSegurocancelamento() 
						&& dataemissao.before(dataemissao)) {
					nFichaSeguroCancelamento = nFichaSeguroCancelamento + 1;
					listaVendasSeguroCancelamento.add(listaControleSeguro.get(i));
				}else if (listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
					nFichasFinalizadas = nFichasFinalizadas + 1;
					listaVendasFinalizada.add(listaControleSeguro.get(i));
				} else if (listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacao()
						.equalsIgnoreCase("ANDAMENTO")
						&& !listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacaofinanceiro()
								.equalsIgnoreCase("L")) {
					nFichasFinanceiro = nFichasFinanceiro + 1;
					listaVendasFinanceiro.add(listaControleSeguro.get(i));
				} else if (listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacao()
						.equalsIgnoreCase("ANDAMENTO")) {
					nFichasAndamento = nFichasAndamento + 1;
					listaVendasAndamento.add(listaControleSeguro.get(i));
				} else {
					nFichaCancelada = nFichaCancelada + 1;
					listaVendasCancelada.add(listaControleSeguro.get(i));
				}
			}else {
				if(listaControleSeguro.get(i).getSeguroviagem().getDataInicio()!=null) {
					if (listaControleSeguro.get(i).getSeguroviagem().isSegurocancelamento() 
							&& listaControleSeguro.get(i).getDataemissao()==null) {
						nFichaSeguroCancelamento = nFichaSeguroCancelamento + 1;
						listaVendasSeguroCancelamento.add(listaControleSeguro.get(i));
					}else if (listaControleSeguro.get(i).getSeguroviagem().isSegurocancelamento() 
							&& dataemissao.before(dataemissao)) {
						nFichaSeguroCancelamento = nFichaSeguroCancelamento + 1;
						listaVendasSeguroCancelamento.add(listaControleSeguro.get(i));
					}else if (listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
							&& listaControleSeguro.get(i).getDataemissao()==null 
							&& listaControleSeguro.get(i).getSeguroviagem().getDataInicio().before(data)) {
						nFichasFinalizadas = nFichasFinalizadas + 1;
						listaVendasFinalizada.add(listaControleSeguro.get(i));
					} else if (listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
							&& !listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")
							&& listaControleSeguro.get(i).getDataemissao()==null  
							&& listaControleSeguro.get(i).getSeguroviagem().getDataInicio().before(data)) {
						nFichasFinanceiro = nFichasFinanceiro + 1;
						listaVendasFinanceiro.add(listaControleSeguro.get(i));
					} else if (listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
							&& listaControleSeguro.get(i).getDataemissao()==null 
							&& listaControleSeguro.get(i).getSeguroviagem().getDataInicio().before(data)) {
						nFichasAndamento = nFichasAndamento + 1;
						listaVendasAndamento.add(listaControleSeguro.get(i));
					} else if(listaControleSeguro.get(i).getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("CANCELADA") 
							&& listaControleSeguro.get(i).getSeguroviagem().getDataInicio().before(data)) {
						nFichaCancelada = nFichaCancelada + 1;
						listaVendasCancelada.add(listaControleSeguro.get(i));
					}
				}
			}
		}
	}
	
	public String possuiSeguro(Controleseguro controleseguro) {
		if(!controleseguro.getSeguroviagem().isSegurocancelamento()) {
			return "iconeSApp";
		}
		return "iconeCheck";
	}
	
	public String titleVenda(Controleseguro controleseguro) {
		if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
			return "FINALIZADO";
		}else if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("CANCELADA")) {
			return "CANCELADA";
		}else if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
				&& !controleseguro.getSeguroviagem().getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			return "FINANCEIRO";
		}else if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			return "ANDAMENTO";
		}
		return "";
	}
	
	public String imagemVenda(Controleseguro controleseguro) {
		if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
			return "../../resources/img/finalizadoFicha.png";
		}else if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("CANCELADA")) {
			return "../../resources/img/fichaCancelada.png";
		}else if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")
				&& !controleseguro.getSeguroviagem().getVendas().getSituacaofinanceiro().equalsIgnoreCase("L")) {
			return "../../resources/img/ficharestricao.png";
		}else if(controleseguro.getSeguroviagem().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			return "../../resources/img/amarelaFicha.png";
		}
		return "";
	}
	
	public String uploadApolise(Controleseguro controleseguro) { 
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("controleseguro", controleseguro);
		if(controleseguro.getSeguroviagem().getControle().equalsIgnoreCase("Avulso")) {
			session.setAttribute("vendas", controleseguro.getSeguroviagem().getVendas());
		}else {
			
			Vendas vendas = vendasDao.consultarVendas(controleseguro.getSeguroviagem().getIdvendacurso());
			session.setAttribute("vendas", vendas);
		} 
		RequestContext.getCurrentInstance().openDialog("cadArquivo", options, null); 
		return "";
	}
	
	public void finalizarSeguro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Controleseguro controleseguro = (Controleseguro) session.getAttribute("controleseguro");
		session.removeAttribute("controleseguro");
		if(controleseguro!=null) {
			controleseguro.setDataemissao(new Date());
			ControleSeguroFacade controleSeguroFacade = new ControleSeguroFacade();
			controleseguro = controleSeguroFacade.salvarControle(controleseguro); 
		}
	}
	
	public void salvarDataEmissão() {
		ControleSeguroFacade controleSeguroFacade = new ControleSeguroFacade();
		String sql = "SELECT c FROM Controleseguro c WHERE c.dataemissao is null " 
				+ " and c.seguroviagem.dataInicio<'" + Formatacao.ConvercaoDataSql(new Date())
				+ "' ORDER BY c.seguroviagem.dataInicio, c.idcontroleSeguro DESC";
		listaControleSeguro = controleSeguroFacade.listar(sql);
		if (listaControleSeguro != null) {
			Date data = Formatacao.ConvercaoStringData("01/01/1900");
			for (int i = 0; i < listaControleSeguro.size(); i++) {
				listaControleSeguro.get(i).setDataemissao(data);
				controleSeguroFacade.salvarControle(listaControleSeguro.get(i));
			}
		}
	}
	
	public void imprimirFicha(Seguroviagem seguro) {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if (seguro.getIdvendacurso() > 0) {
			caminhoRelatorio = ("/reports/seguro/FichaSeguroCursoPagina01.jasper");
		} else {
			caminhoRelatorio = ("/reports/seguro/FichaSeguroPagina01.jasper");
		}
		Map parameters = new HashMap();
		try {
			if(seguro.getIdvendacurso()>0){
				CursoFacade cursoFacade = new CursoFacade();
				Curso curso = cursoFacade.consultarCursos(seguro.getIdvendacurso());
				if(curso!=null){
					seguro.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
					seguro.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
					seguro.setPaisDestino(curso.getPais());
					SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
					seguro = seguroViagemFacade.salvar(seguro);
				}
			}
			parameters.put("idvendas", seguro.getVendas().getIdvendas());
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		try {
			gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "fichaSeguroViagem", null);
		} catch (JRException | IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String visualizarContasReceber(Seguroviagem seguroviagem) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", seguroviagem.getVendas());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 750);
		RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		return "";
	}
}
