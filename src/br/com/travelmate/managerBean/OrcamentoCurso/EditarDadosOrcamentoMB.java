package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.bean.NumeroParcelasBean;
import br.com.travelmate.dao.OCursoDao;
import br.com.travelmate.dao.OCursoFormaPagamentoDao;
import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.facade.OcClienteFacade;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Occliente;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursoformapagamento;
import br.com.travelmate.model.Publicidade;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Kamila
 */
@Named
@ViewScoped
public class EditarDadosOrcamentoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Ocurso ocurso;
	private Ocursoformapagamento formaPagamento2;
	private Ocursoformapagamento formaPagamento3;
	private Ocursoformapagamento formaPagamento4;
	private boolean digitosFoneResidencial;
	private boolean digitosFoneCelular;
	private List<Publicidade> listaPublicidades;
	private List<NumeroParcelasBean> listaNumeroParcelas;
	private NumeroParcelasBean numeroParcelaSelecionado;
	private boolean painelcliente=true;
	private boolean painelOcCliente=false;
	private Occliente occliente;
	
	@Inject
	private OCursoDao oCursoDao;
	@Inject 
	private OCursoFormaPagamentoDao oCursoFormaPagamentoDao;
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		ocurso = (Ocurso) session.getAttribute("ocurso");
		session.removeAttribute("ocurso");
		digitosFoneCelular = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				ocurso.getCliente().getFoneCelular());
		digitosFoneResidencial = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(),
				ocurso.getCliente().getFoneResidencial());
		gerarListaPublicidade();
		if (ocurso.getOcursoformapagamentoList().size() > 0) {
			formaPagamento2 = ocurso.getOcursoformapagamentoList().get(0);
		} else {
			formaPagamento2 = new Ocursoformapagamento();
			formaPagamento2.setOcurso(ocurso);
		}
		if (ocurso.getOcursoformapagamentoList().size() > 1) {
			formaPagamento3 = ocurso.getOcursoformapagamentoList().get(1);
		} else {
			formaPagamento3 = new Ocursoformapagamento();
			formaPagamento3.setOcurso(ocurso);
		}
		if (ocurso.getOcursoformapagamentoList().size() > 2) {
			formaPagamento4 = ocurso.getOcursoformapagamentoList().get(2);
		} else {
			formaPagamento4 = new Ocursoformapagamento();
			formaPagamento4.setOcurso(ocurso);
		}
		listaNumeroParcelas = new ArrayList<NumeroParcelasBean>();
		gerarListaNumeroParcelas(ocurso.getDatainicio());
		if(ocurso.getCliente()!=null && ocurso.getCliente().getIdcliente()>1){
			ocurso.setNomecliente(ocurso.getCliente().getNome());
		}else{
			OcClienteFacade ocClienteFacade = new OcClienteFacade();
			occliente = ocClienteFacade.consultar(ocurso.getOccliente());
			painelOcCliente = true;
			painelcliente =false;
		}  
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Ocursoformapagamento getFormaPagamento2() {
		return formaPagamento2;
	}

	public void setFormaPagamento2(Ocursoformapagamento formaPagamento2) {
		this.formaPagamento2 = formaPagamento2;
	}

	public Ocursoformapagamento getFormaPagamento3() {
		return formaPagamento3;
	}

	public void setFormaPagamento3(Ocursoformapagamento formaPagamento3) {
		this.formaPagamento3 = formaPagamento3;
	}

	public Ocursoformapagamento getFormaPagamento4() {
		return formaPagamento4;
	}

	public void setFormaPagamento4(Ocursoformapagamento formaPagamento4) {
		this.formaPagamento4 = formaPagamento4;
	}

	public boolean isDigitosFoneResidencial() {
		return digitosFoneResidencial;
	}

	public void setDigitosFoneResidencial(boolean digitosFoneResidencial) {
		this.digitosFoneResidencial = digitosFoneResidencial;
	}

	public boolean isDigitosFoneCelular() {
		return digitosFoneCelular;
	}

	public void setDigitosFoneCelular(boolean digitosFoneCelular) {
		this.digitosFoneCelular = digitosFoneCelular;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Publicidade> getListaPublicidades() {
		return listaPublicidades;
	}

	public void setListaPublicidades(List<Publicidade> listaPublicidades) {
		this.listaPublicidades = listaPublicidades;
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

	public boolean isPainelcliente() {
		return painelcliente;
	}

	public void setPainelcliente(boolean painelcliente) {
		this.painelcliente = painelcliente;
	}

	public boolean isPainelOcCliente() {
		return painelOcCliente;
	}

	public void setPainelOcCliente(boolean painelOcCliente) {
		this.painelOcCliente = painelOcCliente;
	}

	public Occliente getOccliente() {
		return occliente;
	}

	public void setOccliente(Occliente occliente) {
		this.occliente = occliente;
	}

	public String cancelar() {
		ocurso = new Ocurso();
		RequestContext.getCurrentInstance().closeDialog(ocurso);
		return "";
	}

	public String salvar() throws SQLException {
		ocurso.setOcursoformapagamentoList(new ArrayList<Ocursoformapagamento>());
			if (formaPagamento2.getValorparcela() != null && formaPagamento2.getValorEntrada() > 0) {
				formaPagamento2 = oCursoFormaPagamentoDao.salvar(formaPagamento2);
				ocurso.getOcursoformapagamentoList().add(formaPagamento2);
			}
			if (formaPagamento3.getValorEntrada() != null && formaPagamento3.getValorEntrada() > 0) {
				formaPagamento3 = oCursoFormaPagamentoDao.salvar(formaPagamento3);
				ocurso.getOcursoformapagamentoList().add(formaPagamento3);
			}
			if (formaPagamento4.getValorEntrada() != null && formaPagamento4.getValorEntrada() > 0) {
				formaPagamento4 = oCursoFormaPagamentoDao.salvar(formaPagamento4);
				ocurso.getOcursoformapagamentoList().add(formaPagamento4);
			}
		ocurso = oCursoDao.salvar(ocurso);
		if(painelOcCliente){
			salvarOcCliente();
		}
		RequestContext.getCurrentInstance().closeDialog(ocurso);
		return "";
	}

	public String validarMascaraFoneResidencial() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneResidencial);
	}

	public String validarMascaraFoneCelular() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneCelular);
	}

	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade();
		try {
			listaPublicidades = publicidadeFacade.listar();
			if (listaPublicidades == null) {
				listaPublicidades = new ArrayList<Publicidade>();
			}
		} catch (SQLException ex) {
			Mensagem.lancarMensagemErro("Erro Listar Publicidade", "ERRO");
		}
	}

	public void alterarValorCambio() {
		ocurso.setTotalmoedanacional(ocurso.getTotalmoedaestrangeira() * ocurso.getValorcambio());
		ocurso.setDataorcamento(new Date());
		ocurso = oCursoDao.salvar(ocurso);
		try {
			recalcularFormaPagamento();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void recalcularFormaPagamento() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		// forma de pagamento 2
		if (formaPagamento2.getPercentualEntrada() != null && formaPagamento2.getPercentualEntrada() > 0) {
			valorEntrada = ocurso.getTotalmoedanacional() * (formaPagamento2.getPercentualEntrada() / 100);
			saldo = 100 - formaPagamento2.getPercentualEntrada();
			valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
			formaPagamento2.setValorEntrada(valorEntrada.floatValue());
			formaPagamento2.setValorSaldo(valorSaldo.floatValue());
			formaPagamento2.setPercentualSaldo(saldo);
			valorSaldo = formaPagamento2.getValorSaldo().doubleValue();
			valorSaldo = valorSaldo / formaPagamento2.getNumeroparcela();
			formaPagamento2.setValorparcela(valorSaldo.floatValue());
		}

		// forma de pagamento 3
		valorSaldo = 0.0;
		saldo = 0.0;
		valorEntrada = 0.0;
		if (formaPagamento3.getPercentualEntrada() != null && formaPagamento3.getPercentualEntrada() > 0) {
			valorEntrada = ocurso.getTotalmoedanacional() * (formaPagamento3.getPercentualEntrada() / 100);
			saldo = 100 - formaPagamento3.getPercentualEntrada();
			valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
			formaPagamento3.setValorEntrada(valorEntrada.floatValue());
			formaPagamento3.setValorSaldo(valorSaldo.floatValue());
			formaPagamento3.setPercentualSaldo(saldo.doubleValue());
		}
		valorSaldo = 0.0;
		if (formaPagamento3.getNumeroparcela() > 0) {
			if (formaPagamento3.getValorSaldo() != null && formaPagamento3.getValorSaldo() > 0) {
				valorSaldo = formaPagamento3.getValorSaldo().doubleValue();
				if (valorSaldo > 0) {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento3.getNumeroparcela(), "Juros Cliente");
					valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
					formaPagamento3.setValorparcela(valorSaldo.floatValue());
				}
			}
		}

		valorSaldo = 0.0;
		saldo = 0.0;
		valorEntrada = 0.0;
		if (formaPagamento4.getPercentualEntrada() != null && formaPagamento4.getPercentualEntrada() > 0) {
			valorEntrada = ocurso.getTotalmoedanacional()
					* (formaPagamento4.getPercentualEntrada().doubleValue() / 100);
			saldo = 100 - formaPagamento4.getPercentualEntrada().doubleValue();
			valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
			formaPagamento4.setValorEntrada(valorEntrada.floatValue());
			formaPagamento4.setValorSaldo(valorSaldo.floatValue());
			formaPagamento4.setPercentualSaldo(saldo);
		} else {
			valorEntrada = 0.0;
			saldo = 100 - formaPagamento4.getPercentualEntrada().doubleValue();
			valorSaldo = ocurso.getTotalmoedanacional() - valorEntrada;
			formaPagamento4.setValorEntrada(valorEntrada.floatValue());
			formaPagamento4.setValorSaldo(valorSaldo.floatValue());
			formaPagamento4.setPercentualSaldo(saldo);
		}
		valorSaldo = 0.0;
		if (formaPagamento4.getNumeroparcela() > 0) {
			if (formaPagamento4.getValorSaldo() != null && formaPagamento4.getValorSaldo() > 0) {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento4.getNumeroparcela(), "Juros Cliente");
				Double valor = formaPagamento4.getValorSaldo().doubleValue() * cf.getCoeficiente();
				formaPagamento4.setValorparcela(valor.floatValue());
			} else {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formaPagamento4.getNumeroparcela(), "Juros Cliente");
				Double valor = ocurso.getTotalmoedanacional() * cf.getCoeficiente();
				formaPagamento4.setValorparcela(valor.floatValue());
			}
		}
	}

	public void gerarListaNumeroParcelas(Date dataInicio) {
		NumeroParcelasBean np = new NumeroParcelasBean();
		np.setNumero(0);
		np.setTitulo("0");
		listaNumeroParcelas.add(np);
		int dias = Formatacao.subtrairDatas(dataInicio, new Date());
		if (dias > 30) {
			dias = dias / 30;
			if (dias > 0) {
				for (int i = 0; i < dias; i++) {
					np = new NumeroParcelasBean();
					np.setNumero(i + 1);
					np.setTitulo(String.valueOf(i + 1));
					listaNumeroParcelas.add(np);
				}
			}
		}
	}
	
	public void salvarOcCliente() {
		occliente.setUnidadenegocio(usuarioLogadoMB.getUsuario().getUnidadenegocio());
		OcClienteFacade occlienteFacade = new OcClienteFacade();
		occliente = occlienteFacade.salvar(occliente);
	}

}
