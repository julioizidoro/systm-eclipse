package br.com.travelmate.managerBean.financeiro.crmcobranca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import br.com.travelmate.facade.CrmCobrancaFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Crmcobranca;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;


@Named
@ViewScoped
public class FollowUpCobrancaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private String imagemNovos = "novos";
	private String imagemHoje = "hojeClick";
	private String imagemAtrasados = "atrasados";
	private String imagemProx = "prox";
	private String imagemTodos = "todos";
	private String sql;
	private String funcao;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private String nomeCliente;
	private Date dataProxInicio;
	private Date dataProxFinal;
	private Usuario usuario;
	private Date dataUltInicio;
	private Date dataUltFinal;
	private Produtos programas;
	private List<Produtos> listaProgramas;
	private int idvenda;
	private String prioridade;
	private List<Usuario> listaUsuario;
	private List<Crmcobranca> listaCrmCobranca;
	private int novos;
	private int atrasados;
	private int hoje;
	private int prox7;
	private int todos;
	private String status;
	
	
	@PostConstruct
	public void init(){
		if (!aplicacaoMB.isLeituraCobranca()) {
			CrmCobrancaBean crmCobrancaBean = new CrmCobrancaBean();
			crmCobrancaBean.gerarListaInadiplentes();
			aplicacaoMB.setLeituraCobranca(true);
		}
		listaUnidade = GerarListas.listarUnidade();
		listaUsuario = GerarListas.listarUsuarios("Select u FROM Usuario u where u.situacao='Ativo'");
		listaProgramas = GerarListas.listarProdutos("Select p From Produtos p");
		if (funcao == null || funcao.length() == 0) {
			funcao = "hoje";
		}
		pesquisar();
		mudarCoresBotoes(funcao);
	}


	public String getImagemNovos() {
		return imagemNovos;
	}


	public void setImagemNovos(String imagemNovos) {
		this.imagemNovos = imagemNovos;
	}


	public String getImagemHoje() {
		return imagemHoje;
	}


	public void setImagemHoje(String imagemHoje) {
		this.imagemHoje = imagemHoje;
	}


	public String getImagemAtrasados() {
		return imagemAtrasados;
	}


	public void setImagemAtrasados(String imagemAtrasados) {
		this.imagemAtrasados = imagemAtrasados;
	}


	public String getImagemProx() {
		return imagemProx;
	}


	public void setImagemProx(String imagemProx) {
		this.imagemProx = imagemProx;
	}


	public String getImagemTodos() {
		return imagemTodos;
	}


	public void setImagemTodos(String imagemTodos) {
		this.imagemTodos = imagemTodos;
	}
	
	
	
	public String getSql() {
		return sql;
	}


	public void setSql(String sql) {
		this.sql = sql;
	}


	public String getFuncao() {
		return funcao;
	}


	public void setFuncao(String funcao) {
		this.funcao = funcao;
	}


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}


	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}


	public String getNomeCliente() {
		return nomeCliente;
	}


	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}


	public Date getDataProxInicio() {
		return dataProxInicio;
	}


	public void setDataProxInicio(Date dataProxInicio) {
		this.dataProxInicio = dataProxInicio;
	}


	public Date getDataProxFinal() {
		return dataProxFinal;
	}


	public void setDataProxFinal(Date dataProxFinal) {
		this.dataProxFinal = dataProxFinal;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Date getDataUltInicio() {
		return dataUltInicio;
	}


	public void setDataUltInicio(Date dataUltInicio) {
		this.dataUltInicio = dataUltInicio;
	}


	public Date getDataUltFinal() {
		return dataUltFinal;
	}


	public void setDataUltFinal(Date dataUltFinal) {
		this.dataUltFinal = dataUltFinal;
	}


	public Produtos getProgramas() {
		return programas;
	}


	public void setProgramas(Produtos programas) {
		this.programas = programas;
	}


	public List<Produtos> getListaProgramas() {
		return listaProgramas;
	}


	public void setListaProgramas(List<Produtos> listaProgramas) {
		this.listaProgramas = listaProgramas;
	}


	public int getIdvenda() {
		return idvenda;
	}


	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}


	public String getPrioridade() {
		return prioridade;
	}


	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}


	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	public List<Crmcobranca> getListaCrmCobranca() {
		return listaCrmCobranca;
	}


	public void setListaCrmCobranca(List<Crmcobranca> listaCrmCobranca) {
		this.listaCrmCobranca = listaCrmCobranca;
	}


	public int getNovos() {
		return novos;
	}


	public void setNovos(int novos) {
		this.novos = novos;
	}


	public int getAtrasados() {
		return atrasados;
	}


	public void setAtrasados(int atrasados) {
		this.atrasados = atrasados;
	}


	public int getHoje() {
		return hoje;
	}


	public void setHoje(int hoje) {
		this.hoje = hoje;
	}


	public int getProx7() {
		return prox7;
	}


	public void setProx7(int prox7) {
		this.prox7 = prox7;
	}


	public int getTodos() {
		return todos;
	}


	public void setTodos(int todos) {
		this.todos = todos;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String retornarCoresPrioridade(int numeroSituacao) {
		if (numeroSituacao == 1) {
			return "#1E90FF;";
		} else if (numeroSituacao == 2) {
			return "#9ACD32;";
		} else if (numeroSituacao == 3) {
			return "#FF8C00;";
		} else if (numeroSituacao == 4) {
			return "#B22222;";
		} else if (numeroSituacao == 5) {
			return "#228B22;";
		} else if (numeroSituacao == 6) {
			return "#8B8989;";
		} else if (numeroSituacao == 7) {
			return "#9400D3;";
		}
		return "";
	}
	
	
	public String historicoCobrancaCliente(Crmcobranca crmcobranca) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("crmcobranca", crmcobranca);
		return "historicoCobrancaCliente";
	}
	
	
	public void mudarCoresBotoes(String funcao) {
		sql = "";
		this.funcao = funcao;
		if (funcao.equalsIgnoreCase("novos")) {
			imagemNovos = "novosClick";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todos";
			sql = "select l from Crmcobranca l where l.proximocontato is null and l.datafinalizada is null ";
		} else if (funcao.equalsIgnoreCase("hoje")) {
			imagemNovos = "novos";
			imagemHoje = "hojeClick";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todos";
			sql = "select l from Crmcobranca l where l.proximocontato='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
		} else if (funcao.equalsIgnoreCase("atrasados")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasadoClick";
			imagemProx = "prox";
			imagemTodos = "todos";
			Date data = new Date();
			sql = "select l from Crmcobranca l where l.proximocontato < '" + Formatacao.ConvercaoDataSql(data) + "'";
		} else if (funcao.equalsIgnoreCase("prox")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "proxClick";
			imagemTodos = "todos";
			Date data;
			try {
				data = Formatacao.SomarDiasDatas(new Date(), 7);
			} catch (Exception e) {
				data = null;
			}
			sql = "select l from Crmcobranca l where  l.proximocontato>'" + Formatacao.ConvercaoDataSql(new Date())
					+ "' and l.proximocontato<'" + Formatacao.ConvercaoDataSql(data) + "'";
		} else if (funcao.equalsIgnoreCase("todos")) {
			imagemNovos = "novos";
			imagemHoje = "hoje";
			imagemAtrasados = "atrasados";
			imagemProx = "prox";
			imagemTodos = "todosClick";
			sql = "select l from Crmcobranca l where (l.nota like '%%' or l.nota is null) ";
		}
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and l.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario != null && usuario.getIdusuario() != null) {
				sql = sql + " and l.vendas.usuario.idusuario=" + usuario.getIdusuario();
		} 
		if (nomeCliente != null && nomeCliente.length() > 0) {
			sql = sql + " and l.vendas.cliente.nome like '" + nomeCliente + "%'";
		}

		if (dataProxInicio != null && dataProxFinal != null) {
			sql = sql + " and l.proximocontato>='" + Formatacao.ConvercaoDataSql(dataProxInicio) + "' and "
					+ "l.proximocontato<='" + Formatacao.ConvercaoDataSql(dataProxFinal) + "'";
		}

		if (dataUltInicio != null && dataUltFinal != null) {
			sql = sql + " and l.dataultimocontato>='" + Formatacao.ConvercaoDataSql(dataUltInicio) + "' and "
					+ "l.dataultimocontato<='" + Formatacao.ConvercaoDataSql(dataUltFinal) + "'";
		}
		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and l.vendas.produtos.idprodutos=" + programas.getIdprodutos();
		}
		if (status != null && status.length()>0 && !status.equalsIgnoreCase("0")) {
			if(status.equalsIgnoreCase("Novos")){
				sql = sql + " and l.proximocontato is null and l.datafinalizada is null";
			}else if(status.equalsIgnoreCase("Atrasados")){ 
				sql = sql + " and l.proximocontato<'" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			}else if(status.equalsIgnoreCase("Hoje")){ 
				sql = sql + " and l.proximocontato='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			}else if(status.equalsIgnoreCase("Prox. 7 Dias")){ 
				Date data7;
				try {
					data7 = Formatacao.SomarDiasDatas(new Date(), 7);
				} catch (Exception e) {
					data7 = null;
				}
				sql = sql + " and l.proximocontato>'" + Formatacao.ConvercaoDataSql(new Date())
						+ "' and l.proximocontato<'" + Formatacao.ConvercaoDataSql(data7) + "'";
			}  
		}
		gerarListaCrmCobranca();
	}
	
	
	public void gerarListaCrmCobranca(){
		CrmCobrancaFacade crmCobrancaFacade = new CrmCobrancaFacade();
		listaCrmCobranca = crmCobrancaFacade.lista(sql);
		if (listaCrmCobranca == null) {
			listaCrmCobranca = new ArrayList<>();
		}
	}
	
	
	public void pegarPrioridade(String prioridade){
		this.prioridade = prioridade;
	}
	
	
	public void limparPesquisa() {
		nomeCliente = null;
		dataProxFinal = null;
		dataProxInicio = null;
		dataUltFinal = null;
		dataUltInicio = null;
		usuario = null;
		unidadenegocio = null;
		programas = null;
		prioridade = "1";
	}
	
	
	public void pesquisar() {
		imagemNovos = "novos";
		imagemHoje = "hoje";
		imagemAtrasados = "atrasados";
		imagemProx = "prox";
		imagemTodos = "todosClick";
		novos = 0;
		atrasados = 0;
		prox7 = 0;
		todos = 0;
		hoje = 0;
		sql = "select l from Crmcobranca l where (l.nota like '%%' or l.nota is null) ";
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and l.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " and l.vendas.usuario.idusuario=" + usuario.getIdusuario();
		} 
		if (prioridade != null && prioridade.length() > 0 && !prioridade.equals("0")) {
			funcao = "todos";
			sql = sql + " and l.prioridade='" + prioridade + "'";
		}
		if (nomeCliente != null && nomeCliente.length() > 0) {
			sql = sql + " and (l.vendas.cliente.nome like '" + nomeCliente + "%' or l.vendas.cliente.email like '" + nomeCliente + "%')";
		}
		if (dataProxInicio != null && dataProxFinal != null) {
			sql = sql + " and l.proximocontato>='" + Formatacao.ConvercaoDataSql(dataProxInicio) + "' and "
					+ "l.proximocontato<='" + Formatacao.ConvercaoDataSql(dataProxFinal) + "'";
		}

		if (dataUltInicio != null && dataUltFinal != null) {
			sql = sql + " and l.dataultimocontato>='" + Formatacao.ConvercaoDataSql(dataUltInicio) + "' and "
					+ "l.dataultimocontato<='" + Formatacao.ConvercaoDataSql(dataUltFinal) + "'";
		}
		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and l.vendas.produtos.idprodutos=" + programas.getIdprodutos();
		}
		if (status != null && status.length()>0 && !status.equalsIgnoreCase("0")) {
			if(status.equalsIgnoreCase("Novos")){
				sql = sql + " and l.proximocontato is null and l.datafinalizada is null";
			}else if(status.equalsIgnoreCase("Atrasados")){ 
				sql = sql + " and l.proximocontato<'" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			}else if(status.equalsIgnoreCase("Hoje")){ 
				sql = sql + " and l.proximocontato='" + Formatacao.ConvercaoDataSql(new Date()) + "'";
			}else if(status.equalsIgnoreCase("Prox. 7 Dias")){ 
				Date data7;
				try {
					data7 = Formatacao.SomarDiasDatas(new Date(), 7);
				} catch (Exception e) {
					data7 = null;
				}
				sql = sql + " and l.proximocontato>'" + Formatacao.ConvercaoDataSql(new Date())
						+ "' and l.proximocontato<'" + Formatacao.ConvercaoDataSql(data7) + "'";
			}  
		}
		gerarListaCrmCobranca();
		for (int i = 0; i < listaCrmCobranca.size(); i++) {
			if (!listaCrmCobranca.get(i).getSituacao().equals("0")) {
				todos = todos + 1;
			}
			if (!listaCrmCobranca.get(i).getSituacao().equals("0")
					&& listaCrmCobranca.get(i).getProximocontato() == null) {
				novos = novos + 1;
			} else if ((listaCrmCobranca.get(i).getProximocontato()) != null
					&& (Formatacao.ConvercaoDataSql(listaCrmCobranca.get(i).getProximocontato())
							.equalsIgnoreCase(Formatacao.ConvercaoDataSql(new Date())))
					&& (!listaCrmCobranca.get(i).getSituacao().equals("0"))) {
				hoje = hoje + 1;
			} else if (listaCrmCobranca.get(i).getProximocontato() != null
					&& listaCrmCobranca.get(i).getProximocontato().before(new Date())
					&& !listaCrmCobranca.get(i).getSituacao().equals("0")) {
				atrasados = atrasados + 1;
			} else if (listaCrmCobranca.get(i).getProximocontato() != null
					&& listaCrmCobranca.get(i).getProximocontato().after(new Date())
					&& !listaCrmCobranca.get(i).getSituacao().equals("0")) {
				Date data7 = null;
				try {
					data7 = Formatacao.SomarDiasDatas(new Date(), 7);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (listaCrmCobranca.get(i).getProximocontato().before(data7)) {
					prox7 = prox7 + 1;
				}
			}
		}
	}

}
