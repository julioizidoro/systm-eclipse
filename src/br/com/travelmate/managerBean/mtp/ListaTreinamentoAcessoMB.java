package br.com.travelmate.managerBean.mtp;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.facade.TreinamentoAcessoFacade; 
import br.com.travelmate.model.Mtp; 
import br.com.travelmate.model.Treinamentoacesso; 

@Named
@ViewScoped
public class ListaTreinamentoAcessoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 
	private Mtp mtp; 
	private List<Treinamentoacesso> listaTreinamentoAcesso;  
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        mtp = (Mtp) session.getAttribute("mtp");
        session.removeAttribute("mtp"); 
        listarAcesso();
	}
  
	
	public Mtp getMtp() {
		return mtp;
	}


	public void setMtp(Mtp mtp) {
		this.mtp = mtp;
	}


	public List<Treinamentoacesso> getListaTreinamentoAcesso() {
		return listaTreinamentoAcesso;
	}


	public void setListaTreinamentoAcesso(List<Treinamentoacesso> listaTreinamentoAcesso) {
		this.listaTreinamentoAcesso = listaTreinamentoAcesso;
	}


	public void fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	} 
	
	public void listarAcesso(){
		TreinamentoAcessoFacade treinamentoAcessoFacade = new TreinamentoAcessoFacade();
		listaTreinamentoAcesso = treinamentoAcessoFacade.listar("select t From Treinamentoacesso t where t.mtp.idmtp="+mtp.getIdmtp());
		if(listaTreinamentoAcesso==null){
			listaTreinamentoAcesso = new ArrayList<>();
		}
	}
}
