package br.com.travelmate.managerBean.cloud.midia;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadArquivo5MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Pasta1 pasta1;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private Departamento departamento;
	private Pasta2 pasta2;
	private Arquivo5 arquivo5;
	private Pasta3 pasta3;
	private String nomeArquivo = "";
	private Pasta4 pasta4;
	private Pasta5 pasta5;
	private String nomeArquivoAviso;
	private String caminhoArquivoAviso;
	private String nomeSalvoArquivoAviso;
	private Date dataValidade;
	private Date dataInicio;
	private GerarAvisosDocsBean gerarAvisos;
	private List<GerarAvisosDocsBean> listaGerarAvisos;
	private List<Arquivo5> listaArquivo5;
	private boolean arquivoExistente = false;
	private boolean restrito;
	private boolean arquivoEnviado = false;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pasta5 = (Pasta5) session.getAttribute("pasta5");
		session.removeAttribute("pasta5");
		if (pasta5 != null) {
			pasta4 = pasta5.getPasta4();
			pasta3 = pasta5.getPasta3();
			pasta2 = pasta5.getPasta2();
			pasta1 = pasta5.getPasta1();
			departamento = pasta1.getDepartamento();
		}
		if (arquivo5 == null) {
			arquivo5 = new Arquivo5();
			listaNomeArquivo = new ArrayList<>();
		}
	}

	public Arquivo5 getArquivo5() {
		return arquivo5;
	}

	public void setArquivo5(Arquivo5 arquivo5) {
		this.arquivo5 = arquivo5;
	}

	public Pasta5 getPasta5() {
		return pasta5;
	}

	public void setPasta5(Pasta5 pasta5) {
		this.pasta5 = pasta5;
	}

	public Pasta4 getPasta4() {
		return pasta4;
	}

	public void setPasta4(Pasta4 pasta4) {
		this.pasta4 = pasta4;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	/**
	 * @return the pasta1
	 */
	public Pasta1 getPasta1() {
		return pasta1;
	}

	/**
	 * @param pasta1
	 *            the pasta1 to set
	 */
	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}

	/**
	 * @return the pasta2
	 */
	public Pasta2 getPasta2() {
		return pasta2;
	}

	/**
	 * @param pasta2
	 *            the pasta2 to set
	 */
	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
	}

	/**
	 * @return the pasta3
	 */
	public Pasta3 getPasta3() {
		return pasta3;
	}

	/**
	 * @param pasta3
	 *            the pasta3 to set
	 */
	public void setPasta3(Pasta3 pasta3) {
		this.pasta3 = pasta3;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getNomeArquivoFTP() {
		return nomeArquivoFTP;
	}

	public void setNomeArquivoFTP(String nomeArquivoFTP) {
		this.nomeArquivoFTP = nomeArquivoFTP;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}

	public List<UploadedFile> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<UploadedFile> listaFile) {
		this.listaFile = listaFile;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
	
	

	public String getNomeArquivoAviso() {
		return nomeArquivoAviso;
	}

	public void setNomeArquivoAviso(String nomeArquivoAviso) {
		this.nomeArquivoAviso = nomeArquivoAviso;
	}

	public String getCaminhoArquivoAviso() {
		return caminhoArquivoAviso;
	}

	public void setCaminhoArquivoAviso(String caminhoArquivoAviso) {
		this.caminhoArquivoAviso = caminhoArquivoAviso;
	}

	public String getNomeSalvoArquivoAviso() {
		return nomeSalvoArquivoAviso;
	}

	public void setNomeSalvoArquivoAviso(String nomeSalvoArquivoAviso) {
		this.nomeSalvoArquivoAviso = nomeSalvoArquivoAviso;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	

	public GerarAvisosDocsBean getGerarAvisos() {
		return gerarAvisos;
	}

	public void setGerarAvisos(GerarAvisosDocsBean gerarAvisos) {
		this.gerarAvisos = gerarAvisos;
	}

	public List<GerarAvisosDocsBean> getListaGerarAvisos() {
		return listaGerarAvisos;
	}

	public void setListaGerarAvisos(List<GerarAvisosDocsBean> listaGerarAvisos) {
		this.listaGerarAvisos = listaGerarAvisos;
	}
	
	

	public List<Arquivo5> getListaArquivo5() {
		return listaArquivo5;
	}

	public void setListaArquivo5(List<Arquivo5> listaArquivo5) {
		this.listaArquivo5 = listaArquivo5;
	}

	public boolean isArquivoExistente() {
		return arquivoExistente;
	}

	public void setArquivoExistente(boolean arquivoExistente) {
		this.arquivoExistente = arquivoExistente;
	}
	
	

	public boolean isRestrito() {
		return restrito;
	}

	public void setRestrito(boolean restrito) {
		this.restrito = restrito;
	}

	public String nomeArquivo() {
		nomeArquivoFTP = departamento.getIddepartamento() + "_" + pasta1.getIdpasta1() + "_" + pasta2.getIdpasta2()
				+ "_" + pasta3.getIdpasta3() + "_" + pasta4.getIdpasta4() + "_" + pasta5.getIdpasta5();
		return nomeArquivoFTP;
	}

	public boolean salvarArquivoFTP() {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(CadArquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			nomeArquivoFTP = nomeArquivo();
			arquivoEnviado = ftp.enviarArquivoDOCS(file, nomeArquivoFTP, "/cloud/departamentos/");
			if (arquivoEnviado) {
				msg = "Arquivo: " + nomeArquivoFTP + " enviado com sucesso";
			}else{
				msg = "Erro ao salvar arquivo";
			}
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CadArquivo4MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		} 
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
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

	public String salvar() {
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		listaGerarAvisos = new ArrayList<>();
		if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
			Mensagem.lancarMensagemInfo("Atenção", "você não fez nenhum upload de arquivo!!");
		} else {
			for (int j = 0; j < listaNomeArquivo.size(); j++) {
				if (arquivoExistente) {
					for (int i = 0; i < listaArquivo5.size(); i++) {
						arquivo5Facade.excluir(listaArquivo5.get(i).getIdarquivo5());
					}
					arquivoExistente = false;
				}
				arquivo5 = new Arquivo5();
				arquivo5.setPasta3(pasta3);
				arquivo5.setPasta2(pasta2);
				arquivo5.setPasta1(pasta1);
				arquivo5.setPasta4(pasta4);
				arquivo5.setPasta5(pasta5);
				arquivo5.setRestrito(restrito);
				arquivo5.setDatavalidade(dataValidade);
				arquivo5.setNomeftp(nomeArquivo() + "_" + listaNomeArquivo.get(j).trim());
				arquivo5.setTipo(gerarTipoArquivo(listaNomeArquivo.get(j)));
				if (nomeArquivo == null || nomeArquivo.equalsIgnoreCase("")) {
					arquivo5.setNome(listaNomeArquivo.get(j));
				} else {
					arquivo5.setNome(nomeArquivo);
				}
				if (dataInicio == null) {
					arquivo5.setDatainicio(new Date());
				}else{
					arquivo5.setDatainicio(dataInicio);
				}
				arquivo5.setDataupload(new Date());
				arquivo5.setHora(retornarHoraAtual());
				arquivo5 = arquivo5Facade.salvar(arquivo5);
				gerarAvisos = new GerarAvisosDocsBean();
				gerarAvisos.setCaminhoArquivoAviso(gerarCaminhoArquivo(arquivo5));
				gerarAvisos.setNomeArquivoAviso(arquivo5.getNome());
				gerarAvisos.setNomeSalvoArquivoAviso(arquivo5.getNomeftp());
				gerarAvisos.setRestrito(arquivo5.isRestrito());
				listaGerarAvisos.add(gerarAvisos);
			}
//			new Thread() {
//				@Override
//				public void run() {
//					AvisoArquivoBean avisoArquivoBean = new AvisoArquivoBean(listaGerarAvisos);
//
//				}
//			}.start();
			RequestContext.getCurrentInstance().closeDialog(listaGerarAvisos);
		}
		return "";
	}

	public String gerarCaminhoArquivo(Arquivo5 arquivo) {
		String caminho = arquivo.getPasta1().getDepartamento().getNome() + "\\" + arquivo.getPasta1().getNome() + "\\"
				+ arquivo.getPasta2().getNome() + "\\" + arquivo.getPasta3().getNome() + "\\"
				+ arquivo.getPasta4().getNome() + "\\" + arquivo.getPasta5().getNome() + "\\" + arquivo.getNome();
		return caminho;
	}

	public String gerarTipoArquivo(String nomeArquivo) {
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length() > 4) {
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".jpg")) {
			return "jpg";
		} else if (extensao.equalsIgnoreCase(".pdf")) {
			return "pdf";
		} else if (extensao.equalsIgnoreCase(".docx")) {
			return "docx";
		} else if (extensao.equalsIgnoreCase(".xls")) {
			return "xls";
		} else if (extensao.equalsIgnoreCase(".txt")) {
			return "txt";
		} else if (extensao.equalsIgnoreCase(".jpeg")) {
			return "jpeg";
		} else if (extensao.equalsIgnoreCase(".png")) {
			return "png";
		} else if (extensao.equalsIgnoreCase(".doc")) {
			return "doc";
		} else if (extensao.equalsIgnoreCase(".pptx")) {
			return "pptx";
		} else if (extensao.equalsIgnoreCase(".cdr")) {
			return "cdr";
		} else if (extensao.equalsIgnoreCase(".eps")) {
			return "eps";
		} else if (extensao.equalsIgnoreCase(".bmp")) {
			return "bmp";
		} else if (extensao.equalsIgnoreCase(".xlsx")) {
			return "xlsx";
		}else if(extensao.equalsIgnoreCase(".wma")){
			return "wma";
		}else if(extensao.equalsIgnoreCase(".ppsx")){
			return "ppsx";
		}
		return "";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(new ArrayList<GerarAvisosDocsBean>());
		return "";
	}

	public String validarDados() {
		String msg = "";
		if (nomeArquivo.equalsIgnoreCase("")) {
			msg = msg + " você não informou o nome do arquivo";
		}
		return msg;
	}
	
	public boolean verificacaoArquivo(String nomeArquivo){
		nomeArquivo = nomeArquivo() + "_" + nomeArquivo;
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		listaArquivo5 = arquivo5Facade.listar("Select a From Arquivo5 a Where a.nomeftp='" + nomeArquivo + "'");
		if (listaArquivo5 == null || listaArquivo5.isEmpty()) {
			return false;
		}else{
			arquivoExistente = true;
			return true;
		}
	}
	
	public String confirmaArquivo() {
		listaGerarAvisos = new ArrayList<>();
		if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
			Mensagem.lancarMensagemInfo("Atenção", "você não fez nenhum upload de arquivo!!");
		} else {
			for (int j = 0; j < listaNomeArquivo.size(); j++) {
				if (verificacaoArquivo(listaNomeArquivo.get(j))) {
					arquivoExistente = true;
					return "";
				}else{
					arquivoExistente = false;
					salvar();
				}
			}
		}
		return "";
	}
	
	
	public String retornarHoraAtual(){
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date hora = Calendar.getInstance().getTime();
	    return sdf.format(hora);
	}

}
