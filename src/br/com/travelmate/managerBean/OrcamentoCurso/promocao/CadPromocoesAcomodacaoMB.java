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
import br.com.travelmate.facade.FornecedorCidadeIdiomaFacade; 
import br.com.travelmate.facade.PromocaoAcomodacaoCidadeFacade;
import br.com.travelmate.facade.PromocaoAcomodacaoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Avisos;
import br.com.travelmate.model.Avisousuario;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorcidadeidioma;  
import br.com.travelmate.model.Promocaoacomodacao;
import br.com.travelmate.model.Promocaoacomodacaocidade;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadPromocoesAcomodacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Cidade> listaCidade;
	private List<Cidade> listaCidadeSelecionada;
	private Promocaoacomodacao promocaoAcomodacao;
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
		promocaoAcomodacao = new Promocaoacomodacao();
		if (listaCidade != null) {
			listaCidadeSelecionada = listaCidade;
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

	

	public Promocaoacomodacao getPromocaoAcomodacao() {
		return promocaoAcomodacao;
	}

	public void setPromocaoAcomodacao(Promocaoacomodacao promocaoAcomodacao) {
		this.promocaoAcomodacao = promocaoAcomodacao;
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

	public String confirmar() {
		if (listaCidadeSelecionada != null && listaCidadeSelecionada.size() > 0) {
			salvarPromocao();
			descricao = descricao(promocaoAcomodacao);
			salvarPromocaoCidade();
			salvarAvisos();
			Mensagem.lancarMensagemErro("Salvo com sucesso.", "");
			return "consPromocoesAcomodacao";
		} else {
			Mensagem.lancarMensagemErro("Atenção", "Selecione as cidades desejadas.");
		}
		return "";
	}

	public void salvarPromocao() {
		PromocaoAcomodacaoFacade promocaoAcomodacaoFacade = new PromocaoAcomodacaoFacade();
		promocaoAcomodacao = promocaoAcomodacaoFacade.salvar(promocaoAcomodacao);
	}

	public void salvarPromocaoCidade() {
		FornecedorCidadeIdiomaFacade fornecedorCidadeIdiomaFacade = new FornecedorCidadeIdiomaFacade();
		String sql = "select f from Fornecedorcidadeidioma f where (f.fornecedorcidade.cidade.idcidade="
				+ listaCidadeSelecionada.get(0).getIdcidade();
		for (int i = 1; i < listaCidadeSelecionada.size(); i++) {
			sql = sql + " or f.fornecedorcidade.cidade.idcidade=" + listaCidadeSelecionada.get(i).getIdcidade();
		}
		sql = sql
				+ ") and f.fornecedorcidade.fornecedor.idfornecedor="
				+ fornecedor.getIdfornecedor();
		List<Fornecedorcidadeidioma> listaFornecedorCidadeIdioma = fornecedorCidadeIdiomaFacade
				.listar(sql);
		PromocaoAcomodacaoCidadeFacade promocaoAcomodacaoCidadeFacade = new PromocaoAcomodacaoCidadeFacade();
		if (listaFornecedorCidadeIdioma != null) {
			for (int j = 0; j < listaFornecedorCidadeIdioma.size(); j++) {
				Promocaoacomodacaocidade promocaoacomodacaocidade = new Promocaoacomodacaocidade();
				promocaoacomodacaocidade.setFornecedorcidadeidioma(listaFornecedorCidadeIdioma.get(j));
				promocaoacomodacaocidade.setPromocaoacomodacao(promocaoAcomodacao);
				promocaoacomodacaocidade = promocaoAcomodacaoCidadeFacade.salvar(promocaoacomodacaocidade);
				descricao = descricao
						+ promocaoacomodacaocidade.getFornecedorcidadeidioma()
							.getFornecedorcidade().getCidade().getNome()+ ", ";  
			}  
		} else {
			Mensagem.lancarMensagemErro("", "Erro ao salvar.");
		}
	}

	public String cancelar() {
		return "consPromocoesAcomodacao";
	}
	
	public String descricao(Promocaoacomodacao promocaoacomodacao) {
		String descricao = fornecedor.getNome().toUpperCase() + " -  "; 
		if (promocaoacomodacao.getDatainicioacomodacao() != null && promocaoacomodacao.getDataterminioacodomodacao() != null) {
			descricao = descricao + "Período de acomodação: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioacomodacao()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDataterminioacodomodacao()) + " | ";
		}
		if (promocaoacomodacao.getDatainicioiniciocurso() != null && promocaoacomodacao.getDatainicioterminiocurso() != null) {
			descricao = descricao + "Período de início do curso: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioiniciocurso()) + " até "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatainicioterminiocurso()) + " | ";
		}
		if (promocaoacomodacao.getDatamatricula() != null) {
			descricao = descricao + "Data de matricula até: "
					+ Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatamatricula()) + " | ";
		}
		if (promocaoacomodacao.getDatavalidadeinicial() != null && promocaoacomodacao.getDatavalidadefinal() != null) {
			descricao = descricao + "Data validade: " + Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatavalidadeinicial())+" até "+Formatacao.ConvercaoDataPadrao(promocaoacomodacao.getDatavalidadefinal())
					+ " | ";
		}
		if (promocaoacomodacao.getNumerosemanafinal() != null && promocaoacomodacao.getNumerosemanainicio() != null && promocaoacomodacao.getNumerosemanainicio()>0) {
			descricao = descricao + "Nº de semanas: " + promocaoacomodacao.getNumerosemanainicio() + " até "
					+ promocaoacomodacao.getNumerosemanafinal() + " | ";
		}
		if (promocaoacomodacao.getTipoacomodacao() != null && promocaoacomodacao.getTipoacomodacao().length() > 2) {
			descricao = descricao + "Tipo de Acomodação: " + promocaoacomodacao.getTipoacomodacao() + " | ";
		}
		if (promocaoacomodacao.getTipoquarto() != null && promocaoacomodacao.getTipoquarto().length() > 2) {
			descricao = descricao + "Tipo de Quarto: " + promocaoacomodacao.getTipoquarto() + " | ";
		}
		if (promocaoacomodacao.getTiporefeicao() != null && promocaoacomodacao.getTiporefeicao().length() > 2) {
			descricao = descricao + "Tipo de Refeição: " + promocaoacomodacao.getTiporefeicao() + " | ";
		}
		if (promocaoacomodacao.getTipobanheiro() != null && promocaoacomodacao.getTipobanheiro().length() > 2) {
			descricao = descricao + "Tipo de Banheiro: " + promocaoacomodacao.getTipobanheiro() + " | ";
		}
		if (promocaoacomodacao.getValormaximodesconto() != null && promocaoacomodacao.getValormaximodesconto() > 0) {
			descricao = descricao + "Valor máximo de desconto: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValormaximodesconto()) + " | ";
		}
		if (promocaoacomodacao.getValorsemanaacima() != null && promocaoacomodacao.getValorsemanaacima() > 0) {
			descricao = descricao + "Valor por semana acima de: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemanaacima()) + " | ";
		}
		if (promocaoacomodacao.getValorsemanaigual() != null && promocaoacomodacao.getValorsemanaigual() > 0) {
			descricao = descricao + "Valor por semana igual a: "
					+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemanaigual()) + " | ";
		}
		if (promocaoacomodacao.getTipodesconto() != null && promocaoacomodacao.getValorsemana() != null
				&& promocaoacomodacao.getValorsemana() > 0) {
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + "% | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + " | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional por semana: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValorsemana()) + " | ";
			}
		}
		if (promocaoacomodacao.getTipodesconto() != null && promocaoacomodacao.getValortotal() != null
				&& promocaoacomodacao.getValortotal() > 0) {
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("p")) {
				descricao = descricao + "Percentual de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + "% | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("v")) {
				descricao = descricao + "Valor de desconto sobre a acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + " | ";
			}
			if (promocaoacomodacao.getTipodesconto().equalsIgnoreCase("t")) {
				descricao = descricao + "Valor promocional da acomodação: "
						+ Formatacao.formatarFloatString(promocaoacomodacao.getValortotal()) + " | ";
			}
		}
		descricao = descricao + "  CIDADES:";
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
