package br.com.travelmate.managerBean.financeiro.relatorios;


import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
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
public class RelatorioMapaVendasMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Unidadenegocio> listaUnidadeNegocio;
    private Unidadenegocio unidadenegocio;
    private Date dataInicio;
    private Date dataTermino;
    private List<Produtos> listaProdutos;
    private Produtos produtos;
    private List<Usuario> listaConsultor;
    private Usuario usuario;
    private String tipovenda;
    
    @PostConstruct()
    public void init(){
    	unidadenegocio = new Unidadenegocio();
    	produtos = new Produtos();
    	usuario = new Usuario();
        gerarListaUnidadeNegocio();
        gerarListaProduto();
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

    public List<Produtos> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }
    
    public List<Usuario> getListaConsultor() {
		return listaConsultor;
	}

	public void setListaConsultor(List<Usuario> listaConsultor) {
		this.listaConsultor = listaConsultor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTipovenda() {
		return tipovenda;
	}

	public void setTipovenda(String tipovenda) {
		this.tipovenda = tipovenda;
	}

	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
    
    public void gerarListaProduto(){
        ProdutoFacade produtosFacade = new ProdutoFacade();
        listaProdutos = produtosFacade.listarProdutos("");
        if (listaProdutos == null) {
            listaProdutos = new ArrayList<Produtos>();
        }
    }
    
    public String gerarSql(){
        String sql = "Select distinct vendas.idVendas, vendas.dataVenda, vendas.valor,produtos.descricao as descricaoProduto, cliente.nome as nomeCliente, usuario.nome as consultor,unidadenegocio.nomeFantasia, moedas.descricao as descricaoMoeda, moedas.sigla, orcamento.valorCambio,fornecedor.nome as nomeFornecedor, cambio.idcambio from"
                + " vendas join cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " join produtos on vendas.produtos_idprodutos = produtos.idprodutos"
                + " join usuario on vendas.usuario_idusuario = usuario.idusuario"
            	+ " join UnidadeNegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadeNegocio.idUnidadeNegocio"
                + " join fornecedor on vendas.fornecedor_idfornecedor = fornecedor.idfornecedor"
                + " join orcamento on vendas.idvendas = orcamento.vendas_idvendas"
                + " join cambio on orcamento.cambio_idcambio = cambio.idcambio"
                + " join moedas on cambio.moedas_idmoedas = moedas.idmoedas"
                + " where  (vendas.situacao='FINALIZADA' OR vendas.situacao='ANDAMENTO') and vendas.dataVenda >='"  + Formatacao.ConvercaoDataSql(dataInicio) + " ' and vendas.dataVenda<='"
                        + Formatacao.ConvercaoDataSql(dataTermino) + "'";
        if (unidadenegocio!=null && unidadenegocio.getIdunidadeNegocio()!=null){
            sql = sql + " and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        }
        if (produtos!=null && produtos.getIdprodutos()!=null){
            sql = sql + " and vendas.produtos_idprodutos=" + produtos.getIdprodutos();
        }
        if (usuario!=null && usuario.getIdusuario()!=null){
            sql = sql + " and vendas.usuario_idusuario=" + usuario.getIdusuario();
        }
        if (tipovenda.equalsIgnoreCase("Matriz")){
            sql = sql + " and vendas.vendasMatriz='S'";
        }else if(tipovenda.equalsIgnoreCase("Loja")){
        	sql = sql + " and vendas.vendasMatriz='N'";
        }
        sql = sql + " order by vendas.dataVenda ";
        return sql;
    }
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/mapavendas/mapaVendas.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql());
        parameters.put("dataInicial", dataInicio);
        parameters.put("dataFinal", dataTermino);
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "mapaVendas"+Formatacao.ConvercaoDataSql(new Date())+".pdf", null);
        } catch (JRException ex) {
            Logger.getLogger(RelatorioMapaVendasMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RelatorioMapaVendasMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
    
    public void gerarListaConsultor(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        listaConsultor = usuarioFacade.listar("select u from Usuario u where u.situacao='Ativo' and u.unidadenegocio.idunidadeNegocio="+unidadenegocio.getIdunidadeNegocio()+" order by u.nome");
        if (listaConsultor==null){
            listaConsultor = new ArrayList<Usuario>();
        }
    }
}
