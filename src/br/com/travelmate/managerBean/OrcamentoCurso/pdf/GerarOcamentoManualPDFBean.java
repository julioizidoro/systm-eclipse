package br.com.travelmate.managerBean.OrcamentoCurso.pdf;

import java.util.ArrayList;
import java.util.List;

import br.com.travelmate.facade.EscolaPadraoFacade;
import br.com.travelmate.facade.OcClienteFacade;
import br.com.travelmate.facade.OrcamentoCursoFacade;
import br.com.travelmate.model.Escolapadrao;
import br.com.travelmate.model.Occliente;
import br.com.travelmate.model.Orcamentocurso;
import br.com.travelmate.model.Orcamentocursoformapagamento;
import br.com.travelmate.model.Produtoorcamentocurso;
import br.com.travelmate.util.Formatacao;

public class GerarOcamentoManualPDFBean {

	private List<OrcamentoPDFBean> lista;
	private float totalCursoRs;
	private float totalCursoMe;
	private float totalAcRs;
	private float totalAcMe;
	private float totalTxRs;
	private float totalTxMe;
	private float totalAdRs;
	private float totalAdMe;
	private float totalDeRs;
	private float totalDeMe;
	private Orcamentocurso orcamentoCurso;
	private String composicao;
	
	private Produtoorcamentocurso  p;
	

	public GerarOcamentoManualPDFBean(Orcamentocurso orcamentoCurso) {
		this.orcamentoCurso = orcamentoCurso;
		if(orcamentoCurso.getCambio().getMoedas().getSigla().equalsIgnoreCase("OUTRAS")){
			orcamentoCurso.getCambio().getMoedas().setSigla("$");
		}
		lista = new ArrayList<OrcamentoPDFBean>();
		composicao = "Curso";
		carregarTaxas();
		carregarAcomodacao();
		carregarItensAdicionais();
		carregarDesconto();
		carregarCustosExtras();
		calcularTotais();
	}

	public List<OrcamentoPDFBean> getLista() {
		return lista;
	}

	public void setLista(List<OrcamentoPDFBean> lista) {
		this.lista = lista;
	}
	
	public List<Produtoorcamentocurso> getListaProdutoOrcanentoCurso(String tipoOrcamento){
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		String sql = "SELECT o FROM Produtoorcamentocurso o where o.produtosOrcamento.tipoorcamento='" + tipoOrcamento + "' and o.orcamentocurso=" + orcamentoCurso.getIdorcamentoCurso()
				+ " order by o.produtosOrcamento.descricao";
		return orcamentoCursoFacade.listarProdutoOrcamentoCurso(sql);
	}
	
	
	
