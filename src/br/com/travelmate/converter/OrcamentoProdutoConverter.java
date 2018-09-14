package br.com.travelmate.converter;

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
@FacesConverter(value="OrcamentoProdutoConverter")
public class OrcamentoProdutoConverter implements Converter{

    @SuppressWarnings("unchecked")
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Produtosorcamento> listaProdutos = (List<Produtosorcamento>) component.getAttributes().get("listaProdutosOrcamento");
        if (listaProdutos != null) {
            for (Produtosorcamento produto : listaProdutos) {
                if (produto.getDescricao().equalsIgnoreCase(value)) {
                    return produto;
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