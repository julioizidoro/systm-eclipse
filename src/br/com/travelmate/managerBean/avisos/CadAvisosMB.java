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
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Usuario;

@Named
@ViewScoped
public class CadAvisosMB implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;@Inject
	private AvisosDao avisosDao;
	private Avisos aviso;
	
	@PostConstruct 
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        aviso = (Avisos) session.getAttribute("aviso");
        session.removeAttribute("aviso");
        if (aviso==null){
        	aviso = new Avisos();
        	aviso.setUsuario(usuarioLogadoMB.getUsuario());
        	aviso.setData(new Date());
        }
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Avisos getAviso() {
		return aviso;
	}

	public void setAviso(Avisos aviso) {
		this.aviso = aviso;
	}
	
	public String salvar(){
		aviso.setIdunidade(0);
		aviso.setLiberar(true);
		aviso.setAvisousuarioList(salvarAvisoUsuario());
		aviso = avisosDao.salvar(aviso);
		RequestContext.getCurrentInstance().closeDialog(aviso);
		return "";
	}
	
	public String cancelar(){ 
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public List<Avisousuario> salvarAvisoUsuario(){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String sql = "select u from Usuario u where u.situacao='Ativo'";
		List<Usuario> listaUsuario = usuarioFacade.listar(sql);
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		if (listaUsuario!=null){
			for(int i=0;i<listaUsuario.size();i++){
				Avisousuario avisousuario = new Avisousuario();
				avisousuario.setAvisos(aviso);
				avisousuario.setUsuario(listaUsuario.get(i));
				avisousuario.setVisto(false);
				lista.add(avisousuario);
			}
		}
		return lista;
	}
	

}
