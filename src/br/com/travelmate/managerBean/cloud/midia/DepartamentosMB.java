package br.com.travelmate.managerBean.cloud.midia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.Arquivo1Facade;
import br.com.travelmate.facade.AvisosDocsUsuarioFacade;
import br.com.travelmate.facade.Pasta1Facade;
import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.MidiaFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivo1;
import br.com.travelmate.model.Arquivo2;
import br.com.travelmate.model.Arquivo3;
import br.com.travelmate.model.Arquivo4;
import br.com.travelmate.model.Arquivo5;
import br.com.travelmate.model.Avisodocsusuario;
import br.com.travelmate.model.Pasta1;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Midias;

@Named
@ViewScoped
public class DepartamentosMB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> listaConteudo;
	private List<String> listaLink;
	private List<Midias> listaMidia;
	private List<Departamento> listaDepartamentos;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Departamento departamento;
	private Pasta1 pasta1;
	private List<Pasta1> listaPasta1;
	private boolean semDepartamentos = false;
	private String tituloPagina = "";
	private boolean pesquisar;
	private String nomeArquivo;
	private String tipoArquivo;
	private ArquivosVencidosBean arquivosVencidosBean;
	private List<ArquivosVencidosBean> listaArquivoVencidoBean;
	private List<Avisodocsusuario> listaAvisosArquivosNovos;
	private Integer nArquivosNovos = 0;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			departamento = (Departamento) session.getAttribute("departamento");
			session.removeAttribute("departamento");
			gerarListaDepartamento();
			if (listaArquivoVencidoBean == null) {
				listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
			}
			// Verifica se contém departamentos na tela
			semConteudo();
			gerarListaAvisoArquivo();
		}
	}

	public String getTituloPagina() {
		return tituloPagina;
	}

	public void setTituloPagina(String tituloPagina) {
		this.tituloPagina = tituloPagina;
	}

	public boolean isSemDepartamentos() {
		return semDepartamentos;
	}

	public void setSemDepartamentos(boolean semDepartamentos) {
		this.semDepartamentos = semDepartamentos;
	}

	/**
	 * @return the pasta1
	 */
	public Pasta1 getPasta1() {
		return pasta1;
	}

	/**
	 * @param pasta1
	 *            the pasta1 to set
	 */
	public void setPasta1(Pasta1 pasta1) {
		this.pasta1 = pasta1;
	}

	/**
	 * @return the listaPasta1
	 */
	public List<Pasta1> getListaPasta1() {
		return listaPasta1;
	}

	/**
	 * @param listaPasta1
	 *            the listaPasta1 to set
	 */
	public void setListaPasta1(List<Pasta1> listaPasta1) {
		this.listaPasta1 = listaPasta1;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Departamento> getListaDepartamentos() {
		return listaDepartamentos;
	}

	public void setListaDepartamentos(List<Departamento> listaDepartamentos) {
		this.listaDepartamentos = listaDepartamentos;
	}

	public List<Midias> getListaMidia() {
		return listaMidia;
	}

	public void setListaMidia(List<Midias> listaMidia) {
		this.listaMidia = listaMidia;
	}

	public List<String> getListaLink() {
		return listaLink;
	}

	public void setListaLink(List<String> listaLink) {
		this.listaLink = listaLink;
	}

	public List<String> getListaConteudo() {
		return listaConteudo;
	}

	public void setListaConteudo(List<String> listaConteudo) {
		this.listaConteudo = listaConteudo;
	}

	public boolean isPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(boolean pesquisar) {
		this.pesquisar = pesquisar;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public String getTipoArquivo() {
		return tipoArquivo;
	}

	public void setTipoArquivo(String tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
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

	public List<Avisodocsusuario> getListaAvisosArquivosNovos() {
		return listaAvisosArquivosNovos;
	}

	public void setListaAvisosArquivosNovos(List<Avisodocsusuario> listaAvisosArquivosNovos) {
		this.listaAvisosArquivosNovos = listaAvisosArquivosNovos;
	}
	
	

	public Integer getnArquivosNovos() {
		return nArquivosNovos;
	}

	public void setnArquivosNovos(Integer nArquivosNovos) {
		this.nArquivosNovos = nArquivosNovos;
	}

	public void gerarListaMidias() {
		MidiaFacade midiasFacade = new MidiaFacade();
		listaMidia = midiasFacade.listarSql("select m from Midias m where m.situacao='Ativo' and m.tipo='midias'");
		if (listaMidia == null) {
			listaMidia = new ArrayList<Midias>();
		}
	}

	public void gerarListaDepartamento() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		listaDepartamentos = departamentoFacade.listar("Select d from Departamento d where d.pasta=1 order by d.nome");
		if (listaDepartamentos == null) {
			listaDepartamentos = new ArrayList<Departamento>();
		}
	}

	public String devolverNomeSemEspaco(String palavra) {
		String nomeSemEspaco = palavra.replaceAll(" ", "");
		if (nomeSemEspaco.equalsIgnoreCase("")) {
			nomeSemEspaco = "NaoContem";
		}
		return nomeSemEspaco;
	}

	public String pegarNomeDepartamento(Departamento departamento) {
		if (departamento != null) {
			String nome = departamento.getNome();
			return nome;
		}
		return "";
	}

	public String pasta1(Departamento departamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 630);
		session.setAttribute("departamento", departamento);
		return "consPasta1";
	}

	public boolean habilitarQuebraLinha(Integer posicao) {
		boolean quebraLinha;
		if (posicao == 3) {
			quebraLinha = true;
			return quebraLinha;
		} else {
			quebraLinha = false;
			return quebraLinha;
		}
	}

	public Integer gerarTotalPastas(Departamento departamento) {
		Pasta1Facade pasta1Facade = new Pasta1Facade();
		Integer numeroTotal = 0;
		String sql = "Select c from Pasta1 c where c.departamento.iddepartamento=" + departamento.getIddepartamento();
		if (usuarioLogadoMB != null) {
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
				sql = sql + " and c.restrito=0 ";
			}
		} else {
			sql = sql + " and c.restrito=0 ";
		}
		listaPasta1 = pasta1Facade.listar(sql);
		if (listaPasta1 == null) {
			listaPasta1 = new ArrayList<>();
			numeroTotal = listaPasta1.size();
			return numeroTotal;
		} else {
			numeroTotal = listaPasta1.size();
			return numeroTotal;
		}
	}

	public List<Arquivo1> gerarTotalArquivos(Departamento departamento) {
		Arquivo1Facade cloudFilesFacade = new Arquivo1Facade();
		List<Arquivo1> listaArquivos = cloudFilesFacade
				.listar("Select cf from Arquivo1 cf Join Pasta1 cp on cf.pasta1.idpasta1= "
						+ "cp.idpasta1 where cp.departamento.iddepartamento=" + departamento.getIddepartamento()
						+ " order by cf.arquivo1 DESC");
		if (listaArquivos == null) {
			listaArquivos = new ArrayList<Arquivo1>();
			return listaArquivos;
		} else {
			return listaArquivos;
		}
	}

	public String posicaoInteracaoUiRepeat(String posicao) {
		String classe = "";
		if (posicao.equalsIgnoreCase("0") || posicao.equalsIgnoreCase("4") || posicao.equalsIgnoreCase("8")
				|| posicao.equalsIgnoreCase("12") || posicao.equalsIgnoreCase("16") || posicao.equalsIgnoreCase("20")
				|| posicao.equalsIgnoreCase("24") || posicao.equalsIgnoreCase("28") || posicao.equalsIgnoreCase("32")
				|| posicao.equalsIgnoreCase("36") || posicao.equalsIgnoreCase("40")) {
			classe = "panel-stat3 bg-danger";
			return classe;
		} else if (posicao.equalsIgnoreCase("1") || posicao.equalsIgnoreCase("5") || posicao.equalsIgnoreCase("9")
				|| posicao.equalsIgnoreCase("13") || posicao.equalsIgnoreCase("17") || posicao.equalsIgnoreCase("21")
				|| posicao.equalsIgnoreCase("25") || posicao.equalsIgnoreCase("29") || posicao.equalsIgnoreCase("33")
				|| posicao.equalsIgnoreCase("37") || posicao.equalsIgnoreCase("41")) {
			classe = "panel-stat3 bg-info";
			return classe;
		} else if (posicao.equalsIgnoreCase("2") || posicao.equalsIgnoreCase("6") || posicao.equalsIgnoreCase("10")
				|| posicao.equalsIgnoreCase("14") || posicao.equalsIgnoreCase("18") || posicao.equalsIgnoreCase("22")
				|| posicao.equalsIgnoreCase("26") || posicao.equalsIgnoreCase("30") || posicao.equalsIgnoreCase("34")
				|| posicao.equalsIgnoreCase("38") || posicao.equalsIgnoreCase("42")) {
			classe = "panel-stat3 bg-warning";
			return classe;
		} else {
			classe = "panel-stat3 bg-success";
			return classe;
		}
	}

	// Verifica se contém departamentos na tela
	public void semConteudo() {
		if (listaDepartamentos == null || listaDepartamentos.isEmpty()) {
			semDepartamentos = true;
		} else {
			semDepartamentos = false;
		}
	}

	public boolean verificarAcesso(Departamento departamento) {
		Boolean acesso = false;
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
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
		}
		return acesso;
	}

	public String listarArquivosVencidos(Departamento departamento) {
		boolean arquivosNovos = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("departamento", departamento);
		tituloPagina = "Lista de Arquivos Vencidos";
		pesquisar = false;
		session.setAttribute("arquivosNovos", arquivosNovos);
		session.setAttribute("tituloPagina", tituloPagina);
		session.setAttribute("pesquisar", pesquisar);
		return "listarArquivos";
	}

	public String pesquisar() {
		boolean arquivosNovos = false;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tituloPagina = "Pesquisar Arquivos";
		pesquisar = true;
		for (int i = 0; i < listaDepartamentos.size(); i++) {
			gerarListaArquivos(listaDepartamentos.get(i).getPasta1List(), listaDepartamentos.get(i));
		}
		session.setAttribute("arquivosNovos", arquivosNovos);
		session.setAttribute("listaArquivoVencidoBean", listaArquivoVencidoBean);
		session.setAttribute("tituloPagina", tituloPagina);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("listaDepartamentos", listaDepartamentos);
		return "listarArquivos";
	}

	public void gerarListaArquivos(List<Pasta1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			Object obj = lista.get(i);
			if (obj instanceof Pasta1) {
				gerarListaArquivo1(((Pasta1) obj).getArquivo1List(), departamento);
				gerarListaArquivo2(((Pasta1) obj).getArquivo2List(), departamento);
				gerarListaArquivo3(((Pasta1) obj).getArquivo3List(), departamento);
				gerarListaArquivo4(((Pasta1) obj).getArquivo4List(), departamento);
				gerarListaArquivo5(((Pasta1) obj).getArquivo5List(), departamento);

			}
		}
	}

	public void gerarListaArquivo1(List<Arquivo1> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				if (lista.get(i).getDatavalidade().after(dataAtual)
						|| lista.get(i).getDatavalidade().equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							}
						}
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (!lista.get(i).isRestrito()) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo1(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
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

	public void gerarListaArquivo2(List<Arquivo2> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				Date dataArquivo = lista.get(i).getDatavalidade();
				if (dataArquivo.after(dataAtual) || dataArquivo.equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (!lista.get(i).isRestrito()) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							}
						}
					}

				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo2(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
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

	public void gerarListaArquivo3(List<Arquivo3> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				if (lista.get(i).getDatavalidade().after(dataAtual)
						|| lista.get(i).getDatavalidade().equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							}
						}
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo3(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
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

	public void gerarListaArquivo4(List<Arquivo4> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				if (lista.get(i).getDatavalidade().after(dataAtual)
						|| lista.get(i).getDatavalidade().equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}
						}
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo4(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
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

	public void gerarListaArquivo5(List<Arquivo5> lista, Departamento departamento) {
		for (int i = 0; i < lista.size(); i++) {
			arquivosVencidosBean = null;
			Date dataAtual = new Date();
			if (lista.get(i).getDatavalidade() != null) {
				if (lista.get(i).getDatavalidade().after(dataAtual)
						|| lista.get(i).getDatavalidade().equals(dataAtual)) {
					if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
						if (!lista.get(i).isRestrito()) {
							if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
								if (tipoArquivo.equalsIgnoreCase("TODAS")) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
									arquivosVencidosBean = new ArquivosVencidosBean();
									arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
								}
							}
						}
					} else {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}
						}
					}
				}
			} else {
				if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) {
					if (!lista.get(i).isRestrito()) {
						if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
							if (tipoArquivo.equalsIgnoreCase("TODAS")) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
								arquivosVencidosBean = new ArquivosVencidosBean();
								arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
							}
						}
					}
				} else {
					if (lista.get(i).getNome().toUpperCase().contains(nomeArquivo.toUpperCase())) {
						if (tipoArquivo.equalsIgnoreCase("TODAS")) {
							arquivosVencidosBean = new ArquivosVencidosBean();
							arquivosVencidosBean = adicionarArquivo5(lista.get(i), departamento);
						} else if (lista.get(i).getTipo().equalsIgnoreCase(tipoArquivo)) {
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

	public ArquivosVencidosBean adicionarArquivo1(Arquivo1 arquivo1, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo1.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo1.getNome());
			arquivoBean.setPasta("1");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo1.getPasta1().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo1.getIdArquivo1());
			arquivoBean.setNomeFtp(arquivo1.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
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

	public ArquivosVencidosBean adicionarArquivo2(Arquivo2 arquivo2, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo2.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo2.getNome());
			arquivoBean.setPasta("2");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo2.getPasta1().getNome() + "\\"
					+ arquivo2.getPasta2().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo2.getIdarquivo2());
			arquivoBean.setNomeFtp(arquivo2.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
			arquivoBean.setNome(arquivo2.getNome());
			arquivoBean.setPasta("2");
			arquivoBean.setData(arquivo2.getDatavalidade());
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo2.getPasta1().getNome() + "\\"
					+ arquivo2.getPasta2().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo2.getIdarquivo2());
			arquivoBean.setNomeFtp(arquivo2.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}

	public ArquivosVencidosBean adicionarArquivo3(Arquivo3 arquivo3, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo3.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo3.getNome());
			arquivoBean.setPasta("3");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo3.getPasta1().getNome() + "\\"
					+ arquivo3.getPasta2().getNome() + "\\" + arquivo3.getPasta3().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo3.getIdarquivo3());
			arquivoBean.setNomeFtp(arquivo3.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
			arquivoBean.setNome(arquivo3.getNome());
			arquivoBean.setData(arquivo3.getDatavalidade());
			arquivoBean.setPasta("3");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo3.getPasta1().getNome() + "\\"
					+ arquivo3.getPasta2().getNome() + "\\" + arquivo3.getPasta3().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo3.getIdarquivo3());
			arquivoBean.setNomeFtp(arquivo3.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}

	public ArquivosVencidosBean adicionarArquivo4(Arquivo4 arquivo4, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo4.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo4.getNome());
			arquivoBean.setData(arquivo4.getDatavalidade());
			arquivoBean.setPasta("4");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo4.getPasta1().getNome() + "\\"
					+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
					+ arquivo4.getPasta4().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
			arquivoBean.setNomeFtp(arquivo4.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
			arquivoBean.setNome(arquivo4.getNome());
			arquivoBean.setData(arquivo4.getDatavalidade());
			arquivoBean.setPasta("4");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo4.getPasta1().getNome() + "\\"
					+ arquivo4.getPasta2().getNome() + "\\" + arquivo4.getPasta3().getNome() + "\\"
					+ arquivo4.getPasta4().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo4.getIdarquivo4());
			arquivoBean.setNomeFtp(arquivo4.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}

	public ArquivosVencidosBean adicionarArquivo5(Arquivo5 arquivo5, Departamento departamento) {
		ArquivosVencidosBean arquivoBean = new ArquivosVencidosBean();
		if (arquivo5.getDatavalidade() == null) {
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo5.getPasta1().getNome() + "\\"
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\"
					+ arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		} else {
			arquivoBean.setNome(arquivo5.getNome());
			arquivoBean.setData(arquivo5.getDatavalidade());
			arquivoBean.setPasta("5");
			arquivoBean.setDiretorio(departamento.getNome() + "\\" + arquivo5.getPasta1().getNome() + "\\"
					+ arquivo5.getPasta2().getNome() + "\\" + arquivo5.getPasta3().getNome() + "\\"
					+ arquivo5.getPasta4().getNome() + "\\" + arquivo5.getPasta5().getNome() + "\\");
			arquivoBean.setnArquivo(arquivo5.getIdarquivo5());
			arquivoBean.setNomeFtp(arquivo5.getNomeftp());
			arquivoBean.setIdarquivoBean(listaArquivoVencidoBean.size() + 1);
		}
		return arquivoBean;
	}

	public void limparPesquisa() {
		nomeArquivo = "";
		tipoArquivo = "TODAS";
		listaArquivoVencidoBean = new ArrayList<ArquivosVencidosBean>();
	}

	public Integer numeroArquivosNovos() {
		Integer numero = 0;
		for (int i = 0; i < listaAvisosArquivosNovos.size(); i++) {
			numero = numero + 1;
		}
		return numero;
	}

	public void gerarListaAvisoArquivo() {
		AvisosDocsUsuarioFacade avisosDocsUsuarioFacade = new AvisosDocsUsuarioFacade();
		listaAvisosArquivosNovos = avisosDocsUsuarioFacade
				.lista("Select a from Avisodocsusuario a where a.usuario.idusuario="
						+ usuarioLogadoMB.getUsuario().getIdusuario() + " and a.visualizou=0");
		if (listaAvisosArquivosNovos == null || listaAvisosArquivosNovos.isEmpty()) {
			listaAvisosArquivosNovos = new ArrayList<Avisodocsusuario>();
		}
		if (listaAvisosArquivosNovos.size() > 0) {
			nArquivosNovos = listaAvisosArquivosNovos.size();
		}else{
			nArquivosNovos = 0;
		}
	}

	public String arquivosNovos() {
		boolean viewArquivoNovo = true;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		pesquisar = true;
		tituloPagina = "Arquivos Novos";
		session.setAttribute("tituloPagina", tituloPagina);
		session.setAttribute("pesquisar", pesquisar);
		session.setAttribute("arquivosNovos", viewArquivoNovo);
		session.setAttribute("listaAvisosArquivosNovos", listaAvisosArquivosNovos);
		List<ArquivosVencidosBean> listaArquivosNovos = new ArrayList<ArquivosVencidosBean>();
		for (int i = 0; i < listaAvisosArquivosNovos.size(); i++) {
			ArquivosVencidosBean arquivosNovos = new ArquivosVencidosBean();
			arquivosNovos.setDiretorio(listaAvisosArquivosNovos.get(i).getAvisodocs().getCaminhoarquivo());
			arquivosNovos.setNome(listaAvisosArquivosNovos.get(i).getAvisodocs().getNomearquivo());
			arquivosNovos.setNomeFtp(listaAvisosArquivosNovos.get(i).getAvisodocs().getNomesalvo());
			arquivosNovos.setIdarquivoBean(listaArquivosNovos.size() + 1);
			listaArquivosNovos.add(arquivosNovos);
		}
		session.setAttribute("listaArquivoVencidoBean", listaArquivosNovos);
		return "listarArquivos";
	}

}
