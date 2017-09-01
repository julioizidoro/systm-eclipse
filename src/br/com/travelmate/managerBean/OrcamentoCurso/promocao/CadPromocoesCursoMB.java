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
import br.com.travelmate.facade.FornecedorCidadeIdiomaProdutoFacade; 
import br.com.travelmate.facade.PromocaoCursoFacade;
import br.com.travelmate.facade.PromocaoCursoFornecedorCidadeFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto; 
import br.com.travelmate.model.Promocaocurso;
import br.com.travelmate.model.Promocaocursocidade;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadPromocoesCursoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cidade> listaCidade;
	private List<Cidade> listaCidadeSelecionada;
	private Promocaocurso promocaocurso;
	private List<Fornecedorcidadeidiomaproduto> listaProdutos;
	private List<Fornecedorcidadeidiomaproduto> listaProdutosSelecionado;
	private Fornecedor fornecedor;
	private String descricao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		listaCidade = (List<Cidade>) session.getAttribute("listaCidade");
		fornecedor = (Fornecedor) session.getAttribute("fornecedor");
		session.removeAttribute("fornecedor");
		session.removeAttribute("listaCidade");
		if (promocaocurso == null) {
			promocaocurso = new Promocaocurso();
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

	public Promocaocurso getPromocaocurso() {
		return promocaocurso;
	}

	public void setPromocaocurso(Promocaocurso promocaocurso) {
		this.promocaocurso = promocaocurso;
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

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
					+fornecedor.getIdfornecedor()+" group by f.produtosorcamento.idprodutosOrcamento order by f.produtosorcamento.descricao";
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
				descricao = descricao(promocaocurso);
				salvarPromocaoCidade();
				salvarAvisos();
				Mensagem.lancarMensagemErro("Salvo com sucesso.", "");
				return "consPromocoes";
			} else {
				Mensagem.lancarMensagemErro("Atenção", "Selecione os produtos desejados.");
			}
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Selecione as cidades desejadas.");
		}
		return "";
	}

	public void salvarPromocao() {
		PromocaoCursoFacade promocaoCursoFacade = new PromocaoCursoFacade();
		promocaocurso = promocaoCursoFacade.salvar(promocaocurso);
	}

	public void salvarPromocaoCidade() {
		FornecedorCidadeIdiomaProdutoFacade fornecedorCidadeIdiomaProdutoFacade = new FornecedorCidadeIdiomaProdutoFacade();
		Fornecedorcidadeidiomaproduto produtoOrcamento = new Fornecedorcidadeidiomaproduto();
		for (int p = 0; p < listaProdutosSelecionado.size(); p++) {
			String sqlProduto ="select f from Fornecedorcidadeidiomaproduto f where f.idfornecedorcidadeidiomaproduto="+listaProdutosSelecionado.get(p);
			produtoOrcamento = fornecedorCidadeIdiomaProdutoFacade.consultar(sqlProduto);
			String sql = "select f from Fornecedorcidadeidiomaproduto f where (f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
					+ listaCidadeSelecionada.get(0).getIdcidade();
			for (int i = 1; i < listaCidadeSelecionada.size(); i++) {
				sql = sql + " or f.fornecedorcidadeidioma.fornecedorcidade.cidade.idcidade="
						+ listaCidadeSelecionada.get(i).getIdcidade();
			}
			sql = sql + ") and f.produtosorcamento.tipoproduto='C' and f.produtosorcamento.idprodutosOrcamento="
					+ produtoOrcamento.getProdutosorcamento().getIdprodutosOrcamento()
					+ " and f.fornecedorcidadeidioma.fornecedorcidade.fornecedor.idfornecedor="+fornecedor.getIdfornecedor();
			List<Fornecedorcidadeidiomaproduto> listaFornecedorCidadeIdiomaProduto = fornecedorCidadeIdiomaProdutoFacade
					.listar(sql);
			PromocaoCursoFornecedorCidadeFacade promocaoCursoCidadeFacade = new PromocaoCursoFornecedorCidadeFacade();
			if(listaFornecedorCidadeIdiomaProduto!=null){
				for (int j = 0; j < listaFornecedorCidadeIdiomaProduto.size(); j++) { 
					Promocaocursocidade promocaocursocidade = new Promocaocursocidade();
					promocaocursocidade.setFornecedorcidadeidiomaproduto(listaFornecedorCidadeIdiomaProduto.get(j));
					promocaocursocidade.setPromoacaocurso(promocaocurso);
					promocaocursocidade = promocaoCursoCidadeFacade.salvar(promocaocursocidade);
					descricao = descricao
							+ promocaocursocidade.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
									.getFornecedorcidade().getCidade().getNome()
							+ " - " + promocaocursocidade.getFornecedorcidadeidiomaproduto().getProdutosorcamento().getDescricao()
							+ ", ";
				}
			}else{
				Mensagem.lancarMensagemErro("", "Erro ao salvar.");
			}
		}
	}

	public String cancelar() {
		return "consPromocoes";
	}
	

	public String descricao(Promocaocurso promocaocurso) {
		String descricao = fornecedor.getNome().toUpperCase()+" -   ";
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
		if ((promocaocurso.getDatavalidadefinal() != null) && (promocaocurso.getDatavalidadeinicial()!=null)) {
			descricao = descricao + "Data validade: " + Formatacao.ConvercaoDataPadrao(promocaocurso.getDatavalidadeinicial()) +
					" a " +  Formatacao.ConvercaoDataPadrao(promocaocurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaocurso.getNumerosemanafinal() != null && promocaocurso.getNumerosemanainicio() != null && promocaocurso.getNumerosemanainicio()>0) {
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
				descricao = descricao + "Percentual de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + "% | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaocurso.getValorsemana()) + " | ";
			}
			if (promocaocurso.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional por semana: "
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
		descricao = descricao + "CIDADES - CURSO: ";
		return descricao;
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
