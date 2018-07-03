package br.com.travelmate.bean;

import java.util.Date;
import java.util.List;

import javax.inject.Named; 

import br.com.travelmate.facade.MateFaturamentoAnualFacade;
import br.com.travelmate.facade.MetaFaturamentoMensalFacade; 
import br.com.travelmate.facade.RegraVendaFacade;
import br.com.travelmate.facade.UnidadeNegocioFacade;
import br.com.travelmate.facade.UsuarioPontosFacade;
import br.com.travelmate.facade.VendaProdutoFacade;
import br.com.travelmate.model.Metafaturamentoanual;
import br.com.travelmate.model.Metasfaturamentomensal; 
import br.com.travelmate.model.Pacotes; 
import br.com.travelmate.model.Pacotetrecho; 
import br.com.travelmate.model.Regravenda;
import br.com.travelmate.model.Unidadenegocio;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Usuariopontos;
import br.com.travelmate.model.Vendaproduto;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

@Named
public class DashBoardBean {

	public void calcularNumeroVendasProdutos(Vendas venda, boolean cancelamento) {
		String sql = "";
		Vendaproduto vendaproduto;
		VendaProdutoFacade vendaProdutoFacade = new VendaProdutoFacade();
		String mesano = Formatacao.gerarCopetencia(new Date());
		if (venda.getUnidadenegocio().getIdunidadeNegocio() != 6) {
			sql = "SELECT v FROM Vendaproduto v where v.mesano='" + mesano + "' and v.unidadenegocio.idunidadeNegocio="
					+ venda.getUnidadenegocio().getIdunidadeNegocio();
			vendaproduto = vendaProdutoFacade.lista(sql);
			if (vendaproduto == null) {
				vendaproduto = new Vendaproduto();
				vendaproduto.setIntercambio(0);
				vendaproduto.setMesano(mesano);
				vendaproduto.setProduto(0);
				vendaproduto.setTurismo(0);
				vendaproduto.setUnidadenegocio(venda.getUnidadenegocio());
			}
			if (venda.getProdutos().getIdprodutos() == 1 || venda.getProdutos().getIdprodutos() == 4
					|| venda.getProdutos().getIdprodutos() == 5 || venda.getProdutos().getIdprodutos() == 9
					|| venda.getProdutos().getIdprodutos() == 10 || venda.getProdutos().getIdprodutos() == 11
					|| venda.getProdutos().getIdprodutos() == 13 || venda.getProdutos().getIdprodutos() == 16
					|| venda.getProdutos().getIdprodutos() == 20) {
				if (cancelamento) {
					vendaproduto.setIntercambio(vendaproduto.getIntercambio() - 1);
				} else {
					vendaproduto.setIntercambio(vendaproduto.getIntercambio() + 1);
				}
			} else if (venda.getProdutos().getIdprodutos() == 7) {
				if (cancelamento) {
					vendaproduto.setTurismo(vendaproduto.getTurismo() - 1);
				} else {
					vendaproduto.setTurismo(vendaproduto.getTurismo() + 1);
				}
			} else if (venda.getProdutos().getIdprodutos() == 2 || venda.getProdutos().getIdprodutos() == 3
					|| venda.getProdutos().getIdprodutos() == 6) {
				if (cancelamento) {
					vendaproduto.setProduto(vendaproduto.getProduto() - 1);
				} else {
					vendaproduto.setProduto(vendaproduto.getProduto() + 1);
				}

			}
			vendaProdutoFacade.salvar(vendaproduto);
		}

			// salvar Matriz
		if (venda.getUnidadenegocio().getIdunidadeNegocio() != 6) {
			sql = "SELECT v FROM Vendaproduto v where v.mesano='" + mesano + "' and v.unidadenegocio.idunidadeNegocio=6";
			vendaproduto = vendaProdutoFacade.lista(sql);
			if (vendaproduto == null) {
				vendaproduto = new Vendaproduto();
				vendaproduto.setIntercambio(0);
				vendaproduto.setMesano(mesano);
				vendaproduto.setProduto(0);
				vendaproduto.setTurismo(0);
				UnidadeNegocioFacade unidadeNegocioFacade = new UnidadeNegocioFacade();
				Unidadenegocio unidadenegocio = unidadeNegocioFacade.consultar(6);
				vendaproduto.setUnidadenegocio(unidadenegocio);
			}
			if (venda.getProdutos().getIdprodutos() == 1 || venda.getProdutos().getIdprodutos() == 4
					|| venda.getProdutos().getIdprodutos() == 5 || venda.getProdutos().getIdprodutos() == 9
					|| venda.getProdutos().getIdprodutos() == 10 || venda.getProdutos().getIdprodutos() == 11
					|| venda.getProdutos().getIdprodutos() == 13 || venda.getProdutos().getIdprodutos() == 16
					|| venda.getProdutos().getIdprodutos() == 20) {
				if (cancelamento) {
					vendaproduto.setIntercambio(vendaproduto.getIntercambio() - 1);
				} else {
					vendaproduto.setIntercambio(vendaproduto.getIntercambio() + 1);
				}
			} else if (venda.getProdutos().getIdprodutos() == 7) {
				if (cancelamento) {
					vendaproduto.setTurismo(vendaproduto.getTurismo() - 1);
				} else {
					vendaproduto.setTurismo(vendaproduto.getTurismo() + 1);
				}
			} else if (venda.getProdutos().getIdprodutos() == 2 || venda.getProdutos().getIdprodutos() == 3
					|| venda.getProdutos().getIdprodutos() == 6) {
				if (cancelamento) {
					vendaproduto.setProduto(vendaproduto.getProduto() - 1);
				} else {
					vendaproduto.setProduto(vendaproduto.getProduto() + 1);
				}
			}
			vendaProdutoFacade.salvar(vendaproduto);
		}
	}

