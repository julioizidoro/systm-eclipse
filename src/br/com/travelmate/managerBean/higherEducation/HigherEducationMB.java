package br.com.travelmate.managerBean.higherEducation;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.bean.GerarBoletoConsultorBean;
import br.com.travelmate.bean.ListaHeBean;
import br.com.travelmate.bean.RelatorioErroBean;
import br.com.travelmate.dao.AcessoUnidadeDao;
import br.com.travelmate.facade.ContasReceberFacade;
import br.com.travelmate.facade.HeFacade;
import br.com.travelmate.facade.QuestionarioHeFacade;
import br.com.travelmate.facade.VendasFacade;
import br.com.travelmate.managerBean.AplicacaoMB;
import br.com.travelmate.managerBean.UsuarioLogadoMB;
import br.com.travelmate.model.Acessounidade;
import br.com.travelmate.model.Contasreceber; 
import br.com.travelmate.model.He;
import br.com.travelmate.model.Questionariohe;
import br.com.travelmate.model.Unidadenegocio; 
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarListas;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;
import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class HigherEducationMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AplicacaoMB aplicacaoMB;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject 
	private AcessoUnidadeDao acessoUnidadeDao;
	private List<ListaHeBean> listaHe;
	private int idvenda;
	private String nomeCliente = "";
	private Date dataInicio;
	private Date dataTermino;
	private String situacao;
	private Unidadenegocio unidadenegocio;
	private List<Unidadenegocio> listaUnidade;
	private boolean habilitarUnidade = true;
	private boolean expandirOpcoes;
	private boolean esconderFicha=true;

	@PostConstruct()
	public void init() {
		if (usuarioLogadoMB.getUsuario() != null && usuarioLogadoMB.getUsuario().getIdusuario() != null) {
			listaUnidade = GerarListas.listarUnidade();
			if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				habilitarUnidade = false;
			} else {
				habilitarUnidade = true;
				unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
			}
			gerarListaHe();
		}
	}

	public AplicacaoMB getAplicacaoMB() {
		return aplicacaoMB;
	}

	public void setAplicacaoMB(AplicacaoMB aplicacaoMB) {
		this.aplicacaoMB = aplicacaoMB;
	}

	public UsuarioLogadoMB getUsuarioLogadoMB() {
		return usuarioLogadoMB;
	}

	public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
		this.usuarioLogadoMB = usuarioLogadoMB;
	}

	public List<ListaHeBean> getListaHe() {
		return listaHe;
	}

	public void setListaHe(List<ListaHeBean> listaHe) {
		this.listaHe = listaHe;
	}

	public int getIdvenda() {
		return idvenda;
	}

	public void setIdvenda(int idvenda) {
		this.idvenda = idvenda;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
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

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Unidadenegocio getUnidadenegocio() {
		return unidadenegocio;
	}

	public void setUnidadenegocio(Unidadenegocio unidadenegocio) {
		this.unidadenegocio = unidadenegocio;
	}

	public List<Unidadenegocio> getListaUnidade() {
		return listaUnidade;
	}

	public void setListaUnidade(List<Unidadenegocio> listaUnidade) {
		this.listaUnidade = listaUnidade;
	}

	public boolean isHabilitarUnidade() {
		return habilitarUnidade;
	}

	public void setHabilitarUnidade(boolean habilitarUnidade) {
		this.habilitarUnidade = habilitarUnidade;
	}

	public boolean isExpandirOpcoes() {
		return expandirOpcoes;
	}

	public void setExpandirOpcoes(boolean expandirOpcoes) {
		this.expandirOpcoes = expandirOpcoes;
	}

	public boolean isEsconderFicha() {
		return esconderFicha;
	}

	public void setEsconderFicha(boolean esconderFicha) {
		this.esconderFicha = esconderFicha;
	}

	public String novoQuestionario() {
		return "questionarioHe";
	}

	public String editar(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("he", listaHeBean.getHe());
			if (listaHeBean.getHe().isAprovado()) {
				return "cadFichaHe2";
			} else {
				session.setAttribute("questionariohe", listaHeBean.getHe().getQuestionariohe());
				return "cadFichaHe1";
			}
		} else if (listaHeBean.getQuestionariohe() != null) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("questionariohe", listaHeBean.getQuestionariohe());
			if (listaHeBean.getQuestionariohe().isAutorizado()) {
				return "cadFichaHe1";
			} else {
				return "questionarioHe";
			}
		} else
			return "";
	}

	public void gerarListaHe() {
		String dataConsulta = Formatacao.SubtarirDatas(new Date(), 30, "yyyy/MM/dd");
		// questionario
		String sqlQuestionario = "Select q From Questionariohe q where q.dataenvio>='" + dataConsulta + "'";
		if (!usuarioLogadoMB.getUsuario().getGrupoacesso().getAcesso().isAprovarquestionariohe()) {
			sqlQuestionario = sqlQuestionario + " and q.cliente.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
					+usuarioLogadoMB.getUsuario().getIdusuario());
			if(acessounidade!=null) {
				if(!acessounidade.isEmissaoconsulta()) {
					sqlQuestionario = sqlQuestionario + " and q.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		} else {
			sqlQuestionario = sqlQuestionario + " and q.autorizado=false";
		}
		sqlQuestionario = sqlQuestionario + " order by q.dataenvio desc";
		QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
		List<Questionariohe> listaQuestionario = questionarioHeFacade.listar(sqlQuestionario);

		// ficha inscricao
		String sqlFicha1 = "Select h From He h where h.vendas.dataVenda>='" + dataConsulta + "' and h.fichafinal=FALSE";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sqlFicha1 = sqlFicha1 + " and h.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
					+usuarioLogadoMB.getUsuario().getIdusuario());
			if(acessounidade!=null) {
				if(!acessounidade.isEmissaoconsulta()) {
					sqlFicha1 = sqlFicha1 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		} else {
			sqlFicha1 = sqlFicha1 + " and h.aprovado=false";
		}
		sqlFicha1 = sqlFicha1 + " order by h.vendas.dataVenda desc";
		HeFacade heFacade = new HeFacade();
		List<He> listaficha1 = heFacade.listar(sqlFicha1);

		// ficha inscricao
		String sqlFicha2 = "Select h From He h where h.vendas.dataVenda>='" + dataConsulta + "' and h.fichafinal=TRUE";
		if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
					+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
			Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
					+usuarioLogadoMB.getUsuario().getIdusuario());
			if(acessounidade!=null) {
				if(!acessounidade.isEmissaoconsulta()) {
					sqlFicha2 = sqlFicha2 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
				}
			}
		}
		sqlFicha2 = sqlFicha2 + " order by h.vendas.dataVenda desc";
		List<He> listaficha2 = heFacade.listar(sqlFicha2);
		listaHe = new ArrayList<ListaHeBean>();
		ListaHeBean heBean; 
		if (listaficha1 != null && listaficha1.size() > 0) {
			for (int i = 0; i < listaficha1.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha1.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha1.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha1.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha1.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha1.get(i));
				heBean.setQuestionariohe(listaficha1.get(i).getQuestionariohe());
				if (listaficha1.get(i).isAprovado()) {
					heBean.setSituacao("Ficha de Inscrição Aprovada");
				} else
					heBean.setSituacao("Ficha de Inscrição");
				heBean.setStatus(listaficha1.get(i).getVendas().getSituacao());
				heBean.setData(listaficha1.get(i).getVendas().getDataVenda());
				heBean.setIdVenda(listaficha1.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha1.get(i).isAprovado());
				listaHe.add(heBean);
			}
		}
		if (listaficha2 != null && listaficha2.size() > 0) {
			for (int i = 0; i < listaficha2.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha2.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha2.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha2.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha2.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha2.get(i));
				heBean.setQuestionariohe(listaficha2.get(i).getQuestionariohe());
				heBean.setSituacao("Ficha Final");  
				heBean.setStatus(listaficha2.get(i).getVendas1().getSituacao());
				heBean.setData(listaficha2.get(i).getVendas1().getDataVenda());
				heBean.setIdVenda(listaficha2.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha2.get(i).isAprovado());
				listaHe.add(heBean); 
			}
		}
		if (listaQuestionario != null && listaQuestionario.size() > 0) {
			for (int i = 0; i < listaQuestionario.size(); i++) {
				boolean ok=true;
				if (listaficha2 != null && listaficha2.size() > 0) {
					for (int j = 0; j < listaficha2.size(); j++) {
						if(listaficha2.get(j).getVendas().getIdvendas()!=listaQuestionario.get(i).getVendas().getIdvendas()){
							ok=false;
						}
					}
				}
				if (listaficha1 != null && listaficha1.size() > 0) {
					for (int j = 0; j < listaficha1.size(); j++) {
						if(listaficha1.get(j).getVendas().getIdvendas()==listaQuestionario.get(i).getVendas().getIdvendas()){
							ok=false;
						}
					}  
				}
				if(ok){
					heBean = new ListaHeBean();
					heBean.setConsultor(listaQuestionario.get(i).getUsuario().getNome());
					heBean.setFornecedor("");
					heBean.setUnidade(listaQuestionario.get(i).getUsuario().getUnidadenegocio().getNomerelatorio());
					heBean.setNomecliente(listaQuestionario.get(i).getCliente().getNome());
					heBean.setQuestionariohe(listaQuestionario.get(i));
					if (listaQuestionario.get(i).isAutorizado()) {
						heBean.setSituacao("Questionario Autorizado");
					} else
						heBean.setSituacao("Questionario");
					heBean.setStatus(listaQuestionario.get(i).getSituacao());
					heBean.setData(listaQuestionario.get(i).getDataenvio());
					heBean.setIdVenda(listaQuestionario.get(i).getVendas().getIdvendas());
					heBean.setAutorizado(listaQuestionario.get(i).isAutorizado());
					listaHe.add(heBean);
				}
			}
		}
	}

	public String corNome(ListaHeBean hebean) {
		if (hebean.getHe() != null) {
			if (hebean.getHe().getVendas().getSituacao().equals("CANCELADA")) {
				return "color:#808080;text-decoration: line-through;";
			}
		}else if (hebean.getQuestionariohe().getSituacao().equals("CANCELADA")) {
			return "color:#808080;text-decoration: line-through;";
		}
		return "color:#000000;";
	}

	public String cor(ListaHeBean hebean) {
		if (hebean.getHe() != null) {
			if (hebean.getHe().getVendas().getSituacao().equals("CANCELADA")) {
				return "color:#808080;";
			}
		}else if (hebean.getQuestionariohe().getSituacao().equals("CANCELADA")) {
			return "color:#808080;";
		}
		return "color:#000000;";
	}

	public boolean habilitarBtnEditar(ListaHeBean hebean) {
		if (hebean.getHe() != null) {
			if (!hebean.getHe().getVendas().getSituacao().equals("CANCELADA")) {
				return false;
			}
		}else if (hebean.getQuestionariohe().getSituacao().equals("CANCELADA")) {
			return false;
		}
		return true;
	}

	public void limpar() {
		dataInicio = null;
		dataTermino = null;
		if (usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
			unidadenegocio = null;
		} else {
			unidadenegocio = usuarioLogadoMB.getUsuario().getUnidadenegocio();
		}
		nomeCliente = "";
		idvenda = 0;
		situacao = "sn";
		gerarListaHe();
	}

	public void pesquisar() {
		// questionario
		List<Questionariohe> listaQuestionario = null;
		if (situacao.equalsIgnoreCase("sn") || situacao.equalsIgnoreCase("Questionario")
				|| situacao.equalsIgnoreCase("Questionario Autorizado")) {
			String sqlQuestionario = "Select q From Questionariohe q where q.cliente.nome like '" + nomeCliente + "%'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sqlQuestionario = sqlQuestionario + " and q.cliente.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
				Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
						+usuarioLogadoMB.getUsuario().getIdusuario());
				if(acessounidade!=null) {
					if(!acessounidade.isEmissaoconsulta()) {
						sqlQuestionario = sqlQuestionario + " and q.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}else if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sqlQuestionario = sqlQuestionario + " and q.cliente.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio();
			}
			if (dataInicio != null && dataTermino != null) {
				sqlQuestionario = sqlQuestionario + " and q.dataenvio>='" + Formatacao.ConvercaoDataSql(dataInicio)
						+ "' and q.dataenvio<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			}
			if (idvenda > 0) {
				sqlQuestionario = sqlQuestionario + " and q.vendas.idvendas=" + idvenda;
			}
			if (situacao.equalsIgnoreCase("Questionario Autorizado")) {
				sqlQuestionario = sqlQuestionario + " and q.autorizado=true";
			} else if (situacao.equalsIgnoreCase("Questionario")) {
				sqlQuestionario = sqlQuestionario + " and q.autorizado=false";
			}
			sqlQuestionario = sqlQuestionario + " order by q.dataenvio desc";
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaQuestionario = questionarioHeFacade.listar(sqlQuestionario);
		}

		// ficha inscricao
		List<He> listaficha1 = null;
		if (situacao.equalsIgnoreCase("sn") || situacao.equalsIgnoreCase("Ficha Inscrição")
				|| situacao.equalsIgnoreCase("Ficha Inscrição Aprovada")) {
			String sqlFicha1 = "Select h From He h where h.vendas.cliente.nome like '" + nomeCliente + "%'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
				Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
						+usuarioLogadoMB.getUsuario().getIdusuario());
				if(acessounidade!=null) {
					if(!acessounidade.isEmissaoconsulta()) {
						sqlFicha1 = sqlFicha1 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}else if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio();
			}
			if (dataInicio != null && dataTermino != null) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
						+ "' and h.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			}
			if (situacao.equalsIgnoreCase("Ficha Inscrição Aprovada")) {
				sqlFicha1 = sqlFicha1 + " and h.aprovado=true";
			} else if (situacao.equalsIgnoreCase("Ficha Inscrição")) {
				sqlFicha1 = sqlFicha1 + " and h.aprovado=false";
			}
			if (idvenda > 0) {
				sqlFicha1 = sqlFicha1 + " and h.vendas.idvendas=" + idvenda;
			}
			sqlFicha1 = sqlFicha1 + " and h.fichafinal=FALSE order by h.vendas.dataVenda desc";
			HeFacade heFacade = new HeFacade();
			listaficha1 = heFacade.listar(sqlFicha1);
		}
		// ficha final
		List<He> listaficha2 = null;
		if (situacao.equalsIgnoreCase("sn") || situacao.equalsIgnoreCase("Ficha Final")) {
			String sqlFicha2 = "Select h From He h where h.vendas.cliente.nome like '" + nomeCliente + "%'";
			if (!usuarioLogadoMB.getUsuario().getTipo().equalsIgnoreCase("Gerencial")) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ usuarioLogadoMB.getUsuario().getUnidadenegocio().getIdunidadeNegocio();
				Acessounidade acessounidade = acessoUnidadeDao.consultar("SELECT a FROM Acessounidade a WHERE a.usuario.idusuario="
						+usuarioLogadoMB.getUsuario().getIdusuario());
				if(acessounidade!=null) {
					if(!acessounidade.isEmissaoconsulta()) {
						sqlFicha2 = sqlFicha2 + " and h.vendas.usuario.idusuario="+usuarioLogadoMB.getUsuario().getIdusuario();
					}
				}
			}if (unidadenegocio != null && unidadenegocio.getIdunidadeNegocio() != null) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.unidadenegocio.idunidadeNegocio="
						+ unidadenegocio.getIdunidadeNegocio();
			}
			if (dataInicio != null && dataTermino != null) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.dataVenda>='" + Formatacao.ConvercaoDataSql(dataInicio)
						+ "' and h.vendas.dataVenda<='" + Formatacao.ConvercaoDataSql(dataTermino) + "'";
			} 
			if (idvenda > 0) {
				sqlFicha2 = sqlFicha2 + " and h.vendas.idvendas=" + idvenda;
			}
			sqlFicha2 = sqlFicha2 + " and h.fichafinal=TRUE order by h.vendas.dataVenda desc";
			HeFacade heFacade = new HeFacade();
			listaficha2 = heFacade.listar(sqlFicha2);
		}
		listaHe = new ArrayList<ListaHeBean>();
		ListaHeBean heBean; 
		if (listaficha1 != null && listaficha1.size() > 0) {
			for (int i = 0; i < listaficha1.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha1.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha1.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha1.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha1.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha1.get(i));
				heBean.setQuestionariohe(listaficha1.get(i).getQuestionariohe());
				if (listaficha1.get(i).isAprovado()) {
					heBean.setSituacao("Ficha de Inscrição Aprovada");
				} else
					heBean.setSituacao("Ficha de Inscrição");
				heBean.setStatus(listaficha1.get(i).getVendas().getSituacao());
				heBean.setData(listaficha1.get(i).getVendas().getDataVenda());
				heBean.setIdVenda(listaficha1.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha1.get(i).isAprovado());
				listaHe.add(heBean);
			}
		} 
		if (listaficha2 != null && listaficha2.size() > 0) {
			for (int i = 0; i < listaficha2.size(); i++) {
				heBean = new ListaHeBean();
				heBean.setConsultor(listaficha2.get(i).getVendas().getUsuario().getNome());
				heBean.setFornecedor(listaficha2.get(i).getVendas().getFornecedorcidade().getFornecedor().getNome());
				heBean.setUnidade(listaficha2.get(i).getVendas().getUnidadenegocio().getNomerelatorio());
				heBean.setNomecliente(listaficha2.get(i).getVendas().getCliente().getNome());
				heBean.setHe(listaficha2.get(i));
				heBean.setQuestionariohe(listaficha2.get(i).getQuestionariohe());
				heBean.setSituacao("Ficha Final");  
				heBean.setStatus(listaficha2.get(i).getVendas1().getSituacao());
				heBean.setData(listaficha2.get(i).getVendas1().getDataVenda());
				heBean.setIdVenda(listaficha2.get(i).getVendas().getIdvendas());
				heBean.setAutorizado(listaficha2.get(i).isAprovado());
				listaHe.add(heBean);
			}
		}
		if (listaQuestionario != null && listaQuestionario.size() > 0) {
			for (int i = 0; i < listaQuestionario.size(); i++) {
				boolean ok=true;
				if (listaficha2 != null && listaficha2.size() > 0) {
					for (int j = 0; j < listaficha2.size(); j++) {
						int id= listaficha2.get(j).getVendas().getIdvendas();
						if(id==listaQuestionario.get(i).getVendas().getIdvendas()){
							ok=false;
						}
					}
				}
				if (listaficha1 != null && listaficha1.size() > 0) {
					for (int j = 0; j < listaficha1.size(); j++) {
						int id= listaficha1.get(j).getVendas().getIdvendas();
						if(id==listaQuestionario.get(i).getVendas().getIdvendas()){
							ok=false;
						}
					}   
				}
				if(ok){
					heBean = new ListaHeBean();
					heBean.setConsultor(listaQuestionario.get(i).getUsuario().getNome());
					heBean.setFornecedor("");
					heBean.setUnidade(listaQuestionario.get(i).getUsuario().getUnidadenegocio().getNomerelatorio());
					heBean.setNomecliente(listaQuestionario.get(i).getCliente().getNome());
					heBean.setQuestionariohe(listaQuestionario.get(i));
					if (listaQuestionario.get(i).isAutorizado()) {
						heBean.setSituacao("Questionario Autorizado");
					} else
						heBean.setSituacao("Questionario");
					heBean.setStatus(listaQuestionario.get(i).getSituacao());
					heBean.setData(listaQuestionario.get(i).getDataenvio());
					heBean.setIdVenda(listaQuestionario.get(i).getVendas().getIdvendas());
					heBean.setAutorizado(listaQuestionario.get(i).isAutorizado());
					listaHe.add(heBean);
				}
			}
		}
	}

	public void autorizarQuestionario(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			listaHeBean.getHe().setAprovado(true);
			listaHeBean.setAutorizado(true);
			listaHeBean.setSituacao("Ficha de Inscrição Aprovada");
			HeFacade heFacade = new HeFacade();
			listaHeBean.setHe(heFacade.salvar(listaHeBean.getHe()));
			Mensagem.lancarMensagemInfo("Ficha de Inscrição autorizada!", "");
		} else if (listaHeBean.getQuestionariohe() != null) {
			listaHeBean.getQuestionariohe().setAutorizado(true);
			listaHeBean.setAutorizado(true);
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaHeBean.setSituacao("Questionario Autorizado");
			listaHeBean.setQuestionariohe(questionarioHeFacade.salvar(listaHeBean.getQuestionariohe()));
			Mensagem.lancarMensagemInfo("Questionario autorizado!", "");
		}
	}

	public String retornarimagem(String status) {
		if (status.equalsIgnoreCase("FINALIZADO")) {
			return "../../resources/img/finalizadoFicha.png";
		}else if (status.equalsIgnoreCase("FINALIZADA")) {
			return "../../resources/img/finalizadoFicha.png";
		} else if (status.equalsIgnoreCase("ANDAMENTO")) {
			return "../../resources/img/amarelaFicha.png";
		} else if (status.equalsIgnoreCase("CANCELADA")) {
			return "../../resources/img/fichaCancelada.png";
		} else {
			return "../../resources/img/processoFicha.png";   
		}
	}

	public boolean desabilitarBtnAutorizar(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			if (listaHeBean.isAutorizado()) {
				return true;
			} else {
				return false;
			}
		} else if (listaHeBean.getQuestionariohe() != null) {
			if (listaHeBean.getQuestionariohe().isAutorizado()) {
				return true;
			} else {
				return false;
			}
		} else
			return false;
	}

	public boolean desabilitarBtnFichaInscricao(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null) {
			return true;
		} else if (listaHeBean.getQuestionariohe() != null) {
			return false;
		} else
			return false;
	}
	
	public boolean desabilitarBtnContrato(ListaHeBean listaHeBean) {
		if (listaHeBean.getHe() != null && listaHeBean.getHe().isFichafinal()) {
			return true;
		} else return false;
	}

	public String gerarRelatorioFicha(ListaHeBean listaHeBean) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio;
		if(listaHeBean.getHe().isFichafinal()){
			caminhoRelatorio = "/reports/higherEducation/FichaFinalHe1.jasper";
		}else{
			caminhoRelatorio = "/reports/higherEducation/FichaInscricaoHe1.jasper";
		} 
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
		if(listaHeBean.getHe().isFichafinal()){
			parameters.put("idvendas", listaHeBean.getHe().getVendas1().getIdvendas());
		}else{
			parameters.put("idvendas", listaHeBean.getHe().getVendas().getIdvendas()); 
		}
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"Ficha de Inscrição-" + listaHeBean.getHe().getVendas().getCliente().getNome() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}

	public String gerarRelatorioContrato(ListaHeBean listaHeBean) throws IOException {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		String caminhoRelatorio = "/reports/higherEducation/contratoHePagina01.jasper";
		Map parameters = new HashMap();
		parameters.put("SUBREPORT_DIR", servletContext.getRealPath("//reports//higherEducation//"));
		parameters.put("idvendas", listaHeBean.getHe().getVendas1().getIdvendas());
		File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
		BufferedImage logo = ImageIO.read(f);
		parameters.put("logo", logo);
		GerarRelatorio gerarRelatorio = new GerarRelatorio();
		try {
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"Contrato de Higher Education-" + listaHeBean.getHe().getVendas1().getIdvendas() + ".pdf", null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (JRException ex1) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex1);
		} catch (IOException ex) {
			Logger.getLogger(HigherEducationMB.class.getName()).log(Level.SEVERE, null, ex);
		}
		return "";
	}
	
	public String documentacao(ListaHeBean listaHeBean) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false); 
		session.setAttribute("vendas", listaHeBean.getQuestionariohe().getVendas());
		if(listaHeBean.getHe()!=null && listaHeBean.getHe().getVendas1()!=null){
			session.setAttribute("vendas1", listaHeBean.getHe().getVendas1());
		}
		String voltar = "consquestionarioHe";
		session.setAttribute("voltar", voltar);
		return "consArquivos"; 
	}
	
	public String informacoes(ListaHeBean listaHeBean) { 
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
		session.setAttribute("vendas", listaHeBean.getQuestionariohe().getVendas());
		String voltar = "consquestionarioHe";
		session.setAttribute("voltar", voltar);
		return "consLogVenda"; 
	}
	
	public String cancelarVenda(ListaHeBean listaHeBean) {
		if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("FINALIZADA")
				|| listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("ANDAMENTO")) {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put("contentWidth", 400);
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("venda", listaHeBean.getQuestionariohe().getVendas());
			if(listaHeBean.getHe()!=null && listaHeBean.getHe().getVendas1()!=null){
				session.setAttribute("venda1", listaHeBean.getHe().getVendas1());
			}
			RequestContext.getCurrentInstance().openDialog("cancelarVenda", options, null);
		} else if (listaHeBean.getQuestionariohe().getVendas().getSituacao().equalsIgnoreCase("PROCESSO")) {
			VendasFacade vendasFacade = new VendasFacade();
			listaHeBean.getQuestionariohe().getVendas().setSituacao("CANCELADA");
			vendasFacade.salvar(listaHeBean.getQuestionariohe().getVendas());
			QuestionarioHeFacade questionarioHeFacade = new QuestionarioHeFacade();
			listaHeBean.getQuestionariohe().setSituacao("CANCELADA");
			listaHeBean.setQuestionariohe(questionarioHeFacade.salvar(listaHeBean.getQuestionariohe()));
			gerarListaHe();
		}
		return "";
	}
	
	public String boletos(ListaHeBean listaHeBean) {
		ContasReceberFacade contasReceberFacade = new ContasReceberFacade();
		String sql = "SELECT r FROM Contasreceber r where r.vendas.idvendas=";
		if(listaHeBean.getHe()!=null && listaHeBean.getHe().isFichafinal()){
			sql=sql	+listaHeBean.getHe().getVendas1().getIdvendas();
		}else{
			sql=sql	+listaHeBean.getQuestionariohe().getVendas().getIdvendas();
		} 
		sql=sql	+ " and r.tipodocumento='Boleto' and r.situacao<>'cc' " + " order by r.idcontasreceber";
		List<Contasreceber> listaContas = contasReceberFacade.listar(sql);
		if (listaContas != null) {
			if (listaContas.size() > 0) {
				GerarBoletoConsultorBean gerarBoletoConsultorBean = new GerarBoletoConsultorBean();
				if(listaHeBean.getHe()!=null && listaHeBean.getHe().isFichafinal()){
					gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(listaHeBean.getHe().getVendas1().getIdvendas()));
				}else{
					gerarBoletoConsultorBean.gerarBoleto(listaContas, String.valueOf(listaHeBean.getQuestionariohe().getVendas().getIdvendas()));
				}  
			} else {
				FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
				relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
			}
		} else {
			FacesMessage msg = new FacesMessage("Venda não possui forma de pagamento Boleto. ", " ");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			RelatorioErroBean relatorioErroBean = new RelatorioErroBean();
			relatorioErroBean.iniciarRelatorioErro("Venda não possui forma de pagamento Boleto.");
		}

		return "";
	}
	 
	
	public void expandirOpcoes(){
		if(expandirOpcoes){
			expandirOpcoes=false;
			esconderFicha=true;
		}else {
			expandirOpcoes=true;
			esconderFicha=false;
		}
	}
	
	public String retornarImgOpcoes(){
		if(expandirOpcoes){ 
			return "../../resources/img/esconderOpcoes.png";
		}else return "../../resources/img/expandirOpcoes.png";
	} 
	
	public String retornarTitleOpcoes(){
		if(expandirOpcoes){ 
			return "Esconder Opções";     
		}else return "Expandir Opções";
	} 

}
