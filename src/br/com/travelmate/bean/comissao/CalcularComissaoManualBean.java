package br.com.travelmate.bean.comissao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.travelmate.bean.FinalizarPacoteOperadora;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.FornecedorComissaoCursoFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.PacoteTrechoFacade;
import br.com.travelmate.facade.PacotesFacade;
import br.com.travelmate.facade.PassagemFacade;
import br.com.travelmate.facade.PassagemPassageiroFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TraineeFacade;

import br.com.travelmate.facade.VistosFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.Fornecedorcomissaocurso;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Pacotes;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Passagemaerea;
import br.com.travelmate.model.Passagempassageiro;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendascomissao;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;

public class CalcularComissaoManualBean {

	private Vendascomissao vendascomissao;
	private Vendas venda;
	private AplicacaoMB aplicacaoMB;
	private VendasDao vendasDao;

	public CalcularComissaoManualBean(AplicacaoMB aplicacaoMB, VendasDao vendasDao) {
		this.aplicacaoMB = aplicacaoMB;
		this.vendasDao = vendasDao;
	}

	public Vendascomissao getVendascomissao() {
		return vendascomissao;
	}

	public void setVendascomissao(Vendascomissao vendascomissao) {
		this.vendascomissao = vendascomissao;
	}

	public Vendas getVenda() {
		return venda;
	}

	public void setVenda(Vendas venda) {
		this.venda = venda;
	}

