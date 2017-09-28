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
public class ComissaoDemiPairBean {

    private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Orcamentoprodutosorcamento> listaProdutosGeral;
    private Fornecedorcomissaocurso fornecedorcomissaocurso;
    private List<Parcelamentopagamento> listaParcelamento;
    private Date dataInicioPrograma;
    private Float valorComissionavel;
    private Float valorComissaoMatriz;
    private Float valorComissaoFranquia;
    private Float percentualComissao=0.0f;
    private float valorJuros;
       
    public ComissaoDemiPairBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Orcamentoprodutosorcamento> listaProdutosGeral, Fornecedorcomissaocurso fornecedorcomissaocurso, List<Parcelamentopagamento> listaParcelamento, Date dataInicioPrograma, Vendascomissao vendascomissao, float valorJuros){
    	this.vendasComissao = vendascomissao;
        this.venda = venda;
        this.dataInicioPrograma = dataInicioPrograma;
        this.aplicacaoMB = aplicacaoMB;
        this.listaProdutosGeral = listaProdutosGeral;
        this.listaParcelamento = listaParcelamento;
        this.dataInicioPrograma = dataInicioPrograma;
        this.fornecedorcomissaocurso = fornecedorcomissaocurso;
        this.aplicacaoMB = aplicacaoMB;
        this.valorComissaoFranquia=0.0f;
        this.valorComissaoMatriz=0.0f;
        this.valorComissionavel=0.0f;
        this.valorJuros = valorJuros;
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
        vendasComissao.setDescontofornecedor(comissaoBean.calcularDescontoFornecedor(listaProdutosGeral, aplicacaoMB));
        vendasComissao.setDesagio(comissaoBean.calcularDesagio(listaParcelamento, aplicacaoMB, vendasComissao));
        vendasComissao.setTaxatm(comissaoBean.calcularTaxaTM(listaProdutosGeral, aplicacaoMB));
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(venda.getValor() + vendasComissao.getDescontoloja() + vendasComissao.getDescontotm());
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorcomissionavel(this.valorComissionavel);
        vendasComissao.setComissaotm(this.valorComissaoMatriz);
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
        	vendasComissao.setComissaofranquiabruta(this.valorComissaoFranquia);
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
        vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento, percentualComissao.floatValue(), aplicacaoMB, false, formapagamento, cursoPacote);
    }
    
	public void calcularValorComissional() {
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
									Float calculo = listaProdutosGeral.get(n).getValorMoedaNacional() * ((lista.get(i).getPremium()/100));
									valorComissaoFranquia = valorComissaoFranquia + calculo.floatValue();
									if (percentualComissao<lista.get(i).getPremium()){
										percentualComissao = lista.get(i).getPremium();
									}
								}else {
									Float calculo = listaProdutosGeral.get(n).getValorMoedaNacional() * ((lista.get(i).getExpress()/100));
									valorComissaoFranquia = valorComissaoFranquia + calculo.floatValue();
									if (percentualComissao<lista.get(i).getExpress()){
										percentualComissao = lista.get(i).getExpress();
									}
								}
								Float calculo = listaProdutosGeral.get(n).getValorMoedaNacional() * ((lista.get(i).getMatriz()/100));
								valorComissaoFranquia = valorComissaoFranquia + calculo.floatValue();
							}
						}
					}
				}
			}
		}
	}
	
	public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}    
}
