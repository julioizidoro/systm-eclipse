package br.com.travelmate.managerBean.voluntariadoprojeto.orcamento;

import java.util.ArrayList;
import java.util.List;

import br.com.travelmate.facade.EscolaPadraoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoDescontoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoFormaPagamentoFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoProdutosExtrasFacade;
import br.com.travelmate.facade.OrcamentoVoluntariadoSeguroFacade;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.OrcamentoPDFBean;
import br.com.travelmate.model.Escolapadrao;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;
import br.com.travelmate.model.Orcamentovoluntariadodesconto;
import br.com.travelmate.model.Orcamentovoluntariadoformapagamento;
import br.com.travelmate.model.Orcamentovoluntariadoprodutosextras;
import br.com.travelmate.model.Orcamentovoluntariadoseguro;
import br.com.travelmate.util.Formatacao;

public class GerarOrcamentoVoluntariadoPDFBean {

	private List<OrcamentoPDFBean> lista;
	private float totalCursoRs;
	private float totalCursoMe;
	private float totalTxRs;
	private float totalTxMe;
	private float totalAdRs;
	private float totalAdMe;
	private float totalDeRs;
	private float totalDeMe;
	private Orcamentoprojetovoluntariado orcamentoprojetovoluntariado;
	private String composicao;
	private String moeda;
	private String moedaNacional; 

