package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.Arquivo2Facade;
import br.com.travelmate.facade.Arquivo3Facade;
import br.com.travelmate.facade.Arquivo4Facade;
import br.com.travelmate.facade.AvisosDocsUsuarioFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Pasta2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Avisodocsusuario;
import br.com.travelmate.model.Pasta3;
import br.com.travelmate.model.Pasta4;
import br.com.travelmate.model.Pasta5;
import br.com.travelmate.util.Mensagem;
import br.com.travelmate.util.UploadAWSS3;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Ftpdados;

@Named
@ViewScoped
public class ListaArquivosVencidosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Departamento departamento;
	private ArquivosVencidosBean arquivosVencidosBean;
	private List<ArquivosVencidosBean> listaArquivoVencidoBean;
	private List<Arquivo1> listaArquivo1;
	private List<Arquivo2> listaArquivo2;
	private List<Arquivo3> listaArquivo3;
	private List<Pasta1> listaPasta1;
	private List<Pasta2> listaPasta2;
	private List<Pasta3> listaPasta3;
	private Ftpdados ftpDados;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String tituloPagina = "";
	private String tipoArquivo;
	private String nomeArquivo;
	private boolean pesquisa;
	private List<Departamento> listaDepartamentos;
	private boolean arquivosNovos;
	private List<Avisodocsusuario> listaAvisosDocsUsuario;
	private Pasta1 pasta1;
	private Pasta2 pasta2;
	private Pasta3 pasta3;
	private Pasta4 pasta4;
	private Pasta5 pasta5;
	private boolean habilitarVoltaPasta1 = false;
	private boolean habilitarVoltaPasta2 = false;
	private boolean habilitarVoltaPasta3 = false;
	private boolean habilitarVoltaPasta4 = false;
	private boolean habilitarVoltaPasta5 = false;
	private boolean habilitarVoltaDepartamento = false;
	private String urlArquivo = "";
	
	
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		departamento = (Departamento) session.getAttribute("departamento");
		tituloPagina = (String) session.getAttribute("tituloPagina");
		pesquisa = (boolean) session.getAttribute("pesquisar");
		arquivosNovos = (boolean) session.getAttribute("arquivosNovos");
		pasta1 = (Pasta1) session.getAttribute("pasta1");
		pasta2 = (Pasta2) session.getAttribute("pasta2");
		pasta3 = (Pasta3) session.getAttribute("pasta3");
		pasta4 = (Pasta4) session.getAttribute("pasta4");
		pasta5 = (Pasta5) session.getAttribute("pasta5");
		listaArquivoVencidoBean = (List<ArquivosVencidosBean>) session.getAttribute("listaArquivoVencidoBean");
		listaDepartamentos = (List<Departamento>) session.getAttribute("listaDepartamentos");
		listaAvisosDocsUsuario = (List<Avisodocsusuario>) session.getAttribute("listaAvisosArquivosNovos");
		session.removeAttribute("pesquisar");
		session.removeAttribute("listaArquivoVencidoBean");
		session.removeAttribute("listaDepartamentos");
		session.removeAttribute("departamento");
		session.removeAttribute("tituloPagina");
		session.removeAttribute("arquivosNovos");
		session.removeAttribute("listaAvisosArquivosNovos");
		session.removeAttribute("pasta1");
		session.removeAttribute("pasta2");
		session.removeAttribute("pasta3");
		session.removeAttribute("pasta4");
		session.removeAttribute("pasta5");
		if (listaArquivoVencidoBean == null) {
			listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
		}
		if ((!pesquisa) && (!arquivosNovos)) {
			gerarListaVencidas(departamento.getPasta1List(), departamento);
		}
		if (arquivosNovos) {
			visualizouArquivosNovos();
		}
		ftpDados = new Ftpdados();
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpDados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (ftpDados != null) {
			urlArquivo = "https:docs.systm.com.br";
		}
		verificacaoNiveisPastas();
	}

	

	public String getTituloPagina() {
		return tituloPagina;
	}



	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}



	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public Ftpdados getFtpDados() {
		return ftpDados;
	}



	public void setFtpDados(Ftpdados ftpDados) {
		this.ftpDados = ftpDados;
	}



	public Departamento getDepartamento() {
		return departamento;
	}


	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}


	public ArquivosVencidosBean getArquivosVencidosBean() {
		return arquivosVencidosBean;
	}


	public void setArquivosVencidosBean(ArquivosVencidosBean arquivosVencidosBean) {
		this.arquivosVencidosBean = arquivosVencidosBean;
	}


	public List<ArquivosVencidosBean> getListaArquivoVencidoBean() {
		return listaArquivoVencidoBean;
	}


	public void setListaArquivoVencidoBean(List<ArquivosVencidosBean> listaArquivoVencidoBean) {
		this.listaArquivoVencidoBean = listaArquivoVencidoBean;
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


	public List<Pasta1> getListaPasta1() {
		return listaPasta1;
	}


	public void setListaPasta1(List<Pasta1> listaPasta1) {
		this.listaPasta1 = listaPasta1;
	}


	public List<Pasta2> getListaPasta2() {
		return listaPasta2;
	}


	public void setListaPasta2(List<Pasta2> listaPasta2) {
		this.listaPasta2 = listaPasta2;
	}


	public List<Pasta3> getListaPasta3() {
		return listaPasta3;
	}


	public void setListaPasta3(List<Pasta3> listaPasta3) {
		this.listaPasta3 = listaPasta3;
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

	

	public boolean isPesquisa() {
		return pesquisa;
	}



	public void setPesquisa(boolean pesquisa) {
		this.pesquisa = pesquisa;
	}

	

	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}



	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	

	public boolean isArquivosNovos() {
		return arquivosNovos;
	}



	public void setArquivosNovos(boolean arquivosNovos) {
		this.arquivosNovos = arquivosNovos;
	}



	public List<Avisodocsusuario> getListaAvisosDocsUsuario() {
		return listaAvisosDocsUsuario;
	}



	public void setListaAvisosDocsUsuario(List<Avisodocsusuario> listaAvisosDocsUsuario) {
		this.listaAvisosDocsUsuario = listaAvisosDocsUsuario;
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



	public boolean isHabilitarVoltaPasta1() {
		return habilitarVoltaPasta1;
	}



	public void setHabilitarVoltaPasta1(boolean habilitarVoltaPasta1) {
		this.habilitarVoltaPasta1 = habilitarVoltaPasta1;
	}



	public boolean isHabilitarVoltaPasta2() {
		return habilitarVoltaPasta2;
	}



	public void setHabilitarVoltaPasta2(boolean habilitarVoltaPasta2) {
		this.habilitarVoltaPasta2 = habilitarVoltaPasta2;
	}



	public boolean isHabilitarVoltaPasta3() {
		return habilitarVoltaPasta3;
	}



	public void setHabilitarVoltaPasta3(boolean habilitarVoltaPasta3) {
		this.habilitarVoltaPasta3 = habilitarVoltaPasta3;
	}



	public boolean isHabilitarVoltaPasta4() {
		return habilitarVoltaPasta4;
	}



	public void setHabilitarVoltaPasta4(boolean habilitarVoltaPasta4) {
		this.habilitarVoltaPasta4 = habilitarVoltaPasta4;
	}



	public boolean isHabilitarVoltaPasta5() {
		return habilitarVoltaPasta5;
	}



	public void setHabilitarVoltaPasta5(boolean habilitarVoltaPasta5) {
		this.habilitarVoltaPasta5 = habilitarVoltaPasta5;
	}

	

	public boolean isHabilitarVoltaDepartamento() {
		return habilitarVoltaDepartamento;
	}



	public void setHabilitarVoltaDepartamento(boolean habilitarVoltaDepartamento) {
		this.habilitarVoltaDepartamento = habilitarVoltaDepartamento;
	}



	public String getUrlArquivo() {
		return urlArquivo;
	}



	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}



	public void gerarListaVencidas(List<Pasta1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof Pasta1) {
				gerarListaArquivo1(((Pasta1) obj).getArquivo1List(), departamento);
				gerarListaArquivo2(((Pasta1) obj).getArquivo2List(), departamento);
				gerarListaArquivo3(((Pasta1) obj).getArquivo3List(), departamento);
				gerarListaArquivo4(((Pasta1) obj).getArquivo4List(), departamento);
				gerarListaArquivo5(((Pasta1) obj).getArquivo5List(), departamento);
			} else if (obj instanceof Pasta2) {
				gerarListaArquivo2(((Pasta2) obj).getArquivo2List(), departamento);
				gerarListaArquivo3(((Pasta2) obj).getArquivo3List(), departamento);
				gerarListaArquivo4(((Pasta2) obj).getArquivo4List(), departamento);
				gerarListaArquivo5(((Pasta2) obj).getArquivo5List(), departamento);
			} else if (obj instanceof Pasta3) {
				gerarListaArquivo3(((Pasta3) obj).getArquivo3List(), departamento);
				gerarListaArquivo4(((Pasta3) obj).getArquivo4List(), departamento);
				gerarListaArquivo5(((Pasta3) obj).getArquivo5List(), departamento);
			} else if (obj instanceof Pasta4) {
				gerarListaArquivo4(((Pasta4) obj).getArquivo4List(), departamento);
				gerarListaArquivo5(((Pasta4) obj).getArquivo5List(), departamento);
			} else {
				gerarListaArquivo5(((Pasta4) obj).getArquivo5List(), departamento);
			}
		}
	} 

	public void gerarListaArquivo1(List<Arquivo1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date(); 
			if (!pesquisa) {
				if (lista.get(i).getDatavalidade() != null) {
					if (lista.get(i).getDatavalidade().before(dataAtual)) {
						arquivosVencidosBean = new ArquivosVencidosBean();
						arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
					}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
				}
			}else{
				if (lista.get(i).getDatavalidade() != null) {
					if (lista.get(i).getDatavalidade().after(dataAtual) || lista.get(i).getDatavalidade().equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}
							}
						}
					}
				}else{
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}
							}
						}
					}else{
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							}
						}
					}
				}
				if (arquivosVencidosBean != null) {
					listaArquivoVencidoBean.add(arquivosVencidosBean);
				}
			}
		}
	}  
	
	public void gerarListaArquivo2(List<Arquivo2> lista, Departamento departamento){
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (!pesquisa) {
				if (lista.get(i).getDatavalidade() != null) {
					if (lista.get(i).getDatavalidade().before(dataAtual)) {
						arquivosVencidosBean = new ArquivosVencidosBean();
						arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
					}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
				}     
			}else{
				if (lista.get(i).getDatavalidade() != null) {
					Date dataArquivo = lista.get(i).getDatavalidade();
					if (dataArquivo.after(dataAtual) || dataArquivo.equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								}
							}
						}
					}
			}else{
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							}
						}
					}
				}else{
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
						}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
						}
					}
				}
			}
				if (arquivosVencidosBean != null) {
					listaArquivoVencidoBean.add(arquivosVencidosBean);
				}
			}
		}   
	}
	
	public void gerarListaArquivo3(List<Arquivo3> lista, Departamento departamento){
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (!pesquisa) {
				if (lista.get(i).getDatavalidade() != null) {
					if (lista.get(i).getDatavalidade().before(dataAtual)) {
						arquivosVencidosBean = new ArquivosVencidosBean();
						arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
					}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
				}
			}else{
				if (lista.get(i).getDatavalidade() != null) {	
					if (lista.get(i).getDatavalidade().after(dataAtual) || lista.get(i).getDatavalidade().equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								}
							}
						}
					}
			}else{
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							}
						}
					}
				}else{
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
						}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
						}
					}
				}
			}
				if (arquivosVencidosBean != null) {
					listaArquivoVencidoBean.add(arquivosVencidosBean);
				}
			}
		} 
	}
	
	public void gerarListaArquivo4(List<Arquivo4> lista, Departamento departamento){
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (!pesquisa) {
				if (lista.get(i).getDatavalidade() != null) {
					if (lista.get(i).getDatavalidade().before(dataAtual)) {
						arquivosVencidosBean = new ArquivosVencidosBean();
						arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
					}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
				}
			}else{
				if (lista.get(i).getDatavalidade() != null) {	
					if (lista.get(i).getDatavalidade().after(dataAtual) || lista.get(i).getDatavalidade().equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}
							}
						}
					}
			}else{
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}
						}
					}
				}else{
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
						}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
						}
					}
				}
			}
				if (arquivosVencidosBean != null) {
					listaArquivoVencidoBean.add(arquivosVencidosBean);
				}
			}
		} 
	}
	
	public void gerarListaArquivo5(List<Arquivo5> lista, Departamento departamento){
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (!pesquisa) {
				if (lista.get(i).getDatavalidade() != null) {
					if (lista.get(i).getDatavalidade().before(dataAtual)) {
						arquivosVencidosBean = new ArquivosVencidosBean();
						arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
					}
					if (arquivosVencidosBean != null) {
						listaArquivoVencidoBean.add(arquivosVencidosBean);
					}
				}
			}else{
				if (lista.get(i).getDatavalidade() != null) {	
					if (lista.get(i).getDatavalidade().after(dataAtual) || lista.get(i).getDatavalidade().equals(dataAtual)) {
						if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
							if (!lista.get(i).isRestrito()) {
								if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
									if (tipoArquivo.equalsIgnoreCase("TODAS")) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
									}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
										arquivosVencidosBean = new ArquivosVencidosBean();
										arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
									}
								}
							}
						}else{
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}
							}
						}
					}
			}else{
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}
						}
					}
				}else{
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
						}else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
						}
					}
				}
			}
				if (arquivosVencidosBean != null) {
					listaArquivoVencidoBean.add(arquivosVencidosBean);
				}
			}
		} 
	}

	
	public String retornaIconeArquivo(String nomeArquivo){
		if (nomeArquivo == "") {
			return "";
		}
		String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf("."), nomeArquivo.length());
		if (extensao.length()>5){
			extensao = ".pdf";
		}
		if (extensao.equalsIgnoreCase(".jpg") || extensao.equalsIgnoreCase(".jpeg") || extensao.equalsIgnoreCase(".png")) {
			return "../../resources/img/icone_jpg.png";
		}else if(extensao.equalsIgnoreCase(".pdf")){
			return "../../resources/img/icone_pdf.png";
		}else if(extensao.equalsIgnoreCase(".docx") || extensao.equalsIgnoreCase(".doc")){
			return "../../resources/img/icone_docx.png";
		}else if(extensao.equalsIgnoreCase(".xls") || extensao.equalsIgnoreCase(".xlsx")){
			return "../../resources/img/icone_xls.png";
		}else if(extensao.equalsIgnoreCase(".txt")){
			return "../../resources/img/icone_txt.png";
		}else if(extensao.equalsIgnoreCase(".cdr")){
			return "../../resources/img/icone_cdr.png";
		} else if(extensao.equalsIgnoreCase(".eps")){
			return "../../resources/img/icone_eps.png";
		}else if(extensao.equalsIgnoreCase(".bmp")){
			return "../../resources/img/icone_bmp.png";
		}else if(extensao.equalsIgnoreCase(".pptx")){
			return "../../resources/img/icone_pptx.png";
		} else if (extensao.equalsIgnoreCase(".wma")) {
			return "../../resources/img/iconewma.png";
		} else if(extensao.equalsIgnoreCase(".ppsx")){
			return "../../resources/img/icone_ppsx.png";
		} 
		return ""; 
	}
	
	
	public boolean verificarAcesso(){
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == departamento.getIddepartamento()) {
			acesso = true;
		}else if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 9) {
			acesso = true;
			if (departamento.getIddepartamento()==1){
				acesso =false;
			}
		}  
		return acesso;
	}
	
	
	public String alterarNomeArquivo(Arquivo4 arquivo4){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 480);
		session.setAttribute("arquivo4", arquivo4);
		RequestContext.getCurrentInstance().openDialog("editarArquivo4", options, null);
		return "";
	}  
	
	
	public void retornoDialogAlteracaoNomeArquivo(SelectEvent event) {
        Arquivo4 arquivo4 = (Arquivo4) event.getObject();
        if (arquivo4.getIdarquivo4() != null) {
        	Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
    }
	
	public void excluirArquivo(ArquivosVencidosBean arquivo){
		if (arquivo.getPasta().equalsIgnoreCase("1")) {
			Arquivo1Facade arquivo1Facade = new Arquivo1Facade();
			excluirArquivoFTP(arquivo);
			arquivo1Facade.excluir(arquivo.getnArquivo());
		}else if(arquivo.getPasta().equalsIgnoreCase("2")){
			Arquivo2Facade arquivo2Facade = new Arquivo2Facade();
			excluirArquivoFTP(arquivo);
			arquivo2Facade.excluir(arquivo.getnArquivo());
		}else if(arquivo.getPasta().equalsIgnoreCase("3")){
			Arquivo3Facade arquivo3Facade = new Arquivo3Facade();
			excluirArquivoFTP(arquivo);
			arquivo3Facade.excluir(arquivo.getnArquivo());
		}else{
			Arquivo4Facade arquivo4Facade = new Arquivo4Facade();
			excluirArquivoFTP(arquivo); 
			arquivo4Facade.excluir(arquivo.getnArquivo());
		}
		listaArquivoVencidoBean.remove(arquivo);
	}

	public boolean excluirArquivoFTP(ArquivosVencidosBean arquivo) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		String caminho = servletContext.getRealPath("/resources/aws.properties");
			UploadAWSS3 s3 = new UploadAWSS3("docs", caminho);
			S3ObjectSummary objectSummary = new S3ObjectSummary();
			objectSummary.setKey(arquivo.getNomeFtp());
			if(s3.delete(objectSummary)) {
				Mensagem.lancarMensagemInfo("Excluido com sucesso", "");
				return true;
			}else {
				Mensagem.lancarMensagemInfo("Falha ao excluir", "");
				return false;
			}
	}
	
	
	
	
	public void mostrarMensagem(Exception ex, String erro, String titulo){
        FacesContext context = FacesContext.getCurrentInstance();
        erro = erro + " - " + ex;
        context.addMessage(null, new FacesMessage(titulo, erro));
    }
	
	// Desabilitar botao de excluido caso for uma pesquisa
	public boolean verificarOperacaoTela(){
		boolean habilitar = false;
		if (!pesquisa) {
			habilitar = true;
		}
		return habilitar;
	}
	
	
	// habilita o botão de pesquisar caso for uma pesquisa
	public boolean habilitarPesquisa(){
		boolean habilitar = true;
		if (!pesquisa) {
			habilitar = false;
		}
		if (arquivosNovos) {
			habilitar = false;
		}
		return habilitar;
	}  
	
	public String trocarCorLinhaTabela(ArquivosVencidosBean arquivo){
		String cor = "background:#FFFFFF;";
		if (arquivo != null) {
			if ((arquivo.getIdarquivoBean() % 2) == 0) {
				cor = "background:#FFFFFF;";
			}else{
				cor = "background:#F3F3F3";
			}	
		}
		return cor;
	}
	
	
	public String voltarConsDepartamento(){
		return "consDepartamentos";
	}
	
	
	public ArquivosVencidosBean adicionarArquivo1(Arquivo1 arquivo1, Departamento departamento){
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo1.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo1.getNome());
			arquivoBean.setPasta("1");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo1.getPasta1().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo1.getIdArquivo1());
			arquivoBean.setNomeFtp(arquivo1.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}else{
			arquivoBean.setNome(arquivo1.getNome());
			arquivoBean.setData(arquivo1.getDatavalidade());
			arquivoBean.setPasta("1");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo1.getPasta1().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo1.getIdArquivo1());
			arquivoBean.setNomeFtp(arquivo1.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}
	
	
	public ArquivosVencidosBean adicionarArquivo2(Arquivo2 arquivo2, Departamento departamento){
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo2.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo2.getNome());
			arquivoBean.setPasta("2");
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo2.getPasta1().getNome() + "\\" 
					+ arquivo2.getPasta2().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo2.getIdarquivo2());
			arquivoBean.setNomeFtp(arquivo2.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}else{
			arquivoBean.setNome(arquivo2.getNome());
			arquivoBean.setPasta("2");
			arquivoBean.setData(arquivo2.getDatavalidade());
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo2.getPasta1().getNome() + "\\" 
					+ arquivo2.getPasta2().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo2.getIdarquivo2());
			arquivoBean.setNomeFtp(arquivo2.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}
	
	
	public ArquivosVencidosBean adicionarArquivo3(Arquivo3 arquivo3, Departamento departamento){
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo3.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo3.getNome());
			arquivoBean.setPasta("3");
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo3.getPasta1().getNome() + "\\" 
					+ arquivo3.getPasta2().getNome() + "\\" + arquivo3.getPasta3().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo3.getIdarquivo3());
			arquivoBean.setNomeFtp(arquivo3.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}else{
			arquivoBean.setNome(arquivo3.getNome());
			arquivoBean.setData(arquivo3.getDatavalidade());
			arquivoBean.setPasta("3");
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo3.getPasta1().getNome() + "\\" 
					+ arquivo3.getPasta2().getNome() + "\\" + arquivo3.getPasta3().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo3.getIdarquivo3());
			arquivoBean.setNomeFtp(arquivo3.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}
	
	
	public ArquivosVencidosBean adicionarArquivo4(Arquivo4 arquivo4, Departamento departamento){
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo4.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo4.getNome());
			arquivoBean.setData(arquivo4.getDatavalidade());
			arquivoBean.setPasta("4");
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo4.getPasta1().getNome() + "\\" 
					+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
					+ arquivo4.getPasta4().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
			arquivoBean.setNomeFtp(arquivo4.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}else{
			arquivoBean.setNome(arquivo4.getNome());
			arquivoBean.setData(arquivo4.getDatavalidade());
			arquivoBean.setPasta("4");
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo4.getPasta1().getNome() + "\\" 
					+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
					+ arquivo4.getPasta4().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
			arquivoBean.setNomeFtp(arquivo4.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}
	
	
	public ArquivosVencidosBean adicionarArquivo5(Arquivo5 arquivo5, Departamento departamento){
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo5.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo5.getPasta1().getNome() + "\\" 
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\" +
					arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}else{
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome()+ "\\" + arquivo5.getPasta1().getNome() + "\\" 
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\" +
					arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}
	
	
	public void pesquisar(){
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			gerarListaVencidas(listaDepartamentos.get(i).getPasta1List(), listaDepartamentos.get(i));
		}
		Mensagem.lancarMensagemInfo("Pesquisa", "feita com sucesso");
	}
	 
	public void limparPesquisa(){
		nomeArquivo = "";
		tipoArquivo = "TODAS";
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
	}
	
	public void visualizouArquivosNovos(){
		AvisosDocsUsuarioFacade avisosDocsUsuarioFacade = new AvisosDocsUsuarioFacade();
		for (int i = 0; i < listaArquivoVencidoBean.size(); i++) {
			listaAvisosDocsUsuario.get(i).setVisualizou(true);
			listaAvisosDocsUsuario.get(i).setData(new Date());
			avisosDocsUsuarioFacade.salvar(listaAvisosDocsUsuario.get(i));
		}
	}
	
	
	public void verificacaoNiveisPastas(){
		if (departamento != null) {
			habilitarVoltaDepartamento = true;
		}
		
		if (pasta1 != null) {
			habilitarVoltaDepartamento = true;
			habilitarVoltaPasta1 = true;
		}
		
		if (pasta2 != null) {
			habilitarVoltaDepartamento = true;
			habilitarVoltaPasta1 = true;
			habilitarVoltaPasta2 = true;
		}
		
		if (pasta3 != null) {
			habilitarVoltaDepartamento = true;
			habilitarVoltaPasta1 = true;
			habilitarVoltaPasta2 = true;
			habilitarVoltaPasta3 = true;
		}
		
		if (pasta4 != null) {
			habilitarVoltaDepartamento = true;
			habilitarVoltaPasta1 = true;
			habilitarVoltaPasta2 = true;
			habilitarVoltaPasta3 = true;
			habilitarVoltaPasta4 = true;
		}
		
		if (pasta5 != null) {
			habilitarVoltaDepartamento = true;
			habilitarVoltaPasta1 = true;
			habilitarVoltaPasta2 = true;
			habilitarVoltaPasta3 = true;
			habilitarVoltaPasta4 = true;
			habilitarVoltaPasta5 = true;
		}
	}
	
	public String voltarConsPasta1() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1";
	}
	
	
	public String voltarConsPasta2Arquivo1(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		return "consPasta2Arquivo1";
	}
	
	public String voltarConsSubPasta3Arquivo2(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		session.setAttribute("pasta2", pasta2);
		return "consPasta3Arquivo2";
	}
	
	public String voltarConsSubPasta4Arquivo3(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		session.setAttribute("pasta2", pasta2);
		session.setAttribute("pasta3", pasta3);
		return "consPasta4Arquivo3";
	}
	
	public String voltarConsSubPasta5Arquivo4(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		session.setAttribute("pasta2", pasta2);
		session.setAttribute("pasta3", pasta3);
		session.setAttribute("pasta4", pasta4);
		return "consPasta5Arquivo4";
	}
	
	
	public String voltarConsArquivo5(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String,Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		session.setAttribute("pasta1", pasta1);
		session.setAttribute("pasta2", pasta2);
		session.setAttribute("pasta3", pasta3);
		session.setAttribute("pasta4", pasta4);
		session.setAttribute("pasta5", pasta5);
		return "consArquivo5";
	}
	
}
