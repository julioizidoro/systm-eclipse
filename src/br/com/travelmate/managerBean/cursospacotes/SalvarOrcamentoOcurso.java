package br.com.travelmate.managerBean.cursospacotes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import br.com.travelmate.bean.LeadSituacaoBean;
import br.com.travelmate.bean.OCursoProdutosBean;
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadHistoricoDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.dao.OCursoDao;
import br.com.travelmate.dao.OCursoDescontoDao;
import br.com.travelmate.dao.OCursoFormaPagamentoDao;
import br.com.travelmate.dao.OCursoProdutoDao;
import br.com.travelmate.dao.OcursoPacoteDao;
import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.FornecedorFeriasFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;

import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.TipoContatoFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Cursopacoteformapagamento;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Fornecedorferias;
import br.com.travelmate.model.Grupoobrigatorio;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Leadhistorico;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Ocursoformapagamento;
import br.com.travelmate.model.Ocursopacote;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Tipocontato;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

public class SalvarOrcamentoOcurso {
	
	private Ocurso ocurso;
	private List<ProdutosOrcamentoBean> listaSuplemento;
	private Date dataconsulta;
	private Cliente cliente;
	private Date datainicio;
	private Cursospacote cursospacote; 
	private AplicacaoMB aplicacaoMB;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Float valortotal;
	private Cursopacoteformapagamento formapagamento;
	private Ocrusoprodutos ocrusoprodutos;
	private OCursoProdutosBean oCursoProdutosBean;
	private List<OCursoProdutosBean> listaOcursoProdutosBean;
	
	@Inject
	private LeadHistoricoDao leadHistoricoDao;
	@Inject
	private LeadDao leadDao;
	@Inject
	private OCursoDao oCursoDao;
	@Inject 
	private OCursoFormaPagamentoDao oCursoFormaPagamentoDao;
	@Inject 
	private OCursoProdutoDao oCursoProdutoDao;
	@Inject
	private OCursoDescontoDao oCursoDescontoDao;
	@Inject
	private OcursoPacoteDao oCursoPacoteDao;
	@Inject
	private LeadSituacaoDao leadSituacaoDao;
	
	public SalvarOrcamentoOcurso(Cliente cliente, Date datainicio, Cursospacote cursospacote,
			AplicacaoMB aplicacaoMB,UsuarioLogadoMB usuarioLogadoMB,Cursopacoteformapagamento formapagamento, OCursoDao oCursoDao, OCursoProdutoDao oCursoProdutoDao,
			LeadHistoricoDao leadHistoricoDao, LeadDao leadDao, OCursoFormaPagamentoDao oCursoFormaPagamentoDao, OCursoDescontoDao oCursoDescontoDao, OcursoPacoteDao ocursoPacoteDao,
			LeadSituacaoDao leadSituacaoDao) {
		this.cliente=cliente;
		this.datainicio=datainicio;
		this.cursospacote=cursospacote;
		this.aplicacaoMB=aplicacaoMB;
		this.usuarioLogadoMB=usuarioLogadoMB; 
		this.formapagamento=formapagamento;
		this.oCursoDao = oCursoDao;
		this.oCursoProdutoDao = oCursoProdutoDao;
		this.leadHistoricoDao = leadHistoricoDao;
		this.leadDao = leadDao;
		this.oCursoFormaPagamentoDao = oCursoFormaPagamentoDao;
		this.oCursoDescontoDao = oCursoDescontoDao;
		this.oCursoPacoteDao = ocursoPacoteDao;
		this.leadSituacaoDao = leadSituacaoDao;
	}

