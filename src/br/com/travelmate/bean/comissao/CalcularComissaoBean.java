/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean.comissao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.travelmate.bean.DesagioBean;
import br.com.travelmate.bean.FaturaFranquiaBean; 
import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

/**
 *
 * @author Wolverine
 */
public class CalcularComissaoBean {
    

	public Float calcularDescontoMatriz(List<Orcamentoprodutosorcamento> listaProdutosGeral, AplicacaoMB aplicacaoMB){
		Float descontoMatriz = 0.0f;
        if (listaProdutosGeral!=null){
            descontoMatriz=0.0f;
            int codDM = aplicacaoMB.getParametrosprodutos().getDescontomatriz();
            for (int i=0;i<listaProdutosGeral.size();i++){
                int codBean = listaProdutosGeral.get(i).getProdutosorcamento().getIdprodutosOrcamento();
                if (codDM==codBean){
                    float valorDescontoMatriz =listaProdutosGeral.get(i).getValorMoedaNacional();
                    if (valorDescontoMatriz<0){
                    	valorDescontoMatriz = valorDescontoMatriz * -1;
                    }
                    descontoMatriz = descontoMatriz + valorDescontoMatriz;
                }
            }
        }
        return descontoMatriz;
    }
	
	public Float calcularDescontoLoja(List<Orcamentoprodutosorcamento> listaProdutosGeral, AplicacaoMB aplicacaoMB){
		Float descontoLoja=0.0f;
        if (listaProdutosGeral!=null){
            descontoLoja=0.0f;
            int codDL = aplicacaoMB.getParametrosprodutos().getDescontoloja();
            for (int i=0;i<listaProdutosGeral.size();i++){
                int codBean = listaProdutosGeral.get(i).getProdutosorcamento().getIdprodutosOrcamento();
                if (codDL==codBean){
                    float valordescontoLoja= listaProdutosGeral.get(i).getValorMoedaNacional();
                    if (valordescontoLoja<=0){
                    	valordescontoLoja = valordescontoLoja * -1;
                    }
                    descontoLoja = descontoLoja + valordescontoLoja;
                }
            }
        }
        return descontoLoja;
    }
	
	public Float calcularDescontoFornecedor(List<Orcamentoprodutosorcamento> listaProdutosGeral, AplicacaoMB aplicacaoMB){
		Float descontoFornecedor = 0.0f;
        if (listaProdutosGeral!=null){
            descontoFornecedor=0.0f;
            int codDL = aplicacaoMB.getParametrosprodutos().getPromocaoescola();
            for (int i=0;i<listaProdutosGeral.size();i++){
                int codBean = listaProdutosGeral.get(i).getProdutosorcamento().getIdprodutosOrcamento();
                if (codDL==codBean){
                    float valorDescontoFornecedor  = listaProdutosGeral.get(i).getValorMoedaNacional();
                    if (valorDescontoFornecedor<0){
                    	valorDescontoFornecedor  = valorDescontoFornecedor * -1;
                    }
                    descontoFornecedor = descontoFornecedor + valorDescontoFornecedor;
                }
            }
        }
        return descontoFornecedor;
    }
	
	public Float calcularDesagio(List<Parcelamentopagamento> listaParcelamento, AplicacaoMB aplicacaoMB, Vendascomissao vendacomissao){
		DesagioBean desagioBean = new DesagioBean(listaParcelamento, aplicacaoMB);
	    Float custoFinanceiro = desagioBean.getBoleto() + desagioBean.getCartao() + desagioBean.getJuros() + desagioBean.getCartaoDebito();
	    return custoFinanceiro;
    }
	
	public Float calcularTaxaTM(List<Orcamentoprodutosorcamento> listaProdutosGeral, AplicacaoMB aplicacaoMB){
		Float taxaTM = 0.0f;
        if (listaProdutosGeral!=null){
            taxaTM=0.0f;
            int codDM = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
            for (int i=0;i<listaProdutosGeral.size();i++){
                int codBean = listaProdutosGeral.get(i).getProdutosorcamento().getIdprodutosOrcamento();
                if (codDM==codBean){
                    taxaTM=taxaTM +  listaProdutosGeral.get(i).getValorMoedaNacional();
                }
            }
        }
        return taxaTM;
    }
	
