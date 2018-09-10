package br.com.travelmate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Produtosorcamento;

@FacesConverter(value="ProdutoOrcamentoVinculadoConverter")
public class ProdutoOrcamentoVinculadoConverter implements Converter{
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Filtroorcamentoproduto> listaProdutos = (List<Filtroorcamentoproduto>) component.getAttributes().get("listaProdutosVinculado");
        if (listaProdutos != null) {
            for (Filtroorcamentoproduto produto : listaProdutos) {
                if (produto.getProdutosorcamento().getDescricao().equalsIgnoreCase(value)) {
                    return produto.getProdutosorcamento();
                }
            }
        } else {
            return new Produtosorcamento();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            Produtosorcamento produto = (Produtosorcamento) value;
            return produto.getDescricao();
        }
    }

}
