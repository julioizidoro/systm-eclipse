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

import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadArquivo3MB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Pasta1 pasta1;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<UploadedFile> listaFile;
	private Departamento departamento;
	private Pasta2 pasta2;
	private Arquivo3 arquivo3;
	private Pasta3 pasta3;
	private String nomeArquivo = "";
	private String nomeArquivoAviso;
	private String caminhoArquivoAviso;
	private String nomeSalvoArquivoSalvo;
	private Date dataValidade;
	private Date dataInicio;
	private GerarAvisosDocsBean gerarAvisos;
	private List<GerarAvisosDocsBean> listaGerarAvisos;
	private List<Arquivo3> listaArquivo3;
	private boolean arquivoExistente = false;
	private boolean restrito;
	private boolean arquivoEnviado = false;
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pasta3 = (Pasta3) session.getAttribute("pasta3");
		session.removeAttribute("pasta3");
		if (pasta3 != null) {
			pasta2 = pasta3.getPasta2();
			pasta1 = pasta3.getPasta1();
			departamento = pasta1.getDepartamento();
		}
		if (arquivo3 == null) {
			arquivo3 = new Arquivo3();
			listaNomeArquivo = new ArrayList<>();
			arquivo3.setDatainicio(new Date());
		}
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
	 * @param pasta1 the pasta1 to set
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
	 * @param pasta2 the pasta2 to set
	 */
	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
	}



	/**
	 * @return the arquivo3
	 */
	public Arquivo3 getArquivo3() {
		return arquivo3;
	}



	/**
	 * @param arquivo3 the arquivo3 to set
	 */
	public void setArquivo3(Arquivo3 arquivo3) {
		this.arquivo3 = arquivo3;
	}



	/**
	 * @return the pasta3
	 */
	public Pasta3 getPasta3() {
		return pasta3;
	}



	/**
	 * @param pasta3 the pasta3 to set
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



	public String getNomeSalvoArquivoSalvo() {
		return nomeSalvoArquivoSalvo;
	}



	public void setNomeSalvoArquivoSalvo(String nomeSalvoArquivoSalvo) {
		this.nomeSalvoArquivoSalvo = nomeSalvoArquivoSalvo;
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

	
	


	public List<Arquivo3> getListaArquivo3() {
		return listaArquivo3;
	}



	public void setListaArquivo3(List<Arquivo3> listaArquivo3) {
		this.listaArquivo3 = listaArquivo3;
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
		nomeArquivoFTP = departamento.getIddepartamento()+ "_" + pasta1.getIdpasta1() + "_" + pasta2.getIdpasta2() + "_" + pasta3.getIdpasta3();
		return nomeArquivoFTP;
	}
	
	
	public boolean salvarArquivoFTP(){
		String msg = "";
        FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
        Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadArquivo3MB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CadArquivo3MB.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CadArquivo3MB.class.getName()).log(Level.SEVERE, null, ex);
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
	
	 
	public String salvar(){
			Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
			listaGerarAvisos = new ArrayList<>();
			if (listaNomeArquivo == null || listaNomeArquivo.isEmpty()) {
				Mensagem.lancarMensagemInfo("Atenção", "você não fez nenhum upload de arquivo!!");
			}else{
				for (int j = 0; j < listaNomeArquivo.size(); j++) {
					if (arquivoExistente) {
						for (int i = 0; i < listaArquivo3.size(); i++) {
							arquivo3Facade.excluir(listaArquivo3.get(i).getIdarquivo3());
						}
						arquivoExistente = false;
					}
					arquivo3 = new Arquivo3();
					arquivo3.setPasta3(pasta3);
					arquivo3.setPasta2(pasta2);
					arquivo3.setPasta1(pasta1);
					arquivo3.setDatavalidade(dataValidade);
					arquivo3.setRestrito(restrito);
					arquivo3.setNomeftp(nomeArquivo() + "_" + listaNomeArquivo.get(j).trim());
					arquivo3.setTipo(gerarTipoArquivo(listaNomeArquivo.get(j)));
					if (nomeArquivo == null || nomeArquivo.equalsIgnoreCase("")) {
						arquivo3.setNome(listaNomeArquivo.get(j));
					}else{
						arquivo3.setNome(nomeArquivo);
					}
					if (dataInicio == null) {
						arquivo3.setDatainicio(new Date());
					}else{
						arquivo3.setDatainicio(dataInicio);
					}
					arquivo3.setHora(retornarHoraAtual());
					arquivo3.setDataupload(new Date());
					arquivo3 = arquivo3Facade.salvar(arquivo3);
					gerarAvisos = new GerarAvisosDocsBean();
					gerarAvisos.setCaminhoArquivoAviso(gerarCaminhoArquivo(arquivo3));
					gerarAvisos.setNomeArquivoAviso(arquivo3.getNome());
					gerarAvisos.setNomeSalvoArquivoAviso(arquivo3.getNomeftp());
					gerarAvisos.setRestrito(arquivo3.isRestrito());
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
	
	public String gerarCaminhoArquivo(Arquivo3 arquivo){
		String caminho = arquivo.getPasta1().getDepartamento().getNome() + "\\" + arquivo.getPasta1().getNome() + "\\" + arquivo.getPasta2().getNome()
				  + "\\"  + arquivo.getPasta3().getNome() + "\\" + arquivo.getNome();
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
		nomeArquivo = nomeArquivo() + "_" +nomeArquivo;
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		listaArquivo3 = arquivo3Facade.listar("Select a From Arquivo3 a Where a.nomeftp='" + nomeArquivo + "'");
		if (listaArquivo3 == null || listaArquivo3.isEmpty()) {
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
