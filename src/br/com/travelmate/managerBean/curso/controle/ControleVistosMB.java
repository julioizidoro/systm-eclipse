package br.com.travelmate.managerBean.curso.controle;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.facade.ArquivosFacade;
import br.com.travelmate.facade.CursoFacade;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Controlecurso;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ControleVistosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controlecurso controlecurso;
	private List<ControleVistosBean> listaControleVistoBean;
	private int nFichas;
	
	
	
	@PostConstruct
	public void init(){
		gerarListaAustralia();
		gerarListaNovaZelandia();
		gerarListaEUA();
		gerarListaCanada();
		gerarListaInglaterra();
		gerarListaEmiradosArabesUnidos();
		nFichas = listaControleVistoBean.size();
	}



	public Controlecurso getControlecurso() {
		return controlecurso;
	}



	public void setControlecurso(Controlecurso controlecurso) {
		this.controlecurso = controlecurso;
	}



	public List<ControleVistosBean> getListaControleVistoBean() {
		return listaControleVistoBean;
	}



	public void setListaControleVistoBean(List<ControleVistosBean> listaControleVistoBean) {
		this.listaControleVistoBean = listaControleVistoBean;
	}
	
	
	
	public int getnFichas() {
		return nFichas;
	}



	public void setnFichas(int nFichas) {
		this.nFichas = nFichas;
	}

	
	


	public void gerarListaAustralia(){
		CursoFacade cursoFacade = new CursoFacade();
		ControleVistosBean controleVistosBean = new ControleVistosBean();
		List<Controlecurso> listaAustralia = cursoFacade.listaControle(" Select c From Controlecurso c Where c.vendas.cliente.tipovisto='Estudante' and c.curso.numeroSenamas + c.curso.sNumeroSemanas>=14 "
				+ "and c.vendas.fornecedorcidade.cidade.pais.idpais=4 and c.situacao<>'Finalizado' and c.situacao<>'FINALIZADA' "
				+ " and c.situacao<>'Cancelado' and c.vendas.dataVenda>='2017-03-20'");
		
		if (listaAustralia == null) {
			listaAustralia = new ArrayList<>();
		}
		    
		if (listaControleVistoBean == null) {
			listaControleVistoBean = new ArrayList<>();
		}
		
		for (int i = 0; i < listaAustralia.size(); i++) {
			controleVistosBean = new ControleVistosBean();
			controleVistosBean.setDataEmbarque(listaAustralia.get(i).getDataEmbarque());
			controleVistosBean.setDataInicio(listaAustralia.get(i).getCurso().getDataInicio());
			controleVistosBean.setEscola(listaAustralia.get(i).getVendas().getFornecedor().getNome());
			controleVistosBean.setNomeCliente(listaAustralia.get(i).getVendas().getCliente().getNome());
			controleVistosBean.setControlecurso(listaAustralia.get(i));
			controleVistosBean.setSituacao(listaAustralia.get(i).getSituacao());
			controleVistosBean.setPais(listaAustralia.get(i).getVendas().getFornecedorcidade().getCidade().getPais());
			controleVistosBean.setUnidade(listaAustralia.get(i).getVendas().getUnidadenegocio());
			if (listaAustralia.get(i).isUrgenciavisto()) {
				controleVistosBean.setCor("color:#FF0000;");
			}
			listaControleVistoBean.add(controleVistosBean);
		}
		
	}
	
	
	public void gerarListaNovaZelandia(){
		CursoFacade cursoFacade = new CursoFacade();
		ControleVistosBean controleVistosBean = new ControleVistosBean();
		List<Controlecurso> listaNovazelandia = cursoFacade.listaControle(" Select c From Controlecurso c Where c.vendas.cliente.tipovisto='Estudante' and c.curso.numeroSenamas + c.curso.sNumeroSemanas>=14 "
				+ "and c.vendas.fornecedorcidade.cidade.pais.idpais=22 and c.situacao<>'Finalizado' and c.situacao<>'FINALIZADA' "
				+ " and c.situacao<>'Cancelado'  and c.vendas.dataVenda>='2017-03-01'");
		
		if (listaNovazelandia == null) {
			listaNovazelandia = new ArrayList<>();
		}
		
		if (listaControleVistoBean == null) {
			listaControleVistoBean = new ArrayList<>();
		}
		
		for (int i = 0; i < listaNovazelandia.size(); i++) {
			controleVistosBean = new ControleVistosBean();
			controleVistosBean.setDataEmbarque(listaNovazelandia.get(i).getDataEmbarque());
			controleVistosBean.setDataInicio(listaNovazelandia.get(i).getCurso().getDataInicio());
			controleVistosBean.setEscola(listaNovazelandia.get(i).getVendas().getFornecedor().getNome());
			controleVistosBean.setNomeCliente(listaNovazelandia.get(i).getVendas().getCliente().getNome());
			controleVistosBean.setControlecurso(listaNovazelandia.get(i));
			controleVistosBean.setSituacao(listaNovazelandia.get(i).getSituacao());
			controleVistosBean.setPais(listaNovazelandia.get(i).getVendas().getFornecedorcidade().getCidade().getPais());
			controleVistosBean.setUnidade(listaNovazelandia.get(i).getVendas().getUnidadenegocio());

			if (listaNovazelandia.get(i).isUrgenciavisto()) {
				controleVistosBean.setCor("color:#FF0000;");
			}
			listaControleVistoBean.add(controleVistosBean);
		}
		
	}

	
	public void gerarListaInglaterra(){
		CursoFacade cursoFacade = new CursoFacade();
		ControleVistosBean controleVistosBean = new ControleVistosBean();
		List<Controlecurso> listaInglaterra = cursoFacade.listaControle(" Select c From Controlecurso c Where c.vendas.cliente.tipovisto='Estudante' and c.curso.numeroSenamas + c.curso.sNumeroSemanas>=25 "
				+ "and c.vendas.fornecedorcidade.cidade.pais.idpais=16 and c.situacao<>'Finalizado' and c.situacao<>'FINALIZADA' "
				+ " and c.situacao<>'Cancelado' and c.vendas.dataVenda>='2017-03-01'");
		if (listaInglaterra == null) {
			listaInglaterra = new ArrayList<>();
		}
		
		if (listaControleVistoBean == null) {
			listaControleVistoBean = new ArrayList<>();
		}
		
		for (int i = 0; i < listaInglaterra.size(); i++) {
			controleVistosBean = new ControleVistosBean();
			controleVistosBean.setDataEmbarque(listaInglaterra.get(i).getDataEmbarque());
			controleVistosBean.setDataInicio(listaInglaterra.get(i).getCurso().getDataInicio());
			controleVistosBean.setEscola(listaInglaterra.get(i).getVendas().getFornecedor().getNome());
			controleVistosBean.setNomeCliente(listaInglaterra.get(i).getVendas().getCliente().getNome());
			controleVistosBean.setControlecurso(listaInglaterra.get(i));
			controleVistosBean.setSituacao(listaInglaterra.get(i).getSituacao());
			controleVistosBean.setPais(listaInglaterra.get(i).getVendas().getFornecedorcidade().getCidade().getPais());
			controleVistosBean.setUnidade(listaInglaterra.get(i).getVendas().getUnidadenegocio());
			if (listaInglaterra.get(i).isUrgenciavisto()) {
				controleVistosBean.setCor("color:#FF0000;");
			}
			listaControleVistoBean.add(controleVistosBean);
		}
		
	}
	
	public void gerarListaEmiradosArabesUnidos(){
		CursoFacade cursoFacade = new CursoFacade();
		ControleVistosBean controleVistosBean = new ControleVistosBean();
		List<Controlecurso> listaEmirado = cursoFacade.listaControle(" Select c From Controlecurso c Where c.vendas.cliente.tipovisto='Estudante' and c.curso.numeroSenamas + c.curso.sNumeroSemanas>=12 "
				+ "and c.vendas.fornecedorcidade.cidade.pais.idpais=42 and c.situacao<>'Finalizado' and c.situacao<>'FINALIZADA' "
				+ " and c.situacao<>'Cancelado' and c.vendas.dataVenda>='2017-03-01'");
		if (listaEmirado == null) {
			listaEmirado = new ArrayList<>();
		}
		
		if (listaControleVistoBean == null) {
			listaControleVistoBean = new ArrayList<>();
		}
		
		for (int i = 0; i < listaEmirado.size(); i++) {
			controleVistosBean = new ControleVistosBean();
			controleVistosBean.setDataEmbarque(listaEmirado.get(i).getDataEmbarque());
			controleVistosBean.setDataInicio(listaEmirado.get(i).getCurso().getDataInicio());
			controleVistosBean.setEscola(listaEmirado.get(i).getVendas().getFornecedor().getNome());
			controleVistosBean.setNomeCliente(listaEmirado.get(i).getVendas().getCliente().getNome());
			controleVistosBean.setControlecurso(listaEmirado.get(i));
			controleVistosBean.setSituacao(listaEmirado.get(i).getSituacao());
			controleVistosBean.setPais(listaEmirado.get(i).getVendas().getFornecedorcidade().getCidade().getPais());
			controleVistosBean.setUnidade(listaEmirado.get(i).getVendas().getUnidadenegocio());
			if (listaEmirado.get(i).isUrgenciavisto()) {
				controleVistosBean.setCor("color:#FF0000;");
			}
			listaControleVistoBean.add(controleVistosBean);
		}
		
	}
	
	
	
	public void gerarListaCanada(){
		CursoFacade cursoFacade = new CursoFacade();
		ControleVistosBean controleVistosBean = new ControleVistosBean();
		List<Controlecurso> listaCanada = cursoFacade.listaControle(" Select c From Controlecurso c Where c.vendas.cliente.tipovisto='Estudante' and c.curso.numeroSenamas + c.curso.sNumeroSemanas>=24 "
				+ "and c.vendas.fornecedorcidade.cidade.pais.idpais=6 and c.situacao<>'Finalizado' and c.situacao<>'FINALIZADA' "
				+ " and c.situacao<>'Cancelado' and (c.vendas.fornecedorcidade.cidade.idcidade=45 or "
				+ " c.vendas.fornecedorcidade.cidade.idcidade=49) and c.vendas.dataVenda>='2017-03-01'");
		
		if (listaCanada == null) {
			listaCanada = new ArrayList<>();  
		}
		
		if (listaControleVistoBean == null) {
			listaControleVistoBean = new ArrayList<>();
		}
		
		for (int i = 0; i < listaCanada.size(); i++) {
			controleVistosBean = new ControleVistosBean();
			controleVistosBean.setDataEmbarque(listaCanada.get(i).getDataEmbarque());
			controleVistosBean.setDataInicio(listaCanada.get(i).getCurso().getDataInicio());
			controleVistosBean.setEscola(listaCanada.get(i).getVendas().getFornecedor().getNome());
			controleVistosBean.setNomeCliente(listaCanada.get(i).getVendas().getCliente().getNome());
			controleVistosBean.setControlecurso(listaCanada.get(i));
			controleVistosBean.setSituacao(listaCanada.get(i).getSituacao());
			controleVistosBean.setPais(listaCanada.get(i).getVendas().getFornecedorcidade().getCidade().getPais());
			controleVistosBean.setUnidade(listaCanada.get(i).getVendas().getUnidadenegocio());
			if (listaCanada.get(i).isUrgenciavisto()) {
				controleVistosBean.setCor("color:#FF0000;");
			}
			listaControleVistoBean.add(controleVistosBean);
		}
		
	}
	
	
	public void gerarListaEUA(){
		CursoFacade cursoFacade = new CursoFacade();
		ControleVistosBean controleVistosBean = new ControleVistosBean();
		List<Controlecurso> listaEUA = cursoFacade.listaControle(" Select c From Controlecurso c Where c.vendas.cliente.tipovisto='Estudante' "
				+ "and c.vendas.fornecedorcidade.cidade.pais.idpais=13 and c.situacao<>'Finalizado' and c.situacao<>'FINALIZADA' "
				+ " and c.situacao<>'Cancelado' and c.curso.aulassemana + c.curso.sCargaHoraria>=18  and c.vendas.dataVenda>='2017-03-01'");
		
		if (listaEUA == null) {       
			listaEUA = new ArrayList<>();
		}  
		
		if (listaControleVistoBean == null) {
			listaControleVistoBean = new ArrayList<>();
		}
		
		for (int i = 0; i < listaEUA.size(); i++) {
			if (listaEUA.get(i).isUrgenciavisto()) {
				controleVistosBean.setCor("color:#FF0000;");
			}
			if (listaEUA.get(i).getCurso().getTipoDuracao().equalsIgnoreCase("Horas por Semana")) {
				controleVistosBean = new ControleVistosBean();
				controleVistosBean.setDataEmbarque(listaEUA.get(i).getDataEmbarque());
				controleVistosBean.setDataInicio(listaEUA.get(i).getCurso().getDataInicio());
				controleVistosBean.setEscola(listaEUA.get(i).getVendas().getFornecedor().getNome());
				controleVistosBean.setNomeCliente(listaEUA.get(i).getVendas().getCliente().getNome());
				controleVistosBean.setControlecurso(listaEUA.get(i));
				controleVistosBean.setSituacao(listaEUA.get(i).getSituacao());
				controleVistosBean.setPais(listaEUA.get(i).getVendas().getFornecedorcidade().getCidade().getPais());
				controleVistosBean.setUnidade(listaEUA.get(i).getVendas().getUnidadenegocio());
				listaControleVistoBean.add(controleVistosBean);
			}else if ((listaEUA.get(i).getCurso().getTipoDuracao().equalsIgnoreCase("Aulas por Semana")) && (listaEUA.get(i).getCurso().getAulassemana() >= 23)) {
				controleVistosBean = new ControleVistosBean();
				controleVistosBean.setDataEmbarque(listaEUA.get(i).getDataEmbarque());
				controleVistosBean.setDataInicio(listaEUA.get(i).getCurso().getDataInicio());
				controleVistosBean.setEscola(listaEUA.get(i).getVendas().getFornecedor().getNome());
				controleVistosBean.setNomeCliente(listaEUA.get(i).getVendas().getCliente().getNome());
				controleVistosBean.setControlecurso(listaEUA.get(i));
				controleVistosBean.setSituacao(listaEUA.get(i).getSituacao());
				controleVistosBean.setPais(listaEUA.get(i).getVendas().getFornecedorcidade().getCidade().getPais());
				controleVistosBean.setUnidade(listaEUA.get(i).getVendas().getUnidadenegocio());
				listaControleVistoBean.add(controleVistosBean);
			}
		}
		
	}   
	
	
	
	public String abrirObsVistos(Controlecurso controlecurso) throws SQLException {
		FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        session.setAttribute("controlecurso", controlecurso);
		Map<String, Object> options = new HashMap<String, Object>();
	    options.put("contentWidth",500); 
	    options.put("closable", false);
		RequestContext.getCurrentInstance().openDialog("obsVistos", options, null);
		return "";
	}
	
	
	
	public void retornoDialogObs(SelectEvent event){
		Controlecurso controlecurso = (Controlecurso) event.getObject();
		if (controlecurso.getIdcontroleCursos() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
		}
		
	}
	
	
	public String retornarIconeObs(Controlecurso controlecurso){
		if (controlecurso.getObsvisto() == null) {
			return "../../resources/img/iconeObsVerde.png";
		}
		if (controlecurso.getObsvisto().length() > 0) {
			return "../../resources/img/iconeObsVermelho.png";
		}else{
			return "../../resources/img/iconeObsVerde.png";
		}
	}
	
	
	
	public String buscarArquivo(ControleVistosBean controleVistosBean){
		ArquivosFacade arquivosFacade = new ArquivosFacade();
		List<Arquivos> listaArquivos;
		String sql = "Select a From Arquivos a Where a.vendas.idvendas=" + controleVistosBean.getControlecurso().getVendas().getIdvendas();
		if (controleVistosBean.getPais().getIdpais() == 4) {
			sql = sql + " and a.tipoarquivo.idtipoArquivo=47";
		}else if(controleVistosBean.getPais().getIdpais() == 22){
			sql = sql + " and a.tipoarquivo.idtipoArquivo=48";
		}else if(controleVistosBean.getPais().getIdpais() == 13){
			sql = sql + " and a.tipoarquivo.idtipoArquivo=49";
		}else if(controleVistosBean.getPais().getIdpais() == 6){
			sql = sql + " and a.tipoarquivo.idtipoArquivo=50";
		}else if(controleVistosBean.getPais().getIdpais() == 16){
			sql = sql + " and a.tipoarquivo.idtipoArquivo=51";
		}
		try {
			listaArquivos = arquivosFacade.listar(sql);
			if (listaArquivos == null) {
				listaArquivos = new ArrayList<>();
			}
			if (listaArquivos.size() > 0) {
				return listaArquivos.get(0).getTipoarquivo().getDescricao();
			}
		} catch (SQLException e) {
			  
		}
		return "";
	}
	
	
	public void confirmarUrgencia(){
		CursoFacade cursoFacade = new CursoFacade();
		controlecurso.setUrgenciavisto(true);
		cursoFacade.salvar(controlecurso);
	}
	
	
	public void cancelarUrgencia(){
		CursoFacade cursoFacade = new CursoFacade();
		controlecurso.setUrgenciavisto(false);
		cursoFacade.salvar(controlecurso);
	}
	
	
	
	public String retornarCores(Controlecurso controlecurso){
		if (controlecurso.isUrgenciavisto()) {
			return "color:#FF0000;";
		}else{
			return "";
		}
	}
	
	
	public void dialogSalvarUrgencia(Controlecurso controlecurso) {
		this.controlecurso = controlecurso;
	}
	
	
	
	
	public String retornarIconeRelatorio(Controlecurso controlecurso){
		if (controlecurso.getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			return "../../resources/img/bolaAmarela.png";
		}else if (controlecurso.getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")) {
			return "../../resources/img/bolaVerde.png";
		}
		return "";
	} 
	
	
}
