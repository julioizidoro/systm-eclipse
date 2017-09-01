/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.facade.OcClienteFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Occliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class ConsultaOcClienteMB implements Serializable{
    
    /**
	 * 
	 */
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private static final long serialVersionUID = 1L;
	private List<Occliente> listaCliente;
    private String nome;
    
    
    public List<Occliente> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Occliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String carregarListaCliente(){
        if (nome==null){
            nome = "";
        }
        OcClienteFacade ocClienteFacade = new OcClienteFacade();
        String sql = "select c from Occliente c where c.nome like '%" + nome + "%'";
        if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento()!=1){
        	sql = sql + " and c.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
        }
        sql = sql + " order by c.nome";
        listaCliente = ocClienteFacade.consultarNome(sql);
        if (listaCliente==null){
            listaCliente = new ArrayList<Occliente>();
        }
        return "";
    }
    
    public void selecionarCliente(Occliente ocCliente){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("ocCliente", ocCliente);
        RequestContext.getCurrentInstance().closeDialog(ocCliente);
    }
}