	public Ocurso salvarOcurso() {
		valortotal = 0.0f;
		ocurso = new Ocurso();
		ocurso.setCambio(consultarCambio());
		ocurso.setValorcambio(ocurso.getCambio().getValor());
		if(cursospacote.getValorcambio()!=null && cursospacote.getValorcambio()>0){
			ocurso.setValorcambio(cursospacote.getValorcambio()); 
			ocurso.setCambiocongelado(true);
		}
		ocurso.setCargahoraria(cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso()
				.getCargahoraria() + " "
				+ cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso().getTipocargahoraria());
		ocurso.setCliente(cliente); 
		ocurso.setDataorcamento(new Date());
		ocurso.setDatavalidade(cursospacote.getDataterminovenda());
		ocurso.setDatainicio(datainicio);
		ocurso.setDatatermino(Formatacao.calcularDataFinal(datainicio, cursospacote.getNumerosemanacurso()));
		ocurso.setFornecedorcidadeidioma(cursospacote.getFornecedorcidadeidioma());
		ocurso.setIdioma(cursospacote.getFornecedorcidadeidioma().getIdioma());
		ocurso.setNomecliente(cliente.getNome());
		ocurso.setNumerosemanas(cursospacote.getNumerosemanacurso());
		ocurso.setNumerosemanastotal(ocurso.getNumerosemanas());
		ocurso.setProdutosorcamento(cursospacote.getValorcoprodutos_curso().getCoprodutos().getProdutosorcamento());
		ocurso.setTotalmoedaestrangeira(0.0f);
		ocurso.setTotalmoedanacional(0.0f);
		ocurso.setTurno(cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso().getTurno());
		ocurso.setUsuario(usuarioLogadoMB.getUsuario()); 
		if(cursospacote.getValoravista()!=null && cursospacote.getValoravista()>0){
			ocurso.setValoravista(cursospacote.getValoravista());
		}else{
			ocurso.setValoravista(ocurso.getTotalmoedaestrangeira());
		}
		ocurso = oCursoDao.salvar(ocurso);
		dataconsulta = retornarDataConsultaOrcamento();
		salvarProdutosCurso();
	//	salvarTaxasCurso();
		salvarProdutosObrigatorios();
		if (cursospacote.getValorcoprodutos_acomodacao() != null) {
			salvarAcomodacao();
			salvarTaxasAcomodacao();
		}
		salvarOcursoDesconto();
//		ocurso.setTotalmoedaestrangeira(ocurso.getValoravista() / ocurso.getValorcambio());
//		ocurso.setTotalmoedanacional(ocurso.getValoravista());
//		if (valorCurso > 0f) {
//			oCursoProdutosBean = new OCursoProdutosBean();
//			oCursoProdutosBean.setTipoSoma("adicao");
//			oCursoProdutosBean.setValor(valorCurso);
//			if (listaOcursoProdutosBean == null) {
//				listaOcursoProdutosBean = new ArrayList<OCursoProdutosBean>();
//			}
//			listaOcursoProdutosBean.add(oCursoProdutosBean);
//		}
		float valorAvista = 0f;
		for (int i = 0; i < listaOcursoProdutosBean.size(); i++) {
			if (listaOcursoProdutosBean.get(i).getTipoSoma().equalsIgnoreCase("adicao")) {
				valorAvista = valorAvista + listaOcursoProdutosBean.get(i).getValor();
			}else {
				valorAvista = valorAvista - listaOcursoProdutosBean.get(i).getValor();
			}
			
		}
		ocurso.setValoravista(valorAvista* ocurso.getValorcambio());
		salvarFormaPagamento();
		ocurso.setTotalmoedaestrangeira(valorAvista);
		ocurso.setTotalmoedanacional(valorAvista * ocurso.getValorcambio());
			ocurso = oCursoDao.salvar(ocurso);
			Ocursopacote ocursopacote = new Ocursopacote();
			ocursopacote.setOcurso(ocurso);
			ocursopacote.setCursospacote(cursospacote);
			ocursopacote = oCursoPacoteDao.salvar(ocursopacote); 
		Lead lead = leadDao.consultar("SELECT l From Lead l where l.cliente.idcliente=" + cliente.getIdcliente());
		if (lead != null) {
			if (lead.getSituacao() < 3) {
				LeadSituacaoBean leadSituacaoBean = new LeadSituacaoBean(lead, lead.getSituacao(), 3, leadSituacaoDao);
				lead.setSituacao(3);
			}
			salvarHistoricoLead(ocrusoprodutos);
		}
		return ocurso;
	}
	
	public void salvarHistoricoLead(Ocrusoprodutos ocrusoprodutos) {
		Leadhistorico leadhistorico = new Leadhistorico();
		leadhistorico.setCliente(ocurso.getCliente());
		leadhistorico.setDatahistorico(new Date());
		leadhistorico.setDataproximocontato(new Date());
		TipoContatoFacade tipoContatoFacade = new TipoContatoFacade();
		String sql = "Select t From Tipocontato t where t.tipo='Orçamento'";
		Tipocontato tipocontato = tipoContatoFacade.consultar(sql);
		leadhistorico.setTipocontato(tipocontato);
		leadhistorico.setHistorico("ORÇAMENTO TARIFÁRIO " + ocurso.getIdocurso() + ": "
				+ ocrusoprodutos.getValorcoprodutos().getCoprodutos().getProdutosorcamento().getDescricao() + " - "
				+ ocrusoprodutos.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma().getFornecedorcidade()
						.getCidade().getNome()
				+ ", " + ocrusoprodutos.getValorcoprodutos().getCoprodutos().getFornecedorcidadeidioma()
						.getFornecedorcidade().getFornecedor().getNome()
				+ ".");
		leadhistorico.setTipoorcamento("t");
		leadhistorico.setIdorcamento(ocurso.getIdocurso());
		leadhistorico = leadHistoricoDao.salvar(leadhistorico);
	}

