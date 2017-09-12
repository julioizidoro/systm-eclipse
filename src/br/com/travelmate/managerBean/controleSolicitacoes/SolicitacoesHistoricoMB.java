package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.TiSolicitacoesHistoricoFacade;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.model.Tisolicitacoeshistorico;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class SolicitacoesHistoricoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Tisolicitacoes tisolicitacoes;
	private List<Tisolicitacoes> listaSolicitacoes;
	private boolean desabilitarCadastro = false;
	
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tisolicitacoes = (Tisolicitacoes) session.getAttribute("tisolicitacoes");
		listaSolicitacoes = (List<Tisolicitacoes>) session.getAttribute("listaSolicitacoes");
		session.removeAttribute("tisolicitacoes");
		session.removeAttribute("listaSolicitacoes");
		if (tisolicitacoes.getConcluido().booleanValue()) {
			desabilitarCadastro = true;
		}
	}




	public Tisolicitacoes getTisolicitacoes() {
		return tisolicitacoes;
	}




	public void setTisolicitacoes(Tisolicitacoes tisolicitacoes) {
		this.tisolicitacoes = tisolicitacoes;
	}
	
	
	
	
	public List<Tisolicitacoes> getListaSolicitacoes() {
		return listaSolicitacoes;
	}




	public void setListaSolicitacoes(List<Tisolicitacoes> listaSolicitacoes) {
		this.listaSolicitacoes = listaSolicitacoes;
	}




	public boolean isDesabilitarCadastro() {
		return desabilitarCadastro;
	}




	public void setDesabilitarCadastro(boolean desabilitarCadastro) {
		this.desabilitarCadastro = desabilitarCadastro;
	}




	public String voltar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaSolicitacoes", listaSolicitacoes);
		return "consSolicitacoes";
	}
	
	
	public String retornarCorHistorico(int idHistorico) {
		if (idHistorico % 2 == 0) {
			return "dataScrollerBranco";
		} else
			return "dataScrollerCinza";
	}
	
	
	public void cadastrarHistorico(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tisolicitacoes", tisolicitacoes);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("cadSolicitacoesHistorico", options, null);
	}
	
	public void editarHistorico(Tisolicitacoeshistorico tisolicitacoeshistorico){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tisolicitacoeshistorico", tisolicitacoeshistorico);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("cadSolicitacoesHistorico", options, null);
	}
	
	
	public void retonarDialogHistorico(SelectEvent event){
		Tisolicitacoeshistorico tisolicitacoeshistorico = (Tisolicitacoeshistorico) event.getObject();
		if (tisolicitacoeshistorico.getIdtisolicitacoeshistorico() != null) {
			if (tisolicitacoes.getTisolicitacoeshistoricoList() == null) {
				tisolicitacoes.setTisolicitacoeshistoricoList(new ArrayList<Tisolicitacoeshistorico>());
			}
			tisolicitacoes.getTisolicitacoeshistoricoList().add(tisolicitacoeshistorico);
		}
	}
	
	
	public void excluirHistorico(Tisolicitacoeshistorico tisolicitacoeshistorico){
		TiSolicitacoesHistoricoFacade tiSolicitacoesHistoricoFacade = new TiSolicitacoesHistoricoFacade();
		tisolicitacoes.getTisolicitacoeshistoricoList().remove(tisolicitacoeshistorico);
		tiSolicitacoesHistoricoFacade.excluir(tisolicitacoeshistorico.getIdtisolicitacoeshistorico());
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
	}
	
	
   
}
