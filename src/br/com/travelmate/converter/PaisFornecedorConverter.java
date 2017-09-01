package br.com.travelmate.converter;
  
import br.com.travelmate.model.Pais;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Wolverine
 */
@FacesConverter(value="PaisFornecedorConverter")
public class PaisFornecedorConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Pais> listaPaisFornecedor = (List<Pais>) component.getAttributes().get("listaPaisFornecedor");
        if (listaPaisFornecedor != null) {
            for (Pais pais : listaPaisFornecedor) {
                if (pais.getNome().equalsIgnoreCase(value)) {
                    return pais;
                }
            }
        } else {
            return new Pais();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value.toString().equalsIgnoreCase("0")){
            return "Selecione";
        }else {
            if (value instanceof Pais){
                Pais pais = (Pais) value;
                return pais.getNome();
            }else return "";
        }
    }
    
}
