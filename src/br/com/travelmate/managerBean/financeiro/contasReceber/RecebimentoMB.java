package br.com.travelmate.managerBean.financeiro.contasReceber;

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

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class RecebimentoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Contasreceber> listaContas;
	private float totalreceber;
	private float valorjuros;
	private float valordesconto;
	private float valorrecebido;
	private Banco banco;
	private String formapagamento;
	private List<Banco> listaBanco;
	private Date dataPagamento;
	private String novodocuemnto;
	private float desagioparcela;
	private boolean validarData;
	private String novoCartao;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		novoCartao = (String) session.getAttribute("novoCartao");
		listaContas = (List<Contasreceber>) session.getAttribute("listaContas");
		session.removeAttribute("listaContas");
		gerarListaBanco();
		if (novoCartao.equalsIgnoreCase("sim")){
			float desagio = listaContas.get(0).getVendas().getVendascomissao().getDesagio() +
					listaContas.get(0).getVendas().getVendascomissao().getJurospago();
			desagioparcela = desagio;
			desagio = desagio / listaContas.size();
		    for(int i=0;i<listaContas.size();i++){
		    	listaContas.get(i).setDesagio(desagio);
		    }
		}
		calcularValorTotal();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Contasreceber> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<Contasreceber> listaContas) {
		this.listaContas = listaContas;
	}

	public float getTotalreceber() {
		return totalreceber;
	}

	public void setTotalreceber(float totalreceber) {
		this.totalreceber = totalreceber;
	}

	public float getValorjuros() {
		return valorjuros;
	}

	public void setValorjuros(float valorjuros) {
		this.valorjuros = valorjuros;
	}

	public float getValordesconto() {
		return valordesconto;
	}

	public void setValordesconto(float valordesconto) {
		this.valordesconto = valordesconto;
	}

	public float getValorrecebido() {
		return valorrecebido;
	}

	public void setValorrecebido(float valorrecebido) {
		this.valorrecebido = valorrecebido;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public String getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(String formapagamento) {
		this.formapagamento = formapagamento;
	}

	public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}
	
	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	

	public String getNovodocuemnto() {
		return novodocuemnto;
	}

	public void setNovodocuemnto(String novodocuemnto) {
		this.novodocuemnto = novodocuemnto;
	}

	public float getDesagioparcela() {
		return desagioparcela;
	}

	public void setDesagioparcela(float desagioparcela) {
		this.desagioparcela = desagioparcela;
	}

	public void gerarListaBanco(){
		BancoFacade bancoFacade = new BancoFacade();
		listaBanco = bancoFacade.listar();
		if (listaBanco==null){
			listaBanco = new ArrayList<Banco>();
		}
	}
	
	public void calcularValorTotal(){
		valordesconto=0.0f;
		valorjuros=0.0f;
		valorrecebido=0.0f;
		totalreceber=0.0f;
		for(int i=0;i<listaContas.size();i++){
			totalreceber = totalreceber + listaContas.get(i).getValorparcela();
			valordesconto = valordesconto + listaContas.get(i).getDesagio();
			valorjuros = valorjuros + listaContas.get(i).getJuros();
			listaContas.get(i).setValorpago(listaContas.get(i).getValorparcela()-listaContas.get(i).getDesagio()+listaContas.get(i).getJuros());
			valorrecebido = valorrecebido + listaContas.get(i).getValorpago();
		}
		valorrecebido = totalreceber + valorjuros - valordesconto;
	}
	
	public String salvar() {
		if ((banco != null) && (banco.getIdbanco() != null)) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			for (int i = 0; i < listaContas.size(); i++) {
				Contasreceber conta = listaContas.get(i);
				if (conta.getDatapagamento()==null){
					if (dataPagamento != null) {
						conta.setDatapagamento(dataPagamento);
					}
				}
				conta.setSituacao("vd");
				conta.setBanco(banco);
				conta = contasReceberFacade.salvar(conta);
				CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
				crmCobrancaBean.baixar(listaContas.get(i), usuarioLogadoMB.getUsuario());
				EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Recebimento pelo usuário", conta, usuarioLogadoMB.getUsuario());
				if (!novoCartao.equalsIgnoreCase("sim")){
					lancarConciliacao(conta);
				}
			}
			if (novoCartao.equalsIgnoreCase("sim")){
				lancarConciliacaoUnica();
			}
			return "consContasReceber";
		}else {
			Mensagem.lancarMensagemInfo("Informação", "Banco não selecionado");
		}
		return "";
	}
	
	public String cancelar(){
		return "consContasReceber";
	}
	
	public void lancarConciliacao(Contasreceber conta){
		Contaspagar contaspagar = new Contaspagar();
		conta.setBanco(conta.getBanco());
		contaspagar.setCompetencia(Formatacao.gerarCopetencia(conta.getDatavencimento()));
		contaspagar.setDatacompensacao(conta.getDatapagamento());
		contaspagar.setDataEmissao(conta.getDataEmissao());
		contaspagar.setDatavencimento(conta.getDatavencimento());
		contaspagar.setDescricao("Recebimento - " + conta.getTipodocumento() +" " + conta.getNumeroparcelas() +  " - "  + conta.getVendas().getCliente().getNome() + "(" + conta.getNumerodocumento() + ")");  
		contaspagar.setIdcontasreceber(conta.getIdcontasreceber());
		contaspagar.setPlanoconta(conta.getPlanoconta());
		if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			Unidadenegocio un = unidadeNegocioFacade.consultar(6);
			contaspagar.setUnidadenegocio(un);
		}else {
			contaspagar.setUnidadenegocio(conta.getVendas().getUnidadenegocio());
		}
		contaspagar.setValorentrada(conta.getValorpago());
		contaspagar.setValorsaida(0.0f);
		contaspagar.setBanco(banco);
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		contasPagarFacade.salvar(contaspagar);
		EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Recebimento pelo usáruio", conta, usuarioLogadoMB.getUsuario());
	}
	
	public void lancarConciliacaoUnica(){
		Contasreceber conta = listaContas.get(0);
		Contaspagar contaspagar = new Contaspagar();
		contaspagar.setCompetencia(Formatacao.gerarCopetencia(dataPagamento));
		contaspagar.setDatacompensacao(dataPagamento);
		contaspagar.setDataEmissao(conta.getDataEmissao());
		contaspagar.setDatavencimento(conta.getDatavencimento());
		contaspagar.setDescricao("Recebimento - " + conta.getTipodocumento() +" " + listaContas.size() +  " - "  + conta.getVendas().getCliente().getNome() + "(" + conta.getNumerodocumento() + ")");  
		contaspagar.setIdcontasreceber(conta.getIdcontasreceber());
		contaspagar.setPlanoconta(conta.getPlanoconta());
		if (conta.getVendas().getUnidadenegocio().getIdunidadeNegocio()>2){
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			Unidadenegocio un = unidadeNegocioFacade.consultar(6);
			contaspagar.setUnidadenegocio(un);
		}else {
			contaspagar.setUnidadenegocio(conta.getVendas().getUnidadenegocio());
		}
		contaspagar.setValorentrada(valorrecebido);
		contaspagar.setValorsaida(0.0f);
		contaspagar.setBanco(banco);
		ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
		contasPagarFacade.salvar(contaspagar);
		EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Recebimento pelo usáruio", conta, usuarioLogadoMB.getUsuario());
	}
	
	public String enderecoCliente(Cliente cliente){
		if (cliente!=null){
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("cliente", cliente);
			RequestContext.getCurrentInstance().openDialog("enderecoCliente");
		}else{
			Mensagem.lancarMensagemInfo("Informação", "Cliente não localizado");
		}
    	return "";
    }
	
	public String alterarNumeroDocumento(){
		if (novodocuemnto.length()>0){
			for (int i = 0; i < listaContas.size(); i++) {
				listaContas.get(i).setNumerodocumento(novodocuemnto);
			}
		}
		return "";
	}
	
	public String alterarValorDesagioParcela(){
		if (desagioparcela>=0){
			for (int i = 0; i < listaContas.size(); i++) {
				listaContas.get(i).setDesagio(desagioparcela);
			}
			calcularValorTotal();
		}
		return "";
	}
	
	public void alterarDataRecebimento(Contasreceber conta, String linha){
		listaContas.add(Integer.parseInt(linha), conta);
	}
	
	public void validarDataRecebimentoCartao(){
		validarData=true;
		for (int i = 0; i < listaContas.size(); i++) {
			if (listaContas.get(i).getDatapagamento()==null){
				validarData = false;
			}
		}
	}
	
	public void validarDataRecebimento(){
		validarData=true;
		if (dataPagamento==null){
				validarData = false;
		}
	}
	
	
	public void setarDataRecebimentoCartao(){
		if (dataPagamento!=null){
			for(int i=0;i<listaContas.size();i++){
				listaContas.get(i).setDatapagamento(dataPagamento);
				dataPagamento = Formatacao.AdcionarDiaMesAnoData(dataPagamento, 1, "m");
			}
		}
	}
}
