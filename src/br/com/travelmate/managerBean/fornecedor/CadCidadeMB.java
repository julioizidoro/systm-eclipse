package br.com.travelmate.managerBean.fornecedor;

import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Pais;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadCidadeMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pais pais;
    private Cidade cidade;
    
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        pais = (Pais) session.getAttribute("pais");
        session.removeAttribute("pais");
        cidade = new Cidade();
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
    
    
    public String salvar(){
        cidade.setPais(pais);
        CidadeFacade cidadeFacade = new CidadeFacade();
        cidade = cidadeFacade.salvar(cidade);
        FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Cidade cadastrada.");
        FacesContext.getCurrentInstance().addMessage(null, mensagem);
        RequestContext.getCurrentInstance().closeDialog(cidade);
        return "";
    }
    
    public String cancelarCadastro(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
}
