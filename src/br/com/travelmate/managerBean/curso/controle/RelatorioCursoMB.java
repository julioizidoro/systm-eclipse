package br.com.travelmate.managerBean.curso.controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.dao.PaisDao;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;


@Named
@ViewScoped
public class RelatorioCursoMB implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private PaisDao paisDao;
	private String acomodacao;
	private Unidadenegocio unidadenegocio;
	private Usuario usuario;
	private List<Usuario> listaConsultor;
	private List<Unidadenegocio> listaUnidadeNegocio;
	private Date datainivenda;
	private Date datafinalvenda;
	private Date datainiembarque;
	private Date datafinalembarque;
	private Date datainicurso;
	private Date datafinalcurso;
	private Date datainiprevisao;
	private Date datafinalprevisao;
	private Date dataininascimento;
	private Date datafinalnascimento;
	private Date datainiprograma;
	private Date datafinalprograma;
	private Date dataIniTerminoCurso;
	private Date dataFinalTerminoCurso;
	private String sql;
	private Pais pais;
	private List<Pais> listaPais;
	private Cidade cidade;
	private Fornecedorcidade fornecedorcidade;
	private List<Fornecedorcidade> listaFornecedorCidade;
	private String ordenar;
	private List<Controlecurso> listaControlecurso;
	private String visto;
	private String tipoAcomodacao;
	private List<Produtosorcamento> listaProdutosOrcamentos;
	private Produtosorcamento produtosorcamento;
	private String kitviagem;
	private boolean selecionartodos = true;
	private boolean dataenvio = true;
	private boolean unidade = true;
	private boolean consultor = true;
	private boolean idade = true;
	private boolean datavenda = true;
	private boolean paisVisu = true;
	private boolean cidadeVisu = true;
	private boolean parceiro = true;
	private boolean inicioprograma = true;
	private boolean docvistos = true;
	private boolean dataaplicacao = true;
	private boolean previsaopagamento = true;
	private boolean situacaogerencia = true;
	private boolean credito = true;
	private boolean nomecliente = true;
	private boolean dataembarque = true;
	private boolean kitviagemVisu = true;
	private boolean recebimentoproof = true;
	private boolean email = true;
	private boolean telefone = true;
	private boolean nsemanas = true;
	private boolean curso = true;
	private boolean datainscricao = true;
	private boolean terminocurso = true;
	private String docsvistoPesquisa;
	private boolean creditoPesquisa = false;
	private String duracaoSemana;
	private String situacaoPesquisa;
	

	@PostConstruct
	public void init() {
		listaPais = paisDao.listar();
		gerarListaFornecedor();
		listaUnidadeNegocio = GerarListas.listarUnidade();
		gerarListaCursos();
	}

	public String getAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(String acomodacao) {
		this.acomodacao = acomodacao;
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

	public Date getDatafinalvenda() {
		return datafinalvenda;
	}

	public void setDatafinalvenda(Date datafinalvenda) {
		this.datafinalvenda = datafinalvenda;
	}

	public Date getDatainiembarque() {
		return datainiembarque;
	}

	public void setDatainiembarque(Date datainiembarque) {
		this.datainiembarque = datainiembarque;
	}

	public Date getDatafinalembarque() {
		return datafinalembarque;
	}

	public void setDatafinalembarque(Date datafinalembarque) {
		this.datafinalembarque = datafinalembarque;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public Date getDatainiprevisao() {
		return datainiprevisao;
	}

	public void setDatainiprevisao(Date datainiprevisao) {
		this.datainiprevisao = datainiprevisao;
	}

	public Date getDatafinalprevisao() {
		return datafinalprevisao;
	}

	public void setDatafinalprevisao(Date datafinalprevisao) {
		this.datafinalprevisao = datafinalprevisao;
	}

	public Date getDatainicurso() {
		return datainicurso;
	}

	public void setDatainicurso(Date datainicurso) {
		this.datainicurso = datainicurso;
	}

	public Date getDatafinalcurso() {
		return datafinalcurso;
	}

	public void setDatafinalcurso(Date datafinalcurso) {
		this.datafinalcurso = datafinalcurso;
	}

	public Date getDataininascimento() {
		return dataininascimento;
	}

	public void setDataininascimento(Date dataininascimento) {
		this.dataininascimento = dataininascimento;
	}

	public Date getDatafinalnascimento() {
		return datafinalnascimento;
	}

	public void setDatafinalnascimento(Date datafinalnascimento) {
		this.datafinalnascimento = datafinalnascimento;
	}

	public String getVisto() {
		return visto;
	}

	public void setVisto(String visto) {
		this.visto = visto;
	}

	public String getTipoAcomodacao() {
		return tipoAcomodacao;
	}

	public void setTipoAcomodacao(String tipoAcomodacao) {
		this.tipoAcomodacao = tipoAcomodacao;
	}

	public boolean isKitviagemVisu() {
		return kitviagemVisu;
	}

	public void setKitviagemVisu(boolean kitviagemVisu) {
		this.kitviagemVisu = kitviagemVisu;
	}

	public Date getDatainiprograma() {
		return datainiprograma;
	}

	public void setDatainiprograma(Date datainiprograma) {
		this.datainiprograma = datainiprograma;
	}

	public Date getDatafinalprograma() {
		return datafinalprograma;
	}

	public void setDatafinalprograma(Date datafinalprograma) {
		this.datafinalprograma = datafinalprograma;
	}

	public String getOrdenar() {
		return ordenar;
	}

	public void setOrdenar(String ordenar) {
		this.ordenar = ordenar;
	}

	public boolean isSelecionartodos() {
		return selecionartodos;
	}

	public void setSelecionartodos(boolean selecionartodos) {
		this.selecionartodos = selecionartodos;
	}

	public boolean isUnidade() {
		return unidade;
	}

	public void setUnidade(boolean unidade) {
		this.unidade = unidade;
	}

	public boolean isConsultor() {
		return consultor;
	}

	public void setConsultor(boolean consultor) {
		this.consultor = consultor;
	}

	public boolean isDatavenda() {
		return datavenda;
	}

	public void setDatavenda(boolean datavenda) {
		this.datavenda = datavenda;
	}

	public boolean isPaisVisu() {
		return paisVisu;
	}

	public void setPaisVisu(boolean paisVisu) {
		this.paisVisu = paisVisu;
	}

	public boolean isCidadeVisu() {
		return cidadeVisu;
	}

	public void setCidadeVisu(boolean cidadeVisu) {
		this.cidadeVisu = cidadeVisu;
	}

	public boolean isParceiro() {
		return parceiro;
	}

	public void setParceiro(boolean parceiro) {
		this.parceiro = parceiro;
	}

	public boolean isInicioprograma() {
		return inicioprograma;
	}

	public void setInicioprograma(boolean inicioprograma) {
		this.inicioprograma = inicioprograma;
	}

	public boolean isDataaplicacao() {
		return dataaplicacao;
	}

	public void setDataaplicacao(boolean dataaplicacao) {
		this.dataaplicacao = dataaplicacao;
	}

	public boolean isPrevisaopagamento() {
		return previsaopagamento;
	}

	public void setPrevisaopagamento(boolean previsaopagamento) {
		this.previsaopagamento = previsaopagamento;
	}

	public boolean isSituacaogerencia() {
		return situacaogerencia;
	}

	public void setSituacaogerencia(boolean situacaogerencia) {
		this.situacaogerencia = situacaogerencia;
	}

	public boolean isCredito() {
		return credito;
	}

	public void setCredito(boolean credito) {
		this.credito = credito;
	}

	public boolean isNomecliente() {
		return nomecliente;
	}

	public void setNomecliente(boolean nomecliente) {
		this.nomecliente = nomecliente;
	}

	public boolean isDataembarque() {
		return dataembarque;
	}

	public void setDataembarque(boolean dataembarque) {
		this.dataembarque = dataembarque;
	}

	public List<Controlecurso> getListaControlecurso() {
		return listaControlecurso;
	}

	public void setListaControlecurso(List<Controlecurso> listaControlecurso) {
		this.listaControlecurso = listaControlecurso;
	}

	public boolean isIdade() {
		return idade;
	}

	public void setIdade(boolean idade) {
		this.idade = idade;
	}

	public boolean isDocvistos() {
		return docvistos;
	}

	public void setDocvistos(boolean docvistos) {
		this.docvistos = docvistos;
	}

	public boolean isRecebimentoproof() {
		return recebimentoproof;
	}

	public void setRecebimentoproof(boolean recebimentoproof) {
		this.recebimentoproof = recebimentoproof;
	}

	public boolean isDataenvio() {
		return dataenvio;
	}

	public void setDataenvio(boolean dataenvio) {
		this.dataenvio = dataenvio;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isTelefone() {
		return telefone;
	}

	public void setTelefone(boolean telefone) {
		this.telefone = telefone;
	}

	public boolean isNsemanas() {
		return nsemanas;
	}

	public void setNsemanas(boolean nsemanas) {
		this.nsemanas = nsemanas;
	}

	public boolean isCurso() {
		return curso;
	}

	public void setCurso(boolean curso) {
		this.curso = curso;
	}

	public boolean isDatainscricao() {
		return datainscricao;
	}

	public void setDatainscricao(boolean datainscricao) {
		this.datainscricao = datainscricao;
	}

	public boolean isTerminocurso() {
		return terminocurso;
	}

	public void setTerminocurso(boolean terminocurso) {
		this.terminocurso = terminocurso;
	}

	public List<Produtosorcamento> getListaProdutosOrcamentos() {
		return listaProdutosOrcamentos;
	}

	public void setListaProdutosOrcamentos(List<Produtosorcamento> listaProdutosOrcamentos) {
		this.listaProdutosOrcamentos = listaProdutosOrcamentos;
	}

	public Produtosorcamento getProdutosorcamento() {
		return produtosorcamento;
	}

	public void setProdutosorcamento(Produtosorcamento produtosorcamento) {
		this.produtosorcamento = produtosorcamento;
	}

	public Date getDataIniTerminoCurso() {
		return dataIniTerminoCurso;
	}

	public void setDataIniTerminoCurso(Date dataIniTerminoCurso) {
		this.dataIniTerminoCurso = dataIniTerminoCurso;
	}

	public Date getDataFinalTerminoCurso() {
		return dataFinalTerminoCurso;
	}

	public void setDataFinalTerminoCurso(Date dataFinalTerminoCurso) {
		this.dataFinalTerminoCurso = dataFinalTerminoCurso;
	}

	public boolean isCreditoPesquisa() {
		return creditoPesquisa;
	}

	public void setCreditoPesquisa(boolean creditoPesquisa) {
		this.creditoPesquisa = creditoPesquisa;
	}

	public String getKitviagem() {
		return kitviagem;
	}

	public void setKitviagem(String kitviagem) {
		this.kitviagem = kitviagem;
	}

	public String getDocsvistoPesquisa() {
		return docsvistoPesquisa;
	}

	public void setDocsvistoPesquisa(String docsvistoPesquisa) {
		this.docsvistoPesquisa = docsvistoPesquisa;
	}

	public String getDuracaoSemana() {
		return duracaoSemana;
	}

	public void setDuracaoSemana(String duracaoSemana) {
		this.duracaoSemana = duracaoSemana;
	}

	public String getSituacaoPesquisa() {
		return situacaoPesquisa;
	}

	public void setSituacaoPesquisa(String situacaoPesquisa) {
		this.situacaoPesquisa = situacaoPesquisa;
	}

	public void gerarListaFornecedor() {
		String sql = "select distinct f from Fornecedorcidade f where f.produtos.idprodutos=1 ";
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = sql + " and f.cidade.idcidade=" + cidade.getIdcidade();
		}
		sql = sql + " and f.ativo=1 group by f.fornecedor order by f.fornecedor.nome";
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
		listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
	}

	public void gerarListaConsultor() {
		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			listaConsultor = GerarListas.listarUsuarios(
					"Select u FROM Usuario u where u.situacao='Ativo'" + " and u.unidadenegocio.idunidadeNegocio="
							+ unidadenegocio.getIdunidadeNegocio() + " order by u.nome");
		} else {
			listaConsultor = GerarListas
					.listarUsuarios("Select u FROM Usuario u where u.situacao='Ativo' order by u.nome");
		}
		usuario = null;
	}

	public void pesquisar() {
		CursoFacade cursoFacade = new CursoFacade();
		String sql = "SELECT c FROM Controlecurso c WHERE c.vendas.cliente.nome like '%%' ";

		if (pais != null && pais.getIdpais() != null) {
			sql = sql + " AND c.vendas.fornecedorcidade.cidade.pais.idpais=" + pais.getIdpais();
		}

		if (cidade != null && cidade.getIdcidade() != null) {
			sql = sql + " AND c.vendas.fornecedorcidade.cidade.idcidade=" + cidade.getIdcidade();
		}

		if (fornecedorcidade != null && fornecedorcidade.getIdfornecedorcidade() != null) {
			sql = sql + " AND c.vendas.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedorcidade.getFornecedor().getIdfornecedor();
		}

		if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
			sql = sql + " AND c.vendas.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
		}

		if (datainiembarque != null && datafinalembarque != null) {
			sql = sql + " AND c.dataEmbarque>='" + Formatacao.ConvercaoDataSql(datainiembarque)
					+ "' AND c.dataEmbarque<='" + Formatacao.ConvercaoDataSql(datafinalembarque) + "'";
		}
		

		if (datainivenda != null && datafinalvenda != null) {
			sql = sql + " AND c.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(datainivenda)
					+ "' AND c.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(datafinalvenda) + "'";
		}

		if (datainiprevisao != null && datafinalprevisao != null) {
			sql = sql + " AND c.vendas.invoice.dataPrevistaPagamento>='"
					+ Formatacao.ConvercaoDataSql(datainiprevisao)
					+ "' AND c.vendas.invoice.dataPrevistaPagamento<='"
					+ Formatacao.ConvercaoDataSql(datafinalprevisao) + "'";
		}

		if (datainicurso != null && datafinalcurso != null) {
			sql = sql + " AND c.curso.dataInicio>='"
					+ Formatacao.ConvercaoDataSql(datainicurso)
					+ "' AND c.curso.dataInicio<='"
					+ Formatacao.ConvercaoDataSql(datafinalcurso) + "'";
		}

		if (dataIniTerminoCurso != null && dataFinalTerminoCurso != null) {
			sql = sql + " AND c.curso.dataTermino>='"
					+ Formatacao.ConvercaoDataSql(dataIniTerminoCurso)
					+ "' AND c.curso.dataTermino<='"
					+ Formatacao.ConvercaoDataSql(dataFinalTerminoCurso) + "'";
		}

		if (dataininascimento != null && datafinalnascimento != null) {
			sql = sql + " AND c.vendas.cliente.dataNascimento>='"
					+ Formatacao.ConvercaoDataSql(dataininascimento)
					+ "' AND c.vendas.cliente.dataNascimento<='"
					+ Formatacao.ConvercaoDataSql(datafinalnascimento) + "'";
		}
		
		if (docsvistoPesquisa != null && !docsvistoPesquisa.equalsIgnoreCase("Selecione")) {
			sql = sql + " AND c.visto='" + docsvistoPesquisa + "' ";
		}
		
		if (kitviagem != null && !kitviagem.equalsIgnoreCase("Selecione")) {
			sql = sql + " AND c.kitViagem='" +  kitviagem + "' ";
		}
		
		if (creditoPesquisa) {
			sql = sql + " AND c.vendas.invoice.valorcredito>0 ";
		}
		
		if (duracaoSemana != null && duracaoSemana.length() > 0) {
			sql = sql + " AND c.curso.numeroSenamas=" + duracaoSemana;
		}
		
		if (tipoAcomodacao != null && !tipoAcomodacao.equalsIgnoreCase("Selecione")) {
			sql = sql + " AND c.curso.tipoAcomodacao='" + tipoAcomodacao + "' ";
		}
		
		if (produtosorcamento != null) {
			sql = sql + " AND c.curso.nomeCurso='" + produtosorcamento.getDescricao() + "' ";
		}
		
		if (situacaoPesquisa != null && !situacaoPesquisa.equalsIgnoreCase("TODOS")) {
			sql = sql + " AND c.situacao='" + situacaoPesquisa + "' "; 
		}
		

		listaControlecurso = cursoFacade.listaControle(sql);

		if (listaControlecurso == null) {
			listaControlecurso = new ArrayList<Controlecurso>();
		}
	}

	public void limpar() {
		datainiembarque = null;
		datafinalembarque = null;
		datainiprevisao = null;
		datafinalprevisao = null;
		datainiprograma = null;
		datafinalprograma = null;
		datainivenda = null;
		datafinalvenda = null;
		unidadenegocio = null;
		usuario = null;
		pais = null;
		cidade = null;
		fornecedorcidade = null;
		acomodacao = "";
		ordenar = "";
		acomodacao = "";
		datafinalcurso = null;
		datainicurso = null;
		datafinalnascimento = null;
		dataininascimento = null;
		produtosorcamento = null;
		duracaoSemana = null;
		tipoAcomodacao = null;
		listaControlecurso = new ArrayList<Controlecurso>();

	}

	public void selecionartodos() {
		if (!selecionartodos) {
			selecionartodos = false;
			unidade = false;
			consultor = false;
			docvistos = false;
			datavenda = false;
			paisVisu = false;
			cidadeVisu = false;
			parceiro = false;
			inicioprograma = false;
			recebimentoproof = false;
			dataaplicacao = false;
			previsaopagamento = false;
			situacaogerencia = false;
			credito = false;
			nomecliente = false;
			dataembarque = false;
			kitviagemVisu = false;
			email = false;
			telefone = false;
			dataenvio = false;
			nsemanas = false;
			idade = false;
			curso = false;
			datainscricao = false;
			terminocurso = false;
		} else {
			selecionartodos = true;
			unidade = true;
			consultor = true;
			docvistos = true;
			datavenda = true;
			paisVisu = true;
			cidadeVisu = true;
			parceiro = true;
			inicioprograma = true;
			recebimentoproof = true;
			dataaplicacao = true;
			previsaopagamento = true;
			situacaogerencia = true;
			credito = true;
			nomecliente = true;
			dataembarque = true;
			kitviagemVisu = true;
			email = true;
			telefone = true;
			dataenvio = true;
			nsemanas = true;
			idade = true;
			curso = true;
			datainscricao = true;
			terminocurso = true;
		}
	}
	
	
	
	public void gerarListaCursos() {
			FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
			String sql = "SELECT f FROM Fornecedorcidadeidiomaproduto f where f.produtosorcamento.tipoproduto='C' " 
					+ " group by f.produtosorcamento order by f.produtosorcamento.descricao";
			List<Fornecedorcidadeidiomaproduto> lista = fornecedorCidadeIdiomaProdutoFacade.listar(sql);
			listaProdutosOrcamentos = new ArrayList<Produtosorcamento>();
			if (lista != null){
				for (int i = 0; i < lista.size(); i++) {
					listaProdutosOrcamentos.add(lista.get(i).getProdutosorcamento());
				}
			}
	}


}
