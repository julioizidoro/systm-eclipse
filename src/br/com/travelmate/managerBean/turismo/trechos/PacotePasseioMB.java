/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.trechos;

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

import br.com.travelmate.facade.PacotePasseioFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pacotepasseio;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.util.GerarListas;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class PacotePasseioMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Pacotepasseio pacotepasseio;
    private Cambio cambio;
    private Fornecedorcidade fornecedorcidade;
    private List<Paisproduto> listaPais;
    private Cidade cidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    private Pais pais;
    private List<Pacotepasseio> listaPacotePasseio;
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
            listaPacotePasseio = pacotetrecho.getPacotepasseioList();
        }
        
        pacotepasseio = new Pacotepasseio();
        pacotepasseio.setPacotetrecho(pacotetrecho);
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

	public String getPacoteImportado() {
		return pacoteImportado;
	}

	public void setPacoteImportado(String pacoteImportado) {
		this.pacoteImportado = pacoteImportado;
	}

	public Pacotepasseio getPacotepasseio() {
        return pacotepasseio;
    }

    public void setPacotepasseio(Pacotepasseio pacotepasseio) {
        this.pacotepasseio = pacotepasseio;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
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

    public List<Pacotepasseio> getListaPacotePasseio() {
        return listaPacotePasseio;
    }

    public void setListaPacotePasseio(List<Pacotepasseio> listaPacotePasseio) {
        this.listaPacotePasseio = listaPacotePasseio;
    }
    
    public void listarFornecedorCidade(int idProduto){
        if (usuarioLogadoMB!=null){
            idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
        }
        if ((idProduto>0) && (cidade!=null)){
        	listaFornecedorCidade = GerarListas.listarFornecedorTurismo(idProduto, cidade.getIdcidade(), "Passeio");
        }
    }
    
    public String salvarPasseio(){
        pacotepasseio.setFornecedorcidade(fornecedorcidade);
        pacotepasseio.setCambio(cambio);
        pacotepasseio.setValorcambio(valorCambio);
        listaPacotePasseio.add(pacotepasseio);
        Pacotetrecho pacotetrecho = pacotepasseio.getPacotetrecho();
        pacotepasseio = new Pacotepasseio();
        pacotepasseio.setPacotetrecho(pacotetrecho);
        fornecedorcidade = new Fornecedorcidade();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
        return "";
    }
   
    public String cancelar(){
        Pacotetrecho pacotetrecho = pacotepasseio.getPacotetrecho();
        pacotepasseio = new Pacotepasseio();
        pacotepasseio.setPacotetrecho(pacotetrecho);
        fornecedorcidade = new Fornecedorcidade();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacoteTrecho", pacotetrecho);
        context.addMessage(null, new FacesMessage("Cancelado", ""));
        return "pacotepasseio";
    }
    
    public String finalizar(){
    	if(listaPacotePasseio!=null){
	        for (int i = 0; i < listaPacotePasseio.size(); i++) {
	        	PacotePasseioFacade pacotePasseioFacade = new PacotePasseioFacade();
	        	pacotePasseioFacade.salvar(listaPacotePasseio.get(i));
			}
    	}
       RequestContext.getCurrentInstance().closeDialog("cadPacote");
       return "";
    }
    
    public String excluirItem(Pacotepasseio pacotepasseio){
        PacotePasseioFacade pacotePasseioFacdde = new PacotePasseioFacade();
        if (pacotepasseio.getIdpacotepasseio() != null) {
    			pacotePasseioFacdde.excluir(pacotepasseio.getIdpacotepasseio());
		}
        listaPacotePasseio.remove(pacotepasseio);
        pacotepasseio.getPacotetrecho().getPacotepasseioList().remove(pacotepasseio);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Passeio Excluído", ""));
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
