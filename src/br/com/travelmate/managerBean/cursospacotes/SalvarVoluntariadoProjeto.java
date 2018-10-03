package br.com.travelmate.managerBean.cursospacotes;

import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.facade.FornecedorPaisFacade; 
import br.com.travelmate.facade.OrcamentoProjetoVoluntariadoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoDescontoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoFormaPagamentoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoProdutosExtrasFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosExtrasBean;
import br.com.travelmate.managerBean.voluntariadoprojeto.orcamento.OrcamentoVoluntariadoBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Cursopacoteformapagamento;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Fornecedorpais;
import br.com.travelmate.model.Ocursodesconto; 
import br.com.travelmate.model.Orcamentoprojetovoluntariado;
import br.com.travelmate.model.Orcamentovoluntariadodesconto;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento;
import br.com.travelmate.model.Orcamentovoluntariadoprodutosextras;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Voluntariadoprojetovalor;
import br.com.travelmate.util.Formatacao;
 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 

public class SalvarVoluntariadoProjeto {

	private Voluntariadoprojetovalor voluntariadoprojetovalor;
	private Date datainicial;
	private Cliente cliente;
	private AplicacaoMB aplicacaoMB;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Cursospacote cursospacote;
	private Cursopacoteformapagamento formapagamento;
	private CambioDao cambioDao;

