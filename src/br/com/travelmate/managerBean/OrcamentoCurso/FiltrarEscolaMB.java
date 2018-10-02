
package br.com.travelmate.managerBean.OrcamentoCurso;

import br.com.travelmate.dao.CoProdutosDao;
import br.com.travelmate.dao.OcursoFeriadoDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.ClienteFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoDataFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.FornecedorFeriasFacade;
import br.com.travelmate.facade.FornecedorPaisFacade;
import br.com.travelmate.facade.GrupoObrigatorioFacade;
import br.com.travelmate.facade.IdiomaFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.PromocaoBrindeCursoCidadeFacade;
import br.com.travelmate.facade.PromocaoCursoFornecedorCidadeFacade;
import br.com.travelmate.facade.PromocaoTaxaCidadeFacade;
import br.com.travelmate.facade.PublicidadeFacade;
import br.com.travelmate.facade.ValorCoProdutosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio; 
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Cliente;
import br.com.travelmate.model.Coprodutos;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Fornecedorcidadeidiomaprodutodata;
import br.com.travelmate.model.Fornecedorferias;
import br.com.travelmate.model.Fornecedorpais;
import br.com.travelmate.model.Idioma;
import br.com.travelmate.model.Lead;
import br.com.travelmate.model.Ocurso;
import br.com.travelmate.model.Ocursodesconto;
import br.com.travelmate.model.Ocursoferiado;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Promocaobrindecurso;
import br.com.travelmate.model.Promocaobrindecursocidade;
import br.com.travelmate.model.Promocaocurso;
import br.com.travelmate.model.Promocaocursocidade;
import br.com.travelmate.model.Promocaotaxacidade;
import br.com.travelmate.model.Promocaotaxacurso;
import br.com.travelmate.model.Publicidade;
import br.com.travelmate.model.Valorcoprodutos;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class FiltrarEscolaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	@Inject
	private CoProdutosDao coProdutosDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private FiltrarEscolaBean filtrarEscolaBean;
	private List<ProdutosOrcamentoBean> listaSuplemento;
	private int verificarSuplemento = 0;
	private boolean digitosFoneResidencial;
	private boolean digitosFoneCelular;
	private List<DatasBean> listaFornecedorCidadeDatas;
	private List<DatasBean> listaDatas;
	private boolean calendario=true;
	private boolean comboDatas=false;
	private DatasBean datasBean;
	private Lead lead;
	private String funcao;
	private boolean habilitarUpload = true;
	private List<Fornecedor> listaFornecedorAtualizando;
	private String moedaNacional;
	
	@Inject
	private OcursoFeriadoDao ocursoFeriadoDao;
 
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		filtrarEscolaBean = (FiltrarEscolaBean) session.getAttribute("filtrarEscolaBean");
		funcao = (String) session.getAttribute("funcao");
		lead = (Lead) session.getAttribute("lead");
		if (filtrarEscolaBean == null) {
			iniciarNovoOrcamento();
		}else {
			gerarListaFornecedorCidade();
		}
		int idProduto = 0;
		getUsuarioLogadoMB();
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		idProduto = lead.getProdutos().getIdprodutos();
		filtrarEscolaBean.setListaPais(paisProdutoFacade.listar(idProduto));
		filtrarEscolaBean.getOcurso().setCliente((Cliente) session.getAttribute("cliente"));
		session.removeAttribute("cliente");
		if (filtrarEscolaBean.getOcurso().getCliente() == null) {
			filtrarEscolaBean.getOcurso().setCliente(new Cliente());
		} else {
			filtrarEscolaBean.setClientelead(true);
			if (filtrarEscolaBean.getLead() == null) {
				filtrarEscolaBean.setLead(filtrarEscolaBean.getOcurso().getCliente().getLead());
			}
		}
		gerarListaIdioma();
		if (lead != null) {
			if (lead.getCliente().getIdioma() > 0) {
				IdiomaFacade idiomaFacade = new IdiomaFacade();
				filtrarEscolaBean.setIdioma(idiomaFacade.consultar("SELECT i FROM Idioma i WHERE i.ididioma=" + lead.getCliente().getIdioma()));
				filtrarEscolaBean.setIdiomaescolhido(false);
			}
		}
		gerarListaPublicidade();
		digitosFoneCelular = aplicacaoMB
				.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), null);
		digitosFoneResidencial = aplicacaoMB
				.checkBoxTelefone(usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), null);
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1) {
			habilitarUpload = true;
		}else{
			habilitarUpload = false;
		}
		moedaNacional = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getMoedas().getSigla();
	}

	public void iniciarNovoOrcamento() {
		getUsuarioLogadoMB();
		filtrarEscolaBean = new FiltrarEscolaBean();
		filtrarEscolaBean.setPais(new Pais());
		filtrarEscolaBean.setCidade(new Cidadepaisproduto());
		filtrarEscolaBean.setOcurso(new Ocurso());
		filtrarEscolaBean.setCambio(new Cambio());
		filtrarEscolaBean.setIdioma(new Idioma());
		filtrarEscolaBean.getOcurso().setCliente(new Cliente());
		filtrarEscolaBean.getOcurso().getCliente().setPublicidade(new Publicidade());
		filtrarEscolaBean.getOcurso().setUsuario(usuarioLogadoMB.getUsuario());
		filtrarEscolaBean.setSelecionarCliente(false);
		filtrarEscolaBean.setNomeBotao("Pesquisar");
		filtrarEscolaBean.setListaCidade(new ArrayList<Cidadepaisproduto>());
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public FiltrarEscolaBean getFiltrarEscolaBean() {
		return filtrarEscolaBean;
	}

	public void setFiltrarEscolaBean(FiltrarEscolaBean filtrarEscolaBean) {
		this.filtrarEscolaBean = filtrarEscolaBean;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public boolean isDigitosFoneResidencial() {
		return digitosFoneResidencial;
	}

	public void setDigitosFoneResidencial(boolean digitosFoneResidencial) {
		this.digitosFoneResidencial = digitosFoneResidencial;
	}

	public boolean isDigitosFoneCelular() {
		return digitosFoneCelular;
	}

	public void setDigitosFoneCelular(boolean digitosFoneCelular) {
		this.digitosFoneCelular = digitosFoneCelular;
	}

	public List<ProdutosOrcamentoBean> getListaSuplemento() {
		return listaSuplemento;
	}

	public void setListaSuplemento(List<ProdutosOrcamentoBean> listaSuplemento) {
		this.listaSuplemento = listaSuplemento;
	}

	public int getVerificarSuplemento() {
		return verificarSuplemento;
	}

	public void setVerificarSuplemento(int verificarSuplemento) {
		this.verificarSuplemento = verificarSuplemento;
	}

	public List<DatasBean> getListaDatas() {
		return listaDatas;
	}

	public void setListaDatas(List<DatasBean> listaDatas) {
		this.listaDatas = listaDatas;
	}

	public boolean isCalendario() {
		return calendario;
	}

	public void setCalendario(boolean calendario) {
		this.calendario = calendario;
	}

	public boolean isComboDatas() {
		return comboDatas;
	}

	public void setComboDatas(boolean comboDatas) {
		this.comboDatas = comboDatas;
	}

	public DatasBean getDatasBean() {
		return datasBean;
	}

	public void setDatasBean(DatasBean datasBean) {
		this.datasBean = datasBean;
	}

	public boolean isHabilitarUpload() {
		return habilitarUpload;
	}

	public void setHabilitarUpload(boolean habilitarUpload) {
		this.habilitarUpload = habilitarUpload;
	}

	public String getMoedaNacional() {
		return moedaNacional;
	}

	public void setMoedaNacional(String moedaNacional) {
		this.moedaNacional = moedaNacional;
	}

	public void gerarListaIdioma() {
		IdiomaFacade idiomaFacade = new IdiomaFacade();
		String sql = "Select i from Idioma  i  order by i.descricao";
		filtrarEscolaBean.setListaIdiomas(idiomaFacade.listar(sql));
		if (filtrarEscolaBean.getListaIdiomas() == null) {
			filtrarEscolaBean.setListaIdiomas(new ArrayList<Idioma>());
		}
	}

	public String validarCamposPesquisa() {
		String mensagem = "";
		if (filtrarEscolaBean.getIdioma() == null) {
			mensagem = mensagem + "Selecione um idioma\r\n";
		}
		
		if (filtrarEscolaBean.getOcurso().getCliente() == null) {
			mensagem = mensagem + "Selecione o cliente\r\n";
		}
		if (filtrarEscolaBean.getPais() == null) {
			mensagem = mensagem + "Selecione País\r\n";
		}
		if (filtrarEscolaBean.getCidade() == null) {
			mensagem = mensagem + "Selecione cidade\r\n";
		}
		if (filtrarEscolaBean.getOcurso().getProdutosorcamento() == null) {
			mensagem = mensagem + "Selecione tipo de curso\r\n";
		}
		if (filtrarEscolaBean.getOcurso().getDatainicio() == null) {
			mensagem = mensagem + "Selecione uma data de inicio\r\n";
		} else {
			if (filtrarEscolaBean.getOcurso().getDatainicio().before(new Date())) {
				mensagem = mensagem + "Data início tem que ser maior que data atual\r\n";
			}
		}

		if (filtrarEscolaBean.getOcurso().getNumerosemanas() == null || filtrarEscolaBean.getOcurso().getNumerosemanas() == 0) {
			mensagem = mensagem + "Número de semanas não informado";
		}
		
		return mensagem;
	}

	public void gerarListaFornecedorCidade() {
		String sql = null;
		if (filtrarEscolaBean.getCidade() != null && filtrarEscolaBean.getIdioma() != null && lead != null) {
			sql = "select f from Fornecedorcidadeidioma f where f.fornecedorcidade.cidade.idcidade="
					+ filtrarEscolaBean.getCidade().getCidade().getIdcidade() + " and f.idioma.ididioma ="
					+ filtrarEscolaBean.getIdioma().getIdidioma()
					+ " and f.fornecedorcidade.fornecedor.habilitarorcamento=true"
					+ " and f.acomodacaoindependente=FALSE"
					+ " and f.habilitada=true and f.fornecedorcidade.produtos.idprodutos="+ lead.getProdutos().getIdprodutos() +" order by f.fornecedorcidade.fornecedor.nome";
			FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
			filtrarEscolaBean.setListaFornecedorCidadeIdioma(fornecedorCidadeIdiomaFacade.listar(sql));
			if (filtrarEscolaBean.getListaFornecedorCidadeIdioma() == null) {
				filtrarEscolaBean.setListaFornecedorCidadeIdioma(new ArrayList<Fornecedorcidadeidioma>());
			}
			if (filtrarEscolaBean.getListaFornecedorCidadeIdioma().size() == 0) {
				filtrarEscolaBean.setPossuifornecedor(true);
				FacesMessage mensagemAtencao = new FacesMessage("Nenhum escola encontrado com os dados pesquisados.",
						"");
				filtrarEscolaBean.setFornecedorcidadeidioma(null);
				FacesContext.getCurrentInstance().addMessage("Atenção", mensagemAtencao);
			} else
				gerarListaCursoTodasEscolas();
			filtrarEscolaBean.setPossuifornecedor(false);
		}
	}

	public String localizarFornecedorCidade() {
		if(datasBean!=null && datasBean.getDescricao()!=null){
			filtrarEscolaBean.getOcurso().setDatainicio(Formatacao.ConvercaoStringData(datasBean.getDescricao()));
		}
		String sql;
		if (filtrarEscolaBean.getFornecedorcidadeidioma() != null) {
			sql = "Select p from Produtosorcamento p where p.tipo='S' " + " and p.idprodutosOrcamento!="
					+ aplicacaoMB.getParametrosprodutos().getPromocaoescola() + " order by p.descricao";

		} else {
			sql = "Select p from Produtosorcamento p where p.tipo='S' order by p.descricao";
		}
		ProdutoOrcamentoFacade produtosOrcamentoFacade = new ProdutoOrcamentoFacade();
		List<Produtosorcamento> listaProdutos = produtosOrcamentoFacade.listarProdutosOrcamentoSql(sql);
		filtrarEscolaBean.getOcurso().setOcursodescontoList(new ArrayList<Ocursodesconto>());
		int idtxtm = aplicacaoMB.getParametrosprodutos().getPassagemTaxaTM();
		for (int i = 0; listaProdutos.size() > i; i++) {
			int idtxttmproduto = listaProdutos.get(i).getIdprodutosOrcamento();
			Ocursodesconto ocursodesconto = new Ocursodesconto();
			if (idtxtm == idtxttmproduto) {
				ocursodesconto.setValormoedanacional(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getTaxatm());
				ocursodesconto.setSelecionado(true);
				ocursodesconto.setTaxaTmSelecionado(true);
			} else {
				ocursodesconto.setValormoedanacional(0.0f);
				ocursodesconto.setTaxaTmSelecionado(false);
			}
			ocursodesconto.setProdutosorcamento(listaProdutos.get(i));
			ocursodesconto.setValormoedaestrangeira(0.0f);
			filtrarEscolaBean.getOcurso().getOcursodescontoList().add(ocursodesconto);
			filtrarEscolaBean.getOcurso().setUsuario(usuarioLogadoMB.getUsuario());
		}
		String mensagem = validarCamposPesquisa();
		if (filtrarEscolaBean.getFornecedorcidadeidioma() != null) {
			filtrarEscolaBean.setListaFornecedorCidadeIdioma(new ArrayList<Fornecedorcidadeidioma>());
			filtrarEscolaBean.getListaFornecedorCidadeIdioma().add(filtrarEscolaBean.getFornecedorcidadeidioma());
		}
		if (mensagem.length() < 5) {
			if (filtrarEscolaBean.getListaFornecedorCidadeIdioma() != null) {
				if (filtrarEscolaBean.getListaFornecedorCidadeIdioma().size() > 0) {
					filtrarEscolaBean.setListaFornecedorProdutosBean(new ArrayList<FornecedorProdutosBean>());
					listaFornecedorAtualizando = new ArrayList<Fornecedor>();
					for (int i = 0; i < filtrarEscolaBean.getListaFornecedorCidadeIdioma().size(); i++) {
						listarCoProdutoFornecedor(filtrarEscolaBean.getListaFornecedorCidadeIdioma().get(i));
					}
					verificarListaEscolas();
					if (filtrarEscolaBean.getListaFornecedorProdutosBean().size() > 0) {
						if (filtrarEscolaBean.getOcurso().getIdocurso() == null) {
							filtrarEscolaBean.getOcurso().setIdioma(filtrarEscolaBean.getIdioma());
							filtrarEscolaBean.getOcurso().setUsuario(usuarioLogadoMB.getUsuario());
							ClienteFacade clienteFacade = new ClienteFacade();
							Cliente cliente = filtrarEscolaBean.getOcurso().getCliente();
							if (cliente.getIdioma() != filtrarEscolaBean.getIdioma().getIdidioma()) {
								cliente.setIdioma(filtrarEscolaBean.getIdioma().getIdidioma());
							}
							cliente = clienteFacade.salvar(cliente);
							filtrarEscolaBean.getOcurso().setCliente(cliente);
							FacesContext fc = FacesContext.getCurrentInstance();
							HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
							session.setAttribute("filtrarEscolaBean", filtrarEscolaBean);
							session.setAttribute("listaFornecedorAtualizando", listaFornecedorAtualizando);	
							return "resultadoFiltroOrcamento";
						}
					} else {
						if (filtrarEscolaBean.getFornecedorcidadeidioma() != null && filtrarEscolaBean.getFornecedorcidadeidioma().getFornecedorcidade() != null
								&& filtrarEscolaBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor() != null) {
							if (filtrarEscolaBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().isTarifarioatualizado()) {
								
								Mensagem.lancarMensagemInfo("Nenhum fornecedor encontrado com os dados pesquisados.", "");
							}else {
								Mensagem.lancarMensagemInfo("Tarifário da "+ filtrarEscolaBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor().getNome() +" em atualização.", "");
							}
						}else {
							if (listaFornecedorAtualizando == null) {
								listaFornecedorAtualizando = new ArrayList<Fornecedor>();
							}
							for (int i = 0; i < listaFornecedorAtualizando.size(); i++) {
								if (listaFornecedorAtualizando.get(i).isTarifarioatualizado()) {
									Mensagem.lancarMensagemInfo("Nenhum  dados pesquisados encontrado no parceiro " + listaFornecedorAtualizando.get(i).getNome(), "");
								}else {
									Mensagem.lancarMensagemInfo("Tarifário da "+ listaFornecedorAtualizando.get(i).getNome() +" em atualização.", "");
								}
							}
						}
						filtrarEscolaBean.setFornecedorcidadeidioma(null);
						gerarListaFornecedorCidade();
						return null;
					}
				} else {
					return null;
				}
			} else
				return null;
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(mensagem, ""));
			return null;
		}
		return "";
	}

	public void listarCoProdutoFornecedor(Fornecedorcidadeidioma fornecedorCidadeIdioma) {
		FornecedorProdutosBean fpb = new FornecedorProdutosBean();
		fpb.setFornecedorcidadeidioma(fornecedorCidadeIdioma);
		Ocurso nocurso = new Ocurso();
		if (filtrarEscolaBean.getOcurso().getDataorcamento() == null) {
			nocurso.setDataorcamento(new Date());
		} else {
			nocurso.setDataorcamento(filtrarEscolaBean.getOcurso().getDataorcamento());
		}
		nocurso.setDatainicio(filtrarEscolaBean.getOcurso().getDatainicio());
		nocurso.setDatatermino(calcularDataTerminoCurso(filtrarEscolaBean.getOcurso().getDatainicio(),
				filtrarEscolaBean.getOcurso().getNumerosemanas()));
		nocurso.setFornecedorcidadeidioma(fornecedorCidadeIdioma);
		nocurso.setIdioma(filtrarEscolaBean.getOcurso().getIdioma());
		nocurso.setNivelidioma(filtrarEscolaBean.getOcurso().getNivelidioma());
		nocurso.setNumerosemanas(filtrarEscolaBean.getOcurso().getNumerosemanas());
		nocurso.setProdutosorcamento(filtrarEscolaBean.getOcurso().getProdutosorcamento());
		nocurso.setCliente(filtrarEscolaBean.getOcurso().getCliente());
		nocurso.setDataorcamento(filtrarEscolaBean.getOcurso().getDataorcamento());
		nocurso.setOcursodescontoList(filtrarEscolaBean.getOcurso().getOcursodescontoList());
		nocurso.setUsuario(usuarioLogadoMB.getUsuario());
		fpb.setCambio(consultarCambio(fpb));
		nocurso.setCambio(fpb.getCambio());
		fpb.setOcurso(nocurso);
		fpb.setDataConsulta(retornarDataConsultaOrcamento(filtrarEscolaBean.getOcurso().getDatainicio(), fpb));
		filtrarEscolaBean.setOcurso(nocurso);
		fpb.setListaProdutoFornecedor(new ArrayList<ProdutoFornecedorBean>());
		String sql;
		if (filtrarEscolaBean.getTaxaTM() == null) {
			carregarTaxaTM(fpb.getCambio().getValor());
		}
		sql = "Select c from Coprodutos c where c.situacao=TRUE and c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ fornecedorCidadeIdioma.getIdfornecedorcidadeidioma() + " and c.produtosorcamento.idprodutosOrcamento="
				+ filtrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " and c.tipo<>'Acomodacao' and c.tipo<>'Transfer' and c.tipo<>'AcOpcional' and c.apenaspacote=FALSE";
		List<Coprodutos> listaCoProdutos = coProdutosDao.listar(sql);
		if (listaCoProdutos != null) {
			listaCoProdutos = verificarCursoPossuiValor(listaCoProdutos, fpb);
		}
		if (listaCoProdutos != null) {
			fpb.setListaOpcionais(gerarListaValorCoProdutos(fpb, "Opcional", 0, 0));
			calcularFeridado(fpb);
			int numeroSemanasCurso = nocurso.getNumerosemanas();
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutoFornecedorBean produtoFornecedor = new ProdutoFornecedorBean();
				produtoFornecedor.setCoprodutos(listaCoProdutos.get(i));
				produtoFornecedor.setListaObrigaroerios(gerarListaValorCoProdutos(fpb, "Obrigatorio",
						fornecedorCidadeIdioma.getIdfornecedorcidadeidioma(),
						produtoFornecedor.getCoprodutos().getIdcoprodutos()));
				produtoFornecedor.setListaObriSalvo(produtoFornecedor.getListaObrigaroerios());
				if (produtoFornecedor.getCoprodutos().isPacote() == false) {
					gerarPromocaoCurso(produtoFornecedor.getListaObrigaroerios());
				}
				produtoFornecedor
						.setListaCursoPrincipal(buscarCursoPrincipal(produtoFornecedor.getListaObrigaroerios()));
				produtoFornecedor
						.setListaObrigaroerios(removerCursoPrincipal(produtoFornecedor.getListaObrigaroerios()));
				if (filtrarEscolaBean.getTaxaTM() != null) {
					produtoFornecedor.getListaObrigaroerios().add(filtrarEscolaBean.getTaxaTM());
				}
				if (produtoFornecedor.getCoprodutos().isPacote() == false) {
					gerarPromocaoTaxas(produtoFornecedor.getListaCursoPrincipal(),
							produtoFornecedor.getListaObrigaroerios());
					gerarPromocaoBrindes(produtoFornecedor.getListaCursoPrincipal(),
							produtoFornecedor.getListaObrigaroerios(), numeroSemanasCurso, fpb);
				}
				produtoFornecedor.setValorDesconto(0.0f);
				produtoFornecedor.setValorMoedaEstrangeira(valorMoedaEstrangeira(
						produtoFornecedor.getListaObrigaroerios(), produtoFornecedor.getListaCursoPrincipal()));
				produtoFornecedor.setValorMoedaNacional(
						produtoFornecedor.getValorMoedaEstrangeira() * fpb.getCambio().getValor());
				produtoFornecedor.setSvalorMoedaEstrangeira(fpb.getCambio().getMoedas().getSigla() + " "
						+ Formatacao.formatarFloatString(produtoFornecedor.getValorMoedaEstrangeira()));
				produtoFornecedor.setSvalorMoedaNacional(
					moedaNacional + " " + Formatacao.formatarFloatString(produtoFornecedor.getValorMoedaNacional()));
				produtoFornecedor.setLinhaFornecedor(0);
				produtoFornecedor.setFornecedorCidadeIdioma(fornecedorCidadeIdioma);
				fpb.getListaProdutoFornecedor().add(produtoFornecedor);
			}
			filtrarEscolaBean.getListaFornecedorProdutosBean().add(fpb);
		}else {
			if (listaFornecedorAtualizando == null) {
				listaFornecedorAtualizando = new ArrayList<Fornecedor>();
			}
			listaFornecedorAtualizando.add(fornecedorCidadeIdioma.getFornecedorcidade().getFornecedor());
		}
		for (int i = 0; i < filtrarEscolaBean.getListaFornecedorProdutosBean().size(); i++) {
			for (int j = 0; j < filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor().size(); j++) {
				filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor().get(j).setLinhaFornecedorProdutoBean(i);
			}
		}
	}

	public Cambio consultarCambio(FornecedorProdutosBean fornecedorProdutosBean) {
		CambioFacade cambioFacade = new CambioFacade();
		Cambio cambio = null;
		Fornecedorpais fornecedorPais = buscarFornecedorPais(
				fornecedorProdutosBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor()
						.getIdfornecedor(),
				fornecedorProdutosBean.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais()
						.getIdpais());
		int idMoeda = fornecedorProdutosBean.getFornecedorcidadeidioma().getFornecedorcidade().getCidade().getPais()
				.getMoedas().getIdmoedas();
		if (fornecedorPais != null) {
			idMoeda = fornecedorPais.getMoedas().getIdmoedas();
		}
		cambio = cambioFacade.consultarCambioMoedaPais(
				Formatacao.ConvercaoDataSql(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getDatacambio()),
				idMoeda, usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais());
		return cambio;
	}

	public Float valorMoedaEstrangeira(List<ProdutosOrcamentoBean> listaObrigaroerios,
			List<ProdutosOrcamentoBean> listaCurso) {
		float total = 0.0f;
		if (listaObrigaroerios != null) {
			for (int i = 0; i < listaObrigaroerios.size(); i++) {
				if (listaObrigaroerios.get(i).getValorPromocional() != null
						&& listaObrigaroerios.get(i).getValorPromocional() > 0) {
					total = total + listaObrigaroerios.get(i).getValorPromocional();
				} else
					total = total + listaObrigaroerios.get(i).getValorOrigianl();
			}
		}
		if (listaCurso != null) {
			for (int i = 0; i < listaCurso.size(); i++) {
				if (listaCurso.get(i).getValorPromocional() != null && listaCurso.get(i).getValorPromocional() > 0) {
					total = total + listaCurso.get(i).getValorPromocional();
				} else
					total = total + listaCurso.get(i).getValorOrigianl();
			}
		}
		return total;
	}

	public List<ProdutosOrcamentoBean> gerarListaValorCoProdutos(FornecedorProdutosBean fornecedorProdutosBean,
			String tipo, int idfornecedorcidadeidioma, int idCoProdutos) {
		Date dataInicioTarifario = fornecedorProdutosBean.getDataConsulta();
		List<ProdutosOrcamentoBean> listaRetorno = new ArrayList<ProdutosOrcamentoBean>();
		List<Coprodutos> listaCoProdutos;
		String sql = null;
		if (tipo.equalsIgnoreCase("Obrigatorio")) {
			sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos=" + idCoProdutos;
			GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
			filtrarEscolaBean.setListaGrupoObrigatorio(grupoObrigatorioFacade.listar(sql));
			listaCoProdutos = new ArrayList<Coprodutos>();
			int suplementoMenorDeIdade = 0;
			if (filtrarEscolaBean.getListaGrupoObrigatorio() != null) {
				for (int i = 0; i < filtrarEscolaBean.getListaGrupoObrigatorio().size(); i++) {
					int idadeVinculados = 18;
					int idadeestudante=0;
					if(filtrarEscolaBean.getListaGrupoObrigatorio().get(i).getCoprodutos().getIdade()>0){
						idadeVinculados = filtrarEscolaBean.getListaGrupoObrigatorio().get(i).getCoprodutos().getIdade();
					}  
					if (filtrarEscolaBean.getOcurso().getCliente().getDataNascimento() != null) {
						idadeestudante = Formatacao
								.calcularIdade(filtrarEscolaBean.getOcurso().getCliente().getDataNascimento());
					}
					if (idadeestudante >= idadeVinculados) {
						if (filtrarEscolaBean.getListaGrupoObrigatorio().get(i).isMenorobrigatorio()) {
							filtrarEscolaBean.getListaGrupoObrigatorio().remove(i);
							i--;
						} else {
							listaCoProdutos.add(filtrarEscolaBean.getListaGrupoObrigatorio().get(i).getProduto());
							if (filtrarEscolaBean.getOcurso().getCliente().getDataNascimento() != null) {
								if (suplementoMenorDeIdade == 0) {
									if (filtrarEscolaBean.getListaGrupoObrigatorio().get(i).getProduto()
											.isSuplementemenoridade()) { 
										if (idadeestudante < idadeVinculados) {
											String sqlSuplementoIdade = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
													+ fornecedorProdutosBean.getFornecedorcidadeidioma()
															.getIdfornecedorcidadeidioma()
													+ " and c.produtosorcamento.idprodutosOrcamento="
													+ aplicacaoMB.getParametrosprodutos().getSuplementoidade();
											Coprodutos coprodutos = new Coprodutos();
											coprodutos = coProdutosDao.consultar(sqlSuplementoIdade);
						
											if (coprodutos != null) { 
												listaCoProdutos.add(coprodutos);
												suplementoMenorDeIdade = 1;
											}
										}
									}
								}
							}
						}
					} else {
						listaCoProdutos.add(filtrarEscolaBean.getListaGrupoObrigatorio().get(i).getProduto());
						if (filtrarEscolaBean.getOcurso().getCliente().getDataNascimento() != null) {
							if (suplementoMenorDeIdade == 0) {
								if (filtrarEscolaBean.getListaGrupoObrigatorio().get(i).getProduto()
										.isSuplementemenoridade()) { 
									if (idadeestudante <= idadeVinculados) {
										String sqlSuplementoIdade = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
												+ fornecedorProdutosBean.getFornecedorcidadeidioma()
														.getIdfornecedorcidadeidioma()
												+ " and c.produtosorcamento.idprodutosOrcamento="
												+ aplicacaoMB.getParametrosprodutos().getSuplementoidade();
										Coprodutos coprodutos = new Coprodutos();
										coprodutos = coProdutosDao.consultar(sqlSuplementoIdade);
										if (coprodutos != null) {
											listaCoProdutos.add(coprodutos);
											suplementoMenorDeIdade = 1;
										}
									}
								}
							}
						}
					}
				}
				suplementoMenorDeIdade = 0;
			} else {
				FacesMessage mensagem = new FacesMessage("Atenção! ",
						"O tipo de curso não possui escolas disponiveis para esta cidade.");
				FacesContext.getCurrentInstance().addMessage(null, mensagem);
			}
		}else {
			sql = "Select c from Coprodutos c where c.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ fornecedorProdutosBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma() + " and c.tipo='"
					+ tipo + "' and c.produtosorcamento.idprodutosOrcamento<>"
					+ aplicacaoMB.getParametrosprodutos().getSuplementoidade()
					+ " and c.produtosorcamento.idprodutosOrcamento<>"
					+ aplicacaoMB.getParametrosprodutos().getSuplementoacomodacao()
					+ " and c.produtosorcamento.idprodutosOrcamento<>"
					+ aplicacaoMB.getParametrosprodutos().getSuplementomenoridadeacomodacao();
			listaCoProdutos = coProdutosDao.listar(sql);
		}

		if (listaCoProdutos != null) {
			for (int i = 0; i < listaCoProdutos.size(); i++) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						fornecedorProdutosBean, dataInicioTarifario, tipo);
				if (po != null) {
					listaRetorno.add(po);
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), fornecedorProdutosBean,
							new Date(), tipo);
					if (po != null) {
						listaRetorno.add(po);
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), fornecedorProdutosBean,
								dataInicioTarifario, tipo);
						if (po != null) {
							listaRetorno.add(po);
						}
					}
				}
			}
		}
		if (tipo.equalsIgnoreCase("obrigatorio")) {
			if (listaSuplemento != null) {
				for (int i = 0; i < listaSuplemento.size(); i++) {
					listaRetorno.add(listaSuplemento.get(i));
				}
				listaSuplemento = null;
			}
		}
		return listaRetorno;
	}

	public ProdutosOrcamentoBean consultarValores(String tipoData, int idCoProdutos,
			FornecedorProdutosBean fornecedorProdutosBean, Date dataConsulta, String tipo) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = null;
		String sql = "Select v from  Valorcoprodutos v where v.produtosuplemento='valor' and v.datainicial<='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.datafinal>='"
				+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.numerosemanainicial<="
				+ filtrarEscolaBean.getOcurso().getNumerosemanas() + " and v.numerosemanafinal>="
				+ filtrarEscolaBean.getOcurso().getNumerosemanas() + " and v.tipodata='" + tipoData
				+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
		List<Valorcoprodutos> listaValorcoprodutoses = valorCoProdutosFacade.listar(sql);
