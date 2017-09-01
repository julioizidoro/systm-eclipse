package br.com.travelmate.managerBean.cursospacotes;
 
import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.facade.CursosPacotesFacade;
import br.com.travelmate.facade.CursosPacotesFormaPagamentoFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Cursopacoteformapagamento;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class CadCursosPacotesMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Cursospacote cursospacote;
	private Fornecedorcidadeidioma fornecedorCidadeIdioma; 
	private String sql;
	private Cursopacoteformapagamento formapagamento;
	private Produtos produtos;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			fornecedorCidadeIdioma = (Fornecedorcidadeidioma) session.getAttribute("fornecedorcidadeidioma");
			sql = (String) session.getAttribute("sql"); 
			cursospacote = (Cursospacote) session.getAttribute("cursospacote");
			produtos = (Produtos) session.getAttribute("produtos");
			session.removeAttribute("produtos");
			session.removeAttribute("cursospacote");
			session.removeAttribute("sql");
			session.removeAttribute("fornecedorcidadeidioma"); 
			if (cursospacote == null) {
				cursospacote = new Cursospacote();
				cursospacote.setFornecedorcidadeidioma(fornecedorCidadeIdioma);  
				formapagamento = new Cursopacoteformapagamento();
			} else{
				CursosPacotesFormaPagamentoFacade cursosPacotesFormaPagamentoFacade = new CursosPacotesFormaPagamentoFacade();
				formapagamento = cursosPacotesFormaPagamentoFacade.consultar("select c from Cursopacoteformapagamento c where"
						+ " c.cursospacote.idcursospacote="+cursospacote.getIdcursospacote());
				if(formapagamento==null){
					formapagamento = new Cursopacoteformapagamento();
				}
				produtos = cursospacote.getProdutos();
			}
		}   
	}  

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Cursospacote getCursospacote() {
		return cursospacote;
	}

	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	}

	public Fornecedorcidadeidioma getFornecedorCidadeIdioma() {
		return fornecedorCidadeIdioma;
	}

	public void setFornecedorCidadeIdioma(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		this.fornecedorCidadeIdioma = fornecedorCidadeIdioma;
	} 

	public Cursopacoteformapagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(Cursopacoteformapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public String cancelar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("sql", sql);
		return "cursospacotes";
	}

	public String selecionarCoproduto(String tipo) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("tipoproduto", tipo);
		session.setAttribute("cursospacote", cursospacote);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		RequestContext.getCurrentInstance().openDialog("selecionarProdutos", options, null);
		return "";
	}

	public boolean validarDados() {
		if (cursospacote.getDatainicovenda() == null || cursospacote.getDataterminovenda() == null) {
			Mensagem.lancarMensagemErro("Atenção!", "Informe o período de venda do pacote.");
			return false;
		} else if (cursospacote.getDatainiciocurso() == null || cursospacote.getDatainiciocurso() == null) {
			Mensagem.lancarMensagemErro("Atenção!", "Informe o período de início do pacote.");
			return false;
		} else if (cursospacote.getDescricao() == null || cursospacote.getDescricao().length() == 0) {
			Mensagem.lancarMensagemErro("Atenção!", "Informe a descrição do pacote.");
			return false;
		} else if (cursospacote.getValorcoprodutos_acomodacao() != null) {
			if (cursospacote.getNumerosemanaacomodacao() == 0) {
				Mensagem.lancarMensagemErro("Atenção!", "Informe o número de semanas de acomodação.");
				return false;
			}
		}
		return true;
	}

	public String salvar() {
		if (validarDados()) {
			CursosPacotesFacade cursosPacotesFacade = new CursosPacotesFacade();
			cursospacote.setProdutos(produtos); 
			int idcurso = produtos.getIdprodutos();
			if(idcurso != aplicacaoMB.getParametrosprodutos().getCursos()) {
				ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
				Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
						.consultar(aplicacaoMB.getParametrosprodutos().getTaxatmorcamento());
				if(valorcoprodutos!=null) {
					cursospacote.setValorcoprodutos_curso(valorcoprodutos);
				}
			}
			cursospacote = cursosPacotesFacade.salvar(cursospacote);
			formapagamento.setCursospacote(cursospacote);
			CursosPacotesFormaPagamentoFacade pacotesFormaPagamentoFacade = new CursosPacotesFormaPagamentoFacade();
			formapagamento = pacotesFormaPagamentoFacade.salvar(formapagamento);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");  
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("sql", sql);
			return "cursospacotes";
		}
		return "";
	}

	public void calcularValorTotalCurso() {
		if (cursospacote.getValorcoprodutos_curso() != null) {
			if (cursospacote.getValorcoprodutos_curso().getCobranca().equalsIgnoreCase("S")) {
				cursospacote.setValortotalcurso(cursospacote.getNumerosemanacurso()
						* cursospacote.getValorcoprodutos_curso().getValororiginal());
			} else if (cursospacote.getValorcoprodutos_curso().getCobranca().equalsIgnoreCase("F")) {
				cursospacote.setValortotalcurso(cursospacote.getValorcoprodutos_curso().getValororiginal());
			} else if (cursospacote.getValorcoprodutos_curso().getCobranca().equalsIgnoreCase("D")) {
				int multiplicador = cursospacote.getNumerosemanacurso() * 7;
				cursospacote
						.setValortotalcurso(multiplicador * cursospacote.getValorcoprodutos_curso().getValororiginal());
			} 
		} else {
			Mensagem.lancarMensagemErro("Atenção!", "Selecione um curso.");
		}
	}

	public void calcularValorTotalAcomodacao() {
		if (cursospacote.getValorcoprodutos_acomodacao() != null) {
			if (cursospacote.getValorcoprodutos_acomodacao().getCobranca().equalsIgnoreCase("S")) {
				cursospacote.setValortotalacomodacao(cursospacote.getNumerosemanaacomodacao()
						* cursospacote.getValorcoprodutos_acomodacao().getValororiginal());
			} else if (cursospacote.getValorcoprodutos_acomodacao().getCobranca().equalsIgnoreCase("F")) {
				cursospacote.setValortotalacomodacao(cursospacote.getValorcoprodutos_acomodacao().getValororiginal());
			} else if (cursospacote.getValorcoprodutos_acomodacao().getCobranca().equalsIgnoreCase("D")) {
				int multiplicador = (int) (cursospacote.getNumerosemanaacomodacao() * 7);
				cursospacote.setValortotalacomodacao(
						multiplicador * cursospacote.getValorcoprodutos_acomodacao().getValororiginal());
			} 
		} else {
			Mensagem.lancarMensagemErro("Atenção!", "Selecione uma acomodação.");
		}
	}
	
	
	public void calcularParcelamento2() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (formapagamento.getEntradaboleto() != null) {
			if (formapagamento.getEntradaboleto()!=null) {
				valorEntrada = (double) (cursospacote.getValoravista() * (formapagamento.getEntradaboleto() / 100));
				saldo = (double) (100 - formapagamento.getEntradaboleto());
				valorSaldo = cursospacote.getValoravista() - valorEntrada;
				formapagamento.setValorentradaboleto(valorEntrada.floatValue());
				formapagamento.setValorsaldoboleto(valorSaldo.floatValue());
				formapagamento.setSaldoboleto(saldo.floatValue());
			} else if (formapagamento.getValorentradaboleto() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = cursospacote.getValoravista().doubleValue();
				formapagamento.setValorentradaboleto(valorEntrada.floatValue());
				formapagamento.setValorsaldoboleto(valorSaldo.floatValue());
				formapagamento.setSaldoboleto(saldo.floatValue());
			}
			valorSaldo = 0.0;
			if (formapagamento.getNumeroparcelasboleto()!=null && formapagamento.getNumeroparcelasboleto() > 0) {
				valorSaldo = formapagamento.getValorsaldoboleto().doubleValue();
				if (valorSaldo > 0) {
					valorSaldo = valorSaldo / formapagamento.getNumeroparcelasboleto();
					formapagamento.setValorparcelaboleto(valorSaldo.floatValue());
				}
			}
		}
	}

	public void calcularParcelamento3() throws SQLException {
		if (formapagamento.getEntradacartao() != null) {
			Double valorSaldo = 0.0;
			Double saldo = 0.0;
			Double valorEntrada = 0.0;
			if (formapagamento.getEntradacartao()!=null) {
				valorEntrada = (double) (cursospacote.getValoravista() * (formapagamento.getEntradacartao() / 100));
				saldo = (double) (100 - formapagamento.getEntradacartao());
				valorSaldo = cursospacote.getValoravista() - valorEntrada;
				formapagamento.setValorentradacartao(valorEntrada.floatValue());
				formapagamento.setValorsaldocartao(valorSaldo.floatValue());
				formapagamento.setSaldocartao(saldo.floatValue());
			} else if (formapagamento.getValorentradacartao() == 0) {
				valorEntrada = 0.0;
				saldo = 100.0;
				valorSaldo = cursospacote.getValoravista().doubleValue();
				formapagamento.setValorentradacartao(valorEntrada.floatValue());
				formapagamento.setValorsaldocartao(valorSaldo.floatValue());
				formapagamento.setSaldocartao(saldo.floatValue());
			}
			valorSaldo = 0.0;
			if (formapagamento.getNumeroparcelascartao()!=null && formapagamento.getNumeroparcelascartao() > 0) {
				if (formapagamento.getValorsaldocartao() > 0) {
					valorSaldo = formapagamento.getValorsaldocartao().doubleValue();
					if (valorSaldo > 0) {
						CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
						Coeficientejuros cf = coneficienteJurosFacade
								.consultar(formapagamento.getNumeroparcelascartao(), "Juros Cliente");
						valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
						formapagamento.setValorparcelacartao(valorSaldo.floatValue());
					}
				}
			}
		}
	}
	
	public void calcularParcelamento4() throws SQLException{ 
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (formapagamento.getEntradafinanciamento()!=null) {
			valorEntrada = cursospacote.getValoravista()
					* (formapagamento.getEntradafinanciamento().doubleValue() / 100);
			saldo = 100 - formapagamento.getEntradafinanciamento().doubleValue();
			valorSaldo = cursospacote.getValoravista() - valorEntrada;
			formapagamento.setValorentradafinanciamento(valorEntrada.floatValue());
			formapagamento.setValorsaldofinanciamento(valorSaldo.floatValue());
			formapagamento.setSaldofinanciamento(saldo.floatValue());
		} else if (formapagamento.getValorentradafinanciamento()==0){
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = cursospacote.getValoravista().doubleValue();
			formapagamento.setValorentradafinanciamento(valorEntrada.floatValue());
			formapagamento.setValorsaldofinanciamento(valorSaldo.floatValue());
			formapagamento.setSaldofinanciamento(saldo.floatValue());
		}
		valorSaldo = 0.0;
		if (formapagamento.getNparcelasfinanciamento()!=null && formapagamento.getNparcelasfinanciamento() > 0) {
			if (formapagamento.getValorsaldofinanciamento() > 0) {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formapagamento.getNparcelasfinanciamento().intValue(), "Juros Cliente");
				Double valor = formapagamento.getValorsaldofinanciamento().doubleValue() * cf.getCoeficiente();
				formapagamento.setValorparcelafinanciamento(valor.floatValue());
			} else {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formapagamento.getNparcelasfinanciamento().intValue(), "Juros Cliente");
				Double valor = cursospacote.getValoravista() * cf.getCoeficiente();
				formapagamento.setValorparcelafinanciamento(valor.floatValue());
			}
		} 
	}

	public void calcularParcelamentoValorEntrada2() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (formapagamento.getValorentradaboleto() > 0) {
			valorEntrada = formapagamento.getValorentradaboleto().doubleValue();
			float percentualentrada = (formapagamento.getValorentradaboleto() / cursospacote.getValoravista()) * 100;
			formapagamento.setEntradaboleto(percentualentrada);
			saldo = (double) (100 - formapagamento.getEntradaboleto());
			valorSaldo = cursospacote.getValoravista() - valorEntrada;
			formapagamento.setValorentradaboleto(valorEntrada.floatValue());
			formapagamento.setValorsaldoboleto(valorSaldo.floatValue());
			formapagamento.setSaldoboleto(saldo.floatValue());
		} else if (formapagamento.getEntradaboleto() == 0) {
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = cursospacote.getValoravista().doubleValue();
			formapagamento.setValorentradaboleto(valorEntrada.floatValue());
			formapagamento.setValorsaldoboleto(valorSaldo.floatValue());
			formapagamento.setSaldoboleto(saldo.floatValue());
		}
		valorSaldo = 0.0;
		if (formapagamento.getNumeroparcelasboleto() > 0) {
			valorSaldo = formapagamento.getValorsaldoboleto().doubleValue();
			if (valorSaldo > 0) {
				valorSaldo = valorSaldo / formapagamento.getNumeroparcelasboleto();
				formapagamento.setValorparcelaboleto(valorSaldo.floatValue());
			}
		}
	}

	public void calcularParcelamentoValorEntrada3() throws SQLException {
		Double valorSaldo = 0.0;
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (formapagamento.getValorentradafinanciamento() > 0) {
			valorEntrada = formapagamento.getValorentradafinanciamento().doubleValue();
			float percentualentrada = (formapagamento.getValorentradafinanciamento() / cursospacote.getValoravista()) * 100; 
			formapagamento.setEntradafinanciamento(percentualentrada);
			saldo = (double) (100 - formapagamento.getEntradafinanciamento());
			valorSaldo = cursospacote.getValoravista() - valorEntrada;
			formapagamento.setValorentradafinanciamento(valorEntrada.floatValue());
			formapagamento.setValorsaldofinanciamento(valorSaldo.floatValue());
			formapagamento.setSaldofinanciamento(saldo.floatValue());
		} else if (formapagamento.getEntradafinanciamento() == 0) {
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = cursospacote.getValoravista().doubleValue();
			formapagamento.setValorentradafinanciamento(valorEntrada.floatValue());
			formapagamento.setValorsaldofinanciamento(valorSaldo.floatValue());
			formapagamento.setSaldofinanciamento(saldo.floatValue());
		}
		valorSaldo = 0.0;  
		if (formapagamento.getNumeroparcelascartao() > 0) {
			if (formapagamento.getValorsaldocartao() > 0) {
				valorSaldo = formapagamento.getValorsaldocartao().doubleValue();
				if (valorSaldo > 0) {
					CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
					Coeficientejuros cf = coneficienteJurosFacade.consultar(formapagamento.getNumeroparcelascartao(), "Juros Cliente");
					valorSaldo = (double) (valorSaldo * cf.getCoeficiente());
					formapagamento.setValorparcelacartao(valorSaldo.floatValue());
				}
			}
		}
	}

	public void calcularParcelamentoValorEntrada4() throws SQLException {
		Double valorSaldo = 0.0; 
		Double saldo = 0.0;
		Double valorEntrada = 0.0;
		if (formapagamento.getValorentradafinanciamento() > 0) {
			valorEntrada = formapagamento.getValorentradafinanciamento().doubleValue();
			float percentualentrada = (formapagamento.getValorentradafinanciamento() / cursospacote.getValoravista()) * 100; 
			formapagamento.setEntradafinanciamento(percentualentrada);
			valorSaldo = cursospacote.getValoravista() - valorEntrada;
			formapagamento.setValorentradafinanciamento(valorEntrada.floatValue());
			formapagamento.setValorsaldofinanciamento(valorSaldo.floatValue());
			formapagamento.setSaldofinanciamento(saldo.floatValue());
		} else if (formapagamento.getEntradafinanciamento() == 0) {
			valorEntrada = 0.0;
			saldo = 100.0;
			valorSaldo = cursospacote.getValoravista().doubleValue();
			formapagamento.setValorentradafinanciamento(valorEntrada.floatValue());
			formapagamento.setValorsaldofinanciamento(valorSaldo.floatValue());
			formapagamento.setSaldofinanciamento(saldo.floatValue());
		}
		valorSaldo = 0.0;
		if (formapagamento.getNparcelasfinanciamento() > 0) {
			if (formapagamento.getValorsaldofinanciamento() > 0) {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formapagamento.getNparcelasfinanciamento().intValue(), "Juros Cliente");
				Double valor = formapagamento.getValorsaldofinanciamento().doubleValue() * cf.getCoeficiente();
				formapagamento.setValorparcelafinanciamento(valor.floatValue());
			} else {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formapagamento.getNparcelasfinanciamento().intValue(), "Juros Cliente");
				Double valor = cursospacote.getValoravista() * cf.getCoeficiente();
				formapagamento.setValorparcelaboleto(valor.floatValue());
			}
		}
	}
 

}
