package br.com.travelmate.managerBean.higherEducation.controle;

import java.io.Serializable;
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
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.dao.HeControleDao;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.He;
import br.com.travelmate.model.Hecontrole;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ControleHeMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private HeControleDao heControleDao;
	private Hecontrole hecontrole;
	private List<Hecontrole> listaHeControle;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private String nomeCliente;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private String docs;
	private String situacao;
	private String idvenda;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Date datainivenda;
	private Date datafimvenda;
	private String sql;
	private String obsTM;
	private String pesquisar = "Nao";
	private String situacaoTabela;
	private String chamadaTela = "";
	private List<Hecontrole> listaHeControleFinanceiro;
	private List<Hecontrole> listaHeControleFinalizado;
	private int nFinanceiro;
	private int nFinalizada;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		sql = (String) session.getAttribute("sql");
		chamadaTela = (String) session.getAttribute("chamadaTela");
		session.removeAttribute("chamadaTela");
		session.removeAttribute("sql");
		if (chamadaTela == null || chamadaTela.equalsIgnoreCase("Menu")) {
			sql = null;
		}
		listarControle();   
		gerarListaUnidadeNegocio();
	}

	public Hecontrole getHecontrole() {
		return hecontrole;
	}

	public void setHecontrole(Hecontrole hecontrole) {
		this.hecontrole = hecontrole;
	}

	public List<Hecontrole> getListaHeControle() {
		return listaHeControle;
	}

	public void setListaHeControle(List<Hecontrole> listaHeControle) {
		this.listaHeControle = listaHeControle;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDocs() {
		return docs;
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(String idvenda) {
		this.idvenda = idvenda;
	}

	public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}

	public List<Unidadenegocio> getListaUnidadeNegocio() {
		return listaUnidadeNegocio;
	}

	public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
		this.listaUnidadeNegocio = listaUnidadeNegocio;
	}

	public Date getDatainivenda() {
		return datainivenda;
	}

	public void setDatainivenda(Date datainivenda) {
		this.datainivenda = datainivenda;
	}

	public Date getDatafimvenda() {
		return datafimvenda;
	}

	public void setDatafimvenda(Date datafimvenda) {
		this.datafimvenda = datafimvenda;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getObsTM() {
		return obsTM;
	}

	public void setObsTM(String obsTM) {
		this.obsTM = obsTM;
	}

	public String getPesquisar() {
		return pesquisar;
	}

	public void setPesquisar(String pesquisar) {
		this.pesquisar = pesquisar;
	}

	public String getSituacaoTabela() {
		return situacaoTabela;
	}

	public void setSituacaoTabela(String situacaoTabela) {
		this.situacaoTabela = situacaoTabela;
	}

	public List<Hecontrole> getListaHeControleFinanceiro() {
		return listaHeControleFinanceiro;
	}

	public void setListaHeControleFinanceiro(List<Hecontrole> listaHeControleFinanceiro) {
		this.listaHeControleFinanceiro = listaHeControleFinanceiro;
	}

	public List<Hecontrole> getListaHeControleFinalizado() {
		return listaHeControleFinalizado;
	}

	public void setListaHeControleFinalizado(List<Hecontrole> listaHeControleFinalizado) {
		this.listaHeControleFinalizado = listaHeControleFinalizado;
	}

	public int getnFinanceiro() {
		return nFinanceiro;
	}

	public void setnFinanceiro(int nFinanceiro) {
		this.nFinanceiro = nFinanceiro;
	}

	public int getnFinalizada() {
		return nFinalizada;
	}

	public void setnFinalizada(int nFinalizada) {
		this.nFinalizada = nFinalizada;
	}

	public void listarControle() {
		if (sql != null && sql.length() > 0) {
			listaHeControle = heControleDao.listar(sql);
		} else {
			String data = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
			sql = "select h from Hecontrole h where  h.he.vendas.dataVenda>='" + data
					+ "' order by h.he.vendas.dataVenda desc";
			listaHeControle = heControleDao.listar(sql);
		}
		if (listaHeControle == null) {
			listaHeControle = new ArrayList<Hecontrole>();
		}
		listaHeControleFinalizado = new ArrayList<Hecontrole>();
		listaHeControleFinanceiro = new ArrayList<Hecontrole>();
		for (int i = 0; i < listaHeControle.size(); i++) {
			if (listaHeControle.get(i).getHe().isFichafinal()) {
				listaHeControle.get(i).setTipo("Final");
			} else {
				listaHeControle.get(i).setTipo("Formulário");
			}
			if (listaHeControle.get(i).isImpresso()) {
				listaHeControle.get(i).setIconeFormImpresso("../../resources/img/iconeSApp.png");
			} else {
				listaHeControle.get(i).setIconeFormImpresso("../../resources/img/iconeCheck.png");
			}

			if (listaHeControle.get(i).getHe().getListaHeParceirosList() != null
					&& listaHeControle.get(i).getHe().getListaHeParceirosList().size() > 0) {
				listaHeControle.get(i)
						.setParceiro(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0)
								.getFornecedorcidade().getFornecedor().getNome() + " - "
								+ listaHeControle.get(i).getHe().getListaHeParceirosList().size());
				listaHeControle.get(i).setInicioPrograma(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getDatainiciohe());
				listaHeControle.get(i).setInicioPathway(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).getDatainicio());
				if (listaHeControle.get(i).getHe().getListaHeParceirosList().get(0).isPathway()) {
					listaHeControle.get(i).setPathway("Sim");
				} else {
					listaHeControle.get(i).setPathway("Não");
				}
			}else {
				if (listaHeControle.get(i).getHe().isFichafinal()) {
					listaHeControle.get(i)
							.setParceiro(listaHeControle.get(i).getHe().getVendas().getFornecedorcidade().getFornecedor().getNome());
					listaHeControle.get(i).setCidade(listaHeControle.get(i).getHe().getVendas().getFornecedorcidade().getCidade().getNome());
					listaHeControle.get(i).setInicioPrograma(
							listaHeControle.get(i).getHe().getDatainiciohe());
					listaHeControle.get(i).setPathway(listaHeControle.get(i).getHe().getCursarparhaway());
					listaHeControle.get(i).setInicioPathway(
							listaHeControle.get(i).getHe().getDatainicio());
				}
			}
			if (listaHeControle.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
				listaHeControleFinanceiro.add(listaHeControle.get(i));
			}else if (listaHeControle.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				listaHeControleFinalizado.add(listaHeControle.get(i));
			}
		}
		nFinalizada = listaHeControleFinalizado.size();
		nFinanceiro = listaHeControleFinanceiro.size();
	}

	public void gerarListaUnidadeNegocio() {
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
		if (listaUnidadeNegocio == null) {
			listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
		}
	}

	public String obsTM(Hecontrole hecontrole) {
		obsTM = hecontrole.getHe().getVendas().getObstm();
		return obsTM;
	}

	public String retornarIconeObsTM(Hecontrole hecontrole) {
		if (hecontrole != null && hecontrole.getHe() != null) {
			if (hecontrole.getHe().getVendas().getObstm() != null
					&& hecontrole.getHe().getVendas().getObstm().length() > 0) {
				return "../../resources/img/obsVermelha.png";
			}
		}
		return "../../resources/img/obsFicha.png";
	}

	public String atualizarInformacoes(Hecontrole hecontrole) {
		if (hecontrole != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("hecontrole", hecontrole);
			session.setAttribute("sql", sql);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 700);
			RequestContext.getCurrentInstance().openDialog("atualizarControleHe", options, null);
		}
		return "";
	}

	public void retornoAtualizacaoHe(SelectEvent event) {
		sql = (String) event.getObject();
		listarControle();
	}

	public String visualizarContasReceber(Vendas venda) {
		if ((venda.getOrcamento() != null)) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", venda);
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 620);
			RequestContext.getCurrentInstance().openDialog("visualizarContasReceber", options, null);
		} else {
			FacesMessage msg = new FacesMessage("Venda não Possui Contas a Receber! ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return "";
	}

	public String informacoes(Hecontrole hecontrole) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", hecontrole.getHe().getVendas());
		session.setAttribute("voltar", "consControleHe");
		return "consLogVenda";
	}

	public String documentacao(He he) {
		boolean validar = true;
		if (he.getVendas().getSituacao().equalsIgnoreCase("PROCESSO")
				&& usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() != 1) {
			String dataStringValidade = Formatacao.ConvercaoDataPadrao(new Date());
			Date dataAtual = Formatacao.ConvercaoStringData(dataStringValidade);
			Date dataValidade = he.getVendas().getDatavalidade();
			if (dataValidade != null) {
				if (!dataAtual.after(dataValidade)) {
					validar = true;
				} else {
					validar = false;
				}
			}
		}
		if (!validar) {
			Mensagem.lancarMensagemInfo("Favor atualizar o câmbio desta ficha",
					"está ficha ultrapassou os 3 dias de validade");
			return "";
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("vendas", he.getVendas());
			session.setAttribute("cliente", he.getVendas().getCliente());
			session.setAttribute("pesquisar", "Sim");
			session.setAttribute("nomePrograma", "He");
			session.setAttribute("chamadaTela", "fichaHE");
			String voltar = "consControleHe";
			session.setAttribute("voltar", voltar);
		}
		return "consArquivos";
	}

	public void pesquisar() {  
		sql = "select h from Hecontrole h where  h.he.vendas.cliente.nome like '%" + nomeCliente + "%'";

		if (datainivenda != null && datafimvenda != null) {
			sql = sql + " AND h.he.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(datainivenda)
					+ "' AND h.he.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(datafimvenda) + "' ";
		}

		if (situacao != null && !situacao.equalsIgnoreCase("Selecione")) {
			sql = sql + " AND h.situacaoaplicacao='" + situacao + "' ";
		}

		if (idvenda != null && idvenda.length() > 0) {
			sql = sql + " AND h.he.vendas.idvendas=" + idvenda + " ";
		}

		if (unidadenegocio != null) {
			sql = sql + " AND h.he.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}

		listaHeControle = heControleDao.listar(sql);

		if (listaHeControle == null) {
			listaHeControle = new ArrayList<Hecontrole>();
		}
		listaHeControleFinalizado = new ArrayList<Hecontrole>();
		listaHeControleFinanceiro = new ArrayList<Hecontrole>();
		for (int i = 0; i < listaHeControle.size(); i++) {
			if (listaHeControle.get(i).getHe().isFichafinal()) {
				listaHeControle.get(i).setTipo("Final");
			} else {
				listaHeControle.get(i).setTipo("Formulário");
			}
			if (listaHeControle.get(i).isImpresso()) {
				listaHeControle.get(i).setIconeFormImpresso("../../resources/img/iconeSApp.png");
			} else {
				listaHeControle.get(i).setIconeFormImpresso("../../resources/img/iconeCheck.png");
			}
			if (listaHeControle.get(i).getHe().getListaHeParceirosList() != null
					&& listaHeControle.get(i).getHe().getListaHeParceirosList().size() > 0) {
				listaHeControle.get(i)
						.setParceiro(listaHeControle.get(i).getHe().getListaHeParceirosList().get(0)
								.getFornecedorcidade().getFornecedor().getNome() + " - "
								+ listaHeControle.get(i).getHe().getListaHeParceirosList().size());
			}else {
				if (listaHeControle.get(i).getHe().isFichafinal()) {
					listaHeControle.get(i)
							.setParceiro(listaHeControle.get(i).getHe().getVendas().getFornecedorcidade().getFornecedor().getNome());
					listaHeControle.get(i).setCidade(listaHeControle.get(i).getHe().getVendas().getFornecedorcidade().getCidade().getNome());
					listaHeControle.get(i).setInicioPrograma(
							listaHeControle.get(i).getHe().getDatainicio());
					listaHeControle.get(i).setPathway(listaHeControle.get(i).getHe().getCursarparhaway());
				}
			}
			if (listaHeControle.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
				listaHeControleFinanceiro.add(listaHeControle.get(i));
			}else if (listaHeControle.get(i).getHe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
				listaHeControleFinalizado.add(listaHeControle.get(i));
			}
		}
		nFinalizada = listaHeControleFinalizado.size();
		nFinanceiro = listaHeControleFinanceiro.size();
	}

	public void limpar() {
		sql = null;
		datafimvenda = null;
		datainivenda = null;
		idvenda = null;
		situacao = null;
		unidadenegocio = null;
		nomeCliente = null;
		listarControle();
	}

	public void atualizarSituacao(Hecontrole hecontrole) {
		if (situacaoTabela != null) {
			hecontrole.setSituacaoaplicacao(situacaoTabela);
			heControleDao.salvar(hecontrole);
			situacaoTabela = null;
		}
	}

	public void visualizarParceiros(Hecontrole hecontrole) {
		if (hecontrole.getHe().getListaHeParceirosList() != null
				&& hecontrole.getHe().getListaHeParceirosList().size() > 0) {
			if (hecontrole.getHe().getListaHeParceirosList().size() > 1) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("listaHeParceiros", hecontrole.getHe().getListaHeParceirosList());
				session.setAttribute("voltar", "consControleHe");
				RequestContext.getCurrentInstance().openDialog("visualizarParceiros");
			}
		} else {
			Mensagem.lancarMensagemInfo("Nenhum parceiro encontrado", "");
		}
	}
	
	
	public String fichaHE(He he){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("he", he);
		session.setAttribute("sql", sql);
		chamadaTela = "ControleHe";
		session.setAttribute("chamadaTela", chamadaTela);
		if (he.isFichafinal()) {
			return "fichaHE";
		}else {
			return "fichaFormAssessoria";
		}
	}
	
	public String contrato(He he){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("he", he);
		session.setAttribute("sql", sql);
		session.setAttribute("chamadaTela", "ControleHe");
		if (he.isFichafinal()) {
			return "contratoHeFinal";
		}else {
			return "contratoHE";
		}      
	}
	
	
	public String invoice(Hecontrole hecontrole) {
		if (hecontrole != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			hecontrole.getHe().getVendas().setIdControle(hecontrole.getIdhecontrole());
			session.setAttribute("vendas", hecontrole.getHe().getVendas());
			session.setAttribute("nomePrograma", "ControleHe");
			session.setAttribute("chamadaTela", "ControleHe");
			session.setAttribute("voltar", "consControleHe");
			return "consultaInvoice";
		}
		return "";
	}
	
	
	public void confirmarImpressao(Hecontrole hecontrole) {
		if (hecontrole.isImpresso()) {
			hecontrole.setImpresso(false);
		}else {
			hecontrole.setImpresso(true);
		}
		heControleDao.salvar(hecontrole);
		listarControle();
	}

}
