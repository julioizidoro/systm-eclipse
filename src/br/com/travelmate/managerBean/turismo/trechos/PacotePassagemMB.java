/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.managerBean.turismo.trechos;
import br.com.travelmate.facade.PacotePassagemPassageiroFacade;
import br.com.travelmate.facade.PacotesPassagemFacade;
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Cidade;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Pacotepassagem;
import br.com.travelmate.model.Pacotepassagempassageiro;
import br.com.travelmate.model.Pacotetrecho;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Wolverine
 */
@Named
@ViewScoped
public class PacotePassagemMB implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private AplicacaoMB aplicacaoMB;
    private Pacotepassagem pacotepassagem;
    private Cambio cambio;
    private Fornecedorcidade fornecedorcidade;
    private List<Paisproduto> listaPais;
    private Cidade cidade;
    private List<Fornecedorcidade> listaFornecedorCidade;
    private Pais pais;
    private Pacotepassagempassageiro pacotepassagempassageiro;
    private List<Pacotepassagempassageiro> listaPassageiros;
    private float valorCambio=0;
    private String pacoteImportado;
    private float totalPagar=0;
    private float valorComissaoAdt;
    private float valorComissaoChd;
    private float valorComissaoInf;
    private float percentualComissaoAdt;
    private float percentualComissaoChd;
    private float percentualComissaoInf;

    @PostConstruct
    public void init() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Pacotetrecho pacotetrecho = (Pacotetrecho) session.getAttribute("pacoteTrecho");
        session.removeAttribute("pacoteTrecho");
        int idProduto = 0;
        if (pacotetrecho != null) {
            PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
            idProduto = pacotetrecho.getPacotes().getVendas().getProdutos().getIdprodutos();
            listaPais = paisProdutoFacade.listar(idProduto);
        }
        
        PacotesPassagemFacade pacotesPassagemFacade = new PacotesPassagemFacade();
        pacotepassagem = pacotesPassagemFacade.consultar(pacotetrecho.getIdpacotetrecho());
        if (pacotepassagem == null) {
            pacotepassagem = new Pacotepassagem();
            pacotepassagem.setPacotetrecho(pacotetrecho);
            fornecedorcidade = new Fornecedorcidade();
            pais = new Pais();
            cidade = new Cidade();
            pacotepassagempassageiro = new Pacotepassagempassageiro();
            listaPassageiros =  new ArrayList<Pacotepassagempassageiro>();
        } else {
            listaPassageiros = new ArrayList<Pacotepassagempassageiro>();
            fornecedorcidade = pacotepassagem.getFornecedorcidade();
            pais = fornecedorcidade.getCidade().getPais();
            cidade = fornecedorcidade.getCidade();
            listarFornecedorCidade(idProduto);
            iniciarListaPassageiros();
            pacotepassagempassageiro = new Pacotepassagempassageiro();
        }
        calcularValorTotal();
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public Pacotepassagem getPacotepassagem() {
        return pacotepassagem;
    }

    public void setPacotepassagem(Pacotepassagem pacotepassagem) {
        this.pacotepassagem = pacotepassagem;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Fornecedorcidade getFornecedorcidade() {
        return fornecedorcidade;
    }

    public void setFornecedorcidade(Fornecedorcidade fornecedorcidade) {
        this.fornecedorcidade = fornecedorcidade;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getPacoteImportado() {
		return pacoteImportado;
	}

	public void setPacoteImportado(String pacoteImportado) {
		this.pacoteImportado = pacoteImportado;
	}

	public List<Paisproduto> getListaPais() {
        return listaPais;
    }

    public void setListaPais(List<Paisproduto> listaPais) {
        this.listaPais = listaPais;
    }

    public List<Fornecedorcidade> getListaFornecedorCidade() {
        return listaFornecedorCidade;
    }

    public void setListaFornecedorCidade(List<Fornecedorcidade> listaFornecedorCidade) {
        this.listaFornecedorCidade = listaFornecedorCidade;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    
    public Pacotepassagempassageiro getPacotepassagempassageiro() {
		return pacotepassagempassageiro;
	}

	public void setPacotepassagempassageiro(Pacotepassagempassageiro pacotepassagempassageiro) {
		this.pacotepassagempassageiro = pacotepassagempassageiro;
	}

	public List<Pacotepassagempassageiro> getListaPassageiros() {
		return listaPassageiros;
	}

	public void setListaPassageiros(List<Pacotepassagempassageiro> listaPassageiros) {
		this.listaPassageiros = listaPassageiros;
	}

	public float getValorCambio() {
		return valorCambio;
	}

	public void setValorCambio(float valorCambio) {
		this.valorCambio = valorCambio;
	}

	public float getTotalPagar() {
		return totalPagar;
	}

	public float getValorComissaoAdt() {
		return valorComissaoAdt;
	}

	public void setValorComissaoAdt(float valorComissaoAdt) {
		this.valorComissaoAdt = valorComissaoAdt;
	}

	public float getValorComissaoChd() {
		return valorComissaoChd;
	}

	public void setValorComissaoChd(float valorComissaoChd) {
		this.valorComissaoChd = valorComissaoChd;
	}

	public float getValorComissaoInf() {
		return valorComissaoInf;
	}

	public void setValorComissaoInf(float valorComissaoInf) {
		this.valorComissaoInf = valorComissaoInf;
	}

	public void setTotalPagar(float totalPagar) {
		this.totalPagar = totalPagar;
	}

	public float getPercentualComissaoAdt() {
		return percentualComissaoAdt;
	}

	public void setPercentualComissaoAdt(float percentualComissaoAdt) {
		this.percentualComissaoAdt = percentualComissaoAdt;
	}

	public float getPercentualComissaoChd() {
		return percentualComissaoChd;
	}

	public void setPercentualComissaoChd(float percentualComissaoChd) {
		this.percentualComissaoChd = percentualComissaoChd;
	}

	public float getPercentualComissaoInf() {
		return percentualComissaoInf;
	}

	public void setPercentualComissaoInf(float percentualComissaoInf) {
		this.percentualComissaoInf = percentualComissaoInf;
	}

	public void listarFornecedorCidade(int idProduto){
        if (usuarioLogadoMB!=null){
            idProduto = aplicacaoMB.getParametrosprodutos().getPacotes();
        }
        if ((idProduto>0) && (cidade!=null)){
        	listaFornecedorCidade = GerarListas.listarFornecedorTurismo(idProduto, cidade.getIdcidade(), "Passagem");
        }
    }
    
    public void selecionarCliente(){
        RequestContext.getCurrentInstance().openDialog("consCliente");
    }
    
    public String adicionarPassageiroBean(){
    	if (pacotepassagempassageiro.getNome().length()>0 && pacotepassagempassageiro.getDatanascimento()!=null && pacotepassagempassageiro.getCategoria().length()>0){
            if(pacotepassagempassageiro.getCategoria().equalsIgnoreCase("ADT")){
            	pacotepassagempassageiro.setValor(pacotepassagem.getAdttarifa()+pacotepassagem.getAdttaxas()+pacotepassagem.getAdttaxaemissao());
            }else if(pacotepassagempassageiro.getCategoria().equalsIgnoreCase("CHD")){
            	pacotepassagempassageiro.setValor(pacotepassagem.getChdtarifa()+pacotepassagem.getChdtaxas()+pacotepassagem.getChdtaxaemissao());
            }else{
            	pacotepassagempassageiro.setValor(pacotepassagem.getInftarifa()+pacotepassagem.getInftaxas()+pacotepassagem.getInftaxaemissao());
            }
            listaPassageiros.add(pacotepassagempassageiro);
            pacotepassagempassageiro = new Pacotepassagempassageiro();
            calcularValorTotal();
        }else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Atenção!", "Preencha todos os dados do cliente."));
        }
        return "";
    }
    
    public String removerPassageiroBean(String linha, Pacotepassagempassageiro pacotepassagempassageiro){
		if(pacotepassagempassageiro.getIdpacotepassagempassageiro()!=null){
			PacotePassagemPassageiroFacade pacotePassagemPassageiroFacade =  new PacotePassagemPassageiroFacade();
			pacotePassagemPassageiroFacade.excluir(pacotepassagempassageiro.getIdpacotepassagempassageiro());
			iniciarListaPassageiros();
		}else{
	        int nlinha = Integer.parseInt(linha);
	        if (nlinha>=0){
	            listaPassageiros.remove(nlinha);
	        }
		}
        calcularValorTotal();
        return "";
    }
    
    
    public void iniciarListaPassageiros(){
    	PacotePassagemPassageiroFacade pacotePassagemPassageiroFacade =  new PacotePassagemPassageiroFacade();
    	String sql = "SELECT p From Pacotepassagempassageiro p where p.pacotepassagem.idpacotepassagem="+pacotepassagem.getIdpacotepassagem()+" order by p.nome";
   	 	listaPassageiros =  pacotePassagemPassageiroFacade.lista(sql);
        if(listaPassageiros==null){
       	 listaPassageiros = new ArrayList<Pacotepassagempassageiro>();
        }
        calcularValorTotal();
    }
    
    public String validarDados() {
    	String msg = "";
    	if (pais == null || pais.getIdpais() == null) {
			msg = msg + "Informe o pais; \n";
		}
    	if (cidade == null || cidade.getIdcidade() == null) {
			msg = msg + "Informe a cidade; \n";
		}
    	if (fornecedorcidade == null || fornecedorcidade.getIdfornecedorcidade() == null) {
			msg = msg + "Informe o parceiro; \n";
		}
    	return msg;
    }
    
    public String salvarPassagem(){
    	String msg = validarDados();
    	if (msg == null || msg.length() == 0) {
	        PacotesPassagemFacade pacotePassagemFacade = new PacotesPassagemFacade();
	        pacotepassagem.setFornecedorcidade(fornecedorcidade);
	        pacotepassagem = pacotePassagemFacade.salvar(pacotepassagem);
	        salvarPassageiro();
	        fornecedorcidade = new Fornecedorcidade();
	        FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("Salvo com Sucesso", ""));
	        RequestContext.getCurrentInstance().closeDialog(pacotepassagem);
		}else {
			Mensagem.lancarMensagemInfo(msg, "");
		}
        return "";
    }
    
    public void salvarPassageiro(){
    	 for (int i = 0; i < listaPassageiros.size(); i++) {
			 listaPassageiros.get(i).setPacotepassagem(pacotepassagem);
             PacotePassagemPassageiroFacade pacotePassagemPassageiroFacade = new PacotePassagemPassageiroFacade();
             pacotePassagemPassageiroFacade.salvar( listaPassageiros.get(i));
		 }
    }
    
    public String cancelar(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotepassagem.getPacotetrecho().getPacotes());
        if (pacotepassagem.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }else{
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }
    }
    
    public String excluir(){
        PacotesPassagemFacade pacotePassagemFacade = new PacotesPassagemFacade();
        pacotePassagemFacade.excluir(pacotepassagem.getIdpacotepassagem());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("Excluído com Sucesso", ""));
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);  
        session.setAttribute("pacote", pacotepassagem.getPacotetrecho().getPacotes());
        if (pacotepassagem.getPacotetrecho().getPacotes().getOperacao().equalsIgnoreCase("Operadora")){
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }else{
            RequestContext.getCurrentInstance().closeDialog(null);
            return "";
        }
    }
    
    public void calcularComissaoAdt(){
    	percentualComissaoAdt = percentualComissaoAdt /100;
        valorComissaoAdt = (float) (pacotepassagem.getAdttarifa() * percentualComissaoAdt);
    	pacotepassagem.setAdtcomissao(valorComissaoAdt);
    }
    
    public void calcularComissaoChd(){
    	percentualComissaoChd = percentualComissaoChd /100;
        valorComissaoChd = (float) (pacotepassagem.getChdtarifa() * percentualComissaoChd);
    	pacotepassagem.setChdcomissao(valorComissaoChd);
    }
    
    public void calcularComissaoInf(){
    	percentualComissaoInf = percentualComissaoInf /100;
        valorComissaoInf = (float) (pacotepassagem.getInftarifa() * percentualComissaoInf);
    	pacotepassagem.setInfcomissao(valorComissaoInf);
    }
   
    public void retornoDialogValorComissaoAdt(SelectEvent event){
    	Float valorComissao = (Float) event.getObject();
    	pacotepassagem.setAdtcomissao(valorComissao);
    }
    
    public void retornoDialogValorComissaoChd(SelectEvent event){
    	Float valorComissao = (Float) event.getObject();
    	pacotepassagem.setChdcomissao(valorComissao);
    }
    
    public void retornoDialogValorComissaoInf(SelectEvent event){
    	Float valorComissao = (Float) event.getObject();
    	pacotepassagem.setInfcomissao(valorComissao);
    }
   
    
    
    public void calcularValorTotal(){
    	totalPagar = 0.0f;
		for (int i = 0; i < listaPassageiros.size(); i++) {
			totalPagar = totalPagar + listaPassageiros.get(i).getValor();
		}
		
    }
    public String desabilitarPacoteImportado(){
    	String habilitado;
    	if(!pacoteImportado.equalsIgnoreCase("Sim")){
    		habilitado = "false";
    	}else{
    		habilitado = "true";
    	}
    	return habilitado;
    }
}
