package br.com.travelmate.managerBean.coprodutos;

import br.com.travelmate.dao.AvisosDao;
import br.com.travelmate.dao.CambioDao;
import br.com.travelmate.dao.CoProdutosDao;
import br.com.travelmate.facade.ComplementoAcomodacaoFacade;
import br.com.travelmate.facade.ComplementoCursoFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FornecedorPaisFacade;
import br.com.travelmate.facade.IdiomaFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.UsuarioFacade; 
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Complementoacomodacao;
import br.com.travelmate.model.Complementocurso;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Fornecedorpais;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

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
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class CoProdutosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private CambioDao cambioDao;
	@Inject
	private CoProdutosDao coProdutosDao;
	@Inject
	private AvisosDao avisosDao;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private Coprodutos coprodutos;
	private Valorcoprodutos valorcoproduto;
	private List<Coprodutos> listaCoProdutos;
	private List<Paisproduto> listaPais;
	private Cidade cidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private Pais pais;
	private Idioma idioma;
	private Fornecedorcidadeidioma fornecedorCidadeIdioma;
	private List<Fornecedorcidadeidioma> listaFornecedorIdioma;
	private List<Idioma> listaIdiomas;
	private boolean selecionado;
	private boolean habilitarBotoes = true;
	private int ano;
	private List<Moedas> listaMoedas;
	private Fornecedorpais fornecedorpais;
	private boolean situacao;
	private String produto;
	private int maioridade;

	@PostConstruct
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			situacao = true;
			int idProduto = 0;
			getUsuarioLogadoMB();
			getAplicacaoMB();
			listarIdiomas();
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			coprodutos = (Coprodutos) session.getAttribute("coprodutos");
			session.removeAttribute("coprodutos");
			fornecedorCidadeIdioma = (Fornecedorcidadeidioma) session.getAttribute("fornecedorcidadeidioma");
			session.removeAttribute("coprodutos");
			session.removeAttribute("fornecedorciadeidioma");
			PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
			listaPais = paisProdutoFacade.listar(idProduto);
			listaMoedas = cambioDao.listaMoedas();
			if (coprodutos == null) {
				fornecedorCidadeIdioma = new Fornecedorcidadeidioma();
				ano = Formatacao.getAnoData(new Date());
				pais = new Pais();
				cidade = new Cidade();
				listarForCidadeIdioma();
				fornecedorpais = new Fornecedorpais();
				maioridade = 0;
			} else {
				Fornecedorcidadeidioma fornecedorcidadeidioma = new Fornecedorcidadeidioma();
				fornecedorcidadeidioma = fornecedorCidadeIdioma;
				idioma = fornecedorCidadeIdioma.getIdioma();
				pais = coprodutos.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais();
				cidade = coprodutos.getFornecedorcidadeidioma().getFornecedorcidade().getCidade();
				listarForCidadeIdioma();
				fornecedorCidadeIdioma = fornecedorcidadeidioma;
				ano = fornecedorCidadeIdioma.getAno();
				maioridade = fornecedorcidadeidioma.getMaioridade();
				listarCoProdutos();
				buscarFornecedorPais();
			}
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Coprodutos getCoprodutos() {
		return coprodutos;
	}

	public void setCoprodutos(Coprodutos coprodutos) {
		this.coprodutos = coprodutos;
	}

	public Valorcoprodutos getValorcoproduto() {
		return valorcoproduto;
	}

	public void setValorcoproduto(Valorcoprodutos valorcoproduto) {
		this.valorcoproduto = valorcoproduto;
	}

	public List<Coprodutos> getListaCoProdutos() {
		return listaCoProdutos;
	}

	public void setListaCoProdutos(List<Coprodutos> listaCoProdutos) {
		this.listaCoProdutos = listaCoProdutos;
	}

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Idioma getIdioma() {
		return idioma;
	}

	public void setIdioma(Idioma idioma) {
		this.idioma = idioma;
	}

	public Fornecedorcidadeidioma getFornecedorCidadeIdioma() {
		return fornecedorCidadeIdioma;
	}

	public void setFornecedorCidadeIdioma(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		this.fornecedorCidadeIdioma = fornecedorCidadeIdioma;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	public Fornecedorpais getFornecedorpais() {
		return fornecedorpais;
	}

	public void setFornecedorpais(Fornecedorpais fornecedorpais) {
		this.fornecedorpais = fornecedorpais;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public List<Fornecedorcidadeidioma> getListaFornecedorIdioma() {
		return listaFornecedorIdioma;
	}

	public void setListaFornecedorIdioma(List<Fornecedorcidadeidioma> listaFornecedorIdioma) {
		this.listaFornecedorIdioma = listaFornecedorIdioma;
	}

	public List<Idioma> getListaIdiomas() {
		return listaIdiomas;
	}

	public void setListaIdiomas(List<Idioma> listaIdiomas) {
		this.listaIdiomas = listaIdiomas;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMaioridade() {
		return maioridade;
	}

	public void setMaioridade(int maioridade) {
		this.maioridade = maioridade;
	}

	public void listarCoProdutos() {
		if (fornecedorCidadeIdioma != null) {
			buscarFornecedorPais();
			String sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ fornecedorCidadeIdioma.getIdfornecedorcidadeidioma() + " and c.situacao=" + situacao
					+ "  order by c.produtosorcamento.descricao";
			listaCoProdutos = coProdutosDao.listar(sql);
			habilitarBotoes = false;
			ano = fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor().getAnotarifario();
			if (listaCoProdutos == null) {
				listaCoProdutos = new ArrayList<Coprodutos>();
			}
		}
	}

	public boolean isHabilitarBotoes() {
		return habilitarBotoes;
	}

	public void setHabilitarBotoes(boolean habilitarBotoes) {
		this.habilitarBotoes = habilitarBotoes;
	}

	public void listarIdiomas() {
		IdiomaFacade idiomaFacade = new IdiomaFacade();
		listaIdiomas = idiomaFacade.listar("Select i from Idioma i order by i.descricao");
		if (listaIdiomas == null) {
			listaIdiomas = new ArrayList<Idioma>();
		}
	}

	public void listarForCidadeIdioma() {

		if (cidade != null && idioma != null) {
			String sql = "select f from Fornecedorcidadeidioma f where f.fornecedorcidade.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.idioma.ididioma =" + idioma.getIdidioma()
					+ " order by f.fornecedorcidade.fornecedor.nome";
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			listaFornecedorIdioma = fornecedorCidadeIdiomaFacade.listar(sql);
			if (listaFornecedorIdioma == null) {
				listaFornecedorIdioma = new ArrayList<Fornecedorcidadeidioma>();
			}
			listaCoProdutos = new ArrayList<Coprodutos>();
			fornecedorCidadeIdioma = new Fornecedorcidadeidioma();
			fornecedorCidadeIdioma.setAno(2016);
		}
	}

	public String cadProduto() {
		if (cidade != null && idioma != null && fornecedorCidadeIdioma != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("fornecedorcidadeidioma", fornecedorCidadeIdioma);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			RequestContext.getCurrentInstance().openDialog("cadProdutos", options, null);
			return "";
		} else {
			Mensagem.lancarMensagemErro("Atenção! ", "Campos obrigatórios não preenchidos."); 
			return "";
		}
	}

	public String cadSuplemento(Coprodutos coprodutos) {
		List<Coprodutos> listaSelecao = new ArrayList<Coprodutos>();
		for (int i = 0; i < listaCoProdutos.size(); i++) {
			if (listaCoProdutos.get(i).isSelecione()) {
				listaSelecao.add(listaCoProdutos.get(i));
			}
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		if (listaSelecao.size() > 0) {
			session.setAttribute("listaSelecao", listaSelecao);
		} else {
			session.setAttribute("coprodutos", coprodutos);
		}
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadSuplemento", options, null);
		return "";
	}

	public String consValorProduto(Coprodutos coprodutos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("coprodutos", coprodutos);
		session.setAttribute("fornecedorcidadeidioma", fornecedorCidadeIdioma);
		return "consValorCoProdutos";
	}

	public void retornoDialogoNovo(SelectEvent event) {
		Coprodutos coproduto = (Coprodutos) event.getObject();
		listaCoProdutos.add(coproduto);
	}

	public void retornoDialogoEditar() {
		filtrarProdutos();
	}

	public String editar(Coprodutos coprodutos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("fornecedorcidadeidioma", fornecedorCidadeIdioma);
		session.setAttribute("coprodutos", coprodutos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadProdutos", options, null);
		return "";
	}

	public String consultarSuplementos(Coprodutos coprodutos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("coprodutos", coprodutos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 600);
		RequestContext.getCurrentInstance().openDialog("consSuplemento", options, null);
		return "";
	}
 
	public void cadastrarCidadeIdioma() {
		List<Coprodutos> lista = coProdutosDao
				.listar("Select c from Coprodutos c where c.idcoprodutos>=4000 and c.idcoprodutos<=4400");
		FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getFornecedorcidadeidioma() == null) {
				List<Fornecedorcidadeidioma> listaIdioma = fornecedorCidadeIdiomaFacade
						.listar("Select f from Fornecedorcidadeidioma f where f.fornecedorcidade.idfornecedorcidade="
								+ lista.get(i).getFornecedorcidade().getIdfornecedorcidade());
				if (listaIdioma != null) {
					lista.get(i).setFornecedorcidadeidioma(listaIdioma.get(0));
					coProdutosDao.salvar(lista.get(i));
				}
			}
		} 
	}

	public String vincularProdutos(Coprodutos coprodutos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("coprodutos", coprodutos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 430);
		RequestContext.getCurrentInstance().openDialog("vincularCoProduto");
		return "";
	}

	public String habilitarFornecedorOrcamento() {
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		Fornecedor fornecedor = new Fornecedor();
		fornecedor = fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor();
		fornecedor = fornecedorFacade.salvar(fornecedor);
		gerarAvisoNovaEscolaCadastrada();
		return "";
	}

	public void salvarAno() {
		if (fornecedorCidadeIdioma != null) {
			FornecedorCidadeIdiomaFacade cidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			fornecedorCidadeIdioma.setAno(ano);
			fornecedorCidadeIdioma.setMaioridade(maioridade);
			fornecedorCidadeIdioma = cidadeIdiomaFacade.salvar(fornecedorCidadeIdioma);
			Fornecedor fornecedor = fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor();
			fornecedor.setAnotarifario(ano);
			FornecedorFacade fornecedorFacade = new FornecedorFacade();
			fornecedorFacade.salvar(fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor());
			FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
			fornecedorPaisFacade.salvar(fornecedorpais);
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
		}
	}

	public void gerarAvisoNovaEscolaCadastrada() {
		Avisos aviso = new Avisos();
		aviso.setData(new Date());
		aviso.setTexto("Tarifario da escola " + fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor().getNome()
				+ " já esta cadastrado.");
		aviso.setImagem("informacao");
		aviso.setIdunidade(0);
		aviso.setLiberar(true);
		aviso.setDepartamento("outros");
		aviso.setUsuario(usuarioLogadoMB.getUsuario());
		aviso.setAvisousuarioList(salvarAvisoUsuario(aviso));
		aviso = avisosDao.salvar(aviso);

	}

	public List<Avisousuario> salvarAvisoUsuario(Avisos aviso) {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String sql = "select u from Usuario u where u.situacao='Ativo'";
		List<Usuario> listaUsuario = usuarioFacade.listar(sql);
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		if (listaUsuario != null) {
			for (int i = 0; i < listaUsuario.size(); i++) {
				Avisousuario avisousuario = new Avisousuario();
				avisousuario.setAvisos(aviso);
				avisousuario.setUsuario(listaUsuario.get(i));
				avisousuario.setVisto(false);
				lista.add(avisousuario);
			}
		}
		return lista;
	}

	public String adicionarFornecedorIdioma() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadFornecedorCidadeIdioma");
		return "";
	}

	public String produtosOrcamento() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("produtosOrcamento");
		return "";
	}

	public void buscarFornecedorPais() {
		if ((pais != null) && (fornecedorCidadeIdioma != null)) {
			String sql = "SELECT f FROM Fornecedorpais f where f.fornecedor.idfornecedor="
					+ fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor().getIdfornecedor()
					+ " and f.pais.idpais=" + pais.getIdpais();
			FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
			fornecedorpais = fornecedorPaisFacade.buscar(sql);
			if (fornecedorpais == null) {
				fornecedorpais = new Fornecedorpais();
				fornecedorpais.setFornecedor(fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor());
				fornecedorpais.setPais(pais);
				fornecedorpais.setMoedas(pais.getMoedas());
				salvarFornecedorPais();
			}
		}
	}

	public void salvarFornecedorPais() {
		FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
		fornecedorpais = fornecedorPaisFacade.salvar(fornecedorpais);
	}

	public String complementoscurso(Coprodutos coprodutos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("coproduto", coprodutos);
		if (coprodutos.getProdutosorcamento().getTipoproduto().equalsIgnoreCase("C")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			RequestContext.getCurrentInstance().openDialog("complementoCurso");
		}
		return "";
	}

	public String complementosacomodacao(Coprodutos coprodutos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("coproduto", coprodutos);
		if ((coprodutos.getProdutosorcamento().getTipo().equalsIgnoreCase("A"))
				|| (coprodutos.isPacote() && coprodutos.isAcomodacao())) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			RequestContext.getCurrentInstance().openDialog("complementoAcomodacao");
		}
		return "";
	}

	public void limparIdioma() {
		idioma = new Idioma();
		fornecedorCidadeIdioma = new Fornecedorcidadeidioma();
	}

	public String alterarSituacao(Coprodutos coprodutos) {
		if (coprodutos.isSituacao()) {
			coprodutos.setSituacao(false);
		} else {
			coprodutos.setSituacao(true);
		}
		coProdutosDao.salvar(coprodutos);
		filtrarProdutos();
		return "";
	}

	public void filtrarProdutos() {
		if (fornecedorCidadeIdioma != null && !produto.equalsIgnoreCase("sn")) {
			buscarFornecedorPais();
			String sql = "Select c from Coprodutos c where c.tipo='" + produto + "'"
					+ " and c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ fornecedorCidadeIdioma.getIdfornecedorcidadeidioma() + " and c.situacao=" + situacao
					+ "  order by c.produtosorcamento.descricao";
			
			listaCoProdutos = coProdutosDao.listar(sql);
			habilitarBotoes = false;
			if (listaCoProdutos == null) {
				listaCoProdutos = new ArrayList<Coprodutos>();
			}
		} else {
			listarCoProdutos();
		}
	}

	public String pacotes() {
		return "cursospacotes";
	}

	public String cadFornecedorCidadeIdiomaAcomodacao() {
		if (cidade != null && idioma != null && fornecedorCidadeIdioma != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("fornecedorcidadeidioma", fornecedorCidadeIdioma);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 600);
			options.put("modal", true);
			RequestContext.getCurrentInstance().openDialog("cadFornecedorCidadeIdiomaAcomodacao", options, null);
			return "";
		} else {
			Mensagem.lancarMensagemErro("Atenção! ", "Campos obrigatórios não preenchidos."); 
			return "";
		}
	}
	
	public void excluir(Coprodutos coprodutos) {
		ComplementoAcomodacaoFacade complementoAcomodacaoFacade =new ComplementoAcomodacaoFacade();
    	Complementoacomodacao complemento = complementoAcomodacaoFacade.consultar("select c from Complementoacomodacao c "
    			+ "where c.coprodutos.idcoprodutos="+coprodutos.getIdcoprodutos());
    	if(complemento!=null) {
    		complementoAcomodacaoFacade.excluir(complemento.getIdcomplementoacomodacao());
    	}
    	ComplementoCursoFacade complementoCursoFacade = new ComplementoCursoFacade();
        Complementocurso complementocurso = complementoCursoFacade.consultar("select c from Complementocurso c where"
    			+ " c.coprodutos.idcoprodutos="+coprodutos.getIdcoprodutos());
        if(complementocurso!=null) {
        	complementoCursoFacade.excluir(complementocurso.getIdcomplementocurso());
    	}
		coProdutosDao.excluir(coprodutos.getIdcoprodutos());
		listaCoProdutos.remove(coprodutos);
	}
	
	public String editarValoresEscola() {
		if (cidade != null && idioma != null && fornecedorCidadeIdioma != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("fornecedorcidadeidioma", fornecedorCidadeIdioma);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			options.put("modal", true);
			RequestContext.getCurrentInstance().openDialog("editarValoresCoProdutos", options, null);
			return "";
		} else {
			Mensagem.lancarMensagemErro("Atenção! ", "Campos obrigatórios não preenchidos."); 
			return "";
		}
	}
	
	public String complementoAcomodacaoDiaSemana(Coprodutos coprodutos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("coproduto", coprodutos);
		if (coprodutos.getProdutosorcamento().getTipo().equalsIgnoreCase("A")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 500);
			RequestContext.getCurrentInstance().openDialog("complementoAcomodacaoDiaSemana");
		}
		return "";
	}
	
	
}
