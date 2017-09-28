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
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Valoresaupair;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;


/**
 *
 * @author Wolverine
 */
public class ComissaoAuPairBean {

	private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Orcamentoprodutosorcamento> listaProdutosGeral;
    private List<Parcelamentopagamento> listaParcelamento;
    private Date dataInicioPrograma;
    private Valoresaupair valorAupair; 
    private float valorGross;
    private float cambioVenda;
    private float valorJuros;
    
    
    public ComissaoAuPairBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Orcamentoprodutosorcamento> listaProdutosGeral, float valorCambio, Valoresaupair valoraupair, List<Parcelamentopagamento> listaParcelamento, Date dataInicioPrograma, Vendascomissao vendascomissao, float valorJutos) {
        this.vendasComissao = vendascomissao;
        this.aplicacaoMB = aplicacaoMB;
        this.venda = venda;
        this.listaProdutosGeral = listaProdutosGeral;
        this.valorAupair = valoraupair;
        this.cambioVenda = valorCambio;
        this.listaParcelamento = listaParcelamento;
        this.dataInicioPrograma = dataInicioPrograma;
        this.valorJuros = valorJutos;
        boolean gerar=true;
        if (vendascomissao.getFaturaFranquias()!=null){
        	if (vendasComissao.getFaturaFranquias().isFatura()){
        		gerar=false;
        	}
        }
        if (gerar){
        	conversaoValoresAupair();
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
        vendasComissao.setTaxatm(comissaoBean.calcularTaxaTM(listaProdutosGeral, aplicacaoMB));
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(venda.getValor() + vendasComissao.getDescontoloja() + vendasComissao.getDescontotm());
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorcomissionavel(valorGross);
        vendasComissao.setComissaotm(valorGross);
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        float jurosFranquia = 0.0f;
        if ((valorJuros<=0) && (vendasComissao.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2)){
        	jurosFranquia=comissaoBean.calcularJurosFranquia(listaParcelamento, dataInicioPrograma);
        	vendasComissao.setCustofinanceirofranquia(vendasComissao.getDesagio() +jurosFranquia);
        }else {
        	vendasComissao.setCustofinanceirofranquia(0.0f);
        }
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
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
        vendasComissao.setDatainicioprograma(dataInicioPrograma);
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        vendasComissao.setPrevisaopagamento(Formatacao.calcularPrevisaoPagamentoFornecedor(dataInicioPrograma, vendasComissao.getProdutos().getIdprodutos(), aplicacaoMB.getParametrosprodutos().getCursos()));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
        Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
        boolean cursoPacote = false;
		if (vendasComissao.getVendas().getVendaspacote()!=null) {
			cursoPacote = true;
		}
        vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento, percentualComissaoFranquia(), aplicacaoMB, false, formapagamento, cursoPacote);
    }
    
    public void conversaoValoresAupair(){
        valorGross = valorAupair.getValorgross() * cambioVenda;
    }
    
    public Float percentualComissaoFranquia() {
		float comissao = 0.0f;
		if (vendasComissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")) {
			comissao = valorAupair.getComissaopremium();
		} else {
			comissao = valorAupair.getComissaoexpress();
		}
		return comissao;
	}
        
	public Float calcularComissaoFranquiaBruto() {
		float comissaoLoja = 0.0f;
		if (vendasComissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")) {
			comissaoLoja = this.valorGross * (valorAupair.getComissaopremium()/100);
		} else {
			comissaoLoja = this.valorGross * (valorAupair.getComissaoexpress()/100);
		}
		return comissaoLoja;
	}
    
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}
	
	
    
    
}
