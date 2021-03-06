package br.com.travelmate.managerBean.financeiro.relatorios;

import br.com.travelmate.facade.TerceirosFacade;
import br.com.travelmate.model.Terceiros;
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
public class RelatorioComissaoTerceirosMB implements Serializable{
    
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Terceiros> listaTerceiros;
    private Terceiros terceiros;
    private Date dataInicio;
    private Date dataTermino;
    
    @PostConstruct()
    public void init(){
        gerarListaTerceiros();
    }

    public List<Terceiros> getListaTerceiros() {
        return listaTerceiros;
    }

    public void setListaTerceiros(List<Terceiros> listaTerceiros) {
        this.listaTerceiros = listaTerceiros;
    }

    public Terceiros getTerceiros() {
        return terceiros;
    }

    public void setTerceiros(Terceiros terceiros) {
        this.terceiros = terceiros;
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
    
    public void gerarListaTerceiros(){
        TerceirosFacade terceirosFacade = new TerceirosFacade();
        listaTerceiros = terceirosFacade.lista();
        if (listaTerceiros==null){
            listaTerceiros = new ArrayList<Terceiros>();
        }
    }
    
    
    public String gerarSql(){
        String sql = "SELECT distinct vendas.idvendas, vendas.dataVenda, produtos.descricao, unidadenegocio.nomefantasia, vendas.valor, "
                + "cliente.nome as nomecliente, vendascomissao.comissaoterceiros, terceiros.nome as nomeTerceiros From "
            	+ " vendas join produtos on vendas.produtos_idprodutos = produtos.idprodutos"
                + " join unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " join cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " join vendascomissao on vendas.idvendas = vendascomissao.vendas_idvendas "
                + " join usuario on vendas.usuario_idusuario = usuario.idusuario "
                + " join terceiros on vendascomissao.terceiros_idterceiros= terceiros.idterceiros "
                + " where   (vendas.situacao='FINALIZADA' OR vendas.situacao='ANDAMENTO') " + 
                " and vendas.dataVenda >='"  + Formatacao.ConvercaoDataSql(dataInicio) + " ' and vendas.dataVenda<='"
                        + Formatacao.ConvercaoDataSql(dataTermino) + "'";
        if (terceiros!=null){
            sql = sql + " and vendascomissao.terceiros_idterceiros=" + terceiros.getIdterceiros();
        }else {
        	sql = sql + " and vendascomissao.terceiros_idterceiros<>1 ";
        }
        sql = sql + " order by vendas.dataVenda ";
        return sql;
    }
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/financeiro/comissaoTerceiros.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql());
        if (terceiros==null){
        	parameters.put("terceiros", "TODOS");
        }else {
        	parameters.put("terceiros", terceiros.getNome());
        }
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        String periodo= "";
        if ((dataInicio!=null) && (dataTermino!=null)){
                periodo = Formatacao.ConvercaoDataPadrao(dataInicio) 
                        + "              " + Formatacao.ConvercaoDataPadrao(dataTermino);
        }
        parameters.put("periodo", periodo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "comissaoTerceiros.pdf", null);
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
