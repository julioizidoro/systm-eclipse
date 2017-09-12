/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

import java.sql.SQLException;
import java.util.List;



import br.com.travelmate.facade.FaturaFranquiasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Faturafranquias;
import br.com.travelmate.model.Orcamentoprodutosorcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;

/**
 *
 * @author Wolverine
 */
public class FaturaFranquiaBean {
    
    private Faturafranquias fatura;
    private boolean seguroAvulso;
    private AplicacaoMB aplicacaoMB;
    
    
    public FaturaFranquiaBean(Vendascomissao vendascomissao, List<Parcelamentopagamento> listaParcelamento, Float percentualComissao, AplicacaoMB aplicacaoMB, boolean seguroAvulso) throws SQLException {
    	this.fatura = vendascomissao.getFaturaFranquias();
    	this.aplicacaoMB = aplicacaoMB;
    	this.seguroAvulso = seguroAvulso;
        if (this.fatura==null || this.fatura.getIdfaturafranquias()==null){
        	FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
        	fatura = faturaFranquiasFacade.getFaturaFranquia(vendascomissao.getIdvendascomissao());
        	if (fatura==null){
        		fatura = new Faturafranquias();
        	}
        }
        if (percentualComissao>=0){
        	fatura.setPercentualcomissao(percentualComissao);
        }
        fatura.setVendascomissao(vendascomissao);
        gerarFaturaFranquias(listaParcelamento, vendascomissao);
    }
    
    public Faturafranquias getFatura() {
		return fatura;
	}

	public void setFatura(Faturafranquias fatura) {
		this.fatura = fatura;
	}

	
    
    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public void gerarFaturaFranquias(List<Parcelamentopagamento> listaParcelamento, Vendascomissao vendascomissao) throws SQLException{
        Float liquido = 0.0f;
        Float pagoMatriz = 0.0f;
        int idseguroparametro = aplicacaoMB.getParametrosprodutos().getSeguroPrivado();
        int idproduto = fatura.getVendascomissao().getVendas().getProdutos().getIdprodutos();
        if (idseguroparametro == idproduto){
        	pagoMatriz =0.0f;
        	if (seguroAvulso){
        		if (vendascomissao.getVendas().getVendasMatriz().equalsIgnoreCase("S")){
        			for (int i = 0; i < listaParcelamento.size(); i++) {
                    	if (!listaParcelamento.get(i).getTipoParcelmaneto().equalsIgnoreCase("Loja")) {
                    		pagoMatriz = pagoMatriz + listaParcelamento.get(i).getValorParcelamento();
                        }
                    }
        		}
        	}
        }else {
        	for (int i = 0; i < listaParcelamento.size(); i++) {
            	if (!listaParcelamento.get(i).getTipoParcelmaneto().equalsIgnoreCase("Loja")) {
            		pagoMatriz = pagoMatriz + listaParcelamento.get(i).getValorParcelamento();
                }
            }
        }
        Float taxaTM = 0.0f;
        if (fatura.getVendascomissao().getTaxatm()>0){
        	taxaTM= fatura.getVendascomissao().getTaxatm()/2;
        }
        Float comissao = fatura.getVendascomissao().getComissaofranquiabruta();
        int idPassagem = aplicacaoMB.getParametrosprodutos().getPassagem();
        int idPacote = aplicacaoMB.getParametrosprodutos().getPacotes();
        Float custoFinanceiroFranquia = 0.0f;
        if ((fatura.getVendascomissao().getVendas().getProdutos().getIdprodutos()!=idPassagem) && 
        		(fatura.getVendascomissao().getVendas().getProdutos().getIdprodutos()!=idPacote)){
        	if (vendascomissao.getCustofinanceirofranquia() > 0) {
    			if (vendascomissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Express")) {
    				custoFinanceiroFranquia = (vendascomissao.getCustofinanceirofranquia() / 3);
    			} else {
    				custoFinanceiroFranquia = (vendascomissao.getCustofinanceirofranquia() / 2);
    			}
    		}
        }
        Float descontoMatriz = 0.0f;
        if (fatura.getVendascomissao().getDescontotm()>0){
        	descontoMatriz = fatura.getVendascomissao().getDescontotm()/2;
        }
        Float valorTotal = fatura.getVendascomissao().getVendas().getValor();
        liquido = comissao + taxaTM + pagoMatriz; 
       
        liquido = liquido - (custoFinanceiroFranquia + descontoMatriz + fatura.getVendascomissao().getDescontoloja() + valorTotal);		
        fatura.setPagomatriz(pagoMatriz);
        fatura.setLiquidopagar(liquido);
        fatura.setTotalprodutos(valorTotal);
        FaturaFranquiasFacade faturaFranquiasFacade = new FaturaFranquiasFacade();
        calcularMesAnoFatura(vendascomissao);
        fatura = faturaFranquiasFacade.salvar(fatura);
    }
    
    
	public void calularTotaprodutos(){
		if(fatura.getVendascomissao().getVendas().getOrcamento()!=null)	{
			if (fatura.getVendascomissao().getVendas().getOrcamento().getOrcamentoprodutosorcamentoList() != null) {
				fatura.setTotalprodutos(somar(fatura.getVendascomissao().getVendas().getOrcamento().getOrcamentoprodutosorcamentoList()));
				if (fatura.getTotalprodutos() == 0) {
					fatura.setTotalprodutos(fatura.getVendascomissao().getValorbruto());
				}
			}
		}
	}
	