	public OrcamentoPDFBean carregarDadosIniciais(){
		OrcamentoPDFBean o = new OrcamentoPDFBean();
		List<Produtoorcamentocurso> lista = null;
		if (p==null){
			lista = getListaProdutoOrcanentoCurso("C");
			if(lista!=null && lista.get(0)!=null){
				p = lista.get(0);
			}
		}
		if (p!=null){
			Escolapadrao escolaPadrao=null; 
			if(orcamentoCurso.getCliente()!=null && orcamentoCurso.getCliente().getIdcliente()>1){
				o.setNomeCliente(orcamentoCurso.getCliente().getNome());
			}else{
				OcClienteFacade ocClienteFacade = new OcClienteFacade();
				Occliente occliente = ocClienteFacade.consultar(orcamentoCurso.getOccliente());
				o.setNomeCliente(occliente.getNome());
			}     
			o.setNomeConsultor(orcamentoCurso.getUsuario().getNome());
			o.setCidadeUnidade(orcamentoCurso.getUsuario().getUnidadenegocio().getNomerelatorio().toUpperCase());
			o.setEmailConsultor(orcamentoCurso.getUsuario().getEmail());
			o.setFoneUnidade(orcamentoCurso.getUsuario().getUnidadenegocio().getFone());
			o.setPaisEscola(orcamentoCurso.getFornecedorcidade().getCidade().getPais().getNome().toUpperCase());
			if(orcamentoCurso.getFornecedorcidade().getCidade().getPais().getTexto()!=null
					&& orcamentoCurso.getFornecedorcidade().getCidade().getPais().getTexto().length()>0){
				o.setDescricaoPais(orcamentoCurso.getFornecedorcidade().getCidade().getPais().getTexto());
			}else{
				EscolaPadraoFacade escolaPadraoFacade = new EscolaPadraoFacade();
				escolaPadrao = escolaPadraoFacade.pesquisar();
				o.setDescricaoPais(escolaPadrao.getDescricaopais());
			}
			o.setNomeEscola(orcamentoCurso.getFornecedorcidade().getFornecedor().getNome().toUpperCase());
			o.setCidadeEscola(orcamentoCurso.getFornecedorcidade().getCidade().getNome().toUpperCase());
			if(orcamentoCurso.getFornecedorcidade().getTexto()!=null
					&& orcamentoCurso.getFornecedorcidade().getTexto().length()>0){
				o.setDescricaoEscolaCidade(orcamentoCurso.getFornecedorcidade().getTexto());
			}else{
				EscolaPadraoFacade escolaPadraoFacade = new EscolaPadraoFacade();
				escolaPadrao = escolaPadraoFacade.pesquisar();  
				o.setDescricaoEscolaCidade(escolaPadrao.getDescricaoescola());
			}
			o.setSiteescola(orcamentoCurso.getFornecedorcidade().getFornecedor().getSite()); 
			// Dados Pagina 03
			o.setLocalCurso(orcamentoCurso.getFornecedorcidade().getCidade().getNome() + ", "
					+ orcamentoCurso.getFornecedorcidade().getCidade().getPais().getNome());
			o.setInstituicao(orcamentoCurso.getFornecedorcidade().getFornecedor().getNome() + " "
					+ orcamentoCurso.getFornecedorcidade().getCidade().getNome());
			o.setTipCurso(orcamentoCurso.getCurso()); 
			o.setDataInicio(Formatacao.ConvercaoDataPadrao(orcamentoCurso.getDataInicio()));
			o.setDataTermino(Formatacao.ConvercaoDataPadrao(orcamentoCurso.getDataTermino())); 
			o.setDuracao(orcamentoCurso.getNumeroSemanas() + " semanas");
			o.setCargahoraria(String.valueOf(orcamentoCurso.getAulasSemana()) + " " + orcamentoCurso.getTipoDuracao());
			o.setTurno(orcamentoCurso.getTurno());
			o.setTotalCursome(orcamentoCurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(p.getValorMoedaEstrangeira()));
			o.setTotalcursors("R$ " + Formatacao.formatarFloatString(p.getValorMoedaNacional()));
			o.setDataOrcamento(Formatacao.ConvercaoDataPadrao(orcamentoCurso.getData()));
			o.setCambio(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
					+ Formatacao.formatarFloatStringOutras(orcamentoCurso.getValorCambio()));
			totalCursoMe = p.getValorMoedaEstrangeira();
			totalCursoRs = p.getValorMoedaNacional();
			// Dados Pagina 04
			if(p.getProdutosOrcamento().getDescricao().equalsIgnoreCase("Voluntariado")){
				o.setComposicao("voluntariado + taxas".toUpperCase()); 
			}else{
				o.setComposicao(composicao); 
			}
			if (orcamentoCurso.getOrcamentocursoformapagamentoList() != null && orcamentoCurso.getOrcamentocursoformapagamentoList().size() > 0) {
				Orcamentocursoformapagamento f = orcamentoCurso.getOrcamentocursoformapagamentoList().get(0);
				o.setValorVista("R$ " + Formatacao.formatarFloatString(f.getAVista()));
				if(f.getValorParcela02()!=null){
					o.setValorentradaboelto("R$ "
							+ Formatacao.formatarFloatString(f.getValorEntrada2()));
					o.setSaldoboleto("Saldo em " + f.getNumeroParcelas02() + " parcelas fixas");
					o.setValorsaldoboleto("R$ " + Formatacao.formatarFloatString(f.getValorParcela02()));
				}
				if(f.getValorEntrada3()!=null){
					o.setValorentradacartao("R$ "
								+ Formatacao.formatarFloatString(f.getValorEntrada3()));
					o.setSaldocartao(
								"Saldo em " + f.getNumeroParcelas03() + " parcelas fixas");
					o.setValorsaldocartao("R$ "
								+ Formatacao.formatarFloatString(f.getValorParcela03()));
				}
				if(f.getFinaciamento()!=null && f.getFinaciamento()>0){
					o.setValorentradafinanciamento("R$ 0,00");
					o.setDescricaofinanciamento(
								"Saldo em " + f.getNumeroParcelasFinanciamento() + " parcelas fixas");
					o.setValorfinanciamento("R$ "
								+ Formatacao.formatarFloatString(f.getFinaciamento()));
				}
			}
			o.setObservacao(orcamentoCurso.getObservacao());
			o.setNumeroorcamento(String.valueOf(orcamentoCurso.getIdorcamentoCurso()));
			return o;
		}
		return o;
	}
	
	
	