	public float calcularComissaoGerente(Vendascomissao vendasComissao){
        float comGerente=0.0f;
        float base=0.0f;
        if (vendasComissao.getProdutos().getComissaogerente()>0){
            base = vendasComissao.getComissaotm() + vendasComissao.getTaxatm();
            base = base - (vendasComissao.getDescontotm() + vendasComissao.getComissaoterceiros() + vendasComissao.getDesagio());
            if (vendasComissao.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
    			base =base - vendasComissao.getLiquidofranquia();
    		}
            Double perGerente = vendasComissao.getProdutos().getComissaogerente()/100;
            comGerente = base * perGerente.floatValue();
            if (comGerente<0){
            	comGerente = 0.0f;
            }
        }
        return comGerente;
    }
    
    public float calcularComissaoEmissor(Vendascomissao vendasComissao){
    	float percentual = vendasComissao.getVendas().getUnidadenegocio().getPerconsultor().floatValue();
    	if (percentual>0){
    		float calculo = vendasComissao.getComissaotm() + vendasComissao.getTaxatm(); 
    		calculo = calculo - (vendasComissao.getDescontotm() + vendasComissao.getDescontoloja() + vendasComissao.getComissaoterceiros() + vendasComissao.getDesagio());
    		if (vendasComissao.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
    			calculo = calculo - vendasComissao.getLiquidofranquia();
    		}
    		calculo = calculo * (percentual/100);
    		return calculo;
    	}
    	return 0.0f;
    }
    
	public float calcularComissaoFranquia(Vendascomissao vendasComissao, Float perFq) {
		float comFq = 0.0f;
		float sub = 0.0f;
		float som = 0.0f;
		if (perFq>0){
			som = vendasComissao.getValorcomissionavel() * perFq.floatValue();
		}else som = vendasComissao.getComissaofranquiabruta();
		
		if (vendasComissao.getTaxatm() > 0) {
			som = som + (vendasComissao.getTaxatm() / 2);
		}
		som = som + vendasComissao.getIncentivo();
		if (vendasComissao.getCustofinanceirofranquia() > 0) {
			if (vendasComissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Express")) {
				sub = sub + (vendasComissao.getCustofinanceirofranquia() / 3);
			} else {
				sub = sub + (vendasComissao.getCustofinanceirofranquia() / 2);
			}
		}
		
		if (vendasComissao.getDescontotm() > 0) {
			sub = sub + (vendasComissao.getDescontotm() / 2);
		}
		if (vendasComissao.getDescontoloja() > 0) {
			sub = sub + vendasComissao.getDescontoloja();
		}
		comFq = som - sub;
		return comFq;
	}
	
	public float calcularComissaoFranquiaTurismo(Vendascomissao vendasComissao, Float perFq) {
		float comFq = 0.0f;
		float sub = 0.0f;
		float som = 0.0f;
		if (perFq>0){
			som = vendasComissao.getValorcomissionavel() * perFq.floatValue();
		}else som = vendasComissao.getComissaofranquiabruta();
		
		if (vendasComissao.getTaxatm() > 0) {
			som = som + (vendasComissao.getTaxatm() / 2);
		}
		som = som + vendasComissao.getIncentivo();
		if (vendasComissao.getDescontotm() > 0) {
			sub = sub + (vendasComissao.getDescontotm() / 2);
		}
		if (vendasComissao.getDescontoloja() > 0) {
			sub = sub + vendasComissao.getDescontoloja();
		}
		comFq = som - sub;
		return comFq;
	}
	
	public float calcularTotalComissaoTurismo(Vendascomissao vendasComissao){
        float somar = vendasComissao.getComissaotm() + vendasComissao.getTaxatm();
        float subtrair  = (vendasComissao.getLiquidofranquia() + vendasComissao.getComissaogerente() + vendasComissao.getComissaoemissor() + vendasComissao.getDescontotm() + vendasComissao.getDescontoloja() + vendasComissao.getComissaoterceiros());
        return somar - subtrair;
    }
    
