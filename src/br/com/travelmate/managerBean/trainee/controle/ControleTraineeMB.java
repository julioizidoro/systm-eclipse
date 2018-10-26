package br.com.travelmate.managerBean.trainee.controle;

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
import br.com.travelmate.facade.TraineeFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;

import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controlehighschool;
import br.com.travelmate.model.Controletrainee;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ControleTraineeMB implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Controletrainee controletrainee;
	private List<Controletrainee> listaControle;
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
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaControle = (List<Controletrainee>) session.getAttribute("listaControle");
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

	public Controletrainee getControletrainee() {
		return controletrainee;
	}

	public void setControletrainee(Controletrainee controletrainee) {
		this.controletrainee = controletrainee;
	}

	public List<Controletrainee> getListaControle() {
		return listaControle;
	}

	public void setListaControle(List<Controletrainee> listaControle) {
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
		TraineeFacade controleFacade =new TraineeFacade();
		String sql;
		String data = Formatacao.SubtarirDatas(new Date(), 365, "yyyy/MM/dd");
		sql= "select c from Controletrainee c where c.statusprocesso<>'Finalizado' and c.statusprocesso<>'Cancelado' and c.vendas.dataVenda>='" + data + "' order by c.vendas.dataVenda desc";
		listaControle = controleFacade.listaControle(sql);
		if (listaControle==null){
			listaControle = new ArrayList<Controletrainee>();
        }
	}
	


	public String invoice(Controletrainee controle) {
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			controle.getVendas().setIdControle(controle.getIdcontroletrainee());
			session.setAttribute("vendas", controle.getVendas());
			session.setAttribute("voltar", "controleTrainee");
			return "consultaInvoice";
		}
		return "";
	}
	
	public void pesquisar(){
		TraineeFacade controleFacade =new TraineeFacade();
		String sql;
		sql= "select c from Controletrainee c where c.vendas.cliente.nome like '%" + nomeCliente + "%' ";
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
		sql=sql+ " order by c.idcontroletrainee";
		listaControle = controleFacade.listaControle(sql);
		if (listaControle==null){
			listaControle = new ArrayList<Controletrainee>();
        }
	}
	
	public void limpar(){
		iniDataEmbarque=null;
		finalDataEmbarque=null;
		nomeCliente="";
		listarControle();
	}
	
	public void cancelar(){
		TraineeFacade controleFacade =new TraineeFacade();
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
	
	public void finalizar(Controletrainee controletrainee){
		TraineeFacade controleFacade =new TraineeFacade();
		controletrainee.setStatusprocesso("Finalizado");
		controleFacade.salvar(controletrainee);
		listarControle();
	}
	
	public String documentacao(Controletrainee controletrainee){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("vendas", controletrainee.getVendas());
        voltar = "controleTrainee";
        session.setAttribute("voltar", voltar);
        session.setAttribute("listaControle", listaControle);
		return "consArquivos";
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
	
	
	public String imagemDocs(Controletrainee controle){ 
		if(controle.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")){
			return "../../resources/img/bolaVerde.png";
		}else return "../../resources/img/bolaVermelha.png";
	} 
	
	public void retornoDialogAtualizar(SelectEvent event) {
		controletrainee = (Controletrainee) event.getObject(); 
	}
	
	public String atualizarControle(Controletrainee controle){
		if (controle != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("controle", controle);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleTrainee", options, null);
		}
		return "";
	}
	
	public String imagemSituacao(Controletrainee controle){
		if(controle.getStatusprocesso().equalsIgnoreCase("Processo")){
			return "../../resources/img/bolaVerde.png";
		}else if(controle.getStatusprocesso().equalsIgnoreCase("Empregado")){
			return "../../resources/img/bolaAzul.png";
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
	
	public void salvarStatus(Controletrainee controle){
		TraineeFacade traineeFacade =new TraineeFacade();
		controle = traineeFacade.salvar(controle);
	}
}
