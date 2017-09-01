package br.com.travelmate.converter;

import br.com.travelmate.model.Filtroorcamentoproduto;
import br.com.travelmate.model.Produtosorcamento;


import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Wolverine
 */
@FacesConverter(value="ProdutoOrcamentoConverter")
public class ProdutoOrcamentoConverter implements Converter{
    
    
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Object> lista =  (List<Object>) component.getAttributes().get("listaProdutos");
        if (lista != null) {
            for (Object produto : lista) {
            	if (produto instanceof Filtroorcamentoproduto){
            		Filtroorcamentoproduto filtro = (Filtroorcamentoproduto) produto;
            		if (filtro.getProdutosorcamento().getDescricao().equalsIgnoreCase(value)) {
            			return filtro.getProdutosorcamento();
            		}
            	}else if (produto instanceof Produtosorcamento){
            		return (Produtosorcamento) produto;
            			
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