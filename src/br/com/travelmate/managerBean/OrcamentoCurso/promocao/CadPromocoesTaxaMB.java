package br.com.travelmate.managerBean.OrcamentoCurso.promocao;

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
 

import br.com.travelmate.facade.AvisosFacade;
import br.com.travelmate.facade.FiltroOrcamentoProdutoFacade;
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade;
import br.com.travelmate.facade.PromocaoTaxaCidadeFacade;
import br.com.travelmate.facade.PromocaoTaxaCursoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Promocaotaxacidade;
import br.com.travelmate.model.Promocaotaxacurso;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadPromocoesTaxaMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cidade> listaCidade;
	private List<Cidade> listaCidadeSelecionada;
	private Promocaotaxacurso promocaotaxacurso;
	private List<Fornecedorcidadeidiomaproduto> listaProdutos;
	private List<Fornecedorcidadeidiomaproduto> listaProdutosSelecionado;
	private Fornecedor fornecedor;
	private List<Filtroorcamentoproduto> listaFiltroorcamentoproduto;
	private Produtosorcamento produtoOrcamento;
	private String descricao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaCidade = (List<Cidade>) session.getAttribute("listaCidade");
		fornecedor = (Fornecedor) session.getAttribute("fornecedor");
		session.removeAttribute("fornecedor");
		session.removeAttribute("listaCidade");
		gerarListaProdutosOrcamento();
		if (promocaotaxacurso == null) {
			promocaotaxacurso = new Promocaotaxacurso();
			if (listaCidade != null) {
				listaCidadeSelecionada = listaCidade;
			}
			gerarListaProdutos();
		}

	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Cidade> getListaCidadeSelecionada() {
		return listaCidadeSelecionada;
	}

	public void setListaCidadeSelecionada(List<Cidade> listaCidadeSelecionada) {
		this.listaCidadeSelecionada = listaCidadeSelecionada;
	}

	public Promocaotaxacurso getPromocaotaxacurso() {
		return promocaotaxacurso;
	}

	public void setPromocaotaxacurso(Promocaotaxacurso promocaotaxacurso) {
		this.promocaotaxacurso = promocaotaxacurso;
	}

	public List<Fornecedorcidadeidiomaproduto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Fornecedorcidadeidiomaproduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Fornecedorcidadeidiomaproduto> getListaProdutosSelecionado() {
		return listaProdutosSelecionado;
	}

	public void setListaProdutosSelecionado(List<Fornecedorcidadeidiomaproduto> listaProdutosSelecionado) {
		this.listaProdutosSelecionado = listaProdutosSelecionado;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Filtroorcamentoproduto> getListaFiltroorcamentoproduto() {
		return listaFiltroorcamentoproduto;
	}

	public void setListaFiltroorcamentoproduto(List<Filtroorcamentoproduto> listaFiltroorcamentoproduto) {
		this.listaFiltroorcamentoproduto = listaFiltroorcamentoproduto;
	}

	public Produtosorcamento getProdutoOrcamento() {
		return produtoOrcamento;
	}

	public void setProdutoOrcamento(Produtosorcamento produtoOrcamento) {
		this.produtoOrcamento = produtoOrcamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public void gerarListaProdutos() {
		String sql = "";
		if (listaCidadeSelecionada != null && listaCidadeSelecionada.size() > 0) {
			sql = "select f from Fornecedorcidadeidiomaproduto f where (f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
					+ listaCidadeSelecionada.get(0).getIdcidade();
			for (int i = 1; i < listaCidadeSelecionada.size(); i++) {
				sql = sql + " or f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
						+ listaCidadeSelecionada.get(i).getIdcidade();
			}
			sql = sql
					+ ") and f.produtosorcamento.tipoproduto='C' and f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor()
					+ " group by f.produtosorcamento.idprodutosOrcamento order by f.produtosorcamento.descricao";
		}
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeFacade = new FornecedorCidadeIdiomaProdutoFacade();
		listaProdutos = fornecedorCidadeFacade.listar(sql);
		if (listaProdutos == null || listaProdutos.size() == 0) {
			Mensagem.lancarMensagemInfo("Atenção", "Este fornecedor não possui produtos cadastrados.");
		} else {
			listaProdutosSelecionado = listaProdutos;
		}
	}

	public String confirmar() {
		if (listaCidadeSelecionada != null && listaCidadeSelecionada.size() > 0) {
			if (listaProdutosSelecionado != null && listaProdutosSelecionado.size() > 0) {
				salvarPromocao();
				descricao = descricao(promocaotaxacurso);
				salvarPromocaoCidade();
				salvarAvisos();
				Mensagem.lancarMensagemErro("Salvo com sucesso.", "");
				return "consPromocoesTaxas";
			} else {
				Mensagem.lancarMensagemErro("Atenção", "Selecione os produtos desejados.");
			}
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Selecione as cidades desejadas.");
		}
		return "";
	}

	public void salvarPromocao() {
		PromocaoTaxaCursoFacade promocaoTaxaCursoFacade = new PromocaoTaxaCursoFacade();
		promocaotaxacurso.setProdutosorcamento(produtoOrcamento);
		promocaotaxacurso = promocaoTaxaCursoFacade.salvar(promocaotaxacurso);
	}

	public String descricao(Promocaotaxacurso promocaotaxacurso) {
		String descricao = fornecedor.getNome().toUpperCase()+" -   ";
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
		descricao = descricao + "CIDADES - CURSO: ";
		return descricao;
	}

	public void salvarPromocaoCidade() {
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
		Fornecedorcidadeidiomaproduto produtoOrcamento = new Fornecedorcidadeidiomaproduto();
		for (int p = 0; p < listaProdutosSelecionado.size(); p++) {
			String sqlProduto = "select f from Fornecedorcidadeidiomaproduto f where f.idfornecedorcidadeidiomaproduto="
					+ listaProdutosSelecionado.get(p);
			produtoOrcamento = fornecedorCidadeIdiomaProdutoFacade.consultar(sqlProduto);
			String sql = "select f from Fornecedorcidadeidiomaproduto f where (f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
					+ listaCidadeSelecionada.get(0).getIdcidade();
			for (int i = 1; i < listaCidadeSelecionada.size(); i++) {
				sql = sql + " or f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
						+ listaCidadeSelecionada.get(i).getIdcidade();
			}
			sql = sql + ") and f.produtosorcamento.tipoproduto='C' and f.produtosorcamento.idprodutosOrcamento="
					+ produtoOrcamento.getProdutosorcamento().getIdprodutosOrcamento()
					+ " and f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="
					+ fornecedor.getIdfornecedor();
			List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto = fornecedorCidadeIdiomaProdutoFacade
					.listar(sql);
			PromocaoTaxaCidadeFacade promocaotaxacursoCidadeFacade = new PromocaoTaxaCidadeFacade();
			if (listaFornecedorCidadeIdiomaProduto != null) {
				for (int j = 0; j < listaFornecedorCidadeIdiomaProduto.size(); j++) {
					Promocaotaxacidade promocaotaxacidade = new Promocaotaxacidade();
					promocaotaxacidade.setFornecedorcidadeidiomaproduto(listaFornecedorCidadeIdiomaProduto.get(j));
					promocaotaxacidade.setPromocaotaxacurso(promocaotaxacurso);
					promocaotaxacidade = promocaotaxacursoCidadeFacade.salvar(promocaotaxacidade);
					descricao = descricao
							+ promocaotaxacidade.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
									.getFornecedorcidade().getCidade().getNome()
							+ " - " + promocaotaxacidade.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
									.getFornecedorcidade().getFornecedor().getNome()
							+ ", ";
				}
			} else {
				Mensagem.lancarMensagemErro("", "Erro ao salvar.");
			}
		}
	}

	public String cancelar() {
		return "consPromocoesTaxas";
	}

	public void gerarListaProdutosOrcamento() {
		FiltroOrcamentoProdutoFacade filtroOrcamentoProdutoFacade = new FiltroOrcamentoProdutoFacade();
		String sql = "";
		sql = "select f from Filtroorcamentoproduto f where f.produtos.idprodutos="
				+ aplicacaoMB.getParametrosprodutos().getCursos()
				+ " and (f.produtosorcamento.tipo='O' or f.produtosorcamento.tipo='C' or f.produtosorcamento.tipo='A') order by f.produtosorcamento.descricao";
		listaFiltroorcamentoproduto = filtroOrcamentoProdutoFacade.pesquisar(sql);
		if (listaFiltroorcamentoproduto == null) {
			listaFiltroorcamentoproduto = new ArrayList<Filtroorcamentoproduto>();
		}
	}

	public void salvarAvisos() {
		AvisosFacade avisosFacade = new AvisosFacade();
		Avisos aviso = new Avisos();
		aviso.setUsuario(usuarioLogadoMB.getUsuario());
    	aviso.setData(new Date());
		aviso.setIdunidade(0);   
		aviso.setLiberar(true);
		aviso.setImagem("promocao");
		aviso.setTexto(descricao);
		aviso.setAvisousuarioList(salvarAvisoUsuario(aviso));
		aviso = avisosFacade.salvar(aviso);
	}
	
	public List<Avisousuario> salvarAvisoUsuario(Avisos aviso){
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		String sql = "select u from Usuario u where u.situacao='Ativo'";
		List<Usuario> listaUsuario = usuarioFacade.listar(sql);
		List<Avisousuario> lista = new ArrayList<Avisousuario>();
		if (listaUsuario!=null){
			for(int i=0;i<listaUsuario.size();i++){
				Avisousuario avisousuario = new Avisousuario();
				avisousuario.setAvisos(aviso);
				avisousuario.setUsuario(listaUsuario.get(i));
				avisousuario.setVisto(false);
				lista.add(avisousuario);
			}
		}
		return lista;
	}

}
