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
import br.com.travelmate.facade.PromocaoBrindeCursoCidadeFacade;
import br.com.travelmate.facade.PromocaoBrindeCursoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidadeidiomaproduto;
import br.com.travelmate.model.Promocaobrindecurso;
import br.com.travelmate.model.Promocaobrindecursocidade;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadPromocoesBrindesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cidade> listaCidade;
	private List<Cidade> listaCidadeSelecionada;
	private Promocaobrindecurso promocaobrindecurso;
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
		if (promocaobrindecurso == null) {
			promocaobrindecurso = new Promocaobrindecurso();
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

	public Promocaobrindecurso getPromocaobrindecurso() {
		return promocaobrindecurso;
	}

	public void setPromocaobrindecurso(Promocaobrindecurso promocaobrindecurso) {
		this.promocaobrindecurso = promocaobrindecurso;
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
				descricao = descricao(promocaobrindecurso);
				salvarPromocaoCidade();
				salvarAvisos();
				Mensagem.lancarMensagemErro("Salvo com sucesso.", "");
				return "consPromocoesBrindes";
			} else {
				Mensagem.lancarMensagemErro("Atenção", "Selecione os produtos desejados.");
			}
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Selecione as cidades desejadas.");
		}
		return "";
	}

	public void salvarPromocao() {
		PromocaoBrindeCursoFacade promocaoBrindeCursoFacade = new PromocaoBrindeCursoFacade();
		promocaobrindecurso = promocaoBrindeCursoFacade.salvar(promocaobrindecurso);
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
			PromocaoBrindeCursoCidadeFacade promocaoBrindeCursoCidadeFacade = new PromocaoBrindeCursoCidadeFacade();
			if(listaFornecedorCidadeIdiomaProduto!=null){
				for (int j = 0; j < listaFornecedorCidadeIdiomaProduto.size(); j++) { 
					Promocaobrindecursocidade promocaobrindecursocidade = new Promocaobrindecursocidade();
					promocaobrindecursocidade.setFornecedorcidadeidiomaproduto(listaFornecedorCidadeIdiomaProduto.get(j));
					promocaobrindecursocidade.setPromocaobrindecurso(promocaobrindecurso);
					promocaobrindecursocidade = promocaoBrindeCursoCidadeFacade.salvar(promocaobrindecursocidade);
					descricao = descricao
							+ promocaobrindecursocidade.getFornecedorcidadeidiomaproduto().getFornecedorcidadeidioma()
									.getFornecedorcidade().getCidade().getNome()
							+ " - " + promocaobrindecursocidade.getFornecedorcidadeidiomaproduto().getProdutosorcamento().getDescricao()
							+ ", ";
				}
			}else{
				Mensagem.lancarMensagemErro("", "Erro ao salvar.");
			}
		}
	}

	public String cancelar() {
		return "consPromocoesBrindes";
	}
	
	public String descricao(Promocaobrindecurso promocaobrindecurso) {
		String descricao = fornecedor.getNome().toUpperCase()+ " -  ";
		if (promocaobrindecurso.getDatavalidadeinicial() != null && promocaobrindecurso.getDatavalidadeinicial() != null) {
			descricao = descricao + "Período de validade: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadeinicial()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatavalidadefinal()) + " | ";
		}
		if (promocaobrindecurso.getNumerosemanainicial() != null && promocaobrindecurso.getNumerosemanafinal() != null
				&& promocaobrindecurso.getNumerosemanainicial()>0 && promocaobrindecurso.getNumerosemanafinal()>0) {
			descricao = descricao + "Nº de semanas: "
					+ promocaobrindecurso.getNumerosemanainicial() + " até "
					+ promocaobrindecurso.getNumerosemanafinal() + " | ";
		}
		if (promocaobrindecurso.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaobrindecurso.getDatamatricula()) + " | ";
		}
		if ((promocaobrindecurso.getDataacomodacaoinicial() != null) && (promocaobrindecurso.getDataacomodacaofinal() != null)) {
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
		if (promocaobrindecurso.getTurno() != null && promocaobrindecurso.getTurno().length()>2) {
			descricao = descricao + "Turno: " + promocaobrindecurso.getTurno() + " | ";
		}
		if (promocaobrindecurso.getNumerosemanacurso() != null && promocaobrindecurso.getNumerosemanacurso() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanacurso() + " semana(s) curso | ";
		}
		if (promocaobrindecurso.getNumerosemanaacomodacao() != null && promocaobrindecurso.getNumerosemanaacomodacao() > 0) {
			descricao = descricao + "A cada " + promocaobrindecurso.getNumerosemanaacomodacao() + " semana(s) acomodação  | ";
		}
		if (promocaobrindecurso.getGanhasemana()!=null && promocaobrindecurso.getGanhasemana()>0) {
			descricao = descricao + "Ganha: " + promocaobrindecurso.getGanhasemana() +" semana(s) de curso " ;
		}
		if (promocaobrindecurso.getGanhadescontosemana()!=null && promocaobrindecurso.getGanhadescontosemana()>0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemana() +" semana(s) de curso " ;
		}
		if (promocaobrindecurso.getGanhadescontosemanaacomodacao()!=null && promocaobrindecurso.getGanhadescontosemanaacomodacao()>0) {
			descricao = descricao + "Valor Insento: " + promocaobrindecurso.getGanhadescontosemanaacomodacao() +" semana(s) de acomodação " ;
		}
		if (promocaobrindecurso.getGanhadescricao()!=null && promocaobrindecurso.getGanhadescricao().length()>2) {
			descricao = descricao + "Brinde: " + promocaobrindecurso.getGanhadescricao();
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
