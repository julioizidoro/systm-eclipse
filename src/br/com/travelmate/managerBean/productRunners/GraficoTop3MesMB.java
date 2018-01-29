package br.com.travelmate.managerBean.productRunners;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.CorridaProdutoMesFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.comercial.relatorio.NumeroTopBean;
import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class GraficoTop3MesMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private int mes;
	private int ano;
	private List<Corridaprodutomes> listaPontos;
	private int numero;
	private List<NumeroTopBean> listaTOP;
	private Produtos produtos;

	@PostConstruct()
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		produtos = (Produtos) session.getAttribute("produtos");
		session.removeAttribute("produtos");    
		mes = Formatacao.getMesData(new Date()) + 1;
		ano = Formatacao.getAnoData(new Date());
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		String sql = "SELECT c FROM Corridaprodutomes c where c.mes=" + mes + " and c.ano=" + ano + " and c.produtos.idprodutos=" + produtos.getIdprodutos() +
				" ORDER BY c.pontos DESC";
		listaPontos = corridaProdutoMesFacade.listar(sql);
		if (listaPontos==null){
			listaPontos = new ArrayList<Corridaprodutomes>();   
		} 
		numero = listaPontos.size();
		listaTOP = new ArrayList<>();
		for (int i = 0; i <3; i++) {   
			NumeroTopBean e = new NumeroTopBean();
			e.setNumero(i);
			listaTOP.add(e); 
		}  
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





	public List<Corridaprodutomes> getListaPontos() {
		return listaPontos;
	}

	public void setListaPontos(List<Corridaprodutomes> listaPontos) {
		this.listaPontos = listaPontos;
	}

	public Produtos getProdutos() {
		return produtos;
	}

	public void setProdutos(Produtos produtos) {
		this.produtos = produtos;
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

}
