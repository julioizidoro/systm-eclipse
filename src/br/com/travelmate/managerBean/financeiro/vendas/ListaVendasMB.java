package br.com.travelmate.managerBean.financeiro.vendas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
@ViewScoped
public class ListaVendasMB implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private VendasDao vendasDao;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	private List<Vendas> listaVendas;
	private String vendasAcumuladas;
	
	
	@PostConstruct
	public void init(){
		gerarListaVendas();
	}
	
	
	
	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}



	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}



	public String getVendasAcumuladas() {
		return vendasAcumuladas;
	}



	public void setVendasAcumuladas(String vendasAcumuladas) {
		this.vendasAcumuladas = vendasAcumuladas;
	}



	public List<Vendas> getListaVendas() {
		return listaVendas;
	}



	public void setListaVendas(List<Vendas> listaVendas) {
		this.listaVendas = listaVendas;
	}



	public void gerarListaVendas(){
		if (usuarioLogadoMB.getUsuario() == null || usuarioLogadoMB.getUsuario().getIdusuario() == null) {
		}else{
	    	GregorianCalendar dataCal = new GregorianCalendar();
	    	dataCal.setTime(new Date());
	    	int mes = dataCal.get(Calendar.MONTH);
	    	mes = mes + 1;
	    	int ano = dataCal.get(Calendar.YEAR);
	    	String data = String.valueOf(ano) + "-";
	    	if (mes<=9){
	    		data = data + "0" + String.valueOf(mes);
	    	}else data = data + String.valueOf(mes);
	    	data = data + "-" + "01";
	    	Date dataConsulta = null;
			try {
				dataConsulta = Formatacao.SomarDiasDatas(new Date(), -2);
				int diaSemana = Formatacao.diaSemana(dataConsulta);
				if (diaSemana==1){
					dataConsulta = Formatacao.SomarDiasDatas(new Date(), -3);
				}else if (diaSemana==3){
					dataConsulta = Formatacao.SomarDiasDatas(new Date(), -3);
				}else if (diaSemana==7){
					dataConsulta = Formatacao.SomarDiasDatas(new Date(), -3);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			String dataInicial = Formatacao.ConvercaoDataSql(dataConsulta);
			String sql = "Select v from Vendas v where (v.situacao='FINALIZADA' OR v.situacao='ANDAMENTO') and v.vendasMatriz='S' and v.dataVenda>='" + dataInicial + "' ";
			String sqlacumulado = "Select distinct sum(valor) as valor " +
			        "From vendas where (situacao='FINALIZADA' OR situacao='ANDAMENTO') and vendasMatriz='S' and dataVenda>='" + data + "'";
			if ((usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) && (usuarioLogadoMB.getUsuario().getGrupoacesso().getIdgrupoAcesso()==1)){
				sql = sql + " and v.unidadenegocio.idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
				sqlacumulado = sqlacumulado + " and unidadeNegocio_idunidadeNegocio=" + usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			}else if ((usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Unidade")) && (usuarioLogadoMB.getUsuario().getGrupoacesso().getIdgrupoAcesso()>1)){
				sql = sql + " and v.usuario.idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
				sqlacumulado = sqlacumulado + " and usuario_idusuario=" + usuarioLogadoMB.getUsuario().getIdusuario();
			}else 
			sql = sql + " order by v.dataVenda desc, v.idvendas";
			
			listaVendas = vendasDao.lista(sql);
			if (listaVendas==null){
				listaVendas = new ArrayList<>();
			}	
	    	Double valor= vendasDao.saldoAcumulado(sqlacumulado);
			vendasAcumuladas = Formatacao.formatarDoubleString(valor);
		}
	}

}
