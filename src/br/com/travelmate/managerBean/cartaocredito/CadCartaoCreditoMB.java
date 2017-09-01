package br.com.travelmate.managerBean.cartaocredito;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Cartaocredito; 
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadCartaoCreditoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private AplicacaoMB aplicacaoMB;
	private Cartaocredito cartaocredito; 
	private Banco banco;
	private List<Banco> listaBanco;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cartaocredito = (Cartaocredito) session.getAttribute("cartaocredito");
		session.removeAttribute("cartaocredito"); 
		gerarListaBanco();
		if (cartaocredito == null) { 
			cartaocredito = new Cartaocredito(); 
		} else{
			banco = cartaocredito.getBanco();
		} 
	}
  
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	} 

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	} 

	public Cartaocredito Cartaocredito() {
		return cartaocredito;
	}

	public void setCartaocredito(Cartaocredito banco) {
		this.cartaocredito = banco;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public Cartaocredito getCartaocredito() {
		return cartaocredito;
	}

	public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public void validarDados(String msg) {  
		if (cartaocredito.getNome() == null || cartaocredito.getNome().length() < 2) {
			msg = msg + "\n" + "Nome não informado.";
		}
		if (cartaocredito.getNumero() == null || cartaocredito.getNumero().length() < 2) {
			msg = msg + "\n" + "Número não informado.";
		}
		if (cartaocredito.getValidade() == null) {
			msg = msg + "\n" + "Validade não informada.";
		}
		if (cartaocredito.getBandeira() == null || cartaocredito.getBandeira().length() < 2) {
			msg = msg + "\n" + "Bandeira não informada.";
		}
		if (cartaocredito.getCv() == null || cartaocredito.getCv().length() < 2) {
			msg = msg + "\n" + "Código de Verificação não informado.";
		}
		if(banco==null || banco.getIdbanco()==null){
			msg = msg + "\n" + "Banco não informado.";
		}
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
			cartaocredito.setBanco(banco);
			cartaocredito = cartaoCreditoFacade.salvar(cartaocredito);
			return "consCartaoCredito";
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}
 
	public String cancelar() {
		return "consCartaoCredito";
	}  
	
	public void gerarListaBanco(){
		BancoFacade bancoFacade = new BancoFacade();
		listaBanco = bancoFacade.listar();
		if (listaBanco==null){
			listaBanco = new ArrayList<Banco>();
		}
	}
	
}
