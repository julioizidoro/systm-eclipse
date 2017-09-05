package br.com.travelmate.managerBean.controleDocsVideos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.FornecedorCidadeDocsFacade;
import br.com.travelmate.model.Fornecedorcidadedocs;
import br.com.travelmate.model.Fornecedordocs;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class ExcluirFornecedorCidadeDocsMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean todos;
	private Fornecedordocs fornecedordocs;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedordocs = (Fornecedordocs) session.getAttribute("fornecedordocs");
		session.removeAttribute("fornecedordocs");
	}



	public boolean isTodos() {
		return todos;
	}



	public void setTodos(boolean todos) {
		this.todos = todos;
	}



	public Fornecedordocs getFornecedordocs() {
		return fornecedordocs;
	}



	public void setFornecedordocs(Fornecedordocs fornecedordocs) {
		this.fornecedordocs = fornecedordocs;
	}
	
	
	
	
	public void selecionarTodos(){
		for (int i = 0; i < fornecedordocs.getFornecedorcidadedocsList().size(); i++) {
			fornecedordocs.getFornecedorcidadedocsList().get(i).setSelecionado(todos);
		}
	}
	
	
	public void excluir(){
		boolean excluiu = false;
		List<Fornecedorcidadedocs> listaFornecedorCidadeDocs = new ArrayList<>();
		Fornecedorcidadedocs fornecedorcidadedocs;
		if (fornecedordocs.getFornecedorcidadedocsList() != null) {
			for (int i = 0; i < fornecedordocs.getFornecedorcidadedocsList().size(); i++) {
				if (fornecedordocs.getFornecedorcidadedocsList().get(i).isSelecionado()) {
					FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
					fornecedorcidadedocs = fornecedordocs.getFornecedorcidadedocsList().get(i);
					fornecedorCidadeDocsFacade.excluir(fornecedorcidadedocs.getIdfornecedorcidadedocs());
					excluiu = true;
					listaFornecedorCidadeDocs.add(fornecedordocs.getFornecedorcidadedocsList().get(i));
				}
			}
			if (excluiu) {
				for (int i = 0; i < listaFornecedorCidadeDocs.size(); i++) {
					fornecedordocs.getFornecedorcidadedocsList().remove(listaFornecedorCidadeDocs.get(i));
				}
				Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
			}
		}
	}
	

	
	
	
}
