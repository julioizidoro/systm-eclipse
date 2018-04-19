/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.bean.comissao;

import br.com.travelmate.facade.ProdutoFacade;
import br.com.travelmate.facade.UsuarioFacade;
import br.com.travelmate.facade.VendasComissaoFacade;
import br.com.travelmate.model.Produtos;
import br.com.travelmate.model.Usuario;
import br.com.travelmate.model.Vendascomissao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wolverine
 */
public class CalcularComissao {
    private List<ComissaoBean> listaComissao;
    private String dataInical;
    private String dataFinal;

    public CalcularComissao(String dataInical, String dataFinal) {
        this.dataInical = dataInical;
        this.dataFinal = dataFinal;
        listaComissao = new  ArrayList<ComissaoBean>();
        iniciarCalculoComissao();
    }

    
    
    public List<ComissaoBean> getListaComissao() {
        return listaComissao;
    }

    public void setListaComissao(List<ComissaoBean> listaComissao) {
        this.listaComissao = listaComissao;
    }

    public String getDataInical() {
        return dataInical;
    }

    public void setDataInical(String dataInical) {
        this.dataInical = dataInical;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }
    
    public void iniciarCalculoComissao(){
        ProdutoFacade produtoFacade = new ProdutoFacade();
        List<Produtos> listaProduto = produtoFacade.listarProdutos("");
        if (listaProduto != null) {
            for (int i = 0; i < listaProduto.size(); i++) {
                if (listaProduto.get(i).getIdgerente() > 0) {
                    gerarComissaoGeretes(listaProduto.get(i).getIdprodutos(), listaProduto.get(i).getIdgerente(), listaProduto.get(i).getDescricao());
                }
            }
        }
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        List<Usuario> listaUsuario = usuarioFacade.listaUsuarioUnidade(1);
        if (listaUsuario!=null){
            for(int i=0;i<listaUsuario.size();i++){
                if (listaUsuario.get(i).getSituacao().equalsIgnoreCase("Ativo")){
                    if ((listaUsuario.get(i).getCargo().getIdcargo()==2) || (listaUsuario.get(i).getCargo().getIdcargo()==15)){
                        gerarComissaoConsultor(listaUsuario.get(i));
                    }
                }
            }
        }
        listaUsuario = usuarioFacade.listaUsuarioUnidade(2);
        if (listaUsuario!=null){
            for(int i=0;i<listaUsuario.size();i++){
                if (listaUsuario.get(i).getSituacao().equalsIgnoreCase("Ativo")){
                	if ((listaUsuario.get(i).getCargo().getIdcargo()==2) || (listaUsuario.get(i).getCargo().getIdcargo()==15)){
                        gerarComissaoConsultor(listaUsuario.get(i));
                    }
                }
            }
        }
        
        listaUsuario = usuarioFacade.listaUsuarioUnidade(6);
        if (listaUsuario!=null){
            for(int i=0;i<listaUsuario.size();i++){
                if (listaUsuario.get(i).getSituacao().equalsIgnoreCase("Ativo")){
                	if ((listaUsuario.get(i).getCargo().getIdcargo()==2) || (listaUsuario.get(i).getCargo().getIdcargo()==15)){
                        gerarComissaoConsultor(listaUsuario.get(i));
                    }
                }
            }
        }
    }
    
    public void gerarComissaoGeretes(int idProduto, int idGerente, String produto){
        VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
        String sql = "Select v from Vendascomissao v where v.vendas.dataVenda>='" + dataInical +
                "' and v.vendas.dataVenda<='" + dataFinal + "' and v.vendas.produtos.idprodutos=" + idProduto;
        List<Vendascomissao> lista = vendasComissaoFacade.listar(sql);
        float valorComissao=0.0f;
        float valorBruto=0.0f;
        if (lista!=null){
            for(int i=0;i<lista.size();i++){
                valorBruto = valorBruto + lista.get(i).getValorbruto();
                valorComissao = valorComissao + lista.get(i).getComissaogerente();
            }
        }
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario usuario = usuarioFacade.consultar(idGerente);
        ComissaoBean comissaoBean = new ComissaoBean();
        comissaoBean.setCargo("Gerente - " + produto);
        comissaoBean.setComissao(valorComissao);
        comissaoBean.setNome(usuario.getNome());
        comissaoBean.setUnidade(usuario.getUnidadenegocio().getNomeFantasia());
        comissaoBean.setValorBruto(valorBruto);
        listaComissao.add(comissaoBean);
    }
    
    public void gerarComissaoConsultor(Usuario usuario){
        VendasComissaoFacade vendasComissaoFacade = new VendasComissaoFacade();
        String sql = "Select v from Vendascomissao v where v.vendas.dataVenda>='" + dataInical +
                "' and v.vendas.dataVenda<='" + dataFinal + "' and v.vendas.usuario.idusuario=" + usuario.getIdusuario();
        List<Vendascomissao> lista = vendasComissaoFacade.listar(sql);
        float valorComissao=0.0f;
        float valorBruto=0.0f;
        if (lista!=null){
            for(int i=0;i<lista.size();i++){
                valorBruto = valorBruto + lista.get(i).getValorbruto();
                valorComissao = valorComissao + lista.get(i).getComissaogerente();
            }
        }
        ComissaoBean comissaoBean = new ComissaoBean();
        comissaoBean.setCargo("Consultor");
        comissaoBean.setComissao(valorComissao);
        comissaoBean.setNome(usuario.getNome());
        comissaoBean.setUnidade(usuario.getUnidadenegocio().getNomeFantasia());
        comissaoBean.setValorBruto(valorBruto);
        listaComissao.add(comissaoBean);
    }
    
}
