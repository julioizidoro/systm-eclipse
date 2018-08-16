/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.trechos;


import br.com.travelmate.facade.PacotesHotelFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pacotehotel;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.model.Regravenda;
import br.com.travelmate.model.Usuariopontos;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.Date;
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
public class PacoteHotelMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Pacotehotel pacotehotel;
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
        PacotesHotelFacade pacotehotelFacade = new PacotesHotelFacade();
        pacotehotel = pacotehotelFacade.consultar(pacotetrecho.getIdpacotetrecho());
        if (pacotehotel == null) {
            pacotehotel = new Pacotehotel();
            pacotehotel.setPacotetrecho(pacotetrecho);
            fornecedorcidade = new Fornecedorcidade();
            pais = new Pais();
            cidade = new Cidade();
        } else {
        	valorCambio =  pacotehotel.getValorcambio();
            cambio = pacotehotel.getCambio();
            fornecedorcidade = pacotehotel.getFornecedorcidade();
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

    public int hashCode() {
		return aplicacaoMB.hashCode();
	}

	public String getMascara8() {
		return aplicacaoMB.getMascara8();
	}

	public void setMascara8(String mascara8) {
		aplicacaoMB.setMascara8(mascara8);
	}

	public String getMascara9() {
		return aplicacaoMB.getMascara9();
	}

	public void setMascara9(String mascara9) {
		aplicacaoMB.setMascara9(mascara9);
	}

	public Parametrosprodutos getParametrosprodutos() {
		return aplicacaoMB.getParametrosprodutos();
	}

	public void setParametrosprodutos(Parametrosprodutos parametrosprodutos) {
		aplicacaoMB.setParametrosprodutos(parametrosprodutos);
	}

	public String getIata() {
		return aplicacaoMB.getIata();
	}

	public void setIata(String iata) {
		aplicacaoMB.setIata(iata);
	}

	public String getUds() {
		return aplicacaoMB.getUds();
	}

	public void setUds(String uds) {
		aplicacaoMB.setUds(uds);
	}

	public String getEur() {
		return aplicacaoMB.getEur();
	}

	public void setEur(String eur) {
		aplicacaoMB.setEur(eur);
	}

	public String getGbp() {
		return aplicacaoMB.getGbp();
	}

	public void setGbp(String gbp) {
		aplicacaoMB.setGbp(gbp);
	}

	public String getCad() {
		return aplicacaoMB.getCad();
	}

	public void setCad(String cad) {
		aplicacaoMB.setCad(cad);
	}

	public String getAud() {
		return aplicacaoMB.getAud();
	}

	public void setAud(String aud) {
		aplicacaoMB.setAud(aud);
	}

	public String getNzd() {
		return aplicacaoMB.getNzd();
	}

	public void setNzd(String nzd) {
		aplicacaoMB.setNzd(nzd);
	}

	public String getChf() {
		return aplicacaoMB.getChf();
	}

	public void setChf(String chf) {
		aplicacaoMB.setChf(chf);
	}

	public String getZar() {
		return aplicacaoMB.getZar();
	}

	public void setZar(String zar) {
		aplicacaoMB.setZar(zar);
	}

	public List<Cambio> getListaCambio() {
		return aplicacaoMB.getListaCambio();
	}

	public void setListaCambio(List<Cambio> listaCambio) {
		aplicacaoMB.setListaCambio(listaCambio);
	}

	public String getDatacambio() {
		return aplicacaoMB.getDatacambio();
	}

	public void setDatacambio(String datacambio) {
		aplicacaoMB.setDatacambio(datacambio);
	}

	public String validarMascaraTelefone(boolean digitosTelefone) {
		return aplicacaoMB.validarMascaraTelefone(digitosTelefone);
	}

	public boolean equals(Object obj) {
		return aplicacaoMB.equals(obj);
	}

	public boolean checkBoxTelefone(int numeroTelefone, String qntdNumTelefone) {
		return aplicacaoMB.checkBoxTelefone(numeroTelefone, qntdNumTelefone);
	}

	public void carregarCambioDia(Date datacambiohoje) {
		aplicacaoMB.carregarCambioDia(datacambiohoje);
	}

	public void pontuarRunners(Vendas venda, int numeroSemanas, String programa) {
		aplicacaoMB.pontuarRunners(venda, numeroSemanas, programa);
	}

	public int valirdarRegra(Regravenda regra, Vendas venda, int numeroSemanas, String programa,
			Usuariopontos usuariopontos) {
		return aplicacaoMB.valirdarRegra(regra, venda, numeroSemanas, programa, usuariopontos);
	}

	public String toString() {
		return aplicacaoMB.toString();
	}

	public Pacotehotel getPacotehotel() {
        return pacotehotel;
    }

    public void setPacotehotel(Pacotehotel pacotehotel) {
        this.pacotehotel = pacotehotel;
    }

    

    public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
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
    
    public void listarFornecedorCidade(int idProduto){
        if (usuarioLogadoMB!=null){
            idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
        }
        if ((idProduto>0) && (cidade!=null)){
        	listaFornecedorCidade = GerarListas.listarFornecedorTurismo(idProduto, cidade.getIdcidade(), "Hotel");
        }
    }
    
    public String salvarHotel(){
    	String msg = validarDados();
    	if (msg == null || msg.length() == 0) {
	        PacotesHotelFacade pacotehotelFacade = new PacotesHotelFacade();
	        pacotehotel.setFornecedorcidade(fornecedorcidade);
	        pacotehotel.setCambio(cambio);
	        pacotehotel.setValorcambio(valorCambio);
	        pacotehotel = pacotehotelFacade.salvar(pacotehotel);
	        fornecedorcidade = new Fornecedorcidade();
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
	        RequestContext.getCurrentInstance().closeDialog(pacotehotel);	
		}else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
        return "";
    }
    
    public String validarDados() {
    	String msg = "";
    	if (cambio == null || cambio.getIdcambio() == null) {
			msg = msg + "Informe o câmbio; \n";
		}
    	
    	if (pais == null || pais.getIdpais() == null) {
			msg = msg + "Informe o  pais; \n";
		}
    	
    	if (cidade == null || cidade.getIdcidade() == null) {
			msg = msg + "Informe a cidade; \n";
		}
    	
    	if (fornecedorcidade == null || fornecedorcidade.getIdfornecedorcidade() == null) {
			msg = msg + "Informe o parceiro; \n";
		}
    	return msg;
    }
    
    public String cancelar(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotehotel.getPacotetrecho().getPacotes());
        if (pacotehotel.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }else{
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }
    }
    
    public String excluir(){
        PacotesHotelFacade pacoteHotelFacade = new PacotesHotelFacade();
        pacoteHotelFacade.excluir(pacotehotel.getIdpacoteHotel());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotehotel.getPacotetrecho().getPacotes());
        if (pacotehotel.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
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
}
