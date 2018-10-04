package br.com.travelmate.managerBean.comercial.regrasPontuacao;

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

import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorFacade;

import br.com.travelmate.facade.RegraVendaFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Regravenda;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadRegrasPontuacaoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PaisDao paisDao;
	private Produtos produtos;
	private Pais pais;
	private Cidade cidade;
	private Fornecedor fornecedor;
	private Regravenda regraVenda;
	private List<Produtos> listaProdutos;
	private List<Pais> listaPais;
	private List<Fornecedor> listaFornecedor;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private boolean habilitarFornecedor;
	private boolean habilitarFornecedorCidade;
	 
	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		regraVenda = (Regravenda) session.getAttribute("regraVenda");
		session.removeAttribute("regraVenda");
	    listaProdutos = GerarListas.listarProdutos("");
	    
        listaPais = paisDao.listar();
        gerarListaFornecedor();
        pais = new Pais();
        cidade = new Cidade();
        fornecedor = new Fornecedor();
        fornecedorCidade =new Fornecedorcidade();
        produtos = new Produtos(); 
        habilitarFornecedor = true;
        habilitarFornecedorCidade = false;
        if(regraVenda!=null){
        	iniciarAlteracao();
        }else regraVenda = new Regravenda();
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
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

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Regravenda getRegraVenda() {
		return regraVenda;
	}

	public void setRegraVenda(Regravenda regraVenda) {
		this.regraVenda = regraVenda;
	}

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}

	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public boolean isHabilitarFornecedor() {
		return habilitarFornecedor;
	}

	public void setHabilitarFornecedor(boolean habilitarFornecedor) {
		this.habilitarFornecedor = habilitarFornecedor;
	}

	public boolean isHabilitarFornecedorCidade() {
		return habilitarFornecedorCidade;
	}

	public void setHabilitarFornecedorCidade(boolean habilitarFornecedorCidade) {
		this.habilitarFornecedorCidade = habilitarFornecedorCidade;
	}

	public String fechar(){
	    RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    } 
	
	public void gerarListaFornecedor(){
        FornecedorFacade forncedorFacade = new FornecedorFacade();
        listaFornecedor = forncedorFacade.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
        if(listaFornecedor==null){
            listaFornecedor = new ArrayList<Fornecedor>();
        }
    }
	
	public void salvar(){
		if(produtos!=null && produtos.getIdprodutos()!=null){
			RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
			regraVenda.setProdutos(produtos);
			regraVenda.setSituacao(true);
			if(pais!=null && pais.getIdpais()!=null){
				regraVenda.setPais(pais.getIdpais());
				if(cidade!=null && cidade.getIdcidade()!=null){
					regraVenda.setCidade(cidade.getIdcidade());
					if(fornecedorCidade!=null && fornecedorCidade.getIdfornecedorcidade()!=null){
						regraVenda.setFornecedor(fornecedorCidade.getFornecedor().getIdfornecedor());
					}else regraVenda.setFornecedor(0);
				}else regraVenda.setCidade(0);
			}else{
				regraVenda.setPais(0);
			}  
			if(cidade==null || cidade.getIdcidade()==null){
				if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
					regraVenda.setFornecedor(fornecedor.getIdfornecedor());
				}else regraVenda.setFornecedor(0);
			} 
			regraVenda = regraVendaFacade.salvar(regraVenda);
			RequestContext.getCurrentInstance().closeDialog(null);
		}else Mensagem.lancarMensagemErro("Produto não selecionado", "");
	}

	
	public void iniciarAlteracao(){
		produtos = regraVenda.getProdutos();
		if(regraVenda.getPais()>0){
			
			pais = paisDao.consultar(regraVenda.getPais());
			if(regraVenda.getCidade()>0){
				CidadeFacade cidadeFacade = new CidadeFacade();
				cidade = cidadeFacade.consultar(regraVenda.getCidade());
				listarFornecedorCidade();
				if(regraVenda.getFornecedor()>0){
					FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
					fornecedorCidade = fornecedorCidadeFacade.getFonecedorCidade(regraVenda.getFornecedor(), regraVenda.getCidade());
				}
			}else{
				habilitarFornecedorCidade=false;
				habilitarFornecedor=true; 
			}
		}else{
			habilitarFornecedorCidade=false;
			habilitarFornecedor=true; 
		}
		if(regraVenda.getFornecedor()>0){
			FornecedorFacade fornecedorFacade = new FornecedorFacade();
			fornecedor = fornecedorFacade.consultar(regraVenda.getFornecedor());
		}
	}
	
	public void listarFornecedorCidade(){
        if ((produtos!=null) && (cidade!=null)){
            listaFornecedorCidade = GerarListas.listarFornecedorCidade(produtos.getIdprodutos(), cidade.getIdcidade());
            habilitarFornecedorCidade=true;
			habilitarFornecedor=false;
        }else Mensagem.lancarMensagemInfo("Produto não selecionado.", ""); 
    }
}
