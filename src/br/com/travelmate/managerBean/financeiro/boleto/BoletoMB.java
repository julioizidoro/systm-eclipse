package br.com.travelmate.managerBean.financeiro.boleto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.GerarArquivoRemessaItau;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.RemessaArquivoFacade;
import br.com.travelmate.facade.RemessaContasFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.managerBean.financeiro.relatorios.RetornoBean;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contasreceber;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Remessaarquivo;
import br.com.travelmate.model.Remessacontas;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.UploadAWSS3;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class BoletoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Contasreceber> listarSelecionados;
	private String nomearquivo;
	private String nomeFTP;
	private File file;
	private Ftp ftp;
	private Ftpdados ftpdados;
	private boolean enviarRemessa = true;
	private boolean downloadRemessa = false;
	private String nomeBotao;
	private List<RetornoBean> listaEnviada;
	private int numeroRegistros;
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			listarSelecionados = (List<Contasreceber>) session.getAttribute("listaContas"); 
			nomearquivo = Formatacao.ConvercaoDataPadrao(new Date());
			nomeFTP = nomearquivo.substring(6, 10) + nomearquivo.substring(3, 5) + nomearquivo.substring(0, 2) + ".REM";
			nomearquivo = nomeFTP;
			nomeBotao ="Enviar";
			numeroRegistros = listarSelecionados.size() * 3;
		}
	}

	public List<Contasreceber> getListarSelecionados() {
		return listarSelecionados;
	}

	public void setListarSelecionados(List<Contasreceber> listarSelecionados) {
		this.listarSelecionados = listarSelecionados;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getNomearquivo() {
		return nomearquivo;
	}

	public void setNomearquivo(String nomearquivo) {
		this.nomearquivo = nomearquivo;
	}

	public void fechardialogBoletos() {
		RequestContext.getCurrentInstance().closeDialog(null);
	}
	
	

	public int getNumeroRegistros() {
		return numeroRegistros;
	}

	public void setNumeroRegistros(int numeroRegistros) {
		this.numeroRegistros = numeroRegistros;
	}

	public String getNomeBotao() {
		return nomeBotao;
	}

	public void setNomeBotao(String nomeBotao) {
		this.nomeBotao = nomeBotao;
	}

	public String getNomeFTP() {
		return nomeFTP;
	}

	public void setNomeFTP(String nomeFTP) {
		this.nomeFTP = nomeFTP;
	}

	
	public boolean isEnviarRemessa() {
		return enviarRemessa;
	}

	public void setEnviarRemessa(boolean enviarRemessa) {
		this.enviarRemessa = enviarRemessa;
	}

	public boolean isDownloadRemessa() {
		return downloadRemessa;
	}

	public void setDownloadRemessa(boolean downloadRemessa) {
		this.downloadRemessa = downloadRemessa;
	}

	public String enviarBoleto() {
		if (nomeBotao.equalsIgnoreCase("Enviar")) {
			RemessaArquivoFacade remessaArquivoFacade = new RemessaArquivoFacade();
			RemessaContasFacade remessaContasFacade = new RemessaContasFacade();
			Remessaarquivo remessaarquivo;
			Remessacontas remessacontas;
			UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
			Unidadenegocio unidade = unidadeNegocioFacade.consultar(6);
			BancoFacade bancoFacade = new BancoFacade();
			Banco bancoFranquia = bancoFacade.getBanco("SELECT b FROM Banco b where b.idbanco=3");
			List<Contasreceber> lista = new ArrayList<Contasreceber>();
			for (int i = 0; i < listarSelecionados.size(); i++) {
				if (listarSelecionados.get(i).isSelecionado()) {
					lista.add(listarSelecionados.get(i));
				}
			}
			if (lista.size() == 0) {
				lista = listarSelecionados;
			}  
			if (lista.size() > 0) {
				GerarArquivoRemessaItau remessaItau = new GerarArquivoRemessaItau(lista, usuarioLogadoMB,
						nomearquivo, nomeFTP, unidade, bancoFranquia);
				FacesMessage msg = new FacesMessage("Enviado! ", "Disponivel para download, aperte novamente");
				FacesContext.getCurrentInstance().addMessage(null, msg); 
				FacesContext facesContext = FacesContext.getCurrentInstance();  
		        ServletContext servletContext = (ServletContext)facesContext.getExternalContext().getContext();
				//InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/remessa/" + nomearquivo);
				nomearquivo = servletContext.getRealPath("/remessa/"+nomearquivo);
				file = remessaItau.getFile();
				String caminho = nomearquivo = servletContext.getRealPath("/resources/aws.properties");
				UploadAWSS3 s3 = new UploadAWSS3("remessa", caminho);
				s3.uploadFile(file, "");
			} else {
				FacesMessage msg = new FacesMessage("Erro! ", "Nenhuma Conta Selecionada");
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
			remessaarquivo = new Remessaarquivo();
			remessaarquivo.setDataenvio(new Date());
			remessaarquivo.setUsuario(usuarioLogadoMB.getUsuario());
			remessaarquivo.setNomearquivo(file.getName());
			remessaarquivo = remessaArquivoFacade.salvar(remessaarquivo);
			for (int i = 0; i < lista.size(); i++) {
				remessacontas = new Remessacontas();
				remessacontas.setContasreceber(lista.get(i));
				remessacontas.setCodigoocorrencia("01");
				remessacontas.setRemessaarquivo(remessaarquivo);
				remessaContasFacade.salvar(remessacontas);
			}
			try {
				String url = "//remessa.systm.com.br/" + this.file.getName();
				FacesContext.getCurrentInstance().getExternalContext().redirect(url);
			} catch (IOException e) {
				  
			}
			
		}
			
		
		return "";  
	}
	
	
	
	public InputStream procurarArquivo(){
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		InputStream is = null;
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		ftp = new Ftp(ftpdados.getHostupload(), ftpdados.getUser(), ftpdados.getPassword());
		try {
			ftp.conectar();
			is = ftp.receberArquivo("", nomeFTP, "/systm/remessa/");
			ftp.desconectar();  
		} catch (IOException e) {
			System.out.println("Problema no recebimento");
			  
		}
		return is;
	}
	
	public String imprimirListaBoletosItau(){
		if(listaEnviada!=null && listaEnviada.size()>0){
			String caminhoRelatorio = "/reports/financeiro/reportboletositau.jasper";  
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
	        BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e) {
				  
			}  
	        parameters.put("logo", logo);
	        parameters.put("titulo", "Remessa Enviada");
	        parameters.put("tituloColunaData", "Data Vcto.");
	        JRDataSource jrds = new JRBeanCollectionDataSource(listaEnviada);
	        GerarRelatorio gerarRelatorio = new GerarRelatorio();
	        try {
	            gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "BoletosItau.pdf");
	        } catch (JRException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (IOException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        listaEnviada = null;
		} else {
			FacesMessage msg = new FacesMessage("Lista de remessa vazia. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Lista de remessa vazia.");
		}
        return "";
	} 
	
	public boolean btnImprimirRetorno(){
		if(listaEnviada!=null && listaEnviada.size()>0){
			return true;
		}else return false;
	}
	
}
