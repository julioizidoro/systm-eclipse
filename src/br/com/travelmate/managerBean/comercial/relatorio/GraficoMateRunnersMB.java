package br.com.travelmate.managerBean.comercial.relatorio;

import br.com.travelmate.facade.UsuarioPontosFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Usuariopontos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class GraficoMateRunnersMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private String mes;
	private String ano;
	private List<Usuariopontos> listaPontos;
	private int numero;
	private List<NumeroTopBean> listaTOP;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		mes = (String) session.getAttribute("mes");
		ano = (String) session.getAttribute("ano");
		numero = (int) session.getAttribute("numero");
		session.removeAttribute("numero");
		session.removeAttribute("ano");
		session.removeAttribute("mes");
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT p FROM Usuariopontos p where p.mes=" + mes + " and p.ano=" + ano
				+ " ORDER BY p.pontos DESC, p.usuario.nome";
		listaPontos = usuarioPontosFacade.listar(sql);
		if (listaPontos == null) {
			listaPontos = new ArrayList<Usuariopontos>();
		}
		listaTOP = new ArrayList<>();
		for (int i = 0; i < numero; i++) {
			NumeroTopBean e = new NumeroTopBean();
			e.setNumero(i);
			listaTOP.add(e);
		}
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public List<Usuariopontos> getListaPontos() {
		return listaPontos;
	}

	public void setListaPontos(List<Usuariopontos> listaPontos) {
		this.listaPontos = listaPontos;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<NumeroTopBean> getListaTOP() {
		return listaTOP;
	}

	public void setListaTOP(List<NumeroTopBean> listaTOP) {
		this.listaTOP = listaTOP;
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public Double getPercentualEscola(int i) {
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

	public String getNumeroVendasEscola(int i) {
		if (listaPontos.size() > i) {
			return String.valueOf(listaPontos.get(i).getPontos());
		}
		return "0";
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

	public String getTituloMateRunners() {
		if (listaPontos != null) {
			return "MAIOR PONTUADOR";
		}
		return null;
	}

	public String getNomeUnidadeTituloMateRunners() {
		if (listaPontos != null) {
			return listaPontos.get(0).getUsuario().getNome() + " | "
					+ listaPontos.get(0).getUsuario().getUnidadenegocio().getNomerelatorio();
		}
		return null;
	}

	public int getPontuacaoTituloMateRunners() {
		if (listaPontos != null) {
			if (listaPontos.get(0).getUsuario().getIdusuario()==16){
				if (listaPontos.get(0).getPontos()>0){
					int pontos = listaPontos.get(0).getPontos()/2;
					return pontos;
				}
			}
			return listaPontos.get(0).getPontos();
		}
		return 0;
	}

	public String getFotoUsuarioPonto() {
		String caminho = null;
		caminho = aplicacaoMB.getParametrosprodutos().getCaminhoimagens();
		if (listaPontos != null) {
			if (listaPontos.get(0).getUsuario().isFoto()) {
				return caminho = caminho + "/usuario/" + listaPontos.get(0).getUsuario().getIdusuario() + ".jpg";
			} else
				return caminho = caminho + "/usuario/0.png";
		}
		return "";
	}

}
