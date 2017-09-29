package br.com.travelmate.managerBean.controleSolicitacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.travelmate.facade.DepartamentoFacade;
import br.com.travelmate.facade.TiSolicitacoesFacade;
import br.com.travelmate.model.Departamento;
import br.com.travelmate.model.Tisolicitacoes;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ViewScoped
public class RelatorioSolicitacoesMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Departamento departamento;
	private List<Departamento> listaDepartamento;
	private Date dataInicial;
	private Date dataFinal;

	@PostConstruct
	public void init() {
		departamento = new Departamento();
		gerarListaDepartamentos();
		try {
			// 45 dias antes
			dataInicial = Formatacao.SomarDiasDatas(new Date(), -45);
			// 45 dias depois
			dataFinal = Formatacao.SomarDiasDatas(new Date(), 45);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}

	public String carregarListaSolicitacoes() {
		TiSolicitacoesFacade tiSolicitacoesFacade = new TiSolicitacoesFacade();
		String sql = "SELECT t FROM Tisolicitacoes t WHERE t.liberar=true";
		if (dataInicial != null && dataFinal != null) {
			sql = sql + " AND t.datasolicitacao>='" + Formatacao.ConvercaoDataSql(dataInicial)
					+ "' AND t.datasolicitacao<='" + Formatacao.ConvercaoDataSql(dataFinal) + "'";
		}
		if (departamento != null && departamento.getIddepartamento() != null) {
			sql = sql + " AND t.usuario.departamento.iddepartamento=" + departamento.getIddepartamento();
		}
		sql = sql + " ORDER BY t.datasolicitacao";
		List<Tisolicitacoes> listaSolicitacoes = tiSolicitacoesFacade.listar(sql);
		if (listaSolicitacoes == null) {
			Mensagem.lancarMensagemInfo("Atenção!", "Nenhuma solicitação encontrada.");
			return "";
		} else {
			for (int i = 0; i < listaSolicitacoes.size(); i++) {
				listaSolicitacoes.get(i).setCor(verificarCor(listaSolicitacoes.get(i)));
			}
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
			session.setAttribute("listaSolicitacoes", listaSolicitacoes);
			return "relatorioSolicitacoesFinal";
		}
	}

	public String verificarCor(Tisolicitacoes tisolicitacoes) { 
		if (tisolicitacoes.getSituacao().equalsIgnoreCase("Concluida")) {
			return "color:#006400;";
		} else if (tisolicitacoes.getSituacao().equalsIgnoreCase("Nova")) {
			return "color:#0000CD;";
		} else if (tisolicitacoes.getDataprevisao()!=null
				&& tisolicitacoes.getDataprevisao().before(new Date())) {
			return "color:#8B0000;";
		}
		return "";
	}

	public void gerarListaDepartamentos() {
		DepartamentoFacade departamentoFacade = new DepartamentoFacade();
		String sql = "SELECT d FROM Departamento d WHERE d.lista=TRUE";
		listaDepartamento = departamentoFacade.listar(sql);
		if (listaDepartamento == null) {
			listaDepartamento = new ArrayList<>();
		}
	}
}
