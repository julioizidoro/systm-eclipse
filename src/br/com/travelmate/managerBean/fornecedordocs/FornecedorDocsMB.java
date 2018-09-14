package br.com.travelmate.managerBean.fornecedordocs;

import br.com.travelmate.facade.FornecedorDocsFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedordocs;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class FornecedorDocsMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
	private List<Fornecedordocs> listaDocs;
	private String nome;

	@PostConstruct
	public void init() {
		gerarListaFornecedor();
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

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	public List<Fornecedordocs> getListaDocs() {
		return listaDocs;
	}

	public void setListaDocs(List<Fornecedordocs> listaDocs) {
		this.listaDocs = listaDocs;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void gerarListaFornecedor() {
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade
				.listar("select f from Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}

	public String novo() {
		return "cadFornecedorDocs";
	}

	public String vincularCidades(Fornecedordocs fornecedordocs) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedordocs", fornecedordocs);
		return "cadFornecedorCidadeDocs";
	}

	public String editar(Fornecedordocs fornecedordocs) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedordocs", fornecedordocs);
		return "cadFornecedorDocs";
	}
	
	public void pesquisar(){
		FornecedorDocsFacade fornecedorDocsFacade = new FornecedorDocsFacade();
		String sql= "select f from Fornecedordocs f where f.nome<>''";
		if(fornecedor!=null && fornecedor.getIdfornecedor()!=null){
			sql = sql + " and f.fornecedor.idfornecedor="+fornecedor.getIdfornecedor();
		}
		if(nome!=null && nome.length()>0){
			sql = sql + " and f.nome like '%"+nome+"%'";
		}   
		listaDocs = fornecedorDocsFacade.listar(sql);
	}
	
	
	public void excluirArquivos(Fornecedordocs fornecedordocs){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedordocs", fornecedordocs);
		session.setAttribute("listaDocs", listaDocs);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("excluirFornecedorCidadeDocs", options, null);
	}
	
	
	@SuppressWarnings("unchecked")
	public void retornoDialogExcluir(SelectEvent event) {
		listaDocs = (List<Fornecedordocs>) event.getObject();
		Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
	}
}
