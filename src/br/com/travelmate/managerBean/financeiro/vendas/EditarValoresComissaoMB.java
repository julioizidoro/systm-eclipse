/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.vendas;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class EditarValoresComissaoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String titulo;
    private String selecao;
    private Cambio cambio;
    private float valorMoeda;
    private String baseCalculo;
    private double percentual;
    private float novoValorBaseCalculo;
    private float novoValorMoeda;
    private float novoValorManual;
    private float novoValor;
    private List<Cambio> listaCambio;
    private Vendascomissao vendascomissao;
    private String campoAlteracao;
    
    @PostConstruct
    public void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Vendas venda = (Vendas) session.getAttribute("venda");
        session.removeAttribute("venda");
        setTitulo("Venda No. " + String.valueOf(venda.getIdvendas()));
        cambio = new Cambio();
        campoAlteracao = (String) session.getAttribute("campoAlteracao");
        consultarCambio(venda.getDataVenda());
        vendascomissao=venda.getVendascomissao();
        if(vendascomissao==null){
        	vendascomissao = new Vendascomissao();
        	vendascomissao.setVendas(venda);
        }
        selecao= "manual";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSelecao() {
        return selecao;
    }

    public void setSelecao(String selecao) {
        this.selecao = selecao;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public float getValorMoeda() {
        return valorMoeda;
    }

    public void setValorMoeda(float valorMoeda) {
        this.valorMoeda = valorMoeda;
    }

    public String getBaseCalculo() {
        return baseCalculo;
    }

    public void setBaseCalculo(String baseCalculo) {
        this.baseCalculo = baseCalculo;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        this.percentual = percentual;
    }

    public float getNovoValorBaseCalculo() {
        return novoValorBaseCalculo;
    }

    public void setNovoValorBaseCalculo(float novoValorBaseCalculo) {
        this.novoValorBaseCalculo = novoValorBaseCalculo;
    }

    public float getNovoValorMoeda() {
        return novoValorMoeda;
    }

    public void setNovoValorMoeda(float novoValorMoeda) {
        this.novoValorMoeda = novoValorMoeda;
    }

    public float getNovoValor() {
        return novoValor;
    }

    public void setNovoValor(float novoValor) {
        this.novoValor = novoValor;
    }

    
    public List<Cambio> getListaCambio() {
        return listaCambio;
    }

    public void setListaCambio(List<Cambio> listaCambio) {
        this.listaCambio = listaCambio;
    }

    public Vendascomissao getVendascomissao() {
        return vendascomissao;
    }

    public void setVendascomissao(Vendascomissao vendascomissao) {
        this.vendascomissao = vendascomissao;
    }

    public String getCampoAlteracao() {
        return campoAlteracao;
    }

    public void setCampoAlteracao(String campoAlteracao) {
        this.campoAlteracao = campoAlteracao;
    }

    public float getNovoValorManual() {
        return novoValorManual;
    }

    public void setNovoValorManual(float novoValorManual) {
        this.novoValorManual = novoValorManual;
    }
    
    
    
    public void consultarCambio(Date dataCambio){
        CambioFacade cambioFacade = new CambioFacade();
        listaCambio = cambioFacade.listar(Formatacao.ConvercaoDataSql(dataCambio));
        if (listaCambio==null){
            listaCambio = new ArrayList<Cambio>();
        }
        
    }
    
    public void iniciarCalculo(){
        if (selecao.equalsIgnoreCase("basecalculo")){
            iniciarBaseCalculo();
        }else if (selecao.equalsIgnoreCase("moeda")){
            if (cambio==null){
                novoValorMoeda = 0.0f;
            }else if (valorMoeda==0){
                novoValorMoeda=0.0f;
            }else {
                novoValorMoeda = cambio.getValor() * valorMoeda;
                novoValor = novoValorMoeda;
            }
        }else novoValor = novoValorManual;
    }
    
    public void iniciarBaseCalculo(){
        if (baseCalculo.equalsIgnoreCase("valorcomissionavel")){
            baseCalculoValorComissionavel();
        }else if (baseCalculo.equalsIgnoreCase("valortotal")){
            baseCalculoValorTotal();
        }else if (baseCalculo.equalsIgnoreCase("comissaotm")){
            baseCalculoComissaoTM();
        }else if (baseCalculo.equalsIgnoreCase("comTMdescontoMatrizTaxaTMdesagio")){
            baseCalculoComissaoTMDescontoMatrizTaxaTMDesagio();
        }else if (baseCalculo.equalsIgnoreCase("comFranquiaComTerceiros")){
            baseCalculoComissaoFranquiasComissaoTerceiros();
        }
    }
    
    public void baseCalculoValorComissionavel(){
        if (percentual>0){
           novoValorBaseCalculo = (float) (vendascomissao.getValorcomissionavel() * (percentual / 100));
           novoValor = novoValorBaseCalculo;
        }
    }
    
    public void baseCalculoValorTotal(){
        if (percentual>0){
            novoValorBaseCalculo = (float) (vendascomissao.getVendas().getValor() * (percentual/100));
            novoValor = novoValorBaseCalculo;
        }
    }
    
    public void baseCalculoComissaoTM(){
        if (percentual>0){
            novoValorBaseCalculo = (float) (vendascomissao.getComissaotm() * (percentual/100));
            novoValor = novoValorBaseCalculo;
        }
    }
    
    public void baseCalculoComissaoTMDescontoMatrizTaxaTMDesagio(){
        if (percentual>0){
            float base = (vendascomissao.getComissaotm() + vendascomissao.getTaxatm()) - (vendascomissao.getDescontotm() + vendascomissao.getDesagio());
            novoValorBaseCalculo = (float) (base * (percentual/100));
            novoValor = novoValorBaseCalculo;
        }
    }
    
    public void baseCalculoComissaoFranquiasComissaoTerceiros(){
        if (percentual>0){
            float base = vendascomissao.getComissaofraquia() - vendascomissao.getComissaoemissor();
            novoValorBaseCalculo = (float) (base * (percentual/100));
            novoValor = novoValorBaseCalculo;
        }
    }
    
    public String cancalar(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("campoAlteracao", "cancelado");
        session.setAttribute("novoValor", 0.01f);
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    public String confirmar(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("campoAlteracao", campoAlteracao);
        session.setAttribute("novoValor", novoValor);
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
}
