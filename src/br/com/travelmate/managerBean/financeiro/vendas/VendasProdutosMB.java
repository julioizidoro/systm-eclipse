/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.vendas;

import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Vendas;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class VendasProdutosMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Orcamento orcamento;
    private String titulo;
    private Formapagamento formaPagamento;
    
    @PostConstruct
    private void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Vendas venda = (Vendas) session.getAttribute("venda");
        session.removeAttribute("venda");
        if (venda!=null){
            setTitulo("Produtos da Venda No. " + String.valueOf(venda.getIdvendas()));
            orcamento = venda.getOrcamento();
            FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
            formaPagamento = formaPagamentoFacade.consultar(venda.getIdvendas());
        }
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

	public Formapagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(Formapagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
    
    
    
}
