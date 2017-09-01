package br.com.travelmate.managerBean.fornecedordocs;

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

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.FornecedorProdutoFacade;
import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.managerBean.financeiro.relatorios.RelatorioConciliacaoMB;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Fornecedorproduto;
import br.com.travelmate.model.Terceiros;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class RelatorioFornecedorDocsMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fornecedor fornecedor;
	private List<Fornecedor> listaFornecedor;
    private Date dataInicio;
    private Date dataTermino;
    private List<Fornecedorproduto> listaFornecedorProduto;
    
    @PostConstruct()
    public void init(){
        gerarListaFornecedor();
    }

    
    public Fornecedor getFornecedor() {
		return fornecedor;
	}


	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}


	public List<Fornecedor> getListaFornecedor() {
		return listaFornecedor;
	}


	public void setListaFornecedor(List<Fornecedor> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
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
    
    public List<Fornecedorproduto> getListaFornecedorProduto() {
		return listaFornecedorProduto;
	}


	public void setListaFornecedorProduto(List<Fornecedorproduto> listaFornecedorProduto) {
		this.listaFornecedorProduto = listaFornecedorProduto;
	}


	public void gerarListaFornecedor(){
		FornecedorFacade fornecedorFacade = new FornecedorFacade();
		listaFornecedor = fornecedorFacade.listar("Select f From Fornecedor f where f.idfornecedor>=1000");
		if (listaFornecedor == null) {
			listaFornecedor = new ArrayList<Fornecedor>();
		}
    }
    
    
    public String gerarSql(){
        String sql = "SELECT fornecedordocs.nome,fornecedordocs.idfornecedordocs, fornecedordocs.tipo, fornecedordocs.datavalidade, fornecedordocs.datainicio, cidade.nome as nomecidade, "+
						"pais.nome nomepais, fornecedor.nome as nomefornecedor, fornecedordocs.nome as nomearquivo FROM "+
						"fornecedordocs join fornecedorcidadedocs on fornecedordocs.idfornecedordocs = fornecedorcidadedocs.fornecedordocs_idfornecedordocs "+
						"join fornecedor on fornecedordocs.fornecedor_idfornecedor = fornecedor.idfornecedor "+
						"join fornecedorcidade on fornecedorcidadedocs.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade "+
						"join cidade on fornecedorcidade.cidade_idcidade = cidade.idcidade "+
						"join pais on cidade.pais_idpais = pais.idpais";
        if (fornecedor!=null){
            sql = sql + " where fornecedordocs.fornecedor_idfornecedor=" + fornecedor.getIdfornecedor();
        }else {
        	sql = sql + " where fornecedordocs.fornecedor_idfornecedor>=1000";
        }
        sql = sql + " order by fornecedordocs.nome, pais.nome, cidade.nome";
        return sql;
    }   
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/fornecedordocs/relatorioDocumentosFornecedor.jasper";  
        Map parameters = new HashMap();
        parameters.put("sql", gerarSql());
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "relatorioDocumentosFornecedor.pdf", null);
        } catch (JRException ex) {
            Logger.getLogger(RelatorioFornecedorDocsMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioFornecedorDocsMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }

}
