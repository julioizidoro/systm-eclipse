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

import org.jfree.data.time.Year;
import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.facade.CartaoCreditoLancamentoContasFacade;
import br.com.travelmate.facade.CartaoCreditoLancamentoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cartaocredito;
import br.com.travelmate.model.Cartaocreditolancamento;
import br.com.travelmate.model.Cartaocreditolancamentocontas;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.util.Formatacao;
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
	private boolean confirmar = false;
	private boolean habilitarValorRecorrente = true;
	private boolean desabiltarMoeda = true;
	private boolean habilitarMoeda = false;
	private int mes;
	private int ano;
	private int nParcelas;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lancamento = (Cartaocreditolancamento) session.getAttribute("lancamento");
		confirmar = (boolean) session.getAttribute("confirmar");
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
			PlanoContaFacade planoContaFacade = new PlanoContaFacade();
			moedas = cambioFacade.consultarMoeda(8);
			planoconta = planoContaFacade.consultar(1);
			lancamento.setData(new Date());
			lancamento.setConferido(false);
			lancamento.setLancado(false);
		} else {
			cartaocredito = lancamento.getCartaocredito();
			planoconta = lancamento.getPlanoconta();
			moedas = lancamento.getMoedas();
		}
		if (confirmar) {
			habilitarValorRecorrente = false;
			desabiltarMoeda = true;
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

	public boolean isConfirmar() {
		return confirmar;
	}

	public void setConfirmar(boolean confirmar) {
		this.confirmar = confirmar;
	}

	public boolean isHabilitarValorRecorrente() {
		return habilitarValorRecorrente;
	}

	public void setHabilitarValorRecorrente(boolean habilitarValorRecorrente) {
		this.habilitarValorRecorrente = habilitarValorRecorrente;
	}

	public boolean isDesabiltarMoeda() {
		return desabiltarMoeda;
	}

	public void setDesabiltarMoeda(boolean desabiltarMoeda) {
		this.desabiltarMoeda = desabiltarMoeda;
	}

	public boolean isHabilitarMoeda() {
		return habilitarMoeda;
	}

	public void setHabilitarMoeda(boolean habilitarMoeda) {
		this.habilitarMoeda = habilitarMoeda;
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
		if (lancamento.getNumeroparcelas() == null || lancamento.getNumeroparcelas().equals("")) {
			msg = msg + "\n" + "Informe número de parcelas.";
		}
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			if (confirmar) {
				lancarContasPagar();
			}else{
				CartaoCreditoLancamentoFacade cartaoCreditoFacade = new CartaoCreditoLancamentoFacade();
				mes = Formatacao.getMesData(lancamento.getData()) + 1;
				ano = Formatacao.getAnoData(lancamento.getData());
				nParcelas = Integer.parseInt(lancamento.getNumeroparcelas());
				lancamento.setCartaocredito(cartaocredito);
				lancamento.setPlanoconta(planoconta);
				lancamento.setUsuario(usuarioLogadoMB.getUsuario());
				lancamento.setMoedas(moedas);
				lancamento.setValorlancado(lancamento.getValorinformado());
				if (lancamento.isHabilitarmoeda()) {
					CambioFacade cambioFacade = new CambioFacade();
					Cambio cambio = cambioFacade.consultarCambioMoeda(Formatacao.ConvercaoDataSql(new Date()), moedas.getIdmoedas());
					if (cambio != null) {
						lancamento.setValorcambio(cambio.getValor());
						lancamento.setValorlancado(lancamento.getValorlancado() * cambio.getValor());
					}
				}
				lancamento.setValorlancado(lancamento.getValorlancado() / Integer.parseInt(lancamento.getNumeroparcelas()));
				if (Formatacao.getDiaData(lancamento.getData()) > lancamento.getCartaocredito().getDatafechamento()) {
					mes = mes + 1;
				}
				String dataLancamento = "" + lancamento.getCartaocredito().getDatavencimento() + "/" + mes + "/"
						+ ano;
				lancamento.setData(Formatacao.ConvercaoStringData(dataLancamento));
				lancamento.setNumeroparcelas(1 + "/" + lancamento.getNumeroparcelas());
				lancamento = cartaoCreditoFacade.salvar(lancamento);
				gerarLancamentoRecorrente();
				Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			} 
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
	
	
	public void lancarContasPagar() {
		if (lancamento.getLancado()) {
			Mensagem.lancarMensagemInfo("Contas a Pagar já está lançado!", "");
		} else {
				Contaspagar contaspagar = new Contaspagar();
				contaspagar.setBanco(lancamento.getCartaocredito().getBanco());
				String comp;
				int mes = Formatacao.getMesData(lancamento.getData());
				if (mes < 10) {
					comp = "0" + mes + "/" + Formatacao.getAnoData(lancamento.getData());
				} else
					comp = mes + "/" + Formatacao.getAnoData(lancamento.getData());
				contaspagar.setCompetencia(comp);
				contaspagar.setDataEmissao(new Date());
				contaspagar.setDescricao(lancamento.getDescricao());
				contaspagar.setPlanoconta(lancamento.getPlanoconta());
				contaspagar.setUnidadenegocio(lancamento.getUsuario().getUnidadenegocio());
				contaspagar.setValorentrada(lancamento.getValorlancado());
				contaspagar.setValorsaida(0.0f);
				contaspagar.setDatacompensacao(lancamento.getData());
				contaspagar.setDatavencimento(lancamento.getData());
				ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
				contaspagar = contasPagarFacade.salvar(contaspagar);
				lancamento.setLancado(true);
				CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
				lancamento = cartaoCreditoLancamentoFacade.salvar(lancamento);
				CartaoCreditoLancamentoContasFacade cartaoCreditoLancamentoContasFacade = new CartaoCreditoLancamentoContasFacade();
				Cartaocreditolancamentocontas cartaocreditolancamentocontas = new Cartaocreditolancamentocontas();
				cartaocreditolancamentocontas.setCartaocreditolancamento(lancamento);
				cartaocreditolancamentocontas.setContaspagar(contaspagar);
				cartaocreditolancamentocontas = cartaoCreditoLancamentoContasFacade.salvar(cartaocreditolancamentocontas);
				Mensagem.lancarMensagemInfo("Contas a Pagar lançado com sucesso!", "");
		}
	}
	
	public void gerarLancamentoRecorrente(){
		CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
		Cartaocreditolancamento lancamentoRecorrente;
		Integer parcelas = 2;
		for (int i = 0; i < nParcelas - 1; i++) {
			mes = mes + 1;
			if (mes > 12) {
				mes = 1;
				ano = ano + 1;
			}
			String dataLancamento = "" + lancamento.getCartaocredito().getDatavencimento() + "/" + mes + "/"
					+ ano;
			lancamentoRecorrente = new Cartaocreditolancamento();
			lancamentoRecorrente.setCartaocredito(lancamento.getCartaocredito());
			lancamentoRecorrente.setConferido(lancamento.getConferido());
			lancamentoRecorrente.setData(Formatacao.ConvercaoStringData(dataLancamento));
			lancamentoRecorrente.setDescricao(lancamento.getDescricao());
			lancamentoRecorrente.setEstabelecimento(lancamento.getEstabelecimento());
			lancamentoRecorrente.setHabilitarmoeda(lancamento.isHabilitarmoeda());
			lancamentoRecorrente.setMoedas(lancamento.getMoedas());
			lancamentoRecorrente.setLancado(lancamento.getLancado());
			lancamentoRecorrente.setNumeroparcelas(parcelas + "/" + nParcelas);
			lancamentoRecorrente.setObservacao(lancamento.getObservacao());
			lancamentoRecorrente.setPlanoconta(lancamento.getPlanoconta());
			lancamentoRecorrente.setUsuario(lancamento.getUsuario());
			lancamentoRecorrente.setValorcambio(lancamento.getValorcambio());
			lancamentoRecorrente.setValorinformado(lancamento.getValorinformado());
			lancamentoRecorrente.setValorlancado(lancamento.getValorlancado());
			lancamentoRecorrente.setValorrecorrente(lancamento.isValorrecorrente());
			cartaoCreditoLancamentoFacade.salvar(lancamentoRecorrente);
			parcelas = parcelas + 1;

			
		}
	}
	
	
	public void desabilitandoMoeda(){
		if (lancamento.isHabilitarmoeda()) {
			desabiltarMoeda = false;
		}else{
			desabiltarMoeda = true;
		}
	}
}
