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

import br.com.travelmate.facade.PacotesCarroFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pacotecarro;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class PacoteCarroMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
    private AplicacaoMB aplicacaoMB;
    private Pacotecarro pacotecarro;
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
        }
        
        PacotesCarroFacade pacoteCarroFacade = new PacotesCarroFacade();
        pacotecarro = pacoteCarroFacade.consultar(pacotetrecho.getIdpacotetrecho());
        if (pacotecarro == null) {
            pacotecarro = new Pacotecarro();
            pacotecarro.setPacotetrecho(pacotetrecho);
            fornecedorcidade = new Fornecedorcidade();
            pais = new Pais();
            cidade = new Cidade();
        } else {
        	valorCambio = pacotecarro.getValorcambio();
            cambio = pacotecarro.getCambio();
            fornecedorcidade = pacotecarro.getFornecedorcidade();
            pais = fornecedorcidade.getCidade().getPais();
            cidade = fornecedorcidade.getCidade();
            listarFornecedorCidade(String.valueOf(idProduto));
        }
    }
    
    

    public String getPacoteImportado() {
		return pacoteImportado;
	}



	public void setPacoteImportado(String pacoteImportado) {
		this.pacoteImportado = pacoteImportado;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}



	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Pacotecarro getPacotecarro() {
        return pacotecarro;
    }

    public void setPacotecarro(Pacotecarro pacotecarro) {
        this.pacotecarro = pacotecarro;
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

    public float getValorCambio() {
		return valorCambio;
	}



	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
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
    
    public void listarFornecedorCidade(String id){
        int idProduto = Integer.parseInt(id);
        if (usuarioLogadoMB!=null){
            idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
        }
        if ((idProduto>0) && (cidade!=null)){
            listaFornecedorCidade = GerarListas.listarFornecedorTurismo(idProduto, cidade.getIdcidade(), "Carro");
        }
    }
    
    public String salvarCarro(){
    	String msg = validarDados();
    	if (msg == null || msg.length() == 0) {
	        PacotesCarroFacade pacotesCarroFacade = new PacotesCarroFacade();
	        pacotecarro.setFornecedorcidade(fornecedorcidade);
	        pacotecarro.setCambio(cambio);
	        pacotecarro.setValorcambio(valorCambio);
	        pacotecarro = pacotesCarroFacade.salvar(pacotecarro);
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
	        RequestContext.getCurrentInstance().closeDialog(pacotecarro);	
		}else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
        return "";
    }
    
    public String cancelar(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotecarro.getPacotetrecho().getPacotes());
        if (pacotecarro.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }else{
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }
    }
    
    public String excluir(){
        PacotesCarroFacade pacotesCarroFacade = new PacotesCarroFacade();
        pacotesCarroFacade.excluir(pacotecarro.getIdpacoteCarro());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotecarro.getPacotetrecho().getPacotes());
        if (pacotecarro.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }else{
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }
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
    
    
    public String validarDados() {
    	String msg = "";
    	if (pais == null || pais.getIdpais() == null) {
			msg = msg + "Informe o pais; \n";
		}
    	if (cidade == null || cidade.getIdcidade() == null) {
			msg = msg + "Informe a cidade; \n";
		}
    	if (fornecedorcidade == null || fornecedorcidade.getIdfornecedorcidade() == null) {
			msg = msg + "Informe o parceiro; \n";
		}
    	return msg;
    }
}
