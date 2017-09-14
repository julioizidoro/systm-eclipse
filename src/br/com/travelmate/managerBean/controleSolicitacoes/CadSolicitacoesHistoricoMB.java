package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable;
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
import br.com.travelmate.facade.TiSolicitacoesHistoricoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.model.Tisolicitacoeshistorico;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadSolicitacoesHistoricoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Tisolicitacoes tisolicitacoes;
	private Tisolicitacoeshistorico tisolicitacoeshistorico;
	
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tisolicitacoes = (Tisolicitacoes) session.getAttribute("tisolicitacoes");
		tisolicitacoeshistorico = (Tisolicitacoeshistorico) session.getAttribute("tisolicitacoeshistorico");
		session.removeAttribute("tisolicitacoeshistorico");
		session.removeAttribute("tisolicitacoes");
		if (tisolicitacoeshistorico == null) {
			tisolicitacoeshistorico = new Tisolicitacoeshistorico();
		}else {
			tisolicitacoes = tisolicitacoeshistorico.getTisolicitacoes();
		}
	}




	public Tisolicitacoes getTisolicitacoes() {
		return tisolicitacoes;
	}




	public void setTisolicitacoes(Tisolicitacoes tisolicitacoes) {
		this.tisolicitacoes = tisolicitacoes;
	}




	public Tisolicitacoeshistorico getTisolicitacoeshistorico() {
		return tisolicitacoeshistorico;
	}




	public void setTisolicitacoeshistorico(Tisolicitacoeshistorico tisolicitacoeshistorico) {
		this.tisolicitacoeshistorico = tisolicitacoeshistorico;
	}
	
	
	
	public void salvarHistorico(){
		TiSolicitacoesHistoricoFacade tiSolicitacoesHistoricoFacade = new TiSolicitacoesHistoricoFacade();
		if (validarDados()) {
			tisolicitacoeshistorico.setData(new Date());
			tisolicitacoeshistorico.setTisolicitacoes(tisolicitacoes);
			tisolicitacoeshistorico.setUsuario(usuarioLogadoMB.getUsuario());
			if (tisolicitacoeshistorico.getIdtisolicitacoeshistorico() == null) {
				tisolicitacoeshistorico = tiSolicitacoesHistoricoFacade.salvar(tisolicitacoeshistorico);
				RequestContext.getCurrentInstance().closeDialog(tisolicitacoeshistorico);
			}else{
				tiSolicitacoesHistoricoFacade.salvar(tisolicitacoeshistorico);
				RequestContext.getCurrentInstance().closeDialog(new Tisolicitacoeshistorico());
			}
			notificarNovaSolicitacao();
		}
		
	}
	
	
	public boolean validarDados(){
		if (tisolicitacoeshistorico.getTipo() == null || tisolicitacoeshistorico.getTipo().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe o tipo", "");
			return false;
		}
		
		if (tisolicitacoeshistorico.getDescricao() == null || tisolicitacoeshistorico.getDescricao().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe a descrição", "");
			return false;
		}
		return true;
	}
	
	
	public void notificarNovaSolicitacao(){
		AvisosFacade avisosFacade = new AvisosFacade();
		Avisos avisos = new Avisos();
		Avisousuario avisousuario = new Avisousuario();
		avisos.setData(new Date());
		avisos.setDepartamento(usuarioLogadoMB.getUsuario().getDepartamento().getNome());
		avisos.setUsuario(usuarioLogadoMB.getUsuario());
		avisos.setIdunidade(usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio());
		avisos.setImagem("aviso");
		avisos.setLiberar(true);
		avisos.setIdvenda(0);
		avisos.setTexto("Nova histórico inserido na solicitação de " + usuarioLogadoMB.getUsuario().getNome() + " da unidade " + usuarioLogadoMB.getUsuario().getUnidadenegocio().getNomerelatorio());
		avisos = avisosFacade.salvar(avisos);
		List<Usuario> listaUsuario = GerarListas.listarUsuarios("SELECT u FROM Usuario u WHERE (u.idusuario=1 or u.idusuario=125 or u.idusuario=134)");
		for (int i = 0; i < listaUsuario.size(); i++) {
			avisousuario = new Avisousuario();
			avisousuario.setAvisos(avisos);
			avisousuario.setUsuario(listaUsuario.get(i));
			avisousuario.setVisto(false);
			avisosFacade.salvar(avisousuario);
		}
	}
	
	
	

}
