/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.vendas;

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Vendas;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class GerarContasReceberMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private Contasreceber conta;
    private Vendas vendas;
   
    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        vendas = (Vendas) session.getAttribute("vendas");
        session.removeAttribute("vendas");
        conta = new Contasreceber();
        conta.setNumerodocumento(String.valueOf(vendas.getIdvendas()));
    }

    public Contasreceber getConta() {
        return conta;
    }

    public void setConta(Contasreceber conta) {
        this.conta = conta;
    }
    
    public Vendas getVendas() {
        return vendas;
    }

    public void setVendas(Vendas vendas) {
        this.vendas = vendas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

	public String salvar(){
		List<Parcelamentopagamento> listaParcelamento = new ArrayList<Parcelamentopagamento>();
		Parcelamentopagamento parcela = new Parcelamentopagamento();
		parcela.setDiaVencimento(conta.getDatavencimento());
		parcela.setFormaPagamento(conta.getTipodocumento());
		parcela.setNumeroParcelas(Integer.parseInt(conta.getNumeroparcelas()));
		parcela.setTipoParcelmaneto("Matriz");
		parcela.setValorParcela(conta.getValorparcela());
		parcela.setValorParcelamento(0.0f);
		listaParcelamento.add(parcela);
		ContasReceberBean contasReceberBean = new ContasReceberBean(vendas,listaParcelamento, usuarioLogadoMB, conta.getNumerodocumento(), false, null);
        RequestContext.getCurrentInstance().closeDialog(conta);
        return "";
    }

    public String cancelar() {
        RequestContext.getCurrentInstance().closeDialog(null);
        return null;
    }
}
