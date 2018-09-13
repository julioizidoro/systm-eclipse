package br.com.travelmate.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.travelmate.model.Produtosorcamentoindice;

/**
 *
 * @author Wolverine
 */
@SuppressWarnings("unchecked")
@FacesConverter(value="ProdutoOrcamentoIndiceConverter")
public class ProdutoOrcamentoIndiceConverter implements Converter{
    
    
	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	List<Object> lista =  (List<Object>) component.getAttributes().get("listaProdutos");
        if (lista != null) {
            for (Object produto : lista) {
            	if (produto instanceof Produtosorcamentoindice){
            		Produtosorcamentoindice produtotoorcamentoindice = (Produtosorcamentoindice) produto;
            		if (produtotoorcamentoindice.getDescricao().equalsIgnoreCase(value)) {
            			return produtotoorcamentoindice;
            		}
            	}
            }
        } else {
            return new Produtosorcamentoindice();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            Produtosorcamentoindice produto = (Produtosorcamentoindice) value;
            return produto.getDescricao();
        }
    }
    
}