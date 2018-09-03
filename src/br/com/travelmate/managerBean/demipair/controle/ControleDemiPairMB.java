package br.com.travelmate.managerBean.demipair.controle;

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

import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controledemipair;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleDemiPairMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controledemipair controledemi;
	private List<Controledemipair> listaControle;
	private String nomeCliente;
	private Date iniDataEmbarque;
	private Date finalDataEmbarque;
	private String motivoCancelamento;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String situacao;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private int idVenda;
	private String voltar;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaControle =  (List<Controledemipair>) session.getAttribute("listaControle");
		session.removeAttribute("listaControle");
		if (listaControle == null || listaControle.size() == 0) {
			listarControle();
		}
		gerarListaUnidadeNegocio();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Controledemipair getControledemi() {
		return controledemi;
	}

	public void setControledemi(Controledemipair controledemi) {
		this.controledemi = controledemi;
	}

	public List<Controledemipair> getListaControle() {
		return listaControle;
	}

	public void setListaControle(List<Controledemipair> listaControle) {
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	
	

	public String getVoltar() {
		return voltar;
	}

	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}

	public void listarControle() {
		DemipairFacade demipairFacade = new DemipairFacade();
		String sql;
		String data = Formatacao.SubtarirDatas(new Date(), 365, "yyyy/MM/dd");
		sql = "select c from Controledemipair c where c.statusprocesso<>'Finalizado' and c.statusprocesso<>'Cancelado' and c.vendas.dataVenda>='" + data + "' order by c.vendas.dataVenda desc";
		listaControle = demipairFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controledemipair>();
		}
	}
  
	public String invoice(Controledemipair controle) {
		if (controle != null) {
			InvoiceFacade invoiceFacade = new InvoiceFacade();
			Invoice invoice = invoiceFacade.consultarVenda(controle.getVendas().getIdvendas(),
					controle.getVendas().getProdutos().getIdprodutos(), controle.getIdcontroledemipair());
			if (invoice == null) {
				invoice = new Invoice();
				invoice.setControle(controle.getIdcontroledemipair());
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
		DemipairFacade demipairFacade = new DemipairFacade();
		String sql;
		sql = "select c from Controledemipair c where c.vendas.cliente.nome like '%" + nomeCliente
				+ "%'  ";
		if(idVenda>0){
			sql= sql+ " and c.vendas.idvendas="+idVenda;
		}
		if (unidadenegocio!=null){ 
			sql = sql + " and  c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario!=null && usuario.getIdusuario()!=null){ 
			sql = sql + " and  c.vendas.usuario.idusuario=" + usuario.getIdusuario();
		}
		if ((iniDataEmbarque != null) && (finalDataEmbarque != null)) {
			sql = sql + " and c.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(iniDataEmbarque) + "'";
			sql = sql + " and c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(finalDataEmbarque) + "'";
		}
		if(!situacao.equalsIgnoreCase("TODOS")){
			sql = sql + " and c.statusprocesso='"+situacao+"'";
		}
		sql = sql + " order by c.idcontroledemipair";
		listaControle = demipairFacade.listaControle(sql);
		if (listaControle == null) {
			listaControle = new ArrayList<Controledemipair>();
		}
	}

	public void limpar() {
		iniDataEmbarque = null;
		finalDataEmbarque = null;
		nomeCliente = "";
		idVenda=0;
		unidadenegocio=null;
		usuario=null;
		situacao="TODOS";
		listarControle();
	}


	public void finalizar(Controledemipair controle) {
		DemipairFacade demipairFacade = new DemipairFacade();
		controle.setStatusprocesso("Finalizado");;
		demipairFacade.salvar(controle);
		listarControle();
	}

	public String documentacao(Controledemipair controle) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", controle.getVendas());
		voltar = "controleDemipair";
		session.setAttribute("voltar", voltar);
		session.setAttribute("listaControle", listaControle);
		return "consArquivos";
	}
	
	public void gerarListaConsultor(){
		if(unidadenegocio!=null){
	        UsuarioFacade usuarioFacade = new UsuarioFacade();
	        listaConsultor = usuarioFacade.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio()+" order by u.nome");
	        if (listaConsultor==null){
	            listaConsultor = new ArrayList<Usuario>();
	        }
		}
    }
	
	
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
	
	
	
	public String atualizarInformacoes(Controledemipair controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleDemipair", options, null);
		}
		return "";
	}
	
	
	public String documentosDemiPair(Controledemipair controle){
		if(controle.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")){
			return "../../resources/img/bolaVermelha.png";
		}else{
			return "../../resources/img/bolaVerde.png";
		}
	}
	

	public void retornoDialogAtualizar(SelectEvent event) {
		Controledemipair controledemipair = (Controledemipair) event.getObject();
		documentosDemiPair(controledemipair);
	}
	
	public String imagemSituacao(Controledemipair controle){
		if(controle.getStatusprocesso().equalsIgnoreCase("Processo")){
			return "../../resources/img/bolaVerde.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Visto")){
			return "../../resources/img/bolaAmarela.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Cancelado")){
			return "../../resources/img/bolaVermelha.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Finalizado")){
			return "../../resources/img/bolaPreta.png";
		}else{
			return "";
		}
	}
	
	public void salvarStatus(Controledemipair controle){
		DemipairFacade demipairFacade = new DemipairFacade();
		controle = demipairFacade.salvar(controle);
	}
}
