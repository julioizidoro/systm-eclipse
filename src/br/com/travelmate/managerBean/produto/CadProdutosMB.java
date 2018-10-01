package br.com.travelmate.managerBean.produto;

import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.GerarListas;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadProdutosMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produtos produtos;
    private List<Usuario> listaUsuario;
    private Usuario usuario;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        produtos = (Produtos) session.getAttribute("produtos");
        session.removeAttribute("produtos");
        String sql = "SELECT u FROM Usuario u WHERE u.cargo.idcargo=1 and u.situacao='Ativo' order by u.nome";
        listaUsuario = GerarListas.listarUsuarios(sql);
        if(produtos==null){
            produtos = new Produtos();
        }else{  
            UsuarioFacade usuarioFacade= new UsuarioFacade();
            usuario = usuarioFacade.consultar(produtos.getIdgerente());
        }
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    public String salvarProduto(){
         produtos.setIdgerente(usuario.getIdusuario());
         ProdutoFacade produtoFacade = new ProdutoFacade();
         produtos = produtoFacade.salvar(produtos);
         FacesMessage mensagem = new FacesMessage("Salvo com Sucesso! ", "Produto salvo.");
         FacesContext.getCurrentInstance().addMessage(null, mensagem);
         RequestContext.getCurrentInstance().closeDialog(produtos);
         return "";
    }
    
    public String cancelar(){
         RequestContext.getCurrentInstance().closeDialog(null);
         return "";
    }
    
}
