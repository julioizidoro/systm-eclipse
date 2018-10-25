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

import br.com.travelmate.bean.BolinhasBean;
import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AcomodacaoCursoFacade;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.LogVendaFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.contasReceber.EventoContasReceberBean;
import br.com.travelmate.managerBean.financeiro.crmcobranca.CrmCobrancaBean;
import br.com.travelmate.model.Acomodacaocurso;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Logvenda;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Orcamento;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Vendapendencia;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;
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
	private AvisosDao avisosDao;
	@Inject
	private VendasDao vendasDao;
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
    private float valorMatrizLoja;
	private List<BolinhasBean> listaBolinhas;
	private boolean botaocartaocredito = false;
	
	@SuppressWarnings("unchecked")
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
            verificarContasReceber();
            gerarBolinhasBean();
            verificarParcelamento();       
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




	public float getValorMatrizLoja() {
		return valorMatrizLoja;
	}




	public void setValorMatrizLoja(float valorMatrizLoja) {
		this.valorMatrizLoja = valorMatrizLoja;
	}




	public List<BolinhasBean> getListaBolinhas() {
		return listaBolinhas;
	}




	public void setListaBolinhas(List<BolinhasBean> listaBolinhas) {
		this.listaBolinhas = listaBolinhas;
	}




	public boolean isBotaocartaocredito() {
		return botaocartaocredito;
	}




	public void setBotaocartaocredito(boolean botaocartaocredito) {
		this.botaocartaocredito = botaocartaocredito;
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
			valorMatrizLoja = valorMatriz + valorLoja;
		}
    }
    
	public String liberarVenda() {
		
		
		if (validarLiberacao()) {
			Vendas vendaSeguro = null;
			Vendas vendasAcomodacao = null;
			if (venda.getProdutos().getIdprodutos()==1) {
				vendaSeguro = getVendaSeguro(venda.getIdvendas());
				vendasAcomodacao = buscarVendaAcomodacao();
			}
			
			if (venda.getSituacaofinanceiro().equalsIgnoreCase("N")) {			
				if (vendaSeguro!=null) {
					listaVendaNova.remove(vendaSeguro);
				}
				listaVendaNova.remove(venda);
			} else if (venda.getSituacaofinanceiro().equalsIgnoreCase("P")) {
				if (vendaSeguro!=null) {
					listaVendaPendente.remove(vendaSeguro);
				}
				listaVendaPendente.remove(venda);
			}
			venda.setSituacaofinanceiro("L");
			if (venda.getSituacaogerencia().equalsIgnoreCase("F")) {
				venda.setSituacao("FINALIZADA");
				venda.setDataprocesso(new Date());
				if (vendaSeguro!=null) {
					vendaSeguro.setSituacaofinanceiro("L");
					vendaSeguro.setSituacao("FINALIZADA");
					vendaSeguro.setDataprocesso(new Date());
					vendasDao.salvar(vendaSeguro);
				}
				
				if (vendasAcomodacao!=null) {
					vendasAcomodacao.setSituacaofinanceiro("L");
					vendasAcomodacao.setSituacao("FINALIZADA");
					vendasDao.salvar(vendasAcomodacao);
				}
				
				Avisos avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + venda.getCliente().getNome() + ", Nº da venda "
						+ venda.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

			String titulo = "";
			if (venda.getSituacaogerencia().equalsIgnoreCase("F")) {
				titulo = "Ficha de " + venda.getProdutos().getDescricao() + " Está Finalizada. " + venda.getIdvendas();
			}else {
				titulo = "Ficha de " + venda.getProdutos().getDescricao() + ". " + venda.getIdvendas();
			}
			String operacao = "I";
			String imagemNotificacao = "inserido";

			String vm = "Venda pela Matriz";
			if (venda.getVendasMatriz().equalsIgnoreCase("N")) {
				vm = "Venda pela Loja";
			}

			DepartamentoFacade departamentoFacade = new DepartamentoFacade();
			List<Departamento> departamento = departamentoFacade
					.listar("select d From Departamento d where d.usuario.idusuario="
							+ venda.getProdutos().getIdgerente());
			if (departamento != null && departamento.size() > 0) {
				String dataInicio = verificarDataInicio(venda);
				venda.setVendascomissao(null);  
				Formatacao.gravarNotificacaoVendasFinalizado(titulo, venda.getUnidadenegocio(), venda.getCliente().getNome(), venda.getFornecedorcidade().getFornecedor().getNome()
						, dataInicio, venda.getUsuario().getNome(), vm, venda.getValor(), venda.getValorcambio(), venda.getCambio().getMoedas().getSigla(), operacao
						, imagemNotificacao, operacao, departamento.get(0));
				
			}  
			if (venda.getFormapagamento().getIdformaPagamento() == null) {
				venda.setFormapagamento(null);
			}
	    	
	    	if (venda.getOrcamento().getIdorcamento() == null) {
				venda.setOrcamento(null);
			}
	    	
	    	if (venda.getCambio().getIdcambio() == null) {
				venda.setCambio(null);
			}
	    	
	    	if (venda.getVendascomissao() == null || venda.getVendascomissao().getIdvendascomissao() == null) {
				venda.setVendascomissao(null);
			}
	    	
	    	if (venda.getFornecedor().getIdfornecedor() == null) {
				venda.setFornecedor(null);
			}
	    	
			venda = vendasDao.salvar(venda);
			int idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
			int idProdutoVoluntariado = aplicacaoMB.getParametrosprodutos().getVoluntariado();
			boolean pendenciarSeguro = false;
			if (venda.getProdutos().getIdprodutos()==idProduto) {
				pendenciarSeguro = true;
			}else if (venda.getProdutos().getIdprodutos()==idProdutoVoluntariado) {
				pendenciarSeguro = true;
			}
			if (pendenciarSeguro) {
				int idVendaSeguro = getIdVendaSeguro(venda.getIdvendas());
				if (idVendaSeguro>0) {
					for(int i=0;i<listaVendaNova.size();i++) {
						if (listaVendaNova.get(i).getIdvendas()==idVendaSeguro) {
							listaVendaNova.get(i).setSituacaofinanceiro("L");
							listaVendaNova.get(i).setSituacao("FINALIZADA");
							vendasDao.salvar(listaVendaNova.get(i));
							gerarLogVenda("Liberada", "Venda liberada pelo financeiro");
							listaVendaNova.remove(i);
							i=listaVendaNova.size() + 10;
						}
					}
				}
			}
			
			if (vendasAcomodacao != null && vendasAcomodacao.getIdvendas() != null) {
				for (int i = 0; i < listaVendaNova.size(); i++) {
					int idListaVenda = listaVendaNova.get(i).getIdvendas();
					int idVendaAcomodacao = vendasAcomodacao.getIdvendas();
					if (idListaVenda == idVendaAcomodacao) {
						listaVendaNova.get(i).setSituacaofinanceiro("L");
						listaVendaNova.get(i).setSituacao("FINALIZADA");
						vendasDao.salvar(listaVendaNova.get(i));
						gerarLogVenda("Liberada", "Venda liberada pelo financeiro");
						listaVendaNova.remove(i);
						i = listaVendaNova.size() + 10;
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
		return "";
	}
	
	public Vendas buscarVendaAcomodacao() {
		AcomodacaoCursoFacade acomodacaoCursoFacade = new AcomodacaoCursoFacade();
		Acomodacaocurso acomodacaocurso = acomodacaoCursoFacade.consultar("SELECT a FROM Acomodacaocurso a WHERE a.curso.vendas.idvendas=" + venda.getIdvendas());
		if (acomodacaocurso != null && acomodacaocurso.getAcomodacao() != null) {
			return acomodacaocurso.getAcomodacao().getVendas();
		}else return null;
	}
	
	
	public boolean validarLiberacao() {
		if (venda.getFormapagamento() == null || venda.getFormapagamento().getIdformaPagamento() == null) {
			Mensagem.lancarMensagemInfo("LIBERAÇÃO NEGADA", "Venda sem forma de pagamento!!");
			return false;
		}


		if (venda.getFornecedor() == null || venda.getFornecedor().getIdfornecedor() == null) {
			Mensagem.lancarMensagemInfo("LIBERAÇÃO NEGADA", "Venda sem fornecedor");
			return false;
		}
		return true;
	}
    
    
    public void retornoDialogPendencia(SelectEvent event){
    	Vendapendencia vendapendencia = (Vendapendencia) event.getObject();
    	if (vendapendencia.getIdvendapendencia() != null) {
    		try {
    				gerarLogVenda("Pendencia", "Venda com pendencia financeira");
				FacesContext.getCurrentInstance().getExternalContext().redirect("consVendasRevisaoFinanceiro.jsf");
			} catch (IOException e) {
				  
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
					CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
					crmCobrancaBean.baixar(venda.getContasreceberList().get(i), usuarioLogadoMB.getUsuario());
					new EventoContasReceberBean("Recebimento pelo usuário", venda.getContasreceberList().get(i), usuarioLogadoMB.getUsuario());
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
    
    
	public void validarDados() {
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

		if (venda.getContasreceberList() == null) {
			venda.setContasreceberList(new ArrayList<Contasreceber>());
		}
	}
    
	public void gerarLogVenda(String situacao, String operacao) {
		Logvenda logVenda = new Logvenda();
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
		if (seguro != null) {
			return seguro.getVendas().getIdvendas();
		}
		return 0;

	}
	
	
	public String corValorMatrizLoja(){
		String valorFormatadoVenda = Formatacao.formatarFloatStringNumero(venda.getValor());
		String valorFormatadoMatrizLoja = Formatacao.formatarFloatStringNumero(valorMatrizLoja);
		float valorVenda = Formatacao.formatarStringfloat(valorFormatadoVenda);
		valorMatrizLoja = Formatacao.formatarStringfloat(valorFormatadoMatrizLoja);
		if (valorMatrizLoja != valorVenda) {
			return "color:red;"; 
		}
		return "";
	}
	
	public List<Avisousuario> salvarAvisoUsuario(Avisos aviso) {
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		if (venda.getUnidadenegocio().getIdunidadeNegocio() == 2) {
			for (int i = 0; i < usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().size(); i++) {
				
				Avisousuario avisousuario = new Avisousuario();
				avisousuario.setAvisos(aviso);
				avisousuario.setUsuario(usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().get(i).getUsuarioNotificar());
				avisousuario.setVisto(false);
				avisousuario = avisosDao.salvar(avisousuario);
			}
		}
		return lista;
	}
	
    
	public void verificarContasReceber(){
		listaContasReceber = new ArrayList<>();
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		if (venda.getContasreceberList() == null || venda.getContasreceberList().isEmpty()) {
			List<Contasreceber> lista = contasReceberFacade.listar("SELECT c FROM Contasreceber c WHERE c.situacao<>'cc' and c.vendas.idvendas=" + venda.getIdvendas());
			if (lista == null) {
				lista = new ArrayList<Contasreceber>();
			}
			venda.setContasreceberList(lista);
		}
		for (int i = 0; i < venda.getContasreceberList().size(); i++) {
			if (!venda.getContasreceberList().get(i).getSituacao().equalsIgnoreCase("cc")) {
				venda.getContasreceberList().get(i).setBolinhas(verStatus(venda.getContasreceberList().get(i)));
				listaContasReceber.add(venda.getContasreceberList().get(i));
			}
		}
		venda.setContasreceberList(listaContasReceber);
	}
	
	public void verificarParcelamento(){
		if (venda.getFormapagamento().getParcelamentopagamentoList() == null) {
			venda.getFormapagamento().setParcelamentopagamentoList(new ArrayList<Parcelamentopagamento>());
		}
		for (int i = 0; i < venda.getFormapagamento().getParcelamentopagamentoList().size(); i++) {
			if (venda.getFormapagamento().getParcelamentopagamentoList().get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito") || 
					venda.getFormapagamento().getParcelamentopagamentoList().get(i).getFormaPagamento().equalsIgnoreCase("Cartão débito") ||
					venda.getFormapagamento().getParcelamentopagamentoList().get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito autorizado")) {
				if (!botaocartaocredito) {
					botaocartaocredito = true;
					i = venda.getFormapagamento().getParcelamentopagamentoList().size();
				}
			}
		}  
	}
	
	public void gerarBolinhasBean() {
		listaBolinhas = new ArrayList<BolinhasBean>();
		BolinhasBean bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Verde");
		bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
		listaBolinhas.add(bolinhasBean);
		bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Amarela");
		bolinhasBean.setCaminho("../../resources/img/bolaAmarela.png");
		listaBolinhas.add(bolinhasBean);
		bolinhasBean = new BolinhasBean();
		bolinhasBean.setCor("Vermelha");
		bolinhasBean.setCaminho("../../resources/img/bolaVermelha.png");
		listaBolinhas.add(bolinhasBean);
	}
	
	
	public BolinhasBean verStatus(Contasreceber contasreceber) {
		BolinhasBean bolinhasBean = new BolinhasBean();
		if (contasreceber.getSituacao().equalsIgnoreCase("vd")) {
			bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
			bolinhasBean.setCor("Verde");
			return bolinhasBean;
		} else {
			if (contasreceber.getSituacao().equalsIgnoreCase("vm")) {
				bolinhasBean.setCaminho("../../resources/img/bolaVermelha.png");
				bolinhasBean.setCor("Vermelha");
				return bolinhasBean;
			} else {
				if (contasreceber.getSituacao().equalsIgnoreCase("am")) {
					bolinhasBean.setCaminho("../../resources/img/bolaAmarela.png");
					bolinhasBean.setCor("Amarela");
					return bolinhasBean;
				}
			}
		}
		bolinhasBean.setCaminho("../../resources/img/bolaVerde.png");
		bolinhasBean.setCor("Verde");
		return bolinhasBean;
	}
	
	public String lancarCartaoCredito() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("venda", venda);
		session.setAttribute("listaVendaNova", listaVendaNova);
		session.setAttribute("listaVendaPendente", listaVendaPendente);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 250);
		RequestContext.getCurrentInstance().openDialog("lancamentoCartaoCredito", options, null);
		return "";
	} 
	
	@SuppressWarnings("unchecked")
	public void retornoDialogCartaoCredito(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		venda = (Vendas) session.getAttribute("venda");
		listaVendaPendente = (List<Vendas>) session.getAttribute("listaVendaPendente");
		listaVendaNova = (List<Vendas>) session.getAttribute("listaVendaNova");
		session.removeAttribute("venda");
		session.removeAttribute("listaVendaPendente");
		session.removeAttribute("listaVendaNova");
	}
	
	
	public String  verificarDataInicio(Vendas vendas) {
		Date dataInciio = null;
		int idProduto = vendas.getProdutos().getIdprodutos();
		if (idProduto == 1) {
			CursoFacade cursoFacade = new CursoFacade();
			Curso cursos = cursoFacade.consultarCursos(vendas.getIdvendas());
			if (cursos != null) {
				dataInciio = cursos.getDataInicio();
			}
		}else if(idProduto == 4) {
			HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
			Highschool highschool = highSchoolFacade.consultarHighschool(vendas.getIdvendas());
			if (highschool != null) {
				dataInciio = highschool.getValoreshighschool().getDatainicio();
			}
		}else if (idProduto == 5) {
			ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
			Programasteens programasteens = programasTeensFacede.find(vendas.getIdvendas());
			if (programasteens != null) {
				dataInciio = programasteens.getDataInicioCurso();
			}
		}else if (idProduto == 9) {
			AupairFacade aupairFacade = new AupairFacade();
			Aupair aupair = aupairFacade.consultar(vendas.getIdvendas());
			if (aupair != null) {
				dataInciio = aupair.getDataInicioPretendida01();
			}
		}else if(idProduto == 10){
			WorkTravelFacade workTravelFacade = new WorkTravelFacade();
			Worktravel worktravel = workTravelFacade.consultarWork(vendas.getIdvendas());
			if (worktravel != null) {
				dataInciio = worktravel.getDataInicioPretendida01();
			}
		}else if(idProduto == 16) {
			VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
			Voluntariado voluntariado = voluntariadoFacade.consultar(vendas.getIdvendas());
			if (voluntariado != null) {
				dataInciio = voluntariado.getDataInicio();
			}
		}else if(idProduto == 20) {
			DemipairFacade demipairFacade = new DemipairFacade();
			Demipair demipair = demipairFacade.consultar(vendas.getIdvendas());
			if (demipair != null) {
				dataInciio = demipair.getDatainicio();
			}
		}
		String dataExtenso = "";
		if (dataInciio != null) {
			dataExtenso = Formatacao.ConvercaoDataPadrao(dataInciio);
		}
		return dataExtenso;
	}
	
	public Vendas getVendaSeguro(int idVenda) {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemFacade.consultarSeguroCurso(idVenda);
		if (seguro!=null) {
			return seguro.getVendas();
		}
		return null;
	}
    
}
