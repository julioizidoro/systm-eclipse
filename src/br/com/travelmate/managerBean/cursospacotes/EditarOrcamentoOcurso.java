package br.com.travelmate.managerBean.cursospacotes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.travelmate.facade.CoProdutosFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutoFornecedorBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Grupoobrigatorio;
import br.com.travelmate.model.Ocrusoprodutos;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Ocursoformapagamento;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;

public class EditarOrcamentoOcurso {

	private Ocurso ocurso;
	private List<ProdutosOrcamentoBean> listaSuplemento;
	private Date dataconsulta;
	private Cliente cliente;
	private Date datainicio;
	private Cursospacote cursospacote;
	private AplicacaoMB aplicacaoMB;
	private UsuarioLogadoMB usuarioLogadoMB;
	private Float valortotal;

	public EditarOrcamentoOcurso(Cliente cliente, Date datainicio, Cursospacote cursospacote, AplicacaoMB aplicacaoMB,
			UsuarioLogadoMB usuarioLogadoMB) {
		this.cliente = cliente;
		this.datainicio = datainicio;
		this.cursospacote = cursospacote;
		this.aplicacaoMB = aplicacaoMB;
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Ocurso getOcurso() {
		return ocurso;
	}

	public void setOcurso(Ocurso ocurso) {
		this.ocurso = ocurso;
	}

	public List<ProdutosOrcamentoBean> getListaSuplemento() {
		return listaSuplemento;
	}

	public void setListaSuplemento(List<ProdutosOrcamentoBean> listaSuplemento) {
		this.listaSuplemento = listaSuplemento;
	}

	public Date getDataconsulta() {
		return dataconsulta;
	}

	public void setDataconsulta(Date dataconsulta) {
		this.dataconsulta = dataconsulta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDatainicio() {
		return datainicio;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public Cursospacote getCursospacote() {
		return cursospacote;
	}

	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public Float getValortotal() {
		return valortotal;
	}

	public void setValortotal(Float valortotal) {
		this.valortotal = valortotal;
	}

	public Ocurso buscarOcurso() {
		valortotal = 0.0f;
		ocurso = new Ocurso();
		ocurso.setCambio(consultarCambio());
		ocurso.setValorcambio(ocurso.getValorcambio());
		if(cursospacote.getValorcambio()!=null && cursospacote.getValorcambio()>0){
			ocurso.setValorcambio(cursospacote.getValorcambio()); 
		}
		ocurso.setCargahoraria(cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso()
				.getCargahoraria() + " "
				+ cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso().getTipocargahoraria());
		ocurso.setCliente(cliente);
		ocurso.setDatainicio(datainicio);
		ocurso.setDataorcamento(new Date());
		ocurso.setDatavalidade(cursospacote.getDataterminovenda());
		ocurso.setDatatermino(Formatacao.calcularDataFinal(datainicio, cursospacote.getNumerosemanacurso()));
		ocurso.setFornecedorcidadeidioma(cursospacote.getFornecedorcidadeidioma());
		ocurso.setIdioma(cursospacote.getFornecedorcidadeidioma().getIdioma());
		ocurso.setNomecliente(cliente.getNome());
		ocurso.setNumerosemanas(cursospacote.getNumerosemanacurso());
		ocurso.setNumerosemanastotal(ocurso.getNumerosemanas());
		ocurso.setObservacao(cursospacote.getDescricao());
		ocurso.setProdutosorcamento(cursospacote.getValorcoprodutos_curso().getCoprodutos().getProdutosorcamento());
		ocurso.setTotalmoedaestrangeira(0.0f);
		ocurso.setTotalmoedanacional(0.0f);
		ocurso.setTurno(cursospacote.getValorcoprodutos_curso().getCoprodutos().getComplementocurso().getTurno());
		ocurso.setUsuario(usuarioLogadoMB.getUsuario()); 
		dataconsulta = retornarDataConsultaOrcamento();
		ocurso.setOcrusoprodutosList(new ArrayList<Ocrusoprodutos>());
		ocurso.setOcursodescontoList(new ArrayList<Ocursodesconto>());
		ocurso.setOcursoformapagamentoList(new ArrayList<Ocursoformapagamento>());
		ocurso.setTotalmoedaestrangeira(valortotal);
		ocurso.setTotalmoedanacional(valortotal * ocurso.getValorcambio());
		return ocurso;
	}

	public ProdutoFornecedorBean retornarFornecedorProdutosBean() {
		ProdutoFornecedorBean produtoFornecedorBean = new ProdutoFornecedorBean();
		produtoFornecedorBean.setListaCursoPrincipal(addProdutosCurso());
		produtoFornecedorBean.setListaObrigaroerios(addProdutosObrigatorios());
		return produtoFornecedorBean;
	}

	public List<ProdutosOrcamentoBean> addProdutosCurso() {
		List<ProdutosOrcamentoBean> listaCursoPrincipal = new ArrayList<>();
		ProdutosOrcamentoBean produtosOrcamentoBean = adicionarProduto(
				cursospacote.getNumerosemanacurso().doubleValue(), cursospacote.getValorcoprodutos_curso(),
				cursospacote.getValortotalcurso(), cursospacote.getValorcoprodutos_curso().getCoprodutos().getNome(),
				cursospacote.getValorcoprodutos_curso().getCoprodutos().getDescricao(), 1, "Curso");
		listaCursoPrincipal.add(produtosOrcamentoBean);
		return listaCursoPrincipal;
	}

	public List<ProdutosOrcamentoBean> addProdutosObrigatorios() {
		List<ProdutosOrcamentoBean> listaProdutos = new ArrayList<>();
		// taxaTM
		float valor = 350 / ocurso.getValorcambio();
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos taxatm = valorCoProdutosFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getTaxatmorcamento());
		ProdutosOrcamentoBean produtosOrcamentoBean = adicionarProduto(
				cursospacote.getNumerosemanacurso().doubleValue(), taxatm, valor, "Taxa TM", "Taxa TM", 1, "Curso");
		listaProdutos.add(produtosOrcamentoBean);

		String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
				+ cursospacote.getValorcoprodutos_curso().getCoprodutos().getIdcoprodutos()
				+ " and g.produto.idcoprodutos<>"
				+ cursospacote.getValorcoprodutos_curso().getCoprodutos().getIdcoprodutos();
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
					ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
							dataconsulta, "Obrigatorio");
					if (po != null) {
						listaProdutos.add(po);
					} else {
						po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta,
								"Obrigatorio");
						if (po != null) {
							listaProdutos.add(po);
						} else {
							po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta,
									"Obrigatorio");
							if (po != null) {
								listaProdutos.add(po);
							}
						}
					}
				}
			}
		}
		return listaProdutos;
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
				+ Formatacao.ConvercaoDataSql(datainicio) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(datainicio) + "' and v.numerosemanainicial<="
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
		int anoFornecedor = cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor()
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
		Date dataTermino = ocurso.getDatatermino();
		int numeroDias = 0;
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

			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatafinal(),
					po.getValorcoprodutos().getDatainicial());
		} else if ((po.getValorcoprodutos().getDatainicial().after(dataconsulta))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(dataTermino, po.getValorcoprodutos().getDatainicial());

		} else if ((po.getValorcoprodutos().getDatainicial().before(dataconsulta))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatafinal(), dataconsulta);
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
		return valorSuplemento;
	}

	public List<ProdutosOrcamentoBean> addTaxasAcomodacao() {
		List<ProdutosOrcamentoBean> listaProdutos = new ArrayList<>();
		if (cursospacote.getValorcoprodutos_acomodacao() != null) {
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

					ProdutosOrcamentoBean po = consultarValores("DI",
							listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(), dataconsulta, "Obrigatorio");
					if (po != null) {
						listaProdutos.add(po);
					} else {
						po = consultarValores("DM", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),
								ocurso.getDatainicio(), "Obrigatorio");
						if (po != null) {
							listaProdutos.add(po);
						} else {
							po = consultarValores("DS", listaGrupoObrigatorio.get(0).getProduto().getIdcoprodutos(),
									ocurso.getDatainicio(), "Obrigatorio");
							if (po != null) {
								listaProdutos.add(po);
							}
						}
					}
				}
			}

			ProdutosOrcamentoBean po = consultarValoresSuplementoAcomodacqo();
			if (po != null) {
				po.getValorcoprodutos().getCoprodutos()
						.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento() + " - Acomodação");
				listaProdutos.add(po);
			}
		}
		return listaProdutos;
	}

	public List<ProdutosOrcamentoBean> suplementoMenorIdadeAcomodacao() {
		List<ProdutosOrcamentoBean> listaProdutos = new ArrayList<>();
		int idadeVinculados = 18;
		if (cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdade() > 0) {
			idadeVinculados = cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdade();
		}
		int idade = Formatacao.calcularIdade(ocurso.getCliente().getDataNascimento());
		if (idade < idadeVinculados) {
			String sqlSuplementoIdade = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ cursospacote.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
					+ " and c.produtosorcamento.idprodutosOrcamento="
					+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
			Coprodutos coprodutos = new Coprodutos();
			CoProdutosFacade produtosFacade = new CoProdutosFacade();
			coprodutos = produtosFacade.consultar(sqlSuplementoIdade);
			if (coprodutos != null) {
				ProdutosOrcamentoBean po = consultarValores("DI", coprodutos.getIdcoprodutos(), dataconsulta,
						"Obrigatorio");
				if (po != null) {
					listaProdutos.add(po);
				} else {
					po = consultarValores("DM", coprodutos.getIdcoprodutos(), ocurso.getDatainicio(), "Obrigatorio");
					if (po != null) {
						listaProdutos.add(po);
					} else {
						po = consultarValores("DS", coprodutos.getIdcoprodutos(), ocurso.getDatainicio(),
								"Obrigatorio");
						if (po != null) {
							listaProdutos.add(po);
						}
					}
				}
			}
		}
		return listaProdutos;

	}

	public ProdutosOrcamentoBean consultarValoresSuplementoAcomodacqo() {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.datainicial>='"
				+ cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getFornecedorcidadeidioma()
						.getFornecedorcidade().getFornecedor().getAnotarifario()
				+ "-01-01' and v.numerosemanainicial<=" + cursospacote.getNumerosemanaacomodacao()
				+ " and v.numerosemanafinal>=" + cursospacote.getNumerosemanaacomodacao()
				+ " and v.coprodutos.idcoprodutos="
				+ cursospacote.getValorcoprodutos_acomodacao().getCoprodutos().getIdcoprodutos()
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
			if (valorSuplemento == 0) {
				po = null;
			} else {
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * ocurso.getValorcambio());
			}
			return po;
		}
		return null;
	}

	public ProdutosOrcamentoBean adicionarProduto(double numerosemana, Valorcoprodutos valorcoprodutos,
			float valororiginal, String nome, String descricao, int tipo, String nomegrupo) {
		ProdutosOrcamentoBean produto = new ProdutosOrcamentoBean();
		produto.setNumeroSemanas(numerosemana);
		produto.setValorcoprodutos(valorcoprodutos);
		produto.setValorOrigianl(valororiginal);
		produto.setValorOriginalRS(valororiginal * ocurso.getValorcambio());
		return produto;
	}

	public List<ProdutosOrcamentoBean> gerarListaValorCoProdutos(String tipo) {
		List<ProdutosOrcamentoBean> listaRetorno = new ArrayList<ProdutosOrcamentoBean>();
		List<Coprodutos> listaCoProdutos;
		CoProdutosFacade coProdutosFacade = new CoProdutosFacade();
		String sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ cursospacote.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma() + " and c.tipo='" + tipo
				+ "' and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
				+ " and c.produtosorcamento.idprodutosOrcamento<>"
				+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
		listaCoProdutos = coProdutosFacade.listar(sql);
		if (listaCoProdutos != null) {
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						dataconsulta, tipo);
				if (po != null) {
					listaRetorno.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), new Date(), tipo);
					if (po != null) {
						listaRetorno.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), dataconsulta, tipo);
						if (po != null) {
							listaRetorno.add(po);
						}
					}
				}
			}
		}
		if (tipo.equalsIgnoreCase("obrigatorio")) {
			if (listaSuplemento != null) {
				for (int i = 0; i < listaSuplemento.size(); i++) {
					listaRetorno.add(listaSuplemento.get(i));
				}
				listaSuplemento = null;
			}
		}
		return listaRetorno;
	}

	public List<Ocursodesconto> addOcursoDesconto() {
		ocurso.setOcursodescontoList(new ArrayList<Ocursodesconto>());
		Ocursodesconto ocursodesconto = new Ocursodesconto();
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		Produtosorcamento produtosorcamento = new Produtosorcamento();
		produtosorcamento = produtoOrcamentoFacade.consultar(9);
		ocursodesconto.setOcurso(ocurso);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		ocursodesconto.setValormoedaestrangeira(0.0f);
		ocursodesconto.setValormoedanacional(0.0f);
		ocurso.getOcursodescontoList().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(33);
		ocursodesconto.setOcurso(ocurso);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		int anoinicio=Formatacao.getAnoData(datainicio);
		if(cursospacote.getAno1()>0 || cursospacote.getAno2()>0) { 
			if(anoinicio==cursospacote.getAno1()) {
				if (cursospacote.getDescontotm1() > 0) {
					ocursodesconto.setValormoedaestrangeira(cursospacote.getDescontotm1());
					ocursodesconto.setValormoedanacional(cursospacote.getDescontotm1() * ocurso.getValorcambio());
					ocursodesconto.setSelecionado(true);
				}
			} else if(anoinicio==cursospacote.getAno2()) {
				if (cursospacote.getDescontotm2() > 0) {
					ocursodesconto.setValormoedaestrangeira(cursospacote.getDescontotm2());
					ocursodesconto.setValormoedanacional(cursospacote.getDescontotm2() * ocurso.getValorcambio());
					ocursodesconto.setSelecionado(true);
				}
			}  else {
				ocursodesconto.setValormoedaestrangeira(0.0f);
				ocursodesconto.setValormoedanacional(0.0f);
			}
		} else {
			ocursodesconto.setValormoedaestrangeira(0.0f);
			ocursodesconto.setValormoedanacional(0.0f);
		}
		ocurso.getOcursodescontoList().add(ocursodesconto);

		ocursodesconto = new Ocursodesconto();
		produtosorcamento = produtoOrcamentoFacade.consultar(267);
		ocursodesconto.setOcurso(ocurso);
		ocursodesconto.setProdutosorcamento(produtosorcamento);
		if (cursospacote.getPromocaoescola() > 0) {
			ocursodesconto.setValormoedaestrangeira(cursospacote.getPromocaoescola());
			ocursodesconto.setValormoedanacional(cursospacote.getPromocaoescola() * ocurso.getValorcambio());
			ocursodesconto.setSelecionado(true);
		} else {
			ocursodesconto.setValormoedaestrangeira(0.0f);
			ocursodesconto.setValormoedanacional(0.0f);
		}
		ocurso.getOcursodescontoList().add(ocursodesconto);
		return ocurso.getOcursodescontoList();
	}
}