	public void geraListaVendas(String dataInicial, String dataFinal, VendasDao vendasDao) { 
		for (int p = 8; p < 21; p++) {
			String sql = "Select v from Vendas v where v.dataVenda>='2016-07-01' and v.produtos.idprodutos=" + p;
			List<Vendas> lista = vendasDao.lista(sql);
			if (lista != null) {
				for (int i = 0; i < lista.size(); i++) {
					venda = lista.get(i);
					vendascomissao = venda.getVendascomissao();
					if (vendascomissao == null) {
						vendascomissao = new Vendascomissao();
						vendascomissao.setVendas(venda);
					}

					try {
						recalcular(null);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void recalcular(Vendascomissao vc) throws SQLException {
		if (vc != null) {
			this.vendascomissao = vc;
			this.venda = vc.getVendas();
		}
		int idproduto = vendascomissao.getVendas().getProdutos().getIdprodutos();
		if (aplicacaoMB.getParametrosprodutos().getCursos() == idproduto) {
			calcularComissaoCurso();
		} else if (aplicacaoMB.getParametrosprodutos().getAupair() == idproduto) {
			calcularComissaoAupair();
		} else if (aplicacaoMB.getParametrosprodutos().getHighSchool() == idproduto) {
			calcularComissaoHighSchool();
		} else if (aplicacaoMB.getParametrosprodutos().getProgramasTeens() == idproduto) {
			calcularComissaoProgramasTeens();
		} else if (aplicacaoMB.getParametrosprodutos().getSeguroPrivado() == idproduto) {
			calcularComissaoSeguroViagems();
		} else if (aplicacaoMB.getParametrosprodutos().getTrainee() == idproduto) {
			calcularComissaoTrainee();
		} else if (aplicacaoMB.getParametrosprodutos().getVisto() == idproduto) {
			calcularComissaoVisto();
		} else if (aplicacaoMB.getParametrosprodutos().getWork() == idproduto) {
			calcularComissaoWork();
		} else if (aplicacaoMB.getParametrosprodutos().getVoluntariado() == idproduto) {
			calcularComissaoVoluntariado();
		} else if (aplicacaoMB.getParametrosprodutos().getPassagem() == idproduto) {
			calcularComissaoPassagem();
		} else if (aplicacaoMB.getParametrosprodutos().getPacotes() == idproduto) {
			calcularComissaoPacote();
		}
	}

	public void calcularComissaoPassagem() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		PassagemFacade passagemFacade = new PassagemFacade();
		Passagemaerea passagem = passagemFacade.consultar(vendascomissao.getVendas().getIdvendas());
		PassagemPassageiroFacade passagemPassageiroFacade = new PassagemPassageiroFacade();
		List<Passagempassageiro> listaPassageiro = passagemPassageiroFacade
				.lista("select p From Passagempassageiro p where p.passagemaerea.idpassagemAerea="
						+ passagem.getIdpassagemAerea());
		ComissaoPassagemBean comissaoPassagemBean = new ComissaoPassagemBean(aplicacaoMB, venda,
				formapagamento.getParcelamentopagamentoList(), passagem.getDataviagem(), venda.getVendascomissao(),
				formapagamento.getValorJuros(), passagem, listaPassageiro);
		vendascomissao = comissaoPassagemBean.getVendasComissao();
	}

	public void calcularComissaoPacote() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		PacotesFacade pacotesFacade = new PacotesFacade();
		Pacotes pacote = pacotesFacade.consultar(vendascomissao.getVendas().getIdvendas());
		PacoteTrechoFacade pacoteTrechoFacade = new PacoteTrechoFacade();
		List<Pacotetrecho> listaPacoteTrecho = pacoteTrechoFacade.listar("select p from Pacotetrecho p where p.idpacotetrecho="+pacote.getIdpacotes());
		FinalizarPacoteOperadora finalizarPacoteOperadora = new FinalizarPacoteOperadora(pacote.getPacotetrechoList());
		Float totalTarifas = finalizarPacoteOperadora.getTarifas() + finalizarPacoteOperadora.getTaxas()
				+ finalizarPacoteOperadora.getComissaoFornecedor();
		ComissaoPacotesBean comissaoPacotesBean = new ComissaoPacotesBean(aplicacaoMB, venda,
				formapagamento.getParcelamentopagamentoList(), pacote.getDatainicio(), venda.getVendascomissao(),
				formapagamento.getValorJuros(), pacote, totalTarifas,listaPacoteTrecho);
		vendascomissao = comissaoPacotesBean.getVendasComissao();
	}

	public void calcularComissaoCurso() {
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorcomissaocurso = fornecedorComissaoCursoFacade.consultar(
				vendascomissao.getVendas().getFornecedorcidade().getFornecedor().getIdfornecedor(),
				vendascomissao.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais());
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		CursoFacade cursoFacade = new CursoFacade();
		Curso curso = cursoFacade.consultarCursos(vendascomissao.getVendas().getIdvendas());
		if (fornecedorcomissaocurso != null) {
			if (fornecedorcomissaocurso.getFornecedorcomissaocursoprodutoList() != null) {
				if (venda.getOrcamento() != null) {
					if (venda.getVendaspacote()==null) {
						ComissaoCursoBean comissaoCursoBean = new ComissaoCursoBean(aplicacaoMB, vendascomissao.getVendas(),
								vendascomissao.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
								fornecedorcomissaocurso, formapagamento.getParcelamentopagamentoList(),
								curso.getDataInicio(), vendascomissao, getValorJuros(venda), true);
						vendascomissao = comissaoCursoBean.getVendasComissao();
					}else {
						ComissaoCursoPacoteBean comissaoCursoBean = new ComissaoCursoPacoteBean(aplicacaoMB, vendascomissao.getVendas(),
								vendascomissao.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
								fornecedorcomissaocurso, formapagamento.getParcelamentopagamentoList(),
								curso.getDataInicio(), vendascomissao, getValorJuros(venda), true);
						vendascomissao = comissaoCursoBean.getVendasComissao();
					}
					
				}
			}
		}

	}

	public void calcularComissaoVoluntariado() {
		FornecedorComissaoCursoFacade fornecedorComissaoCursoFacade = new FornecedorComissaoCursoFacade();
		Fornecedorcomissaocurso fornecedorcomissaocurso = fornecedorComissaoCursoFacade.consultar(
				vendascomissao.getVendas().getFornecedorcidade().getFornecedor().getIdfornecedor(),
				vendascomissao.getVendas().getFornecedorcidade().getCidade().getPais().getIdpais());
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		Voluntariado voluntariado = voluntariadoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		if (fornecedorcomissaocurso != null) {
			if (fornecedorcomissaocurso.getFornecedorcomissaocursoprodutoList() != null) {
				if (venda.getOrcamento() != null) {
					ComissaoVoluntariadoBean comissaoVoluntariadoBean = new ComissaoVoluntariadoBean(aplicacaoMB, vendascomissao.getVendas(),
							vendascomissao.getVendas().getOrcamento().getOrcamentoprodutosorcamentoList(),
							fornecedorcomissaocurso, formapagamento.getParcelamentopagamentoList(),
							voluntariado.getDataInicioVoluntariado(), vendascomissao, getValorJuros(venda), true);
					vendascomissao = comissaoVoluntariadoBean.getVendasComissao();
				}
			}
		}

	}

	public void calcularComissaoAupair() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		AupairFacade aupairFacade = new AupairFacade();
		Aupair aupair = aupairFacade.consultar(vendascomissao.getVendas().getIdvendas());
		ComissaoAuPairBean comissaoAuPair = new ComissaoAuPairBean(aplicacaoMB, venda,
				venda.getOrcamento().getOrcamentoprodutosorcamentoList(), venda.getOrcamento().getValorCambio(),
				aupair.getValoresAupair(), formapagamento.getParcelamentopagamentoList(),
				aupair.getDataInicioPretendida01(), vendascomissao, getValorJuros(venda), true);
		vendascomissao = comissaoAuPair.getVendasComissao();
	}

	public void calcularComissaoHighSchool() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		Highschool highschool = highSchoolFacade.consultarHighschool(venda.getIdvendas());
		if (highschool != null) {
			ComissaoHighSchoolBean cc = new ComissaoHighSchoolBean(aplicacaoMB, venda,
					venda.getOrcamento().getOrcamentoprodutosorcamentoList(), venda.getOrcamento().getCambio(),
					highschool.getValoreshighschool(), formapagamento.getParcelamentopagamentoList(), vendascomissao,
					highschool.getValoreshighschool().getDatainicio(), getValorJuros(venda), true);
			vendascomissao = cc.getVendasComissao();
		}
	}

	public void calcularComissaoProgramasTeens() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		Programasteens programasteens = programasTeensFacede.find(vendascomissao.getVendas().getIdvendas());
		if (venda.getOrcamento() != null) {
			ComissaoProgramasTeensBean cc = new ComissaoProgramasTeensBean(aplicacaoMB, venda,
					venda.getOrcamento().getOrcamentoprodutosorcamentoList(),
					venda.getOrcamento().getCambio().getValor(), programasteens.getValoresprogramasteens(),
					formapagamento.getParcelamentopagamentoList(), programasteens.getDataInicioCurso(), vendascomissao,
					getValorJuros(venda), true);
			vendascomissao = cc.getVendasComissao();
		}
	}

