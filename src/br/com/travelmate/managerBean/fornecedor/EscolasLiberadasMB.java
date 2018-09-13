package br.com.travelmate.managerBean.fornecedor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Pais;

@Named
@ViewScoped
public class EscolasLiberadasMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto;
	private Pais pais;
	private List<Pais> listaPais;
	private Fornecedor fornecedor;
	private List<Fornecedorcidade> listaFornecedorCidade;

	@PostConstruct
	public void init() {
		gerarListaFornecedor();
		gerarListaPais();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Fornecedorcidadeidiomaproduto> getListaFornecedorCidadeIdiomaProduto() {
		return listaFornecedorCidadeIdiomaProduto;
	}

	public void setListaFornecedorCidadeIdiomaProduto(
			List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto) {
		this.listaFornecedorCidadeIdiomaProduto = listaFornecedorCidadeIdiomaProduto;
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

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	
	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public void gerarListaFornecedor() {
		
	}

	public void gerarListaPais() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar("");
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
	}
	
	public void pesquisar(){
		String sql = "select f from Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.habilitarorcamento=true ";
		if(fornecedor!=null){
			sql =sql+" and f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="+fornecedor.getIdfornecedor();
		}
		if(pais!=null){
			sql =sql+" and f.fornecedorcidadeidioma.fornecedorcidade.cidade.pais.idpais="+pais.getIdpais();
		}
		sql =sql+" order by f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.nome";
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
		listaFornecedorCidadeIdiomaProduto = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
		if(listaFornecedorCidadeIdiomaProduto==null){
			listaFornecedorCidadeIdiomaProduto = new ArrayList<Fornecedorcidadeidiomaproduto>();
		}
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
}
