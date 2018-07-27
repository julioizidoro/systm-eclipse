package br.com.travelmate.managerBean.voluntariadoprojeto.orcamento;


import br.com.travelmate.bean.LeadSituacaoBean;
import br.com.travelmate.bean.NumeroParcelasBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadHistoricoDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.facade.CoeficienteJurosFacade;

import br.com.travelmate.facade.OrcamentoProjetoVoluntariadoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoDescontoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoFormaPagamentoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoProdutosExtrasFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoSeguroFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadhistorico;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;
import br.com.travelmate.model.Orcamentovoluntariadodesconto;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento;
import br.com.travelmate.model.Orcamentovoluntariadoprodutosextras;
import br.com.travelmate.model.Orcamentovoluntariadoseguro;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
import javax.annotation.PostConstruct; 
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class FormaPagamentoOrcamentoVoluntariadoMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadSituacaoDao leadSituacaoDao;
	@Inject
	private LeadHistoricoDao leadHistoricoDao;
	@Inject
	private LeadDao leadDao;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private OrcamentoVoluntariadoBean orcamento; 
    private Orcamentoprojetovoluntariado orcamentoprojetovoluntariado; 
    private boolean habilitaFormaPagamento02=true;
	private boolean habilitaFormaPagamento03=true;
	private boolean habilitaFormaPagamento04=true;
	private List<NumeroParcelasBean> listaNumeroParcelas;
	private NumeroParcelasBean numeroParcelaSelecionado;
    private String obs;
    

	@PostConstruct
    public void init() throws SQLException{
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
        orcamento = (OrcamentoVoluntariadoBean) session.getAttribute("orcamento");
        session.removeAttribute("orcamento");
        getUsuarioLogadoMB(); 
        listaNumeroParcelas = new ArrayList<NumeroParcelasBean>();
        gerarListaNuneroParcelas(orcamento.getDatainicial()); 
        orcamento.getOrcamentovoluntariadoformapagamento().setAvista(orcamento.getTotalRS()); 
    }
	
	public List<NumeroParcelasBean> getListaNumeroParcelas() {
		return listaNumeroParcelas;
	}

	public void setListaNumeroParcelas(List<NumeroParcelasBean> listaNumeroParcelas) {
		this.listaNumeroParcelas = listaNumeroParcelas;
	}

	public NumeroParcelasBean getNumeroParcelaSelecionado() {
		return numeroParcelaSelecionado;
	}

	public void setNumeroParcelaSelecionado(NumeroParcelasBean numeroParcelaSelecionado) {
		this.numeroParcelaSelecionado = numeroParcelaSelecionado;
	} 
    
    public boolean isHabilitaFormaPagamento02() {
		return habilitaFormaPagamento02;
	}

	public void setHabilitaFormaPagamento02(boolean habilitaFormaPagamento02) {
		this.habilitaFormaPagamento02 = habilitaFormaPagamento02;
	}

	public boolean isHabilitaFormaPagamento03() {
		return habilitaFormaPagamento03;
	}

	public void setHabilitaFormaPagamento03(boolean habilitaFormaPagamento03) {
		this.habilitaFormaPagamento03 = habilitaFormaPagamento03;
	}

	public boolean isHabilitaFormaPagamento04() {
		return habilitaFormaPagamento04;
	}

	public void setHabilitaFormaPagamento04(boolean habilitaFormaPagamento04) {
		this.habilitaFormaPagamento04 = habilitaFormaPagamento04;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}
 
    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public OrcamentoVoluntariadoBean getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(OrcamentoVoluntariadoBean orcamento) {
		this.orcamento = orcamento;
	}

	public Orcamentoprojetovoluntariado getOrcamentoprojetovoluntariado() {
		return orcamentoprojetovoluntariado;
	}

	public void setOrcamentoprojetovoluntariado(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
		this.orcamentoprojetovoluntariado = orcamentoprojetovoluntariado;
	}
 
    public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public void calcularParcelamento2() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradaboleto() != null) {
			if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradaboleto()!=null) {
				valorEntrada = (double) (orcamento.getTotalRS() * (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradaboleto() / 100));
				saldo = (double) (100 - orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradaboleto());
				valorSaldo = orcamento.getTotalRS() - valorEntrada;
				orcamento.getOrcamentovoluntariadoformapagamento().setValorentradaboleto(valorEntrada.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldoboleto(valorSaldo.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldoboleto(saldo.floatValue());
			} else if (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradaboleto() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = (double) orcamento.getTotalRS();
				orcamento.getOrcamentovoluntariadoformapagamento().setValorentradaboleto(valorEntrada.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldoboleto(valorSaldo.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldoboleto(saldo.floatValue());
			}
			valorSaldo = 0.0;
			if (orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelasboleto() > 0) {
				valorSaldo = orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldoboleto().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelasboleto();
					orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelaboleto(valorSaldo.floatValue());
				}
			}
		}
	}

	public void calcularParcelamento3() throws SQLException {
		if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradacartao() != null) {
			Double valorSaldo = 0.0;
			Double saldo = 0.0;
			Double valorEntrada = 0.0;
			if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradacartao()!=null) {
				valorEntrada = (double) (orcamento.getTotalRS() * (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradacartao() / 100));
				saldo = (double) (100 - orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradacartao());
				valorSaldo = orcamento.getTotalRS() - valorEntrada;
				orcamento.getOrcamentovoluntariadoformapagamento().setValorentradacartao(valorEntrada.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldocartao(valorSaldo.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldocartao(saldo.floatValue());
			} else if (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradacartao() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = (double) orcamento.getTotalRS();
				orcamento.getOrcamentovoluntariadoformapagamento().setValorentradacartao(valorEntrada.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldocartao(valorSaldo.floatValue());
				orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldocartao(saldo.floatValue());
			}
			valorSaldo = 0.0;
			if (orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelascartao() > 0) {
				if (orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldocartao() > 0) {
					valorSaldo = orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldocartao().doubleValue();
					if (valorSaldo > 0) {
						CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
						Coeficientejuros cf = coneficienteJurosFacade
								.consultar(orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelascartao(), "Juros Cliente");
						valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
						orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelacartao(valorSaldo.floatValue());
					}
				}
			}
		}
	}
	
	public void calcularParcelamento4() throws SQLException{ 
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradafinanciamento()!=null) {
			valorEntrada = orcamento.getTotalRS()
					* (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradafinanciamento().doubleValue() / 100);
			saldo = 100 - orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradafinanciamento().doubleValue();
			valorSaldo = orcamento.getTotalRS() - valorEntrada;
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradafinanciamento(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldofinanciamento(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldofinanciamento(saldo.floatValue());
		} else if (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradafinanciamento()==0){
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = (double) orcamento.getTotalRS();
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradafinanciamento(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldofinanciamento(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldofinanciamento(saldo.floatValue());
		}
		valorSaldo = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getNparcelasfinanciamento() > 0) {
			if (orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldofinanciamento() > 0) {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(orcamento.getOrcamentovoluntariadoformapagamento().getNparcelasfinanciamento(), "Juros Cliente");
				Double valor = orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldofinanciamento().doubleValue() * cf.getCoeficiente();
				orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelafinanciamento(valor.floatValue());
			} else {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(orcamento.getOrcamentovoluntariadoformapagamento().getNparcelasfinanciamento(), "Juros Cliente");
				Double valor = orcamento.getTotalRS() * cf.getCoeficiente();
				orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelafinanciamento(valor.floatValue());
			}
		} 
	}

	public void calcularParcelamentoValorEntrada2() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradaboleto() > 0) {
			valorEntrada = orcamento.getOrcamentovoluntariadoformapagamento().getValorentradaboleto().doubleValue();
			float percentualentrada = (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradaboleto() / orcamento.getTotalRS()) * 100;
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualentradaboleto(percentualentrada);
			saldo = (double) (100 - orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradaboleto());
			valorSaldo = orcamento.getTotalRS() - valorEntrada;
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradaboleto(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldoboleto(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldoboleto(saldo.floatValue());
		} else if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradaboleto() == 0) {
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = (double) orcamento.getTotalRS();
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradaboleto(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldoboleto(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldoboleto(saldo.floatValue());
		}
		valorSaldo = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelasboleto() > 0) {
			valorSaldo = orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldoboleto().doubleValue();
			if (valorSaldo > 0) {
				valorSaldo = valorSaldo / orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelasboleto();
				orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelaboleto(valorSaldo.floatValue());
			}
		}
	}

	public void calcularParcelamentoValorEntrada3() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradafinanciamento() > 0) {
			valorEntrada = orcamento.getOrcamentovoluntariadoformapagamento().getValorentradafinanciamento().doubleValue();
			float percentualentrada = (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradafinanciamento() / orcamento.getTotalRS()) * 100; 
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualentradafinanciamento(percentualentrada);
			saldo = (double) (100 - orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradafinanciamento());
			valorSaldo = orcamento.getTotalRS() - valorEntrada;
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradafinanciamento(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldofinanciamento(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldofinanciamento(saldo.floatValue());
		} else if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradafinanciamento() == 0) {
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = (double) orcamento.getTotalRS();
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradafinanciamento(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldofinanciamento(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldofinanciamento(saldo.floatValue());
		}
		valorSaldo = 0.0;  
		if (orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelascartao() > 0) {
			if (orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldocartao() > 0) {
				valorSaldo = orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldocartao().doubleValue();
				if (valorSaldo > 0) {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(orcamento.getOrcamentovoluntariadoformapagamento().getNumparcelascartao(), "Juros Cliente");
					valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
					orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelacartao(valorSaldo.floatValue());
				}
			}
		}
	}

	public void calcularParcelamentoValorEntrada4() throws SQLException {
		Double valorSaldo = 0.0; 
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradafinanciamento() > 0) {
			valorEntrada = orcamento.getOrcamentovoluntariadoformapagamento().getValorentradafinanciamento().doubleValue();
			float percentualentrada = (orcamento.getOrcamentovoluntariadoformapagamento().getValorentradafinanciamento() / orcamento.getTotalRS()) * 100; 
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualentradafinanciamento(percentualentrada);
			valorSaldo = orcamento.getTotalRS() - valorEntrada;
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradafinanciamento(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldofinanciamento(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldofinanciamento(saldo.floatValue());
		} else if (orcamento.getOrcamentovoluntariadoformapagamento().getPercentualentradafinanciamento() == 0) {
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = (double) orcamento.getTotalRS();
			orcamento.getOrcamentovoluntariadoformapagamento().setValorentradafinanciamento(valorEntrada.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setValorsaldofinanciamento(valorSaldo.floatValue());
			orcamento.getOrcamentovoluntariadoformapagamento().setPercentualsaldofinanciamento(saldo.floatValue());
		}
		valorSaldo = 0.0;
		if (orcamento.getOrcamentovoluntariadoformapagamento().getNparcelasfinanciamento() > 0) {
			if (orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldofinanciamento() > 0) {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(orcamento.getOrcamentovoluntariadoformapagamento().getNparcelasfinanciamento(), "Juros Cliente");
				Double valor = orcamento.getOrcamentovoluntariadoformapagamento().getValorsaldofinanciamento().doubleValue() * cf.getCoeficiente();
				orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelafinanciamento(valor.floatValue());
			} else {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(orcamento.getOrcamentovoluntariadoformapagamento().getNparcelasfinanciamento(), "Juros Cliente");
				Double valor = orcamento.getTotalRS() * cf.getCoeficiente();
				orcamento.getOrcamentovoluntariadoformapagamento().setValorparcelaboleto(valor.floatValue());
			}
		}
	}
	
	public void gerarListaNuneroParcelas(Date dataInicio){
    	NumeroParcelasBean np = new NumeroParcelasBean();
		np.setNumero(0);
		np.setTitulo("0");
		listaNumeroParcelas.add(np);
    	int dias = Formatacao.subtrairDatas(new Date(), dataInicio);
    	if (dias>30){
    		dias = dias-30;
    		dias = dias/30;
    		if (dias>0){
        		for(int i=0;i<dias;i++){
        			np  = new NumeroParcelasBean();
        			np.setNumero(i+1);
        			np.setTitulo(String.valueOf(i+1));
        			listaNumeroParcelas.add(np);
        		}
        	}
    	}
    }
	
	public String finalizarOrcamento(){
		if(orcamento.getOrcamentoprojetovoluntariado()!=null && 
				orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado()!=null){
			orcamentoprojetovoluntariado = orcamento.getOrcamentoprojetovoluntariado();
			excluirSeguro();
			excluirProdutosExtras();
			excluirFormaPagamento();
			excluirDesconto();
		}
		salvarOrcamento();
		salvarSeguro();
		salvarProdutosExtras();
		salvarDesconto();
		salvarFormaPagamento();
		if(orcamento.getCliente().isClienteLead()){
			salvarHistoricoLead(); 
			Lead lead = orcamento.getCliente().getLead();
			if(lead!=null){
				lead.setDataultimocontato(new Date());
				if (lead.getSituacao() < 3) {
					LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), 3, leadSituacaoDao);
        			lead.setSituacao(3);
				}
				lead = leadDao.salvar(lead);
			}
		}
		Mensagem.lancarMensagemInfo("Orçamento salvo com sucesso!", "");
		return "consVoluntariadoProjetoOrcamento";
	}
	
	public void salvarHistoricoLead() {
		Leadhistorico leadhistorico = new Leadhistorico();
		leadhistorico.setCliente(orcamento.getCliente());
		leadhistorico.setDatahistorico(new Date());
		leadhistorico.setDataproximocontato(new Date());
		TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
		String sql="Select t From Tipocontato t where t.tipo='Orçamento'";
		Tipocontato tipocontato = tipoContatoFacade.consultar(sql);
		leadhistorico.setTipocontato(tipocontato);
		leadhistorico.setHistorico("ORÇAMENTO TARIFÁRIO "+orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado()+": "+
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getDescricao()+" - "
		    +orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getCidade().getNome()+", "
			+orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getFornecedor().getNome()+".");
		leadhistorico.setTipoorcamento("t");
		leadhistorico.setIdorcamento(orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado());
		leadhistorico = leadHistoricoDao.salvar(leadhistorico);
    }
	
	public void salvarOrcamento(){
		Orcamentoprojetovoluntariado voluntariado;
		OrcamentoProjetoVoluntariadoFacade orcamentoProjetoVoluntariadoFacade = new OrcamentoProjetoVoluntariadoFacade();
		if(orcamento.getOrcamentoprojetovoluntariado()==null){
			voluntariado = new Orcamentoprojetovoluntariado();
		}else{
			voluntariado = orcamento.getOrcamentoprojetovoluntariado();
		}
		voluntariado.setDatainicial(orcamento.getDatainicial()); 
		voluntariado.setDatafinal(orcamento.getDatatermino());
		voluntariado.setNsemanas(orcamento.getTotalnumerosemanas());
		voluntariado.setPossuiacomodacao(orcamento.isPossuiAcomodacao());
		voluntariado.setPossuitransfer(orcamento.isPossuiTransfer());
		voluntariado.setValorcambio(orcamento.getValorcambio());
		voluntariado.setValorprojeto(orcamento.getValor());
		voluntariado.setValorprojetors(orcamento.getValorRS());
		voluntariado.setValorsemanaadicional(orcamento.getValorSemanaAdc());
		voluntariado.setValorsemanaadicionalrs(orcamento.getValorSemanaAdcRS());
		voluntariado.setNsemanaadicional(orcamento.getNumeroSemanasAdicionais());
		voluntariado.setValortotal(orcamento.getTotal());
		voluntariado.setValortotalrs(orcamento.getTotalRS());
		voluntariado.setVoluntariadoprojetovalor(orcamento.getVoluntariadoprojetovalor());
		voluntariado.setObs(obs);
		voluntariado.setDataorcamento(new Date());
		voluntariado.setUsuario(usuarioLogadoMB.getUsuario()); 
		voluntariado.setCliente(orcamento.getCliente());
		voluntariado = orcamentoProjetoVoluntariadoFacade.salvar(voluntariado);
		orcamentoprojetovoluntariado = voluntariado;
	}
	
	public void salvarFormaPagamento(){
		OrcamentoVoluntariadoFormaPagamentoFacade orcamentoVoluntariadoFormaPagamentoFacade = new OrcamentoVoluntariadoFormaPagamentoFacade();
		Orcamentovoluntariadoformapagamento formapagamento = orcamento.getOrcamentovoluntariadoformapagamento();
		formapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
		formapagamento = orcamentoVoluntariadoFormaPagamentoFacade.salvar(formapagamento);
	}
    
	public void salvarDesconto(){
		OrcamentoVoluntariadoDescontoFacade descontoFacade = new OrcamentoVoluntariadoDescontoFacade();
		Orcamentovoluntariadodesconto desconto = new Orcamentovoluntariadodesconto();
		if(orcamento.getListaDesconto()!=null){
			for (int i = 0; i < orcamento.getListaDesconto().size(); i++) {
				if(orcamento.getListaDesconto().get(i).isSelecionado()){
					desconto.setProdutosorcamento(orcamento.getListaDesconto().get(i).getProdutosorcamento());
					desconto.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
					desconto.setValor(orcamento.getListaDesconto().get(i).getValormoedaestrangeira());
					desconto.setValorrs(orcamento.getListaDesconto().get(i).getValormoedanacional());
					descontoFacade.salvar(desconto);
				}
			}
		}
	}
	
	public void salvarSeguro(){
		if(orcamento.isPossuiSeguroViagem()){
			Orcamentovoluntariadoseguro seguro = new Orcamentovoluntariadoseguro();
			OrcamentoVoluntariadoSeguroFacade voluntariadoSeguroFacade = new OrcamentoVoluntariadoSeguroFacade();
			seguro.setDatainicial(orcamento.getSeguroviagem().getDataInicio());
			seguro.setDatafinal(orcamento.getSeguroviagem().getDataTermino());
			seguro.setNumerodias(orcamento.getSeguroviagem().getNumeroSemanas());
			seguro.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
			seguro.setSomarvalortotal(orcamento.getSeguroviagem().isSomarvalortotal());
			seguro.setValor(orcamento.getSeguroviagem().getValorSeguro()/orcamento.getValorcambio());
			seguro.setValorrs(orcamento.getSeguroviagem().getValorSeguro());
			seguro.setValoresseguro(orcamento.getValorSeguro());
			seguro.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
			seguro = voluntariadoSeguroFacade.salvar(seguro);
		}
	}
	
	public void salvarProdutosExtras(){
		OrcamentoVoluntariadoProdutosExtrasFacade voluntariadoProdutosExtrasFacade = new OrcamentoVoluntariadoProdutosExtrasFacade();
		Orcamentovoluntariadoprodutosextras produtosextras;
		if(orcamento.getListaOutrosProdutos()!=null && orcamento.getListaOutrosProdutos().size()>0){ 
			for (int i = 0; i < orcamento.getListaOutrosProdutos().size(); i++) {
				produtosextras = new Orcamentovoluntariadoprodutosextras();
				produtosextras.setNome(orcamento.getListaOutrosProdutos().get(i).getDescricao());
				produtosextras.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
				produtosextras.setProdutosorcamento(orcamento.getListaOutrosProdutos().get(i).getProdutosorcamento());
				produtosextras.setSomarvalortotal(orcamento.getListaOutrosProdutos().get(i).isSomarvalortotal());
				produtosextras.setValor(orcamento.getListaOutrosProdutos().get(i).getValorOrigianl());
				produtosextras.setValorrs(orcamento.getListaOutrosProdutos().get(i).getValorOriginalRS());
				voluntariadoProdutosExtrasFacade.salvar(produtosextras);
			}
		}
		if(orcamento.getTaxatm()!=null){
			produtosextras = new Orcamentovoluntariadoprodutosextras();
			produtosextras.setProdutosorcamento(orcamento.getTaxatm());
			produtosextras.setNome(orcamento.getTaxatm().getDescricao());
			produtosextras.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
			produtosextras.setSomarvalortotal(true);
			produtosextras.setValor(orcamento.getValortaxatm());
			produtosextras.setValorrs(orcamento.getValortaxatmRS());
			voluntariadoProdutosExtrasFacade.salvar(produtosextras);
		}
	}
	
	public String voltar(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("orcamento", orcamento);
		return "resultadoOrcamentoVoluntariado";
	}
	
	public void excluirFormaPagamento(){ 
		OrcamentoVoluntariadoFormaPagamentoFacade formaPagamentoFacade = new OrcamentoVoluntariadoFormaPagamentoFacade();
		String sql = "select o from Orcamentovoluntariadodesconto o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoformapagamento> lista = formaPagamentoFacade.listar(sql);
		if(lista!=null && lista.size()>0){
			OrcamentoVoluntariadoFormaPagamentoFacade orcamentoVoluntariadoFormaPagamentoFacade = new OrcamentoVoluntariadoFormaPagamentoFacade();
			orcamentoVoluntariadoFormaPagamentoFacade.excluir(lista.get(0).getIdorcamentovoluntariadoformapagamento());
		}
	}
	
	public void excluirSeguro(){ 
		OrcamentoVoluntariadoSeguroFacade seguroFacade = new OrcamentoVoluntariadoSeguroFacade();
		String sql = "select o from Orcamentovoluntariadoseguro o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoseguro> lista = seguroFacade.listar(sql);
		if(lista!=null && lista.size()>0){
			OrcamentoVoluntariadoSeguroFacade orcamentoVoluntariadoSeguroFacade = new OrcamentoVoluntariadoSeguroFacade();
			orcamentoVoluntariadoSeguroFacade.excluir(lista.get(0).getIdorcamentovoluntariadoseguro());
		}
	}
	
	public void excluirDesconto(){
		OrcamentoVoluntariadoDescontoFacade descontoFacade = new OrcamentoVoluntariadoDescontoFacade();
		String sql = "select o from Orcamentovoluntariadodesconto o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadodesconto> lista = descontoFacade.listar(sql);
		if(lista!=null && lista.size()>0){
			for (int i = 0; i < lista.size(); i++) {
				descontoFacade.excluir(lista.get(i).getIdorcamentovoluntariadodesconto());
			}
		}
	}
	
	public void excluirProdutosExtras(){
		OrcamentoVoluntariadoProdutosExtrasFacade extrasFacade = new OrcamentoVoluntariadoProdutosExtrasFacade();
		String sql = "select o from Orcamentovoluntariadoprodutosextras o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamento.getOrcamentoprojetovoluntariado().getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoprodutosextras> lista = extrasFacade.listar(sql);
		if(lista!=null && lista.size()>0){
			for (int i = 0; i < lista.size(); i++) {
				extrasFacade.excluir(lista.get(i).getIdorcamentovoluntariadoprodutosextras());
			}
		}
	}
}
