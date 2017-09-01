package br.com.travelmate.managerBean.coprodutos;

import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;

import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Grupoobrigatorio;
import br.com.travelmate.model.Produtosorcamento;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadCoProdutosMB implements Serializable{
    
    
    
	private static final long serialVersionUID = 1L;
	@Inject
    private AplicacaoMB aplicacaoMB;
    private Coprodutos coprodutos;
    private Fornecedorcidade fornecedorcidade;
    private Fornecedorcidadeidioma fornecedorcidadeidioma;
    private List<Filtroorcamentoproduto> listaFiltroorcamentoproduto;
    private List<Coprodutos> listaCoProdutos;
    private Produtosorcamento prdutoOrcamento;
    private Grupoobrigatorio grupoobrigatorio;
    private Coprodutos coProdutoVincular;
    private String habilitarOpcoesPacotes;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Fornecedorcidadeidioma fornecedorcidadeidiomas = (Fornecedorcidadeidioma) session.getAttribute("fornecedorcidadeidioma");
        coprodutos = (Coprodutos) session.getAttribute("coprodutos");
        session.removeAttribute("coprodutos");
        fornecedorcidade = fornecedorcidadeidiomas.getFornecedorcidade();
        fornecedorcidadeidioma = fornecedorcidadeidiomas;
        getAplicacaoMB();
        if(coprodutos==null){
            coprodutos = new Coprodutos();
            prdutoOrcamento = new Produtosorcamento();
            grupoobrigatorio = new Grupoobrigatorio();
            listaFiltroorcamentoproduto = new ArrayList<Filtroorcamentoproduto>();
            coprodutos.setPacote(false);
            habilitarOpcoesPacote();
            coprodutos.setAcomodacao(false);
            coprodutos.setTransfer(false);
        }else{
           gerarListaProdutosOrcamento();
           gerarListaCoProdutosVincular();
           prdutoOrcamento = coprodutos.getProdutosorcamento();
           GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
           grupoobrigatorio = grupoObrigatorioFacade.consultar(coprodutos.getIdcoprodutos());
           if(grupoobrigatorio!=null){
                coProdutoVincular  = grupoobrigatorio.getProduto();
           }else{
               grupoobrigatorio = new Grupoobrigatorio();
           }
           habilitarOpcoesPacote();
        }
     }

    

    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}



	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}



	public Coprodutos getCoprodutos() {
        return coprodutos;
    }

    public void setCoprodutos(Coprodutos coprodutos) {
        this.coprodutos = coprodutos;
    }

    public List<Filtroorcamentoproduto> getListaFiltroorcamentoproduto() {
        return listaFiltroorcamentoproduto;
    }

    public void setListaFiltroorcamentoproduto(List<Filtroorcamentoproduto> listaFiltroorcamentoproduto) {
        this.listaFiltroorcamentoproduto = listaFiltroorcamentoproduto;
    }

    public Produtosorcamento getPrdutoOrcamento() {
        return prdutoOrcamento;
    }

    public void setPrdutoOrcamento(Produtosorcamento prdutoOrcamento) {
        this.prdutoOrcamento = prdutoOrcamento;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }
	
	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}

	public List<Coprodutos> getListaCoProdutos() {
		return listaCoProdutos;
	}

	public void setListaCoProdutos(List<Coprodutos> listaCoProdutos) {
		this.listaCoProdutos = listaCoProdutos;
	}

	public Coprodutos getCoProdutoVincular() {
		return coProdutoVincular;
	}

	public void setCoProdutoVincular(Coprodutos coProdutoVincular) {
		this.coProdutoVincular = coProdutoVincular;
	}

	public Grupoobrigatorio getGrupoobrigatorio() {
		return grupoobrigatorio;
	}

	public void setGrupoobrigatorio(Grupoobrigatorio grupoobrigatorio) {
		this.grupoobrigatorio = grupoobrigatorio;
	}
	
	
    public String getHabilitarOpcoesPacotes() {
		return habilitarOpcoesPacotes;
	}



	public void setHabilitarOpcoesPacotes(String habilitarOpcoesPacotes) {
		this.habilitarOpcoesPacotes = habilitarOpcoesPacotes;
	}



	public void gerarListaProdutosOrcamento(){
        FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
        String sql="";
        if(coprodutos.getTipo().equalsIgnoreCase("Acomodacao")){
            sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " and f.produtosorcamento.tipo='A' order by f.produtosorcamento.descricao";
        }else if(coprodutos.getTipo().equalsIgnoreCase("AcOpcional")){
            sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " and f.produtosorcamento.tipo='A' order by f.produtosorcamento.descricao";
        }else if(coprodutos.getTipo().equalsIgnoreCase("Transfer")){
            sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " and f.produtosorcamento.tipo='T' order by f.produtosorcamento.descricao";
        }else if(coprodutos.getTipo().equalsIgnoreCase("Transfer")){
            sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " and f.produtosorcamento.tipo='T' order by f.produtosorcamento.descricao";
        }else if(coprodutos.getTipo().equalsIgnoreCase("Opcional")){
            sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " and f.produtosorcamento.tipo='D' order by f.produtosorcamento.descricao";
        }else if(coprodutos.getTipo().equalsIgnoreCase("Obrigatorio")){
            sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos=" + 
                aplicacaoMB.getParametrosprodutos().getCursos() + " and f.produtosorcamento.tipo='O' or f.produtosorcamento.tipo='C' order by f.produtosorcamento.descricao";
        }
        listaFiltroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(sql);
        if (listaFiltroorcamentoproduto==null){
            listaFiltroorcamentoproduto = new ArrayList<Filtroorcamentoproduto>();
        }
    }
    
    public String salvarCoProduto(){
        if(coprodutos.getTipo()!=null && prdutoOrcamento.getIdprodutosOrcamento()!=null && coprodutos.getDescricao()!=null){
            coprodutos.setFornecedorcidadeidioma(fornecedorcidadeidioma);
            coprodutos.setFornecedorcidade(fornecedorcidade);
            coprodutos.setProdutosorcamento(prdutoOrcamento);
            coprodutos.setSituacao(true);
            CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
            coprodutos = coProdutosFacade.salvar(coprodutos);
            if(coProdutoVincular!=null){
            	grupoobrigatorio.setCoprodutos(coprodutos);
            	grupoobrigatorio.setProduto(coProdutoVincular);
        		GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
        		grupoobrigatorio = grupoObrigatorioFacade.salvar(grupoobrigatorio);
        	}
            RequestContext.getCurrentInstance().closeDialog(coprodutos);
            return "";
        }else{
            FacesMessage mensagem = new FacesMessage("Atencao! ", "Campos obrigatorios nao preenchidos.");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
    public String cancelarCoProduto(){
        RequestContext.getCurrentInstance().closeDialog(null);
        coprodutos = new Coprodutos();
        return "";
    }
    
    public void gerarListaCoProdutosVincular(){
    	if (fornecedorcidadeidioma!=null){
    		String sql = "Select c from Coprodutos c  where c.fornecedorcidadeidioma.idfornecedorcidadeidioma=" + fornecedorcidadeidioma.getIdfornecedorcidadeidioma();
    		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
    		listaCoProdutos = coProdutosFacade.listar(sql);
    		if (listaCoProdutos==null){
    			listaCoProdutos = new ArrayList<Coprodutos>();
    		}
    	}
    }
    
    public void habilitarOpcoesPacote(){
    	if(coprodutos.isPacote()){
    		habilitarOpcoesPacotes = "false";
    	}else{
    		habilitarOpcoesPacotes = "true";
    	}
    }
}
