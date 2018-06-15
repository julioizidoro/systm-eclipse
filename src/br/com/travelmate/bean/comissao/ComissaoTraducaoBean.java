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
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Traducaojuramentada;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;


/**
 *
 * @author Wolverine
 */
public class ComissaoTraducaoBean {

	private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Parcelamentopagamento> listaParcelamento;
    private float valorJuros;
    private Traducaojuramentada traducao;
    
    
    public ComissaoTraducaoBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Parcelamentopagamento> listaParcelamento, Vendascomissao vendascomissao, Traducaojuramentada traducao) {
        this.vendasComissao = vendascomissao;
        this.aplicacaoMB = aplicacaoMB;
        this.venda = venda;
        this.listaParcelamento = listaParcelamento;
        this.traducao = traducao;
        boolean gerar=true;
        if (vendascomissao.getFaturaFranquias()!=null){
        	if (vendasComissao.getFaturaFranquias().isFatura()){
        		gerar=false;
        	}
        }
        if (gerar){
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
        vendasComissao.setDescontotm(0.0f);
        vendasComissao.setDescontoloja(0.0f);
        vendasComissao.setDescontofornecedor(0.0f);
        vendasComissao.setDesagio(comissaoBean.calcularDesagio(listaParcelamento, aplicacaoMB, vendasComissao));
        vendasComissao.setTaxatm(0.0f);
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(venda.getValor() + vendasComissao.getDescontoloja() + vendasComissao.getDescontotm());
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorcomissionavel(traducao.getAssessoriatm());
        vendasComissao.setComissaotm(traducao.getAssessoriatm());
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        vendasComissao.setCustofinanceirofranquia(0.0f);
        vendasComissao.setComissaofranquiabruta(0.0f);
        vendasComissao.setComissaofraquia(0.0f);
        vendasComissao.setLiquidofranquia(0.0f);
        vendasComissao.setValorfornecedor(0.0f);
        vendasComissao.setComissaogerente(0.0f);
        vendasComissao.setComissaoemissor(vendasComissao.getValorcomissionavel()/2);
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
        Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
        boolean cursoPacote = false;
		vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento, 0.0f, aplicacaoMB, false, formapagamento, cursoPacote);
    }
    
    
        
	
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}
	
	
    
    
}
