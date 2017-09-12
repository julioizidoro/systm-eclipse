package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.TiSolicitacoesFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadSolicitacoesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Tisolicitacoes tisolicitacoes;
	
	
	
	@PostConstruct
	public void init(){
		tisolicitacoes = new Tisolicitacoes();
	}



	public Tisolicitacoes getTisolicitacoes() {
		return tisolicitacoes;
	}



	public void setTisolicitacoes(Tisolicitacoes tisolicitacoes) {
		this.tisolicitacoes = tisolicitacoes;
	}
	
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Tisolicitacoes());
	}
	
	
	
	public boolean validarDados(){
		if (tisolicitacoes.getDescricao() == null || tisolicitacoes.getDescricao().equalsIgnoreCase("")) {
			Mensagem.lancarMensagemInfo("Informe uma descrição", "");
			return false;
		}
		
		if (tisolicitacoes.getDataprevisao() == null) {
			Mensagem.lancarMensagemInfo("Informe uma data de previsão", "");
			return false;
		}
		return true;
	}
	
	
	
	public void salvar(){
		TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
		if (validarDados()) {
			tisolicitacoes.setSituacao("Nova");
			tisolicitacoes.setDepartamento(usuarioLogadoMB.getUsuario().getDepartamento());
			tisolicitacoes.setConcluido(false);
			tisolicitacoes.setLiberar(false);
			tisolicitacoes.setDatasolicitacao(new Date());
			tisolicitacoes = tiSolicitacoesFacade.salvar(tisolicitacoes);
			RequestContext.getCurrentInstance().closeDialog(tisolicitacoes);
		}
	}
	
	

}
