package br.com.travelmate.managerBean.financeiro.crmcobranca;


import br.com.travelmate.facade.CobrancaFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CrmCobrancaHistoricoFacade;
import br.com.travelmate.facade.HistoricoCobrancaFacade;
import br.com.travelmate.facade.TiSolicitacoesFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Crmcobrancahistorico;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Historicocobranca;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

@Named
@ViewScoped
public class VisualizarContasCobrancaMB implements Serializable{

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Vendas venda;
	private String titulo;
	private List<Contasreceber> listaContasReceber;
	private Formapagamento formapagamento;
	private boolean editar = true;

	@PostConstruct
	private void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		session.removeAttribute("venda");
		if (venda != null) {
			setTitulo("Venda No. " + String.valueOf(venda.getIdvendas()));
			formapagamento = venda.getFormapagamento();
		}
		carregarContasReceber();
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Contasreceber> getListaContasReceber() {
		return listaContasReceber;
	}

	public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}

	public Formapagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	public String gerarStatusImagem(Contasreceber conta) {
		String retorno;
		Date data = Formatacao.formatarDataAgora();
		boolean dataVenvida = conta.getDatavencimento().before(data);
		if (conta.getSituacao().equalsIgnoreCase("cc")) {
			retorno = "../../resources/img/bolaAzul.png";
		} else if (conta.getDatapagamento() != null) {
			retorno = "../../resources/img/bolaVerde.png";
		} else if (dataVenvida) {
			retorno = "../../resources/img/bolaVermelha.png";
		} else {
			return "../../resources/img/bolaAmarela.png";
		}
		return retorno;
	}

	public String gerarTitulo(Contasreceber conta) {
		String retorno;
		Date data = Formatacao.formatarDataAgora();
		boolean dataVenvida = conta.getDatavencimento().before(data);
		if (conta.getSituacao().equalsIgnoreCase("cc")) {
			retorno = "Cancelado";
		} else if (conta.getDatapagamento() != null) {
			retorno = "Já recebido";
		} else if (dataVenvida) {
			retorno = "Em atraso";
		} else {
			return "Dentro do prazo";
		}
		return retorno;
	}

	public void carregarContasReceber() {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		String sql;
		sql = "Select c from Contasreceber c where c.vendas.idvendas='" + venda.getIdvendas()
				+ "' and c.situacao<>'cc' order by c.datavencimento";
		listaContasReceber = contasReceberFacade.listar(sql);
		if (listaContasReceber == null) {
			listaContasReceber = new ArrayList<Contasreceber>();
		}
	}

	public String retornarCorContaVencida(Contasreceber contasreceber) {
		if (contasreceber.getCrmcobrancaconta() != null
				&& contasreceber.getCrmcobrancaconta().getPaga()==false){
			return "color:red;";
		}
		return "";
	}
	
	public boolean habilitarEdicao(Contasreceber contasreceber){
		if (contasreceber.getCrmcobrancaconta() != null
				&& contasreceber.getCrmcobrancaconta().getPaga()==false) {
			return true;
		}
		return false;
	}
	
	
	public void editar(RowEditEvent event) {
		Contasreceber contasreceber = ((Contasreceber) event.getObject());
		if (contasreceber != null) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			contasReceberFacade.salvar(contasreceber);
			Mensagem.lancarMensagemInfo("Editado com sucesso!", "");
		}
		if (contasreceber.getBoletoenviado()) {
			contasreceber.setDataalterada(Boolean.TRUE);
			contasreceber.setBoletoenviado(Boolean.FALSE);
		}
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		contasreceber = contasReceberFacade.salvar(contasreceber);
		Crmcobrancahistorico crmcobrancahistorico = new Crmcobrancahistorico();
		crmcobrancahistorico.setHistorico("Nova Data de vencimento da parcela "+ contasreceber.getNumeroparcelas() + " para " 
				+ Formatacao.ConvercaoDataPadrao(contasreceber.getDatanovovencimento()));
		crmcobrancahistorico.setCliente(venda.getCliente());
		crmcobrancahistorico.setData(new Date());
		crmcobrancahistorico.setUsuario(usuarioLogadoMB.getUsuario());
		crmcobrancahistorico.setProximocontato(new Date());
		crmcobrancahistorico.setTipocontato("");
		CrmCobrancaHistoricoFacade crmCobrancaHistoricoFacade = new CrmCobrancaHistoricoFacade();
		crmCobrancaHistoricoFacade.salvar(crmcobrancahistorico);
	}
	
	
	public void cancelarEdicao(RowEditEvent event) {
		Mensagem.lancarMensagemInfo("Operação cancelada!", "");
	}
	
	
	public void fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	
	public void salvarCobranca(Contasreceber contasreceber) {
		CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
		crmCobrancaBean.baixar(contasreceber, usuarioLogadoMB.getUsuario());
		carregarContasReceber();
	}
}
