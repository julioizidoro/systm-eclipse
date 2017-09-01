/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.financeiro.relatorios;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.ContasPagarFacade;
import br.com.travelmate.facade.PlanoContaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Banco;
import br.com.travelmate.model.Contaspagar;
import br.com.travelmate.model.Planoconta;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.context.RequestContext;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class RelatorioConciliacaoMB implements Serializable{
    
	private static final long serialVersionUID = 1L;
    private List<Unidadenegocio> listaUnidadeNegocio;
    private List<Banco> listaBanco;
    private Date dataInicio;
    private Date dataTermino;
    private Unidadenegocio unidadenegocio;
    private Banco banco;
    private List<Planoconta> listaPlanoContas;
    private Planoconta planoConta;
    private Float saldoInicial;
    private String Unidade;
    private String nomebanco;
    
    
    @PostConstruct()
    public void init(){
        gerarListaUnidadeNegocio();
        gerarListaPlanoContas();
        gerarListaBanco();
    }

    public List<Unidadenegocio> getListaUnidadeNegocio() {
        return listaUnidadeNegocio;
    }

    public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
        this.listaUnidadeNegocio = listaUnidadeNegocio;
    }

    public List<Banco> getLsitaBanco() {
        return listaBanco;
    }

    public void setLsitaBanco(List<Banco> listaBanco) {
        this.listaBanco = listaBanco;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }
    
    public List<Banco> getListaBanco() {
		return listaBanco;
	}

	public void setListaBanco(List<Banco> listaBanco) {
		this.listaBanco = listaBanco;
	}

	public List<Planoconta> getListaPlanoContas() {
		return listaPlanoContas;
	}

	public void setListaPlanoContas(List<Planoconta> listaPlanoContas) {
		this.listaPlanoContas = listaPlanoContas;
	}

	public Planoconta getPlanoConta() {
		return planoConta;
	}

	public void setPlanoConta(Planoconta planoConta) {
		this.planoConta = planoConta;
	}

	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listarContasPagar();
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
    
	public void gerarListaBanco() {
		BancoFacade bancoFacade = new BancoFacade();
		listaBanco = bancoFacade.listar();
		if (listaBanco == null) {
			listaBanco = new ArrayList<Banco>();
		}
	}
    
    public void gerarListaPlanoContas(){
    	PlanoContaFacade planoContaFacade = new PlanoContaFacade();
    	listaPlanoContas = planoContaFacade.listar("");
    	if (listaPlanoContas==null){
    		listaPlanoContas = new ArrayList<Planoconta>();
    	}
    }
    
    public String gerarRelatorio() throws SQLException, IOException {
        String caminhoRelatorio = "/reports/financeiro/conciliacao.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        List<ConciliacaoBean> lista = gerarListaConciliacao();
        parameters.put("dataInicial", Formatacao.ConvercaoDataPadrao(dataInicio));
        parameters.put("dataFinal", Formatacao.ConvercaoDataPadrao(dataTermino));
        if(Unidade.length()>0){
        	parameters.put("unidade", Unidade);
        }else{
        	parameters.put("unidade", "");
        }
        if(saldoInicial!=null){
        	parameters.put("saldoinicial", saldoInicial);
        }else{
        	saldoInicial = 0.0f;
        	parameters.put("unidade", saldoInicial);
        }
        if(nomebanco.length()>0){
        	parameters.put("banco", nomebanco);
        }else{
        	parameters.put("banco", "");
        }
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        JRDataSource jrds = new JRBeanCollectionDataSource(lista);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioDSPDF(caminhoRelatorio, parameters, jrds, "Conciliação.pdf");
        } catch (JRException ex) {
            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioConciliacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    public List<ConciliacaoBean> gerarListaConciliacao(){
    	Unidade = "TODAS";
    	nomebanco = "TODOS";
    	String sql = "Select c From Contaspagar c where c.datacompensacao>='" + Formatacao.ConvercaoDataSql(dataInicio) + "' and" +
    				" c.datacompensacao<='" + Formatacao.ConvercaoDataSql(dataTermino) + "' ";
    				    				
    	if (unidadenegocio!=null){
    		if (unidadenegocio.getIdunidadeNegocio()!=null){
    			sql =sql + " and c.unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
    			Unidade = unidadenegocio.getNomeFantasia();
    		}
    	}
    	if (banco!=null){
    		if (banco.getIdbanco()!=null){
	    		sql = sql + " and c.banco.idbanco=" + banco.getIdbanco();
	    		nomebanco = banco.getNome();
    		}
    	}
    	if (planoConta!=null){
    		if (planoConta.getIdplanoconta()!=null){
    			sql = sql + " and c.planoconta.idplanoconta=" + planoConta.getIdplanoconta();
    	    }
    	}
    	sql = sql + " order by c.datacompensacao";
    	ContasPagarFacade contasPagarFacade = new ContasPagarFacade();
    	List<Contaspagar> lista = contasPagarFacade.listar(sql);
    	List<ConciliacaoBean> listaConciliacao = new ArrayList<ConciliacaoBean>();
    	if (lista!=null){
    		saldoInicial = gerarSaldoInicial();
    		Float saldoAtual = saldoInicial;
    		for(int i=0;i<lista.size();i++){
    			ConciliacaoBean conciliacao = new ConciliacaoBean();
    			conciliacao.setDataCompensacao(lista.get(i).getDatacompensacao());
    			conciliacao.setDescricao(lista.get(i).getDescricao());
    			conciliacao.setPlanoContas(lista.get(i).getPlanoconta().getDescricao());
    			saldoAtual = saldoAtual  + lista.get(i).getValorentrada()  - lista.get(i).getValorsaida();
    			conciliacao.setSaldo(saldoAtual);
    			conciliacao.setValorEntrada(lista.get(i).getValorentrada());
    			conciliacao.setValorSaida(lista.get(i).getValorsaida());
    			listaConciliacao.add(conciliacao);
    		}
    	}
    	return listaConciliacao;
    }
    
    public float gerarSaldoInicial(){
    	ContasPagarFacade contasPagarFacade= new ContasPagarFacade();
    	return contasPagarFacade.gerarSaldoInicial(dataInicio, banco.getIdbanco());
    }
}
    

