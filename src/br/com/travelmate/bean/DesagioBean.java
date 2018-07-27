/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean;

import java.util.List;

import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Parcelamentopagamento;

/**
 *
 * @author Wolverine
 */
public class DesagioBean {
    
	private AplicacaoMB aplicacaoMB;
    private List<Parcelamentopagamento> listaParcelamento;
    private Float boleto;
    private Float cartao;
    private Float juros;
    private Float cartaoDebito;
    
    
    

    public DesagioBean(List<Parcelamentopagamento> listaParcelamento, AplicacaoMB aplicacaoMB) {
    	this.aplicacaoMB= aplicacaoMB;
        this.cartao=0.0f;
        this.juros=0.0f;
        this.boleto=0.0f;
        this.cartaoDebito=0.0f;
        this.listaParcelamento = listaParcelamento;
        verificarDesagios();
    }

    public Float getBoleto() {
        return boleto;
    }

    public void setBoleto(Float boleto) {
        this.boleto = boleto;
    }

    public Float getCartao() {
        return cartao;
    }

    public void setCartao(Float cartao) {
        this.cartao = cartao;
    }

    public Float getJuros() {
        return juros;
    }

    public void setJuros(Float juros) {
        this.juros = juros;
    }

    public Float getCartaoDebito() {
        return cartaoDebito;
    }

    public void setCartaoDebito(Float cartaoDebito) {
        this.cartaoDebito = cartaoDebito;
    }
    
    

	public void verificarDesagios() {
		if(listaParcelamento!=null){
			for (int i = 0; i < listaParcelamento.size(); i++) {
				if (listaParcelamento.get(i).getTipoParcelmaneto().equalsIgnoreCase("Matriz")) {
					if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Boleto")) {
						calcularDesagioBoleto(listaParcelamento.get(i));
					} else if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
						calcularDesagioCartao(listaParcelamento.get(i));
					} else if (listaParcelamento.get(i).getFormaPagamento()
							.equalsIgnoreCase("Cartão de crédito autorizado")) {
						calcularDesagioCartao(listaParcelamento.get(i));
					} else if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão débito")) {
						calcularCartaoDebito(listaParcelamento.get(i));
					}
				}
			}
		}
	}
    
    public void calcularDesagioBoleto(Parcelamentopagamento parcela){
        boleto = boleto + (parcela.getNumeroParcelas() * aplicacaoMB.getParametrosprodutos().getBoletos());
    }
    
    public void calcularCartaoDebito(Parcelamentopagamento parcela){
        cartaoDebito = cartaoDebito + (parcela.getValorParcelamento()*(aplicacaoMB.getParametrosprodutos().getCartaodebito()/100));
    }
    
    public void calcularDesagioCartao(Parcelamentopagamento parcela){
       CoeficienteJurosFacade coeficienteJurosFacade = new CoeficienteJurosFacade();
       Coeficientejuros coeficientejuros = coeficienteJurosFacade.consultar(1, "Juros Cartao");
       if (coeficientejuros!=null){
    	   cartao =cartao + ((float) (parcela.getValorParcelamento() * (coeficientejuros.getDesagio()/100)));
       }
    }
    
}