    public float calcularTotalComissao(Vendascomissao vendasComissao){
        float somar = vendasComissao.getComissaotm() + vendasComissao.getTaxatm();
        float subtrair  = (vendasComissao.getLiquidofranquia() + vendasComissao.getComissaogerente() + vendasComissao.getComissaoemissor() + vendasComissao.getDesagio() + vendasComissao.getDescontotm() + vendasComissao.getDescontoloja() + vendasComissao.getComissaoterceiros() + vendasComissao.getJurospago());
        return somar - subtrair;
    }
    
    public Vendascomissao salvarComissao(Vendascomissao vendasComissao,List<Parcelamentopagamento> listaParcelamento, float percentualComissao, AplicacaoMB aplicacaoMB, boolean seguroAvulso, Formapagamento formapagamento){
        vendasComissao.setPaga("Não");
        if (vendasComissao.getTerceiros()==null){
        	TerceirosFacade terceirosFacade = new TerceirosFacade();
            vendasComissao.setTerceiros(terceirosFacade.consultar(1));
            vendasComissao.setComissaoterceiros(0.0f);
        }
        if(formapagamento!=null && formapagamento.getValorJuros()!=null && formapagamento.getValorJuros()>0){
        	vendasComissao.setDesagio(0.0f);
        }
        VendasComissaoFacade vendasComissaoFacade= new VendasComissaoFacade();
        vendasComissao = vendasComissaoFacade.salvar(vendasComissao);
        try {
			FaturaFranquiaBean faturaFranquias = new FaturaFranquiaBean(vendasComissao, listaParcelamento, percentualComissao, aplicacaoMB, seguroAvulso);
			vendasComissao.setFaturaFranquias(faturaFranquias.getFatura());
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return vendasComissao;
    }
    
    public Usuario getGerente(Vendascomissao vendasComissao){
    	UsuarioFacade usuarioFacade= new UsuarioFacade();
        Usuario usuario = usuarioFacade.consultar(vendasComissao.getProdutos().getIdgerente());
        return usuario;
    }
    
    public Float valorPagoFornecedor(List<Parcelamentopagamento> listaParcelamento){
    	Float pagoFornecedor =0.0f;
    	if(listaParcelamento!=null){
	    	for (int i = 0; i < listaParcelamento.size(); i++) {
	            if (listaParcelamento.get(i).getTipoParcelmaneto().equalsIgnoreCase("Fornecedor")) {
	                pagoFornecedor = pagoFornecedor + listaParcelamento.get(i).getValorParcelamento();
	            }
	        }
    	}
    	return pagoFornecedor;
    }
    
    public Float calcularValorFornecedor(Vendascomissao vendasComissao, List<Parcelamentopagamento> listaParcelamento){
    	Float valor = vendasComissao.getValorbruto() - (vendasComissao.getComissaotm() + vendasComissao.getTaxatm() + valorPagoFornecedor(listaParcelamento));
    	if (vendasComissao.getVendas().getValorcambio()>0) {
    		valor = valor / vendasComissao.getVendas().getValorcambio();
    	}else if (vendasComissao.getVendas().getCambio().getValor()>0){
    		valor = valor / vendasComissao.getVendas().getCambio().getValor();
    	}else {
    		valor =0.0f;
    	}
    	return valor;
    }
    
    public void pegarListaVendas(int idInical){
    	VendasFacade vendasFacade = new VendasFacade();
    	String sql = "SELECT v From Vendas v WHERE v.dataVenda>='2016-07-01'  order by v.idvendas";
    	List<Vendas> listaVendas = vendasFacade.lista(sql);
    	if (listaVendas!=null){
    		for(int i=0;i<listaVendas.size();i++){
    			apagarVendasComissao(listaVendas.get(i));
    		}
    	}
    }
    
    public void apagarVendasComissao(Vendas venda){
    	VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
    	String sql = "SELECT v FROM Vendascomissao v where v.vendas.idvendas=" + venda.getIdvendas() + " order by v.idvendascomissao";
    	List<Vendascomissao> listaComissao = vendasComissaoFacade.listar(sql);
    	if (listaComissao!=null){
    		for(int i=0;i<listaComissao.size();i++){
    			vendasComissaoFacade.excluir(listaComissao.get(i).getIdvendascomissao());
    		}
    	}
    }
    
    public float calcularJurosPagos(List<Parcelamentopagamento> listaParcelamento){
    	float jurosPago = 0.0f;
    	if(listaParcelamento!=null){
    		CoeficienteJurosFacade coeficienteJurosFacade = new CoeficienteJurosFacade();
    		Coeficientejuros coeficientejuros = null;
			for (int i = 0; i < listaParcelamento.size(); i++) {
				if (listaParcelamento.get(i).getTipoParcelmaneto().equalsIgnoreCase("Matriz")) {
					if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
						coeficientejuros = coeficienteJurosFacade.consultar(listaParcelamento.get(i).getNumeroParcelas(), "Juros Cartao");
						if (coeficientejuros!=null){
							jurosPago = (float) (jurosPago + (listaParcelamento.get(i).getValorParcelamento() * (coeficientejuros.getCoeficiente()/100)));
						}
					} else if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Financiamento banco")) {
						coeficientejuros = coeficienteJurosFacade.consultar(listaParcelamento.get(i).getNumeroParcelas(), "Juros Financiamento");
						if (coeficientejuros!=null){
							jurosPago = (float) (jurosPago + (listaParcelamento.get(i).getValorParcelamento() * (coeficientejuros.getCoeficiente()/100)));
						}
					}
				}
			}
		}
    	return jurosPago;
    }
    
    public float calcularJurosFranquia(List<Parcelamentopagamento> listaParcelamento,  Date dataEmbarque){
    	float jurosFranquia = 0.0f;
    	if(listaParcelamento!=null){
    		for (int i = 0; i < listaParcelamento.size(); i++) {
    			if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
    				int parcelasRestantes = calcularNumeroParcelasJuros(dataEmbarque, listaParcelamento.get(i).getDiaVencimento(), listaParcelamento.get(i).getNumeroParcelas());
    				if (parcelasRestantes>0){
    					CoeficienteJurosFacade coeficienteJurosFacade = new CoeficienteJurosFacade();
    					Coeficientejuros coeficientejuros = coeficienteJurosFacade.consultar(parcelasRestantes, "Juros Franquia");
    					if (coeficientejuros!=null){
    						jurosFranquia = jurosFranquia + (float) ((listaParcelamento.get(i).getValorParcela() * parcelasRestantes) *  (coeficientejuros.getCoeficiente()/100));
    					}
    				}
    			}
    		}
    	}
    	return jurosFranquia;
    }
    
    public int calcularNumeroParcelasJuros(Date dataEmbarque, Date dataParcela, int numeroParcelas){
    	int parcelasRestantes = 0;
    	int anoParcela = Formatacao.getAnoData(dataParcela);
    	int mesParcela = Formatacao.getMesData(dataParcela) + 1;
    	int anoEmbarque = Formatacao.getAnoData(dataEmbarque);
    	int mesEmbarque = Formatacao.getMesData(dataEmbarque) + 1;
    	for (int i=0;i<numeroParcelas;i++){
    		if (anoParcela==anoEmbarque){
    			if(mesParcela<mesEmbarque){
    				if (mesParcela==12){
    					mesParcela=1;
    					anoParcela= anoParcela+1;
    				}else {
    					mesParcela++;
    				}
    			}else {
    				parcelasRestantes = numeroParcelas - i;
    				i = 1000;
    			}
    		}else{
    			if (mesParcela==12){
    				mesParcela=1;
    				anoParcela++;
    			}else {
    				mesParcela++;
    			}
    		}
    	}
    	return parcelasRestantes;
    }
    
  

    
}