	public void calcularComissaoSeguroViagems() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemFacade.consultar(vendascomissao.getVendas().getIdvendas());
		if (seguro != null) {
			List<Parcelamentopagamento> listaParcelamento = new ArrayList<Parcelamentopagamento>();
			Parcelamentopagamento p = new Parcelamentopagamento();
			p.setDiaVencimento(new Date());
			p.setFormaPagamento("Dinheiro");
			p.setNumeroParcelas(01);
			p.setTipoParcelmaneto("Matriz");
			p.setValorParcela(venda.getValor());
			p.setValorParcelamento(venda.getValor());
			listaParcelamento.add(p);
			if (formapagamento != null) {
				if (formapagamento.getParcelamentopagamentoList() != null) {
					if (seguro.getIdvendacurso() == 0) {
						listaParcelamento = new ArrayList<Parcelamentopagamento>();
						listaParcelamento = formapagamento.getParcelamentopagamentoList();
					}
				}
			}
			boolean seguroAvulso = false;
			if (seguro != null) {
				if (seguro.getIdvendacurso() == 0) {
					seguroAvulso = true;
				}
			}
			float valorJuros = 0.0f;
			if (seguro.getIdvendacurso() == 0) {
				valorJuros = getValorJuros(venda);
			}
			ComissaoSeguroBean cc = new ComissaoSeguroBean(aplicacaoMB, venda, listaParcelamento, vendascomissao,
					seguro.getDescontoloja(), seguro.getDescontomatriz(), valorJuros, seguroAvulso, formapagamento,seguro);
			vendascomissao = cc.getVendasComissao();
		}
	}

	public void calcularComissaoSeguroViagemsSemCurso() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemFacade.consultar(vendascomissao.getVendas().getIdvendas());
		if (seguro == null) {
			seguro = new Seguroviagem();
			seguro.setVendas(venda);
			seguro.setValorSeguro(venda.getValor());
			seguro.setIdvendacurso(0);
			seguro.setPossuiSeguro("Sim");
		}
		if ((seguro.getIdvendacurso() == 0) && (seguro.getPossuiSeguro().equalsIgnoreCase("Sim"))) {
			List<Parcelamentopagamento> listaParcelamento = new ArrayList<Parcelamentopagamento>();
			Parcelamentopagamento p = new Parcelamentopagamento();
			p.setDiaVencimento(new Date());
			p.setFormaPagamento("Dinheiro");
			p.setNumeroParcelas(01);
			p.setTipoParcelmaneto("Loja");
			p.setValorParcela(venda.getValor());
			p.setValorParcelamento(venda.getValor());
			listaParcelamento.add(p);
			if (formapagamento != null) {
				if (formapagamento.getParcelamentopagamentoList() != null) {
					if (formapagamento.getParcelamentopagamentoList().size() > 0) {
						listaParcelamento = formapagamento.getParcelamentopagamentoList();
					}
				}
			}
			ComissaoSeguroBean cc = new ComissaoSeguroBean(aplicacaoMB, venda, listaParcelamento, vendascomissao,
					seguro.getDescontoloja(), seguro.getDescontomatriz(), getValorJuros(venda), true, formapagamento, seguro);
			vendascomissao = cc.getVendasComissao();
		}
	}

	public void calcularComissaoTrainee() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		TraineeFacade traineeFacade = new TraineeFacade();
		Trainee trainee = traineeFacade.consultar(vendascomissao.getVendas().getIdvendas());
		ComissaoTraineeBean cc = new ComissaoTraineeBean(aplicacaoMB, venda,
				venda.getOrcamento().getOrcamentoprodutosorcamentoList(), venda.getOrcamento().getCambio().getValor(),
				trainee.getValorestrainee(), formapagamento.getParcelamentopagamentoList(), null, vendascomissao,
				getValorJuros(venda), true);
		vendascomissao = cc.getVendasComissao();
	}

	public void calcularComissaoVisto() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		VistosFacade vistosFacade = new VistosFacade();
		Vistos vistos = vistosFacade.consultarVistos(vendascomissao.getVendas().getIdvendas());
		ComissaoVistoBean cc = new ComissaoVistoBean(venda, vendascomissao, aplicacaoMB, getValorJuros(venda),
				formapagamento.getParcelamentopagamentoList(), vistos);
		vendascomissao = cc.getVendasComissao();
	}

	public void calcularComissaoWork() {
		FormaPagamentoFacade formaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento formapagamento = formaPagamentoFacade.consultar(vendascomissao.getVendas().getIdvendas());
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		Worktravel work = workTravelFacade.consultarWork(vendascomissao.getVendas().getIdvendas());
		if (venda.getOrcamento() != null) {
			ComissaoWorkBean cc = new ComissaoWorkBean(aplicacaoMB, venda,
					venda.getOrcamento().getOrcamentoprodutosorcamentoList(),
					venda.getOrcamento().getCambio().getValor(), work.getValoreswork(),
					formapagamento.getParcelamentopagamentoList(), vendascomissao, getValorJuros(venda), true);
			vendascomissao = cc.getVendasComissao();
		}

	}

	public void gerarVendasseguroViagems() {
		CursoFacade cursoFacade = new CursoFacade();
		List<Curso> lista = cursoFacade.lista(
				"SELECT c FROM Curso c where c.vendas.dataVenda>='2016-04-01' and c.vendas.dataVenda<='2016-04-30'");
		ProdutoFacade produtoFacade = new ProdutoFacade();
		Produtos produto = produtoFacade.consultar(aplicacaoMB.getParametrosprodutos().getSeguroPrivado());
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		if (lista.size() > 0) {
			for (int i = 0; i < lista.size(); i++) {
				Seguroviagem seguro = seguroViagemFacade.consultar(lista.get(i).getVendas().getIdvendas());
				if ((seguro != null) && (seguro.getPossuiSeguro().equalsIgnoreCase("Sim"))) {
					Vendas venda = new Vendas();
					venda.setCambio(lista.get(i).getVendas().getCambio());
					venda.setCliente(lista.get(i).getVendas().getCliente());
					venda.setDataVenda(lista.get(i).getVendas().getDataVenda());
					venda.setFormapagamento(lista.get(i).getVendas().getFormapagamento());
					venda.setFornecedorcidade(seguro.getValoresseguro().getFornecedorcidade());
					venda.setFornecedor(venda.getFornecedorcidade().getFornecedor());
					venda.setOrcamento(lista.get(i).getVendas().getOrcamento());
					venda.setProdutos(produto);
					venda.setSituacao(lista.get(i).getVendas().getSituacao());
					venda.setUnidadenegocio(lista.get(i).getVendas().getUnidadenegocio());
					venda.setUsuario(lista.get(i).getVendas().getUsuario());
					venda.setValor(seguro.getValorSeguro());
					venda.setVendasMatriz(lista.get(i).getVendas().getVendasMatriz());
					venda = vendasDao.salvar(venda);
					float novaValorVenda = lista.get(i).getVendas().getValor() - seguro.getValorSeguro();
					lista.get(i).getVendas().setValor(novaValorVenda);
					vendasDao.salvar(lista.get(i).getVendas());
					seguro.setVendas(venda);
					seguro.setIdvendacurso(lista.get(i).getVendas().getIdvendas());
					seguroViagemFacade.salvar(seguro);
					vendascomissao = new Vendascomissao();
					vendascomissao.setVendas(venda);
					this.venda = venda;
					calcularComissaoSeguroViagems();
				}
			}
		}
	}

	public float getValorJuros(Vendas venda) {
		if (venda.getFormapagamento() != null && venda.getFormapagamento().getValorJuros()!=null) {
			return venda.getFormapagamento().getValorJuros();
		} else
			return 0.0f;
	}

}
