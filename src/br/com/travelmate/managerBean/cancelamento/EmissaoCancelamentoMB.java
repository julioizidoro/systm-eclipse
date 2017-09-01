package br.com.travelmate.managerBean.cancelamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.CondicaoCancelamentoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Condicaocancelamento;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class EmissaoCancelamentoMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cancelamento cancelamento;
	private List<Condicaocancelamento> listaCondicao;
	private boolean habilitarTotalRecebido;
	private float multaFornecedorReais;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cancelamento = (Cancelamento) session.getAttribute("cancelamento");
        session.removeAttribute("cancelamento");
        gerarListaCondicao();
        Date data = Formatacao.ConvercaoStringData("30/04/2016");
        if (cancelamento.getVendas().getDataVenda().after(data)){
        	habilitarTotalRecebido = true;
        	if (cancelamento.getTotalrecebido()<=0){
        		calcularTotalRecebidoLoja();
        		calcularTotalRecebidoMatriz();
        		habilitarTotalRecebido = true;
        	}else {
        		cancelamento.setTotalrecebidoloja(0.0f);
        		cancelamento.setTotalrecebidomatriz(0.0f);
        		cancelamento.setTotalrecebido(0.0f);
        		habilitarTotalRecebido = false;
        	}
        }else {
        	habilitarTotalRecebido = false;
        }
        calcularMultaFornecedorReais();
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Cancelamento getCancelamento() {
		return cancelamento;
	}

	public void setCancelamento(Cancelamento cancelamento) {
		this.cancelamento = cancelamento;
	}

	public List<Condicaocancelamento> getListaCondicao() {
		return listaCondicao;
	}

	public void setListaCondicao(List<Condicaocancelamento> listaCondicao) {
		this.listaCondicao = listaCondicao;
	}
	
	public boolean isHabilitarTotalRecebido() {
		return habilitarTotalRecebido;
	}

	public void setHabilitarTotalRecebido(boolean habilitarTotalRecebido) {
		this.habilitarTotalRecebido = habilitarTotalRecebido;
	}
	
	

	public float getMultaFornecedorReais() {
		return multaFornecedorReais;
	}

	public void setMultaFornecedorReais(float multaFornecedorReais) {
		this.multaFornecedorReais = multaFornecedorReais;
	}

	public void gerarListaCondicao(){
		String sql = "SELECT c FROM Condicaocancelamento c where c.produtos.idprodutos=" +
				cancelamento.getVendas().getProdutos().getIdprodutos() + " order by c.descricao";
		CondicaoCancelamentoFacade condicaoCancelamentoFacade = new CondicaoCancelamentoFacade();
		listaCondicao = condicaoCancelamentoFacade.listar(sql);
		if (listaCondicao==null){
			listaCondicao = new ArrayList<Condicaocancelamento>();
		}
	}
	
	public void calcularTotalRecebidoLoja(){
		Formapagamento forma = cancelamento.getVendas().getFormapagamento();
		float valorRecebidoLoja =0.0f;
		if (forma!=null){
			if (forma.getParcelamentopagamentoList()!=null){
				for(int i=0;i<forma.getParcelamentopagamentoList().size();i++){
					if (forma.getParcelamentopagamentoList().get(i).getTipoParcelmaneto().equalsIgnoreCase("loja")){
						valorRecebidoLoja = valorRecebidoLoja + forma.getParcelamentopagamentoList().get(i).getValorParcelamento();
					}
				}
			}
		}
		cancelamento.setTotalrecebidoloja(valorRecebidoLoja);
		cancelamento.setTotalrecebido(cancelamento.getTotalrecebidoloja() +cancelamento.getTotalrecebidomatriz());
	}
	
	public void calcularTotalRecebidoMatriz(){
		float valorRecebidoMatriz =0.0f;
		ContasReceberFacade contasReceberFacade = new  ContasReceberFacade();
		String sql = "SELECT c FROM Contasreceber c where c.vendas.idvendas=" + cancelamento.getVendas().getIdvendas() +
				" and c.valorpago>0";
		List<Contasreceber> listaConta = contasReceberFacade.listar(sql);
		if (listaConta!=null){
			for(int i=0;i<listaConta.size();i++){
				valorRecebidoMatriz = valorRecebidoMatriz + listaConta.get(i).getValorpago();
			}
		}
		cancelamento.setTotalrecebidomatriz(valorRecebidoMatriz);
		cancelamento.setTotalrecebido(cancelamento.getTotalrecebidoloja() +cancelamento.getTotalrecebidomatriz());
	}
	
	public void calcularMultaCliente(){
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		cancelamento.setMultacliente(calcularMultaCancelamentoBean.calcularMulta(cancelamento));
		cancelamento.setEstornoloja(calcularMultaCancelamentoBean.calcularEstornoFranquia(cancelamento));
		calcularMultaCancelamentoBean.calcularTotais(cancelamento);
	}
	
	
	
	public String editarValoresCancelamento(String campo){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("campo", campo);
        if (campo.equalsIgnoreCase("recebidomatriz")){
        	session.setAttribute("valorcampo", cancelamento.getTotalrecebidomatriz());
        }else if (campo.equalsIgnoreCase("recebidoloja")){
        	session.setAttribute("valorcampo", cancelamento.getTotalrecebidoloja());
        }
        else if (campo.equalsIgnoreCase("multacliente")){
        	session.setAttribute("valorcampo", cancelamento.getMultacliente());
        }else if (campo.equalsIgnoreCase("estornoloja")){
        	session.setAttribute("valorcampo", cancelamento.getEstornoloja());
        }
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth",250);
        RequestContext.getCurrentInstance().openDialog("editarValoresCancelamento", options, null);
        return "";
    }
	
	public void retornoEditarValoresCancelamento(SelectEvent event){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        String campo = (String) session.getAttribute("campo");
        float valor = (float) session.getAttribute("valorcampo");
        String operacao = (String) session.getAttribute("operacao");
        session.removeAttribute("campo");
        session.removeAttribute("valorcampo");
        session.removeAttribute("operacao");
        if (operacao.equalsIgnoreCase("confirmada")){
        	CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
        	if (campo.equalsIgnoreCase("recebidomatriz")){
            	cancelamento.setTotalrecebidomatriz(valor);	
            }else if (campo.equalsIgnoreCase("recebidoloja")){
            	cancelamento.setTotalrecebidoloja(valor);
            }
            else if (campo.equalsIgnoreCase("multacliente")){
            	cancelamento.setMultacliente(valor);
            }else if (campo.equalsIgnoreCase("estornoloja")){
            	cancelamento.setEstornoloja(valor);
            }
        	cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
        }
	}
	
	public void calcularMultaFornecedorReais(){
		if(cancelamento.getMultafornecedor()>0){
			multaFornecedorReais = cancelamento.getMultafornecedor() * cancelamento.getVendas().getValorcambio();
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
		}else multaFornecedorReais = 0.0f;
	}
	
	public String confirmar(){
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		boolean situacao = calcularMultaCancelamentoBean.verifcarValorCreditoReembolso(cancelamento);
		if (situacao){
			CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
			cancelamento= cancelamentoFacade.salvar(cancelamento);
			Mensagem.lancarMensagemInfo("Confirmação", "Cancelamento salvo com sucesso");
			return "consCancelamento";
		}else {
			Mensagem.lancarMensagemWarn("Cancelamento", "Valor Crédito + Valor Remebolso maior que Total Reembolso");
			return "";
		}
	}
	
	public String cancelar(){
		Mensagem.lancarMensagemInfo("Confirmação", "Solicitação de cancelamento cancelada");
		return "consCancelamento";
	}
	
	public void calcularTotal(){
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		if (cancelamento.getTotalrecebidomatriz()==0){
			if (!habilitarTotalRecebido){
				cancelamento.setTotalrecebidomatriz(cancelamento.getTotalrecebido());
			}
		}
		calcularMultaCancelamentoBean.calcularTotais(cancelamento);
	}
	
	
	
	
	
	
	
	

}
