package br.com.travelmate.managerBean.coprodutos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.FornecedorCidadeIdiomaAcomodacaoFacade;
import br.com.travelmate.model.Fornecedorcidadeidioma;
import br.com.travelmate.model.Fornecedorcidadeidiomaacomodacao;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class CadFornecedorCidadeIdiomaAcomodacaoMB implements Serializable {
 
	private static final long serialVersionUID = 1L;
	private Fornecedorcidadeidiomaacomodacao fornecedorcidadeidiomaacomodacao;
	private Fornecedorcidadeidioma fornecedorcidadeidioma;
	private Integer diasemanaentrada;
	private Integer diasemanasaida;
	private List<Fornecedorcidadeidiomaacomodacao> listaAcomodacao;
	private List<String> tiposAcomodacao;
	private String tipoacomodacao;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedorcidadeidioma = (Fornecedorcidadeidioma) session.getAttribute("fornecedorcidadeidioma");
		session.removeAttribute("fornecedorcidadeidioma"); 
		fornecedorcidadeidiomaacomodacao = new Fornecedorcidadeidiomaacomodacao(); 
		listarAcomodacao();
		gerarDadosAcomodacoes();
		diasemanaentrada=20;
		diasemanasaida=20;
	}

	public Fornecedorcidadeidiomaacomodacao getFornecedorcidadeidiomaacomodacao() {
		return fornecedorcidadeidiomaacomodacao;
	}

	public void setFornecedorcidadeidiomaacomodacao(Fornecedorcidadeidiomaacomodacao fornecedorcidadeidiomaacomodacao) {
		this.fornecedorcidadeidiomaacomodacao = fornecedorcidadeidiomaacomodacao;
	}

	public Fornecedorcidadeidioma getFornecedorcidadeidioma() {
		return fornecedorcidadeidioma;
	}

	public void setFornecedorcidadeidioma(Fornecedorcidadeidioma fornecedorcidadeidioma) {
		this.fornecedorcidadeidioma = fornecedorcidadeidioma;
	}
  
	
	public Integer getDiasemanaentrada() {
		return diasemanaentrada;
	}

	public void setDiasemanaentrada(Integer diasemanaentrada) {
		this.diasemanaentrada = diasemanaentrada;
	}

	public Integer getDiasemanasaida() {
		return diasemanasaida;
	}

	public void setDiasemanasaida(Integer diasemanasaida) {
		this.diasemanasaida = diasemanasaida;
	}

	public List<Fornecedorcidadeidiomaacomodacao> getListaAcomodacao() {
		return listaAcomodacao;
	}

	public void setListaAcomodacao(List<Fornecedorcidadeidiomaacomodacao> listaAcomodacao) {
		this.listaAcomodacao = listaAcomodacao;
	}

	public List<String> getTiposAcomodacao() {
		return tiposAcomodacao;
	}

	public void setTiposAcomodacao(List<String> tiposAcomodacao) {
		this.tiposAcomodacao = tiposAcomodacao;
	}

	public String getTipoacomodacao() {
		return tipoacomodacao;
	}

	public void setTipoacomodacao(String tipoacomodacao) {
		this.tipoacomodacao = tipoacomodacao;
	}

	public String salvar() {
		if (diasemanaentrada != null) { 
			if (diasemanasaida != null) {
				FornecedorCidadeIdiomaAcomodacaoFacade acomodacaoFacade = new FornecedorCidadeIdiomaAcomodacaoFacade();
				if(tipoacomodacao.equalsIgnoreCase("Todos")){
					for (int j = 0; j < tiposAcomodacao.size(); j++) { 
						fornecedorcidadeidiomaacomodacao = new Fornecedorcidadeidiomaacomodacao();
						fornecedorcidadeidiomaacomodacao.setTipoacomodacao(tiposAcomodacao.get(j)); 
						fornecedorcidadeidiomaacomodacao.setDiaentrada(diasemanaentrada);
						fornecedorcidadeidiomaacomodacao.setDiasaida(diasemanasaida); 
						fornecedorcidadeidiomaacomodacao.setFornecedorcidadeidioma(fornecedorcidadeidioma); 
						fornecedorcidadeidiomaacomodacao = acomodacaoFacade.salvar(fornecedorcidadeidiomaacomodacao); 
					}
				}else{ 
					fornecedorcidadeidiomaacomodacao = new Fornecedorcidadeidiomaacomodacao();
					fornecedorcidadeidiomaacomodacao.setTipoacomodacao(tipoacomodacao); 
					fornecedorcidadeidiomaacomodacao.setDiaentrada(diasemanaentrada);
					fornecedorcidadeidiomaacomodacao.setDiasaida(diasemanasaida);  
					fornecedorcidadeidiomaacomodacao.setFornecedorcidadeidioma(fornecedorcidadeidioma); 
					fornecedorcidadeidiomaacomodacao = acomodacaoFacade.salvar(fornecedorcidadeidiomaacomodacao); 
				}  
				diasemanaentrada=20;
				diasemanasaida=20;
				tipoacomodacao = "Todos";
				Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
				listarAcomodacao();
				return "";
			} else {
				Mensagem.lancarMensagemErro("Atenção!", "Campo de saída não preenchido.");
			}
		} else {
			Mensagem.lancarMensagemErro("Atenção!", "Campo de chegada não preenchido.");
		}
		return "";
	}

	public String cancelar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	
	public void listarAcomodacao(){
		FornecedorCidadeIdiomaAcomodacaoFacade acomodacaoFacade = new FornecedorCidadeIdiomaAcomodacaoFacade();
		String sql = "Select f From Fornecedorcidadeidiomaacomodacao f where"
				+ " f.fornecedorcidadeidioma.idfornecedorcidadeidioma="
				+ fornecedorcidadeidioma.getIdfornecedorcidadeidioma();
		listaAcomodacao = acomodacaoFacade.listar(sql); 
	}
	
	public void gerarDadosAcomodacoes(){
		tiposAcomodacao = new ArrayList<>();
		tiposAcomodacao.add("Alojamento");
		tiposAcomodacao.add("Casa de família");
		tiposAcomodacao.add("Apartamento");
		tiposAcomodacao.add("Flat");
		tiposAcomodacao.add("Hotel");
		tiposAcomodacao.add("Hostel");
		tiposAcomodacao.add("Residência");
		tiposAcomodacao.add("Studio");
	} 
	
	public String retornarDiaSemana(int dia){ 
		return Formatacao.getSemana(dia);
	}
	
	public void excluir(Fornecedorcidadeidiomaacomodacao fornecedorcidadeidiomaacomodacao){
		FornecedorCidadeIdiomaAcomodacaoFacade acomodacaoFacade = new FornecedorCidadeIdiomaAcomodacaoFacade();
		acomodacaoFacade.excluir(fornecedorcidadeidiomaacomodacao.getIdfornecedorcidadeidiomaacomodacao());
		listarAcomodacao();
	}
}
