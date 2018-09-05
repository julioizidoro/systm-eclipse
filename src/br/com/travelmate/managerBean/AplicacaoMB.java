package br.com.travelmate.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.travelmate.dao.VendasDao;
import br.com.travelmate.facade.CambioFacade;
import br.com.travelmate.facade.PaisFacade;
import br.com.travelmate.facade.ParametrosProdutosFacade;
import br.com.travelmate.facade.RegraVendaFacade;
import br.com.travelmate.facade.UsuarioPontosFacade;

import br.com.travelmate.model.Cambio;
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Parametrosprodutos;
import br.com.travelmate.model.Pontuacaovendas;
import br.com.travelmate.model.Regravenda;
import br.com.travelmate.model.Usuariopontos;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.Mensagem;

@Named
@ApplicationScoped
public class AplicacaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioLogadoMB usuarioLogadoMB;
	@Inject
	private VendasDao vendasDao;
	private String mascara8;
	private String mascara9;
	private Parametrosprodutos parametrosprodutos;  
	private String iata;
	private String uds;
	private String eur;
	private String gbp;
	private String cad;
	private String aud;
	private String nzd;
	private String chf;
	private String zar;
	private List<Cambio> listaCambio;
	private String datacambio;
	private boolean leituraCobranca;
	private List<Pais> listaPais;

	@PostConstruct
	public void init() {
		mascara8 = "(99)9999-9999";
		mascara9 = "(99)99999-9999";
		ParametrosProdutosFacade parametrosProdutosFacade = new ParametrosProdutosFacade();
		parametrosprodutos = parametrosProdutosFacade.consultar();
		carregarCambioDia(new Date());
		this.setLeituraCobranca(false);
	}

	public String getMascara8() {
		return mascara8;
	}

	public void setMascara8(String mascara8) {
		this.mascara8 = mascara8;
	}

	public String getMascara9() {
		return mascara9;
	}

	public void setMascara9(String mascara9) {
		this.mascara9 = mascara9;
	}

	public Parametrosprodutos getParametrosprodutos() {
		return parametrosprodutos;
	}

	public void setParametrosprodutos(Parametrosprodutos parametrosprodutos) {
		this.parametrosprodutos = parametrosprodutos;
	}

	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public String getUds() {
		return uds;
	}

	public void setUds(String uds) {
		this.uds = uds;
	}

	public String getEur() {
		return eur;
	}

	public void setEur(String eur) {
		this.eur = eur;
	}

	public String getGbp() {
		return gbp;
	}

	public void setGbp(String gbp) {
		this.gbp = gbp;
	}

	public String getCad() {
		return cad;
	}

	public void setCad(String cad) {
		this.cad = cad;
	}

	public String getAud() {
		return aud;
	}

	public void setAud(String aud) {
		this.aud = aud;
	}

	public String getNzd() {
		return nzd;
	}

	public void setNzd(String nzd) {
		this.nzd = nzd;
	}

	public String getChf() {
		return chf;
	}

	public void setChf(String chf) {
		this.chf = chf;
	}

	public String getZar() {
		return zar;
	}

	public void setZar(String zar) {
		this.zar = zar;
	}

	public List<Cambio> getListaCambio() {
		return listaCambio;
	}

	public void setListaCambio(List<Cambio> listaCambio) {
		this.listaCambio = listaCambio;
	}

	public String getDatacambio() {
		return datacambio;
	}

	public void setDatacambio(String datacambio) {
		this.datacambio = datacambio;
	}

	

	public boolean isLeituraCobranca() {
		return leituraCobranca;
	}

	public void setLeituraCobranca(boolean leituraCobranca) {
		this.leituraCobranca = leituraCobranca;
	}
	
	

	public List<Pais> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
	}

	public String validarMascaraTelefone(boolean digitosTelefone) {
		if (digitosTelefone) {
			return getMascara9();
		} else {
			return getMascara8();
		}
	}

	public boolean checkBoxTelefone(int numeroTelefone, String qntdNumTelefone) {
		if (numeroTelefone == 9) {
			if (qntdNumTelefone != null && qntdNumTelefone.length() > 0) {
				if (qntdNumTelefone.length() < 14) {
					return false;
				}
			}
			return true;
		} else {
			if (qntdNumTelefone != null && qntdNumTelefone.length() > 0) {
				if (qntdNumTelefone.length() > 13) {
					return true;
				}
			}
			return false;
		}
	}

	public void carregarCambioDia(Date datacambiohoje) {
		String sql = "Select c from Cambio c where ";
		listarPais();
		for (int i=0;i<listaPais.size();i++) {
			sql = sql + " (c.data='" + Formatacao.ConvercaoDataSql(listaPais.get(i).getDatacambio()) + "'  and c.pais.idpais= " + listaPais.get(i).getIdpais() + ")";
			if (listaPais.size()>(i+1)) {
				sql = sql + " or ";
			}
		}
		sql = sql + " order by c.pais";
		CambioFacade cambioFacade = new CambioFacade();
		listaCambio = cambioFacade.listarCambio(sql);
	/*	int contador = 0;
		if ((listaCambio != null) && (listaCambio.size() == 0)) {
			listaCambio = null;
		}
	*/	/*if (listaCambio == null) {
			while (listaCambio == null) {
				try {
					data = Formatacao.SubtarirDatas(new Date(), contador, "yyyy/MM/dd");
				} catch (Exception ex) {
					Logger.getLogger(UsuarioLogadoMB.class.getName()).log(Level.SEVERE, null, ex);
				}
				listaCambio = cambioFacade.listar(data);
				if ((listaCambio != null) && (listaCambio.size() == 0)) {
					listaCambio = null;
				}
				contador++;
			}
		}*/

		if (listaCambio == null) {
			datacambio = "Erro";
			iata = "0,0000";
			uds = "0,0000";
			eur = "0,0000";
			gbp = "0,0000";
			cad = "0,0000";
			aud = "0,0000";
			nzd = "0,0000";
			chf = "0,0000";
		} else {
			boolean liberar = true;
			for (int i = 0; i < listaCambio.size(); i++) {
				if(listaCambio.get(i).getValor()==0 && listaCambio.get(i).getMoedas().getIdmoedas()!=8){
					liberar=false;
				}
			}
			if(!liberar){
				Mensagem.lancarMensagemErro("Atenção!", "Moedas com valores zerados!");
			}
		}

	}
	
	public String retornarDataCambio() {
		return Formatacao.ConvercaoDataPadrao(usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getDatacambio());
	}
	
	public String retornarCambio(String moeda) {
		if (listaCambio != null) {
			for (int i = 0; i < listaCambio.size(); i++) {
				String moedacambio = listaCambio.get(i).getMoedas().getSigla();
				int idPaisCambio = listaCambio.get(i).getPais().getIdpais();
				int idPaisUnidade = usuarioLogadoMB.getUsuario().getUnidadenegocio().getPais().getIdpais();
				if (moeda.equalsIgnoreCase(moedacambio) && (idPaisCambio == idPaisUnidade)) {
					return Formatacao.formatarValorCambio(listaCambio.get(i).getValor());
				}
			}
		}
		return "0,0000";
	}

	public void pontuarRunners(Vendas venda, int numeroSemanas, String programa) {
		Usuariopontos usuariopontos = new Usuariopontos();
		int ano = Formatacao.getAnoData(venda.getDataVenda());
		int mes = Formatacao.getMesData(venda.getDataVenda()) + 1;
		usuariopontos.setAno(ano);
		usuariopontos.setMes(mes);
		usuariopontos.setUsuario(venda.getUsuario());
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=" + venda.getProdutos().getIdprodutos()
				+ " and r.situacao=TRUE";
		List<Regravenda> listaRegras = regraVendaFacade.lista(sql);
		int ponto = 0;
		if (listaRegras != null) {
			if (listaRegras.size() > 0) {
				for (int i = 0; i < listaRegras.size(); i++) {
					ponto = ponto + valirdarRegra(listaRegras.get(i), venda, numeroSemanas, programa, usuariopontos);
				}

			} else {
				Regravenda r = regraVendaFacade.consultar("SELECT r FROM Regravenda r where r.idregravenda=1");
				Pontuacaovendas pontuacaovendas = new Pontuacaovendas();
				pontuacaovendas.setRegravenda(r);
				pontuacaovendas.setUsuariopontos(usuariopontos);
				usuariopontos.getPontuacaovendasList().add(pontuacaovendas);
				ponto = 1;
			}
		}

		usuariopontos.setPontos(ponto);
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		usuarioPontosFacade.salvar(usuariopontos);
		
		vendasDao.salvar(venda);
	}

	public int valirdarRegra(Regravenda regra, Vendas venda, int numeroSemanas, String programa,
			Usuariopontos usuariopontos) {
		if (regra.getValorinicial() > 0) {
			if (venda.getValor() < regra.getValorinicial()) {
				return 0;
			}
		}
		if (regra.getNumerosemanasinicial() > 0) {
			if (numeroSemanas < regra.getNumerosemanasinicial()) {
				return 0;
			}
			if (numeroSemanas > regra.getNumerosemanafinal()) {
				return 0;
			}
		}
		if (regra.getPrograma().length() > 0) {
			if (!programa.equalsIgnoreCase(regra.getPrograma())) {
				return 0;
			}
		}
		if (regra.getPais() > 0) {
			if (venda.getFornecedorcidade().getCidade().getPais().getIdpais() != regra.getPais()) {
				return 0;
			}
		}
		if (regra.getCidade() > 0) {
			if (venda.getFornecedorcidade().getCidade().getIdcidade() != regra.getCidade()) {
				return 0;
			}
		}
		if (regra.getFornecedor() > 0) {
			if (venda.getFornecedorcidade().getFornecedor().getIdfornecedor() != regra.getFornecedor()) {
				return 0;
			}
		}
		Pontuacaovendas pontuacaovendas = new Pontuacaovendas();
		pontuacaovendas.setRegravenda(regra);
		pontuacaovendas.setUsuariopontos(usuariopontos);
		usuariopontos.getPontuacaovendasList().add(pontuacaovendas);
		return regra.getPonto();
	}
	
	public void listarPais(){
		PaisFacade paisFacade = new PaisFacade();
		listaPais = paisFacade.listarModelo("Select p from Pais p where p.possuifranquia=1");
		if (listaPais==null) {
			listaPais = new ArrayList<Pais>();
		}
	}

}
