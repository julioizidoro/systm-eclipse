/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.contasPagar;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;


@Named
@ViewScoped
public class CadContasPagarMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Contaspagar conta;
    private List<Unidadenegocio> listaUnidadeNegocio;
    private List<Planoconta> listaPlanoConta;
    private List<Banco> listaBanco;
    private Unidadenegocio unidadenegocio;
    private Banco banco;
    private Planoconta planoconta; 
    private Contaspagar contaRepetir;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        conta = (Contaspagar) session.getAttribute("contapagar");
        session.removeAttribute("contapagar");
        carregarUnidadeNegocio();
        carregarPlanoConta();
        carregarBanco();
        getUsuarioLogadoMB();
        if (conta==null){
            conta = new Contaspagar();
        }
        conta.setDataEmissao(new Date());
        if(conta.getUnidadenegocio()!=null){
        	unidadenegocio = conta.getUnidadenegocio();
        }else{
        	unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
        }
    }

    public Contaspagar getConta() {
        return conta;
    }

    public void setConta(Contaspagar conta) {
        this.conta = conta;
    }

    public List<Unidadenegocio> getListaUnidadeNegocio() {
        return listaUnidadeNegocio;
    }

    public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
        this.listaUnidadeNegocio = listaUnidadeNegocio;
    }

    public List<Planoconta> getListaPlanoConta() {
        return listaPlanoConta;
    }

    public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
        this.listaPlanoConta = listaPlanoConta;
    }

    public List<Banco> getListaBanco() {
        return listaBanco;
    }

    public void setListaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Planoconta getPlanoconta() {
        return planoconta;
    }

    public void setPlanoconta(Planoconta planoconta) {
        this.planoconta = planoconta;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Contaspagar getContaRepetir() {
		return contaRepetir;
	}

	public void setContaRepetir(Contaspagar contaRepetir) {
		this.contaRepetir = contaRepetir;
	}

	public String confirmarContaPagar(){
        return "confContasPagar";
    }
    
    public void salvar(){
    	if (conta.getCompetencia().length()>0){
    		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
    		String tipo="";
    		if (conta.getIdcontaspagar()==null){
    			tipo = "Nova conta";
    		}else tipo= "Alteração de conta";
    		conta.setUnidadenegocio(unidadenegocio);
    		if(conta.getValorentrada()==null){
    			conta.setValorentrada(0.0f);
    		}
    		if(conta.getValorsaida()==null){
    			conta.setValorsaida(0.0f);
    		}
    		conta = contasPagarFacade.salvar(conta);
    		EventoContasPagarBean enContasPagarBean = new EventoContasPagarBean(tipo, conta);
    		RequestContext.getCurrentInstance().closeDialog(conta);
    	}else {
    		Mensagem.lancarMensagemInfo("Erro Lançamento", "Campo competencia obrigatório");
    	}
    }
    
    public void carregarUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listarContasPagar();
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
     
    public void carregarPlanoConta(){
        PlanoContaFacade planoContaFacade = new PlanoContaFacade();
        listaPlanoConta = planoContaFacade.listar("");
        if (listaPlanoConta==null){
            listaPlanoConta = new ArrayList<Planoconta>();
        }
    }
     
   public void carregarBanco(){
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco==null){
            listaBanco = new ArrayList<Banco>();
        }
    } 
   
   public String cancelar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
   
   public String adicionarPlanoConta(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",400);
        RequestContext.getCurrentInstance().openDialog("planoconta", options, null);
        return "";
    }
   
    public void preencherDataCompensacao(){
    	if (conta.getDatavencimento()!=null){
    		conta.setDatacompensacao(conta.getDatavencimento());
    	}
    }
     
    
    public void repetir(){
        ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
        contaRepetir = new Contaspagar();
        contaRepetir.setBanco(conta.getBanco());
        contaRepetir.setCompetencia(conta.getCompetencia());
        contaRepetir.setDatacompensacao(conta.getDatacompensacao());
        contaRepetir.setDataEmissao(conta.getDataEmissao());
        contaRepetir.setDatavencimento(conta.getDatavencimento());
        contaRepetir.setDescricao(conta.getDescricao());
        contaRepetir.setPlanoconta(conta.getPlanoconta());
        contaRepetir.setUnidadenegocio(conta.getUnidadenegocio());
        contaRepetir.setValorentrada(conta.getValorentrada());
        contaRepetir.setValorsaida(conta.getValorsaida());
        contaRepetir = contasPagarFacade.salvar(contaRepetir);
        Mensagem.lancarMensagemInfo("Lançamento Repetido", "");
    }
}
