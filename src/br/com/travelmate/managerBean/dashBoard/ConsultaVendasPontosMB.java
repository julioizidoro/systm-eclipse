package br.com.travelmate.managerBean.dashBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ConsultaVendasPontosMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject 
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Vendas> listaVendas;
	
	@PostConstruct
	public void init(){
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			getUsuarioLogadoMB();
			listarVendasMes();
		}
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<Vendas> getListaVendas() {
		return listaVendas;
	}

	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}
	
	public void listarVendasMes(){
		int mes = Formatacao.getMesData(new Date());
		mes = mes + 1;
		int ano = Formatacao.getAnoData(new Date());
		String data = String.valueOf(ano) + "-" + String.valueOf(mes) + "-01"; 
		
		String sql = "SELECT v FROM Vendas v where v.dataVenda>='" + data + "' and (v.situacao='FINALIZADA' OR v.situacao='ANDAMENTO') and v.vendasMatriz='S'" +
				 " and v.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario() + "  order By v.dataVenda ASC, v.idvendas ASC";
		listaVendas = vendasDao.lista(sql);
		if (listaVendas==null){
			listaVendas = new ArrayList<Vendas>();
		}
	}
	
	public String fechar(){
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	

}