	public void carregarCustosExtras() {
		OrcamentoPDFBean o; 
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		String sql = "SELECT o FROM Produtoorcamentocurso o where o.produtosOrcamento.tipoorcamento='R' and o.orcamentocurso=" + orcamentoCurso.getIdorcamentoCurso()
				+ " and o.somarvalortotal=false order by o.produtosOrcamento.descricao";
		List<Produtoorcamentocurso> listaR = orcamentoCursoFacade.listarProdutoOrcamentoCurso(sql);
		if (listaR != null) {
			for (int i = 0; i < listaR.size(); i++) {
				if (listaR.get(i).getProdutosOrcamento().getIdprodutosOrcamento() != 5) {
					o = carregarDadosIniciais();
					o.setTituloLista("CUSTOS EXTRAS");
					o.setDescricaolista(listaR.get(i).getProdutosOrcamento().getDescricao());
					o.setValorme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(listaR.get(i).getValorMoedaEstrangeira()));
					o.setValorrs("R$ " + Formatacao
							.formatarFloatString(listaR.get(i).getValorMoedaNacional()));
					o.setIdgrupo(5);
					this.lista.add(o);
				}
			}
		} 
		if(orcamentoCurso.getPassagemAerea()!=null && orcamentoCurso.getPassagemAerea().length()>0){
			o = carregarDadosIniciais();
			o.setTituloLista("CUSTOS EXTRAS");
			o.setDescricaolista("Passagem Aérea: " + orcamentoCurso.getPassagemAerea());
			o.setSubDescricaoLista("");
			o.setValorme("");
			o.setValorrs("");
			o.setIdgrupo(5);
			this.lista.add(o);
		}
		
		if(orcamentoCurso.getVistoConsular()!=null && orcamentoCurso.getVistoConsular().length()>0){
			o = carregarDadosIniciais();
			o.setTituloLista("CUSTOS EXTRAS");
			o.setDescricaolista("Visto consular: " + orcamentoCurso.getVistoConsular());
			o.setSubDescricaoLista("");
			o.setValorme("");
			o.setValorrs("");
			o.setIdgrupo(5);
			this.lista.add(o);
		}
		if(orcamentoCurso.getSedexInternacional()!=null && orcamentoCurso.getSedexInternacional().length()>0){
			o  = carregarDadosIniciais();
			o.setTituloLista("CUSTOS EXTRAS");
			o.setDescricaolista("Sedex Internacional: " + orcamentoCurso.getSedexInternacional());
			o.setSubDescricaoLista("");
			o.setValorme("");
			o.setValorrs("");
			o.setIdgrupo(5);
			this.lista.add(o);	
		}
		if(orcamentoCurso.getOutrasTaxas()!=null && orcamentoCurso.getOutrasTaxas().length()>0){
			o  = carregarDadosIniciais();
			o.setTituloLista("CUSTOS EXTRAS");
			o.setDescricaolista(orcamentoCurso.getOutrasTaxas());
			o.setSubDescricaoLista("");
			o.setValorme("");
			o.setValorrs("");
			o.setIdgrupo(5);
			this.lista.add(o);	
		}
		
