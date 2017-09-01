package br.com.travelmate.managerBean.financeiro.alteracao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.AlteracaofinanceiroparcelasFacade;
import br.com.travelmate.model.Alteracaofinanceiro;
import br.com.travelmate.model.Alteracaofinanceiroparcelas;

@Named
@ViewScoped
public class AlteracaoFinanceiroParcelasMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private List<Alteracaofinanceiroparcelas> listaOriginal;
    private List<Alteracaofinanceiroparcelas> listaNovo;
    private Alteracaofinanceiro alteracaofinanceiro;
    
    @PostConstruct()
    public void init(){
    	FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		alteracaofinanceiro = (Alteracaofinanceiro) session.getAttribute("alteracaofinanceiro");
		session.removeAttribute("alteracaofinanceiro");
    	gerarListas();
    }
	
	
	public List<Alteracaofinanceiroparcelas> getListaOriginal() {
		return listaOriginal;
	}


	public void setListaOriginal(List<Alteracaofinanceiroparcelas> listaOriginal) {
		this.listaOriginal = listaOriginal;
	}


	public List<Alteracaofinanceiroparcelas> getListaNovo() {
		return listaNovo;
	}

	public void setListaNovo(List<Alteracaofinanceiroparcelas> listaNovo) {
		this.listaNovo = listaNovo;
	}

	public Alteracaofinanceiro getAlteracaofinanceiro() {
		return alteracaofinanceiro;
	}

	public void setAlteracaofinanceiro(Alteracaofinanceiro alteracaofinanceiro) {
		this.alteracaofinanceiro = alteracaofinanceiro;
	}

	
	public void gerarListas(){
		AlteracaofinanceiroparcelasFacade alteracaoFacade = new AlteracaofinanceiroparcelasFacade();
		String sql = "select a from Alteracaofinanceiroparcelas a where a.alteracaofinanceiro.idalteracaofinanceiro="+alteracaofinanceiro.getIdalteracaofinanceiro()+
				" and a.tipo='O'";
		listaOriginal = alteracaoFacade.listar(sql);
		if(listaOriginal==null){
			listaOriginal = new ArrayList<Alteracaofinanceiroparcelas>();
		}
		sql = "select a from Alteracaofinanceiroparcelas a where a.alteracaofinanceiro.idalteracaofinanceiro="
		+alteracaofinanceiro.getIdalteracaofinanceiro()+" and a.tipo='N'"; 
		listaNovo = alteracaoFacade.listar(sql);
		if(listaNovo==null){
			listaNovo = new ArrayList<Alteracaofinanceiroparcelas>();
		}
	}
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
}
