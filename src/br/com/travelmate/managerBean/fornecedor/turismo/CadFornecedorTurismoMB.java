package br.com.travelmate.managerBean.fornecedor.turismo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.CidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FornecedorFinanceiroFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ProdutoFacade; 
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorfinanceiro;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadFornecedorTurismoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedor fornecedor;
	private Fornecedorfinanceiro fornecedorfinanceiro;
	private Pais pais;
	private Cidade cidade;
	private List<Pais> listaPais;
	private boolean novo;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        fornecedorfinanceiro = (Fornecedorfinanceiro) session.getAttribute("fornecedorfinanceiro");
        session.removeAttribute("fornecedorfinanceiro");
        PaisFacade paisFacade = new PaisFacade();
        listaPais = paisFacade.listar();
        if(fornecedorfinanceiro==null){
        	fornecedorfinanceiro = new Fornecedorfinanceiro();
        	fornecedor = new Fornecedor();
        	novo=true;
        	pais = new Pais();
        	cidade = new Cidade();
        }else{
        	fornecedor = fornecedorfinanceiro.getFornecedor();
        	if(fornecedorfinanceiro.getCidade()!=null) {
	        	pais = fornecedorfinanceiro.getCidade().getPais();
	        	cidade = fornecedorfinanceiro.getCidade();
        	}
        	novo = false;
        }
	}  
	
	public Fornecedorfinanceiro getFornecedorfinanceiro() {
		return fornecedorfinanceiro;
	} 

	public void setFornecedorfinanceiro(Fornecedorfinanceiro fornecedorfinanceiro) {
		this.fornecedorfinanceiro = fornecedorfinanceiro;
	} 
 
	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public boolean isNovo() {
		return novo;
	}

	public void setNovo(boolean novo) {
		this.novo = novo;
	}

	public String cancelar(){
		return "consFornecedorTurismo";
	} 
	
	public String confirmar(){  
		if(cidade!=null && cidade.getIdcidade()!=null) {
			FornecedorFacade fornecedorFacade =new FornecedorFacade();
			fornecedor = fornecedorFacade.salvar(fornecedor);
			fornecedorfinanceiro.setFornecedor(fornecedor);
			fornecedorfinanceiro.setCidade(cidade);
			FornecedorFinanceiroFacade fornecedorFinanceiroFacade = new FornecedorFinanceiroFacade();
			fornecedorfinanceiro = fornecedorFinanceiroFacade.salvar(fornecedorfinanceiro); 
			if(novo){
				FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade(); 
				ProdutoFacade produtoFacade = new ProdutoFacade();
				Produtos produtos = produtoFacade.consultar(7);
				CidadeFacade cidadeFacade = new CidadeFacade();
				//curitiba
				Cidade cidade = cidadeFacade.consultar(33);
				Fornecedorcidade fornecedorcidade = new Fornecedorcidade();
				fornecedorcidade.setCidade(cidade);
				fornecedorcidade.setFornecedor(fornecedor);
				fornecedorcidade.setProdutos(produtos);
				fornecedorCidadeFacade.salvar(fornecedorcidade);
				//florianopolis
				cidade = cidadeFacade.consultar(32);
				fornecedorcidade = new Fornecedorcidade();
				fornecedorcidade.setCidade(cidade);
				fornecedorcidade.setFornecedor(fornecedor);
				fornecedorcidade.setProdutos(produtos);
				fornecedorCidadeFacade.salvar(fornecedorcidade);  
			}
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
		}else Mensagem.lancarMensagemErro("Atenção!", "Cidade não informada.");
		return "consFornecedorTurismo";
	} 
}
