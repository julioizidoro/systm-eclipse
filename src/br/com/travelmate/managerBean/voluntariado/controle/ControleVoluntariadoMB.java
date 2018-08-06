package br.com.travelmate.managerBean.voluntariado.controle;

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

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;

import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleVoluntariadoMB implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controlevoluntariado controlevoluntariado;
	private List<Controlevoluntariado> listaControle;
	private String nomeCliente;
	private int idVenda;
	private Date iniDataEmbarque;
	private Date finalDataEmbarque;
	private String motivoCancelamento;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String situacao;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private String voltar;
	
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

	public Controlevoluntariado getControlevoluntariado() {
		return controlevoluntariado;
	}


	public void setControlevoluntariado(Controlevoluntariado controlevoluntariado) {
		this.controlevoluntariado = controlevoluntariado;
	}



	public List<Controlevoluntariado> getListaControle() {
		return listaControle;
	}


	public void setListaControle(List<Controlevoluntariado> listaControle) {
		this.listaControle = listaControle;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
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


	public void listarControle() {
		VoluntariadoFacade voluntariadoFacade =new VoluntariadoFacade();
		String sql;
		String data = Formatacao.SubtarirDatas(new Date(), 365, "yyyy/MM/dd");
		sql= "select c from Controlevoluntariado c where c.statusprocesso<>'Finalizado' and c.statusprocesso<>'Cancelado' and c.vendas.dataVenda>='" + data + "' order by c.vendas.dataVenda desc";
		listaControle = voluntariadoFacade.listaControle(sql);
		if (listaControle==null){
			listaControle = new ArrayList<Controlevoluntariado>();
        }
	}
	
	public String invoice(Controlevoluntariado controlevoluntariado){
		if (controlevoluntariado!=null){
			InvoiceFacade invoiceFacade = new InvoiceFacade();
			Invoice invoice = invoiceFacade.consultarVenda(controlevoluntariado.getVendas().getIdvendas(), controlevoluntariado.getVendas().getProdutos().getIdprodutos(), controlevoluntariado.getIdcontrolevoluntariado());
			if (invoice==null){
				invoice = new Invoice();
				invoice.setControle(controlevoluntariado.getIdcontrolevoluntariado());
				invoice.setProdutos(controlevoluntariado.getVendas().getProdutos());
				invoice.setVendas(controlevoluntariado.getVendas());
			}
			FacesContext fc = FacesContext.getCurrentInstance();
		    HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		    session.setAttribute("invoice", invoice);
			Map<String,Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 600);
			RequestContext.getCurrentInstance().openDialog("invoiceControle", options, null);
		}
    	return "";
    }
	
	public void pesquisar(){
		VoluntariadoFacade controleFacade =new VoluntariadoFacade();
		String sql;
		sql= "select c from Controlevoluntariado c where c.vendas.cliente.nome like '%" + nomeCliente + "%' and c.vendas.situacao<>'CANCELADA' ";
		if (idVenda>0){ 
			sql = sql + " and  c.vendas.idvendas=" + idVenda;
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
		sql=sql+ " order by c.idcontrolevoluntariado";
		listaControle = controleFacade.listaControle(sql);
		if (listaControle==null){
			listaControle = new ArrayList<Controlevoluntariado>();
        }
	}
	
	public void limpar(){
		iniDataEmbarque = null;
		finalDataEmbarque = null;
		nomeCliente = "";
		idVenda=0;
		unidadenegocio=null;
		usuario=null;
		situacao="TODOS";
		listarControle();
	}
	
	public void cancelar(){
		VoluntariadoFacade controleFacade =new VoluntariadoFacade();
		for (int i = 0; i < listaControle.size(); i++) {
			if(listaControle.get(i).isSelecionado()){
				listaControle.get(i).setStatusprocesso("Cancelado");
				controleFacade.salvar(listaControle.get(i));
				Vendas vendas= new Vendas();
				vendas = listaControle.get(i).getVendas();
				vendas.setSituacao("CANCELADA");
				vendas.setObsCancelar(motivoCancelamento);
				vendas.setDatacancelamento(new Date());
				vendas.setUsuariocancelamento(usuarioLogadoMB.getUsuario().getIdusuario());
				
				vendas = vendasDao.salvar(vendas);
			}
		}
		listarControle();
	}
	
	public void finalizar(Controlevoluntariado voluntariado){
		VoluntariadoFacade voluntariadoFacade =new VoluntariadoFacade();
		voluntariado.setStatusprocesso("Finalizado");
		voluntariadoFacade.salvar(voluntariado);
		listarControle();
	}
	
	public String documentacao(Controlevoluntariado controlevoluntariado){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", controlevoluntariado.getVendas());
        voltar = "controleVoluntariado";
        session.setAttribute("voltar", voltar);
		return "consArquivos";
    }
	
	public String atualizarControle(Controlevoluntariado controlevoluntariado){
		if (controlevoluntariado != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controlevoluntariado);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleVoluntariado", options, null);
		}
		return "";
	}
	
	
	public String imagemDocs(Controlevoluntariado controle){ 
		if(controle.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")){
			return "../../resources/img/bolaVerde.png";
		}else return "../../resources/img/bolaVermelha.png";
	}
	 
	public void retornoDialogAtualizar(SelectEvent event) {
		controlevoluntariado = (Controlevoluntariado) event.getObject();
		imagemDocs(controlevoluntariado);
	}
	
	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
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
	
	
	public String imagemSituacao(Controlevoluntariado controle){
		if(controle.getStatusprocesso().equalsIgnoreCase("Processo")){
			return "../../resources/img/bolaVerde.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Cancelado")){
			return "../../resources/img/bolaVermelha.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Finalizado")){
			return "../../resources/img/bolaPreta.png";
		}else{
			return "";
		}
	}
	
	public void salvarStatus(Controlevoluntariado controle){
		VoluntariadoFacade voluntariadoFacade =new VoluntariadoFacade();
		controle = voluntariadoFacade.salvar(controle);
	}
	
}
