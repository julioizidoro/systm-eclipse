package br.com.travelmate.managerBean.financeiro.relatorios;

import br.com.travelmate.facade.CartaoCreditoFacade;
import br.com.travelmate.facade.PlanoContaFacade; 
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.model.Cartaocredito;
import br.com.travelmate.model.Cartaocreditolancamento;
import br.com.travelmate.model.Planoconta; 
import br.com.travelmate.model.Usuario;
import br.com.travelmate.util.Formatacao;
import br.com.travelmate.util.GerarRelatorio;
import br.com.travelmate.util.Mensagem;

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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

@Named
@ViewScoped
public class RelatorioConferenciaCartaoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Cartaocredito> listaCartaoCredito;
	private Cartaocredito cartaocredito;
	private Date dataLancamentoInicio;
	private Date dataLancamentoFinal;
	private Usuario usuario;
	private List<Usuario> listaUsuario;
	private Planoconta planoconta;
	private List<Planoconta> listaPlanoConta;
	private List<Cartaocreditolancamento> listaLancamento;

	@PostConstruct()
	public void init() {
		gerarlistaCartaoCredito(); 
		gerarListaPlanoConta();
		gerarListaUsuario(); 
	}

	public List<Cartaocredito> getListaCartaoCredito() {
		return listaCartaoCredito;
	}

	public void setListaCartaoCredito(List<Cartaocredito> listaCartaoCredito) {
		this.listaCartaoCredito = listaCartaoCredito;
	}

	public Cartaocredito getCartaocredito() {
		return cartaocredito;
	}

	public void setCartaocredito(Cartaocredito cartaocredito) {
		this.cartaocredito = cartaocredito;
	}

	public Date getDataLancamentoInicio() {
		return dataLancamentoInicio;
	}

	public void setDataLancamentoInicio(Date dataLancamentoInicio) {
		this.dataLancamentoInicio = dataLancamentoInicio;
	}

	public Date getDataLancamentoFinal() {
		return dataLancamentoFinal;
	}

	public void setDataLancamentoFinal(Date dataLancamentoFinal) {
		this.dataLancamentoFinal = dataLancamentoFinal;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Planoconta getPlanoconta() {
		return planoconta;
	}

	public void setPlanoconta(Planoconta planoconta) {
		this.planoconta = planoconta;
	}

	public List<Planoconta> getListaPlanoConta() {
		return listaPlanoConta;
	}

	public void setListaPlanoConta(List<Planoconta> listaPlanoConta) {
		this.listaPlanoConta = listaPlanoConta;
	}

	public List<Cartaocreditolancamento> getListaLancamento() {
		return listaLancamento;
	}

	public void setListaLancamento(List<Cartaocreditolancamento> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}
 

	public String gerarSql() {
  		String sql = "select distinct cartaocreditolancamento.descricao, cartaocreditolancamento.data as dataLancamento," 
				+ " cartaocreditolancamento.conferido, cartaocredito.nome as cartao, planoconta.descricao as planoconta, usuario.nome as usuario,"
				+ " cartaocreditolancamento.valorlancado, moedas.sigla as moeda, cartaocreditolancamento.valorcambio"
				+ " FROM cartaocreditolancamento "
				+ " join cartaocredito on cartaocreditolancamento.cartaocredito_idcartaocredito = cartaocredito.idcartaocredito"
				+ " join planoconta on cartaocreditolancamento.planoconta_idplanoconta=planoconta.idplanoconta"
				+ " join moedas on cartaocreditolancamento.moedas_idmoedas=moedas.idmoedas"
				+ " join usuario on cartaocreditolancamento.usuario_idusuario=usuario.idusuario"
				+ " where conferido=true and cartaocreditolancamento.data>='"
				+ Formatacao.ConvercaoDataSql(dataLancamentoInicio) + "' and cartaocreditolancamento.data<='"
				+ Formatacao.ConvercaoDataSql(dataLancamentoFinal) + "'";
		if (usuario != null && usuario.getIdusuario() != null) {
			sql = sql + " and usuario.idusuario=" + usuario.getIdusuario();
		}
		if (planoconta != null && planoconta.getIdplanoconta() != null) {
			sql = sql + " and planoconta.idplanoconta=" + planoconta.getIdplanoconta();
		}  
		if (cartaocredito != null && cartaocredito.getIdcartaocredito() != null) {
			sql = sql + " and cartaocredito.idcartaocredito=" + cartaocredito.getIdcartaocredito();
		} 
		sql = sql + " order by cartaocreditolancamento.data, cartaocredito.nome";
		return sql;
	}

	public String gerarRelatorio() throws SQLException, IOException {
		if(dataLancamentoInicio==null || dataLancamentoFinal==null){
			Mensagem.lancarMensagemInfo("Atençao!", "Período obrigatório.");
		}else{
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			String caminhoRelatorio = "/reports/financeiro/conferenciaCartaoCredito.jasper";
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("sql", gerarSql()); 
			File f = new File(servletContext.getRealPath("/resources/img/logoRelatorio.jpg"));
			BufferedImage logo = ImageIO.read(f);
			parameters.put("logo", logo);
			GerarRelatorio gerarRelatorio = new GerarRelatorio();
			try {
				gerarRelatorio.gerarRelatorioSqlPDF(caminhoRelatorio, parameters,
						"mapaVendas" + Formatacao.ConvercaoDataSql(new Date()) + ".pdf", null);
			} catch (JRException ex) {
				Logger.getLogger(RelatorioConferenciaCartaoMB.class.getName()).log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(RelatorioConferenciaCartaoMB.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return "";
	}

	public String fechar() {
		RequestContext.getCurrentInstance().closeDialog(null);
		return "";
	}
	

	public void gerarlistaCartaoCredito(){
		CartaoCreditoFacade cartaoCreditoFacade = new CartaoCreditoFacade();
        listaCartaoCredito = cartaoCreditoFacade.listar();
        if (listaCartaoCredito==null){
        	listaCartaoCredito = new ArrayList<Cartaocredito>();
        }
    } 
	
	public void gerarListaPlanoConta(){
        PlanoContaFacade planoContaFacade = new PlanoContaFacade();
        listaPlanoConta = planoContaFacade.listar("");
        if (listaPlanoConta==null){
            listaPlanoConta = new ArrayList<Planoconta>();
        }
    }
	
	public void gerarListaUsuario(){
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        String sql ="select u from Usuario u where u.situacao='Ativo' order by u.nome";
        listaUsuario = usuarioFacade.listar(sql);
        if (listaUsuario==null){
        	listaUsuario = new ArrayList<Usuario>();
        }
    }
 
}
