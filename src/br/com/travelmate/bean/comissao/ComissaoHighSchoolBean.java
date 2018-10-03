/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean.comissao;

import java.util.Date;
import java.util.List;

import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Valoreshighschool;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;


/**
 *
 * @author Wolverine
 */
public class ComissaoHighSchoolBean {

	 private Vendas venda;
	 private Vendascomissao vendasComissao;
	 private AplicacaoMB aplicacaoMB;
	 private List<Orcamentoprodutosorcamento> listaProdutosGeral;
	 private List<Parcelamentopagamento> listaParcelamento;
	 private Valoreshighschool valoreshighschool; 
	 private float valorGross;
	 private float valorNet;
	 private Cambio cambioVenda;
	 private float valorJuros;
	 private Date dataInicioPrograma;
	 private boolean salvarCalculos;
	 private Cambio cambioComissao;
    
    
    public ComissaoHighSchoolBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Orcamentoprodutosorcamento> listaProdutosGeral, Cambio valorCambio,
    		Valoreshighschool valoreshighschool, List<Parcelamentopagamento> listaParcelamento, Vendascomissao vendascomissao, Date datainicio, float valorJuros, boolean salvarCalculos,  Cambio cambioComissao) {
        this.vendasComissao = vendascomissao;
        this.aplicacaoMB = aplicacaoMB;
        this.venda = venda;
        this.valoreshighschool = valoreshighschool;
        this.cambioVenda = valorCambio;
        this.listaParcelamento = listaParcelamento;
        this.listaProdutosGeral = listaProdutosGeral;
        this.valorJuros = valorJuros;
        this.dataInicioPrograma = datainicio;
        this.salvarCalculos = salvarCalculos;
        boolean gerar=true;
        if (vendascomissao.getFaturaFranquias()!=null){
        	if (vendasComissao.getFaturaFranquias().isFatura()){
        		gerar=false;
        	}
        }
        if (gerar){
        	conversaoValores();
            IniciarCalculoComissao();
        }
    }
    
    public void conversaoValores(){
        valorGross = valoreshighschool.getValorgross() * cambioVenda.getValor();
        valorNet = valoreshighschool.getValornet() * cambioVenda.getValor();
    }
    
    public void IniciarCalculoComissao(){
    	CalcularComissaoBean comissaoBean = new CalcularComissaoBean();
    	if (vendasComissao==null){
            vendasComissao = new Vendascomissao();
            vendasComissao.setVendas(venda);
        }else{
        	if (vendasComissao.getVendas()==null){
        		vendasComissao.setVendas(venda);
        	}
        }
    	vendasComissao.setJuros(valorJuros);
        vendasComissao.setDescontotm(comissaoBean.calcularDescontoMatriz(listaProdutosGeral, aplicacaoMB));
        vendasComissao.setDescontoloja(comissaoBean.calcularDescontoLoja(listaProdutosGeral, aplicacaoMB));
        vendasComissao.setDescontofornecedor(0.0f);
        vendasComissao.setDesagio(comissaoBean.calcularDesagio(listaParcelamento, aplicacaoMB, vendasComissao));
        vendasComissao.setTaxatm(comissaoBean.calcularTaxaTM(listaProdutosGeral, aplicacaoMB));
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(venda.getValor() + vendasComissao.getDescontoloja() + vendasComissao.getDescontotm());
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorcomissionavel(valorGross);
        vendasComissao.setComissaotm(valorGross - valorNet);
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        float jurosFranquia = 0.0f;
        if ((valorJuros<=0) && (vendasComissao.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2)){
        	jurosFranquia=comissaoBean.calcularJurosFranquia(listaParcelamento, dataInicioPrograma);
        	vendasComissao.setCustofinanceirofranquia(vendasComissao.getDesagio() +jurosFranquia);
        }else {
        	vendasComissao.setCustofinanceirofranquia(0.0f);
        }
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2 ){
        	vendasComissao.setComissaofranquiabruta(0.0f);
            vendasComissao.setComissaofraquia(0.0f);
            vendasComissao.setLiquidofranquia(0.0f);
        }else {
        	vendasComissao.setComissaofranquiabruta(calcularComissaoFranquiaBruto());
            vendasComissao.setComissaofraquia(comissaoBean.calcularComissaoFranquia(vendasComissao, 0.0f));
            vendasComissao.setLiquidofranquia(vendasComissao.getComissaofraquia());
        }
        vendasComissao.setValorfornecedor(comissaoBean.calcularValorFornecedor(vendasComissao, listaParcelamento));
        vendasComissao.setComissaogerente(comissaoBean.calcularComissaoGerente(vendasComissao));
        vendasComissao.setComissaoemissor(comissaoBean.calcularComissaoEmissor(vendasComissao));
        vendasComissao.setDatainicioprograma(null);
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        //vendasComissao.setPrevisaopagamento(n);
        vendasComissao.setDatainicioprograma(valoreshighschool.getDatainicio());
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        if (salvarCalculos) {
        	FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
    		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
    		boolean cursoPacote = false;
    		if (vendasComissao.getVendas().getVendaspacote()!=null) {
    			cursoPacote = true;
    		}
    		vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento,0.0f, aplicacaoMB, false, formapagamento, cursoPacote);
		}
    }
    
    
    public Float calcularComissaoFranquiaBruto() {
        float comissaoLoja = 0.0f;
        if (cambioComissao != null) {
            if (vendasComissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")) {
                comissaoLoja = valoreshighschool.getComissaopremium() * cambioComissao.getValor();
            } else {
                comissaoLoja = valoreshighschool.getComissaoexpress() * cambioComissao.getValor();
            }
        }
        return comissaoLoja;
    }
    
    public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}
    
    
    
//    public Date gerarDataHighSchol(){
//        int posicao = 0;
//        String mes ="";
//        String ano ="";
//        for(int i=0;i<valoreshighschool.getInicio().length();i++){
//            if (valoreshighschool.getInicio().substring(i).equalsIgnoreCase("/")){
//                posicao = i;
//                i=10000;
//            }
//        }
//        if (posicao>0){
//            mes = valoreshighschool.getInicio().substring(0,posicao);
//            ano = valoreshighschool.getInicio().substring((posicao+1),(valoreshighschool.getInicio().length()-1));
//        }
//        mes = Formatacao.nomeMes(mes);
//        String data = "01/" + mes + "/" + ano;
//        Date dataGerada = Formatacao.ConvercaoStringData(data);
//        return dataGerada;
//    }
}
