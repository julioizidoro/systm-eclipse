package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.sql.SQLException;
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

import br.com.travelmate.bean.TmRaceBean;
import br.com.travelmate.bean.UsuarioProdutoRunnersBean;
import br.com.travelmate.facade.CorridaProdutoMesFacade;
import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.UsuarioPontosFacade;
import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Usuariopontos;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class RelatorioDashBoardMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private int mesCorrente;
	private List<Usuariopontos> listaPontos;
	private int mesReferencia;
	private String nomeEscola;
	private Ftpdados ftpdados;
	private List<Produtos> listaProdutos;
	private List<Corridaprodutomes> listaCorrida;
	private List<TmRaceBean> listaGold;
	private List<TmRaceBean> listaSinze;
	private List<TmRaceBean> listaBronze;
	private int mes;
	private int ano;
	private List<UsuarioProdutoRunnersBean> listaCorridaUsuario;
	
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		mes = (int) session.getAttribute("mes");
		ano = (int) session.getAttribute("ano");
		session.removeAttribute("mes");
		session.removeAttribute("ano");
		pegarFtpDados();
		if (listaPontos == null) {
			mesReferencia = Formatacao.getMesData(new Date());
			carregarListaRunners();
		}
		gerarListaProdutos();
		gerarListaGold();
		gerarListaSinze();
		gerarListaBronze();
	}
	
	
	
	
	public int getMesCorrente() {
		return mesCorrente;
	}

	public void setMesCorrente(int mesCorrente) {
		this.mesCorrente = mesCorrente;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Usuariopontos> getListaPontos() {
		return listaPontos;
	}

	public void setListaPontos(List<Usuariopontos> listaPontos) {
		this.listaPontos = listaPontos;
	}

	public int getMesReferencia() {
		return mesReferencia;
	}

	public void setMesReferencia(int mesReferencia) {
		this.mesReferencia = mesReferencia;
	}

	public String getNomeEscola() {
		return nomeEscola;
	}

	public void setNomeEscola(String nomeEscola) {
		this.nomeEscola = nomeEscola;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Ftpdados getFtpdados() {
		return ftpdados;
	}

	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}

	public void carregarListaRunners() {
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT p FROM Usuariopontos p where p.usuario.nome like '%%'";
		if (mes > 0) {
			sql = sql + " and p.mes=" + mes;
		}
		if (ano > 0) {
			sql = sql + " and p.ano=" + ano;
		}
		sql = sql + " ORDER BY p.pontos DESC, p.usuario.nome";
		listaPontos = usuarioPontosFacade.listar(sql);
		if (listaPontos == null) {
			listaPontos = new ArrayList<Usuariopontos>();
		}
		if (listaPontos.size() < 10) {
			gerarLista(mes, ano);
		}
	}

	public void gerarLista(int mes, int ano) {
		UsuarioFacade usuarioFacade = new UsuarioFacade();
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT u FROM Usuario u where u.vende=TRUE and u.situacao='ATIVO' ORDER BY u.nome";
		List<Usuario> listaUsuario = usuarioFacade.listar(sql);
		if (listaUsuario != null) {
			for (int i = 0; i < listaUsuario.size(); i++) {
				Usuariopontos u = new Usuariopontos();
				u.setAno(ano);
				u.setMes(mes);
				u.setPontos(0);
				u.setUsuario(listaUsuario.get(i));
				u = usuarioPontosFacade.salvar(u);
				listaPontos.add(u);
			}
		}
	}

	public void atualizarMes() {
		int mesAtual = Formatacao.getMesData(new Date());
		if (mesReferencia < mesAtual) {
			carregarListaRunners();
		}
	}

	public String getNumeroVendas(int i) {
		if (listaPontos.size() > i) {
			return String.valueOf(listaPontos.get(i).getPontos());
		}
		return "0";
	}

	public Double getPercentual(int i) {
		if (listaPontos.size() > i) {
			if (listaPontos.get(i).getPontos() > 0) {
				double maior = listaPontos.get(0).getPontos();
				double menor = listaPontos.get(i).getPontos();
				Double perc = (menor / maior) * 100;
				return perc;
			}
		}
		return 0.0;
	}

	public String getFoto(int i) {
		String caminho = null;
		if (listaPontos.size() > i) {
			caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
			if (listaPontos.get(i).getUsuario().isFoto()) {
				caminho = caminho + "/usuario/" + listaPontos.get(i).getUsuario().getIdusuario() + ".jpg";
			} else
				caminho = caminho + "/usuario/0.png";
		}
		return caminho;
	}

	public String getNome(int i) {
		if (listaPontos.size() > i) {
			return listaPontos.get(i).getUsuario().getNome();
		}
		return "";
	}

	public String getNomeUnidade(int i) {
		if (listaPontos.size() > i) {
			return listaPontos.get(i).getUsuario().getUnidadenegocio().getNomerelatorio();
		}
		return "";
	}

	public String getFotoUsuarioPonto(int idusuario) {
		String caminho = null;
		caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (listaPontos != null) {
			for (int i = 0; i < listaPontos.size(); i++) {
				if (listaPontos.get(i).getUsuario().getIdusuario() == idusuario) {
					if (listaPontos.get(i).getUsuario().isFoto()) {
						return caminho = caminho + "/usuario/" + listaPontos.get(i).getUsuario().getIdusuario()
								+ ".jpg";
					} else
						return caminho = caminho + "/usuario/0.png";
				}
			}
			if (listaPontos.get(0).getUsuario().isFoto()) {
				return caminho = caminho + "/usuario/" + listaPontos.get(0).getUsuario().getIdusuario() + ".jpg";
			} else
				return caminho = caminho + "/usuario/0.png";
		}
		return "";
	}

	public String getTituloMateRunners(int idusuario) {
		if (listaPontos != null) {
			for (int i = 0; i < listaPontos.size(); i++) {
				if (listaPontos.get(i).getUsuario().getIdusuario() == idusuario) {
					return "SUA PONTUAÇÃO ATUAL";
				}
			}
			return "MAIOR PONTUADOR";
		}
		return null;
	}

	public String getNomeUnidadeTituloMateRunners(int idusuario) {
		if (listaPontos != null) {
			for (int i = 0; i < listaPontos.size(); i++) {
				if (listaPontos.get(i).getUsuario().getIdusuario() == idusuario) {
					return listaPontos.get(i).getUsuario().getNome() + " | "
							+ listaPontos.get(i).getUsuario().getUnidadenegocio().getNomerelatorio();
				}
			}
			return listaPontos.get(0).getUsuario().getNome() + " | "
					+ listaPontos.get(0).getUsuario().getUnidadenegocio().getNomerelatorio();
		}
		return null;
	}

	public int getPontuacaoTituloMateRunners(int idusuario) {
		if (listaPontos != null) {
			for (int i = 0; i < listaPontos.size(); i++) {
				if (listaPontos.get(i).getUsuario().getIdusuario() == idusuario) {
					return listaPontos.get(i).getPontos();
				}
			}
			return listaPontos.get(0).getPontos();
		}
		return 0;
	}

	public void carregarListaRunnersEscola() {
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT p FROM Usuariopontos p where p.usuario.nome like '%%'";
		if (mes > 0) {
			sql = sql + " and p.mes=" + mes;
		}
		if (ano > 0) {
			sql = sql + " and p.ano=" + ano;
		}
		sql = sql 
				+ " ORDER BY p.pontoescola DESC, p.usuario.nome";
		listaPontos = usuarioPontosFacade.listar(sql);
		if (listaPontos == null) {
			listaPontos = new ArrayList<Usuariopontos>();
		}
		if (listaPontos.size() < 10) {
			gerarLista(mes, ano);
		}
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		Fornecedor fornecedor = fornecedorFacade.consultar(aplicacaoMB.getParametrosprodutos().getEscolaracer());
		nomeEscola = fornecedor.getNome();
	}

	public String getNumeroVendasEscola(int i) {
		if (listaPontos.size() > i) {
			return String.valueOf(listaPontos.get(i).getPontoescola());
		}
		return "0";
	}

	public Double getPercentualEscola(int i) {
		if (listaPontos.size() > i) {
			if (listaPontos.get(i).getPontoescola() > 0) {
				double maior = listaPontos.get(0).getPontoescola();
				double menor = listaPontos.get(i).getPontoescola();
				Double perc = (menor / maior) * 100;
				return perc;
			}
		}
		return 0.0;
	}

	public String fecharDialog() {
		carregarListaRunners();
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public void pegarFtpDados() {
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String pegarEndereco() {
		String endereco = "http://";
		endereco = endereco + ftpdados.getHost();
		endereco = endereco + ":82/ftproot/systm/tmstar/TMS01.pdf";
		return endereco;
	}

	public String retornarCssBotao() {
		String css = "display: inline-block;position: relative;padding: 0;margin-right: .1em;text-decoration: none !important;"
				+ "cursor: pointer;text-align: center;zoom: 1;overflow: visible;background: #54A515;"
				+ "font-weight: bold;color: #ffffff;font-family: segoe ui, Arial, sans-serif;text-transform: none;margin: 0px;"
				+ "border-radius: 5px;";
		return css;
	}

	public String gerarGraficoUnidade(Usuario usuario) {
		String mes = (Formatacao.getMesData(new Date()) + 1) + "";
		String ano = Formatacao.getAnoData(new Date()) + "";
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("ano", ano);
		session.setAttribute("mes", mes);
		session.setAttribute("idunidade", usuario.getUnidadenegocio().getIdunidadeNegocio());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 780); 
		RequestContext.getCurrentInstance().openDialog("graficoMateRunnersUnidade", options,null);
		return "";
	}
	
	

	public List<Produtos> getListaProdutos() {
		return listaProdutos;
	}


	public void setListaProdutos(List<Produtos> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	
	
	public String getFoto(Produtos produtos) {
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<Corridaprodutomes> listaCorridaMes = null;
		String caminho = null;
		String sql = "SELECT c FROM Corridaprodutomes c WHERE c.produtos.idprodutos=" + produtos.getIdprodutos();
						

		if (mes > 0) {
			sql = sql + " and c.mes=" + mes;
		}
		if (ano > 0) {
			sql = sql + " and c.ano=" + ano;
		}
		sql = sql + " ORDER BY c.pontos DESC";
		listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		if (listaCorridaMes == null) {
			listaCorridaMes = new ArrayList<Corridaprodutomes>();
		}
		caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (listaCorridaMes.size() > 0) {
			if (listaCorridaMes.get(0).getUsuario().isFoto()) {
				caminho = caminho + "/usuario/" + listaCorridaMes.get(0).getUsuario().getIdusuario() + ".jpg";
			}else{
				caminho = caminho + "/usuario/0.png";
			}
		}else{
			caminho = caminho + "/usuario/0.png";
		}
		return caminho;
	}
	
	
	public Integer getPontuacao(Produtos produtos) {
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<Corridaprodutomes> listaCorridaMes = null;
		String sql = "SELECT c FROM Corridaprodutomes c WHERE  c.produtos.idprodutos=" + produtos.getIdprodutos();
						
		if (mes > 0) {
			sql = sql + " and c.mes=" + mes;
		}
		if (ano > 0) {
			sql = sql + " and c.ano=" + ano;
		}
		sql = sql + " ORDER BY c.pontos DESC";
		listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		if (listaCorridaMes == null) {
			listaCorridaMes = new ArrayList<Corridaprodutomes>();
		}
		if (listaCorridaMes.size() > 0) {
			return listaCorridaMes.get(0).getPontos();
		}
		return 0;
	}
	
	
	public String graficoTop3Mes(Produtos produtos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("produtos", produtos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 380); 
		RequestContext.getCurrentInstance().openDialog("graficoTop3Mes", options,null);
		return "";
	}
	
	
	public String graficoTopAno(Produtos produtos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("produtos", produtos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 580); 
		RequestContext.getCurrentInstance().openDialog("graficoTopAno", options,null);
		return "";
	}   
	
	
	
	
	
	public List<Corridaprodutomes> getListaCorrida() {
		return listaCorrida;
	}





	public void setListaCorrida(List<Corridaprodutomes> listaCorrida) {
		this.listaCorrida = listaCorrida;
	}





	public List<TmRaceBean> getListaGold() {
		return listaGold;
	}


	public void setListaGold(List<TmRaceBean> listaGold) {
		this.listaGold = listaGold;
	}


	public List<TmRaceBean> getListaSinze() {
		return listaSinze;
	}


	public void setListaSinze(List<TmRaceBean> listaSinze) {
		this.listaSinze = listaSinze;
	}


	public List<TmRaceBean> getListaBronze() {
		return listaBronze;
	}


	public void setListaBronze(List<TmRaceBean> listaBronze) {
		this.listaBronze = listaBronze;
	}
	
	     
	
	
	public int getMes() {
		return mes;
	}




	public void setMes(int mes) {
		this.mes = mes;
	}




	public int getAno() {
		return ano;
	}




	public void setAno(int ano) {
		this.ano = ano;
	}




	public List<UsuarioProdutoRunnersBean> getListaCorridaUsuario() {
		return listaCorridaUsuario;
	}




	public void setListaCorridaUsuario(List<UsuarioProdutoRunnersBean> listaCorridaUsuario) {
		this.listaCorridaUsuario = listaCorridaUsuario;
	}




	public void gerarListaGold(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<TmRaceBean> listaposicao = null;
		listaGold = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Gold%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutomes>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			String sql = "SELECT c FROM Corridaprodutomes c WHERE c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio();
			if (mes > 0) {
				sql = sql + " and c.mes=" + mes;
			}
			if (ano > 0) {
				sql = sql + " and c.ano=" + ano;
			}
			listaCorrida = corridaProdutoMesFacade.listar(sql);
			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaGold.size() < 3) {
				listaGold.add(tmRaceBean);
			}else if(listaCorrida.size() > 0 && listaGold.size() >= 3){
					if ((listaGold.get(0).getPontos() <= tmRaceBean.getPontos()) && (listaGold.get(0).getPontos() <= listaGold.get(1).getPontos()) &&
							(listaGold.get(0).getPontos() <= listaGold.get(2).getPontos())) {
						listaposicao.add(listaGold.get(0));
						listaGold.add(tmRaceBean);
					}else if ((listaGold.get(1).getPontos() <= tmRaceBean.getPontos()) && (listaGold.get(1).getPontos() <= listaGold.get(0).getPontos()) &&
							(listaGold.get(1).getPontos() <= listaGold.get(2).getPontos())) {
						listaposicao.add(listaGold.get(1));
						listaGold.add(tmRaceBean);
					} else if ((listaGold.get(2).getPontos() <= tmRaceBean.getPontos()) && (listaGold.get(2).getPontos() <= listaGold.get(1).getPontos()) &&
							(listaGold.get(2).getPontos() <= listaGold.get(0).getPontos())) {
						listaposicao.add(listaGold.get(2));
						listaGold.add(tmRaceBean);
					}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaGold.remove(listaposicao.get(j));
				}
			}
		}
		if (listaGold != null) {
			if (listaGold.size() == 1) {
				listaGold.get(0).setPosicao(1);
				listaGold.get(0).setPorcentagem(100f);
			}else if (listaGold.size() == 2){
				if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos()) {
					listaGold.get(0).setPosicao(1);
					listaGold.get(0).setPorcentagem(100f);
					listaGold.get(1).setPosicao(2);
					listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(0).getPontos());
				}else{
					listaGold.get(0).setPosicao(2);
					listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(1).getPontos());
					listaGold.get(1).setPosicao(1);
					listaGold.get(1).setPorcentagem(100f);
				}
			}else if(listaGold.size() == 3){
				if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos() && listaGold.get(0).getPontos() >= listaGold.get(2).getPontos()) {
					listaGold.get(0).setPosicao(1);
					listaGold.get(1).setPorcentagem(100f);
					if (listaGold.get(1).getPontos() >= listaGold.get(2).getPontos()) {
						listaGold.get(1).setPosicao(2);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(0).getPontos());
						listaGold.get(2).setPosicao(3);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(0).getPontos());
					}else{
						listaGold.get(1).setPosicao(3);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(0).getPontos());
						listaGold.get(2).setPosicao(2);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(0).getPontos());
					}
				}else if(listaGold.get(1).getPontos() >= listaGold.get(0).getPontos() && listaGold.get(1).getPontos() >= listaGold.get(2).getPontos()){
					listaGold.get(1).setPosicao(1);
					listaGold.get(1).setPorcentagem(100f);
					if (listaGold.get(0).getPontos() >= listaGold.get(2).getPontos()) {
						listaGold.get(0).setPosicao(2);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(1).getPontos());
						listaGold.get(2).setPosicao(3);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(1).getPontos());
					}else{
						listaGold.get(0).setPosicao(3);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(1).getPontos());
						listaGold.get(2).setPosicao(2);
						listaGold.get(2).setPorcentagem((listaGold.get(2).getPontos() * 100) / listaGold.get(1).getPontos());
					}
				}else if(listaGold.get(2).getPontos() >= listaGold.get(0).getPontos() && listaGold.get(2).getPontos() >= listaGold.get(1).getPontos()){
					listaGold.get(2).setPosicao(1);
					listaGold.get(2).setPorcentagem(100f);
					if (listaGold.get(0).getPontos() >= listaGold.get(1).getPontos()) {
						listaGold.get(0).setPosicao(2);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(2).getPontos());
						listaGold.get(1).setPosicao(3);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(2).getPontos());
					}else{
						listaGold.get(0).setPosicao(3);
						listaGold.get(0).setPorcentagem((listaGold.get(0).getPontos() * 100) / listaGold.get(2).getPontos());
						listaGold.get(1).setPosicao(2);
						listaGold.get(1).setPorcentagem((listaGold.get(1).getPontos() * 100) / listaGold.get(2).getPontos());
					}
				}
			}
		}
	}
	
	public void gerarListaSinze(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<TmRaceBean> listaposicao = null;
		listaSinze = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Silver%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutomes>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			String sql = "SELECT c FROM Corridaprodutomes c WHERE  c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio();

			if (mes > 0) {
				sql = sql + " and c.mes=" + mes;
			}
			if (ano > 0) {
				sql = sql + " and c.ano=" + ano;
			}
			listaCorrida = corridaProdutoMesFacade.listar(sql);

			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaSinze.size() < 3) {
				listaSinze.add(tmRaceBean);
			}else if (listaCorrida.size() > 0 && listaSinze.size() >= 3){
				if ((listaSinze.get(0).getPontos() <= tmRaceBean.getPontos()) && (listaSinze.get(0).getPontos() <= listaSinze.get(1).getPontos()) &&
						(listaSinze.get(0).getPontos() <= listaSinze.get(2).getPontos())) {
					listaposicao.add(listaSinze.get(0));
					listaSinze.add(tmRaceBean);
				}else if ((listaSinze.get(1).getPontos() <= tmRaceBean.getPontos()) && (listaSinze.get(1).getPontos() <= listaSinze.get(0).getPontos()) &&
						(listaSinze.get(1).getPontos() <= listaSinze.get(2).getPontos())) {
					listaposicao.add(listaSinze.get(1));
					listaSinze.add(tmRaceBean);
				} else if ((listaSinze.get(2).getPontos() <= tmRaceBean.getPontos()) && (listaSinze.get(2).getPontos() <= listaSinze.get(1).getPontos()) &&
						(listaSinze.get(2).getPontos() <= listaSinze.get(0).getPontos())) {
					listaposicao.add(listaSinze.get(2));
					listaSinze.add(tmRaceBean);
				}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaSinze.remove(listaposicao.get(j));
				}
			}
		}
		if (listaSinze != null) {
			if (listaSinze.size() == 1) {
				listaSinze.get(0).setPosicao(1);
				listaSinze.get(1).setPorcentagem(100f);
			}else if (listaSinze.size() == 2){
				if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos()) {
					listaSinze.get(0).setPosicao(1);
					listaSinze.get(0).setPorcentagem(100f);
					listaSinze.get(1).setPosicao(2);
					listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(0).getPontos());
				}else{
					listaSinze.get(0).setPosicao(2);
					listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(1).getPontos());
					listaSinze.get(1).setPosicao(1);
					listaSinze.get(1).setPorcentagem(100f);
				}
			}else if(listaSinze.size() == 3){
				if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos() && listaSinze.get(0).getPontos() >= listaSinze.get(2).getPontos()) {
					listaSinze.get(0).setPosicao(1);
					listaSinze.get(0).setPorcentagem(100f);
					if (listaSinze.get(1).getPontos() >= listaSinze.get(2).getPontos()) {
						listaSinze.get(1).setPosicao(2);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(0).getPontos());
						listaSinze.get(2).setPosicao(3);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(0).getPontos());
					}else{
						listaSinze.get(1).setPosicao(3);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(0).getPontos());
						listaSinze.get(2).setPosicao(2);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(0).getPontos());
					}
				}else if(listaSinze.get(1).getPontos() >= listaSinze.get(0).getPontos() && listaSinze.get(1).getPontos() >= listaSinze.get(2).getPontos()){
					listaSinze.get(1).setPosicao(1);
					listaSinze.get(1).setPorcentagem(100f);
					if (listaSinze.get(0).getPontos() >= listaSinze.get(2).getPontos()) {
						listaSinze.get(0).setPosicao(2);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(1).getPontos());
						listaSinze.get(2).setPosicao(3);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(1).getPontos());
					}else{
						listaSinze.get(0).setPosicao(3);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(1).getPontos());
						listaSinze.get(2).setPosicao(2);
						listaSinze.get(2).setPorcentagem((listaSinze.get(2).getPontos() * 100) / listaSinze.get(1).getPontos());
					}
				}else if(listaSinze.get(2).getPontos() >= listaSinze.get(0).getPontos() && listaSinze.get(2).getPontos() >= listaSinze.get(1).getPontos()){
					listaSinze.get(2).setPosicao(1);
					listaSinze.get(2).setPorcentagem(100f);
					if (listaSinze.get(0).getPontos() >= listaSinze.get(1).getPontos()) {
						listaSinze.get(0).setPosicao(2);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(2).getPontos());
						listaSinze.get(1).setPosicao(3);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(2).getPontos());
					}else{
						listaSinze.get(0).setPosicao(3);
						listaSinze.get(0).setPorcentagem((listaSinze.get(0).getPontos() * 100) / listaSinze.get(2).getPontos());
						listaSinze.get(1).setPosicao(2);
						listaSinze.get(1).setPorcentagem((listaSinze.get(1).getPontos() * 100) / listaSinze.get(2).getPontos());
					}
				}
			}
		}
	}
	
	public void gerarListaProdutos(){
		UsuarioProdutoRunnersBean corrida;
		listaCorridaUsuario = new ArrayList<UsuarioProdutoRunnersBean>();
		ProdutoFacade produtoFacade = new ProdutoFacade();
		listaProdutos = produtoFacade.listarProdutosSql("SELECT p From Produtos p WHERE p.produtorunners=true Order By p.ordem ");
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Produtos>();
		}
		for (int i = 0; i < listaProdutos.size(); i++) {
			corrida = new UsuarioProdutoRunnersBean();
			if (listaProdutos.get(i).getIdprodutos() ==1) {
				listaProdutos.get(i).setCorTitulo("#b6c72c;");
			} else if (listaProdutos.get(i).getIdprodutos() ==2) {
				listaProdutos.get(i).setCorTitulo("#c12c2f;");
			}else if (listaProdutos.get(i).getIdprodutos() ==4) {
				listaProdutos.get(i).setCorTitulo("#decf25;");
			}else if (listaProdutos.get(i).getIdprodutos() ==5) {
				listaProdutos.get(i).setCorTitulo("#522c7b;");
			}else if (listaProdutos.get(i).getIdprodutos() ==6) {
				listaProdutos.get(i).setCorTitulo("#66b0ca;");
			}else if (listaProdutos.get(i).getIdprodutos() ==7) {
				listaProdutos.get(i).setCorTitulo("#dfa422;");
			}else if (listaProdutos.get(i).getIdprodutos() ==9) {
				listaProdutos.get(i).setCorTitulo("#be2a73;");
			}else if (listaProdutos.get(i).getIdprodutos() ==10) {
				listaProdutos.get(i).setCorTitulo("#79191d;");
			}else if (listaProdutos.get(i).getIdprodutos() ==16) {   
				listaProdutos.get(i).setCorTitulo("#344d97;");
			}else if (listaProdutos.get(i).getIdprodutos() ==22) {
				listaProdutos.get(i).setCorTitulo("#31436a;");
			}else {
				listaProdutos.get(i).setCorTitulo("#decf25;");
			}
			corrida.setProdutos(listaProdutos.get(i));
			corrida = gerarListaCorrida(corrida);
			listaCorridaUsuario.add(corrida);
		}
	}
	
	public void gerarListaBronze(){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<TmRaceBean> listaposicao = null;
		listaBronze = new ArrayList<TmRaceBean>();
		UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
		List<Unidadenegocio> listaUnidade = unidadeNegocioFacade.listarUnidade("SELECT u FROM Unidadenegocio u WHERE u.categoria like '%Bronze%'");
		if (listaUnidade == null) {
			listaUnidade = new ArrayList<>();
		}
		if (listaCorrida == null) {
			listaCorrida = new ArrayList<Corridaprodutomes>();
		}
		for (int i = 0; i < listaUnidade.size(); i++) {
			listaposicao = new ArrayList<>();
			String sql = "SELECT c FROM Corridaprodutomes c WHERE  c.usuario.unidadenegocio.idunidadeNegocio="+
					listaUnidade.get(i).getIdunidadeNegocio();
			if (mes > 0) {
				sql = sql + " and c.mes=" + mes;
			}
			if (ano > 0) {
				sql = sql + " and c.ano=" + ano;
			}
			listaCorrida = corridaProdutoMesFacade.listar(sql);

			if (listaCorrida == null) {
				listaCorrida = new ArrayList<Corridaprodutomes>();
			}
			TmRaceBean tmRaceBean = new TmRaceBean();
			if (listaCorrida.size() > 0) {
				tmRaceBean.setNomeUnidade(listaUnidade.get(i).getNomerelatorio());
				tmRaceBean.setPontos(0);	
			}
			for (int j = 0; j < listaCorrida.size(); j++) {
				tmRaceBean.setPontos(tmRaceBean.getPontos() + listaCorrida.get(j).getPontos());
			}
			if (listaCorrida.size() > 0 && listaBronze.size() < 3) {
				listaBronze.add(tmRaceBean);
			}else if (listaCorrida.size() > 0 && listaBronze.size() >= 3){
				if ((listaBronze.get(0).getPontos() <= tmRaceBean.getPontos()) && (listaBronze.get(0).getPontos() <= listaBronze.get(1).getPontos()) &&
						(listaBronze.get(0).getPontos() <= listaBronze.get(2).getPontos())) {
					listaposicao.add(listaBronze.get(0));
					listaBronze.add(tmRaceBean);
				}else if ((listaBronze.get(1).getPontos() <= tmRaceBean.getPontos()) && (listaBronze.get(1).getPontos() <= listaBronze.get(0).getPontos()) &&
						(listaBronze.get(1).getPontos() <= listaBronze.get(2).getPontos())) {
					listaposicao.add(listaBronze.get(1));
					listaBronze.add(tmRaceBean);
				} else if ((listaBronze.get(2).getPontos() <= tmRaceBean.getPontos()) && (listaBronze.get(2).getPontos() <= listaBronze.get(1).getPontos()) &&
						(listaBronze.get(2).getPontos() <= listaBronze.get(0).getPontos())) {
					listaposicao.add(listaBronze.get(2));
					listaBronze.add(tmRaceBean);
				}
				for (int j = 0; j < listaposicao.size(); j++) {
					listaBronze.remove(listaposicao.get(j));
				}
			}
		}
		if (listaBronze != null) {
			if (listaBronze.size() == 1) {
				listaBronze.get(0).setPosicao(1);
				listaSinze.get(0).setPorcentagem(100f);
			}else if (listaBronze.size() == 2){
				if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos()) {
					listaBronze.get(0).setPosicao(1);
					listaBronze.get(0).setPorcentagem(100f);
					listaBronze.get(1).setPosicao(2);
					listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(0).getPontos());
				}else{
					listaBronze.get(0).setPosicao(2);
					listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(1).getPontos());
					listaBronze.get(1).setPosicao(1);
					listaBronze.get(1).setPorcentagem(100f);
				}
			}else if(listaBronze.size() == 3){
				if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos() && listaBronze.get(0).getPontos() >= listaBronze.get(2).getPontos()) {
					listaBronze.get(0).setPosicao(1);
					listaBronze.get(0).setPorcentagem(100f);
					if (listaBronze.get(1).getPontos() >= listaBronze.get(2).getPontos()) {
						listaBronze.get(1).setPosicao(2);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(0).getPontos());
						listaBronze.get(2).setPosicao(3);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(0).getPontos());
					}else{
						listaBronze.get(1).setPosicao(3);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(0).getPontos());
						listaBronze.get(2).setPosicao(2);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(0).getPontos());
					}
				}else if(listaBronze.get(1).getPontos() >= listaBronze.get(0).getPontos() && listaBronze.get(1).getPontos() >= listaBronze.get(2).getPontos()){
					listaBronze.get(1).setPosicao(1);
					listaBronze.get(1).setPorcentagem(100f);
					if (listaBronze.get(0).getPontos() >= listaBronze.get(2).getPontos()) {
						listaBronze.get(0).setPosicao(2);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(1).getPontos());
						listaBronze.get(2).setPosicao(3);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(1).getPontos());
					}else{  
						listaBronze.get(0).setPosicao(3);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(1).getPontos());
						listaBronze.get(2).setPosicao(2);
						listaBronze.get(2).setPorcentagem((listaBronze.get(2).getPontos() * 100) / listaBronze.get(1).getPontos());
					}
				}else if(listaBronze.get(2).getPontos() >= listaBronze.get(0).getPontos() && listaBronze.get(2).getPontos() >= listaBronze.get(1).getPontos()){
					listaBronze.get(2).setPosicao(1);
					listaBronze.get(2).setPorcentagem(100f);
					if (listaBronze.get(0).getPontos() >= listaBronze.get(1).getPontos()) {
						listaBronze.get(0).setPosicao(2);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(2).getPontos());
						listaBronze.get(1).setPosicao(3);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(2).getPontos());
					}else{
						listaBronze.get(0).setPosicao(3);
						listaBronze.get(0).setPorcentagem((listaBronze.get(0).getPontos() * 100) / listaBronze.get(2).getPontos());
						listaBronze.get(1).setPosicao(2);
						listaBronze.get(1).setPorcentagem((listaBronze.get(1).getPontos() * 100) / listaBronze.get(2).getPontos());
					}
				}
			}
		}
	}
	

	
	public String getNomeUnidadeGold(int posicao){
		for (int i = 0; i < listaGold.size(); i++) {
			if (listaGold.get(i).getPosicao() == posicao) {
				return listaGold.get(i).getNomeUnidade();
			}
		}
		return "";
	}
	
	public int getPontos(int posicao){
		for (int i = 0; i < listaGold.size(); i++) {
			if (listaGold.get(i).getPosicao() == posicao) {
				return listaGold.get(i).getPontos();
			}
		}
		return 0;
	}
	
	public String getNomeUnidadeSinze(int posicao){
		for (int i = 0; i < listaSinze.size(); i++) {
			if (listaSinze.get(i).getPosicao() == posicao) {
				return listaSinze.get(i).getNomeUnidade();
			}
		}
		return "";
	}
	
	public int getPontosSinze(int posicao){
		for (int i = 0; i < listaSinze.size(); i++) {
			if (listaSinze.get(i).getPosicao() == posicao) {
				return listaSinze.get(i).getPontos();
			}
		}
		return 0;
	}
	

	
	
	public String getNomeUnidadeBronze(int posicao){
		for (int i = 0; i < listaBronze.size(); i++) {
			if (listaBronze.get(i).getPosicao() == posicao) {
				return listaBronze.get(i).getNomeUnidade();
			}
		}
		return "";
	}
	
	public int getPontosBronze(int posicao){
		for (int i = 0; i < listaBronze.size(); i++) {
			if (listaBronze.get(i).getPosicao() == posicao) {
				return listaBronze.get(i).getPontos();
			}
		}
		return 0;
	}
	

	
	public float getPorcentagem(int posicao){
		for (int i = 0; i < listaGold.size(); i++) {
			if (listaGold.get(i).getPosicao() == posicao) {
				return listaGold.get(i).getPorcentagem();
			}
		}
		return 0;
	}
	
	public float getPorcentagemSinze(int posicao){
		for (int i = 0; i < listaSinze.size(); i++) {
			if (listaSinze.get(i).getPosicao() == posicao) {
				return listaSinze.get(i).getPorcentagem();
			} 
		}
		return 0;
	}
	
	public float getPorcentagemBronze(int posicao){
		for (int i = 0; i < listaBronze.size(); i++) {
			if (listaBronze.get(i).getPosicao() == posicao) {
				return listaBronze.get(i).getPorcentagem();
			}
		}
		return 0;
	}
	
	public UsuarioProdutoRunnersBean gerarListaCorrida(UsuarioProdutoRunnersBean corrida){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		List<Corridaprodutomes> listaCorridaMes = null;
		String sql = "SELECT c FROM Corridaprodutomes c WHERE  c.produtos.idprodutos=" + corrida.getProdutos().getIdprodutos();
		if (mes > 0) {
			sql = sql + " and c.mes=" + mes;
		}
		if (ano > 0) {
			sql = sql + " and c.ano=" + ano;
		}
		sql = sql + " ORDER BY c.pontos DESC";
		listaCorridaMes = corridaProdutoMesFacade.listar(sql);
		if (listaCorridaMes == null) {
			listaCorridaMes = new ArrayList<Corridaprodutomes>();
		}
		if (listaCorridaMes.size() > 0) {
			corrida.setFoto(getFotoUsuario(listaCorridaMes.get(0).getUsuario()));
			corrida.setPontos(listaCorridaMes.get(0).getPontos());
			corrida.setNomeConsultor(listaCorridaMes.get(0).getUsuario().getNome());
			corrida.setNomeUnidade(listaCorridaMes.get(0).getUsuario().getUnidadenegocio().getNomerelatorio());
		}else{
			corrida.setFoto(getFotoUsuario(null));
			corrida.setPontos(0);
			corrida.setNomeConsultor("");
			corrida.setNomeUnidade("");
		}
		return corrida;
	}
	
	
	public String getFotoUsuario(Usuario usuario) {
		String caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (usuario != null) {
			if (usuario.isFoto()) {
				caminho = caminho + "/usuario/" + usuario.getIdusuario() + ".jpg";
			}else{
				caminho = caminho + "/usuario/0.png";
			}
		}else{
			caminho = caminho + "/usuario/0.png";
		}
		return caminho;
	}

}
