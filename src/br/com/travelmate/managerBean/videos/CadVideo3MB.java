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

import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.model.Video3;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.util.UploadAWSS3;

@Named
@ViewScoped
public class CadVideo3MB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Video3 video3;
	private Videopasta1 videopasta1;
	private Videopasta2 videopasta2;
	private Videopasta3 videopasta3;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private String descricao;
	private boolean ativo;
	private String nome;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		videopasta3 = (Videopasta3) session.getAttribute("videopasta3");
		session.removeAttribute("videopasta3");
		if (videopasta3 != null) {
			videopasta1 = videopasta3.getVideopasta1();
			videopasta2 = videopasta3.getVideopasta2();
		}
		if (video3 == null) {
			video3 = new Video3();
			listaNomeArquivo = new ArrayList<>();
		}
	}

	public Video3 getVideo3() {
		return video3;
	}

	public void setVideo3(Video3 video3) {
		this.video3 = video3;
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

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String salvar() {
		Video3Facade video3Facade = new Video3Facade();
		for (int i = 0; i < listaNomeArquivo.size(); i++) {
			video3 = new Video3();
			video3.setVideopasta1(videopasta1);
			video3.setVideopasta2(videopasta2);
			video3.setVideopasta3(videopasta3);
			video3.setAtivo(ativo);
			video3.setHost(nomeArquivo() + "_" + listaNomeArquivo.get(i) + ".mov");
			video3.setDescricao(descricao);
			video3.setNome(nome);
			video3.setHora(retornarHoraAtual());
			video3.setDataupload(new Date());
			video3 = video3Facade.salvar(video3);
		}
		RequestContext.getCurrentInstance().closeDialog(video3);
		return "";
	}

	public void cancelar() {
		RequestContext.getCurrentInstance().closeDialog(new Video3());
	}

	// public String validarDados(){
	// String mensagem = "";
	// if (nomeArquivo.length() == 0) {
	// mensagem = mensagem + " Informe o nome do video \r\n";
	// }
	// return mensagem;
	// }

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

	public void fileUploadListener(FileUploadEvent e) {
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

	public String nomeArquivo() {
		nomeArquivoFTP = videopasta1.getIdvideopasta1() + "_" + videopasta2.getIdvideopasta2() + "_"
				+ videopasta3.getIdvideopasta3();
		return nomeArquivoFTP;
	}
	
	
	public String retornarHoraAtual(){
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	    Date hora = Calendar.getInstance().getTime();
	    return sdf.format(hora);
	}

}
