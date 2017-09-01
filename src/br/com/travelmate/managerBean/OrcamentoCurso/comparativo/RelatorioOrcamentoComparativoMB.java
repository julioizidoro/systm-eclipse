package br.com.travelmate.managerBean.OrcamentoCurso.comparativo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession; 

@Named
@ViewScoped
public class RelatorioOrcamentoComparativoMB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OrcamentoComparativoBean> listaOrcamento;
	private boolean doisorcamentos;
	private boolean tresorcamentos;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        listaOrcamento = (List<OrcamentoComparativoBean>) session.getAttribute("listaOrcamento");
        session.removeAttribute("listaOrcamento");  
        if(listaOrcamento.size()==2){
        	doisorcamentos=true;
        	tresorcamentos=false;
        }else if(listaOrcamento.size()==3){
        	tresorcamentos=true;
        	doisorcamentos=false;
        }
	}

	public List<OrcamentoComparativoBean> getListaOrcamento() {
		return listaOrcamento;
	}

	public void setListaOrcamento(List<OrcamentoComparativoBean> listaOrcamento) {
		this.listaOrcamento = listaOrcamento;
	}

	public boolean isDoisorcamentos() {
		return doisorcamentos;
	}

	public void setDoisorcamentos(boolean doisorcamentos) {
		this.doisorcamentos = doisorcamentos;
	}

	public boolean isTresorcamentos() {
		return tresorcamentos;
	}

	public void setTresorcamentos(boolean tresorcamentos) {
		this.tresorcamentos = tresorcamentos;
	}

	 
}
