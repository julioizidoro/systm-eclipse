/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.produto;

import br.com.travelmate.facade.NotificacaoEmailFacade;
import br.com.travelmate.model.Notificacaoemail;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamila Rodrigues
 */
@Named
@ViewScoped
public class CadUsuarioMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produtos produtos;
    private List<Usuario> listaUsuarioEmail;
    private Notificacaoemail notificacaoemail;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        produtos = (Produtos) session.getAttribute("produtos");
        session.removeAttribute("produtos");
        String sqlUsuario = "Select u from Usuario u order by u.nome";
        listaUsuarioEmail = GerarListas.listarUsuarios(sqlUsuario);
    }

    public List<Usuario> getListaUsuarioEmail() {
        return listaUsuarioEmail;
    }

    public void setListaUsuarioEmail(List<Usuario> listaUsuarioEmail) {
        this.listaUsuarioEmail = listaUsuarioEmail;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }
    
    public String salvarUsuario(){
        for(int i=0;i<listaUsuarioEmail.size();i++){
            if(listaUsuarioEmail.get(i).isSelecionado()){
                notificacaoemail = new Notificacaoemail();
                notificacaoemail.setUsuario(listaUsuarioEmail.get(i));
                notificacaoemail.setProdutos(produtos);
                NotificacaoEmailFacade notificacaoEmailFacade = new NotificacaoEmailFacade();
                notificacaoEmailFacade.salvar(notificacaoemail);
            }
        }
        RequestContext.getCurrentInstance().closeDialog(notificacaoemail);
        return "";
    }
    
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    
}
