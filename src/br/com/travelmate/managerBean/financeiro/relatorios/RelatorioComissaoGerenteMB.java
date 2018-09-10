package br.com.travelmate.managerBean.financeiro.relatorios;



import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Usuario;
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
public class RelatorioComissaoGerenteMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Usuario> listaGerente;
    private Usuario usuario;
    private Date dataInicio;
    private Date dataTermino;
    
    @PostConstruct()
    public void init(){
        gerarListaConsultor();
    }

    public List<Usuario> getListaGerente() {
        return listaGerente;
    }

    public void setListaGerente(List<Usuario> listaGerente) {
        this.listaGerente = listaGerente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
    
    public void gerarListaConsultor(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        listaGerente = usuarioFacade.listar("select u from Usuario u where u.tipo='Gerencial' order by u.nome");
        if (listaGerente==null){
            listaGerente = new ArrayList<Usuario>();
        }
    }
    
    public String gerarSql(){
        String sql = "SELECT distinct vendas.idvendas, vendas.dataVenda, produtos.descricao, unidadenegocio.nomefantasia, vendas.valor, "
                + "cliente.nome as nomecliente, vendascomissao.comissaogerente From "
            	+ " vendas join produtos on vendas.produtos_idprodutos = produtos.idprodutos"
                + " join unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " join cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " join vendascomissao on vendas.idvendas = vendascomissao.vendas_idvendas "
                + " join usuario on vendas.usuario_idusuario = usuario.idusuario"
                + " where  (vendas.situacao='FINALIZADA' OR vendas.situacao='ANDAMENTO') and vendas.dataVenda >='"  + Formatacao.ConvercaoDataSql(dataInicio) + " ' and vendas.dataVenda<='"
                        + Formatacao.ConvercaoDataSql(dataTermino) + "'";
        if (usuario!=null){
            sql = sql + " and produtos.idgerente=" + usuario.getIdusuario();
        }
        sql = sql + " order by vendas.dataVenda ";
        return sql;
    }
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/financeiro/comissaoGerente.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql());
        parameters.put("gerente", usuario.getNome());
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        String periodo= "";
        if ((dataInicio!=null) && (dataTermino!=null)){
                periodo = Formatacao.ConvercaoDataPadrao(dataInicio) 
                        + "         " + Formatacao.ConvercaoDataPadrao(dataTermino);
        }
        parameters.put("periodo", periodo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "comissaogerente.pdf", null);
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