//		String sqlSuplemento = "Select v from  Valorcoprodutos v where v.datainicial<='" + Formatacao.ConvercaoDataSql(dataConsulta)  + "' and v.datafinal>='"
//				+ Formatacao.ConvercaoDataSql(dataConsulta)
//				+ "' and v.numerosemanainicial<=" + filtrarEscolaBean.getOcurso().getNumerosemanas()
//				+ " and v.numerosemanafinal>=" + filtrarEscolaBean.getOcurso().getNumerosemanas() + " and v.tipodata='"
//				+ tipoData + "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
//		List<Valorcoprodutos> listaValorcoprodutosSuplemento = valorCoProdutosFacade.listar(sqlSuplemento);
//		if (listaValorcoprodutosSuplemento == null) {
//			Date dataTermino = calcularDataTerminoCurso(dataConsulta, filtrarEscolaBean.getOcurso().getNumerosemanas());
//			sqlSuplemento = "Select v from  Valorcoprodutos v where v.datainicial>='"
//					+ Formatacao.ConvercaoDataSql(dataConsulta) + "'" + " and v.datainicial<='"
//					+ Formatacao.ConvercaoDataSql(dataTermino) + "'" + " and v.datafinal>='"
//					+ Formatacao.ConvercaoDataSql(dataConsulta) + "' and v.numerosemanainicial<="
//					+ filtrarEscolaBean.getOcurso().getNumerosemanas() + " and v.numerosemanafinal>="
//					+ filtrarEscolaBean.getOcurso().getNumerosemanas() + " and v.tipodata='" + tipoData
//					+ "' and v.coprodutos.idcoprodutos=" + idCoProdutos;
//			listaValorcoprodutosSuplemento = valorCoProdutosFacade.listar(sqlSuplemento);
//		}
//		if (listaValorcoprodutosSuplemento != null) {
//			for (int n = 0; n < listaValorcoprodutosSuplemento.size(); n++) {
//				if (listaValorcoprodutosSuplemento.get(n).getProdutosuplemento().equalsIgnoreCase("Curso")) {
//					listaValorcoprodutosSuplemento.get(n).getCoprodutos().getProdutosorcamento().setDescricao(
//							listaValorcoprodutosSuplemento.get(n).getCoprodutos().getProdutosorcamento().getDescricao()+" - Curso");
//					Valorcoprodutos valorSuplemente = listaValorcoprodutosSuplemento.get(n);
//					listarValores(valorSuplemente, fornecedorProdutosBean, tipo);
//				}
//			}  
//		}
		if (listaValorcoprodutoses != null) {
			for (int n = 0; n < listaValorcoprodutoses.size(); n++) {
				if(listaValorcoprodutoses.get(n).isDatalimite() 
						&& (listaValorcoprodutoses.get(n).getDatafinal().after(new Date())
								|| listaValorcoprodutoses.get(n).getDatafinal().equals(new Date()))){
					if (valorcoprodutos == null) {
						valorcoprodutos = new Valorcoprodutos();
						valorcoprodutos = listaValorcoprodutoses.get(n);
					} else {
						valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
					}
				}else if(!listaValorcoprodutoses.get(n).isDatalimite()){
					if (valorcoprodutos == null) {
						valorcoprodutos = new Valorcoprodutos();
						valorcoprodutos = listaValorcoprodutoses.get(n);
					} else {
						valorcoprodutos = compararValores(listaValorcoprodutoses.get(n), valorcoprodutos);
					}
				}
			}
		}
		if (valorcoprodutos != null) {
			ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
			po.setValorcoprodutos(valorcoprodutos);
			po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
			int multiplicador = 1;
			if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
				multiplicador = fornecedorProdutosBean.getOcurso().getNumerosemanas();
			} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
				multiplicador = fornecedorProdutosBean.getOcurso().getNumerosemanas() * 7;
			}
			po.setValorOrigianl(valorcoprodutos.getValororiginal() * multiplicador);
			po.setValorOriginalRS(po.getValorOrigianl() * fornecedorProdutosBean.getCambio().getValor());
			return po;
		}
		return null;
	}

	public void listarValores(Valorcoprodutos valorcoprodutos, FornecedorProdutosBean fornecedorProdutosBean,
			String tipo) {
		if (listaSuplemento == null) {
			if (valorcoprodutos != null) {
				ProdutosOrcamentoBean po = new ProdutosOrcamentoBean();
				po.setValorcoprodutos(valorcoprodutos);
				po.setIdproduto(valorcoprodutos.getCoprodutos().getIdcoprodutos());
				int multiplicador = 1;
				if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
					multiplicador = fornecedorProdutosBean.getOcurso().getNumerosemanas();
				} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
					multiplicador = fornecedorProdutosBean.getOcurso().getNumerosemanas() * 7;
				} 
				float valorSuplemento = calcularValorFracionadoSuplemento
					(multiplicador, fornecedorProdutosBean, po, filtrarEscolaBean.getOcurso().getDatainicio());
				if(valorSuplemento<=0){
					valorSuplemento = calcularValorFracionadoSuplemento
							(multiplicador, fornecedorProdutosBean, po, fornecedorProdutosBean.getDataConsulta());
				}
				po.setValorOrigianl(valorSuplemento);
				po.setValorOriginalRS(valorSuplemento * fornecedorProdutosBean.getCambio().getValor());
				if (listaSuplemento == null) {
					listaSuplemento = new ArrayList<ProdutosOrcamentoBean>();
				}
				po.getValorcoprodutos().getCoprodutos()
						.setDescricao("Suplemento de " + po.getValorcoprodutos().getTiposuplemento() + " - Curso");
				if(valorSuplemento>0){
					listaSuplemento.add(po);
				}
			}
		}
	}

	public Valorcoprodutos compararValores(Valorcoprodutos valorNovo, Valorcoprodutos valorAtual) {
		if (valorNovo.getPromocional() != null && valorNovo.getPromocional()) {
			return valorNovo;
		} else
			return valorAtual;
	}

	public Date calcularDataTerminoCurso(Date dataInical, int numeroSemanas) {
		if ((dataInical != null) && (numeroSemanas > 0)) {
			if (numeroSemanas > 0) {
				Date data = Formatacao.calcularDataFinal(dataInical, numeroSemanas);
				int diaSemana = Formatacao.diaSemana(data);
				try {
					if (diaSemana == 1) {
						data = Formatacao.SomarDiasDatas(data, -2);
					} else if (diaSemana == 7) {
						data = Formatacao.SomarDiasDatas(data, -1);
					}
				} catch (Exception ex) {
					Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
							.log(Level.SEVERE, null, ex);
				}
				String sql = "Select f from Fornecedorferias f where f.datafinal>='"
						+ Formatacao.ConvercaoDataSql(dataInical) + "' and f.datafinal<='"
						+ Formatacao.ConvercaoDataSql(data) + "'";
				FornecedorFeriasFacade fornecedorFeriasFacade = new FornecedorFeriasFacade();
				List<Fornecedorferias> listaFornecedorferias = fornecedorFeriasFacade.listar(sql);
				if (listaFornecedorferias == null) {
					listaFornecedorferias = new ArrayList<Fornecedorferias>();
				}
				if (listaFornecedorferias.size() > 0) {
					try {
						data = Formatacao.SomarDiasDatas(data, listaFornecedorferias.get(0).getNumerodias());
					} catch (Exception ex) {
						Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				}
				return data;
			}
		}
		return null;
	}

	public void gerarListaCursos() {
		if (filtrarEscolaBean.getFornecedorcidadeidioma() != null) {
			FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
			String sql = "SELECT f FROM Fornecedorcidadeidiomaproduto f where f.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ filtrarEscolaBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
					+ " and f.produtosorcamento.tipoproduto='C' " + " order by f.produtosorcamento.descricao";
			List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
			if (lista == null) {
				lista = new ArrayList<Fornecedorcidadeidiomaproduto>();
			} else {
				filtrarEscolaBean.setListaProdutosOrcamento(new ArrayList<Produtosorcamento>());
				for (int i = 0; i < lista.size(); i++) {
					filtrarEscolaBean.getListaProdutosOrcamento().add(lista.get(i).getProdutosorcamento());
				}
			}
		}
	}

	public void gerarListaCursoTodasEscolas() {
		String sql = "SELECT f FROM Fornecedorcidadeidiomaproduto f where f.produtosorcamento.tipoproduto='C' and ";
		for (int i = 0; i < filtrarEscolaBean.getListaFornecedorCidadeIdioma().size(); i++) {
			sql = sql + " f.fornecedorcidadeidioma.idfornecedorcidadeidioma="
					+ filtrarEscolaBean.getListaFornecedorCidadeIdioma().get(i).getIdfornecedorcidadeidioma();
			if ((i + 1) < filtrarEscolaBean.getListaFornecedorCidadeIdioma().size()) {
				sql = sql + " or ";
			}
		}  
		sql = sql + "  order by f.produtosorcamento.descricao ";
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
		List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
		if (lista == null) {
			lista = new ArrayList<Fornecedorcidadeidiomaproduto>();
		} else {
			filtrarEscolaBean.setListaProdutosOrcamento(new ArrayList<Produtosorcamento>());
			for (int i = 0; i < lista.size(); i++) {
				if (verificarListaProdutosOrcamento(lista.get(i).getProdutosorcamento())) {
					filtrarEscolaBean.getListaProdutosOrcamento().add(lista.get(i).getProdutosorcamento());
				}
			}
		}
	}
	
	public boolean verificarListaProdutosOrcamento(Produtosorcamento produtoOcamento) {
		int idProdutoOrcamento = produtoOcamento.getIdprodutosOrcamento();
		for (int i=0;i<filtrarEscolaBean.getListaProdutosOrcamento().size();i++) {
			if (filtrarEscolaBean.getListaProdutosOrcamento().get(i).getIdprodutosOrcamento()==idProdutoOrcamento) {
				return false;
			}
		}
		return true;
	}

	public String pesquisarCliente() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 650);
		RequestContext.getCurrentInstance().openDialog("consultarCoCliente", options, null);
		return "";
	}

	public void gerarListaPublicidade() {
		PublicidadeFacade publicidadeFacade = new PublicidadeFacade();
		try {
			filtrarEscolaBean.setListaPublicidades(publicidadeFacade.listar());
			if (filtrarEscolaBean.getListaPublicidades() == null) {
				filtrarEscolaBean.setListaPublicidades(new ArrayList<Publicidade>());
			}
		} catch (SQLException ex) {
			Logger.getLogger(FiltrarEscolaMB.class.getName()).log(Level.SEVERE, null, ex);
			mostrarMensagem(ex, "Erro Listar Publicidade", "ERRO");
		}
	}

	public void mostrarMensagem(Exception ex, String erro, String titulo) {
		FacesContext context = FacesContext.getCurrentInstance();
		erro = erro + " - " + ex;
		context.addMessage(null, new FacesMessage(titulo, erro));
	}

	public void retornoDialogNovo(SelectEvent event) {
		Cliente cliente = (Cliente) event.getObject();
		filtrarEscolaBean.getOcurso().setCliente(cliente);
		digitosFoneCelular = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), cliente.getFoneCelular());
		digitosFoneResidencial = aplicacaoMB.checkBoxTelefone(
				usuarioLogadoMB.getUsuario().getUnidadenegocio().getDigitosTelefone(), cliente.getFoneResidencial());
	}

	public String editarcambio() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 260);
		RequestContext.getCurrentInstance().openDialog("editarcambio", options, null);
		return "";
	}

	public void verificarListaEscolas() {
		List<FornecedorProdutosBean> listaFornecedorProduto = new ArrayList<FornecedorProdutosBean>();
		for (int i = 0; i < filtrarEscolaBean.getListaFornecedorProdutosBean().size(); i++) {
			for (int n = 0; n < filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor()
					.size(); n++) {
				if (filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor().get(n)
						.getListaObrigaroerios().size() == 0) {
					filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor().remove(n);
				}
			}
			if (filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor().size() > 0) {
				listaFornecedorProduto.add(filtrarEscolaBean.getListaFornecedorProdutosBean().get(i));
			}
		}
		filtrarEscolaBean.getListaFornecedorProdutosBean().clear();
		for (int i = 0; i < listaFornecedorProduto.size(); i++) {
			filtrarEscolaBean.getListaFornecedorProdutosBean().add(listaFornecedorProduto.get(i));
		}
		gerarIndiceEscola();
	}

	public void gerarIndiceEscola() {
		for (int i = 0; i < filtrarEscolaBean.getListaFornecedorProdutosBean().size(); i++) {
			for (int n = 0; n < filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor()
					.size(); n++) {
				filtrarEscolaBean.getListaFornecedorProdutosBean().get(i).getListaProdutoFornecedor().get(n)
						.setLinhaFornecedor(i);
			}
		}
	}

	public List<Coprodutos> verificarCursoPossuiValor(List<Coprodutos> listaCoProdutos,
			FornecedorProdutosBean fornecedorProdutosBean) {
		boolean encontrou = false;
		Date dataInicioTarifario = fornecedorProdutosBean.getDataConsulta();
		List<Coprodutos> novaLista = new ArrayList<Coprodutos>();
		for (int i = 0; i < listaCoProdutos.size(); i++) {
			encontrou = false;
			String sql = "Select g from Grupoobrigatorio g where g.coprodutos.idcoprodutos="
					+ listaCoProdutos.get(i).getIdcoprodutos() + " and g.produto.idcoprodutos="
					+ listaCoProdutos.get(i).getIdcoprodutos();
			GrupoObrigatorioFacade grupoObrigatorioFacade = new GrupoObrigatorioFacade();
			filtrarEscolaBean.setListaGrupoObrigatorio(grupoObrigatorioFacade.listar(sql));
			if (filtrarEscolaBean.getListaGrupoObrigatorio() != null) {
				ProdutosOrcamentoBean po = consultarValores("DI", listaCoProdutos.get(i).getIdcoprodutos(),
						fornecedorProdutosBean, dataInicioTarifario, "Obrigatorio");
				if (po != null) {
					encontrou = true;
				} else {
					po = consultarValores("DM", listaCoProdutos.get(i).getIdcoprodutos(), fornecedorProdutosBean,
							new Date(), "Obrigatorio");
					if (po != null) {
						encontrou = true;
					} else {
						po = consultarValores("DS", listaCoProdutos.get(i).getIdcoprodutos(), fornecedorProdutosBean,
								dataInicioTarifario, "Obrigatorio");
						if (po != null) {
							encontrou = true;
						}
					}
				}
			}
			if (encontrou) {
				novaLista.add(listaCoProdutos.get(i));
			}
		}
		return novaLista;

	}

	public void validarEmail() {
		if (Formatacao.validarEmail(filtrarEscolaBean.getOcurso().getCliente().getEmail())) {
			if (filtrarEscolaBean.getOcurso().getCliente() == null
					|| filtrarEscolaBean.getOcurso().getCliente().getIdcliente() == null) {
				ClienteFacade clienteFacade = new ClienteFacade();
				Cliente cliente = clienteFacade.consultarEmail(filtrarEscolaBean.getOcurso().getCliente().getEmail());
				if (cliente != null && cliente.getIdcliente() != null) {
					Mensagem.lancarMensagemInfo("Cliente já existente.", "");
					filtrarEscolaBean.getOcurso().setCliente(cliente);
				}
			}
		}
		verificarIdiomaSelecionado();
	}

	public void verificarIdiomaSelecionado() {
		if (Formatacao.validarEmail(filtrarEscolaBean.getOcurso().getCliente().getEmail())) {
			if (filtrarEscolaBean.getIdioma() != null) {
				if (filtrarEscolaBean.getIdioma().getIdidioma() != null) {
					filtrarEscolaBean.setIdiomaescolhido(false);
				} else
					filtrarEscolaBean.setIdiomaescolhido(true);
			} else
				filtrarEscolaBean.setIdiomaescolhido(true);
		} else
			filtrarEscolaBean.setIdiomaescolhido(true);
	}

	public void carregarTaxaTM(float valorCambio) {
		ValorCoProdutosFacade valorCoProdutosFacade = new ValorCoProdutosFacade();
		Valorcoprodutos valorcoprodutos = valorCoProdutosFacade
				.consultar(aplicacaoMB.getParametrosprodutos().getTaxatmorcamento());
		if (valorcoprodutos != null) {
			filtrarEscolaBean.setTaxaTM(new ProdutosOrcamentoBean());
			filtrarEscolaBean.getTaxaTM().setValorcoprodutos(valorcoprodutos);
			filtrarEscolaBean.getTaxaTM().setSelecionado(true);
			filtrarEscolaBean.getTaxaTM().setValorOrigianl(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getTaxatm() / valorCambio);
			filtrarEscolaBean.getTaxaTM().setValorOriginalRS(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getTaxatm());
			filtrarEscolaBean.getTaxaTM().setValorPromocional(0.0f);
			filtrarEscolaBean.getTaxaTM().setValorPromocionalRS(0.0f);
		}
	}

	public Date retornarDataConsultaOrcamento(Date dataInicio, FornecedorProdutosBean fornecedorProdutosBean) {
		int anoFornecedor = fornecedorProdutosBean.getFornecedorcidadeidioma().getFornecedorcidade().getFornecedor()
				.getAnotarifario();
		Calendar c = new GregorianCalendar();
		c.setTime(dataInicio);
		int ano = Formatacao.getAnoData(dataInicio);
		if (anoFornecedor >= ano) {
			return dataInicio;
		} else if (anoFornecedor < ano) {
			String sData = Formatacao.ConvercaoDataPadrao(dataInicio);
			String dia = sData.substring(0, 2);
			String mes = sData.substring(3, 5);
			sData = dia + "/" + mes + "/" + String.valueOf(anoFornecedor);
			return Formatacao.ConvercaoStringDataBrasil(sData);
		}
		return dataInicio;
	}

	public float calcularValorFracionadoSuplemento(int multiplicador, FornecedorProdutosBean fornecedorProdutosBean,
			ProdutosOrcamentoBean po, Date dataInical) {
		float valorSuplemento = 0.0f; 
		Date dataTermino = calcularDataTerminoCurso(dataInical, filtrarEscolaBean.getOcurso().getNumerosemanas());
		int numeroDias = 0;
		if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
				|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;

		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			valorSuplemento = po.getValorcoprodutos().getValororiginal() * multiplicador;

		} else if ((po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino))) {

			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), po.getValorcoprodutos().getDatafinal());
		} else if ((po.getValorcoprodutos().getDatainicial().before(dataTermino))
				&& (po.getValorcoprodutos().getDatainicial().after(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().after(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);

		} else if ((po.getValorcoprodutos().getDatainicial().before(dataInical))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			if (Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical))) {
				numeroDias = 1;
			}else {
				numeroDias = Formatacao.subtrairDatas(dataInical, po.getValorcoprodutos().getDatafinal());
			}
		}else if ((po.getValorcoprodutos().getDatainicial().before(dataInical)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatainicial())
						.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataInical)))
				&& (po.getValorcoprodutos().getDatafinal().before(dataTermino)
						|| Formatacao.ConvercaoDataSql(po.getValorcoprodutos().getDatafinal())
								.equalsIgnoreCase(Formatacao.ConvercaoDataSql(dataTermino)))) {
			numeroDias = Formatacao.subtrairDatas(po.getValorcoprodutos().getDatainicial(), dataTermino);
		
		}else{
			valorSuplemento = -1;   
			numeroDias = 0;
		}
		if ((valorSuplemento == 0) && (numeroDias > 0)) {
			if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
				int resto = (numeroDias % 7);
				numeroDias = numeroDias / 7;
				if (resto > 0) {
					numeroDias = numeroDias + 1;
				}
			}
			valorSuplemento = po.getValorcoprodutos().getValororiginal();
			valorSuplemento = valorSuplemento * numeroDias;
		}
		if (valorSuplemento < 0) {
			valorSuplemento = 0;
		}
		return valorSuplemento;
	}

	public Fornecedorpais buscarFornecedorPais(int idFornecedor, int idPais) {
		String sql = "SELECT f FROM Fornecedorpais f where f.fornecedor.idfornecedor=" + idFornecedor
				+ " and f.pais.idpais=" + idPais;
		FornecedorPaisFacade fornecedorPaisFacade = new FornecedorPaisFacade();
		return fornecedorPaisFacade.buscar(sql);
	}

	public String validarMascaraFoneResidencial() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneResidencial);
	}

	public String validarMascaraFoneCelular() {
		return aplicacaoMB.validarMascaraTelefone(digitosFoneCelular);
	}

	public void possuiCurso() {
		if (filtrarEscolaBean.getOcurso().getProdutosorcamento() != null) {
			filtrarEscolaBean.setPossuiCurso(false);
		} else
			filtrarEscolaBean.setPossuiCurso(true);
	}

	public List<ProdutosOrcamentoBean> buscarCursoPrincipal(List<ProdutosOrcamentoBean> lista) {
		List<ProdutosOrcamentoBean> curso = new ArrayList<ProdutosOrcamentoBean>();
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoproduto() != null
					&& lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoproduto()
							.equalsIgnoreCase("C")) {
				curso.add(lista.get(i));
				return curso;
			}
		}
		return curso;
	}

	public List<ProdutosOrcamentoBean> removerCursoPrincipal(List<ProdutosOrcamentoBean> lista) {
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoproduto() != null
					&& lista.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoproduto()
							.equalsIgnoreCase("C")) {
				lista.remove(lista.get(i));
				return lista;
			}
		}
		return lista;
	}

	public void gerarPromocaoCurso(List<ProdutosOrcamentoBean> listaObrigatorios) {
		String sql = "select p From Promocaocursocidade p where p.promoacaocurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promoacaocurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ filtrarEscolaBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ filtrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promoacaocurso.idpromoacaocurso";
		PromocaoCursoFornecedorCidadeFacade cidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
		List<Promocaocursocidade> listaPromocaocursocidade = cidadeFacade.listar(sql);
		if (listaPromocaocursocidade != null) { 
			int posicao = 0;
			float desconto = 0.0f; 
			String descricao ="";
			for (int j = 0; j < listaPromocaocursocidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = null; 
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getComplementocurso() != null && 
							(listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoorcamento().equalsIgnoreCase("C") || 
							listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento().getTipoorcamento().equalsIgnoreCase("A"))) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1000;
					}
				} 
				if (listaPromocaocursocidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoValida(listaPromocaocursocidade.get(j).getPromoacaocurso(),
							valorcoprodutos);
					if (tempromocao) { 
						float valordesconto = 0.0f;
						filtrarEscolaBean.setCodigo(listaPromocaocursocidade.get(j).getPromoacaocurso().getCodigo()); 
						if (filtrarEscolaBean.getOcurso().getDatavalidade() == null) {
							filtrarEscolaBean.getOcurso().setDatavalidade(
									listaPromocaocursocidade.get(j).getPromoacaocurso().getDatavalidadefinal());
						} else if (filtrarEscolaBean.getOcurso().getDatavalidade() != null
								&& filtrarEscolaBean.getOcurso().getDatavalidade().after(
										listaPromocaocursocidade.get(j).getPromoacaocurso().getDatavalidadefinal())) {
							filtrarEscolaBean.getOcurso().setDatavalidade(
									listaPromocaocursocidade.get(j).getPromoacaocurso().getDatavalidadefinal());
						}
						if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								valordesconto = valorcoprodutos.getValororiginal()
										* (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() / 100);
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = valordesconto * filtrarEscolaBean.getOcurso().getNumerosemanas();
								}
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								int multiplicador = 1;  
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas() * 7;
								}
								float valorcurso = valorcoprodutos.getValororiginal() * multiplicador;
								if(listaPromocaocursocidade.get(j).getPromoacaocurso().isAcumulapromocao()){
									valorcurso = (valorcoprodutos.getValororiginal() * multiplicador)-desconto;
								} 
								valordesconto = valorcurso
										* (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() / 100); 
							}
						} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana()
										* filtrarEscolaBean.getOcurso().getNumerosemanas();
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal();
							}
						} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getTipodesconto()
								.equalsIgnoreCase("T")) {
							if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana() > 0) {
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = (valorcoprodutos.getValororiginal()
											* filtrarEscolaBean.getOcurso().getNumerosemanas());
								} else {
									valordesconto = (valorcoprodutos.getValororiginal());
								}
								valordesconto = valordesconto
										- (listaPromocaocursocidade.get(j).getPromoacaocurso().getValorsemana()
												* filtrarEscolaBean.getOcurso().getNumerosemanas());
							} else if (listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() != null
									&& listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal() > 0) {
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									valordesconto = (valorcoprodutos.getValororiginal()
											* filtrarEscolaBean.getOcurso().getNumerosemanas());
								} else {
									valordesconto = (valorcoprodutos.getValororiginal());
								}
								valordesconto = valordesconto
										- listaPromocaocursocidade.get(j).getPromoacaocurso().getValortotal();
							}
						}
						if ((listaPromocaocursocidade.get(j).getPromoacaocurso().getValormaximodesconto() != null)
								&& (listaPromocaocursocidade.get(j).getPromoacaocurso().getValormaximodesconto() > 0)) {
							if (valordesconto > listaPromocaocursocidade.get(j).getPromoacaocurso()
									.getValormaximodesconto()) {
								valordesconto = listaPromocaocursocidade.get(j).getPromoacaocurso()
										.getValormaximodesconto();
							}
						} 
						if(valordesconto>0){
							desconto = desconto + valordesconto;
						}
					}
					descricao = descricao + " " +descricaoPromocao(listaPromocaocursocidade.get(j).getPromoacaocurso()); 
				}
			}
			if (desconto > 0) {
				listaObrigatorios.get(posicao).setDescricaopromocao(descricao);
				listaObrigatorios.get(posicao).setValorPromocional(
						listaObrigatorios.get(posicao).getValorOrigianl() - desconto);
				listaObrigatorios.get(posicao)
						.setValorPromocionalRS(listaObrigatorios.get(posicao).getValorPromocional()
								* filtrarEscolaBean.getOcurso().getCambio().getValor());
				listaObrigatorios.get(posicao).setPromocao(true);
			}
		}
	}

	public boolean verificarPromocaoValida(Promocaocurso promocao, Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainicioiniciocurso() != null && promocao.getDatainicioterminiocurso() != null) {
			if ((filtrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDatainicioiniciocurso())
					|| filtrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatainicioiniciocurso()))
					&& (filtrarEscolaBean.getOcurso().getDatainicio().before(promocao.getDatainicioterminiocurso())
							|| filtrarEscolaBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainicioterminiocurso()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if(promocao.getAcomodacaoescola()==true){
			return false;
		}else if (promocao.getDatainicioacomodacao() != null && promocao.getDataterminioacodomodacao() != null) {
			if ((filtrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDatainicioacomodacao())
					|| filtrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatainicioacomodacao()))
					&& (filtrarEscolaBean.getOcurso().getDatatermino().before(promocao.getDataterminioacodomodacao())
							|| filtrarEscolaBean.getOcurso().getDatatermino()
									.equals(promocao.getDataterminioacodomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDatamatricula() != null) {
			String datatexto = Formatacao.ConvercaoDataPadrao(new Date());
			Date data = Formatacao.ConvercaoStringData(datatexto);
			if (data.before(promocao.getDatamatricula())
					|| data.equals(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminocurso() != null) {
			if (filtrarEscolaBean.getOcurso().getDatatermino().before(promocao.getDataterminocurso())
					|| filtrarEscolaBean.getOcurso().getDatatermino().equals(promocao.getDataterminocurso())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanainicio() != null && promocao.getNumerosemanainicio() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (filtrarEscolaBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanainicio()
					&& filtrarEscolaBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaacima() != null && promocao.getValorsemanaacima() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorsemanaacima()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorsemanaigual() != null && promocao.getValorsemanaigual() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorsemanaigual()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (valorcoprodutos.getCoprodutos().getComplementocurso().getTurno()
					.equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer
					.parseInt(valorcoprodutos.getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && valorcoprodutos.getCoprodutos().getComplementocurso()
					.getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;
	}

	public void gerarPromocaoTaxas(List<ProdutosOrcamentoBean> curso, List<ProdutosOrcamentoBean> listaObrigatorios) {
		String sql = "select p From Promocaotaxacidade p where p.promocaotaxacurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaotaxacurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ filtrarEscolaBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ filtrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaotaxacurso.idpromocaotaxacurso";
		PromocaoTaxaCidadeFacade promocaoTaxaCidadeFacade = new PromocaoTaxaCidadeFacade();
		List<Promocaotaxacidade> listaPromocaotaxacidade = promocaoTaxaCidadeFacade.listar(sql);
		if (listaPromocaotaxacidade != null) {
			for (int j = 0; j < listaPromocaotaxacidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = null;
				int idproduto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getProdutosorcamento()
						.getIdprodutosOrcamento();
				int posicao = 0;
				for (int i = 0; i < listaObrigatorios.size(); i++) {
					if (listaObrigatorios.get(i).getValorcoprodutos().getCoprodutos().getProdutosorcamento()
							.getIdprodutosOrcamento() == idproduto) {
						valorcoprodutos = listaObrigatorios.get(i).getValorcoprodutos();
						posicao = i;
						i = 1005;
					}
				}
				if (listaPromocaotaxacidade.get(j) != null && valorcoprodutos != null) {
					filtrarEscolaBean.getOcurso().setTurno(
							curso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getTurno());
					boolean tempromocao = verificarPromocaoTaxasValida(curso,
							listaPromocaotaxacidade.get(j).getPromocaotaxacurso(), valorcoprodutos);
					if (tempromocao) {
						filtrarEscolaBean.setCodigo(listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getCodigo());
						if (filtrarEscolaBean.getOcurso().getDatavalidade() == null) {
							filtrarEscolaBean.getOcurso().setDatavalidade(
									listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getDatavalidadefinal());
						} else if (filtrarEscolaBean.getOcurso().getDatavalidade() != null
								&& filtrarEscolaBean.getOcurso().getDatavalidade().after(
										listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getDatavalidadefinal())) {
							filtrarEscolaBean.getOcurso().setDatavalidade(
									listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getDatavalidadefinal());
						}
						float valordesconto = 0.0f;
						if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("P")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = (valorcoprodutos.getValororiginal() * multiplicador)
										* (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
												/ 100);
							}
						} else if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getTipodesconto()
								.equalsIgnoreCase("V")) {
							if (listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() != null
									&& listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto() > 0) {
								int multiplicador = 1;
								if (valorcoprodutos.getCobranca().equalsIgnoreCase("S")) {
									multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas();
								} else if (valorcoprodutos.getCobranca().equalsIgnoreCase("D")) {
									multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas() * 7;
								}
								valordesconto = listaPromocaotaxacidade.get(j).getPromocaotaxacurso().getValordesconto()
										* multiplicador;
							}
						}
						if (valordesconto > 0) {
							listaObrigatorios.get(posicao).setDescricaopromocao(
									descricaoPromocaoTaxas(listaPromocaotaxacidade.get(j).getPromocaotaxacurso()));
							listaObrigatorios.get(posicao).setValorPromocional(
									listaObrigatorios.get(posicao).getValorOrigianl() - valordesconto);
							listaObrigatorios.get(posicao)
									.setValorPromocionalRS(listaObrigatorios.get(posicao).getValorPromocional()
											* filtrarEscolaBean.getOcurso().getCambio().getValor());
							listaObrigatorios.get(posicao).setPromocao(true);
						}
					}
				}
			}
		}
	}

	public boolean verificarPromocaoTaxasValida(List<ProdutosOrcamentoBean> curso, Promocaotaxacurso promocao,
			Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainiciocursoinicial() != null && promocao.getDatainiciocursofinal() != null) {
			if ((filtrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDatainiciocursoinicial())
					|| filtrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatainiciocursoinicial()))
					&& (filtrarEscolaBean.getOcurso().getDatainicio().before(promocao.getDatainiciocursofinal())
							|| filtrarEscolaBean.getOcurso().getDatainicio()
									.equals(promocao.getDatainiciocursofinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDatafinalacomodacao() != null) {
			if ((filtrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDataacomodacaoinicial())
					|| filtrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDataacomodacaoinicial()))
					&& (filtrarEscolaBean.getOcurso().getDatatermino().before(promocao.getDatafinalacomodacao())
							|| filtrarEscolaBean.getOcurso().getDatatermino()
									.equals(promocao.getDatafinalacomodacao()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getAcomodacaoselecionada()) {
			return false;
		}
		if (promocao.getDatamatriculaenciadaate() != null) {
			String datatexto = Formatacao.ConvercaoDataPadrao(new Date());
			Date data = Formatacao.ConvercaoStringData(datatexto);
			if (data.before(promocao.getDatamatriculaenciadaate())
					|| data.equals(promocao.getDatamatriculaenciadaate())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataterminio() != null) {
			if (filtrarEscolaBean.getOcurso().getDatatermino().before(promocao.getDataterminio())
					|| filtrarEscolaBean.getOcurso().getDatatermino().equals(promocao.getDataterminio())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanasinicial() != null && promocao.getNumerosemanasinicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (filtrarEscolaBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanasinicial()
					&& filtrarEscolaBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() > promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (filtrarEscolaBean.getOcurso().getTurno().equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer.parseInt(
					curso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && curso.get(0).getValorcoprodutos().getCoprodutos()
					.getComplementocurso().getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;

	}

	public String descricaoPromocaoTaxas(Promocaotaxacurso promocaotaxacurso) {
		String descricao = "";
		if (promocaotaxacurso.getAcomodacaoselecionada()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaotaxacurso.getCargahoraria() != null && promocaotaxacurso.getCargahoraria() > 0
				&& promocaotaxacurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaotaxacurso.getCargahoraria() + " "
					+ promocaotaxacurso.getTipocargahoraria() + " | ";
		}
		if (promocaotaxacurso.getDataacomodacaoinicial() != null
				&& promocaotaxacurso.getDatafinalacomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatafinalacomodacao()) + " | ";
		}
		if (promocaotaxacurso.getDatainiciocursoinicial() != null
				&& promocaotaxacurso.getDatainiciocursofinal() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatainiciocursofinal()) + " | ";
		}
		if (promocaotaxacurso.getDatamatriculaenciadaate() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatamatriculaenciadaate()) + " | ";
		}
		if (promocaotaxacurso.getDataterminio() != null) {
			descricao = descricao + "Data termino de curso até: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDataterminio()) + " | ";
		}
		if ((promocaotaxacurso.getDatavalidadefinal() != null)
				&& (promocaotaxacurso.getDatavalidadeinicial() != null)) {
			descricao = descricao + "Data validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadeinicial()) + " a "
					+ Formatacao.ConvercaoDataPadrao(promocaotaxacurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaotaxacurso.getNumerosemanafinal() != null && promocaotaxacurso.getNumerosemanasinicial() != null
				&& promocaotaxacurso.getNumerosemanasinicial() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaotaxacurso.getNumerosemanasinicial() + " até "
					+ promocaotaxacurso.getNumerosemanafinal() + " | ";
		}
		if (promocaotaxacurso.getTurno() != null && promocaotaxacurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaotaxacurso.getTurno() + " | ";
		}
		if (promocaotaxacurso.getValorporsemana() != null && promocaotaxacurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaotaxacurso.getValorporsemana()) + " | ";
		}

		if (promocaotaxacurso.getTipodesconto() != null) {
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + "% | ";
			}
			if (promocaotaxacurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre "
						+ promocaotaxacurso.getProdutosorcamento().getDescricao() + ": "
						+ Formatacao.formatarFloatString(promocaotaxacurso.getValordesconto()) + " | ";
			}
		}
		return descricao;
	}

	public String descricaoPromocao(Promocaocurso promocaocurso) {
		String descricao = "";
		if (promocaocurso.getAcomodacaoescola()) {
			descricao = descricao + "Acomodação com a escola | ";
		}
		if (promocaocurso.getCargahoraria() != null && promocaocurso.getCargahoraria() > 0
				&& promocaocurso.getTipocargahoraria() != null) {
			descricao = descricao + "Carga Horaria: " + promocaocurso.getCargahoraria() + " "
					+ promocaocurso.getTipocargahoraria() + " | ";
		}
		if (promocaocurso.getDatainicioacomodacao() != null && promocaocurso.getDataterminioacodomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatainicioacomodacao()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDataterminioacodomodacao()) + " | ";
		}
		if (promocaocurso.getDatainicioiniciocurso() != null && promocaocurso.getDatainicioterminiocurso() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatainicioiniciocurso()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatainicioterminiocurso()) + " | ";
		}
		if (promocaocurso.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatamatricula()) + " | ";
		}
		if (promocaocurso.getDataterminocurso() != null) {
			descricao = descricao + "Data termino de curso até: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDataterminocurso()) + " | ";
		}
		if ((promocaocurso.getDatavalidadeinicial() != null) && (promocaocurso.getDatavalidadefinal() != null)) {
			descricao = descricao + "Data validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatavalidadeinicial()) + " a "
					+ Formatacao.ConvercaoDataPadrao(promocaocurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaocurso.getNumerosemanafinal() != null && promocaocurso.getNumerosemanainicio() != null
				&& promocaocurso.getNumerosemanainicio() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaocurso.getNumerosemanainicio() + " até "
					+ promocaocurso.getNumerosemanafinal() + " | ";
		}
		if (promocaocurso.getTurno() != null && promocaocurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaocurso.getTurno() + " | ";
		}
		if (promocaocurso.getValormaximodesconto() != null && promocaocurso.getValormaximodesconto() > 0) {
			descricao = descricao + "Valor máximo de desconto: "
					+ Formatacao.formatarFloatString(promocaocurso.getValormaximodesconto()) + " | ";
		}
		if (promocaocurso.getValorsemanaacima() != null && promocaocurso.getValorsemanaacima() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaocurso.getValorsemanaacima()) + " | ";
		}
		if (promocaocurso.getValorsemanaigual() != null && promocaocurso.getValorsemanaigual() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaocurso.getValorsemanaigual()) + " | ";
		}
		if (promocaocurso.getTipodesconto() != null && promocaocurso.getValorsemana() != null
				&& promocaocurso.getValorsemana() > 0) {
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre o curso por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + "% | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre o curso por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + " | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional sobre o curso por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + " | ";
			}
		}
		if (promocaocurso.getTipodesconto() != null && promocaocurso.getValortotal() != null
				&& promocaocurso.getValortotal() > 0) {
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre o curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal()) + "% | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre o curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal()) + " | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional do curso: "
						+ Formatacao.formatarFloatString(promocaocurso.getValortotal()) + " | ";
			}
		}
		return descricao;
	}

	public void gerarPromocaoBrindes(List<ProdutosOrcamentoBean> listaCurso,
			List<ProdutosOrcamentoBean> listaObrigatorio, int numeroSemanasCurso, FornecedorProdutosBean fpb) {
		if (listaCurso != null && listaCurso.size() > 0 && listaCurso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso()!= null) {
			filtrarEscolaBean.getOcurso()
				.setTurno(listaCurso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getTurno());
		}
		String sql = "select p From Promocaobrindecursocidade p where p.promocaobrindecurso.datavalidadeinicial<='"
				+ Formatacao.ConvercaoDataSql(new Date()) + "' and p.promocaobrindecurso.datavalidadefinal>='"
				+ Formatacao.ConvercaoDataSql(new Date())
				+ "'  and p.fornecedorcidadeidiomaproduto.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ filtrarEscolaBean.getOcurso().getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
				+ " and p.fornecedorcidadeidiomaproduto.produtosorcamento.idprodutosOrcamento="
				+ filtrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()
				+ " group by p.promocaobrindecurso.idpromocaobrindecurso";  
		PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
		List<Promocaobrindecursocidade> listaPromocaoBrindeCursoCidade = promocaoBrindeCursoCidadeFacade.listar(sql);
		if (listaPromocaoBrindeCursoCidade != null) {
			for (int j = 0; j < listaPromocaoBrindeCursoCidade.size(); j++) {
				Valorcoprodutos valorcoprodutos = listaCurso.get(0).getValorcoprodutos();
				if (listaPromocaoBrindeCursoCidade.get(j) != null && valorcoprodutos != null) {
					boolean tempromocao = verificarPromocaoBrindesValido(listaCurso,
							listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso(), valorcoprodutos);
					if (tempromocao) {
						filtrarEscolaBean.setCodigo(listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getCodigo());
						if (filtrarEscolaBean.getOcurso().getDatavalidade() == null) {
							filtrarEscolaBean.getOcurso().setDatavalidade(listaPromocaoBrindeCursoCidade.get(j)
									.getPromocaobrindecurso().getDatavalidadefinal());
						} else if (filtrarEscolaBean.getOcurso().getDatavalidade() != null
								&& filtrarEscolaBean.getOcurso().getDatavalidade().after(listaPromocaoBrindeCursoCidade
										.get(j).getPromocaobrindecurso().getDatavalidadefinal())) {
							filtrarEscolaBean.getOcurso().setDatavalidade(listaPromocaoBrindeCursoCidade.get(j)
									.getPromocaobrindecurso().getDatavalidadefinal());
						}
						if (listaCurso.get(0).getDescricaobrinde() == null) {
							float valordesconto = 0.0f;
							if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso().getGanhasemana() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhasemana() > 0) {
								int numeroSemana = 0;
								if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
										.getNumerosemanacurso() > 0) {
									if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getNumerosemanacurso() == numeroSemanasCurso) {
										numeroSemana = listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhasemana();
									} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getNumerosemanacurso() < numeroSemanasCurso) {
										numeroSemana = (int) numeroSemanasCurso / listaPromocaoBrindeCursoCidade.get(j)
												.getPromocaobrindecurso().getNumerosemanacurso();
										if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhasemana() >= 1) {
											numeroSemana = numeroSemana * listaPromocaoBrindeCursoCidade.get(j)
													.getPromocaobrindecurso().getGanhasemana();
										}
									}
								}
								listaCurso.get(0).setDescricaobrinde("Matricule-se até o dia "
										+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
												.getPromocaobrindecurso().getDatamatricula())
										+ " compre " + numeroSemanasCurso + " semanas e ganhe " + numeroSemana
										+ " semanas FREE.");
								listaCurso.get(0).setPromocao(true);
								fpb.getOcurso().setNumerosemanasbrinde(numeroSemana);
								int nsemanastotal=numeroSemanasCurso+numeroSemana;
								Date datatermino = calcularDataTerminoCurso
										(fpb.getOcurso().getDatainicio(), nsemanastotal);
								fpb.getOcurso().setDatatermino(datatermino);
								listaCurso.get(0).setPromocao(true);
								int idtaxatm = filtrarEscolaBean.getTaxaTM().getIdproduto();
								for (int i = 0; i < listaObrigatorio.size(); i++) {
									if (idtaxatm != listaObrigatorio.get(i).getIdproduto()) {
										ProdutosOrcamentoBean po = listaObrigatorio.get(i);
										int multiplicador = 1;
										if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("S")) {
											multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas();
										} else if (po.getValorcoprodutos().getCobranca().equalsIgnoreCase("D")) {
											multiplicador = filtrarEscolaBean.getOcurso().getNumerosemanas() * 7;
										}
										po.setValorOrigianl(po.getValorcoprodutos().getValororiginal() * multiplicador);
										po.setValorOriginalRS(po.getValorOrigianl()
												* filtrarEscolaBean.getOcurso().getCambio().getValor());
									}
								}

							} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescontosemana() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescontosemana() > 0) {
								valordesconto = valorcoprodutos.getValororiginal() * listaPromocaoBrindeCursoCidade
										.get(j).getPromocaobrindecurso().getGanhadescontosemana();
								int numeroSemanas = filtrarEscolaBean.getOcurso().getNumerosemanas()
										- listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
												.getGanhadescontosemana();
								listaCurso.get(0)
										.setDescricaobrinde("Matricule-se até o dia "
												+ Formatacao.ConvercaoDataPadrao(listaPromocaoBrindeCursoCidade.get(j)
														.getPromocaobrindecurso().getDatamatricula())
												+ " pague " + numeroSemanas + " semanas e curse "
												+ filtrarEscolaBean.getOcurso().getNumerosemanas() + ".");
								listaCurso.get(0).setPromocao(true);
							} else if (listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
									.getGanhadescricao() != null
									&& listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()
											.getGanhadescricao().length() > 2) {
								listaCurso.get(0).setDescricaobrinde(listaPromocaoBrindeCursoCidade.get(j)
										.getPromocaobrindecurso().getGanhadescricao());
								listaCurso.get(0).setPromocao(true);
							}
							listaCurso.get(0).setDescricaopromocao(
									listaCurso.get(0).getDescricaopromocao() + "  " + descricaoPromocaoBrinde(
											listaPromocaoBrindeCursoCidade.get(j).getPromocaobrindecurso()));
							if (listaCurso.get(0).getValorPromocional() == null
									|| listaCurso.get(0).getValorPromocional() == 0) {
								listaCurso.get(0)
										.setValorPromocional(listaCurso.get(0).getValorOrigianl() - valordesconto);
								listaCurso.get(0).setValorPromocionalRS(listaCurso.get(0).getValorPromocional()
										* filtrarEscolaBean.getOcurso().getCambio().getValor());
							} else {
								listaCurso.get(0)
										.setValorPromocional(listaCurso.get(0).getValorPromocional() - valordesconto);
								listaCurso.get(0).setValorPromocionalRS(listaCurso.get(0).getValorPromocional()
										* filtrarEscolaBean.getOcurso().getCambio().getValor());
							}
						}
					}
				}
			}
		}

	}

	public boolean verificarPromocaoBrindesValido(List<ProdutosOrcamentoBean> listaCurso, Promocaobrindecurso promocao,
			Valorcoprodutos valorcoprodutos) {
		Boolean tempromocao = false;
		if (promocao.getDatainicio() != null && promocao.getDatafinal() != null) {
			if ((filtrarEscolaBean.getOcurso().getDatainicio().after(promocao.getDatainicio())
					|| filtrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatainicio()))
					&& (filtrarEscolaBean.getOcurso().getDatainicio().before(promocao.getDatafinal())
							|| filtrarEscolaBean.getOcurso().getDatainicio().equals(promocao.getDatafinal()))) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getDataacomodacaoinicial() != null && promocao.getDataacomodacaofinal() != null) { 
			return false; 
		}
		if (promocao.getDatamatricula() != null) {
			String datatexto = Formatacao.ConvercaoDataPadrao(new Date());
			Date data = Formatacao.ConvercaoStringData(datatexto);
			if (data.before(promocao.getDatamatricula())
					|| data.equals(promocao.getDatamatricula())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanainicial() != null && promocao.getNumerosemanainicial() > 0
				&& promocao.getNumerosemanafinal() != null && promocao.getNumerosemanafinal() > 0) {
			if (filtrarEscolaBean.getOcurso().getNumerosemanas() >= promocao.getNumerosemanainicial()
					&& filtrarEscolaBean.getOcurso().getNumerosemanas() <= promocao.getNumerosemanafinal()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getNumerosemanacurso() != null && promocao.getNumerosemanacurso() > 0) {
			tempromocao = true;
		}
		if (promocao.getNumerosemanaacomodacao() != null && promocao.getNumerosemanaacomodacao() > 0) {
			tempromocao = true;
		}
		if (promocao.getValorporsemana() != null && promocao.getValorporsemana() > 0) {
			if (valorcoprodutos.getValororiginal() == promocao.getValorporsemana()) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getTurno() != null && promocao.getTurno().length() > 1) {
			if (filtrarEscolaBean.getOcurso().getTurno().equalsIgnoreCase(promocao.getTurno())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		if (promocao.getCargahoraria() != null && promocao.getCargahoraria() > 0
				&& promocao.getTipocargahoraria() != null) {
			int cargahoraria = Integer.parseInt(
					listaCurso.get(0).getValorcoprodutos().getCoprodutos().getComplementocurso().getCargahoraria());
			if (cargahoraria == promocao.getCargahoraria() && listaCurso.get(0).getValorcoprodutos().getCoprodutos()
					.getComplementocurso().getTipocargahoraria().equalsIgnoreCase(promocao.getTipocargahoraria())) {
				tempromocao = true;
			} else {
				return false;
			}
		}
		return tempromocao;

	}

	public String descricaoPromocaoBrinde(Promocaobrindecurso promocaobrindecurso) {
		String descricao = "";
		if (promocaobrindecurso.getDatavalidadeinicial() != null
				&& promocaobrindecurso.getDatavalidadeinicial() != null) {
			descricao = descricao + "Período de validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadeinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaobrindecurso.getNumerosemanainicial() != null && promocaobrindecurso.getNumerosemanafinal() != null
				&& promocaobrindecurso.getNumerosemanainicial() > 0 && promocaobrindecurso.getNumerosemanafinal() > 0) {
			descricao = descricao + "Nº de semanas: " + promocaobrindecurso.getNumerosemanainicial() + " até "
					+ promocaobrindecurso.getNumerosemanafinal() + " | ";
		}
		if (promocaobrindecurso.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatamatricula()) + " | ";
		}
		if ((promocaobrindecurso.getDataacomodacaoinicial() != null)
				&& (promocaobrindecurso.getDataacomodacaofinal() != null)) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaoinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDataacomodacaofinal()) + " | ";
		}
		if ((promocaobrindecurso.getDatainicio() != null) && (promocaobrindecurso.getDatafinal() != null)) {
			descricao = descricao + "Data início do curso entre: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatainicio()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatafinal()) + " | ";
		}
		if (promocaobrindecurso.getValorporsemana() != null && promocaobrindecurso.getValorporsemana() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaobrindecurso.getValorporsemana()) + " | ";
		}
		if (promocaobrindecurso.getTurno() != null && promocaobrindecurso.getTurno().length() > 2) {
			descricao = descricao + "Turno: " + promocaobrindecurso.getTurno() + " | ";
		}
		if (promocaobrindecurso.getNumerosemanacurso() != null && promocaobrindecurso.getNumerosemanacurso() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanacurso() + " semana(s) curso | ";
		}
		if (promocaobrindecurso.getNumerosemanaacomodacao() != null
				&& promocaobrindecurso.getNumerosemanaacomodacao() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanaacomodacao()
					+ " semana(s) acomodação  | ";
		}
		if (promocaobrindecurso.getGanhasemana() != null && promocaobrindecurso.getGanhasemana() > 0) {
			descricao = descricao + "Ganha: " + promocaobrindecurso.getGanhasemana() + " semana(s) de curso ";
		}
		if (promocaobrindecurso.getGanhadescontosemana() != null && promocaobrindecurso.getGanhadescontosemana() > 0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemana()
					+ " semana(s) de curso ";
		}
		if (promocaobrindecurso.getGanhadescontosemanaacomodacao() != null
				&& promocaobrindecurso.getGanhadescontosemanaacomodacao() > 0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemanaacomodacao()
					+ " semana(s) de acomodação ";
		}
		if (promocaobrindecurso.getGanhadescricao() != null && promocaobrindecurso.getGanhadescricao().length() > 2) {
			descricao = descricao + "Brinde: " + promocaobrindecurso.getGanhadescricao();
		}
		return descricao;
	}

	public void calcularFeridado(FornecedorProdutosBean fpb) {
		
		String sql = "SELECT o FROM Ocursoferiado o where o.fornecedorcidade.idfornecedorcidade="
				+ fpb.getFornecedorcidadeidioma().getFornecedorcidade().getIdfornecedorcidade()
				+ " and o.datainicial>='" + Formatacao.ConvercaoDataSql(fpb.getOcurso().getDatainicio()) + "' and o.datainicial<='" + 
				Formatacao.ConvercaoDataSql(fpb.getOcurso().getDatatermino()) + "'";
		List<Ocursoferiado> listaFeriados = ocursoFeriadoDao.listar(sql);
		int numeroDias = 0;
		if (listaFeriados != null) {
			fpb.setListaOcrusoFeriado(listaFeriados);
			for (int i = 0; i < listaFeriados.size(); i++) {
				int diascalculo = 0;
				if (!listaFeriados.get(i).getContacurso()) {
					if (fpb.getOcurso().getDatainicio().before(listaFeriados.get(i).getDatainicial())) {
						if (fpb.getOcurso().getDatatermino().before(listaFeriados.get(i).getDatafinal())) {
							diascalculo = Formatacao.subtrairDatas(fpb.getOcurso().getDatatermino(),
									listaFeriados.get(i).getDatainicial());
						} else {
							diascalculo = Formatacao.subtrairDatas(listaFeriados.get(i).getDatainicial(),
									listaFeriados.get(i).getDatafinal());
						}

					}
				} else {
					if (fpb.getOcurso().getDatatermino().before(listaFeriados.get(i).getDatafinal())) {
						diascalculo = Formatacao.subtrairDatas(fpb.getOcurso().getDatatermino(),
								listaFeriados.get(i).getDatainicial());
					} else {
						diascalculo = Formatacao.subtrairDatas(listaFeriados.get(i).getDatainicial(),
								listaFeriados.get(i).getDatafinal());
					}
				}
				numeroDias = numeroDias + diascalculo + 1;
				numeroDias = numeroDias / 7;
				if (numeroDias > 0) {
					fpb.getOcurso().setNumerosemanastotal(fpb.getOcurso().getNumerosemanas() + numeroDias);
					Date data = Formatacao.calcularDataFinal(fpb.getOcurso().getDatainicio(),
							fpb.getOcurso().getNumerosemanastotal());
					int diaSemana = Formatacao.diaSemana(data);
					try {
						if (diaSemana == 1) {
							data = Formatacao.SomarDiasDatas(data, -2);
						} else if (diaSemana == 7) {
							data = Formatacao.SomarDiasDatas(data, -1);
						}
						fpb.getOcurso().setDatatermino(data);
						fpb.getOcurso().setNumerodiasferiado(numeroDias * 7);
					} catch (Exception ex) {
						Logger.getLogger(br.com.travelmate.managerBean.OrcamentoCurso.FiltrarEscolaMB.class.getName())
								.log(Level.SEVERE, null, ex);
					}
				} else {
					fpb.getOcurso().setNumerosemanastotal(fpb.getOcurso().getNumerosemanas());
					fpb.getOcurso().setNumerodiasferiado(0);
				}
			}

		} else {
			fpb.setListaOcrusoFeriado(new ArrayList<Ocursoferiado>());
			fpb.getOcurso().setNumerosemanastotal(fpb.getOcurso().getNumerosemanas());
		}
	}

	public void selecionarCliente(Cliente cliente) {
		filtrarEscolaBean.getOcurso().setCliente(cliente);
	}
	
	public void verificarDatas(){
		if(filtrarEscolaBean.getOcurso().getProdutosorcamento()!=null
				&& filtrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento()!=null
				&& filtrarEscolaBean.getFornecedorcidadeidioma()!=null
				&& filtrarEscolaBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()!=null){
			FornecedorCidadeIdiomaProdutoFacade produtoFacade = new FornecedorCidadeIdiomaProdutoFacade();
			Fornecedorcidadeidiomaproduto fornecedorcidadeidiomaproduto = produtoFacade
					.consultar("SELECT f FROM Fornecedorcidadeidiomaproduto f WHERE"
							+ " f.fornecedorcidadeidioma.idfornecedorcidadeidioma="+filtrarEscolaBean.getFornecedorcidadeidioma().getIdfornecedorcidadeidioma()
							+ " AND f.produtosorcamento.idprodutosOrcamento="+filtrarEscolaBean.getOcurso().getProdutosorcamento().getIdprodutosOrcamento());
			if(fornecedorcidadeidiomaproduto!=null && fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto()!=null){
				FornecedorCidadeIdiomaProdutoDataFacade dataFacade = new FornecedorCidadeIdiomaProdutoDataFacade();
				List<Fornecedorcidadeidiomaprodutodata> lista =
						dataFacade.listar("SELECT f FROM Fornecedorcidadeidiomaprodutodata f WHERE"
								+ " f.fornecedorcidadeidiomaproduto.idfornecedorcidadeidiomaproduto="
								+fornecedorcidadeidiomaproduto.getIdfornecedorcidadeidiomaproduto() 
										+ " order by f.datainicio");
				if(lista!=null){    
					listaFornecedorCidadeDatas= new ArrayList<>();
					listaDatas = new ArrayList<>();
					boolean semData = false;
					String dataString = Formatacao.ConvercaoDataPadrao(new Date());
					Date dataAtual = Formatacao.ConvercaoStringData(dataString);
					for (int i = 0; i < lista.size(); i++) { 
						String dataInicioString = Formatacao.ConvercaoDataPadrao(lista.get(i).getDatainicio());
						Date dataIncio = Formatacao.ConvercaoStringData(dataInicioString);
						if (dataIncio.before(dataAtual) && (listaDatas == null || listaDatas.isEmpty())) {
							semData = true;
						}else {
							DatasBean datasBean = new DatasBean();
							datasBean.setDescricao(Formatacao.ConvercaoDataPadrao(lista.get(i).getDatainicio()));
							datasBean.setNumerosemanas(lista.get(i).getNumerosemanas());
							listaDatas.add(datasBean);
							listaFornecedorCidadeDatas.add(datasBean);
							semData = false;
						}
					}
					calendario = false;
					comboDatas = true;
					if (semData) {
						Mensagem.lancarMensagemInfo("Nenhuma data encontrada", "");
					}
				}else{
					calendario = true;
					comboDatas = false;
				}
			}else{
				calendario = true;
				comboDatas = false;
			}
		}
	}
	
	public void verificarDatasNumeroSemana(){
		if(listaFornecedorCidadeDatas!=null && listaFornecedorCidadeDatas.size()>0 && 
				filtrarEscolaBean.getOcurso().getNumerosemanas()!=null && filtrarEscolaBean.getOcurso().getNumerosemanas()>0){
			listaDatas = new ArrayList<>();
			for (int i = 0; i < listaFornecedorCidadeDatas.size(); i++) {
				if(listaFornecedorCidadeDatas.get(i).getNumerosemanas()==filtrarEscolaBean.getOcurso().getNumerosemanas()
						|| listaFornecedorCidadeDatas.get(i).getNumerosemanas()==0){
					listaDatas.add(listaFornecedorCidadeDatas.get(i));
				}
			}
			if (listaDatas == null || listaDatas.isEmpty()) {
				Mensagem.lancarMensagemErro("Nenhuma data disponivel com o número de semana informado", "");
			}
		}
	}
	
	public void listarCidades() {
		CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
		String sql = "SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.pais.idpais="+filtrarEscolaBean.getPais().getIdpais()
				+ " and c.paisproduto.produtos.idprodutos="+lead.getProdutos().getIdprodutos()
				+ " ORDER BY c.cidade.nome";
		filtrarEscolaBean.setListaCidade(cidadePaisProdutosFacade.listar(sql));  
	}
	
	public String retornarEstrela(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		if(fornecedorcidadeidioma!=null) {
			if(fornecedorcidadeidioma.getFornecedorcidade().getNumestrelas()==0) {
				return "estrelacinza.png";
			}else if(fornecedorcidadeidioma.getFornecedorcidade().getNumestrelas()==1) {
				if(fornecedorcidadeidioma.getFornecedorcidade().isToptmstar()) {
					return "estrelatop1.png";
				}else return "estrela1.png";
			}else if(fornecedorcidadeidioma.getFornecedorcidade().getNumestrelas()==2) {
				if(fornecedorcidadeidioma.getFornecedorcidade().isToptmstar()) {
					return "estrelatop2.png";
				}else return "estrela2.png";
			}else if(fornecedorcidadeidioma.getFornecedorcidade().getNumestrelas()==3) {
				if(fornecedorcidadeidioma.getFornecedorcidade().isToptmstar()) {
					return "estrelatop3.png";
				}else return "estrela3.png";
			}else if(fornecedorcidadeidioma.getFornecedorcidade().getNumestrelas()==4) {
				if(fornecedorcidadeidioma.getFornecedorcidade().isToptmstar()) {
					return "estrelatop4.png";
				}else return "estrela4.png";
			}else if(fornecedorcidadeidioma.getFornecedorcidade().getNumestrelas()==5) {
				if(fornecedorcidadeidioma.getFornecedorcidade().isToptmstar()) {
					return "estrelatop5.png";
				}else return "estrela5.png";
			}
		}
		return "estrelacinza.png";
	}
	
	public String retornaHistoricoLead() {
		if (lead != null) {
			if (lead.getIdlead() != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
				session.setAttribute("lead", lead);
				session.setAttribute("funcao", funcao);
				session.setAttribute("posicao", 0);
				return "historicoCliente";
			}
		}
		return "";
	}
	
	public String uploadImagem() {
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 450);
		RequestContext.getCurrentInstance().openDialog("uploadImagem", options, null);
		return "";
	}
}
