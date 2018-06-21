package br.com.travelmate.managerBean.crm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.FormaPagamentoFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.ParcelamentoPagamentoFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TraineeFacade;
import br.com.travelmate.facade.VistosFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Formapagamento;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Parcelamentopagamento;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Seguroviagem; 
import br.com.travelmate.model.Trainee; 
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

public class FuncoesFichasBean {

	private Curso curso;
	private Worktravel worktravel;
	private Voluntariado voluntariado;
	private Aupair aupair;
	private Trainee trainee;
	private Programasteens programateens;
	private Highschool highschool;
	private Vendas vendas;
	private Demipair demipair;
	private He he;
	private Seguroviagem seguroViagem;
	private Vistos vistos;
	
	public FuncoesFichasBean(Vendas vendas) { 
		this.vendas = vendas;
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
		} else if (vendas.getProdutos().getIdprodutos() == 4) {
			buscarHighSchool();
		} else if (vendas.getProdutos().getIdprodutos() == 5) {
			buscarProgramasTeens();
		} else if (vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
		} else if (vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
		} else if (vendas.getProdutos().getIdprodutos() == 16) {
			buscarVoluntariado();
		} else if (vendas.getProdutos().getIdprodutos() == 20) {
			buscarDemiPair();
		} else if (vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
		}
	} 
	
	public Curso buscarCurso() {
		CursoFacade cursoFacade = new CursoFacade();
		curso = cursoFacade.consultarCursos(vendas.getIdvendas());
		return curso;
	}

	public Aupair buscarAuPair() {
		AupairFacade aupairFacade = new AupairFacade();
		aupair = aupairFacade.consultar(vendas.getIdvendas());
		return aupair;
	}

	public Highschool buscarHighSchool() {
		HighSchoolFacade hiSchoolFacade = new HighSchoolFacade();
		highschool = hiSchoolFacade.consultarHighschool(vendas.getIdvendas());
		return highschool;
	}

	public Programasteens buscarProgramasTeens() {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		programateens = programasTeensFacede.find(vendas.getIdvendas());
		return programateens;
	}

	public Trainee buscarTrainee() {
		TraineeFacade traineeFacade = new TraineeFacade();
		trainee = traineeFacade.consultar(vendas.getIdvendas());
		return trainee;
	}

	public Voluntariado buscarVoluntariado() {
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		voluntariado = voluntariadoFacade.consultar(vendas.getIdvendas());
		return voluntariado;
	}

	public Demipair buscarDemiPair() {
		DemipairFacade demipairFacade = new DemipairFacade();
		demipair = demipairFacade.consultar(vendas.getIdvendas());
		return demipair;
	}

	public Worktravel buscarWorkTravel() {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		worktravel = workTravelFacade.consultarWork(vendas.getIdvendas());
		return worktravel;
	}

	public He buscarHe() {
		HeFacade heFacade = new HeFacade();
			he = heFacade.consultarVenda(vendas.getIdvendas());
		return he;   
	}     

	public Seguroviagem buscarSeguro() {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		seguroViagem = seguroViagemFacade.consultar(vendas.getIdvendas());
		return seguroViagem;
	}
	
	public Vistos buscarVistos(){
		VistosFacade vistosFacade = new VistosFacade();
		vistos = vistosFacade.consultarVistos(vendas.getIdvendas());
		return vistos;
	}
	
	public String gerarRelatorioContrato() throws SQLException, IOException {
		this.vendas = vendas;
		Map parameters = new HashMap();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		if (vendas.getProdutos().getIdprodutos() == 1) {
			buscarCurso();
			if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
				caminhoRelatorio = ("/reports/curso/contratoCursoPagina01.jasper");
			} else
				caminhoRelatorio = ("/reports/curso/contratoCurso2Pagina01.jasper");
			parameters.put("idvendas", curso.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));

		} else if (vendas.getProdutos().getIdprodutos() == 4) {
			caminhoRelatorio = "/reports/highSchool/contratoHighSchoolPagina01.jasper";
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));

		} else if (vendas.getProdutos().getIdprodutos() == 5) {
			caminhoRelatorio = ("/reports/cursosTeens/contratoTeensPagina01.jasper");
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));

		} else if (vendas.getProdutos().getIdprodutos() == 9) {
			buscarAuPair();
			if (!aupair.getPaisinteresse().equalsIgnoreCase("Caregiver")) {
				caminhoRelatorio = "/reports/aupair/contratoAupairPagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/aupair/contratoCaregiverPagina01.jasper";
			}
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));

		} else if (vendas.getProdutos().getIdprodutos() == 13) {
			buscarTrainee();
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				caminhoRelatorio = "/reports/trainee/contratoEstagioAustralia01.jasper";
			} else {
				caminhoRelatorio = "/reports/trainee/contratoTraineePagina01.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
			parameters.put("idvendas", vendas.getIdvendas());

		} else if (vendas.getProdutos().getIdprodutos() == 16) {
			caminhoRelatorio = "/reports/voluntariado/contratoVoluntariadoPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
			parameters.put("idvendas", vendas.getIdvendas());

		} else if (vendas.getProdutos().getIdprodutos() == 10) {
			buscarWorkTravel();
			if (worktravel.getTipo().equalsIgnoreCase("Premium")) {
				caminhoRelatorio = "/reports/worktravel/contratoWorkPremiumPagina01.jasper";
			} else if (worktravel.getTipo().equalsIgnoreCase("France")) {
				caminhoRelatorio = "/reports/worktravel/contratoWorkFrancePagina01.jasper";
			} else {
				caminhoRelatorio = "/reports/worktravel/contratoWorkIndependentPagina01.jasper";
			}
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));

		} else if (vendas.getProdutos().getIdprodutos() == 20) {
			caminhoRelatorio = "/reports/demipair/contratoDemipairPagina01.jasper";
			parameters.put("idvendas", vendas.getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));

		} else if (vendas.getProdutos().getIdprodutos() == 22) {
			caminhoRelatorio = "/reports/higherEducation/contratoHePagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
			parameters.put("idvendas", he.getVendas().getIdvendas());
		}else if(vendas.getProdutos().getIdprodutos() == 3){
			caminhoRelatorio = ("/reports/visto/contratovisto.jasper");
			parameters.put("idvendas", vistos.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//visto//"));
		}
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorioContrato = new GerarRelatorio();
		try {
			gerarRelatorioContrato.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"contratoCurso-" + vendas.getIdvendas() + ".pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioFicha() throws IOException {
		Map parameters = new HashMap();
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "";
		if (vendas.getProdutos().getIdprodutos() == 1) {
			if (curso.getHabilitarSegundoCurso().equalsIgnoreCase("N")) {
				if (curso.isDadospais()) {
					caminhoRelatorio = "/reports/curso/FichaCursoDadosPaisPagina01.jasper";
				} else {
					caminhoRelatorio = "/reports/curso/FichaCursoPagina01.jasper";
				}
			} else {
				if (curso.isDadospais()) {
					caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
				} else {
					caminhoRelatorio = "/reports/curso/FichaCurso2Pagina01.jasper";
				}
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//curso//"));
			parameters.put("idvendas", curso.getVendas().getIdvendas());
			parameters.put("sqlpagina2", gerarSqlSeguroViagems());
		} else if (vendas.getProdutos().getIdprodutos() == 4) {
			caminhoRelatorio = "/reports/highSchool/FichaHighSchoolPagina01.jasper";
			parameters.put("idvendas", highschool.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//highSchool//"));
		} else if (vendas.getProdutos().getIdprodutos() == 5) {
			caminhoRelatorio = "/reports/cursosTeens/FichaProgramasTeensPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//cursosTeens//"));
			parameters.put("idvendas", programateens.getVendas().getIdvendas());
		} else if (vendas.getProdutos().getIdprodutos() == 9) {
			caminhoRelatorio = "/reports/aupair/FichaAupairPagina01.jasper";
			parameters.put("idvendas", aupair.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//aupair//"));
		} else if (vendas.getProdutos().getIdprodutos() == 13) {
			if (trainee.getTipotrainee().equalsIgnoreCase("Australia")) {
				caminhoRelatorio = "/reports/trainee/FichaEstagioAustralia01.jasper";
			} else {
				caminhoRelatorio = "/reports/trainee/FichaTraineePagina01.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//trainee//"));
			parameters.put("idvendas", trainee.getVendas().getIdvendas());
		} else if (vendas.getProdutos().getIdprodutos() == 16) {
			caminhoRelatorio = "/reports/voluntariado/FichaVoluntariadoPagina01.jasper";
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//voluntariado//"));
			parameters.put("idvendas", voluntariado.getVendas().getIdvendas());
			parameters.put("sqlpagina2", gerarSqlPagina1(voluntariado));
		} else if (vendas.getProdutos().getIdprodutos() == 10) {
			caminhoRelatorio = "/reports/worktravel/FichaWorkPagina01.jasper";
			parameters.put("idvendas", worktravel.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//worktravel//"));
		} else if (vendas.getProdutos().getIdprodutos() == 20) {
			caminhoRelatorio = "/reports/demipair/FichaDemipairPagina01.jasper";
			parameters.put("idvendas", demipair.getVendas().getIdvendas());
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//demipair//"));
		} else if (vendas.getProdutos().getIdprodutos() == 22) {
			if (he.isFichafinal()) {
				caminhoRelatorio = "/reports/higherEducation/FichaFinalHe1.jasper";
			} else {
				caminhoRelatorio = "/reports/higherEducation/FichaInscricaoHe1.jasper";
			}
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
			parameters.put("idvendas", he.getVendas().getIdvendas());
		} else if (vendas.getProdutos().getIdprodutos() == 2) {
			if (seguroViagem.getIdvendacurso() > 0) {
				caminhoRelatorio = ("/reports/seguro/FichaSeguroCursoPagina01.jasper");
			} else {
				caminhoRelatorio = ("/reports/seguro/FichaSeguroPagina01.jasper");
			}
			if (seguroViagem.getIdvendacurso() > 0) {
				CursoFacade cursoFacade = new CursoFacade();
				Curso curso = cursoFacade.consultarCursos(seguroViagem.getIdvendacurso());
				if (curso != null) {
					seguroViagem.setNomeContatoEmergencia(curso.getNomeContatoEmergencia());
					seguroViagem.setFoneContatoEmergencia(curso.getFoneContatoEmergencia());
					seguroViagem.setPaisDestino(curso.getPais());
					SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
					seguroViagem = seguroViagemFacade.salvar(seguroViagem);
				}
			}
			parameters.put("idvendas", seguroViagem.getVendas().getIdvendas());
		}else if(vendas.getProdutos().getIdprodutos() == 3){
			caminhoRelatorio = "/reports/visto/FichaOrcamentoVistoPagina01.jasper";
			parameters.put("idvisto", vistos.getIdvistos());
		}
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"ficha-" + vendas.getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	public String gerarSqlPagina1(Voluntariado voluntariado) {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(voluntariado.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(voluntariado.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct " + "vendas.dataVenda, vendas.valor as valorVenda,vendas.idvendas,"
				+ "voluntariado.idvoluntariado,voluntariado.idiomaEstudar, voluntariado.nivelIdiomaEstudar,"
				+ "voluntariado.nomeContatoEmergencia,voluntariado.foneContatoEmergencia, voluntariado.emailContatoEmergencia,"
				+ "voluntariado.relacaoContatoEmergencia,voluntariado.instituicaoEstuda,voluntariado.cursoEstuda,"
				+ "voluntariado.duracaoCursoEstuda,voluntariado.periodoCursoEstuda,voluntariado.dataMatriculaCursoEstuda,voluntariado.dataEstimadaTerminoCursoEstuda,"
				+ "voluntariado.profissao,voluntariado.cargo,voluntariado.descricaoCargo,voluntariado.outrasHabilidade,"
				+ "voluntariado.curso,voluntariado.aulasporSemana,voluntariado.numeroSemanas,voluntariado.dataInicio,"
				+ "voluntariado.dataTermino,voluntariado.tipoAcomodacao,voluntariado.numeroSemanasAcomodacao,voluntariado.tipoQuarto,"
				+ "voluntariado.refeicoes,voluntariado.adicionais,voluntariado.dataChegadaAcomodacao,voluntariado.dataPartidaAcomodacao,"
				+ "voluntariado.familiaCrianca,voluntariado.familiaAnimais,voluntariado.fumante,voluntariado.vegetariano,"
				+ "voluntariado.hobbies,voluntariado.possuiAlergia,voluntariado.quaisAlergias,voluntariado.solicitacoesEspeciais,voluntariado.transferin,"
				+ "voluntariado.transferout,voluntariado.dataChegadaTransfer,"
				+ "voluntariado.voo,voluntariado.ciaerea,voluntariado.horario,voluntariado.projetoVoluntariado,"
				+ "voluntariado.dataInicioVoluntariado,voluntariado.dataTerminoVoluntariado,voluntariado.experienciaVoluntariado,voluntariado.motivoVoluntariado,"
				+ "voluntariado.deficienciaFisica,voluntariado.possuiProblemaSaude,voluntariado.descricaoProblemaSaude,voluntariado.tratamentoMedico,"
				+ "voluntariado.descricaoMedico,voluntariado.tratamentoDrogas,voluntariado.descricaoDrogas,"
				+ "voluntariado.fezCirurgia,voluntariado.descricaoCirurgia,voluntariado.dietaEspecifica,voluntariado.cartaoVTM,"
				+ "voluntariado.numerocartaoVTM,voluntariado.meodaCartaoVTM,voluntariado.seguroViagem,voluntariado.seguradora,voluntariado.planoSeguro,"
				+ "voluntariado.dataInicioSeguro,voluntariado.dataTerminoSeguro,voluntariado.numeroSemanasSeguro,voluntariado.passagemAerea,"
				+ "voluntariado.observacaoPassagem,voluntariado.vistoConsular,voluntariado.observacaoVistoConsultar,voluntariado.vendas_idvendas,"
				+ "voluntariado.dataEntregaDocumentoVisto,voluntariado.nivelFitness,voluntariado.controle, "
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "cliente.nome, cliente.dataNascimento, cliente.paisnascimento, cliente.cidadenascimento, cliente.rg,"
				+ "cliente.sexo, cliente.numeropassaporte, cliente.dataExpedicaoPassaporte,"
				+ "cliente.validadePassaporte, cliente.tipologradouro as clientetipologradouro, cliente.logradouro as clientelogradouro, cliente.numero as clientenumero,"
				+ "cliente.bairro as clientebairro, cliente.cidade as clientecidade, cliente.estado as clienteestado, cliente.cep as clientecep, cliente.cpf as clientecpf, cliente.pais as clientepais, cliente.foneresidencial,"
				+ "cliente.fonecelular, cliente.fonecomercial, cliente.profissao,"
				+ "cliente.email as emailcliente,cliente.escolaridade, cliente.nomePai, cliente.profissaopai, cliente.fonepai, cliente.nomemae,"
				+ "cliente.profissaomae, cliente.fonemae,"
				+ "usuario.nome as nomeUsuario, usuario.email as usuarioemail,"
				+ "unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, formapagamento.condicao, formapagamento.valortotal,"
				+ "formapagamento.valorjuros, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento, moedas.descricao as descricaoMoedas, cambio.idcambio, moedas.sigla,parcelamentopagamento.idparcemlamentoPagamento,"
				+ "parcelamentopagamento.formaPagamento," + "parcelamentopagamento.tipoParcelmaneto,"
				+ "parcelamentopagamento.valorParcelamento," + "parcelamentopagamento.diaVencimento,"
				+ "parcelamentopagamento.numeroParcelas," + "parcelamentopagamento.valorParcela,"
				+ "parcelamentopagamento.formaPagamento_idformaPagamento,orcamentoprodutosorcamento.idorcamentoprodutosorcamento,"
				+ "fornecedor.idfornecedor,seguroviagem.idseguroViagem,seguroviagem.seguradora,"
				+ "seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,"
				+ "seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,"
				+ "seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor, "
				+ "fornecedor.nome as nomeFornecedor, cidade.nome as nomeCidade, pais.nome nomePais" + " from "
				+ "  vendas join voluntariado on vendas.idvendas = voluntariado.vendas_idvendas"
				+ "  join usuario on vendas.usuario_idusuario = usuario.idusuario"
				+ "  join cliente on vendas.cliente_idcliente = cliente.idcliente"
				+ "  join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio"
				+ " join orcamento on vendas.idvendas = orcamento.vendas_idvendas"
				+ "  join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento"
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento"
				+ " join formaPagamento on vendas.idvendas = formapagamento.vendas_idvendas"
				+ " join cambio on orcamento.cambio_idcambio = cambio.idcambio"
				+ "  join moedas on cambio.moedas_idmoedas = moedas.idmoedas"
				+ " join parcelamentopagamento on formapagamento.idformapagamento = parcelamentopagamento.formapagamento_idformaPagamento"
				+ " join fornecedorCidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
				+ "   join fornecedor on fornecedorcidade.fornecedor_idfornecedor = fornecedor.idfornecedor"
				+ "  join cidade on fornecedorcidade.cidade_idcidade = cidade.idcidade"
				+ "  join pais on cidade.pais_idpais = pais.idpais";
		sql = sql + sqlseguro;
		sql = sql + " where " + "vendas.idvendas =" + voluntariado.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento";
		return sql;
	}
	
	public String gerarSqlSeguroViagems() {
		SeguroViagemFacade seguroViagemController = new SeguroViagemFacade();
		Seguroviagem seguro = seguroViagemController.consultarSeguroCurso(curso.getVendas().getIdvendas());
		String sqlseguro = "";
		if (seguro == null) {
			seguro = seguroViagemController.consultar(curso.getVendas().getIdvendas());
			sqlseguro = " join seguroviagem on seguroviagem.vendas_idvendas = vendas.idvendas ";
		} else {
			sqlseguro = " join seguroviagem on seguroviagem.idvendacurso = vendas.idvendas";
		}
		String sql = "Select distinct vendas.dataVenda, vendas.valor as valorVenda, cursos.nomeCurso, cursos.escola,"
				+ "cursos.cidade, cursos.pais, cursos.aulassemana, cursos.numerosenamas, cursos.dataInicio, "
				+ "cursos.dataTermino, cursos.tipoAcomodacao, cursos.numeroSemanasAcamodacao, cursos.tipoquarto,"
				+ "cursos.refeicoes, cursos.adicionais, cursos.datachegada, cursos.dataSaida, cursos.familiacomcrianca,"
				+ "cursos.familiacomanimais, cursos.fumante, cursos.vegetariano, cursos.hobbies, cursos.possuiAlergia,"
				+ "cursos.quaisalergias, cursos.solicitacoesespeciais, cursos.caratovtm, cursos.numerocartaovtm,"
				+ "cursos.moedacartaovtm, cursos.transferin, cursos.transferouto, cursos.passagemaerea, cursos.observacaopassagemaerea,"
				+ "cursos.vistoconsular, cursos.dataEntregadocumentosvistos, cursos.observacaovisto, cursos.nomecontatoemergencia,"
				+ "cursos.fonecontatoemergencia, cursos.emalcontatoemergencia, cursos.relacaocontatoemergencia, cursos.datainscricao as dataInscricaCurso, cursos.idioma, cursos.nivelIdioma, cursos.possuiSeguroGovernamental, cursos.numeroMeses as numeromesesgovernamental, cursos.seguradoraGovernamental,"
				+ "unidadeNegocio.razaoSocial, unidadeNegocio.nomeFantasia, unidadeNegocio.tipologradouro as tipologradourounidadeNegocio, unidadeNegocio.logradouro as logradourounidadeNegocio, unidadeNegocio.numero as nuemrounidadeNegocio, unidadeNegocio.complemento as complementounidadeNegocio, unidadeNegocio.bairro as bairrounidadeNegocio, unidadeNegocio.cidade as cidadeunidadeNegocio, unidadeNegocio.estado as estadounidadeNegocio, unidadeNegocio.cep as cepunidadeNegocio, unidadeNegocio.cnpj as cnpjunidadeNegocio,"
				+ "usuario.nome as nomeUsuario,unidadeNegocio.nomeFantasia, orcamento.dataCambio, orcamento.valorCambio, orcamento.totalMoedaEstrangeira,"
				+ "orcamento.totalmoedanacional, orcamento.TaxaTm, orcamentoprodutosorcamento.valormoedaestrangeira, orcamentoprodutosorcamento.valormoedanacional,"
				+ "produtosorcamento.descricao as descricaoprodutosOrcamento,seguroviagem.idseguroViagem,seguroviagem.seguradora,seguroviagem.plano,seguroviagem.dataInicio as dataInicioSeguro,"
				+ "seguroviagem.dataTermino dataTerminoSeguro,seguroviagem.numeroSemanas as numeroSemanasSeguro,seguroviagem.valorSeguro,seguroviagem.possuiSeguro,"
				+ "seguroviagem.nomeContatoEmergencia,seguroviagem.paisDestino,seguroviagem.foneContatoEmergencia,seguroviagem.vendas_idvendas,seguroviagem.fornecedor_idfornecedor,orcamentoprodutosorcamento.idorcamentoprodutosorcamento"
				+ " from " + "vendas join cursos on vendas.idvendas = cursos.vendas_idvendas "
				+ "join usuario on vendas.usuario_idusuario = usuario.idusuario "
				+ "join unidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idunidadeNegocio "
				+ "join orcamento on vendas.idvendas = orcamento.vendas_idvendas "
				+ "join orcamentoprodutosorcamento on orcamento.idorcamento = orcamentoprodutosorcamento.orcamento_idorcamento "
				+ " join produtosorcamento on orcamentoprodutosorcamento.produtosorcamento_idprodutosorcamento = produtosorcamento.idprodutosorcamento ";
		sql = sql + sqlseguro;
		sql = sql + " where " + " vendas.idvendas = " + curso.getVendas().getIdvendas()
				+ " order by orcamentoprodutosorcamento.idorcamentoprodutosorcamento ";
		return sql;

	}

	public String gerarRelatorioRecibo() throws SQLException, IOException {
		this.vendas = vendas;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		float valorRecibo = 0.0f;
		String caminhoRelatorio = ("/reports/recibo/reciboPagamento.jasper");
		FormaPagamentoFacade FormaPagamentoFacade = new FormaPagamentoFacade();
		Formapagamento forma = FormaPagamentoFacade.consultar(vendas.getIdvendas());
		ParcelamentoPagamentoFacade parcelamentoPagamentoFacade = new ParcelamentoPagamentoFacade();
		List<Parcelamentopagamento> listaParcelamento = parcelamentoPagamentoFacade.listar(forma.getIdformaPagamento());
		if (listaParcelamento != null) {
			for (int i = 0; i < listaParcelamento.size(); i++) {
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Dinheiro")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("cheque")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("depósito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
				if (listaParcelamento.get(i).getFormaPagamento().equalsIgnoreCase("Cartão de crédito")) {
					valorRecibo = valorRecibo + listaParcelamento.get(i).getValorParcelamento();
				}
			}
		}
		if (valorRecibo > 0.0f) {
			Map parameters = new HashMap();
			parameters.put("idvendas", vendas.getIdvendas());
			String valorExtenso = Formatacao.valorPorExtenso(valorRecibo);
			parameters.put("valorExtenso", valorExtenso);
			parameters.put("valorRecibo", valorRecibo);
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
			try {
				gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "reciboPagamento.pdf", null);
			} catch (JRException ex1) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
			} catch (IOException ex) {
				Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			FacesMessage msg = new FacesMessage("Sem recibo para imprimir.", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Sem recibo para imprimir.");
		}
		return "";
	}
	
	public String gerarRelatorioTermoVisto(UsuarioLogadoMB usuarioLogadoMB) throws SQLException, IOException {
		this.vendas = vendas;
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/curso/termoCiencia.jasper");
		Map parameters = new HashMap();
		parameters.put("idcliente", vendas.getCliente().getIdcliente());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		parameters.put("cidade", usuarioLogadoMB.getUsuario().getUnidadenegocio().getCidade());
		GerarRelatorio gerarRelatorioTermo = new GerarRelatorio();
		try {
			gerarRelatorioTermo.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "TermoVisto.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	
	public String boletos() {
		this.vendas = vendas;
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		String sql = "SELECT r FROM Contasreceber r WHERE r.vendas.idvendas=" + vendas.getIdvendas()
				+ " AND r.tipodocumento='Boleto' AND r.situacao<>'cc' AND r.valorpago=0"
				+ " AND r.datapagamento is null ORDER BY r.idcontasreceber";
		List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
		if (listaContas != null) {
			if (listaContas.size() > 0) {
				GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
				gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(vendas.getIdvendas()));
			} else {
				FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
				relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
			}
		} else {
			FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
		}

		return "";
	}
}
