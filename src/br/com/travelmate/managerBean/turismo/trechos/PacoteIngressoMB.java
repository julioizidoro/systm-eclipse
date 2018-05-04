/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.trechos;

import br.com.travelmate.facade.PacoteIngressoFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pacoteingresso;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.util.GerarListas;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
public class PacoteIngressoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Pacoteingresso pacoteingresso;
     private List<Pacoteingresso> listaPacoteIngresso;
    private Cambio cambio;
    private Fornecedorcidade fornecedorcidade;
    private List<Paisproduto> listaPais;
    private Cidade cidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    private Pais pais;
    private float valorCambio=0;
    private String pacoteImportado;


    @PostConstruct
    public void init() {
    	
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Pacotetrecho pacotetrecho = (Pacotetrecho) session.getAttribute("pacoteTrecho");
        session.removeAttribute("pacoteTrecho");
        int idProduto = 0;
        if (pacotetrecho != null) {
            PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
            idProduto = pacotetrecho.getPacotes().getVendas().getProdutos().getIdprodutos();
            listaPais = paisProdutoFacade.listar(idProduto);
            listaPacoteIngresso = pacotetrecho.getPacoteingressoList();
        }
        pacoteingresso = new Pacoteingresso();
        pacoteingresso.setPacotetrecho(pacotetrecho);
       
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Pacoteingresso getPacoteingresso() {
        return pacoteingresso;
    }

    public void setPacoteingresso(Pacoteingresso pacoteingresso) {
        this.pacoteingresso = pacoteingresso;
    }

    public List<Pacoteingresso> getListaPacoteIngresso() {
        return listaPacoteIngresso;
    }

    public void setListaPacoteIngresso(List<Pacoteingresso> listaPacoteIngresso) {
        this.listaPacoteIngresso = listaPacoteIngresso;
    }

    public String getPacoteImportado() {
		return pacoteImportado;
	}

	public void setPacoteImportado(String pacoteImportado) {
		this.pacoteImportado = pacoteImportado;
	}

	public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public List<Paisproduto> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<Paisproduto> listaPais) {
        this.listaPais = listaPais;
    }

    

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Fornecedorcidade> getListaFornecedorCidade() {
        return listaFornecedorCidade;
    }

    public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
        this.listaFornecedorCidade = listaFornecedorCidade;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
    
    public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public void listarFornecedorCidade(int idProduto){
        if (usuarioLogadoMB!=null){
            idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
        }
        if ((idProduto>0) && (cidade!=null)){
        	listaFornecedorCidade = GerarListas.listarFornecedorTurismo(idProduto, cidade.getIdcidade(), "Ingresso");
        }
    }
    
    public String salvarIngresso(){
        pacoteingresso.setFornecedorcidade(fornecedorcidade);
        pacoteingresso.setCambio(cambio);
        pacoteingresso.setValorcambio(valorCambio);
        listaPacoteIngresso.add(pacoteingresso);
        Pacotetrecho pacotetrecho = pacoteingresso.getPacotetrecho();
        pacoteingresso = new Pacoteingresso();
        pacoteingresso.setPacotetrecho(pacotetrecho);
        fornecedorcidade = new Fornecedorcidade();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
        return "";
    }
    
    public String cancelar(){
        Pacotetrecho pacotetrecho = pacoteingresso.getPacotetrecho();
        pacoteingresso = new Pacoteingresso();
        pacoteingresso.setPacotetrecho(pacotetrecho);
        fornecedorcidade = new Fornecedorcidade();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Cancelado", ""));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacoteTrecho", pacotetrecho);
        return "pacoteingresso";
    }
    
    public String finalizar(){
    	if(listaPacoteIngresso!=null){
	        for (int i = 0; i < listaPacoteIngresso.size(); i++) {
	        	PacoteIngressoFacade pacoteIngressoFacade = new PacoteIngressoFacade();
	        	pacoteIngressoFacade.salvar(listaPacoteIngresso.get(i));
			}
    	}
        RequestContext.getCurrentInstance().closeDialog("cadPacote");
        return "";
    }
    
    public String excluirItem(Pacoteingresso pacoteingresso){
		PacoteIngressoFacade pacoteIngressoFacade = new PacoteIngressoFacade();
		if (pacoteingresso.getIdpacoteingresso() != null) {
			pacoteIngressoFacade.excluir(pacoteingresso.getIdpacoteingresso());
		}
		listaPacoteIngresso.remove(pacoteingresso);
		pacoteingresso.getPacotetrecho().getPacoteingressoList().remove(pacoteingresso);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("Ingresso Excluído", ""));
		return "";
	}
    
    public void carregarValorCambio(){
		valorCambio = cambio.getValor();
	}
    
    public String desabilitarPacoteImportado(){
    	String habilitado;
    	if(!pacoteImportado.equalsIgnoreCase("Sim")){
    		habilitado = "false";
    	}else{
    		habilitado = "true";
    	}
    	return habilitado;
    }
}
