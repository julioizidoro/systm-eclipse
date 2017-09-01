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
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Fornecedorcomissaocursoproduto;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;


/**
 *
 * @author Wolverine
 */
public class ComissaoCursoBean {

    private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Orcamentoprodutosorcamento> listaProdutosGeral;
    private Fornecedorcomissaocurso fornecedorcomissaocurso;
    private List<Parcelamentopagamento> listaParcelamento;
    private Date dataInicioPrograma;
    private float valorComissionavel;
    private float valorComissaoMatriz;
    private float valorComissaoFranquia;
    private Double percentualComissao;
    private float valorJuros;
    private boolean salvarCalculo;
   
       
    public ComissaoCursoBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Orcamentoprodutosorcamento> listaProdutosGeral,
    		Fornecedorcomissaocurso fornecedorcomissaocurso, List<Parcelamentopagamento> listaParcelamento, 
    		Date dataInicioPrograma, Vendascomissao vendascomissao, float valorJuros, boolean salvarCalculo){
    	this.vendasComissao = vendascomissao;
    	this.salvarCalculo =salvarCalculo;
        this.venda = venda;
        this.valorJuros = valorJuros;
        this.dataInicioPrograma = dataInicioPrograma;
        this.aplicacaoMB = aplicacaoMB;
        this.listaProdutosGeral = listaProdutosGeral;
        this.listaParcelamento = listaParcelamento;
        this.dataInicioPrograma = dataInicioPrograma;
        this.fornecedorcomissaocurso = fornecedorcomissaocurso;
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
    
    public Float getValorComissionavel() {
		return valorComissionavel;
	}

	public void setValorComissionavel(Float valorComissionavel) {
		this.valorComissionavel = valorComissionavel;
	}

	public Float getValorComissaoMatriz() {
		return valorComissaoMatriz;
	}

	public void setValorComissaoMatriz(Float valorComissaoMatriz) {
		this.valorComissaoMatriz = valorComissaoMatriz;
	}

	public Float getValorComissaoFranquia() {
		return valorComissaoFranquia;
	}

	public void setValorComissaoFranquia(Float valorComissaoFranquia) {
		this.valorComissaoFranquia = valorComissaoFranquia;
	}

	public Double getPercentualComissao() {
		return percentualComissao;
	}

	public void setPercentualComissao(Double percentualComissao) {
		this.percentualComissao = percentualComissao;
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
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        float jurosFranquia = 0.0f;
        if ((valorJuros<=0) && (vendasComissao.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2)){
        	jurosFranquia=comissaoBean.calcularJurosFranquia(listaParcelamento, dataInicioPrograma);
        	vendasComissao.setCustofinanceirofranquia(vendasComissao.getDesagio() +jurosFranquia);
        }else {
        	vendasComissao.setCustofinanceirofranquia(0.0f);
        }
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(venda.getValor() + vendasComissao.getDescontoloja() + vendasComissao.getDescontotm());
        vendasComissao.setProdutos(venda.getProdutos());
        if (vendasComissao.getDescontofornecedor()>0){
        	float percDescontoFornecedor = (this.valorComissaoFranquia/this.valorComissionavel); 
            this.valorComissaoFranquia = this.valorComissaoFranquia - (this.valorComissaoFranquia * percDescontoFornecedor);
        	this.valorComissionavel = this.valorComissionavel - vendasComissao.getDescontofornecedor();
        	this.valorComissaoMatriz = this.valorComissaoMatriz - (this.valorComissaoMatriz*percDescontoFornecedor);
        }
        vendasComissao.setValorcomissionavel(this.valorComissionavel);
        vendasComissao.setComissaotm(this.valorComissaoMatriz);
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
        	vendasComissao.setComissaofranquiabruta(0.0f);
            vendasComissao.setComissaofraquia(0.0f);
            vendasComissao.setLiquidofranquia(0.0f);
        }else {
        	if (vendasComissao.getVendas().getUnidadenegocio().getPercentualcurso()>0){
        		vendasComissao.setComissaofranquiabruta(valorComissionavel	 * (vendasComissao.getVendas().getUnidadenegocio().getPercentualcurso()/100));
        	}else {
        		vendasComissao.setComissaofranquiabruta(this.valorComissaoFranquia);
        	}
        	vendasComissao.setComissaofraquia(comissaoBean.calcularComissaoFranquia(vendasComissao, 0.0f));
            vendasComissao.setLiquidofranquia(vendasComissao.getComissaofraquia());
        }
        vendasComissao.setVendas(venda);
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorfornecedor(comissaoBean.calcularValorFornecedor(vendasComissao, listaParcelamento));
        vendasComissao.setComissaogerente(comissaoBean.calcularComissaoGerente(vendasComissao));
        vendasComissao.setComissaoemissor(comissaoBean.calcularComissaoEmissor(vendasComissao));
        vendasComissao.setDatainicioprograma(dataInicioPrograma);
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        vendasComissao.setPrevisaopagamento(Formatacao.calcularPrevisaoPagamentoFornecedor(dataInicioPrograma, vendasComissao.getProdutos().getIdprodutos(), aplicacaoMB.getParametrosprodutos().getCursos()));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        
        if (salvarCalculo){
        	FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
        	Formapagamento formapagamento = formaPagamentoFacade.consultar(vendasComissao.getVendas().getIdvendas());
        	vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento,percentualComissao.floatValue(), aplicacaoMB, false,formapagamento);
        }
    }
    
    public void calcularValorComissional() {
    	valorComissaoFranquia = 0.0f;
    	valorComissaoMatriz=0.0f;
    	valorComissionavel = 0.0f;
    	percentualComissao = 0.0;
		if (fornecedorcomissaocurso.getFornecedorcomissaocursoprodutoList() != null) {
			List<Fornecedorcomissaocursoproduto> lista = fornecedorcomissaocurso.getFornecedorcomissaocursoprodutoList();
			if (listaProdutosGeral.size() > 0) {
				if (lista != null) {
					for (int i = 0; i < lista.size(); i++) {
						int codList = lista.get(i).getProdutosorcamento().getIdprodutosOrcamento();
						for (int n = 0; n < listaProdutosGeral.size(); n++) {
							int codBean = listaProdutosGeral.get(n).getProdutosorcamento().getIdprodutosOrcamento();
							if (codList == codBean) {
								valorComissionavel = valorComissionavel
										+ listaProdutosGeral.get(n).getValorMoedaNacional();
								if (venda.getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")){
									Double calculo = listaProdutosGeral.get(n).getValorMoedaNacional() * ((lista.get(i).getPremium()/100));
									valorComissaoFranquia = valorComissaoFranquia + calculo.floatValue();
									if (percentualComissao<lista.get(i).getPremium()){
										percentualComissao = lista.get(i).getPremium();
									}
								}else {
									Double calculo = listaProdutosGeral.get(n).getValorMoedaNacional() * ((lista.get(i).getExpress()/100));
									valorComissaoFranquia = valorComissaoFranquia + calculo.floatValue();
									if (percentualComissao<lista.get(i).getExpress()){
										percentualComissao = lista.get(i).getExpress();
									}
								}
								Double calculo = listaProdutosGeral.get(n).getValorMoedaNacional() * ((lista.get(i).getMatriz()/100));
								valorComissaoMatriz = valorComissaoMatriz + calculo.floatValue();
							}
						}
					}
				}
			}
		}
	}

    
	public Float calcularComissaoFranquiaBruto() {
		Float comissao = 0.0f;
		if (vendasComissao.getValorcomissionavel() > 0) {
			if (fornecedorcomissaocurso.getPercloja() > 0) {
				Double percLoja = fornecedorcomissaocurso.getPercloja() / 100;
				comissao= (float) (vendasComissao.getValorcomissionavel() * percLoja.floatValue());
			}
		}
		return comissao;
	}
    
    
    
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}    
}
