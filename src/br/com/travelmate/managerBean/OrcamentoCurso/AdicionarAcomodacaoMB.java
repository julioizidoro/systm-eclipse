package br.com.travelmate.managerBean.OrcamentoCurso;

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

import br.com.travelmate.facade.CoProdutosFacade; 
import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Coprodutos; 
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class AdicionarAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private ResultadoOrcamentoBean resultadoOrcamentoBean;
	private List<ProdutosOrcamentoBean> listaAcomodacoes;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		resultadoOrcamentoBean = (ResultadoOrcamentoBean) session.getAttribute("resultadoOrcamentoBean");
		session.removeAttribute("resultadoOrcamentoBean");
		gerarListaAcomodacao();
	}

	public ResultadoOrcamentoBean getResultadoOrcamentoBean() {
		return resultadoOrcamentoBean;
	}

	public void setResultadoOrcamentoBean(ResultadoOrcamentoBean resultadoOrcamentoBean) {
		this.resultadoOrcamentoBean = resultadoOrcamentoBean;
	}

	public List<ProdutosOrcamentoBean> getListaAcomodacoes() {
		return listaAcomodacoes;
	}

	public void setListaAcomodacoes(List<ProdutosOrcamentoBean> listaAcomodacoes) {
		this.listaAcomodacoes = listaAcomodacoes;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void gerarListaAcomodacao() {
		listaAcomodacoes = new ArrayList<>();
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		String sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ resultadoOrcamentoBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and c.tipo='Acomodacao" + "' and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao() + " and c.apenaspacote=FALSE";
		List<Coprodutos> listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) {
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						resultadoOrcamentoBean.getDataConsulta());
				if (po != null) {
					listaAcomodacoes.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date());
					if (po != null) {
						listaAcomodacoes.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(),
								resultadoOrcamentoBean.getDataConsulta());
						if (po != null) {
							listaAcomodacoes.add(po);
						}
					}
				}
			}
		}
	}

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataconsulta) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataconsulta) + "' and v.numerosemanainicial<="
				+ resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " and v.numerosemanafinal>="
				+ resultadoOrcamentoBean.getOcurso().getNumerosemanas() + " and v.tipodata='" + tipoData
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
		List<Valorcoprodutos> listaValorCoprodutos = valorCoProdutosFacade.listar(sql);
		int ano;
		if (resultadoOrcamentoBean.getFornecedorcidadeidioma() != null) {
			ano = resultadoOrcamentoBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor()
					.getAnotarifario();
		} else {
			ano = Formatacao.getAnoData(new Date());
		}
		if (listaValorCoprodutos != null) {
			for (int n = 0; n < listaValorCoprodutos.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorCoprodutos.get(n);
				} else {
					valorcoprodutos = compararValores(listaValorCoprodutos.get(n), valorcoprodutos);
				}
			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			po.setValorOrigianl(0.0f);
			po.setValorOriginalRS(0.0f);
			return po;
		}
		return null;
	}

	public Valorcoprodutos compararValores(Valorcoprodutos valorNovo, Valorcoprodutos valorAtual) {
		if (valorNovo.getPromocional() != null && valorNovo.getPromocional()) {
			return valorNovo;
		} else
			return valorAtual;
	}

	public String adicionar(ProdutosOrcamentoBean po) {
		po.setSelecionado(true);
		RequestContext.getCurrentInstance().closeDialog(po);
		return "";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public boolean renderedValorPromocionalAparecendo(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean renderedValorPromocionalEscondido(ProdutosOrcamentoBean produtosOrcamentoBean) {
		if (produtosOrcamentoBean.isPromocao() && produtosOrcamentoBean.getValorPromocional() != null) {
			return false;
		} else {
			return true;
		}
	} 

	public void calcularValorAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		Valorcoprodutos valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos(); 
		valorcoprodutos = null;
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade(); 
		String sql = "Select v from  Valorcoprodutos v where v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(resultadoOrcamentoBean.getDataConsulta()) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(resultadoOrcamentoBean.getDataConsulta())
				+ "' and v.numerosemanainicial<=" + produtosOrcamentoBean.getNumeroSemanas()
				+ " and v.numerosemanafinal>=" + produtosOrcamentoBean.getNumeroSemanas()
				+ " and v.coprodutos.idcoprodutos="
				+ produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getIdcoprodutos();
		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				} else {
					valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
				}
			}
		}
		if (valorcoprodutos != null) {
			produtosOrcamentoBean.setValorcoprodutos(valorcoprodutos); 
			int multiplicador = 1;
			if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) produtosOrcamentoBean.getNumeroSemanas();
			} else if (produtosOrcamentoBean.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (produtosOrcamentoBean.getNumeroSemanas() * 7);
			}
			produtosOrcamentoBean
					.setValorOrigianl(multiplicador * produtosOrcamentoBean.getValorcoprodutos().getValororiginal());
			produtosOrcamentoBean.setValorOriginalRS(
					produtosOrcamentoBean.getValorOrigianl() * resultadoOrcamentoBean.getOcurso().getValorcambio());
			gerarPromocaoAcomodacao(produtosOrcamentoBean);
		}else{ 
			produtosOrcamentoBean.setValorOrigianl(0.0f);
			produtosOrcamentoBean.setValorOriginalRS(0.0f);
			produtosOrcamentoBean.setNumeroSemanas(0);
		}
	}
	
	public void gerarPromocaoAcomodacao(ProdutosOrcamentoBean produtosOrcamentoBean) {
		String sql = "select p From Promocaoacomodacaocidade p where p.promocaoacomodacao.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaoacomodacao.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "'  and p.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ resultadoOrcamentoBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " group by p.promocaoacomodacao.idpromoacaoacomodacao";
		PromocaoAcomodacaoCidadeFacade cidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		Promocaoacomodacaocidade promocaoacomodacaocidade = cidadeFacade.consultar(sql);
		Valorcoprodutos valorcoprodutos = null;
		if (produtosOrcamentoBean.getValorcoprodutos().getCoprodutos().getComplementoacomodacao() != null) {
			valorcoprodutos = produtosOrcamentoBean.getValorcoprodutos();
		}
		if (promocaoacomodacaocidade != null && valorcoprodutos != null) {
			boolean tempromocao = verificarPromocaoAcomodacaoValida(promocaoacomodacaocidade.getPromocaoacomodacao(),
					valorcoprodutos, produtosOrcamentoBean);
			if (tempromocao) {
				float valordesconto = 0.0f;
				if (resultadoOrcamentoBean.getOcurso().getDatavalidade() == null) {
					resultadoOrcamentoBean.getOcurso()
							.setDatavalidade(promocaoacomodacaocidade.getPromocaoacomodacao().getDatavalidadefinal());
				} else if (resultadoOrcamentoBean.getOcurso().getDatavalidade() != null
						&& resultadoOrcamentoBean.getOcurso().getDatavalidade()
								.after(promocaoacomodacaocidade.getPromocaoacomodacao().getDatavalidadefinal())) {
					resultadoOrcamentoBean.getOcurso()
							.setDatavalidade(promocaoacomodacaocidade.getPromocaoacomodacao().getDatavalidadefinal());
				}
				if (promocaoacomodacaocidade.getPromocaoacomodacao().getTipodesconto().equalsIgnoreCase("P")) {
					if (promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana() != null
							&& promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana() > 0) {
						valordesconto = valorcoprodutos.getValororiginal()
								* (promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana() / 100);
						valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
					} else if (promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal() != null
							&& promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal() > 0) {
						valordesconto = (float) (valorcoprodutos.getValororiginal()
								* produtosOrcamentoBean.getNumeroSemanas());
						valordesconto = valordesconto
								* (promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal() / 100);
					}
				} else if (promocaoacomodacaocidade.getPromocaoacomodacao().getTipodesconto().equalsIgnoreCase("V")) {
					if (promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana() != null
							&& promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana() > 0) {
						valordesconto = (float) (promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana()
								* produtosOrcamentoBean.getNumeroSemanas());
					} else if (promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal() != null
							&& promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal() > 0) {
						valordesconto = promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal();
					}
				} else if (promocaoacomodacaocidade.getPromocaoacomodacao().getTipodesconto().equalsIgnoreCase("T")) {
					if (promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana() != null
							&& promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana() > 0) {
						valordesconto = valorcoprodutos.getValororiginal()
								- promocaoacomodacaocidade.getPromocaoacomodacao().getValorsemana();
						if(valorcoprodutos.getCobranca().equalsIgnoreCase("S")){
							valordesconto = (float) (valordesconto * produtosOrcamentoBean.getNumeroSemanas());
						}
					} else if (promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal() != null
							&& promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal() > 0) {
						valordesconto = (float) (valorcoprodutos.getValororiginal()
								* produtosOrcamentoBean.getNumeroSemanas());
						valordesconto = valordesconto
								- promocaoacomodacaocidade.getPromocaoacomodacao().getValortotal();
					}
				}
				if ((promocaoacomodacaocidade.getPromocaoacomodacao().getValormaximodesconto() != null)
						&& (promocaoacomodacaocidade.getPromocaoacomodacao().getValormaximodesconto() > 0)) {
					if (valordesconto > promocaoacomodacaocidade.getPromocaoacomodacao().getValormaximodesconto()) {
						valordesconto = promocaoacomodacaocidade.getPromocaoacomodacao().getValormaximodesconto();
					}
				}
				if (valordesconto > 0) { 
					float valorOriginal = (float) (valorcoprodutos.getValororiginal()
							* produtosOrcamentoBean.getNumeroSemanas());
					produtosOrcamentoBean.setValorOrigianl(valorOriginal);
					produtosOrcamentoBean.setValorOriginalRS(produtosOrcamentoBean.getValorOrigianl()
							* resultadoOrcamentoBean.getOcurso().getValorcambio());
					produtosOrcamentoBean.setValorPromocional(produtosOrcamentoBean.getValorOrigianl() - valordesconto);
					produtosOrcamentoBean.setValorPromocionalRS(produtosOrcamentoBean.getValorPromocional()
							* resultadoOrcamentoBean.getOcurso().getValorcambio());
					produtosOrcamentoBean.setPromocao(true);
				}
			} else {
				produtosOrcamentoBean.setValorPromocional(0.0f);
				produtosOrcamentoBean.setValorPromocionalRS(0.0f);
				produtosOrcamentoBean.setPromocao(false);
			}
		} else {
			produtosOrcamentoBean.setValorPromocional(0.0f);
			produtosOrcamentoBean.setValorPromocionalRS(0.0f);
			produtosOrcamentoBean.setPromocao(false);
		}
	}

	public boolean verificarPromocaoAcomodacaoValida(Promocaoacomodacao promocao, Valorcoprodutos valorcoprodutos,
			ProdutosOrcamentoBean produtosOrcamentoBean) {
		Boolean tempromocao = false;
		if (promocao.getDatainicioiniciocurso() != null && promocao.getDatainicioterminiocurso() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioiniciocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioiniciocurso()))
					&& (resultadoOrcamentoBean.getOcurso().getDatainicio().before(promocao.getDatainicioterminiocurso())
							|| resultadoOrcamentoBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((resultadoOrcamentoBean.getOcurso().getDatainicio().after(promocao.getDatainicioacomodacao())
					|| resultadoOrcamentoBean.getOcurso().getDatainicio().equals(promocao.getDatainicioacomodacao()))
					&& (resultadoOrcamentoBean.getOcurso().getDatatermino()
							.before(promocao.getDataterminioacodomodacao())
							|| resultadoOrcamentoBean.getOcurso().getDatatermino()
									.equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (resultadoOrcamentoBean.getOcurso().getDatatermino().before(promocao.getDataterminocurso())
					|| resultadoOrcamentoBean.getOcurso().getDatatermino().equals(promocao.getDataterminocurso())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			if (new Date().before(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}

		if (promocao.getNumerosemanainicio() != null && promocao.getNumerosemanainicio() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (produtosOrcamentoBean.getNumeroSemanas() >= promocao.getNumerosemanainicio()
					&& produtosOrcamentoBean.getNumeroSemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaacima() != null && promocao.getValorsemanaacima() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorsemanaacima()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaigual() != null && promocao.getValorsemanaigual() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorsemanaigual()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoacomodacao() != null && promocao.getTipoacomodacao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoacomodacao()
					.equalsIgnoreCase(promocao.getTipoacomodacao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipoquarto() != null && promocao.getTipoquarto().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipoquarto()
					.equalsIgnoreCase(promocao.getTipoquarto())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTiporefeicao() != null && promocao.getTiporefeicao().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTiporefeicao()
					.equalsIgnoreCase(promocao.getTiporefeicao())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTipobanheiro() != null && promocao.getTipobanheiro().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementoacomodacao().getTipobanheiro()
					.equalsIgnoreCase(promocao.getTipobanheiro())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	}
}
