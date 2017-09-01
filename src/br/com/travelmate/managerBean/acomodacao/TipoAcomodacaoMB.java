package br.com.travelmate.managerBean.acomodacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.TipoAcomodacaoFacade;
import br.com.travelmate.model.Tipoacomodacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class TipoAcomodacaoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Tipoacomodacao> listaTipo;
	private Tipoacomodacao tipoacomodacao;
	
	@PostConstruct
	public void init() {
		tipoacomodacao = new Tipoacomodacao();
		gerarListaTipoAcomodacao();
	}

	public List<Tipoacomodacao> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<Tipoacomodacao> listaTipo) {
		this.listaTipo = listaTipo;
	}

	public Tipoacomodacao getTipoacomodacao() {
		return tipoacomodacao;
	}

	public void setTipoacomodacao(Tipoacomodacao tipoacomodacao) {
		this.tipoacomodacao = tipoacomodacao;
	}

	public void gerarListaTipoAcomodacao(){
		TipoAcomodacaoFacade tipoAcomodacaoFacade = new TipoAcomodacaoFacade();
		String sql = "select t from Tipoacomodacao t order by t.descricao";
		listaTipo = tipoAcomodacaoFacade.listar(sql);
		if(listaTipo==null){
			listaTipo = new ArrayList<Tipoacomodacao>();
		}
	}
	
	public void salvar(){
		if(tipoacomodacao.getDescricao()!=null && tipoacomodacao.getDescricao().length()>1){
			TipoAcomodacaoFacade tipoAcomodacaoFacade = new TipoAcomodacaoFacade();
			tipoacomodacao = tipoAcomodacaoFacade.salvar(tipoacomodacao);
			gerarListaTipoAcomodacao();
			Mensagem.lancarMensagemInfo("Salvo com sucesso.", "");
			tipoacomodacao = new Tipoacomodacao();
		}else{
			Mensagem.lancarMensagemErro("Atenção!", "Campo descrição em branco.");
		}
	}
	
	public String fechar(){
        RequestContext.getCurrentInstance().closeDialog(null);
        return "";
    }
	
	public void editar(Tipoacomodacao tipoacomodacao){
		this.tipoacomodacao = tipoacomodacao;
	}
	
	
	public void excluir(Tipoacomodacao tipoacomodacao){
		TipoAcomodacaoFacade tipoAcomodacaoFacade = new TipoAcomodacaoFacade();
		tipoAcomodacaoFacade.excluir(tipoacomodacao.getIdtipoacomodacao());
		gerarListaTipoAcomodacao();
		Mensagem.lancarMensagemInfo("Excluído com sucesso!", "");
		tipoacomodacao = new Tipoacomodacao();
	}
}
