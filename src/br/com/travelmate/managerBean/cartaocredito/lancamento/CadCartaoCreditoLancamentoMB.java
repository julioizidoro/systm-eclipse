package br.com.travelmate.managerBean.cartaocredito.lancamento;

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

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.facade.CartaoCreditoLancamentoFacade;
import br.com.travelmate.facade.PlanoContaFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB; 
import br.com.travelmate.model.Cartaocredito;
import br.com.travelmate.model.Cartaocreditolancamento;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadCartaoCreditoLancamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;
	private Planoconta planoconta;
	private List<Planoconta> listaPlanoConta;
	private List<Cartaocredito> listaCartaoCredito;
	private Cartaocredito cartaocredito;
	private Cartaocreditolancamento lancamento;
	private Moedas moedas;
	private List<Moedas> listaMoedas;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lancamento = (Cartaocreditolancamento) session.getAttribute("lancamento");
		session.removeAttribute("lancamento");
		gerarListaPlanoConta();
		gerarlistaCartaoCredito();
		gerarlistaMoedas();
		cartaocredito = new Cartaocredito();
		planoconta = new Planoconta();
		moedas = new Moedas();
		if (lancamento == null) {
			lancamento = new Cartaocreditolancamento();
			CambioFacade cambioFacade = new CambioFacade();
			moedas = cambioFacade.consultarMoeda(8);
			lancamento.setData(new Date());
			lancamento.setConferido(false);
			lancamento.setLancado(false);
		} else {
			cartaocredito = lancamento.getCartaocredito();
			planoconta = lancamento.getPlanoconta();
			moedas = lancamento.getMoedas();
		}
	}

	public Planoconta getPlanoconta() {
		return planoconta;
	}

	public void setPlanoconta(Planoconta planoconta) {
		this.planoconta = planoconta;
	}

	public List<Planoconta> getListaPlanoConta() {
		return listaPlanoConta;
	}

	public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
		this.listaPlanoConta = listaPlanoConta;
	}

	public List<Cartaocredito> getListaCartaoCredito() {
		return listaCartaoCredito;
	}

	public void setListaCartaoCredito(List<Cartaocredito> listaCartaoCredito) {
		this.listaCartaoCredito = listaCartaoCredito;
	}

	public Cartaocredito getCartaocredito() {
		return cartaocredito;
	}

	public void setCartaocredito(Cartaocredito cartaocredito) {
		this.cartaocredito = cartaocredito;
	}

	public Cartaocreditolancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Cartaocreditolancamento lancamento) {
		this.lancamento = lancamento;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Moedas getMoedas() {
		return moedas;
	}

	public void setMoedas(Moedas moedas) {
		this.moedas = moedas;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public void validarDados(String msg) {
		if (lancamento.getDescricao() == null || lancamento.getDescricao().length() < 2) {
			msg = msg + "\n" + "Descrição não informado.";
		} 
		if (lancamento.getData() == null) {
			msg = msg + "\n" + "Data não informada.";
		}
		if (planoconta==null || planoconta.getIdplanoconta()==null) {
			msg = msg + "\n" + "Plano de conta não informado.";
		}
		if (cartaocredito==null || cartaocredito.getIdcartaocredito()==null) {
			msg = msg + "\n" + "Cartão de Crédito não informado.";
		}
		if (moedas == null || moedas.getIdmoedas() == null) {
			msg = msg + "\n" + "Moeda não informada.";
		}
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			CartaoCreditoLancamentoFacade cartaoCreditoFacade = new CartaoCreditoLancamentoFacade();
			lancamento.setCartaocredito(cartaocredito);
			lancamento.setPlanoconta(planoconta);
			lancamento.setUsuario(usuarioLogadoMB.getUsuario());
			lancamento.setMoedas(moedas);
			lancamento = cartaoCreditoFacade.salvar(lancamento);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			RequestContext.getCurrentInstance().closeDialog(null); 
			return "";
		} else {
			Mensagem.lancarMensagemErro("Atenção!", msg);
			return "";
		}
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public void gerarListaPlanoConta() {
		PlanoContaFacade planoContaFacade = new PlanoContaFacade();
		listaPlanoConta = planoContaFacade.listar("");
		if (listaPlanoConta == null) {
			listaPlanoConta = new ArrayList<Planoconta>();
		}
	}

	public void gerarlistaCartaoCredito() {
		CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
		listaCartaoCredito = cartaoCreditoFacade.listar();
		if (listaCartaoCredito == null) {
			listaCartaoCredito = new ArrayList<Cartaocredito>();
		}
	}

	public void gerarlistaMoedas() {
		CambioFacade cambioFacade = new CambioFacade();
		listaMoedas = cambioFacade.listaMoedas();
		if (listaMoedas == null) {
			listaMoedas = new ArrayList<Moedas>();
		}
	}
}
