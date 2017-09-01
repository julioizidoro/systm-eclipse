package br.com.travelmate.managerBean.avisos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.facade.NotificacaoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Notificacao;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class NotificacaoVendasMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Notificacao> listaNotificacoes;
	private String tipo;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			gerarListaNotificacao();
		}
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public List<Notificacao> getListaNotificacoes() {
		return listaNotificacoes;
	}

	public void setListaNotificacoes(List<Notificacao> listaNotificacoes) {
		this.listaNotificacoes = listaNotificacoes;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void gerarListaNotificacao(){
		NotificacaoFacade notificaocaFacade = new NotificacaoFacade();
		String sql="";
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		sql="Select n from Notificacao n where n.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario() +" and n.limpar=false "
				+ " and n.data>='" + dataConsulta + "' order by n.data DESC, n.idnotificacao DESC";
		listaNotificacoes = notificaocaFacade.listar(sql);
		if (listaNotificacoes==null){
			listaNotificacoes = new ArrayList<Notificacao>();
		}
	}  
	
	public void limpar(Notificacao notificacao){
		notificacao.setLimpar(true);
		NotificacaoFacade notificacaoFacade = new NotificacaoFacade();
		notificacao = notificacaoFacade.salvar(notificacao);
		listaNotificacoes.remove(notificacao);
	}
	
	public String limparTodasNotificacoes(){
		if (listaNotificacoes!=null){
			for(int i=0;i<listaNotificacoes.size();i++){
				Notificacao notificacao = listaNotificacoes.get(i);
				notificacao.setLimpar(true);
				NotificacaoFacade notificacaoFacade = new NotificacaoFacade();
				notificacao = notificacaoFacade.salvar(notificacao); 
			}
			listaNotificacoes = new ArrayList<Notificacao>();
		}
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String fechar(){
		 RequestContext.getCurrentInstance().closeDialog(null);
		 return "";
	}
	
	public String imagem(Notificacao notificacao){
		return "../../resources/img/"+notificacao.getImagem()+".png";
	}
	
	public String mostrarData(Notificacao notificacao){
		if(notificacao.getImagem().equals("cancelado")){
			return "false";
		}
		return "true";
	}
}
