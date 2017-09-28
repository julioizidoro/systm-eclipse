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
import br.com.travelmate.model.Valoresprogramasteens;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;



/**
 *
 * @author Wolverine
 */
public class ComissaoProgramasTeensBean {

	
	private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Orcamentoprodutosorcamento> listaProdutosGeral;
    private List<Parcelamentopagamento> listaParcelamento;
    private Date dataInicioPrograma;
    private Valoresprogramasteens valoresProgramasTeens; 
    private float valorGross;
    private float valorNet;
    private float cambioVenda;
    private float valorJuros;
    
    
    public ComissaoProgramasTeensBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Orcamentoprodutosorcamento> listaProdutosGeral, float valorCambio, Valoresprogramasteens valoresprogramasteens, List<Parcelamentopagamento> listaParcelamento, Date dataInicioPrograma, Vendascomissao vendascomissao, float valorJuros) {
        this.vendasComissao = vendascomissao;
        this.aplicacaoMB = aplicacaoMB;
        this.venda = venda;
        this.listaProdutosGeral = listaProdutosGeral;
        this.valoresProgramasTeens = valoresprogramasteens;
        this.cambioVenda = valorCambio;
        this.listaParcelamento = listaParcelamento;
        this.valorJuros = valorJuros;
        this.dataInicioPrograma = dataInicioPrograma;
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
        valorGross = valoresProgramasTeens.getValorgross() * cambioVenda;
        valorNet = valoresProgramasTeens.getValornet()* cambioVenda;
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
        vendasComissao.setDescontofornecedor(comissaoBean.calcularDescontoFornecedor(listaProdutosGeral, aplicacaoMB));
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
        vendasComissao.setPrevisaopagamento(Formatacao.calcularPrevisaoPagamentoFornecedor(dataInicioPrograma, vendasComissao.getProdutos().getIdprodutos(), aplicacaoMB.getParametrosprodutos().getProgramasTeens()));
        vendasComissao.setDatainicioprograma(dataInicioPrograma);
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
    		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
    		boolean cursoPacote = false;
    		if (vendasComissao.getVendas().getVendaspacote()!=null) {
    			cursoPacote = true;
    		}
    		vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento, 0.0f, aplicacaoMB, false, formapagamento, cursoPacote);
    }
    
	public Float calcularComissaoFranquiaBruto() {
		float comissaoLoja = 0.0f;
		if (venda.getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")) {
			comissaoLoja = valoresProgramasTeens.getComissaopremium() * cambioVenda;
		} else comissaoLoja = valoresProgramasTeens.getComissaoexpress() * cambioVenda;
		return comissaoLoja;
	}
    
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}
    
}