	public void salvarProdutosCurso() {
		salvarOcursoproduto(cursospacote.getNumerosemanacurso().doubleValue(), cursospacote.getValorcoprodutos_curso(),
				cursospacote.getValortotalcurso(), cursospacote.getValorcoprodutos_curso().getCoprodutos().getNome(),
				cursospacote.getValorcoprodutos_curso().getCoprodutos().getDescricao(), 1, "Curso", "adicao"); 
		//taxaTM
		float valor = aplicacaoMB.getParametrosprodutos().getValorTaxaTM() / ocurso.getValorcambio();
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos taxatm = valorCoProdutosFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getTaxatmorcamento());
		salvarOcursoproduto(cursospacote.getNumerosemanacurso().doubleValue(),taxatm,
				valor, "Taxa TM", "Taxa TM", 1, "Curso", "adicao"); 
	}

	public void salvarAcomodacao() {
		String descricao = cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getComplementoacomodacao()
				.getTipoacomodacao()
				+ ", "
				+ cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getComplementoacomodacao()
						.getTipoquarto()
				+ ", "
				+ cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getComplementoacomodacao()
						.getTiporefeicao()
				+ ", " + cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getComplementoacomodacao()
						.getTipobanheiro();
		salvarOcursoproduto(cursospacote.getNumerosemanaacomodacao().doubleValue(), cursospacote.getValorcoprodutos_acomodacao(),
				cursospacote.getValortotalacomodacao(), cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getNome(),
				descricao,3, "Acomodação", "adicao");  
	}

	public void salvarProdutosObrigatorios() {
		String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
				+ cursospacote.getValorcoprodutos_curso().getCoprodutos().getIdcoprodutos()
				+ " and g.produto.idcoprodutos<>"+cursospacote.getValorcoprodutos_curso().getCoprodutos().getIdcoprodutos();
		GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
		List<Grupoobrigatorio> listaGrupoObrigatorio = grupoObrigatorioFacade.listar(sql);
		List<Coprodutos> listaCoProdutos = new ArrayList<Coprodutos>();
		int suplementoMenorDeIdade = 0;
		if (listaGrupoObrigatorio != null) {
			
			for (int i = 0; i < listaGrupoObrigatorio.size(); i++) {   
				int idadeVinculados = 18;
				int idadeestudante = 0;
				if (listaGrupoObrigatorio.get(i).getCoprodutos().getIdade() > 0) {
					idadeVinculados = listaGrupoObrigatorio.get(i).getCoprodutos().getIdade();
				}
				if (ocurso.getCliente().getDataNascimento() != null) {
					idadeestudante = Formatacao.calcularIdade(ocurso.getCliente().getDataNascimento());
				}
				if (idadeestudante >= idadeVinculados) {
					if (listaGrupoObrigatorio.get(i).isMenorobrigatorio()) {
						listaGrupoObrigatorio.remove(i);
					} else {
						listaCoProdutos.add(listaGrupoObrigatorio.get(i).getProduto());
						if (ocurso.getCliente().getDataNascimento() != null) {
							if (suplementoMenorDeIdade == 0) {
								if (listaGrupoObrigatorio.get(i).getProduto().isSuplementemenoridade()) {
									if (idadeestudante <= idadeVinculados) {
										String sqlSuplementoIdade = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
												+ ocurso.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
												+ " and c.produtosorcamento.idprodutosOrcamento="
												+ aplicacaoMB.getParametrosprodutos().getSuplementoidade();
										Coprodutos coprodutos = new Coprodutos();
										CoProdutosFacade produtosFacade = new CoProdutosFacade();
										coprodutos = produtosFacade.consultar(sqlSuplementoIdade);

										if (coprodutos != null) {
											listaCoProdutos.add(coprodutos);
											suplementoMenorDeIdade = 1;
										}
									}
								}
							}
						}
					}
				} else {
					listaCoProdutos.add(listaGrupoObrigatorio.get(i).getProduto());
					if (ocurso.getCliente().getDataNascimento() != null) {
						if (suplementoMenorDeIdade == 0) {
							if (listaGrupoObrigatorio.get(i).getProduto().isSuplementemenoridade()) {
								if (idadeestudante <= idadeVinculados) {
									String sqlSuplementoIdade = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
											+ ocurso.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
											+ " and c.produtosorcamento.idprodutosOrcamento="
											+ aplicacaoMB.getParametrosprodutos().getSuplementoidade();
									Coprodutos coprodutos = new Coprodutos();
									CoProdutosFacade produtosFacade = new CoProdutosFacade();
									coprodutos = produtosFacade.consultar(sqlSuplementoIdade);
									if (coprodutos != null) {
										listaCoProdutos.add(coprodutos);
										suplementoMenorDeIdade = 1;
									}
								}
							}
						}
					}
				}
			} 
			if (listaCoProdutos != null) {
				for (int i = 0; i < listaCoProdutos.size(); i++) { 
					ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta,
							"Obrigatorio");
					if (po != null) {
						salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
								po.getValorcoprodutos(), po.getValorOrigianl(),
								po.getValorcoprodutos().getCoprodutos().getNome(),
								po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");  
					} else {
						po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date(), "Obrigatorio");
						if (po != null) {
							salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
									po.getValorcoprodutos(), po.getValorOrigianl(),
									po.getValorcoprodutos().getCoprodutos().getNome(),
									po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");  
						} else {
							po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta, "Obrigatorio");
							if (po != null) {
								salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
										po.getValorcoprodutos(), po.getValorOrigianl(),
										po.getValorcoprodutos().getCoprodutos().getNome(),
										po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");  
							}
						}
					}
				}
			}
		}
	}

	public Cambio consultarCambio() {
		Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), 
				cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas());
		return cambio;
	}

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos, Date dataConsulta, String tipo) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.numerosemanainicial<="
				+ cursospacote.getNumerosemanacurso() + " and v.numerosemanafinal>="
				+ cursospacote.getNumerosemanacurso() + " and v.tipodata='" + tipoData
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);

		int ano = cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getAnotarifario();
		String sqlSuplemento = "Select v from  Valorcoprodutos v where v.datainicial>='" + ano
				+ "-01-01' and v.numerosemanainicial<=" + cursospacote.getNumerosemanacurso()
				+ " and v.numerosemanafinal>=" + cursospacote.getNumerosemanacurso() + " and v.tipodata='" + tipoData
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
		List<Valorcoprodutos> listaValorcoprodutosSuplemento = valorCoProdutosFacade.listar(sqlSuplemento);
		if (listaValorcoprodutosSuplemento != null) {
			for (int n = 0; n < listaValorcoprodutosSuplemento.size(); n++) {
				if (listaValorcoprodutosSuplemento.get(n).getProdutosuplemento().equalsIgnoreCase("Curso")) {
					listaValorcoprodutosSuplemento.get(n).getCoprodutos().getProdutosorcamento().setDescricao(
							listaValorcoprodutosSuplemento.get(n).getCoprodutos().getProdutosorcamento().getDescricao()
									+ " - Curso");
					Valorcoprodutos valorSuplemente = new Valorcoprodutos();
					valorSuplemente = listaValorcoprodutosSuplemento.get(n);
					listarValores(valorSuplemente, tipo);
				}
			}
		}
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				}
			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = cursospacote.getNumerosemanacurso();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = cursospacote.getNumerosemanacurso() * 7;
			}
			po.setValorOrigianl(valorcoprodutos.getValororiginal() * multiplicador);
			po.setValorOriginalRS(po.getValorOrigianl() * ocurso.getValorcambio());
			if (tipo.equalsIgnoreCase("Acomodacao")) {
				po.setValorOrigianl(0.0f);
				po.setValorOriginalRS(0.0f);
			}
			return po;
		}
		return null;
	}

	public Date retornarDataConsultaOrcamento() {
		int anoFornecedor = 0;
		if(cursospacote.getAnotarifario()>0) {
			anoFornecedor = cursospacote.getAnotarifario();
			Calendar c = new GregorianCalendar();
			c.setTime(datainicio);
			int ano = Formatacao.getAnoData(datainicio);
			if (anoFornecedor == ano) {
				return datainicio;
			} else {
				String sData = Formatacao.ConvercaoDataPadrao(datainicio);
				String dia = sData.substring(0, 2);
				String mes = sData.substring(3, 5);
				sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
				return Formatacao.ConvercaoStringDataBrasil(sData);
			} 
		}else {
			anoFornecedor = cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor()
				.getAnotarifario();
			Calendar c = new GregorianCalendar();
			c.setTime(datainicio);
			int ano = Formatacao.getAnoData(datainicio);
			if (anoFornecedor >= ano) {
				return datainicio;
			} else if (anoFornecedor < ano) {
				String sData = Formatacao.ConvercaoDataPadrao(datainicio);
				String dia = sData.substring(0, 2);
				String mes = sData.substring(3, 5);
				sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
				return Formatacao.ConvercaoStringDataBrasil(sData);
			}
			return datainicio;
		} 
	}

	public void listarValores(Valorcoprodutos valorcoprodutos, String tipo) {
		if (listaSuplemento == null) {
			if (valorcoprodutos != null) {
				ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
				po.setValorcoprodutos(valorcoprodutos);
				po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
				int multiplicador = 1;
				if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
					multiplicador = ocurso.getNumerosemanastotal();
				} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
					multiplicador = ocurso.getNumerosemanastotal() * 7;
				}
				float valorSuplemento = calcularValorFracionadoSuplemento(multiplicador, po);
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * ocurso.getValorcambio());
				if (tipo.equalsIgnoreCase("Acomodacao")) {
					po.setValorOrigianl(0.0f);
					po.setValorOriginalRS(0.0f);
				}
				if (listaSuplemento == null) {
					listaSuplemento = new ArrayList<ProdutosOrcamentoBean>();
				}
				po.getValorcoprodutos().getCoprodutos()
						.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento() + " - Curso");
				listaSuplemento.add(po);
			}
		}
	}

	public float calcularValorFracionadoSuplemento(int multiplicador, ProdutosOrcamentoBean po) {
		float valorSuplemento = 0.0f; 
		Date dataTermino = calcularDataTerminoCurso(dataconsulta, cursospacote.getNumerosemanaacomodacao().intValue());
		int numeroDias = 0;
		boolean calcular = true;
		if (po.getValorcoprodutos().getDatainicial().after(dataconsulta) && po.getValorcoprodutos().getDatainicial().after(dataTermino)  ||
				(po.getValorcoprodutos().getDatafinal().before(dataconsulta) && po.getValorcoprodutos().getDatafinal().before(dataTermino))){
			calcular = false;
		}
		if (calcular) {
			if ((po.getValorcoprodutos().getDatainicial().before(dataconsulta)
					|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
							.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataconsulta)))
					&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
							|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
									.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
				valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;
	
			} else if ((po.getValorcoprodutos().getDatainicial().after(dataconsulta))
					&& (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
							.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
				valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;
	
			} else if ((po.getValorcoprodutos().getDatainicial().after(dataconsulta))
					&& (po.getValorcoprodutos().getDatafinal().before(dataTermino))) {
	
				numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(),
						po.getValorcoprodutos().getDatafinal());
			} else if ((po.getValorcoprodutos().getDatainicial().after(dataconsulta))
					&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
							|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
									.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
				numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);
	
			} else if ((po.getValorcoprodutos().getDatainicial().before(dataconsulta))
					&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
							|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
									.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
				numeroDias = Formatacao.subtrairDatas(dataconsulta, po.getValorcoprodutos().getDatafinal());
			}
			if ((valorSuplemento == 0) && (numeroDias > 0)) {
				if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
					int resto = (numeroDias % 7);
					numeroDias = numeroDias / 7;
					if (resto > 0) {
						numeroDias = numeroDias + 1;
					}
				}
				valorSuplemento = po.getValorcoprodutos().getValororiginal();
				valorSuplemento = valorSuplemento * numeroDias;
			}
		}
		return valorSuplemento;
	}
	
	public Date calcularDataTerminoCurso(Date dataInical, int numeroSemanas) {
		if ((dataInical != null) && (numeroSemanas > 0)) {
			if (numeroSemanas > 0) {
				Date data = Formatacao.calcularDataFinal(dataInical, numeroSemanas);
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.OrcamentoCursoMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				String sql = "Select f from Fornecedorferias f where f.datafinal>='"
						+ Formatacao.ConvercaoDataSql(dataInical) + "' and f.datafinal<='"
						+ Formatacao.ConvercaoDataSql(data) + "'";
				FornecedorFeriasFacade fornecedorFeriasFacade = new FornecedorFeriasFacade();
				List<Fornecedorferias> listaFornecedorferias = fornecedorFeriasFacade.listar(sql);
				if (listaFornecedorferias == null) {
					listaFornecedorferias = new ArrayList<Fornecedorferias>();
				}
				if (listaFornecedorferias.size() > 0) {
					try {
						data = Formatacao.SomarDiasDatas(data, listaFornecedorferias.get(0).getNumerodias());
					} catch (Exception ex) {
						Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.OrcamentoCursoMB.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				}
				return data;
			}
		}
		return null;
	}

	public void salvarTaxasAcomodacao() {
		if (ocurso.getCliente().getDataNascimento() != null) {
			if (cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().isSuplementemenoridade()) {
				int idadeVinculados = 18;
				if (cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdade() > 0) {
					idadeVinculados = cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdade();
				}
				int idade = Formatacao.calcularIdade(ocurso.getCliente().getDataNascimento());
				if (idade < idadeVinculados) {
					suplementoMenorIdadeAcomodacao();
				}
			}
		} 
		String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
				+ cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdcoprodutos();
		GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
		List<Grupoobrigatorio> listaGrupoObrigatorio;
		listaGrupoObrigatorio = grupoObrigatorioFacade.listar(sql); 
		if (listaGrupoObrigatorio != null) {
			if (listaGrupoObrigatorio.size() > 0) {  
				ProdutosOrcamentoBean po = consultarValores("DI", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),
						dataconsulta, "Obrigatorio");
				if (po != null) {
					salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
							po.getValorcoprodutos(), po.getValorOrigianl(),
							po.getValorcoprodutos().getCoprodutos().getNome(),
							po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
				} else {
					po = consultarValores("DM", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),  
							new Date(), "Obrigatorio");
					if (po != null) {
						salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
								po.getValorcoprodutos(), po.getValorOrigianl(),
								po.getValorcoprodutos().getCoprodutos().getNome(),
								po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
					} else {
						po = consultarValores("DS", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),  
								dataconsulta, "Obrigatorio");
						if (po != null) {
							salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
									po.getValorcoprodutos(), po.getValorOrigianl(),
									po.getValorcoprodutos().getCoprodutos().getNome(),
									po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
						} 
					}
				}
			}
		}

		ProdutosOrcamentoBean po = consultarValoresSuplementoAcomodacqo();
		if (po != null) {
			po.getValorcoprodutos().getCoprodutos()
					.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento() + " - Acomodação");
			salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
					po.getValorcoprodutos(), po.getValorOrigianl(),
					po.getValorcoprodutos().getCoprodutos().getNome(),
					po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
		}
	}
	
	public void salvarTaxasCurso() {
		if (ocurso.getCliente().getDataNascimento() != null) {
			if (cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().isSuplementemenoridade()) {
				int idadeVinculados = 18;
				if (cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdade() > 0) {
					idadeVinculados = cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdade();
				}
				int idade = Formatacao.calcularIdade(ocurso.getCliente().getDataNascimento());
				if (idade < idadeVinculados) {
					suplementoMenorIdadeAcomodacao();
				}
			}
		} 
		String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
				+ cursospacote.getValorcoprodutos_curso().getCoprodutos().getIdcoprodutos();
		GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
		List<Grupoobrigatorio> listaGrupoObrigatorio;
		listaGrupoObrigatorio = grupoObrigatorioFacade.listar(sql); 
		if (listaGrupoObrigatorio != null) {
			if (listaGrupoObrigatorio.size() > 0) {  
				ProdutosOrcamentoBean po = consultarValores("DI", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),
						dataconsulta, "Obrigatorio");
				if (po != null) {
					salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
							po.getValorcoprodutos(), po.getValorOrigianl(),
							po.getValorcoprodutos().getCoprodutos().getNome(),
							po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
				} else {
					po = consultarValores("DM", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),  
							new Date(), "Obrigatorio");
					if (po != null) {
						salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
								po.getValorcoprodutos(), po.getValorOrigianl(),
								po.getValorcoprodutos().getCoprodutos().getNome(),
								po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
					} else {
						po = consultarValores("DS", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),  
								dataconsulta, "Obrigatorio");
						if (po != null) {
							salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
									po.getValorcoprodutos(), po.getValorOrigianl(),
									po.getValorcoprodutos().getCoprodutos().getNome(),
									po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
						} 
					}
				}
			}
		}

		ProdutosOrcamentoBean po = consultarValoresSuplementoCurso();
		if (po != null) {
			po.getValorcoprodutos().getCoprodutos()
					.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento() + " - Curso");
			salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
					po.getValorcoprodutos(), po.getValorOrigianl(),
					po.getValorcoprodutos().getCoprodutos().getNome(),
					po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
		}
	}
	
	public ProdutosOrcamentoBean consultarValoresSuplementoCurso() {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.datainicial>='"
				+ cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getFornecedorcidadeidioma()
						.getFornecedorcidade().getFornecedor().getAnotarifario()
				+ "-01-01' and v.numerosemanainicial<=" + cursospacote.getNumerosemanacurso()
				+ " and v.numerosemanafinal>=" + cursospacote.getNumerosemanacurso()
				+ " and v.coprodutos.idcoprodutos=" + cursospacote.getValorcoprodutos_curso().getCoprodutos().getIdcoprodutos()
				+ " and v.produtosuplemento='Curso'";

		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				}  
			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) cursospacote.getNumerosemanaacomodacao().intValue();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (cursospacote.getNumerosemanaacomodacao() * 7);
			}
			float valorSuplemento = calcularValorFracionadoSuplemento(multiplicador, po);
			if(valorSuplemento==0){
				po=null;
			}else{
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * ocurso.getValorcambio());
			}
			return po;
		}
		return null;
	}
	
	public void suplementoMenorIdadeAcomodacao() {
		String sqlSuplementoIdade = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ cursospacote.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and c.produtosorcamento.idprodutosOrcamento="
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
		Coprodutos coprodutos = new Coprodutos();
		CoProdutosFacade produtosFacade = new CoProdutosFacade();
		coprodutos = produtosFacade.consultar(sqlSuplementoIdade);
		if (coprodutos != null) { 
			ProdutosOrcamentoBean po = consultarValores("DI", coprodutos.getIdcoprodutos(),
					dataconsulta, "Obrigatorio");
			if (po != null) {
				salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
						po.getValorcoprodutos(), po.getValorOrigianl(),
						po.getValorcoprodutos().getCoprodutos().getNome(),
						po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
			} else {
				po = consultarValores("DM", coprodutos.getIdcoprodutos(),  
						ocurso.getDatainicio(), "Obrigatorio");
				if (po != null) {
					salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
							po.getValorcoprodutos(), po.getValorOrigianl(),
							po.getValorcoprodutos().getCoprodutos().getNome(),
							po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
				} else {
					po = consultarValores("DS", coprodutos.getIdcoprodutos(),  
							ocurso.getDatainicio(), "Obrigatorio");
					if (po != null) {
						salvarOcursoproduto(ocurso.getNumerosemanas().doubleValue(), 
								po.getValorcoprodutos(), po.getValorOrigianl(),
								po.getValorcoprodutos().getCoprodutos().getNome(),
								po.getValorcoprodutos().getCoprodutos().getDescricao(), 1, "Curso", "adicao");   
					} 
				}
			}
		}
	}
	
	public ProdutosOrcamentoBean consultarValoresSuplementoAcomodacqo() {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.datainicial>='"
				+ cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getFornecedorcidadeidioma()
						.getFornecedorcidade().getFornecedor().getAnotarifario()
				+ "-01-01' and v.numerosemanainicial<=" + cursospacote.getNumerosemanaacomodacao()
				+ " and v.numerosemanafinal>=" + cursospacote.getNumerosemanaacomodacao()
				+ " and v.coprodutos.idcoprodutos=" + cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdcoprodutos()
				+ " and v.produtosuplemento='Acomodação'";

		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if (valorcoprodutos == null) {
					valorcoprodutos = new Valorcoprodutos();
					valorcoprodutos = listaValorcoprodutoses.get(n);
				}  
			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = (int) cursospacote.getNumerosemanaacomodacao().intValue();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = (int) (cursospacote.getNumerosemanaacomodacao() * 7);
			}
			float valorSuplemento = calcularValorFracionadoSuplemento(multiplicador, po);
			if(valorSuplemento==0){
				po=null;
			}else{
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * ocurso.getValorcambio());
			}
			return po;
		}
		return null;
	}
	
	
	public void salvarOcursoproduto(double numerosemana, Valorcoprodutos valorcoprodutos, float valororiginal,
			String nome, String descricao, int tipo, String nomegrupo, String tiposoma){
		Ocrusoprodutos produto = new Ocrusoprodutos();
		produto.setNumerosemanas(numerosemana);
		produto.setValorcoprodutos(valorcoprodutos);
		produto.setValororiginal(valororiginal);
		produto.setValorpromocional(0.0f);
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setTipo(tipo);
		produto.setNomegrupo(nomegrupo);
		produto.setOcurso(ocurso);
			produto = oCursoProdutoDao.salvar(produto);
			if(tiposoma.equalsIgnoreCase("adicao")){
				valortotal = valortotal + valororiginal;
			}else{
				valortotal = valortotal - valororiginal;
			}
		if (ocrusoprodutos == null) {
			ocrusoprodutos = produto;
		}
		oCursoProdutosBean = new OCursoProdutosBean();
		if (listaOcursoProdutosBean == null) {
			listaOcursoProdutosBean = new ArrayList<>();
		}
		oCursoProdutosBean.setTipoSoma(tiposoma);
		oCursoProdutosBean.setValor(valororiginal);
		listaOcursoProdutosBean.add(oCursoProdutosBean);
	}
	
	public void salvarFormaPagamento(){
		Ocursoformapagamento ocursoformapagamento;
			if(formapagamento!=null){  
				ocursoformapagamento = new Ocursoformapagamento();
				if(formapagamento.getValorparcelaboleto()!=null && formapagamento.getValorparcelaboleto()>0){ 
					ocursoformapagamento.setValorEntrada(formapagamento.getValorentradaboleto());
					ocursoformapagamento.setNumeroparcela(formapagamento.getNumeroparcelasboleto());
					ocursoformapagamento.setValorparcela(formapagamento.getValorparcelaboleto());
					ocursoformapagamento.setOcurso(ocurso);
					ocursoformapagamento = oCursoFormaPagamentoDao.salvar(ocursoformapagamento);
				}else{
					ocursoformapagamento.setValorEntrada(0.0f);
					ocursoformapagamento.setNumeroparcela(0);
					ocursoformapagamento.setValorparcela(0.0f);
					ocursoformapagamento.setOcurso(ocurso);
					ocursoformapagamento = oCursoFormaPagamentoDao.salvar(ocursoformapagamento);
				}
				ocursoformapagamento = new Ocursoformapagamento();
				if(formapagamento.getValorparcelacartao()!=null && formapagamento.getValorparcelacartao()>0){ 
					ocursoformapagamento.setValorEntrada(formapagamento.getValorentradacartao());
					ocursoformapagamento.setNumeroparcela(formapagamento.getNumeroparcelascartao());
					ocursoformapagamento.setValorparcela(formapagamento.getValorparcelacartao());
					ocursoformapagamento.setOcurso(ocurso); 
					ocursoformapagamento = oCursoFormaPagamentoDao.salvar(ocursoformapagamento); 
				} else{
					ocursoformapagamento.setValorEntrada(0.0f);
					ocursoformapagamento.setNumeroparcela(0);
					ocursoformapagamento.setValorparcela(0.0f);
					ocursoformapagamento.setOcurso(ocurso);
					ocursoformapagamento = oCursoFormaPagamentoDao.salvar(ocursoformapagamento);
				}
				ocursoformapagamento = new Ocursoformapagamento();
				if(formapagamento.getValorparcelafinanciamento()!=null && formapagamento.getValorparcelafinanciamento()>0){ 
					ocursoformapagamento.setValorEntrada(formapagamento.getValorentradafinanciamento());
					ocursoformapagamento.setNumeroparcela(formapagamento.getNparcelasfinanciamento().intValue());
					ocursoformapagamento.setValorparcela(formapagamento.getValorparcelafinanciamento());
					ocursoformapagamento.setOcurso(ocurso); 
					ocursoformapagamento = oCursoFormaPagamentoDao.salvar(ocursoformapagamento); 
				} else{
					ocursoformapagamento.setValorEntrada(0.0f);
					ocursoformapagamento.setNumeroparcela(0);
					ocursoformapagamento.setValorparcela(0.0f);
					ocursoformapagamento.setOcurso(ocurso);
					ocursoformapagamento = oCursoFormaPagamentoDao.salvar(ocursoformapagamento);
				}
			}else{
				ocursoformapagamento = new Ocursoformapagamento();
				ocursoformapagamento.setValorEntrada(0.0f);
				ocursoformapagamento.setNumeroparcela(0);
				ocursoformapagamento.setValorparcela(0.0f);
				ocursoformapagamento.setOcurso(ocurso);
				ocursoformapagamento = oCursoFormaPagamentoDao.salvar(ocursoformapagamento);
			}  
	
	}
	
	public void salvarOcursoDesconto(){
		Ocursodesconto ocursodesconto = new Ocursodesconto();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = new Produtosorcamento();
		produtosorcamento = produtoOrcamentoFacade.consultar(9);
		ocursodesconto.setOcurso(ocurso);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f);
		oCursoDescontoDao.salvar(ocursodesconto);
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
    	Valorcoprodutos valorcoprodutos = valorCoProdutosFacade.consultar(aplicacaoMB.getParametrosprodutos().getCoseguroprivado());
    	ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(33);
		ocursodesconto.setOcurso(ocurso);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		int anoinicio=Formatacao.getAnoData(datainicio);
		if(cursospacote.getAno1()>0 || cursospacote.getAno2()>0) { 
			if(anoinicio==cursospacote.getAno1()) {
				if (cursospacote.getDescontotmreal1() > 0) {
					 valorcoprodutos = valorCoProdutosFacade.consultar(aplicacaoMB.getParametrosprodutos().getCodescontomatriz());
					 float descontoTMEstrangeira = cursospacote.getDescontotm1() + (cursospacote.getDescontotmreal1() / ocurso.getValorcambio());
					 ocursodesconto.setValormoedaestrangeira(descontoTMEstrangeira);
					 ocursodesconto.setValormoedanacional(descontoTMEstrangeira * ocurso.getValorcambio());
					 valortotal = valortotal - ocursodesconto.getValormoedaestrangeira();
					 ocursodesconto.setSelecionado(true);
					 salvarOcursoproduto(0.0, valorcoprodutos, ocursodesconto.getValormoedaestrangeira(),
					 				produtosorcamento.getDescricao(), produtosorcamento.getDescricao(), 8, "Desconto", "subtracao"); 
				}
			} else if(anoinicio==cursospacote.getAno2()) {
				if (cursospacote.getDescontotmreal2() > 0) {
					 valorcoprodutos = valorCoProdutosFacade.consultar(aplicacaoMB.getParametrosprodutos().getCodescontomatriz());
					 float descontoTMEstrangeira = cursospacote.getDescontotm2() + (cursospacote.getDescontotmreal2() / ocurso.getValorcambio());
					 ocursodesconto.setValormoedaestrangeira(descontoTMEstrangeira);
					 ocursodesconto.setValormoedanacional(descontoTMEstrangeira * ocurso.getValorcambio());
					 valortotal = valortotal - ocursodesconto.getValormoedaestrangeira();
					 ocursodesconto.setSelecionado(true);
					 salvarOcursoproduto(0.0, valorcoprodutos, ocursodesconto.getValormoedaestrangeira(),
					 				produtosorcamento.getDescricao(), produtosorcamento.getDescricao(), 8, "Desconto", "subtracao"); 
				}
			}  else {
				ocursodesconto.setValormoedaestrangeira(0.0f);
				ocursodesconto.setValormoedanacional(0.0f);
			}
		} else {
			ocursodesconto.setValormoedaestrangeira(0.0f);
			ocursodesconto.setValormoedanacional(0.0f);
		}
		oCursoDescontoDao.salvar(ocursodesconto); 
		
		
		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(267);
		ocursodesconto.setOcurso(ocurso);
		ocursodesconto.setProdutosorcamento(produtosorcamento);  
		if(cursospacote.getPromocaoescola()>0){
			valorcoprodutos = valorCoProdutosFacade.consultar(aplicacaoMB.getParametrosprodutos().getCopromocaoescola());
			ocursodesconto.setValormoedaestrangeira(cursospacote.getPromocaoescola());
			ocursodesconto.setValormoedanacional(cursospacote.getPromocaoescola()*ocurso.getValorcambio());
			ocursodesconto.setSelecionado(true);
			valortotal = valortotal - ocursodesconto.getValormoedaestrangeira();
			salvarOcursoproduto(0.0, valorcoprodutos, ocursodesconto.getValormoedaestrangeira(),
					produtosorcamento.getDescricao(), produtosorcamento.getDescricao(), 8, "Desconto", "subtracao");
		}else{
			ocursodesconto.setValormoedaestrangeira(0.0f);
			ocursodesconto.setValormoedanacional(0.0f);
		}
		oCursoDescontoDao.salvar(ocursodesconto);
		
	}
}