	public void calcularMetaAnual(Vendas venda, float valorantigo, boolean cancelamento) {
		int ano = Formatacao.getAnoData(new Date());
		MateFaturamentoAnualFacade mateFaturamentoAnualFacade = new MateFaturamentoAnualFacade();
		String sql = "SELECT m FROM Metafaturamentoanual m where m.ano=" + ano
				+ " and m.unidadenegocio.idunidadeNegocio=" + venda.getUnidadenegocio().getIdunidadeNegocio();
		Metafaturamentoanual meta = mateFaturamentoAnualFacade.getMeta(sql);
		if (meta == null) {
			meta = new Metafaturamentoanual();
			meta.setAno(ano);
			meta.setMes(0);
			meta.setPercentualalcancado(0.0f);
			meta.setUnidadenegocio(venda.getUnidadenegocio());
			meta.setMetaalcancada(0.0f);
		}
		if (cancelamento == false) {
			if (valorantigo > 0) {
				meta.setMetaalcancada(meta.getMetaalcancada() - valorantigo);
			}
			meta.setMetaalcancada(meta.getMetaalcancada() + venda.getValor());
		} else {
			meta.setMetaalcancada(meta.getMetaalcancada() - venda.getValor());
		}
		meta.setPercentualalcancado((meta.getMetaalcancada() / meta.getValormeta()) * 100);
		mateFaturamentoAnualFacade.salvar(meta);

		// matriz
		sql = "SELECT m FROM Metafaturamentoanual m where m.ano=" + ano + " and m.unidadenegocio.idunidadeNegocio=6";
		meta = mateFaturamentoAnualFacade.getMeta(sql);
		meta.setMetaalcancada(meta.getMetaalcancada() + venda.getValor());
		meta.setPercentualalcancado((meta.getMetaalcancada() / meta.getValormeta()) * 100);
		mateFaturamentoAnualFacade.salvar(meta);
	}

