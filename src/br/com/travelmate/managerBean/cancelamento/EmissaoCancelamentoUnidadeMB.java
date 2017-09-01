package br.com.travelmate.managerBean.cancelamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.CondicaoCancelamentoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Condicaocancelamento;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class EmissaoCancelamentoUnidadeMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cancelamento cancelamento;
	private List<Condicaocancelamento> listaCondicao;
	private float multaFornecedorReais;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        cancelamento = (Cancelamento) session.getAttribute("cancelamento");
        session.removeAttribute("cancelamento");
        gerarListaCondicao();
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
	
	public void calcularMultaCliente(){
		CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
		cancelamento.setMultacliente(calcularMultaCancelamentoBean.calcularMulta(cancelamento));
		cancelamento.setEstornoloja(calcularMultaCancelamentoBean.calcularEstornoFranquia(cancelamento));
		calcularMultaCancelamentoBean.calcularTotais(cancelamento);
	}
	
	public void calcularMultaFornecedorReais(){
		if(cancelamento.getMultafornecedor()>0){
			multaFornecedorReais = cancelamento.getMultafornecedor() * cancelamento.getVendas().getValorcambio();
			CalcularMultaCancelamentoBean calcularMultaCancelamentoBean = new CalcularMultaCancelamentoBean();
			cancelamento = calcularMultaCancelamentoBean.calcularTotais(cancelamento);
		}else multaFornecedorReais = 0.0f;
	}
	
	
	public String confirmar(){
		CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
		cancelamento.setSituacao("FINANCEIRO");
		cancelamento= cancelamentoFacade.salvar(cancelamento);
		Mensagem.lancarMensagemInfo("Confirmação", "Cancelamento salvo com sucesso");
		return "consCancelamento";
	}
	
	
	public String cancelar(){
		return "consCancelamento";
	}
	
	
	
	
	
	
	
	

}