	public SalvarVoluntariadoProjeto(Cliente cliente, Date datainicio,
			Voluntariadoprojetovalor voluntariadoprojetovalor, Cursospacote cursospacote, AplicacaoMB aplicacaoMB,
			UsuarioLogadoMB usuarioLogadoMB, Cursopacoteformapagamento formapagamento, CambioDao cambioDao) {
		this.cliente = cliente;
		this.datainicial = datainicio;
		this.cambioDao = cambioDao;
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
		this.cursospacote = cursospacote;
		this.aplicacaoMB = aplicacaoMB;
		this.usuarioLogadoMB = usuarioLogadoMB;
		this.formapagamento = formapagamento;
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

	public Voluntariadoprojetovalor getVoluntariadoprojetovalor() {
		return voluntariadoprojetovalor;
	}

	public void setVoluntariadoprojetovalor(Voluntariadoprojetovalor voluntariadoprojetovalor) {
		this.voluntariadoprojetovalor = voluntariadoprojetovalor;
	}

	public Date getDatainicial() {
		return datainicial;
	}

	public void setDatainicial(Date datainicial) {
		this.datainicial = datainicial;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cursospacote getCursospacote() {
		return cursospacote;
	}

	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	}

	public Cursopacoteformapagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(Cursopacoteformapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	public Orcamentoprojetovoluntariado gerarOrcamento() {
		OrcamentoVoluntariadoBean orcamento = new OrcamentoVoluntariadoBean();
		orcamento.setCliente(cliente);
		orcamento.setVoluntariadoprojetovalor(voluntariadoprojetovalor);
		orcamento.setValor(voluntariadoprojetovalor.getValor());
		Cambio cambio = consultarCambio();
		orcamento.setCambio(cambio);
		if (cursospacote.getValorcambio() != null && cursospacote.getValorcambio() > 0) {
			orcamento.setValorcambio(cursospacote.getValorcambio());
		} else {
			orcamento.setValorcambio(cambio.getValor());
		}
		orcamento.setValorRS(voluntariadoprojetovalor.getValor() * cursospacote.getValorcambio());
		orcamento.setNumeroSemanasAdicionais(0);
		orcamento.setValorSemanaAdc(voluntariadoprojetovalor.getValorsemanaadicional() * 0);
		orcamento.setValorSemanaAdcRS(orcamento.getValorSemanaAdc() * cursospacote.getValorcambio());
		orcamento.setDatainicial(datainicial);
		orcamento.setDatatermino(calcularDataTermino());
		orcamento.setTotalnumerosemanas(voluntariadoprojetovalor.getNumerosemanainicial() + " Semana(s)");
		// curso
		if (voluntariadoprojetovalor.getVoluntariadoprojeto().getVoluntariadoprojetocursoList() != null
				&& voluntariadoprojetovalor.getVoluntariadoprojeto().getVoluntariadoprojetocursoList().size() > 0) {
			orcamento.setPossuiCurso(true);
			orcamento.setVoluntariadoprojetocurso(
					voluntariadoprojetovalor.getVoluntariadoprojeto().getVoluntariadoprojetocursoList().get(0));
		} else {
			orcamento.setPossuiCurso(false);
		}
		// acomodação
		if (voluntariadoprojetovalor.getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList() != null
				&& voluntariadoprojetovalor.getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList()
						.size() > 0) {
			orcamento.setPossuiAcomodacao(true);
			orcamento.setVoluntariadoprojetoacomodacao(
					voluntariadoprojetovalor.getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList().get(0));
		} else {
			orcamento.setPossuiAcomodacao(false);
		}
		// transfer
		if (voluntariadoprojetovalor.getVoluntariadoprojeto().getTransfer() != null
				&& voluntariadoprojetovalor.getVoluntariadoprojeto().getTransfer().length() > 0) {
			orcamento.setPossuiTransfer(true);
		}
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento taxatm = produtoOrcamentoFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM());
		orcamento.setTaxatm(taxatm);
		orcamento.setValortaxatmRS(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getTaxatm());
		orcamento.setValortaxatm(orcamento.getValortaxatmRS() / orcamento.getValorcambio());
		orcamento.setSeguroviagem(new Seguroviagem());
		orcamento.setListaOutrosProdutos(new ArrayList<ProdutosExtrasBean>());
		orcamento.setListaDesconto(addOcursoDesconto(orcamento));
		orcamento.setTotal(cursospacote.getValoravista());
		Orcamentoprojetovoluntariado orcamentoprojetovoluntariado = salvarOrcamento(orcamento);
		return orcamentoprojetovoluntariado;
	}

	public Cambio consultarCambio() {
		Cambio cambio = null;
		Date data = new Date();
		Fornecedorpais fornecedorPais = buscarFornecedorPais(
				voluntariadoprojetovalor.getVoluntariadoprojeto().getFornecedorcidade().getFornecedor()
						.getIdfornecedor(),
				voluntariadoprojetovalor.getVoluntariadoprojeto().getFornecedorcidade().getCidade().getPais()
						.getIdpais());
		int idMoeda = voluntariadoprojetovalor.getVoluntariadoprojeto().getFornecedorcidade().getCidade().getPais()
				.getMoedas().getIdmoedas();
		if (fornecedorPais != null) {
			idMoeda = fornecedorPais.getMoedas().getIdmoedas();
		}
		while (cambio == null) {
			cambio = cambioDao.consultarCambioMoeda(Formatacao.ConvercaoDataSql(data), idMoeda);
			try {
				data = Formatacao.SomarDiasDatas(data, -1);
			} catch (Exception ex) {
				Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
						.log(Level.SEVERE, null, ex);
			}
		}
		return cambio;
	}

	public Fornecedorpais buscarFornecedorPais(int idFornecedor, int idPais) {
		String sql = "SELECT f FROM Fornecedorpais f where f.fornecedor.idfornecedor=" + idFornecedor
				+ " and f.pais.idpais=" + idPais;
		FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
		return fornecedorPaisFacade.buscar(sql);
	}

	// corrigir data termino
	public Date calcularDataTermino() {
		if ((datainicial != null) && (voluntariadoprojetovalor.getNumerosemanainicial() > 0)) {
			int numerosemanas = voluntariadoprojetovalor.getNumerosemanainicial();
			Date data = Formatacao.calcularDataFinal(datainicial, numerosemanas);
			int diaSemana = Formatacao.diaSemana(data);
			try {
				if (diaSemana == 1) {
					data = Formatacao.SomarDiasDatas(data, -2);
				} else if (diaSemana == 7) {
					data = Formatacao.SomarDiasDatas(data, -1);
				}
			} catch (Exception ex) {

			}
			return data;
		}
		return null;
	}

	public List<Ocursodesconto> addOcursoDesconto(OrcamentoVoluntariadoBean orcamento) {
		orcamento.setListaDesconto(new ArrayList<Ocursodesconto>());
		Ocursodesconto ocursodesconto = new Ocursodesconto();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = new Produtosorcamento();
		produtosorcamento = produtoOrcamentoFacade.consultar(33);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		int anoinicio = Formatacao.getAnoData(datainicial);
		if (cursospacote.getAno1() > 0 || cursospacote.getAno2() > 0) {
			if (anoinicio == cursospacote.getAno1()) {
				if (cursospacote.getDescontotm1() > 0) {
					ocursodesconto.setValormoedaestrangeira(cursospacote.getDescontotm1());
				}
			} else if (anoinicio == cursospacote.getAno2()) {
				if (cursospacote.getDescontotm2() > 0) {
					ocursodesconto.setValormoedaestrangeira(cursospacote.getDescontotm2());
				}
			}
		}
		if (ocursodesconto.getValormoedaestrangeira() == 0) {
			ocursodesconto.setValormoedanacional(0.0f);
		} else {
			ocursodesconto
					.setValormoedanacional(ocursodesconto.getValormoedaestrangeira() * orcamento.getValorcambio());
			ocursodesconto.setSelecionado(true);
		}
		orcamento.getListaDesconto().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(9);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f);
		orcamento.getListaDesconto().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(267);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		if (cursospacote.getPromocaoescola() > 0) {
			ocursodesconto.setValormoedaestrangeira(cursospacote.getPromocaoescola());
			ocursodesconto.setValormoedanacional(cursospacote.getPromocaoescola() * orcamento.getValorcambio());
			ocursodesconto.setSelecionado(true);
			orcamento.setValor(orcamento.getValor() - ocursodesconto.getValormoedaestrangeira());
		} else {
			ocursodesconto.setValormoedaestrangeira(0.0f);
			ocursodesconto.setValormoedanacional(0.0f);
		}
		orcamento.getListaDesconto().add(ocursodesconto);
		return orcamento.getListaDesconto();
	}

