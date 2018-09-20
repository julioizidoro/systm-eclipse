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

import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.UsuarioPontosFacade;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Usuariopontos;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class MateRunnersMB implements Serializable {

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
	private int posicao = 1;
	private boolean habilitarMateRunners = true;
	private boolean habilitarProductRunners = false;
	private boolean habilitarTmRace = false;
	private String urlArquivo = "";

	@PostConstruct
	public void init() {
		pegarFtpDados();
		if (listaPontos == null) {
			mesReferencia = Formatacao.getMesData(new Date());
			carregarListaRunners();
		}
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

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public boolean isHabilitarMateRunners() {
		return habilitarMateRunners;
	}

	public void setHabilitarMateRunners(boolean habilitarMateRunners) {
		this.habilitarMateRunners = habilitarMateRunners;
	}

	public boolean isHabilitarProductRunners() {
		return habilitarProductRunners;
	}

	public void setHabilitarProductRunners(boolean habilitarProductRunners) {
		this.habilitarProductRunners = habilitarProductRunners;
	}

	public boolean isHabilitarTmRace() {  
		return habilitarTmRace;
	}

	public void setHabilitarTmRace(boolean habilitarTmRace) {
		this.habilitarTmRace = habilitarTmRace;
	}

	public String getUrlArquivo() {
		return urlArquivo;
	}

	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}

	public void carregarListaRunners() {
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT p FROM Usuariopontos p where p.mes=" + mes + " and p.ano=" + ano
				+ " and p.usuario.vende=1 ORDER BY p.pontos DESC, p.usuario.nome";
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
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT p FROM Usuariopontos p where p.mes=" + mes + " and p.ano=" + ano
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
			if (ftpdados != null) {
				urlArquivo = ftpdados.getProtocolo() + "://" + ftpdados.getHost() + ":" + ftpdados.getWww();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String pegarEndereco() {
		String endereco = ftpdados.getProtocolo() + "://";
		endereco = endereco + ftpdados.getHost();
		endereco = endereco + ":82/systm/tmstar/TMS01.pdf";
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
	
	
	public void mudarPosicao(boolean tipo){
		if (tipo) {
			posicao = posicao + 1;
		}else{
			posicao = posicao - 1;
		}
		if (posicao <= 0) {
			posicao = 3;
		}else if(posicao > 3){
			posicao = 1;
		}
		if (posicao == 1) {
			habilitarMateRunners = true;
			habilitarProductRunners = false;
			habilitarTmRace = false;
		}else if(posicao == 2){
			habilitarMateRunners = false;
			habilitarProductRunners = true;
			habilitarTmRace = false;
		}else if(posicao == 3){
			habilitarMateRunners = false;
			habilitarProductRunners = false;
			habilitarTmRace = true;
		}
	}
	
	public void mudar(){
		if (posicao == 1) {
			habilitarMateRunners = true;
			habilitarProductRunners = false;
			habilitarTmRace = false;
		}else if(posicao == 2){
			habilitarMateRunners = false;
			habilitarProductRunners = true;
			habilitarTmRace = false;
		}else if(posicao == 3){
			habilitarMateRunners = false;
			habilitarProductRunners = false;
			habilitarTmRace = true;
		}
	}
}
