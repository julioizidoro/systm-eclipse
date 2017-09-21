package br.com.travelmate.managerBean.worksponsor;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
 
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.TipoArquivoProdutoFacade; 
import br.com.travelmate.facade.WorkSponsorArquivoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivos; 
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Tipoarquivoproduto;
import br.com.travelmate.model.Worksponsor;
import br.com.travelmate.model.Worksponsorarquivos;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class WorkSponsorArquivoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Worksponsorarquivos> listarArquivos;
	private Arquivos arquivos; 
	private Worksponsor worksponsor;
	private Ftpdados ftpdados; 
	private Tipoarquivoproduto tipoarquivo;
	private List<Tipoarquivoproduto> listaTipoArquivo; 

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			worksponsor = (Worksponsor) session.getAttribute("worksponsor"); 
			gerarListaTipoArquivo(); 
			if (worksponsor!=null) {
				gerarListaArquivos();
				FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
				ftpdados = new Ftpdados();
				try {
					ftpdados = ftpDadosFacade.getFTPDados();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			} else {
				listarArquivos = new ArrayList<Worksponsorarquivos>();
			}
		}
	}
 
 
	public Arquivos getArquivos() {
		return arquivos;
	}

	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	} 
 
	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Worksponsorarquivos> getListarArquivos() {
		return listarArquivos;
	}

	public void setListarArquivos(List<Worksponsorarquivos> listarArquivos) {
		this.listarArquivos = listarArquivos;
	}

	public Worksponsor getWorksponsor() {
		return worksponsor;
	}

	public void setWorksponsor(Worksponsor worksponsor) {
		this.worksponsor = worksponsor;
	}

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	} 

	public Tipoarquivoproduto getTipoarquivo() {
		return tipoarquivo;
	}

	public void setTipoarquivo(Tipoarquivoproduto tipoarquivo) {
		this.tipoarquivo = tipoarquivo;
	}

	public List<Tipoarquivoproduto> getListaTipoArquivo() {
		return listaTipoArquivo;
	}

	public void setListaTipoArquivo(List<Tipoarquivoproduto> listaTipoArquivo) {
		this.listaTipoArquivo = listaTipoArquivo;
	} 

	public void gerarListaArquivos() {
		WorkSponsorArquivoFacade workSponsorArquivoFacade = new WorkSponsorArquivoFacade();
		listarArquivos = workSponsorArquivoFacade
				.listar("SELECT w FROM Worksponsorarquivos w WHERE w.worksponsor.idworksponsor="
							+ worksponsor.getIdworksponsor()); 
		if (listarArquivos == null) {
			listarArquivos = new ArrayList<>();
		}
	}

	public String excluir(Worksponsorarquivos arquivos) {
		WorkSponsorArquivoFacade workSponsorArquivoFacade = new WorkSponsorArquivoFacade(); 
		excluirArquivoFTP(arquivos);
		workSponsorArquivoFacade.excluir(arquivos.getIdworksponsorarquivos());
		listarArquivos.remove(arquivos); 
		return "";
	}
  
	public String novoArquivo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("worksponsor", worksponsor);
		session.setAttribute("listaArquivos", listarArquivos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadWorkSponsorArquivo", options, null);
		return "";
	}

	public void retornoDialog(SelectEvent event) {
		if (event.getObject() instanceof Arquivos) {
			Worksponsorarquivos arquivos = (Worksponsorarquivos) event.getObject();
			if (arquivos.getIdworksponsorarquivos() != null) {
				Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				listarArquivos = (List<Worksponsorarquivos>) session.getAttribute("listaArquivos");
				session.removeAttribute("listaArquivos");
			}
		}
	}

	public boolean excluirArquivoFTP(Worksponsorarquivos arquivos) {
		String msg = "";
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		Ftpdados dadosFTP = null;
		try {
			dadosFTP = ftpDadosFacade.getFTPDados();
		} catch (SQLException ex) {
			Logger.getLogger(CadWorkSponsorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
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
			Logger.getLogger(CadWorkSponsorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro conectar FTP", "Erro");
		}
		try {
			String nomeArquivoFTP = arquivos.getNomeftp();
			msg = ftp.excluirArquivo(nomeArquivoFTP, "/systm/worksponsor/");
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(msg, ""));
			ftp.desconectar();
			return true;
		} catch (IOException ex) {
			Logger.getLogger(CadWorkSponsorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(null, "Erro Salvar Arquivo " + ex);
		}
		try {
			ftp.desconectar();
		} catch (IOException ex) {
			Logger.getLogger(CadWorkSponsorArquivoMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro desconectar FTP", "Erro");
		}
		return false;
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String voltarTela() {
		return "consWorkSponsor";
	}

	public String corArquivo(Arquivos arquivos) {
		String cor = "";
		if (arquivos.isSe()) {
			cor = "color:red;";
		} else {
			cor = "color:black;";
		}
		return cor;
	}   
	
	public void gerarListaTipoArquivo() {
		TipoArquivoProdutoFacade tipoArquivoFacade = new TipoArquivoProdutoFacade();
		try {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				listaTipoArquivo = tipoArquivoFacade.listar("Select t from Tipoarquivoproduto t"
						+ " where t.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getWork());
			} else
				listaTipoArquivo = tipoArquivoFacade
						.listar("Select t from Tipoarquivoproduto t where t.tipoarquivo.unidade='Sim'"
								+ " and t.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getWork());
			if (listaTipoArquivo == null) {
				listaTipoArquivo = new ArrayList<Tipoarquivoproduto>();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void editar(RowEditEvent event) {
		Worksponsorarquivos arquivos = ((Worksponsorarquivos) event.getObject());
		if (arquivos != null) {
			WorkSponsorArquivoFacade workSponsorArquivoFacade = new WorkSponsorArquivoFacade();
			arquivos.setTipoarquivo(tipoarquivo.getTipoarquivo());
			workSponsorArquivoFacade.salvar(arquivos);
			Mensagem.lancarMensagemInfo("Editado com sucesso!", "");
		}
	} 
	
	public void cancelarEdicao(RowEditEvent event) {
		Mensagem.lancarMensagemInfo("Operação cancelada!", "");
	} 
}
