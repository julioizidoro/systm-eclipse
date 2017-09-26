package br.com.travelmate.managerBean.fornecedor;

import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoProdutoFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Fornecedorcomissaocursoproduto;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named; 

@Named
@ViewScoped
public class FornecedorComissaoMB implements Serializable{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private AplicacaoMB aplicacaoMB;
    private Pais pais;
    private List<Pais> listaPais;
    private Produtosorcamento produtoOrcamento;
    private List<Filtroorcamentoproduto> listaProdutos;
    private Fornecedor fornecedor;
    private List<Fornecedor> listaFornecedor;
    private Fornecedorcomissaocurso fornecedorcomissaocurso;
    private Fornecedorcomissaocursoproduto fornecedorcomissaocursoproduto;
    private List<Fornecedorcomissaocursoproduto> listaComissao;
    private Float matriz;
    private Float express;
    private Float premium;
    
    
    @PostConstruct
    public void init() {
        gerarListaFornecedor();
        gerarListaProdutos();
        fornecedorcomissaocurso = new Fornecedorcomissaocurso();
        fornecedorcomissaocursoproduto = new Fornecedorcomissaocursoproduto();
    }

    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	

	public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Pais> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<Pais> listaPais) {
        this.listaPais = listaPais;
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

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Fornecedor> getListaFornecedor() {
        return listaFornecedor;
    }

    public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
        this.listaFornecedor = listaFornecedor;
    }

    public Fornecedorcomissaocurso getFornecedorcomissaocurso() {
        return fornecedorcomissaocurso;
    }

    public void setFornecedorcomissaocurso(Fornecedorcomissaocurso fornecedorcomissaocurso) {
        this.fornecedorcomissaocurso = fornecedorcomissaocurso;
    }

    public Fornecedorcomissaocursoproduto getFornecedorcomissaocursoproduto() {
        return fornecedorcomissaocursoproduto;
    }

    public void setFornecedorcomissaocursoproduto(Fornecedorcomissaocursoproduto fornecedorcomissaocursoproduto) {
        this.fornecedorcomissaocursoproduto = fornecedorcomissaocursoproduto;
    }

    public List<Fornecedorcomissaocursoproduto> getListaComissao() {
        return listaComissao;
    }

    public void setListaComissao(List<Fornecedorcomissaocursoproduto> listaComissao) {
        this.listaComissao = listaComissao;
    }

   
    
	

	public void gerarListaPais(){
		pais = null;
        PaisFacade paisFacade = new PaisFacade();
        listaPais = paisFacade.listar("");
        if(listaPais==null){
            listaPais = new ArrayList<Pais>();
        } 
        listaComissao = new ArrayList<>();
    }
    
    public void gerarListaFornecedor(){
        FornecedorFacade fornecedorFacade = new FornecedorFacade();
        listaFornecedor = fornecedorFacade.listar("select f from Fornecedor f where f.idfornecedor>1000 order by f.nome");
        if(listaFornecedor==null){
            listaFornecedor = new ArrayList<Fornecedor>();
        }
    }
    
    public void gerarListaProdutos(){
    	produtoOrcamento = new Produtosorcamento();
        FiltroOrcamentoProdutoFacade filtroOrcamentoFacade = new FiltroOrcamentoProdutoFacade();
        listaProdutos = filtroOrcamentoFacade.pesquisar("select f from Filtroorcamentoproduto f order by f.produtosorcamento.descricao");
        if(listaProdutos==null){
            listaProdutos = new ArrayList<Filtroorcamentoproduto>();
        }
    }
    
    public String cancelarCadastro(){ 
        return "consFornecedorPais";
    }
    
    public String salvar(){
        if(pais!=null && produtoOrcamento!=null && fornecedor!=null){  
            FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
            if (fornecedorcomissaocurso.getIdfornecedorcomissao()==null){
            	fornecedorcomissaocurso.setFornecedor(fornecedor);
                fornecedorcomissaocurso.setPais(pais);
                fornecedorcomissaocurso.setPercloja(premium.doubleValue());
                fornecedorcomissaocurso.setPercmatriz(matriz.doubleValue());
            }
            fornecedorcomissaocurso = fornecedorComissaoCursoFacade.salvar(fornecedorcomissaocurso);
            FornecedorComissaoCursoProdutoFacade fornecedorComissaoCursoProdutoFacade = new FornecedorComissaoCursoProdutoFacade();
            fornecedorcomissaocursoproduto.setFornecedorcomissaocurso(fornecedorcomissaocurso);
            fornecedorcomissaocursoproduto.setProdutosorcamento(produtoOrcamento);
            fornecedorcomissaocursoproduto.setPremium(premium);
            fornecedorcomissaocursoproduto.setExpress(express);
            fornecedorcomissaocursoproduto.setMatriz(matriz);
            fornecedorcomissaocursoproduto = fornecedorComissaoCursoProdutoFacade.salvar(fornecedorcomissaocursoproduto);
            consultarComissaoFornecedorCurso();
            fornecedorcomissaocursoproduto = new Fornecedorcomissaocursoproduto();
            express=0.0f;
            matriz=0.0f;
            premium=0.0f;
            produtoOrcamento = null;
        } else {
            FacesMessage mensagem = new FacesMessage("Faltam informações! ", "");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
        }
        return "";
    } 
    
    
    
    public Float getMatriz() {
		return matriz;
	}

	public void setMatriz(Float matriz) {
		this.matriz = matriz;
	}

	public Float getExpress() {
		return express;
	}

	public void setExpress(Float express) {
		this.express = express;
	}

	public Float getPremium() {
		return premium;
	}

	public void setPremium(Float premium) {
		this.premium = premium;
	}

	public void consultarComissaoFornecedorCurso(){
    	 if(pais!=null &&  fornecedor!=null){
    		 FornecedorComissaoCursoFacade comissaoCursoFacade = new FornecedorComissaoCursoFacade();
    		 fornecedorcomissaocurso = comissaoCursoFacade.consultar(fornecedor.getIdfornecedor(), pais.getIdpais());
    		 if(fornecedorcomissaocurso!=null){
	    		 String sql;
	    		 sql ="select f from Fornecedorcomissaocursoproduto f where f.fornecedorcomissaocurso.idfornecedorcomissao=" + fornecedorcomissaocurso.getIdfornecedorcomissao() + "  order by f.produtosorcamento.descricao";
	    		 FornecedorComissaoCursoProdutoFacade fornecedorComissaoCursoProdutoFacade = new FornecedorComissaoCursoProdutoFacade();
	    		 listaComissao = fornecedorComissaoCursoProdutoFacade.listar(sql);
    		 }else {
    			 fornecedorcomissaocurso = new Fornecedorcomissaocurso();
    			 listaComissao = new ArrayList<Fornecedorcomissaocursoproduto>();
    		 }
    		 if (listaComissao==null){
    			 listaComissao = new ArrayList<Fornecedorcomissaocursoproduto>();
    		 }
             fornecedorcomissaocursoproduto = new Fornecedorcomissaocursoproduto(); 
    	 }else{
    		 listaComissao = new ArrayList<Fornecedorcomissaocursoproduto>();
    	 }
    }
     
    public String excluir(Fornecedorcomissaocursoproduto fornecedorcomissaocursoproduto){  
    	 FornecedorComissaoCursoProdutoFacade comissaoCursoProdutoFacade = new FornecedorComissaoCursoProdutoFacade();
    	 comissaoCursoProdutoFacade.excluir(fornecedorcomissaocursoproduto.getIdfornecedorcomissaocursoproduto()); 
    	 consultarComissaoFornecedorCurso();
    	 return "";
     }
    
    
    public List<Fornecedorcidadeidiomaproduto>  gerarListaProdutosOrcamento(){
		String sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="+
	    pais.getIdpais()+ " and f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="+fornecedor.getIdfornecedor() +
	    " and f.produtosorcamento.tipo='O'";
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
		List<Fornecedorcidadeidiomaproduto>listaFornecedorCidadeIdiomaProduto = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
		if(listaFornecedorCidadeIdiomaProduto==null){
			listaFornecedorCidadeIdiomaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
		}
		return listaFornecedorCidadeIdiomaProduto;
    }
    
     
    public String salvarProdutosOrcamento(){
    	List<Fornecedorcidadeidiomaproduto>listaFornecedorCidadeIdiomaProduto = gerarListaProdutosOrcamento();
        if(pais!=null && fornecedor!=null && premium>0.0f && express>0.0f && matriz>0.0f){  
        	if(listaFornecedorCidadeIdiomaProduto!=null && listaFornecedorCidadeIdiomaProduto.size()>0){
	        	for (int i = 0; i < listaFornecedorCidadeIdiomaProduto.size(); i++) {
	        		fornecedorcomissaocursoproduto = new Fornecedorcomissaocursoproduto();
	        		FornecedorComissaoCursoProdutoFacade fornecedorComissaoCursoProdutoFacade = new FornecedorComissaoCursoProdutoFacade();
	        		fornecedorcomissaocursoproduto = fornecedorComissaoCursoProdutoFacade.pesquisar(fornecedorcomissaocurso.getIdfornecedorcomissao(), listaFornecedorCidadeIdiomaProduto.get(i).getProdutosorcamento().getIdprodutosOrcamento());
		            if (fornecedorcomissaocursoproduto==null){
		            	fornecedorcomissaocursoproduto = new Fornecedorcomissaocursoproduto();
			            fornecedorcomissaocursoproduto.setFornecedorcomissaocurso(fornecedorcomissaocurso);
			            fornecedorcomissaocursoproduto.setProdutosorcamento(listaFornecedorCidadeIdiomaProduto.get(i).getProdutosorcamento());
						fornecedorcomissaocursoproduto.setPremium(premium);
			            fornecedorcomissaocursoproduto.setExpress(express);
			            fornecedorcomissaocursoproduto.setMatriz(matriz);
			            fornecedorComissaoCursoProdutoFacade.salvar(fornecedorcomissaocursoproduto);
			            consultarComissaoFornecedorCurso(); 
		            }
	        	}
        	}
        	fornecedorcomissaocursoproduto = new Fornecedorcomissaocursoproduto();
            express=0.0f;
            matriz=0.0f;
            premium=0.0f;
            produtoOrcamento = null;
        } else {
            Mensagem.lancarMensagemErro("Faltam informações! ", ""); 
        }
        fornecedorcomissaocursoproduto = new Fornecedorcomissaocursoproduto();
        return "";
    }
}
