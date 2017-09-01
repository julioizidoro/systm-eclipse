package br.com.travelmate.managerBean.seguroviagem;


import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.SeguroPlanosFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.ValorSeguroFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Seguroplanos;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Valoresseguro;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class SeguroViagemMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Cambio cambio;
    private Seguroviagem seguroviagem;
    private Fornecedorcidade fornecedorcidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    private Valoresseguro valoresseguro;
    private List<Valoresseguro> listaValoresSeguro;
    private Date dataCambio;
    private List<Seguroplanos> listaSeguroPlanos;
	private Seguroplanos seguroplanos;

    @PostConstruct
    public void init() {
    	if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
	        FacesContext fc = FacesContext.getCurrentInstance();
	        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
	        Pacotes pacotes = (Pacotes) session.getAttribute("pacote");
	        SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
	        seguroviagem = seguroViagemFacade.consultar(pacotes.getVendas().getIdvendas());
	        iniciarListaFornecedorCidade();
	        if (seguroviagem == null) {
	            seguroviagem = new Seguroviagem();
	            seguroviagem.setVendas(pacotes.getVendas());
	            cambio = new Cambio();
	            fornecedorcidade = new Fornecedorcidade();
	            valoresseguro = new Valoresseguro();
	            seguroplanos = new Seguroplanos();
	        }else{
	            fornecedorcidade = seguroviagem.getValoresseguro().getFornecedorcidade();
	            listarPlanosSeguro();
	            seguroplanos = seguroviagem.getValoresseguro().getSeguroplanos();
	            listarValoresSeguro();
	            valoresseguro = seguroviagem.getValoresseguro();
	        }
	        if (dataCambio==null){
	        	dataCambio = aplicacaoMB.getListaCambio().get(0).getData();
	        }
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

	public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Seguroviagem getSeguroviagem() {
        return seguroviagem;
    }

    public void setSeguroviagem(Seguroviagem seguroviagem) {
        this.seguroviagem = seguroviagem;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }


    public List<Fornecedorcidade> getListaFornecedorCidade() {
        return listaFornecedorCidade;
    }

    public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
        this.listaFornecedorCidade = listaFornecedorCidade;
    }    

    public Valoresseguro getValoresseguro() {
        return valoresseguro;
    }

    public void setValoresseguro(Valoresseguro valoresseguro) {
        this.valoresseguro = valoresseguro;
    }
    
    public List<Valoresseguro> getListaValoresSeguro() {
		return listaValoresSeguro;
	}

	public void setListaValoresSeguro(List<Valoresseguro> listaValoresSeguro) {
		this.listaValoresSeguro = listaValoresSeguro;
	}

	public List<Seguroplanos> getListaSeguroPlanos() {
		return listaSeguroPlanos;
	}

	public void setListaSeguroPlanos(List<Seguroplanos> listaSeguroPlanos) {
		this.listaSeguroPlanos = listaSeguroPlanos;
	}

	public Seguroplanos getSeguroplanos() {
		return seguroplanos;
	}

	public void setSeguroplanos(Seguroplanos seguroplanos) {
		this.seguroplanos = seguroplanos;
	}

	public String salvarSeguro() throws SQLException{
        SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
        seguroviagem.setFornecedor(fornecedorcidade.getFornecedor());
        seguroviagem.setPlano(valoresseguro.getPlano());
        seguroviagem.setPossuiSeguro("Sim");
        seguroviagem.setSeguradora(valoresseguro.getFornecedorcidade().getFornecedor().getNome());
        seguroviagem.setValoresseguro(valoresseguro);
        seguroViagemFacade.salvar(seguroviagem);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
        RequestContext.getCurrentInstance().closeDialog("cadpacotesoperadora");
        return "";
    }
    
    
    public void iniciarListaFornecedorCidade(){
    	PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
        int idProduto = (int) aplicacaoMB.getParametrosprodutos().getCartao01();
        List<Paisproduto> listaPais = paisProdutoFacade.listar(idProduto);
        if (listaPais!=null){
            listaFornecedorCidade =  listaPais.get(0).getProdutos().getFornecedorcidadeList();
        }
    }
    
    public void calcularDataTermino(){
    	seguroviagem.setValoresseguro(valoresseguro);
        if ((seguroviagem.getDataInicio()!=null) && (seguroviagem.getNumeroSemanas()>0)){
        	CambioFacade cambioFacade= new CambioFacade(); 
        	Cambio cambioSeguro = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(dataCambio), valoresseguro.getMoedas().getIdmoedas());
        	if (cambioSeguro!=null){
        		if (seguroviagem.getValoresseguro().getCobranca().equalsIgnoreCase("semana")){
        			seguroviagem.setDataTermino(Formatacao.calcularDataFinalPorDias(seguroviagem.getDataInicio(), seguroviagem.getNumeroSemanas()));
        		}else if (seguroviagem.getValoresseguro().getCobranca().equalsIgnoreCase("diaria")) {
        			seguroviagem.setDataTermino(Formatacao.calcularDataFinal(seguroviagem.getDataInicio(), seguroviagem.getNumeroSemanas()));
        		}
        		float valornacional = seguroviagem.getValoresseguro().getValorgross() * cambioSeguro.getValor();
        		seguroviagem.setValorSeguro(valornacional * seguroviagem.getNumeroSemanas());
        	}	
        }
    }
    
    public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog("cadpacotesoperadora");
        return "";
    }
    
    public String excluir() {
        SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
        seguroViagemFacade.excluir(seguroviagem.getIdseguroViagem());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
        RequestContext.getCurrentInstance().closeDialog("cadpacotesoperadora");
        return "";
    }
    
    public void listarPlanosSeguro() {
		if (fornecedorcidade != null) {
			SeguroPlanosFacade seguroPlanosFacade = new SeguroPlanosFacade();
			String sql = "SELECT s FROM Seguroplanos  s WHERE s.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor() + " AND s.ativo=TRUE ORDER BY s.nome";
			listaSeguroPlanos = seguroPlanosFacade.listar(sql);
		}
		if (listaSeguroPlanos == null) {
			listaSeguroPlanos = new ArrayList<Seguroplanos>();
		}
	}

	public void listarValoresSeguro() {
		if (fornecedorcidade != null && seguroplanos!=null && seguroplanos.getIdseguroplanos()!=null) {
			ValorSeguroFacade valorSeguroFacade = new ValorSeguroFacade();
			String sql;
			sql = "SELECT v FROM Valoresseguro v WHERE v.fornecedorcidade.idfornecedorcidade="
					+ fornecedorcidade.getIdfornecedorcidade() + " AND v.situacao='Ativo'"
					+ " AND v.seguroplanos.idseguroplanos=" + seguroplanos.getIdseguroplanos();
			listaValoresSeguro = valorSeguroFacade.listar(sql);
		}
		if (listaValoresSeguro == null) {
			listaValoresSeguro = new ArrayList<Valoresseguro>();
		}
	}
}
