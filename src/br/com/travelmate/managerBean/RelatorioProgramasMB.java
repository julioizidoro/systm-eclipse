package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CancelamentoFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.model.Cancelamento;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Curso;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class RelatorioProgramasMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pais pais;
	private Cidade cidade;
	private Produtos programas;
	private Usuario consultor;
	private Unidadenegocio unidadenegocio;
	private List<Pais> listaPais;
	private List<Cidade> listaCidade;
	private List<Produtos> listaProgramas;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Date dataInicio;
	private Date dataTermino;
	private Date dataVendaIni;
	private Date dataVendaFinal;
	private Fornecedorcidade fornecedorCidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private List<Curso> listaCursos;
	
	
	@PostConstruct
	public void init() {
		gerarListaPais();
		listaUnidadeNegocio = GerarListas.listarUnidade();
		listaProgramas = GerarListas.listarProdutos("");
	}


	public Pais getPais() {
		return pais;
	}


	public void setPais(Pais pais) {
		this.pais = pais;
	}


	public Cidade getCidade() {
		return cidade;
	}


	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}


	public Produtos getProgramas() {
		return programas;
	}


	public void setProgramas(Produtos programas) {
		this.programas = programas;
	}


	public Usuario getConsultor() {
		return consultor;
	}


	public void setConsultor(Usuario consultor) {
		this.consultor = consultor;
	}


	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}


	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}


	public List<Pais> getListaPais() {
		return listaPais;
	}


	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}


	public List<Cidade> getListaCidade() {
		return listaCidade;
	}


	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}




	public List<Produtos> getListaProgramas() {
		return listaProgramas;
	}


	public void setListaProgramas(List<Produtos> listaProgramas) {
		this.listaProgramas = listaProgramas;
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


	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Date getDataTermino() {
		return dataTermino;
	}


	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}


	public Date getDataVendaIni() {
		return dataVendaIni;
	}


	public void setDataVendaIni(Date dataVendaIni) {
		this.dataVendaIni = dataVendaIni;
	}


	public Date getDataVendaFinal() {
		return dataVendaFinal;
	}


	public void setDataVendaFinal(Date dataVendaFinal) {
		this.dataVendaFinal = dataVendaFinal;
	}
	
	
	
	public Fornecedorcidade getFornecedorCidade() {
		return fornecedorCidade;
	}


	public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
		this.fornecedorCidade = fornecedorCidade;
	}


	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}


	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}


	public List<Curso> getListaCursos() {
		return listaCursos;
	}


	public void setListaCursos(List<Curso> listaCursos) {
		this.listaCursos = listaCursos;
	}


	public void listarFornecedorCidade() {
		if (cidade != null) {
			String sql = "select f from Fornecedorcidade f where f.cidade.idcidade=" + cidade.getIdcidade() +
					" and f.ativo=1 order by f.fornecedor.nome";
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedorCidade == null) {
				listaFornecedorCidade = new ArrayList<Fornecedorcidade>();
			}
		}
	}
	
	
	
	public void pesquisar() {
		String sql = "SELECT c FROM Curso c WHERE c.vendas.cliente.nome like '%%' ";
		if (pais != null && pais.getIdpais() != null) {
			sql = sql + " and c.vendas.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais(); 
		}
		
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = sql + " and c.vendas.fornecedorcidade.cidade.idcidade=" + cidade.getIdcidade();
		}
		
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " and c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}
		
		if (consultor != null && consultor.getIdusuario() != null) {
			sql = sql + " and c.vendas.usuario.idusuario=" + consultor.getIdusuario();
		}
		
		if (programas != null && programas.getIdprodutos() != null) {
			sql = sql + " and c.vendas.produtos.idprodutos=" + programas.getIdprodutos();
		}
		
		if (fornecedorCidade != null && fornecedorCidade.getIdfornecedorcidade() != null) {
			sql = sql + " and c.vendas.fornecedorcidade.idfornecedorcidade=" + fornecedorCidade.getIdfornecedorcidade();
		}
		
		if (dataInicio != null && dataTermino != null) {
			sql = sql + " and c.dataInicio>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and c.dataTermino<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
		}
		
		if (dataVendaIni != null && dataVendaFinal != null) {
			sql = sql + " and c.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataVendaIni) + "' and c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataVendaFinal) + "'";
		}
		CursoFacade cursoFacade = new CursoFacade();
		listaCursos = cursoFacade.lista(sql);
		if (listaCursos == null) {
			listaCursos = new ArrayList<Curso>();
		}
	}
	
	
	public void limparPesquisa() {
		unidadenegocio = null;
		dataInicio = null;
		dataTermino = null;
		dataVendaFinal = null;
		dataVendaIni = null;
		fornecedorCidade = null;
		pais = null;
		cidade = null;
		programas = null;
		consultor = null;
		listaCursos = new ArrayList<Curso>();
	}
	

	
	public void gerarListaPais() {
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listarModelo("SELECT p FROM Pais p");
		if (listaPais == null) {
			listaPais = new ArrayList<Pais>();
		}
	}
	
	
	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaConsultor = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		}else {
			listaConsultor = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo' order by u.nome");
		}
	}
	
	
	public void dadosCancelamento(Curso cursos) {
		if (cursos.getVendas().getSituacao().equalsIgnoreCase("CANCELADA")) {
			CancelamentoFacade cancelamentoFacade = new CancelamentoFacade();
			Cancelamento cancelamento = cancelamentoFacade.consultar(cursos.getVendas().getIdvendas());
			if (cancelamento != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
				session.setAttribute("cancelamento", cancelamento);
				Map<String, Object> options = new HashMap<String, Object>();
				options.put("contentWidth", 400);
				RequestContext.getCurrentInstance().openDialog("dadosCancelamento", options, null);
			}else {
				Mensagem.lancarMensagemInfo("Venda sem informações do cancelamento", "");
			}
		}
	}
	
	
	
	

}
