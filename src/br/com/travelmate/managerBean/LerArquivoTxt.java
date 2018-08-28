package br.com.travelmate.managerBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.swing.JOptionPane;

import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.DemipairFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.HighSchoolFacade;
import br.com.travelmate.facade.ProgramasTeensFacede;
import br.com.travelmate.facade.VistosFacade;
import br.com.travelmate.facade.VoluntariadoFacade;
import br.com.travelmate.managerBean.arquivo.CadArquivoMB;
import br.com.travelmate.managerBean.contrato.curso.DadosCurso;
import br.com.travelmate.managerBean.contrato.curso.FormaPagamentoContrato;
import br.com.travelmate.managerBean.contrato.demipair.DadosDemiPair;
import br.com.travelmate.managerBean.contrato.highschool.DadosHishSchool;
import br.com.travelmate.managerBean.contrato.teens.DadosTeens;
import br.com.travelmate.managerBean.contrato.voluntariado.DadosVoluntariado;
import br.com.travelmate.model.Aupair;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Demipair;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Highschool;
import br.com.travelmate.model.Programasteens;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.model.Vistos;
import br.com.travelmate.model.Voluntariado;
import br.com.travelmate.util.Ftp;


public class LerArquivoTxt {
	
	private String contrato = "";
    private FileWriter arquivocontrato;
    private File arquivoCurso;
    private Vendas vendas;
    private String nomePasta = "";
    private Curso curso;
    private Aupair aupair;
    private Highschool highschool;
    private Programasteens programasteens;
    private Demipair demipair;
    private Voluntariado voluntariado;
    private Vistos vistos;
	
	public LerArquivoTxt(Vendas vendas, String nomePasta) {
		this.vendas = vendas;
		this.nomePasta = nomePasta;
	}
	
	
	
	


	public String getContrato() {
		return contrato;
	}






	public void setContrato(String contrato) {
		this.contrato = contrato;
	}






	public FileWriter getArquivocontrato() {
		return arquivocontrato;
	}






	public void setArquivocontrato(FileWriter arquivocontrato) {
		this.arquivocontrato = arquivocontrato;
	}






	public File getArquivoCurso() {
		return arquivoCurso;
	}






	public void setArquivoCurso(File arquivoCurso) {
		this.arquivoCurso = arquivoCurso;
	}






