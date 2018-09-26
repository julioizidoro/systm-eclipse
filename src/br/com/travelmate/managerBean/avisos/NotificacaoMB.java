package br.com.travelmate.managerBean.avisos;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.dao.VendasDao;


import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class NotificacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AvisosDao avisosDao;
	private List<Avisousuario> listaAvisos;
	private String tipo;
	private String tipo2;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private VendasDao vendasDao;
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        tipo = (String) session.getAttribute("tipo");
	        tipo2 = (String) session.getAttribute("tipo2");
	        session.removeAttribute("tipo2");
	        session.removeAttribute("tipo");
			gerarListaNotificacao();
		}
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public List<Avisousuario> getListaAvisos() {
		return listaAvisos;
	}

	public void setListaAvisos(List<Avisousuario> listaAvisos) {
		this.listaAvisos = listaAvisos;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public String getTipo2() {
		return tipo2;
	}

	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void gerarListaNotificacao(){
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		String sql = "Select a from Avisousuario a where a.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario() +
					 "  and (a.avisos.imagem='" + tipo + "'";
		if(tipo2!=null && tipo2.length()>2) {
			sql = sql +  " or a.avisos.imagem='" + tipo2 + "'";
		}
		sql = sql + ") and a.visto=false "+  " and a.avisos.data>='" + dataConsulta 
				  + "' and a.avisos.liberar=1  order by a.avisos.data desc";
		listaAvisos= avisosDao.listarAvisoUsuario(sql);
		if (listaAvisos==null){
			listaAvisos= new ArrayList<Avisousuario>();
		}
	}  
	
	public String carregarImagem(Avisousuario avisousuario){
		if(avisousuario.getAvisos().getImagem().equalsIgnoreCase("promocao")){
			return "../../resources/img/promocaoAviso.png";
		}else if(avisousuario.getAvisos().getImagem().equalsIgnoreCase("atencao")){
			return "../../resources/img/atencaoAviso.png";
		}else if(avisousuario.getAvisos().getImagem().equalsIgnoreCase("Upload")){
			return "../../resources/img/uploadAviso.png";
		}else if(avisousuario.getAvisos().getImagem().equalsIgnoreCase("lead")){
			return "../../resources/img/crm/novosClick.png";
		}else{
			return "../../resources/img/notificacaoAviso.png";
		}
	}
	
	public String consultarTitle(Avisousuario avisousuario){
		if(avisousuario.getAvisos().getImagem().equalsIgnoreCase("Upload")){
			return "Consultar Arquivos";
		}else if(avisousuario.getAvisos().getImagem().equalsIgnoreCase("lead")){
			return "Ir para distribuição de leads";
		}else{
			return "";
		}
	}
	
	public String carregarTitulo(){
		if(tipo.equalsIgnoreCase("promocao")){
			return "Promoções";
		}else if(tipo.equalsIgnoreCase("aviso")){
			return "Avisos";
		}else if(tipo.equalsIgnoreCase("Upload")){
			return "Upload de Arquivos";
		}else{
			return "Avisos";
		}
	}
	
	public String limparNotificacao(Avisousuario avisousuario){
		avisousuario.setVisto(true);
		avisosDao.salvar(avisousuario);
		listaAvisos.remove(avisousuario);
		return "";
	}
	
	public String limparTodasNotificacoes(){
		if (listaAvisos!=null){
			for(int i=0;i<listaAvisos.size();i++){
				Avisousuario aviso = listaAvisos.get(i);
				aviso.setVisto(true);
				avisosDao.salvar(aviso);
			}
			listaAvisos = new ArrayList<Avisousuario>();
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String fechar(){
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return "";
	}
	
	public String feedNoticia(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("feed", "Sim");
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return "";
	}
	
	public String consultarArquivos(Avisousuario avisousuario){
		if (avisousuario.getAvisos().getIdvenda()>0){
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        Vendas vendas = vendasDao.consultarVendas(avisousuario.getAvisos().getIdvenda());
	        if (vendas!=null){
	        	if (vendas.getProdutos().getIdprodutos()==22) {
	        		session.setAttribute("cliente", vendas.getCliente());
	        	}
	        	session.setAttribute("vendas", vendas);
				String voltar = "abrir";
				session.setAttribute("voltar", voltar);
				session.setAttribute("redirecionar", "sim");
				RequestContext.getCurrentInstance().closeDialog("arquivo"); 
	        }
		}else if (avisousuario.getAvisos().getImagem().equalsIgnoreCase("lead")){
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
			session.setAttribute("redirecionar", "simLead");
			RequestContext.getCurrentInstance().closeDialog("lead");  
		}
		return "";
	}
	
	public String consultarLead(Avisousuario avisousuario){
		
		return "";
	} 
	
	public String retornarTextoLink(String tipo){
		if (!tipo.equalsIgnoreCase("Upload") && !tipo.equalsIgnoreCase("lead")) {
			return "text-decoration:none;";
		}
		return "";
	}
}
