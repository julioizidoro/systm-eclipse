package br.com.travelmate.managerBean.cursospacotes;
 
import br.com.travelmate.dao.LeadDao;
import br.com.travelmate.dao.LeadHistoricoDao;
import br.com.travelmate.dao.LeadSituacaoDao;
import br.com.travelmate.dao.OCursoDao;
import br.com.travelmate.dao.OCursoDescontoDao;
import br.com.travelmate.dao.OCursoFormaPagamentoDao;
import br.com.travelmate.dao.OCursoProdutoDao;
import br.com.travelmate.dao.OcursoPacoteDao;
import br.com.travelmate.dao.OcursoSeguroViagemDao;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.CoeficienteJurosFacade;
import br.com.travelmate.facade.CursosPacotesFormaPagamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ConsultaOrcamentoMB;
import br.com.travelmate.managerBean.OrcamentoCurso.ProdutosOrcamentoBean;
import br.com.travelmate.managerBean.OrcamentoCurso.ResultadoOrcamentoBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.GerarOcamentoPDFBean;
import br.com.travelmate.managerBean.OrcamentoCurso.pdf.OrcamentoPDFFactory;
import br.com.travelmate.managerBean.voluntariadoprojeto.orcamento.GerarOrcamentoVoluntariadoPDFBean;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Coeficientejuros;
import br.com.travelmate.model.Cursopacoteformapagamento;
import br.com.travelmate.model.Cursospacote;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Orcamentoprojetovoluntariado;
import br.com.travelmate.model.Pacotesinicial;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class GerarOrcamentoPacoteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Cursospacote cursospacote;
	private List<Cliente> listaCliente;
	private Cliente cliente;
	private Date datainicio;
	private String nome;
	private Cursopacoteformapagamento formapagamento;
	private boolean formapagamento2;
	private boolean formapagamento3;
	private boolean formapagamento4;
	private boolean curso;
	private boolean voluntariado;
	private Date datanascimento;
	private boolean dataInicioExcedida;
	private String dataBrasil;
	private String moedaNacional;
	@Inject
	private OCursoDao oCursoDao;
	@Inject
	private OCursoProdutoDao oCursoProdutoDao;
	@Inject
	private LeadHistoricoDao leadHistoricoDao;
	@Inject
	private LeadDao leadDao;
	@Inject 
	private OCursoFormaPagamentoDao oCursoFormaPagamentoDao;
	@Inject
	private OCursoDescontoDao oCursoDescontoDao;
	@Inject
	private OcursoPacoteDao oCursoPacoteDao;
	@Inject
	private LeadSituacaoDao leadSituacaoDao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		cursospacote = (Cursospacote) session.getAttribute("cursospacote");
		session.removeAttribute("cursospacote");
		getAplicacaoMB();
		int produto = cursospacote.getProdutos().getIdprodutos();
		if(produto==aplicacaoMB.getParametrosprodutos().getCursos()) {
			curso = true;
		}else if(produto==aplicacaoMB.getParametrosprodutos().getVoluntariado()) {
			voluntariado=true;
		}
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
		calcularValorCambioAtual();
		consultarFormaPagamento(); 
	}

	public Cursospacote getCursospacote() {
		return cursospacote;
	}

	public void setCursospacote(Cursospacote cursospacote) {
		this.cursospacote = cursospacote;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}
 
	public Cursopacoteformapagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(Cursopacoteformapagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	public boolean isFormapagamento2() {
		return formapagamento2;
	}

	public void setFormapagamento2(boolean formapagamento2) {
		this.formapagamento2 = formapagamento2;
	}

	public boolean isFormapagamento3() {
		return formapagamento3;
	}

	public void setFormapagamento3(boolean formapagamento3) {
		this.formapagamento3 = formapagamento3;
	}

	public boolean isFormapagamento4() {
		return formapagamento4;
	}

	public void setFormapagamento4(boolean formapagamento4) {
		this.formapagamento4 = formapagamento4;
	}

	public boolean isCurso() {
		return curso;
	}

	public void setCurso(boolean curso) {
		this.curso = curso;
	}

	public boolean isVoluntariado() {
		return voluntariado;
	}

	public void setVoluntariado(boolean voluntariado) {
		this.voluntariado = voluntariado;
	}

	public Date getDatanascimento() {
		return datanascimento;
	}

	public void setDatanascimento(Date datanascimento) {
		this.datanascimento = datanascimento;
	}

	public boolean isDataInicioExcedida() {
		return dataInicioExcedida;
	}

	public void setDataInicioExcedida(boolean dataInicioExcedida) {
		this.dataInicioExcedida = dataInicioExcedida;
	}

	public String getDataBrasil() {
		return dataBrasil;
	}

	public void setDataBrasil(String dataBrasil) {
		this.dataBrasil = dataBrasil;
	}

	public String cancelar() { 
		return "paginainicial";
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public void gerarListaCliente() {
		ClienteFacade clienteFacade = new ClienteFacade();
		String sql = "select c from Cliente c where c.nome like '%" + nome + "%'"
			+ " and c.unidadenegocio.idunidadeNegocio="
			+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio()
			+ " order by c.nome";
		listaCliente = clienteFacade.listar(sql);
		if (listaCliente == null) {
			listaCliente = new ArrayList<Cliente>();
		}
	}

	public void selecionarCliente(Cliente cliente) {
		this.cliente = cliente;
		nome = cliente.getNome();
		datanascimento = cliente.getDataNascimento();
	} 

	public String salvar() throws IOException {
		if(validarDados()){
			SalvarOrcamentoOcurso salvarOrcamentoOcurso = new SalvarOrcamentoOcurso(cliente, datainicio, cursospacote, aplicacaoMB,usuarioLogadoMB,formapagamento, oCursoDao
					, oCursoProdutoDao, leadHistoricoDao, leadDao, oCursoFormaPagamentoDao, oCursoDescontoDao, oCursoPacoteDao, leadSituacaoDao);
			Ocurso ocurso = salvarOrcamentoOcurso.salvarOcurso();  
			ocurso = oCursoDao.consultar(ocurso.getIdocurso());
			GerarOcamentoPDFBean o = new GerarOcamentoPDFBean(ocurso, aplicacaoMB);
			OrcamentoPDFFactory.setLista(o.getLista());
	
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String caminhoRelatorio = "/reports/orcamentopdf/orcamentoPagina01.jasper"; 
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//orcamentopdf//"));
			File f = new File(servletContext.getRealPath("/reports/orcamentopdf/mascote.png"));
			BufferedImage mascote = null;
			try {
				mascote = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("mascote", mascote);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/pagina01.png"));
			BufferedImage pagina01 = null;
			try {
				pagina01 = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("pagina01", pagina01);
	
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/logocinza.png"));
			BufferedImage logocinza = null;
			try {
				logocinza = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("logocinza", logocinza);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/curso.png"));
			BufferedImage curso = null;
			try {
				curso = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("curso", curso);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/pais.png"));
			BufferedImage pais = null;
			try {
				pais = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("pais", pais);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/orcamento.png"));
			BufferedImage orcamento = null;
			try {
				orcamento = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("orcamento", orcamento);
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/icon.png"));
			BufferedImage icon = null;
			try {
				icon = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("icon", icon);
			//
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/logo.png"));
			BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("logo", logo);
			//
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/pagamento.png"));
			BufferedImage pagamento = null;
			try {
				pagamento = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("pagamento", pagamento);
			//
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/outroscustos.png"));
			BufferedImage adicionais = null;
			try {
				adicionais = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("adicionais", adicionais);
	
			f = new File(servletContext.getRealPath("/reports/orcamentopdf/observacoes.png"));
			BufferedImage obs = null;
			try {
				obs = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			parameters.put("obs", obs);
			parameters.put("nometipo", "Tipo de Curso");
	
			FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
			Ftpdados dadosFTP = null;
	
			try {
				dadosFTP = ftpDadosFacade.getFTPDados();
			} catch (SQLException ex) {
				Logger.getLogger(ConsultaOrcamentoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
			Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
			ftp.conectar();
			InputStream is = ftp.receberArquivo("",
					ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getIdpais() + ".png",
					"/systm/pais/");
			Image imgPais = null;
			try {
				imgPais = ImageIO.read(is);
				if (imgPais == null) {
					is = ftp.receberArquivo("", "0.png", "/systm/pais/");
					imgPais = ImageIO.read(is);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			ftp.desconectar();
			ftp.conectar();
			is = ftp.receberArquivo("",
					ocurso.getFornecedorcidadeidioma().getFornecedorcidade().getIdfornecedorcidade() + ".png",
					"/systm/fornecedorcidade/");
			Image imgCidade = null;
			try {
				imgCidade = ImageIO.read(is);
				if (imgCidade == null) {
					is = ftp.receberArquivo("", "0.png", "/systm/fornecedorcidade/");
					imgCidade = ImageIO.read(is);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			ftp.desconectar();
	
			parameters.put("pais", imgPais);
			parameters.put("fornecedorcidade", imgCidade); 
			parameters.put("lista", OrcamentoPDFFactory.getLista()); 
			JRDataSource jrds = new JRBeanCollectionDataSource(OrcamentoPDFFactory.getLista());
			GerarRelatorio gerarRelatorio = new GerarRelatorio();
			String nomeArquivo = "TM-" + String.valueOf(ocurso.getIdocurso()) + ".pdf";
			try { 
				gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeArquivo); 
			} catch (JRException e) { 
				e.printStackTrace();
			}
			Mensagem.lancarMensagemInfo("Gerado com sucesso!", "");
		}
		return "";
	} 
	
	
	public String editar(){
		if(validarDados()){
			EditarOrcamentoOcurso editarOrcamentoOcurso = new EditarOrcamentoOcurso(cliente, datainicio, cursospacote, aplicacaoMB,usuarioLogadoMB);
			ResultadoOrcamentoBean resultadoOrcamentoBean = new ResultadoOrcamentoBean();
			resultadoOrcamentoBean.setOcurso(editarOrcamentoOcurso.buscarOcurso());
			resultadoOrcamentoBean.getOcurso().setValorcambio(editarOrcamentoOcurso.getOcurso().getValorcambio());
			resultadoOrcamentoBean.setCambio(editarOrcamentoOcurso.getOcurso().getCambio());
			resultadoOrcamentoBean.setFornecedorcidadeidioma(editarOrcamentoOcurso.getOcurso().getFornecedorcidadeidioma());  
			resultadoOrcamentoBean.setDataConsulta(editarOrcamentoOcurso.getDataconsulta());  
			resultadoOrcamentoBean.setProdutoFornecedorBean(editarOrcamentoOcurso.retornarFornecedorProdutosBean());
			List<ProdutosOrcamentoBean> lista = editarOrcamentoOcurso.addTaxasAcomodacao();
			for (int i = 0; i < lista.size(); i++) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(lista.get(i));
			}  
			lista = editarOrcamentoOcurso.suplementoMenorIdadeAcomodacao();
			for (int i = 0; i < lista.size(); i++) {
				resultadoOrcamentoBean.getProdutoFornecedorBean().getListaObrigaroerios().add(lista.get(i));
			}    
			resultadoOrcamentoBean.setListaAcomodacoes(editarOrcamentoOcurso.gerarListaValorCoProdutos("Acomodacao"));
			resultadoOrcamentoBean.setListaAcOpcional(editarOrcamentoOcurso.gerarListaValorCoProdutos("AcOpcional"));
			resultadoOrcamentoBean.setListaOpcionais(editarOrcamentoOcurso.gerarListaValorCoProdutos("Opcional"));
			resultadoOrcamentoBean.setListaTransfer(editarOrcamentoOcurso.gerarListaValorCoProdutos("Transfer")); 
			resultadoOrcamentoBean.getOcurso().setOcursodescontoList(editarOrcamentoOcurso.addOcursoDesconto());
			if(cursospacote.getValorcoprodutos_acomodacao()!=null){
				if(resultadoOrcamentoBean.getListaAcomodacoes()!=null &&
						resultadoOrcamentoBean.getListaAcomodacoes().size()>0){
					for (int i = 0; i < resultadoOrcamentoBean.getListaAcomodacoes().size(); i++) {
						int id = resultadoOrcamentoBean.getListaAcomodacoes().get(i).getValorcoprodutos().getIdvalorcoprodutos();
						if (id==cursospacote.getValorcoprodutos_acomodacao().getIdvalorcoprodutos()) {
							resultadoOrcamentoBean.getListaAcomodacoes().get(i).setSelecionado(true);
							resultadoOrcamentoBean.getListaAcomodacoes().get(i).setNumeroSemanas(cursospacote.getNumerosemanaacomodacao());
							resultadoOrcamentoBean.getListaAcomodacoes().get(i).setValorOrigianl(cursospacote.getValortotalacomodacao());
							resultadoOrcamentoBean.getListaAcomodacoes().get(i).setValorOriginalRS(
									cursospacote.getValortotalacomodacao() * resultadoOrcamentoBean.getOcurso().getValorcambio());
						}
					}  
				}
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("resultadoOrcamentoBean", resultadoOrcamentoBean);
			session.setAttribute("voltar", "orcamento");
			RequestContext.getCurrentInstance().closeDialog(null);
		}
		return "";   
	}
	
	public boolean validarDados(){
		if(cliente==null || cliente.getIdcliente()==null){
			Mensagem.lancarMensagemErro("Atenção!", "Selecione um cliente.");
			return false;
		}else{
			if (cliente.getDataNascimento() == null) {
				String datanascimentoMaiorIdadeString = Formatacao.SubtarirDatas(new Date(), 6750, "dd/MM/yyyy");
				Date dataNasc = Formatacao.ConvercaoStringData(datanascimentoMaiorIdadeString);
				cliente.setDataNascimento(dataNasc);    
				ClienteFacade clienteFacade = new ClienteFacade();
				cliente = clienteFacade.salvar(cliente);
			}
		}
		if(datainicio==null){
			Mensagem.lancarMensagemErro("Atenção!", "Informe uma data de início.");
			return false;
		}else{
			if(datainicio.before(cursospacote.getDatainiciocurso())){
				Mensagem.lancarMensagemErro("Atenção!", "Data de início invalída para este pacote.");
				return false;
			}
		}
		return true;
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
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formapagamento.getNparcelasfinanciamento().intValue(), "Juros Banco");
				Double valor = formapagamento.getValorsaldofinanciamento().doubleValue() * cf.getCoeficiente();
				formapagamento.setValorparcelafinanciamento(valor.floatValue());
			} else {
				CoeficienteJurosFacade coneficienteJurosFacade = new CoeficienteJurosFacade();
				Coeficientejuros cf = coneficienteJurosFacade.consultar(formapagamento.getNparcelasfinanciamento().intValue(), "Juros Banco");
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
	
	public String saibaMais(){  
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cursospacote", cursospacote);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("informacoesCursosPacote", options, null);
		return ""; 
	}
	
	public void consultarFormaPagamento() {
		CursosPacotesFormaPagamentoFacade cursosPacotesFormaPagamentoFacade = new CursosPacotesFormaPagamentoFacade();
		formapagamento = cursosPacotesFormaPagamentoFacade.consultar(
			"SELECT c FROM Cursopacoteformapagamento c WHERE c.cursospacote.idcursospacote="+cursospacote.getIdcursospacote());
		if(formapagamento==null) {
			formapagamento = new Cursopacoteformapagamento();
		}else {
			if((formapagamento.getValorparcelaboleto()!=null && formapagamento.getValorparcelaboleto()>0) || 
					(formapagamento.getValorparcelacartao()!=null && formapagamento.getValorparcelacartao()>0)
					|| (formapagamento.getValorparcelafinanciamento()!=null && formapagamento.getValorparcelafinanciamento()>0)) {
				if(formapagamento.getValorparcelaboleto()!=null && formapagamento.getValorparcelaboleto()>0) {
					formapagamento2 = false;
				}else {
					formapagamento2 = true;
				}
				if(formapagamento.getValorparcelacartao()!=null && formapagamento.getValorparcelacartao()>0) {
					formapagamento3 = false;
				}else {
					formapagamento3 = true;
				}
				if(formapagamento.getValorparcelafinanciamento()!=null && formapagamento.getValorparcelafinanciamento()>0) {
					formapagamento4 = true;
				}else {
					formapagamento4 = true;
				}
			}
		}
	} 
	
	public void gerarOrcamentoPDFVoluntariado() throws IOException { 
		SalvarVoluntariadoProjeto salvarVoluntariadoProjeto = new SalvarVoluntariadoProjeto
				(cliente, datainicio, cursospacote.getVoluntariadopacoteList().get(0).getVoluntariadoprojetovalor(), 
						cursospacote, aplicacaoMB, usuarioLogadoMB, formapagamento);
		Orcamentoprojetovoluntariado orcamentoprojetovoluntariado = salvarVoluntariadoProjeto
				.gerarOrcamento();
		GerarOrcamentoVoluntariadoPDFBean o = new GerarOrcamentoVoluntariadoPDFBean(orcamentoprojetovoluntariado);
		OrcamentoPDFFactory.setLista(o.getLista());

		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/orcamentovoluntariadopdf/orcamentoPagina01.jasper";
		 
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//orcamentovoluntariadopdf//"));
		File f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/mascote.png"));
		BufferedImage mascote = null;
		try {
			mascote = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("mascote", mascote);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/pagina01.png"));
		BufferedImage pagina01 = null;
		try {
			pagina01 = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("pagina01", pagina01);

		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/logocinza.png"));
		BufferedImage logocinza = null;
		try {
			logocinza = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("logocinza", logocinza);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/curso.png"));
		BufferedImage curso = null;
		try {
			curso = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("curso", curso);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/pais.png"));
		BufferedImage pais = null;
		try {
			pais = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("pais", pais);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/orcamento.png"));
		BufferedImage orcamento = null;
		try {
			orcamento = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("orcamento", orcamento);
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/icon.png"));
		BufferedImage icon = null;
		try {
			icon = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("icon", icon);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/logo.png"));
		BufferedImage logo = null;
		try {
			logo = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("logo", logo);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/pagamento.png"));
		BufferedImage pagamento = null;
		try {
			pagamento = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("pagamento", pagamento);
		//
		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/outroscustos.png"));
		BufferedImage adicionais = null;
		try {
			adicionais = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("adicionais", adicionais);

		f = new File(servletContext.getRealPath("/reports/orcamentovoluntariadopdf/observacoes.png"));
		BufferedImage obs = null;
		try {
			obs = ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
		parameters.put("obs", obs);

		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;

		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(ConsultaOrcamentoMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		ftp.conectar();
		InputStream is = ftp.receberArquivo("",
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getCidade().getPais().getIdpais() + ".png",
				"/systm/pais/");
		Image imgPais = null;
		try {
			imgPais = ImageIO.read(is);
			if (imgPais == null) {
				is = ftp.receberArquivo("", "0.png", "/systm/pais/");
				imgPais = ImageIO.read(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftp.desconectar();
		ftp.conectar();
		is = ftp.receberArquivo("",
				orcamentoprojetovoluntariado.getVoluntariadoprojetovalor().getVoluntariadoprojeto().getFornecedorcidade().getIdfornecedorcidade() + ".png",
				"/systm/fornecedorcidade/");
		Image imgCidade = null;
		try {
			imgCidade = ImageIO.read(is);
			if (imgCidade == null) {
				is = ftp.receberArquivo("", "0.png", "/systm/fornecedorcidade/");
				imgCidade = ImageIO.read(is);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		ftp.desconectar();

		parameters.put("pais", imgPais);
		parameters.put("fornecedorcidade", imgCidade);
		parameters.put("nometipo", "Tipo de Projeto");
		parameters.put("lista", OrcamentoPDFFactory.getLista());

		JRDataSource jrds = new JRBeanCollectionDataSource(OrcamentoPDFFactory.getLista());
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		String nomeArquivo = "TM-" + String.valueOf("vol"+orcamentoprojetovoluntariado.getIdorcamentoprojetovoluntariado()) + ".pdf";
		try {
			gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, nomeArquivo);
		} catch (JRException e) { 
			e.printStackTrace();
		}
	}
	
	
	public void calcularValorCambioAtual() {
		float valorInicial = cursospacote.getValortotalpacote();
		if (cursospacote.getValorcambio()==0) {
			Cambio cambio = Formatacao.carregarCambioDia(aplicacaoMB.getListaCambio(), cursospacote.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais().getMoedas(),
					usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
			if (cambio!=null) {
				float valor = cursospacote.getValormoedaestrangeira() * cambio.getValor();
				valorInicial = valor;
			}
			
		}
		cursospacote.setValoravista(valorInicial);
	}
	
	
	public void verificarDataInicio() {
		if (datainicio.after(cursospacote.getDataterminocurso())) {
			dataInicioExcedida = true;
			dataBrasil = Formatacao.ConvercaoDataPadrao(cursospacote.getDataterminocurso());
		}else {
			dataInicioExcedida = false;
		}
	}
	
	
	public void fecharNotificacao() {
		dataInicioExcedida = false;
	}
	
}
