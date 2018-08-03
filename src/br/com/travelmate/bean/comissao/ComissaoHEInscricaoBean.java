/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean.comissao;

import java.util.List;

import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;


/**
 *
 * @author Wolverine
 */
public class ComissaoHEInscricaoBean {

    private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Orcamentoprodutosorcamento> listaProdutosGeral;
    private List<Parcelamentopagamento> listaParcelamento;
    private Float valorComissionavel;
    private Float valorComissaoMatriz;
    private Float valorComissaoFranquia;
    private Double percentualComissao;
    private float valorJuros;
    private boolean salvarCalculos;
       
    public ComissaoHEInscricaoBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Orcamentoprodutosorcamento> listaProdutosGeral, List<Parcelamentopagamento> listaParcelamento,
    		Vendascomissao vendascomissao, float valorJuros, boolean salvarCalculos){
    	this.vendasComissao = vendascomissao;
        this.venda = venda;
        this.aplicacaoMB = aplicacaoMB;
        this.listaProdutosGeral = listaProdutosGeral;
        this.listaParcelamento = listaParcelamento;
        this.salvarCalculos = salvarCalculos;
        boolean gerar=true;
        if (vendascomissao.getFaturaFranquias()!=null){
        	if (vendasComissao.getFaturaFranquias().isFatura()){
        		gerar=false;
        	}
        }
        if (gerar){
        	calcularValorComissional();
            IniciarCalculoComissao();
        }
        
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
        vendasComissao.setTaxatm(0.0f);
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(venda.getValor() + vendasComissao.getDescontoloja() + vendasComissao.getDescontotm());
        vendasComissao.setProdutos(venda.getProdutos());
        
        this.valorComissionavel = this.valorComissionavel - vendasComissao.getDescontofornecedor(); 
        vendasComissao.setValorcomissionavel(this.valorComissionavel);
        vendasComissao.setComissaotm(this.valorComissionavel);
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
        	vendasComissao.setComissaofranquiabruta(0.0f);
            vendasComissao.setComissaofraquia(0.0f);
            vendasComissao.setLiquidofranquia(0.0f);
        }else {
       		vendasComissao.setComissaofranquiabruta(valorComissaoFranquia);
        	vendasComissao.setComissaofraquia(vendasComissao.getComissaofranquiabruta());
            vendasComissao.setLiquidofranquia(vendasComissao.getComissaofraquia());
        }
        vendasComissao.setValorfornecedor(0.0f);
        vendasComissao.setComissaogerente(100.00f);
        vendasComissao.setComissaoemissor(comissaoBean.calcularComissaoEmissor(vendasComissao));
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        if (salvarCalculos) {
        	FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
    		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
    		boolean cursoPacote = false;
    		if (vendasComissao.getVendas().getVendaspacote()!=null) {
    			cursoPacote = true;
    		}
    		vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento,percentualComissao.floatValue(), aplicacaoMB, false,formapagamento, cursoPacote);
		}
    }
    
    public void calcularValorComissional() {
    	valorComissaoFranquia = 0.0f;
    	valorComissaoMatriz=0.0f;
    	valorComissionavel = venda.getValor();
    	percentualComissao = 0.0;
    	if (venda.getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")){
			Double calculo = valorComissionavel * 0.5;
			valorComissaoFranquia = valorComissaoFranquia + calculo.floatValue();
			percentualComissao = 50.0;
		}else {
			Double calculo = valorComissionavel * 0.25;
			valorComissaoFranquia = valorComissaoFranquia + calculo.floatValue();
			percentualComissao = 25.0;
		}
	}

    

    
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}    
}
