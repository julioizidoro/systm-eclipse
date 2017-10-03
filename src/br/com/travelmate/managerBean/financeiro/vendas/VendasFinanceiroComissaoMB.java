/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.vendas;

 
import br.com.travelmate.bean.comissao.CalcularComissaoBean;
import br.com.travelmate.bean.comissao.CalcularComissaoManualBean;
import br.com.travelmate.facade.FaturaFranquiasFacade;
import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Faturafranquias;
import br.com.travelmate.model.Terceiros;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class VendasFinanceiroComissaoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Vendas venda;
    private String titulo;
    private Vendascomissao vendascomissao;
    private List<Terceiros> listaTerceiros;
    private String editarComissaoConsultor = "comissaoconsultor";
    private String editarcomissaotm = "comissaotm";
    private String editardesagio = "desagio";
    private String editarcomissaofranquia = "comissaofranquia";
    private String editarincentivo = "incentivo";
    private String editarcomissaoterceiros = "comissaoterceiros";
    private String editarcomissaogerente = "comissaogerente";
    private String editarcustofinanceiro = "custofinanceiro";
    private Terceiros terceiros;
    CalcularComissaoBean comissaoBean;
    
    @PostConstruct
    private void init(){
    	comissaoBean = new CalcularComissaoBean();
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        venda = (Vendas) session.getAttribute("venda");
        session.removeAttribute("venda");
        if (venda!=null){
            setTitulo("Venda No. " + String.valueOf(venda.getIdvendas()));
            vendascomissao = venda.getVendascomissao();
            if (vendascomissao==null){
            	vendascomissao= new Vendascomissao();
            	vendascomissao.setVendas(venda);
            	vendascomissao.setComissaoemissor(0.0f);
            	vendascomissao.setComissaofranquiabruta(0.0f);
            	vendascomissao.setComissaofraquia(0.0f);
            	vendascomissao.setComissaoterceiros(0.0f);
            	vendascomissao.setComissaogerente(0.0f);
            	vendascomissao.setComissaotm(0.0f);
            	vendascomissao.setDesagio(0.0f);
            	vendascomissao.setDescontoloja(0.0f);
            	vendascomissao.setDescontotm(0.0f);
            	vendascomissao.setIncentivo(0.0f);
            	vendascomissao.setLiquidofranquia(0.0f);
            	vendascomissao.setLiquidovendas(0.0f);
            	vendascomissao.setPaga("Não");
            	vendascomissao.setProdutos(venda.getProdutos());
            	vendascomissao.setTaxatm(0.0f);
            	vendascomissao.setUsuario(venda.getUsuario());
            	vendascomissao.setValorbruto(0.0f);
            	vendascomissao.setValorcomissionavel(0.0f);
            	vendascomissao.setValorfornecedor(0.0f);
            	vendascomissao.setJuros(0.0f);
            	if(vendascomissao.getTerceiros()==null){
	            	TerceirosFacade terceirosFacade = new TerceirosFacade();
	                vendascomissao.setTerceiros(terceirosFacade.consultar(1));
            	}
            }
            gerarListaTerceiros();
            terceiros = vendascomissao.getTerceiros();
        }
    }

    public Vendas getVenda() {
        return venda;
    }

    public void setVenda(Vendas venda) {
        this.venda = venda;
    }

    public String getEditardesagio() {
        return editardesagio;
    }

    public void setEditardesagio(String editardesagio) {
        this.editardesagio = editardesagio;
    }

    public String getEditarcomissaofranquia() {
        return editarcomissaofranquia;
    }

    public void setEditarcomissaofranquia(String editarcomissaofranquia) {
        this.editarcomissaofranquia = editarcomissaofranquia;
    }

    public String getEditarincentivo() {
        return editarincentivo;
    }

    public void setEditarincentivo(String editarincentivo) {
        this.editarincentivo = editarincentivo;
    }

    public String getEditarcomissaoterceiros() {
        return editarcomissaoterceiros;
    }

    public void setEditarcomissaoterceiros(String editarcomissaoterceiros) {
        this.editarcomissaoterceiros = editarcomissaoterceiros;
    }
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditarcomissaotm() {
        return editarcomissaotm;
    }

    public void setEditarcomissaotm(String editarcomissaotm) {
        this.editarcomissaotm = editarcomissaotm;
    }
    
    public Vendascomissao getVendascomissao() {
        return vendascomissao;
    }

    public void setVendascomissao(Vendascomissao vendascomissao) {
        this.vendascomissao = vendascomissao;
    }

    public List<Terceiros> getListaTerceiros() {
        return listaTerceiros;
    }

    public void setListaTerceiros(List<Terceiros> listaTerceiros) {
        this.listaTerceiros = listaTerceiros;
    }

    public String getEditarComissaoConsultor() {
        return editarComissaoConsultor;
    }

    public void setEditarComissaoConsultor(String editarComissaoConsultor) {
        this.editarComissaoConsultor = editarComissaoConsultor;
    }

    public String getEditarcomissaogerente() {
        return editarcomissaogerente;
    }

    public void setEditarcomissaogerente(String editarcomissaogerente) {
        this.editarcomissaogerente = editarcomissaogerente;
    }
    
	public Terceiros getTerceiros() {
		return terceiros;
	}

	public void setTerceiros(Terceiros terceiros) {
		this.terceiros = terceiros;
	}

	public String getEditarcustofinanceiro() {
		return editarcustofinanceiro;
	}

	public void setEditarcustofinanceiro(String editarcustofinanceiro) {
		this.editarcustofinanceiro = editarcustofinanceiro;
	}

	public String nomeGerente(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario usuario = usuarioFacade.consultar(venda.getProdutos().getIdgerente());
        if (usuario==null){
            return "sem nome";
        }else return usuario.getNome();
    }
    
    private void gerarListaTerceiros(){
        TerceirosFacade terceirosFacade = new TerceirosFacade();
        listaTerceiros = terceirosFacade.lista();
        if (listaTerceiros==null){
            listaTerceiros = new ArrayList<Terceiros>();
        }
    }
    
    public String editarInformacoes(String campo){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("campoAlteracao", campo);
        session.setAttribute("venda", venda);
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",800);
        RequestContext.getCurrentInstance().openDialog("editarInformacoes", options, null);
        return "";
    }
    
    public void retornoDialog(SelectEvent event){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String campoAlterado = (String) session.getAttribute("campoAlteracao");
        Float novoValor = (Float) session.getAttribute("novoValor");
        session.removeAttribute("campoAlteracao");
        session.removeAttribute("novoValor");
        setNovoValor(campoAlterado, novoValor);
    }
    
    private void setNovoValor(String campo, float novoValor){
        if (campo.equalsIgnoreCase("comissaotm")){
            novoValorComissaoTM(novoValor);
        }else if (campo.equalsIgnoreCase("desagio")){
            novoValorDessagio(novoValor);
        }else if (campo.equalsIgnoreCase("comissaofranquia")){
            novoValorComissaoFranquia(novoValor);
        }else if (campo.equalsIgnoreCase("incentivo")){
            novoValorIncentivo(novoValor);
        }else if (campo.equalsIgnoreCase("comissaoterceiros")){
            novoValorComissaoTerceiros(novoValor);
        }else if (campo.equalsIgnoreCase("comissaoconsultor")){
            novoValorComissaoConsultor(novoValor);
        }else if (campo.equalsIgnoreCase("comissaogerente")){
            novoValorComissaoGerente(novoValor);
        }else if (campo.equalsIgnoreCase("custofinanceiro")){
            novoValorCustofinanceiro(novoValor);
        }
    }
    
    public String cancelar(){
        return "consVendas";
    }
    
    public String confirmar(){
        VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
        vendascomissao.setTerceiros(terceiros);
        vendascomissao = vendasComissaoFacade.salvar(vendascomissao);
       
        return "consVendas";
    }
    
    public void confirmarAlterarData(){
        VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
        vendasComissaoFacade.salvar(vendascomissao);
    }
    
    public void novoValorComissaoTM(float novoValor){
    	 vendascomissao.setComissaotm(novoValor); 
         calcularLiquidoFraquias();
         calcularLiquidoVendas();
         calcularComissaoGerente();
         calcularComissaoConsultor();
         calcularValorPrevisto();
    }
    
    public void novoValorDessagio(float novoValor){
        //Comissão Franquia = % comissão franquia * Valor Comissionavel + 50% Taxa TM – 50% Desconto Matriz – 100% Desconto Loja + Incentivo
        vendascomissao.setDesagio(novoValor);
        calcularLiquidoFraquias();
        calcularLiquidoVendas();
        calcularComissaoGerente();
        calcularComissaoConsultor();
        
    }
    
    public void novoValorComissaoFranquia(float novoValor){
        vendascomissao.setComissaofranquiabruta(novoValor);
        calcularLiquidoFraquias();
        calcularLiquidoVendas();
        calcularComissaoGerente();
        calcularComissaoConsultor();
    }
    
    public void novoValorIncentivo(float novoValor){
        vendascomissao.setIncentivo(novoValor);
        calcularComissaoGerente();
        calcularComissaoConsultor();
        calcularLiquidoFraquias();
        calcularLiquidoVendas();
    }
    
    public void novoValorComissaoTerceiros(float novoValor){
        vendascomissao.setComissaoterceiros(novoValor);
         calcularLiquidoVendas();
    }
    
    public void novoValorComissaoConsultor(float novoValor){
        vendascomissao.setComissaoemissor(novoValor);
        calcularLiquidoFraquias();
        calcularLiquidoVendas();
    }
    
    public void novoValorComissaoGerente(float novoValor){
        vendascomissao.setComissaogerente(novoValor);
         calcularLiquidoVendas();
    }
    
    public void novoValorCustofinanceiro(float novoValor){
        vendascomissao.setCustofinanceirofranquia(novoValor);
         calcularLiquidoVendas();
    }
    
    public void calcularLiquidoVendas(){
        vendascomissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendascomissao));
    }  
    
    public void calcularComissaoConsultor(){
    	vendascomissao.setComissaoemissor(comissaoBean.calcularComissaoEmissor(vendascomissao));
    }
    
    public void calcularComissaoGerente(){
    	vendascomissao.setComissaogerente(comissaoBean.calcularComissaoGerente(vendascomissao));
    }
    
	public void calcularLiquidoFraquias() {
		if (vendascomissao.getVendas().getUnidadenegocio().getIdunidadeNegocio() > 2) {
			float som = vendascomissao.getComissaofranquiabruta();
			if (vendascomissao.getTaxatm() > 0) {
				som = som + (vendascomissao.getTaxatm() / 2);
			}
			som = som + vendascomissao.getIncentivo();
			float sub = 0.0f;
			if (vendascomissao.getDesagio() > 0) {
				sub = sub + (vendascomissao.getDesagio() / 2);
			}
			if (vendascomissao.getDescontotm() > 0) {
				sub = sub + (vendascomissao.getDescontotm() / 2);
			}
			if (vendascomissao.getDescontoloja() > 0) {
				sub = sub + vendascomissao.getDescontoloja();
			}
			vendascomissao.setComissaofraquia(som - sub);
			vendascomissao.setLiquidofranquia(som - sub);
		} else {
			vendascomissao.setComissaofranquiabruta(0.0f);
			vendascomissao.setComissaofraquia(0.0f);
			vendascomissao.setLiquidofranquia(0.0f);
		}
	}
    
	public void calcularValorPrevisto() {
		int idProdutovendas = vendascomissao.getVendas().getProdutos().getIdgerente();
        boolean cf = false;
        if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getCursos()){
       	 cf = true;
        }else if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getAupair()){
       	 cf = true;
        }else if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getProgramasTeens()){
       	 cf = true;
        }else if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getHighSchool()){
       	 cf = true;
        }else if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getDemipair()){
       	 cf = true;
        }else if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getTrainee()){
       	 cf = true;
        }else if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getWork()){
       	 cf = true;
        }else if (idProdutovendas==aplicacaoMB.getParametrosprodutos().getVoluntariado()){
       	 cf = true;
        }
        if (cf){
       	 vendascomissao.setValorfornecedor(comissaoBean.calcularValorFornecedor(vendascomissao, vendascomissao.getVendas().getFormapagamento().getParcelamentopagamentoList()));
        }
	}
    
    public void recalcular() {
    	CalcularComissaoManualBean ccb = new CalcularComissaoManualBean(aplicacaoMB);
    	try {
    		if (vendascomissao.getIdvendascomissao()!=null){
    			if (vendascomissao.getFaturaFranquias()==null){
    				gerarFaturaFranquia();
    			}
    		}
			ccb.recalcular(vendascomissao);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    		vendascomissao = ccb.getVendascomissao();  
    		Vendas venda = vendascomissao.getVendas();
    		venda.setVendascomissao(vendascomissao);
    		FacesContext fc = FacesContext.getCurrentInstance();
    		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        List<Vendas> listaVendas =  (List<Vendas>) session.getAttribute("listaVendas");
    		int indiceLista = (Integer) session.getAttribute("indiceLsita");
    		session.removeAttribute("indeceLista");
    		listaVendas.set(indiceLista, venda);
    		
    }
    
    public void gerarFaturaFranquia(){
    	Faturafranquias fatura = new Faturafranquias();
    	fatura.setAno(0);
    	fatura.setMes(0);
    	fatura.setApagarfatura(false);
    	fatura.setFatura(false);
    	fatura.setLiquidopagar(0.0f);
    	fatura.setPagomatriz(0.0f);
    	fatura.setPercentualcomissao(0.0f);
    	fatura.setSelecionado(false);
    	fatura.setTotalprodutos(0.0f);
    	fatura.setVendascomissao(vendascomissao);
    	FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
    	try {
			fatura = faturaFranquiasFacade.salvar(fatura);
			vendascomissao.setFaturaFranquias(fatura);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    }
    
    
    public String produtoVendas(){
        if ((venda.getOrcamento()!=null)){
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
            session.setAttribute("venda", venda);
            Map<String,Object> options = new HashMap<String, Object>();
            options.put("contentWidth",570);
            RequestContext.getCurrentInstance().openDialog("produtoVendas", options, null);
        }
        else{
            FacesMessage msg = new FacesMessage("Venda não Possui produtos! ", " ");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "";
    }
}
