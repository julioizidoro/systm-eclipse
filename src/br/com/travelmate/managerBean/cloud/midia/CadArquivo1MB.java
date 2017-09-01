package br.com.travelmate.managerBean.cloud.midia;

import java.io.File;
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
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadArquivo1MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pasta1 pasta1;
	private Arquivo1 arquivo1;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private Departamento departamento;
	private String nomeArquivo = "";
	private String nomeArquivoAviso;
	private String caminhoArquivoAviso;
	private String nomeSalvoArquivoAviso;
	private Date dataValidade;
	private Date dataInicio;
	private List<GerarAvisosDocsBean> listaGerarAvisos;
	private GerarAvisosDocsBean gerarAvisos;
	private List<Arquivo1> listaArquivo1;
	private boolean arquivoExistente = false;
	private boolean restrito;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pasta1 = (Pasta1) session.getAttribute("pasta1");
		session.removeAttribute("pasta1");
		if (pasta1 != null) {
			departamento = pasta1.getDepartamento();
		}
		if (arquivo1 == null) {
			arquivo1 = new Arquivo1();
			listaNomeArquivo = new ArrayList<>();
		}
	}


	
	public String getNomeArquivo() {
		return nomeArquivo;
	}



	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
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



	
	
	
	
	/**
	 * @return the pasta1
	 */
	public Pasta1 getPasta1() {
		return pasta1;
	}



	/**
	 * @param pasta1 the pasta1 to set
	 */
	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}



	/**
	 * @return the arquivo1
	 */
	public Arquivo1 getArquivo1() {
		return arquivo1;
	}



	/**
	 * @param arquivo1 the arquivo1 to set
	 */
	public void setArquivo1(Arquivo1 arquivo1) {
		this.arquivo1 = arquivo1;
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

	


	public List<GerarAvisosDocsBean> getListaGerarAvisos() {
		return listaGerarAvisos;
	}



	public void setListaGerarAvisos(List<GerarAvisosDocsBean> listaGerarAvisos) {
		this.listaGerarAvisos = listaGerarAvisos;
	}
	
	
	


	public GerarAvisosDocsBean getGerarAvisos() {
		return gerarAvisos;
	}



	public void setGerarAvisos(GerarAvisosDocsBean gerarAvisos) {
		this.gerarAvisos = gerarAvisos;
	}
	

	public List<Arquivo1> getListaArquivo1() {
		return listaArquivo1;
	}



	public void setListaArquivo1(List<Arquivo1> listaArquivo1) {
		this.listaArquivo1 = listaArquivo1;
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



	public String nomeArquivo(){
		nomeArquivoFTP = departamento.getIddepartamento()+ "_" + pasta1.getIdpasta1();
		return nomeArquivoFTP;
	}
	
	
	public boolean salvarArquivoFTP(){
		String msg = "";
        FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
        if (dadosFTP==null){
            return false;
        }
        Ftp ftp = new Ftp(dadosFTP.getHostupload(),dadosFTP.getUser(), dadosFTP.getPassword());
        try {
            if (!ftp.conectar()){
                mostrarMensagem(null, "Erro conectar FTP", "");
                return false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CadArquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensagem(ex, "Erro conectar FTP", "Erro");
        }
        try {
        	nomeArquivoFTP = nomeArquivo();
        	msg = ftp.enviarArquivoDOCS(file, nomeArquivoFTP, "/cloud/departamentos/");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(msg, ""));
            ftp.desconectar();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(CadArquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
        } 
        return false;
    }
	
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	
	public void fileUploadListener(FileUploadEvent e){
		this.file = e.getFile();
		if (listaFile == null) {
			listaFile = new ArrayList<UploadedFile>();
		}
		listaFile.add(file);
		salvarArquivoFTP();
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
	
	
	

	public String salvar() {
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		listaGerarAvisos = new ArrayList<>();
		if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
			Mensagem.lancarMensagemInfo("Atenção", "você não fez nenhum upload de arquivo!!");
		} else {
			for (int j = 0; j < listaNomeArquivo.size(); j++) {
				if (arquivoExistente) {
					for (int i = 0; i < listaArquivo1.size(); i++) {
						arquivo1Facade.excluir(listaArquivo1.get(i).getIdArquivo1());
					}
					arquivoExistente = false;
				}
				arquivo1 = new Arquivo1(); 
				arquivo1.setPasta1(pasta1);
				arquivo1.setNomeftp(nomeArquivo() + "_" + listaNomeArquivo.get(j));
				arquivo1.setTipo(gerarTipoArquivo(listaNomeArquivo.get(j)));
				arquivo1.setRestrito(restrito);
				if (nomeArquivo == null || nomeArquivo.equalsIgnoreCase("")) {
					arquivo1.setNome(listaNomeArquivo.get(j));
				} else {
					arquivo1.setNome(nomeArquivo);
				}
				arquivo1.setDatavalidade(dataValidade);
				if (dataInicio == null) {
					arquivo1.setDatainicio(new Date());
				} else {
					arquivo1.setDatainicio(dataInicio);
				}
				arquivo1.setHora(retornarHoraAtual());
				arquivo1.setDataupload(new Date());
				arquivo1 = arquivo1Facade.salvar(arquivo1);
				gerarAvisos = new GerarAvisosDocsBean();
				gerarAvisos.setNomeArquivoAviso(arquivo1.getNome());
				gerarAvisos.setCaminhoArquivoAviso(gerarCaminhoArquivo(arquivo1));
				gerarAvisos.setNomeSalvoArquivoAviso(arquivo1.getNomeftp());
				gerarAvisos.setRestrito(arquivo1.isRestrito());
				listaGerarAvisos.add(gerarAvisos);
			}
			// new Thread(){
			// @Override
			// public void run() {
			// AvisoArquivoBean avisoArquivoBean = new
			// AvisoArquivoBean(listaGerarAvisos);

			// }
			// }.start();
			RequestContext.getCurrentInstance().closeDialog(listaGerarAvisos);
		}
		return "";
	}
		
		
	
	public String gerarCaminhoArquivo(Arquivo1 arquivo){
		String caminho = arquivo.getPasta1().getDepartamento().getNome() + "\\" + arquivo.getPasta1().getNome() + "\\" + arquivo.getNome();
		return caminho;
	}
	
	public String gerarTipoArquivo(String nomeArquivo){
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length()>5){
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".jpg")) {
			return "jpg";
		}else if(extensao.equalsIgnoreCase(".pdf")){
			return "pdf";
		}else if(extensao.equalsIgnoreCase(".docx")){
			return "docx";
		}else if(extensao.equalsIgnoreCase(".xls")){
			return "xls";
		}else if(extensao.equalsIgnoreCase(".txt")){
			return "txt";
		}else if(extensao.equalsIgnoreCase(".jpeg")){
			return "jpeg";
		}else if(extensao.equalsIgnoreCase(".png")){
			return "png";
		}else if(extensao.equalsIgnoreCase(".doc")){
			return "doc";
		}else if(extensao.equalsIgnoreCase(".pptx")){
			return "pptx";
		}else if(extensao.equalsIgnoreCase(".cdr")){
			return "cdr";
		} else if(extensao.equalsIgnoreCase(".eps")){
			return "eps";
		}else if(extensao.equalsIgnoreCase(".bmp")){
			return "bmp";
		}else if(extensao.equalsIgnoreCase(".xlsx")){
			return "xlsx";
		}else if(extensao.equalsIgnoreCase(".wma")){
			return "wma";
		}else if(extensao.equalsIgnoreCase(".ppsx")){
			return "ppsx";
		}else if(extensao.equalsIgnoreCase(".gif")){
			return "gif";
		}
		return ""; 
	}
	
	
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new ArrayList<GerarAvisosDocsBean>());
		return "";
	}
	
	
	public String validarDados(){
		String msg = "";
		if (nomeArquivo.equalsIgnoreCase("")) {
			msg = msg + " Você não informou o nome do arquivo";
		}
		return msg;
	}
	
	
	public boolean verificacaoArquivo(String nomeArquivo){
		nomeArquivo = nomeArquivo() + "_" + nomeArquivo;
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		listaArquivo1 = arquivo1Facade.listar("Select a From Arquivo1 a Where a.nomeftp='" + nomeArquivo + "'");
		if (listaArquivo1 == null || listaArquivo1.isEmpty()) {
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
	
	public void fecharArquivoSalvo(){
		RequestContext.getCurrentInstance().closeDialog(arquivo1);
	}  
	
	
	public String retornarHoraAtual(){
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date hora = Calendar.getInstance().getTime();
	    return sdf.format(hora);
	}
}
