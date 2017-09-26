package br.com.travelmate.managerBean.financeiro.relatorios;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FornecedorFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Fornecedor;
import br.com.travelmate.model.Pais;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ComissaoParceirosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pais pais;
	private List<Pais> listaPais;
	private Fornecedor fornecedor;
    private List<Fornecedor> listaFornecedor;
	
	@PostConstruct()
    public void init(){
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listar();
		gerarListaFornecedor();
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
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
	
	public void gerarListaFornecedor(){
        FornecedorFacade forncedorFacade = new FornecedorFacade();
        listaFornecedor = forncedorFacade.listar("SELECT f From Fornecedor f where f.idfornecedor>1000 order by f.nome");
        if(listaFornecedor==null){
            listaFornecedor = new ArrayList<Fornecedor>();
        }
    }
	
	
	public String gerarSql(){
        String sql = "SELECT distinct fornecedor.nome as escola, pais.nome as pais, fornecedorcomissaocursoproduto.premium, "
        			+ "fornecedorcomissaocursoproduto.tipocomissao, fornecedorcomissaocursoproduto.express, pais.idpais, fornecedor.idfornecedor,produtosorcamento.descricao"
        			+" From"
        		    +" fornecedorcomissaocursoproduto join fornecedorcomissaocurso on fornecedorcomissaocursoproduto.fornecedorcomissaocurso_idfornecedorcomissao = fornecedorcomissaocurso.idfornecedorcomissao"
        			+" join fornecedor on fornecedorcomissaocurso.fornecedor_idfornecedor = fornecedor.idfornecedor"
        			+" join pais on fornecedorcomissaocurso.pais_idpais = pais.idpais"
        			+" join produtosorcamento on fornecedorcomissaocursoproduto.produtosorcamento_idprodutosorcamento=produtosorcamento.idprodutosorcamento"
        			+" where fornecedor.idfornecedor>1000";
        if (pais!=null){
        	if(pais.getIdpais()!=null){
        		sql = sql + " and pais.idpais=" + pais.getIdpais();
        	}
        }
        if (fornecedor!=null){
        	if(fornecedor.getIdfornecedor()!=null){
        		sql = sql + " and fornecedor.idfornecedor=" + fornecedor.getIdfornecedor();
        	}
        }
        sql = sql + " order by pais, escola";
        return sql;
    }
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/relatorios/comissaoParceiros.jasper";  
        Map<String, Object> parameters = new HashMap();
        parameters.put("sql", gerarSql());
        if(pais!=null){
        	parameters.put("pais", "Pais: "+ pais.getNome());
        }else{
        	parameters.put("pais", " ");
        }
        if(fornecedor!=null){
        	parameters.put("escola", "Escola: "+ fornecedor.getNome());
        }else{
        	parameters.put("escola", " ");
        }
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "ComissaoParceiros.pdf", null);
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
