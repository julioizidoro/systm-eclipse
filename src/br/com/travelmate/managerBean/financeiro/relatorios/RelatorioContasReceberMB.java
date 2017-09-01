package br.com.travelmate.managerBean.financeiro.relatorios;

import br.com.travelmate.facade.BancoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.model.Banco;
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
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class RelatorioContasReceberMB  implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private Date dataInicio;
    private Date dataTermino;
    private String tipoRelatorio;
    private String tipoDocumento;
    private Banco banco;
    private List<Banco> listaBanco;
    
    @PostConstruct()
    public void init(){
        gerarListaUnidadeNegocio();
        gerarListaBanco();
    }

    public List<Unidadenegocio> getListaUnidadeNegocio() {
        return listaUnidadeNegocio;
    }

    public void setListaUnidadeNegocio(List<Unidadenegocio> listaUnidadeNegocio) {
        this.listaUnidadeNegocio = listaUnidadeNegocio;
    }

    public Unidadenegocio getUnidadenegocio() {
        return unidadenegocio;
    }

    public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
        this.unidadenegocio = unidadenegocio;
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

    public String getTipoRelatorio() {
        return tipoRelatorio;
    }

    public void setTipoRelatorio(String tipoRelatorio) {
        this.tipoRelatorio = tipoRelatorio;
    }
    
    public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
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

	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar();
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
    
     public String gerarSql(){
        String sql = "SELECT distinct contasreceber.idcontasReceber, contasreceber.numeroDocumento, contasreceber.valorParcela, " +
                    "contasreceber.numeroparcelas,contasreceber.dataVencimento, contasreceber.juros, contasreceber.desagio," +
                    "contasreceber.tipodocumento, contasreceber.dataPagamento, contasreceber.valorpago, cliente.nome," +
                    "banco.nome as nomeBanco From "
            	+ " contasreceber join vendas on contasreceber.vendas_idvendas = vendas.idvendas"
                + " join cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " join unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idUnidadeNegocio"
                + " join banco on contasreceber.banco_idbanco = banco.idbanco"
                + " where contasreceber.situacao<>''";
        if(tipoRelatorio.equalsIgnoreCase("Contas Canceladas")){
        	sql=sql+" and contasreceber.datacancelamento>='"+ Formatacao.ConvercaoDataSql(dataInicio) 
        		+ "' and contasreceber.datacancelamento<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
        }else{
        	sql=sql+" and contasreceber.situacao<>'cc' and contasreceber.datavencimento>='"  
        			+ Formatacao.ConvercaoDataSql(dataInicio) + "' and contasreceber.datavencimento<='"
    				+ Formatacao.ConvercaoDataSql(dataTermino) + "'";
        }
        if (unidadenegocio!=null){
            sql = sql + " and unidadenegocio.idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        }
        if (banco.getIdbanco()!=null){
            sql = sql + " and banco.idbanco=" + banco.getIdbanco();
        }
        if (tipoDocumento!=null && tipoDocumento.length()>3){
            sql = sql + " and tipodocumento='" + tipoDocumento+"'";
        }
        if (tipoRelatorio!=null){
            if(tipoRelatorio.equalsIgnoreCase("Contas Recebidas")){
               sql = sql + " and contasreceber.valorpago>0";
            }else if(tipoRelatorio.equalsIgnoreCase("Contas em Aberto")){
               sql = sql + " and contasreceber.valorpago=0";
            }else if(tipoRelatorio.equalsIgnoreCase("Contas Canceladas")){
                sql = sql + " and contasreceber.situacao='cc'";
            }else sql = sql + " ";
        }
        sql = sql + " order by contasreceber.datavencimento ";
        return sql;
    }
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/financeiro/contasReceber.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql());
        if (unidadenegocio==null){
        	parameters.put("unidade", "TRAVELMATE - Matriz");
        }else parameters.put("unidade", unidadenegocio.getNomeFantasia());
        parameters.put("titulo", tipoRelatorio);
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        String periodo= "";
        if ((dataInicio!=null) && (dataTermino!=null)){
                periodo = "Periodo : " + Formatacao.ConvercaoDataPadrao(dataInicio) 
                        + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
        }
        parameters.put("periodo", periodo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "contasreceber.pdf", null);
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
    
    
}
