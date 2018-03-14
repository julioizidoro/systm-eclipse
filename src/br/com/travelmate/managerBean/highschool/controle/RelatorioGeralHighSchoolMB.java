package br.com.travelmate.managerBean.highschool.controle;
 
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Fornecedorcidade;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class RelatorioGeralHighSchoolMB implements Serializable{
    
    /**
	 *      
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private Date dataInicioVenda;
    private Date dataTerminoVenda;    
    private Date dataInicio;
    private Date dataTermino;     
    private Fornecedorcidade fornecedorcidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    private String valoresAnoInicio;
    private String valoresInicio;
    
    @PostConstruct()
    public void init(){
    	unidadenegocio = new Unidadenegocio(); 
    	gerarListaFornecedorCidade();
        gerarListaUnidadeNegocio(); 
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
 
	public Fornecedorcidade getFornecedorcidade() {
		return fornecedorcidade;
	}

	public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
		this.fornecedorcidade = fornecedorcidade;
	}

	public List<Fornecedorcidade> getListaFornecedorCidade() {
		return listaFornecedorCidade;
	}

	public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
		this.listaFornecedorCidade = listaFornecedorCidade;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Date getDataInicioVenda() {
		return dataInicioVenda;
	}

	public void setDataInicioVenda(Date dataInicioVenda) {
		this.dataInicioVenda = dataInicioVenda;
	}

	public Date getDataTerminoVenda() {
		return dataTerminoVenda;
	}

	public void setDataTerminoVenda(Date dataTerminoVenda) {
		this.dataTerminoVenda = dataTerminoVenda;
	}

	public String getValoresAnoInicio() {
		return valoresAnoInicio;
	}

	public void setValoresAnoInicio(String valoresAnoInicio) {
		this.valoresAnoInicio = valoresAnoInicio;
	}

	public String getValoresInicio() {
		return valoresInicio;
	}

	public void setValoresInicio(String valoresInicio) {
		this.valoresInicio = valoresInicio;
	}

	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    } 
    
    public String gerarSql(){
        String sql = "SELECT distinct cliente.nome, cliente.dataNascimento, vendas.dataVenda, cliente.foneCelular,"
                + " cliente.email, cliente.fonePai, cliente.foneMae, unidadenegocio.nomerelatorio, highschool.duracaoIntercambio,"
                + " highschool.escolaIntercambio as nomeEscola, controlehighschool.dataEnvioApp, controlehighschool.datadocumentacaovisto,"
                + " controlehighschool.datacomprovantepagamento, controlehighschool.dataenviopassagem, controlehighschool.familia"
            	+ " FROM Controlehighschool JOIN vendas on controlehighschool.vendas_idvendas = vendas.idvendas"
                + " JOIN cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " JOIN highschool on controlehighschool.highschool_idhighschool = highschool.idhighschool"
                + " JOIN valoreshighschool on highschool.valoreshighschool_idvaloreshighschool = valoreshighschool.idvaloreshighschool"
                + " JOIN unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " where (vendas.situacao='FINALIZADA' OR vendas.situacao='ANDAMENTO')";
        if(dataInicioVenda!=null && dataTerminoVenda!=null){
        	sql = sql + " and vendas.dataVenda >='"+Formatacao.ConvercaoDataSql(dataInicioVenda)
        			  +"' and vendas.dataVenda <='" + Formatacao.ConvercaoDataSql(dataTerminoVenda) + "'";
        } 
        if(valoresAnoInicio!=null && !valoresAnoInicio.equalsIgnoreCase("TODOS")){
        		sql = sql + " and valoreshighschool.anoinicio='"+ valoresAnoInicio + "'";
        }  
        if(valoresInicio!=null && !valoresInicio.equalsIgnoreCase("TODOS")){
    		sql = sql + " and valoreshighschool.inicio='"+ valoresInicio + "'";
    } 
        if (unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
            sql = sql + " and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        }
        if (fornecedorcidade!=null && fornecedorcidade.getIdfornecedorcidade()!=null){
            sql = sql + " and vendas.fornecedor_idfornecedor=" + fornecedorcidade.getFornecedor().getIdfornecedor();
        }
        sql = sql + " order by vendas.dataVenda ";
        return sql;
    }
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/controlehighschool/relatorioGeral.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql()); 
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "Relatório Geral.pdf", null);
        } catch (JRException ex) {
            Logger.getLogger(RelatorioGeralHighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioGeralHighSchoolMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    } 
    
    public void gerarListaFornecedorCidade() {
    	FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
    	String sql = "SELECT f FROM Fornecedorcidade f WHERE f.fornecedor.idfornecedor>1000"
    			+ " AND f.produtos.idprodutos="+aplicacaoMB.getParametrosprodutos().getHighSchool()
    			+ " GROUP BY f.fornecedor.idfornecedor ORDER BY f.fornecedor.nome";
    	listaFornecedorCidade = fornecedorCidadeFacade.listar(sql);
    }
}
