package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

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
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.IdiomaFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroviagem;

@Named
@ViewScoped
public class FornecedorCidadeIdiomaProdutoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private AplicacaoMB aplicacaoMB;
	private List<Paisproduto> listaPais;
    private Cidade cidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    private Pais pais;
    private Idioma idioma;
    private List<Idioma> listaIdiomas;
    private Fornecedorcidadeidioma fornecedorCidadeIdioma;
    private List<Fornecedorcidadeidioma> listaFornecedorIdioma;
    private Produtosorcamento produtoOrcamento;
    private List<Filtroorcamentoproduto> listaProdutos;
    private List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto;
    private Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto;
    private String sql;
    
    @PostConstruct
    public void init(){
    	gerarListaProdutos();
    	listarIdiomas();
    	int idProduto = 0;
    	PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
        idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
        listaPais = paisProdutoFacade.listar(idProduto);
        fornecedorcidadeidiomaproduto = new Fornecedorcidadeidiomaproduto();
        FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		sql = (String) session.getAttribute("sql");
		session.removeAttribute("sql");
		if(sql!=null && sql.length()>0){
	        FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
			listaFornecedorCidadeIdiomaProduto = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
			if(listaFornecedorCidadeIdiomaProduto==null){
				listaFornecedorCidadeIdiomaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
			}
		}
    }
    
	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Fornecedorcidadeidioma getFornecedorCidadeIdioma() {
		return fornecedorCidadeIdioma;
	}

	public void setFornecedorCidadeIdioma(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		this.fornecedorCidadeIdioma = fornecedorCidadeIdioma;
	}

	public List<Fornecedorcidadeidioma> getListaFornecedorIdioma() {
		return listaFornecedorIdioma;
	}

	public void setListaFornecedorIdioma(List<Fornecedorcidadeidioma> listaFornecedorIdioma) {
		this.listaFornecedorIdioma = listaFornecedorIdioma;
	}

	public List<Idioma> getListaIdiomas() {
		return listaIdiomas;
	}

	public void setListaIdiomas(List<Idioma> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}

	public Produtosorcamento getProdutoOrcamento() {
		return produtoOrcamento;
	}

	public void setProdutoOrcamento(Produtosorcamento produtoOrcamento) {
		this.produtoOrcamento = produtoOrcamento;
	}

	public List<Filtroorcamentoproduto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Filtroorcamentoproduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public void gerarListaProdutos(){
        FiltroOrcamentoProdutoFacade filtroOrcamentoFacade = new FiltroOrcamentoProdutoFacade();
        listaProdutos = filtroOrcamentoFacade.pesquisar("select f from Filtroorcamentoproduto f order by f.produtosorcamento.descricao");
        if(listaProdutos==null){
            listaProdutos = new ArrayList<Filtroorcamentoproduto>();
        }
    }
	

	public List<Fornecedorcidadeidiomaproduto> getListaFornecedorCidadeIdiomaProduto() {
		return listaFornecedorCidadeIdiomaProduto;
	}

	public void setListaFornecedorCidadeIdiomaProduto(
			List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto) {
		this.listaFornecedorCidadeIdiomaProduto = listaFornecedorCidadeIdiomaProduto;
	}

	public void listarIdiomas(){
        IdiomaFacade  idiomaFacade = new IdiomaFacade();
        listaIdiomas = idiomaFacade.listar("Select i from Idioma i order by i.descricao");
        if (listaIdiomas==null){
            listaIdiomas = new ArrayList<Idioma>();
        }
    }
	
	
    public void listarForCidadeIdioma(){
        if(cidade !=null && idioma != null){
            String sql = "select f from Fornecedorcidadeidioma f where f.fornecedorcidade.cidade.idcidade=" + 
                    cidade.getIdcidade() + " and f.idioma.ididioma =" 
                    + idioma.getIdidioma() + " order by f.fornecedorcidade.fornecedor.nome"; 
            FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
            listaFornecedorIdioma = fornecedorCidadeIdiomaFacade.listar(sql);
            if (listaFornecedorIdioma == null){
                listaFornecedorIdioma = new ArrayList<Fornecedorcidadeidioma>();
            }
        }
    }
    
    public void listarFornecedorCidadeIdiomaProduto(){
    	if(fornecedorCidadeIdioma!=null){
    		sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.idfornecedorcidadeidioma="+
    			 fornecedorCidadeIdioma.getIdfornecedorcidadeidioma();
    		if(produtoOrcamento!=null && produtoOrcamento.getIdprodutosOrcamento()!=null){
    			sql = sql+ " and f.produtosorcamento.idprodutosOrcamento="+produtoOrcamento.getIdprodutosOrcamento();
    		}
    		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
    		listaFornecedorCidadeIdiomaProduto = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
    		if(listaFornecedorCidadeIdiomaProduto==null){
    			listaFornecedorCidadeIdiomaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
    		}
    	}
    } 
    
    public void salvar(){
    	fornecedorcidadeidiomaproduto.setProdutosorcamento(produtoOrcamento);
    	fornecedorcidadeidiomaproduto.setFornecedorcidadeidioma(fornecedorCidadeIdioma);
    	FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
    	fornecedorcidadeidiomaproduto = fornecedorCidadeIdiomaProdutoFacade.salvar(fornecedorcidadeidiomaproduto);
    	fornecedorcidadeidiomaproduto= new Fornecedorcidadeidiomaproduto();
    	listarFornecedorCidadeIdiomaProduto();
    } 
    
    public void excluir(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto){
    	FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
    	fornecedorCidadeIdiomaProdutoFacade.excluir(fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto());
    	listarFornecedorCidadeIdiomaProduto();
    } 
    
    public String cancelarCadastro(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    public void limparDados(){
    	idioma = null;
    	fornecedorCidadeIdioma = null;
    }
    
    public String cadastrarDatasIniciais(Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("fornecedorcidadeidiomaproduto", fornecedorcidadeidiomaproduto);
        session.setAttribute("sql", sql);
        return "cadFornecedorCidadeIdiomaProdutoData";
    }
}
