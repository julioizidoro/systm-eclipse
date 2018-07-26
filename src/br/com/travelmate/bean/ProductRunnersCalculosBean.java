package br.com.travelmate.bean;

import java.util.Date;

import br.com.travelmate.facade.CorridaProdutoAnoFacade;
import br.com.travelmate.facade.CorridaProdutoMesFacade;
import br.com.travelmate.model.Corridaprodutoano;
import br.com.travelmate.model.Corridaprodutomes;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendas;
import br.com.travelmate.util.Formatacao;

public class ProductRunnersCalculosBean {
	
	public void calcularPontuacao(Vendas vendas, int pontos, int pontoremover, boolean cancelamento, Usuario usuario){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		Corridaprodutomes corridaprodutomes;
		Corridaprodutoano corridaprodutoano;
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		if (cancelamento) {
			corridaprodutomes = corridaProdutoMesFacade.consultar("SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + usuario.getIdusuario());
			if (corridaprodutomes == null) {
				corridaprodutomes = new Corridaprodutomes();
				corridaprodutomes.setAno(ano);
				corridaprodutomes.setMes(mes);
				corridaprodutomes.setPontos(0);
				corridaprodutomes.setProdutos(vendas.getProdutos());
				corridaprodutomes.setUsuario(usuario);
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}else{
				corridaprodutomes.setPontos(corridaprodutomes.getPontos() - pontos);
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}
			corridaprodutoano = corridaProdutoAnoFacade.consultar("SELECT c FROM Corridaprodutoano c WHERE  c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + usuario.getIdusuario());
			if (corridaprodutoano == null) {
				corridaprodutoano = new Corridaprodutoano();
				corridaprodutoano.setAno(ano);
				corridaprodutoano.setPontos(0);
				corridaprodutoano.setProdutos(vendas.getProdutos());
				corridaprodutoano.setUsuario(usuario);
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}else{
				corridaprodutoano.setPontos(corridaprodutoano.getPontos() - pontos);
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}
		}else{
			corridaprodutomes = corridaProdutoMesFacade.consultar("SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + usuario.getIdusuario());
			if (corridaprodutomes == null) {
				corridaprodutomes = new Corridaprodutomes();
				corridaprodutomes.setAno(ano);
				corridaprodutomes.setMes(mes);
				corridaprodutomes.setPontos(pontos);
				corridaprodutomes.setProdutos(vendas.getProdutos());
				corridaprodutomes.setUsuario(usuario);
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}else{
				corridaprodutomes.setPontos(corridaprodutomes.getPontos() + pontos - pontoremover);
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}
			corridaprodutoano = corridaProdutoAnoFacade.consultar("SELECT c FROM Corridaprodutoano c WHERE  c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + usuario.getIdusuario());
			if (corridaprodutoano == null) {
				corridaprodutoano = new Corridaprodutoano();
				corridaprodutoano.setAno(ano);
				corridaprodutoano.setPontos(pontos);
				corridaprodutoano.setProdutos(vendas.getProdutos());
				corridaprodutoano.setUsuario(usuario);
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}else{
				corridaprodutoano.setPontos(corridaprodutoano.getPontos() + pontos - pontoremover);
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}
		}
	}
	
	
	public void calcularPontuacaoPacote(Usuario usuarioFranquia, Produtos produtos, boolean cancelamento, int pontos){
		
			Vendas vendasFranquia = new Vendas();
			vendasFranquia.setUsuario(usuarioFranquia);
			vendasFranquia.setProdutos(produtos);
			calcularPontuacaoPacote(vendasFranquia, pontos, cancelamento, 0);
	}
	
	
	
	public void calcularPontuacaoPacote(Vendas vendas, int pontos, boolean cancelamento, int pontonegativo){
		CorridaProdutoMesFacade corridaProdutoMesFacade = new CorridaProdutoMesFacade();
		CorridaProdutoAnoFacade corridaProdutoAnoFacade = new CorridaProdutoAnoFacade();
		Corridaprodutomes corridaprodutomes;
		Corridaprodutoano corridaprodutoano;
		int mes = Formatacao.getMesData(new Date()) + 1;
		int ano = Formatacao.getAnoData(new Date());
		if (cancelamento) {
			corridaprodutomes = corridaProdutoMesFacade.consultar("SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutomes == null) {
				corridaprodutomes = new Corridaprodutomes();
				corridaprodutomes.setAno(ano);
				corridaprodutomes.setMes(mes);
				corridaprodutomes.setPontos(0);
				corridaprodutomes.setProdutos(vendas.getProdutos());
				corridaprodutomes.setUsuario(vendas.getUsuario());
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}else{
				corridaprodutomes.setPontos(corridaprodutomes.getPontos() - pontos);
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}
			corridaprodutoano = corridaProdutoAnoFacade.consultar("SELECT c FROM Corridaprodutoano c WHERE  c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutoano == null) {
				corridaprodutoano = new Corridaprodutoano();
				corridaprodutoano.setAno(ano);
				corridaprodutoano.setPontos(0);
				corridaprodutoano.setProdutos(vendas.getProdutos());
				corridaprodutoano.setUsuario(vendas.getUsuario());
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}else{
				corridaprodutoano.setPontos(corridaprodutoano.getPontos() - pontos);
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}
		}else{
			corridaprodutomes = corridaProdutoMesFacade.consultar("SELECT c FROM Corridaprodutomes c WHERE c.mes=" + mes + " and c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutomes == null) {
				corridaprodutomes = new Corridaprodutomes();
				corridaprodutomes.setAno(ano);
				corridaprodutomes.setMes(mes);
				corridaprodutomes.setPontos(pontos);
				corridaprodutomes.setProdutos(vendas.getProdutos());
				corridaprodutomes.setUsuario(vendas.getUsuario());
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}else{
				corridaprodutomes.setPontos(corridaprodutomes.getPontos() + pontos - vendas.getPonto());
				corridaprodutomes = corridaProdutoMesFacade.salvar(corridaprodutomes);
			}
			corridaprodutoano = corridaProdutoAnoFacade.consultar("SELECT c FROM Corridaprodutoano c WHERE  c.ano=" + ano + 
					" and c.produtos.idprodutos=" + vendas.getProdutos().getIdprodutos() + " and c.usuario.idusuario=" + vendas.getUsuario().getIdusuario());
			if (corridaprodutoano == null) {
				corridaprodutoano = new Corridaprodutoano();
				corridaprodutoano.setAno(ano);
				corridaprodutoano.setPontos(pontos);
				corridaprodutoano.setProdutos(vendas.getProdutos());
				corridaprodutoano.setUsuario(vendas.getUsuario());
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}else{
				corridaprodutoano.setPontos(corridaprodutoano.getPontos() + pontos - vendas.getPonto());
				corridaprodutoano = corridaProdutoAnoFacade.salvar(corridaprodutoano);
			}
		}
	}

}
