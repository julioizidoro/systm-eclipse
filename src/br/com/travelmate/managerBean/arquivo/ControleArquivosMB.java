package br.com.travelmate.managerBean.arquivo;

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
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.travelmate.dao.ArquivosHistoricoDao;
import br.com.travelmate.dao.ArquivosListaDao;
import br.com.travelmate.facade.ArquivosFacade;
import br.com.travelmate.facade.FtpDadosFacade;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Arquivos;
import br.com.travelmate.model.Arquivoshitorico;
import br.com.travelmate.model.Arquvioslista;
import br.com.travelmate.model.Ftpdados;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class ControleArquivosMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private ArquivosHistoricoDao arquivosHistoricoDao;
	@Inject
	private ArquivosListaDao arquivosListaDao;
	private List<String> listaTeste;
	private String descricao;
	private Arquivos arquivos;
	private List<Arquivoshitorico> listaHistorico;
	private List<Arquivoshitorico> listaHistorico2;
	private List<Arquivoshitorico> listaHistorico3;
	private List<Arquivoshitorico> listaHistorico4;
	private boolean panelPdf = false;
	private boolean panelHistorico = false;
	private List<Arquvioslista> listaArquivos;
	private List<Arquvioslista> listaArquivos2;
	private List<Arquvioslista> listaArquivos3;
	private List<Arquvioslista> listaArquivos4;
	private Vendas vendas;
	private String voltar;
	private String titulo1;
	private String titulo2;
	private String titulo3;
	private String titulo4;
	private Arquivos arquivos2;
	private Arquivos arquivos3;
	private Arquivos arquivos4;
	private boolean panelPdf2 = false;
	private boolean panelPdf3 = false;
	private boolean panelPdf4 = false;
	private boolean panelHistorico2 = false;
	private boolean panelHistorico3 = false;
	private boolean panelHistorico4 = false;
	private String descricao2;
	private String descricao3;
	private String descricao4;
	private Ftpdados ftpdados;
	private String urlArquivo = "";
	
	
	
	@PostConstruct
	public void init() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		vendas = (Vendas) session.getAttribute("vendas");
		voltar = (String) session.getAttribute("voltar");
		session.removeAttribute("vendas");
		session.removeAttribute("voltar");
		gerarListaAquivos();
		gerarListaAquivos2();
		gerarListaAquivos3();
		gerarListaAquivos4();
		FtpDadosFacade ftpDadosFacade = new FtpDadosFacade();
		try {
			ftpdados = ftpDadosFacade.getFTPDados();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ftpdados != null) {
			urlArquivo = ftpdados.getProtocolo() + "://" + ftpdados.getHost() + ":"+ ftpdados.getWww() +"/systm/arquivos/";
		}
		
	}



	public List<String> getListaTeste() {
		return listaTeste;
	}



	public void setListaTeste(List<String> listaTeste) {
		this.listaTeste = listaTeste;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
	public Arquivos getArquivos() {
		return arquivos;
	}



	public void setArquivos(Arquivos arquivos) {
		this.arquivos = arquivos;
	}



	public List<Arquivoshitorico> getListaHistorico() {
		return listaHistorico;
	}



	public void setListaHistorico(List<Arquivoshitorico> listaHistorico) {
		this.listaHistorico = listaHistorico;
	}



	public boolean isPanelPdf() {
		return panelPdf;
	}



	public void setPanelPdf(boolean panelPdf) {
		this.panelPdf = panelPdf;
	}



	public boolean isPanelHistorico() {
		return panelHistorico;
	}



	public void setPanelHistorico(boolean panelHistorico) {
		this.panelHistorico = panelHistorico;
	}



	public List<Arquvioslista> getListaArquivos() {
		return listaArquivos;
	}



	public void setListaArquivos(List<Arquvioslista> listaArquivos) {
		this.listaArquivos = listaArquivos;
	}



	public Vendas getVendas() {
		return vendas;
	}



	public void setVendas(Vendas vendas) {
		this.vendas = vendas;
	}



	public String getVoltar() {
		return voltar;
	}



	public void setVoltar(String voltar) {
		this.voltar = voltar;
	}



	public String getTitulo1() {
		return titulo1;
	}



	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}



	public String getTitulo2() {
		return titulo2;
	}



	public void setTitulo2(String titulo2) {
		this.titulo2 = titulo2;
	}



	public String getTitulo3() {
		return titulo3;
	}



	public void setTitulo3(String titulo3) {
		this.titulo3 = titulo3;
	}



	public String getTitulo4() {
		return titulo4;
	}



	public void setTitulo4(String titulo4) {
		this.titulo4 = titulo4;
	}



	public List<Arquvioslista> getListaArquivos2() {
		return listaArquivos2;
	}



	public void setListaArquivos2(List<Arquvioslista> listaArquivos2) {
		this.listaArquivos2 = listaArquivos2;
	}



	public List<Arquvioslista> getListaArquivos3() {
		return listaArquivos3;
	}



	public void setListaArquivos3(List<Arquvioslista> listaArquivos3) {
		this.listaArquivos3 = listaArquivos3;
	}



	public Arquivos getArquivos2() {
		return arquivos2;
	}



	public void setArquivos2(Arquivos arquivos2) {
		this.arquivos2 = arquivos2;
	}



	public Arquivos getArquivos3() {
		return arquivos3;
	}



	public void setArquivos3(Arquivos arquivos3) {
		this.arquivos3 = arquivos3;
	}



	public boolean isPanelPdf2() {
		return panelPdf2;
	}



	public void setPanelPdf2(boolean panelPdf2) {
		this.panelPdf2 = panelPdf2;
	}



	public boolean isPanelPdf3() {
		return panelPdf3;
	}



	public void setPanelPdf3(boolean panelPdf3) {
		this.panelPdf3 = panelPdf3;
	}



	public boolean isPanelHistorico2() {
		return panelHistorico2;
	}



	public void setPanelHistorico2(boolean panelHistorico2) {
		this.panelHistorico2 = panelHistorico2;
	}



	public boolean isPanelHistorico3() {
		return panelHistorico3;
	}



	public void setPanelHistorico3(boolean panelHistorico3) {
		this.panelHistorico3 = panelHistorico3;
	}



	public List<Arquivoshitorico> getListaHistorico2() {
		return listaHistorico2;
	}



	public void setListaHistorico2(List<Arquivoshitorico> listaHistorico2) {
		this.listaHistorico2 = listaHistorico2;
	}



	public List<Arquivoshitorico> getListaHistorico3() {
		return listaHistorico3;
	}



	public void setListaHistorico3(List<Arquivoshitorico> listaHistorico3) {
		this.listaHistorico3 = listaHistorico3;
	}



	public List<Arquivoshitorico> getListaHistorico4() {
		return listaHistorico4;
	}



	public void setListaHistorico4(List<Arquivoshitorico> listaHistorico4) {
		this.listaHistorico4 = listaHistorico4;
	}



	public List<Arquvioslista> getListaArquivos4() {
		return listaArquivos4;
	}



	public void setListaArquivos4(List<Arquvioslista> listaArquivos4) {
		this.listaArquivos4 = listaArquivos4;
	}



	public Arquivos getArquivos4() {
		return arquivos4;
	}



	public void setArquivos4(Arquivos arquivos4) {
		this.arquivos4 = arquivos4;
	}



	public boolean isPanelPdf4() {
		return panelPdf4;
	}



	public void setPanelPdf4(boolean panelPdf4) {
		this.panelPdf4 = panelPdf4;
	}



	public boolean isPanelHistorico4() {
		return panelHistorico4;
	}



	public void setPanelHistorico4(boolean panelHistorico4) {
		this.panelHistorico4 = panelHistorico4;
	}



	public String getDescricao2() {
		return descricao2;
	}



	public void setDescricao2(String descricao2) {
		this.descricao2 = descricao2;
	}



	public String getDescricao3() {
		return descricao3;
	}



	public void setDescricao3(String descricao3) {
		this.descricao3 = descricao3;
	}



	public String getDescricao4() {
		return descricao4;
	}



	public void setDescricao4(String descricao4) {
		this.descricao4 = descricao4;
	}



	public Ftpdados getFtpdados() {
		return ftpdados;
	}



	public void setFtpdados(Ftpdados ftpdados) {
		this.ftpdados = ftpdados;
	}



	public String getUrlArquivo() {
		return urlArquivo;
	}



	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}



	public void salvarCorrecao2() {
		Arquivoshitorico arquivoshitorico = new Arquivoshitorico();
		arquivoshitorico.setDescricao(descricao2);
		arquivoshitorico.setData(new Date());
		arquivoshitorico.setHora(Formatacao.foramtarHoraString());
		arquivoshitorico.setUsuario(usuarioLogadoMB.getUsuario());
		arquivoshitorico.setArquivos(arquivos2);
		arquivoshitorico = arquivosHistoricoDao.salvar(arquivoshitorico);
		if (listaHistorico2 == null) {
			listaHistorico2 = new ArrayList<Arquivoshitorico>();
		}
		listaHistorico2.add(arquivoshitorico);
		descricao2 = "";
	}
	
	public void salvarCorrecao() {
		Arquivoshitorico arquivoshitorico = new Arquivoshitorico();
		arquivoshitorico.setDescricao(descricao);
		arquivoshitorico.setData(new Date());
		arquivoshitorico.setHora(Formatacao.foramtarHoraString());
		arquivoshitorico.setUsuario(usuarioLogadoMB.getUsuario());
		arquivoshitorico.setArquivos(arquivos);
		arquivoshitorico = arquivosHistoricoDao.salvar(arquivoshitorico);
		if (listaHistorico == null) {
			listaHistorico = new ArrayList<Arquivoshitorico>();
		}
		listaHistorico.add(arquivoshitorico);
		descricao = "";
	}
	
	public void salvarCorrecao3() {
		Arquivoshitorico arquivoshitorico = new Arquivoshitorico();
		arquivoshitorico.setDescricao(descricao3);
		arquivoshitorico.setData(new Date());
		arquivoshitorico.setHora(Formatacao.foramtarHoraString());
		arquivoshitorico.setUsuario(usuarioLogadoMB.getUsuario());
		arquivoshitorico.setArquivos(arquivos3);
		arquivoshitorico = arquivosHistoricoDao.salvar(arquivoshitorico);
		if (listaHistorico3 == null) {
			listaHistorico3 = new ArrayList<Arquivoshitorico>();
		}
		listaHistorico3.add(arquivoshitorico);
		descricao3 = "";
	}
	
	public void salvarCorrecao4() {
		Arquivoshitorico arquivoshitorico = new Arquivoshitorico();
		arquivoshitorico.setDescricao(descricao4);
		arquivoshitorico.setData(new Date());
		arquivoshitorico.setHora(Formatacao.foramtarHoraString());
		arquivoshitorico.setUsuario(usuarioLogadoMB.getUsuario());
		arquivoshitorico.setArquivos(arquivos4);
		arquivoshitorico = arquivosHistoricoDao.salvar(arquivoshitorico);
		if (listaHistorico4 == null) {
			listaHistorico4 = new ArrayList<Arquivoshitorico>();
		}
		listaHistorico4.add(arquivoshitorico);
		descricao4 = "";
	}
	
	
	public void habilitarUplaod(Arquivos arquivos) {
		panelHistorico = false;
		panelPdf = true;
		this.arquivos = arquivos;
	}
	
	public void habilitarUplaod2(Arquivos arquivos) {
		panelHistorico2 = false;
		panelPdf2 = true;
		this.arquivos2 = arquivos;
	}
	
	public void habilitarUplaod3(Arquivos arquivos) {
		panelHistorico3 = false;
		panelPdf3 = true;
		this.arquivos3 = arquivos;
	}
	
	public void habilitarUplaod4(Arquivos arquivos) {
		panelHistorico4 = false;
		panelPdf4 = true;
		this.arquivos4 = arquivos;
	}
	
	public void retornoDialogArquivo(SelectEvent event) {
		Arquivos arquivos = (Arquivos) event.getObject();
		if (arquivos != null && arquivos.getIdarquivos() != null) {
			Mensagem.lancarMensagemInfo("Arquivo anexado com sucesso", "");
			for (int i = 0; i < listaArquivos.size(); i++) {
				int idLista = listaArquivos.get(i).getArquivos().getIdarquivos();
				int idArquivo = arquivos.getIdarquivos();
				if (idLista == idArquivo) {
					listaArquivos.get(i).setCorArquivo("color:green");
					listaArquivos.get(i).getArquivos().setNomesalvos(arquivos.getNomesalvos());
					i = 10000;
				}
			}
		}
	}
	
	public void retornoDialogArquivo2(SelectEvent event) {
		Arquivos arquivos = (Arquivos) event.getObject();
		if (arquivos != null && arquivos.getIdarquivos() != null) {
			Mensagem.lancarMensagemInfo("Arquivo anexado com sucesso", "");
			for (int i = 0; i < listaArquivos2.size(); i++) {
				int idLista = listaArquivos2.get(i).getArquivos().getIdarquivos();
				int idArquivo = arquivos.getIdarquivos();
				if (idLista == idArquivo) {
					listaArquivos2.get(i).setCorArquivo("color:green");
					listaArquivos2.get(i).getArquivos().setNomesalvos(arquivos.getNomesalvos());
					i = 10000;
				}
			}
		}
	}
	
	public void retornoDialogArquivo3(SelectEvent event) {
		Arquivos arquivos = (Arquivos) event.getObject();
		if (arquivos != null && arquivos.getIdarquivos() != null) {
			Mensagem.lancarMensagemInfo("Arquivo anexado com sucesso", "");
			for (int i = 0; i < listaArquivos3.size(); i++) {
				int idLista = listaArquivos3.get(i).getArquivos().getIdarquivos();
				int idArquivo = arquivos.getIdarquivos();
				if (idLista == idArquivo) {
					listaArquivos3.get(i).setCorArquivo("color:green");
					listaArquivos3.get(i).getArquivos().setNomesalvos(arquivos.getNomesalvos());
					i = 10000;
				}
			}
		}
	}
	
	public void retornoDialogArquivo4(SelectEvent event) {
		Arquivos arquivos = (Arquivos) event.getObject();
		if (arquivos != null && arquivos.getIdarquivos() != null) {
			Mensagem.lancarMensagemInfo("Arquivo anexado com sucesso", "");
			for (int i = 0; i < listaArquivos4.size(); i++) {
				int idLista = listaArquivos4.get(i).getArquivos().getIdarquivos();
				int idArquivo = arquivos.getIdarquivos();
				if (idLista == idArquivo) {
					listaArquivos4.get(i).setCorArquivo("color:green");
					listaArquivos4.get(i).getArquivos().setNomesalvos(arquivos.getNomesalvos());
					i = 10000;
				}
			}
		}
	}
	
	
	public void habilitarHistorico(Arquivos arquivos) {
		panelHistorico = true;
		panelPdf = false;
		this.arquivos = arquivos;
		gerarArquivoHistorico();
	}
	
	public void habilitarHistorico2(Arquivos arquivos) {
		panelHistorico2 = true;
		panelPdf2 = false;
		this.arquivos2 = arquivos;
		gerarArquivoHistorico2();
	}
	
	public void habilitarHistorico3(Arquivos arquivos) {
		panelHistorico3 = true;
		panelPdf3 = false;
		this.arquivos3 = arquivos;
		gerarArquivoHistorico3();
	}
	
	public void habilitarHistorico4(Arquivos arquivos) {
		panelHistorico4 = true;
		panelPdf4 = false;
		this.arquivos4 = arquivos;
		gerarArquivoHistorico4();
	}
	
	
	public void fecharHistorico() {
		panelHistorico = false;
	}
	
	public void fecharPDF() {
		panelPdf = false;
	}
	
	
	public void fecharHistorico2() {
		panelHistorico2 = false;
	}
	
	public void fecharPDF2() {
		panelPdf2 = false;
	}
	
	
	public void fecharHistorico3() {
		panelHistorico3 = false;
	}
	
	public void fecharPDF3() {
		panelPdf3 = false;
	}
	
	
	public void fecharHistorico4() {
		panelHistorico4 = false;
	}
	
	public void fecharPDF4() {
		panelPdf4 = false;
	}
	
	public void gerarListaAquivos() {
		String sql = "SELECT a FROM Arquvioslista a WHERE a.arquivos.vendas.idvendas=" + vendas.getIdvendas() 
		+ " and a.arquivoslistamodelo.tipoarquivoproduto.produtos.idprodutos="+ vendas.getProdutos().getIdprodutos() 
		+" and a.arquivoslistamodelo.classe='Documentos de Inscrição' order by a.arquivoslistamodelo.ordem";
		listaArquivos = arquivosListaDao.lista(sql);
		if (listaArquivos == null) {
			listaArquivos = new ArrayList<Arquvioslista>();
		}
		for (int i = 0; i < listaArquivos.size(); i++) {
			if (listaArquivos.get(i).getArquivos().isSitaucao()) {
				listaArquivos.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
			}else {
				listaArquivos.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
			}
			if (listaArquivos.get(i).getArquivos().getNomesalvos() == null || listaArquivos.get(i).getArquivos().getNomesalvos().length() == 0) {
				listaArquivos.get(i).setCorArquivo("color:red;");
			}else {
				listaArquivos.get(i).setCorArquivo("color:green;");
			}
			titulo1 = listaArquivos.get(i).getArquivoslistamodelo().getClasse();
		}
	}
	
	public void gerarListaAquivos2() {
		String sql = "SELECT a FROM Arquvioslista a WHERE a.arquivos.vendas.idvendas=" + vendas.getIdvendas() 
		+ " and a.arquivoslistamodelo.tipoarquivoproduto.produtos.idprodutos="+ vendas.getProdutos().getIdprodutos() 
		+" and a.arquivoslistamodelo.classe='Documentos Complementares'  order by a.arquivoslistamodelo.ordem";
		listaArquivos2 = arquivosListaDao.lista(sql);
		if (listaArquivos2 == null) {
			listaArquivos2 = new ArrayList<Arquvioslista>();
		}
		for (int i = 0; i < listaArquivos2.size(); i++) {
			if (listaArquivos2.get(i).getArquivos().isSitaucao()) {
				listaArquivos2.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
			}else {
				listaArquivos2.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
			}
			if (listaArquivos2.get(i).getArquivos().getNomesalvos() == null || listaArquivos2.get(i).getArquivos().getNomesalvos().length() == 0) {
				listaArquivos2.get(i).setCorArquivo("color:red;");
			}else {
				listaArquivos2.get(i).setCorArquivo("color:green;");
			}
			titulo2 = listaArquivos2.get(i).getArquivoslistamodelo().getClasse();
		}
	}
	
	public void gerarListaAquivos3() {
		String sql = "SELECT a FROM Arquvioslista a WHERE a.arquivos.vendas.idvendas=" + vendas.getIdvendas() 
		+ " and a.arquivoslistamodelo.tipoarquivoproduto.produtos.idprodutos="+ vendas.getProdutos().getIdprodutos() 
		+" and a.arquivoslistamodelo.classe='Kit Viagem'    order by a.arquivoslistamodelo.ordem";
		listaArquivos3 = arquivosListaDao.lista(sql);
		if (listaArquivos3 == null) {
			listaArquivos3 = new ArrayList<Arquvioslista>();
		}
		for (int i = 0; i < listaArquivos3.size(); i++) {
			if (listaArquivos3.get(i).getArquivos().isSitaucao()) {
				listaArquivos3.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
			}else {
				listaArquivos3.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
			}
			if (listaArquivos3.get(i).getArquivos().getNomesalvos() == null || listaArquivos3.get(i).getArquivos().getNomesalvos().length() == 0) {
				listaArquivos3.get(i).setCorArquivo("color:red;");
			}else {
				listaArquivos3.get(i).setCorArquivo("color:green;");
			}
			titulo3 = listaArquivos3.get(i).getArquivoslistamodelo().getClasse();
		}
	}
	
	
	public void gerarArquivoHistorico() {
		String sql = "SELECT a FROM Arquivoshitorico a WHERE a.arquivos.idarquivos=" + arquivos.getIdarquivos();
		listaHistorico = arquivosHistoricoDao.lista(sql);
		if (listaHistorico == null) {
			listaHistorico = new ArrayList<Arquivoshitorico>();
		}
	}
	
	public void gerarArquivoHistorico2() {
		String sql = "SELECT a FROM Arquivoshitorico a WHERE a.arquivos.idarquivos=" + arquivos2.getIdarquivos();
		listaHistorico2 = arquivosHistoricoDao.lista(sql);
		if (listaHistorico2 == null) {
			listaHistorico2 = new ArrayList<Arquivoshitorico>();
		}
	}
	
	public void gerarArquivoHistorico3() {
		String sql = "SELECT a FROM Arquivoshitorico a WHERE a.arquivos.idarquivos=" + arquivos3.getIdarquivos();
		listaHistorico3 = arquivosHistoricoDao.lista(sql);
		if (listaHistorico3 == null) {
			listaHistorico3 = new ArrayList<Arquivoshitorico>();
		}
	}
	
	public void gerarArquivoHistorico4() {
		String sql = "SELECT a FROM Arquivoshitorico a WHERE a.arquivos.idarquivos=" + arquivos4.getIdarquivos();
		listaHistorico4 = arquivosHistoricoDao.lista(sql);
		if (listaHistorico4 == null) {
			listaHistorico4 = new ArrayList<Arquivoshitorico>();
		}
	}
	
	public String buscarNomeArquivo() {
		if (arquivos != null && arquivos.getNomesalvos() != null) {
			return arquivos.getNomesalvos();
		}
		return "";
	}
	
	
	public String buscarNomeArquivo2() {
		if (arquivos2 != null && arquivos2.getNomesalvos() != null) {
			return arquivos2.getNomesalvos();
		}
		return "";
	}
	
	
	public String buscarNomeArquivo3() {
		if (arquivos3 != null && arquivos3.getNomesalvos() != null) {
			return arquivos3.getNomesalvos();
		}
		return "";
	}
	
	
	public String buscarNomeArquivo4() {
		if (arquivos4 != null && arquivos4.getNomesalvos() != null) {
			return arquivos4.getNomesalvos();
		}
		return "";
	}
	
	
	public void mudarSituacao(Arquivos arquivos) {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1 || usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
			if (arquivos.isSitaucao()) {
				arquivos.setSitaucao(false);
			}else {
				arquivos.setSitaucao(true);
			}
			ArquivosFacade arquivosFacade = new ArquivosFacade();
			arquivos = arquivosFacade.salvar(arquivos);
			for (int i = 0; i < listaArquivos.size(); i++) {
				int idLista = listaArquivos.get(i).getArquivos().getIdarquivos();
				int idArquivos = arquivos.getIdarquivos();
				if (idArquivos == idLista) {
					if (arquivos.isSitaucao()) {
						listaArquivos.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
					}else {
						listaArquivos.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
					}
				}
			}
		}
	}
	
	
	public void mudarSituacao2(Arquivos arquivos) {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1 || usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
			if (arquivos.isSitaucao()) {
				arquivos.setSitaucao(false);
			}else {
				arquivos.setSitaucao(true);
			}
			ArquivosFacade arquivosFacade = new ArquivosFacade();
			arquivos = arquivosFacade.salvar(arquivos);
			for (int i = 0; i < listaArquivos2.size(); i++) {
				int idLista = listaArquivos2.get(i).getArquivos().getIdarquivos();
				int idArquivos = arquivos.getIdarquivos();
				if (idArquivos == idLista) {
					if (arquivos.isSitaucao()) {
						listaArquivos2.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
					}else {
						listaArquivos2.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
					}
				}
			}
		}
	}
	
	
	public void mudarSituacao3(Arquivos arquivos) {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1 || usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
			if (arquivos.isSitaucao()) {
				arquivos.setSitaucao(false);
			}else {
				arquivos.setSitaucao(true);
			}
			ArquivosFacade arquivosFacade = new ArquivosFacade();
			arquivos = arquivosFacade.salvar(arquivos);
			for (int i = 0; i < listaArquivos3.size(); i++) {
				int idLista = listaArquivos3.get(i).getArquivos().getIdarquivos();
				int idArquivos = arquivos.getIdarquivos();
				if (idArquivos == idLista) {
					if (arquivos.isSitaucao()) {
						listaArquivos3.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
					}else {
						listaArquivos3.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
					}
				}
			}
		}
	}
	
	public void mudarSituacao4(Arquivos arquivos) {
		if (usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 1 || usuarioLogadoMB.getUsuario().getDepartamento().getIddepartamento() == 4) {
			if (arquivos.isSitaucao()) {
				arquivos.setSitaucao(false);
			}else {
				arquivos.setSitaucao(true);
			}
			ArquivosFacade arquivosFacade = new ArquivosFacade();
			arquivos = arquivosFacade.salvar(arquivos);
			for (int i = 0; i < listaArquivos4.size(); i++) {
				int idLista = listaArquivos4.get(i).getArquivos().getIdarquivos();
				int idArquivos = arquivos.getIdarquivos();
				if (idArquivos == idLista) {
					if (arquivos.isSitaucao()) {
						listaArquivos4.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
					}else {
						listaArquivos4.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
					}
				}
			}
		}
	}
	
	
	public void gerarListaAquivos4() {
		String sql = "SELECT a FROM Arquvioslista a WHERE a.arquivos.vendas.idvendas=" + vendas.getIdvendas() 
		+ " and a.arquivoslistamodelo.tipoarquivoproduto.produtos.idprodutos="+ vendas.getProdutos().getIdprodutos() 
		+" and a.arquivoslistamodelo.classe<>'Kit Viagem'  and a.arquivoslistamodelo.classe<>'Documentos para Inscrição'  and a.arquivoslistamodelo.classe<>'Documentos Complementares'    order by a.arquivoslistamodelo.ordem";
		listaArquivos4 = arquivosListaDao.lista(sql);
		if (listaArquivos4 == null) {
			listaArquivos4 = new ArrayList<Arquvioslista>();
		}
		for (int i = 0; i < listaArquivos4.size(); i++) {
			if (listaArquivos4.get(i).getArquivos().isSitaucao()) {
				listaArquivos4.get(i).setImgSituacao("../../resources/img/confirmarBola.png");
			}else {
				listaArquivos4.get(i).setImgSituacao("../../resources/img/iconeSApp.png");
			}
			if (listaArquivos4.get(i).getArquivos().getNomesalvos() == null || listaArquivos4.get(i).getArquivos().getNomesalvos().length() == 0) {
				listaArquivos4.get(i).setCorArquivo("color:red;");
			}else {
				listaArquivos4.get(i).setCorArquivo("color:green;");
			}
			titulo4 = listaArquivos4.get(i).getArquivoslistamodelo().getClasse();
		}
	}
	
	
	public void adicionarOutrosArquivo() {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", vendas);
		session.setAttribute("arquivoslistamodelo", listaArquivos4.get(0).getArquivoslistamodelo());
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 400);
		RequestContext.getCurrentInstance().openDialog("cadOutrosArquivos", options, null);
	}
	
	
	public void retornoDialogOutrosArquivos(SelectEvent event) {
		Arquvioslista arquvioslista = (Arquvioslista) event.getObject();
		if (arquvioslista != null && arquvioslista.getIdarquvioslista() != null) {
			Mensagem.lancarMensagemInfo("Salvo com sucesso", "");
			if (listaArquivos4 == null) {
				listaArquivos4 = new ArrayList<Arquvioslista>();
			}
			arquvioslista.setImgSituacao("../../resources/img/iconeSApp.png");
			listaArquivos4.add(arquvioslista);
		}
	}
	
	public void adicionarrUplaod(Arquivos arquivos) {
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", vendas);
		session.setAttribute("arquivos", arquivos);
		Map<String, Object> options = new HashMap<String, Object>();
		options.put("contentWidth", 500);
		RequestContext.getCurrentInstance().openDialog("cadControleArquivos", options, null);
	}
	
	public String voltarTela() {
		
		return voltar;
	}


}
