package br.com.travelmate.managerBean.financeiro.relatorios;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.RemessaContasFacade;
import br.com.travelmate.facade.RetornoContasFacade;
import br.com.travelmate.model.Remessaarquivo;
import br.com.travelmate.model.Remessacontas;
import br.com.travelmate.model.Retornoarquivo;
import br.com.travelmate.model.Retornocontas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Named
@ViewScoped
public class RelatorioRemessaRetornoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipo;
	private Remessaarquivo remessaarquivo;
	private Remessacontas remessacontas;
	private Retornoarquivo retornoaruqivo;
	private Retornocontas retornocontas;
	private List<Remessacontas> listaRemessaContas;
	private List<Retornocontas> listaRetornoContas;
	private boolean tabelaRemessa = false;
	private boolean tabelaRetorno = false;
	private Date dataInicial;
	private Date dataFinal;
	private List<RetornoBean> listaEnviada;
	private boolean todasremessa;
	private boolean todosretorno;
	
	
	@PostConstruct
	public void init(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		tipo = (String) session.getAttribute("tipo");
		session.removeAttribute("tipo");
		if (tipo == null) {
			tipo = "";
		}
		if (tipo.equalsIgnoreCase("Remessa")) {
			tabelaRemessa = true;
		}else if(tipo.equalsIgnoreCase("Retorno")){
			tabelaRetorno = true;
		}
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


	public Remessaarquivo getRemessaarquivo() {
		return remessaarquivo;
	}


	public void setRemessaarquivo(Remessaarquivo remessaarquivo) {
		this.remessaarquivo = remessaarquivo;
	}


	public Remessacontas getRemessacontas() {
		return remessacontas;
	}


	public void setRemessacontas(Remessacontas remessacontas) {
		this.remessacontas = remessacontas;
	}


	public Retornoarquivo getRetornoaruqivo() {
		return retornoaruqivo;
	}


	public void setRetornoaruqivo(Retornoarquivo retornoaruqivo) {
		this.retornoaruqivo = retornoaruqivo;
	}


	public Retornocontas getRetornocontas() {
		return retornocontas;
	}


	public void setRetornocontas(Retornocontas retornocontas) {
		this.retornocontas = retornocontas;
	}


	public List<Remessacontas> getListaRemessaContas() {
		return listaRemessaContas;
	}


	public void setListaRemessaContas(List<Remessacontas> listaRemessaContas) {
		this.listaRemessaContas = listaRemessaContas;
	}


	public List<Retornocontas> getListaRetornoContas() {
		return listaRetornoContas;
	}


	public void setListaRetornoContas(List<Retornocontas> listaRetornoContas) {
		this.listaRetornoContas = listaRetornoContas;
	}

	
	
	public boolean isTabelaRemessa() {
		return tabelaRemessa;
	}


	public void setTabelaRemessa(boolean tabelaRemessa) {
		this.tabelaRemessa = tabelaRemessa;
	}


	public boolean isTabelaRetorno() {
		return tabelaRetorno;
	}


	public void setTabelaRetorno(boolean tabelaRetorno) {
		this.tabelaRetorno = tabelaRetorno;
	}


	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	
	public boolean isTodasremessa() {
		return todasremessa;
	}


	public void setTodasremessa(boolean todasremessa) {
		this.todasremessa = todasremessa;
	}


	public boolean isTodosretorno() {
		return todosretorno;
	}


	public void setTodosretorno(boolean todosretorno) {
		this.todosretorno = todosretorno;
	}


	public void direcionarPesquisa(){
		if (tipo.equalsIgnoreCase("Remessa")) {
			pesquisarRemessa();
		}else if(tipo.equalsIgnoreCase("Retorno")){
			pesquisarRetorno();
		}
	}
	
	
	public void pesquisarRemessa(){
		RemessaContasFacade remessaContasFacade = new RemessaContasFacade();
		String sql = "Select r From Remessacontas r ";
		if (dataInicial != null || dataFinal != null) {
			sql = sql + " Where ";
		}
		if (dataInicial != null) {
			sql = sql + " r.remessaarquivo.dataenvio>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
			if (dataFinal != null) {
				sql = sql + " and ";
			}
		}
		
		if (dataFinal != null) {
			sql = sql + " r.remessaarquivo.dataenvio<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
		}
		
		listaRemessaContas = remessaContasFacade.listar(sql);
		
		if (listaRemessaContas == null) {
			listaRemessaContas = new ArrayList<>();
		}
	}
	
	
	public void pesquisarRetorno(){
		RetornoContasFacade retornoContasFacade = new RetornoContasFacade();
		String sql = "Select r From Retornocontas r ";
		if (dataInicial != null || dataFinal != null) {
			sql = sql + " Where ";
		}
		if (dataInicial != null) {
			sql = sql + " r.retornoarquivo.dataretorno>='" + Formatacao.ConvercaoDataSql(dataInicial) + "' ";
			if (dataFinal != null) {
				sql = sql + " and ";
			}
		}
		
		if (dataFinal != null) {  
			sql = sql + " r.retornoarquivo.dataretorno<='" + Formatacao.ConvercaoDataSql(dataFinal) + "' ";
		}
		
		listaRetornoContas = retornoContasFacade.listar(sql);
		
		if (listaRetornoContas == null) {
			listaRetornoContas = new ArrayList<>();
		}
	}
	
	
	public void imprimirRemessa(){
		listaEnviada = new ArrayList<>();
		for (int i = 0; i < listaRemessaContas.size(); i++) {
			if (listaRemessaContas.get(i).isSelecionado()) {
				RetornoBean retornoBean = new RetornoBean();
				retornoBean.setNomePagador(listaRemessaContas.get(i).getContasreceber().getVendas().getCliente().getNome());
				retornoBean.setValorJuros(listaRemessaContas.get(i).getContasreceber().getJuros());
				retornoBean.setNossoNumero(listaRemessaContas.get(i).getContasreceber().getNossonumero());
				retornoBean.setValorTitulo(listaRemessaContas.get(i).getContasreceber().getValorparcela());
				retornoBean.setDataPagamento(Formatacao.ConvercaoDataPadrao(listaRemessaContas.get(i).getContasreceber().getDatavencimento()));
				retornoBean.setCodigoOcorrencia(listaRemessaContas.get(i).getCodigoocorrencia());
				listaEnviada.add(retornoBean);
			}
		}
		imprimirListaRemessa();
	}
	
	public void imprimirRetorno(){
		listaEnviada = new ArrayList<>();
		for (int i = 0; i < listaRetornoContas.size(); i++) {
			if (listaRetornoContas.get(i).isSelecionado()) {
				RetornoBean retornoBean = new RetornoBean();
				retornoBean.setNomePagador(listaRetornoContas.get(i).getContasreceber().getVendas().getCliente().getNome());
				retornoBean.setValorJuros(listaRetornoContas.get(i).getContasreceber().getJuros());
				retornoBean.setNossoNumero(listaRetornoContas.get(i).getContasreceber().getNossonumero());
				retornoBean.setValorTitulo(listaRetornoContas.get(i).getContasreceber().getValorparcela());
				retornoBean.setDataPagamento(Formatacao.ConvercaoDataPadrao(listaRetornoContas.get(i).getContasreceber().getDatavencimento()));
				retornoBean.setCodigoOcorrencia(listaRetornoContas.get(i).getCodigoocorrencia());
				listaEnviada.add(retornoBean);
			}
		}
		imprimirListaRemessa();
	}
	
	
	public String imprimirListaRemessa(){
		if(listaEnviada!=null && listaEnviada.size()>0){
			String caminhoRelatorio = "/reports/financeiro/reportboletositau.jasper";  
	        Map<String, Object> parameters = new HashMap<String, Object>();
	        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
	        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
	        BufferedImage logo = null;
			try {
				logo = ImageIO.read(f);
			} catch (IOException e) {
				e.printStackTrace();
			}  
	        parameters.put("logo", logo);
	        parameters.put("titulo", "Remessa Enviada");
	        parameters.put("tituloColunaData", "Data vcto.");
	        JRDataSource jrds = new JRBeanCollectionDataSource(listaEnviada);
	        GerarRelatorio gerarRelatorio = new GerarRelatorio();
	        try {
	            gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "BoletosItau.pdf");
	        } catch (JRException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (IOException ex) {
	            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        listaEnviada = null;
		} else {
			FacesMessage msg = new FacesMessage("Lista de remessa vazia. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Lista de remessa vazia.");
		}
        return "";
	} 
	

	public void selecionarTodasRemessa(){
		for (int i = 0; i < listaRemessaContas.size(); i++) {
			listaRemessaContas.get(i).setSelecionado(todasremessa);
		}
	}
	
	
	public void selecionarTodasRetorno(){
		for (int i = 0; i < listaRetornoContas.size(); i++) {
			listaRetornoContas.get(i).setSelecionado(todosretorno);
		}
	}
	
	
	

}