	public void calcularMetaMensal(Vendas venda, float valorantigo, boolean cancelamento) {
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		MetaFaturamentoMensalFacade metaFaturamentoMensalFacade = new MetaFaturamentoMensalFacade();
		String sql = "SELECT m FROM Metasfaturamentomensal m where m.mes=" + mes + " and m.ano=" + ano
				+ " and m.unidadenegocio.idunidadeNegocio=" + venda.getUnidadenegocio().getIdunidadeNegocio();
		Metasfaturamentomensal meta = metaFaturamentoMensalFacade.getMeta(sql);
		if (meta == null) {
			meta = new Metasfaturamentomensal();
			meta.setAno(ano);
			meta.setMes(mes);
			meta.setPercentualalcancado(0.0f);
			meta.setUnidadenegocio(venda.getUnidadenegocio());
			meta.setValoralcancado(0.0f);
			meta.setValormeta(0.0f);
			meta.setValormetasemana(0.0f);
		}
		if(meta.getValoralcancado()==null){
			meta.setValoralcancado(0.0f);
		}
		if (cancelamento == false) {
			if (valorantigo > 0) {
				meta.setValoralcancado(meta.getValoralcancado() - valorantigo);
			}
			meta.setValoralcancado(meta.getValoralcancado() + venda.getValor());
		} else {
			meta.setValoralcancado(meta.getValoralcancado() - venda.getValor());
		}
		if (meta.getValormeta() > 0) {
			meta.setPercentualalcancado((meta.getValoralcancado() / meta.getValormeta()) * 100);
		}
		metaFaturamentoMensalFacade.salvar(meta);

		if (venda.getUnidadenegocio().getIdunidadeNegocio() != 6) {
			// Calculo matriz
			meta = new Metasfaturamentomensal();
			sql = "SELECT m FROM Metasfaturamentomensal m where m.mes=" + mes + " and m.ano=" + ano
					+ " and m.unidadenegocio.idunidadeNegocio=6";
			meta = metaFaturamentoMensalFacade.getMeta(sql);
			if (cancelamento == false) {
				if (valorantigo > 0) {
					meta.setValoralcancado(meta.getValoralcancado() - valorantigo);
				}
				meta.setValoralcancado(meta.getValoralcancado() + venda.getValor());
			} else {
				meta.setValoralcancado(meta.getValoralcancado() - venda.getValor());
			}
			if (meta.getValormeta() > 0) {
				meta.setPercentualalcancado((meta.getValoralcancado() / meta.getValormeta()) * 100);
			}
			metaFaturamentoMensalFacade.salvar(meta);
		}
	}

	public int[] calcularPontuacao(Vendas venda, int numeroSemanas, String programa, boolean cancelamento, Usuario usuario) {
		int ponto = 0;
		int pontoEscola = 0;
		int idregra = 0;
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT u FROM Usuariopontos u where u.usuario.idusuario=" + usuario.getIdusuario()
				+ " and u.mes=" + mes + " and u.ano=" + ano;
		Usuariopontos usuariopontos = usuarioPontosFacade.consultar(sql);
		if (usuariopontos == null) {
			usuariopontos = new Usuariopontos();
			usuariopontos.setAno(ano);
			usuariopontos.setMes(mes);
			usuariopontos.setUsuario(usuario);
			usuariopontos.setPontos(0);
			usuariopontos.setTotalpontos(0);
		}
		if (cancelamento == false) {
			RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
			sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=" + venda.getProdutos().getIdprodutos()
					+ " and r.situacao=1 and r.escola=false";
			List<Regravenda> lista = regraVendaFacade.lista(sql);
			if (lista != null) { 
				for (int i = 0; i < lista.size(); i++) {
					boolean validar = validarRegra(lista.get(i), venda, numeroSemanas, programa);
					if (validar) {
						ponto = ponto + lista.get(i).getPonto(); 
						idregra = lista.get(i).getIdregravenda();
					}
				}
			}
			if (ponto <= 0) {
				ponto = 1;
			}
			if (usuariopontos.getPontoescola() == null) {
				usuariopontos.setPontoescola(0);
			}
			if(usuario.getIdusuario()==16) {
				usuariopontos.setTotalpontos(usuariopontos.getTotalpontos() + ponto - venda.getPonto()); 
				ponto = ponto/2;
				usuariopontos.setPontos(usuariopontos.getPontos() + ponto - venda.getPonto());  
			}else {
				usuariopontos.setPontos(usuariopontos.getPontos() + ponto - venda.getPonto()); 
				usuariopontos.setTotalpontos(usuariopontos.getPontos() + ponto - venda.getPonto()); 
			}
		} else {
			usuariopontos.setPontos(usuariopontos.getPontos() - venda.getPonto());
			usuariopontos.setPontoescola(usuariopontos.getPontoescola() - venda.getPontoescola());
		}
		int[] pontos = new int[3];
		pontos[0] = ponto;
		pontos[1] = pontoEscola;
		pontos[2] = idregra;
		usuariopontos = usuarioPontosFacade.salvar(usuariopontos);
		return pontos;
	}

