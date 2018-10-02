package br.com.travelmate.managerBean;

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

import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.facade.NotificacaoFacade;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Notificacao;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class PollMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Inject
	private AvisosDao avisosDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private DashBoardMB dashBoardMB;
	private int numeroPromocao;
	private int numeroNotificacoes;
	private int numeroAtencao;
	private int numeroUpload;
	

    @PostConstruct
    public void init() {
    	if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
    		gerarListaAviso("");
    	}
    }

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public int getNumeroPromocao() {
		return numeroPromocao;
	}

	public int getNumeroNotificacoes() {
		return numeroNotificacoes;
	}

	public int getNumeroAtencao() {
		return numeroAtencao;
	}

	public int getNumeroUpload() {
		return numeroUpload;
	}

	public DashBoardMB getDashBoardMB() {
		return dashBoardMB;
	}

	public void setDashBoardMB(DashBoardMB dashBoardMB) {
		this.dashBoardMB = dashBoardMB;
	}

	public void gerarListaAviso(String tipo) {
		List<Avisousuario> listaAvisos;
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		String sql = "Select a from Avisousuario a where a.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario() +
				 "  and a.avisos.imagem='promocao' and a.visto=false "+
				 " and a.avisos.data>='" + dataConsulta + "' and a.avisos.liberar=1  order by a.avisos.data desc";
		listaAvisos = avisosDao.listarAvisoUsuario(sql);
		numeroPromocao = listaAvisos.size();
		//
		sql = "Select a from Avisousuario a where a.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario() +
				 "  and (a.avisos.imagem='aviso' or a.avisos.imagem='lead') and a.visto=false "+
				 " and a.avisos.data>='" + dataConsulta + "' and a.avisos.liberar=1  order by a.avisos.data desc";
		listaAvisos = avisosDao.listarAvisoUsuario(sql);
		numeroAtencao = listaAvisos.size();
		//
	
		sql = "Select a from Avisousuario a where a.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario() +
		 "  and a.avisos.imagem='Upload' and a.visto=false "+
		 " and a.avisos.data>='" + dataConsulta + "' and a.avisos.liberar=1  order by a.avisos.data desc";
		listaAvisos = avisosDao.listarAvisoUsuario(sql);
		numeroUpload = listaAvisos.size();
	/*	if (tipo.equalsIgnoreCase("p")) {
			dashBoardMB.gerarDadosDashBoard();
		}
	*/	gerarListaNotificacao();
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String redirecionar = (String) session.getAttribute("redirecionar");
        session.removeAttribute("redirecionar");
        if (redirecionar!=null){
        	if (redirecionar.equalsIgnoreCase("sim")){
        		try {
					fc.getExternalContext().redirect("/pages/arquivo/consArquivos.jsf");
				} catch (IOException e) {
					  
				}
        	}else if (redirecionar.equalsIgnoreCase("simLead")){
        		try {
					fc.getExternalContext().redirect("/pages/crm/distribuicaoLeads.jsf");
				} catch (IOException e) {
					   
				}
        	} 
        }
	}

	public void gerarListaNotificacao() {
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 15, "yyyy/MM/dd");
		List<Notificacao> listaNotificacoes;
		String sql = "Select n from Notificacao n where n.usuario.idusuario="
				+ usuarioLogadoMB.getUsuario().getIdusuario() + " and n.data>='" + dataConsulta + "' and n.limpar=false order by n.titulo";
		NotificacaoFacade notificaocaFacade = new NotificacaoFacade();
		listaNotificacoes = notificaocaFacade.listar(sql);
		if (listaNotificacoes == null) {
			listaNotificacoes = new ArrayList<Notificacao>();
		}
		numeroNotificacoes = listaNotificacoes.size();
	}
	
	public void gerarPool(){
		gerarListaAviso("");
		gerarListaNotificacao();
	}
	
	public boolean habilitarNumeroAviso(){
		if(numeroAtencao>0){
			return true;
		}return false;
	}
	
	public boolean habilitarNumeroNotificacao(){
		if(numeroNotificacoes>0){
			return true;
		}return false;
	}
	
	public boolean habilitarNumeroPromocao(){
		if(numeroPromocao>0){
			return true;
		}return false;
	}

	public boolean habilitarNumeroUpload(){
		if(numeroUpload>0){
			return true;
		}return false;
	}
	
	public String paginaPrincipal() {
		gerarListaAviso("p");
		return "paginainicial";
	}

 
}
