/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.trechos;

import br.com.travelmate.facade.PacoteTransferFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pacotetransfer;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

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
public class PacoteTransferMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Pacotetransfer pacotetransfer;
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
        PacoteTransferFacade pacoteTransferFacade = new PacoteTransferFacade();
        pacotetransfer = pacoteTransferFacade.consultar(pacotetrecho.getIdpacotetrecho());
        if (pacotetransfer == null) {
            pacotetransfer = new Pacotetransfer();
            pacotetransfer.setPacotetrecho(pacotetrecho);
            fornecedorcidade = new Fornecedorcidade();
            pais = new Pais();
            cidade = new Cidade();
        } else {
        	valorCambio = pacotetransfer.getValorcambio();
            cambio = pacotetransfer.getCambio();
            fornecedorcidade = pacotetransfer.getFornecedorcidade();
            pais = fornecedorcidade.getCidade().getPais();
            cidade = fornecedorcidade.getCidade();
            listarFornecedorCidade(idProduto);
        }
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

	public Pacotetransfer getPacotetransfer() {
        return pacotetransfer;
    }

    public void setPacotetransfer(Pacotetransfer pacotetransfer) {
        this.pacotetransfer = pacotetransfer;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public String getPacoteImportado() {
		return pacoteImportado;
	}

	public void setPacoteImportado(String pacoteImportado) {
		this.pacoteImportado = pacoteImportado;
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

    public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public List<Paisproduto> getListaPais() {
        return listaPais;
    }

    public Cidade getCidade() {
        return cidade;
    }

    
    public void setListaPais(List<Paisproduto> listaPais) {
        this.listaPais = listaPais;
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
    
    public void listarFornecedorCidade(int idProduto){
        if (usuarioLogadoMB!=null){
            idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
        }
        if ((idProduto>0) && (cidade!=null)){
        	listaFornecedorCidade = GerarListas.listarFornecedorTurismo(idProduto, cidade.getIdcidade(), "Transfer");
        }
    }
    
    public String salvarTransfer(){
        PacoteTransferFacade pacoteTransferFacade = new PacoteTransferFacade();
        pacotetransfer.setFornecedorcidade(fornecedorcidade);
        pacotetransfer.setCambio(cambio);
        pacotetransfer.setValorcambio(valorCambio);
        pacotetransfer = pacoteTransferFacade.salvar(pacotetransfer);
        fornecedorcidade = new Fornecedorcidade();
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
        RequestContext.getCurrentInstance().closeDialog(pacotetransfer);
        return "";
    }
    
    
    public String cancelar(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotetransfer.getPacotetrecho().getPacotes());
        if (pacotetransfer.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }else{
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }
    }
    
    public String excluir(){
        PacoteTransferFacade pacoteTransferFacade = new PacoteTransferFacade();
        pacoteTransferFacade.excluir(pacotetransfer.getIdpacotetransfer());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotetransfer.getPacotetrecho().getPacotes());
        if (pacotetransfer.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
            //return "cadpacotesoperadora";
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
    
    public void inverter(){
    	if(pacotetransfer.getDe()!=null && pacotetransfer.getDe().length()>0){
    		if(pacotetransfer.getPara()!=null && pacotetransfer.getPara().length()>0){
    			pacotetransfer.setDeout(pacotetransfer.getPara());
    			pacotetransfer.setParaout(pacotetransfer.getDe());
    		}else Mensagem.lancarMensagemInfo("Preencha Para(In) não preenchido corretamento.", "");
    	}else Mensagem.lancarMensagemInfo("Preencha De(In) não preenchido corretamento.", "");
    }
    
}