	public int[] calcularPontuacao(Usuario usuario, Vendas venda, int numeroSemanas, String programa,
			boolean cancelamento) {
		int ponto = 0;
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT u FROM Usuariopontos u where u.usuario.idusuario=" + usuario.getIdusuario() + " and u.mes="
				+ mes + " and u.ano=" + ano;
		Usuariopontos usuariopontos = usuarioPontosFacade.consultar(sql);
		if (usuariopontos == null) {
			usuariopontos = new Usuariopontos();
			usuariopontos.setAno(ano);
			usuariopontos.setMes(mes);
			usuariopontos.setUsuario(usuario);
			usuariopontos.setPontos(0);
			usuariopontos.setPontoescola(0);
			usuariopontos.setTotalpontos(0);
		}
		if (usuariopontos.getPontoescola() == null) {
			usuariopontos.setPontoescola(0);
		}
		if (cancelamento == false) {
			RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
			sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=" + venda.getProdutos().getIdprodutos()
					+ " and r.situacao=1 and r.escola=false";
			List<Regravenda> lista = regraVendaFacade.lista(sql);
			if (lista != null) { 
				for (int i = 0; i < lista.size(); i++) {
					boolean validar = validarRegra(lista.get(i), venda, numeroSemanas, programa);
					if (validar) {
						ponto = ponto + lista.get(i).getPonto();
					} 
				}
			}
			if (ponto <= 0) {
				ponto = 1;
			}
			if(venda.getUsuario().getIdusuario()==16) {
				usuariopontos.setTotalpontos(usuariopontos.getTotalpontos() + ponto - venda.getPonto()); 
				ponto = ponto/2;
				usuariopontos.setPontos(usuariopontos.getPontos() + ponto - venda.getPonto());  
			}else {
				usuariopontos.setPontos(usuariopontos.getPontos() + ponto - venda.getPonto()); 
				usuariopontos.setTotalpontos(usuariopontos.getPontos() + ponto - venda.getPonto()); 
			}
		} else {
			usuariopontos.setPontos(usuariopontos.getPontos() - venda.getPonto());
		}
		int[] pontos = new int[2];
		pontos[0] = ponto;    
		pontos[1] = 0; 
		usuariopontos = usuarioPontosFacade.salvar(usuariopontos);
		return pontos;
	}

	public boolean validarRegra(Regravenda regra, Vendas venda, int numeroSemanas, String programa) {
		boolean validar = false;
		if ((regra.getValorinicial() != null) && (regra.getValorfinal() != null) && (regra.getValorinicial() > 0.0f)
				&& (regra.getValorfinal() > 0.0f)) {
			if ((regra.getValorinicial() <= venda.getValor()) && (regra.getValorfinal() >= venda.getValor())) {
				  validar = true;
			} else
				 validar =  false;
		}
		if ((regra.getNumerosemanasinicial() != null) && (regra.getNumerosemanafinal() != null)
				&& (regra.getNumerosemanasinicial() > 0) && (regra.getNumerosemanafinal() > 0)) {
			if ((regra.getNumerosemanasinicial() <= numeroSemanas) && (regra.getNumerosemanafinal() >= numeroSemanas)) {
				 validar = true;
			} else
				 validar = false;
		}
		if (regra.getPrograma() != null && regra.getPrograma().length() > 0) {
			if (regra.getPrograma().equalsIgnoreCase(programa)) {
				 validar = true;
			} else
				 validar = false;
		}
		if (regra.getPais() != null && regra.getPais() > 0) {
			if (regra.getPais() == venda.getFornecedorcidade().getCidade().getPais().getIdpais()) {
				 validar = true;
			} else
				 validar = false;
		}
		if (regra.getFornecedor() != null && regra.getFornecedor() > 0) {
			int fornecedor = venda.getFornecedor().getIdfornecedor();
			if (regra.getFornecedor() == fornecedor) {
				 validar = true;
			} else
				 validar = false;
		}
		if (regra.getCidade() != null && regra.getCidade() > 0) {
			if (regra.getCidade()==venda.getFornecedorcidade().getCidade().getIdcidade()) {
				 validar = true;
			} else
				 validar = false;
		}
		return validar;

	}
	
