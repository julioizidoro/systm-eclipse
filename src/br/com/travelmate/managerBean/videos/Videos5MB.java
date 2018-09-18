package br.com.travelmate.managerBean.videos;

import java.io.IOException;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.managerBean.cloud.midia.Pastas2Arquivo1MB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Video2;
import br.com.travelmate.model.Video5;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.model.Videopasta5;
import br.com.travelmate.util.Ftp;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;


@Named
@ViewScoped
public class Videos5MB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private Videopasta1 videopasta1;
	private Ftpdados ftpDados;
	private boolean semPastaArquivo = false;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private boolean tabelaArquivoLista = false;
	private boolean tabelaArquivoQuadro = true;
	private boolean pesquisar;
	private String tituloPagina;
	private String tipoArquivo;
	private String nomeArquivo;
	private List<Video5> listaVideo5;
	private Videopasta2 videopasta2;
	private Videopasta3 videopasta3;
	private Videopasta4 videopasta4;
	private Videopasta5 videopasta5;
	private List<Video5Bean> listaVideo5Bean;
	private Video5Bean video5Bean;
	private ArquivoBean arquivoBean;
	private String urlArquivo = "";
	
	
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			videopasta5 = (Videopasta5) session.getAttribute("videopasta5");
			session.removeAttribute("videopasta5");
			if (videopasta5 != null) {
				videopasta1 = videopasta5.getVideopasta1();
				videopasta2 = videopasta5.getVideopasta2();
				videopasta3 = videopasta5.getVideopasta3();
				videopasta4 = videopasta5.getVideopasta4();
				departamento = videopasta1.getDepartamento();
				gerarVideos5();
				ftpDados = new Ftpdados();
				FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
				try {
					ftpDados = ftpDadosFacade.getFTPDados();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				if (ftpDados != null) {
					urlArquivo = ftpDados.getProtocolo() + "://" + ftpDados.getHost() +  ":"+ ftpDados.getWww() +"/videos";
				}
				// Verificar se existe pastas ou arquivos na tela
				semConteudo();
				verificarExibicao();
			}else{
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("consDepartamentos1Videos.jsf");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



	
	public Departamento getDepartamento() {
		return departamento;
	}




	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}




	public Videopasta1 getVideopasta1() {
		return videopasta1;
	}




	public void setVideopasta1(Videopasta1 videopasta1) {
		this.videopasta1 = videopasta1;
	}

	




	public List<Video5> getListaVideo5() {
		return listaVideo5;
	}




	public void setListaVideo5(List<Video5> listaVideo5) {
		this.listaVideo5 = listaVideo5;
	}




	public Ftpdados getFtpDados() {
		return ftpDados;
	}




	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}




	public boolean isSemPastaArquivo() {
		return semPastaArquivo;
	}




	public void setSemPastaArquivo(boolean semPastaArquivo) {
		this.semPastaArquivo = semPastaArquivo;
	}




	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}




	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}




	public boolean isTabelaArquivoLista() {
		return tabelaArquivoLista;
	}




	public void setTabelaArquivoLista(boolean tabelaArquivoLista) {
		this.tabelaArquivoLista = tabelaArquivoLista;
	}




	public boolean isTabelaArquivoQuadro() {
		return tabelaArquivoQuadro;
	}




	public void setTabelaArquivoQuadro(boolean tabelaArquivoQuadro) {
		this.tabelaArquivoQuadro = tabelaArquivoQuadro;
	}




	public boolean isPesquisar() {
		return pesquisar;
	}




	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}




	public String getTituloPagina() {
		return tituloPagina;
	}




	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}




	public String getTipoArquivo() {
		return tipoArquivo;
	}




	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}




	public String getNomeArquivo() {
		return nomeArquivo;
	}




	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}



	public Videopasta2 getVideopasta2() {
		return videopasta2;
	}




	public void setVideopasta2(Videopasta2 videopasta2) {
		this.videopasta2 = videopasta2;
	}




	public Videopasta3 getVideopasta3() {
		return videopasta3;
	}




	public void setVideopasta3(Videopasta3 videopasta3) {
		this.videopasta3 = videopasta3;
	}




	public Videopasta4 getVideopasta4() {
		return videopasta4;
	}




	public void setVideopasta4(Videopasta4 videopasta4) {
		this.videopasta4 = videopasta4;
	}




	public Videopasta5 getVideopasta5() {
		return videopasta5;
	}




	public void setVideopasta5(Videopasta5 videopasta5) {
		this.videopasta5 = videopasta5;
	}
	
	




