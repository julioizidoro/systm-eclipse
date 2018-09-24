package br.com.travelmate.managerBean.videos;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.model.Video3;
import br.com.travelmate.model.Video4;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.util.UploadAWSS3;


@Named
@ViewScoped
public class CadVideo4MB implements Serializable{

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
	private Video4 video4;
	private boolean ativo;
	private String nome;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta4 = (Videopasta4) session.getAttribute("videopasta4");
		session.removeAttribute("videopasta3");
		if (videopasta4 != null) {
			videopasta1 = videopasta4.getVideopasta1();
			videopasta2 = videopasta4.getVideopasta2();
			videopasta3 = videopasta4.getVideopasta3();
		}
		if (video4 == null) {
			video4 = new Video4();
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







	public Video4 getVideo4() {
		return video4;
	}







	public void setVideo4(Video4 video4) {
		this.video4 = video4;
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
		Video4Facade video4Facade = new Video4Facade();
			for (int i = 0; i < listaNomeArquivo.size(); i++) {
				video4 = new Video4();
				video4.setVideopasta1(videopasta1);
				video4.setVideopasta2(videopasta2);
				video4.setVideopasta3(videopasta3);
				video4.setVideopasta4(videopasta4);
				video4.setAtivo(ativo);
				video4.setHost(nomeArquivo() + "_" + listaNomeArquivo.get(i) + ".mov");
				video4.setDescricao(descricao);
				video4.setNome(nome);
				video4.setHora(retornarHoraAtual());
				video4.setDataupload(new Date());
				video4 = video4Facade.salvar(video4);
			}
			RequestContext.getCurrentInstance().closeDialog(video4);
		return "";
	}
	
	
	public void cancelar(){
		RequestContext.getCurrentInstance().closeDialog(new Video3());
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
		if (s3.uploadFile(arquivoFile, "")) {
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
				"_" + videopasta4.getIdvideopasta4();
		return nomeArquivoFTP;
	}
	
	
	public String retornarHoraAtual(){
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date hora = Calendar.getInstance().getTime();
	    return sdf.format(hora);
	}

}