	/*public boolean validarRegraPacote(Regravenda regra, float valorItem, String programa) {
		boolean validar = false;
		if ((regra.getValorinicial() != null) && (regra.getValorfinal() != null) && (regra.getValorinicial() > 0)
				&& (regra.getValorfinal() > 0)) {
			if ((regra.getValorinicial() <= valorItem) && (regra.getValorfinal() >= valorItem)) {
				validar = true;
			} else
				return false;
		}
		
		if (regra.getPrograma() != null && regra.getPrograma().length() > 0) {
			if (regra.getPrograma().equalsIgnoreCase(programa)) {
				validar = true;
			} else
				return false;
		}
		return validar;

	}*/

	public int[] calcularPontuacaoRace(Vendas venda, int numeroSemanas, String programa, boolean cancelamento) {
		int pontoEscola = 0;
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT u FROM Usuariopontos u where u.usuario.idusuario=" + venda.getUsuario().getIdusuario()
				+ " and u.mes=" + mes + " and u.ano=" + ano;
		Usuariopontos usuariopontos = usuarioPontosFacade.consultar(sql);
		if (usuariopontos == null) {
			usuariopontos = new Usuariopontos();
			usuariopontos.setAno(ano);
			usuariopontos.setMes(mes);
			usuariopontos.setUsuario(venda.getUsuario());
			usuariopontos.setPontos(0);
			usuariopontos.setPontoescola(0);
			usuariopontos.setTotalpontos(0);
		}
		if (cancelamento == false) {
			RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
			sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=" + venda.getProdutos().getIdprodutos()
					+ " and r.situacao=1 and r.escola=true";
			List<Regravenda> lista = regraVendaFacade.lista(sql);
			if (lista != null) { 
				for (int i = 0; i < lista.size(); i++) {
					boolean validar = validarRegra(lista.get(i), venda, numeroSemanas, programa);
					if (validar) {
						pontoEscola = pontoEscola + lista.get(i).getPonto(); 
					}
				}
			}
			if (usuariopontos.getPontoescola() == null) {
				usuariopontos.setPontoescola(0);
			}
			usuariopontos.setPontoescola(usuariopontos.getPontoescola() + pontoEscola - venda.getPontoescola());
		} else {
			usuariopontos.setPontoescola(usuariopontos.getPontoescola() - venda.getPontoescola());
		}
		int[] pontos = new int[2];
		pontos[0] = pontoEscola;
		usuariopontos = usuarioPontosFacade.salvar(usuariopontos);
		return pontos;
	}
	
