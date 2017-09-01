package br.com.travelmate.managerBean.financeiro.planoContas;

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

import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.model.Planoconta;


@Named
@ViewScoped
public class PlanoContasMB implements Serializable{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Planoconta> listaPlanoContas;
	private String descricao;
	
	@PostConstruct
	public void init(){
		gerarListaPlanoContas();
	}
	
	
	public List<Planoconta> getListaPlanoContas() {
		return listaPlanoContas;
	}



	public void setListaPlanoContas(List<Planoconta> listaPlanoContas) {
		this.listaPlanoContas = listaPlanoContas;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public String novo(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",400);
        RequestContext.getCurrentInstance().openDialog("planoconta", options, null);
        return "";
    }
	
	public void gerarListaPlanoContas(){
		if (descricao==null){
			descricao="";
		}
		PlanoContaFacade planoContaFacade = new PlanoContaFacade();
		listaPlanoContas = planoContaFacade.listar(descricao);
		if (listaPlanoContas==null){
			listaPlanoContas = new ArrayList<Planoconta>();
		}
	}
	
	public String pesquisar(){
		gerarListaPlanoContas();
		return "";
	}
	
	public String cancelar(){
		descricao="";
		gerarListaPlanoContas();
		return "";
	}
	
	public String editar(Planoconta planoconta){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("planoconta", planoconta);
		Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",400);
        RequestContext.getCurrentInstance().openDialog("planoconta", options, null);
        return "";
	}
	
	public void retornoDialog(){
		gerarListaPlanoContas();
	}
}
