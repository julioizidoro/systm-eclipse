package br.com.travelmate.managerBean.arquivo;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.bean.ContasReceberBean;
import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.dao.LeadPosVendaDao;
import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.AcomodacaoCursoFacade;
import br.com.travelmate.facade.ArquivosFacade;
import br.com.travelmate.facade.AupairFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.InvoiceFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.RegraVendaFacade;
import br.com.travelmate.facade.SeguroViagemFacade;
import br.com.travelmate.facade.TipoArquivoProdutoFacade;
import br.com.travelmate.facade.TraineeFacade;
import br.com.travelmate.facade.UsuarioDepartamentoUnidadeFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.UsuarioPontosFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.facade.WorkTravelFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.MateRunnersMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.aupair.FinalizarMB;
import br.com.travelmate.model.Acomodacaocurso;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Controlevoluntariado;
import br.com.travelmate.model.Controlework;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Invoice;
import br.com.travelmate.model.Leadposvenda;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Regravenda;
import br.com.travelmate.model.Seguroviagem;
import br.com.travelmate.model.Tipoarquivoproduto;
import br.com.travelmate.model.Trainee;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Usuariodepartamentounidade;
import br.com.travelmate.model.Usuariopontos;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.model.Worktravel;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;


@Named
@ViewScoped
public class CadControleArquivosMB implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LeadPosVendaDao leadPosVendaDao;
	@Inject
	private AvisosDao avisosDao;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private MateRunnersMB metaRunnersMB;
	private Arquivos arquivos;
	private Tipoarquivoproduto tipoarquivo;
	private List<Tipoarquivoproduto> listaTipoArquivo;
	private Vendas vendas;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private List<Arquivos> listaArquivos;
	private boolean camposbilhete=false;
	private Date dataembarque;
	private Date datachegadabrasil;
	private boolean arquivoEnviado = false;
	private FinalizarMB finalizar;
	private Cliente cliente;

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		vendas = (Vendas) session.getAttribute("vendas");
		listaArquivos = (List<Arquivos>) session.getAttribute("listaArquivos");
		cliente = (Cliente) session.getAttribute("cliente");
		arquivos = (Arquivos) session.getAttribute("arquivos");
		session.removeAttribute("vendas");
		session.removeAttribute("cliente");
		session.removeAttribute("listaArquivos");
		session.removeAttribute("arquivos");
		tipoarquivo = buscarTipoArquivo();
		if (arquivos == null) {
			arquivos = new Arquivos();
			listaNomeArquivo = new ArrayList<String>();
		}
		if (listaArquivos == null) {
			listaArquivos = new ArrayList<Arquivos>();
		} 
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}

	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Arquivos getArquivos() {
		return arquivos;
	}

	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	}

	public Tipoarquivoproduto getTipoarquivo() {
		return tipoarquivo;
	}

	public void setTipoarquivo(Tipoarquivoproduto tipoarquivo) {
		this.tipoarquivo = tipoarquivo;
	}

	public List<Tipoarquivoproduto> getListaTipoArquivo() {
		return listaTipoArquivo;
	}

	public void setListaTipoArquivo(List<Tipoarquivoproduto> listaTipoArquivo) {
		this.listaTipoArquivo = listaTipoArquivo;
	}

	public Vendas getVendas() {
		return vendas;
	}

	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}

	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
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

	public List<Arquivos> getListaArquivos() {
		return listaArquivos;
	}

	public void setListaArquivos(List<Arquivos> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}

	public List<UploadedFile> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<UploadedFile> listaFile) {
		this.listaFile = listaFile;
	}

	public MateRunnersMB getMetaRunnersMB() {
		return metaRunnersMB;
	}

	public void setMetaRunnersMB(MateRunnersMB metaRunnersMB) {
		this.metaRunnersMB = metaRunnersMB;
	}

	public boolean isCamposbilhete() {
		return camposbilhete;
	}

	public void setCamposbilhete(boolean camposbilhete) {
		this.camposbilhete = camposbilhete;
	}

	public Date getDataembarque() {
		return dataembarque;
	}

	public void setDataembarque(Date dataembarque) {
		this.dataembarque = dataembarque;
	}

	public Date getDatachegadabrasil() {
		return datachegadabrasil;
	}

	public void setDatachegadabrasil(Date datachegadabrasil) {
		this.datachegadabrasil = datachegadabrasil;
	}

	public boolean isArquivoEnviado() {
		return arquivoEnviado;
	}

	public void setArquivoEnviado(boolean arquivoEnviado) {
		this.arquivoEnviado = arquivoEnviado;
	}

	public FinalizarMB getFinalizar() {
		return finalizar;
	}

	public void setFinalizar(FinalizarMB finalizar) {
		this.finalizar = finalizar;
	}

	public void gerarListaTipoArquivo() {
		TipoArquivoProdutoFacade tipoArquivoFacade = new TipoArquivoProdutoFacade();
		try {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				listaTipoArquivo = tipoArquivoFacade.listar("Select t from Tipoarquivoproduto t"
						+ " where t.produtos.idprodutos="+vendas.getProdutos().getIdprodutos());
			} else
				listaTipoArquivo = tipoArquivoFacade
						.listar("Select t from Tipoarquivoproduto t where t.tipoarquivo.unidade='Sim'"
								+ " and t.produtos.idprodutos="+vendas.getProdutos().getIdprodutos()); 
			if (listaTipoArquivo == null) {
				listaTipoArquivo = new ArrayList<Tipoarquivoproduto>();
			}
		} catch (SQLException e) {  
			  
		}
	}

	public String salvar() {
		String obs = "";
		int idproduto = vendas.getProdutos().getIdprodutos();
		if (arquivos != null) {
			if (arquivos.getObservacao() != null) {
				try {
					obs = new String(arquivos.getObservacao().getBytes(Charset.defaultCharset()), "UTF-8");
				} catch (UnsupportedEncodingException e) { 
					  
				}
			}
		}
		if (validacaoDados()) {
			for (int i = 0; i < listaNomeArquivo.size(); i++) {
				String nomeArquivo = nomeArquivoSalvo();
				ArquivosFacade arquivosFacade = new ArquivosFacade();
				arquivos.setTipoarquivo(tipoarquivo.getTipoarquivo());
				arquivos.setUsuario(usuarioLogadoMB.getUsuario());
				arquivos.setDataInclusao(new Date());
				if (vendas.getProdutos().getIdprodutos()==22) {
					arquivos.setCliente(cliente);
				}else arquivos.setCliente(vendas.getCliente());
				arquivos.setVendas(vendas);
				arquivos.setNomesalvos(nomeArquivo + "_" + listaNomeArquivo.get(i));
				arquivos.setNomeArquivo(listaNomeArquivo.get(i));
				arquivos.setObservacao(obs);
				arquivos = arquivosFacade.salvar(arquivos);
				listaArquivos.add(arquivos);
				if (cliente == null) {
					cliente = vendas.getCliente();
				}
				if (vendas.getSituacao().equalsIgnoreCase("ANDAMENTO")) {
					if (aplicacaoMB.getParametrosprodutos().getCursos() != idproduto) {
						if (arquivos.getTipoarquivo().getUnidade().equalsIgnoreCase("Sim")) { 
							
							Avisos avisos = new Avisos();
							avisos.setData(new Date());
							avisos.setUsuario(usuarioLogadoMB.getUsuario());
							avisos.setImagem("Upload");
							avisos.setIdvenda(vendas.getIdvendas());
							avisos.setLiberar(true);
							avisos.setTexto("Upload " + arquivos.getTipoarquivo().getDescricao() + " "
									+ cliente.getNome() + " - " + vendas.getProdutos().getDescricao() +
									" | " + obs);
							avisos.setIdunidade(0);
							avisos = avisosDao.salvar(avisos);
							salvarAvisoUsuario(avisos);
						}
					}
				} else if (vendas.getSituacao().equalsIgnoreCase("FINALIZADA")) {
						if (arquivos.getTipoarquivo().getUnidade().equalsIgnoreCase("Sim")) { 
							
							Avisos avisos = new Avisos();
							avisos.setData(new Date());
							avisos.setUsuario(usuarioLogadoMB.getUsuario());
							avisos.setImagem("Upload");
							avisos.setLiberar(true);
							avisos.setIdvenda(vendas.getIdvendas());
							avisos.setTexto("Upload " + arquivos.getTipoarquivo().getDescricao() + " "
									+ cliente.getNome() + " - " + vendas.getProdutos().getDescricao() +
									" | " + obs);
							avisos.setIdunidade(0);
							avisos = avisosDao.salvar(avisos);
							salvarAvisoUsuario(avisos);
						}
				}
				if(vendas.getUnidadenegocio().getIdunidadeNegocio() == 2){
					if (usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList() != null) {
						if (arquivos.getTipoarquivo().getUnidade().equalsIgnoreCase("Sim")) { 
							
							Avisos avisos = new Avisos();
							avisos.setData(new Date());
							avisos.setUsuario(usuarioLogadoMB.getUsuario());
							avisos.setImagem("Upload");
							avisos.setLiberar(true);
							avisos.setIdvenda(vendas.getIdvendas());
							avisos.setTexto("Upload " + arquivos.getTipoarquivo().getDescricao() + " "
									+ cliente.getNome() + " - " + vendas.getProdutos().getDescricao() +
									" | " + obs);
							avisos.setIdunidade(0);
							avisos = avisosDao.salvar(avisos);
							salvarAvisoUsuarioVinculado(avisos);
						}
					}
				}
			}
			if (vendas.getSituacao().equalsIgnoreCase("PROCESSO")) {
				verificarDocumentosCursos();
				if (aplicacaoMB.getParametrosprodutos().getCursos() == idproduto) {
					if (validarBilheteAereo()) {
						validarSeguroViagem();
					}
				}
			}
			if ((tipoarquivo.getTipoarquivo().getIdtipoArquivo() == 58)
					|| (tipoarquivo.getTipoarquivo().getIdtipoArquivo() == 59)) {
				dataRecebimentoInvoice();
			}
			
			
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("cliente", cliente);
			session.setAttribute("msgBilhete", "");
			session.setAttribute("listaArquivos", listaArquivos);
			session.setAttribute("vendas", vendas);
			RequestContext.getCurrentInstance().closeDialog(arquivos);
		}else {
			TipoArquivoProdutoFacade tipoArquivoProdutoFacade = new TipoArquivoProdutoFacade();
			tipoarquivo = new Tipoarquivoproduto();
			try {
				tipoarquivo = tipoArquivoProdutoFacade.consultar(1);
			} catch (SQLException e) {
				  
			}
		}
		return "";
	}

	public String cancelar() {
		// para preencher a combobox tipo arquivo para não de erro de null
		TipoArquivoProdutoFacade tipoArquivoProdutoFacade = new TipoArquivoProdutoFacade();
		tipoarquivo = new Tipoarquivoproduto();
		try {
			tipoarquivo = tipoArquivoProdutoFacade.consultar(1);
		} catch (SQLException e) {
			  
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("cliente", cliente);
		RequestContext.getCurrentInstance().closeDialog(new Arquivos());
		return "";
	}

	// Salvar Nome para o ftp
	public String nomeArquivo() {
		if (vendas.getProdutos().getIdprodutos()==22) {
			nomeArquivoFTP = vendas.getIdvendas() + "_" +cliente.getIdcliente() ;
		}else {
			nomeArquivoFTP = vendas.getIdvendas() + "_" + vendas.getCliente().getIdcliente() ;
		}
		
		return nomeArquivoFTP;
	}

	// Salvar nome do arquivo para tabela arquivos
	public String nomeArquivoSalvo() {
		if (vendas.getProdutos().getIdprodutos()==22) {
			nomeArquivoFTP = vendas.getIdvendas() + "_" + cliente.getIdcliente();
		}else {
			nomeArquivoFTP = vendas.getIdvendas() + "_" + vendas.getCliente().getIdcliente();
		}
		
		return nomeArquivoFTP;
	}

	public void fileUploadListener(FileUploadEvent e) {
		this.file = e.getFile();
		salvarArquivoFTP();
		if (arquivoEnviado) {
			String nome = e.getFile().getFileName();
			try {
				nome = new String(nome.getBytes(Charset.defaultCharset()), "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			if (listaNomeArquivo == null) {
				listaNomeArquivo = new ArrayList<String>();
			}
			listaNomeArquivo.add(nome);
		}
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHostupload(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}    
		try {
			nomeArquivoFTP = nomeArquivoSalvo();
			arquivoEnviado = ftp.enviarArquivoDOCS(file, nomeArquivoFTP, "/systm/arquivos");
			if (arquivoEnviado) {
				msg = "Arquivo: " + nomeArquivoFTP + " enviado com sucesso";
			}else{
				msg = " Erro no nome do arquivo";
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(CadArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public void verificarDocumentosCursos() {
		boolean ficha = false;
		boolean contrato = false;
		boolean rg = false;
		boolean documentoComFoto = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 13) {
				rg = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 1) {
				documentoComFoto = true;
			}
		}
		if (((ficha) && (contrato) && (documentoComFoto)) || ((ficha) && (contrato) && (rg))) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
				vendas.setDataprocesso(new Date());
			}
			int idProduto = vendas.getProdutos().getIdprodutos();
			if (idProduto == 9) {
				finalizarAupair();
			}else if(idProduto == 13) {
				finalizarTrainee();
			}else if(idProduto == 10) {
				finalizarWork();
			}else if(idProduto == 16) {
				finalizarVoluntariado();
			}else if(idProduto == 20) {
				finalizarDemiPair();
			}else if(idProduto == 4) {
				finalizarHighSchool();
			}else if(idProduto == 5) {
				finalizarTeens();
			}else if(idProduto == 1) {
				finalizarCurso();
			}
			vendas.setSituacaogerencia("F");
			if (vendas.getPontoescola() == 0) {
				if(vendas.getPonto()>0 && vendas.getIdregravenda()>0){
					int numerodias = 0;
					if (vendas.getDataprocesso() != null) {
						numerodias = Formatacao.subtrairDatas(vendas.getDataVenda(), vendas.getDataprocesso());
					}
					RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
					Regravenda regravenda = regraVendaFacade.consultar("select r from Regravenda r where r.idregravenda="+vendas.getIdregravenda());
					UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
					int ano = Formatacao.getAnoData(vendas.getDataVenda());
					int mes = Formatacao.getMesData(vendas.getDataVenda()) + 1;
					String sql = "SELECT u FROM Usuariopontos u where u.usuario.idusuario=" + vendas.getUsuario().getIdusuario() + " and u.mes="
							+ mes + " and u.ano=" + ano;
					Usuariopontos usuariopontos = usuarioPontosFacade.consultar(sql);
					if(numerodias<aplicacaoMB.getParametrosprodutos().getRegracursofinalizar()){
						vendas.setPontoextra(regravenda.getPontomais());
						usuariopontos.setPontos(usuariopontos.getPontos()+regravenda.getPontomais());
					}else if(numerodias>=aplicacaoMB.getParametrosprodutos().getRegracursofinalizar()){
						vendas.setPontoextra(regravenda.getPontomenos());
						usuariopontos.setPontos(usuariopontos.getPontos()-regravenda.getPontomenos());
					}
					usuariopontos = usuarioPontosFacade.salvar(usuariopontos);
					metaRunnersMB.carregarListaRunners();
				}
			}
			
			vendas.setSituacao("ANDAMENTO");
			vendasDao.salvar(vendas);
			if (idProduto == 1) {
				SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
				Seguroviagem seguroviagem = seguroViagemFacade.consultarSeguroCurso(vendas.getIdvendas());
				if (seguroviagem != null && seguroviagem.getIdseguroViagem() != null) {
					seguroviagem.getVendas().setSituacao("ANDAMENTO");
					seguroviagem.getVendas().setSituacaogerencia("F");
					vendasDao.salvar(seguroviagem.getVendas());
				}
				
				AcomodacaoCursoFacade acomodacaoCursoFacade = new AcomodacaoCursoFacade();
				Acomodacaocurso acomodacaocurso = acomodacaoCursoFacade.consultar("SELECT a FROM Acomodacaocurso a WHERE a.curso.vendas.idvendas=" + vendas.getIdvendas());
				if (acomodacaocurso != null && acomodacaocurso.getIdacomodacaocurso() != null) {
					Vendas vendasAcomodacao = acomodacaocurso.getAcomodacao().getVendas();
					vendasAcomodacao.setSituacao("ANDAMENTO");
					vendasAcomodacao.setSituacaogerencia("F");
					vendasDao.salvar(vendasAcomodacao);
				}
			}else if (idProduto == 16) {
				SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
				Seguroviagem seguroviagem = seguroViagemFacade.consultarSeguroCurso(vendas.getIdvendas());
				if (seguroviagem != null && seguroviagem.getIdseguroViagem() != null) {
					seguroviagem.getVendas().setSituacao("ANDAMENTO");
					seguroviagem.getVendas().setSituacaogerencia("F");
					vendasDao.salvar(seguroviagem.getVendas());
				}
			}
			
			Avisos avisos = new Avisos();
			int idprodutoCurso = aplicacaoMB.getParametrosprodutos().getCursos();
			if (idprodutoCurso == vendas.getProdutos().getIdprodutos()) {
				gerarNotificacaoUsuarioVinculado("Cursos");
			}
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}
		}
	}
	
	
	public void finalizarAupair() {
		AupairFacade aupairFacade = new AupairFacade();
		Aupair aupair = aupairFacade.consultar(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizar(aupair, vendasDao);
		new ContasReceberBean(aupair.getVendas(),
				aupair.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true,
				aupair.getDataInicioPretendida01());
	}
	
	
	public void finalizarTrainee() {
		TraineeFacade traineeFacade = new TraineeFacade();
		Trainee trainee = traineeFacade.consultar(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizarTrainee(trainee);
		new ContasReceberBean(trainee.getVendas(),
				trainee.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true, null);
	}
	
	
	public void finalizarWork() {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		Worktravel worktravel = workTravelFacade.consultarWork(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizarWork(worktravel);
		if (Formatacao.validarDataVenda(worktravel.getVendas().getDataVenda())) {
			new ContasReceberBean(worktravel.getVendas(),
					worktravel.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true,
					worktravel.getDataInicioPretendida01());
		}
	}
	
	public void finalizarVoluntariado() {
		VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
		Voluntariado voluntariado = voluntariadoFacade.consultar(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizarVoluntariado(voluntariado, vendasDao); 
		if (Formatacao.validarDataVenda(voluntariado.getVendas().getDataVenda())) {
			new ContasReceberBean(voluntariado.getVendas(),
					voluntariado.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true,
					voluntariado.getDataInicio());
		}
	}
	
	public void finalizarDemiPair() {
		DemipairFacade demipairFacade = new DemipairFacade();
		Demipair demipair = demipairFacade.consultar(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizarDemipair(demipair);

		if (Formatacao.validarDataVenda(demipair.getVendas().getDataVenda())) {
			new ContasReceberBean(demipair.getVendas(),
					demipair.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true, demipair.getDatainicio());
		}
	}
	
	public void finalizarHighSchool() {
		HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
		Highschool highschool = highSchoolFacade.consultarHighschool(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizarHighSchool(highschool);
		if (Formatacao.validarDataVenda(highschool.getVendas().getDataVenda())) {
			new ContasReceberBean(highschool.getVendas(),
					highschool.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true,
					highschool.getValoreshighschool().getDatainicio());
		}
	}
	
	public void finalizarTeens() {
		ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
		Programasteens programasteens = programasTeensFacede.find(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizarTeens(programasteens);
		new ContasReceberBean(programasteens.getVendas(),
				programasteens.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true,
				programasteens.getDataInicioCurso());
	}
	
	public void finalizarCurso() {
		CursoFacade cursoFacade = new CursoFacade();
		Curso curso = cursoFacade.consultarCursos(vendas.getIdvendas());
		FinalizarMB finalizarMB = new FinalizarMB(aplicacaoMB);
		vendas = finalizarMB.finalizarCurso(curso, vendasDao);
		new ContasReceberBean(curso.getVendas(),
				curso.getVendas().getFormapagamento().getParcelamentopagamentoList(), usuarioLogadoMB, null, true,
				curso.getDataInicio());
	}

	public void verificarDocumentosHE() {
		boolean ficha = false;
		boolean contrato = false;
		boolean rg = false;
		boolean documentoComFoto = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 13) {
				rg = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 1) {
				documentoComFoto = true;
			}
		}
		if (((ficha) && (contrato) && (documentoComFoto)) || ((ficha) && (contrato) && (rg))) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
				vendas.setDataprocesso(new Date());
			}
			vendas.setSituacaogerencia("F");
			
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Upload do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

		}
	}

	public void verificarDocumentosVoluntariado() {
		boolean ficha = false;
		boolean contrato = false;
		boolean documentoComFoto = false;
		boolean cartaMotivacao = false;
		boolean curriculum = false;
		boolean antecedentesCriminais = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 1) {
				documentoComFoto = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 40) {
				cartaMotivacao = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 20) {
				antecedentesCriminais = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 17) {
				curriculum = true;
			}
		}
		if (((ficha) && (contrato) && (documentoComFoto) && (cartaMotivacao) && (curriculum)
				&& (antecedentesCriminais))) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
			}
			vendas.setSituacaogerencia("F");
			
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Voluntariado: Upload do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

		}
	}

	public void verificarDocumentosAupair() {
		boolean ficha = false;
		boolean contrato = false;
		boolean documentoComFoto = false;
		boolean formulario = false;
		boolean antecedentesCriminais = false;
		boolean certificado = false;
		boolean characterReference = false;
		boolean childCare = false;
		boolean medicalReport = false;
		boolean aupairInverviewReport = false;
		boolean englishEvaluationTest = false;
		boolean copiaCNH = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 1) {
				documentoComFoto = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 34) {
				formulario = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 20) {
				antecedentesCriminais = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 26) {
				certificado = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 35) {
				characterReference = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 36) {
				childCare = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 37) {
				medicalReport = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 38) {
				aupairInverviewReport = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 39) {
				englishEvaluationTest = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 32) {
				copiaCNH = true;
			}
		}
		if (((ficha) && (contrato) && (documentoComFoto) && (formulario) && (certificado) && (antecedentesCriminais)
				&& (characterReference) && (childCare) && (medicalReport) && (aupairInverviewReport)
				&& (englishEvaluationTest) && (copiaCNH))) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
			}
			vendas.setSituacaogerencia("F");
			
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Au Pair: Upload do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

		}
	}

	public void verificarDocumentosDemipair() {
		boolean ficha = false;
		boolean contrato = false;
		boolean applicationForm = false;
		boolean testeIngles = false;
		boolean termoCondicoes = false;
		boolean cartaApresentacao = false;
		boolean curriculum = false;
		boolean diploma = false;
		boolean historicoEscolar = false;
		boolean cartaRecomendacao1 = false;
		boolean cartaRecomendacao2 = false;
		boolean antecedentesCriminais = false;
		boolean copiaCNH = false;
		boolean documentoComFoto = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 1) {
				documentoComFoto = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 32) {
				copiaCNH = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 23) {
				applicationForm = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 21) {
				testeIngles = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 31) {
				termoCondicoes = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 24) {
				cartaApresentacao = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 17) {
				curriculum = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 25) {
				diploma = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 26) {
				historicoEscolar = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 18) {
				cartaRecomendacao1 = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 19) {
				cartaRecomendacao2 = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 20) {
				antecedentesCriminais = true;
			}
		}
		if (((ficha) && (contrato) && (documentoComFoto) && (copiaCNH) && (applicationForm) && (testeIngles)
				&& (termoCondicoes) && (cartaApresentacao) && (curriculum) && (diploma) && (historicoEscolar)
				&& (cartaRecomendacao1) && (cartaRecomendacao2) && (antecedentesCriminais))) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
			}
			vendas.setSituacaogerencia("F");
			
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Demi Pair: Upload do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

		}
	}

	public void verificarDocumentosProgramaEstagio() {
		boolean ficha = false;
		boolean contrato = false;
		boolean applicationForm = false;
		boolean cartaApresentacao = false;
		boolean curriculum = false;
		boolean diploma = false;
		boolean historicoEscolar = false;
		boolean cartaRecomendacao1 = false;
		boolean cartaRecomendacao2 = false;
		boolean antecedentesCriminais = false;
		boolean documentoComFoto = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 1) {
				documentoComFoto = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 42) {
				applicationForm = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 24) {
				cartaApresentacao = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 17) {
				curriculum = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 25) {
				diploma = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 26) {
				historicoEscolar = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 18) {
				cartaRecomendacao1 = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 19) {
				cartaRecomendacao2 = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 20) {
				antecedentesCriminais = true;
			}
		}
		if (((ficha) && (contrato) && (documentoComFoto) && (applicationForm) && (cartaApresentacao) && (curriculum)
				&& (diploma) && (historicoEscolar) && (cartaRecomendacao1) && (cartaRecomendacao2)
				&& (antecedentesCriminais))) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
			}
			vendas.setSituacaogerencia("F");
			
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Trainee: Upload do cliente " + vendas.getCliente().getNome() + ", ID da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

		}
	}

	public void verificarDocumentosWorkTravel() {
		WorkTravelFacade workTravelFacade = new WorkTravelFacade();
		Worktravel worktravel = workTravelFacade.consultarWork(vendas.getIdvendas());

		boolean ficha = false;
		boolean contrato = false;
		boolean atestadoMatricula = false;
		boolean documentoComFoto = false;
		boolean curriculum = false;
		boolean cartaApresentacao = false;
		boolean cartaRecomendacao1 = false;
		boolean cartaRecomendacao2 = false;
		boolean antecedentesCriminais = false;
		boolean teste;
		if (worktravel != null && worktravel.getTipo().equalsIgnoreCase("Independent")) {
			teste = true;
		} else {
			teste = false;
		}

		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 1) {
				documentoComFoto = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 15) {
				atestadoMatricula = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 24) {
				cartaApresentacao = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 17) {
				curriculum = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 18) {
				cartaRecomendacao1 = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 19) {
				cartaRecomendacao2 = true;
			}
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 20) {
				antecedentesCriminais = true;
			}
			if (worktravel != null && !worktravel.getTipo().equalsIgnoreCase("Independent")) {
				if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 45) {
					teste = true;
				}
			}
		}
		if (((ficha) && (contrato) && (documentoComFoto) && (atestadoMatricula) && (cartaApresentacao) && (curriculum)
				&& (cartaRecomendacao1) && (cartaRecomendacao2) && (antecedentesCriminais) && (teste))) { 
			Controlework controlework = workTravelFacade.consultarControle(vendas.getIdvendas());
			if(controlework!=null) {
				controlework.setDocumentacao(true);
				controlework = workTravelFacade.salvar(controlework);
			}
			
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
			}
			vendas.setSituacaogerencia("F");
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Work and Travel: Upload do cliente " + vendas.getCliente().getNome() + ", ID da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}
		}
	}

	public void verificarDocumentosHighSchool() {
		boolean ficha = false;
		boolean contrato = false;
		boolean passaporte = false;
		boolean historico = false;
		boolean vacinacao = false;
		boolean application = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 46) {
				passaporte = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 26) {
				historico = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 28) {
				vacinacao = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 23) {
				application = true;
			}
		}
		if ((ficha) && (contrato) && (passaporte) && (historico) && (vacinacao) && (application)) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
			}
			vendas.setSituacaogerencia("F");
			
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("High School: Upload do cliente " + vendas.getCliente().getNome() + ", ID da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

		}

	}

	public void verificarDocumentosCursosTeens() {
		boolean ficha = false;
		boolean contrato = false;
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 2) {
				ficha = true;
			} else if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 3) {
				contrato = true;
			}
		}
		if ((ficha) && (contrato)) {
			if (vendas.getSituacaofinanceiro().equalsIgnoreCase("L")){
				vendas.setSituacao("FINALIZADA");
			}
			vendas.setSituacaogerencia("F");
			
			vendasDao.salvar(vendas);
			
			Avisos avisos = new Avisos();
			avisos.setData(new Date());
			avisos.setUsuario(usuarioLogadoMB.getUsuario());
			avisos.setImagem("aviso");
			avisos.setLiberar(true);
			avisos.setTexto("Programas Teens: Upload do cliente " + vendas.getCliente().getNome() + ", ID da venda "
					+ vendas.getIdvendas() + " está completo.");
			avisos.setIdunidade(0);
			avisos = avisosDao.salvar(avisos);
			for (int i = 0; i < usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().size(); i++) {
				
			}  
			salvarAvisoUsuario(avisos);
			if ((vendas.getSituacaofinanceiro().equalsIgnoreCase("L")) && (vendas.getSituacaogerencia().equalsIgnoreCase("F"))) {
				avisos = new Avisos();
				avisos.setData(new Date());
				avisos.setUsuario(usuarioLogadoMB.getUsuario());
				avisos.setImagem("aviso");
				avisos.setLiberar(true);
				avisos.setTexto("Venda do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
						+ vendas.getIdvendas() + " está finalizada.");
				avisos.setIdunidade(0);
				avisos = avisosDao.salvar(avisos);
				salvarAvisoUsuario(avisos);
			}

		}
	}

	public boolean validacaoDados() {
		if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
			Mensagem.lancarMensagemInfo("Você esta tentando confirmar sem um upload de arquivo", "");
			return false;
		}
		return true;
	}

	public List<Avisousuario> salvarAvisoUsuario(Avisos aviso) {
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String sql = "";
		List<Usuario> listaUsuario = null;
		
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 2
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 5
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4
				|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 7) {
			sql = "select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="
					+ vendas.getUnidadenegocio().getIdunidadeNegocio() + " and u.vende=true or u.idusuario=1";
			listaUsuario = usuarioFacade.listar(sql);
			if (listaUsuario != null) {
				
				for (int i = 0; i < listaUsuario.size(); i++) {
					if (listaUsuario.get(i).getIdusuario() != 396) {
						Avisousuario avisousuario = new Avisousuario();
						avisousuario.setAvisos(aviso);
						avisousuario.setUsuario(listaUsuario.get(i));
						avisousuario.setVisto(false);
						avisousuario = avisosDao.salvar(avisousuario);
						lista.add(avisousuario);
					}
				}
			}
		} else {
			DepartamentoFacade departamentoFacade = new DepartamentoFacade();
			List<Departamento> departamento = departamentoFacade.listar(
					"select d From Departamento d where d.usuario.idusuario=" + vendas.getProdutos().getIdgerente());
			if (departamento != null && departamento.size() > 0) {
				sql = "select u From Usuariodepartamentounidade u where u.unidadenegocio.idunidadeNegocio="
						+ vendas.getUnidadenegocio().getIdunidadeNegocio() + " and u.departamento.iddepartamento="
						+ departamento.get(0).getIddepartamento();
				UsuarioDepartamentoUnidadeFacade usuarioDepartamentoUnidadeFacade = new UsuarioDepartamentoUnidadeFacade();
				List<Usuariodepartamentounidade> listaNoficacao = usuarioDepartamentoUnidadeFacade.listar(sql);
				if (listaNoficacao != null) {
					
					for (int i = 0; i < listaNoficacao.size(); i++) {
						if (listaNoficacao.get(i).getUsuario().getIdusuario() != 396) {
							Avisousuario avisousuario = new Avisousuario();
							avisousuario.setAvisos(aviso);
							avisousuario.setUsuario(listaNoficacao.get(i).getUsuario());
							avisousuario.setVisto(false);
							avisousuario = avisosDao.salvar(avisousuario);
							lista.add(avisousuario);
						}
					}
				}
			}
		}
		return lista;
	}

	public void dataRecebimentoInvoice() {
		InvoiceFacade invoiceFacade = new InvoiceFacade();
		String sql = "SELECT i FROM Invoice i where i.vendas.idvendas=" + arquivos.getVendas().getIdvendas();
		if (tipoarquivo.getTipoarquivo().getIdtipoArquivo() == 58) {
			sql = sql + " and i.tipo='Programa'";
		} else
			sql = sql + " and i.tipo='Acomodação'";
		List<Invoice> lista = invoiceFacade.listar(sql);
		if (lista != null) {
			if (lista.size() == 1) {
				Invoice invoice = lista.get(0);
				invoice.setDatarecebimentocomprovante(new Date());
				invoiceFacade.salvar(invoice);
			} else {
				Mensagem.lancarMensagemWarn("Warn", "Cliente possui mais de uma invoice para baixar");
			}
		} else {
			Mensagem.lancarMensagemWarn("Warn", "Nenhuma invoice localizada");
		}
	}
	
	
	public void gerarNotificacaoUsuarioVinculado(String programa){
		if (usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList() != null && usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().size() > 0) {
			
			Avisos aviso = new Avisos();
			aviso.setData(new Date());
			aviso.setUsuario(usuarioLogadoMB.getUsuario());
			aviso.setImagem("aviso");
			aviso.setLiberar(true);
			aviso.setTexto(programa + ": Upload do cliente " + vendas.getCliente().getNome() + ", Nº da venda "
					+ vendas.getIdvendas() + " está completo.");
			aviso.setIdunidade(0);
			aviso = avisosDao.salvar(aviso);
			for (int i = 0; i < usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().size(); i++) {
				Avisousuario avisousuario = new Avisousuario();
				avisousuario.setAvisos(aviso);
				avisousuario.setUsuario(usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().get(i).getUsuarioNotificar());
				avisousuario.setVisto(false);
				avisousuario = avisosDao.salvar(avisousuario);
			}
		}
	}
	
	public void habilitarCambosBilhete() {
		if(tipoarquivo!=null && tipoarquivo.getIdtipoarquivoproduto()!=null &&
				tipoarquivo.getTipoarquivo().getIdtipoArquivo()==4) {
			camposbilhete=true;
		}else camposbilhete=false;
	}
	
	public void validarSeguroViagem() {
		SeguroViagemFacade seguroViagemFacade = new SeguroViagemFacade();
		Seguroviagem seguroviagem = seguroViagemFacade.consultarSeguroCurso(vendas.getIdvendas());
		boolean possuiSeguro = false;
		if (seguroviagem == null) {
			seguroviagem = seguroViagemFacade.consultar(vendas.getIdvendas());
			if (seguroviagem == null) {
				possuiSeguro = false;
			} else if (seguroviagem.getPossuiSeguro().equalsIgnoreCase("Não")) {
				possuiSeguro = false;
			} else
				possuiSeguro = true;
		} else if (seguroviagem.getPossuiSeguro().equalsIgnoreCase("Não")) {
			possuiSeguro = false;
		} else possuiSeguro = true;
		if (possuiSeguro && this.datachegadabrasil != null && this.dataembarque != null) {
			if ((!seguroviagem.getDataInicio().after(this.dataembarque))
					|| (!seguroviagem.getDataTermino().before(this.datachegadabrasil))) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("msgBilhete", "lancar");
			} else {
				seguroviagem.getControleseguro().setDataembarque(this.getDataembarque());
				seguroViagemFacade.salvarControle(seguroviagem.getControleseguro());
				if (seguroviagem.getControle().equalsIgnoreCase("Curso")) {
					CursoFacade cursoFacade = new CursoFacade();
					Controlecurso controle = cursoFacade.consultarControleCursos(vendas.getIdvendas());
					if (controle != null) {
						controle.setDatachegadabrasil(this.datachegadabrasil);
						controle.setDataEmbarque(this.dataembarque);
						cursoFacade.salvar(controle);
					}
				} else if (seguroviagem.getControle().equalsIgnoreCase("Voluntariado")) {
					VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
					Controlevoluntariado controle = voluntariadoFacade.consultarControle(vendas.getIdvendas());
					if (controle != null) {
						controle.setDatachegadabrasil(this.datachegadabrasil);
						controle.setDataembarque(this.dataembarque);
						voluntariadoFacade.salvar(controle);
					}
				}
				Leadposvenda leadposvenda = leadPosVendaDao.consultar("SELECT l FROM Leadposvenda l WHERE l.vendas.idvendas="+vendas.getIdvendas());
				if(leadposvenda!=null) {
					leadposvenda.setDataembarque(dataembarque);
					leadposvenda.setDatachegada(datachegadabrasil);
					leadposvenda = leadPosVendaDao.salvar(leadposvenda);
				}
			}
		}

	}
	
	public boolean validarBilheteAereo() {
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getTipoarquivo().getIdtipoArquivo() == 4) {
				return true;
			} 
		}
		return false;
	}
	
	
	public List<Avisousuario> salvarAvisoUsuarioVinculado(Avisos aviso) {
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		for (int i = 0; i < usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().size(); i++) {
			
			Avisousuario avisousuario = new Avisousuario();
			avisousuario.setAvisos(aviso);
			avisousuario.setUsuario(usuarioLogadoMB.getUsuario().getNotificacaoUploadNotificarList().get(i).getUsuarioNotificar());
			avisousuario.setVisto(false);
			avisousuario = avisosDao.salvar(avisousuario);
		}
		return lista;
	}
	
	public Tipoarquivoproduto buscarTipoArquivo() {
		List<Tipoarquivoproduto> lista = null;
		TipoArquivoProdutoFacade tipoArquivoProdutoFacade = new TipoArquivoProdutoFacade();
		try {
			lista = tipoArquivoProdutoFacade.listar("SELECT t FROM Tipoarquivoproduto t WHERE t.produtos.idprodutos=" 
					+ arquivos.getVendas().getProdutos().getIdprodutos() + " and t.tipoarquivo.idtipoArquivo=" + arquivos.getTipoarquivo().getIdtipoArquivo());
		} catch (SQLException e) {
			  
		}
		
		if (lista != null && lista.size() > 0) {
			return lista.get(0);
		}
		return null;
	}
}