	/*public int calcularPontuacaoPacate(Vendas venda, boolean cancelamento, Pacotes pacote, Usuario usuarioFranquia){
		int pontos = 0;
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT u FROM Usuariopontos u where u.usuario.idusuario=" + usuarioFranquia.getIdusuario()
				+ " and u.mes=" + mes + " and u.ano=" + ano;
		Usuariopontos usuariopontosFranquia = usuarioPontosFacade.consultar(sql);
		if (usuariopontosFranquia == null) {
			usuariopontosFranquia = new Usuariopontos();
			usuariopontosFranquia.setAno(ano);
			usuariopontosFranquia.setMes(mes);
			usuariopontosFranquia.setUsuario(venda.getUsuario());
			usuariopontosFranquia.setPontos(0);
			usuariopontosFranquia.setPontoescola(0);
			usuariopontosFranquia.setTotalpontos(0);
		}
		
		
		if (cancelamento){
			usuariopontosFranquia.setPontos(usuariopontosFranquia.getPontos() - venda.getPonto());
		} else {
			if (pacote.getPacotetrechoList() != null) {
				pontos = pontos
						+ pontuacaoPacoteCarro(pacote.getPacotetrechoList(), venda);
				pontos = pontos + pontuacaoPacoteCircuito(
								pacote.getPacotetrechoList(), venda);
				pontos = pontos + pontuacaoPacoteCruzeiro(
								pacote.getPacotetrechoList(), venda);
				pontos = pontos
								+ pontuacaoPacoteHotel(pacote.getPacotetrechoList(), venda);
				pontos = pontos + pontuacaoPacoteIngresso(
								pacote.getPacotetrechoList(), venda);
				pontos = pontos + pontuacaoPacotePassagem(
								pacote.getPacotetrechoList(), venda);
				pontos = pontos + pontuacaoPacotePasseio(
								pacote.getPacotetrechoList(), venda);
				pontos = pontos + pontuacaoPacoteServico(
								pacote.getPacotetrechoList(), venda);
				pontos = pontos + pontuacaoPacoteTransfer(
								pacote.getPacotetrechoList(), venda);
				pontos = pontos
								+ pontuacaoPacoteTrem(pacote.getPacotetrechoList(), venda);
			}
		}
		if (pontos<=0){
			pontos =1;
		}
		if(usuarioFranquia.getIdusuario()==16) {
			usuariopontosFranquia.setTotalpontos(usuariopontosFranquia.getTotalpontos() + pontos - venda.getPonto()); 
			pontos = pontos/2;
			usuariopontosFranquia.setPontos(usuariopontosFranquia.getPontos() + pontos - venda.getPonto());  
		}else {
			usuariopontosFranquia.setPontos(usuariopontosFranquia.getPontos() + pontos - venda.getPonto()); 
			usuariopontosFranquia.setTotalpontos(usuariopontosFranquia.getPontos() + pontos- venda.getPonto()); 
		} 
		usuariopontosFranquia = usuarioPontosFacade.salvar(usuariopontosFranquia);
		return pontos;
	}*/
	
