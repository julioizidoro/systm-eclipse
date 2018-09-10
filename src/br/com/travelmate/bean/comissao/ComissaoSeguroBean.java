/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean.comissao;

import java.util.List;

import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;


/**
 *
 * @author Wolverine
 */
public class ComissaoSeguroBean {

	private Vendas venda;
    private Vendascomissao vendasComissao;
    private AplicacaoMB aplicacaoMB;
    private List<Parcelamentopagamento> listaParcelamento;
    private float valorGross;
    private float valorNet;
    private float descontoloja;
    private float descontomatriz;
    private float valorJuros;
    private boolean seguroAvulso;
    private Seguroviagem seguroviagem;
    
    public ComissaoSeguroBean(AplicacaoMB aplicacaoMB, Vendas venda, List<Parcelamentopagamento> listaParcelamento,  Vendascomissao vendascomissao, float descontoloja, float descontomatriz, float valorJuros, boolean seguroAvulso,Formapagamento formapagamento,Seguroviagem seguroviagem) {
    	this.vendasComissao = vendascomissao;
    	this.aplicacaoMB= aplicacaoMB;
    	this.seguroAvulso = seguroAvulso;
        this.venda = venda;
        this.listaParcelamento = listaParcelamento;
        this.descontoloja = descontoloja;
        this.descontomatriz = descontomatriz;
        this.valorJuros = valorJuros; 
        this.seguroviagem = seguroviagem;
        boolean gerar=true;
        if (vendascomissao != null && vendascomissao.getFaturaFranquias()!=null){
        	if (vendasComissao.getFaturaFranquias().isFatura()){
        		gerar=false;
        	}
        }
        if (gerar){
        	IniciarCalculoComissao(formapagamento); 
        }
    }
    
    public void IniciarCalculoComissao(Formapagamento formapagamento){
    	CalcularComissaoBean comissaoBean = new CalcularComissaoBean();
        if (vendasComissao==null){
            vendasComissao = new Vendascomissao();
            vendasComissao.setVendas(venda);
        }else{
        	if (vendasComissao.getVendas()==null){
        		vendasComissao.setVendas(venda);
        	}
        }
        vendasComissao.setDescontotm(this.descontomatriz);
        vendasComissao.setDescontoloja(this.descontoloja);
        vendasComissao.setDescontofornecedor(0.0f);
        vendasComissao.setDesagio(comissaoBean.calcularDesagio(listaParcelamento, aplicacaoMB, vendasComissao));
        vendasComissao.setTaxatm(0.0f);
        calcularValorComissional();
        vendasComissao.setVendas(venda);
        vendasComissao.setValorbruto(valorGross);
        vendasComissao.setProdutos(venda.getProdutos());
        vendasComissao.setValorcomissionavel(valorGross);
        vendasComissao.setJuros(valorJuros);
        vendasComissao.setComissaotm(valorGross - valorNet);
        vendasComissao.setJurospago(comissaoBean.calcularJurosPagos(listaParcelamento));
        if ((valorJuros<=0) && (vendasComissao.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2)){
        	vendasComissao.setCustofinanceirofranquia(vendasComissao.getDesagio());
        }else {
        	vendasComissao.setCustofinanceirofranquia(0.0f);
        }
        float percComissao = 0.0f;
        if (venda.getUnidadenegocio().getIdunidadeNegocio()<=2){
        	vendasComissao.setComissaofranquiabruta(0.0f);
            vendasComissao.setComissaofraquia(0.0f);
            vendasComissao.setLiquidofranquia(0.0f);
        }else {
        	Float valorFraquia = 0.0f;
        	if (vendasComissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Premium")){
        		valorFraquia = (float) (valorGross * (seguroviagem.getValoresseguro().getComissaopremium()/100));
        		percComissao = seguroviagem.getValoresseguro().getComissaopremium();
        	}else {
        		valorFraquia = (float) (valorGross * (seguroviagem.getValoresseguro().getComissaoexpress()/100));
        		percComissao = seguroviagem.getValoresseguro().getComissaoexpress();
        	}
        vendasComissao.setComissaofranquiabruta(valorFraquia);
        vendasComissao.setComissaofraquia(comissaoBean.calcularComissaoFranquia(vendasComissao, 0.0f));
        vendasComissao.setLiquidofranquia(vendasComissao.getComissaofraquia());
        }
        vendasComissao.setValorfornecedor(valorNet);
        vendasComissao.setComissaogerente(comissaoBean.calcularComissaoGerente(vendasComissao));
        vendasComissao.setComissaoemissor(comissaoBean.calcularComissaoEmissor(vendasComissao));
        vendasComissao.setUsuario(comissaoBean.getGerente(vendasComissao));
        vendasComissao.setLiquidovendas(comissaoBean.calcularTotalComissao(vendasComissao));
        vendasComissao.setDatainicioprograma(seguroviagem.getDataInicio());
        boolean cursoPacote = false;
		if (vendasComissao.getVendas().getVendaspacote()!=null) {
			cursoPacote = true;
		}
        vendasComissao = comissaoBean.salvarComissao(vendasComissao, listaParcelamento, percComissao, aplicacaoMB, seguroAvulso, formapagamento, cursoPacote);
    }
    
    public void calcularValorComissional(){
        valorGross = venda.getValor() + vendasComissao.getDescontotm() + vendasComissao.getDescontoloja();
        valorNet = (seguroviagem.getValoresseguro().getValornet() * seguroviagem.getNumeroSemanas()) * venda.getCambio().getValor();
        //valorNet = valorGross * (venda.getFornecedorcidade().getFornecedorcidadecomissao().getPercentualmatriz()/100);
    }

    public Vendascomissao getVendasComissao() {
		return vendasComissao;
	}

	public void setVendasComissao(Vendascomissao vendasComissao) {
		this.vendasComissao = vendasComissao;
	}
}