		if (orcamentoCurso.getOrcamentomanualseguro()!=null && !orcamentoCurso.getOrcamentomanualseguro().isSomarvalortotal()){
			o= carregarDadosIniciais();
			o.setDescricaolista("Seguro Viagem");
			o.setSubDescricaoLista(String.valueOf(orcamentoCurso.getOrcamentomanualseguro().getNumerodias()) + " dias");
			o.setValorme(orcamentoCurso.getCambio().getMoedas().getSigla() + " " + Formatacao
					.formatarFloatString(orcamentoCurso.getOrcamentomanualseguro().getValor() / orcamentoCurso.getValorCambio()));
			o.setValorrs("R$ " + Formatacao.formatarFloatString(orcamentoCurso.getOrcamentomanualseguro().getValor()));
			o.setIdgrupo(5);
			lista.add(o);
		} 
	}
		
	public void carregarTaxas() {
		List<Produtoorcamentocurso> lista = getListaProdutoOrcanentoCurso("T");
		if (lista != null) {
			composicao = composicao + " + TAXAS e SUPLEMENTOS";
			OrcamentoPDFBean o = new OrcamentoPDFBean();
			for (int i = 0; i < lista.size(); i++) {
				o = carregarDadosIniciais();
				o.setTituloLista("TAXAS");
				o.setDescricaolista(lista.get(i).getProdutosOrcamento().getDescricao());
				o.setSubDescricaoLista("");
				o.setValorme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(lista.get(i).getValorMoedaEstrangeira()));
				o.setValorrs("R$ " + Formatacao
						.formatarFloatString(lista.get(i).getValorMoedaNacional()));
				o.setIdgrupo(1);
				this.lista.add(o);
				totalTxMe = totalTxMe + lista.get(i).getValorMoedaEstrangeira();
				totalTxRs = totalTxRs + (lista.get(i).getValorMoedaNacional());
			}
			o.setTotalmelista(orcamentoCurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalTxMe));
			o.setTotalrslista("R$ " + Formatacao.formatarFloatString(totalTxRs));
		} 
	}

	public void carregarAcomodacao() {
		List<Produtoorcamentocurso> lista = getListaProdutoOrcanentoCurso("A");
		if (lista != null) {
			composicao = composicao + " + ACOMODAÇÃO";
			OrcamentoPDFBean o = new OrcamentoPDFBean();
			for (int i = 0; i < lista.size(); i++) {
				o = carregarDadosIniciais();
				o.setDescricaolista(orcamentoCurso.getTipoAcomodacao()
						+ ", "
						+ orcamentoCurso.getTipoQuarto()
						+ ", "
						+ orcamentoCurso.getRefeicoes()
						+ ", Banheiro " + orcamentoCurso.getTipobanheiro()+", "+orcamentoCurso.getDuracaoAcomodacao()+" semanas.");
				o.setSubDescricaoLista(String.valueOf(orcamentoCurso.getDuracaoAcomodacao()) + "semanas");
				o.setValorme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(lista.get(i).getValorMoedaEstrangeira()));
				o.setValorrs("R$ " + Formatacao
						.formatarFloatString(lista.get(i).getValorMoedaNacional()));
				o.setIdgrupo(2);
				o.setTituloLista("ACOMODAÇÃO");
				this.lista.add(o);
				totalAcMe = totalAcMe + lista.get(i).getValorMoedaEstrangeira();
				totalAcRs = totalAcRs + lista.get(i).getValorMoedaNacional();
			}
			o.setTotalmelista(orcamentoCurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalAcMe));
			o.setTotalrslista("R$ " + Formatacao.formatarFloatString(totalAcRs));
		} 
	}

	public void carregarDesconto() {
		List<Produtoorcamentocurso> lista = getListaProdutoOrcanentoCurso("D");
		if (lista != null) {
			composicao = composicao + " - DESCONTO";
			OrcamentoPDFBean o = new OrcamentoPDFBean();
			for (int i = 0; i < lista.size(); i++) {
				o = carregarDadosIniciais();
				o.setDescricaolista(lista.get(i).getProdutosOrcamento().getDescricao());
				o.setSubDescricaoLista("");
				o.setValorme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(lista.get(i).getValorMoedaEstrangeira()));
				o.setValorrs("R$ " + Formatacao
						.formatarFloatString(lista.get(i).getValorMoedaNacional()));
				o.setIdgrupo(3);
				o.setTituloLista("DESCONTO");
				this.lista.add(o);
				totalDeMe = totalDeMe + lista.get(i).getValorMoedaEstrangeira();
				totalDeRs = totalDeRs + lista.get(i).getValorMoedaNacional();
			}
			o.setTotalmelista(orcamentoCurso.getCambio().getMoedas().getSigla() + " " + Formatacao.formatarFloatString(totalDeMe));
			o.setTotalrslista("R$ " + Formatacao.formatarFloatString(totalDeRs));
		}
	}
	
	public void carregarItensAdicionais() {
		OrcamentoPDFBean o = new OrcamentoPDFBean();
		OrcamentoCursoFacade orcamentoCursoFacade = new OrcamentoCursoFacade();
		String sql = "SELECT o FROM Produtoorcamentocurso o where o.produtosOrcamento.tipoorcamento='R' and o.orcamentocurso=" + orcamentoCurso.getIdorcamentoCurso()
				+ " and o.somarvalortotal=true order by o.produtosOrcamento.descricao";
		List<Produtoorcamentocurso> listaR = orcamentoCursoFacade.listarProdutoOrcamentoCurso(sql);
		if (listaR != null) {
			for (int i = 0; i < listaR.size(); i++) {
				if (listaR.get(i).getProdutosOrcamento().getIdprodutosOrcamento() != 5) {
					o = carregarDadosIniciais();
					o.setTituloLista("ITENS ADICIONAIS");
					o.setDescricaolista(listaR.get(i).getProdutosOrcamento().getDescricao());
					o.setValorme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(listaR.get(i).getValorMoedaEstrangeira()));
					o.setValorrs("R$ " + Formatacao
							.formatarFloatString(listaR.get(i).getValorMoedaNacional()));
					o.setIdgrupo(4);
					this.lista.add(o);
					totalAdMe = totalAdMe + listaR.get(i).getValorMoedaEstrangeira();
					totalAdRs = totalAdRs + listaR.get(i).getValorMoedaNacional();
				}
			}
		} 
		
		if (orcamentoCurso.getOrcamentomanualseguro()!=null && orcamentoCurso.getOrcamentomanualseguro().isSomarvalortotal()){
			o= carregarDadosIniciais();
			o.setTituloLista("ITENS ADICIONAIS");
			o.setDescricaolista("Seguro Viagem");
			o.setSubDescricaoLista(String.valueOf(orcamentoCurso.getOrcamentomanualseguro().getNumerodias()) + " dias");
			o.setValorme(orcamentoCurso.getCambio().getMoedas().getSigla() + " " + Formatacao
					.formatarFloatString(orcamentoCurso.getOrcamentomanualseguro().getValor() / orcamentoCurso.getValorCambio()));
			o.setValorrs("R$ " + Formatacao.formatarFloatString(orcamentoCurso.getOrcamentomanualseguro().getValor()));
			o.setIdgrupo(4);
			lista.add(o);
			totalAdMe = totalAdMe + (orcamentoCurso.getOrcamentomanualseguro().getValor() / orcamentoCurso.getValorCambio());
			totalAdRs = totalAdRs + orcamentoCurso.getOrcamentomanualseguro().getValor();
		} 
		if(totalAdMe>0){
			o.setTotalmelista(Formatacao.formatarFloatString(totalAdMe));
			o.setTotalrslista(Formatacao.formatarFloatString(totalAdRs));
		}
	}

	public void calcularTotais() {
		if (lista != null) {
			for (int i = 0; i < lista.size(); i++) {
				if (lista.get(i).getIdgrupo() == 1) {
					lista.get(i).setTotaltxme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalTxMe));
					lista.get(i).setTotaltxrs("R$ " + Formatacao.formatarFloatString(totalTxRs));
				} else if (lista.get(i).getIdgrupo() == 2) {
					lista.get(i).setTotalacme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAcMe));
					lista.get(i).setTotalacrs("R$ " + Formatacao.formatarFloatString(totalAcRs));
				} else if (lista.get(i).getIdgrupo() == 3) {
					lista.get(i).setTotalDeme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalDeMe));
					lista.get(i).setTotalDers("R$ " + Formatacao.formatarFloatString(totalDeRs));
				} else if (lista.get(i).getIdgrupo() == 4) {
					lista.get(i).setTotaladicionalme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
							+ Formatacao.formatarFloatString(totalAdMe));
					lista.get(i).setTotaladicionalrs("R$ " + Formatacao.formatarFloatString(totalAdRs));
				}
				lista.get(i).setSubPacoteme(orcamentoCurso.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(totalAcMe + totalAdMe + totalTxMe + totalCursoMe - totalDeMe));
				lista.get(i).setSubPacoters("R$ "
						+ Formatacao.formatarFloatString(totalAcRs + totalAdRs + totalTxRs + totalCursoRs - totalDeRs));
				
			}
		}
	}

	public Produtoorcamentocurso getP() {
		return p;
	}

	public void setP(Produtoorcamentocurso p) {
		this.p = p;
	} 
	
	
}
