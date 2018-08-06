package br.com.travelmate.managerBean.financeiro.relatorios;


import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;

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
public class RelatorioVendasMB implements Serializable{
    
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
    
    @PostConstruct()
    public void init(){
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

	public void gerarListaUnidadeNegocio(){
        UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
        listaUnidadeNegocio = unidadeNegocioFacade.listar(true);
        if (listaUnidadeNegocio==null){
            listaUnidadeNegocio = new ArrayList<Unidadenegocio>();
        }
    }
    
    public void gerarListaProduto(){
        ProdutoFacade produtosFacade = new ProdutoFacade();
        listaProdutos = produtosFacade.listarProdutos(" ");
        if (listaProdutos == null) {
            listaProdutos = new ArrayList<Produtos>();
        }
    }
    
    public String gerarSql(){
        String sql = "SELECT distinct vendas.idvendas, vendas.dataVenda, produtos.descricao, unidadenegocio.nomefantasia, vendas.valor, "
                + "cliente.nome as nomecliente, vendascomissao.comissaotm, vendascomissao.taxatm, vendascomissao.descontotm, usuario.nome as usuarionome, "
                + " vendascomissao.comissaoemissor, vendascomissao.comissaogerente, vendascomissao.comissaofraquia,"
                + " vendascomissao.comissaoterceiros, vendascomissao.desagio, cambio.valor as cambio, fornecedor.nome as nomeFonecedor, moedas.sigla as sigla From "
            	+ " vendas join produtos on vendas.produtos_idprodutos = produtos.idprodutos"
                + " join unidadenegocio on vendas.unidadeNegocio_idunidadeNegocio = unidadenegocio.idunidadeNegocio"
                + " join cliente on vendas.cliente_idcliente = cliente.idcliente"
                + " join vendascomissao on vendas.idvendas = vendascomissao.vendas_idvendas "
                + " join usuario on vendas.usuario_idusuario = usuario.idusuario"
                + " join cambio on vendas.cambio_idcambio = cambio.idcambio"
                + " join moedas on cambio.moedas_idmoedas = moedas.idmoedas"
                + " join fornecedorcidade on vendas.fornecedorcidade_idfornecedorcidade = fornecedorcidade.idfornecedorcidade"
                + " join fornecedor on fornecedorcidade.fornecedor_idfornecedor = fornecedor.idfornecedor"
                + " where vendasMatriz='S' and vendas.dataVenda >='"  + Formatacao.ConvercaoDataSql(dataInicio) + " ' and vendas.dataVenda<='"
                        + Formatacao.ConvercaoDataSql(dataTermino) + "' ";
        if (unidadenegocio!=null){
            sql = sql + " and vendas.unidadeNegocio_idunidadeNegocio=" + unidadenegocio.getIdunidadeNegocio();
        }
        if (produtos!=null){
            sql = sql + " and vendas.produtos_idprodutos=" + unidadenegocio.getIdunidadeNegocio();
        }
        sql = sql + " order by vendas.dataVenda ";
        return sql;
    }
    
    
    
    public String gerarRelatorio() throws SQLException, IOException {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String caminhoRelatorio = "/reports/financeiro/vendas.jasper";  
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("sql", gerarSql());
        if(unidadenegocio!=null){
        	if(unidadenegocio.getIdunidadeNegocio()!=null){
        		parameters.put("unidade", unidadenegocio.getNomeFantasia());
        	}else{
        		parameters.put("unidade", "Todas as unidades");
        	}
        }else{
    		parameters.put("unidade", "Todas as unidades");
    	}
        File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
        BufferedImage logo = ImageIO.read(f);  
        parameters.put("logo", logo);
        String periodo= "";
        if ((dataInicio!=null) && (dataTermino!=null)){
                periodo = Formatacao.ConvercaoDataPadrao(dataInicio) 
                        + "    " + Formatacao.ConvercaoDataPadrao(dataTermino);
            }else periodo = "Produto : " + produtos;
        parameters.put("periodo", periodo);
        GerarRelatorio gerarRelatorio = new GerarRelatorio();
        try {
            gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters, "vendas.pdf", null);
        } catch (JRException ex) {
        	Mensagem.lancarMensagemErro("Erro Relatório", "Erro gerar relatório de Vendas " + ex.getMessage());
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Erro gerar relatório de Vendas."); 
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
