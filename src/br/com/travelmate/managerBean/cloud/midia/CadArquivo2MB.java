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

import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadArquivo2MB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Pasta1 pasta1;
	private Arquivo2 arquivo2;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private Departamento departamento;
	private Pasta2 pasta2;
	private String nomeArquivo = "";
	private String nomeArquivoAviso;
	private String caminhoArquivoAviso;
	private String nomeSalvoArquivoAviso;
	private Date dataValidade;
	private Date dataInicio;
	private GerarAvisosDocsBean gerarAvisos;
	private List<GerarAvisosDocsBean> listaGerarAvisos;
	private List<Arquivo2> listaArquivo2;
	private boolean arquivoExistente = false;
	private boolean restrito;
	private boolean arquivoEnviado = false;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pasta2 = (Pasta2) session.getAttribute("pasta2");
		session.removeAttribute("pasta2");
		if (pasta2 != null) {
			pasta1 = pasta2.getPasta1();
			departamento = pasta1.getDepartamento();
		}
		if (arquivo2 == null) {
			arquivo2 = new Arquivo2();
			listaNomeArquivo = new ArrayList<>();
			arquivo2.setDatainicio(new Date());
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
	 * @return the arquivo2
	 */
	public Arquivo2 getArquivo2() {
		return arquivo2;
	}



	/**
	 * @param arquivo2 the arquivo2 to set
	 */
	public void setArquivo2(Arquivo2 arquivo2) {
		this.arquivo2 = arquivo2;
	}



	/**
	 * @return the pasta2
	 */
	public Pasta2 getPasta2() {
		return pasta2;
	}



	/**
	 * @param pasta2 the pasta2 to set
	 */
	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
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
	
	



	public List<Arquivo2> getListaArquivo2() {
		return listaArquivo2;
	}



	public void setListaArquivo2(List<Arquivo2> listaArquivo2) {
		this.listaArquivo2 = listaArquivo2;
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



	public boolean salvarArquivoFTP(){
		String msg = "";
        FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivo2MB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CadArquivo2MB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CadArquivo2MB.class.getName()).log(Level.SEVERE, null, ex);
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
		salvarArquivoFTP();
		String nome = e.getFile().getFileName();
		if (arquivoEnviado) {
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
	
	 
	public String salvar(){
			Arquivo2Facade cloudSubPastasArquivoFacade = new Arquivo2Facade();
			listaGerarAvisos = new ArrayList<>();
			if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
				Mensagem.lancarMensagemInfo("Atenção", "você não fez nenhum upload de arquivo!!");
			}else{
				for (int j = 0; j < listaNomeArquivo.size(); j++) {
					if (arquivoExistente) {
						for (int i = 0; i < listaArquivo2.size(); i++) {
							cloudSubPastasArquivoFacade.excluir(listaArquivo2.get(i).getIdarquivo2());
						}
						arquivoExistente = false;
					}
					arquivo2 = new Arquivo2();
					arquivo2.setPasta1(pasta1);
					arquivo2.setPasta2(pasta2);
					arquivo2.setDatavalidade(dataValidade);
					arquivo2.setRestrito(restrito);
					arquivo2.setNomeftp(nomeArquivo() + "_" + listaNomeArquivo.get(j).trim());
					arquivo2.setTipo(gerarTipoArquivo(listaNomeArquivo.get(j)));
					if (nomeArquivo == null || nomeArquivo.equalsIgnoreCase("")) {
						arquivo2.setNome(listaNomeArquivo.get(j));
					}else{
						arquivo2.setNome(nomeArquivo);	
					}
					if (dataInicio == null) {
						arquivo2.setDatainicio(new Date());
					}else{
						arquivo2.setDatainicio(dataInicio);
					}
					arquivo2.setHora(retornarHoraAtual());
					arquivo2.setDataupload(new Date());
					arquivo2 = cloudSubPastasArquivoFacade.salvar(arquivo2);
					gerarAvisos = new GerarAvisosDocsBean();
					gerarAvisos.setCaminhoArquivoAviso(gerarCaminhoArquivo(arquivo2));
					gerarAvisos.setNomeArquivoAviso(arquivo2.getNome());
					gerarAvisos.setNomeSalvoArquivoAviso(arquivo2.getNomeftp());
					gerarAvisos.setRestrito(arquivo2.isRestrito());
					listaGerarAvisos.add(gerarAvisos);
						
				}
//				new Thread(){
//					@Override
//					public void run() {
//						AvisoArquivoBean avisoArquivoBean = new AvisoArquivoBean(listaGerarAvisos);
//						
//					}
//				}.start();
				RequestContext.getCurrentInstance().closeDialog(listaGerarAvisos);
			}
		return "";
	}
	
	public String gerarCaminhoArquivo(Arquivo2 arquivo){
		String caminho = arquivo.getPasta1().getDepartamento().getNome() + "\\" + arquivo.getPasta1().getNome() + "\\" + arquivo.getPasta2().getNome() +
				"\\" + arquivo.getNome();
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
		}
		return ""; 
	} 
	
	
	public String nomeArquivo(){
		nomeArquivoFTP = departamento.getIddepartamento()+ "_" + pasta1.getIdpasta1() + "_" + pasta2.getIdpasta2();
		return nomeArquivoFTP;
	}
	
	
	public String cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new ArrayList<GerarAvisosDocsBean>());
		return "";
	}
	
	public String validarDados(){
		String msg = "";
		if (nomeArquivo.equalsIgnoreCase("")) {
			msg = msg + " você não informou o nome do arquivo";
		}
		return msg;
	}
	
	
	public boolean verificacaoArquivo(String nomeArquivo){
		nomeArquivo = nomeArquivo() + "_" + nomeArquivo;
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		listaArquivo2 = arquivo2Facade.listar("Select a From Arquivo2 a Where a.nomeftp='" + nomeArquivo + "'");
		if (listaArquivo2 == null || listaArquivo2.isEmpty()) {
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
