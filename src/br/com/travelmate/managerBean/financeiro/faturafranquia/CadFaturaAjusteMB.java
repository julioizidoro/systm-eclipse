/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.com.travelmate.managerBean.financeiro.faturafranquia;
 
import br.com.travelmate.facade.FaturaAjusteFacade;
import br.com.travelmate.facade.FaturaFacade;
import br.com.travelmate.facade.FaturaFaturaAjusteFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fatura;
import br.com.travelmate.model.Faturaajustes;
import br.com.travelmate.model.Faturafaturaajuste;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext; 

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class CadFaturaAjusteMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Fatura fatura;
	private Faturaajustes faturaajuste;
	private Faturafaturaajuste faturafaturaajuste;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fatura = (Fatura) session.getAttribute("fatura"); 
		session.removeAttribute("fatura");
		if(fatura!=null){
			faturaajuste = new Faturaajustes();
			faturaajuste.setUnidadenegocio(fatura.getUnidadenegocio());
			faturaajuste.setDatalancamento(new Date());
			faturafaturaajuste = new Faturafaturaajuste();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public Faturaajustes getFaturaajuste() {
		return faturaajuste;
	}

	public void setFaturaajuste(Faturaajustes faturaajuste) {
		this.faturaajuste = faturaajuste;
	}

	public Faturafaturaajuste getFaturafaturaajuste() {
		return faturafaturaajuste;
	}

	public void setFaturafaturaajuste(Faturafaturaajuste faturafaturaajuste) {
		this.faturafaturaajuste = faturafaturaajuste;
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public String salvarFaturaAjuste(){
		if(camposObrigatorios()){
			FaturaAjusteFacade faturaAjusteFacade = new FaturaAjusteFacade();
			faturaajuste.setUsuario(usuarioLogadoMB.getUsuario());
			if(faturaajuste.getCreditodebito().equalsIgnoreCase("D")){
				faturaajuste.setValor(faturaajuste.getValor() * (-1));
			}
			faturaajuste = faturaAjusteFacade.salvar(faturaajuste);
			int mes = fatura.getMes();
			int ano = fatura.getAno();
			if(faturaajuste.getMes()==mes && ano==faturaajuste.getAno()){
				faturafaturaajuste.setFatura(fatura);
			}else{   
				FaturaFacade faturaFacade = new FaturaFacade();
				Fatura novafatura = faturaFacade.getFatura("select f from Fatura f where f.ano=" +faturaajuste.getAno()
						+ " and f.mes=" + faturaajuste.getMes() + " and f.unidadenegocio.idunidadeNegocio="
						+fatura.getUnidadenegocio().getIdunidadeNegocio());
				if(novafatura==null || novafatura.getIdfatura()==null){
					novafatura = new Fatura();
					novafatura.setAno(faturaajuste.getAno());
					novafatura.setMes(faturaajuste.getMes());
					novafatura.setUnidadenegocio(fatura.getUnidadenegocio());
					novafatura.setUsuario(usuarioLogadoMB.getUsuario());
					novafatura = faturaFacade.salvar(novafatura);
				}
				faturafaturaajuste.setFatura(novafatura);
			} 
			faturafaturaajuste.setFaturaajustes(faturaajuste);
			FaturaFaturaAjusteFacade faturaFaturaAjusteFacade = new FaturaFaturaAjusteFacade();
			faturafaturaajuste = faturaFaturaAjusteFacade.salvar(faturafaturaajuste);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			RequestContext.getCurrentInstance().closeDialog(faturafaturaajuste);
		}
		return "";
	}
	
	public boolean camposObrigatorios(){
		if(faturaajuste.getCreditodebito()==null){
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Crédito/Débito não informado.");
			return false;
		}else if(faturaajuste.getMes()==null){
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Mês cobrança não informado.");
			return false;
		}else if(faturaajuste.getAno()==null){
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Ano cobrança não informado.");
			return false;
		}else if(faturaajuste.getDescricao()==null || faturaajuste.getDescricao().length()==0){
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Descrição não informada.");
			return false;
		}else if(faturaajuste.getValor()==null || faturaajuste.getValor()==0){
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Valor não informado.");
			return false;
		}else if(faturaajuste.getMes() >12){
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Mês cobrança invalido.");
			return false;
		}      
		return true;
	}

}
