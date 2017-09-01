package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.Arquivo5Facade;
import br.com.travelmate.facade.FornecedorCidadeDocsFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorDocsFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.Video1Facade;
import br.com.travelmate.facade.Video2Facade;
import br.com.travelmate.facade.Video3Facade;
import br.com.travelmate.facade.Video4Facade;
import br.com.travelmate.facade.Video5Facade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.fornecedordocs.ArquivoBean;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadedocs;
import br.com.travelmate.model.Fornecedordocs;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.model.Video1;
import br.com.travelmate.model.Video2;
import br.com.travelmate.model.Video3;
import br.com.travelmate.model.Video4;
import br.com.travelmate.model.Video5;
import br.com.travelmate.model.Videopasta1;
import br.com.travelmate.model.Videopasta2;
import br.com.travelmate.model.Videopasta3;
import br.com.travelmate.model.Videopasta4;
import br.com.travelmate.model.Videopasta5;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class VincularFornecedorDocsMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
	private Fornecedordocs fornecedordocs;
	private String nomeArquivoFTP;
	private UploadedFile file;
	private FileUploadEvent ex;
	private List<String> listaNomeArquivo;
	private List<Pais> listaPais;
	private List<Fornecedorcidade> listaCidade;
	private boolean todospais;
	private boolean todoscidade;
	private ArquivoBean arquivoBean;
	private String urlDocs;
	private Pasta1 pasta1;
	private Pasta2 pasta2;
	private Pasta3 pasta3;
	private Pasta4 pasta4;
	private Pasta5 pasta5;
	private Videopasta1 videopasta1;
	private Videopasta2 videopasta2;
	private Videopasta3 videopasta3;
	private Videopasta4 videopasta4;
	private Videopasta5 videopasta5;
	private Arquivo1 arquivo1;
	private Arquivo2 arquivo2;
	private Arquivo3 arquivo3;
	private Arquivo4 arquivo4;
	private Arquivo5 arquivo5;
	private Video1 video1;
	private Video2 video2;
	private Video3 video3;
	private Video4 video4;
	private Video5 video5;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		arquivoBean = (ArquivoBean) session.getAttribute("arquivoBean");
		urlDocs = (String) session.getAttribute("urlDocs");
		pasta1 = (Pasta1) session.getAttribute("pasta1");
		pasta2 = (Pasta2) session.getAttribute("pasta2");
		pasta3 = (Pasta3) session.getAttribute("pasta3");
		pasta4 = (Pasta4) session.getAttribute("pasta4");
		pasta5 = (Pasta5) session.getAttribute("pasta5");
		videopasta1 = (Videopasta1) session.getAttribute("videopasta1");
		videopasta2 = (Videopasta2) session.getAttribute("videopasta2");
		videopasta3 = (Videopasta3) session.getAttribute("videopasta3");
		videopasta4 = (Videopasta4) session.getAttribute("videopasta4");
		videopasta5 = (Videopasta5) session.getAttribute("videoapasta5");
		arquivo1 = (Arquivo1) session.getAttribute("arquivo1");
		arquivo2 = (Arquivo2) session.getAttribute("arquivo2");
		arquivo3 = (Arquivo3) session.getAttribute("arquivo3");
		arquivo4 = (Arquivo4) session.getAttribute("arquivo4");
		arquivo5 = (Arquivo5) session.getAttribute("arquivo5");
		video1 = (Video1) session.getAttribute("video1");
		video2 = (Video2) session.getAttribute("video2");
		video3 = (Video3) session.getAttribute("video3");
		video4 = (Video4) session.getAttribute("video4");
		video5 = (Video5) session.getAttribute("video5");
		session.removeAttribute("pasta1");
		session.removeAttribute("pasta2");
		session.removeAttribute("pasta3");
		session.removeAttribute("pasta4");
		session.removeAttribute("pasta5");
		session.removeAttribute("videopasta1");
		session.removeAttribute("videopasta2");
		session.removeAttribute("videopasta3");
		session.removeAttribute("videopasta4");
		session.removeAttribute("videoapasta5");
		session.removeAttribute("arquivoBean");
		session.removeAttribute("urlDocs");
		session.removeAttribute("arquivo1");
		session.removeAttribute("arquivo2");
		session.removeAttribute("arquivo3");
		session.removeAttribute("arquivo4");
		session.removeAttribute("arquivo5");
		session.removeAttribute("video1");
		session.removeAttribute("video2");
		session.removeAttribute("video3");
		session.removeAttribute("video4");
		session.removeAttribute("video5");
		gerarListaFornecedor();
		if (fornecedordocs == null) {
			fornecedordocs = new Fornecedordocs();
			fornecedordocs.setDatainicio(new Date());
		} else {
			fornecedor = fornecedordocs.getFornecedor();
		}
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public Fornecedordocs getFornecedordocs() {
		return fornecedordocs;
	}

	public void setFornecedordocs(Fornecedordocs fornecedordocs) {
		this.fornecedordocs = fornecedordocs;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
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

	public List<String> getListaNomeArquivo() {
		return listaNomeArquivo;
	}

	public void setListaNomeArquivo(List<String> listaNomeArquivo) {
		this.listaNomeArquivo = listaNomeArquivo;
	}

	public FileUploadEvent getEx() {
		return ex;
	}

	public void setEx(FileUploadEvent ex) {
		this.ex = ex;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Fornecedorcidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Fornecedorcidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public boolean isTodospais() {
		return todospais;
	}

	public void setTodospais(boolean todospais) {
		this.todospais = todospais;
	}

	public boolean isTodoscidade() {
		return todoscidade;
	}

	public void setTodoscidade(boolean todoscidade) {
		this.todoscidade = todoscidade;
	}

	public ArquivoBean getArquivoBean() {
		return arquivoBean;
	}

	public void setArquivoBean(ArquivoBean arquivoBean) {
		this.arquivoBean = arquivoBean;
	}

	public String getUrlDocs() {
		return urlDocs;
	}

	public void setUrlDocs(String urlDocs) {
		this.urlDocs = urlDocs;
	}

	public Pasta1 getPasta1() {
		return pasta1;
	}

	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}

	public Pasta2 getPasta2() {
		return pasta2;
	}

	public void setPasta2(Pasta2 pasta2) {
		this.pasta2 = pasta2;
	}

	public Pasta3 getPasta3() {
		return pasta3;
	}

	public void setPasta3(Pasta3 pasta3) {
		this.pasta3 = pasta3;
	}

	public Pasta4 getPasta4() {
		return pasta4;
	}

	public void setPasta4(Pasta4 pasta4) {
		this.pasta4 = pasta4;
	}

	public Pasta5 getPasta5() {
		return pasta5;
	}

	public void setPasta5(Pasta5 pasta5) {
		this.pasta5 = pasta5;
	}

	public void gerarListaFornecedor() {
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade
				.listar("select f from Fornecedor f where f.idfornecedor>1000 order by f.nome");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
	}

	public String cancelar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (pasta1 != null) {
			session.setAttribute("pasta1", pasta1);
		} else if (pasta2 != null) {
			session.setAttribute("pasta2", pasta2);
		} else if (pasta3 != null) {
			session.setAttribute("pasta3", pasta3);
		} else if (pasta4 != null) {
			session.setAttribute("pasta4", pasta4);
		} else if(pasta5 != null){
			session.setAttribute("pasta5", pasta5);
		}else if (videopasta1 != null) {
			session.setAttribute("videopasta1", videopasta1);
		} else if (videopasta2 != null) {
			session.setAttribute("videopasta2", videopasta2);
		} else if (videopasta3 != null) {
			session.setAttribute("videopasta3", videopasta3);
		} else if(videopasta4 != null){
			session.setAttribute("videopasta4", videopasta4);
		}else{
			session.setAttribute("videopasta5", videopasta5);
		}
		return urlDocs;
	}

	public String salvar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (validarDados()) {
			if (listaCidade != null && listaCidade.size() > 0) {
				FornecedorDocsFacade fornecedorDocsFacade = new FornecedorDocsFacade();
				fornecedordocs = new Fornecedordocs();
				fornecedordocs.setDatainicio(arquivoBean.getDatainicio());
				fornecedordocs.setDatavalidade(arquivoBean.getDatavalidade());
				if (arquivoBean.getTipo().equalsIgnoreCase("Documento")) {
					fornecedordocs.setDescricao(arquivoBean.getNome());
				} else {
					fornecedordocs.setDescricao(arquivoBean.getDescricao());
				}   
				fornecedordocs.setNome(arquivoBean.getNome());
				fornecedordocs.setNomeftp(arquivoBean.getNomeftp());
				fornecedordocs.setRestrito(arquivoBean.isRestrito());
				fornecedordocs.setTipo(arquivoBean.getTipo());
				fornecedordocs.setFornecedor(fornecedor);
				fornecedordocs = fornecedorDocsFacade.salvar(fornecedordocs);
				for (int i = 0; i < listaCidade.size(); i++) {
					if (listaCidade.get(i).isSelecionado()) {
						Fornecedorcidadedocs fornecedorcidadedocs = new Fornecedorcidadedocs();
						fornecedorcidadedocs.setFornecedorcidade(listaCidade.get(i));
						fornecedorcidadedocs.setFornecedordocs(fornecedordocs);
						FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
						fornecedorCidadeDocsFacade.salvar(fornecedorcidadedocs);
					}
				}
			}
			Mensagem.lancarMensagemInfo("Documento salvo com sucesso!", "");
			if (pasta1 != null) {
				session.setAttribute("pasta1", pasta1);
				Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
				arquivo1.setCopiado(true);
				arquivo1Facade.salvar(arquivo1);
			} else if (pasta2 != null) {
				session.setAttribute("pasta2", pasta2);
				Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
				arquivo2.setCopiado(true);
				arquivo2Facade.salvar(arquivo2);
			} else if (pasta3 != null) {
				session.setAttribute("pasta3", pasta3);
				Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
				arquivo3.setCopiado(true);
				arquivo3Facade.salvar(arquivo3);
			} else if (pasta4 != null) {
				session.setAttribute("pasta4", pasta4);
				Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
				arquivo4.setCopiado(true);
				arquivo4Facade.salvar(arquivo4);
			} else if(pasta5 != null){
				session.setAttribute("pasta5", pasta5);
				Arquivo5Facade arquivo5Facade = new Arquivo5Facade();
				arquivo5.setCopiado(true);
				arquivo5Facade.salvar(arquivo5);
			}else if (videopasta1 != null) {
				session.setAttribute("videopasta1", videopasta1);
				Video1Facade video1Facade = new Video1Facade();
				video1.setCopiado(true);
				video1Facade.salvar(video1);
			} else if (videopasta2 != null) {
				session.setAttribute("videopasta2", videopasta2);
				Video2Facade video2Facade = new Video2Facade();
				video2.setCopiado(true);
				video2Facade.salvar(video2);
			} else if (videopasta3 != null) {
				session.setAttribute("videopasta3", videopasta3);
				Video3Facade video3Facade = new Video3Facade();
				video3.setCopiado(true);
				video3Facade.salvar(video3);
			} else if(videopasta4 != null){
				session.setAttribute("videopasta4", videopasta4);
				Video4Facade video4Facade = new Video4Facade();
				video4.setCopiado(true);
				video4Facade.salvar(video4);
			}else{
				session.setAttribute("videopasta5", videopasta5);
				Video5Facade video5Facade = new Video5Facade();
				video5.setCopiado(true);
				video5Facade.salvar(video5);
			}
			return urlDocs;
		} else {
			FacesMessage mensagem = new FacesMessage("Faltam informações! ", "");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
		}
		return "";
	}

	public boolean validarDados() {
		if (fornecedor == null || fornecedor.getIdfornecedor() == null) {
			return false;
		}

		return true;
	}

	public void carregarListaPais() {
		if (fornecedor != null) {
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			String sqlFornecedorCidade = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor() + " and f.ativo=1 Group By f.cidade.pais.idpais";
			List<Fornecedorcidade> listaCidades = fornecedorCidadeFacade.listar(sqlFornecedorCidade);
			listaPais = new ArrayList<>();
			for (int i = 0; i < listaCidades.size(); i++) {
				listaPais.add(listaCidades.get(i).getCidade().getPais());
			}
		}
	}

	public void selecionarTodosPais() {
		for (int i = 0; i < listaPais.size(); i++) {
			listaPais.get(i).setSelecionado(todospais);
		}
		carregarListaCidades(todospais);
	}

	public void carregarListaCidades(boolean selecionado) {
		listaCidade = new ArrayList<>();
		if (selecionado) {
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			String sqlFornecedorCidade = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor() + " and f.ativo=1 and (";
			boolean passou = false;
			for (int i = 0; i < listaPais.size(); i++) {
				if (listaPais.get(i).isSelecionado()) {
					if (passou) {
						sqlFornecedorCidade = sqlFornecedorCidade + "or (f.cidade.pais.idpais="
								+ listaPais.get(i).getIdpais() + ")";
					} else {
						sqlFornecedorCidade = sqlFornecedorCidade + "(f.cidade.pais.idpais="
								+ listaPais.get(i).getIdpais() + ")";
						passou = true;
					}
				}
			}
			sqlFornecedorCidade = sqlFornecedorCidade + ") Group By f.cidade.idcidade";
			listaCidade = fornecedorCidadeFacade.listar(sqlFornecedorCidade);
			verificarListaSelecionados();
		}
	}

	public void selecionarTodasCidades() {
		for (int i = 0; i < listaCidade.size(); i++) {
			if (listaCidade.get(i).isExcluir()) {
				listaCidade.get(i).setSelecionado(todoscidade);
			}
		}
		verificarListaSelecionados();
	}

	public void verificarListaSelecionados() {
		String sql = "select f From Fornecedorcidadedocs f where f.fornecedordocs.idfornecedordocs="
				+ fornecedordocs.getIdfornecedordocs();
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		List<Fornecedorcidadedocs> lista = fornecedorCidadeDocsFacade.listar(sql);
		if (lista != null && lista.size() > 0) {
			for (int i = 0; i < lista.size(); i++) {
				int idcidade = lista.get(i).getFornecedorcidade().getIdfornecedorcidade();
				for (int j = 0; j < listaCidade.size(); j++) {
					if (listaCidade.get(j).getIdfornecedorcidade() == idcidade) {
						listaCidade.get(j).setFornecedorcidadedocs(lista.get(i));
						listaCidade.get(j).setExcluir(false);
					}
				}
			}
		}
	}

	public void excluir(Fornecedorcidade fornecedorcidade) {
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		fornecedorCidadeDocsFacade.excluir(fornecedorcidade.getFornecedorcidadedocs().getIdfornecedorcidadedocs());
		fornecedorcidade.setExcluir(true);
		fornecedorcidade.setFornecedorcidadedocs(null);
	}

	public boolean desabilitarSelecione(Fornecedorcidade fornecedorcidade) {
		if (!fornecedorcidade.isExcluir()) {
			return true;
		} else {
			return false;
		}
	}

}
