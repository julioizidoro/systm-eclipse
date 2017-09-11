package br.com.travelmate.managerBean.fornecedor;

import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class paisMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Pais> listaPais;
    private Pais pais;
    private Cidade cidade;
    private List<Cidadepaisproduto> listaCidadePaisProduto;
    private Produtos produtos;
    private List<Produtos> listaProduto;
    private List<Cidade> listaCidade;
    
    @PostConstruct
    public void init() {
        pais = new Pais();
        gerarListaPais();
        listaProduto = GerarListas.listarProdutos("");
    }

    public List<Pais> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<Pais> listaPais) {
        this.listaPais = listaPais;
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

	public List<Cidadepaisproduto> getListaCidadePaisProduto() {
		return listaCidadePaisProduto;
	}

	public void setListaCidadePaisProduto(List<Cidadepaisproduto> listaCidadePaisProduto) {
		this.listaCidadePaisProduto = listaCidadePaisProduto;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public List<Produtos> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produtos> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public void gerarListaPais(){
        PaisFacade paisFacade = new PaisFacade();
        listaPais = paisFacade.listar("");
        if(listaPais==null){
            listaPais = new ArrayList<Pais>();
        }
    }
    
    public String cadPais(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 430);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("cadPais", options, null);
        return "";
    }
    
    public String cadCidade(){
        if(pais!=null){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("pais", pais);    
            RequestContext.getCurrentInstance().openDialog("cadCidade");
        }else{
            FacesMessage mensagem = new FacesMessage("Pais não selecionado! ", "campos obrigatorios não preenchidos.");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
        }
        return "";
    }
    
    public String voltar(){
        return "consFornecedorPais";
    }
    
    public void retornoDialogNovoPais(SelectEvent event) {
        Pais pais = (Pais) event.getObject();
        listaPais.add(pais);
    }

    public void retornoDialogNovoCidade(SelectEvent event) {
        Cidade cidade = (Cidade) event.getObject();
        pais.getCidadeList().add(cidade);
    }
    
    
    public String editarPais(Pais pais){
	    	if(pais!=null){
		        Map<String,Object> options = new HashMap<String, Object>();
		        options.put("contentWidth", 430);
		        options.put("modal", true);
		        FacesContext fc = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		        session.setAttribute("pais", pais);    
		        RequestContext.getCurrentInstance().openDialog("cadPais", options, null);
	    	}else Mensagem.lancarMensagemErro("Selecione um País", "");
        return "";
    }
    
    public String uploadVisto(Pais pais){
	    	if(pais!=null){
		        Map<String,Object> options = new HashMap<String, Object>();
		        options.put("contentWidth", 430);
		        options.put("modal", true);
		        FacesContext fc = FacesContext.getCurrentInstance();
		        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		        session.setAttribute("pais", pais);    
		        RequestContext.getCurrentInstance().openDialog("uploadVisto", options, null);
	    	}else Mensagem.lancarMensagemErro("Selecione um País", "");
        return "";
    }
    
    public void selecionarCidade(Cidade cidade) {
	    	this.cidade = cidade;
	    	CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
	    	listaCidadePaisProduto = cidadePaisProdutosFacade.listar(
	    			"SELECT c FROM Cidadepaisproduto c WHERE c.cidade.idcidade="+cidade.getIdcidade());
	    	if(listaCidadePaisProduto==null) {
	    		listaCidadePaisProduto = new ArrayList<>();
	    	}
    }
    
    public void adicionarCidadePaisProduto() {
		if (cidade != null && cidade.getIdcidade() != null) {
			if (produtos != null && produtos.getIdprodutos() != null) {
				CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
				List<Cidadepaisproduto> lista = cidadePaisProdutosFacade
						.listar("SELECT c FROM Cidadepaisproduto c WHERE c.cidade.idcidade=" + cidade.getIdcidade()
								+ " AND c.paisproduto.produtos.idprodutos=" + produtos.getIdprodutos());
				if (lista == null || lista.size() == 0) {
					PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
					Paisproduto paisproduto = paisProdutoFacade.consultar(
							"SELECT p FROM Paisproduto p WHERE p.produtos.idprodutos=" + produtos.getIdprodutos()
							+ " AND p.pais.idpais="+pais.getIdpais());
					if (paisproduto == null || paisproduto.getIdpaisproduto() == null) {
						paisproduto = new Paisproduto();
						paisproduto.setPais(cidade.getPais());
						paisproduto.setProdutos(produtos);
						paisproduto = paisProdutoFacade.salvar(paisproduto);
					}
					Cidadepaisproduto cidadepaisproduto = new Cidadepaisproduto();
					cidadepaisproduto.setPaisproduto(paisproduto);
					cidadepaisproduto.setCidade(cidade);
					cidadepaisproduto = cidadePaisProdutosFacade.salvar(cidadepaisproduto);
					Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
					listaCidadePaisProduto.add(cidadepaisproduto);
				} else
					Mensagem.lancarMensagemErro("Atenção!", "Cidade já possui este produto cadastrado.");
			} else
				Mensagem.lancarMensagemErro("Atenção!", "Selecione um produto.");
		} else
			Mensagem.lancarMensagemErro("Atenção!", "Selecione uma cidade.");
    }
    
    public void salvarAtivo(Cidade cidade) {
	    	CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
	    	listaCidadePaisProduto = cidadePaisProdutosFacade.listar(
	    			"SELECT c FROM Cidadepaisproduto c WHERE c.cidade.idcidade="+cidade.getIdcidade());
	    	FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
	    	List<Fornecedorcidade> listaFornecedor = fornecedorCidadeFacade.listar(
	    			"SELECT f FROM Fornecedorcidade f WHERE f.cidade.idcidade="+cidade.getIdcidade()
	    				+" and f.ativo=TRUE");
	    	if(listaCidadePaisProduto==null || listaCidadePaisProduto.size()==0
	    			|| listaFornecedor==null || listaFornecedor.size()==0) {
	    		CidadeFacade cidadeFacade = new CidadeFacade(); 
	    		cidade = cidadeFacade.salvar(cidade);
	    }else { 
		    	if(cidade.isAtiva()) {
	    			cidade.setAtiva(false);
	    		}else cidade.setAtiva(true);
	    		Mensagem.lancarMensagemErro("Atenção!", "Cidade só poderá ser desativada após excluir os produtos e desativar os parceiros.");
	    }
    }
    
    public void excluirProdutos(Cidadepaisproduto cidadepaisproduto) {
    		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
    		listaCidadePaisProduto.remove(cidadepaisproduto);
    		cidadePaisProdutosFacade.excluir(cidadepaisproduto.getIdcidadepaisproduto());
    		Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
    }
    
    public void listarCidade() {
    		CidadeFacade cidadeFacade = new CidadeFacade();
    		listaCidade = cidadeFacade.listar(pais.getIdpais());
    }
    
}
