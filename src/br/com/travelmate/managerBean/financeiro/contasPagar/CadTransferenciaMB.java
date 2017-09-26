package br.com.travelmate.managerBean.financeiro.contasPagar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;

@Named
@ViewScoped
public class	CadTransferenciaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    @Inject 
    private AplicacaoMB aplicacaoMB;
    private List<Banco> listaBanco;
    private Contaspagar conta;
    private Banco bancoDebito;
    private Banco bancoCredito;
    
    @PostConstruct
    public void init(){
    	conta = new Contaspagar();
    	conta.setDataEmissao(new Date());
    	conta.setDescricao("Transferência");
    	gerarListaBanco();
    }

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public Contaspagar getConta() {
		return conta;
	}

	public void setConta(Contaspagar conta) {
		this.conta = conta;
	}

	public Banco getBancoDebito() {
		return bancoDebito;
	}

	public void setBancoDebito(Banco bancoDebito) {
		this.bancoDebito = bancoDebito;
	}

	
	
	public Banco getBancoCredito() {
		return bancoCredito;
	}

	public void setBancoCredito(Banco bancoCredito) {
		this.bancoCredito = bancoCredito;
	}

	public String salvar(){
		PlanoContaFacade planoContaFacade = new PlanoContaFacade();
		if (validarDados()) {
	    	conta.setPlanoconta(planoContaFacade.consultar(aplicacaoMB.getParametrosprodutos().getPctransferencia()));
	    	conta.setDatacompensacao(conta.getDataEmissao());
	    	conta.setDatavencimento(conta.getDataEmissao());
	    	conta.setCompetencia(Formatacao.gerarCopetencia(conta.getDataEmissao()));
	    	conta.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
	    	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
			Contaspagar debito = new Contaspagar();
			debito.setBanco(bancoDebito);
			debito.setCompetencia(conta.getCompetencia());
			debito.setDatacompensacao(conta.getDataEmissao());
			debito.setDatavencimento(conta.getDataEmissao());
			debito.setDescricao(conta.getDescricao());
			debito.setPlanoconta(conta.getPlanoconta());
			debito.setUnidadenegocio(conta.getUnidadenegocio());
			debito.setValorentrada(0.0f);
			debito.setValorsaida(conta.getValorentrada());
			contasPagarFacade.salvar(debito);
			Contaspagar credito = new Contaspagar();
			credito.setBanco(bancoCredito);
			credito.setCompetencia(conta.getCompetencia());
			credito.setDatacompensacao(conta.getDataEmissao());
			credito.setDatavencimento(conta.getDataEmissao());
			credito.setDescricao(conta.getDescricao());
			credito.setPlanoconta(conta.getPlanoconta());
			credito.setUnidadenegocio(conta.getUnidadenegocio());
			credito.setValorentrada(conta.getValorentrada());
			credito.setValorsaida(0.0f);
			contasPagarFacade.salvar(credito);
			RequestContext.getCurrentInstance().closeDialog(null);
		}
		return "";
	}
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void gerarListaBanco(){
		BancoFacade bancoFacade = new BancoFacade();
		listaBanco = bancoFacade.listar();
		if (listaBanco==null){
			listaBanco = new ArrayList<Banco>();
		}
	}
	
	public boolean validarDados() {
		if (bancoCredito == null || bancoCredito.getIdbanco() == null) {
			Mensagem.lancarMensagemInfo("Informe o banco de credito", "");
			return false;
		}
		if (bancoDebito == null || bancoDebito.getIdbanco() == null) {
			Mensagem.lancarMensagemInfo("Informe o banco de debito", "");
			return false;
		}
		return true;
	}
	
	
	
	
	
}