	public Float somar(List<Orcamentoprodutosorcamento> lista){
    	float total = 0.0f;
    	int idloja = aplicacaoMB.getParametrosprodutos().getDescontoloja();
    	int idmatriz = aplicacaoMB.getParametrosprodutos().getDescontomatriz();
    	for(int i=0;i<lista.size();i++){
    		int idproduto = lista.get(i).getProdutosorcamento().getIdprodutosOrcamento();
    		if (idproduto==idloja){
    			total = total - lista.get(i).getValorMoedaNacional();
    		}else if (idproduto==idmatriz){
    			total = total - lista.get(i).getValorMoedaNacional();
    		}else total = total + lista.get(i).getValorMoedaNacional();
    	}
    	return total;
    	
    }
	
	public void calcularMesAnoFatura(Vendascomissao vendasComissao){
		int mes =0;
		int ano =0;
		if (fatura.getLiquidopagar()>=0){
			mes = Formatacao.getMesData(vendasComissao.getVendas().getDataVenda()) +1;
			ano = Formatacao.getAnoData(vendasComissao.getVendas().getDataVenda());
			if (mes==12){
				mes = 1;
				ano = ano + 1;
			}else{
				mes = mes + 1;
			}
		}else if (fatura.getLiquidopagar()<0){
			if ((seguroAvulso) && vendasComissao.getProdutos().getIdprodutos()==2){
				mes = Formatacao.getMesData(vendasComissao.getVendas().getDataVenda())+1;
				ano = Formatacao.getAnoData(vendasComissao.getVendas().getDataVenda());
				if (mes==12){
					mes = 1;
					ano = ano + 1;
				}else{
					mes = mes + 1;
				}
			}else if ((vendasComissao.getVendas().getProdutos().getTipo().equalsIgnoreCase("P")) && (vendasComissao.getVendas().getProdutos().getIdprodutos()!=2)){
				mes = Formatacao.getMesData(vendasComissao.getVendas().getDataVenda())+1;
				ano = Formatacao.getAnoData(vendasComissao.getVendas().getDataVenda());
				if (mes==12){
					mes = 1;
					ano = ano + 1;
				}else{
					mes = mes + 1;
				}
			}else {
				if (vendasComissao.getVendas().getUnidadenegocio().getTipo().equalsIgnoreCase("Express")){
					mes = Formatacao.getMesData(vendasComissao.getVendas().getDataVenda()) +1;
					ano = Formatacao.getAnoData(vendasComissao.getVendas().getDataVenda());
					if (mes==12){
						mes = 1;
						ano = ano + 1;
					}else{
						mes = mes + 1;
					}
				}else {
					if (vendasComissao.getVendas().getProdutos().getIdprodutos()==10){
						ano = Formatacao.getAnoData(vendasComissao.getVendas().getDataVenda());
						mes = 10;
					}else {
						if(vendasComissao.getDatainicioprograma()!=null){
							mes = Formatacao.getMesData(vendasComissao.getDatainicioprograma());
							ano = Formatacao.getAnoData(vendasComissao.getDatainicioprograma())+1;
						}
						int mesPagamento = vendasComissao.getVendas().getUnidadenegocio().getMespagamento();
						for(int i=0;i<mesPagamento;i++){
							mes = mes - 1;
							if (mes==1){
								mes = 12;
								ano = ano - 1;
							}
						}
					}
				}
			}
		}
		if (mes>0){
			fatura.setMes(mes);
			fatura.setAno(ano);
		}
		
		
	}
}