	public Vendas getVendas() {
		return vendas;
	}






	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}






	public String getNomePasta() {
		return nomePasta;
	}






	public void setNomePasta(String nomePasta) {
		this.nomePasta = nomePasta;
	}






	public Curso getCurso() {
		return curso;
	}






	public void setCurso(Curso curso) {
		this.curso = curso;
	}






	public Aupair getAupair() {
		return aupair;
	}






	public void setAupair(Aupair aupair) {
		this.aupair = aupair;
	}






	public Highschool getHighschool() {
		return highschool;
	}






	public void setHighschool(Highschool highschool) {
		this.highschool = highschool;
	}






	public Programasteens getProgramasteens() {
		return programasteens;
	}






	public void setProgramasteens(Programasteens programasteens) {
		this.programasteens = programasteens;
	}






	public Demipair getDemipair() {
		return demipair;
	}






	public void setDemipair(Demipair demipair) {
		this.demipair = demipair;
	}






	public Voluntariado getVoluntariado() {
		return voluntariado;
	}






	public void setVoluntariado(Voluntariado voluntariado) {
		this.voluntariado = voluntariado;
	}






	public String ler() throws IOException {
		DadosCurso dadosCurso = null;
		DadosHishSchool dadosHighSchool = null;
		DadosTeens dadosTeens = null;
		DadosDemiPair dadosDemiPair = null;
		DadosVoluntariado dadosVoluntariado = null;
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext request = (ServletContext) facesContext.getExternalContext().getContext();
		String pasta = request.getRealPath("");
		String nomeContrato = "Contrato" + vendas.getUnidadenegocio().getIdunidadeNegocio() + vendas.getUsuario().getIdusuario() + vendas.getIdvendas() + ".html";
		File arqSaida = new File(pasta + "\\contrato\\" + nomePasta + "\\ccount.txt");
		pasta = pasta + "\\contrato\\" + nomePasta + "\\"+ nomeContrato;
		
		arquivoCurso = new File(pasta);
		arquivocontrato = new FileWriter(arquivoCurso);
//		FileReader sainfe = null;
//		try {
//			sainfe = new FileReader(arqSaida);
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		}
//
//		BufferedReader leitor = new BufferedReader(sainfe);
		FileInputStream arquivoFileInput = new FileInputStream(arqSaida);
		BufferedReader leitor = new BufferedReader(
				   new InputStreamReader(arquivoFileInput, "ISO-8859-1"));
		String linha = leitor.readLine();
		
		ContratoBean contratoBean = new ContratoBean(vendas);
		contrato = contrato + contratoBean.pegarCaminho();
		if (vendas.getProdutos().getIdprodutos() == 1) {
			CursoFacade cursoFacade = new CursoFacade();
			curso = cursoFacade.consultarCursos(vendas.getIdvendas());
			dadosCurso = new DadosCurso(curso);
			contrato = contrato + dadosCurso.dadosCurso();
		}else if(vendas.getProdutos().getIdprodutos() == 4) {
			HighSchoolFacade highSchoolFacade = new HighSchoolFacade();
			highschool = highSchoolFacade.consultarHighschool(vendas.getIdvendas());
			dadosHighSchool = new DadosHishSchool(highschool);
			contrato = contrato + dadosHighSchool.dadosCurso();
		}else if(vendas.getProdutos().getIdprodutos() == 5) {
			ProgramasTeensFacede programasTeensFacede = new ProgramasTeensFacede();
			programasteens = programasTeensFacede.find(vendas.getIdvendas());
			dadosTeens = new DadosTeens(programasteens);
			contrato = contrato + dadosTeens.dadosCurso();
		}else if(vendas.getProdutos().getIdprodutos()==20) {
			DemipairFacade demipairFacade = new DemipairFacade();
			demipair = demipairFacade.consultar(vendas.getIdvendas());
			dadosDemiPair = new DadosDemiPair(demipair);
			contrato = contrato + dadosDemiPair.dadosCurso();
		}else if(vendas.getProdutos().getIdprodutos()==16) {
			VoluntariadoFacade voluntariadoFacade = new VoluntariadoFacade();
			voluntariado = voluntariadoFacade.consultar(vendas.getIdvendas());
			dadosVoluntariado = new DadosVoluntariado(voluntariado);
			contrato = contrato + dadosVoluntariado.dadosCurso();
		}else if(vendas.getProdutos().getIdprodutos() == 3) {
			VistosFacade vistosFacade = new VistosFacade();
			vistos = vistosFacade.consultarVistos(vendas.getIdvendas());
		}
		while (linha != null) {
			String posicao = "";
			if (linha.length() > 1) {
				posicao = linha.substring(0, 2);
			}
			if (posicao.equalsIgnoreCase("tx")) {
				titulo(linha);
			} else if (posicao.equalsIgnoreCase("xx")) {
				finalTexto(linha);
			} else if (posicao.equalsIgnoreCase("br")) {
				contrato = contrato + "<br/>";
			} else {
				texto(linha);
			}
			linha = leitor.readLine();
		}
		FormaPagamentoContrato formaPagamentoContrato = new FormaPagamentoContrato(vendas);
		
		contrato = contrato + "</div></body></html>";
		arquivocontrato.write(contrato);
		arquivocontrato.close();
		leitor.close();
		salvarArquivoFTP(arquivoCurso, nomeContrato);
		return contrato;
	}
	
	public boolean salvarArquivoFTP(File arquivo, String nomeContrato) {
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
			String arquivoEnviado = ftp.enviarArquivo(arquivo, nomeContrato, "/systm/arquivos");
			if (arquivoEnviado != null) {
				msg = "Arquivo:  enviado com sucesso";
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
	
	
	public void titulo(String linha){
		contrato = contrato + "<div style=\"border: 2px solid;border-color:#9C9C9C;width:100%;\"><p style=\"margin: 1%;\"><b style=\"color:green;\">";
		contrato = contrato + linha.substring(3, linha.length());
		contrato = contrato + "</b><br/>";
	}     
	       
	
	public void texto(String linha){
		contrato = contrato + linha.substring(0, linha.length());
	}
	
	
	public void finalTexto(String linha){
		contrato = contrato + "</p></div><br/>";
	}
	        
	
      
	
	
	
}
