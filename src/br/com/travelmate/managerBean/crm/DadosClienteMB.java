package br.com.travelmate.managerBean.crm;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class DadosClienteMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private String nome;
	private String email;
	private String telefone;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cliente = (Cliente) session.getAttribute("cliente");
		session.removeAttribute("cliente");
		if (cliente != null) {
			nome = cliente.getNome();
			email = cliente.getEmail();
			telefone = cliente.getFoneCelular();
		}
	}
    

	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public boolean validarEmail() {
		if (Formatacao.validarEmail(email)) {
			ClienteFacade clienteFacade = new ClienteFacade();
			Cliente c = clienteFacade.consultarEmail(email);
			if (c != null && c.getIdcliente() != null) {
				if (c.getIdcliente() == cliente.getIdcliente()) {
					return true;
				} else {
					Mensagem.lancarMensagemInfo("Email já cadastrado.", "");
					return false;
				}
			}
		}
		return true;  
	}
	
	
	public void salvar(){
		cliente.setNome(nome);
		cliente.setFoneCelular(telefone);
		if (validarEmail()) {
			cliente.setEmail(email);
			ClienteFacade clienteFacade = new ClienteFacade();
			cliente = clienteFacade.salvar(cliente);
			RequestContext.getCurrentInstance().closeDialog(cliente);
		}
	}
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	

}
