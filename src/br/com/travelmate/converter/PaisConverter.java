package br.com.travelmate.converter;
  
import br.com.travelmate.model.Pais;
import br.com.travelmate.model.Paisproduto;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Wolverine
 */
@FacesConverter(value="PaisConverter")
public class PaisConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        List<Paisproduto> listaPais = (List<Paisproduto>) component.getAttributes().get("listaPais");
        if (listaPais != null) {
            for (Paisproduto paisProduto : listaPais) {
                if (paisProduto.getPais().getNome().equalsIgnoreCase(value)) {
                    return paisProduto.getPais();
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
