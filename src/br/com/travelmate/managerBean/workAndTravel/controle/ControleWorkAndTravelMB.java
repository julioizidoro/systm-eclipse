package br.com.travelmate.managerBean.workAndTravel.controle;

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

import br.com.travelmate.facade.ControleWorkEmbarqueFacade;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Controleworkentrevista;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleWorkAndTravelMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controlework controlework;
	private List<Controlework> listaControle;
	private String nomeCliente;
	private Date iniDataEmbarque;
	private Date finalDataEmbarque;
	private String motivoCancelamento;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String programa;
	private String situacao;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private int idvenda;
	private String voltar;
	private int numeroProcesso;
	private int numeroEntrevista;
	private int numeroContratado;
	private int numeroEmbarcado;
	private List<Controlework> listaProcesso;
	private List<Controlework> listaEntrevista;
	private List<Controlework> listaContratado;
	private List<Controlework> listaEmbarcado;

	@PostConstruct
	public void init() {
		listarControle();
		gerarListaUnidadeNegocio();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Controlework getControlework() {
		return controlework;
	}

	public void setControlework(Controlework controlework) {
		this.controlework = controlework;
	}

	public List<Controlework> getListaControle() {
		return listaControle;
	}

	public void setListaControle(List<Controlework> listaControle) {
		this.listaControle = listaControle;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getIniDataEmbarque() {
		return iniDataEmbarque;
	}

	public void setIniDataEmbarque(Date iniDataEmbarque) {
		this.iniDataEmbarque = iniDataEmbarque;
	}

	public Date getFinalDataEmbarque() {
		return finalDataEmbarque;
	}

	public void setFinalDataEmbarque(Date finalDataEmbarque) {
		this.finalDataEmbarque = finalDataEmbarque;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
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

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public int getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(int numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public int getNumeroEntrevista() {
		return numeroEntrevista;
	}

	public void setNumeroEntrevista(int numeroEntrevista) {
		this.numeroEntrevista = numeroEntrevista;
	}

	public int getNumeroContratado() {
		return numeroContratado;
	}

	public void setNumeroContratado(int numeroContratado) {
		this.numeroContratado = numeroContratado;
	}

	public int getNumeroEmbarcado() {
		return numeroEmbarcado;
	}

	public void setNumeroEmbarcado(int numeroEmbarcado) {
		this.numeroEmbarcado = numeroEmbarcado;
	}

	public List<Controlework> getListaEntrevista() {
		return listaEntrevista;
	}

	public void setListaEntrevista(List<Controlework> listaEntrevista) {
		this.listaEntrevista = listaEntrevista;
	}

	public List<Controlework> getListaContratado() {
		return listaContratado;
	}

	public void setListaContratado(List<Controlework> listaContratado) {
		this.listaContratado = listaContratado;
	}

	public List<Controlework> getListaEmbarcado() {
		return listaEmbarcado;
	}

	public void setListaEmbarcado(List<Controlework> listaEmbarcado) {
		this.listaEmbarcado = listaEmbarcado;
	}

	public List<Controlework> getListaProcesso() {
		return listaProcesso;
	}

	public void setListaProcesso(List<Controlework> listaProcesso) {
		this.listaProcesso = listaProcesso;
	}

	public void listarControle() {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		String sql;
		String data = Formatacao.SubtarirDatas(new Date(), 365, "yyyy/MM/dd");
		sql = "SELECT c FROM Controlework c WHERE c.statusprocesso<>'Cancelado'"
				+ " AND c.vendas.situacao<>'CANCELADA' AND c.vendas.dataVenda>='" + data
				+ "' AND (c.dataretorno IS NULL OR c.dataretorno>='" + Formatacao.ConvercaoDataSql(new Date()) + "')";
		if(!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isGerenciaswork()) {
			sql = sql + " AND c.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			if(usuarioLogadoMB.getUsuario().getAcessounidade().isEmissaoconsulta()) {
				sql = sql + " AND c.vendas.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
			}
		}
		sql = sql + " ORDER BY c.vendas.dataVenda DESC";
		listaControle = workTravelFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controlework>();
		} else {
			gerarNumeroControle();
		}
	}

	public String invoice(Controlework controle) {
		if (controle != null) {
			InvoiceFacade invoiceFacade = new InvoiceFacade();
			Invoice invoice = invoiceFacade.consultarVenda(controle.getVendas().getIdvendas(),
					controle.getVendas().getProdutos().getIdprodutos(), controle.getIdcontroleWork());
			if (invoice == null) {
				invoice = new Invoice();
				invoice.setControle(controle.getIdcontroleWork());
				invoice.setProdutos(controle.getVendas().getProdutos());
				invoice.setVendas(controle.getVendas());
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("invoice", invoice);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 600);
			RequestContext.getCurrentInstance().openDialog("invoiceControle", options, null);
		}
		return "";
	}

	public void pesquisar() {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		String sql;
		sql = "select c from Controlework c where c.vendas.cliente.nome like '%" + nomeCliente + "%'  ";
		if (idvenda > 0) {
			sql = sql + " and  c.vendas.idvendas=" + idvenda;
		}
		if (unidadenegocio != null) {
			sql = sql + " and  c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " and  c.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if ((iniDataEmbarque != null) && (finalDataEmbarque != null)) {
			sql = sql + " and c.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(iniDataEmbarque) + "'";
			sql = sql + " and c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(finalDataEmbarque) + "'";
		}
		if (!situacao.equalsIgnoreCase("TODOS")) {
			sql = sql + " and c.statusprocesso='" + situacao + "'";
		}
		if (!programa.equalsIgnoreCase("TODOS")) {
			sql = sql + " and c.observacoes='" + programa + "'";
		}
		sql = sql + " order by c.idcontroleWork";
		listaControle = workTravelFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controlework>();
		} else {
			gerarNumeroControle();
		}
	}

	public void limpar() {
		iniDataEmbarque = null;
		finalDataEmbarque = null;
		nomeCliente = "";
		idvenda = 0;
		unidadenegocio = null;
		usuario = null;
		situacao = "TODOS";
		programa = "TODOS";
		listarControle();
	}

	public void finalizar(Controlework controle) {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		controle.setStatusprocesso("Finalizado");
		workTravelFacade.salvar(controle);
		listarControle();
	}

	public String documentacao(Controlework controle) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", controle.getVendas());
		voltar = "controleWorkAndTravel";
		session.setAttribute("voltar", voltar);
		return "consArquivos";
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

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

	public String atualizarInformacoes(Controlework controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleWork", options, null);
		}
		return "";
	}

	public String imagemSituacao(Controlework controle) {
		if (controle.getStatusprocesso().equalsIgnoreCase("Processo")) {
			return "../../resources/img/bolaVerde.png";
		} else if (controle.getStatusprocesso().equalsIgnoreCase("Entrevista")) {
			return "../../resources/img/bolaAzul.png";
		} else if (controle.getStatusprocesso().equalsIgnoreCase("Contratado")) {
			return "../../resources/img/bolaAmarela.png";
		} else if (controle.getStatusprocesso().equalsIgnoreCase("Passagem/Visto")) {
			return "../../resources/img/bolaPreta.png";
		} else {
			return "";
		}
	}

	public void gerarNumeroControle() {
		numeroContratado = 0;
		numeroEmbarcado = 0;
		numeroEntrevista = 0;
		numeroProcesso = 0;
		listaProcesso = new ArrayList<Controlework>();
		listaContratado = new ArrayList<Controlework>();
		listaEmbarcado = new ArrayList<Controlework>();
		listaEntrevista = new ArrayList<Controlework>();
		for (int i = 0; i < listaControle.size(); i++) {
			if (listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Processo")) {
				numeroProcesso = numeroProcesso + 1;
				listaProcesso.add(listaControle.get(i));
			} else if (listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Entrevista")) {
				numeroEntrevista = numeroEntrevista + 1;
				listaEntrevista.add(listaControle.get(i));
			} else if (listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Contratado")) {
				numeroContratado = numeroContratado + 1;
				listaContratado.add(listaControle.get(i));
			} else if (listaControle.get(i).getStatusprocesso().equalsIgnoreCase("Passagem/Visto")) {
				numeroEmbarcado = numeroEmbarcado + 1;
				listaEmbarcado.add(listaControle.get(i));
			}
		}
	}

	public String marcarEntrevista(Controlework controlework) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("controlework", controlework);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 700);
		RequestContext.getCurrentInstance().openDialog("cadControleWorkEntrevista", options, null);
		return "";
	}
	
	public String visualizarEntrevista(Controleworkentrevista controleworkentrevista) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("controlework", controleworkentrevista.getControlework());
		if(!controleworkentrevista.getSituacaosponsor().equalsIgnoreCase("Negado")
				|| !controleworkentrevista.getSituacaoempreagador().equalsIgnoreCase("Negado")) {
			session.setAttribute("controleworkentrevista", controleworkentrevista);
		}
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 700);
		RequestContext.getCurrentInstance().openDialog("cadControleWorkEntrevista", options, null);
		return "";
	}
	
	public void editar(Controlework controlework) {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		controlework = workTravelFacade.salvar(controlework);
		if(controlework.getControleworkembarque()!=null) { 
			if(controlework.getDataIniciojoboffer()!=null) {
				try {
					controlework.getControleworkembarque().setDataembarqueinicial(Formatacao.SomarDiasDatas
							(controlework.getDataIniciojoboffer(), -6));
					controlework.getControleworkembarque().setDataembarquefinal(Formatacao.SomarDiasDatas
							(controlework.getDataIniciojoboffer(), -1));
				} catch (Exception e) { 
					  
				}
			}
			if(controlework.getDataTerminojoboffer()!=null) {
				try {
					controlework.getControleworkembarque().setDataretornoinicial(Formatacao.SomarDiasDatas
							(controlework.getDataTerminojoboffer(), 1));
					controlework.getControleworkembarque().setDataretornofinal(Formatacao.SomarDiasDatas
							(controlework.getDataTerminojoboffer(), 29));
				} catch (Exception e) { 
					  
				}
			}
		}
		ControleWorkEmbarqueFacade controleWorkEmbarqueFacade = new ControleWorkEmbarqueFacade();
		controlework.setControleworkembarque(controleWorkEmbarqueFacade.salvar(controlework.getControleworkembarque()));
	}
	
	public String cadControleWorkEmbarque(Controlework controlework) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("controlework", controlework);
		if(controlework.getControleworkembarque()!=null) {
			session.setAttribute("controleworkembarque", controlework.getControleworkembarque());
		}
		Map<String, Object> options = new HashMap<String, Object>();  
		options.put("contentWidth", 700);
		RequestContext.getCurrentInstance().openDialog("cadControleWorkEmbarque", options, null);
		return "";
	}
	
	public String cadControleWorkEmbarqueConsultor(Controlework controlework) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("controlework", controlework);
		if(controlework.getControleworkembarque()!=null) {
			session.setAttribute("controleworkembarque", controlework.getControleworkembarque());
		}
		Map<String, Object> options = new HashMap<String, Object>();  
		options.put("contentWidth", 700);
		RequestContext.getCurrentInstance().openDialog("controleWorkEmbarqueConsultor", options, null);
		return "";
	}
	
	public String controleWorkEmbarqueConsultor() { 
		return "consControleWorkConsultor";
	}
	
	
	public void retornoDialogAtualizar() {
		listarControle();
	}
}
