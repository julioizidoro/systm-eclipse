package br.com.travelmate.managerBean.controleDocsVideos;

import java.io.IOException;
import java.io.Serializable;
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
import javax.swing.JOptionPane;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.FornecedorCidadeDocsFacade;
import br.com.travelmate.facade.FornecedorDocsFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cloud.midia.Pastas2Arquivo1MB;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Fornecedorcidadedocs;
import br.com.travelmate.model.Fornecedordocs;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;



@Named
@ViewScoped
public class ControleDocsVideosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private ArquivoDocsBean arquivoDocsBean;
	private List<ArquivoDocsBean> listaArquivo;
	private List<ListaArquivoDocsBean> listaArquivoDocs;
	private Fornecedorcidadedocs fornecedorcidadedocs;
	private List<Fornecedorcidadedocs> listaFornecedorCidadeDocs;
	private List<Arquivo1> listaArquivo1;
	private List<Arquivo2> listaArquivo2;
	private List<Arquivo3> listaArquivo3;
	private List<Arquivo4> listaArquivo4;
	private List<Arquivo5> listaArquivo5;
	private ListaArquivoDocsBean arquivolistaBean;
	private Ftpdados ftpDados;
	private boolean todosdocumentos;
	private boolean todosdocumentosfornecedor;
	private Date datainicio;
	private Date dataTermino;
	private String urlArquivo = "";
	
	
	
	@PostConstruct
	public void init(){
		if (listaArquivo == null) {
			listaArquivo = new ArrayList<>();
		}
		buscarFtpDados();
		gerarListaArquivo1();
		gerarListaArquivo2();
		gerarListaArquivo3();
		gerarListaArquivo4();
		gerarListaArquivo5();
		gerarListaDocsFornecedorCidade();
	}



	public ArquivoDocsBean getArquivoDocsBean() {
		return arquivoDocsBean;
	}



	public void setArquivoDocsBean(ArquivoDocsBean arquivoDocsBean) {
		this.arquivoDocsBean = arquivoDocsBean;
	}



	public List<ArquivoDocsBean> getListaArquivo() {
		return listaArquivo;
	}



	public void setListaArquivo(List<ArquivoDocsBean> listaArquivo) {
		this.listaArquivo = listaArquivo;
	}



	public List<ListaArquivoDocsBean> getListaArquivoDocs() {
		return listaArquivoDocs;
	}



	public void setListaArquivoDocs(List<ListaArquivoDocsBean> listaArquivoDocs) {
		this.listaArquivoDocs = listaArquivoDocs;
	}



	public Fornecedorcidadedocs getFornecedorcidadedocs() {
		return fornecedorcidadedocs;
	}



	public void setFornecedorcidadedocs(Fornecedorcidadedocs fornecedorcidadedocs) {
		this.fornecedorcidadedocs = fornecedorcidadedocs;
	}



	public List<Fornecedorcidadedocs> getListaFornecedorCidadeDocs() {
		return listaFornecedorCidadeDocs;
	}



	public void setListaFornecedorCidadeDocs(List<Fornecedorcidadedocs> listaFornecedorCidadeDocs) {
		this.listaFornecedorCidadeDocs = listaFornecedorCidadeDocs;
	}



	public List<Arquivo1> getListaArquivo1() {
		return listaArquivo1;
	}



	public void setListaArquivo1(List<Arquivo1> listaArquivo1) {
		this.listaArquivo1 = listaArquivo1;
	}



	public List<Arquivo2> getListaArquivo2() {
		return listaArquivo2;
	}



	public void setListaArquivo2(List<Arquivo2> listaArquivo2) {
		this.listaArquivo2 = listaArquivo2;
	}



	public List<Arquivo3> getListaArquivo3() {
		return listaArquivo3;
	}



	public void setListaArquivo3(List<Arquivo3> listaArquivo3) {
		this.listaArquivo3 = listaArquivo3;
	}



	public List<Arquivo4> getListaArquivo4() {
		return listaArquivo4;
	}



	public void setListaArquivo4(List<Arquivo4> listaArquivo4) {
		this.listaArquivo4 = listaArquivo4;
	}



	public List<Arquivo5> getListaArquivo5() {
		return listaArquivo5;
	}



	public void setListaArquivo5(List<Arquivo5> listaArquivo5) {
		this.listaArquivo5 = listaArquivo5;
	}
	
	
	
	public ListaArquivoDocsBean getArquivolistaBean() {
		return arquivolistaBean;
	}



	public void setArquivolistaBean(ListaArquivoDocsBean arquivolistaBean) {
		this.arquivolistaBean = arquivolistaBean;
	}



	public Ftpdados getFtpDados() {
		return ftpDados;
	}



	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}



	public boolean isTodosdocumentos() {
		return todosdocumentos;
	}



	public void setTodosdocumentos(boolean todosdocumentos) {
		this.todosdocumentos = todosdocumentos;
	}



	public boolean isTodosdocumentosfornecedor() {
		return todosdocumentosfornecedor;
	}



	public void setTodosdocumentosfornecedor(boolean todosdocumentosfornecedor) {
		this.todosdocumentosfornecedor = todosdocumentosfornecedor;
	}



	public Date getDatainicio() {
		return datainicio;
	}



	public void setDatainicio(Date datainicio) {
		this.datainicio = datainicio;
	}



	public Date getDataTermino() {
		return dataTermino;
	}



	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}



	public String getUrlArquivo() {
		return urlArquivo;
	}



	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}



	public void gerarListaArquivo1(){
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		if (datainicio != null && dataTermino != null) {
			listaArquivo1 = arquivo1Facade.listar("Select a From Arquivo1 a Where a.datavalidade>='" + Formatacao.ConvercaoDataSql(datainicio) + "'"
					+ " and a.datavalidade<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'");
		}else {
			listaArquivo1 = arquivo1Facade.listar("Select a From Arquivo1 a WHERE  a.datavalidade<='" + Formatacao.ConvercaoDataSql(new Date()) + "'");
		}
		if (listaArquivo1 == null) {
			listaArquivo1 = new ArrayList<>();
		}
		for (int i = 0; i < listaArquivo1.size(); i++) {
			arquivoDocsBean = new ArquivoDocsBean();
			arquivoDocsBean.setIdArquivoDocs(listaArquivo1.get(i).getIdarquivo1());
			arquivoDocsBean.setTipoArquivo("arquivo1");
			arquivoDocsBean.setDatainicio(listaArquivo1.get(i).getDatainicio());
			arquivoDocsBean.setDatavalidade(listaArquivo1.get(i).getDatavalidade());
			arquivoDocsBean.setNome(listaArquivo1.get(i).getNome());
			arquivoDocsBean.setNomeftp(listaArquivo1.get(i).getNomeftp());
			arquivoDocsBean.setTipo(listaArquivo1.get(i).getTipo());
			arquivoDocsBean.setCaminho(listaArquivo1.get(i).getPasta1().getDepartamento().getNome() + "/" + listaArquivo1.get(i).getPasta1().getNome());
			
			listaArquivo.add(arquivoDocsBean);
		}
	}
	
	
	
	public void gerarListaArquivo2(){
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		if (datainicio != null && dataTermino != null) {
			listaArquivo2 = arquivo2Facade.listar("Select a From Arquivo2 a Where a.datavalidade>='" + Formatacao.ConvercaoDataSql(datainicio) + "'"
					+ " and a.datavalidade<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'");
		}else {
			listaArquivo2 = arquivo2Facade.listar("Select a From Arquivo2 a WHERE  a.datavalidade<='" + Formatacao.ConvercaoDataSql(new Date()) + "'");
		}
		if (listaArquivo2 == null) {
			listaArquivo2 = new ArrayList<>();
		}
		for (int i = 0; i < listaArquivo2.size(); i++) {
			arquivoDocsBean = new ArquivoDocsBean();
			arquivoDocsBean.setIdArquivoDocs(listaArquivo2.get(i).getIdarquivo2());
			arquivoDocsBean.setTipoArquivo("arquivo2");
			arquivoDocsBean.setDatainicio(listaArquivo2.get(i).getDatainicio());
			arquivoDocsBean.setDatavalidade(listaArquivo2.get(i).getDatavalidade());
			arquivoDocsBean.setNome(listaArquivo2.get(i).getNome());
			arquivoDocsBean.setNomeftp(listaArquivo2.get(i).getNomeftp());
			arquivoDocsBean.setTipo(listaArquivo2.get(i).getTipo());
			arquivoDocsBean.setCaminho(listaArquivo2.get(i).getPasta1().getDepartamento().getNome() + "/" + listaArquivo2.get(i).getPasta1().getNome() +
				"/" + listaArquivo2.get(i).getPasta2().getNome());
			
			listaArquivo.add(arquivoDocsBean);
		}
	}
	
	
	public void gerarListaArquivo3(){
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		if (datainicio != null && dataTermino != null) {
			listaArquivo3 = arquivo3Facade.listar("Select a From Arquivo3 a Where a.datavalidade>='" + Formatacao.ConvercaoDataSql(datainicio) + "'"
					+ " and a.datavalidade<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'");
		}else {
			listaArquivo3 = arquivo3Facade.listar("Select a From Arquivo3 a WHERE  a.datavalidade<='" + Formatacao.ConvercaoDataSql(new Date()) + "'");
		}
		if (listaArquivo3 == null) {
			listaArquivo3 = new ArrayList<>();
		}
		for (int i = 0; i < listaArquivo3.size(); i++) {
			arquivoDocsBean = new ArquivoDocsBean();
			arquivoDocsBean.setIdArquivoDocs(listaArquivo3.get(i).getIdarquivo3());
			arquivoDocsBean.setTipoArquivo("arquivo3");
			arquivoDocsBean.setDatainicio(listaArquivo3.get(i).getDatainicio());
			arquivoDocsBean.setDatavalidade(listaArquivo3.get(i).getDatavalidade());
			arquivoDocsBean.setNome(listaArquivo3.get(i).getNome());
			arquivoDocsBean.setNomeftp(listaArquivo3.get(i).getNomeftp());
			arquivoDocsBean.setTipo(listaArquivo3.get(i).getTipo());
			arquivoDocsBean.setCaminho(listaArquivo3.get(i).getPasta1().getDepartamento().getNome() + "/" + listaArquivo3.get(i).getPasta1().getNome() +
				"/" + listaArquivo3.get(i).getPasta2().getNome() + "/" + listaArquivo3.get(i).getPasta3().getNome());
			
			listaArquivo.add(arquivoDocsBean);
		}
	}
	
	
	public void gerarListaArquivo4(){
		Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
		if (datainicio != null && dataTermino != null) {
			listaArquivo4 = arquivo4Facade.listar("Select a From Arquivo4 a Where a.datavalidade>='" + Formatacao.ConvercaoDataSql(datainicio) + "'"
					+ " and a.datavalidade<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'");
		}else {
			listaArquivo4 = arquivo4Facade.listar("Select a From Arquivo4 a WHERE  a.datavalidade<='" + Formatacao.ConvercaoDataSql(new Date()) + "'");
		}
		if (listaArquivo4 == null) {
			listaArquivo4 = new ArrayList<>();
		}
		for (int i = 0; i < listaArquivo4.size(); i++) {
			arquivoDocsBean = new ArquivoDocsBean();
			arquivoDocsBean.setIdArquivoDocs(listaArquivo4.get(i).getIdarquivo4());
			arquivoDocsBean.setTipoArquivo("arquivo4");
			arquivoDocsBean.setDatainicio(listaArquivo4.get(i).getDatainicio());
			arquivoDocsBean.setDatavalidade(listaArquivo4.get(i).getDatavalidade());
			arquivoDocsBean.setNome(listaArquivo4.get(i).getNome());
			arquivoDocsBean.setNomeftp(listaArquivo4.get(i).getNomeftp());
			arquivoDocsBean.setTipo(listaArquivo4.get(i).getTipo());
			arquivoDocsBean.setCaminho(listaArquivo4.get(i).getPasta1().getDepartamento().getNome() + listaArquivo4.get(i).getPasta4().getNome() + "/" +
					listaArquivo4.get(i).getPasta3().getNome()  + "/" + listaArquivo4.get(i).getPasta2().getNome() + "/" + listaArquivo4.get(i).getPasta1().getNome());
			
			listaArquivo.add(arquivoDocsBean);
		}
	}
	
	
	
	public void gerarListaArquivo5(){
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		if (datainicio != null && dataTermino != null) {
			listaArquivo5 = arquivo5Facade.listar("Select a From Arquivo5 a Where a.datavalidade>='" + Formatacao.ConvercaoDataSql(datainicio) + "'"
					+ " and a.datavalidade<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'");
		}else {
			listaArquivo5 = arquivo5Facade.listar("Select a From Arquivo5  a WHERE a.datavalidade<='" + Formatacao.ConvercaoDataSql(new Date()) + "'");
		}
		if (listaArquivo5 == null) {
			listaArquivo5 = new ArrayList<>();
		}
		for (int i = 0; i < listaArquivo5.size(); i++) {
			arquivoDocsBean = new ArquivoDocsBean();
			arquivoDocsBean.setIdArquivoDocs(listaArquivo5.get(i).getIdarquivo5());
			arquivoDocsBean.setTipoArquivo("arquivo5");
			arquivoDocsBean.setDatainicio(listaArquivo5.get(i).getDatainicio());
			arquivoDocsBean.setDatavalidade(listaArquivo5.get(i).getDatavalidade());
			arquivoDocsBean.setNome(listaArquivo5.get(i).getNome());
			arquivoDocsBean.setNomeftp(listaArquivo5.get(i).getNomeftp());
			arquivoDocsBean.setTipo(listaArquivo5.get(i).getTipo());
			arquivoDocsBean.setCaminho(listaArquivo5.get(i).getPasta1().getDepartamento().getNome() + "/" + listaArquivo5.get(i).getPasta5().getNome()
					+ listaArquivo5.get(i).getPasta4().getNome() + "/" + listaArquivo5.get(i).getPasta3().getNome()  + "/" + listaArquivo5.get(i).getPasta2().getNome() 
					+ "/" + listaArquivo5.get(i).getPasta1().getNome());
			
			listaArquivo.add(arquivoDocsBean);
		}
	}
	
	
	public void gerarListaDocsFornecedorCidade(){
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		String sql = "";
		if (datainicio != null && dataTermino != null) {
			 sql = "Select f From Fornecedorcidadedocs f Where f.fornecedordocs.datavalidade>='"+ Formatacao.ConvercaoDataSql(datainicio) + "'" +
					 " and f.fornecedordocs.datavalidade<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'"; 
		}else {
			 sql = "Select f From Fornecedorcidadedocs f Where  f.fornecedordocs.datavalidade<='" + Formatacao.ConvercaoDataSql(new Date()) + "'"; 
		}
		listaFornecedorCidadeDocs = fornecedorCidadeDocsFacade.listar(sql);
		if (listaFornecedorCidadeDocs == null) {
			listaFornecedorCidadeDocs = new ArrayList<>();
		}
	}
	
	public String trocarCorLinhaTabelaFornecedor(Fornecedorcidadedocs fornecedorcidadedocs){
		String cor = "background:#FFFFFF;";
		if (fornecedorcidadedocs != null) {
			if ((fornecedorcidadedocs.getIdfornecedorcidadedocs() % 2) == 0) {
				cor = "background:#FFFFFF;";
			}else{
				cor = "background:#F3F3F3";
			}	
		}
		return cor;
	}
	
	
	public String trocarCorLinhaTabela(ArquivoDocsBean arquivoDocsBean){
		String cor = "background:#FFFFFF;";
		if (arquivoDocsBean != null) {
			if ((arquivoDocsBean.getIdArquivoDocs() % 2) == 0) {
				cor = "background:#FFFFFF;";
			}else{
				cor = "background:#F3F3F3";
			}	
		}
		return cor;
	}
	
	public void buscarFtpDados(){
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpDados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ftpDados != null) {
			urlArquivo = ftpDados.getProtocolo() + "://" + ftpDados.getHost() +  ":82/cloud/departamentos";
		}
	}
	
	
	public String retornaIconeDocumento(String nomeArquivo) {
		if (nomeArquivo == "") {
			return "";
		}
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length() > 5) {
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".jpg") || extensao.equalsIgnoreCase(".jpeg")
				|| extensao.equalsIgnoreCase(".png")) {
			return "../../resources/img/icone_jpg.png";
		} else if (extensao.equalsIgnoreCase(".pdf")) {
			return "../../resources/img/icone_pdf.png";
		} else if (extensao.equalsIgnoreCase(".docx") || extensao.equalsIgnoreCase(".doc")) {
			return "../../resources/img/icone_docx.png";
		} else if (extensao.equalsIgnoreCase(".xls") || extensao.equalsIgnoreCase(".xlsx")) {
			return "../../resources/img/icone_xls.png";
		} else if (extensao.equalsIgnoreCase(".txt")) {
			return "../../resources/img/icone_txt.png";
		} else if (extensao.equalsIgnoreCase(".cdr")) {
			return "../../resources/img/icone_cdr.png";
		} else if (extensao.equalsIgnoreCase(".eps")) {
			return "../../resources/img/icone_eps.png";
		} else if (extensao.equalsIgnoreCase(".bmp")) {
			return "../../resources/img/icone_bmp.png";
		} else if (extensao.equalsIgnoreCase(".pptx")) {
			return "../../resources/img/icone_pptx.png";
		} else if (extensao.equalsIgnoreCase(".wma")) {
			return "../../resources/img/iconewma.png";
		}else if(extensao.equalsIgnoreCase(".ppsx")){
			return "../../resources/img/icone_ppsx.png";
		} else if(extensao.equalsIgnoreCase(".mov")){
			return "../../resources/img/video.png";
		}  
		return "";
	}
	
	
	public String excluir() {
		for (int i = 0; i < listaArquivo.size(); i++) {
			if (listaArquivo.get(i).isSelecionado()) {
				if (listaArquivo.get(i).getTipoArquivo().equalsIgnoreCase("arquivo1")) {
					excluirArquivo1(listaArquivo.get(i));
				}else if(listaArquivo.get(i).getTipoArquivo().equalsIgnoreCase("arquivo2")){
					excluirArquivo2(listaArquivo.get(i));
				}else if(listaArquivo.get(i).getTipoArquivo().equalsIgnoreCase("arquivo3")){
					excluirArquivo3(listaArquivo.get(i));
				}else if(listaArquivo.get(i).getTipoArquivo().equalsIgnoreCase("arquivo4")){
					excluirArquivo4(listaArquivo.get(i));
				}else{
					excluirArquivo5(listaArquivo.get(i));
				}
			}
		}
		listaArquivo = new ArrayList<>();
		gerarListaArquivo1();
		gerarListaArquivo2();
		gerarListaArquivo3();
		gerarListaArquivo4();
		gerarListaArquivo5();
		Mensagem.lancarMensagemInfo("Excluido com Sucesso", "");
		return "";
	}
	
	public void excluirArquivo1(ArquivoDocsBean arquivo){
		Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
		arquivo1Facade.excluir(arquivo.getIdArquivoDocs());
	}
	
	public void excluirArquivo2(ArquivoDocsBean arquivo){
		Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
		arquivo2Facade.excluir(arquivo.getIdArquivoDocs());
	}
	
	public void excluirArquivo3(ArquivoDocsBean arquivo){
		Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
		arquivo3Facade.excluir(arquivo.getIdArquivoDocs());
	}
	
	public void excluirArquivo4(ArquivoDocsBean arquivo){
		Arquivo4Facade cloudFilesFacade = new Arquivo4Facade();
		cloudFilesFacade.excluir(arquivo.getIdArquivoDocs());
	}
	
	public void excluirArquivo5(ArquivoDocsBean arquivo){
		Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
		arquivo5Facade.excluir(arquivo.getIdArquivoDocs());
	}
	
	
	public boolean excluirArquivoFTP(String nome, String pasta) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro", "");
		}
		if (dadosFTP == null) {
			return false;
		}
		Ftp ftp = new Ftp(dadosFTP.getHost(), dadosFTP.getUser(), dadosFTP.getPassword());
		try {
			if (!ftp.conectar()) {
				mostrarMensagem(null, "Erro conectar FTP", "");
				return false;
			}
		} catch (IOException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = nome;
			msg = ftp.excluirArquivo(nomeArquivoFTP, pasta);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			return true;
		} catch (IOException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(Pastas2Arquivo1MB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}
	
	
	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}
	
	
	public void excluirArquivoFornecedor(){
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		for (int i = 0; i < listaFornecedorCidadeDocs.size(); i++) {
			if (listaFornecedorCidadeDocs.get(i).isSelecionado()) {
				fornecedorCidadeDocsFacade.excluir(listaFornecedorCidadeDocs.get(i).getIdfornecedorcidadedocs());
			}
		}
		listaFornecedorCidadeDocs = new ArrayList<>();
		gerarListaDocsFornecedorCidade();
		Mensagem.lancarMensagemInfo("Excluido com Sucesso", "");
	}
	
	
	public void selecionarTodosVencidos() {
		for (int i = 0; i < listaArquivo.size(); i++) {
			listaArquivo.get(i).setSelecionado(todosdocumentos);
		}
	}
	
	
	public void selecionarTodosVencidosFornecedor() {
		for (int i = 0; i < listaFornecedorCidadeDocs.size(); i++) {
			listaFornecedorCidadeDocs.get(i).setSelecionado(todosdocumentosfornecedor);
		}
	}
	
	
	public void pesquisarLista() {
		 listaArquivo = new ArrayList<>();
		buscarFtpDados();
		gerarListaArquivo1();
		gerarListaArquivo2();
		gerarListaArquivo3();
		gerarListaArquivo4();
		gerarListaArquivo5();
		gerarListaDocsFornecedorCidade();
	}
	
	public void limpar() {
		listaArquivo = new ArrayList<>();
	}

}
