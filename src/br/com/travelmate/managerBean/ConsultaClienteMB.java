/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean;

import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.model.Cliente;

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

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class ConsultaClienteMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cliente> listaCliente;
    private String nome;
    @Inject 
    UsuarioLogadoMB usuarioLogadoMB;
    private String tipo;
    private  String cpf;
    
    @PostConstruct
    public void init() {
    	if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
	    	FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        listaCliente =  (List<Cliente>) session.getAttribute("listaCliente");
			session.removeAttribute("listaCliente");
	        tipo = (String) session.getAttribute("turismo");
	        session.removeAttribute("turismo");
	        if (tipo==null){
	        	tipo="";
	        }
    	}
	}
    
    public List<Cliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void limpar(){
		nome = "";
		carregarListaCliente();
	}
	
	public String carregarListaCliente(){
        if (nome==null){
            nome = "";
        }
        ClienteFacade clienteFacade = new ClienteFacade();
        String sql = "select c from Cliente c where c.nome like '%" + nome + "%'";
        if (cpf != null && !cpf.equalsIgnoreCase("")) {
			sql = sql + " and c.cpf='" + cpf + "'";
		}
        if (!tipo.equalsIgnoreCase("turismo")){
        	if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")){
            	sql = sql + "  and c.unidadenegocio.idunidadeNegocio="+usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio(); 
            }
        }
        sql = sql + " order by c.nome";
        listaCliente = clienteFacade.listar(sql);
        if (listaCliente==null){
            listaCliente = new ArrayList<Cliente>();
        }
        return "";
    }
    
    public void selecionarCliente(Cliente cliente){
        RequestContext.getCurrentInstance().closeDialog(cliente);
    }
    
    public String novo(){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("listaCliente", listaCliente);
    	return "cadCliente";
    }
    
    public String editar(Cliente cliente){
    	FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("cliente", cliente);
        session.setAttribute("listaCliente", listaCliente);
        return "cadCliente";
    }
}
