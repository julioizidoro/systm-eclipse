package br.com.travelmate.bean;

import java.sql.SQLException;
import java.util.Date;

import br.com.travelmate.dao.VendasEmbarqueDao;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TipoArquivoProdutoFacade;
import br.com.travelmate.facade.TraineeFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Controleaupair;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Controledemipair;
import br.com.travelmate.model.Controlehighschool;
import br.com.travelmate.model.Controleprogramasteen;
import br.com.travelmate.model.Controleseguro;
import br.com.travelmate.model.Controletrainee;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vendasembarque;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;

public class ControlerBean {

	public void salvarInvoice(int idControle, Vendas venda) {
		Invoice invoice = new Invoice();
		invoice.setValorPago(0.0f);
		invoice.setVendas(venda);
		invoice.setControle(idControle);
		invoice.setProdutos(venda.getProdutos());
		invoice.setValorPagamentoInvoice(0.0f);
		invoice.setValorcredito(0.0f);
		invoice.setPrioridade("Normal");
		invoice.setObscredito("");
		invoice.setTipo("Programa");
		TipoArquivoProdutoFacade tipoArquivoProdutoFacade = new TipoArquivoProdutoFacade();
		try {
			if (venda.getProdutos().getIdprodutos() == 22) {
				invoice.setTipoarquivoproduto(tipoArquivoProdutoFacade.consultar(187));

			} else if (venda.getProdutos().getIdprodutos() == 1) {
				invoice.setTipoarquivoproduto(tipoArquivoProdutoFacade.consultar(31));
			} else {
				invoice.setTipoarquivoproduto(tipoArquivoProdutoFacade.consultarArquivoProduto(
						"SELECT t FROM Tipoarquivoproduto t WHERE t.tipoarquivo.idtipoarquivo=44 AND t.produtos.idprodutos="
								+ venda.getProdutos().getIdprodutos()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		invoiceFacade.salvar(invoice);
	}

	public void salvarControleCurso(Vendas venda, Curso curso, Float valorPrevisto,
			VendasEmbarqueDao vendasEmbarqueDao) {
		Controlecurso controle = new Controlecurso();
		CursoFacade cursoFacade = new CursoFacade();
		controle = cursoFacade.consultarControleCursos(curso.getVendas().getIdvendas());
		if (controle == null) {
			controle = new Controlecurso();
			controle.setCurso(curso);
			controle.setKitViagem("Não");
			controle.setVisto("Não");
			controle.setDocs("VM");

			controle.setOrientacaoPreEmbarque("Não");
			controle.setLoasObs(" ");
			controle.setDocanexado("N");
			controle.setPossuiAcomidacao("Não");
			controle.setValorAcomodacao(0.0f);
			controle.setWork(0);
			controle.setSituacao("Processo");
			controle.setVendas(curso.getVendas());
			controle = cursoFacade.salvar(controle);
			Vendasembarque vendasEmbarque = new Vendasembarque();
			try {
				vendasEmbarque.setDataida(Formatacao.SomarDiasDatas(curso.getDataInicio(), -2));
				vendasEmbarque.setDatavolta(Formatacao.SomarDiasDatas(curso.getDataTermino(), 2));
				vendasEmbarque.setVendas(curso.getVendas());
				vendasEmbarqueDao.salvar(vendasEmbarque);
			} catch (Exception e) {
				e.printStackTrace();
			}
			////salvarInvoice(controle.getIdcontroleCursos(), venda);
		}
	}

	public Date gerarDataPrevistaPagamentoInvoiceCurso(Date dataInicio) {
		try {
			Date dataPrevista = Formatacao.SomarDiasDatas(dataInicio, -30);
			int diaSemana = Formatacao.diaSemana(dataPrevista);
			if (diaSemana == 1) {
				dataPrevista = Formatacao.SomarDiasDatas(dataPrevista, 1);
			} else if (diaSemana == 7) {
				dataPrevista = Formatacao.SomarDiasDatas(dataPrevista, 2);
			}
			return dataPrevista;
		} catch (Exception e) {

		}
		return null;
	}

	public void salvarControleSeguro(Seguroviagem seguroViagem) {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Controleseguro controle = seguroViagem.getControleseguro();
		if (controle == null) {
			controle = new Controleseguro();
			controle.setSeguroviagem(seguroViagem);
			controle.setEnvioVoucher("Não");
			controle.setObservacao(" ");
			controle.setFinalizado("Processo");
			controle.setSituacao("Processo");
			controle = seguroViagemFacade.salvarControle(controle);
			////salvarInvoice(controle.getIdcontroleSeguro(), seguroViagem.getVendas());
		}
	}

	public void salvarControlTrainee(Vendas venda, Trainee trainee, Float valorPrevisto) {
		TraineeFacade traineeFacade = new TraineeFacade();
		Controletrainee controle = new Controletrainee();
		controle = traineeFacade.consultarControle(venda.getIdvendas());
		if (controle == null) {
			controle = new Controletrainee();
			controle.setStatusprocesso("Processo");
			controle.setVendas(trainee.getVendas());
			controle.setApplication("Não");
			controle.setAntecedentescriminais("Não");
			controle.setAtestadomedico("Não");
			controle.setBooking("Não");
			controle.setCartaapresentacao("Não");
			controle.setCartarecomendacao("Não");
			controle.setCarteiravacinacao("Não");
			controle.setContratooriginal("Não");
			controle.setCopiapptcolorida("Não");
			controle.setCurriculum("Não");
			controle.setFichaoriginal("Não");
			controle.setInformativoprograma("Não");
			controle.setLoa("Não");
			controle.setPagamento("Não");
			controle.setPassagem("Não");
			controle.setVisto("Não");
			controle = traineeFacade.salvar(controle);
			//salvarInvoice(controle.getIdcontroletrainee(), venda);
		}
	}

	public void salvarControlWork(Vendas venda, Worktravel work, Float valorPrevisto) {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		Controlework controle = new Controlework();
		controle = workTravelFacade.consultarControle(venda.getIdvendas());
		if (controle == null) {
			controle = new Controlework();

			controle.setStatusprocesso("Processo");
			if (venda.getCliente().getSkype() != null) {
				controle.setSkype(venda.getCliente().getSkype());
			}
			controle.setModalidade(work.getTipo());
			controle.setStatusprocesso("Processo");
			controle.setVendas(work.getVendas());
			controle = workTravelFacade.salvar(controle);
			//salvarInvoice(controle.getIdcontroleWork(), venda);
		}
	}

	public void salvarControleAupair(Vendas venda, Aupair aupair, Float valorPrevisto) {
		AupairFacade aupairFacade = new AupairFacade();
		Controleaupair controle = new Controleaupair();
		controle = aupairFacade.consultarControle(venda.getIdvendas());
		if (controle == null) {
			controle = new Controleaupair();
			controle.setStatusprocesso("Processo");
			controle.setVendas(aupair.getVendas());
			controle = aupairFacade.salvar(controle);
		}
	}

	public void salvarControleDemipair(Vendas venda, Demipair demipair, Float valorPrevisto) {
		DemipairFacade demipairFacade = new DemipairFacade();
		Controledemipair controle = new Controledemipair();
		controle = demipairFacade.consultarControle(venda.getIdvendas());
		if (controle == null) {
			controle = new Controledemipair();

			controle.setStatusprocesso("Processo");
			controle.setVendas(demipair.getVendas());
			controle = demipairFacade.salvar(controle);
			//salvarInvoice(controle.getIdcontroledemipair(), venda);
		}
	}

	public void salvarControleVoluntariado(Vendas venda, Voluntariado voluntariado, Float valorPrevisto) {
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		Controlevoluntariado controle = new Controlevoluntariado();
		controle = voluntariadoFacade.consultarControle(venda.getIdvendas());
		if (controle == null) {
			controle = new Controlevoluntariado();
			controle.setStatusprocesso("Processo");
			controle.setVendas(voluntariado.getVendas());
			controle.setDataembarque(voluntariado.getDataInicioVoluntariado());
			controle.setAntecedentescriminais("Não");
			controle.setFichaoriginal("Não");
			controle.setContratooriginal("Não");
			controle.setInformativooriginal("Não");
			controle.setLoa("Não");
			controle.setBooking("Não");
			controle.setPagamento("Não");
			controle.setPassagem("Não");
			controle.setVacina("Não");
			controle.setVisto("Não");
			controle.setCartamotivacao("Não");
			controle.setCopiapttcolorida("Não");
			controle.setCurriculum("Não");
			controle = voluntariadoFacade.salvar(controle);
			//salvarInvoice(controle.getIdcontrolevoluntariado(), venda);
		}
	}

	public void salvarControleHighSchool(Vendas venda, Highschool highSchool, Float valorPrevisto) {
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		Controlehighschool controle = new Controlehighschool();
		controle = highSchoolFacade.consultarControle(venda.getIdvendas());
		if (controle == null) {
			controle = new Controlehighschool();
			controle.setVendas(venda);
			controle.setVisto("Não");
			controle.setFamilia("Não");
			controle.setSituacao("Processo");
			controle.setHighschool(highSchool);
			controle = highSchoolFacade.salvar(controle);
			//salvarInvoice(controle.getIdcontroleHighSchool(), venda);
		}
	}

	public void salvarControleProgramaTeens(Vendas venda, Programasteens programasteens, Float valorPrevisto) {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		Controleprogramasteen controle = new Controleprogramasteen();
		controle = programasTeensFacede.consultarControle(venda.getIdvendas());
		if (controle == null) {
			controle = new Controleprogramasteen();

			controle.setVendas(venda);
			controle.setVisto("Não");
			controle.setContrato("Não");
			controle.setDocumentacao("Não");
			controle.setFamilia("Não");
			controle.setBonus(" ");
			controle.setObservacoes(" ");
			controle.setFinalizado("Processo");
			controle.setSituacao("Processo");
			controle.setCidadeDestino(venda.getFornecedorcidade().getCidade().getNome());
			controle = programasTeensFacede.salvar(controle);
			//salvarInvoice(controle.getIdcontroleProgramasTeens(), venda);
		}
	}
}