	public Orcamentoprojetovoluntariado salvarOrcamento(OrcamentoVoluntariadoBean orcamento) {
		Orcamentoprojetovoluntariado voluntariado;
		OrcamentoProjetoVoluntariadoFacade orcamentoProjetoVoluntariadoFacade = new OrcamentoProjetoVoluntariadoFacade();
		if (orcamento.getOrcamentoprojetovoluntariado() == null) {
			voluntariado = new Orcamentoprojetovoluntariado();
		} else {
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
		voluntariado.setObs("Pacote Promocional TravelMate");
		voluntariado.setDataorcamento(new Date());
		voluntariado.setUsuario(usuarioLogadoMB.getUsuario());
		voluntariado.setCliente(orcamento.getCliente());
		voluntariado = orcamentoProjetoVoluntariadoFacade.salvar(voluntariado);
		if (orcamento.getTaxatm() != null) {
			OrcamentoVoluntariadoProdutosExtrasFacade voluntariadoProdutosExtrasFacade = new OrcamentoVoluntariadoProdutosExtrasFacade();
			Orcamentovoluntariadoprodutosextras produtosextras = new Orcamentovoluntariadoprodutosextras();
			produtosextras.setProdutosorcamento(orcamento.getTaxatm());
			produtosextras.setNome(orcamento.getTaxatm().getDescricao());
			produtosextras.setOrcamentoprojetovoluntariado(voluntariado);
			produtosextras.setSomarvalortotal(true);
			produtosextras.setValor(orcamento.getValortaxatm());
			produtosextras.setValorrs(orcamento.getValortaxatmRS());
			voluntariadoProdutosExtrasFacade.salvar(produtosextras);
		} 
		salvarFormaPagamento(orcamento, voluntariado);
		salvarDesconto(orcamento, voluntariado);
		return voluntariado;
	}

	public void salvarFormaPagamento(OrcamentoVoluntariadoBean orcamento,
			Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
		OrcamentoVoluntariadoFormaPagamentoFacade orcamentoVoluntariadoFormaPagamentoFacade = new OrcamentoVoluntariadoFormaPagamentoFacade();
		Orcamentovoluntariadoformapagamento orcamentovoluntariadoformapagamento = new Orcamentovoluntariadoformapagamento();
		if (formapagamento != null) {
			orcamentovoluntariadoformapagamento = new Orcamentovoluntariadoformapagamento();
			if (formapagamento.getValorparcelaboleto() != null && formapagamento.getValorparcelaboleto() > 0) {
				orcamentovoluntariadoformapagamento.setAvista(cursospacote.getValoravista());
				orcamentovoluntariadoformapagamento.setPercentualentradaboleto(formapagamento.getEntradaboleto());
				orcamentovoluntariadoformapagamento.setValorentradaboleto(formapagamento.getValorentradaboleto());
				orcamentovoluntariadoformapagamento.setNumparcelasboleto(formapagamento.getNumeroparcelasboleto());
				orcamentovoluntariadoformapagamento.setValorparcelaboleto(formapagamento.getValorparcelaboleto());
				orcamentovoluntariadoformapagamento.setPercentualsaldoboleto(formapagamento.getSaldoboleto());
				orcamentovoluntariadoformapagamento.setValorsaldoboleto(formapagamento.getSaldoboleto());
				orcamentovoluntariadoformapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
				orcamentovoluntariadoformapagamento = orcamentoVoluntariadoFormaPagamentoFacade
						.salvar(orcamentovoluntariadoformapagamento);
			} else {
				orcamentovoluntariadoformapagamento.setPercentualentradaboleto(0.0f);
				orcamentovoluntariadoformapagamento.setValorentradaboleto(0.0f);
				orcamentovoluntariadoformapagamento.setNumparcelasboleto(0);
				orcamentovoluntariadoformapagamento.setValorparcelaboleto(0.0f);
				orcamentovoluntariadoformapagamento.setPercentualsaldoboleto(0.0f);
				orcamentovoluntariadoformapagamento.setValorsaldoboleto(0.0f);
				orcamentovoluntariadoformapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
				orcamentovoluntariadoformapagamento = orcamentoVoluntariadoFormaPagamentoFacade
						.salvar(orcamentovoluntariadoformapagamento);
			}
			orcamentovoluntariadoformapagamento = new Orcamentovoluntariadoformapagamento();
			if (formapagamento.getValorparcelacartao() != null && formapagamento.getValorparcelacartao() > 0) {
				orcamentovoluntariadoformapagamento.setPercentualentradacartao(formapagamento.getEntradacartao());
				orcamentovoluntariadoformapagamento.setValorentradacartao(formapagamento.getValorentradacartao());
				orcamentovoluntariadoformapagamento.setNumparcelascartao(formapagamento.getNumeroparcelascartao());
				orcamentovoluntariadoformapagamento.setValorparcelacartao(formapagamento.getValorparcelacartao());
				orcamentovoluntariadoformapagamento.setPercentualsaldocartao(formapagamento.getSaldocartao());
				orcamentovoluntariadoformapagamento.setValorsaldocartao(formapagamento.getSaldocartao());
				orcamentovoluntariadoformapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
				orcamentovoluntariadoformapagamento = orcamentoVoluntariadoFormaPagamentoFacade
						.salvar(orcamentovoluntariadoformapagamento);
			} else {
				orcamentovoluntariadoformapagamento.setPercentualentradacartao(0.0f);
				orcamentovoluntariadoformapagamento.setValorentradacartao(0.0f);
				orcamentovoluntariadoformapagamento.setNumparcelascartao(0);
				orcamentovoluntariadoformapagamento.setValorparcelacartao(0.0f);
				orcamentovoluntariadoformapagamento.setPercentualsaldocartao(0.0f);
				orcamentovoluntariadoformapagamento.setValorsaldocartao(0.0f);
				orcamentovoluntariadoformapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
				orcamentovoluntariadoformapagamento = orcamentoVoluntariadoFormaPagamentoFacade
						.salvar(orcamentovoluntariadoformapagamento);
			}
			orcamentovoluntariadoformapagamento = new Orcamentovoluntariadoformapagamento();
			if (formapagamento.getValorparcelafinanciamento() != null
					&& formapagamento.getValorparcelafinanciamento() > 0) {
				orcamentovoluntariadoformapagamento
						.setPercentualentradafinanciamento(formapagamento.getEntradafinanciamento());
				orcamentovoluntariadoformapagamento
						.setValorentradafinanciamento(formapagamento.getValorentradafinanciamento());
				orcamentovoluntariadoformapagamento
						.setNparcelasfinanciamento(formapagamento.getNparcelasfinanciamento().intValue());
				orcamentovoluntariadoformapagamento
						.setValorparcelafinanciamento(formapagamento.getValorparcelafinanciamento());
				orcamentovoluntariadoformapagamento
						.setPercentualsaldofinanciamento(formapagamento.getSaldofinanciamento());
				orcamentovoluntariadoformapagamento.setValorsaldofinanciamento(formapagamento.getSaldofinanciamento());
				orcamentovoluntariadoformapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
				orcamentovoluntariadoformapagamento = orcamentoVoluntariadoFormaPagamentoFacade
						.salvar(orcamentovoluntariadoformapagamento);
			} else {
				orcamentovoluntariadoformapagamento.setPercentualentradafinanciamento(0.0f);
				orcamentovoluntariadoformapagamento.setValorentradafinanciamento(0.0f);
				orcamentovoluntariadoformapagamento.setNparcelasfinanciamento(0);
				orcamentovoluntariadoformapagamento.setValorparcelafinanciamento(0.0f);
				orcamentovoluntariadoformapagamento.setPercentualsaldofinanciamento(0.0f);
				orcamentovoluntariadoformapagamento.setValorsaldofinanciamento(0.0f);
				orcamentovoluntariadoformapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
				orcamentovoluntariadoformapagamento = orcamentoVoluntariadoFormaPagamentoFacade
						.salvar(orcamentovoluntariadoformapagamento);
			}
		} else {
			orcamentovoluntariadoformapagamento.setPercentualentradaboleto(0.0f);
			orcamentovoluntariadoformapagamento.setValorentradaboleto(0.0f);
			orcamentovoluntariadoformapagamento.setNumparcelasboleto(0);
			orcamentovoluntariadoformapagamento.setValorparcelaboleto(0.0f);
			orcamentovoluntariadoformapagamento.setPercentualsaldoboleto(0.0f);
			orcamentovoluntariadoformapagamento.setValorsaldoboleto(0.0f);
			orcamentovoluntariadoformapagamento.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
			orcamentovoluntariadoformapagamento = orcamentoVoluntariadoFormaPagamentoFacade
					.salvar(orcamentovoluntariadoformapagamento);
		}
	}

	public void salvarDesconto(OrcamentoVoluntariadoBean orcamento,
			Orcamentoprojetovoluntariado orcamentoprojetovoluntariado) {
		OrcamentoVoluntariadoDescontoFacade descontoFacade = new OrcamentoVoluntariadoDescontoFacade();
		Orcamentovoluntariadodesconto desconto = new Orcamentovoluntariadodesconto();
		if (orcamento.getListaDesconto() != null) {
			for (int i = 0; i < orcamento.getListaDesconto().size(); i++) {
				if (orcamento.getListaDesconto().get(i).isSelecionado()) {
					desconto.setProdutosorcamento(orcamento.getListaDesconto().get(i).getProdutosorcamento());
					desconto.setOrcamentoprojetovoluntariado(orcamentoprojetovoluntariado);
					desconto.setValor(orcamento.getListaDesconto().get(i).getValormoedaestrangeira());
					desconto.setValorrs(orcamento.getListaDesconto().get(i).getValormoedanacional());
					desconto = descontoFacade.salvar(desconto);
					orcamentoprojetovoluntariado.getOrcamentovoluntariadodescontoList().add(desconto);
				}
			}
		}
	}

}
