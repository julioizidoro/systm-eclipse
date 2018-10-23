package br.com.travelmate.managerBean.financeiro.recebimento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.HeControleDao;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.RecinternacionalFacade;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Hecontrole;
import br.com.travelmate.model.Recinternacional;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class RecebimentoInternacionalMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private HeControleDao heControleDao;
	private Recinternacional recinternacional;
	private Banco banco;
	private List<Banco> listaBanco;
	private String siglaMoeda;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        recinternacional = (Recinternacional) session.getAttribute("recinternacional");
        session.removeAttribute("recinternacional");
		 geraListaBanco();
		 if (recinternacional != null) {
			banco = recinternacional.getBanco();
			siglaMoeda = recinternacional.getMoedas().getSigla();
		}
	}



	public Recinternacional getRecinternacional() {
		return recinternacional;
	}



	public void setRecinternacional(Recinternacional recinternacional) {
		this.recinternacional = recinternacional;
	}



	public Banco getBanco() {
		return banco;
	}



	public void setBanco(Banco banco) {
		this.banco = banco;
	}



	public List<Banco> getListaBanco() {
		return listaBanco;
	}



	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}
	
	public String getSiglaMoeda() {
		return siglaMoeda;
	}



	public void setSiglaMoeda(String siglaMoeda) {
		this.siglaMoeda = siglaMoeda;
	}


	
	

	public void geraListaBanco(){
		BancoFacade bancoFacade = new BancoFacade();
		listaBanco = bancoFacade.listar();
		if (listaBanco == null) {
			listaBanco = new ArrayList<>();
		}
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Recinternacional());
	}
	
	
	public boolean validarDados(){
		if (banco == null) {
			Mensagem.lancarMensagemInfo("Informe o banco", "");
			return false;
		}
		if (recinternacional.getDatarecebimento() == null) {
			Mensagem.lancarMensagemInfo("Informe a data de Recebimento", "");
			return false;
		}
		return true;
	}
	
	public void  salvar(){
		RecinternacionalFacade recinternacionalFacade = new RecinternacionalFacade();
		recinternacional.setBanco(banco);
		if (validarDados()) {
			recinternacional = recinternacionalFacade.salvar(recinternacional);
			gerarDadosControleHe();
			RequestContext.getCurrentInstance().closeDialog(recinternacional);
		}
	}
	
	
	public void gerarDadosControleHe() {
			Hecontrole hecontrole = heControleDao.consultar("SELECT h FROM Hecontrole h WHERE h.he.vendas.idvendas=" + recinternacional.getVendas().getIdvendas());
			if (hecontrole != null) {
				hecontrole.setComissaorecebida(new Date());
				heControleDao.salvar(hecontrole);
			}
	}
	
	
	

}
