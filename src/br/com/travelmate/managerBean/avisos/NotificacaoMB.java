package br.com.travelmate.managerBean.avisos;

import java.io.IOException;
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

import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.VendasFacade;
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
	private List<Avisousuario> listaAvisos;
	private String tipo;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        tipo = (String) session.getAttribute("tipo");
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

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void gerarListaNotificacao(){
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		AvisosFacade avisosFacade = new AvisosFacade();
		String sql = "Select a from Avisousuario a where a.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario() +
					 "  and a.avisos.imagem='" + tipo + "' and a.visto=false "+
					 " and a.avisos.data>='" + dataConsulta + "' and a.avisos.liberar=1  order by a.avisos.data desc";
		listaAvisos= avisosFacade.listarAvisoUsuario(sql);
		if (listaAvisos==null){
			listaAvisos= new ArrayList<Avisousuario>();
		}
	}  
	
	public String carregarImagem(){
		if(tipo.equalsIgnoreCase("promocao")){
			return "../../resources/img/promocaoAviso.png";
		}else if(tipo.equalsIgnoreCase("atencao")){
			return "../../resources/img/atencaoAviso.png";
		}else if(tipo.equalsIgnoreCase("Upload")){
			return "../../resources/img/uploadAviso.png";
		}else{
			return "../../resources/img/notificacaoAviso.png";
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
		AvisosFacade avisosFacade = new AvisosFacade();
		avisosFacade.salvar(avisousuario);
		listaAvisos.remove(avisousuario);
		return "";
	}
	
	public String limparTodasNotificacoes(){
		if (listaAvisos!=null){
			for(int i=0;i<listaAvisos.size();i++){
				Avisousuario aviso = listaAvisos.get(i);
				aviso.setVisto(true);
				AvisosFacade avisosFacade = new AvisosFacade();
				avisosFacade.salvar(aviso);
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
	
	public String consultarArquivos(Avisousuario avisousuario){
		if (avisousuario.getAvisos().getIdvenda()>0){
			FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        VendasFacade vendasFacade = new  VendasFacade();
	        Vendas vendas = vendasFacade.consultarVendas(avisousuario.getAvisos().getIdvenda());
	        if (vendas!=null){
	        	session.setAttribute("vendas", vendas);
				String voltar = "abrir";
				session.setAttribute("voltar", voltar);
				session.setAttribute("redirecionar", "sim");
				RequestContext.getCurrentInstance().closeDialog("arquivo"); 
	        }
		}
		return "";
	}
	
	
	public String retornarTextoLink(String tipo){
		if (!tipo.equalsIgnoreCase("Upload")) {
			return "text-decoration:none;";
		}
		return "";
	}
}