	/*public int pontuacaoPacoteCarro(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotecarroList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotecarroList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotecarroList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Carro' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Carro");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	*/
	/*public int pontuacaoPacoteCircuito(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotecircuitoList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotecircuitoList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotecircuitoList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Circuito' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Circuito");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacoteCruzeiro(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotecruzeiroList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotecruzeiroList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotecruzeiroList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Cruzeiro' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Cruzeiro");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacoteHotel(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotehotelList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotehotelList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotehotelList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Hotel' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Hotel");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacoteIngresso(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacoteingressoList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacoteingressoList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacoteingressoList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Ingresso' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Ingresso");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacotePassagem(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotepassagemList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotepassagemList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotepassagemList().get(c).getAdttarifa() + 
							listaTrecho.get(i).getPacotepassagemList().get(c).getInftarifa() + listaTrecho.get(i).getPacotepassagemList().get(c).getChdtarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Passagem' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Passagem");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacotePasseio(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotepasseioList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotepasseioList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotepasseioList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Passeio' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Passeio");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacoteServico(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacoteservicoList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacoteservicoList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacoteservicoList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Servico' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Servico");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacoteTransfer(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotetransferList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotetransferList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotetransferList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Transfer' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Transfer");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	public int pontuacaoPacoteTrem(List<Pacotetrecho> listaTrecho, Vendas venda){
		int pontos =0;
		float valorCarro=0.0f;
		for (int i=0;i<listaTrecho.size();i++){
			if (listaTrecho.get(i).getPacotetremList()!=null){
				for(int c=0;c<listaTrecho.get(i).getPacotetremList().size();c++){
					valorCarro = valorCarro + listaTrecho.get(i).getPacotetremList().get(c).getTarifa();
				}
			}
		}
		RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
		String sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=7 and r.programa='Trem' "
				+ " and r.situacao=1";
		List<Regravenda> lista = regraVendaFacade.lista(sql);
		if (lista!=null){
			for (int n = 0; n < lista.size(); n++) {
				boolean validar = validarRegraPacote(lista.get(n), valorCarro, "Trem");
				if (validar) {
					pontos = pontos + lista.get(n).getPonto(); 
				}
			}
		}
		return pontos;
	}
	
	
	public int[] calcularPontuacaoPassagem(Vendas venda, Usuario usuarioFranquia, int numeroSemanas, String programa, boolean cancelamento){
		int ponto = 0;
		int idregra = 0;
		int ano = Formatacao.getAnoData(new Date());
		int mes = Formatacao.getMesData(new Date()) + 1;
		UsuarioPontosFacade usuarioPontosFacade = new UsuarioPontosFacade();
		String sql = "SELECT u FROM Usuariopontos u where u.usuario.idusuario=" + usuarioFranquia.getIdusuario()
				+ " and u.mes=" + mes + " and u.ano=" + ano;
		Usuariopontos usuariopontosFranquia = usuarioPontosFacade.consultar(sql);
		if (usuariopontosFranquia == null) {
			usuariopontosFranquia = new Usuariopontos();
			usuariopontosFranquia.setAno(ano);
			usuariopontosFranquia.setMes(mes);
			usuariopontosFranquia.setUsuario(usuarioFranquia);
			usuariopontosFranquia.setPontos(0);
			usuariopontosFranquia.setPontoescola(0);
			usuariopontosFranquia.setTotalpontos(0);
		}
		
		
		if (cancelamento){
			usuariopontosFranquia.setPontos(usuariopontosFranquia.getPontos() - venda.getPonto());
		} else {
			RegraVendaFacade regraVendaFacade = new RegraVendaFacade();
			sql = "SELECT r FROM Regravenda r where r.produtos.idprodutos=" + venda.getProdutos().getIdprodutos()
					+ " and r.situacao=1 and r.escola=false";
			List<Regravenda> lista = regraVendaFacade.lista(sql);
			if (lista != null) { 
				for (int i = 0; i < lista.size(); i++) {
					boolean validar = validarRegraPassagem(lista.get(i), venda, numeroSemanas, programa);
					if (validar) {
						ponto = ponto + lista.get(i).getPonto(); 
						idregra = lista.get(i).getIdregravenda();
					}
				}
			}
		}
		if (ponto<=0){
			ponto =1;
		}
		if(usuarioFranquia.getIdusuario()==16) {
			usuariopontosFranquia.setTotalpontos(usuariopontosFranquia.getTotalpontos() + ponto - venda.getPonto()); 
			ponto = ponto/2;
			usuariopontosFranquia.setPontos(usuariopontosFranquia.getPontos() + ponto - venda.getPonto());  
		}else {
			usuariopontosFranquia.setPontos(usuariopontosFranquia.getPontos() + ponto - venda.getPonto()); 
			usuariopontosFranquia.setTotalpontos(usuariopontosFranquia.getPontos()); 
		}
		usuariopontosFranquia = usuarioPontosFacade.salvar(usuariopontosFranquia);
		int[] pontos = new int[3];
		pontos[0] = ponto;
		pontos[1] = 0;
		pontos[2] = idregra;
		return pontos;
	}
	
	
	
	public boolean validarRegraPassagem(Regravenda regra, Vendas venda, int numeroSemanas, String programa) {
		boolean validar = false;
		if ((regra.getValorinicial() != null) && (regra.getValorfinal() != null) && (regra.getValorinicial() > 0)
				&& (regra.getValorfinal() > 0)) {
			if ((regra.getValorinicial() <= venda.getValor()) && (regra.getValorfinal() >= venda.getValor())) {
				validar = true;
			} else
				validar =  false;
		}
		return validar;

	}*/
	
}
