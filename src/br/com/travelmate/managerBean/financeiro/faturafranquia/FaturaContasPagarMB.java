/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.faturafranquia;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.FaturaRecebimentoFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.contasPagar.EventoContasPagarBean;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Fatura;
import br.com.travelmate.model.Faturarecebimento;
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
public class FaturaContasPagarMB implements Serializable{
    
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
    private Fatura fatura;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        unidadenegocio = (Unidadenegocio) session.getAttribute("unidade"); 
        String competencia = (String) session.getAttribute("competencia");
        fatura = (Fatura) session.getAttribute("fatura");
        session.removeAttribute("unidade"); 
        session.removeAttribute("competencia");
        session.removeAttribute("fatura");
        carregarDados(competencia);
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        unidadenegocio = unidadeNegocioFacade.consultar(6);
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
    		Faturarecebimento faturaRecebimento = new Faturarecebimento();
    		faturaRecebimento.setContaspagar(conta);
    		faturaRecebimento.setFatura(fatura);
    		FaturaRecebimentoFacade faturaRecebimentoFacade = new FaturaRecebimentoFacade();
    		faturaRecebimentoFacade.salvar(faturaRecebimento);
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
    
    public void carregarDados(String competencia){
    	carregarUnidadeNegocio();
        carregarPlanoConta();
        carregarBanco();
        getUsuarioLogadoMB();
        conta = new Contaspagar();
        conta.setDataEmissao(new Date());
        conta.setCompetencia(competencia);
        conta.setDescricao("Acerto fatura " + competencia + " " + unidadenegocio.getNomeFantasia());
        PlanoContaFacade planoContaFacade = new PlanoContaFacade();
        Planoconta planoconta = planoContaFacade.consultar(3);
        conta.setPlanoconta(planoconta);
        if(unidadenegocio==null || unidadenegocio.getIdunidadeNegocio()==null){
	        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
	        unidadenegocio = unidadeNegocioFacade.consultar(6);
        }
    }
}
