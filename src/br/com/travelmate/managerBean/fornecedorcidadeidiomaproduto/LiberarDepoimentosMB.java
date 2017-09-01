package br.com.travelmate.managerBean.fornecedorcidadeidiomaproduto;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List;

import javax.annotation.PostConstruct; 
import javax.faces.view.ViewScoped; 
import javax.inject.Named; 

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.facade.FornecedorCidadeDepoimentoFacade; 
import br.com.travelmate.model.Fornecedorcidadedepoimento;  

@Named
@ViewScoped
public class LiberarDepoimentosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Fornecedorcidadedepoimento> listaDepoimento;

	@PostConstruct
	public void init() {
		gerarListaDepoimentos();
	}

	public List<Fornecedorcidadedepoimento> getListaDepoimento() {
		return listaDepoimento;
	}

	public void setListaDepoimento(List<Fornecedorcidadedepoimento> listaDepoimento) {
		this.listaDepoimento = listaDepoimento;
	}

	public void fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}

	public void liberarDepoimento(Fornecedorcidadedepoimento depoimento) {
		depoimento.setApovado(true);
		FornecedorCidadeDepoimentoFacade fornecedorCidadeDepoimentoFacade = new FornecedorCidadeDepoimentoFacade();
		fornecedorCidadeDepoimentoFacade.salvar(depoimento); 
		gerarListaDepoimentos();
	}

	public void gerarListaDepoimentos() {
		String sql = "select f from Fornecedorcidadedepoimento f where f.apovado=false";
		FornecedorCidadeDepoimentoFacade fornecedorCidadeDepoimentoFacade = new FornecedorCidadeDepoimentoFacade();
		listaDepoimento = fornecedorCidadeDepoimentoFacade.lista(sql);
		if (listaDepoimento == null) {
			listaDepoimento = new ArrayList<Fornecedorcidadedepoimento>();
		}
	}

	
	public boolean mostrarContatos(Fornecedorcidadedepoimento depoimento){
		if(depoimento.isAceitacontato()){
			return true;
		}
		return false;
	}   
}
