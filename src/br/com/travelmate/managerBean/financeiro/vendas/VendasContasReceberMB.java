/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.vendas;

import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
public class VendasContasReceberMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vendas venda;
    private String titulo;
    private List<Contasreceber> listaContasReceber;
    private Formapagamento formapagamento;
    
    @PostConstruct
    private void init(){
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        venda = (Vendas) session.getAttribute("venda");
        session.removeAttribute("venda");
        if (venda!=null){
            setTitulo("Venda No. " + String.valueOf(venda.getIdvendas()));
            formapagamento = venda.getFormapagamento();
        }
        carregarContasReceber();
    }

    public Vendas getVenda() {
        return venda;
    }

    public void setVenda(Vendas venda) {
        this.venda = venda;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public List<Contasreceber> getListaContasReceber() {
		return listaContasReceber;
	}

	public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}

	public Formapagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	
	public String gerarStatusImagem(Contasreceber conta){
		String retorno;
        Date data = Formatacao.formatarDataAgora();
        boolean dataVenvida = conta.getDatavencimento().before(data);
        if(conta.getSituacao().equalsIgnoreCase("cc")){
            retorno = "../../resources/img/bolaAzul.png";
        }else if (conta.getDatapagamento()!=null){
        	retorno = "../../resources/img/bolaVerde.png";
        }else if (dataVenvida){
        	 retorno = "../../resources/img/bolaVermelha.png";
        }else {
            return "../../resources/img/bolaAmarela.png";
        }
        return retorno;
    }
    
    public String gerarTitulo(Contasreceber conta){
    	String retorno;
        Date data = Formatacao.formatarDataAgora();
        boolean dataVenvida = conta.getDatavencimento().before(data);
        if(conta.getSituacao().equalsIgnoreCase("cc")){
            retorno = "Cancelado";
        }else if (conta.getDatapagamento()!=null){
        	retorno = "Já recebido";
        }else if (dataVenvida){
        	 retorno = "Em atraso";
        }else {
            return "Dentro do prazo";
        }
        return retorno;
    }
    
    public void carregarContasReceber(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        String sql;
        sql = "Select c from Contasreceber c where c.vendas.idvendas='" 
        		+ venda.getIdvendas() + "' and c.situacao<>'cc' order by c.datavencimento";
        listaContasReceber = contasReceberFacade.listar(sql);
        if (listaContasReceber==null){
        	listaContasReceber = new ArrayList<Contasreceber>();
        }
    }
    
    
    public String retornarCorContaVencida(Contasreceber contasreceber){
		if (contasreceber.getCrmcobrancaconta() != null && contasreceber.getCrmcobrancaconta().getCrmcobranca().getDatafinalizada() == null) {
			return "color:red;";
		}
		return "";
	}
}   