	public GerarOrcamentoVoluntariadoPDFBean(Orcamentoprojetovoluntariado orcamentoprojetovoluntariado, String moedaNacional) {
		this.orcamentoprojetovoluntariado = orcamentoprojetovoluntariado;
		if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade()
				.getCidade().getPais().getMoedasVolutariado().getSigla().equalsIgnoreCase("OUTRAS")) {
			moeda = "$";
		} else {
			moeda = orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
					.getFornecedorcidade().getCidade().getPais().getMoedasVolutariado().getSigla();
		}
		lista = new ArrayList<OrcamentoPDFBean>();
		composicao = "Curso";
		this.moedaNacional = moedaNacional;
		carregarTaxas();
		carregarAcomodacao(); 
		carregarItensAdicionais();
		carregarDesconto();
		calcularTotais();
	}

	public List<OrcamentoPDFBean> getLista() {
		return lista;
	}

	public void setLista(List<OrcamentoPDFBean> lista) {
		this.lista = lista;
	}

	public OrcamentoPDFBean carregarDadosIniciais() {
		OrcamentoPDFBean o = new OrcamentoPDFBean();
		Escolapadrao escolaPadrao = null;
		o.setNomeCliente(orcamentoprojetovoluntariado.getCliente().getNome());
		o.setNomeConsultor(orcamentoprojetovoluntariado.getUsuario().getNome());
		o.setCidadeUnidade(
				orcamentoprojetovoluntariado.getUsuario().getUnidadenegocio().getNomerelatorio().toUpperCase());
		o.setEmailConsultor(orcamentoprojetovoluntariado.getUsuario().getEmail());
		o.setFoneUnidade(orcamentoprojetovoluntariado.getUsuario().getUnidadenegocio().getFone());
		o.setPaisEscola(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getFornecedorcidade().getCidade().getPais().getNome().toUpperCase());
		if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade()
				.getCidade().getPais().getTexto() != null
				&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
						.getFornecedorcidade().getCidade().getPais().getTexto().length() > 0) {
			o.setDescricaoPais(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
					.getFornecedorcidade().getCidade().getPais().getTexto());
		} else {
			EscolaPadraoFacade escolaPadraoFacade = new EscolaPadraoFacade();
			escolaPadrao = escolaPadraoFacade.pesquisar();
			o.setDescricaoPais(escolaPadrao.getDescricaopais());
		}
		o.setNomeEscola(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getFornecedorcidade().getFornecedor().getNome().toUpperCase());
		o.setCidadeEscola(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getFornecedorcidade().getCidade().getNome().toUpperCase());
		if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade()
				.getTexto() != null
				&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
						.getFornecedorcidade().getTexto().length() > 0) {
			o.setDescricaoEscolaCidade(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor()
					.getVoluntariadoprojeto().getFornecedorcidade().getTexto());
		} else {
			EscolaPadraoFacade escolaPadraoFacade = new EscolaPadraoFacade();
			escolaPadrao = escolaPadraoFacade.pesquisar();
			o.setDescricaoEscolaCidade(escolaPadrao.getDescricaoescola());
		}
		o.setSiteescola(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getFornecedorcidade().getFornecedor().getSite());
		// Dados Pagina 03
		o.setLocalCurso(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getFornecedorcidade().getCidade().getNome() + ", "
				+ orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
						.getFornecedorcidade().getCidade().getPais().getNome());
		o.setInstituicao(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getFornecedorcidade().getFornecedor().getNome());
		o.setTipCurso(
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getDescricao());
		o.setDataInicio(Formatacao.ConvercaoDataPadrao(orcamentoprojetovoluntariado.getDatainicial()));
		o.setDataTermino(Formatacao.ConvercaoDataPadrao(orcamentoprojetovoluntariado.getDatafinal()));
		o.setDuracao(orcamentoprojetovoluntariado.getNsemanas());
		if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
				.getVoluntariadoprojetocursoList() != null
				&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
						.getVoluntariadoprojetocursoList().size() > 0) {
			o.setCargahoraria(String
					.valueOf(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetocursoList().get(0).getCargahoraria())
					+ " " + orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetocursoList().get(0).getTipocargahoraria());
		} else {
			o.setCargahoraria("");
		}
		o.setTurno("");
		o.setTotalCursome(moeda + " " + Formatacao.formatarFloatString(orcamentoprojetovoluntariado.getValorprojeto()));
		o.setTotalcursors(moedaNacional + " " + Formatacao.formatarFloatString(orcamentoprojetovoluntariado.getValorprojetors()));
		o.setDataOrcamento(Formatacao.ConvercaoDataPadrao(orcamentoprojetovoluntariado.getDataorcamento()));
		o.setCambio(moeda + " " + Formatacao.formatarFloatStringOutras(orcamentoprojetovoluntariado.getValorcambio()));
		totalCursoMe = orcamentoprojetovoluntariado.getValorprojeto();
		totalCursoRs = orcamentoprojetovoluntariado.getValorprojetors();
		// Dados Pagina 04
		o.setComposicao("Voluntariado + Taxas".toUpperCase());
		OrcamentoVoluntariadoFormaPagamentoFacade formaPagamentoFacade = new OrcamentoVoluntariadoFormaPagamentoFacade();
		String sql = "select o from Orcamentovoluntariadoformapagamento o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoformapagamento> listaFormapagamento = formaPagamentoFacade.listar(sql);
		if(listaFormapagamento!=null && listaFormapagamento.size()>0){
			Orcamentovoluntariadoformapagamento f = listaFormapagamento.get(0);
			o.setValorVista(moedaNacional + " " + Formatacao.formatarFloatString(f.getAvista()));
			if (f.getValorparcelaboleto() != null) {
				o.setValorentradaboelto(moedaNacional + " " + Formatacao.formatarFloatString(f.getValorentradaboleto()));
				o.setSaldoboleto("Saldo em " + f.getNumparcelasboleto() + " parcelas fixas");
				o.setValorsaldoboleto(moedaNacional + Formatacao.formatarFloatString(f.getValorparcelaboleto()));
			}
			if (f.getValorentradacartao() != null) {
				o.setValorentradacartao(moedaNacional + " " + Formatacao.formatarFloatString(f.getValorentradacartao()));
				o.setSaldocartao("Saldo em " + f.getNumparcelascartao() + " parcelas fixas");
				o.setValorsaldocartao(moedaNacional + " " + Formatacao.formatarFloatString(f.getValorparcelacartao()));
			}
			if (f.getValorentradafinanciamento() != null && f.getValorentradafinanciamento() > 0) {
				o.setValorentradafinanciamento(moedaNacional + " " + " 0,00");
				o.setDescricaofinanciamento("Saldo em " + f.getNparcelasfinanciamento() + " parcelas fixas");
				o.setValorfinanciamento(moedaNacional + " " + Formatacao.formatarFloatString(f.getValorparcelafinanciamento()));
			}
		}
		o.setObservacao(orcamentoprojetovoluntariado.getObs());
		o.setNumeroorcamento(String.valueOf(orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado()));
		return o;
	}

	public void carregarTaxas() {
		OrcamentoVoluntariadoProdutosExtrasFacade extrasFacade = new OrcamentoVoluntariadoProdutosExtrasFacade();
		String sql = "select o from Orcamentovoluntariadoprodutosextras o where o.produtosorcamento.idprodutosOrcamento="
				+ 8 + " and o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoprodutosextras> lista = extrasFacade.listar(sql);
		OrcamentoPDFBean o = new OrcamentoPDFBean();
		if (lista != null) {
			composicao = composicao + " + TAXAS e SUPLEMENTOS"; 
			for (int i = 0; i < lista.size(); i++) {
				o = carregarDadosIniciais();
				o.setTituloLista("TAXAS");
				o.setDescricaolista(lista.get(i).getProdutosorcamento().getDescricao());
				o.setSubDescricaoLista("");
				o.setValorme(moeda + " " + Formatacao.formatarFloatString(lista.get(i).getValor()));
				o.setValorrs(moedaNacional + " " + Formatacao.formatarFloatString(lista.get(i).getValorrs()));
				totalTxMe = totalTxMe + lista.get(i).getValor();
				totalTxRs = totalTxRs + lista.get(i).getValorrs();
				o.setIdgrupo(1);
				this.lista.add(o);
			} 
		}
		if(orcamentoprojetovoluntariado.getNsemanaadicional()>0){
			o = carregarDadosIniciais();
			o.setTituloLista("TAXAS");
			o.setDescricaolista(orcamentoprojetovoluntariado.getNsemanaadicional()+" Semana(s) adicionais");
			o.setSubDescricaoLista("");
			o.setValorme(moeda + " " + Formatacao.formatarFloatString(orcamentoprojetovoluntariado.getValorsemanaadicional()));
			o.setValorrs(moedaNacional + " " + Formatacao.formatarFloatString(orcamentoprojetovoluntariado.getValorsemanaadicionalrs()));
			totalTxMe = totalTxMe + orcamentoprojetovoluntariado.getValorsemanaadicional();
			totalTxRs = totalTxRs + orcamentoprojetovoluntariado.getValorsemanaadicionalrs();
			o.setIdgrupo(1);
			this.lista.add(o);
		}
		o.setTotalmelista(moeda + " " + Formatacao.formatarFloatString(totalTxMe));
		o.setTotalrslista(moedaNacional + " " + Formatacao.formatarFloatString(totalTxRs));
	}

	public void carregarAcomodacao() {
		if (orcamentoprojetovoluntariado.getPossuiacomodacao().equals(true)) {
			composicao = composicao + " + ACOMODAÇÃO";
			OrcamentoPDFBean o = new OrcamentoPDFBean();
			o = carregarDadosIniciais();
			o.setDescricaolista(orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
					.getVoluntariadoprojetoacomodacaoList().get(0).getTipoacomodacao()
					+ ", "
					+ orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetoacomodacaoList().get(0).getTipoquarto()
					+ ", "
					+ orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetoacomodacaoList().get(0).getTiporefeicao()
					+ ", Banheiro " + orcamentoprojetovoluntariado.getVoluntariadoprojetovalor()
							.getVoluntariadoprojeto().getVoluntariadoprojetoacomodacaoList().get(0).getTipobanheiro());
			if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
					.getVoluntariadoprojetoacomodacaoList().get(0).getNumerosemanas() != null
					&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
							.getVoluntariadoprojetoacomodacaoList().get(0).getNumerosemanas() > 0) {
				o.setDescricaolista(
						o.getDescricaolista() + ", "
								+ orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto()
										.getVoluntariadoprojetoacomodacaoList().get(0).getNumerosemanas()
								+ " semana(s)");
			} else {
				o.setDescricaolista(o.getDescricaolista() + ", "
						+ orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getNumerosemanainicial()
						+ " semana(s)");
			}
			o.setValorme("INCLUSO");
			o.setIdgrupo(2);
			o.setTituloLista("ACOMODAÇÃO");
			lista.add(o);
		}
	}

	public void carregarDesconto() {
		OrcamentoVoluntariadoDescontoFacade descontoFacade = new OrcamentoVoluntariadoDescontoFacade();
		String sql = "select o from Orcamentovoluntariadodesconto o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadodesconto> lista = descontoFacade.listar(sql);
		if (lista != null) {
			composicao = composicao + " - DESCONTO";
			OrcamentoPDFBean o = new OrcamentoPDFBean();
			for (int i = 0; i < lista.size(); i++) {
				o = carregarDadosIniciais();
				o.setDescricaolista(lista.get(i).getProdutosorcamento().getDescricao());
				o.setSubDescricaoLista("");
				o.setValorme(moeda + " " + Formatacao.formatarFloatString(lista.get(i).getValor()));
				o.setValorrs(moedaNacional + " " + Formatacao.formatarFloatString(lista.get(i).getValorrs()));
				o.setIdgrupo(3);
				o.setTituloLista("DESCONTO");
				this.lista.add(o);
				totalDeMe = totalDeMe + lista.get(i).getValor();
				totalDeRs = totalDeRs + lista.get(i).getValorrs();
			}
			o.setTotalmelista(moeda + " " + Formatacao.formatarFloatString(totalDeMe));
			o.setTotalrslista(moedaNacional + " " + Formatacao.formatarFloatString(totalDeRs));
		}
	}

	public void carregarItensAdicionais() {
		OrcamentoPDFBean o = new OrcamentoPDFBean(); 
		OrcamentoVoluntariadoProdutosExtrasFacade extrasFacade = new OrcamentoVoluntariadoProdutosExtrasFacade();
		String sql = "select o from Orcamentovoluntariadoprodutosextras o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoprodutosextras> listaExtras = extrasFacade.listar(sql);
		if (listaExtras != null) {
			for (int i = 0; i < listaExtras.size(); i++) {
				if (listaExtras.get(i).getProdutosorcamento().getIdprodutosOrcamento() != 8) {
					o = carregarDadosIniciais();
					o.setDescricaolista(listaExtras.get(i).getProdutosorcamento().getDescricao());
					o.setSubDescricaoLista("");
					o.setValorme(moeda + " " + Formatacao.formatarFloatString(listaExtras.get(i).getValor()));
					o.setValorrs(moedaNacional + " " + Formatacao.formatarFloatString(listaExtras.get(i).getValorrs()));
					if (listaExtras.get(i).getSomarvalortotal()) {
						o.setTituloLista("ITENS ADICIONAIS");
						o.setIdgrupo(4);
						totalAdMe = totalAdMe + listaExtras.get(i).getValor();
						totalAdRs = totalAdRs + listaExtras.get(i).getValorrs();
					} else {
						o.setTituloLista("CUSTOS EXTRAS");
						o.setIdgrupo(5);
					}
					this.lista.add(o);
				} 
			}
		}
		if (orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getTransfer() != null
				&& orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getTransfer()
						.length() > 0) {
			o = carregarDadosIniciais();
			o.setTituloLista("ITENS ADICIONAIS");
			o.setIdgrupo(4);
			o.setDescricaolista(
					orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getTransfer());
			o.setValorme("INCLUSO");
			lista.add(o);
		} 
		OrcamentoVoluntariadoSeguroFacade seguroFacade = new OrcamentoVoluntariadoSeguroFacade();
		sql = "select o from Orcamentovoluntariadoseguro o where"
				+ " o.orcamentoprojetovoluntariado.idorcamentoprojetovoluntariado="
				+ orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado();
		List<Orcamentovoluntariadoseguro> listaSeguro = seguroFacade.listar(sql);
		if(listaSeguro!=null && listaSeguro.size()>0){
			o = carregarDadosIniciais();
			o.setDescricaolista("Seguro Viagem");
			o.setSubDescricaoLista(String.valueOf(listaSeguro.get(0).getNumerodias())
					+ " dias");
			o.setValorme(moeda + " " + Formatacao.formatarFloatString(listaSeguro.get(0).getValor()));
			o.setValorrs(moedaNacional + " " + Formatacao.formatarFloatString(listaSeguro.get(0).getValorrs()));
			if(listaSeguro.get(0).getSomarvalortotal()){
				o.setTituloLista("ITENS ADICIONAIS");
				o.setIdgrupo(4);
				totalAdMe = totalAdMe + (listaSeguro.get(0).getValor());
				totalAdRs = totalAdRs + listaSeguro.get(0).getValorrs();
			}else{
				o.setTituloLista("CUSTOS EXTRAS");
				o.setIdgrupo(5);
			} 
			lista.add(o); 
		} 
		if (totalAdMe > 0) {
			o.setTotalmelista(Formatacao.formatarFloatString(totalAdMe));
			o.setTotalrslista(Formatacao.formatarFloatString(totalAdRs));
		}
	}
 
	public void calcularTotais() {
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getIdgrupo() == 1) {
					lista.get(i).setTotaltxme(moeda + " " + Formatacao.formatarFloatString(totalTxMe));
					lista.get(i).setTotaltxrs(moedaNacional + " " + Formatacao.formatarFloatString(totalTxRs));
				} else if (lista.get(i).getIdgrupo() == 3) {
					lista.get(i).setTotalDeme(moeda + " " + Formatacao.formatarFloatString(totalDeMe));
					lista.get(i).setTotalDers(moedaNacional + " " + Formatacao.formatarFloatString(totalDeRs));
				} else if (lista.get(i).getIdgrupo() == 4) {
					lista.get(i).setTotaladicionalme(moeda + " " + Formatacao.formatarFloatString(totalAdMe));
					lista.get(i).setTotaladicionalrs(moedaNacional + " " + Formatacao.formatarFloatString(totalAdRs));
				}
				lista.get(i).setSubPacoteme(
						moeda + " " + Formatacao.formatarFloatString(totalAdMe + totalTxMe + totalCursoMe - totalDeMe));
				lista.get(i).setSubPacoters(
						moedaNacional + " " + Formatacao.formatarFloatString(totalAdRs + totalTxRs + totalCursoRs - totalDeRs));

			}
		}
	}

}
