/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.fornecedor;

import br.com.travelmate.bean.ListaTMStarBean;
import br.com.travelmate.bean.TMStarBean;
import br.com.travelmate.facade.CidadePaisProdutosFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cidadepaisproduto;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import net.sf.jasperreports.engine.JRException;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;


@Named
@ViewScoped
public class FornecedorMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioLogadoMB usuarioLogadoMB;
    private List<Paisproduto> listaPais;
    private Paisproduto pais;
    private Produtos produto;
    private List<Produtos> listaProdutos;
    private Cidadepaisproduto cidadeproduto;
    private List<Cidadepaisproduto> listaCidade;
    private Fornecedorcidade fornecedorCidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    
    @PostConstruct
    public void init() { 
        listaProdutos = GerarListas.listarProdutos("");
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }  

	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Cidadepaisproduto> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidadepaisproduto> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public void setPais(Paisproduto pais) {
		this.pais = pais;
	}

	public void setCidade(Cidadepaisproduto cidade) {
		this.cidadeproduto = cidade;
	}

	public Fornecedorcidade getFornecedorCidade() {
        return fornecedorCidade;
    }

    public void setFornecedorCidade(Fornecedorcidade fornecedorCidade) {
        this.fornecedorCidade = fornecedorCidade;
    } 

    public Produtos getProduto() {
        return produto;
    }

    public void setProduto(Produtos produto) {
        this.produto = produto;
    }

    public List<Produtos> getListaProdutos() {
        return listaProdutos;
    }

    public void setListaProdutos(List<Produtos> listaProdutos) {
        this.listaProdutos = listaProdutos;
    }
 
    public List<Fornecedorcidade> getListaFornecedorCidade() {
        return listaFornecedorCidade;
    }

    public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
        this.listaFornecedorCidade = listaFornecedorCidade;
    } 
    
    public Paisproduto getPais() {
		return pais;
	}

	public Cidadepaisproduto getCidade() {
		return cidadeproduto;
	}

	public String consFornecedor(){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        if(cidadeproduto!=null & produto!=null){
            session.setAttribute("produtos", produto);    
            session.setAttribute("cidade", cidadeproduto.getCidade()); 
            RequestContext.getCurrentInstance().openDialog("consFornecedores", options, null);
            return "";
        }else{
            FacesMessage mensagem = new FacesMessage("Atenção! ", "campos obrigatorios não preenchidos.");
            FacesContext.getCurrentInstance().addMessage(null, mensagem);
            return "";
        }
    }
    
    public String consPais(){
    		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		String voltar = "consultafornecedor";
		session.setAttribute("voltar", voltar);
        return "consPais";
    }
    
    public String cadFornecedorCidade(){
        return "cadFornecedorCidade";
    }
    
    public String cadFornecedorComissao(){ 
        return "cadFornecedorComissao";
    } 
    
    public void retornoDialogNovo(SelectEvent event){
       List<Fornecedorcidade> listaFornecedorCidade = (List<Fornecedorcidade>) event.getObject();
       for (int i = 0; i < listaFornecedorCidade.size(); i++) {
           this.listaFornecedorCidade.add(listaFornecedorCidade.get(i));
       }
   }
    
    public String cadFornecedorCidadeIdiomaProduto(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 800);
    	RequestContext.getCurrentInstance().openDialog("fornecedorCidadeIdiomaProduto", options, null);
    	return "";
    }
    
    
    public String editarFornecedorCidade(Fornecedorcidade fornecedorcidade){
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 500);
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("fornecedorcidade", fornecedorcidade);    
        RequestContext.getCurrentInstance().openDialog("editarFornecedorCidade", options, null);
        return "";
    } 
    
    public void gerarRelatorio() throws IOException, SQLException{
    	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = ("/reports/fornecedor/reporttarifario.jasper");
		Map parameters = new HashMap(); 
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo); 
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
					"Fornecedores-Cadastrados.pdf", null);
		} catch (JRException ex1) {
			Logger.getLogger(FornecedorMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(FornecedorMB.class.getName()).log(Level.SEVERE, null, ex);
		}
    }
    
    public String escolasVendidas(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 320);
    	RequestContext.getCurrentInstance().openDialog("escolasVendidas", options, null);
    	return "";
    }
    
    public String paisesVendidos(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 320);  
    	RequestContext.getCurrentInstance().openDialog("paisesVendidos", options, null);
    	return "";
    }
    
    public String percentualFornecedor(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 320);
    	RequestContext.getCurrentInstance().openDialog("percentualFornecedor", options, null);
    	return "";
    }
    
    public List<TMStarBean> retornarListaImagem(Fornecedorcidade fornecedorcidade){
    	ListaTMStarBean lista = new ListaTMStarBean();
    	lista.setLista(new ArrayList<TMStarBean>());
    	if(fornecedorcidade!=null){ 
			int numeroCinza = 5-fornecedorcidade.getNumestrelas();
			TMStarBean tmStarBean; 
	    	if(fornecedorcidade.getNumestrelas()>0){ 
	    		for (int i = 0; i < fornecedorcidade.getNumestrelas(); i++) {
	    			tmStarBean = new TMStarBean();
	    			if(!fornecedorcidade.isToptmstar()){ 
	    				tmStarBean.setImagem("../../resources/img/imgtmstar.png"); 
	    			}else{
	    				tmStarBean.setImagem("../../resources/img/imgtoptmstar.png");
	    			}
	    			lista.getLista().add(tmStarBean);
				} 
	    	}
	    	if(numeroCinza>0){
    			for (int i = 0; i < numeroCinza; i++) {
	    			tmStarBean = new TMStarBean();   
	    			tmStarBean.setImagem("../../resources/img/imgtmstarcinza.png");  
	    			lista.getLista().add(tmStarBean);
				}
    		}
		}
    	return lista.getLista();
    }
    
    public void salvarAtivo(Fornecedorcidade fornecedorcidade, int posicao) {
    	FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
    	fornecedorcidade = fornecedorCidadeFacade.salvar(fornecedorcidade);
    	listaFornecedorCidade.set(posicao, fornecedorcidade);
    }
    
    public void listarFornecedorCidade(){
        if (produto!=null && cidadeproduto!=null){
            listaFornecedorCidade = GerarListas.listarFornecedorCidade(produto.getIdprodutos(), cidadeproduto.getCidade().getIdcidade());
        }
    }
    
    public void listarPaises() {
    	if (produto!=null) {
	    	PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
			listaPais = paisProdutoFacade.listar(produto.getIdprodutos());
    	}
    }
    
    public void listarCidade() {
    	if (produto!=null && pais!=null) {
	    	CidadePaisProdutosFacade cidadePaisProdutosFacade = new CidadePaisProdutosFacade();
	    	listaCidade = cidadePaisProdutosFacade.listar("SELECT c FROM Cidadepaisproduto c WHERE c.paisproduto.idpaisproduto="
	    			+ pais.getIdpaisproduto());
    	}
    }
    
    public String cadFornecedorCidadeIdioma(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("contentWidth", 800);
    	RequestContext.getCurrentInstance().openDialog("cadFornecedorCidadeIdioma", options, null);
    	return "";
    }
    
    public boolean habilitarBtnNovo() {
    	if(cidadeproduto!=null && cidadeproduto.getIdcidadepaisproduto()!=null) {
    		return false;
    	}else return true;
    }
     
}
