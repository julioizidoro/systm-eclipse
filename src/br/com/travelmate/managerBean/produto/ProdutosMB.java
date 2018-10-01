package br.com.travelmate.managerBean.produto;

import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.model.Produtos;
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
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class ProdutosMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produtos produtos;
    private List<Produtos> listaProdutos;
    private String descricao="";
    
    @PostConstruct
    public void init(){
        produtos = new Produtos();
        gerarListaProdutos();
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    
    public List<Produtos> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String cadastrarProdutos(){
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("cadProdutos", options, null);
        return "";
    }
    
    
    public String consultarUsuarios(Produtos produtos){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("produtos", produtos);       
        return "consUsuariosProduto";
    }
    
    public String adicionarItens(Produtos produtos){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("produtos", produtos);       
        return "adicionarItem";
    }
    
    public String consultarSubProdutos(Produtos produtos){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("produtos", produtos);       
        return "consSubProdutos";
    }
    
    public String voltar(){
        return "consProdutos";
    }
    
    public void listarPesquisaPorDescricao(){
        ProdutoFacade produtoFacade = new ProdutoFacade();
        listaProdutos = produtoFacade.listarProdutos(descricao);
        if(listaProdutos==null){
            listaProdutos = new ArrayList<Produtos>();
        }
    }
    
    public void gerarListaProdutos(){
        ProdutoFacade produtoFacade = new ProdutoFacade();
        listaProdutos = produtoFacade.listarProdutos();
        if(listaProdutos==null){
            listaProdutos = new ArrayList<Produtos>();
        }
    }
    
    public void retornoDialogNovo(SelectEvent event) {
        Produtos produtos = (Produtos) event.getObject();
        listaProdutos.add(produtos);
    }
    
    
    public String editar(Produtos produtos){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("produtos", produtos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
        RequestContext.getCurrentInstance().openDialog("cadProdutos", options, null);
        return "";
    }
    
    public String cadastrarUsuarios(){
        RequestContext.getCurrentInstance().openDialog("cadUsuariosProduto");
        return "";
    }
    
    public String cadastrarSubProdutos(){
        RequestContext.getCurrentInstance().openDialog("cadSubProdutos");
        return "";
    }
   
}
