package br.com.travelmate.managerBean.fornecedor;

import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Produtos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class ConsultaFornecedorMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedorcidade fornecedorcidade;
    private List<Fornecedor> listaFornecedor;
    private Cidade cidade;
    private Produtos produto;
    private Fornecedor fornecedor;
    private String nomefornecedor; 

    public ConsultaFornecedorMB() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        produto = (Produtos) session.getAttribute("produtos");
        cidade = (Cidade) session.getAttribute("cidade");
        fornecedorcidade = (Fornecedorcidade) session.getAttribute("fornecedorcidade");
        session.removeAttribute("produtos");
        session.removeAttribute("cidade");
        session.removeAttribute("fornecedorcidade");
        //gerarListaFornecedor();
        fornecedor = new Fornecedor();
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public List<Fornecedor> getListaFornecedor() {
        return listaFornecedor;
    }

    public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
        this.listaFornecedor = listaFornecedor;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Produtos getProduto() {
        return produto;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }
    
    public String getNomefornecedor() {
		return nomefornecedor;
	}

	public void setNomefornecedor(String nomefornecedor) {
		this.nomefornecedor = nomefornecedor;
	}

	public void gerarListaFornecedor(){
		if (nomefornecedor==null){
			nomefornecedor="";
		}
        FornecedorFacade forncedorFacade = new FornecedorFacade();
        listaFornecedor = forncedorFacade.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 and f.nome like '%" + nomefornecedor + "%'  order by f.nome");
        if(listaFornecedor==null){
            listaFornecedor = new ArrayList<Fornecedor>();
        }
    }

    
    public String salvarFornecedor(){
    	List<Fornecedorcidade> listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
        for(int i=0;i<listaFornecedor.size();i++){
        	boolean work = true;
        	if(fornecedorcidade!=null) {
        		work = fornecedorcidade.isWork();
        	}
            if(listaFornecedor.get(i).isSelecionado()){ 
            	fornecedorcidade = new Fornecedorcidade(); 
            	fornecedorcidade.setWork(work);
                fornecedorcidade.setCidade(cidade);
                fornecedorcidade.setProdutos(produto);
                fornecedorcidade.setFornecedor(listaFornecedor.get(i));
                fornecedorcidade.setPeso(0);
                FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
                fornecedorcidade = fornecedorCidadeFacade.salvar(fornecedorcidade);
                listaFornecedorCidade.add(fornecedorcidade);
            }
        }
        RequestContext.getCurrentInstance().closeDialog(listaFornecedorCidade);
        return "";
    }
    
     public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(new ArrayList<Fornecedorcidade>());
        return "";
    }
   
    public String novo(){
        RequestContext.getCurrentInstance().openDialog("cadFornecedor");
        return "";
    }
    
    public String salvar(){
        FornecedorFacade fornecedorFacade = new FornecedorFacade();
        fornecedor = fornecedorFacade.salvar(fornecedor);
        fornecedor = new Fornecedor();
        return "";
    }
    
    public void cancelarCadastro(){
    	fornecedor = new Fornecedor();
    }
    
    public void receberFornecedor(Fornecedor fornecedor){
    	this.fornecedor = fornecedor;
    }
}
