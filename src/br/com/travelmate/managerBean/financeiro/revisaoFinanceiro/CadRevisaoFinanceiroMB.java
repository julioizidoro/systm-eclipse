package br.com.travelmate.managerBean.financeiro.revisaoFinanceiro;

import java.io.IOException;
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

import com.sun.glass.ui.Application;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.LogVendaFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Logvenda;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Vendapendencia;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadRevisaoFinanceiroMB implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Contasreceber> listaContasReceber;
	private Vendas venda;
	private Formapagamento formapagamento;
    private List<Banco> listaBanco;
    private Banco banco;
    private String tipo;
    private boolean habilitarPendencia;
    private boolean habilitarNovaVenda;
    private float valorMatriz;
    private float valorLoja;
    private float valorTotal;
    private List<Vendas> listaVendaPendente;
    private List<Vendas> listaVendaNova;
    private Date dataPagamento;
    private Float valorRecebido;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		listaVendaPendente = (List<Vendas>) session.getAttribute("listaVendaPendente");
		listaVendaNova = (List<Vendas>) session.getAttribute("listaVendaNova");
		session.removeAttribute("venda");
		session.removeAttribute("listaVendaPendente");
		session.removeAttribute("listaVendaNova");
		if (venda!=null){
			if (venda.getSituacaofinanceiro().equalsIgnoreCase("P")) {
				habilitarPendencia = true;
				habilitarNovaVenda = false;
			}else{
				habilitarNovaVenda = true;
				habilitarPendencia = false;
			}
			validarDados();
            formapagamento = venda.getFormapagamento();
            gerarListaBanco();
            somarValores();
            calcularValorTotal();
        }
	}
	
	
	
	
	public List<Contasreceber> getListaContasReceber() {
		return listaContasReceber;
	}




	public void setListaContasReceber(List<Contasreceber> listaContasReceber) {
		this.listaContasReceber = listaContasReceber;
	}




	public Vendas getVenda() {
		return venda;
	}




	public void setVenda(Vendas venda) {
		this.venda = venda;
	}




	public Formapagamento getFormapagamento() {
		return formapagamento;
	}




	public void setFormapagamento(Formapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}




	public List<Banco> getListaBanco() {
		return listaBanco;
	}




	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}




	public Banco getBanco() {
		return banco;
	}




	public void setBanco(Banco banco) {
		this.banco = banco;
	}




	public String getTipo() {
		return tipo;
	}




	public void setTipo(String tipo) {
		this.tipo = tipo;
	}




	public boolean isHabilitarPendencia() {
		return habilitarPendencia;
	}




	public void setHabilitarPendencia(boolean habilitarPendencia) {
		this.habilitarPendencia = habilitarPendencia;
	}




	public boolean isHabilitarNovaVenda() {
		return habilitarNovaVenda;
	}




	public void setHabilitarNovaVenda(boolean habilitarNovaVenda) {
		this.habilitarNovaVenda = habilitarNovaVenda;
	}




	public float getValorMatriz() {
		return valorMatriz;
	}




	public void setValorMatriz(float valorMatriz) {
		this.valorMatriz = valorMatriz;
	}




	public float getValorLoja() {
		return valorLoja;
	}




	public void setValorLoja(float valorLoja) {
		this.valorLoja = valorLoja;
	}




	public float getValorTotal() {
		return valorTotal;
	}




	public void setValorTotal(float valorTotal) {
		this.valorTotal = valorTotal;
	}




	public List<Vendas> getListaVendaPendente() {
		return listaVendaPendente;
	}




	public void setListaVendaPendente(List<Vendas> listaVendaPendente) {
		this.listaVendaPendente = listaVendaPendente;
	}




	public List<Vendas> getListaVendaNova() {
		return listaVendaNova;
	}




	public void setListaVendaNova(List<Vendas> listaVendaNova) {
		this.listaVendaNova = listaVendaNova;
	}




	public Date getDataPagamento() {
		return dataPagamento;
	}




	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}




	public Float getValorRecebido() {
		return valorRecebido;
	}




	public void setValorRecebido(Float valorRecebido) {
		this.valorRecebido = valorRecebido;
	}




	public String gerarStatusImagem(Contasreceber conta){
		String retorno;
        Date data = Formatacao.formatarDataAgora();
        boolean dataVenvida = conta.getDatavencimento().before(data);
        if(conta.getSituacao().equalsIgnoreCase("cc")){
            retorno = "../../resources/img/bolaAzul.png";
        }else if (conta.getDatapagamento()!=null){
        	retorno = "../../resources/img/bolaVerde.png";
        }else if (dataVenvida){
        	 retorno = "../../resources/img/bolaVermelha.png";
        }else {
            return "../../resources/img/bolaAmarela.png";
        }
        return retorno;
    }
    
    public String gerarTitulo(Contasreceber conta){
    	String retorno;
        Date data = Formatacao.formatarDataAgora();
        boolean dataVenvida = conta.getDatavencimento().before(data);
        if(conta.getSituacao().equalsIgnoreCase("cc")){
            retorno = "Cancelado";
        }else if (conta.getDatapagamento()!=null){
        	retorno = "Já recebido";
        }else if (dataVenvida){
        	 retorno = "Em atraso";
        }else {
            return "Dentro do prazo";
        }
        return retorno;
    }
    
    public void carregarContasReceber(){
        ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
        String sql;
        sql = "Select c from Contasreceber c where c.vendas.idvendas='" 
        		+ venda.getIdvendas() + "' and c.situacao<>'cc' order by c.datavencimento";
        listaContasReceber = contasReceberFacade.listar(sql);
        if (listaContasReceber==null){
        	listaContasReceber = new ArrayList<Contasreceber>();
        }
    }
    
    public void gerarListaBanco() {
        BancoFacade bancoFacade = new BancoFacade();
        listaBanco = bancoFacade.listar();
        if (listaBanco == null) {
            listaBanco = new ArrayList<Banco>();
        }
    }
    
    
    public String cadastroPendencia() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		session.setAttribute("listaVendaNova", listaVendaNova);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("cadVendaPendencia", options, null);
		return "";
	} 
    
    public String enviarEmailRF() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("enviarEmailRF", options, null);
		return "";
	}
    
    
    public String consultarDocumentos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("consDocumentosRF", options, null);
		return "";
	}
    
    public String consultarHistorico() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		session.setAttribute("listaVendaNova", listaVendaNova);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		return "consVendaPendenciaHistorico";
	}
    
    
    public void gerarContasReceber(){
    	ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
    	valorTotal = 0.0f;
    	valorRecebido = 0.0f;
    	listaContasReceber = contasReceberFacade.listar("SELECT c FROM Contasreceber c WHERE c.vendas.idvendas=" + venda.getIdvendas());
    	if (listaContasReceber == null) {
			listaContasReceber = new ArrayList<>();
		}
    	for (int i = 0; i < listaContasReceber.size(); i++) {
    		if (listaContasReceber.get(i).getValorpago() != null) {
        		valorRecebido = valorRecebido + listaContasReceber.get(i).getValorpago();
			}
			valorTotal = valorTotal + listaContasReceber.get(i).getValorparcela();
		}
    }
    
    
    public void somarValores(){
    	if (formapagamento != null  && formapagamento.getParcelamentopagamentoList() != null) {
			for (int i = 0; i < formapagamento.getParcelamentopagamentoList().size(); i++) {
				if (formapagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto().equalsIgnoreCase("Matriz")) {
					valorMatriz = valorMatriz + formapagamento.getParcelamentopagamentoList().get(i).getValorParcelamento();
				}else if (formapagamento.getParcelamentopagamentoList().get(i).getTipoParcelmaneto().equalsIgnoreCase("Loja")) {
					valorLoja = valorLoja + formapagamento.getParcelamentopagamentoList().get(i).getValorParcelamento();
				}
			}
		}
    }
    
	public String liberarVenda() {
		VendasFacade vendasFacade = new VendasFacade();
		if (venda.getSituacaofinanceiro().equalsIgnoreCase("N")) {
			listaVendaNova.remove(venda);
		} else if (venda.getSituacaofinanceiro().equalsIgnoreCase("P")) {
			listaVendaPendente.remove(venda);
		}
		venda.setSituacaofinanceiro("L");
		if (venda.getSituacaogerencia().equalsIgnoreCase("F")) {
			venda.setSituacao("FINALIZADA");
			venda.setDataprocesso(new Date());
		}
		venda = vendasFacade.salvar(venda);
		int idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		if (venda.getProdutos().getIdprodutos()==idProduto) {
			int idVendaSeguro = getIdVendaSeguro(venda.getIdvendas());
			if (idVendaSeguro>0) {
				for(int i=0;i<listaVendaNova.size();i++) {
					if (listaVendaNova.get(i).getIdvendas()==idVendaSeguro) {
						listaVendaNova.get(i).setSituacaofinanceiro("L");
						vendasFacade.salvar(listaVendaNova.get(i));
						gerarLogVenda("Liberada", "Venda liberada pelo financeiro");
						listaVendaNova.remove(i);
						i=listaVendaNova.size() + 10;
					}
				}
			}
		}
		gerarLogVenda("Liberada", "Venda liberada pelo financeiro");
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("listaVendaNova", listaVendaNova);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		return "consVendasRevisaoFinanceiro";
	}
    
    
    public void retornoDialogPendencia(SelectEvent event){
    	Vendapendencia vendapendencia = (Vendapendencia) event.getObject();
    	if (vendapendencia.getIdvendapendencia() != null) {
    		try {
    				gerarLogVenda("Pendencia", "Venda com pendencia financeira");
				FacesContext.getCurrentInstance().getExternalContext().redirect("consVendasRevisaoFinanceiro.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    
    public String salvar() {
		if ((banco != null) && (banco.getIdbanco() != null)) {
			ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
			for (int i = 0; i < venda.getContasreceberList().size(); i++) {
				if (venda.getContasreceberList().get(i).getValorpago() > 0.0f) {
					if (venda.getContasreceberList().get(i).getDatapagamento()==null){
						if (dataPagamento != null) {
							venda.getContasreceberList().get(i).setDatapagamento(dataPagamento);
						}
					}
					venda.getContasreceberList().get(i).setSituacao("vd");
					venda.getContasreceberList().get(i).setBanco(banco);
					contasReceberFacade.salvar(venda.getContasreceberList().get(i));
					EventoContasReceberBean eventoContasReceberBean = new EventoContasReceberBean("Recebimento pelo usuário", venda.getContasreceberList().get(i), usuarioLogadoMB.getUsuario());
				}
			}
			Mensagem.lancarMensagemInfo("Recebimento feito com sucesso", "");
		}else {
			Mensagem.lancarMensagemInfo("Informação", "Banco não selecionado");
		}
		return "";
	}
    
    public void calcularValorTotal(){
    	valorRecebido = 0.0f;
    	valorTotal = 0.0f;
    	for (int i = 0; i < venda.getContasreceberList().size(); i++) {
    		valorRecebido = valorRecebido + venda.getContasreceberList().get(i).getValorpago();
    		if (!venda.getContasreceberList().get(i).getSituacao().equalsIgnoreCase("cc")) {
        		valorTotal = valorTotal + venda.getContasreceberList().get(i).getValorparcela();
			}
		}
    }
    
    
    public String fechar(){
    	FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		session.setAttribute("listaVendaNova", listaVendaNova);
		return "consVendasRevisaoFinanceiro";
    }
    
    
    public void retornoDialogEmail(SelectEvent event){
    	Vendapendencia vendapendencia = (Vendapendencia) event.getObject();
    	if (vendapendencia.getIdvendapendencia() != null) {
			habilitarNovaVenda = false;
			habilitarPendencia = true;
		}
    }
    
    
    public void validarDados(){
    	if (venda.getFormapagamento() == null) {
			venda.setFormapagamento(new Formapagamento());
		}
    	
    	if (venda.getOrcamento() == null) {
			venda.setOrcamento(new Orcamento());
		}
    	
    	if (venda.getCambio() == null) {
			venda.setCambio(new Cambio());
			venda.getCambio().setMoedas(new Moedas());
		}
    	
    	if (venda.getVendascomissao() == null) {
			venda.setVendascomissao(new Vendascomissao());
		}
    	
    	if (venda.getFornecedor() == null) {
			venda.setFornecedor(new Fornecedor());
		}
    }
    
    public void gerarLogVenda(String situacao, String operacao) {
    		Logvenda logVenda = new  Logvenda();
    		logVenda.setSituacao(situacao);
    		logVenda.setOperacao(operacao);
		logVenda.setUsuario(usuarioLogadoMB.getUsuario());
		logVenda.setVendas(venda);
		LogVendaFacade logVendaFacade = new LogVendaFacade();
		logVendaFacade.salvar(logVenda);

    }
    
    public int getIdVendaSeguro(int idVenda) {
    		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
    		Seguroviagem seguro = seguroViagemFacade.consultarSeguroCurso(idVenda);
    		if (seguro!=null) {
    			return seguro.getVendas().getIdvendas();
    		}
    		return 0;
    		
    }
    
    
}
