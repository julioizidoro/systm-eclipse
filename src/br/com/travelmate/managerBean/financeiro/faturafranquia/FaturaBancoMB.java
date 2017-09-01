/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package br.com.travelmate.managerBean.financeiro.faturafranquia;

import br.com.travelmate.facade.FaturaBancoFacade;
import br.com.travelmate.model.Fatura;
import br.com.travelmate.model.Faturabanco;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class FaturaBancoMB implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private Fatura fatura;
	private Faturabanco faturabanco;
	private boolean alteracao;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fatura = (Fatura) session.getAttribute("fatura");
		alteracao = (boolean) session.getAttribute("alteracao");
		session.removeAttribute("alteracao");
		session.removeAttribute("fatura");
		if (fatura != null) {
			FaturaBancoFacade faturaBancoFacade = new FaturaBancoFacade();
			faturabanco = faturaBancoFacade
					.getFatura("select f from Faturabanco f where f.fatura.idfatura=" + fatura.getIdfatura());
			if (faturabanco == null) {
				faturabanco = new Faturabanco();
			}
		}
	}

	public Fatura getFatura() {
		return fatura;
	}

	public void setFatura(Fatura fatura) {
		this.fatura = fatura;
	}

	public Faturabanco getFaturabanco() {
		return faturabanco;
	}

	public void setFaturabanco(Faturabanco faturabanco) {
		this.faturabanco = faturabanco;
	}

	public boolean isAlteracao() {
		return alteracao;
	}

	public void setAlteracao(boolean alteracao) {
		this.alteracao = alteracao;
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String salvarFaturaBanco() {
		if (camposObrigatorios()) {
			FaturaBancoFacade faturaBancoFacade = new FaturaBancoFacade();
			faturabanco.setFatura(fatura);
			faturabanco = faturaBancoFacade.salvar(faturabanco);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			RequestContext.getCurrentInstance().closeDialog(null);
		}
		return "";
	}

	public boolean camposObrigatorios() {
		if (faturabanco.getBanco() == null) {
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Banco não informado.");
			return false;
		} else if (faturabanco.getAgencia() == null) {
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Agencia não informada.");
			return false;
		} else if (faturabanco.getConta() == null) {
			Mensagem.lancarMensagemInfo("Campo obrigatório!", "Conta não informada.");
			return false;
		}
		return true;
	}

}
