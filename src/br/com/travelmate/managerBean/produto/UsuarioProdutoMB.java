package br.com.travelmate.managerBean.produto;

import br.com.travelmate.facade.NotificacaoEmailFacade;
import br.com.travelmate.model.Notificacaoemail;
import br.com.travelmate.model.Produtos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class UsuarioProdutoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produtos produtos;
    private List<Notificacaoemail> listaNotificacaoEmail;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        produtos = (Produtos) session.getAttribute("produtos");
        session.removeAttribute("produtos");
        gerarListaNotificacaoEmail();
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public List<Notificacaoemail> getListaNotificacaoEmail() {
        return listaNotificacaoEmail;
    }

    public void setListaNotificacaoEmail(List<Notificacaoemail> listaNotificacaoEmail) {
        this.listaNotificacaoEmail = listaNotificacaoEmail;
    }
    
    public void gerarListaNotificacaoEmail(){
        NotificacaoEmailFacade notificacaoEmailFacade= new NotificacaoEmailFacade();
        listaNotificacaoEmail = notificacaoEmailFacade.listarPorProduto(produtos.getIdprodutos());
        if(listaNotificacaoEmail==null){
            listaNotificacaoEmail = new ArrayList<Notificacaoemail>();
        }
    }
    
    
    public String adicionarUsuarios(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("produtos", produtos);       
        RequestContext.getCurrentInstance().openDialog("cadAdicionarUsuarios");
        return "";
    }
    
    public void retornoDialogNovo(SelectEvent event) {
        Notificacaoemail notificacaoemail = (Notificacaoemail) event.getObject();
        listaNotificacaoEmail.add(notificacaoemail);
    }
    
    public String excluir(Notificacaoemail notificacaoemail) {
        NotificacaoEmailFacade notificacaoEmailFacade= new NotificacaoEmailFacade();
        notificacaoEmailFacade.excluir(notificacaoemail.getIdnotificacaoEmail());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
        gerarListaNotificacaoEmail();
        return "";
    }
}
