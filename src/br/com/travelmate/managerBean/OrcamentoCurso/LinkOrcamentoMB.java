package br.com.travelmate.managerBean.OrcamentoCurso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Produtosorcamento;

@Named
@ViewScoped
public class LinkOrcamentoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String texto;
	
	@PostConstruct
    public void init(){ 
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        texto =  (String) session.getAttribute("texto");
        session.removeAttribute("texto"); 
	}
	  
	public String getTexto() {
		return texto;
	}
 
	public void setTexto(String texto) {
		this.texto = texto;
	}  
}
