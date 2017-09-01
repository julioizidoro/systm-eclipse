package br.com.travelmate.managerBean.acomodacao;

import java.io.Serializable;
import java.util.ArrayList;  
import java.util.List; 

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession; 

import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.ExtrasAcomodacaoFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade; 
import br.com.travelmate.facade.PaisProdutoFacade;
import br.com.travelmate.facade.ProdutoOrcamentoFacade;
import br.com.travelmate.facade.TipoAcomodacaoFacade;
import br.com.travelmate.facade.ValoresAcomodacaoFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.model.Cidade; 
import br.com.travelmate.model.Extrasacamodacao;
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Moedas;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;
import br.com.travelmate.model.Produtosorcamento;
import br.com.travelmate.model.Tipoacomodacao;
import br.com.travelmate.model.Valoresacomodacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadValoresAcomodacaoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB; 
	private List<Paisproduto> listaPais;
	private Pais pais;
	private Cidade cidade; 
	private List<Fornecedorcidade> listaFornecedor; 
	private List<Extrasacamodacao> listaExtras;
	private Valoresacomodacao valoresacomodacao;
	private Extrasacamodacao extras;
	private List<Produtosorcamento> listaProdutos;
	private List<Moedas> listaMoedas;
	private List<Tipoacomodacao> listaTipoAcomodacao;
	
	@PostConstruct
	public void init() { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		valoresacomodacao = (Valoresacomodacao) session.getAttribute("valoresacomodacao");
		session.removeAttribute("valoresacomodacao");
		PaisProdutoFacade paisProdutoFacade = new PaisProdutoFacade();
		int idProduto = aplicacaoMB.getParametrosprodutos().getCursos();
		listaPais = paisProdutoFacade.listar(idProduto);
		cidade = new Cidade(); 
		gerarListaProdutosOrcamento();
		extras = new Extrasacamodacao();
		CambioFacade cambioFacade = new CambioFacade();
        listaMoedas = cambioFacade.listaMoedas();
        gerarListaTipoAcomodacao();
		if(valoresacomodacao==null){
			valoresacomodacao = new Valoresacomodacao();
		}else{
			pais = valoresacomodacao.getFornecedorcidade().getCidade().getPais();
			cidade = valoresacomodacao.getFornecedorcidade().getCidade();
			gerarListaExtras();
			gerarListaFornecedor();
		}
	}
 
	public List<Paisproduto> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Paisproduto> listaPais) {
		this.listaPais = listaPais;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
  
	public List<Fornecedorcidade> getListaFornecedor() {
		return listaFornecedor;
	}

	public void setListaFornecedor(List<Fornecedorcidade> listaFornecedor) {
		this.listaFornecedor = listaFornecedor;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}  

	public List<Extrasacamodacao> getListaExtras() {
		return listaExtras;
	}

	public void setListaExtras(List<Extrasacamodacao> listaExtras) {
		this.listaExtras = listaExtras;
	}

	public Valoresacomodacao getValoresacomodacao() {
		return valoresacomodacao;
	}

	public void setValoresacomodacao(Valoresacomodacao valoresacomodacao) {
		this.valoresacomodacao = valoresacomodacao;
	}

	public Extrasacamodacao getExtras() {
		return extras;
	}

	public void setExtras(Extrasacamodacao extras) {
		this.extras = extras;
	}

	public List<Produtosorcamento> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produtosorcamento> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Moedas> getListaMoedas() {
		return listaMoedas;
	}

	public void setListaMoedas(List<Moedas> listaMoedas) {
		this.listaMoedas = listaMoedas;
	}

	public List<Tipoacomodacao> getListaTipoAcomodacao() {
		return listaTipoAcomodacao;
	}

	public void setListaTipoAcomodacao(List<Tipoacomodacao> listaTipoAcomodacao) {
		this.listaTipoAcomodacao = listaTipoAcomodacao;
	} 

	public void gerarListaFornecedor() {
		String sql="";
		if (cidade != null && cidade.getIdcidade() != null) {
			sql = "select f from Fornecedorcidade f where f.cidade.idcidade="
					+ cidade.getIdcidade() + " and f.ativo=1  order by f.fornecedor.nome"; 
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade();
			listaFornecedor = fornecedorCidadeFacade.listar(sql);
			if (listaFornecedor == null) {
				listaFornecedor = new ArrayList<Fornecedorcidade>();
			} 
		}  
	} 
	 
	public void excluir(Extrasacamodacao extrasacamodacao) {
		if(extrasacamodacao.getIdextrasacamodacao()!=null){
			for (int i = 0; i < listaExtras.size(); i++) {
				ExtrasAcomodacaoFacade extrasAcomodacaoFacade = new ExtrasAcomodacaoFacade();
				extrasAcomodacaoFacade.excluir(listaExtras.get(i).getIdextrasacamodacao());
			}
		}
		listaExtras.remove(extrasacamodacao);
		Mensagem.lancarMensagemInfo("", "Excluído com sucesso."); 
	}
	
	public void gerarListaExtras(){
		ExtrasAcomodacaoFacade extrasAcomodacaoFacade = new ExtrasAcomodacaoFacade();
		String sql = "select e from Extrasacomodacao e where valoresacomodacao.idvaloresacomodacao="+valoresacomodacao.getIdvaloresacomodacao();
		listaExtras = extrasAcomodacaoFacade.listar(sql);
	}
	
	public void gerarListaProdutosOrcamento() {
		ProdutoOrcamentoFacade produtoOrcamentoFacade = new ProdutoOrcamentoFacade();
		listaProdutos = produtoOrcamentoFacade.listarProdutosOrcamentoSql(
				"select p from Produtosorcamento p where p.tipoproduto='C' order by p.descricao");
		if (listaProdutos == null) {
			listaProdutos = new ArrayList<Produtosorcamento>();
		}
	}
	
	public void adicionarExtras(){
		if(validarExtras()){
			listaExtras.add(extras);
			extras = new Extrasacamodacao();
		}
	}
	
	public boolean validarExtras(){
		if(extras.getProdutosorcamento()==null || extras.getProdutosorcamento().getIdprodutosOrcamento()==null){
			Mensagem.lancarMensagemErro("Atenção", "Produto não informado");
			return false;
		}
		if(extras.getFormapagamento()==null || extras.getFormapagamento().length()==0){
			Mensagem.lancarMensagemErro("Atenção", "Forma de pagamento não informado");
			return false;
		}
		if(extras.getValor()==null || extras.getValor()==0){
			Mensagem.lancarMensagemErro("Atenção", "Valor não informado");
			return false;
		}
		return true;
	}
	 
	public String fechar() { 
		return "consValoresAcomodacao";
	}

	public String salvar(){
		if(validarDados()){
			ValoresAcomodacaoFacade valoresAcomodacaoFacade = new ValoresAcomodacaoFacade();
			valoresacomodacao = valoresAcomodacaoFacade.salvar(valoresacomodacao);
			if(listaExtras!=null){
				ExtrasAcomodacaoFacade extrasAcomodacaoFacade = new ExtrasAcomodacaoFacade();
				for (int i = 0; i < listaExtras.size(); i++) {
					extrasAcomodacaoFacade.salvar(listaExtras.get(i));
				}
			}
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			return "consValoresAcomodacao";
		}
		return "";
	}
	
	public boolean validarDados(){
		if(valoresacomodacao.getFornecedorcidade()==null || valoresacomodacao.getFornecedorcidade().getIdfornecedorcidade()==null){
			Mensagem.lancarMensagemErro("Atenção!", "Fornecedor não selecionado.");
			return false;
		}
		if(valoresacomodacao.getTipoacomodacao()==null || valoresacomodacao.getTipoacomodacao().getIdtipoacomodacao()==null){
			Mensagem.lancarMensagemErro("Atenção!", "Tipo de acomodação não selecionada.");
			return false;
		}
		if(valoresacomodacao.getMoedas()==null || valoresacomodacao.getMoedas().getIdmoedas()==null){
			Mensagem.lancarMensagemErro("Atenção!", "Moeda não selecionada.");
			return false;
		}
		if(valoresacomodacao.getResidencia()==null || valoresacomodacao.getResidencia().length()==0){
			Mensagem.lancarMensagemErro("Atenção!", "Residência não informada.");
			return false;
		}
		if(valoresacomodacao.getDatavalidadeinicial()==null || valoresacomodacao.getDatavalidadefinal()==null){
			Mensagem.lancarMensagemErro("Atenção!", "Data validade não informada.");
			return false;
		}
		if(valoresacomodacao.getNumerosemanainicial()==null || valoresacomodacao.getNumerosemanainicial()==0
			|| valoresacomodacao.getNumerosemanafinal()==null || valoresacomodacao.getNumerosemanafinal()==0){
			Mensagem.lancarMensagemErro("Atenção!", "Nº de semanas não informado.");
			return false;
		}
		if(valoresacomodacao.getValor()==null || valoresacomodacao.getValor()==0){
			Mensagem.lancarMensagemErro("Atenção!", "Valor não informado.");
			return false;
		}
		if(valoresacomodacao.getValortm()==null || valoresacomodacao.getValortm()==0){
			Mensagem.lancarMensagemErro("Atenção!", "Valor TravalMate não informado.");
			return false;
		}
		if(valoresacomodacao.getValorfranquia()==null || valoresacomodacao.getValorfranquia()==0){
			Mensagem.lancarMensagemErro("Atenção!", "Valor Franquia não informado.");
			return false;
		}
		if(valoresacomodacao.getValorparceiro()==null || valoresacomodacao.getValorparceiro()==0){
			Mensagem.lancarMensagemErro("Atenção!", "Valor parceiro não informado.");
			return false;
		}
		if(valoresacomodacao.getTaxamatricula()==null || valoresacomodacao.getTaxamatricula()==0){
			Mensagem.lancarMensagemErro("Atenção!", "Taxa de matrícula não informado.");
			return false;
		}
		return true;
	}
	
	public void gerarListaTipoAcomodacao(){
		TipoAcomodacaoFacade tipoAcomodacaoFacade = new TipoAcomodacaoFacade();
		String sql = "select t from Tipoacomodacao t order by t.descricao";
		listaTipoAcomodacao = tipoAcomodacaoFacade.listar(sql);
	}
}
