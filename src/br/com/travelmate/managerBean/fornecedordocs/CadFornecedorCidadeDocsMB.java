package br.com.travelmate.managerBean.fornecedordocs;

import br.com.travelmate.facade.FornecedorCidadeDocsFacade;
import br.com.travelmate.facade.FornecedorCidadeFacade;
import br.com.travelmate.managerBean.AplicacaoMB; 
import br.com.travelmate.model.Fornecedorcidade;
import br.com.travelmate.model.Fornecedorcidadedocs;
import br.com.travelmate.model.Fornecedordocs;
import br.com.travelmate.model.Pais;
import br.com.travelmate.util.Mensagem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named
@ViewScoped
public class CadFornecedorCidadeDocsMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	private Fornecedordocs fornecedordocs;
	private List<Pais> listaPais;
	private List<Fornecedorcidade> listaCidade;
	private boolean todospais;
	private boolean todoscidade;

	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		fornecedordocs = (Fornecedordocs) session.getAttribute("fornecedordocs");
		session.removeAttribute("fornecedordocs");
		carregarListaPais();
	}

	public Fornecedordocs getFornecedordocs() {
		return fornecedordocs;
	}

	public void setFornecedordocs(Fornecedordocs fornecedordocs) {
		this.fornecedordocs = fornecedordocs;
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public List<Fornecedorcidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Fornecedorcidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public boolean isTodospais() {
		return todospais;
	}

	public void setTodospais(boolean todospais) {
		this.todospais = todospais;
	}

	public boolean isTodoscidade() {
		return todoscidade;
	}

	public void setTodoscidade(boolean todoscidade) {
		this.todoscidade = todoscidade;
	}

	public String cancelar() {
		return "fornecedorDocs";
	}

	public void carregarListaPais() {
		FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade(); 
		String sqlFornecedorCidade = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor="
				+fornecedordocs.getFornecedor().getIdfornecedor()+ " and f.ativo=1 Group By f.cidade.pais.idpais";
		List<Fornecedorcidade> listaCidades = fornecedorCidadeFacade.listar(sqlFornecedorCidade);
		listaPais = new ArrayList<>();
		for (int i = 0; i < listaCidades.size(); i++) {
			listaPais.add(listaCidades.get(i).getCidade().getPais());
		}   
	}
	
	public void selecionarTodosPais(){
		for (int i = 0; i < listaPais.size(); i++) {
			listaPais.get(i).setSelecionado(todospais);
		}
		carregarListaCidades(todospais);
	}
	
	public void carregarListaCidades(boolean selecionado){
		listaCidade = new ArrayList<>();
		if(selecionado){
			FornecedorCidadeFacade fornecedorCidadeFacade = new FornecedorCidadeFacade(); 
			String sqlFornecedorCidade = "select f from Fornecedorcidade f where f.fornecedor.idfornecedor="
					+fornecedordocs.getFornecedor().getIdfornecedor()+ " and f.ativo=1 and (";
			boolean passou=false;
			for (int i = 0; i < listaPais.size(); i++) {
				if(listaPais.get(i).isSelecionado()){
					if(passou){
						sqlFornecedorCidade = sqlFornecedorCidade+ "or (f.cidade.pais.idpais="+listaPais.get(i).getIdpais()+")";
					}else{
						sqlFornecedorCidade = sqlFornecedorCidade+ "(f.cidade.pais.idpais="+listaPais.get(i).getIdpais()+")";
						passou = true;
					}
				}  
			}
			sqlFornecedorCidade = sqlFornecedorCidade+ ") Group By f.cidade.idcidade";
			listaCidade = fornecedorCidadeFacade.listar(sqlFornecedorCidade); 
			verificarListaSelecionados();
		}
	}
	
	public void selecionarTodasCidades(){
		for (int i = 0; i < listaCidade.size(); i++) {
			if(listaCidade.get(i).isExcluir()){
				listaCidade.get(i).setSelecionado(todoscidade);
			}
		}
		verificarListaSelecionados();  
	}
	
	public void verificarListaSelecionados(){
		String sql = "select f From Fornecedorcidadedocs f where f.fornecedordocs.idfornecedordocs="
				+ fornecedordocs.getIdfornecedordocs();
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		List<Fornecedorcidadedocs> lista = fornecedorCidadeDocsFacade.listar(sql);
		if (lista != null && lista.size() > 0) {
			for (int i = 0; i < lista.size(); i++) {
				int idcidade = lista.get(i).getFornecedorcidade().getIdfornecedorcidade();
				for (int j = 0; j < listaCidade.size(); j++) {
					if(listaCidade.get(j).getIdfornecedorcidade()==idcidade){
						listaCidade.get(j).setFornecedorcidadedocs(lista.get(i));
						listaCidade.get(j).setExcluir(false);
					} 
				} 
			}
		}
	}
	
	public String salvar(){
		if(listaCidade!=null && listaCidade.size()>0){
			for (int i = 0; i < listaCidade.size(); i++) {
				if(listaCidade.get(i).isSelecionado()){
					Fornecedorcidadedocs fornecedorcidadedocs = new Fornecedorcidadedocs();
					fornecedorcidadedocs.setFornecedorcidade(listaCidade.get(i));
					fornecedorcidadedocs.setFornecedordocs(fornecedordocs);
					FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
					fornecedorCidadeDocsFacade.salvar(fornecedorcidadedocs);
				}
			}
			Mensagem.lancarMensagemInfo("Salvo com sucesso!", "");
			return "fornecedorDocs";
		}
		return "";
	}
	
	public void excluir(Fornecedorcidade fornecedorcidade){
		FornecedorCidadeDocsFacade fornecedorCidadeDocsFacade = new FornecedorCidadeDocsFacade();
		fornecedorCidadeDocsFacade.excluir(fornecedorcidade.getFornecedorcidadedocs().getIdfornecedorcidadedocs());
		fornecedorcidade.setExcluir(true);
		fornecedorcidade.setFornecedorcidadedocs(null); 
	}
	
	public boolean desabilitarSelecione(Fornecedorcidade fornecedorcidade){
		if(!fornecedorcidade.isExcluir()){
			return true;
		}else{
			return false;
		}
	}

}
