/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean.comissao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Vistos;



/**
 *
 * @author Wolverine
 */
public class ComissaoVistoBean {
	
	private Vendas venda;
    private Vendascomissao vendasComissao;
    private Vistos visto;
    private AplicacaoMB aplicacaoMB;
    private float valorJuros;
    private List<Parcelamentopagamento> listaParcelamento;
    
   
	
    
    
    public ComissaoVistoBean(Vendas venda, Vendascomissao vendascomissao, AplicacaoMB aplicacaoMB, float valorJuros, List<Parcelamentopagamento> listaParcelamento, Vistos visto) {
        this.aplicacaoMB = aplicacaoMB;
        this.visto = visto;
        this.listaParcelamento = listaParcelamento;
    	this.vendasComissao = vendascomissao;
        this.venda = venda;
        this.valorJuros = valorJuros;
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
        vendasComissao.setDescontotm(visto.getDescontomatriz());
        vendasComissao.setDescontoloja(visto.getDescontoloja());
        vendasComissao.setDescontofornecedor(0.0f);
        vendasComissao.setDesagio(comissaoBean.calcularDesagio(listaParcelamento, aplicacaoMB, vendasComissao));
        vendasComissao.setTaxatm(visto.getTaxatm());
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(visto.getTaxaloja() + visto.getTaxatm());
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorfornecedor(0.0f);
        vendasComissao.setValorcomissionavel(vendasComissao.getValorbruto());
        vendasComissao.setComissaotm(0.0f);
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        if ((valorJuros<=0) && (vendasComissao.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2)){
        	vendasComissao.setCustofinanceirofranquia(vendasComissao.getDesagio());
        }else {
        	vendasComissao.setCustofinanceirofranquia(0.0f);
        }
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
        	vendasComissao.setComissaofranquiabruta(0.0f);
            vendasComissao.setComissaofraquia(0.0f);
            vendasComissao.setLiquidofranquia(0.0f);
        }else {
        	float valorComissao = 0.0f;
        	if (vendasComissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")){
        		valorComissao = 100.0f;
        	}else valorComissao = 50.0f;
        	vendasComissao.setComissaofranquiabruta(valorComissao);
            vendasComissao.setComissaofraquia(valorComissao);
            vendasComissao.setLiquidofranquia(valorComissao);
        }
        vendasComissao.setComissaogerente(comissaoBean.calcularComissaoGerente(vendasComissao));
        vendasComissao.setComissaoemissor(comissaoBean.calcularComissaoEmissor(vendasComissao));
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
        Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
        boolean cursoPacote = false;
		if (vendasComissao.getVendas().getVendaspacote()!=null) {
			cursoPacote = true;
		}
        vendasComissao = comissaoBean.salvarComissao(vendasComissao, gerarLitaParcelamento(), 0.0f, aplicacaoMB, false, formapagamento, cursoPacote);
    }
    
            
    public List<Parcelamentopagamento> gerarLitaParcelamento(){
        List<Parcelamentopagamento> listaParcelamento = new ArrayList<Parcelamentopagamento>();
        Parcelamentopagamento p = new Parcelamentopagamento();
        p.setDiaVencimento(new Date());
        p.setFormaPagamento("Dinheiro");
        p.setNumeroParcelas(1);
        if (venda.getVendasMatriz().equalsIgnoreCase("S")){
        	p.setTipoParcelmaneto("Matriz");
        }else {
        	p.setTipoParcelmaneto("loja");
        }
        p.setValorParcela(vendasComissao.getValorbruto());
        p.setValorParcelamento(vendasComissao.getValorbruto());
        listaParcelamento.add(p);
        return listaParcelamento;
    }
    
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}    
}
