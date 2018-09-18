package br.com.travelmate.managerBean.videos;

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

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.managerBean.cloud.midia.CadVideoMB;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Video5;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.model.Videopasta5;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.UploadAWSS3;


@Named
@ViewScoped
public class CadVideo5MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Videopasta1 videopasta1;
	private Videopasta2 videopasta2;
	private Videopasta3 videopasta3;
	private Videopasta4 videopasta4;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private String descricao;
	private Video5 video5;
	private Videopasta5 videopasta5;
	private boolean ativo;
	private String nome;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta5 = (Videopasta5) session.getAttribute("videopasta5");
		session.removeAttribute("videopasta5");
		if (videopasta5 != null) {
			videopasta1 = videopasta5.getVideopasta1();
			videopasta2 = videopasta5.getVideopasta2();
			videopasta3 = videopasta5.getVideopasta3();
			videopasta4 = videopasta5.getVideopasta4();
		}
		if (video5 == null) {
			video5 = new Video5();
			listaNomeArquivo = new ArrayList<>();
		}
	}

	





	public Videopasta3 getVideopasta3() {
		return videopasta3;
	}





	public void setVideopasta3(Videopasta3 videopasta3) {
		this.videopasta3 = videopasta3;
	}





	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}





	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
	}





	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}




	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
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
	
	
	
	
	

	
	public String getDescricao() {
		return descricao;
	}




	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	




	public Videopasta4 getVideopasta4() {
		return videopasta4;
	}







	public void setVideopasta4(Videopasta4 videopasta4) {
		this.videopasta4 = videopasta4;
	}








	public Video5 getVideo5() {
		return video5;
	}







	public void setVideo5(Video5 video5) {
		this.video5 = video5;
	}







	public Videopasta5 getVideopasta5() {
		return videopasta5;
	}







	public void setVideopasta5(Videopasta5 videopasta5) {
		this.videopasta5 = videopasta5;
	}
	
	







	public boolean isAtivo() {
		return ativo;
	}







	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}







	public String getNome() {
		return nome;
	}







	public void setNome(String nome) {
		this.nome = nome;
	}







	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	

	
	
	public String salvar() {
		Video5Facade video5Facade = new Video5Facade();
			for (int i = 0; i < listaNomeArquivo.size(); i++) {
				video5 = new Video5();
				video5.setVideopasta1(videopasta1);
				video5.setVideopasta2(videopasta2);
				video5.setVideopasta3(videopasta3);
				video5.setVideopasta4(videopasta4);
				video5.setVideopasta5(videopasta5);
				video5.setAtivo(ativo);
				video5.setHost(nomeArquivo() + "_" + listaNomeArquivo.get(i) + ".mov");
				video5.setDescricao(descricao);
				video5.setNome(nome);
				video5.setDataupload(new Date());
				video5.setHora(retornarHoraAtual());
				video5 = video5Facade.salvar(video5);
			}
			RequestContext.getCurrentInstance().closeDialog(video5);
		return "";
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video5());
	}
	
	
//	public String validarDados(){
//		String mensagem = "";
//		if (nomeArquivo.length() == 0) {
//			mensagem = mensagem + " Informe o nome do video \r\n";
//		}
//		return mensagem;
//	}
	
	
	public boolean salvarArquivoFTP(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String nomeArquivo = nomeArquivo();
		nomeArquivo = nomeArquivo + "_" + new String(file.getFileName());
		String arquivo = servletContext.getRealPath("/arquivos/");
		String nomeArquivoFile = arquivo + nomeArquivo;
		String videoConverter =  String.format(nomeArquivoFile +".mov", file);
		String caminho = servletContext.getRealPath("/resources/aws.properties");
		UploadAWSS3 s3 = new UploadAWSS3("treinamento", caminho);
		File arquivoFile = s3.getFile(file, videoConverter);
		String msg = "";
		if (s3.uploadFile(arquivoFile)) {
			msg = "Arquivo: " + nomeArquivoFTP + " enviado com sucesso";
		} else {
			msg = " Erro no nome do arquivo";
		}
         FacesContext context = FacesContext.getCurrentInstance();
         context.addMessage(null, new FacesMessage(msg, ""));
         arquivoFile.delete();
        return false;
    }
	
	
	    
	public void fileUploadListener(FileUploadEvent e){
		this.file = e.getFile();
		salvarArquivoFTP();
		if (listaNomeArquivo == null) {
			listaNomeArquivo = new ArrayList<>();
		}
		String nome = e.getFile().getFileName();
		try {
			nome = new String(nome.getBytes(Charset.defaultCharset()), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		listaNomeArquivo.add(nome);
	}
	
	
	public String nomeArquivo(){
		nomeArquivoFTP = videopasta1.getIdvideopasta1() + "_" + videopasta2.getIdvideopasta2() + "_" + videopasta3.getIdvideopasta3() +
				"_" + videopasta4.getIdvideopasta4() + "_" + videopasta5.getIdvideopasta5();
		return nomeArquivoFTP;
	}
	
	
	public String retornarHoraAtual(){
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date hora = Calendar.getInstance().getTime();
	    return sdf.format(hora);
	}

}
