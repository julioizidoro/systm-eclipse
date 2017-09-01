package br.com.travelmate.managerBean.financeiro.terceiros;

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

import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.model.Terceiros;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class TerceirosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Terceiros> listaTerceiros;
	
	@PostConstruct
	public void init(){
		gerarListaTerceiros();
	}
	
	public List<Terceiros> getListaTerceiros() {
		return listaTerceiros;
	}
	public void setListaTerceiros(List<Terceiros> listaTerceiros) {
		this.listaTerceiros = listaTerceiros;
	}
	
	public void gerarListaTerceiros(){
		TerceirosFacade terceirosFacade = new TerceirosFacade();
		listaTerceiros = terceirosFacade.lista();
		if (listaTerceiros==null){
			listaTerceiros = new ArrayList<Terceiros>();
		}
	}
	
	public void retornoDialog(){
		gerarListaTerceiros();
	}

	public String novo(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",300);
        RequestContext.getCurrentInstance().openDialog("cadTerceiros", options, null);
        return "";
    }
	
	public String editar(Terceiros terceiros){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("terceiros", terceiros);
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",300);
        RequestContext.getCurrentInstance().openDialog("cadTerceiros", options, null);
        return "";
	}
	
	
	public String excluir(Terceiros terceiros){
        TerceirosFacade terceirosFacade = new TerceirosFacade();
        terceirosFacade.excluir(terceiros.getIdterceiros());
        gerarListaTerceiros();
        Mensagem.lancarMensagemInfo("Excluido com Sucesso", "");
        return "";
     }
}