//	public String descritivoVideo5() {
//		FacesContext fc = FacesContext.getCurrentInstance();
//		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
//		Map<String, Object> options = new HashMap<String, Object>();
//		options.put("contentWidth", 400);
//		session.setAttribute("videopasta5", videopasta5);
//		RequestContext.getCurrentInstance().openDialog("cadPastaVideos5", options, null);
//		return "";
//	}
	
	
	public String getUrlArquivo() {
		return urlArquivo;
	}




	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}




	public List<Video5Bean> getListaVideo5Bean() {
		return listaVideo5Bean;
	}




	public void setListaVideo5Bean(List<Video5Bean> listaVideo5Bean) {
		this.listaVideo5Bean = listaVideo5Bean;
	}




	public Video5Bean getVideo5Bean() {
		return video5Bean;
	}




	public void setVideo5Bean(Video5Bean video5Bean) {
		this.video5Bean = video5Bean;
	}




	public String cadastroVideo5() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 550);
		session.setAttribute("videopasta5", videopasta5);
		RequestContext.getCurrentInstance().openDialog("cadVideo5", options, null);
		return "";
	}
	
	
	public void gerarVideos5() {
		Video5Facade video5Facade = new Video5Facade();
		String sql = "Select c from Video5 c where c.videopasta1.idvideopasta1=" + videopasta1.getIdvideopasta1()
				+ " and c.videopasta2.idvideopasta2=" + videopasta2.getIdvideopasta2() + " and c.videopasta3.idvideopasta3=" + + videopasta3.getIdvideopasta3()
				+ " and c.videopasta4.idvideopasta4=" + videopasta4.getIdvideopasta4() + " and c.videopasta5.idvideopasta5=" + videopasta5.getIdvideopasta5();

		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
			sql = sql + " and c.ativo=true";
		}
		sql = sql + " order by c.nome";
		listaVideo5 = video5Facade.listar(sql);
		if (listaVideo5 == null) {
			listaVideo5 = new ArrayList<Video5>();
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;
			listaVideo5Bean = new ArrayList<>();
		}
