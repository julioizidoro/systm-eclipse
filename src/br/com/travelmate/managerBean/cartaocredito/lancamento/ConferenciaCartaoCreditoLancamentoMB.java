package br.com.travelmate.managerBean.cartaocredito.lancamento;

import java.io.Serializable; 
import java.util.Date; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
 
import br.com.travelmate.facade.CartaoCreditoLancamentoFacade;
import br.com.travelmate.facade.ContasPagarFacade; 
import br.com.travelmate.managerBean.UsuarioLogadoMB;  
import br.com.travelmate.model.Cartaocreditolancamento;
import br.com.travelmate.model.Contaspagar; 
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ConferenciaCartaoCreditoLancamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB; 
	private Cartaocreditolancamento lancamento; 
	private boolean lancado;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		lancamento = (Cartaocreditolancamento) session.getAttribute("lancamento");
		session.removeAttribute("lancamento");  
		if(lancamento.getLancado()==true){
			lancado=false;
		}else lancado=true;
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
 
	public void validarDados(String msg) {
		if (lancamento.getValorcambio() == null) {
			msg = msg + "\n" + "Câmbio não informado.";
		} 
		if (lancamento.getValorlancado() == null) {
			msg = msg + "\n" + "Valor Lançado não informado.";
		} 
	}

	public String salvar() {
		String msg = "";
		validarDados(msg);
		if (msg.length() < 2) {
			CartaoCreditoLancamentoFacade cartaoCreditoFacade = new CartaoCreditoLancamentoFacade(); 
			lancamento.setConferido(true);
			lancamento = cartaoCreditoFacade.salvar(lancamento);
			if(lancamento.getLancado()==true && lancado){
				lancarContasPagar();
			}
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
	
	
	public void lancarContasPagar(){
		Contaspagar contaspagar = new Contaspagar();
		contaspagar.setBanco(lancamento.getCartaocredito().getBanco());
		String comp;
		int mes = Formatacao.getMesData(lancamento.getData())+1; 
		if(mes<10){
			comp="0"+mes+"/"+Formatacao.getAnoData(lancamento.getData());
		}else comp = mes+"/"+Formatacao.getAnoData(lancamento.getData());  
		contaspagar.setCompetencia(comp); 
		contaspagar.setDataEmissao(new Date());
		contaspagar.setDescricao(lancamento.getDescricao());
		contaspagar.setPlanoconta(lancamento.getPlanoconta());
		contaspagar.setUnidadenegocio(lancamento.getUsuario().getUnidadenegocio());
		contaspagar.setValorentrada(0.0f);
		contaspagar.setValorsaida(lancamento.getValorlancado());
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		contaspagar = contasPagarFacade.salvar(contaspagar);
		lancamento.setLancado(true);
		CartaoCreditoLancamentoFacade cartaoCreditoLancamentoFacade = new CartaoCreditoLancamentoFacade();
		lancamento = cartaoCreditoLancamentoFacade.salvar(lancamento); 
		Mensagem.lancarMensagemInfo("Contas a Pagar lançado com sucesso!", "");
	}
}