//		for (int i = 0; i < listaVideo5.size(); i++) {
//			video5Bean = new Video5Bean();
//			video5Bean.setVideo1(listaVideo5.get(i));
//			if ((i + 1) < listaVideo5.size()) {
//				video5Bean.setVideo2(listaVideo5.get(i + 1));
//				i++;
//				if ((i + 1) < listaVideo5.size()) {
//					video5Bean.setVideo3(listaVideo5.get(i + 1));
//					i++;
//				} else {
//					video5Bean.setVideo3(null);
//				}
//
//			} else {
//				video5Bean.setVideo2(null);
//				video5Bean.setVideo3(null);
//			}
//
//			listaVideo5Bean.add(video5Bean);
//		}
	}


	public String pegarNomeArquivo(Video5 video5) {
		if (video5 != null) {
			String nome = video5.getDescricao();
			return nome;
		}
		return "";
	}

	public String voltarConsVideoPasta1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1Video";
	}

	public String voltarConsVideoPasta2() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("videopasta1", videopasta1);
		return "consPastas2Videos1";
	}
	
	public String voltarConsVideoPasta3() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("videopasta2", videopasta2);
		return "consPastas3Videos2";
	}
	
	public String voltarConsVideoPasta4() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("videopasta3", videopasta3);
		return "consPastas4Videos3";
	}
	
	public String voltarConsVideoPasta5() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("videopasta4", videopasta4);
		return "consPastas5Videos4";
	}  


	public String alterarDescricaoVideo(Video5 video5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video5", video5);
		session.setAttribute("editar", true);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo5", options, null);
		return "";
	}
	 
	public String descricaoVideo(Video5 video5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("video5", video5);
		session.setAttribute("editar", false);
		RequestContext.getCurrentInstance().openDialog("consDescritivoVideo5", options, null);
		return "";
	}


	public String voltarConsDepartamento() {
		return "consDepartamentos1Videos";
	}


	public void retornoDialogAlteracaoDescricao(SelectEvent event) {
		Video5 video5 = (Video5) event.getObject();
		if (video5.getIdvideo5() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		gerarVideos5();
		semConteudo();
	}

	public void retornoDialogNovoVideo(SelectEvent event) {
		Video5 video5 = (Video5) event.getObject();
		if (video5.getIdvideo5() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			gerarVideos5();
		}
		semConteudo();
	}

	// Verificar se existe pastas ou arquivos na tela
	public void semConteudo() {
		if ((listaVideo5 == null || listaVideo5.isEmpty())) {
			semPastaArquivo = true;
		} else {
			semPastaArquivo = false;

		}
	}

	public boolean excluirArquivoFTP(Video5 video5) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("treinamento", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey(video5.getHost());
			if(s3.delete(objectSummary)) {
				Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
				return true;
			}else {
				Mensagem.lancarMensagemInfo("Falha ao excluir", "");
				return false;
			}
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public String excluirArquivo(Video5 video5) {
		excluirArquivoFTP(video5);
		Video5Facade video5Facade = new Video5Facade();
		video5Facade.excluir(video5.getIdvideo5());
		gerarVideos5();
		return "";
	}


	public boolean verificarAcesso() {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			if (departamento != null) {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
			}
		}
		return acesso;
	}

	public boolean verificarArquivo1(Video5Bean video5Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video5Bean.getVideo1() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public boolean verificarArquivo2(Video5Bean video5Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video5Bean.getVideo2() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public boolean verificarArquivo3(Video5Bean video5Bean) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video5Bean.getVideo3() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}
	
	public boolean verificarArquivoLista(Video5 video5) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (video5 == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1
						|| usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 3) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public String editarVideoPasta5(Videopasta5 videopasta5) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		session.setAttribute("departamento", departamento);
		session.setAttribute("videopasta2", videopasta2);
		session.setAttribute("videopasta1", videopasta1);
		session.setAttribute("videopasta3", videopasta3);
		session.setAttribute("videopasta4", videopasta4);
		session.setAttribute("videopasta5", videopasta5);
		RequestContext.getCurrentInstance().openDialog("cadPastaVideos5", options, null);
		return "";
	}

	public void listarArquivoLista() {
		tabelaArquivoLista = true;
		tabelaArquivoQuadro = false;
		salvarModoExibicao("L");
	}

	public void listarArquivoQuadro() {
		tabelaArquivoQuadro = true;
		tabelaArquivoLista = false;
		salvarModoExibicao("I");
	}

	public boolean verificarArquivoLista(Arquivo1 arquivo) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		} else {
			if (arquivo.getIdarquivo1() == null) {
				return acesso;
			} else {
				if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento
						.getIddepartamento()) {
					acesso = true;
				} else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
					acesso = true;
					if (departamento.getIddepartamento() == 1) {
						acesso = false;
					}
				}
				return acesso;
			}
		}
	}

	public String trocarCorLinhaTabela(Arquivo1 arquivo1) {
		String cor = "background:#FFFFFF;";
		if (arquivo1 != null) {
			if ((arquivo1.getIdarquivo1() % 2) == 0) {
				cor = "background:#FFFFFF;";
			} else {
				cor = "background:#F3F3F3";
			}
		}
		return cor;
	}

	public void salvarModoExibicao(String nomeExibicao) {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			UsuarioFacade usuarioFacade = new UsuarioFacade();
			Usuario usuario = usuarioFacade.consultar(usuarioLogadoMB.getUsuario().getIdusuario());
			usuario.setModoexibicao(nomeExibicao);
			usuarioFacade.salvar(usuario);
			usuarioLogadoMB.setUsuario(usuario);
		}
	}

	public void verificarExibicao() {
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		} else {
			if (usuarioLogadoMB.getUsuario().getModoexibicao() == null) {
				tabelaArquivoQuadro = true;
				tabelaArquivoLista = false;
			} else {
				if (usuarioLogadoMB.getUsuario().getModoexibicao().equalsIgnoreCase("I")) {
					tabelaArquivoQuadro = true;
					tabelaArquivoLista = false;
				} else {
					tabelaArquivoLista = true;
					tabelaArquivoQuadro = false;
				}
			}
		}
	}
	
	
	public boolean verificarVideo(Video5 video5){
		if (video5 == null) {
			return false;
		}else{
			return true;
		}
	}
	
	
	public String vincularArquivoFornecedor(Video5 video5){
		String urlDocs = "consVideo5";
		arquivoBean = new ArquivoBean();
		arquivoBean.setDatainicio(new Date());
		arquivoBean.setDatavalidade(null);
		arquivoBean.setNome(video5.getNome());
		arquivoBean.setNomeftp(video5.getHost());
		arquivoBean.setRestrito(!video5.isAtivo());
		arquivoBean.setTipo("Video");
		arquivoBean.setDescricao(video5.getDescricao());
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("arquivoBean", arquivoBean);
		session.setAttribute("videopasta5", videopasta5);
		session.setAttribute("urlDocs", urlDocs);
		session.setAttribute("video5", video5);
		return "vincularFonecedorDocs";
	}
	
	
	public boolean acessoVinculoFornecedor(Video5 video5){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
			return acesso;
		}else{
			if (video5 == null) {
				return acesso;
			}else{
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					return acesso;
				}else{
					acesso = true;
					return acesso;
				}
			}
		}
	}
	
	
	public String verificarvinculoFornecedor(Video5 video5){
		String cor = "";
		if (video5 != null) {
			if (video5.isCopiado()) {
				cor = "color:#6495ED;";
			}
		}
		return cor;
	}
	
	
	public String verificarvinculoFornecedorLista(Video5 video5){
		String cor = "../../resources/img/vincularUnidade.png";
		if (video5 != null) {
			if (video5.isCopiado()) {
				cor = "../../resources/img/iconeVinculadoFornecedor.png";
			}
		}
		return cor;
	}
}
